package com.mine.nidhish;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Created by nidhish on 5/30/15.
 */
public abstract class AbstractGridCellPath {
    // These are references to global objects shared by all paths so that GridCellPath can add new paths directly
    protected final Set<String> globalWordSet;
    protected final Dictionary dictionary;
    protected final char[][] globalGridCharsMatrix;

    List<GridCell> gridCellList = new LinkedList<GridCell>();

    public AbstractGridCellPath(Set<String> wordsSet, Dictionary dictionary,
		    char[][] globalGridCharsMatrix) {
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

	    processNewPathWithNeighbor(neighboringCell);

	}
    }

    abstract void processNewPathWithNeighbor(GridCell neighboringCell);

    protected List<GridCell> findNeighbors() {
	//Get the last cell in the path
	GridCell lastGridCellInPath = getLastGridCell();
	List<GridCell> neighbors = lastGridCellInPath.findNeighbors();

	//remove cells which are already part of the current path from the neighbors list
	neighbors.removeAll(gridCellList);
	return neighbors;
    }



    protected GridCell getLastGridCell() {
	return gridCellList.get(gridCellList.size() - 1);
    }

    protected void addCurrentPath(List<GridCell> gridCellList) {
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
