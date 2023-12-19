package day6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static List<Long> times = new ArrayList<>();
    static List<Long> distances = new ArrayList<>();
    static Pattern numberPattern = Pattern.compile("\\d+");

    public static void main(String[] args) {
        File file = new File("src/day6/input");
//        File file = new File("src/day6/inputpart2");
        int multipliedNumbers = 1;

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            List<String> input = new ArrayList<>();
            while((line = reader.readLine()) != null) {
               input.add(line);
            }
            setTimesAndDistances(input);

            for (int i = 0; i < times.size(); i++) {
                multipliedNumbers *= getWaysToWin(times.get(i), distances.get(i));
            }
            System.out.println(multipliedNumbers);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void setTimesAndDistances(List<String> input) {
        String time = input.get(0).split("Time: ")[1];
        String distance = input.get(1).split("Distance: ")[1];

        Matcher matcher = numberPattern.matcher(time);

        while (matcher.find()) {
            times.add(Long.valueOf(matcher.group()));
        }

        matcher = numberPattern.matcher(distance);

        while (matcher.find()) {
            distances.add(Long.valueOf(matcher.group()));
        }
    }

    public static long getWaysToWin(long time, long distance) {
        int waysToWin = 0;

        for(long charge = 0; charge < time; charge++) {
            if (getTravelDistance(charge, time - charge) > distance) {
                waysToWin++;
            }
        }

        return waysToWin;
    }

    public static long getTravelDistance(long charge, long timeLeft) {
        return charge * timeLeft;
    }
}
