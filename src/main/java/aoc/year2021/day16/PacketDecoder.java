package aoc.year2021.day16;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacketDecoder {

  public static void main(String[] args) {
    final String path = "src/main/java/aoc/year2021/day16/hexadecimal.txt";
    final String hex = importHexFromBITS(path).orElseThrow();
    final String binary = convertHexToBinary(hex);
    final Packet packet = readPacket(binary);
    System.out.println(packet.sumVersions());
    System.out.println(packet.getValue());
  }

  public static Packet readPacket(String binary) {
    final List<Packet> packets = new ArrayList<>();
    readSubPacket(binary, packets);
    return packets.get(0);
  }

  private static int readSubPacket(String binary, List<Packet> packets) {
    int position = 0;
    int version = convertBinaryToDecimal(binary.substring(position, position + 3)).intValue();
    position += 3;
    int typeId = convertBinaryToDecimal(binary.substring(position, position + 3)).intValue();
    position += 3;

    if (typeId == 4) {
      final StringBuilder subPacket = new StringBuilder();
      char bit;
      do {
        bit = binary.charAt(position);
        subPacket.append(binary, position + 1, position + 5);
        position += 5;
      } while (bit == '1');
      packets.add(new LiteralValue(version, typeId, convertBinaryToDecimal(subPacket.toString()).longValue()));
      return position;
    } else {
      char lengthTypeId = binary.charAt(position);
      position++;
      final List<Packet> subPackets = new ArrayList<>();
      if (lengthTypeId == '1') {
        int numberOfSubPackets = convertBinaryToDecimal(binary.substring(position, position + 11)).intValue();
        position += 11;
        for (int p = 0; p < numberOfSubPackets; p++) {
          position += readSubPacket(binary.substring(position), subPackets);
        }
        packets.add(new Operator(version, typeId, subPackets));
        return position;
      } else {
        int numberOfSubPacketBits = convertBinaryToDecimal(binary.substring(position, position + 15)).intValue();
        position += 15;
        String bits = binary.substring(position, position + numberOfSubPacketBits);
        int readBits = 0;
        while (readBits < numberOfSubPacketBits) {
          int consumed = readSubPacket(bits, subPackets);
          readBits += consumed;
          bits = bits.substring(consumed);
        }
        position += numberOfSubPacketBits;
        packets.add(new Operator(version, typeId, subPackets));
        return position;
      }
    }
  }

  private static BigInteger convertBinaryToDecimal(String binary) {
    return new BigInteger(binary, 2);
  }

  private static String convertHexToBinary(String hex) {
    return new BigInteger(hex, 16).toString(2);
  }

  private static Optional<String> importHexFromBITS(String path) {
    try {
      final String hex = Files.readString(Path.of(path));
      return Optional.ofNullable(hex);
    } catch (IOException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

}
