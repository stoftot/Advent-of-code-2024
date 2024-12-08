package December8;

import java.awt.*;
import java.io.File;
import java.lang.invoke.VarHandle;
import java.util.*;

public class December8 {
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
        var input = new File("src/December8/Data/input.txt");
        var myReader = new Scanner(input);

        var antinodesSet = new HashSet<Point>();
        var map = createMap(myReader);

        for (var frequencySet : map.frequencies.keySet()) {
            var values = map.frequencies.get(frequencySet);

            for (int i = 0; i < values.size() - 1; i++) {
                for (int j = i + 1; j < values.size(); j++) {
                    var point2 = values.get(i);
                    var point3 = values.get(j);

                    var point4 = new Point(2 * point3.x - point2.x, 2 * point3.y - point2.y);
                    var point1 = new Point(2 * point2.x - point3.x, 2 * point2.y - point3.y);

                    if (isInMap(map, point4))
                        antinodesSet.add(point4);
                    if (isInMap(map, point1))
                        antinodesSet.add(point1);
                }
            }
        }

        System.out.println(antinodesSet.size());
    }

    public static void Second() throws Exception {
        var input = new File("src/December8/Data/input.txt");
        var myReader = new Scanner(input);

        var antinodesSet = new HashSet<Point>();
        var map = createMap(myReader);

        for (var frequencySet : map.frequencies.keySet()) {
            var values = map.frequencies.get(frequencySet);

            for (int i = 0; i < values.size() - 1; i++) {
                for (int j = i + 1; j < values.size(); j++) {
                    var point2 = values.get(i);
                    var point3 = values.get(j);

                    var point4 = new Point(2 * point3.x - point2.x, 2 * point3.y - point2.y);
                    var point1 = new Point(2 * point2.x - point3.x, 2 * point2.y - point3.y);

                    while (isInMap(map, point4)) {
                        antinodesSet.add(point4);
                        var newPoint4 = new Point(2 * point4.x - point3.x, 2 * point4.y - point3.y);
                        point3 = point4;
                        point4 = newPoint4;
                    }

                    while (isInMap(map, point1)) {
                        antinodesSet.add(point1);
                        var newPoint1 = new Point(2 * point1.x - point2.x, 2 * point1.y - point2.y);
                        point2 = point1;
                        point1 = newPoint1;
                    }
                }
            }
        }
        
        for(var Enrty : map.frequencies.entrySet()){
            if(Enrty.getValue().size() > 1){
                antinodesSet.addAll(Enrty.getValue());
            }
        }
        
        System.out.println(antinodesSet.size());
    }


    private static class Map {
        private HashMap<Character, ArrayList<Point>> frequencies;
        private int width;
        private int height;

    }

    private static Map createMap(Scanner reader) {
        var map = new Map();
        var height = 0;
        map.frequencies = new HashMap<>();
        while (reader.hasNextLine()) {
            var line = reader.nextLine().toCharArray();
            height++;
            map.width = line.length;
            for (int i = 0; i < line.length; i++) {
                var c = line[i];
                if (c != '.') {
                    if (!map.frequencies.containsKey(c)) {
                        map.frequencies.put(c, new ArrayList<>());
                    }
                    map.frequencies.get(c).add(new Point(i, height - 1));
                }
            }
        }
        map.height = height;

        return map;
    }

    private static boolean isInMap(Map map, Point point) {
        return point.x >= 0 && point.x < map.width && point.y >= 0 && point.y < map.height;
    }
}
