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

    int step() {
        Set<Coordinate> flashers = new HashSet<>();
        Queue<Coordinate> toIncrease = new LinkedList<>(keySet());
        while (!toIncrease.isEmpty()) {
            Coordinate top = toIncrease.poll();
            if (!flashers.contains(top)) {
                var val = compute(top, (k, v) -> v == null ? null : v + 1);
                if (val > 9) {
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

    boolean isSyncron() {
        return values().stream().allMatch(i -> i == 0);
    }

}
