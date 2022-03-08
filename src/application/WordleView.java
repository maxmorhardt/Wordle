package application;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
public class WordleView {

	// Game scene constants
	private final int FRAMES_PER_SECOND = 30;
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private final int SCENE_WIDTH = 700;
	private final int SCENE_HEIGHT = 775;
	
	// Game constants
	private final int WORD_LENGTH = 5;
	private final int NUM_GUESSES = 6;
	
	// Margin constants
	private final int GRID_MARGIN = 10;
	private final int LINE_STROKE_WIDTH = 2;
	private final int LINE_BOTTOM_MARGIN = 30;
	private final int TITLE_TOP_MARGIN = 20;
	private final int TITLE_BOTTOM_MARGIN = 10;
	private final int WIN_LOSS_TOP_MARGIN = 30;
	private final int PLAY_AGAIN_TOP_MARGIN = 30;
	
	// Position constants
	private final Pos ROOT_POSITION = Pos.TOP_CENTER;
	private final Pos GRID_POSITION = Pos.CENTER;
	
	// Text constants
	private final String STAGE_TITLE_TEXT = "Wordle Clone - Max Morhardt";
	private final String TITLE_NAME = "WORDLE";
	private final String PLAY_AGAIN_TEXT = "Play again?";
	private final String YOU_WON_TEXT = "You Won!";
	private final char HIT_CHAR = 'G';
	private final char CONTAINS_CHAR = 'Y';
	
	// Color constants
	private final Color SCENE_COLOR = Color.rgb(18,18,19);
	private final Color LINE_COLOR = Color.rgb(54,54,56);
	private final Color HIT_COLOR = Color.rgb(83,141,78);
	private final Color CONTAINS_COLOR = Color.rgb(181,159,59);
	private final Color MISS_COLOR = Color.rgb(58,58,60);
	

	// Variables
	private VBox root;
	private WordleRectangle[][] gridWordleRectangles;
	private List<Character> guessCharacterList;
	private int guessCount;
	private WordleController controller;
	private boolean won;
	private boolean lost;
	private StyleHandler styleHandler;
	private ArrayList<ArrayList<Button>> keyboardButtons;
	
	/**
	 * Constructor
	 */
	public WordleView() {
		// Root for the scene
		root = new VBox();
		// Wordle rectangles within the grid pane
		gridWordleRectangles = new WordleRectangle[WORD_LENGTH][NUM_GUESSES];
		// List of all the characters that are in the current guesses
		guessCharacterList = new ArrayList<>();
		// Number of guesses attempted
		guessCount = 0;
		// Controller
		controller = new WordleController();
		// Variables for ending the game
		won = false;
		lost = false;
		
		styleHandler = new StyleHandler();
		keyboardButtons = new ArrayList<ArrayList<Button>>();
	}

	/**
	 * Main entry point
	 */
	public void start(Stage primaryStage) {
		// Create scene and display
		//System.out.println(controller.getSecretWord());
		Scene scene = setupMainScene();
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
		
		VBox keyboard = setupKeyboard();
		
		// Sets up root to align all elements
		root = new VBox();
		root.setBackground(Background.EMPTY);
		root.setAlignment(ROOT_POSITION);
		VBox.setMargin(title, new Insets(TITLE_TOP_MARGIN, 0, TITLE_BOTTOM_MARGIN, 0));
		VBox.setMargin(line, new Insets(0, 0, LINE_BOTTOM_MARGIN, 0));
		VBox.setMargin(grid, new Insets(0, 0, 30, 0));
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
		title.setStyle(styleHandler.getTitleStyle());
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
		grid.setHgap(GRID_MARGIN);
		grid.setVgap(GRID_MARGIN);
		return grid;
	}
	
