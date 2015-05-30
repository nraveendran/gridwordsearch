package com.mine.nidhish;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Random;

/**
 * Created by nidhish on 5/29/15.
 */
public class GridPerformanceTest {

    public static void main(String[] args) throws IOException {
	//	createLargeDictionaryFile(2000000);

	Path path = createLargeGridFile();
	InputStream gridIn = Files.newInputStream(path);
	InputStream dictionaryIn = getInputStreamForDictionary();

	long startTime = System.currentTimeMillis();

	GridRunner gridRunner = new GridRunner(dictionaryIn, gridIn);
	gridRunner.computeWords();

	System.out.println("Computed words in " + (System.currentTimeMillis() - startTime ) + " ms");

    }

    private static Path createLargeGridFile() throws IOException {
	Path path = Files.createTempFile("large-grid", ".txt");
	path.toFile().deleteOnExit();

	BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("US-ASCII"), StandardOpenOption.CREATE);

	int gridSize = 1000;

	writer.write(gridSize + " " + gridSize);
	writer.newLine();

	for (int row = 0; row < gridSize; row++) {
	    for (int column = 0; column < gridSize; column++) {
		Random r = new Random();
		char c = (char) (r.nextInt(26) + 'a');
		writer.write(c + " ");
	    }
	    writer.newLine();
	}

	writer.close();

	return path;
    }

    private static void createLargeDictionaryFile(int numberOfWords) {

    }

    private static InputStream getInputStreamForDictionary() throws IOException {

	return GridPerformanceTest.class.getClassLoader().getResourceAsStream("englishwords.txt");

    }
}
