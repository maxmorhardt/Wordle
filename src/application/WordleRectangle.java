package application;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * A class for the rectangles that hold text for Wordle
 * 
 * @author Max Morhardt
 */
public class WordleRectangle {
	
	// Attributes
	private final int FONT_SIZE = 15;
	private final int RECTANGLE_SIZE = 50;
	private final String FONT = "Helvetica";
	private final FontWeight FONT_WEIGHT = FontWeight.BOLD;
	private final StrokeType INSIDE_STROKE = StrokeType.INSIDE;
	private final Color BORDER_STROKE_COLOR = Color.rgb(58,58,60);
	private final Color STARTING_COLOR = Color.rgb(18,18,19);
	private final Color TEXT_COLOR = Color.WHITE;
	
	// Variables
	private Rectangle rect;
	private Text text;
	private StackPane rectWithText;
	
	/**
	 * Constructor
	 * 
	 * @param xPos of rectangle
	 * @param yPos of rectangle
	 */
	public WordleRectangle(int x, int y) {
		// Rectangle
		rect = setupRectangle(x, y);
		// Text
		text = setupText();
		// Combined rectangle and text
		rectWithText = setupStackPane(rect, text);
	}
	
	/**
	 * Creates a rectangle with certain attributes
	 * 
	 * @param xPos of rectangle
	 * @param yPos of rectangle
	 * @return rectangle
	 */
	private Rectangle setupRectangle(int x, int y) {
		Rectangle rect = new Rectangle(x * RECTANGLE_SIZE, y * RECTANGLE_SIZE, RECTANGLE_SIZE, RECTANGLE_SIZE);
		rect.setFill(STARTING_COLOR);
		rect.setStroke(BORDER_STROKE_COLOR);
		rect.setStrokeType(INSIDE_STROKE);
		return rect;
	}
	
	/**
	 * Creates text with certain attributes
	 * 
	 * @return text
	 */
	private Text setupText() {
		Text text = new Text();
		text.setFill(TEXT_COLOR);
		text.setFont(Font.font(FONT, FONT_WEIGHT, FONT_SIZE));
		return text;
	}
	
	/**
	 * Combined rectangle and text
	 * 
	 * @param rectangle
	 * @param text
	 * @return stackpane of rectangle and text
	 */
	private StackPane setupStackPane(Rectangle rect, Text text) {
		StackPane sp = new StackPane(rect, text);
		return sp;
	}

	/**
	 * Sets a rectangle to a certain color
	 * 
	 * @param color
	 */
	public void setRectFill(Color color) {
		this.rect.setFill(color);
	}
	
	/**
	 * Sets the string of the text
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text.setText(text);
	}

	/**
	 * Gets the combined rectangle and text
	 * 
	 * @return stackpane of rectangle and text
	 */
	public StackPane getRectWithText() {
		return rectWithText;
	}
	
	/**
	 * Resets the Wordle rectangle to its starting state
	 */
	public void reset() {
		text.setText("");
		rect.setFill(STARTING_COLOR);
	}
	
}
