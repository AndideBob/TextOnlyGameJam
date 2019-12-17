package textOnlyJam.game.text;

import java.io.File;

import lwjgladapter.GameWindowConstants;

class FolderConstants{
	static final String folderGFX = File.separator + "gfx";
}

public enum GFXResourceID {
	TEXT_WHITE(FolderConstants.folderGFX, "characters.png");

	private String filePath;
	
	private GFXResourceID(String folderPath, String filename){
		this.filePath = GameWindowConstants.FILEPATH_DIRECTORY + folderPath + File.separator + filename;
	}

	public String getFilePath() {
		return filePath;
	}
}
