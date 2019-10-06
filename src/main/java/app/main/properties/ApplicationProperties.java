package app.main.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties extends Properties{
	
	private boolean creatDB;
	
	private boolean launchInlineCommands;

	public ApplicationProperties(String fileName) {
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
		creatDB = Boolean.valueOf(this.getProperty("application.database.createDB"));
		launchInlineCommands = Boolean.valueOf(this.getProperty("application.launchInlineCommands"));
	}
	
	public boolean isCreatDB() {
		return creatDB;
	}

	public boolean isLaunchInlineCommands() {
		return launchInlineCommands;
	}
	
}
