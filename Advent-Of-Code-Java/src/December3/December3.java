package December3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class December3 {
    public static void main(String[] args) {
        try {
            //First();
            //> 52874778
            //< 75587648
            //  75587648
            Second();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void First() throws FileNotFoundException {
        var input = new File("src/December3/Data/Input.txt");
        Scanner myReader = new Scanner(input);
        int sum = 0;

        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";
        
        Pattern pattern = Pattern.compile(regex);
        
        while (myReader.hasNextLine()) {
            var data = myReader.nextLine();
            
            Matcher matcher = pattern.matcher(data);
            
            while (matcher.find()) {
                //System.out.println("Found number: " + matcher.group());
                sum += multiply(matcher.group());
            }
        }

        System.out.println(sum);
    }
    
    public static void Second() throws FileNotFoundException {
        var input = new File("src/December3/Data/input2.txt");
        Scanner myReader = new Scanner(input);
        var goodLines = new ArrayList<String>();
        int sum = 0;

        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";

        Pattern pattern = Pattern.compile(regex);

        while (myReader.hasNextLine()) {
            var data = myReader.nextLine();
            
            var temp = data.split("don't\\(\\)");
            goodLines.add(temp[0]);
            
            for(String s : temp) {
                var x = s.split("do\\(\\)");
                if(x.length > 1) {
                    for (int i = 1; i < x.length; i++) {
                        goodLines.add(x[i]);
                    }
                }
            }
        }

        for(String s : goodLines) {
            Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {
                //System.out.println("Found number: " + matcher.group());
                sum += multiply(matcher.group());
            }
        }
        
        System.out.println(sum);
    }

    private static int multiply(String input) {
        input = input.replace("mul(", "");
        input = input.replace(")", "");
        var numbers = input.split(",");
        var first = Integer.parseInt(numbers[0]);
        var second = Integer.parseInt(numbers[1]);
        return first * second;
    }
}