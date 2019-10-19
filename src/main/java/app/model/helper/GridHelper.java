package app.model.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import app.model.Grid;
import app.model.Key;
import app.model.Word;
import app.model.properties.GridProperties;
import app.repository.WordRepository;
import app.utils.AppFiles;

public class GridHelper {

	private final static Logger LOGGER = Logger.getLogger(GridHelper.class);

	public static void solveGrid(Grid grid, WordRepository repository, GridProperties gridProperties) {

		LOGGER.info("");
		LOGGER.info("Solving grid...");
		
		//list containing the possible solution keys
		List<Key> keys = new ArrayList<Key>();
		boolean solveInit = true;
		boolean uniqueKey = false;

		//iterating on all the encrypted words to decode
		//starting with the longest encrypted word to find (length ordered TreeMap), most likely the one with the lowest number of entries in the database
		for (Map.Entry<String, String> entry : grid.getWordsToDecode().entrySet()) {
			//checking if we already have a solution for the word to decode (case of all common characters with previous words)
			if (entry.getValue().contains(gridProperties.getUnknownCharacter())) {

				String numericalRelativeCrypto = entry.getKey();
				for (Word w : repository
						.findByRelativeCrypto(WordHelper.numericalToAlphabetic(numericalRelativeCrypto))) {

					String databaseWord = w.getWord();
					boolean mergeOk = false;
					if (solveInit) {
						//it is the first step, we must add any possible solution
						Key tempKey = new Key(gridProperties, numericalRelativeCrypto, databaseWord);
						keys.add(tempKey);
					} else {
						//for the further steps, we try to merge the database results into the existing keys
						//the right key (correct solution) should have almost one valid merge per step
						for (Key key : keys) {
							mergeOk = key.mergeResult(numericalRelativeCrypto, databaseWord);
						}
					}
					//the only possible compatible merge has already been performed, avoiding next unnecessary incompatible merges
					//especially useful for short words (lots of results)
					if (uniqueKey && mergeOk) {
						break;
					}
				}

				//putting at the top of the list the key with the highest number of valid merges (probable solution)
				keys.sort(Comparator.comparing(Key::getValidMerges).reversed());
				
				//discarding the keys with lower number of valid merges
				keys = purgeKeys(keys);
				
				//the algorithm has converged to only one possible solution key (but still incomplete)
				if (!uniqueKey && keys.size() == 1) {
					//this boolean will later allow to avoid unnecessary incompatible merges into the key
					uniqueKey = true;

				}
				if(uniqueKey) {
					//updating the grid key with new results
					grid.setSolutionKey(keys.get(0));
					//decoding the grid words with the new available characters in the key
					grid.updateWordsToDecode();
				}
				if (solveInit) {
					//after the first step, we don't want to create new possible keys anymore
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
			if (key.getValidMerges() >= bestValidMerges - 1) {
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
