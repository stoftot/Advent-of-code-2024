package December9;

import java.io.File;
import java.util.*;

//Traps remember to keep the ids grouped and not as a single char
//this was a problem when creating one string and swapping the characters
//only look to the left of it
public class December9 {
    public static void main(String[] args) {
        try {
            First();
            Second();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void First() throws Exception {
        var input = new File("src/December9/Data/input.txt");
        var myReader = new Scanner(input);
        var data = myReader.nextLine().split("");
        var disk = new ArrayList<String>();
        int id = 0;

        //create disk representation
        for (int i = 0; i < data.length; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < Integer.parseInt(data[i]); j++) {
                    disk.add(String.valueOf(id));
                }
                id++;
            } else {
                for (int j = 0; j < Integer.parseInt(data[i]); j++) {
                    disk.add(".");
                }
            }
        }

        //create a pointer starting at the end of data on the disk
        int pointer = disk.size() - 1;
        while (disk.get(pointer).equals(".")) {
            pointer--;
        }

        //loop through the disk and swap the empty spaces with the data
        for (int i = 0; i < pointer; i++) {
            if (disk.get(i).equals(".")) {
                swapCharacter(i, pointer, disk);
                while (disk.get(pointer).equals(".")) {
                    pointer--;
                }
            }
        }

        //loop through the disk and calculate filesystem checksum
        long sum = 0;
        for (int i = 0; !disk.get(i).equals("."); i++) {
            sum += (Integer.parseInt(disk.get(i)) * i);
        }

        System.out.println(sum);
    }

    public static void Second() throws Exception {
        var input = new File("src/December9/Data/input.txt");
        var myReader = new Scanner(input);
        String[] data = myReader.nextLine().split("");
        var disk = new ArrayList<String>();
        int id = 0;
        //free space map, key is the index of the free space and value is the size of the free space
        var freeSpaceMap = new HashMap<Integer, Integer>();
        //id map, key is the id of the block and value is the size and last index of the block
        var idMap = new HashMap<Integer, DiskData>();

        //create disk representation
        //and fill out the free space map and id map
        for (int i = 0; i < data.length; i++) {
            int value = Integer.parseInt(data[i]);
            if (i % 2 == 0) {
                for (int j = 0; j < value; j++) {
                    disk.add(String.valueOf(id));
                }

                idMap.put(id, new DiskData() {{
                    size = value;
                    lastIndexOfBlock = disk.size() - 1;
                }});

                id++;
            } else {
                for (int j = 0; j < value; j++) {
                    disk.add(".");
                }
                if (value > 0) {
                    freeSpaceMap.put(disk.size() - value, value);
                }
            }
        }

        //loop through the disk and if there is a free space to the left of the data block, 
        // move it to that free space, and update the free space map
        for (int i = id - 1; i >= 0; i--) {
            int spaceNeeded = idMap.get(i).size;

            //Find free space
            //sort the free space map by key, to get the free space from left to right
            ArrayList<Integer> freeSpaceKeys = new ArrayList<>(freeSpaceMap.keySet());
            Collections.sort(freeSpaceKeys);

            for (int freeSpaceKey : freeSpaceKeys) {
                int freeSpace = freeSpaceMap.get(freeSpaceKey);

                //if it cant find space on the left of it, then break
                if (freeSpaceKey > idMap.get(i).lastIndexOfBlock) {
                    break;
                }

                if (freeSpace >= spaceNeeded) {
                    //Move block
                    int blocksLastIndex = idMap.get(i).lastIndexOfBlock;

                    //To account for indexing, we need to subtract 1 from the space needed
                    if (!tryToMoveBlock(freeSpaceKey, freeSpaceKey + (spaceNeeded - 1), blocksLastIndex - (spaceNeeded - 1), blocksLastIndex, disk)) {
                        System.out.println("Failed to move block");
                    }

                    //Update free space,
                    //that means remove the old free space, 
                    //and if there is any space left, add it to the free space map 
                    freeSpaceMap.remove(freeSpaceKey);
                    if (freeSpace != spaceNeeded) {
                        freeSpaceMap.put(freeSpaceKey + spaceNeeded, freeSpace - spaceNeeded);
                    }
                    break;
                }
            }
        }

        //loop through the disk and calculate filesystem checksum
        long sum = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (!disk.get(i).equals(".")) {
                sum += (Integer.parseInt(disk.get(i)) * i);
            }
        }

        System.out.println(sum);
    }

    private static class DiskData {
        public int size;
        public int lastIndexOfBlock;
    }

    private static void swapCharacter(int first, int second, ArrayList<String> list) {
        var temp = list.get(first);
        list.set(first, list.get(second));
        list.set(second, temp);
    }

    private static boolean tryToMoveBlock(int first, int second, int third, int fourth, ArrayList<String> list) {
        if (second - first < fourth - third) {
            return false;
        }

        for (int i = first; i <= second; i++) {
            swapCharacter(i, third, list);
            third++;
        }
        return true;
    }
}
