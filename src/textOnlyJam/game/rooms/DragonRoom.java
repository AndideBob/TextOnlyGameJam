package textOnlyJam.game.rooms;

import lwjgladapter.utils.Randomizer;

public class DragonRoom extends Room {

	private int dominantType;
	private int dragonTypeA;
	private int dragonTypeB;
	
	private int scaleType;
	private int eyeType;
	private int hornType;
	private int clawType;
	private int wingType;
	
	private int distance;
	private boolean firstMove;
	private int lastDragonAction;
	private int secondToLastDragonAction;
	private boolean hasRoared;
	private boolean hasSpitFire;
	
	private boolean failed;
	private boolean success;
	
	public DragonRoom() {
		dragonTypeA = Randomizer.getRNGNumber(0, 4);
		dragonTypeB = Randomizer.getRNGNumber(0, 4);
		distance = Randomizer.getRNGNumber(3, 7);
		lastDragonAction = -1;
		secondToLastDragonAction = -1;
		hasRoared = false;
		hasSpitFire = false;
		failed = false;
		success = false;
		firstMove = true;
		int typeASelected = 0;
		for(int i = 0; i < 5; i++){
			boolean typeA = Randomizer.getRNGBoolean();
			typeASelected = typeA ? typeASelected + 1 : typeASelected;
			switch(i){
			case 0:
				scaleType = typeA ? dragonTypeA : dragonTypeB;
				break;
			case 1:
				eyeType = typeA ? dragonTypeA : dragonTypeB;
				break;
			case 2:
				hornType = typeA ? dragonTypeA : dragonTypeB;
				break;
			case 3:
				clawType = typeA ? dragonTypeA : dragonTypeB;
				break;
			case 4:
				wingType = typeA ? dragonTypeA : dragonTypeB;
				break;
			}
		}
		dominantType = (typeASelected > 2) ? dragonTypeA : dragonTypeB;
	}

	@Override
	public String getDescription() {
		StringBuilder description = new StringBuilder();
		description.append("The moment you enter this room you know something is off. A roar echoes through it! ");
		description.append("Ahead of you is a giant dragon! It has " + getScaleType() + " scales and " + getEyeType() + " eyes. ");
		description.append("Its " + getWingType() + " are spread out. It has " + getHornsType() + " horns and " + getClawType() + " claws. ");
		description.append("Proceed with caution.");
		return description.toString();
	}

