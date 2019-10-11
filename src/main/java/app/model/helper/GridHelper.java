package app.model.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import app.model.Grid;
import app.model.Key;
import app.model.Word;
import app.repository.WordRepository;
import app.utils.AppFiles;

public class GridHelper {

	private final static Logger LOGGER = Logger.getLogger(GridHelper.class);

	public static Grid loadGrid(String filename) {

		String gridPath = "/grids/" + filename.trim();
		List<String> stringGrid = AppFiles.getResourceAsLines(gridPath);
		return new Grid(stringGrid);
	}

	public static void solveGrid(Grid grid, WordRepository repository) {

		LOGGER.info("");
		LOGGER.info("Solving grid...");
		
		List<Key> keys = new ArrayList<Key>();
		boolean solveInit = true;
		boolean uniqueKey = false;

		for (Map.Entry<String, String> entry : grid.getWordsToDecode().entrySet()) {
			if (entry.getValue().contains("?")) {
				String numericalRelativeCrypto = entry.getKey();
				for (Word w : repository
						.findByRelativeCrypto(WordHelper.numericalToAlphabetic(numericalRelativeCrypto))) {

					String databaseWord = w.getWord();
					boolean mergeOk = false;
					if (solveInit) {
						Key tempKey = new Key(numericalRelativeCrypto, databaseWord);
						keys.add(tempKey);
					} else {
						for (Key key : keys) {
							mergeOk = key.mergeResult(numericalRelativeCrypto, databaseWord);
						}
					}
					if (uniqueKey && mergeOk) {
						break;
					}
				}

				keys.sort(Comparator.comparing(Key::getValidMerges).reversed());
				keys = purgeKeys(keys);
				if (keys.size() == 1) {
					uniqueKey = true;
					grid.setSolutionKey(keys.get(0));
					grid.updateWordsToDecode();
				}
				if (solveInit) {
					solveInit = false;
				}
			}

		}

		// printing results
		grid.printSolution();

	}

	private static List<Key> purgeKeys(List<Key> keys) {

		int bestValidMerges = keys.get(0).getValidMerges();
		List<Key> purgedKeys = new ArrayList<Key>();

		for (Key key : keys) {
			if (key.getValidMerges() == bestValidMerges) {
				purgedKeys.add(key);
			}

		}

		return purgedKeys;

	}

	public static String cleanNrc(String nrc) {

		String rval = nrc.trim();
		rval = rval.replaceAll("[-+.^:,@&]", "");
		rval = rval.replaceAll("  ", " ");

		LOGGER.debug("Cleaned nrc [" + nrc + "] to [" + rval + "]");

		return rval;
	}

}
