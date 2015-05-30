package com.mine.nidhish;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * Created by nidhish on 5/30/15.
 */
public class ConcurrentCharacterGrid extends CharacterGrid {

    private ExecutorService executorService;

    public ConcurrentCharacterGrid(int numberOfRows, int numberOfColumns, char[] allWordCharArray) {
	super(numberOfRows, numberOfColumns, allWordCharArray);
    }

    public ConcurrentCharacterGrid(int numberOfRows, int numberOfColumns, char[][] rowCharArray) {
	super(numberOfRows, numberOfColumns, rowCharArray);
    }

    public Set<String> findWordMatchingDictionary(Dictionary dictionary) {

	return parallelProcessing(dictionary);

    }

    private Set<String> parallelProcessing(Dictionary dictionary) {
	executorService = Executors.newFixedThreadPool(25);
	JobSubmitter jobSubmitCallBack = new JobSubmitter();
	Set<String> finalSetOfWords = new ConcurrentSkipListSet<>();

	for (int currentRowNumber = 0; currentRowNumber < getNumberOfRows(); currentRowNumber++) {
	    for (int currentcolumnNumber = 0; currentcolumnNumber < getNumberOfColumns(); currentcolumnNumber++) {

		//The initial path represented by gridCellPath which is just the inputted single cell
		ConcurrentGridCellPath concurrentGridCellPath = new ConcurrentGridCellPath(jobSubmitCallBack, finalSetOfWords, dictionary,
				gridCharsMatrix);
		GridCell gridCell = new GridCell(this, currentRowNumber, currentcolumnNumber);
		concurrentGridCellPath.addCell(gridCell);
		concurrentGridCellPath.matchAndGenerateNewPaths();
	    }
	}

	jobSubmitCallBack.waitForAllTasksToComplete();
	executorService.shutdown();

	waitForShutDown(executorService);

	System.out.println("shutting down pools");

	return finalSetOfWords;
    }

    private void waitForShutDown(ExecutorService executorService) {
	while (!executorService.isShutdown()) {
	    try {
		Thread.sleep(10);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    class JobSubmitter {

	Vector<Future> submittedTasks;
	int numJobsSubmitted;
	int numJobsRemoved;

	public JobSubmitter() {
	    this.submittedTasks = new Vector<>();
	}

	private void waitForAllTasksToComplete() {

	    while (numJobsSubmitted <= 0)
		;


	    while (!submittedTasks.isEmpty()) {
		Future task = submittedTasks.firstElement();
		try {

		    task.get();

		    submittedTasks.removeElementAt(0);
		    numJobsRemoved++;
		} catch (InterruptedException e) {
		    e.printStackTrace();
		} catch (ExecutionException e) {
		    e.printStackTrace();
		}
	    }

	    System.out.println("all tasks done " + numJobsSubmitted + " submitted " + numJobsRemoved + " removed");

	}

	public synchronized void addToQueue(Callable nextJob) {
	        Future future = executorService.submit(nextJob);
	        submittedTasks.add(future);
	        numJobsSubmitted++;
	}

    }
}
