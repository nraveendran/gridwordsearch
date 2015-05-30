package com.mine.nidhish;

import java.util.Queue;
import java.util.Set;

/**
 * Created by nidhish on 5/29/15.
 */
public class GridCellPath extends AbstractGridCellPath {

    Queue<GridCellPath> globalGridCellPathsToProcess;

    public GridCellPath(Queue<GridCellPath> globalGridCellPathsToProcess, Set<String> wordsSet, Dictionary dictionary, char[][] globalGridCharsMatrix) {
        super(wordsSet, dictionary, globalGridCharsMatrix);
        this.globalGridCellPathsToProcess = globalGridCellPathsToProcess;
    }

    @Override void processNewPathWithNeighbor(GridCell neighboringCell) {
        GridCellPath newGridCellPathWithNeighbor = createNewGridCellPathWithNeighbor(neighboringCell);
        //add the new path to the queue for processing
        globalGridCellPathsToProcess.add(newGridCellPathWithNeighbor);
    }

    protected GridCellPath createNewGridCellPathWithNeighbor(GridCell neighboringCell) {
	GridCellPath newGridCellPathWithNeighbor = new GridCellPath(globalGridCellPathsToProcess, globalWordSet, dictionary, globalGridCharsMatrix);
	//the new path contains all the cells in the current path
	newGridCellPathWithNeighbor.addCurrentPath(gridCellList);
	//the new path contains the neighbor that we found
	newGridCellPathWithNeighbor.addCell(neighboringCell);
	return newGridCellPathWithNeighbor;
    }
}

