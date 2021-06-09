package de.cas.adventofcode.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class FileReaderTest {

	@Test
	public void testGetFileContents() {
		List<String> fileContents = FileReader.getFileContents("shared/file_reader.txt");
		assertEquals("126797", fileContents.get(99));
	}

}
