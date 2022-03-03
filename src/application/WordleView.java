package application;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class for the display of the Wordle game
 * TODO
 * Make a lot of the game logic in here happen in the model
 * Add virtual keyboard
 * Add the Wordle title
 * 
 * @author Max Morhardt
 */
public class WordleView extends Application {

	// Game scene attributes
	private final int FRAMES_PER_SECOND = 30;
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private final int SCENE_WIDTH = 700;
	private final int SCENE_HEIGHT = 775;
	private final int RECTANGLE_SIZE = 50;
	private final int RECTANGLE_SPACING = 20;
	private final int WORD_LENGTH = 5;
	private final int NUM_GUESSES = 6;
	private final Color BACKGROUND_COLOR = Color.rgb(18,18,19);
	private final Color STARTING_COLOR = Color.rgb(18,18,19);
	private final Color BORDER_STROKE_COLOR = Color.rgb(58,58,60);
	private final Color LINE_COLOR = Color.rgb(54,54,56);
	private final Color TEXT_COLOR = Color.WHITE;
	private final Color HIT_COLOR = Color.rgb(83,141,78);
	private final Color CONTAINS_COLOR = Color.rgb(181,159,59);
	private final Color MISS_COLOR = Color.rgb(58,58,60);
	

	// Instance variables
	private Rectangle[][] gridRectangles;
	private Text[][] gridText;
	private List<Character> guessCharacterList;
	private int guessCount;
	private WordleController controller;
	
	/**
	 * Constructor
	 */
	public WordleView() {
		// Rectangles within the grid pane
		gridRectangles = new Rectangle[WORD_LENGTH][NUM_GUESSES];
		// Text on top of each rectangle
		gridText = new Text[WORD_LENGTH][NUM_GUESSES];
		// List of all the characters that are in the current guesses
		guessCharacterList = new ArrayList<>();
		// Number of guesses attempted
		guessCount = 0;
		// Controller
		controller = new WordleController();
	}

	/**
	 * Main entry point
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create scene and display
		Scene scene = setupScene();
		handleKeyboardInput(scene);
		primaryStage.setTitle("Wordle");
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
	 * TODO
	 * Find replacement for BorderPane
	 * Make separate method for title once its more realistic
	 * Fix magic numbers
	 * 
	 * @return Scene including all the elements in a VBox
	 */
	private Scene setupScene() {
		// Adds text for the title of the game
		Text title = new Text("Wordle");
		title.setFill(TEXT_COLOR);
		title.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 35));
		
		// Sets up the grid for text and color to be displayed
		GridPane grid = setupGrid();
		
		// Sets up root to align all elements
		BorderPane root = new BorderPane();
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		BorderPane.setMargin(title, new Insets(25, 0, 0, 0));
		root.setTop(title);
		root.setCenter(grid);

		// Creates scene
		Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, BACKGROUND_COLOR);
		return scene;
	}
	
	/**
	 * Creates a grid of rectangles for where characters will be placed
	 * 
	 * @return GridPane of rectangles
	 */
	private GridPane setupGrid() {
		// Grid plane to hold the rectangle and text stack panes 
		GridPane grid = new GridPane();
		for (int i = 0; i < gridRectangles.length; i++) {
			for (int j = 0; j < gridRectangles[i].length; j++) {
				// Create a rectangle, set it to its starting color, and add them to an array
				Rectangle rect = new Rectangle(i * RECTANGLE_SIZE, j * RECTANGLE_SIZE, RECTANGLE_SIZE, RECTANGLE_SIZE);
				rect.setFill(STARTING_COLOR);
				rect.setStroke(BORDER_STROKE_COLOR);
				rect.setStrokeType(StrokeType.INSIDE);
				gridRectangles[i][j] = rect;
				
				// Create text and add it to an array
				Text text = new Text();
				text.setFill(TEXT_COLOR);
				gridText[i][j] = text;
				
				// Create a stack pane with the text over the rectangle and add to a grid pane of them
				StackPane stack = new StackPane(rect, text);
				grid.add(stack, i, j);
			}
		}
		// Aligns the grid in the center and adds space between the rectangles
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(RECTANGLE_SPACING);
		grid.setVgap(RECTANGLE_SPACING);
		return grid;
	}
	
	/**
	 * Handles all of the possible key events for the game
	 * TODO
	 * Send these events to the model instead
	 * 
	 * @param scene for the game
	 */
	private void handleKeyboardInput(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
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
		});
	}
	
	
	/**
	 * Updates the letters on the GUI
	 */
	private void updateLetters() {
		for (int i = 0; i < WORD_LENGTH; i++) {
			Text currText = gridText[i][guessCount];
			if (guessCharacterList.size() - 1 < i) {
				currText.setText("");
			} else {
				currText.setText(("" + guessCharacterList.get(i)).toUpperCase());
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
			if (guessResult.charAt(i) == 'G') {
				gridRectangles[i][guessCount].setFill(HIT_COLOR);
			} else if (guessResult.charAt(i) == 'Y') {
				gridRectangles[i][guessCount].setFill(CONTAINS_COLOR);
			} else {
				gridRectangles[i][guessCount].setFill(MISS_COLOR);
			}
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
		boolean isValid = controller.isInWordList(guess);
		if (isValid) {
			String guessResult = controller.checkGuess(guess);
			updateRectangleColors(guessResult);
			guessCharacterList.clear();
			guessCount++;
			
		// If its not valid alert the player
		} else {
			// Somehow alert "Not in word list"
		}
	}
	
	/**
	 * What will be called in the game loop
	 * 
	 * @param elapsedTime
	 */
	private void step(int elapsedTime) {
		updateLetters();
	}
	
	/**
	 * Starts program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
