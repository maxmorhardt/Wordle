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
	
	// Word list
	private List<String> words;
	
	// Constructor
	public Main() {
		words = new ArrayList<>();
	}
	
	// Scans the word list and adds all words to a list
	private void scanList(String fileName) {
		try {
			Scanner in = new Scanner(new File(fileName));
			while (in.hasNext()) {
				words.add(in.next());
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file was not found");
			e.printStackTrace();
		}
	}
	
	// Gets a random number within a range
	private int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	// Gets a random word from a word list
	private String getRandomWord(List<String> words) {
		int index = getRandomNumber(0, words.size()-1);
		return words.get(index);
	}
	
	// Allows user to make a guess
	private String makeGuess() {
		String guess = "";
		do {
			Scanner in = new Scanner(System.in);
			guess = in.next();
		} while (!words.contains(guess));
		return guess;
	}
	
	// Runs the program
	private void runProgram() {
		// TODO
		return;
	}
	
	// Main method
	public static void main(String[] args) {
	}
	
}
