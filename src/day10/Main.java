package day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {
    static int rowStart = -1;
    static int colStart = -1;
    static final int MAX_ROWS = 140;
    static int ROWS;
    static int COLUMNS;
    static final int MAX_COLUMNS = 140;
    static int[][] visited = new int[MAX_ROWS][MAX_COLUMNS];
    static char[][] maze = new char[MAX_ROWS][MAX_COLUMNS];
    static int maxDistance = 0;
    static int inside = 0;

    public static void main(String[] args) {
        File file = new File("src/day10/input");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int rowIndex = 0;

            while ((line = reader.readLine()) != null) {
                int colIndex = 0;
                for (char c : line.toCharArray()) {
                    if (c == 'S') {
                        rowStart = rowIndex;
                        colStart = colIndex;
                    }

                    visited[rowIndex][colIndex] = -1;
                    maze[rowIndex][colIndex] = c;
                    colIndex++;
                }
                COLUMNS = colIndex;
                rowIndex++;
            }
            ROWS = rowIndex;

            setStartPointPipe();
            bfs();
            System.out.println(maxDistance);

            deleteOutsideWalls();
            countInside();
            printMaze();
            System.out.println(inside);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void printMaze() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    public static void countInside() {
        for (int i = 0; i < ROWS; i++) {
            boolean isInside = false;
            for (int j = 0; j < COLUMNS; j++) {
                if (Arrays.asList('|', '7', 'F').contains(maze[i][j])) {
                    isInside = !isInside;
                } else if (maze[i][j] == '.') {
                    if (isInside) {
                        maze[i][j] = 'I';
                        inside++;
                    }
                }
            }
        }
    }

    public static void setStartPointPipe() {
        if (Arrays.asList('-', '7').contains(maze[rowStart][colStart + 1])) {
            maze[rowStart][colStart] = 'F';
        }

        if (Arrays.asList('-', 'F').contains(maze[rowStart][colStart - 1])) {
            maze[rowStart][colStart] = '7';
        }
    }

    public static void deleteOutsideWalls() {
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLUMNS; j++) {
                if (visited[i][j] == -1) {
                    maze[i][j] = '.';
                }
            }
        }
    }

    public static void bfs() {
        Queue<Point> queue = new ArrayDeque<>();
        queue.add(new Point(rowStart, colStart, 0));
        visited[rowStart][colStart] = 0;

        while (!queue.isEmpty()) {
            visitAdjacent(queue.remove(), queue);
        }
    }

    public static void visitAdjacent(Point point, Queue<Point> queue) {

        // N W E S
        int[] dx = {-1, 0, 0, 1};
        int[] dy = {0, -1, 1, 0};
        List<List<Character>> permittedChars = List.of(Arrays.asList('|', '7', 'F'), Arrays.asList('-', 'L', 'F'),
                Arrays.asList('-', '7', 'J'), Arrays.asList('|', 'L', 'J'));

        for (int i = 0; i < dx.length; i++) {
            int nextY = point.y + dy[i];
            int nextX = point.x + dx[i];
            if (nextX < 0 || nextX >= ROWS || nextY < 0 || nextY >= COLUMNS) {
                continue;
            }

            if (visited[nextX][nextY] == -1 && permittedChars.get(i).contains(maze[nextX][nextY])
                    && pipeClosesLoop(nextX, nextY)) {
                int nextDistance = point.distance + 1;
                visited[nextX][nextY] = nextDistance;
                queue.add(new Point(nextX, nextY, nextDistance));

                if (nextDistance > maxDistance) {
                    maxDistance = nextDistance;
                }
            }
        }
    }

    public static boolean pipeClosesLoop(int posx, int posy) {
        char pipe = maze[posx][posy];
        char left = posy - 1 < 0 ? '|' : maze[posx][posy - 1];
        char right = posy + 1 >= COLUMNS ? '|' : maze[posx][posy + 1];
        char up = posx - 1 < 0 ? '-' : maze[posx - 1][posy];
        char down = posx + 1 >= ROWS ? '-' : maze[posx + 1][posy];

        return switch (pipe) {
            case 'F' -> Arrays.asList('-', '7', 'J').contains(right) && Arrays.asList('|', 'L', 'J').contains(down);
            case '7' -> Arrays.asList('-', 'F', 'L').contains(left) && Arrays.asList('|', 'L', 'J').contains(down);
            case '|' -> Arrays.asList('|', '7', 'F').contains(up) && Arrays.asList('|', 'L', 'J').contains(down);
            case '-' -> Arrays.asList('-', 'F', 'L').contains(left) && Arrays.asList('-', '7', 'J').contains(right);
            case 'J' -> Arrays.asList('|', '7', 'F').contains(up) && Arrays.asList('-', 'F', 'L').contains(left);
            case 'L' -> Arrays.asList('|', '7', 'F').contains(up) && Arrays.asList('-', '7', 'J').contains(right);
            default -> true;
        };
    }
}
