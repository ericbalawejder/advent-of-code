package aoc.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO improve upon readFile() and processInstructions()
public class DockingData {

    public static void main(String[] args) {
        final List<String> data =
                readFile("src/main/java/aoc/day14/initialization-program.txt");

        final List<List<String>> instructions = processInstructions(data);

        System.out.println(decoderChip1(instructions));
        System.out.println(decoderChip2(instructions));
    }

    static long decoderChip2(List<List<String>> instructions) {
        final HashMap<Long, Long> memory = new HashMap<>();
        Optional<String> bitmask = Optional.empty();

        for (List<String> instruction : instructions) {
            if (instruction.size() == 1) {
                bitmask = Optional.of(instruction.get(0));
            } else {
                final long memoryAddress = Long.parseLong(instruction.get(0));
                final Long value = Long.parseLong(instruction.get(1));
                final List<Long> addresses = applyBitmaskToAddress(memoryAddress, bitmask.orElse(""));
                for (long address : addresses) {
                    memory.put(address, value);
                }
            }
        }
        return memory.values()
                .stream()
                .reduce(0L, Long::sum);
    }

    private static List<Long> applyBitmaskToAddress(long address, String bitmask) {
        Set<Long> addresses = new HashSet<>(Collections.singletonList(address));

        for (int i = 0; i < bitmask.length(); i++) {
            final long bit = 1L << (bitmask.length() - i - 1);
            if (bitmask.charAt(i) != '0') {
                addresses = addresses.stream()
                        .map(a -> a | bit)
                        .collect(Collectors.toSet());

                if (bitmask.charAt(i) == 'X') {
                    addresses.addAll(addresses.stream()
                            .map(a -> a & ~bit)
                            .collect(Collectors.toList()));
                }
            }
        }
        return List.copyOf(addresses);
    }

    static Long decoderChip1(List<List<String>> instructions) {
        final HashMap<Long, Long> memory = new HashMap<>();
        Optional<String> bitmask = Optional.empty();

        for (List<String> instruction : instructions) {
            if (instruction.size() == 1) {
                bitmask = Optional.of(instruction.get(0));
            } else {
                final Long address = Long.parseLong(instruction.get(0));
                final long value = Long.parseLong(instruction.get(1));
                final Long maskedValue = applyBitmask(value, bitmask.orElse(""));
                memory.put(address, maskedValue);
            }
        }
        return memory.values()
                .stream()
                .reduce(0L, Long::sum);
    }

    private static long applyBitmask(long value, String bitmask) {
        final long setValue = Long.parseLong(bitmask.replace("X", "0"), 2);
        final long clearValue = Long.parseLong(bitmask.replace("X", "1"), 2);
        return (value | setValue) & clearValue;
    }

    private static List<List<String>> processInstructions(List<String> data) {
        final List<List<String>> instructions = new ArrayList<>(data.size());
        for (final String line : data) {
            if (line.startsWith("mask")) {
                instructions.add(List.of(line.substring(line.lastIndexOf(' ') + 1)));
            } else {
                final String address = line.substring(line.indexOf('[') + 1, line.indexOf(']'));
                final String value = line.substring(line.lastIndexOf(' ') + 1);
                instructions.add(List.of(address, value));
            }
        }
        return List.copyOf(instructions);
    }

    private static List<String> readFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
