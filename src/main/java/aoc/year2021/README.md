## Advent of code 2021
https://adventofcode.com/

#### [Day 1](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day1)
Count the number of increasing values based on the previous reading. For the second part, count the sum
of increasing values for a contiguous group of three (sliding window) based on the previous group's sum.


#### [Day 2](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day2)
Given a set of rules and ordered instructions, direct a state machine (submarine). This was a good use 
case for the latest Java 17 switch statement and record construct.


#### [Day 3](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day3)


#### [Day 4](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day4)


#### [Day 5](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day5)
Given a list of lines, count the number of times horizontal and vertical lines overlap. For part 2, count
the overlap of horizontal, vertical and diagonal lines with a condition the diagonal lines be 45 degrees.
I created Records `Point(int x, int y)` and `Line(Point a, Point b)` as data holders. Then, generate all
possible points for each line and count their occurrence `Map<Point, Intger> pointCount`. Finally,
counting the points that occur more than once `v -> v > 1` shows the overlapping points on the lines.


#### [Day 6](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day6)
This is an optimization problem. The first part allows you to build a list of elements and return the list
size. Since the list growth is exponential, the second part list will become too large.(Tried passing heap 
arguments -Xmx2G) Instead, use an array where the index is the fish number and the value is the number of 
fish. Then, roll the array with modulus array length.


#### [Day 7](https://github.com/ericbalawejder/advent-of-code/tree/main/src/main/java/aoc/year2021/day7)
