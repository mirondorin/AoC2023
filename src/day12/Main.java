package day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File file = new File("src/day12/input");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                String spring = tokens[0];
                List<Integer> sequence = sequenceFromString(tokens[1]);

                sequenceGoesBrr(spring, sequence);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void sequenceGoesBrr(String spring, List<Integer> sequence) {
        if (sequence.isEmpty()) {
            return;
        }

        if (spring.charAt(0) == '.') {
            sequenceGoesBrr(spring.substring(1), sequence);
        }
    }

    public static List<Integer> sequenceFromString(String sequence) {
        List<Integer> numbers = new ArrayList<>();
        String[] sequenceNumbers = sequence.split(",");

        for (String number : sequenceNumbers) {
            numbers.add(Integer.valueOf(number));
        }

        return numbers;
    }
}
