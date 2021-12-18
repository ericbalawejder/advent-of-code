package aoc.year2021.day11;

import java.io.Serial;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class OctopusMap extends HashMap<Coordinate, Integer> {

    @Serial
    private static final long serialVersionUID = 1L;

    public int step() {
        final Set<Coordinate> flashers = new HashSet<>();
        final Queue<Coordinate> toIncrease = new LinkedList<>(keySet());
        while (!toIncrease.isEmpty()) {
            final Coordinate top = toIncrease.poll();
            if (!flashers.contains(top)) {
                final Integer value = compute(top, (k, v) -> v == null ? null : v + 1);
                if (value > 9) {
                    flashers.add(top);
                    top.adjacents()
                            .stream()
                            .filter(c -> containsKey(c) && !flashers.contains(c))
                            .forEach(toIncrease::add);
                }
            }
        }
        flashers.forEach(c -> put(c, 0));
        return flashers.size();
    }

    public boolean isSynchronized() {
        return values().stream().allMatch(i -> i == 0);
    }

}
