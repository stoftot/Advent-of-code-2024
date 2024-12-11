package December11;

import javax.naming.InsufficientResourcesException;
import java.io.File;
import java.util.*;

public class December11 {
    public static void main(String[] args) {
        try {
//            createMapForSingelDigits();
//            First();
            Second();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void First() throws Exception {
        var input = new File("src/December11/Data/ownTest.txt");
        var myReader = new Scanner(input);
        ArrayList<String> stones = readStones(myReader);

        System.out.println("Initial stones:");
        for (var stone : stones) {
            System.out.print(stone + " ");
        }
        System.out.println();

        for (int i = 1; i <= 25; i++) {
            stones = blink(stones);
            System.out.println("After " + i + " blinks:");
            for (var stone : stones) {
                System.out.print(stone + " ");
            }
            System.out.println();
        }

        System.out.println("Final stones: " + stones.size());
    }

    public static void Second() throws Exception {
        //start time
        long startTime = System.currentTimeMillis();
        var input = new File("src/December11/Data/ownTest.txt");
        var myReader = new Scanner(input);
        ArrayList<String> stones = readStones(myReader);
        var stoneMap = createMapForSingelDigits();
        int amountOfBlinks = 10;
        long stoneCount = solveStones(stones, amountOfBlinks, stoneMap);

        System.out.println("Final stones: " + stoneCount);
        //end time, and print execution time in seconds
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) / 1000.0 + " seconds");
    }

    private static ArrayList<String> blink(List<String> stones) {
        ArrayList<String> newStones = new ArrayList<>();
        for (String stone : stones) {
            String[] updatedStone = updateStone(stone);
            Collections.addAll(newStones, updatedStone);
        }
        return newStones;
    }

    private static String[] updateStone(String stone) {
        if (stone.length() == 1) {
            if (stone.charAt(0) == '0') {
                return new String[]{"1"};
            }
        }
        if (stone.length() % 2 == 0) {
            String firstHalf = stone.substring(0, stone.length() / 2);
            String secondHalf = stone.substring(stone.length() / 2);

            return new String[]{removeUnnecessaryZeros(firstHalf),
                    removeUnnecessaryZeros(secondHalf)};
        }

        long newStoneValue = Long.parseLong(stone) * 2024;
        return new String[]{String.valueOf(newStoneValue)};
    }

    private static String removeUnnecessaryZeros(String stone) {
        return String.valueOf(Long.parseLong(stone));
    }

    private static ArrayList<String> readStones(Scanner myReader) {
        String[] input = myReader.nextLine().split(" ");
        var stones = new ArrayList<String>();
        Collections.addAll(stones, input);
        return stones;
    }

    private static Map<Integer, StoneMap> createMapForSingelDigits() {
        var map = new HashMap<Integer, StoneMap>();
        for (int i = 0; i < 10; i++) {
            if (i == 8) continue;
            var tempBlinkMap = new HashMap<Integer, Integer>();
            List<String> tempStoneList = List.of(String.valueOf(i));
            tempStoneList = blink(tempStoneList);
            int blinkCount = 1;
            tempBlinkMap.put(blinkCount, tempStoneList.size());
            while (!isListSingleDigit(tempStoneList)) {
                tempStoneList = blink(tempStoneList);
                blinkCount++;
                tempBlinkMap.put(blinkCount, tempStoneList.size());
            }
            map.put(i, new StoneMap(tempBlinkMap, tempStoneList, blinkCount));
        }
//        for (var entry : map.entrySet()) {
//            for (var blinks : entry.getValue().blinkMap.entrySet()) {
//                System.out.println("Blink: " + blinks.getKey() + " Stones: " + blinks.getValue());
//            }
//            for (var stone : entry.getValue().singleDigitStones) {
//                System.out.print(stone + " ");
//            }
//            System.out.println("\n------------------------------------------------------");
//        }
        return map;
    }

    private static long solveStones(List<String> stones, int blinksLeft, Map<Integer, StoneMap> stoneMap) {
        long finalStoneCount = 0;
        for (String stone : stones) {
            if (stone.length() == 1) {
                if (stone.equals("8")) {
                    try {
                        finalStoneCount += handle8(blinksLeft, stoneMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    finalStoneCount += blinkStoneToEnd(blinksLeft, Integer.parseInt(stone), stoneMap);
                }
            } else {
                var tempStones = blink(stones);
                solveStones(tempStones, blinksLeft - 1, stoneMap);
            }
        }
        return finalStoneCount;
    }

    private static long blinkStoneToEnd(int blinksLeft, int stone, Map<Integer, StoneMap> stoneMap) {
        StoneBlink stoneBlink = new StoneBlink();
        if (blinksLeft > stoneMap.get(stone).maxBlinks) {
            stoneBlink = blinkWithStoneMap(blinksLeft, stoneMap.get(stone));
            blinksLeft -= stoneBlink.blinksUsed;
            long totalStones = 0;
            for (String stoneString : stoneBlink.stones) {
                totalStones += blinkStoneToEnd(blinksLeft, Integer.parseInt(stoneString), stoneMap);
            }
            return totalStones;
        } else {
            stoneBlink = blinkWithStoneMap(blinksLeft, stoneMap.get(stone));
        }
        return stoneBlink.numberOfStones;
    }

    private static StoneBlink blinkWithStoneMap(int blinksLeft, StoneMap stoneMap) {
        StoneBlink stoneBlink = new StoneBlink();

        if (blinksLeft > stoneMap.maxBlinks) {
            stoneBlink.blinksUsed = stoneMap.maxBlinks;
            stoneBlink.stones = stoneMap.singleDigitStones;
            stoneBlink.numberOfStones = stoneMap.singleDigitStones.size();
        } else {
            stoneBlink.blinksUsed = blinksLeft;
            stoneBlink.numberOfStones = stoneMap.blinkMap.get(blinksLeft);
        }

        return stoneBlink;
    }

    private static long handle8(int blinksLeft, Map<Integer, StoneMap> stoneMap) throws Exception {
        var map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        map.put(2, 1);
        map.put(3, 2);
        map.put(4, 4); // 8
        map.put(5, 7);
        String[] singleDigitStones = new String[]{"3", "2", "7", "7", "2", "6"};
        if (blinksLeft > 5) {
            for (var stone : singleDigitStones) {
                long stonesAtEnd = blinkStoneToEnd(blinksLeft - 5, Integer.parseInt(stone), stoneMap);
                return stonesAtEnd + handle8(blinksLeft - 4, stoneMap);
            }
        } else {
            return map.get(blinksLeft);
        }
        throw new Exception("This wasent supposed to happen");
    }

    private static class StoneBlink {
        private int blinksUsed;
        private List<String> stones;
        private long numberOfStones;
    }

    private static class StoneMap {
        private Map<Integer, Integer> blinkMap;
        private List<String> singleDigitStones;
        private int maxBlinks;

        public StoneMap(HashMap<Integer, Integer> blinkMap, List<String> singleDigitStones, int maxBlinks) {
            this.blinkMap = blinkMap;
            this.singleDigitStones = singleDigitStones;
            this.maxBlinks = maxBlinks;
        }
    }

    private static boolean isListSingleDigit(List<String> stones) {
        for (String stone : stones) {
            if (stone.length() > 1) {
                return false;
            }
        }
        return true;
    }
}
