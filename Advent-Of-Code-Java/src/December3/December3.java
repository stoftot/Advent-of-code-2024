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
        var input = new File("src/December3/Data/input.txt");
        Scanner myReader = new Scanner(input);
        StringBuilder corruptedMemory = new StringBuilder();
        
        // Read the entire input
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            corruptedMemory.append(data);
        }
        myReader.close();

        int sum = 0;
        boolean enabled = true;

        // Regex pattern to match mul(X,Y), do(), or don't()
        String regex = "(mul\\((\\d{1,3}),(\\d{1,3})\\))" + // Group 1: mul(X,Y)
                "|(do\\(\\))" +                      // Group 4: do()
                "|(don't\\(\\))";                    // Group 5: don't()

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(corruptedMemory.toString());
        
        while (matcher.find()) {
            if (matcher.group(1) != null) { // Found mul(X,Y)
                if (enabled) {
                    int x = Integer.parseInt(matcher.group(2));
                    int y = Integer.parseInt(matcher.group(3));
                    sum += x * y;
                }
            } else if (matcher.group(4) != null) { // Found do()
                enabled = true;
            } else if (matcher.group(5) != null) { // Found don't()
                enabled = false;
            }
        }

        System.out.println("Total Sum: " + sum);
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
