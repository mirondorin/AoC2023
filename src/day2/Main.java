package day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static final int MAX_RED_CUBES = 12;
    static final int MAX_GREEN_CUBES = 13;
    static final int MAX_BLUE_CUBES = 14;


    public static void main(String[] args) {
        File file = new File("src/day2/input");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int sum = 0;

            while ((line = reader.readLine()) != null) {
                sum += powerSumOfCubes(line);
            }

            System.out.println(sum);
        } catch (Exception ex) {
            System.out.println("It brokey!");
        }
    }

    public static int gamePossible(String line) {
        String game = line.split(":")[0];
        Pattern pattern = Pattern.compile("([1-9]*[0-9]+)");
        Matcher matcher = pattern.matcher(game);
        matcher.find();
        int gameId = Integer.parseInt(matcher.group());

        String cubes = line.split(":")[1];
        String[] cubeSets = cubes.split(";");

        for(String cubeSet : cubeSets) {
            String[] cubesInSet = cubeSet.split(",");
            for(String cube: cubesInSet) {
                matcher = pattern.matcher(cube);
                matcher.find();
                int cubeCount = Integer.parseInt(matcher.group());

                if(cube.contains("red")) {
                    if (cubeCount > MAX_RED_CUBES) return 0;
                } else if(cube.contains("green")) {
                    if (cubeCount > MAX_GREEN_CUBES) return 0;
                } else if(cube.contains("blue")) {
                    if(cubeCount > MAX_BLUE_CUBES) return 0;
                }
            }
        }
        return gameId;
    }

    public static int powerSumOfCubes(String line) {
        String game = line.split(":")[0];
        Pattern pattern = Pattern.compile("([1-9]*[0-9]+)");
        Matcher matcher = pattern.matcher(game);
        matcher.find();
        int gameId = Integer.parseInt(matcher.group());

        String cubes = line.split(":")[1];
        String[] cubeSets = cubes.split(";");

        int minRedCubes = 0;
        int minGreenCubes = 0;
        int minBlueCubes = 0;

        for(String cubeSet : cubeSets) {


            String[] cubesInSet = cubeSet.split(",");
            for(String cube: cubesInSet) {
                matcher = pattern.matcher(cube);
                matcher.find();
                int cubeCount = Integer.parseInt(matcher.group());

                if(cube.contains("red")) {
                    if (cubeCount > minRedCubes) {
                        minRedCubes = cubeCount;
                    }
                } else if(cube.contains("green")) {
                    if (cubeCount > minGreenCubes) {
                        minGreenCubes = cubeCount;
                    }
                } else if(cube.contains("blue")) {
                    if(cubeCount > minBlueCubes) {
                        minBlueCubes = cubeCount;
                    }
                }
            }
        }
        return minRedCubes * minGreenCubes * minBlueCubes;
    }
}
