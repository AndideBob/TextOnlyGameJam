package textOnlyJam.game.rooms;

public class MenuRoom extends Room {

	private static final int maxLevel = 2;
	
	private int selectedLevel;
	
	public MenuRoom() {
		selectedLevel = 0;
	}

	@Override
	public String getDescription() {
		StringBuilder description = new StringBuilder();
		description.append("Welcome to dungeon for dummies. I hope you have your manual at hand, because the game will be unplayable without.\n ");
		description.append("Pick a level by inputting the number:\n ");
		description.append("1: Basics for Beginners\n ");
		description.append("2: Deeper into the Dungeon");
		return description.toString();
	}

	@Override
	protected String handleSpecific(String command) {
		try{
			int level = Integer.parseInt(command);
			if(level < 1 || level > maxLevel){
				return "Invalid Level!";
			}
			else{
				selectedLevel = level;
				return "Good luck on your adventure!";
			}
		}
		catch(NumberFormatException e){
			
		}
		return null;
	}

	@Override
	public boolean isSolved() {
		return selectedLevel > 0;
	}
	
	public int getSelectedLevel(){
		return selectedLevel;
	}

}
