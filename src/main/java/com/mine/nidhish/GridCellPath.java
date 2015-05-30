package com.mine.nidhish;

import com.mine.nidhish.GridCell;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * A path made of GridCells. This path could be a potential word which could be checked against the dictionary
 */
public class GridCellPath {

    // These are references to global objects shared by all paths so that GridCellPath can add new paths directly
    private final Set<String> globalWordSet;
    private final Dictionary dictionary;
    private final char[][] globalGridCharsMatrix;
    Queue<GridCellPath> globalGridCellPaths;

    List<GridCell> gridCellList = new LinkedList<GridCell>();

    public GridCellPath(Queue<GridCellPath> globalGridCellPaths, Set<String> wordsSet, Dictionary dictionary, char[][] globalGridCharsMatrix) {
	this.globalGridCellPaths = globalGridCellPaths;
	this.globalWordSet = wordsSet;
	this.dictionary = dictionary;
	this.globalGridCharsMatrix = globalGridCharsMatrix;
    }



    public void addCell(GridCell gridCell) {
	gridCellList.add(gridCell);
    }

    /**
     * Match current grid cell path against the dictionary
     * if the current grid cellpath is a word, its added to a global word set
     * if the current path is a prefix to a word, new paths are generated
     */
    public void matchAndGenerateNewPaths() {
	String potentialWord = getCharacterSequenceFromPath();
	if (dictionary.containsWord(potentialWord)) {
	    globalWordSet.add(potentialWord);
	}

	if (dictionary.isPrefixForWords(potentialWord)) {
	    generateNewPaths();
	}
    }

    /**
     * Looks at all the neighbors of the last cell in this path and form new paths which can be submitted for evaluation.
     */
    protected void generateNewPaths() {
	List<GridCell> neighbors = findNeighbors();

	for (GridCell neighboringCell : neighbors) {
	    GridCellPath newGridCellPathWithNeighbor = createNewGridCellPathWithNeighbor(neighboringCell);
		//add the new path to the queue for processing
		globalGridCellPaths.add(newGridCellPathWithNeighbor);
	}
    }

    /**
     *
     * @return all neighbors of this path
     */
    protected List<GridCell> findNeighbors() {
	//Get the last cell in the path
	GridCell lastGridCellInPath = getLastGridCell();
	List<GridCell> neighbors = lastGridCellInPath.findNeighbors();

	//remove cells which are already part of the current path from the neighbors list
	neighbors.removeAll(gridCellList);
	return neighbors;
    }

    /**
     * Takes a neighboringCell and forms new paths
     * @param neighboringCell
     * @return
     */
    protected GridCellPath createNewGridCellPathWithNeighbor(GridCell neighboringCell) {
	GridCellPath newGridCellPathWithNeighbor = new GridCellPath(globalGridCellPaths, globalWordSet, dictionary, globalGridCharsMatrix);
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

    /**
     *
     * @return the String that the cell path represents
     */
    public String getCharacterSequenceFromPath() {

        StringBuilder stringBuilder = new StringBuilder();

	for (GridCell gridCell: gridCellList) {

	     stringBuilder.append(globalGridCharsMatrix[gridCell.getRowNumber()][gridCell.getColumnNumber()]);
	}

	return stringBuilder.toString();
    }
}

