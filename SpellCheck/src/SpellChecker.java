import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class SpellChecker {
    private Set<String> dictionary;

    public SpellChecker(Set<String> dictionary){
        this.dictionary = dictionary;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File dictionaryFile = new File("dictionary/Dictionary.txt");
        Set<String> dictionary = FileUtils.fileToSet(dictionaryFile);
        SpellChecker spellChecker = new SpellChecker(dictionary);

        System.out.print("           Welcome!\nEnter words: ");
        Scanner s = new Scanner(System.in);
        String line;
        while ((line = s.nextLine()) != null){
            if (line.equals("q")) break;
            spellChecker.check(line);
            System.out.println("\nEnter words or 'q' for exit");
        }
        s.close();
    }

    public void check (String input){
        List<String> misSpelledWords = new ArrayList<>();
        String[] words = input.split("\\s+");

        for (String word : words){
            if (!dictionary.contains(word.toLowerCase())){
                misSpelledWords.add(word);
            }
        }

        System.out.print("After checking: ");
        PrintChecked(words, misSpelledWords);
    }

    public void PrintChecked(String[] words, List<String> missSpelledWords) {
        for (String word : words){
            if (missSpelledWords.contains(word)) System.out.println(word);
            else System.out.println(word+ "  <-");
        }

        System.out.println();
        if (missSpelledWords.isEmpty()) System.out.println("No mistakes, It's correct!");
        else {
            System.out.println("Possible corrections for misspelled words");
        for (String missword : missSpelledWords){
            System.out.println("-> "+ suggest(missword));
        }
        }
    }

    private int levenshteinDistance(String word1, String word2) {
        int a = word1.length();
        int b = word2.length();

        int[][] dp = new int[a + 1][b + 1];

        for (int i = 0; i <= a; i++){
            for (int j = 0; j<= b; j++){
                if (i == 0){
                    dp[i][j] = j;
                }else if(j == 0){
                    dp[i][j] = i;
                }else if (word1.charAt(i - 1) == word2.charAt(j - 1)){
                    dp[i][j] = dp[i - 1][j - 1];
                }else {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                }
            }
        }
        return dp[a][b];
    }

    private String suggest(String word) {
        int minDistance = Integer.MAX_VALUE;
        String suggestion = "";
        
        for (String dictWord : dictionary){
            int distance = levenshteinDistance(word, dictWord);

            if (distance < minDistance){
                minDistance = distance;
                suggestion = dictWord;
            }
        }
        return suggestion;
    }
}
