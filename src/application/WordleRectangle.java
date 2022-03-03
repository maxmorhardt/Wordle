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
	
	private final int RECTANGLE_SIZE = 50;
	private final Color BORDER_STROKE_COLOR = Color.rgb(58,58,60);
	private final Color STARTING_COLOR = Color.rgb(18,18,19);
	private final Color TEXT_COLOR = Color.WHITE;
	
	private Rectangle rect;
	private Text text;
	private StackPane rectWithText;
	
	public WordleRectangle(int x, int y) {
		rect = setupRectangle(x, y);
		text = setupText();
		rectWithText = setupStackPane(rect, text);
	}
	
	private Rectangle setupRectangle(int x, int y) {
		Rectangle rect = new Rectangle(x * RECTANGLE_SIZE, y * RECTANGLE_SIZE, RECTANGLE_SIZE, RECTANGLE_SIZE);
		rect.setFill(STARTING_COLOR);
		rect.setStroke(BORDER_STROKE_COLOR);
		rect.setStrokeType(StrokeType.INSIDE);
		return rect;
	}
	
	private Text setupText() {
		Text text = new Text();
		text.setFill(TEXT_COLOR);
		text.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		return text;
	}
	
	private StackPane setupStackPane(Rectangle rect, Text text) {
		StackPane sp = new StackPane(rect, text);
		return sp;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public Text getText() {
		return text;
	}

	public void setText(String text) {
		this.text.setText(text);
	}

	public StackPane getRectWithText() {
		return rectWithText;
	}

	public void setRectWithText(StackPane rectAndText) {
		this.rectWithText = rectAndText;
	}
	
	
}
