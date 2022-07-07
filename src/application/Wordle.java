package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class for the display of the Wordle game
 * 
 * @author Max Morhardt
 */
public class Wordle extends Application {
	
	// Path to the word list file
	private final String WORD_LIST_PATH = "resources/word_list.txt";

	// Game scene constants
	private final int FRAMES_PER_SECOND = 30;
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private final int SCENE_WIDTH = 700;
	private final int SCENE_HEIGHT = 775;
	
	// Game constants
	private final int WORD_LENGTH = 5;
	private final int NUM_GUESSES = 6;
	
	// Margin constants
	private final int GRID_BOTTOM_MARGIN = 30;
	private final int GRID_SPACING = 10;
	private final int LINE_BOTTOM_MARGIN = 30;
	private final int TITLE_TOP_MARGIN = 20;
	private final int TITLE_BOTTOM_MARGIN = 10;
	private final int WIN_LOSS_TOP_MARGIN = 30;
	private final int PLAY_AGAIN_TOP_MARGIN = 30;
	private final int KEYBOARD_MARGIN = 2;
	
	// Line stroke width constant
	private final int LINE_STROKE_WIDTH = 2;
	
	// Position constants
	private final Pos ROOT_POSITION = Pos.TOP_CENTER;
	private final Pos GRID_POSITION = Pos.CENTER;
	private final Pos KEYBOARD_ALIGNMENT = Pos.CENTER;
	
	// Text constants
	private final String STAGE_TITLE_TEXT = "Wordle Clone - Max Morhardt";
	private final String TITLE_NAME = "WORDLE";
	private final String PLAY_AGAIN_TEXT = "Play again?";
	private final String YOU_WON_TEXT = "You Won!";
	private final String ENTER_TEXT = "ENTER";
	private final String DELETE_TEXT = "<=";
	private final String FILE_SCAN_ERROR_TEXT = "The file was not found";
	private final char HIT_CHAR = 'G';
	private final char CONTAINS_CHAR = 'Y';
	private final char MISS_CHAR = 'X';
	
	// Color constants
	private final Color SCENE_COLOR = Color.rgb(18,18,19);
	private final Color LINE_COLOR = Color.rgb(54,54,56);
	private final Color HIT_COLOR = Color.rgb(83,141,78);
	private final Color CONTAINS_COLOR = Color.rgb(181,159,59);
	private final Color MISS_COLOR = Color.rgb(58,58,60);
	
	// Index constants
	private final int THIRD_ROW_INDEX = 2;
	private final int THIRD_ROW_LAST_KEY_INDEX = 6;
	
	// Game variables
	private List<String> words;
	private String secretWord;
	private int guessCount;
	private boolean won;
	private boolean lost;
	
	// View variables
	private VBox root;
	private WordleRectangle[][] gridWordleRectangles;
	private List<Character> guessCharacterList;
	private StyleHandler styleHandler;
	private ArrayList<ArrayList<Button>> keyboardButtons;
	
