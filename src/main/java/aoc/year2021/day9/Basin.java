package aoc.year2021.day9;

import java.io.Serial;
import java.util.Comparator;
import java.util.HashSet;

public class Basin extends HashSet<Coordinate> implements Comparable<Basin> {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Comparator<Basin> COMPARATOR =
            Comparator.comparing(Basin::size, Comparator.reverseOrder());

    @Override
    public int compareTo(Basin o) {
        return COMPARATOR.compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Basin b) {
            return b.size() == size() && b.containsAll(this);
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return stream().mapToInt(Coordinate::hashCode).sum();
    }

}
