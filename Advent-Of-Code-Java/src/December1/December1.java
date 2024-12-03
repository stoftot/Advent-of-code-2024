package December1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class December1 {
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
        var input = new File("src/December1/Data/Input.txt");
        Scanner myReader = new Scanner(input);
        var leftCordinates = new ArrayList<Integer>();
        var rightCordinates = new ArrayList<Integer>();
        var distanceSum = 0;
        
        while (myReader.hasNextLine()) {
            var data = myReader.nextLine().split("\\s+");
            var left = data[0];
            var right = data[1];
            leftCordinates.add(Integer.valueOf(left));
            rightCordinates.add(Integer.valueOf(right));
        }

        Collections.sort(leftCordinates);
        Collections.sort(rightCordinates);
        
        for(int i = 0; i < leftCordinates.size(); i++) {
            distanceSum += Math.abs(leftCordinates.get(i) - rightCordinates.get(i));
        }
        
        System.out.println(distanceSum);
    }
    
    public static void Second() throws FileNotFoundException {
        var input = new File("src/December1/Data/Input.txt");
        Scanner myReader = new Scanner(input);
        var leftCordinates = new ArrayList<Integer>();
        var rightCordinates = new ArrayList<Integer>();
        var consistencyScore = 0;
        
        while (myReader.hasNextLine()) {
            var data = myReader.nextLine().split("\\s+");
            var left = data[0];
            var right = data[1];
            leftCordinates.add(Integer.valueOf(left));
            rightCordinates.add(Integer.valueOf(right));
        }
        
        for(int i : leftCordinates) {
            var occurrences = Collections.frequency(rightCordinates, i);
            consistencyScore += occurrences*i;
        }

        System.out.println(consistencyScore);
    }
}
