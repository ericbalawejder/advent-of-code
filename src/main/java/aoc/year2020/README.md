## Advent of code 2020
https://adventofcode.com/

#### [Day 1](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day1)
This year started off with two common [LeetCode](https://leetcode.com/) problems. 
The first part is a common problem named TwoSum. The naive solution is brute force 
to check each value added to the others. It can be done in one pass using a Map 
to store the difference.

The second part is a continuation of the first: ThreeSum. Brute force for all three 
values works, but a better O(n^2) solution is to sort the list of numbers. Starting 
at `index 0`, while using two additional indexes, `index + 1` and `list.length() - 1`, walk 
the two pointers towards each other taking advantage of the sorted order.


#### [Day 2](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day2)
String processing. I split the data `"4-15 h: gcxfgbpbghdtrkhn"` into a tuple by white space, created 
a Password object to encapsulate the four fields and followed the validation rules.


#### [Day 3](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day3)
Day 3 can be summed up with this line of code. `.filter(i -> grid[i][i * x % grid[0].length] == (int) '#')`.
Navigate through a grid with and count the times you hit a tree `'#'`. I used the int values
of the Characters in the input Strings because it was easier to stream them into an `int[][]`. 


#### [Day 4](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day4)
Validating a passport with regex. This took more time than I wanted.
```
private static final Map<String, String> PASSPORT_POLICY = Map.of(
            "byr", "^(19[2-9][0-9]|200[0-2])$",
            "iyr", "^(201[0-9]|2020)$",
            "eyr", "^(202[0-9]|2030)$",
            "hgt", "^((1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-6])in))$",
            "hcl", "^(#[0-9a-f]{6})$",
            "ecl", "^(amb|blu|brn|gry|grn|hzl|oth)$",
            "pid", "^[0-9]{9}$"
);
```


#### [Day 5](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day5)
A seat encoding is provided in the form `BFBBBFBLRR` and described as binary space partitioning.
Map the encodings to binary `seatEncoding.replaceAll("[FL]", "0").replaceAll("[BR]", "1")` and
sort them to find the highest seat. The sorted order is contiguous except for one value
which is the missing seat.


#### [Day 6](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day6)
Count unique answers of a survey with a set. Use set intersection to count unique answers of
a group of people.


#### [Day 7](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day7)
Use regular expressions to parse bag rules then use recursion to count the nested gold bags.


#### [Day 8](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day8)
Parse machine code `acc`, `jmp` and `nop` and move state machine based on rules.


#### [Day 9](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day9)
Sliding window approach for a given list of numbers. Each number you receive should be the sum 
of any two of the 25 immediately previous numbers.


#### [Day 10](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day10)
In a list of numbers, find the differences between consecutive items, and multiplying how many of those 
differences are 1 with how many of those differences are 3. Then, calculate (brute-force) every single 
combination and check that none have a difference greater than 3. I later created a graph representation, 
using [JGraphT](https://jgrapht.org/), of the adapters and their possible neighbors to optimize the brute 
force approach. The graph works on the test data but uses too much heap space (even with heap arguments `-Xmx2G`) 
to run on the input data.

![graph](/src/main/java/aoc/year2020/day10/graph.png)


#### [Day 11](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day11)
Modify a seating grid based on a set of rules. I modeled the grid with a 2-D array and used an enum
to classify the state of a seat (FLOOR, EMPTY, OCCUPIED).


#### [Day 12](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day12)
Move a ferry based on a set of instructions. Uses the `GridPosition` record to model cartesian coordinates.
Uses an enum `Orientation` (NORTH, EAST, SOUTH, WEST) to classify direction.


#### [Day 13](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day13)
Find the first i such that:
(test data)
i % 7 == 0
i % 13 == 12
i % 59 == 55
i % 31 == 25
i % 19 == 12
using the Chinese Remainder Theorem.


#### [Day 14](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day14)
Bitwise OR and AND.

A bitwise OR operation is often used for a SET operation. Any 1 in a value when OR'd 
against another value will set that bit. A bitwise AND operation is often used for a 
CLEAR operation. Any 0 in a value when AND'd against another value will clear that bit.

The first part was `(value | SET) & CLEAR`
`XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X` can be decomposed down to two operations, an OR 
with 000000000000000000000000000001000000 (the original value where X was replaced with 0) 
to set that one bit that we want to set and an AND with `111111111111111111111111111111111101` 
(the original value where X was replaced with 1). 

Part two was more challenging. I had trouble creating all the bitmask String combinations.
I resorted to looping through each index of the bitmask for bitwise comparison.


#### [Day 15](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day15)
The challenge is sequence building. Take the previous number in the sequence, and see if it has occurred 
before. If it has, next number is how far back the next most recent occurrence was. If not, next number 
is 0. Optimize using a hash map. Find the 2020th and 30,000,000th number. 


#### [Day 16](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day16)
Part 1 is about removing invalid entries from the data, using the heuristic that they contain values that 
don't fit any rule. In the second part of the challenge, we're asked to figure out which rule goes with 
which column by finding an order of rules for which all values in the table are valid.


#### [Day 17](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day17)
¯\_(ツ)_/¯ 3-D modeling using glider pattern from Cantor's game of life.


#### [Day 18](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day18)
Apply Shunting-yard algorithm for processing arithmetic. 

For part 1, the order precedence was equivalent for addition and multiplication.
```
ADD(1, "+"),
MULTIPLY(1, "*");
```
For part 2, the order precedence was addition over multiplication.
```
ADD(1, "+"),
MULTIPLY(0, "*");
```


#### [Day 19](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day19)
Implement an expression grammar.


#### [Day 20](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day20)
¯\_(ツ)_/¯ Assemble the pieces of an image.


#### [Day 21](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day21)
This challenge is about set theory. The allergen is the ingredient at the intersection of
sets consisting of ingredients known to contain it.


#### [Day 22](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day22)
The challenge involves playing a cardOne game that has a simple set of rules. It involves keeping track 
of two stacks of cards. The rules are simple: both players play one cardOne from the top of their deck, highest 
cardOne wins, winning player keeps both cards.

Part two involves a new rule that spans a separate game. The first algorithm is modified to use recursion
to solve the sub-games the are created by the new rule.


#### [Day 23](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day23)
This part of the challenge involves a circular list of numbers, where each round of a game requires doing 
some operation to them. Take the current pointer in the circular list, remove the next three numbers, find 
another number anywhere in the list that is the next value smaller (excluding the numbers removed). 
Then, re-insert the numbers at that point. This day gave me problems because I hate using mutable data 
structures. I used a common implementation of a circular linked list and loathed at the constant mutation 
of Node references and public access of data fields.


#### [Day 24](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day24)


#### [Day 25](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2020/day25)
Brute-forcing a pseudorandom number generator. It is somewhat reminiscent of public key cryptography.
