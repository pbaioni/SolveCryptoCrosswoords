package app.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import app.model.Word;
import app.service.WordService;

public class DictionnaryController {

	private final static Logger LOGGER = Logger.getLogger(DictionnaryController.class);

	private WordService wordService;

	public DictionnaryController(WordService wordService) {
		super();
		this.wordService = wordService;
	}

	public void createDictionnary() {

		Date creationBegin = new Date();
		LOGGER.info("Starting creating dictionnary");

		// iterating on all the alphabet letters
		for (int i = 0; i <= 25; i++) {

			Date begin = new Date();
			List<Word> singleLetterDictionnary = new ArrayList<Word>();
			try {
				// reading the single letter dictionnary file (ex: ITALIANO.f)
				String fileName = "./languages/italian/ITALIANO." + Character.toString((char) (65 + i));
				System.out.println("Processing file: " + fileName);
				BufferedReader br = new BufferedReader(
						new FileReader(getClass().getClassLoader().getResource(fileName).getFile()));
				String wordLine;
				while ((wordLine = br.readLine()) != null) {
					// adding the word to the dictionnary
					singleLetterDictionnary.add(new Word(cleanEntry(wordLine)));
				}

				// saving the single letter dictionnary to database
				wordService.addAll(singleLetterDictionnary);

				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Date end = new Date();
			LOGGER.info("Process Duration: " + (end.getSeconds() - begin.getSeconds()) + " seconds");
		}

		Date creationEnd = new Date();
		LOGGER.info("Dictionnary creation duration: " + (creationEnd.getSeconds() - creationBegin.getSeconds())
				+ " seconds");
	}

	private String cleanEntry(String line) {

		String regex = "^[a-z]*$";
		line = line.trim();
		line = line.replaceAll("-", "");
		line = line.replaceAll("/", "");
		line = line.replace(" ", "");
		line = line.toLowerCase();
		if (line.matches(regex)) {
			return line;
		} else {
			LOGGER.error("Not adding " + line);
			return "";
		}

	}

}
