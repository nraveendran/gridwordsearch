package com.mine.nidhish;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by nidhish on 5/30/15.
 */
public class ConcurrentGridCellPath extends AbstractGridCellPath implements Callable<Integer> {

    private final ConcurrentCharacterGrid.JobSubmitter jobSubmitter;

    public ConcurrentGridCellPath(ConcurrentCharacterGrid.JobSubmitter jobSubmitter, Set<String> wordsSet, Dictionary dictionary, char[][] gridCharsMatrix) {
	super(wordsSet, dictionary, gridCharsMatrix);
	this.jobSubmitter = jobSubmitter;
    }

    @Override void processNewPathWithNeighbor(GridCell neighboringCell) {
	ConcurrentGridCellPath newGridCellPathWithNeighbor = createNewGridCellPathWithNeighbor(neighboringCell);

	jobSubmitter.addToQueue(newGridCellPathWithNeighbor);

    }

    protected ConcurrentGridCellPath createNewGridCellPathWithNeighbor(GridCell neighboringCell) {
	ConcurrentGridCellPath newGridCellPathWithNeighbor = new ConcurrentGridCellPath(jobSubmitter, globalWordSet, dictionary,
			globalGridCharsMatrix);
	//the new path contains all the cells in the current path
	newGridCellPathWithNeighbor.addCurrentPath(gridCellList);
	//the new path contains the neighbor that we found
	newGridCellPathWithNeighbor.addCell(neighboringCell);
	return newGridCellPathWithNeighbor;
    }

    @Override public Integer call() throws Exception {
	matchAndGenerateNewPaths();
	return 0;
    }
}
