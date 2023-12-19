package day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        File file = new File("src/day1/input");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int sum = 0;

            while ((line = reader.readLine()) != null) {
                sum += extractDigitsWithText(line);
            }

            System.out.println(sum);
        } catch (Exception ex) {
            System.out.println("It brokey!");
        }
    }

    public static int extractDigitsWithText(String text) {
        List<String> digits = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d)|(?=(one))|(?=(two))|(?=(three))|(?=(four))|(?=(five))|(?=(six))|(?=(seven))|(?=(eight))|(?=(nine))", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        Map<String, String> stringDigits = new HashMap<>();
        stringDigits.put("one", "1");
        stringDigits.put("two", "2");
        stringDigits.put("three", "3");
        stringDigits.put("four", "4");
        stringDigits.put("five", "5");
        stringDigits.put("six", "6");
        stringDigits.put("seven", "7");
        stringDigits.put("eight", "8");
        stringDigits.put("nine", "9");
        String digit = null;

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null && !matcher.group(i).isEmpty()) {
                    digit = matcher.group(i);
                    break;
                }
            }

            digits.add(stringDigits.get(digit) == null ? matcher.group() : stringDigits.get(digit));
        }

        return digits.size() > 1 ? Integer.parseInt(digits.get(0) + digits.get(digits.size() - 1)) :
                Integer.parseInt(digits.get(0) + digits.get(0));
    }
}