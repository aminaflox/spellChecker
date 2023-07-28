import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Struct;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class FileUtils {

    public static Set<String> fileToSet(File file) throws FileNotFoundException {
        Set<String> wordSet = new HashSet<>();
        try(Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()){
                String word = scanner.nextLine().trim().toLowerCase();
                wordSet.add(word);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return wordSet;
    }
}
