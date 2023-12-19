package day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Part1 {

    static Map<String, Node> nodes = new HashMap<>();

    public static void main(String[] args) {

        File file = new File("src/day8/input");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            long steps = 0;
            String line;
            String directions;

            directions = reader.readLine();

            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] input = line.split(" = ");
                String nodeTag = input[0];
                String neighbours = input[1];
                String leftTag = neighbours.substring(1, 4);
                String rightTag = neighbours.substring(6, 9);

                Node node = new Node(nodeTag, leftTag, rightTag);
                nodes.put(nodeTag, node);

            }
            Node start = nodes.get("AAA");

            while (!start.tag.equals("ZZZ")) {
                for (char dir : directions.toCharArray()) {
                    if (dir == 'L') {
                        start = nodes.get(start.leftTag);
                    } else {
                        start = nodes.get(start.rightTag);
                    }

                    steps++;

                    if (start.tag.equals("ZZZ")) {
                        break;
                    }
                }
            }

            System.out.println(steps);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
