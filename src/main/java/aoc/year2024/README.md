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

#### [Day 5](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2024/day5)

Provided a set of ordering rules, determine if a list of numbers is in the correct order or not. For part two, if the 
numbers are not in the correct order, reorder them to the correct order. I first tried to find a natural ordering based 
on the rules but the rules produce a directed graph with cycles. I created a type `Rule(int x, int y)` for each rule and
then used a `Map<K, Set<V>` for each rule x value and the set of y values. The rules produce a set of all permutations 
for the list of page numbers. The natural ordering, used to sort the numbers, is based on the occurrence of each x value
in the permutations of the list of numbers. The most frequent occurring x values are the first values in the list and 
the least occurring x values are the second to last in the list, the last value being its y counterpart.
```
For page numbers: 75,47,61,53,29

The numbers produce the set of permutations of size two: 
[75 | 47], [75 | 61], [75 | 53], [75 | 29]
[47 | 61], [47 | 53], [47 | 29]
[61 | 53], [61 | 29]
[53 | 29]

Which match the rule subset and thus are in the correct order.

75|47
75|61
75|53
75|29

47|61
47|53
47|29

61|53
61|29

53|29
```
If a rule for a permutation does not exist, flip the permutation, then reorder the set of permutations and mutate the
page numbers to match the rule set.
