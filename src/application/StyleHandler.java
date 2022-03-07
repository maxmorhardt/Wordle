package application;

/**
 * Class for hardcoding styles for text and buttons
 * 
 * @author Max Morhardt
 */
public class StyleHandler {
	
	private final String PLAY_AGAIN_STYLE = "-fx-background-color: #121213; \n"
			+ "-fx-text-fill: #ffffff; \n"
			+ "-fx-border-color: #3a3a3c; \n"
			+ "-fx-font: 17px Helvetica; \n"
			+ "-fx-font-weight: bold; \n"
			+ "-fx-border-width: 3px;";
	
	private final String WIN_LOSS_STYLE = "-fx-fill: #ffffff; \n"
			+ "-fx-font: 40px Helvetica; \n"
			+ "-fx-font-weight: bold;";
	
	private final String TITLE_STYLE = "-fx-fill: #ffffff; \n"
			+ "-fx-font: 35px Helvetica; \n"
			+ "-fx-font-weight: bold;";
	
	public String getPlayAgainStyle() {
		return PLAY_AGAIN_STYLE;
	}
	
	public String getWinLossStyle() {
		return WIN_LOSS_STYLE;
	}
	
	public String getTitleStyle() {
		return TITLE_STYLE;
	}
	
}