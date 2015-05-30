package com.mine.nidhish;

import java.io.*;
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

        loadDictionary();


        InputStream in = this.getClass().getClassLoader()
		        .getResourceAsStream("grid.txt");

        BufferedReader br = new BufferedReader(new InputStreamReader(in));




        try {

            String firstLineOfGridFile = br.readLine();

            String[] rowAndColumn = firstLineOfGridFile.split(" ");

            int numberOfRows = Integer.parseInt(rowAndColumn[0]);
            int numberOfColumns = Integer.parseInt(rowAndColumn[1]);

            String gridLine;
            int currentRowNumber =0;

            char[][] rowCharArray = new char[numberOfRows][numberOfColumns];

	    while ((gridLine = br.readLine()) != null) {
	        String[] row = gridLine.split(" ");
	        if (row.length!=numberOfColumns){
	            throw new RuntimeException("Invalid grid file at line number " + currentRowNumber+1);
	        }

	        char[] colCharArray = new char[numberOfColumns];

	        for (int i = 0; i < row.length; i++) {
	            colCharArray[i] = Character.toLowerCase(row[i].charAt(0));
	        }
	        rowCharArray[currentRowNumber] = colCharArray;
	        currentRowNumber++;
	    }

            if (currentRowNumber != numberOfRows){
                throw new RuntimeException("Invalid grid file wrong number of rows found " + currentRowNumber);
            }


	    br.close();

            CharacterGrid characterGrid = new CharacterGrid(numberOfRows,numberOfColumns,rowCharArray);

            Set<String> wordSet = characterGrid.findWordMatchingDictionary(dictionary);

            System.out.println(wordSet.size());
            for (String word :wordSet){
                System.out.println(word);
            }

        }catch (Exception ex){
	    throw new RuntimeException(ex);
        }
    }

    private void loadDictionary() {
        dictionary = new Dictionary();

	InputStream in = this.getClass().getClassLoader()
			.getResourceAsStream("englishwords.txt");

	BufferedReader br = new BufferedReader(new InputStreamReader(in));

	String word;

	System.out.println("Starting to load dictionary");

	long startTime = System.currentTimeMillis();

	try {

	    while ((word = br.readLine()) != null) {
	        word = word.replaceAll("[^a-zA-Z]", "");
		dictionary.addWord(word);
	    }
	    br.close();
	}catch (Exception ex){
	    throw new RuntimeException(ex);
	}

	System.out.println("Loaded dictionary in " + (System.currentTimeMillis() - startTime) + " milliseconds");
    }

}
