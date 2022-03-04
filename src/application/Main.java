package application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class to see where to run the program from
 * 
 * @author Max Morhardt
 */
public class Main extends Application {
	
	/**
	 * Main entry for the program
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		WordleView wv = new WordleView();
		wv.start(primaryStage);
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
