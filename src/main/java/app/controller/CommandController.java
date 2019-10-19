package app.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import app.model.Word;
import app.service.GridService;
import app.service.WordService;

public class CommandController implements Runnable {

	private final static Logger LOGGER = Logger.getLogger(CommandController.class);

	private static WordService wordService;
	
	private static GridService gridService;

	private static boolean runInlineCommands = true;

	public CommandController(WordService wordService, GridService gridService) {
		CommandController.wordService = wordService;
		CommandController.gridService = gridService;
	}

	@Override
	public void run() {
		BufferedReader br = null;

		try {

			br = new BufferedReader(new InputStreamReader(System.in));
			LOGGER.info("Commands : ");
			LOGGER.info("q : quit");
			LOGGER.info("get word : searches word in the database");
			LOGGER.info("rc relativeCrypto : searches relativeCrypto in the database");
			LOGGER.info("nrc numericalRelativeCrypto : searches numericalRelativeCrypto in the database");
			LOGGER.info("load filename : loads crypto crossword grid from file");

			while (runInlineCommands) {

				LOGGER.info("Enter command: ");
				String input = br.readLine();

				manageCommands(input);

			}

		} catch (IOException e) {
			LOGGER.error(e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.error(e);
					;
				}
			}
		}

	}

	private static void manageCommands(String commandLine) {
		Scanner scanner = new Scanner(commandLine);
		scanner.useDelimiter(" ");
		if (scanner.hasNext()) {
			String command = scanner.next();
			String argument = "";
			try {
				while (scanner.hasNext()) {
					argument += scanner.next() + " ";
				}
			} catch (NoSuchElementException e) {
				// DO NOTHING
			}

			scanner.close();

			// System.out.println("cmd: " + command + ", arg: " + argument);

			switch (command) {
			case "q":
				LOGGER.info("Exit!");
				System.exit(0);
				break;
			case "stop":
				runInlineCommands = false;
				break;
			case "insert":
				wordService.addOne(new Word(argument));
				break;
			case "get":
				wordService.findOne(argument);
				break;
			case "rc":
				wordService.findByRelativeCrypto(argument.toUpperCase());
				break;
			case "nrc":
				wordService.reverseNumericalCrypto(argument);
				break;
			case "load":
				gridService.loadGrid(argument);
				break;
			default:
				LOGGER.error("Unknown command [" + command + " " + argument + "]");
				break;
			}
		}

	}

}
