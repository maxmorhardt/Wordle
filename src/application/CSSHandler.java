package application;

/**
 * Class for hardcoding CSS since my JavaFX doesnt let me use stylesheets
 * 
 * @author Max Morhardt
 */
public class CSSHandler {
	
	final String plauAgainButtonCSS = "-fx-background-color: #121213; \n"
			+ "-fx-text-fill: #ffffff; \n"
			+ "-fx-border-color: #3a3a3c; \n"
			+ "-fx-font: 15px Helvetica;";
	
	public String getPlayAgainButtonCSS() {
		return plauAgainButtonCSS;
	}
	
}
