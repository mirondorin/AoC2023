package day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/day7/input"))) {
            List<Hand> hands = new ArrayList<>();
            String line;
            long sum = 0;

            int index = 0;
            while ((line = reader.readLine()) != null) {
                String[] input = line.split(" ");
                hands.add(new Hand(input[0], Long.parseLong(input[1])));
                index++;
            }
            Collections.sort(hands, Hand.handComparator);

            for (int multiplier = hands.size() - 1; multiplier >= 0; multiplier--) {
                sum += hands.get(multiplier).bid * (multiplier + 1);
            }

            System.out.println(sum);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
