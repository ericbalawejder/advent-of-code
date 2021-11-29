package aoc.year2020.day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CrabCombat {

    public static void main(String[] args) throws IOException {
        final List<String> cards = readFile("src/main/java/aoc/year2020/day22/deck.txt");
        final Map<String, List<Integer>> hands = dealHands(cards);

        final List<List<Integer>> completedGame = playCombat(hands);
        System.out.println(score(completedGame));

        final List<List<Integer>> completedGames = playRecursiveCombat(hands);
        System.out.println(score(completedGames));
    }

    static int score(List<List<Integer>> decks) {
        return decks.get(0).isEmpty() ?
                calculateScore(decks.get(1)) : calculateScore(decks.get(0));
    }

    static List<List<Integer>> playRecursiveCombat(Map<String, List<Integer>> handsByPlayer) {
        final Map<String, List<Integer>> hands = new HashMap<>(handsByPlayer);
        final Set<Integer> decks = new HashSet<>();
        while (!hands.get("Player1").isEmpty() && !hands.get("Player2").isEmpty()) {
            final int deck = hands.get("Player1").hashCode() * 31 + hands.get("Player2").hashCode();
            if (!decks.add(deck)) {
                return List.of(hands.get("Player1"), Collections.emptyList());
            }
            final Integer card1 = hands.get("Player1").get(0);
            final Integer card2 = hands.get("Player2").get(0);
            hands.put("Player1", subtractCard(hands.get("Player1")));
            hands.put("Player2", subtractCard(hands.get("Player2")));
            if (card1 <= hands.get("Player1").size() && card2 <= hands.get("Player2").size()) {

                final Map<String, List<Integer>> recursiveGameHands = Map.of(
                        "Player1", hands.get("Player1").subList(0, card1),
                        "Player2", hands.get("Player2").subList(0, card2));

                final List<List<Integer>> resultDecks = playRecursiveCombat(recursiveGameHands);

                if (!resultDecks.get(0).isEmpty()) {
                    hands.put("Player1", addCards(hands.get("Player1"), List.of(card1, card2)));
                } else {
                    hands.put("Player2", addCards(hands.get("Player2"), List.of(card2, card1)));
                }
            } else {
                if (card1 > card2) {
                    hands.put("Player1", addCards(hands.get("Player1"), List.of(card1, card2)));
                } else {
                    hands.put("Player2", addCards(hands.get("Player2"), List.of(card2, card1)));
                }
            }
        }
        return List.of(hands.get("Player1"), hands.get("Player2"));
    }

    static List<List<Integer>> playCombat(Map<String, List<Integer>> handsByPlayer) {
        final Map<String, List<Integer>> hands = new HashMap<>(handsByPlayer);
        while (!hands.get("Player1").isEmpty() && !hands.get("Player2").isEmpty()) {
            final Integer card1 = hands.get("Player1").get(0);
            final Integer card2 = hands.get("Player2").get(0);
            hands.put("Player1", subtractCard(hands.get("Player1")));
            hands.put("Player2", subtractCard(hands.get("Player2")));
            if (card1 > card2) {
                hands.put("Player1", addCards(hands.get("Player1"), List.of(card1, card2)));
            } else {
                hands.put("Player2", addCards(hands.get("Player2"), List.of(card2, card1)));
            }
        }
        return List.of(hands.get("Player1"), hands.get("Player2"));
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
