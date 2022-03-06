package application;

import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * Handles both normal and GUI keyboard inputs
 * 
 * @author Max Morhardt
 */
public class KeyboardHandler {
	
	private WordleView wv;
	
	public KeyboardHandler() {
		wv = new WordleView();
	}
	/**
	 * Handles all of the possible key events for the game
	 * 
	 * @param scene for the game
	 */
	public void handleKeyboardInput(Scene scene, List<Character> guessCharacterList, boolean won, boolean lost) {
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
						if (guessCharacterList.size() == 5) wv.submitGuess();
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
