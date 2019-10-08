package app.main;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.log4j.Logger;

import app.controller.CommandController;
import app.controller.DictionnaryController;
import app.main.properties.ApplicationProperties;
import app.service.WordService;

public class Application {

	private final static Logger LOGGER = Logger.getLogger(Application.class);

	private ApplicationProperties appProperties;

	private WordService wordService;

	private Executor executor;

	public Application(WordService wordService) {
		appProperties = new ApplicationProperties("application.properties");
		this.wordService = wordService;
		this.executor = new Executor() {

			@Override
			public void execute(Runnable command) {
				// TODO Auto-generated method stub

			}
		};
	}

	public void start() {

		if (appProperties.isCreatDB()) {
			DictionnaryController dictionnaryCreator = new DictionnaryController(wordService);
			dictionnaryCreator.createDictionnary();
			testRecovery();
		}

		if (appProperties.isLaunchInlineCommands()) {
			CommandController commandController = new CommandController(wordService);
			commandController.run();
		}
		
		wordService.loadGrid("1");

	}

	private void testRecovery() {
		LOGGER.info(wordService.findOne("abbattuto"));
		LOGGER.info(wordService.findOne("giocherellone"));
		LOGGER.info(wordService.findOne("hotel"));
		LOGGER.info(wordService.findOne("marittimo"));
		LOGGER.info(wordService.findOne("perdindirindina"));
		LOGGER.info(wordService.findOne("sussulto"));
		LOGGER.info(wordService.findOne("quadro"));
		LOGGER.info(wordService.findOne("zuzzurellone"));
		LOGGER.info(wordService.findOne("notinbase"));
	}
}
