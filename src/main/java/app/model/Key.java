package app.model;

import java.util.TreeMap;

import org.apache.log4j.Logger;

import app.model.helper.GridHelper;
import app.service.WordService;

public class Key {
	private final static Logger LOGGER = Logger.getLogger(Key.class);

	TreeMap<Integer, String> keyMap;

	int validMerges = 0;

	public Key() {
		initKeyMap();
	}

	public Key(String numericalRelativeCrypto, String word) {
		initKeyMap();
		mergeResult(numericalRelativeCrypto, word);
	}

	private void initKeyMap() {
		keyMap = new TreeMap<>();
		for (int i = 1; i <= 26; i++) {
			keyMap.put(i, "?");
		}
	}

	public boolean mergeResult(String numericalRelativeCrypto, String wordResult) {

		String nrc = GridHelper.cleanNrc(numericalRelativeCrypto);

		LOGGER.debug("Merging [" + nrc + "=" + wordResult + "],  starting key:");
		LOGGER.debug(getKeyAsString());
		if (isCompatibleResult(nrc, wordResult)) {
			String[] numbers = nrc.split(" ");
			for (int i = 0; i < wordResult.length(); i++) {
				keyMap.put(Integer.parseInt(numbers[i]), String.valueOf(wordResult.charAt(i)));
			}

			LOGGER.debug("End key:");
			LOGGER.debug(getKeyAsString());
			validMerges++;
			return true;
		}
		return false;
	}

	public boolean isCompatibleResult(String nrc, String wordResult) {
		boolean rval = true;
		String[] numbers = nrc.split(" ");
		for (int i = 0; i < wordResult.length(); i++) {
			char c = wordResult.charAt(i);
			String mapValue = keyMap.get(Integer.parseInt(numbers[i]));

			if (!mapValue.equals(String.valueOf(c)) && !mapValue.equals("?")) {
				LOGGER.debug("Incompatible result, merge aborted");
				rval = false;
				break;
			}

		}
		return rval;
	}

	public String getLetterForNumber(String numberAsString) {
		if (numberAsString.equals("-")) {
			return numberAsString;
		} else {
			return keyMap.get(Integer.parseInt(numberAsString));
		}
	}

	public int getValidMerges() {
		return validMerges;
	}

	public String getKeyAsString() {
		StringBuilder builder = new StringBuilder();
		for (String value : keyMap.values()) {
			builder.append(value);
		}

		return builder.toString();
	}

	@Override
	public String toString() {
		String KeyAsString = "Merges: " + validMerges + ", key:";
		for (Integer key : keyMap.keySet()) {
			KeyAsString += String.valueOf(key) + "=" + keyMap.get(key) + ", ";
		}
		return KeyAsString;
	}
}
