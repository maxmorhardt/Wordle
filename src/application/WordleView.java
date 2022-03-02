package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

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

	// Scene variables
	private Scene myScene;

	// Grid variable
	private Rectangle[][] grid;

	// Controller variable
	private WordleController controller;
	
	public WordleView() {
		grid = new Rectangle[WORD_LENGTH][NUM_GUESSES];
	}

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
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	/**
	 * Puts together all elements of the scene.
	 * 
	 * @return
	 */
	private Scene setupScene() {
		// Sets up the boxes for text and color to be displayed
		GridPane boxes = setupBoxes();
		
		// Sets up root to align all elements
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(ROOT_SPACING, ROOT_SPACING, ROOT_SPACING, ROOT_SPACING));
		root.getChildren().addAll(boxes);

		// Creates scene
		Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);
		return scene;
	}

	/**
	 * Creates a grid of rectangles.
	 * 
	 * @return
	 */
	private GridPane setupBoxes() {
		GridPane boxes = new GridPane();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Rectangle currCell = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				currCell.setFill(Color.GRAY);
				boxes.add(currCell, i, j);
				grid[i][j] = currCell;
			}
		}
		boxes.setAlignment(Pos.TOP_CENTER);
		boxes.setHgap(CELL_SPACING);
		boxes.setVgap(CELL_SPACING);
		return boxes;
	}
	
	/**
	 * Starts program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
