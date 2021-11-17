package aoc.day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CrabCombat {

    public static void main(String[] args) throws IOException {
        final List<String> cards = readFile("src/main/java/aoc/day22/deck.txt");
        final Map<String, List<Integer>> hands = dealHands(cards);
        System.out.println(playCombat(hands));
    }

    static int playCombat(Map<String, List<Integer>> hands) {
        final Map<String, List<Integer>> handByPlayer = new HashMap<>(hands);
        while (!handByPlayer.get("Player1").isEmpty() && !handByPlayer.get("Player2").isEmpty()) {
            final Integer card1 = handByPlayer.get("Player1").get(0);
            final Integer card2 = handByPlayer.get("Player2").get(0);
            if (card1 > card2) {
                handByPlayer.put("Player1", addCards(handByPlayer.get("Player1"), List.of(card1, card2)));
            } else {
                handByPlayer.put("Player2", addCards(handByPlayer.get("Player2"), List.of(card2, card1)));
            }
            handByPlayer.put("Player1", subtractCard(handByPlayer.get("Player1")));
            handByPlayer.put("Player2", subtractCard(handByPlayer.get("Player2")));
        }
        return handByPlayer.get("Player1").isEmpty() ?
                calculateScore(handByPlayer.get("Player2")) :
                calculateScore(handByPlayer.get("Player1"));
    }

    private static int calculateScore(List<Integer> deck) {
        return IntStream.rangeClosed(1, deck.size())
                .map(i -> deck.get(deck.size() - i) * i)
                .reduce(0, Math::addExact);
    }

    private static List<Integer> addCards(List<Integer> hand, List<Integer> cards) {
        return Stream.concat(hand.stream(), cards.stream())
                .collect(Collectors.toUnmodifiableList());
    }

    private static List<Integer> subtractCard(List<Integer> hand) {
        return hand.stream()
                .skip(1)
                .collect(Collectors.toUnmodifiableList());
    }

    private static Map<String, List<Integer>> dealHands(List<String> deck) {
        return deck.stream()
                .map(s -> s.split(": "))
                .collect(Collectors.toMap(a -> a[0], a -> parseCards(a[1])));
    }

    private static List<Integer> parseCards(String cards) {
        return Arrays.stream(cards.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toUnmodifiableList());
    }

    private static List<String> readFile(String path) throws IOException {
        final String data = Files.readString(Path.of(path));
        return Arrays.stream(data.split("\n\n"))
                .map(s -> s.replaceAll(" ", ""))
                .map(s -> s.replace("\n", " "))
                .collect(Collectors.toUnmodifiableList());
    }

}
