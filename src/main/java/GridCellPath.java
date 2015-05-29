import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Created by nidhish on 5/29/15.
 */
public class GridCellPath {

    // These are references to global objects shared by all paths so that GridCellPath can add new paths directly
    private final Set<String> globalWordSet;
    private final Dictionary dictionary;
    private final char[][] globalGridCharsMatrix;
    Queue<GridCellPath> globalGridCellPaths;

    List<CharacterGrid.GridCell> gridCellList = new LinkedList<>();

    public GridCellPath(Queue<GridCellPath> globalGridCellPaths, Set<String> wordsSet, Dictionary dictionary, char[][] globalGridCharsMatrix) {
	this.globalGridCellPaths = globalGridCellPaths;
	this.globalWordSet = wordsSet;
	this.dictionary = dictionary;
	this.globalGridCharsMatrix = globalGridCharsMatrix;
    }

    public void addCell(CharacterGrid.GridCell gridCell) {
	gridCellList.add(gridCell);
    }

    public void matchAndGenerateNewPaths() {
	String potentialWord = getCharacterSequenceFromPath();
	if (dictionary.containsWord(potentialWord)) {
	    globalWordSet.add(potentialWord);
	}

	if (dictionary.isPrefixForWords(potentialWord)) {

	    //Get the last cell in the path
	    CharacterGrid.GridCell lastGridCellInPath = gridCellList.get(gridCellList.size() - 1);
	    List<CharacterGrid.GridCell> neighbors = lastGridCellInPath.findNeighbors();

	    //remove cells which are already part of the current path from the neighbors list
	    neighbors.removeAll(gridCellList);

	    for (CharacterGrid.GridCell neighboringCell : neighbors) {
		GridCellPath newGridCellPathWithNeighbor = new GridCellPath(globalGridCellPaths, globalWordSet, dictionary, globalGridCharsMatrix);
		//the new path contains all the cells in the current path
		newGridCellPathWithNeighbor.addCurrentPath(gridCellList);
		//the new path contains the neighbor that we found
		addCell(neighboringCell);
		//add the new path to the queue for processing
		globalGridCellPaths.add(newGridCellPathWithNeighbor);
	    }
	}
    }

    private void addCurrentPath(List<CharacterGrid.GridCell> gridCellList) {
	this.gridCellList.addAll(gridCellList);
    }

    public String getCharacterSequenceFromPath() {

	char[] charString = new char[gridCellList.size()];
	for (int i = 0; i < gridCellList.size(); i++) {
	    CharacterGrid.GridCell gridCell = gridCellList.get(i);
	    charString[i] = globalGridCharsMatrix[gridCell.getRowNumber()][gridCell.getColumnNumber()];
	}

	return charString.toString();
    }
}

