package day3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        File file = new File("src/day3/input");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            char[][] matrix = new char[141][141];
            int sum = 0;

            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                int j = 0;
                for (char c : line.toCharArray()) {
                    matrix[i][j] = c;
                    j++;
                }
                i++;
            }

            Map<Star, List<Integer>> stars = new HashMap<>();

            for (int ii = 0; ii < matrix.length; ii++) {
                StringBuilder number = new StringBuilder();
                List<Star> visitedStars = new ArrayList<>();
                boolean numberAdjacentToSymbol = false;

                for (int jj = 0; jj < matrix.length; jj++) {
                    if (Character.isDigit(matrix[ii][jj])) {
                        numberAdjacentToSymbol = numberAdjacentToSymbol || isAdjacentToStar(matrix, ii, jj, visitedStars);
                        number.append(matrix[ii][jj]);
                    } else {
                        if (numberAdjacentToSymbol) {
                            for (Star star: visitedStars) {
                                if(stars.containsKey(star)) {
                                    List<Integer> numbersArray = stars.get(star);
                                    numbersArray.add(Integer.parseInt(number.toString()));
                                    stars.replace(star, numbersArray);
                                } else {
                                    List<Integer> numbersArray = new ArrayList<>();
                                    numbersArray.add(Integer.parseInt(number.toString()));
                                    stars.put(star, numbersArray);
                                }
                            }
                            visitedStars = new ArrayList<>();

                        }
                        number = new StringBuilder();
                        numberAdjacentToSymbol = false;
                    }
                }
            }

            for (Map.Entry<Star, List<Integer>> entry: stars.entrySet()) {
                List<Integer> adjacentNumbers = entry.getValue();
                if(adjacentNumbers.size() == 2) {
                    sum += adjacentNumbers.get(0) * adjacentNumbers.get(1);
                }
            }
            System.out.println(sum);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isAdjacentToSymbol(char[][] matrix, int x, int y) {
        boolean isAdjacent = false;
        int dx[] = {-1, -1, -1, 0, 0, 1, 1, 1};
        int dy[] = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < dx.length; i++) {
            if ((x == 0 && dx[i] == -1) || (x == matrix.length && dx[i] == 1)
                    || (y == 0 && dy[i] == -1) || (y >= matrix.length - 1 && dy[i] == 1)) {
                continue;
            }

            char value = matrix[x + dx[i]][y + dy[i]];
            if (!Character.isDigit(value) && value != '.' && value != '\u0000') {
                isAdjacent = true;
                break;
            }
        }
        return isAdjacent;
    }

    public static boolean isAdjacentToStar(char[][] matrix, int x, int y, List<Star> visitedStars) {
        boolean isAdjacent = false;
        int dx[] = {-1, -1, -1, 0, 0, 1, 1, 1};
        int dy[] = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < dx.length; i++) {
            if ((x == 0 && dx[i] == -1) || (x == matrix.length && dx[i] == 1)
                    || (y == 0 && dy[i] == -1) || (y >= matrix.length - 1 && dy[i] == 1)) {
                continue;
            }

            char value = matrix[x + dx[i]][y + dy[i]];
            if (value == '*') {
                isAdjacent = true;
                visitedStars.add(new Star(x + dx[i], y + dy[i]));
            }
        }
        return isAdjacent;
    }
}
