package textOnlyJam.game.rooms;

public abstract class Room {
	
	private static String COMMAND_LOOK = "look";
	private static String UNKNOWN_ACTION = "unknown/wrong command";
	
	public abstract String getDescription();
	
	public final String handleCommand(String command){
		if(COMMAND_LOOK.equalsIgnoreCase(command)){
			return getDescription();
		}
		String specificResult = handleSpecific(command); 
		if(specificResult == null || specificResult.isEmpty()){
			return UNKNOWN_ACTION;
		}
		return specificResult;
	}
	
	protected abstract String handleSpecific(String command);
	
	public abstract boolean isSolved();

}
