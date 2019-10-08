package app.model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import app.model.helper.GridHelper;
import app.service.WordService;

public class Grid {

	private final static Logger LOGGER = Logger.getLogger(WordService.class);

	private Map<String, String> decodeMap;

	private String decodeKey;

	public Grid(List<String> grid) {

		decodeMap = new TreeMap<String, String>(new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				if (s1.length() > s2.length()) {
					return -1;
				} else if (s1.length() < s2.length()) {
					return 1;
				} else {
					return s1.compareTo(s2);
				}
			}
		});

		decodeKey = "??????????????????????????";

		fillDecodeMap(grid);

	}

	private void fillDecodeMap(List<String> grid) {

		for (String row : grid) {

			for (String s : row.split("-")) {
				String numericalRelativeCrypto = s.trim();
				String word = "";

				int wordLength = numericalRelativeCrypto.split(" ").length;
				if (wordLength > 6) {
					for (int i = 1; i < wordLength + 1; i++) {
						word += "?";
					}

					decodeMap.put(numericalRelativeCrypto, word);
				}
			}

		}

		GridHelper.printMap(decodeMap);

	}

	public Map<String, String> getDecodeMap() {
		return decodeMap;
	}

	public void setDecodeMap(Map<String, String> decodeMap) {
		this.decodeMap = decodeMap;
	}

	public String getDecodeKey() {
		return decodeKey;
	}

	public void setDecodeKey(String decodeKey) {
		this.decodeKey = decodeKey;
	}

}
