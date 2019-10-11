package app.main;

import org.apache.log4j.Logger;

import app.controller.CommandController;
import app.controller.DictionnaryController;
import app.main.properties.ApplicationProperties;
import app.service.WordService;

public class Application {

	private final static Logger LOGGER = Logger.getLogger(Application.class);

	private ApplicationProperties appProperties;

	private WordService wordService;

	public Application(WordService wordService) {
		appProperties = new ApplicationProperties("application.properties");
		this.wordService = wordService;
	}

	public void start() {

		if (appProperties.isCreatDB()) {
			DictionnaryController dictionnaryCreator = new DictionnaryController(wordService);
			dictionnaryCreator.createDictionnary();
		}

		if (appProperties.isLaunchInlineCommands()) {
			CommandController commandController = new CommandController(wordService);
			commandController.run();
		}

		//load and solve example 1
		wordService.loadGrid("grid1.dat");

		//load and solve example 2
		wordService.loadGrid("grid2.dat");

	}

}