	/**
	 * Constructor
	 */
	public Wordle() {
		// Words from the word list
		words = scanList(WORD_LIST_PATH);
		// Secret word to guess
		secretWord = getRandomWord();
		// List of all the characters that are in the current guesses
		guessCharacterList = new ArrayList<>();
		// Number of guesses attempted
		guessCount = 0;
		// Variables for ending the game
		won = false;
		lost = false;
		// Root for the scene
		root = new VBox();
		// Wordle rectangles within the grid pane
		gridWordleRectangles = new WordleRectangle[WORD_LENGTH][NUM_GUESSES];
		// Handles the styles of most buttons and text
		styleHandler = new StyleHandler();
		// Contains all buttons on the keyboard
		keyboardButtons = new ArrayList<ArrayList<Button>>();
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
			System.out.println(FILE_SCAN_ERROR_TEXT);
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
	private boolean isInWordList(String guess) {
		if (words.contains(guess.toLowerCase())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks a guess and returns a string of the result
	 * 
	 * @param guess
	 * @return string of corresponding hits, misses, and in the word values
	 */
	private String checkGuess(String guess) {
		String result = "";
		for (int i = 0; i < guess.length(); i++) {
			if (guess.charAt(i) == secretWord.charAt(i)) {
				// G (Green): Correct character and place
				result += HIT_CHAR;
			} else if (secretWord.contains(String.valueOf(guess.charAt(i)))) {
				// Y (Yellow): Correct character wrong place
				result += CONTAINS_CHAR;
			} else {
				// X (Gray): Character is not in the word
				result += MISS_CHAR;
			}
		}
		return result;
	}

	/**
	 * Main entry point
	 */
	@Override
	public void start(Stage primaryStage) {
		// Create scene and display
		Scene scene = setupMainScene();
		System.out.println(secretWord);
		handleKeyboardInput(scene);
		primaryStage.setTitle(STAGE_TITLE_TEXT);
		primaryStage.setScene(scene);
		primaryStage.show();
		// Game loop
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(MILLISECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	/**
	 * Puts together all elements of the scene
	 * 
	 * @return Scene including all the elements in a VBox
	 */
	private Scene setupMainScene() {
		// Adds text for the title of the game
		Text title = setupTitle();
		// Line between the title and grid
		Line line = setupLine();
		// Sets up the grid for text and color to be displayed
		GridPane grid = setupGrid();
		// Creates GUI keyboard
		VBox keyboard = setupKeyboard();
		// Sets up root to align all elements
		root = new VBox();
		root.setBackground(Background.EMPTY);
		root.setAlignment(ROOT_POSITION);
		VBox.setMargin(title, new Insets(TITLE_TOP_MARGIN, 0, TITLE_BOTTOM_MARGIN, 0));
		VBox.setMargin(line, new Insets(0, 0, LINE_BOTTOM_MARGIN, 0));
		VBox.setMargin(line, new Insets(0, 0, LINE_BOTTOM_MARGIN, 0));
		VBox.setMargin(grid, new Insets(0, 0, GRID_BOTTOM_MARGIN, 0));
		root.getChildren().addAll(title, line, grid, keyboard);
		// Creates scene
		Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, SCENE_COLOR);
		return scene;
	}
	
	/**
	 * Creates the title in the GUI for the game
	 * 
	 * @return title
	 */
	private Text setupTitle() {
		Text title = new Text(TITLE_NAME);
		title.setStyle(styleHandler.TITLE_STYLE);
		return title;
	}
	
	/**
	 * Create a horizontal line for the GUI
	 * 
	 * @return line
	 */
	private Line setupLine() {
		Line line = new Line();
		line.setStartX(0);
		line.setEndX(SCENE_WIDTH);
		line.setStroke(LINE_COLOR);
		line.setStrokeWidth(LINE_STROKE_WIDTH);
		return line;
	}
	
	/**
	 * Creates a grid of rectangles for where characters will be placed
	 * 
	 * @return GridPane of rectangles
	 */
	private GridPane setupGrid() {
		// Grid plane to hold the rectangle and text stack panes 
		GridPane grid = new GridPane();
		for (int i = 0; i < gridWordleRectangles.length; i++) {
			for (int j = 0; j < gridWordleRectangles[i].length; j++) {
				WordleRectangle wordleRect = new WordleRectangle(i, j);
				StackPane rectWithText = wordleRect.getRectWithText();
				gridWordleRectangles[i][j] = wordleRect;
				grid.add(rectWithText, i, j);
			}
		}
		// Aligns the grid in the center and adds space between the rectangles
		grid.setAlignment(GRID_POSITION);
		grid.setHgap(GRID_SPACING);
		grid.setVgap(GRID_SPACING);
		return grid;
	}
	
	/**
	 * Creates the GUI keyboard
	 * 
	 * @return VBox with HBox's of buttons
	 */
	private VBox setupKeyboard() {
		// All the characters in a keyboard in order
		final Character[][] keysInOrder = new Character[][] {
			{'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'},
			{'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L'},
			{'Z', 'X', 'C', 'V', 'B', 'N', 'M'}
		};
		VBox keyboard = new VBox();
		for (int i = 0; i < keysInOrder.length; i++) {
			HBox keyboardRow = new HBox();
			ArrayList<Button> keys = new ArrayList<>();
			keyboardButtons.add(keys);
			// Add the enter button as soon as the loop reaches the 3rd row
			if (i == THIRD_ROW_INDEX) {
				Button enter = new Button(ENTER_TEXT);
				enter.setStyle(styleHandler.STARTING_KEY_STYLE);
				enter.setOnAction(e -> {
					if (guessCharacterList.size() == WORD_LENGTH) submitGuess();
				});
				keyboardButtons.get(i).add(enter);
				keyboardRow.getChildren().add(enter);
				HBox.setMargin(enter, new Insets(KEYBOARD_MARGIN, KEYBOARD_MARGIN, KEYBOARD_MARGIN, KEYBOARD_MARGIN));
			}
			//
			for (int j = 0; j < keysInOrder[i].length; j++) {
				// Creates a key for the keyboard
				Button key = new Button("" + keysInOrder[i][j]);
				key.setStyle(styleHandler.STARTING_KEY_STYLE);
				keyboardButtons.get(i).add(key);
				key.setOnAction(e -> {
					if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add(Character.toLowerCase(key.getText().charAt(0)));
				});
				keyboardRow.getChildren().add(key);
				HBox.setMargin(key, new Insets(KEYBOARD_MARGIN, KEYBOARD_MARGIN, KEYBOARD_MARGIN, KEYBOARD_MARGIN));
				// Adds the delete button to the end of the keyboard
				if (i == THIRD_ROW_INDEX && j == THIRD_ROW_LAST_KEY_INDEX) {
					Button delete = new Button(DELETE_TEXT);
					delete.setStyle(styleHandler.STARTING_KEY_STYLE);
					delete.setOnAction(e -> {
						int finalIndex = guessCharacterList.size()-1;
						if (guessCharacterList.size() > 0) guessCharacterList.remove(finalIndex);
					});
					keyboardButtons.get(i).add(delete);
					keyboardRow.getChildren().add(delete);
					HBox.setMargin(delete, new Insets(KEYBOARD_MARGIN, KEYBOARD_MARGIN, KEYBOARD_MARGIN, KEYBOARD_MARGIN));
				}
			}
			keyboardRow.setAlignment(KEYBOARD_ALIGNMENT);
			keyboard.getChildren().add(keyboardRow);
		}
		keyboard.setAlignment(KEYBOARD_ALIGNMENT);
		return keyboard;
	}
	
	/**
	 * Updates the letters on the GUI
	 */
	private void updateLetters() {
		for (int i = 0; i < WORD_LENGTH; i++) {
			WordleRectangle wordleRect = gridWordleRectangles[i][guessCount];
			if (guessCharacterList.size() - 1 < i) {
				wordleRect.setText("");
			} else {
				wordleRect.setText(("" + guessCharacterList.get(i)).toUpperCase());
			}
		}
	}
	
	/**
	 * Updates the colors of the rectangle
	 * 
	 * @param guess result
	 */
	private void updateRectangleColors(String guessResult) {
		for (int i = 0; i < WORD_LENGTH; i++) {
			WordleRectangle wordleRect = gridWordleRectangles[i][guessCount];
			if (guessResult.charAt(i) == HIT_CHAR) {
				wordleRect.setRectFill(HIT_COLOR);
			} else if (guessResult.charAt(i) == CONTAINS_CHAR) {
				wordleRect.setRectFill(CONTAINS_COLOR);
			} else {
				wordleRect.setRectFill(MISS_COLOR);
			}
		}
	}
	
	/**
	 * Updates the keyboard colors after a guess
	 * 
	 * @param guess result
	 */
	private void updateKeyboardColors(String guessResult) {
		// Goes through each key in the keyboard
		for (int i = 0; i < keyboardButtons.size(); i++) {
			for (int j = 0; j < keyboardButtons.get(i).size(); j++) {
				// Compares each key to the guess
				for (int k = 0; k < guessCharacterList.size(); k++) {
					char currChar = guessCharacterList.get(k);
					Button currButton = keyboardButtons.get(i).get(j);
					if (("" + currChar).equals(currButton.getText().toLowerCase())) {
						if (guessResult.charAt(k) == HIT_CHAR) {
							currButton.setStyle(styleHandler.HIT_KEY_STYLE);
						} else if (guessResult.charAt(k) == CONTAINS_CHAR) {
							currButton.setStyle(styleHandler.CONTAINS_KEY_STYLE);
						} else {
							currButton.setStyle(styleHandler.MIS_KEY_STYLE);
						}
					}
				}
			}
		}
	}
	
	/**
	 * What will be called in the game loop
	 * 
	 * @param elapsed time
	 */
	private void step(int elapsedTime) {
		updateLetters();
		if (won || lost) {
			handleEndGame();
		}
	}
	
	/**
	 * Submits a guess to the controller to see if its valid
	 */
	private void submitGuess() {
		// Convert list to string
		String guess = "";
		for (Character c : guessCharacterList) {
			guess += c;
		}
		// If the guess is valid update GUI with result and add a guess
		if (isInWordList(guess)) {
			String guessResult = checkGuess(guess);
			updateRectangleColors(guessResult);
			updateKeyboardColors(guessResult);
			// Check for end game
			if (guess.equals(secretWord)) {
				won = true;
			} else if (guessCount == NUM_GUESSES-1) { 
				lost = true;
			}
			// Dont increment if game is over
			if (!won && !lost) {
				guessCharacterList.clear();
				guessCount++;
			}
		}
	}
	
	/**
	 * Handles either a win or loss
	 */
	private void handleEndGame() {
		// Add the play again button
		Button playAgain = new Button(PLAY_AGAIN_TEXT);
		playAgain.setStyle(styleHandler.PLAY_AGAIN_STYLE);
		VBox.setMargin(playAgain, new Insets(PLAY_AGAIN_TOP_MARGIN, 0, 0, 0));
		// Add text for a win or loss
		Text endGameText = new Text();
		endGameText.setStyle(styleHandler.WIN_LOSS_STYLE);
		VBox.setMargin(endGameText, new Insets(WIN_LOSS_TOP_MARGIN, 0, 0, 0));
		// Set action for the play again button
		playAgain.setOnAction(value -> {
			playAgain();
			root.getChildren().remove(endGameText);
			root.getChildren().remove(playAgain);
		});
		if (won) {
			// Reset win status
			won = false;	
			// Add text incidicating a win
			endGameText.setText(YOU_WON_TEXT);
		} else if (lost) {
			// Reset lost status
			lost = false;
			
			// Indicate what the word was to the user
			endGameText.setText("The word was: " + this.secretWord);
		}
		root.getChildren().addAll(endGameText, playAgain);
	}
	
	/**
	 * Resets the grid to have starting values
	 */
	private void resetGrid() {
		for (int i = 0; i < gridWordleRectangles.length; i++) {
			for (int j = 0; j < gridWordleRectangles[i].length; j++) {
				gridWordleRectangles[i][j].reset();
			}
		}
	}
	
	/**
	 * Resets the keyboard to its starting values
	 */
	private void resetKeyboard() {
		for (int i = 0; i < keyboardButtons.size(); i++) {
			for (int j = 0; j < keyboardButtons.get(i).size(); j++) {
				Button curr = keyboardButtons.get(i).get(j);
				curr.setStyle(styleHandler.STARTING_KEY_STYLE);
			}
		}
	}
	
	/**
	 * Allows the user to reset and play again
	 */
	private void playAgain() {
		// Clear the list
		guessCharacterList.clear();
		// Reset guess count
		guessCount = 0;
		// Pick a new word to guess
		secretWord = getRandomWord();
		// Reset the grid
		resetGrid();
		// Resets the GUI keyboard
		resetKeyboard();
	}
	
	/**
	 * Handles all of the possible key events for the game
	 * 
	 * @param scene for the game
	 */
	private void handleKeyboardInput(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (!won || !lost) {
					switch (event.getCode()) {
					case A: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('a');
						break;
					case B: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('b');
						break;
					case C: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('c');
						break;
					case D: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('d');
						break;
					case E: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('e');
						break;
					case F: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('f');
						break;
					case G: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('g');
						break;
					case H: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('h');
						break;
					case I: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('i');
						break;
					case J: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('j');
						break;
					case K: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('k');
						break;
					case L: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('l');
						break;
					case M: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('m');
						break;
					case N: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('n');
						break;
					case O: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('o');
						break;
					case P: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('p');
						break;
					case Q: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('q');
						break;
					case R: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('r');
						break;
					case S: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('s');
						break;
					case T: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('t');
						break;
					case U: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('u');
						break;
					case V: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('v');
						break;
					case W: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('w');
						break;
					case X: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('x');
						break;
					case Y: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('y');
						break;
					case Z: 
						if (guessCharacterList.size() < WORD_LENGTH) guessCharacterList.add('z');
						break;
					case ENTER: 
						if (guessCharacterList.size() == WORD_LENGTH) submitGuess();
						break;
					case BACK_SPACE: 
						int finalIndex = guessCharacterList.size()-1;
						if (guessCharacterList.size() > 0) guessCharacterList.remove(finalIndex);
						break;
					}
				}
			}
		});
	}
	
	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
