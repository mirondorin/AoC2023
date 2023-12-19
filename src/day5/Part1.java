package day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    static Pattern numberPattern = Pattern.compile("(\\d+)");
    static Pattern rangePattern = Pattern.compile("(\\d+) (\\d+) (\\d+)");
    static List<Long> seeds = new ArrayList<>();
    static List<NumberConverter> seedToSoil = new ArrayList<>();
    static List<NumberConverter> soilToFertilizer = new ArrayList<>();
    static List<NumberConverter> fertilizerToWater = new ArrayList<>();
    static List<NumberConverter> waterToLight = new ArrayList<>();
    static List<NumberConverter> lightToTemperature = new ArrayList<>();
    static List<NumberConverter> temperatureToHumidity = new ArrayList<>();
    static List<NumberConverter> humidityToLocation = new ArrayList<>();
    static List<List<NumberConverter>> mappers = Arrays.asList(seedToSoil, soilToFertilizer, fertilizerToWater,
            waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation);

    public static void main(String[] args) {
        File file = new File("src/day5/input");
        List<String> input = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                input.add(line);
            }

            setSeedNumbers(input.get(0));

            int mapperIndex = 0;
            for (int i = 3; i < input.size(); i++) {
                line = input.get(i);

                if (line.isEmpty()) {
                    mapperIndex++;
                } else {
                    List<NumberConverter> mapper = mappers.get(mapperIndex);
                    Matcher matcher = rangePattern.matcher(line);

                    if (matcher.find()) {
                        mapper.add(new NumberConverter(Long.parseLong(matcher.group(1)),
                                Long.parseLong(matcher.group(2)), Long.parseLong(matcher.group(3))));
                    }
                }
            }

            Long min = null;
            Long foundSeed = null;
            for (Long seed : seeds) {
                Long value = findSeedLocation(seed);
                if (min == null || value < min) {
                    min = findSeedLocation(seed);
                    foundSeed = seed;
                }
            }
            System.out.println(min);
            System.out.println(foundSeed);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static long findSeedLocation(long seed) {
        long value = seed;

        for (int index = 0; index < mappers.size(); index++) {
            List<NumberConverter> mapper = mappers.get(index);
            for (NumberConverter converter : mapper) {
                if (value >= converter.sourceRangeStart
                        && value < converter.sourceRangeStart + converter.rangeLength) {
                    long diff = value - converter.sourceRangeStart;
                    value = converter.destinationRangeStart + diff;
                    break;
                }
            }
        }

        return value;
    }

    public static void setSeedNumbers(String text) {
        text = text.split("seeds:")[1];
        Matcher matcher = numberPattern.matcher(text);

        while (matcher.find()) {
            seeds.add(Long.valueOf(matcher.group()));
        }
    }
}
