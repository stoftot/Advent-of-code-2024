package December4;

import com.sun.source.doctree.ValueTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class December4 {
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
        var input = new File("src/December4/Data/input.txt");
        Scanner myReader = new Scanner(input);
        var lines = new ArrayList<String>();

        while (myReader.hasNextLine()) {
            var data = myReader.nextLine();
            lines.add(data);
        }

        var width = lines.get(0).length();
        var height = lines.size();

        var data = new char[width][height];

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                data[x][y] = lines.get(y).charAt(x);
            }
        }
        var amountOfXmasFound = 0;
        //check horizontally forward
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width - 3; x++) {
                if (data[x][y] == 'X' && data[x + 1][y] == 'M' && data[x + 2][y] == 'A' && data[x + 3][y] == 'S') {
                    amountOfXmasFound++;
                }
            }
        }

        //check horizontally backward
        for (int y = 0; y < height; y++) {
            for (int x = width - 1; x >= 3; x--) {
                if (data[x][y] == 'X' && data[x - 1][y] == 'M' && data[x - 2][y] == 'A' && data[x - 3][y] == 'S') {
                    amountOfXmasFound++;
                }
            }
        }

        //check vertically forward
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height - 3; y++) {
                if (data[x][y] == 'X' && data[x][y + 1] == 'M' && data[x][y + 2] == 'A' && data[x][y + 3] == 'S') {
                    amountOfXmasFound++;
                }
            }
        }

        //check vertically backward
        for (int x = 0; x < width; x++) {
            for (int y = height - 1; y >= 3; y--) {
                if (data[x][y] == 'X' && data[x][y - 1] == 'M' && data[x][y - 2] == 'A' && data[x][y - 3] == 'S') {
                    amountOfXmasFound++;
                }
            }
        }

        //check diagonally right forward
        for (int x = 0; x < width - 3; x++) {
            for (int y = 0; y < height - 3; y++) {
                if (data[x][y] == 'X' && data[x + 1][y + 1] == 'M' && data[x + 2][y + 2] == 'A' && data[x + 3][y + 3] == 'S') {
                    amountOfXmasFound++;
                }
            }
        }

        //check diagonally right backward
        for (int x = width - 1; x >= 3; x--) {
            for (int y = 0; y < height - 3; y++) {
                if (data[x][y] == 'X' && data[x - 1][y + 1] == 'M' && data[x - 2][y + 2] == 'A' && data[x - 3][y + 3] == 'S') {
                    amountOfXmasFound++;
                }
            }
        }

        //check diagonally left forward
        for (int x = width - 1; x >= 3; x--) {
            for (int y = height - 1; y >= 3; y--) {
                if (data[x][y] == 'X' && data[x - 1][y - 1] == 'M' && data[x - 2][y - 2] == 'A' && data[x - 3][y - 3] == 'S') {
                    amountOfXmasFound++;
                }
            }
        }

        //check diagonally left backward
        for (int x = 0; x < width - 3; x++) {
            for (int y = height - 1; y >= 3; y--) {
                if (data[x][y] == 'X' && data[x + 1][y - 1] == 'M' && data[x + 2][y - 2] == 'A' && data[x + 3][y - 3] == 'S') {
                    amountOfXmasFound++;
                }
            }
        }

        System.out.println(amountOfXmasFound);
    }

    public static void Second() throws FileNotFoundException {
        var input = new File("src/December4/Data/input.txt");
        Scanner myReader = new Scanner(input);
        var lines = new ArrayList<String>();

        while (myReader.hasNextLine()) {
            var data = myReader.nextLine();
            lines.add(data);
        }

        var width = lines.get(0).length();
        var height = lines.size();

        var data = new char[width][height];

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                data[x][y] = lines.get(y).charAt(x);
            }
        }
        var amountOfXmasFound = 0;

        for (int y = 1; y < height - 1; y++) {
            for (int x = 0; x < width - 2; x++) {
                if (data[x + 1][y] != 'A') continue;
                if ((data[x][y - 1] == 'M' && data[x + 2][y + 1] == 'S')
                        || (data[x][y - 1] == 'S' && data[x + 2][y + 1] == 'M')) {
                    if ((data[x + 2][y - 1] == 'M' && data[x][y + 1] == 'S')
                            || (data[x + 2][y - 1] == 'S' && data[x][y + 1] == 'M')) {
                        amountOfXmasFound++;
                    }
                }
            }
        }
        
        System.out.println(amountOfXmasFound);
    }
}
