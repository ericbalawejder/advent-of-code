## Advent of code 2020
https://adventofcode.com/

#### [Day 1](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/day1)
This year started off with two common [LeetCode](https://leetcode.com/) problems. 
The first part is a common problem named TwoSum. The naive solution is brute force 
to check each value added to the others. It can be done in one pass using a Map 
to store the difference.

The second part is a continuation of the first: ThreeSum. Brute force for all three 
values works, but a better O(n^2) solution is to sort the list of numbers. Starting 
at `index 0`, while using two additional indexes, `index + 1` and `list.length() - 1`, walk 
the two pointers towards each other taking advantage of the sorted order.


#### [Day 2](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/day2)
String processing. I split the data `"4-15 h: gcxfgbpbghdtrkhn"` into a tuple by white
space and followed the validation rules.


#### [Day 3](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/day3)
Day 3 can be summed up with this line of code. `.filter(i -> grid[i][i * x % grid[0].length] == (int) '#')`.
Navigate through a grid with and count the times you hit a tree ('#'). I used the int values
of the Characters in the input Strings because it was easier to stream them into an `int[][]`. 


#### [Day 4](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/day4)
Validating a passport with regex.
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


#### [Day 5](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/day5)
A seat encoding is provided in the form `BFBBBFBLRR` and described as binary space partitioning.
Map the encodings to binary `seatEncoding.replaceAll("[FL]", "0").replaceAll("[BR]", "1")` and
sort them to find the highest seat. The sorted order is contiguous except for one value
which is the missing seat.


#### [Day 6](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/day6)
Count unique answers of a survey with a set. Use set intersection to count unique answers of
a group of people.


#### [Day 14](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/day14)
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


#### [Day 18](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/day18)
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
