package textOnlyJam.game.rooms;

import lwjgladapter.utils.Randomizer;

public class MazeRoom extends Room {

	private int rightDirectionsTaken;
	private int pathsToTake;
	
	private boolean negateDirection;
	private int requiredDirection;
	
	private boolean success;
	
	public MazeRoom() {
		rightDirectionsTaken = 0;
		pathsToTake = Randomizer.getRNGNumber(4, 7);
		success = false;
	}

	@Override
	public String getDescription() {
		StringBuilder description = new StringBuilder();
		description.append("You enter a hallway which quickly leads you to a crossing!\n ");
		description.append(getNextCrossroad());
		return description.toString();
	}

	@Override
	protected String handleSpecific(String command) {
		String[] parts = command.split(" ");
		if(parts.length == 2 && parts[0].equalsIgnoreCase("go")){
			int chosenDirection = 0;
			if(parts[1].equalsIgnoreCase("straight") || parts[1].equalsIgnoreCase("forward")) {
				chosenDirection = 0;
			}
			else if(parts[1].equalsIgnoreCase("back")) {
				chosenDirection = 1;
			}
			else if(parts[1].equalsIgnoreCase("left")) {
				chosenDirection = 2;
			}
			else if(parts[1].equalsIgnoreCase("right")) {
				chosenDirection = 3;
			}
			else {
				return null;
			}
			StringBuilder description = new StringBuilder();
			if((chosenDirection == requiredDirection && !negateDirection)
					|| (chosenDirection != requiredDirection && negateDirection)) {
				rightDirectionsTaken++;
				if(rightDirectionsTaken >= pathsToTake) {
					success = true;
					description.append("You move on and finally come to an exit! You soon enter the next room.");
				}
				else {
					description.append("You move on and reach the next crossing!\n ");
					description.append(getNextCrossroad());
				}
			}
			else {
				rightDirectionsTaken = 0;
				description.append("You move on and reach the next crossing! You somehow feel lost.\n ");
				description.append(getNextCrossroad());
			}
			return description.toString();
		}
		return null;
	}

	@Override
	public boolean isSolved() {
		return success;
	}
	
	private String getNextCrossroad() {
		negateDirection = false;
		requiredDirection = 0;
		int scenario = Randomizer.getRNGNumber(0, 3);
		StringBuilder description = new StringBuilder();
		switch(scenario) {
		case 0: //Nymphs
			int sound = Randomizer.getRNGNumber(0, 10);
			requiredDirection = Randomizer.getRNGNumber(0, 4);
			description.append("You hear some ");
			switch(sound) {
			case 0:
				description.append("lovely");
				break;
			case 1:
				description.append("comforting");
				break;
			case 2:
				description.append("intoxicating");
				break;
			case 3:
				description.append("sweet");
				break;
			case 4:
				description.append("calm");
				break;
			case 5:
				description.append("warm");
				break;
			case 6:
				description.append("exotic");
				break;
			case 7:
				description.append("hypnotizing");
				break;
			case 8:
				description.append("encouraging");
				break;
			case 9:
				description.append("soothing");
				break;
			}
			description.append(" singing voices coming from ");
			switch(requiredDirection) {
			case 0:
				description.append("in front of you.");
				if(sound < 5) {
					requiredDirection = 1;
				}
				break;
			case 1:
				description.append("behind you.");
				if(sound < 5) {
					requiredDirection = 0;
				}
				break;
			case 2:
				description.append("your left.");
				if(sound < 5) {
					requiredDirection = 3;
				}
				break;
			case 3:
				description.append("your right.");
				if(sound < 5) {
					requiredDirection = 2;
				}
				break;
			}
			return description.toString();
		case 1: //Wind
			boolean warmWind = Randomizer.getRNGBoolean();
			int windDirection = Randomizer.getRNGNumber(0, 4);
			description.append("You feel a " + (warmWind ? "warm" : "cold") + " draft coming from ");
			switch(windDirection) {
			case 0:
				description.append("ahead of you.");
				requiredDirection = warmWind ? 1 : 2;
				break;
			case 1:
				description.append("behind you.");
				requiredDirection = warmWind ? 3 : 2;
				break;
			case 2:
				description.append("your left.");
				requiredDirection = warmWind ? 2 : 0;
				break;
			case 3:
				description.append("your right.");
				requiredDirection = warmWind ? 0 : 1;
				break;
			}
			return description.toString();
		case 2: //Signs
			boolean handwritten = Randomizer.getRNGBoolean();
			boolean twoDirections = Randomizer.getRNGBoolean();
			int material = Randomizer.getRNGNumber(0, 4);
			requiredDirection = Randomizer.getRNGNumber(0, 4);
			description.append("In the middle of the crossing is a sign, it is made of ");
			switch(material) {
			case 0:
				description.append("wood");
				break;
			case 1:
				description.append("stone");
				break;
			case 2:
				description.append("metal");
				break;
			case 3:
				description.append("paper");
				break;
			}
			description.append(" and says 'Go ");
			if(twoDirections) {
				int manipulatedDirection = (requiredDirection + Randomizer.getRNGNumber(1, 4)) % 4;
				description.append(getBasicDirection(manipulatedDirection) + " or ");
			}
			description.append(getBasicDirection(requiredDirection) + "'");
			if(handwritten) {
				description.append(" in handwriting.");
			}
			else {
				description.append(" in printing.");
			}
			//Setting the actual requirement
			if(handwritten) {
				negateDirection = true;
			}
			else if(material == 0) {
				requiredDirection = 1;
			}
			else if(twoDirections) {
				//Already set
			}
			else if(material == 2) {
				if(requiredDirection == 2) {
					requiredDirection = 0;
				}
				else {
					requiredDirection = 2;
				}
			}
			return description.toString();
		default: //Footprints
			requiredDirection = Randomizer.getRNGNumber(0, 4);
			negateDirection = rightDirectionsTaken < 3;
			description.append("On the ground you see some footprints leading ");
			description.append(getBasicDirection(requiredDirection) + ".");
			return description.toString();
		
		}
	}
	
	private String getBasicDirection(int dir) {
		switch(dir) {
		case 0:
			return "straight";
		case 1:
			return "back";
		case 2:
			return "left";
		default:
			return "right";
		}
	}

}
