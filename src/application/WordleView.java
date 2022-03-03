package application;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class for the display of the Wordle game
 * 
 * @author Max Morhardt
 */
public class WordleView extends Application {

	// Game scene attributes
	private final int FRAMES_PER_SECOND = 30;
	private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private final int SCENE_WIDTH = 900;
	private final int SCENE_HEIGHT = 850;
	private final int CELL_SIZE = 50;
	private final int CELL_SPACING = 20;
	private final int ROOT_SPACING = 10;
	private final int WORD_LENGTH = 5;
	private final int NUM_GUESSES = 6;

	// Scene variable
	private Scene myScene;

	// Grid variable
	private Rectangle[][] boxes;
	private Text[][] text;

	// Controller variable
	private WordleController controller;
	
	// Current guess list
	private List<Character> guess;
	
	private int guessCount;
	
	/**
	 * Constructor
	 */
	public WordleView() {
		boxes = new Rectangle[WORD_LENGTH][NUM_GUESSES];
		text = new Text[WORD_LENGTH][NUM_GUESSES];
		guess = new ArrayList<>();
		guessCount = 0;
		controller = new WordleController();
	}

	/**
	 * Main entry point for JavaFX applications
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Sets the title of the application
		primaryStage.setTitle("Wordle");
		
		// Scene
		myScene = setupScene();
		handleKeyboardInput(myScene);

		// Shows scene
		primaryStage.setScene(myScene);
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
	private Scene setupScene() {
		// Adds text for the title of the game
		//Text title = new Text("Wordle");
		
		// Sets up the boxes for text and color to be displayed
		GridPane grid = setupGrid();
		
		// Sets up root to align all elements
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(ROOT_SPACING, ROOT_SPACING, ROOT_SPACING, ROOT_SPACING));
		root.getChildren().addAll(grid);

		// Creates scene
		Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.BLACK);
		return scene;
	}

	/**
	 * Creates a grid of rectangles for where characters will be placed
	 * 
	 * @return GridPane of rectangles
	 */
	private GridPane setupGrid() {
		GridPane grid = new GridPane();
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				Rectangle currBox = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				currBox.setFill(Color.GHOSTWHITE);
				boxes[i][j] = currBox;
				Text currText = new Text();
				text[i][j] = currText;
				StackPane stack = new StackPane(currBox, currText);
				grid.add(stack, i, j);
			}
		}
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(CELL_SPACING);
		grid.setVgap(CELL_SPACING);
		return grid;
	}
	
	private void handleKeyboardInput(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case A: 
					if (guess.size() < 5) guess.add('a');
					break;
				case B: 
					if (guess.size() < 5) guess.add('b');
					break;
				case C: 
					if (guess.size() < 5) guess.add('c');
					break;
				case D: 
					if (guess.size() < 5) guess.add('d');
					break;
				case E: 
					if (guess.size() < 5) guess.add('e');
					break;
				case F: 
					if (guess.size() < 5) guess.add('f');
					break;
				case G: 
					if (guess.size() < 5) guess.add('g');
					break;
				case H: 
					if (guess.size() < 5) guess.add('h');
					break;
				case I: 
					if (guess.size() < 5) guess.add('i');
					break;
				case J: 
					if (guess.size() < 5) guess.add('j');
					break;
				case K: 
					if (guess.size() < 5) guess.add('k');
					break;
				case L: 
					if (guess.size() < 5) guess.add('l');
					break;
				case M: 
					if (guess.size() < 5) guess.add('m');
					break;
				case N: 
					if (guess.size() < 5) guess.add('n');
					break;
				case O: 
					if (guess.size() < 5) guess.add('o');
					break;
				case P: 
					if (guess.size() < 5) guess.add('p');
					break;
				case Q: 
					if (guess.size() < 5) guess.add('q');
					break;
				case R: 
					if (guess.size() < 5) guess.add('r');
					break;
				case S: 
					if (guess.size() < 5) guess.add('s');
					break;
				case T: 
					if (guess.size() < 5) guess.add('t');
					break;
				case U: 
					if (guess.size() < 5) guess.add('u');
					break;
				case V: 
					if (guess.size() < 5) guess.add('v');
					break;
				case W: 
					if (guess.size() < 5) guess.add('w');
					break;
				case X: 
					if (guess.size() < 5) guess.add('x');
					break;
				case Y: 
					if (guess.size() < 5) guess.add('y');
					break;
				case Z: 
					if (guess.size() < 5) guess.add('z');
					break;
				case ENTER: 
					if (guess.size() == 5) submitGuess();
					break;
				case BACK_SPACE: 
					if (guess.size() > 0) guess.remove(guess.size()-1);
					break;
				}
				
			}
		});
	}
	
	
	private void updateLetters() {
		for (int i = 0; i < WORD_LENGTH; i++) {
			Text currText = text[i][guessCount];
			if (guess.size() - 1 < i) {
				currText.setText("");
			} else {
				currText.setText("" + guess.get(i));
			}
		}
	}
	
	private void submitGuess() {
		String currGuess = "";
		for (Character c : guess) {
			currGuess += c;
		}
		boolean isValid = controller.isInWordList(currGuess);
		if (isValid) {
			String checkedGuess = controller.checkGuess(currGuess);
			updateBoxes(checkedGuess);
			guessCount++;
		} else {
			// Somehow alert "Not in word list"
		}
	}
	
	private void updateBoxes(String checkedGuess) {
		for (int i = 0; i < WORD_LENGTH; i++) {
			if (checkedGuess.charAt(i) == 'G') {
				boxes[i][guessCount].setFill(Color.GREEN);
			} else if (checkedGuess.charAt(i) == 'Y') {
				boxes[i][guessCount].setFill(Color.YELLOW);
			} else {
				boxes[i][guessCount].setFill(Color.GRAY);
			}
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
