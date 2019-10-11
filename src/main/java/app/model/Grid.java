package app.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import org.apache.log4j.Logger;


public class Grid {

	private final static Logger LOGGER = Logger.getLogger(Grid.class);

	private List<String> cryptoRows;

	private Map<String, String> wordsToDecode;

	private boolean hasWordsToDecode;

	private Map.Entry<String, String> nextWordToDecode;

	private List<String> solutionRows;

	private Key solutionKey;

	public Grid(List<String> cryptoRows) {

		this.cryptoRows = cryptoRows;

		wordsToDecode = new TreeMap<String, String>(new Comparator<String>() {
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


		LOGGER.info("Grid loaded");
		printNumericalGrid(cryptoRows);
		
		solutionKey = new Key();
		fillWordsToDecode(this.cryptoRows);
		updateWordsToDecode();
	}

	private void fillWordsToDecode(List<String> cryptoRows) {

		for (String row : cryptoRows) {

			for (String s : row.split("-")) {
				String numericalRelativeCrypto = s.trim();
				String word = decodeWord(numericalRelativeCrypto, "");
				if (word.length() > 3) {
					for (int i = 1; i < word.length() + 1; i++) {
						wordsToDecode.put(numericalRelativeCrypto, word);
					}
				}
			}

		}
	}

	public void calculateSolution() {

		for (String row : cryptoRows) {
			solutionRows.add(decodeWord(row, " "));
		}
	}

	private String decodeWord(String numericalRelativeCrypto, String separator) {
		Scanner scanner = new Scanner(numericalRelativeCrypto);
		StringBuilder builder = new StringBuilder();
		while (scanner.hasNext()) {
			builder.append(solutionKey.getLetterForNumber(scanner.next()) + separator);
		}
		scanner.close();
		return builder.toString();
	}

	public void updateWordsToDecode() {
		for (Map.Entry<String, String> entry : wordsToDecode.entrySet()) {
			wordsToDecode.put(entry.getKey(), decodeWord(entry.getKey(), ""));
		}
		this.hasWordsToDecode = false;
		this.nextWordToDecode = null;
		for (Map.Entry<String, String> entry : wordsToDecode.entrySet()) {
			if(entry.getValue().contains("?"))
			{
				this.hasWordsToDecode = true;
				this.nextWordToDecode = entry;
				break;
			}
		}
	}

	public void printWordsToDecode() {
		LOGGER.info("Words:");
		for (Map.Entry<String, String> entry : wordsToDecode.entrySet()) {
			LOGGER.info(entry.getKey() + " : " + entry.getValue().toString());
		}
	}

	private void printNumericalGrid(List<String> list) {
		for (String row : list) {
			LOGGER.info(row);
		}
	}

	public void printSolution() {
		solutionRows = new ArrayList<String>();
		calculateSolution();
		LOGGER.info("");
		LOGGER.info("Solution key:");
		LOGGER.info(solutionKey.toString());
		LOGGER.info("");
		printWordsToDecode();
		LOGGER.info("");
		String space = "   ";
		LOGGER.info(space + "Solution:");
		LOGGER.info(space + "-----------------------------");
		for (String row : solutionRows) {
			LOGGER.info(space + "| " + row + "|");
		}
		LOGGER.info(space + "-----------------------------");
	}

	public Map<String, String> getWordsToDecode() {
		return wordsToDecode;
	}

	public boolean hasWordToDecode() {

		return hasWordsToDecode;
	}

	public Entry<String, String> getNextWordToDecode() {

		return nextWordToDecode;
	}

	public void setSolutionKey(Key solutionKey) {
		this.solutionKey = solutionKey;
	}
}
