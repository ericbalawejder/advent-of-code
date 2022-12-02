## Advent of code 2022
https://adventofcode.com/

#### [Day 1](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2022/day1)
Count the number of calories recorded by each elf. This data is provided in groups of a file separated by 
two new lines. Sum each group of numbers and place them in a `TreeSet`. This allows us to quickly grab the
largest number. For part two, we need to add the top three largest calorie counts. I had to copy the `TreeSet`
to a list since a set does not allow retrieval by index.
