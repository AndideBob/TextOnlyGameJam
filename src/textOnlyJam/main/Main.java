package textOnlyJam.main;

import java.util.HashSet;

import lwjgladapter.GameWindow;
import lwjgladapter.GameWindowConstants;
import lwjgladapter.datatypes.Color;
import lwjgladapter.logging.Logger;
import lwjgladapter.utils.Randomizer;
import textOnlyJam.game.DungeonsForDummies;

public class Main {
	
	public static void main(String[] args) {
		handleArgs(args);
		GameWindowConstants.DEFAULT_SCREEN_WIDTH = 320;
		GameWindowConstants.DEFAULT_SCREEN_HEIGHT = 180;
		GameWindow gameWindow = new GameWindow(640, 360, Color.BLACK, "Dungeons for Dummies", 60);
		Logger.logDebug("new Version");
		gameWindow.run(new DungeonsForDummies());
	}
	
	private static void handleArgs(String[] args){
		HashSet<String> argSet = new HashSet<>();
		String logString = "";
		for(int i = 0; i < args.length; i++){
			logString += args[i];
			argSet.add(args[i]);
			if(i < args.length - 1){
				logString += ",";
			}
		}
		Logger.logDebug("Arguments:[" + logString + "]");
		if(argSet.contains("-bla")){
			//TODO
		}
	}

}
