## Advent of code 2022
https://adventofcode.com/

#### [Day 1](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2022/day1)
Count the number of calories recorded by each elf. This data is provided in groups of a file separated by 
two new lines. Sum each group of numbers and place them in a `TreeSet`. This allows us to quickly grab the
largest number. For part two, we need to add the top three largest calorie counts. I had to copy the `TreeSet`
to a list since a set does not allow retrieval by index.

#### [Day 2](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2022/day2)
Play many games of rock, paper, scissors. Decrypt the hands to play based on a provided strategy guide.
Uses strict types with `Enums` and `Records` and scores the game with a `Map` of their respective outcomes.
