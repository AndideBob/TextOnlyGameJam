package textOnlyJam.game;

import java.io.File;

import lwjgladapter.GameWindow;
import lwjgladapter.GameWindowConstants;
import lwjgladapter.datatypes.LWJGLAdapterException;
import lwjgladapter.game.Game;
import lwjgladapter.gfx.Sprite;
import lwjgladapter.gfx.SpriteMap;
import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;
import lwjgladapter.logging.Logger;
import lwjgladapter.utils.Randomizer;
import textOnlyJam.game.text.DrawTextManager;

public class DungeonsForDummies extends Game {
	
	String text = "";
	int iteration = 0;
	int x = 0;
	int y = 0;

	public DungeonsForDummies() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() throws LWJGLAdapterException {
		DrawTextManager.getInstance().drawText(x, y, text, 10);
	}

	@Override
	public void loadResources() throws LWJGLAdapterException {
		//Init Text Manager
		DrawTextManager.getInstance();
	}

	@Override
	public void update(long arg0) throws LWJGLAdapterException {
		if(InputManager.instance.getKeyState(KeyboardKey.KEY_SPACE).equals(ButtonState.RELEASED)) {
			x = Randomizer.getRNGNumber(0, GameWindowConstants.DEFAULT_SCREEN_WIDTH);
			y = Randomizer.getRNGNumber(0, GameWindowConstants.DEFAULT_SCREEN_HEIGHT);
			
			text = "Text" + iteration++;
			Logger.logDebug("x=" + x + " y=" + y + " iteration=" + iteration);
		}
	}

}
