package textOnlyJam.game.text;

import java.io.File;

import lwjgladapter.GameWindowConstants;
import lwjgladapter.datatypes.Color;
import lwjgladapter.gfx.SpriteMap;

public class DrawTextManager {
	
	private static final int letterTileWidth = 8;
	private static final int letterTileHeight = 8;
	
	private static final int letterSpacing = 2;
	
	private static DrawTextManager instance;
	
	private SpriteMap letterMap;
	
	public static DrawTextManager getInstance(){
		if(instance == null){
			instance = new DrawTextManager();
		}
		return instance;
	}
	
	private DrawTextManager(){
		letterMap = new SpriteMap(GFXResourceID.TEXT_WHITE.getFilePath(), letterTileWidth, letterTileHeight);
	}
	
	public void drawText(int posX, int posY, String text, int maxLength){
		drawText(posX, posY, text, maxLength, new Color(0.5F, 0.4F, 0.2F , 1F));
	}
	
	public void drawText(int posX, int posY, String text, int maxLength, Color textColor){
		int count = 0;
		String actualText = text;
		if(text.length() > maxLength){
			actualText = text.substring(0, maxLength);
		}
		letterMap.setColorValues(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), textColor.getAlpha());
		for(char c : actualText.toCharArray()){
			int letter = ((int)c) % 255;
			if(letter > 32){
				letterMap.draw(letter, posX + count * (letterTileWidth + letterSpacing), posY);
			}
			count++;
		}
	}

}
