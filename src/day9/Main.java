package day9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static Pattern numberPattern = Pattern.compile("(-?\\d+)");

    public static void main(String[] args) {
        long sum = 0;
        File file = new File("src/day9/input");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                List<Long> initialList = toLongList(line, true);
                List<List<Long>> histories = new ArrayList<>();
                histories.add(initialList);
                List<Long> differences = getDifferencesArray(initialList);

                while (!differences.stream().allMatch(n -> n == 0)) {
                    histories.add(differences);
                    differences = getDifferencesArray(differences);
                }

                for (int index = histories.size() - 2; index >= 0; index--) {
                    List<Long> currentHistory = histories.get(index);
                    List<Long> oldHistory = histories.get(index + 1);
                    long left = currentHistory.get(currentHistory.size() - 1);
                    long bottom = oldHistory.get(oldHistory.size() - 1);

                    currentHistory.add(left + bottom);
                    if (index == 0) {
//                        System.out.println(left + bottom);
                        sum += left + bottom;
                    }
                }
            }

            System.out.println(sum);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static List<Long> toLongList(String line, boolean reversed) {
        List<Long> numbers = new ArrayList<>();
        Matcher matcher = numberPattern.matcher(line);

        while (matcher.find()) {
            numbers.add(Long.valueOf(matcher.group()));
        }

        return reversed ? numbers.reversed() : numbers;
    }

    public static List<Long> getDifferencesArray(List<Long> numbers) {
        List<Long> differences = new ArrayList<>();
        for(int index = 1; index < numbers.size(); index++) {
            differences.add(numbers.get(index) - numbers.get(index - 1));
        }

        return differences;
    }
}
