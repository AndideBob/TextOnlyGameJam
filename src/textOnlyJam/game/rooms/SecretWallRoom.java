package textOnlyJam.game.rooms;

import lwjgladapter.utils.Randomizer;

public class SecretWallRoom extends Room {
	
	private static final String SOLVED_MESSAGE = "With a lot of clicking the bricks in the wall shift and "
			+ "reveal a path onwards. You step through.";

	private static final String FAILURE_MESSAGE = "You hear an ominous noise that can not mean anything good. "
			+ "It seems your action was wrong and the wall has reset.";

	int numberOnWall;
	int kindOfStone;
	int typeOfNoiseAdjective;
	int typeOfNoiseNoun;
	boolean requiresThirdPush;
	
	int step;
	int requiredRow;
	int requiredColumn;
	boolean pushRequired;
	
	boolean solved;
	
	public SecretWallRoom() {
		numberOnWall = Randomizer.getRNGNumber(1001, 10000);
		if(numberOnWall % 10 == 0){
			numberOnWall += Randomizer.getRNGNumber(1, 10);
		}
		kindOfStone = Randomizer.getRNGNumber(0, 4);
		typeOfNoiseAdjective = Randomizer.getRNGNumber(0, 4);
		typeOfNoiseNoun = Randomizer.getRNGNumber(0, 3);
		step = 0;
		solved = false;
		calculateRequirements();
	}

	@Override
	public String getDescription() {
		StringBuilder description = new StringBuilder();
		description.append("You find yourself in a room with a suspicious looking wall. ");
		description.append("The wall is made out of " + getKindOfStone() + "-bricks. ");
		description.append("There is the number '" + numberOnWall + "' carved into it.");
		return description.toString();
	}

	@Override
	protected String handleSpecific(String command) {
		String[] parts = command.split(" ");
		try{
			if(parts.length == 3){
				int column = Integer.parseInt(parts[1]);
				int row = Integer.parseInt(parts[2]);
				if(parts[0].equalsIgnoreCase("push")){
					if(!pushRequired){
						return fail();
					}
				}
				else if(parts[0].equalsIgnoreCase("pull")){
					if(pushRequired){
						return fail();
					}
				}
				else{
					return null;
				}
				if(requiredColumn > 0 && requiredColumn != column){
					return fail();
				}
				if(requiredRow > 0 && requiredRow != row){
					return fail();
				}
				return success();
			}
		}
		catch(NumberFormatException e){
			//Just return null
		}
		return null;
	}
	
	private String success(){
		step++;
		if(step >= 2 + (requiresThirdPush ? 1 : 0)){
			solved = true;
			return SOLVED_MESSAGE;
		}
		calculateRequirements();
		if(step == 1){
			return "The stone comes to a rest. From inside the wall you hear a " + getSoundAdjective() + " " + getSoundNoun() + ". What to do next?";
		}
		else{
			return "The brick clicks into place. This should be it, but the wall is not opening yet.";
		}
	}

	private String fail(){
		step = 0;
		calculateRequirements();
		return FAILURE_MESSAGE;
	}
	
	private void calculateRequirements(){
		if(step == 0){
			if(kindOfStone == 0){
				pushRequired = true;
				requiredColumn = 3;
				requiredRow = 4;
			}
			else if(numberOnWall % 3 == 0){
				pushRequired = false;
				requiredColumn = 5;
				requiredRow = 1;
			}
			else if(kindOfStone != 1){
				pushRequired = true;
				requiredColumn = 7;
				requiredRow = 7;
			}
			else{
				pushRequired = false;
				requiredColumn = 0;
				requiredRow = 3;
			}
		}
		else if(step == 1){
			switch(typeOfNoiseNoun){
			case 0:
				pushRequired = true;
				switch(typeOfNoiseAdjective){
				case 0:
					requiredColumn = 6;
					requiredRow = 2;
					break;
				case 1:
					requiredColumn = 8;
					requiredRow = 3;
					break;
				case 2:
					requiredColumn = 2;
					requiredRow = 9;
					break;
				default:
					requiredColumn = 2;
					requiredRow = 4;
					break;
				}
				break;
			case 1:
				pushRequired = false;
				switch(typeOfNoiseAdjective){
				case 0:
					requiredColumn = 1;
					requiredRow = 1;
					break;
				case 1:
					requiredColumn = 3;
					requiredRow = 8;
					break;
				case 2:
					requiredColumn = 3;
					requiredRow = 4;
					break;
				default:
					requiredColumn = 7;
					requiredRow = 2;
					break;
				}
				break;
			default:
				pushRequired = false;
				switch(typeOfNoiseAdjective){
				case 0:
					requiredColumn = 6;
					requiredRow = 6;
					break;
				case 1:
					requiredColumn = 9;
					requiredRow = 8;
					break;
				case 2:
					requiredColumn = 3;
					requiredRow = 5;
					break;
				default:
					requiredColumn = 4;
					requiredRow = 5;
					break;
				}
				break;
			}
		}
		else{
			int firstDigit = (int)Math.floor(numberOnWall / 1000);
			int lastDigit = numberOnWall % 10;
			if(numberOnWall % 2 == 0){
				pushRequired = false;
				requiredColumn = firstDigit;
				requiredRow = lastDigit;
			}
			else{
				pushRequired = true;
				requiredColumn = lastDigit;
				requiredRow = firstDigit;
			}
		}
	}
	
	private String getSoundNoun() {
		switch(typeOfNoiseNoun){
		case 0:
			return "Clicking";
		case 1:
			return "Squeaking";
		default:
			return "Scratching";
		}
	}

	private String getSoundAdjective() {
		switch(typeOfNoiseAdjective){
		case 0:
			return "loud";
		case 1:
			return "faint";
		case 2:
			return "shrill";
		default:
			return "dull";
		}
	}
	
	private String getKindOfStone(){
		switch(kindOfStone){
		case 0:
			return "Sandstone";
		case 1:
			return "Marble";
		case 2:
			return "Granite";
		default:
			return "Limestone";
		}
	}

	@Override
	public boolean isSolved() {
		return solved;
	}
}
