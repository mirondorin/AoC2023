package day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static int sum = 0;
    static List<String> cards = new ArrayList<>();
    static List<Integer> cardMultiplier = new ArrayList<>();

    public static void main(String[] args) {
        File file = new File("src/day4/input");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

//            while ((line = reader.readLine()) != null) {
//                solveFirstPart(line);
//            }
//            System.out.println(sum);

            while ((line = reader.readLine()) != null) {
                cards.add(line);
                cardMultiplier.add(1);
            }


            for (int idx = 0; idx < cards.size(); idx++) {
                String card = cards.get(idx);
                solveSecondPart(card, idx);
            }
            System.out.println(sum);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setScratchCopies(List<String> winningCards, List<String> scratchCards, int idx) {
        int won = 0;
        for (String winningCard : winningCards) {
            if (scratchCards.contains(winningCard)) {
                won++;
            }
        }

        for (int ii = 0; ii < won; ii++) {
            int offset = idx + ii + 1;
            int currentMultiplier = cardMultiplier.get(offset);
            cardMultiplier.set(offset, currentMultiplier + cardMultiplier.get(idx));
        }

        sum += cardMultiplier.get(idx);
    }

    public static void solveSecondPart(String line, int idx) {
        List<String> winningCards = new ArrayList<>();
        List<String> scratchingCards = new ArrayList<>();
        setWinningAndScratchCards(line, winningCards, scratchingCards);
        setScratchCopies(winningCards, scratchingCards, idx);
    }

    public static void solveFirstPart(String line) {
        List<String> winningCards = new ArrayList<>();
        List<String> scratchingCards = new ArrayList<>();
        setWinningAndScratchCards(line, winningCards, scratchingCards);
        sum += getWinningPoints(winningCards, scratchingCards);
    }

    public static int getWinningPoints(List<String> winningCards, List<String> scratchCards) {
        int sum = 0;
        for (String winningCard : winningCards) {
            if (scratchCards.contains(winningCard)) {
                sum = sum < 1 ? 1 : sum << 1;
            }
        }
        return sum;
    }

    public static void setWinningAndScratchCards(String text, List<String> winningCards, List<String> scratchCards) {
        String[] cards = text.split(":")[1]
                .split("\\|");
        String winningCardsString = cards[0];
        String scratchCardsString = cards[1];

        Pattern pattern = Pattern.compile("(\\d*)");
        Matcher matcher = pattern.matcher(winningCardsString);

        while (matcher.find()) {
            if (matcher.group() != "") {
                winningCards.add(matcher.group());
            }
        }

        matcher = pattern.matcher(scratchCardsString);

        while (matcher.find()) {
            if (matcher.group() != "") {
                scratchCards.add(matcher.group());
            }
        }
    }
}

