package com.mine.nidhish;

import java.util.ArrayList;
import java.util.List;

/**
* Created by nidhish on 5/29/15.
*/
public class GridCell {
    private CharacterGrid characterGrid;
    private final int rowNumber;
    private final int columnNumber;

    public GridCell(CharacterGrid characterGrid, int rowNumber, int columnNumber) {
        this.characterGrid = characterGrid;
        this.rowNumber = rowNumber;
	this.columnNumber = columnNumber;
	}

    public int getRowNumber() {
	return rowNumber;
    }

    public int getColumnNumber() {
	return columnNumber;
    }

    /**
     * Find all Possible neighbors
     * @return
     */
    public List<GridCell> findNeighbors() {

        List<GridCell> neighbors = new ArrayList<>();

        boolean up, down, right, left;
        up = this.rowNumber - 1 >= 0;
        down = this.rowNumber + 1 < characterGrid.getNumberOfRows();
        right = this.columnNumber + 1 < characterGrid.getNumberOfColumns();
        left = this.columnNumber - 1 >= 0;

        if (up) {
	    neighbors.add(new GridCell(characterGrid,this.rowNumber - 1, this.columnNumber));
        }
        if (down) {
	    neighbors.add(new GridCell(characterGrid,this.rowNumber + 1, this.columnNumber));
        }

        if (right) {
	    neighbors.add(new GridCell(characterGrid,this.rowNumber, this.columnNumber + 1));
        }
        if (left) {
	    neighbors.add(new GridCell(characterGrid,this.rowNumber, this.columnNumber - 1));
        }
        if (up && left) {
	    neighbors.add(new GridCell(characterGrid,this.rowNumber - 1, this.columnNumber - 1));
        }
        if (up && right) {
	    neighbors.add(new GridCell(characterGrid,this.rowNumber - 1, this.columnNumber + 1));
        }
        if (down && left) {
	    neighbors.add(new GridCell(characterGrid,this.rowNumber + 1, this.columnNumber - 1));
        }
        if (down && right) {
	    neighbors.add(new GridCell(characterGrid,this.rowNumber + 1, this.columnNumber + 1));
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
