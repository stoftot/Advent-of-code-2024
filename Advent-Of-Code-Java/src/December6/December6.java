package December6;

import com.sun.source.doctree.ValueTree;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.net.PortUnreachableException;
import java.util.*;

public class December6 {
    public static void main(String[] args) {
        try {
            //First();
            Second();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void First() throws FileNotFoundException {
        var input = new File("src/December6/Data/input.txt");
        Scanner myReader = new Scanner(input);
        var creation = createMap(myReader);
        var map = creation.map;
        var guardPosX = creation.guardPosX;
        var guardPosY = creation.guardPosY;
        var numberOfSpotsGuardVisited = 0;
        
        try {
            while (true) {
                var guardMovment = moveGuard(map, guardPosX, guardPosY);
                guardPosX = guardMovment.newGuardPosX;
                guardPosY = guardMovment.newGuardPosY;
                if(!guardMovment.hasBeenThere) {
                    numberOfSpotsGuardVisited++;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(numberOfSpotsGuardVisited+1);
        }
    }
    
    public static void Second() throws FileNotFoundException {
        //start timer
        long startTime = System.nanoTime();
        var input = new File("src/December6/Data/input.txt");
        Scanner myReader = new Scanner(input);
        var creation = createMap(myReader);
        var map = creation.map;
        var originalMap = copyMap(map);
        var startGuardPosX = creation.guardPosX;
        var startGuardPosY = creation.guardPosY;
        var guardPosX = creation.guardPosX;
        var guardPosY = creation.guardPosY;
        var numberOfSpotsGuardVisited = 0;
        
        var setOfCoordinates = new HashSet<Point>();
        
        //fill out map
        try {
            while (true) {
                setOfCoordinates.add(new Point(guardPosX, guardPosY));
                var guardMovment = moveGuard(map, guardPosX, guardPosY);
                guardPosX = guardMovment.newGuardPosX;
                guardPosY = guardMovment.newGuardPosY;
                if(!guardMovment.hasBeenThere) {
                    numberOfSpotsGuardVisited++;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        var numberOfPlacesObstacelCanBePlacedToCreateALoop = 0;
        for(var point : setOfCoordinates) {
            var x = (int)point.getX();
            var y = (int)point.getY();
            if(originalMap[x][y] == '^') {
                continue;
            }
            
            var tempMap = copyMap(originalMap);
            tempMap[x][y] = '#';
            var tempGuardPosX = startGuardPosX;
            var tempGuardPosY = startGuardPosY;
            
            var mapForGuardPositions = new HashMap<Point, Set<Character>>();
            while(true) {
                try {
                    var guardMovment = moveGuard(tempMap, tempGuardPosX, tempGuardPosY);
                    tempGuardPosX = guardMovment.newGuardPosX;
                    tempGuardPosY = guardMovment.newGuardPosY;
                    
                    if(mapForGuardPositions.containsKey(new Point(tempGuardPosX, tempGuardPosY))) {
                        var set = mapForGuardPositions.get(new Point(tempGuardPosX, tempGuardPosY));
                        if(set.contains(guardMovment.newGuard)) {
                            //System.out.println("did loop with obstacle at: " + x + ", " + y);
                            numberOfPlacesObstacelCanBePlacedToCreateALoop++;
                            break;
                        }
                        set.add(guardMovment.newGuard);
                    } else {
                        var set = new HashSet<Character>();
                        set.add(guardMovment.newGuard);
                        mapForGuardPositions.put(new Point(tempGuardPosX, tempGuardPosY), set);
                    }
                } catch (Exception e) {
                    //System.out.println("did not loop with obstacle at: " + x + ", " + y);
                    break;
                }
            }
        }
        
        System.out.println("Places to place obstacle: "+numberOfPlacesObstacelCanBePlacedToCreateALoop);
        //end timer
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);
    }


    private static mapCreation createMap(Scanner myReader) {
        var cration = new mapCreation();
        var list = new ArrayList<char[]>();
        while (myReader.hasNextLine()) {
            var line = myReader.nextLine().split("");
            var arr = new char[line.length];
            for(int i = 0; i < line.length; i++) {
                arr[i] = line[i].charAt(0);
                if(arr[i] == '^' || arr[i] == 'v' || arr[i] == '<' || arr[i] == '>') {
                    cration.guardPosX = list.size();
                    cration.guardPosY = i;
                }
            }
            list.add(arr);
        }
        
        var map = new char[list.size()][list.get(0).length];
        for(int i = 0; i < list.size(); i++) {
            map[i] = list.get(i);
        }
        cration.map = map;
        return cration;
    }
    
    private static char turnGuard(char guard) throws Exception {
        switch (guard) {
            case '^':
                return '>';
            case 'v':
                return '<';
            case '<':
                return '^';
            case '>':
                return 'v';
        }
        
        throw new Exception("Invalid guard");
    }
    
    private static guardMovment moveGuard(char[][] map, int guardPosX, int guardPosY) throws Exception {
        var newGuardMovment = new guardMovment();
        newGuardMovment.hasBeenThere = false;
        
        char guard = map[guardPosX][guardPosY];
        var newGuardPos = getNewGuardPos(guard, guardPosX, guardPosY);
        
        if(map[newGuardPos.guardPosX][newGuardPos.guardPosY] == 'X'){
            newGuardMovment.hasBeenThere = true;
        }
        
        
        if(map[newGuardPos.guardPosX][newGuardPos.guardPosY] == '#'){
            var newGuard = turnGuard(guard);
            newGuardMovment.hasBeenThere = true;
            newGuardMovment.newGuardPosX = guardPosX;
            newGuardMovment.newGuardPosY = guardPosY;
            newGuardMovment.newGuard = newGuard;
            map[guardPosX][guardPosY] = newGuard;
            return newGuardMovment;
        }

        map[guardPosX][guardPosY] = 'X';
        map[newGuardPos.guardPosX][newGuardPos.guardPosY] = guard;
        newGuardMovment.newGuardPosX = newGuardPos.guardPosX;
        newGuardMovment.newGuardPosY = newGuardPos.guardPosY;
        newGuardMovment.newGuard = guard;
        return newGuardMovment;
    }
    
    private static guardPos getNewGuardPos(char guard, int guardPosX, int guardPosY) {
        switch (guard) {
            case '^':
                return new guardPos(guardPosX - 1, guardPosY);
            case 'v':
                return new guardPos(guardPosX + 1, guardPosY);
            case '<':
                return new guardPos(guardPosX, guardPosY - 1);
            case '>':
                return new guardPos(guardPosX, guardPosY + 1);
        }
        throw new IllegalArgumentException("Invalid guard");
    }
    
    private static char[][] copyMap(char[][] map) {
        var newMap = new char[map.length][map[0].length];
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                newMap[i][j] = map[i][j];
            }
        }
        return newMap;
    }
    
    private static class guardMovment{
        boolean hasBeenThere;
        char newGuard;
        int newGuardPosX;
        int newGuardPosY;
    }
    
    private static class mapCreation{
        char[][] map;
        int guardPosX;
        int guardPosY;
    }
    
    private static class guardPos{
        int guardPosX;
        int guardPosY;

        public guardPos(int guardPosX, int guardPosY) {
            this.guardPosX = guardPosX;
            this.guardPosY = guardPosY;
        }
    }
}
