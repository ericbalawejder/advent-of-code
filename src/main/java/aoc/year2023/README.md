## Advent of code 2023
https://adventofcode.com/

#### [Day 1](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2023/day1)
Provided a `String` with no white space, extract matching values and only use the first and last match. This
seemed like a problem for a `Trie` but I used regex matching without regard for performance.

#### [Day 2](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2023/day2)
90% parsing, 10% solving. Parse `String` input of notes for each game played and determine if the game is possible
or not given a set amount of cubes. Both parts are simple counting and summing of `Integers`.

#### [Day 4](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2023/day4)
Lots of parsing like day 2. Use `Set` intersection to find a winning scratchcard. Score them by their respective rules 
for part 1 and 2.

#### [Day 5](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2023/day5)
This involves a lot of `String` parsing. Since the garden almanac is an ordered structure, I wanted to leverage the type
system as much as possible. An `Almanac` has a `List<Integer>` seeds and a `List<Garden>`. A `Garden` contains a 
name and a `List<Range>` ranges of `Integer` mappings defined as 
`Range(int destinationStart, int sourceStart, int rangeLength)`. Once the data is organized, the algorithm is 
straight forward.
