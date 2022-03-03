package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Model for the Wordle game
 * 
 * @author Max Morhardt
 */
public class WordleModel {
	
	// File name for the word list
	private static final String WORD_LIST_NAME = "resources/word_list.txt";
	
	// Instance variables
	private List<String> words;
	private String secretWord;
	
	/**
	 * Constructor
	 */
	public WordleModel() {
		// Words from the word list
		words = scanList(WORD_LIST_NAME);
		// Secret word to guess
		secretWord = getRandomWord();
	}
	
	/**
	 * Reads a file into a list
	 * 
	 * @param file name
	 * @return list of strings
	 */
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
	
	/**
	 * Gets a random number within a range
	 * 
	 * @param min
	 * @param max
	 * @return int within a range
	 */
	private int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	/**
	 * Gets a random word from the word list
	 * 
	 * @return random word
	 */
	private String getRandomWord() {
		int index = getRandomNumber(0, words.size()-1);
		return words.get(index);
	}
	
	/**
	 * Validates a guess from the user based on if its in the word list
	 * 
	 * @return if the guess is in the word list
	 */
	public boolean isInWordList(String guess) {
		if (words.contains(guess)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks a guess and returns a string of the result
	 * 
	 * TODO
	 * Fix repeats with duplicate letters when a hit is already found and it marks yellow
	 * 
	 * @param guess
	 * @return string of corresponding hits, misses, and in the word values
	 */
	public String checkGuess(String guess) {
		String result = "";
		for (int i = 0; i < guess.length(); i++) {
			if (guess.charAt(i) == secretWord.charAt(i)) {
				// G (Green): Correct character and place
				result += "G";
			} else if (secretWord.contains(String.valueOf(guess.charAt(i)))) {
				// Y (Yellow): Correct character wrong place
				result += "Y";
			} else {
				// X (Gray): Character is not in the word
				result += "X";
			}
		}
		return result;
	}
	
}
