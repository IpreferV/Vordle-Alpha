import java.util.*;
import java.io.*;
public class Vordle {
    public static void main(String[] args) throws IOException {

        /*
         * Concept: Wordle in Java
         * 1. Start by calling the dictionary file
         * 2. Get a random word from the dictionary file
         * 3. Split the word characters to array indices
         * 5. Ask the user to input a word
         * 6. Split the user input into array indices
         * 7. Verify if:
         * 7.1. The word is an english word
         * 7.2. The user input word matches the randomly called word
         * 8. End if the user matched the randomly called word.
         */

        String vordleWord = "";
        int dictionaryCount = 0;
        
        // from: https://gist.github.com/scholtes/94f3c0303ba6a7768b47583aff36654d
        // call the dictionary file
        Scanner vordleDictionaryRaw = new Scanner(new FileReader("vordleWordDictionary.txt")).useDelimiter(",");

        // read all the dictionary content
        // assuming the dictionary is constantly updating; get the directory count
        while(vordleDictionaryRaw.hasNext()) {
            vordleDictionaryRaw.next();
            dictionaryCount++;
        }

        // randomly call a word from the dictionary with comma as a separator
        vordleDictionaryRaw = new Scanner(new FileReader("vordleWordDictionary.txt")).useDelimiter(",");
        for (int v = 0; v < (int)(Math.random()*dictionaryCount); v++) {
            vordleWord = vordleDictionaryRaw.next();
        }
        
        // split the called word into the vordle array
        char[] vordleWordArray = vordleWord.toCharArray();

        System.out.println(vordleWordArray);
        System.out.println(vordleWordArray[4]);

        // ask the user to input a word
        System.out.println("Guess the word of the day.");
        Scanner userInput = new Scanner(System.in);
        String userGuess = userInput.next();

        // split the user input into the array indices
        char[] vordleGuess = new char[vordleWord.length()];
        if (userGuess.length() == 5)
            vordleGuess = userGuess.toCharArray();
        else
            System.out.println("Please enter a 5-letter word.");

        System.out.println(vordleGuess[1]);

        for (int v = 0; v < vordleWord.length(); v++) {
            if (vordleGuess[v] == vordleWordArray[v]) {
                System.out.println("letter at "+v+" is right!");
            }
            else System.out.println("letter at "+v+" is wrong.");
        }

        userInput.close();
    }
}