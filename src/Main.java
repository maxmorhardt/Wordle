import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	// Constants
	private static final String WORD_LIST_NAME = "word_list.txt";
	
	// Instance variables
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
		} catch (FileNotFoundException e) {
			System.out.println("The file was not found");
			e.printStackTrace();
		}
	}
	
	// Prints all the words (for testing)
	private void printWords() {
		for (int i = 0; i < words.size(); i++) {
			System.out.println((i+1) + ": " + words.get(i));
		}
	}
	
	// Main method
	public static void main(String[] args) {
		Main m = new Main();
		m.scanList(WORD_LIST_NAME);
		m.printWords();
	}
}
