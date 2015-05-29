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
}
