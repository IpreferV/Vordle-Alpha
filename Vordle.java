import java.util.*;
import java.io.*;
import java.time.*;
public class Vordle {
    final static String line = new String(new char[25]).replace('\0', '-');
    static Scanner userInput = new Scanner(System.in);
    static String wordToGuess = "";
    public static Instant timeStats = Instant.now(); // timer
    public static ArrayList<String> dictionary = new ArrayList<>(); // declare dictionary as an array list of all of its contents
    public static ArrayList<String> dictionaryExtra = new ArrayList<>(); // similar above
    public static final String ANSI_RESET = "\u001B[0m"; // Color Reset
    public static final String ANSI_YELLOW = "\u001B[33m"; // Color Yellow
    public static final String ANSI_GREEN = "\u001B[32m"; // Color Green
    public static String startMenu = "Press"+ANSI_GREEN+" 1 to Start the Game"+ANSI_RESET+" | "+ANSI_YELLOW+"2 to Exit the Game"+ANSI_RESET+" | 3 for Mechanics & Info"+" | 4 for User Stats."+"\n-> ";
    
    public static void main(String[] args) throws IOException {
        System.out.println(line);
        System.out.print("Welcome to Vordle!\n");
        System.out.print(startMenu);

        int userMenuInput = userInput.nextInt();
        
        // user menu choice
        if (userMenuInput == 1)
             wordCall(null);
        if (userMenuInput == 2)
            System.exit(0);
        if (userMenuInput == 3) {
            System.out.println(line);
            System.out.println("Vordle: Java Wordle(?) by IpreferV.");
            System.out.println("How it works: Enter a 5-letter word. \nFor 6 attempts, you should guess the random word.\n\nHints are given through input and will change color:");
            System.out.println("SLEE"+ANSI_GREEN+"P"+ANSI_RESET+": P is in the right spot.\n"+ANSI_YELLOW+"Z"+ANSI_RESET+"OOMS: Z is in the word to be guessed, but is in the wrong spot.\n"+"JAVAS: Neither letters are in the random word.");
            main(args);
        }
        if (userMenuInput == 4)
            userStatsMenu(null);
    }

    public static void wordCall (String[] args) throws IOException {
        // from: https://gist.github.com/scholtes/94f3c0303ba6a7768b47583aff36654d
        // call the dictionary file using scanner
        Scanner dictionaryScanner = new Scanner(new FileReader("vordleWordDictionary.txt")).useDelimiter(",");
        
        // read all the dictionary content
        // assuming the dictionary is constantly updating; get the directory count
        // add all contents into array list, separated by commas
        while(dictionaryScanner.hasNext()) {
            dictionary.add(dictionaryScanner.next().toUpperCase());
        }

        // assign a random word from dictionary array to be the final string word for user to guess
        wordToGuess = dictionary.get((int)(Math.random()*dictionary.size()));

        // access/call the extra dictionary file
        Scanner dictionaryExtraScanner = new Scanner(new FileReader("vordleWordExtra.txt")).useDelimiter(",");

        // read the extra dictionary contents
        while(dictionaryExtraScanner.hasNext()) {
            dictionary.add(dictionaryExtraScanner.next().toUpperCase());
        }

        game(null);
    }

    static int score = 0;
    
