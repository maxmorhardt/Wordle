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
		if (model.isInWordList(guess)) return true;
		return false;
	}
	
	public String checkGuess(String guess) {
		return model.checkGuess(guess);
	}
	
	public String getSecretWord() {
		return model.getSecretWord();
	}
}
