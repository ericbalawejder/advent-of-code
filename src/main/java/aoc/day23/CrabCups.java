package aoc.day23;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CrabCups {

    public static void main(String[] args) {
        final List<Integer> cupLabels = labelCups("467528193");
        final CircularLinkedList cups = play(cupLabels, 100);
        System.out.println(findLabels(cups, 1));

        final List<Integer> cupLabels2 = Stream.concat(
                        cupLabels.stream(),
                        IntStream.rangeClosed(10, 1_000_000).boxed())
                .toList();

        final CircularLinkedList cups2 = play(cupLabels2, 10_000_000);
        System.out.println(findProductOfLabels(cups2, 1));
    }

    static long findProductOfLabels(CircularLinkedList cups, int cup) {
        cups.setCurrent(cup);
        final List<Integer> nextTwo = cups.getLabels(2);
        return (long) nextTwo.get(0) * nextTwo.get(1);
    }

    static String findLabels(CircularLinkedList cups, int cup) {
        cups.setCurrent(cup);
        return cups.getLabels(8)
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    static CircularLinkedList play(List<Integer> gameCups, int moves) {
        final CircularLinkedList cups = new CircularLinkedList(gameCups);

        for (int i = 0; i < moves; i++) {
            int current = cups.getCurrentValue();
            int j;
            CircularLinkedList.Node next = cups.getCurrentNode().next;
            CircularLinkedList.Node last = next.next.next;
            for (j = current - 2 + cups.size(); j > 0; j--) {
                int n = j % cups.size() + 1;
                if (next.value != n && next.next.value != n && last.value != n) {
                    break;
                }
            }
            int destination = j % cups.size() + 1;
            cups.insertAfter(next, last, destination);
            cups.next();
        }
        return cups;
    }

    private static List<Integer> labelCups(String labels) {
        return Arrays.stream(labels.split(""))
                .map(Integer::parseInt)
                .toList();
    }

}
