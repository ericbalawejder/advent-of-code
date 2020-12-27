package aoc.day25;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComboBreaker {

    public static void main(String[] args) {
        final List<Long> publicKeys = readFile("src/main/java/aoc/day25/public-keys.txt");
        System.out.println(calculateEncryptionKey(publicKeys.get(0), publicKeys.get(1), 7));
    }

    static long calculateEncryptionKey(long cardPublicKey, long doorPublicKey, long subjectNumber) {
        final long doorLoopSize = findLoopSize(doorPublicKey, subjectNumber);
        final long cardEncryptionKey = findPrivateKey(cardPublicKey, subjectNumber);
        long value = 1;
        for (int i = 0; i < doorLoopSize; i++) {
            value = value * cardEncryptionKey % 20201227;
        }
        return value;
    }

    private static long findPrivateKey(long publicKey, long subjectNumber) {
        final long loopSize = findLoopSize(publicKey, subjectNumber);
        long value = 1;
        for (int i = 0; i < loopSize; i++) {
            value = value * subjectNumber % 20201227;
        }
        return value;
    }

    private static long findLoopSize(long pubicKey, long subjectNumber) {
        long value = 1;
        long loopSize = 0;
        while (value != pubicKey) {
            value = value * subjectNumber % 20201227;
            loopSize++;
        }
        return loopSize;
    }

    private static List<Long> readFile(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.map(Long::parseLong)
                    .collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
