package textOnlyJam.game.console;

import lwjgladapter.datatypes.Color;
import textOnlyJam.game.text.DrawTextManager;
import textOnlyJam.game.text.GameTextUtility;

public class TextConsole {

	private Color textColor;
	
	private int minutes;
	private int seconds;
	
	private int charsPerLine;
	private int rows;
	
	private String[] text;
	
	private int rowsAddedSoFar;
	
	public TextConsole(int charsPerLine, int rows) {
		this.charsPerLine = charsPerLine;
		this.rows = rows;
		text = new String[rows];
		rowsAddedSoFar = 0;
		textColor = new Color(1f, 1f, 1f, 1f);
	}
	
	public void updateTimer(int minutes, int seconds){
		this.minutes = minutes;
		this.seconds = seconds;
	}
	
	public void addText(String newText){
		if(newText == null || newText.isEmpty()){
			return;
		}
		String[] lines = GameTextUtility.splitToLines(newText, charsPerLine);
		for(int i = 0; i < lines.length; i++){
			if(rowsAddedSoFar < rows){
				text[rowsAddedSoFar++] = lines[i];
			}
			else{
				for(int j = 1; j < text.length; j++){
					text[j-1] = text[j];
				}
				text[text.length - 1] = lines[i];
			}
		}
	}
	
	public void draw(int x, int y){
		for(int i = text.length - 1; i >= 0; i--){
			if(text[i] != null && !text[i].isEmpty()){
				int posY = y + (text.length - i) * 10;
				DrawTextManager.getInstance().drawText(x, posY, text[i], charsPerLine, textColor);
			}
		}
		DrawTextManager.getInstance().drawText(x, y, "-----------------------------#--", charsPerLine, textColor);
		drawTimer(x + 291, y + 10);
	}
	
	private void drawTimer(int x, int y){
		for(int i = 0; i < rows; i++){
			int posY = y + (rows - 1 - i) * 10;
			if(i == 0){
				String time = minutes < 10 ? "0" + minutes : "" + minutes;
				DrawTextManager.getInstance().drawText(x, posY, "|" + time, 3, textColor);
			}
			else if(i == 1){
				String time = seconds < 10 ? "0" + seconds : "" + seconds;
				DrawTextManager.getInstance().drawText(x, posY, "|" + time, 3, textColor);
			}
			else{
				DrawTextManager.getInstance().drawText(x, posY, "|", 1, textColor);
			}
		}
	}

}
