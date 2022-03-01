package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WordleView extends Application {

	// Game scene attributes
	private static final int FRAMES_PER_SECOND = 5;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final int EXTRA_VERTICAL = 175;
	private static final int EXTRA_HORIZONTAL = 375;
	private static final int CELL_SIZE = 20;
	private static final int SPACING = 10;
	private static final int WORD_LENGTH = 5;
	private static final int NUM_GUESSES = 6; 

	// Scene variables
	private Scene myScene;
	private VBox root;

	// Grid variable
	private Rectangle[][] grid;

	// Controller variable
	private WordleController controller;

	@Override
	public void start(Stage primaryStage) throws Exception {
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
	protected Scene setupScene() {
		// Sets up root to align all elements
		root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(SPACING);
		root.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
		root.getChildren().addAll();

		// Creates scene
		Scene scene = new Scene(root, WORD_LENGTH * CELL_SIZE + EXTRA_HORIZONTAL, NUM_GUESSES * CELL_SIZE + EXTRA_VERTICAL,
				Color.ANTIQUEWHITE);
		return scene;
	}

	/**
	 * Creates a grid of rectangles.
	 * 
	 * @return
	 */
	protected Group setupGrid() {
		Group gridDrawing = new Group();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Rectangle currCell = new Rectangle(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				grid[i][j] = currCell;
				gridDrawing.getChildren().add(currCell);
			}
		}
		return gridDrawing;
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
