package December2;

import javax.print.attribute.standard.OrientationRequested;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class December2 {
    public static void main(String[] args) {
        try {
            First();
            Second();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void First() throws FileNotFoundException {
        var input = new File("src/December2/Data/Input.txt");
        Scanner myReader = new Scanner(input);
        var safeSequences = 0;

        while (myReader.hasNext()) {
            var data = myReader.nextLine().split("\\s+");

            if(isSafe(new ArrayList<>(Arrays.asList(data)))) {
                safeSequences++;
            }
        }
        System.out.println(safeSequences);
    }

    public static void Second() throws FileNotFoundException {
        var input = new File("src/December2/Data/Input.txt");
        Scanner myReader = new Scanner(input);
        var safeSequences = 0;
        
        whileLoop :
        while (myReader.hasNext())  {
            var data = Arrays.stream(myReader.nextLine().split("\\s+")).toList();
            
            for(int i = 0; i < data.size(); i++) {
                var temp = new ArrayList<String>(data);
                temp.remove(i);
                if (isSafe(temp)) {
                    safeSequences++;
                    continue whileLoop;
                }
            }
        }

        System.out.println(safeSequences);
    }
    
    private static boolean isSafe(ArrayList<String> data) {
        var previous = Integer.valueOf(data.get(0));
        var increasing = true;
        if (previous > Integer.valueOf(data.get(1))) {
            increasing = false;
        }
        
        for (int i = 1; i < data.size(); i++) {
            var current = Integer.valueOf(data.get(i));
            if (!increasing && (previous - current >= 1 && previous - current <= 3)) {
                previous = current;
                continue;
            }

            if (increasing && (current - previous >= 1 && current - previous <= 3)) {
                previous = current;
                continue;
            }

            return false;
        }
        return true;
    }
}
