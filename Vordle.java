import java.util.*;
import java.io.*;
public class Vordle {
    final static String line = new String(new char[25]).replace('\0', '-');
    static String verifiedUserInput = "";
    static Scanner userInput = new Scanner(System.in);
    public static ArrayList<String> dictionary = new ArrayList<>();
    public static ArrayList<String> dictionaryExtra = new ArrayList<>();
    public static String wordToGuess = "";
    public static final String ANSI_RESET = "\u001B[0m"; // Color Reset
    public static final String ANSI_YELLOW = "\u001B[33m"; // Color Yellow
    public static final String ANSI_GREEN = "\u001B[32m"; // Color Green
    public static String startMenu = "Press"+ANSI_GREEN+" 1 to Start the Game"+ANSI_RESET+" | "+ANSI_YELLOW+"2 to Exit the Game"+ANSI_RESET+" | 3 for Mechanics & Info. -> ";
    
    public static void main(String[] args) throws IOException {
        System.out.println(line);
        System.out.print("Welcome to Vordle!\n");
        System.out.print(startMenu);

        int userMenuInput = userInput.nextInt();

        System.out.println(line);
        
        if (userMenuInput == 1)
                game(null);
        if (userMenuInput == 2)
            System.exit(0);
        if (userMenuInput == 3) {
            System.out.println("\nVordle: Java Wordle by Vault Air!\n\nHow it works: Enter a 5-letter word. \nFor 6 attempts, you should guess the random word. \nHints are given through input and will change color if:\n\n"+"SLEE"+ANSI_GREEN+"P"+ANSI_RESET+": P is in the right spot.\n"+ANSI_YELLOW+"Z"+ANSI_RESET+"OOMS: Z is in the word to be guessed, but is in the wrong spot.\n"+"JAVAS: Neither letters are in the random word.");
            main(args);
        }
    }

    public static void wordCall (String[] args) throws IOException {
        // from: https://gist.github.com/scholtes/94f3c0303ba6a7768b47583aff36654d
        // call the dictionary file using scanner
        Scanner dictionaryScanner = new Scanner(new FileReader("vordleWordDictionary.txt")).useDelimiter(",");
        
        // read all the dictionary content
        // assuming the dictionary is constantly updating; get the directory count
        // add all contents into array list, separated by commas
        while(dictionaryScanner.hasNext()) {
            dictionary.add(dictionaryScanner.next());
        }

        // randomly call a word from the dictionary with comma as a separator from above
        wordToGuess = dictionary.get((int)(Math.random()*dictionary.size()));

        Scanner dictionaryExtraScanner = new Scanner(new FileReader("vordleWordExtra.txt")).useDelimiter(",");
        ArrayList<String> dictionaryExtra = new ArrayList<>();

        while(dictionaryExtraScanner.hasNext()) {
            dictionary.add(dictionaryExtraScanner.next());
            game(null);
        }
    }
    
    public static void game(String[] args) throws IOException {
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

        // ask the user to input a word
        System.out.println("Guess the word of the randomness.");
        String userGuess = userInput.next();

        for (int v = 0; v < 5; v++) {
            if (userGuess.length() == 5 && (dictionary.contains(userGuess) || dictionaryExtra.contains(userGuess))) {
                verifiedUserInput = userGuess;
            }
            else {
                System.out.println("Please enter a valid 5-letter English word.");
                }
        }
    }
}