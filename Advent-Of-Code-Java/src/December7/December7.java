package December7;

import java.io.File;
import java.util.Scanner;

public class December7 {
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
        var input = new File("src/December7/Data/input.txt");
        Scanner myReader = new Scanner(input);
    
        long sumOfPossibleEquations = 0;
        
        while (myReader.hasNextLine()) {
            sumOfPossibleEquations += solve(myReader.nextLine(), "+ *");
        }
        System.out.println(sumOfPossibleEquations);
    }

    public static void Second() throws Exception {
        var input = new File("src/December7/Data/input.txt");
        Scanner myReader = new Scanner(input);

        long sumOfPossibleEquations = 0;

        while (myReader.hasNextLine()) {
            sumOfPossibleEquations += solve(myReader.nextLine(), "+ * ||");
        }
        System.out.println(sumOfPossibleEquations);
    }
    
    private static long solve(String input, String equations) {
        var result = Long.parseLong(input.split(":")[0]);
        var equation = input.split(":")[1].trim().split(" ");
        var numbers = new int[equation.length];
        for (int i = 0; i < equation.length; i++) {
            numbers[i] = Integer.parseInt(equation[i]);
        }

        if (isPossible(result, numbers, 0, numbers[0], equations)) {
            return result;
        }
        return 0;
    }

    private static long tryOperation(long a, long b, String operation) {
        switch (operation) {
            case "+":
                return a + b;
            case "*":
                return a * b;
            case "||":
                return Long.parseLong(a + "" + b);
            default:
                throw new IllegalArgumentException("Invalid operation");
        }
    }

    private static boolean isPossible(long result, int[] numbers, int index, long currentResult, String operations) {
        if (result < currentResult) {
            return false;
        }

        if (index == numbers.length - 1) {
            return currentResult == result;
        }
        
        for (String operation : operations.trim().split(" ")) {
            if (isPossible(result, numbers, index + 1, tryOperation(currentResult, numbers[index + 1], operation), operations)) {
                return true;
            }
        }
        
        return false;
    }
}
