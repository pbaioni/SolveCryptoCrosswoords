package app.service;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.model.Grid;
import app.model.Word;
import app.model.helper.GridHelper;
import app.model.helper.WordHelper;
import app.repository.WordRepository;

/** 
 * Service layer.
 * Specify transactional behavior and mainly
 * delegate calls to Repository.
 */
@Component
public class WordService {
	
	private final static Logger LOGGER = Logger.getLogger(WordService.class);

	@Autowired
	private WordRepository wordRepository;

	@Transactional
	public void addOne(Word word) {
		wordRepository.save(word);
		LOGGER.debug(word + " saved in the database");
	}

	@Transactional
	public void addAll(Collection<Word> words) {
		LOGGER.info("Saving " + words.size() + " in the database");
		wordRepository.save(words);
	}
	
	@Transactional(readOnly=true)
	public Word findOne(String word) {
		Word w = wordRepository.findOne(word);
		LOGGER.info(w);
		return w;
	}
	
	@Transactional(readOnly=true)
	public List<Word> findAll() {
		LOGGER.info("Recovering all database entries...");
		return wordRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<Word> findByLength(String length) {
		return wordRepository.findByLength(length);
	}
	
	@Transactional(readOnly=true)
	public List<Word> findByRelativeCrypto(String relativeCrypro) {
		List<Word> results = wordRepository.findByRelativeCrypto(relativeCrypro);
		for(Word w : results) {
			LOGGER.info(w);
		}
		LOGGER.info(results.size() + " results found in database");
		return results;
	}
	
	public void reverseNumericalCrypto(String numericalCrypto){
		findByRelativeCrypto(WordHelper.numericalToAlphabetic(numericalCrypto));
	}

	public void loadGrid(String filename) {
		GridHelper.solveGrid(new Grid(filename), wordRepository);
	}
	

}
