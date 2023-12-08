import java.util.*;
import java.io.*;
public class Vordle {
    final static String line = new String(new char[25]).replace('\0', '-');
    //static String verifiedUserInput = "";
    static Scanner userInput = new Scanner(System.in);
    static String wordToGuess = "";
    public static ArrayList<String> dictionary = new ArrayList<>();
    public static ArrayList<String> dictionaryExtra = new ArrayList<>();
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
        
        // user menu choice
        if (userMenuInput == 1)
             wordCall(null);
        if (userMenuInput == 2)
            System.exit(0);
        if (userMenuInput == 3) {
            System.out.println("\nVordle: Java Wordle by IpreferV!\n\nHow it works: Enter a 5-letter word. \nFor 6 attempts, you should guess the random word. \nHints are given through input and will change color if:\n\n"+"SLEE"+ANSI_GREEN+"P"+ANSI_RESET+": P is in the right spot.\n"+ANSI_YELLOW+"Z"+ANSI_RESET+"OOMS: Z is in the word to be guessed, but is in the wrong spot.\n"+"JAVAS: Neither letters are in the random word.");
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

        // proceed to the game method after reading the extra dictionary
        while(dictionaryExtraScanner.hasNext()) {
            dictionary.add(dictionaryExtraScanner.next());
        }

        game(null);
    }
    
    public static void game(String[] args) throws IOException {

        System.out.println(wordToGuess);
        System.out.println("Guess the word of the randomness.");
        String userGuess = userInput.next();
        
        // attempts

        for (int v = 0; v < 6; v++) {
            if (wordToGuess.equals(userGuess))
                endGame(null);
            else if (userGuess.length() == 5 && (dictionary.contains(userGuess) || dictionaryExtra.contains(userGuess))) {
                String verifiedUserInput = userGuess;
                for (int j = 0; j < 5;j++) {
                    System.out.print(ANSI_GREEN+verifiedUserInput.charAt(j)+ANSI_RESET);
                    continue;
                }
            }
            else {
                System.out.println("Please enter a valid 5-letter English word.");
                return;
            }
        }


        /*for (int j = 0; j < 5; ) {
            if (userGuess == wordToGuess) {
                endGame(null);
                break;
            }
            else if (userGuess.length() == 5 && (dictionary.contains(userGuess) || dictionaryExtra.contains(userGuess))) {
                verifiedUserInput = userGuess;
                System.out.print(ANSI_GREEN+verifiedUserInput.charAt(j)+ANSI_RESET);
                //game(null);
                j++;
            }
            else if (userGuess.length() != 5 || (dictionary.contains(userGuess) || dictionaryExtra.contains(userGuess))) {
                System.out.println("Please enter a valid 5-letter English word.");
                return;
            }
        }*/
    }

    public static void endGame(String[] args) throws IOException {
        System.out.println(line);
        System.out.println("The word was "+ANSI_GREEN+wordToGuess+ANSI_RESET+"!");
    }
}