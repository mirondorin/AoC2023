package day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    static Map<String, Node> nodes = new HashMap<>();
    static List<Node> startNodes = new ArrayList<>();
    static List<Long> stepsTaken = new ArrayList<>();

    public static long gcd(long num1, long num2)
    {
        if (num2 == 0)
            return num1;
        return gcd(num2, num1 % num2);
    }

    public static long lcm_of_array(List<Long> arr)
    {
        long lcm = arr.get(0);
        for (int i = 1; i < arr.size(); i++) {
            long num1 = lcm;
            long num2 = arr.get(i);
            long gcd_val = gcd(num1, num2);
            lcm = (lcm * arr.get(i)) / gcd_val;
        }
        return lcm;
    }

    public static void main(String[] args) {

        File file = new File("src/day8/input");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
                if (nodeTag.charAt(2) == 'A') {
                    startNodes.add(node);
                }
            }

            for (Node node : startNodes) {
                long steps = 0;
                while (node.tag.charAt(2) != 'Z') {
                    for (char dir : directions.toCharArray()) {
                        if (dir == 'L') {
                            node = nodes.get(node.leftTag);
                        } else {
                            node = nodes.get(node.rightTag);
                        }

                        steps++;

                        if (node.tag.charAt(2) == 'Z') {
                            stepsTaken.add(steps);
                            break;
                        }
                    }
                }
            }

            System.out.println(lcm_of_array(stepsTaken));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
