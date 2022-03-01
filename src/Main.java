import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class that runs the program
 * This will be the model for the program when a GUI is added
 * 
 * @author Max Morhardt
 */
public class Main {
	
	// File name for the word list
	private static final String WORD_LIST_NAME = "word_list.txt";
	private static final int NUM_ATTEMPTS = 6;
	
	// Words from the word list
	private List<String> words;
	
	// Constructor
	public Main() {
		words = scanList(WORD_LIST_NAME);
	}
	
	// Scans the word list and adds all words to a list
	private List<String> scanList(String fileName) {
		List<String> list = new ArrayList<>();
		try {
			Scanner in = new Scanner(new File(fileName));
			while (in.hasNext()) {
				list.add(in.next());
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file was not found");
			e.printStackTrace();
		}
		assert list != null;
		return list;
	}
	
	// Gets a random number within a range
	private int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	// Gets a random word from a word list
	private String getRandomWord() {
		int index = getRandomNumber(0, words.size()-1);
		return words.get(index);
	}
	
	// Allows user to make a guess
	private String makeGuess() {
		String guess;
		Scanner in = new Scanner(System.in);
		System.out.print ("Enter a guess: ");
		guess = in.next();
		while (!words.contains(guess)) {
	        System.out.println("Not in the word list");
	        guess = in.next();
        }
		return guess.toLowerCase();
	}
	
	// Checks a guess and returns a string of the result
	// G (Green): Correct character and place
	// Y (Yellow): Correct character wrong place
	// X (Gray): Character is not in the word
	private String checkGuess(String guess, String wordToGuess) {
		String result = "";
		for (int i = 0; i < guess.length(); i++) {
			if (guess.charAt(i) == wordToGuess.charAt(i)) {
				result += "G";
			} else if (wordToGuess.contains(String.valueOf(guess.charAt(i)))) {
				result += "Y";
			} else {
				result += "X";
			}
		}
		return result;
	}
	
	// Runs the program
	private void runProgram() {
		String wordToGuess = getRandomWord();
		String guess = "";
		int numAttempts = NUM_ATTEMPTS;
		while (numAttempts > 0 || guess.equals(wordToGuess)) {
			System.out.println("Number of attempts remaining: " + numAttempts);
			guess = makeGuess();
			numAttempts--;
			String result = checkGuess(guess, wordToGuess);
			System.out.println(result + "\n");
		}
		
		if (guess.equals(wordToGuess)) {
			System.out.println("You have won!");
		} else {
			System.out.println("The word was: " + wordToGuess);
		}
	}
	
	// Main method
	public static void main(String[] args) {
		new Main().runProgram();
	}
	
}
