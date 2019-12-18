package textOnlyJam.game.console;

import java.util.ArrayList;
import java.util.Arrays;

import lwjgladapter.datatypes.Color;
import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;
import textOnlyJam.game.text.DrawTextManager;
import textOnlyJam.game.text.GameTextUtility;

public class InputLine {
	
	private Color textColor;

	private String currentLine;
	private int maxLength;
	private int blinkState;
	
	private boolean confirmed;
	
	private ArrayList<KeyboardKey> relevantKeys;
	
	public InputLine(int maxLength) {
		currentLine = "";
		this.maxLength = maxLength;
		relevantKeys = new ArrayList<>();
		relevantKeys.addAll(Arrays.asList(KeyboardKey.letterButtons));
		relevantKeys.addAll(Arrays.asList(KeyboardKey.numberButtons));
		relevantKeys.addAll(Arrays.asList(KeyboardKey.numPadButtons));
		confirmed = false;
		textColor = new Color(1f, 1f, 1f, 1f);
	}
	
	public void update(){
		blinkState = (blinkState + 1) % 20;
		if(!confirmed){
			//Update Keys
			if(currentLine.length() < maxLength){
				for(KeyboardKey key : relevantKeys){
					if(ButtonState.RELEASED.equals(InputManager.instance.getKeyState(key))){
						currentLine += GameTextUtility.convertKeyboardKeyToChar(key);
					}
				}
				if(ButtonState.RELEASED.equals(InputManager.instance.getKeyState(KeyboardKey.KEY_SPACE))){
					currentLine += " ";
				}
			}
			//Delete
			if(currentLine.length() > 0){
				if(ButtonState.RELEASED.equals(InputManager.instance.getKeyState(KeyboardKey.KEY_BACKSPACE))){
					currentLine = currentLine.substring(0, currentLine.length() - 1);
				}
			}
			//Confirm
			if(ButtonState.RELEASED.equals(InputManager.instance.getKeyState(KeyboardKey.KEY_ENTER))){
				confirmed = true;
			}
		}
	}
	
	public void draw(int x, int y){
		String line = currentLine;
		line += (line.length() < maxLength && blinkState < 10 ? "#" : "");
		DrawTextManager.getInstance().drawText(x, y, line, 32, textColor);
	}
	
	public String getInput(){
		String result = null;
		if(confirmed){
			confirmed = false;
			result = currentLine;
			currentLine = "";
		}
		return result;
	}

}
