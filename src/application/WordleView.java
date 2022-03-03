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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
	private static final int FRAMES_PER_SECOND = 5;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final int SCENE_WIDTH = 900;
	private static final int SCENE_HEIGHT = 850;
	private static final int CELL_SIZE = 50;
	private static final int CELL_SPACING = 20;
	private static final int ROOT_SPACING = 10;
	private static final int WORD_LENGTH = 5;
	private static final int NUM_GUESSES = 6;

	// Scene variable
	private Scene myScene;

	// Grid variable
	private StackPane[][] grid;

	// Controller variable
	private WordleController controller;
	
	// Current guess list
	private List<Character> currGuess;
	
	/**
	 * Constructor
	 */
	public WordleView() {
		grid = new StackPane[WORD_LENGTH][NUM_GUESSES];
		currGuess = new ArrayList<>();
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
		GridPane boxes = setupBoxes();
		
		// Sets up root to align all elements
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(ROOT_SPACING, ROOT_SPACING, ROOT_SPACING, ROOT_SPACING));
		root.getChildren().addAll(boxes);

		// Creates scene
		Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.BLACK);
		handleKeyboardInput(scene);
		return scene;
	}

	/**
	 * Creates a grid of rectangles for where characters will be placed
	 * 
	 * @return GridPane of rectangles
	 */
	private GridPane setupBoxes() {
		GridPane boxes = new GridPane();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Rectangle currCell = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				currCell.setFill(Color.GHOSTWHITE);
				Text text = new Text();
				StackPane stack = new StackPane(currCell, text);
				boxes.add(stack, i, j);
				grid[i][j] = stack;
			}
		}
		boxes.setAlignment(Pos.CENTER);
		boxes.setHgap(CELL_SPACING);
		boxes.setVgap(CELL_SPACING);
		return boxes;
	}
	
	private void handleKeyboardInput(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case A: if (currGuess.size() < 5) currGuess.add('a');
				}
				
			}
		});
	}
	
	/**
	 * What will be called in the game loop
	 * 
	 * @param elapsedTime
	 */
	private void step(int elapsedTime) {
		if (currGuess.size() > 0) {
			System.out.println("Working");
		}
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
