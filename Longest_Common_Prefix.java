import java.util.Scanner;

public class LongestPrefix {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] words = new String[5];

        // 1. Ask the user for 5 words
        System.out.println("Please enter 5 words:");
        for (int i = 0; i < 5; i++) {
            System.out.print("Word " + (i + 1) + ": ");
            words[i] = input.nextLine();
        }

        // 2. Find the prefix
        String prefix = findLongestPrefix(words);

        // 3. Display the result
        if (prefix.isEmpty()) {
            System.out.println("\nThere is no common prefix among these words.");
        } else {
            System.out.println("\nThe longest common prefix is: " + prefix);
        }
        
        input.close();
    }

    public static String findLongestPrefix(String[] words) {
        // If the list is empty, return nothing
        if (words == null || words.length == 0) return "";

        // Start by assuming the first word is the prefix
        String firstWord = words[0];

        // Go through each character of the first word
        for (int i = 0; i < firstWord.length(); i++) {
            char currentChar = firstWord.charAt(i);

            // Compare this character with the same position in the other 4 words
            for (int j = 1; j < words.length; j++) {
                // If we reach the end of another word, or characters don't match...
                if (i == words[j].length() || words[j].charAt(i) != currentChar) {
                    // Return everything we have matched up to this point
                    return firstWord.substring(0, i);
                }
            }
        }

        return firstWord;
    }
}
