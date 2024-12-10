package December10;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class December10 {
    public static void main(String[] args) {
        try {
            First();
            //Second();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void First() throws Exception {
        var input = new File("src/December10/Data/test.txt");
        var myReader = new Scanner(input);
        TrailMap map = createMap(myReader);
        
        int trailheadsScore = 0;
        
        for(Point point : map.startingPoints) {
            Queue<Point> trail = new LinkedList<>();
            trail.add(point);
            
            while(!trail.isEmpty()) {
                Point current = trail.poll();
                
                if(map.map[current.y][current.x] == 9) {
                    trailheadsScore++;
                    continue;
                }
                
                List<Point> nextPoints = hike(map.map, current);
                trail.addAll(nextPoints);
            }
        }
        System.out.println(trailheadsScore);
    }

    public static void Second() throws Exception {
        var input = new File("src/December10/Data/test.txt");
        var myReader = new Scanner(input);
    }
    
    private static List<Point> hike(int[][] map, Point position) {
        var points = new ArrayList<Point>();
        int value = map[position.y][position.x];
        
        //try left
        if (position.x - 1 >= 0 && map[position.y][position.x - 1] == value + 1) {
            points.add(new Point(position.x - 1, position.y));
        }
        
        //try right
        if (position.x + 1 < map[0].length && map[position.y][position.x + 1] == value + 1) {
            points.add(new Point(position.x + 1, position.y));
        }
        
        //try up
        if(position.y - 1 >= 0 && map[position.y - 1][position.x] == value + 1) {
            points.add(new Point(position.x, position.y - 1));
        }
        
        //try down
        if (position.y + 1 < map.length && map[position.y + 1][position.x] == value + 1) {
            points.add(new Point(position.x, position.y + 1));
        }
        
        return points;
    }
    
    private static TrailMap createMap(Scanner scanner) {
        var map = new TrailMap();
        var lines = new ArrayList<String>();
        while(scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        
        map.map = new int[lines.size()][lines.get(0).length()];
        map.startingPoints = new ArrayList<Point>();
        
        for(int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split("");
            for(int j = 0; j < parts.length; j++) {
                int value = Integer.parseInt(parts[j]);
                if(value == 0) {
                    map.startingPoints.add(new Point(j, i));
                }
                map.map[i][j] = value;
            }
        }
        
        return map;
    }
    
    private static class TrailMap {
        private int[][] map;
        private List<Point> startingPoints;
    }
}
