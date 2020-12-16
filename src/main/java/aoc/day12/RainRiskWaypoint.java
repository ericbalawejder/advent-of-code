package aoc.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RainRiskWaypoint {

    private GridPosition waypointGridPosition;
    private Orientation waypointOrientation;
    private GridPosition ferryGridPosition;
    private Orientation ferryOrientation;

    public static void main(String[] args) {
        RainRiskWaypoint rainRiskWaypoint = new RainRiskWaypoint(
                new GridPosition(0, 0), Orientation.EAST,
                new GridPosition(10, 1), Orientation.EAST);

        final List<String> navigationInstructions = rainRiskWaypoint
                .readFile("src/main/java/aoc/day12/test.txt");

        System.out.println(rainRiskWaypoint.manhattanDistance(navigationInstructions));
        System.out.println("Waypoint " + rainRiskWaypoint.getWaypointGridPosition());
        System.out.println("Waypoint " + rainRiskWaypoint.getWaypointOrientation());
        System.out.println("Ferry " + rainRiskWaypoint.getFerryGridPosition());
        System.out.println("Ferry " + rainRiskWaypoint.getFerryOrientation());
    }

    RainRiskWaypoint(GridPosition initialFerryGridPosition, Orientation initialFerryOrientation,
                     GridPosition initialWaypointGridPosition, Orientation initialWaypointOrientation) {
        this.ferryGridPosition = initialFerryGridPosition;
        this.ferryOrientation = initialFerryOrientation;
        this.waypointGridPosition = initialWaypointGridPosition;
        this.waypointOrientation = initialWaypointOrientation;
    }

    int manhattanDistance(List<String> instructions) {
        for (String instruction : instructions) {
            final char action = instruction.charAt(0);
            final int distance = Integer.parseInt(instruction.substring(1));
            parseSymbols(action, distance);
        }
        return Math.abs(ferryGridPosition.getX()) + Math.abs(ferryGridPosition.getY());
    }

    GridPosition getWaypointGridPosition() {
        return waypointGridPosition;
    }

    void setWaypointGridPosition(GridPosition waypointGridPosition) {
        this.waypointGridPosition = waypointGridPosition;
    }

    Orientation getWaypointOrientation() {
        return waypointOrientation;
    }

    void setWaypointOrientation(Orientation waypointOrientation) {
        this.waypointOrientation = waypointOrientation;
    }

    GridPosition getFerryGridPosition() {
        return ferryGridPosition;
    }

    void setFerryGridPosition(GridPosition ferryGridPosition) {
        this.ferryGridPosition = ferryGridPosition;
    }

    Orientation getFerryOrientation() {
        return ferryOrientation;
    }

    void setFerryOrientation(Orientation ferryOrientation) {
        this.ferryOrientation = ferryOrientation;
    }

    private void parseSymbols(char character, int distance) {
        switch (character) {
            case 'N': {
                Orientation orientation = getWaypointOrientation();
                setWaypointOrientation(Orientation.NORTH);
                advanceWaypoint(distance);
                setWaypointOrientation(orientation);
                break;
            }
            case 'E': {
                Orientation orientation = getWaypointOrientation();
                setWaypointOrientation(Orientation.EAST);
                advanceWaypoint(distance);
                setWaypointOrientation(orientation);
                break;
            }
            case 'S': {
                Orientation orientation = getWaypointOrientation();
                setWaypointOrientation(Orientation.SOUTH);
                advanceWaypoint(distance);
                setWaypointOrientation(orientation);
                break;
            }
            case 'W': {
                Orientation orientation = getWaypointOrientation();
                setWaypointOrientation(Orientation.WEST);
                advanceWaypoint(distance);
                setWaypointOrientation(orientation);
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
                advanceFerry(distance);
                break;
            default:
                throw new IllegalArgumentException("Bad instruction format");
        }
    }

    private void advanceWaypoint(int distance) {
        switch (getWaypointOrientation()) {
            case NORTH:
                setWaypointGridPosition(new GridPosition(waypointGridPosition.getX(),
                        waypointGridPosition.getY() + distance));
                break;
            case EAST:
                setWaypointGridPosition(new GridPosition(waypointGridPosition.getX() + distance,
                        waypointGridPosition.getY()));
                break;
            case SOUTH:
                setWaypointGridPosition(new GridPosition(waypointGridPosition.getX(),
                        waypointGridPosition.getY() - distance));
                break;
            case WEST:
                setWaypointGridPosition(new GridPosition(waypointGridPosition.getX() - distance,
                        waypointGridPosition.getY()));
                break;
        }
    }

    private void advanceFerry(int distance) {
        setFerryGridPosition(new GridPosition(
                waypointGridPosition.getX() * distance + ferryGridPosition.getX(),
                waypointGridPosition.getY() * distance + ferryGridPosition.getY()));
    }

    private void turnRight() {
        final int currentIndex = getWaypointOrientation().ordinal();
        final int newIndex = Math.floorMod(currentIndex + 1, Orientation.values().length);
        setWaypointOrientation(Orientation.values()[newIndex]);

        switch (getWaypointOrientation()) {
            case NORTH:
                setWaypointGridPosition(new GridPosition(- Math.abs(waypointGridPosition.getY()),
                        Math.abs(waypointGridPosition.getX())));
                break;
            case EAST:
                setWaypointGridPosition(new GridPosition(Math.abs(waypointGridPosition.getY()),
                        Math.abs(waypointGridPosition.getX())));
                break;
            case SOUTH:
                setWaypointGridPosition(new GridPosition(Math.abs(waypointGridPosition.getY()),
                        - Math.abs(waypointGridPosition.getX())));
                break;
            case WEST:
                setWaypointGridPosition(new GridPosition(- Math.abs(waypointGridPosition.getY()),
                        - Math.abs(waypointGridPosition.getX())));
                break;
        }
    }

    private void turnLeft() {
        final int currentIndex = getWaypointOrientation().ordinal();
        final int newIndex = Math.floorMod(currentIndex - 1, Orientation.values().length);
        setWaypointOrientation(Orientation.values()[newIndex]);

        switch (getWaypointOrientation()) {
            case NORTH:
                setWaypointGridPosition(new GridPosition(- Math.abs(waypointGridPosition.getY()),
                        Math.abs(waypointGridPosition.getX())));
                break;
            case EAST:
                setWaypointGridPosition(new GridPosition(Math.abs(waypointGridPosition.getY()),
                        Math.abs(waypointGridPosition.getX())));
                break;
            case SOUTH:
                setWaypointGridPosition(new GridPosition(Math.abs(waypointGridPosition.getY()),
                        - Math.abs(waypointGridPosition.getX())));
                break;
            case WEST:
                setWaypointGridPosition(new GridPosition(- Math.abs(waypointGridPosition.getY()),
                        - Math.abs(waypointGridPosition.getX())));
                break;
        }
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
