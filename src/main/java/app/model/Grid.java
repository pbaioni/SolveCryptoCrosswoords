package app.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import app.model.helper.GridHelper;
import app.service.WordService;

public class Grid {

	private final static Logger LOGGER = Logger.getLogger(Grid.class);

	private List<String> cryptoRows;

	private List<String> solutionRows;

	private Key solutionKey;

	private Map<String, String> decodeMap;

	public Grid(List<String> gridRows) {

		this.cryptoRows = gridRows;

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

		fillDecodeMap(this.cryptoRows);

		solutionKey = new Key();

		LOGGER.info("Grid loaded");
		printGrid(gridRows);

	}

	private void fillDecodeMap(List<String> gridRows) {

		for (String row : gridRows) {

			for (String s : row.split("-")) {
				String numericalRelativeCrypto = s.trim();
				String word = "";

				int wordLength = numericalRelativeCrypto.split(" ").length;
				if (wordLength > 3) {
					for (int i = 1; i < wordLength + 1; i++) {
						word += "?";
					}

					decodeMap.put(numericalRelativeCrypto, word);
				}
			}

		}

		GridHelper.printMap(decodeMap);

	}
	
	public void calculateSolution() {

		for (String row : cryptoRows) {
			
			Scanner scanner = new Scanner(row);
			StringBuilder builder = new StringBuilder();
			while(scanner.hasNext()) {
				String numberAsString = scanner.next();
				if (!numberAsString.equals("-")) {
					int number = Integer.parseInt(numberAsString);
					String letter = solutionKey.getLetterForNumber(number);
					builder.append(letter);
				}
				else {
					builder.append("-");
				}
				builder.append(" ");
			}

			solutionRows.add(builder.toString());
		}
	}

	private void printGrid(List<String> list) {
		for (String row : list) {
			LOGGER.info(row);
		}
	}

	public Map<String, String> getDecodeMap() {
		return decodeMap;
	}

	public void setDecodeMap(Map<String, String> decodeMap) {
		this.decodeMap = decodeMap;
	}

	public Key getSolutionKey() {
		return solutionKey;
	}

	public void setSolutionKey(Key solutionKey) {
		this.solutionKey = solutionKey;
		solutionRows = new ArrayList<String>();
		calculateSolution();
		printSolution(solutionRows);
	}

	private void printSolution(List<String> solution2) {
		String space = "   ";
		LOGGER.info(space + "Solution:");
		LOGGER.info(space + "-----------------------------");
		for (String row : solutionRows) {
			LOGGER.info(space + "| " + row + " |");
		}
		LOGGER.info(space + "-----------------------------");
	}

}
