package app.main;

import org.apache.log4j.Logger;

import app.controller.CommandController;
import app.controller.DictionnaryController;
import app.main.properties.ApplicationProperties;
import app.model.properties.GridProperties;
import app.service.GridService;
import app.service.WordService;

public class Application {

	private final static Logger LOGGER = Logger.getLogger(Application.class);

	private ApplicationProperties appProperties;

	private WordService wordService;
	
	private GridService gridService;

	public Application(ApplicationProperties appProperties, WordService wordService, GridService gridService) {
		this.appProperties = appProperties;
		this.wordService = wordService;
		this.gridService = gridService;
	}

	public void start() {

		if (appProperties.isCreatDB()) {
			DictionnaryController dictionnaryCreator = new DictionnaryController(wordService);
			dictionnaryCreator.createDictionnary();
		}

		if (appProperties.isLaunchInlineCommands()) {
			CommandController commandController = new CommandController(wordService, gridService);
			commandController.run();
		}

		//load and solve example 1
		gridService.loadGrid("grid1.dat");

		//load and solve example 2
		gridService.loadGrid("grid2.dat");
		
		//load and solve example 2
		gridService.loadGrid("grid3.dat");

	}

}
