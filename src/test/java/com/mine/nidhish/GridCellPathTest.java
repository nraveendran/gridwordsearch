package com.mine.nidhish;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GridCellPathTest {

    private Dictionary dictionary;

    @Before public void setUp() throws Exception {

	dictionary = new Dictionary();
    }



    @Test public void testGetCharacterSequenceFromPath() throws Exception {

	char[] wordChars = { 'A', 'B', 'C', 'D' };

	CharacterGrid characterGrid = new CharacterGrid(2, 2, wordChars);

	Queue<GridCellPath> globalGridCellPaths = new LinkedList<>();
	Set<String> globalWordSet = new HashSet<>();

	GridCellPath gridCellPath = new GridCellPath(globalGridCellPaths, globalWordSet, dictionary, characterGrid.getGridCharsMatrix());
	GridCell gridCell = new GridCell(characterGrid, 0, 0);

	gridCellPath.addCell(gridCell);

	Assert.assertEquals(gridCellPath.getCharacterSequenceFromPath(), "a");

	GridCell gridCell1 = new GridCell(characterGrid, 0, 1);
	gridCellPath.addCell(gridCell1);

	Assert.assertEquals(gridCellPath.getCharacterSequenceFromPath(), "ab");

	GridCell gridCell2 = new GridCell(characterGrid, 1, 1);
	gridCellPath.addCell(gridCell2);

	Assert.assertEquals(gridCellPath.getCharacterSequenceFromPath(), "abd");

    }

    @Test public void testLastGridCellFromPath() {
	char[] wordChars = { 'A', 'B', 'C', 'D' };

	CharacterGrid characterGrid = new CharacterGrid(2, 2, wordChars);

	Queue<GridCellPath> globalGridCellPaths = new LinkedList<>();
	Set<String> globalWordSet = new HashSet<>();

	GridCellPath gridCellPath = new GridCellPath(globalGridCellPaths, globalWordSet, dictionary, characterGrid.getGridCharsMatrix());
	GridCell gridCell = new GridCell(characterGrid, 0, 0);
	GridCell gridCell1 = new GridCell(characterGrid, 0, 1);
	GridCell gridCell2 = new GridCell(characterGrid, 1, 1);
	gridCellPath.addCell(gridCell);
	gridCellPath.addCell(gridCell1);
	gridCellPath.addCell(gridCell2);

	GridCell expectedGridCell = new GridCell(characterGrid, 1, 1);

	Assert.assertEquals(gridCellPath.getLastGridCell(), expectedGridCell);

    }

    @Test public void testCreateNewGridCellPathWithNeighbor() throws Exception {

	char[] wordChars = { 'A', 'B', 'C', 'D', 'E', 'F' };

	CharacterGrid characterGrid = new CharacterGrid(2, 3, wordChars);

	Queue<GridCellPath> globalGridCellPaths = new LinkedList<>();
	Set<String> globalWordSet = new HashSet<>();

	GridCellPath gridCellPath = new GridCellPath(globalGridCellPaths, globalWordSet, dictionary, characterGrid.getGridCharsMatrix());
	GridCell gridCell = new GridCell(characterGrid, 0, 0);
	GridCell gridCell1 = new GridCell(characterGrid, 0, 1);
	GridCell gridCell2 = new GridCell(characterGrid, 1, 1);
	gridCellPath.addCell(gridCell);
	gridCellPath.addCell(gridCell1);
	gridCellPath.addCell(gridCell2);

	GridCell neighborToAppend = new GridCell(characterGrid, 1, 2);
	GridCellPath newGridCellPath = gridCellPath.createNewGridCellPathWithNeighbor(neighborToAppend);

	Assert.assertEquals(newGridCellPath.getLastGridCell(), neighborToAppend);

    }

    @Test public void testCreateNewGridCellPathWithNeighborReversePath() throws Exception {

	char[] wordChars = { 'A', 'B', 'C', 'D', 'E', 'F' };

	CharacterGrid characterGrid = new CharacterGrid(2, 3, wordChars);

	Queue<GridCellPath> globalGridCellPaths = new LinkedList<>();
	Set<String> globalWordSet = new HashSet<>();

	GridCellPath gridCellPath = new GridCellPath(globalGridCellPaths, globalWordSet, dictionary, characterGrid.getGridCharsMatrix());
	GridCell gridCell = new GridCell(characterGrid, 1, 1);
	GridCell gridCell1 = new GridCell(characterGrid, 0, 2);
	GridCell gridCell2 = new GridCell(characterGrid, 1, 2);
	gridCellPath.addCell(gridCell);
	gridCellPath.addCell(gridCell1);
	gridCellPath.addCell(gridCell2);

	GridCell neighborToAppend = new GridCell(characterGrid, 0, 1);
	GridCellPath newGridCellPath = gridCellPath.createNewGridCellPathWithNeighbor(neighborToAppend);

	Assert.assertEquals(newGridCellPath.getLastGridCell(), neighborToAppend);

    }

    @Test public void testFindNeighbors() throws Exception {
	char[] wordChars = { 'A', 'B', 'C', 'D', 'E', 'F','G','H','I' };

	CharacterGrid characterGrid = new CharacterGrid(3, 3, wordChars);

	GridCell gridCell_0_0 = new GridCell(characterGrid, 0, 0);
	GridCell gridCell_0_1 = new GridCell(characterGrid, 0, 1);
	GridCell gridCell_0_2 = new GridCell(characterGrid, 0, 2);
	GridCell gridCell_1_0 = new GridCell(characterGrid, 1, 0);
	GridCell gridCell_1_1 = new GridCell(characterGrid, 1, 1);
	GridCell gridCell_1_2 = new GridCell(characterGrid, 1, 2);
	GridCell gridCell_2_0 = new GridCell(characterGrid, 2, 0);
	GridCell gridCell_2_1 = new GridCell(characterGrid, 2, 1);
	GridCell gridCell_2_2 = new GridCell(characterGrid, 2, 2);


        Queue<GridCellPath> globalGridCellPaths = new LinkedList<>();
        Set<String> globalWordSet = new HashSet<>();

        GridCellPath gridCellPath = new GridCellPath(globalGridCellPaths, globalWordSet, dictionary, characterGrid.getGridCharsMatrix());
        gridCellPath.addCell(gridCell_0_0);
        gridCellPath.addCell(gridCell_0_1);
        gridCellPath.addCell(gridCell_1_1);

        List<GridCell> neighborList = gridCellPath.findNeighbors();

        assertTrue(neighborList.containsAll(Arrays.asList(gridCell_0_2, gridCell_1_0, gridCell_1_2, gridCell_2_0,gridCell_2_1,gridCell_2_2)) && Arrays
		        .asList(gridCell_0_2, gridCell_1_0, gridCell_1_2, gridCell_2_0,gridCell_2_1,gridCell_2_2).containsAll(neighborList));


        gridCellPath = new GridCellPath(globalGridCellPaths, globalWordSet, dictionary, characterGrid.getGridCharsMatrix());
        gridCellPath.addCell(gridCell_1_1);
        gridCellPath.addCell(gridCell_1_2);
        gridCellPath.addCell(gridCell_2_2);
        gridCellPath.addCell(gridCell_2_1);
        gridCellPath.addCell(gridCell_2_0);

        neighborList = gridCellPath.findNeighbors();

        assertTrue(neighborList.containsAll(Arrays.asList(gridCell_1_0)) && Arrays
		        .asList(gridCell_1_0).containsAll(neighborList));


        gridCellPath = new GridCellPath(globalGridCellPaths, globalWordSet, dictionary, characterGrid.getGridCharsMatrix());

        gridCellPath.addCell(gridCell_0_1);
        gridCellPath.addCell(gridCell_1_1);
        gridCellPath.addCell(gridCell_1_0);
        gridCellPath.addCell(gridCell_0_0);


        neighborList = gridCellPath.findNeighbors();

        assertTrue(neighborList.size()==0);
    }


    @Test public void testExactPathMatchWithDictionary() throws Exception {
	char[] wordChars = { 'A', 'A', 'Q', 'U', 'I', 'D','F','U','D','L','G','Y','L','M','K','X' };

        dictionary.addWord("aa");

	CharacterGrid characterGrid = new CharacterGrid(4, 4, wordChars);

	GridCell gridCell_0_0 = new GridCell(characterGrid, 0, 0);
	GridCell gridCell_0_1 = new GridCell(characterGrid, 0, 1);


	Queue<GridCellPath> globalGridCellPaths = new LinkedList<>();
	Set<String> globalWordSet = new HashSet<>();

	GridCellPath gridCellPath = new GridCellPath(globalGridCellPaths, globalWordSet, dictionary, characterGrid.getGridCharsMatrix());
	gridCellPath.addCell(gridCell_0_0);
	gridCellPath.addCell(gridCell_0_1);


        gridCellPath.matchAndGenerateNewPaths();

        assertEquals(globalWordSet.toArray()[0],"aa");

    }



    @Test public void testPrefixBasedPathGeneration() throws Exception {

        char[] wordChars = { 'A', 'A', 'Q', 'U', 'I', 'D','F','U','D','L','G','Y','L','M','K','X' };

        dictionary.addWord("aa");

        CharacterGrid characterGrid = new CharacterGrid(4, 4, wordChars);

        GridCell gridCell_0_0 = new GridCell(characterGrid, 0, 0);
        GridCell gridCell_0_1 = new GridCell(characterGrid, 0, 1);


	Queue<GridCellPath> globalGridCellPaths = new LinkedList<>();
	Set<String> globalWordSet = new HashSet<>();

	GridCellPath gridCellPath = new GridCellPath(globalGridCellPaths, globalWordSet, dictionary, characterGrid.getGridCharsMatrix());
	gridCellPath.addCell(gridCell_0_0);
        gridCellPath.addCell(gridCell_0_1);


	gridCellPath.matchAndGenerateNewPaths();

	assertEquals(globalGridCellPaths.size(),0);

        globalGridCellPaths = new LinkedList<>();
        dictionary.addWord("aad");
        gridCellPath = new GridCellPath(globalGridCellPaths, globalWordSet, dictionary, characterGrid.getGridCharsMatrix());
        gridCellPath.addCell(gridCell_0_0);
        gridCellPath.addCell(gridCell_0_1);

        gridCellPath.matchAndGenerateNewPaths();
        assertEquals(globalGridCellPaths.size(),4);

    }


}