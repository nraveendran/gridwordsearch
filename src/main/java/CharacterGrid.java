import java.util.*;

/**
 * Created by nidhish on 5/29/15.
 */
public class CharacterGrid {
    private final int numberOfRows;
    private final int numberOfColumns;

    private char[][] gridCharsMatrix;

    public CharacterGrid(int numberOfRows, int numberOfColumns,char[] allWordCharArray) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        if (allWordCharArray.length!=numberOfRows * numberOfColumns){
            throw new RuntimeException(numberOfRows* numberOfColumns + " characters expected. " + allWordCharArray.length + " provided");
        }
        this.gridCharsMatrix = new char[numberOfRows][numberOfColumns];

        int allWordCharArrayIndex = 0;

        for (int rowNumber = 0;rowNumber<numberOfRows;rowNumber++){
            for (int columnNumber = 0; columnNumber < numberOfColumns; columnNumber++) {
                gridCharsMatrix[rowNumber][columnNumber] = Character.toLowerCase(allWordCharArray[allWordCharArrayIndex++]);
            }
        }

    }

    public int getNumberOfRows() {
	return numberOfRows;
    }

    public int getNumberOfColumns() {
	return numberOfColumns;
    }

    public char[][] getGridCharsMatrix() {
	return gridCharsMatrix;
    }

    public Set findWordMatchingDictionary(Dictionary dictionary) {

        Set finalSetOfWords = new HashSet<String>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int currentRowNumber = 0; currentRowNumber < getNumberOfRows(); currentRowNumber++) {
            for (int currentcolumnNumber = 0; currentcolumnNumber < getNumberOfColumns(); currentcolumnNumber++) {


                finalSetOfWords.addAll(visitCellsUsingMessaging(currentRowNumber, currentcolumnNumber, dictionary));

            
            }
        }
        return finalSetOfWords;
    }

    private Collection visitCellsUsingMessaging(int currentRowNumber, int currentcolumnNumber, Dictionary dictionary) {
        
        Set<String> wordsSet = new HashSet<String>();
        
        Queue<GridCellPath> gridCellPathsToProcess = new LinkedList<>();


        //The initial path represented by gridCellPath which is just the inputted single cell
        GridCellPath gridCellPath = new GridCellPath(gridCellPathsToProcess,wordsSet,dictionary,gridCharsMatrix);
        GridCell gridCell = new GridCell(this, currentRowNumber,currentcolumnNumber);
        gridCellPath.addCell(gridCell);


        while (!gridCellPathsToProcess.isEmpty()){

            GridCellPath currentGridCellPath = gridCellPathsToProcess.poll();
            //this will check if the current path matches a word in dictionary
            //if the current path matches a prefix in the dictionary, the gridCellPath will generate new paths based on its neighbors and submit them for processing
            currentGridCellPath.matchAndGenerateNewPaths();
        }


        return wordsSet;
    }

}
