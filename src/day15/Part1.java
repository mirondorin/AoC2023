package day15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Part1 {

    public static void main(String[] args) {
        File file = new File("src/day15/input");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            long sum = 0;


            while ((line = reader.readLine()) != null) {
                String[] strings = line.split(",");
                for (String string : strings) {
                    sum += getHash(string);
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
