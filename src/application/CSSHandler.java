package application;

/**
 * Class for hardcoding CSS since my JavaFX doesnt let me use stylesheets
 * 
 * @author Max Morhardt
 */
public class CSSHandler {
	
	final String PLAY_AGAIN_BUTTON_CSS = "-fx-background-color: #121213; \n"
			+ "-fx-text-fill: #ffffff; \n"
			+ "-fx-border-color: #3a3a3c; \n"
			+ "-fx-font: 17px Helvetica;";
	
	final String WIN_LOSS_TEXT_CSS = "-fx-fill: #ffffff; \n"
			+ "-fx-font: 40px Helvetica; \n"
			+ "-fx-font-weight: bold;";
	
	public String getPlayAgainButtonCSS() {
		return PLAY_AGAIN_BUTTON_CSS;
	}
	
	public String getWinLossTextCSS() {
		return WIN_LOSS_TEXT_CSS;
	}
	
}
