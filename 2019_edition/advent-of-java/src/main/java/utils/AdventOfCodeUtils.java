package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class AdventOfCodeUtils {

	public static List<String> readClasspathFileLines(Class sourceClass, String fileName) {
		List<String> fileLines = Collections.emptyList();
		
		try {
		    fileLines = getLinesFromClasspathFile(sourceClass, fileName);
		} catch (Exception e) {
		}
		
		return fileLines;
	}

	private static List<String> getLinesFromClasspathFile(Class sourceClass, String fileName) throws URISyntaxException,
	IOException {
		URL fileUrl = sourceClass.getResource(fileName);
		
		if(fileUrl == null) {
			throw new FileNotFoundException();
		}
		
		File fileToRead = new File(fileUrl.toURI().getPath());
		return Files.readAllLines(fileToRead.toPath());
	}
	
	
}
