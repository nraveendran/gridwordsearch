package com.mine.nidhish;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Created by nidhish on 5/29/15.
 */
public class GridRunner {

    private Dictionary dictionary;

    public static void main(String[] args) {

	GridRunner gridRunner = new GridRunner();

    }

    public GridRunner() {

	//        System.out.println("Starting Grid Scanning ");
	//        long startTime = System.currentTimeMillis();

	loadDictionary();

	CharacterGrid characterGrid = readCharacterGrid();

	Set<String> wordSet = characterGrid.findWordMatchingDictionary(dictionary);

	System.out.println(wordSet.size());
	for (String word : wordSet) {
	    System.out.println(word);
	}

	//        System.out.println("Finish scanning character grid in " + (System.currentTimeMillis() - startTime) + " ms");

    }

    private CharacterGrid readCharacterGrid() {

	//        System.out.println("Starting to read Grid ");
	//
	long startTime = System.currentTimeMillis();

	try {
	    InputStream in;

	    if (System.getProperty("gridFile") != null) {
		String gridFileName = System.getProperty("gridFile");
		Path path = Paths.get(gridFileName);
		in = Files.newInputStream(path);
	    } else {
		in = this.getClass().getClassLoader().getResourceAsStream("grid.txt");
	    }

	    BufferedReader br = new BufferedReader(new InputStreamReader(in));

	    int numberOfRows;
	    int numberOfColumns;
	    char[][] rowCharArray;

	    String firstLineOfGridFile = br.readLine();

	    String[] rowAndColumn = firstLineOfGridFile.split(" ");

	    numberOfRows = Integer.parseInt(rowAndColumn[0]);
	    numberOfColumns = Integer.parseInt(rowAndColumn[1]);

	    String gridLine;
	    int currentRowNumber = 0;

	    rowCharArray = new char[numberOfRows][numberOfColumns];

	    while ((gridLine = br.readLine()) != null) {
		String[] row = gridLine.split(" ");
		if (row.length != numberOfColumns) {
		    throw new RuntimeException("Invalid grid file at line number " + currentRowNumber + 1);
		}

		char[] colCharArray = new char[numberOfColumns];

		for (int i = 0; i < row.length; i++) {
		    colCharArray[i] = Character.toLowerCase(row[i].charAt(0));
		}
		rowCharArray[currentRowNumber] = colCharArray;
		currentRowNumber++;
	    }

	    if (currentRowNumber != numberOfRows) {
		throw new RuntimeException("Invalid grid file wrong number of rows found " + currentRowNumber);
	    }

	    br.close();

	    CharacterGrid characterGrid = new CharacterGrid(numberOfRows, numberOfColumns, rowCharArray);
//	    System.out.println("Finish reading character grid in " + (System.currentTimeMillis() - startTime) + " ms");
	    return characterGrid;
	} catch (Exception ex) {
	    throw new RuntimeException(ex);
	}

    }

    private void loadDictionary() {
	try {
	    dictionary = new Dictionary();

	    String dictionaryFileName = "";

	    InputStream in;

	    if (System.getProperty("dictionaryFile") != null) {
		dictionaryFileName = System.getProperty("dictionaryFile");
		Path path = Paths.get(dictionaryFileName);
		in = Files.newInputStream(path);
	    } else {
		in = this.getClass().getClassLoader().getResourceAsStream("englishwords.txt");
	    }

	    BufferedReader br = new BufferedReader(new InputStreamReader(in));

	    String word;

	    //	System.out.println("Starting to load dictionary");

	    long startTime = System.currentTimeMillis();

	    while ((word = br.readLine()) != null) {
		word = word.replaceAll("[^a-zA-Z]", "");
		dictionary.addWord(word);
	    }
	    br.close();
	} catch (Exception ex) {
	    throw new RuntimeException(ex);
	}

	//	System.out.println("Loaded dictionary in " + (System.currentTimeMillis() - startTime) + " milliseconds");
    }

}
