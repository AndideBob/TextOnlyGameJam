package textOnlyJam.game;

import java.util.ArrayList;

import javax.swing.DebugGraphics;

import lwjgladapter.datatypes.Color;
import lwjgladapter.datatypes.LWJGLAdapterException;
import lwjgladapter.game.Game;
import lwjgladapter.logging.Logger;
import textOnlyJam.game.console.InputLine;
import textOnlyJam.game.console.TextConsole;
import textOnlyJam.game.rooms.MenuRoom;
import textOnlyJam.game.rooms.Room;
import textOnlyJam.game.rooms.SecretWallRoom;
import textOnlyJam.game.text.DrawTextManager;
import textOnlyJam.game.text.Timer;

public class DungeonsForDummies extends Game {
	
	private static final Color COLOR_DESCRIPTOR = new Color(0.2F, 0.6F, 0F, 1F);
	private static final Color COLOR_ACTION = new Color(0.4F, 0.8F, 0.1F, 1F);
	private static final Color COLOR_ERROR = new Color(0.8F, 0.1F, 0.1F, 1F);
	
	int iteration = 0;
	int x = 0;
	int y = 0;
	
	private InputLine input;
	private TextConsole textConsole;
	
	private ArrayList<Room> roomList;
	private int roomNumber;
	private Room currentRoom;
	
	private Timer timer;

	public DungeonsForDummies() {
		textConsole = new TextConsole(30, 16);
		input = new InputLine(16);
		timer = new Timer(0, 0);
		roomList = new ArrayList<>();
		currentRoom = new MenuRoom();
		textConsole.addText(currentRoom.getDescription(), COLOR_DESCRIPTOR);
	}

	@Override
	public void draw() throws LWJGLAdapterException {
		textConsole.draw(0, 11);
		input.draw(0, 0);
	}

	@Override
	public void loadResources() throws LWJGLAdapterException {
		//Init Text Manager
		DrawTextManager.getInstance();
	}

	@Override
	public void update(long deltaTime) throws LWJGLAdapterException {
		timer.update(deltaTime);
		input.update();
		textConsole.updateTimer(timer.getMinutes(), timer.getSeconds(), timer.getProgress());
		String inputCommand = input.getInput();
		if(inputCommand != null){
			textConsole.addText(inputCommand, COLOR_ACTION);
			String result = currentRoom.handleCommand(inputCommand);
			textConsole.addText(result, currentRoom.wasLastCommandValid() ? COLOR_DESCRIPTOR : COLOR_ERROR);
			if(currentRoom.isSolved()){
				if(currentRoom instanceof MenuRoom){
					startNewGame(((MenuRoom)currentRoom).getSelectedLevel());
				}
				else{
					advanceRoom();
				}
			}
		}
	}

	private void startNewGame(int level){
		roomNumber = 0;
		roomList.clear();
		switch(level){
		case 1:
			timer = new Timer(5, 0);
			roomList.add(new SecretWallRoom());
			roomList.add(new SecretWallRoom());
			roomList.add(new SecretWallRoom());
			break;
		case 2:
			timer = new Timer(4, 0);
			roomList.add(new SecretWallRoom());
			roomList.add(new SecretWallRoom());
			roomList.add(new SecretWallRoom());
			break;
		}
		currentRoom = roomList.get(roomNumber);
		textConsole.addText(currentRoom.getDescription(), COLOR_DESCRIPTOR);
	}
	
	private void advanceRoom(){
		roomNumber++;
		if(roomNumber >= roomList.size()){
			Logger.logDebug("Finished all rooms!");
		}
		else{
			currentRoom = roomList.get(roomNumber);
			textConsole.addText(currentRoom.getDescription(), COLOR_DESCRIPTOR);
		}
	}
}
