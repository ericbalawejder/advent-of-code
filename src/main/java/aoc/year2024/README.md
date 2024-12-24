## Advent of code 2024

https://adventofcode.com/

#### [Day 1](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2024/day1)

Measure the [Euclidean distance](https://en.wikipedia.org/wiki/Euclidean_distance) of a pair of `Integer`s and
reduce to a sum and sum of products.

#### [Day 2](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2024/day2)

Check the validity of `List<Integer>` based on the criteria of being
[strictly increasing](https://en.wikipedia.org/wiki/Monotonic_function#Monotonicity_in_calculus_and_analysis),
[strictly decreasing](https://en.wikipedia.org/wiki/Monotonic_function#Monotonicity_in_calculus_and_analysis) and
having a [Euclidean distance](https://en.wikipedia.org/wiki/Euclidean_distance) of `[1, 3]`.

#### [Day 3](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2024/day3)

Some [regex101](https://regex101.com/) practice. Grab matching patterns and process the instructions. Part two
added complexity with an on/off switch.

#### [Day 4](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2024/day4)

Play word search. I find 2D array problems more manageable when making a `Coordinate(x, y)` type and placing them all
in a `Map`. A `LinkedHashMap` is not necessary and is only used to draw a nice picture of the grid. A
`Path(List<Coordinate>)` is then used to define the paths to check for the necessary words assembled from `Character`s.
