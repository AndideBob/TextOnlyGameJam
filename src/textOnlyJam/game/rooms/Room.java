package textOnlyJam.game.rooms;

public abstract class Room {
	
	private static String COMMAND_LOOK = "look";
	private static String UNKNOWN_ACTION = "unknown/wrong command";
	
	private boolean validCommandEntered = false;
	
	public abstract String getDescription();
	
	public final String handleCommand(String command){
		validCommandEntered = true;
		if(COMMAND_LOOK.equalsIgnoreCase(command)){
			return getDescription();
		}
		String specificResult = handleSpecific(command); 
		if(specificResult == null || specificResult.isEmpty()){
			validCommandEntered = false;
			return UNKNOWN_ACTION;
		}
		return specificResult;
	}
	
	public final boolean wasLastCommandValid(){
		return validCommandEntered;
	}
	
	protected abstract String handleSpecific(String command);
	
	public abstract boolean isSolved();

}
