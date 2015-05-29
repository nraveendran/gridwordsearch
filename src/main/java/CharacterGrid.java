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
        
        Set<String> wordsSet = new HashSet<>();
        
        Queue<GridCellPath> gridCellPathsToProcess = new LinkedList<>();


        //The initial path represented by gridCellPath which is just the inputted single cell
        GridCellPath gridCellPath = new GridCellPath(gridCellPathsToProcess,wordsSet,dictionary,gridCharsMatrix);
        GridCell gridCell = new GridCell(currentRowNumber,currentcolumnNumber);
        gridCellPath.addCell(gridCell);


        while (!gridCellPathsToProcess.isEmpty()){

            GridCellPath currentGridCellPath = gridCellPathsToProcess.poll();
            //this will check if the current path matches a word in dictionary
            //if the current path matches a prefix in the dictionary, the gridCellPath will generate new paths based on its neighbors and submit them for processing
            currentGridCellPath.matchAndGenerateNewPaths();
        }


        return wordsSet;
    }




    public class GridCell {
        private final int rowNumber;
        private final int columnNumber;

        public GridCell(int rowNumber, int columnNumber) {
	    this.rowNumber = rowNumber;
	    this.columnNumber = columnNumber;
	}

        public int getRowNumber() {
	    return rowNumber;
        }

        public int getColumnNumber() {
	    return columnNumber;
        }

        public List<GridCell> findNeighbors() {
            // Possible neighbors are up/down/left/right/ur/ul/dr/dl from this cell
            LinkedList<GridCell> neighbors = new LinkedList<>();
            
            boolean up, down, right, left;
            up = this.rowNumber - 1 >= 0;
            down = this.rowNumber + 1 < numberOfRows;
            right = this.columnNumber + 1 < numberOfColumns;
            left = this.columnNumber - 1 >= 0;

            if (up) {
	        neighbors.add(new GridCell(this.rowNumber - 1, this.columnNumber));
            }
            if (down) {
	        neighbors.add(new GridCell(this.rowNumber + 1, this.columnNumber));
            }

            if (right) {
	        neighbors.add(new GridCell(this.rowNumber, this.columnNumber + 1));
            }
            if (left) {
	        neighbors.add(new GridCell(this.rowNumber, this.columnNumber - 1));
            }
            if (up && left) {
	        neighbors.add(new GridCell(this.rowNumber - 1, this.columnNumber - 1));
            }
            if (up && right) {
	        neighbors.add(new GridCell(this.rowNumber - 1, this.columnNumber + 1));
            }
            if (down && left) {
	        neighbors.add(new GridCell(this.rowNumber + 1, this.columnNumber - 1));
            }
            if (down && right) {
	        neighbors.add(new GridCell(this.rowNumber + 1, this.columnNumber + 1));
            }

            return neighbors;
        }

        @Override public boolean equals(Object o) {
	    if (this == o)
	        return true;
	    if (o == null || getClass() != o.getClass())
	        return false;

	    GridCell gridCell = (GridCell) o;

	    if (columnNumber != gridCell.columnNumber)
	        return false;
	    if (rowNumber != gridCell.rowNumber)
	        return false;

	    return true;
        }

        @Override public int hashCode() {
	    int result = rowNumber;
	    result = 31 * result + columnNumber;
	    return result;
        }
    }
}