	@Override
	protected String handleSpecific(String command) {
		if(distance > 0){
			int action = 0;
			if(command.equalsIgnoreCase("sneak")){
				action = 0;
			}
			else if(command.equalsIgnoreCase("run")){
				action = 1;
			}
			else if(command.equalsIgnoreCase("roll")){
				action = 2;
			}
			else{
				return null;
			}
			if(firstMove){
				return getDragonReaction();
			}
			switch(dominantType){
			case 0://Fire
				switch(lastDragonAction){
				case 0://If roar
					if(action == 0){
						distance--;
						return getDragonReaction();
					}
					else{
						failed = true;
						return getDeathReaction();
					}
				case 1:
					if(action == 1 && secondToLastDragonAction != 2){
						distance--;
						return getDragonReaction();
					}
					else if(action == 2 && secondToLastDragonAction == 2){
						distance--;
						return getDragonReaction();
					}
					else{
						failed = true;
						return getDeathReaction();
					}
				default:
					if(action == 2){
						distance--;
						return getDragonReaction();
					}
					else{
						failed = true;
						return getDeathReaction();
					}
				}
			case 1://Water
				switch(lastDragonAction){
				case 0://If roar
					if(action == 1 && secondToLastDragonAction != 0){
						distance--;
						return getDragonReaction();
					}
					else if(action == 0 && secondToLastDragonAction == 0){
						distance--;
						return getDragonReaction();
					}
					else{
						failed = true;
						return getDeathReaction();
					}
				case 1:
					if(action == 2){
						distance--;
						return getDragonReaction();
					}
					else{
						failed = true;
						return getDeathReaction();
					}
				default:
					if(action == 1 && !hasRoared){
						distance--;
						return getDragonReaction();
					}
					else if(action == 0 && hasRoared){
						distance--;
						return getDragonReaction();
					}
					else{
						failed = true;
						return getDeathReaction();
					}
				}
			case 2://Earth
				if(secondToLastDragonAction == 0){
					if(action == lastDragonAction){
						distance--;
						return getDragonReaction();
					}
					else{
						failed = true;
						return getDeathReaction();
					}
				}
				else{
					if(action == 0){
						distance--;
						return getDragonReaction();
					}
					else{
						failed = true;
						return getDeathReaction();
					}
				}
			default://Ice
				switch(lastDragonAction){
				case 0://If roar
					if(action == (hasSpitFire ? 2 : 1)){
						distance--;
						return getDragonReaction();
					}
					else{
						failed = true;
						return getDeathReaction();
					}
				case 1:
					if(action == (hasSpitFire ? 1 : 2)){
						distance--;
						return getDragonReaction();
					}
					else{
						failed = true;
						return getDeathReaction();
					}
				default:
					if(action == 0){
						distance--;
						return getDragonReaction();
					}
					else{
						failed = true;
						return getDeathReaction();
					}
				}
			}
		}
		else{
			String[] parts = command.split(" ");
			if(parts.length == 2 && parts[0].equalsIgnoreCase("strike")){
				int partStruck = 0;
				if(parts[1].equalsIgnoreCase("heart")){
					partStruck = 0;
				}
				else if(parts[1].equalsIgnoreCase("head")){
					partStruck = 1;
				}
				else if(parts[1].equalsIgnoreCase("belly")){
					partStruck = 2;
				}
				else{
					return null;
				}
				if((dragonTypeA == 0 && dragonTypeB == 3) ||
						(dragonTypeA == 3 && dragonTypeB == 0)){
					if(partStruck == 1){
						success = true;
						return getKillReaction();
					}
					failed = true;
					return getDeathReaction();
				}
				else if(dragonTypeA == dragonTypeB){
					if(partStruck == 0){
						success = true;
						return getKillReaction();
					}
					failed = true;
					return getDeathReaction();
				}
				else if(scaleType == 2){
					if(partStruck == 2){
						success = true;
						return getKillReaction();
					}
					failed = true;
					return getDeathReaction();
				}
				else if(eyeType != 2){
					if(partStruck == 1){
						success = true;
						return getKillReaction();
					}
					failed = true;
					return getDeathReaction();
				}
				else if(dominantType == 1){
					if(partStruck == 2){
						success = true;
						return getKillReaction();
					}
					failed = true;
					return getDeathReaction();
				}
				else{
					if(partStruck == 0){
						success = true;
						return getKillReaction();
					}
					failed = true;
					return getDeathReaction();
				}
			}
		}
		return null;
	}
	
	private String getKillReaction(){
		return "The dragon roars in pain as your strike hits it. Moments later it does no longer move. You have bested the beast and move on!";
	}
	
	private String getDeathReaction(){
		return "You missjudged your actions and the dragon makes easy work of you. A good snack for a glorious beast!";
	}
	
	private String getDragonReaction(){
		if(distance <= 0){
			return "You have made it into striking distance to the beast! It is time to land the final blow!";
		}
		else{
			secondToLastDragonAction = lastDragonAction;
			lastDragonAction = Randomizer.getRNGNumber(0, 3);
			switch(lastDragonAction){
			case 0:
				hasRoared = true;
				return "The dragon roars!";
			case 1:
				return "The dragon swipes at you!";
			default:
				hasSpitFire = true;
				return "The dragon spits fire!";
			}
		}
	}

	@Override
	public boolean isSolved() {
		// TODO Auto-generated method stub
		return failed || success;
	}
	
	public boolean isFailed(){
		return failed;
	}
	
	private String getScaleType(){
		switch(scaleType){
		case 0:
			return "red";
		case 1:
			return "blue";
		case 2:
			return "brown";
		default:
			return "white";
		}
	}
	
	private String getEyeType(){
		switch(eyeType){
		case 0:
			return "green";
		case 1:
			return "yellow";
		case 2:
			return "red";
		default:
			return "blue";
		}
	}
	
	private String getHornsType(){
		switch(hornType){
		case 0:
			return "no";
		case 1:
			return "pointy";
		case 2:
			return "twisted";
		default:
			return "stubby";
		}
	}
	
	private String getClawType(){
		switch(clawType){
		case 0:
			return "sharp";
		case 1:
			return "short";
		case 2:
			return "rough";
		default:
			return "hooked";
		}
	}
	
	private String getWingType(){
		switch(wingType){
		case 0:
			return "leathery";
		case 1:
			return "finned";
		case 2:
			return "tiny";
		default:
			return "crystal";
		}
	}

}
