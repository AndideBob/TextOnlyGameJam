package textOnlyJam.game.console;

import lwjgladapter.datatypes.Color;
import textOnlyJam.game.text.DrawTextManager;
import textOnlyJam.game.text.GameTextUtility;

public class TextConsole {

	private Color textColor;
	private Color timerColor;
	
	private int minutes;
	private int seconds;
	
	private int charsPerLine;
	private int rows;
	
	private ConsoleLine[] text;
	
	private int rowsAddedSoFar;
	
	public TextConsole(int charsPerLine, int rows) {
		this.charsPerLine = charsPerLine;
		this.rows = rows;
		text = new ConsoleLine[rows];
		rowsAddedSoFar = 0;
		textColor = new Color(1f, 1f, 1f, 1f);
		timerColor = new Color(0f, 1f, 0f, 1f);
	}
	
	public void updateTimer(int minutes, int seconds, float progress){
		this.minutes = minutes;
		this.seconds = seconds;
		timerColor = new Color(progress, 1F - progress, 0F, 1F);
	}
	
	public void addText(String newText, Color color){
		if(newText == null || newText.isEmpty()){
			return;
		}
		String[] lines = GameTextUtility.splitToLines(newText, charsPerLine);
		for(int i = 0; i < lines.length; i++){
			if(rowsAddedSoFar < rows){
				text[rowsAddedSoFar++] = new ConsoleLine(lines[i], color);
			}
			else{
				for(int j = 1; j < text.length; j++){
					text[j-1] = text[j];
				}
				text[text.length - 1] = new ConsoleLine(lines[i], color);
			}
		}
	}
	
	public void draw(int x, int y){
		for(int i = text.length - 1; i >= 0; i--){
			if(text[i] != null && !text[i].getText().isEmpty()){
				int posY = y + (text.length - i) * 10;
				DrawTextManager.getInstance().drawText(x, posY, text[i].getText(), charsPerLine, text[i].getColor());
			}
		}
		DrawTextManager.getInstance().drawText(x, y, "-----------------------------#--", charsPerLine, textColor);
		drawTimer(x + 291, y + 10);
	}
	
	private void drawTimer(int x, int y){
		for(int i = 0; i < rows; i++){
			int posY = y + (rows - 1 - i) * 10;
			if(i == 0){
				DrawTextManager.getInstance().drawText(x, posY, "|", 1, textColor);
				String time = minutes < 10 ? "0" + minutes : "" + minutes;
				DrawTextManager.getInstance().drawText(x + 10, posY, time, 3, timerColor);
			}
			else if(i == 1){
				DrawTextManager.getInstance().drawText(x, posY, "|", 1, textColor);
				String time = seconds < 10 ? "0" + seconds : "" + seconds;
				DrawTextManager.getInstance().drawText(x + 10, posY, time, 3, timerColor);
			}
			else{
				DrawTextManager.getInstance().drawText(x, posY, "|", 1, textColor);
			}
		}
	}

}