	private VBox setupKeyboard() {
		Character[][] keysInOrder = new Character[][] {
			{'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'},
			{'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L'},
			{'Z', 'X', 'C', 'V', 'B', 'N', 'M'}
		};
		VBox keyboard = new VBox();
		for (int i = 0; i < keysInOrder.length; i++) {
			HBox keyboardRow = new HBox();
			ArrayList<Button> keys = new ArrayList<>();
			keyboardButtons.add(keys);
			if (i == 2) {
				Button enter = new Button("ENTER");
				enter.setStyle(styleHandler.getStartingKeyStyle());
				enter.setOnAction(e -> {
					if (guessCharacterList.size() == 5) submitGuess();
				});
				keyboardButtons.get(i).add(enter);
				keyboardRow.getChildren().add(enter);
				HBox.setMargin(enter, new Insets(2, 2, 2, 2));
			}
			for (int j = 0; j < keysInOrder[i].length; j++) {
				Button key = new Button("" + keysInOrder[i][j]);
				key.setStyle(styleHandler.getStartingKeyStyle());
				keyboardButtons.get(i).add(key);
				key.setOnAction(e -> {
					if (guessCharacterList.size() < 5) guessCharacterList.add(Character.toLowerCase(key.getText().charAt(0)));
				});
				keyboardRow.getChildren().add(key);
				HBox.setMargin(key, new Insets(2, 2, 2, 2));
				if (i == 2 && j == 6) {
					Button delete = new Button("<=");
					delete.setStyle(styleHandler.getStartingKeyStyle());
					delete.setOnAction(e -> {
						int finalIndex = guessCharacterList.size()-1;
						if (guessCharacterList.size() > 0) guessCharacterList.remove(finalIndex);
					});
					keyboardButtons.get(i).add(delete);
					keyboardRow.getChildren().add(delete);
					HBox.setMargin(delete, new Insets(2, 2, 2, 2));
				}
			}
			keyboardRow.setAlignment(Pos.CENTER);
			keyboard.getChildren().add(keyboardRow);
		}
		keyboard.setAlignment(Pos.CENTER);
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
	
	// This could be a lot more efficient
	private void updateKeyboardColors(String guessResult) {
		for (int i = 0; i < keyboardButtons.size(); i++) {
			for (int j = 0; j < keyboardButtons.get(i).size(); j++) {
				for (int k = 0; k < guessCharacterList.size(); k++) {
					char curr = guessCharacterList.get(k);
					Button currButton = keyboardButtons.get(i).get(j);
					if (("" + curr).equals(currButton.getText().toLowerCase())) {
						if (guessResult.charAt(k) == HIT_CHAR) {
							currButton.setStyle(styleHandler.getHitKeyStyle());
						} else if (guessResult.charAt(k) == CONTAINS_CHAR) {
							currButton.setStyle(styleHandler.getContainsKeyStyle());
						} else {
							currButton.setStyle(styleHandler.getMissKeyStyle());
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
	public void submitGuess() {
		// Convert list to string
		String guess = "";
		for (Character c : guessCharacterList) {
			guess += c;
		}
		
		// If the guess is valid update GUI with result and add a guess
		boolean isValid = controller.isInWordList(guess);
		if (isValid) {
			String guessResult = controller.checkGuess(guess);
			updateRectangleColors(guessResult);
			updateKeyboardColors(guessResult);
			
			// Check for win
			if (guess.equals(controller.getSecretWord())) {
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
		Button playAgain = new Button(PLAY_AGAIN_TEXT);
		playAgain.setStyle(styleHandler.getPlayAgainStyle());
		VBox.setMargin(playAgain, new Insets(PLAY_AGAIN_TOP_MARGIN, 0, 0, 0));
		// Kinda a dumb solution to handling the call is step()
		if (won) {
			won = false;
			
			Text youWon = new Text(YOU_WON_TEXT);
			youWon.setStyle(styleHandler.getWinLossStyle());
			VBox.setMargin(youWon, new Insets(WIN_LOSS_TOP_MARGIN, 0, 0, 0));
			
			playAgain.setOnAction(value -> {
				playAgain();
				root.getChildren().remove(youWon);
				root.getChildren().remove(playAgain);
			});
			
			root.getChildren().addAll(youWon, playAgain);
		} else if (lost) {
			lost = false;
			
			Text secretWord = new Text("The word was: " + controller.getSecretWord());
			secretWord.setStyle(styleHandler.getWinLossStyle());
			VBox.setMargin(secretWord, new Insets(WIN_LOSS_TOP_MARGIN, 0, 0, 0));
			
			// This will remove the GUI keyboard once it is complete
			//root.getChildren().remove(3);
			playAgain.setOnAction(value -> {
				playAgain();
				root.getChildren().remove(secretWord);
				root.getChildren().remove(playAgain);
			});
			
			root.getChildren().addAll(secretWord, playAgain);
		}
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
	
	private void resetKeyboard() {
		for (int i = 0; i < keyboardButtons.size(); i++) {
			for (int j = 0; j < keyboardButtons.get(i).size(); j++) {
				Button curr = keyboardButtons.get(i).get(j);
				curr.setStyle(styleHandler.getStartingKeyStyle());
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
		controller.pickNewWord();
		// Reset the grid
		resetGrid();
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
						if (guessCharacterList.size() < 5) guessCharacterList.add('a');
						break;
					case B: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('b');
						break;
					case C: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('c');
						break;
					case D: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('d');
						break;
					case E: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('e');
						break;
					case F: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('f');
						break;
					case G: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('g');
						break;
					case H: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('h');
						break;
					case I: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('i');
						break;
					case J: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('j');
						break;
					case K: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('k');
						break;
					case L: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('l');
						break;
					case M: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('m');
						break;
					case N: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('n');
						break;
					case O: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('o');
						break;
					case P: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('p');
						break;
					case Q: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('q');
						break;
					case R: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('r');
						break;
					case S: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('s');
						break;
					case T: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('t');
						break;
					case U: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('u');
						break;
					case V: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('v');
						break;
					case W: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('w');
						break;
					case X: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('x');
						break;
					case Y: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('y');
						break;
					case Z: 
						if (guessCharacterList.size() < 5) guessCharacterList.add('z');
						break;
					case ENTER: 
						if (guessCharacterList.size() == 5) submitGuess();
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

}
