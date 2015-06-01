package com.mine.nidhish;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by nidhish on 5/30/15.
 */
public class ConcurrentCharacterGrid extends CharacterGrid {

    private ExecutorService executorService;

    ExecutorService singleThreadedExecutor = Executors.newSingleThreadExecutor();

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
	singleThreadedExecutor.submit(jobSubmitCallBack);
	Set<String> finalSetOfWords = new ConcurrentSkipListSet<>();

	for (int currentRowNumber = 0; currentRowNumber < getNumberOfRows(); currentRowNumber++) {
	    for (int currentcolumnNumber = 0; currentcolumnNumber < getNumberOfColumns(); currentcolumnNumber++) {

		//The initial path represented by gridCellPath which is just the inputted single cell
		ConcurrentGridCellPath concurrentGridCellPath = new ConcurrentGridCellPath(jobSubmitCallBack, finalSetOfWords, dictionary,
				gridCharsMatrix);
		GridCell gridCell = new GridCell(this, currentRowNumber, currentcolumnNumber);
		concurrentGridCellPath.addCell(gridCell);
		jobSubmitCallBack.addToQueue(concurrentGridCellPath);

	    }
	}


	jobSubmitCallBack.waitForAllTasksToComplete();

	singleThreadedExecutor.shutdown();
	executorService.shutdown();

        waitForShutDown(singleThreadedExecutor);

        waitForShutDown(executorService);

	System.out.println("shutting down pools");

	return finalSetOfWords;
    }

    private void waitForShutDown(ExecutorService executorService) {

	while (!executorService.isShutdown()){
	    try {
		Thread.sleep(10);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    class JobSubmitter implements Runnable {

	Queue<Callable> jobsToSubmit;
	Queue<Future> submittedTasks;
        int numJobsSubmitted;
        int numJobsFinished;

	public JobSubmitter() {
	    this.jobsToSubmit = new LinkedList<>();
	    this.submittedTasks = new LinkedList<>();
	}

	@Override public void run() {
	    while (numJobsSubmitted<=0);

	    System.out.println("polling queue for jobs");
	    while (!jobsToSubmit.isEmpty()) {
		Callable nextJob = jobsToSubmit.poll();
		if (nextJob != null) {
		    submittedTasks.add(executorService.submit(nextJob));
		    numJobsSubmitted++;
		}
	    }
	}

  	private void waitForAllTasksToComplete() {

	    System.out.println("checking for status of submitted jobs");
	    while (!submittedTasks.isEmpty()) {
		Future task = submittedTasks.poll();
		try {

		    task.get();
		    numJobsFinished++;
		} catch (InterruptedException e) {
		    e.printStackTrace();
		} catch (ExecutionException e) {
		    e.printStackTrace();
		}
	    }

	    System.out.println("all tasks done with " + numJobsSubmitted + " jobs submitted and " + numJobsFinished +  " finished");


	}

	public void addToQueue(Callable newGridCellPathWithNeighbor) {
	    jobsToSubmit.add(newGridCellPathWithNeighbor);
	    numJobsSubmitted++;
	}

    }
}
