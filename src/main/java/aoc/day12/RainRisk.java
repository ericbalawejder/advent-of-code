package aoc.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RainRisk {

    private GridPosition gridPosition;
    private Orientation orientation;

    public static void main(String[] args) {
        RainRisk ferry = new RainRisk(new GridPosition(0, 0), Orientation.EAST);
        final List<String> navigationInstructions =
                ferry.readFile("src/main/java/aoc/day12/navigation.txt");
        System.out.println(ferry.manhattanDistance(navigationInstructions));
    }

    RainRisk(GridPosition initialGridPosition, Orientation initialOrientation) {
        this.gridPosition = initialGridPosition;
        this.orientation = initialOrientation;
    }

    int manhattanDistance(List<String> instructions) {
        for (String instruction : instructions) {
            final char action = instruction.charAt(0);
            final int distance = Integer.parseInt(instruction.substring(1));
            parseSymbols(action, distance);
        }
        return Math.abs(gridPosition.getX()) + Math.abs(gridPosition.getY());
    }

    void setGridPosition(GridPosition gridPosition) {
        this.gridPosition = gridPosition;
    }

    Orientation getOrientation() {
        return this.orientation;
    }

    void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    private void parseSymbols(char character, int distance) {
        switch (character) {
            case 'N': {
                Orientation orientation = getOrientation();
                setOrientation(Orientation.NORTH);
                advance(distance);
                setOrientation(orientation);
                break;
            }
            case 'E': {
                Orientation orientation = getOrientation();
                setOrientation(Orientation.EAST);
                advance(distance);
                setOrientation(orientation);
                break;
            }
            case 'S': {
                Orientation orientation = getOrientation();
                setOrientation(Orientation.SOUTH);
                advance(distance);
                setOrientation(orientation);
                break;
            }
            case 'W': {
                Orientation orientation = getOrientation();
                setOrientation(Orientation.WEST);
                advance(distance);
                setOrientation(orientation);
                break;
            }
            case 'R':
                for (int i = 0; i < distance / 90; i++) {
                    turnRight();
                }
                break;
            case 'L':
                for (int i = 0; i < distance / 90; i++) {
                    turnLeft();
                }
                break;
            case 'F':
                advance(distance);
                break;
            default:
                throw new IllegalArgumentException("Bad instruction format");
        }
    }

    private void advance(int distance) {
        switch (getOrientation()) {
            case NORTH:
                setGridPosition(new GridPosition(gridPosition.getX(),
                        gridPosition.getY() + distance));
                break;
            case EAST:
                setGridPosition(new GridPosition(gridPosition.getX() + distance,
                        gridPosition.getY()));
                break;
            case SOUTH:
                setGridPosition(new GridPosition(gridPosition.getX(),
                        gridPosition.getY() - distance));
                break;
            case WEST:
                setGridPosition(new GridPosition(gridPosition.getX() - distance,
                        gridPosition.getY()));
                break;
        }
    }

    private void turnRight() {
        final int currentIndex = getOrientation().ordinal();
        final int newIndex = Math.floorMod(currentIndex + 1, Orientation.values().length);
        setOrientation(Orientation.values()[newIndex]);
    }

    private void turnLeft() {
        final int currentIndex = getOrientation().ordinal();
        final int newIndex = Math.floorMod(currentIndex - 1, Orientation.values().length);
        setOrientation(Orientation.values()[newIndex]);
    }

    private List<String> readFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.collect(Collectors.toUnmodifiableList());
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
