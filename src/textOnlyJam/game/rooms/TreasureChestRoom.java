package textOnlyJam.game.rooms;

import lwjgladapter.utils.Randomizer;

public class TreasureChestRoom extends Room {
	
	private boolean isInLockPickPhase;
	private boolean trapTriggered;
	private boolean success;
	
	private int timesKicked;
	private int lockPickSound;
	private int timesToPickLock;
	

	private boolean hasLock;
	
	private int materialFrame;
	
	private int materialCorners;
	
	private int materialStraps;
	
	public TreasureChestRoom() {
		isInLockPickPhase = false;
		trapTriggered = false;
		success = false;
		timesKicked = 0;
		hasLock = Randomizer.getRNGBoolean();
		materialFrame = Randomizer.getRNGNumber(0, 3);
		materialCorners = Randomizer.getRNGNumber(0, 3);
		materialStraps = Randomizer.getRNGNumber(0, 3);
		timesToPickLock = Randomizer.getRNGNumber(4, 7);
	}

	@Override
	public String getDescription() {
		StringBuilder description = new StringBuilder();
		description.append("You finally found the treasure room! There is a big " + getMaterial(materialFrame) + " chest sitting in the middle of it. ");
		description.append("It has " + getMaterial(materialCorners) + " corners and " + getMaterial(materialStraps) + " straps.");
		return description.toString();
	}

	@Override
	protected String handleSpecific(String command) {
		if(!isInLockPickPhase) {
			int action = 0;
			if(command.equalsIgnoreCase("open")) {
				action = 0;
			}
			else if(command.equalsIgnoreCase("kick")) {
				action = 1;
			}
			else if(command.equalsIgnoreCase("lockpick")) {
				action = 2;
			}
			else {
				return null;
			}
			//Check for Lockpick
			if(action == 2) {
				if(hasLock) {
					if(materialCorners == 0 && materialFrame == 0 && materialStraps == 0) {
						trapTriggered = true;
						return "You insert the lockpick and hear a silent clicking noise followed by more sounds of an ominous mechanism. "
								+ "Only seconds later you find your demise through a trap which you foolishly triggered.";
					}
					isInLockPickPhase = true;
					return "You insert the lockpick into the lock and hear a silent " + getLockpickAction() + ".";
				}
				else {
					return "The chest has no lock to pick!";
				}
			}
			//Check other Actions
			if(materialCorners != 0 && materialFrame != 0 && materialStraps != 0) {
				if(action == 0) {//Open
					if(timesKicked == 2) {
						success = true;
						return getSuccess();
					}
					trapTriggered = true;
					return "As soon as you open the chest, you hear sounds of an ominous mechanism. "
							+ "Only seconds later you find your demise through a trap which you foolishly triggered.";
				}
				else {//Kick
					if(timesKicked < 2) {
						timesKicked++;
						return "You kick the chest hard! Nothing happens.";
					}
					trapTriggered = true;
					return "You kick the chest hard and suddenly hear sounds of an ominous mechanism. "
							+ "Only seconds later you find your demise through a trap which you foolishly triggered.";
				}
			}
			else if(materialCorners == 1 && materialFrame == 1 && materialStraps == 1) {
				if(action == 0) {//Open
					if(timesKicked == 3) {
						success = true;
						return getSuccess();
					}
					trapTriggered = true;
					return "As soon as you open the chest, you hear sounds of an ominous mechanism. "
							+ "Only seconds later you find your demise through a trap which you foolishly triggered.";
				}
				else {//Kick
					if(timesKicked < 3) {
						timesKicked++;
						return "You kick the chest hard! Nothing happens.";
					}
					trapTriggered = true;
					return "You kick the chest hard and suddenly hear sounds of an ominous mechanism. "
							+ "Only seconds later you find your demise through a trap which you foolishly triggered.";
				}
			}
			else if(materialCorners != 2 && materialFrame != 2 && materialStraps != 2) {
				if(action == 0) {//Open
					success = true;
					return getSuccess();
				}
				else {//Kick
					trapTriggered = true;
					return "You kick the chest hard and suddenly hear sounds of an ominous mechanism. "
							+ "Only seconds later you find your demise through a trap which you foolishly triggered.";
				}
			}
			else {
				if(action == 0) {//Open
					if(timesKicked == 1) {
						success = true;
						return getSuccess();
					}
					trapTriggered = true;
					return "As soon as you open the chest, you hear sounds of an ominous mechanism. "
							+ "Only seconds later you find your demise through a trap which you foolishly triggered.";
				}
				else {//Kick
					if(timesKicked < 1) {
						timesKicked++;
						return "You kick the chest hard! Nothing happens.";
					}
					trapTriggered = true;
					return "You kick the chest hard and suddenly hear sounds of an ominous mechanism. "
							+ "Only seconds later you find your demise through a trap which you foolishly triggered.";
				}
			}
		}
		else {
			int action = 0;
			if(command.equalsIgnoreCase("turn")) {
				action = 0;
			}
			else if(command.equalsIgnoreCase("wiggle")) {
				action = 1;
			}
			else if(command.equalsIgnoreCase("poke")) {
				action = 2;
			}
			else {
				return null;
			}
			if(action != lockPickSound) {
				timesToPickLock--;
				if(timesToPickLock <= 0) {
					success = true;
					return "You move the lockpick and suddenly the lock snaps open! " + getSuccess();
				}
				return "You move the lockpick and hear a " + getLockpickAction() + ".";
			}
			trapTriggered = true;
			return "You move the lockpick and suddenly there are sounds of an ominous mechanism. "
					+ "Only seconds later you find your demise through a trap which you foolishly triggered.";
		}
	}
	
	private String getSuccess() {
		return "The chest opens and reveals its marvealous treasures to you! You did it! Good job!";
	}
	
	private String getLockpickAction() {
		lockPickSound = Randomizer.getRNGNumber(0, 3);
		switch(lockPickSound) {
		case 0:
			return "click";
		case 1:
			return "rattle";
		default:
			return "squeak";
		}
	}

	@Override
	public boolean isSolved() {
		return success || trapTriggered;
	}
	
	public boolean isFailed(){
		return trapTriggered;
	}
	
	private String getMaterial(int nr) {
		switch(nr) {
		case 0:
			return "wooden";
		case 1:
			return "iron";
		default:
			return "stone";
		}
	}

}
