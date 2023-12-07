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
