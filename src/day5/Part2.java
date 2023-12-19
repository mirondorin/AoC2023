package day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {
    static Pattern destinationSourceRangePattern = Pattern.compile("(\\d+) (\\d+) (\\d+)");
    static Pattern seedRangePattern = Pattern.compile("(\\d+) (\\d+)");
    static List<NumberConverter> seeds = new ArrayList<>();
    static List<NumberConverter> seedToSoil = new ArrayList<>();
    static List<NumberConverter> soilToFertilizer = new ArrayList<>();
    static List<NumberConverter> fertilizerToWater = new ArrayList<>();
    static List<NumberConverter> waterToLight = new ArrayList<>();
    static List<NumberConverter> lightToTemperature = new ArrayList<>();
    static List<NumberConverter> temperatureToHumidity = new ArrayList<>();
    static List<NumberConverter> humidityToLocation = new ArrayList<>();
    static List<List<NumberConverter>> mappers = Arrays.asList(seeds, seedToSoil, soilToFertilizer, fertilizerToWater,
            waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation);

    public static void main(String[] args) {
        File file = new File("src/day5/input");
        List<String> input = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                input.add(line);
            }

            setMappers(input);

            Long min = null;
            Long foundSeed = null;

            long location = 0;
            while(min == null) {
                min = findMinSeedLocation(location);
                location++;
            }
            System.out.println(min);
            System.out.println(foundSeed);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void setMappers(List<String> input) {
        String line;

        line = input.get(0);
        Matcher matcher = seedRangePattern.matcher(line);
        while (matcher.find()) {
            seeds.add(new NumberConverter(Long.parseLong(matcher.group(1)),
                    Long.parseLong(matcher.group(1)),Long.parseLong(matcher.group(2))));
        }

        int mapperIndex = 1;
        for (int i = 3; i < input.size(); i++) {
            line = input.get(i);

            if (line.isEmpty()) {
                mapperIndex++;
            } else {
                List<NumberConverter> mapper = mappers.get(mapperIndex);
                matcher = destinationSourceRangePattern.matcher(line);

                if (matcher.find()) {
                    mapper.add(new NumberConverter(Long.parseLong(matcher.group(1)),
                            Long.parseLong(matcher.group(2)), Long.parseLong(matcher.group(3))));
                }
            }
        }
    }

    public static Long findMinSeedLocation(long location) {
        long value = location;
        boolean found = false;

        for(int index = mappers.size() - 1; index >= 0; index --) {
            List<NumberConverter> mapper = mappers.get(index);
            for (NumberConverter converter : mapper) {
                if (value >= converter.destinationRangeStart
                        && value < converter.destinationRangeStart + converter.rangeLength) {
                    long diff = value - converter.destinationRangeStart;
                    value = converter.sourceRangeStart + diff;
                    if (index == 0) {
                        found = true;
                    }
                    break;
                }
            }
        }

        return found ? location : null;
    }
}
