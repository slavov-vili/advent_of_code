package de.cas.adventofcode.shared;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileReader {
	public static final String HENNING = "henning";
	public static final String OSWALDO = "oswaldo";
	public static final String VELISLAV = "velislav";
	public static final String[] TEAMMATES = new String[] {HENNING, OSWALDO, VELISLAV};

    public static List<String> getFileContents(final String filePath) {
        final URL resource = FileReader.class.getClassLoader().getResource(filePath);
        try {
            final File file = new File(resource.toURI());
            return FileUtils.readLines(file, "UTF-8");
        } catch (final IOException | URISyntaxException e) {
            return Collections.emptyList();
        }
    }
    
    public static Map<String, List<String>> getInputs(String dayFolder) {
    	Map<String, List<String>> nameToInput = new HashMap<>();
    	for (String name : TEAMMATES) {
    		String pathToFile = String.format("%s/input_%s.txt", dayFolder, name);
    		nameToInput.put(name, FileReader.getFileContents(pathToFile));
    	}
    	return nameToInput;
    }
}
