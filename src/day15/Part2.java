package day15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Part2 {

    public static void main(String[] args) {
        File file = new File("src/day15/input");
        Map<Integer, Map<String, Integer>> boxes = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            long sum = 0;

            while ((line = reader.readLine()) != null) {
                String[] strings = line.split(",");

                for (String string : strings) {
                    String[] tokens = new String[3];
                    int lens = 0;

                    if (string.contains("-")) {
                        tokens = string.split("-");
                    } else if (string.contains("=")) {
                        tokens = string.split("=");
                        lens = Integer.parseInt(tokens[1]);
                    }

                    String letters = tokens[0];
                    int box = getHash(letters);
                    if (!boxes.containsKey(box)) {
                        boxes.put(box, new LinkedHashMap<>());
                    }
                    Map<String, Integer> boxLettersMap = boxes.get(box);

                    if (string.contains("-")) {
                        boxLettersMap.remove(letters);
                    } else if (string.contains("=")) {
                        boxLettersMap.put(letters, lens);
                    }
                }
            }

            for (Map.Entry<Integer, Map<String, Integer>> ii : boxes.entrySet()) {
                Map<String, Integer> boxLettersMap = ii.getValue();
                int index = 1;

                for (Map.Entry<String, Integer> jj : boxLettersMap.entrySet()) {
                    sum += (long) (ii.getKey() + 1) * index * jj.getValue();
                    index++;
                }
            }
            System.out.println(sum);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static int getHash(String string) {
        int sum = 0;
        for (char c : string.toCharArray()) {
            sum += c;
            sum *= 17;
            sum %= 256;
        }

        return sum;
    }
}
