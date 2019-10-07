package app.model.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import app.model.Grid;
import app.model.Word;
import app.repository.WordRepository;
import app.utils.AppFiles;

public class GridHelper {

	private final static Logger LOGGER = Logger.getLogger(WordHelper.class);

	public static Grid loadGrid(String filename) {
		String gridPath = "/grids/" + filename.trim() + ".txt";
		List<String> stringGrid = AppFiles.getResourceAsLines(gridPath);
		for (String row : stringGrid) {
			System.out.println(row);
		}

		return new Grid(stringGrid);
	}

	public static void printMap(Map<String, String> decodeMap) {
		for (Map.Entry<String, String> entry : decodeMap.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue().toString());
		}
	}

	public static void solveGrid(Grid grid, WordRepository repository) {

		List<String> tempKeys = new ArrayList<String>();
		boolean init = true;

		for (Map.Entry<String, String> entry : grid.getDecodeMap().entrySet()) {

			String numericalRelativeCrypto = entry.getKey();
			// System.out.println(entry.getKey() + " : " + entry.getValue().toString());
			for (Word w : repository.findByRelativeCrypto(WordHelper.numericalToAlphabetic(numericalRelativeCrypto))) {
				String tempKey = computeKeyFromNrc(numericalRelativeCrypto, w.getWord());
				if(init) {
					
					tempKeys.add(tempKey);
					
				} else { 
					
					tempKeys = mergeKeys(tempKeys, tempKey);
					
				}
			}
			
			init = false;

		}
		
		System.out.println("Key: " + tempKeys.get(0));

	}

	public static String computeKeyFromNrc(String numericalRelativeCrypto, String word) {

		String key = "??????????????????????????";
		String[] letters = numericalRelativeCrypto.split(" ");
		for (int i = 0; i < letters.length; i++) {
			int index = Integer.parseInt(letters[i].trim());
			String character = word.substring(i, i+1);
			key = putCharacter(key, character, index);
		}

		return key;
	}


	public static boolean areCompatibleKeys(String decodeKey, String tempKey) {
		
		if (decodeKey.length() != tempKey.length()) {
			return false;
		}

		boolean compatibility = true;
		for (int i = 0; i < decodeKey.length(); i++) {
			String char1 = decodeKey.substring(i, i + 1);
			String char2 = tempKey.substring(i, i + 1);
			if (!char1.equals("?") && !char2.equals("?")) {
				if (!char1.equals(char2)) {
					compatibility = false;
				}
			}
		}
		return compatibility;
	}

	public static List<String> mergeKeys(List<String> tempKeys, String tempKey) {

		
		// key1   = "?a??????????r??????t??????";
		// key2   = "??f????????????i???t??????";
		// merged = "?af?????????r??i???t??????";
		
		List<String> mergedKeys = new ArrayList<String>();
		
		for (String key : tempKeys) {
			if (areCompatibleKeys(key, tempKey)) {
				//merging tempKey into key
				String mergedKey = key;
				for (int i = 0; i < key.length(); i++) {
					String char2 = tempKey.substring(i, i + 1);
					if (!char2.equals("?")) {
						mergedKey = putCharacter(mergedKey, char2, i);
					}
				}
				mergedKeys.add(mergedKey);
			}
		}
		
		return mergedKeys;
	}
	
	private static String putCharacter(String key, String character, int index) {
		String before = key.substring(0, index);
		String after = key.substring(index+1, key.length());
		return before + character + after;
	}

}
