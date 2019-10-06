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
import app.service.WordService;

public class CommandController implements Runnable {

	private final static Logger LOGGER = Logger.getLogger(CommandController.class);

	private static WordService wordService;

	public CommandController(WordService wordService) {
		CommandController.wordService = wordService;
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
			

			while (true) {

				LOGGER.info("Enter command: ");
				String input = br.readLine();

				manageCommands(input);

			}

		} catch (IOException e) {
			LOGGER.error(e.getCause().getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getCause().getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.error(e.getCause().getMessage());;
				}
			}
		}

	}

	private static void manageCommands(String commandLine) {
		Scanner scanner = new Scanner(commandLine);
		scanner.useDelimiter(" ");
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
		case "get":
			wordService.findOne(argument);
			break;
		case "rc":
			wordService.findByRelativeCrypto(argument.toUpperCase());
			break;
		case "nrc":
			wordService.reverseNumericalCrypto(argument);
			break;
		default:
			LOGGER.error("Unknown command [" + command + " " + argument + "]");
			break;
		}

	}

}
