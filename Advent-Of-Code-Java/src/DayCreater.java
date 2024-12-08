import java.io.File;
import java.time.LocalDate;

public class DayCreater {
    public static void main(String[] args) {
        try {
            var today = LocalDate.now().getDayOfMonth();
            //create a new folder for the day
            var folderName = "src/December" + today;
            var folder = new File(folderName);
            folder.mkdir();

            //create data folder
            var dataFolder = new File(folderName + "/Data");
            dataFolder.mkdir();
            
            //create test file
            var testFile = new File(folderName + "/Data/test.txt");
            testFile.createNewFile();
            
            //create main file
            var mainFile = new File(folderName + "/December"+today+".java");
            mainFile.createNewFile();
            var mainWriter = new java.io.FileWriter(mainFile);
            mainWriter.write(String.format("""
                package December%1$d;
                
                import java.io.File;
                import java.util.*;
                
                public class December%1$d {
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
                        var input = new File("src/December%1$d/Data/test.txt");
                        var myReader = new Scanner(input);
                    }
                    
                    public static void Second() throws Exception {
                        var input = new File("src/December%1$d/Data/test.txt");
                        var myReader = new Scanner(input);
                    }
                }
                    """, today));
            mainWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
