package aoc.day22;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {

    /*
    static int playRecursiveCombat(Map<String, List<Integer>> hands) {
        final Set<Integer> previousDecks = new HashSet<>();
        final Map<String, List<Integer>> playerHands = new HashMap<>(hands);
        while (!playerHands.get("Player 1").isEmpty() && !playerHands.get("Player 2").isEmpty()) {
            if(!previousDecks.add(
                    playerHands.get("Player 1").hashCode() * 31 + playerHands.get("Player 2").hashCode())) {
                //return List.of(playerHands.get("Player 1"), new LinkedList<>());
            }
            final Integer card1 = playerHands.get("Player 1").get(0);
            final Integer card2 = playerHands.get("Player 2").get(0);
            if (card1 <= playerHands.get("Player 1").size() - 1 &&
                    card2 <= playerHands.get("Player 2").size() - 1) {
                if (card1 > card2) {
                    playerHands.put("Player 1", addCards(playerHands.get("Player 1"), List.of(card1, card2)));
                } else {
                    playerHands.put("Player 2", addCards(playerHands.get("Player 2"), List.of(card2, card1)));
                }
                playerHands.put("Player 1", subtractCard(playerHands.get("Player 1")));
                playerHands.put("Player 2", subtractCard(playerHands.get("Player 2")));
            } else {
                playRecursiveCombat(hands);
            }
        }
        return playerHands.get("Player 1").isEmpty() ? calculateScore(playerHands.get("Player 2")) :
                calculateScore(playerHands.get("Player 1"));
    }

     */
}
