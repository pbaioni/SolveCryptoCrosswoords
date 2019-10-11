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
		
		String gridPath = "/grids/" + filename.trim() + ".dat";
		List<String> stringGrid = AppFiles.getResourceAsLines(gridPath);
		return new Grid(stringGrid);
	}

	
	public static void solveGrid(Grid grid, WordRepository repository) {

		List<Key> keys = new ArrayList<Key>();
		boolean init = true;

		for (Map.Entry<String, String> entry : grid.getDecodeMap().entrySet()) {
			String numericalRelativeCrypto = entry.getKey();
			for (Word w : repository.findByRelativeCrypto(WordHelper.numericalToAlphabetic(numericalRelativeCrypto))) {
				
				if (init) {
					Key tempKey = new Key(numericalRelativeCrypto, w.getWord());
					keys.add(tempKey);
				} else {
					for (Key key : keys) {
						boolean mergeOk = key.mergeResult(numericalRelativeCrypto, w.getWord());
						
					}
				}
			}

			init = false;
			keys.sort(Comparator.comparing(Key::getValidMerges).reversed());
			keys = purgeKeys(keys);

		}
		
		//printing results
		LOGGER.info("Solution key:");
		LOGGER.info(keys.get(0).toString());
		LOGGER.info("");
		
		grid.setSolutionKey(keys.get(0));
		
	}
	
	
	private static List<Key> purgeKeys(List<Key> keys) {
		
		int bestValidMerges = keys.get(0).getValidMerges();
		List<Key> purgedKeys = new ArrayList<Key>();

		for(Key key : keys) {
			if(key.getValidMerges() >= bestValidMerges-1) {
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
	
	
	public static void printMap(Map<String, String> decodeMap) {
		LOGGER.info("Cryptos:");
		for (Map.Entry<String, String> entry : decodeMap.entrySet()) {
			LOGGER.info(entry.getKey() + " : " + entry.getValue().toString());
		}
	}
}
