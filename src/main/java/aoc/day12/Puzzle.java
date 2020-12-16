package aoc.day12;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Puzzle {

    public static void main(String[] args) throws IOException {
        List<String> instructions = Files.lines(Paths.get("src/main/java/aoc/day12/navigation.txt"))
                .collect(toList());

        int[] northEast = {0, 0};
        int[] wpNorthEast = {1, 10};

//		List<int[]> vectors= List.of(new int[]{0, 1}, new int[]{-1, 0}, new int[]{0, -1}, new int[]{1, 0});
//		int currentDirection = 0;

        for (String instruction : instructions) {
            char direction = instruction.charAt(0);
            int amount = Integer.parseInt(instruction.substring(1));
            if(direction == 'N') {
//				northEast[0] += amount;
                wpNorthEast[0] += amount;
            }
            if(direction == 'S') {
//				northEast[0] -= amount;
                wpNorthEast[0] -= amount;
            }
            if(direction == 'E') {
//				northEast[1] += amount;
                wpNorthEast[1] += amount;
            }
            if(direction == 'W') {
//				northEast[1] -= amount;
                wpNorthEast[1] -= amount;
            }
            if(direction == 'F') {
//				int[] vector = vectors.get(currentDirection);
//				northEast[0] += amount * vector[0];
//				northEast[1] += amount * vector[1];
                northEast[0] += amount * wpNorthEast[0];
                northEast[1] += amount * wpNorthEast[1];
            }
            if(direction == 'R') {
                int turn = amount / 90;
//				currentDirection = (currentDirection + turn) % vectors.size();
                while(turn-- > 0) {
                    wpNorthEast = new int[]{wpNorthEast[1] * -1, wpNorthEast[0]};
                }
            }
            if(direction == 'L') {
                int turn = amount / 90;
//				currentDirection = (currentDirection - turn + vectors.size()) % vectors.size();
                while(turn-- > 0) {
                    wpNorthEast = new int[] {wpNorthEast[1], wpNorthEast[0] * -1};
                }
            }
        }

        System.out.println(Math.abs(northEast[0]) + Math.abs(northEast[1]));
    }
}
