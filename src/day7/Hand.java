package day7;

import java.util.*;

public class Hand {
    String cards;
    long bid;

    public Hand(String cards, long bid) {
        this.cards = cards;
        this.bid = bid;
    }

    public static Comparator<Hand> handComparator = (h1, h2) -> {
        int h1Value = h1.getHandValueWithJokers();
        int h2Value = h2.getHandValueWithJokers();

        if (h1Value == h2Value) {
            return h1.compareFirstHigherCard(h2.cards);
        }

        return h1Value - h2Value;
    };

    public int compareFirstHigherCard(String otherCards) {
        char[] cardsArr = cards.toCharArray();
        char[] otherCardsArr = otherCards.toCharArray();

        for (int index = 0; index < cardsArr.length; index++) {
            if (cardsArr[index] != otherCardsArr[index]) {
                return getCardValue(cardsArr[index]) - getCardValue(otherCardsArr[index]);
            }
        }

        return 0;
    }

    public int getCardValue(char card) {
        return switch (card) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 1;
            case 'T' -> 10;
            default -> Integer.parseInt(String.valueOf(card));
        };
    }

    public int getHandValueWithJokers() {
        int sum = 0;
        char[] sortedCards = cards.toCharArray();
        Arrays.sort(sortedCards);

        List<Integer> sameFound = new ArrayList<>();
        int sameInARow = 1;
        int jokers = 0;

        for (char c : sortedCards) {
            if (c == 'J') {
                jokers++;
            }
        }

        for (int index = 1; index < sortedCards.length; index++) {

            if (sortedCards[index] == sortedCards[index - 1] && sortedCards[index - 1] != 'J') {
                sameInARow++;
            } else {
                sameFound.add(sameInARow);
                sameInARow = 1;
            }
        }

        sameFound.add(sameInARow);
        Collections.sort(sameFound, Collections.reverseOrder());
        for (int same : sameFound) {
            if (jokers > 0) {
                same += jokers;
                same = Math.min(same, 5);
                sum += same * same;
                jokers = 0;
            } else {
                if (same > 1) {
                    sum += same * same;
                }
            }
        }
//        sum += sameInARow * sameInARow;
        return sum;
    }

    public int getHandValue() {
        int sum = 0;
        char[] sortedCards = cards.toCharArray();
        Arrays.sort(sortedCards);

        int sameInARow = 1;
        for (int index = 1; index < sortedCards.length; index++) {

            if (sortedCards[index] == sortedCards[index - 1]) {
                sameInARow++;
            } else {
                sum += sameInARow * sameInARow;
                sameInARow = 1;
            }
        }

        sum += sameInARow * sameInARow;
        return sum;
    }

}
