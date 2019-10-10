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
		
		String gridPath = "/grids/" + filename.trim() + ".txt";
		List<String> stringGrid = AppFiles.getResourceAsLines(gridPath);
		return new Grid(stringGrid);
	}

	
	public static void solveGrid(Grid grid, WordRepository repository) {

		List<Key> initKeys = new ArrayList<Key>();
		boolean init = true;

		for (Map.Entry<String, String> entry : grid.getDecodeMap().entrySet()) {

			String numericalRelativeCrypto = entry.getKey();
			for (Word w : repository.findByRelativeCrypto(WordHelper.numericalToAlphabetic(numericalRelativeCrypto))) {
				
				if (init) {
					Key tempKey = new Key(numericalRelativeCrypto, w.getWord());
					initKeys.add(tempKey);
				} else {
					for (Key key : initKeys) {
						boolean mergeOk = key.mergeResult(numericalRelativeCrypto, w.getWord());
						
					}
				}
			}

			init = false;

		}
		
		//printing results
		initKeys.sort(Comparator.comparing(Key::getValidMerges).reversed());
		LOGGER.info("Solution key:");
		initKeys.get(0).printKey();
		LOGGER.info("");
		
		grid.setSolutionKey(initKeys.get(0));
		
	}
	
	
	public static String cleanNrc(String nrc) {
		
		String rval = nrc.trim();
		rval = rval.replaceAll("[-+.^:,@&]", "");
		rval = rval.replaceAll("  ", " ");

		LOGGER.debug("Cleaned nrc [" + nrc + "] to [" + rval + "]");

		return rval;
	}
	
	
	public static void printMap(Map<String, String> decodeMap) {
		LOGGER.info("Cryptos:");
		for (Map.Entry<String, String> entry : decodeMap.entrySet()) {
			LOGGER.info(entry.getKey() + " : " + entry.getValue().toString());
		}
	}
}
