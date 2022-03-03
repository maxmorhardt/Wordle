package application;

/**
 * Controller for model and view to communicate
 * 
 * @author Max Morhardt
 */
public class WordleController {
	
	private WordleModel model;
	
	public WordleController() {
		model = new WordleModel();
	}
	
	public boolean isInWordList(String guess) {
		return model.isInWordList(guess);
	}
	
	public String checkGuess(String guess) {
		return model.checkGuess(guess);
	}

}
