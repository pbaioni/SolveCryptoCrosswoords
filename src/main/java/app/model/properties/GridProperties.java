package app.model.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GridProperties extends Properties{
	
	private String letterSeparator;
	
	private String wordSeparator;
	
	private String unknownCharacter;
	
	private int shortWordMaxLength;
	
	private String pathToGrids;

	public GridProperties(String fileName) {
		loadProperties(fileName);
		init();
	}
	
	private void loadProperties(String fileName) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {

            // load a properties file
            this.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	private void init() {
		letterSeparator = this.getProperty("grid.letterSeparator");
		wordSeparator = this.getProperty("grid.wordSeparator");
		unknownCharacter = this.getProperty("grid.unknownCharacter");
		shortWordMaxLength = Integer.parseInt(this.getProperty("grid.shortWordMaxLength"));
		pathToGrids = this.getProperty("grid.pathToGrids");
	}

	public String getLetterSeparator() {
		return letterSeparator;
	}

	public String getWordSeparator() {
		return wordSeparator;
	}

	public String getUnknownCharacter() {
		return unknownCharacter;
	}

	public int getShortWordMaxLength() {
		return shortWordMaxLength;
	}

	public String getPathToGrids() {
		return pathToGrids;
	}	
}