    public static void game(String[] args) throws IOException {

        System.out.println(line);

        System.out.println(wordToGuess); // comment can be removed to show the randomly called word
        
        System.out.println("Guess the word of the randomness.");

        int attempts = 6;
        
        Instant timeStart = Instant.now();

        while (attempts > 0) {
            if (attempts > 0) {
                String userGuess = userInput.next();
                System.out.print("-> "); // vanity purposes 

                // verify user input if not english or 5 char long
                if (userGuess.toUpperCase().length() != wordToGuess.toUpperCase().length() || !(dictionary.contains(userGuess.toUpperCase()) || dictionaryExtra.contains(userGuess.toUpperCase())))
                    System.out.print("Please enter a 5-letter English word.");
                else { // game proper
                    String verifiedUserGuess = userGuess; // i think i dont have to do this actually
                    for (int v = 0; v < 5; v++) {
                        char wtgPosition = wordToGuess.toUpperCase().charAt(v); // individual word to guess letters from word to guess word
                        char vuiPosition = verifiedUserGuess.toUpperCase().charAt(v); // individual user input letters from user input word

                        // user guess matches word
                        if (wordToGuess.equalsIgnoreCase(userGuess)) {
                            Instant timeStop = Instant.now();
                            Duration elapsedTime = Duration.between(timeStart, timeStop);
                            System.out.println("The word was "+ANSI_GREEN+wordToGuess.toUpperCase()+ANSI_RESET+"!");
                            System.out.println(ANSI_GREEN+"Congratulations."+ANSI_RESET);
                            System.out.println(ANSI_YELLOW+"\nElapsed time: "+elapsedTime.toSeconds()+" seconds."+ANSI_RESET);
                            score++;
                            endMenu(null); // go to end menu
                        }
                        if (vuiPosition == wtgPosition) // if letter position of user input matches with the word to guess letter positions
                            System.out.print(ANSI_GREEN+verifiedUserGuess.toUpperCase().charAt(v)+ANSI_RESET);
                        else if (wordToGuess.contains(String.valueOf(vuiPosition))) // if letter position of user is in the word to guess but in wrong position
                            System.out.print(ANSI_YELLOW+verifiedUserGuess.toUpperCase().charAt(v)+ANSI_RESET);
                        else // if user input is not in the word to guess
                            System.out.print(verifiedUserGuess.toUpperCase().charAt(v));
                    }
                    --attempts; // only decrement attempts if the user input is a valid english 5-letter word
                }
                System.out.println("\nAttempts left: "+attempts);
            }
            // no more attempts
            if (attempts == 0) {
                System.out.println("Out of attempts.");
                Instant timeStop = Instant.now();
                Duration elapsedTime = Duration.between(timeStart, timeStop);
                System.out.println(ANSI_YELLOW+"\nElapsed time: "+elapsedTime.toSeconds()+" seconds."+ANSI_RESET);

                endMenu(null); // go to end menu
            }
        }
    }

    public static void userStatsMenu(String[] args) throws IOException {
        System.out.println(line);
        System.out.println("Total correctly guessed words: "+score);
        Instant timeStop = Instant.now();
        Duration statsUserTime = Duration.between(timeStats, timeStop);
        System.out.println(ANSI_YELLOW+"Total game time: "+statsUserTime.toSeconds()+" seconds."+ANSI_RESET);

        System.out.println(ANSI_GREEN+"\n1"+ANSI_RESET+" Go back to Main Menu?");
        System.out.println(ANSI_YELLOW+"2 Reset stats?"+ANSI_RESET);
        int userStatsIn = userInput.nextInt();

        if (userStatsIn == 1)
            main(args);
        if (userStatsIn ==2 ) {
            score = 0;
            timeStats = Instant.now();
        }

        System.out.println("Stats cleared.");
        userStatsMenu(null);
    }

    public static void endMenu(String[] args) throws IOException {
        System.out.println(line);

        System.out.println("\nPress "+ANSI_GREEN+"1"+ANSI_RESET+" to "+ANSI_YELLOW+"try the same word"+ANSI_RESET+".");
        System.out.println("Press "+ANSI_GREEN+"2"+ANSI_RESET+" to "+ANSI_GREEN+"guess a new word"+ANSI_RESET+".");
        System.out.println("Press "+ANSI_YELLOW+"3"+ANSI_RESET+" to "+ANSI_YELLOW+"main menu"+ANSI_RESET+".");
        System.out.println("Press "+ANSI_YELLOW+"4"+ANSI_RESET+" to "+ANSI_YELLOW+"display statistics"+ANSI_RESET+".");

        System.out.print("-> ");

        int userMenuInput = userInput.nextInt();

        if (userMenuInput == 1)
            game(null);
        if (userMenuInput == 2)
            wordCall(null);
        if (userMenuInput == 3)
            main(args);;
        if (userMenuInput == 4)
            userStatsMenu(null);

    }
}