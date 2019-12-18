package textOnlyJam.game.console;

import lwjgladapter.datatypes.Color;

public class ConsoleLine {

	private String text;
	
	private Color color;

	public ConsoleLine(String text, Color color) {
		this.text = text;
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public Color getColor() {
		return color;
	}

}
