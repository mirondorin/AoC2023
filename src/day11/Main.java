package day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Point> galaxies = new ArrayList<>();
    static int[] colVisited = new int[10000000];
    static int[] rowVisited = new int[10000000];

    static int m, n;

    public static void main(String[] args) {
        File file = new File("src/day11/input");
        int rowIndex = 0;
        n = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                int colIndex = 0;
                for (char c : line.toCharArray()) {
                    if (c == '#') {
                        galaxies.add(new Point(rowIndex, colIndex));
                        rowVisited[rowIndex] = 1;
                        colVisited[colIndex] = 1;
                    }
                    colIndex++;
                }

                rowIndex++;


                m = colIndex;
                n++;
            }
            m -= 1;
            n -= 1;

            expandGalaxies();
            System.out.println(sumOfAllDistances());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void expandGalaxies() {
        for (Point point : galaxies) {
            int rowsToAdd = 0, colsToAdd = 0;
            for (int i = 0; i < m; i++) {
                if (colVisited[i] == 0 && i < point.y) {
                    colsToAdd++;
                }
            }

            for (int i = 0; i < n; i++) {
                if (rowVisited[i] == 0 && i < point.x) {
                    rowsToAdd++;
                }
            }

            point.x += rowsToAdd * (1000000 - 1);
            point.y += colsToAdd * (1000000 - 1);
        }
    }

    public static long sumOfAllDistances() {
        long sum = 0;
        for (int i = 0; i < galaxies.size() - 1; i++) {
            Point start = galaxies.get(i);

            for (int j = i + 1 ; j < galaxies.size(); j++) {
                long dist = start.distanceBetween(galaxies.get(j));
                //System.out.printf("%d %d: %d", i+1, j+1, dist);
                //System.out.println();
                sum += start.distanceBetween(galaxies.get(j));
            }
        }

        return sum;
    }
}
