package com.mine.nidhish;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Created by nidhish on 5/30/15.
 */
public class AbstractGridCellPath {
    // These are references to global objects shared by all paths so that GridCellPath can add new paths directly
    protected final Set<String> globalWordSet;
    protected final Dictionary dictionary;
    protected final char[][] globalGridCharsMatrix;Queue<GridCellPath> globalGridCellPathsToProcess;
    List<GridCell> gridCellList = new LinkedList<GridCell>();

    public AbstractGridCellPath(Queue<GridCellPath> globalGridCellPathsToProcess, Set<String> wordsSet, Dictionary dictionary,
		    char[][] globalGridCharsMatrix) {
	this.globalGridCellPathsToProcess = globalGridCellPathsToProcess;
	this.globalWordSet = wordsSet;
	this.dictionary = dictionary;
	this.globalGridCharsMatrix = globalGridCharsMatrix;
    }

    public void addCell(GridCell gridCell) {
	gridCellList.add(gridCell);
    }

    public void matchAndGenerateNewPaths() {
	String potentialWord = getCharacterSequenceFromPath();
	if (dictionary.containsWord(potentialWord)) {
	    globalWordSet.add(potentialWord);
	}

	if (dictionary.isPrefixForWords(potentialWord)) {
	    generateNewPaths();
	}
    }

    protected void generateNewPaths() {
	List<GridCell> neighbors = findNeighbors();

	for (GridCell neighboringCell : neighbors) {
	    GridCellPath newGridCellPathWithNeighbor = createNewGridCellPathWithNeighbor(neighboringCell);
		//add the new path to the queue for processing
		globalGridCellPathsToProcess.add(newGridCellPathWithNeighbor);
	}
    }

    protected List<GridCell> findNeighbors() {
	//Get the last cell in the path
	GridCell lastGridCellInPath = getLastGridCell();
	List<GridCell> neighbors = lastGridCellInPath.findNeighbors();

	//remove cells which are already part of the current path from the neighbors list
	neighbors.removeAll(gridCellList);
	return neighbors;
    }

    protected GridCellPath createNewGridCellPathWithNeighbor(GridCell neighboringCell) {
	GridCellPath newGridCellPathWithNeighbor = new GridCellPath(globalGridCellPathsToProcess, globalWordSet, dictionary, globalGridCharsMatrix);
	//the new path contains all the cells in the current path
	newGridCellPathWithNeighbor.addCurrentPath(gridCellList);
	//the new path contains the neighbor that we found
        newGridCellPathWithNeighbor.addCell(neighboringCell);
	return newGridCellPathWithNeighbor;
    }

    protected GridCell getLastGridCell() {
	return gridCellList.get(gridCellList.size() - 1);
    }

    private void addCurrentPath(List<GridCell> gridCellList) {
	this.gridCellList.addAll(gridCellList);
    }

    public String getCharacterSequenceFromPath() {

        StringBuilder stringBuilder = new StringBuilder();

	for (GridCell gridCell: gridCellList) {

	     stringBuilder.append(globalGridCharsMatrix[gridCell.getRowNumber()][gridCell.getColumnNumber()]);
	}

	return stringBuilder.toString();
    }
}
