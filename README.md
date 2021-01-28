## Advent of code 2020
https://adventofcode.com/

#### Problem 1
This year started off with two common [LeetCode](https://leetcode.com/) problems. 
TwoSum. The naive solution is brute force to check each value added to each other.
It can be done in one pass using a Map to store the difference.

ThreeSum. Brute force for all three values works, but a better O(n^2) solution is
to sort the list of numbers. Starting at index 0, while using two pointers index + 1 and list.length() - 1, 
walk the two pointers towards each other taking advantage of the sorted order.
