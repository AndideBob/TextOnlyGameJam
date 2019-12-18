package textOnlyJam.game.text;

import java.util.ArrayList;

import lwjgladapter.input.KeyboardKey;

public class GameTextUtility {
	
	public static final int lettersPerSecond = 20;

	public static String[] splitToLines(String text, int lineLength){
		ArrayList<String> lines = new ArrayList<>();
		
		String[] words = text.split(" ");
		String line = "";
		for(String word : words){
			if(line.length() + word.length() + 1 > lineLength){
				lines.add(line);
				line = "";
			}
			line += word + (word.contains(".") ? "" : " ");
			if(word.contains("\n")){
				lines.add(line);
				line = "";
			}
		}
		if(!line.isEmpty()){
			lines.add(line);
		}
		String[] result = new String[lines.size()];
		return lines.toArray(result);
	}
	
	public static String convertKeyboardKeyToChar(KeyboardKey key){
		switch(key){
		case KEY_NUM0:
		case KEY_0:
			return "0";
		case KEY_NUM1:
		case KEY_1:
			return "1";
		case KEY_NUM2:
		case KEY_2:
			return "2";
		case KEY_NUM3:
		case KEY_3:
			return "3";
		case KEY_NUM4:
		case KEY_4:
			return "4";
		case KEY_NUM5:
		case KEY_5:
			return "5";
		case KEY_NUM6:
		case KEY_6:
			return "6";
		case KEY_NUM7:
		case KEY_7:
			return "7";
		case KEY_NUM8:
		case KEY_8:
			return "8";
		case KEY_NUM9:
		case KEY_9:
			return "9";
		case KEY_A:
			return "a";
		case KEY_B:
			return "b";
		case KEY_C:
			return "c";
		case KEY_D:
			return "d";
		case KEY_E:
			return "e";
		case KEY_F:
			return "f";
		case KEY_G:
			return "g";
		case KEY_H:
			return "h";
		case KEY_I:
			return "i";
		case KEY_J:
			return "j";
		case KEY_K:
			return "k";
		case KEY_L:
			return "l";
		case KEY_M:
			return "m";
		case KEY_N:
			return "n";
		case KEY_O:
			return "o";
		case KEY_P:
			return "p";
		case KEY_Q:
			return "q";
		case KEY_R:
			return "r";
		case KEY_S:
			return "s";
		case KEY_T:
			return "t";
		case KEY_U:
			return "u";
		case KEY_V:
			return "v";
		case KEY_W:
			return "w";
		case KEY_X:
			return "x";
		case KEY_Y:
			return "y";
		case KEY_Z:
			return "z";
		case KEY_SPACE:
			return " ";
		default:
			return "";
		}
	}

}
