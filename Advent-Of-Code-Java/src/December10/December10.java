package December10;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class December10 {
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
        var input = new File("src/December10/Data/input.txt");
        var myReader = new Scanner(input);
        
        TrailMap map = createMap(myReader);
        
        int trailheadsScore = 0;

        //for each starting point, find all trailheads
        for(Point point : map.startingPoints) {
            Set<Point> trailheads = new HashSet<>();
            Queue<Point> trail = new LinkedList<>();
            trail.add(point);

            //while there are points in the trail, try to expand it
            while(!trail.isEmpty()) {
                Point current = trail.poll();
                
                //if the current point is a trailhead, add it to the set
                if(map.map[current.y][current.x] == 9) {
                    trailheads.add(current);
                    continue;
                }
                
                //find the next valid points and add them to the trail
                List<Point> nextPoints = expandPath(map.map, current);
                trail.addAll(nextPoints);
            }
            trailheadsScore += trailheads.size();
        }
        System.out.println(trailheadsScore);
    }

    public static void Second() throws Exception {
        var input = new File("src/December10/Data/input.txt");
        var myReader = new Scanner(input);

        TrailMap map = createMap(myReader);

        int trailheadsScore = 0;
        
        //for each starting point, find all trailheads
        for(Point point : map.startingPoints) {
            Queue<Point> trail = new LinkedList<>();
            trail.add(point);

            //while there are points in the trail, try to expand it
            while(!trail.isEmpty()) {
                Point current = trail.poll();
                
                //if the current point is a trailhead, increase the score
                if(map.map[current.y][current.x] == 9) {
                    trailheadsScore++;
                    continue;
                }
                
                //find the next valid points and add them to the trail
                List<Point> nextPoints = expandPath(map.map, current);
                trail.addAll(nextPoints);
            }
        }
        System.out.println(trailheadsScore);
    }
    
    //Helper class
    private static class TrailMap {
        private int[][] map;
        private List<Point> startingPoints;
    }
    
    private static List<Point> expandPath(int[][] map, Point position) {
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
    
    //create a map from input, return map, and starting points
    //it's a bit unnecessary to convert the list to an array, 
    //but I like 2d arrays when working with coordinates
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
}
