package app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AppFiles {

	public static List<String> getFileAsLines(String filePath) {
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get(filePath));
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

	public static String getFileAsString(String filePath) {

		List<String> lines = getFileAsLines(filePath);
		String fileContent = "";

		for (String line : lines) {
			fileContent += line;
		}

		return fileContent;
	}
}
