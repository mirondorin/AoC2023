package day14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Optional;

public class Part1 {
    static char[][] matrix = new char[100][100];
    static int rows;
    static int columns;

    public static void main(String[] args) {
        File file = new File("src/day14/input");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                columns = 0;
                for (char c : line.toCharArray()) {
                    matrix[rows][columns] = c;
                    columns++;
                }
                rows++;
            }

            for (int i = 0; i < columns; i++) {
                moveRocksForColumn(i);
            }
            printMatrix();
            System.out.println(calculateLoad(matrix));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void moveRocksForColumn(int column) {
        boolean movedRock = true;

        while (movedRock) {
            movedRock = false;
            for (int j = 0; j < rows; j++) {
                Point firstSpace = getFirstElementOf(column, j, '.');
                for (int i = 0; i < rows; i++) {
                    Point firstRock = getFirstElementOf(column, i, 'O');
                    if (firstSpace == null || firstRock == null) {
                        continue;
                    }

                    if (firstSpace.x < firstRock.x) {
                        Optional<Point> firstCubeBlockingSpace = cubeAfterSpace(firstSpace);
                        if (firstCubeBlockingSpace.isEmpty() || firstCubeBlockingSpace.get().x > firstRock.x) {
                            movedRock = moveRock(firstRock.x, firstRock.y, firstSpace.x, firstSpace.y);
                            firstSpace = getFirstElementOf(column, 0, '.');
                        }
                    }
                }
            }
        }
    }

    private static Optional<Point> cubeAfterSpace(Point space) {
        for (int i = space.x; i < rows; i++) {
            if (matrix[i][space.y] == '#') return Optional.of(new Point(i, space.y));
        }

        return Optional.empty();
    }

    private static Point getFirstElementOf(int column, int fromRow, char element) {

        for (int i = fromRow; i < rows; i++) {
            if (matrix[i][column] == element) {
                return new Point(i, column);
            }
        }

        return null;
    }

    private static boolean moveRock(int fromX, int fromY, int toX, int toY) {
        matrix[fromX][fromY] = '.';
        matrix[toX][toY] = 'O';
        return true;
    }

    public static long calculateLoad(char[][] matrix) {
        long sum = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == 'O') {
                    sum += rows - i;
                }
            }
        }
        return sum;
    }

    private static void printMatrix() {
        System.out.println();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }
}
