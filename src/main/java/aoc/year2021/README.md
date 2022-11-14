## Advent of code 2021
https://adventofcode.com/

#### [Day 1](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day1)
Count the number of increasing values based on the previous reading. For the second part, count the sum
of increasing values for a contiguous group of three (sliding window) based on the previous group's sum.


#### [Day 2](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day2)
Given a set of rules and ordered instructions, direct a state machine (submarine). This was a good use 
case for the latest Java 17 switch statement and record construct.


#### [Day 3](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day3)
Part one appeared to be bit manipulation of a list of binary numbers. I converted the binary string to 
an integer and used bit shifting to find values based on a set of rules. For part two, I had to change 
my approach to using string manipulation of the binary numbers because the rules involved comparing
the next bit of the string until there was only one number left in the collection.


#### [Day 4](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day4)
Play a game of Bingo with a squid. Most of the logic revolves around the GameCard class which simulates
a mutable grid of numbers. Each time a number is called, cross off the number called and check for a winner.


#### [Day 5](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day5)
Given a list of lines, count the number of times horizontal and vertical lines overlap. For part 2, count
the overlap of horizontal, vertical and diagonal lines with a condition the diagonal lines be 45 degrees.
I created Records `Point(int x, int y)` and `Line(Point a, Point b)` as data holders. Then, generate all
possible points for each line and count their occurrence `Map<Point, Intger> pointCount`. Finally,
counting the points that occur more than once `v -> v > 1` shows the overlapping points on the lines.


#### [Day 6](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day6)
This is an optimization problem. The first part allows you to build a list of elements and return the list
size. Since the list growth is exponential, the second part list will become too large. I tried passing heap 
arguments `-Xmx2G` but it wasn't enough. Instead, use an array where the index is the fish number and the 
value is the number of fish. Then, roll the array with modulus array length.


#### [Day 7](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day7)
This is an optimization problem. Sort the list of numbers and find the median. I initially failed because
I was using the mode as the optimal point instead. Since the median is a float, find both int values and
calculate the sum based on a distance function the crabs have to move. The distance function for part 1 is
linear. Use the min of the two. For part two, the distance function is sigmaN and requires using the means
as the optimal point. Since the list has an even size, there are two median values. Apply the distance
function to each value and take the min.


#### [Day 8](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day8)
Solving for set constraints. A signal pattern is a scrambled string. The scrambled string represents one of 
ten numbers. Count the number of unique signal patterns by their length. A pattern of length two represents 
the number 1. A pattern of length three represents a 7...length four is 4 and length seven is 8.
```
  0:      1:      2:      3:      4:
 aaaa    ....    aaaa    aaaa    ....
b    c  .    c  .    c  .    c  b    c
b    c  .    c  .    c  .    c  b    c
 ....    ....    dddd    dddd    dddd
e    f  .    f  e    .  .    f  .    f
e    f  .    f  e    .  .    f  .    f
 gggg    ....    gggg    gggg    ....

  5:      6:      7:      8:      9:
 aaaa    aaaa    aaaa    aaaa    aaaa
b    .  b    .  .    c  b    c  b    c
b    .  b    .  .    c  b    c  b    c
 dddd    dddd    ....    dddd    dddd
.    f  e    f  .    f  e    f  .    f
.    f  e    f  .    f  e    f  .    f
 gggg    gggg    ....    gggg    gggg
```
Once we have four of the ten numbers, we need to find the remaining six. There are three signals of 
length five and three signals of length six. Based on the segment layout above, use deduction with 
set operations to determine the unknown signals. Once all ten signal patterns are known, decode
a different set of signals (referred to as output values) and sum their numeric values.


#### [Day 9](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day9)
Traverse a grid and find values based on a set of rules. I used a `record` `Coordinate(int x, int y)` 
as a data holder for the point. The coordinate `(x, y)` of each point in the grid is used as the key and 
the number at each point is the value in a hashmap. For part one, find all the low points in the grid and 
take their sum. For part two, find all the basins in the grid and take the product of the three largest.


#### [Day 10](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day10)
Use a stack to check for matching delimiters `[({(<(())[]>[[{[]{<()<>>`. If a delimiter doesn't match, 
return it and compute a score based on the value assigned to the delimiter. Some lines are incomplete
because they don't contain enough right sided delimiters. For part two, find the incomplete lines and 
complete the delimiter sequence. Compute a score based on the value of each delimiter required.


#### [Day 11](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day11)
Apply rules to a 2-D array of values. Similar to a bingo card from day 4. I used the same `Coordinate`
`record` from day 9 to model the grid of octopus energy levels. For part 1, apply 100 transformations
of the grid and count how many times a 9 flips over to zero, called a flash. For part 2, find on what
number transformation the grid becomes synchronized, defined as all zeros.


#### [Day 12](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day12)
First graph problem on the calendar. For the cave mappings, `end` can't be a key and `start` can't be a value 
in the hashmap representing the cave paths. This logic is seen in the `readCaves()` method. Then, recursively
traverse the cave paths counting the number of paths from `start` to `end`, visiting the small caves only once.
For part 2, repeat with allowing small caves to be visited at most twice. This is achieved with the `boolean`
parameter `repeat` passed to the `countPaths()` method.


#### [Day 13](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day13)
Create another grid using the record `Coordinate` from previous problems. Follow the rules for folding the 
transparent paper, or grid, at a horizontal or vertical line. This involves mapping the points on the paper 
beyond the fold to under the fold. For part one, fold the paper once and count the points. For part 2, fold 
the paper by the instructions in the data file and display the finished paper (grid) to reveal the code in 
text.


#### [Day 14](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day14)
This was an optimization problem similar to day 6. The polymer string grows exponentially and uses too much
memory to represent as a string. I tried to increase the heap size for part 2 after implementing part 1 as
a string and `Xmx2G` wasn't enough. Instead, use a hashmap to count each pair from the polymer created after 
applying the rules. Add the first and last character of the polymer string to the map because they only get 
counted once. Count all the characters from the keys of the map (pairs `NN`, `HB`, etc) and divide by two to 
get their actual count. Sort the characters by value (`Long` count) and subtract the least (first) from the 
largest (last).


#### [Day 15](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day15)
Create another grid using the record `Coordinate` from previous problems. Traverse the grid from top left to 
bottom right using Dijkstra shortest path. I used a `Path` type to hold the current `Coordinate` as a well as 
the sum of the cost/risk levels. Part 2 involves expanding the grid to 5 times the size and increasing each 
risk by 1 and rolling a 9 value over to a 1. Then, apply the same method on the larger grid to escape the cave.
