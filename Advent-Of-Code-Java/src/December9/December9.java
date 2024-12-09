package December9;

import javax.management.ValueExp;
import java.awt.*;
import java.io.File;
import java.util.*;

//Traps remember to keep the ids grouped and not as a single char
//this was a problem when creating one string and swapping the characters
//only look to the left of it
public class December9 {
    public static void main(String[] args) {
        try {
//            First();
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
        var id = 0;

        for (var i = 0; i < data.length; i++) {
            if (i % 2 == 0) {
                for (var j = 0; j < Integer.parseInt(data[i]); j++) {
                    disk.add(String.valueOf(id));
                }
                id++;
            } else {
                for (var j = 0; j < Integer.parseInt(data[i]); j++) {
                    disk.add(".");
                }
            }
        }

        var pointer = disk.size() - 1;
        while (disk.get(pointer).equals(".")) {
            pointer--;
        }

        for (var i = 0; i < pointer; i++) {
            if (disk.get(i) == ".") {
                swapCharacter(i, pointer, disk);
                while (disk.get(pointer).equals(".")) {
                    pointer--;
                }
            }
        }

        long sum = 0;

        for (int i = 0; !disk.get(i).equals("."); i++) {
            sum += (Integer.parseInt(disk.get(i)) * i);
        }

        System.out.println(sum);
    }

    public static void Second() throws Exception {
        var input = new File("src/December9/Data/input.txt");
        var myReader = new Scanner(input);
        var data = myReader.nextLine().split("");
        var disk = new ArrayList<String>();
        var id = 0;
        var freeSpaceMap = new HashMap<Integer, Integer>();
        var idMap = new HashMap<Integer, DiskData>();

        for (var i = 0; i < data.length; i++) {
            int value = Integer.parseInt(data[i]);
            if (i % 2 == 0) {
                for (var j = 0; j < value; j++) {
                    disk.add(String.valueOf(id));
                }
                
                idMap.put(id, new DiskData(){{
                    size = value;
                    lastIndexOfBlock = disk.size()-1;
                }});
                
                id++;
            } else {
                for (var j = 0; j < value; j++) {
                    disk.add(".");
                }
                if (value > 0) {
                    freeSpaceMap.put(disk.size()-value, value);
                }
            }
        }

        var pointer = disk.size() - 1;
        while (disk.get(pointer).equals(".")) {
            pointer--;
        }

        for (int i = id - 1; i >= 0; i--) {
            int spaceNeeded = idMap.get(i).size;
            //Find free space
            ArrayList<Integer> freeSpaceKeys = new ArrayList<>(freeSpaceMap.keySet());
            Collections.sort(freeSpaceKeys);
            for (int freeSpaceKey : freeSpaceKeys) {
                int freeSpace = freeSpaceMap.get(freeSpaceKey);

                //if it cant find space on the left of it, then break
                if(freeSpaceKey > idMap.get(i).lastIndexOfBlock){
                    break;
                }
                
                
                if (freeSpace >= spaceNeeded) {
                    //Move block
                    pointer = idMap.get(i).lastIndexOfBlock;
                    if(!tryToMoveBlock(freeSpaceKey, freeSpaceKey + (spaceNeeded-1), pointer - (spaceNeeded-1), pointer, disk)) {
                        System.out.println("Failed to move block");
                    }
                    
                    //Update free space
                    freeSpaceMap.remove(freeSpaceKey);
                    if (freeSpace != spaceNeeded) {
                        freeSpaceMap.put(freeSpaceKey + spaceNeeded, freeSpace - spaceNeeded);
                    }
                    break;
                }
            }
//            pointer -= spaceNeeded;
//            while (disk.get(pointer).equals(".")) {
//                pointer--;
//            }
//            System.out.println("id: " + i);
//            for (var u = 0; u < disk.size(); u++) {
//                System.out.print(disk.get(u));
//            }
//            System.out.println();
        }

//        for (var i = 0; i < pointer; i++) {
//            if (disk.get(i).equals(".")) {
//                //Determine block
//                var first = i;
//                var second = i;
//                while (disk.get(second).equals(".")) {
//                    second++;
//                }
//                second--;
//                //Determine block to move
//                int third = pointer--;
//                var blockID = disk.get(pointer);
//                while (disk.get(third).equals(blockID)) {
//                    third--;
//                }
//                third++;
//
//                if (!tryToMoveBlock(first, second, third, pointer, disk)) {
//                    while (disk.get(i).equals(blockID)) {
//                        i++;
//                    }
//                }
//                while (disk.get(pointer).equals(".")) {
//                    pointer--;
//                }
//                
//                i = second;
//            }
//        }

        //print disk
//        for (var i = 0; i < disk.size(); i++) {
//            System.out.print(disk.get(i));
//        }
//        System.out.println();

        long sum = 0;

        for (int i = 0; i < disk.size(); i++) {
            if (!disk.get(i).equals(".")) {
                sum += (Integer.parseInt(disk.get(i)) * i);
            }
        }

        System.out.println(sum);
    }
    
    private static class DiskData{
        public int size;
        public int lastIndexOfBlock;
    }

    private static void swapCharacter(int first, int second, ArrayList<String> list) {
        var temp = list.get(first);
        list.set(first, list.get(second));
        list.set(second, temp);
    }

    private static boolean tryToMoveBlock(int first, int second, int third, int fourth, ArrayList<String> list) {
//        for (int i = first; i < second; i++) {
//            if (!list.get(i).equals(".")) {
//                return false;
//            }
//        }
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
