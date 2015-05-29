import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class GridCellTest {

    @Test public void testFindNeighbors() throws Exception {

	char[] wordChars = { 'A', 'B', 'C', 'D', 'E', 'F' };

	CharacterGrid characterGrid = new CharacterGrid(2, 3, wordChars);

	GridCell gridCell_0_0 = new GridCell(characterGrid, 0, 0);
	GridCell gridCell_0_1 = new GridCell(characterGrid, 0, 1);
	GridCell gridCell_0_2 = new GridCell(characterGrid, 0, 2);
	GridCell gridCell_1_0 = new GridCell(characterGrid, 1, 0);
	GridCell gridCell_1_1 = new GridCell(characterGrid, 1, 1);
	GridCell gridCell_1_2 = new GridCell(characterGrid, 1, 2);

	assertTrue(gridCell_0_0.findNeighbors().containsAll(Arrays.asList(gridCell_0_1, gridCell_1_1, gridCell_1_0)) && Arrays
			.asList(gridCell_0_1, gridCell_1_1, gridCell_1_0).containsAll(gridCell_0_0.findNeighbors()));

	assertTrue(gridCell_0_1.findNeighbors().containsAll(Arrays.asList(gridCell_0_0, gridCell_1_0, gridCell_1_1, gridCell_0_2, gridCell_1_2))
			&& Arrays.asList(gridCell_0_0, gridCell_1_0, gridCell_1_1, gridCell_0_2, gridCell_1_2)
			.containsAll(gridCell_0_1.findNeighbors()));

	assertTrue(gridCell_0_2.findNeighbors().containsAll(Arrays.asList(gridCell_0_1, gridCell_1_1, gridCell_1_2)) && Arrays
			.asList(gridCell_0_1, gridCell_1_1, gridCell_1_2).containsAll(gridCell_0_2.findNeighbors()));

	assertTrue(gridCell_1_0.findNeighbors().containsAll(Arrays.asList(gridCell_0_0, gridCell_0_1, gridCell_1_1)) && Arrays
			.asList(gridCell_0_0, gridCell_0_1, gridCell_1_1).containsAll(gridCell_1_0.findNeighbors()));

	assertTrue(gridCell_1_1.findNeighbors().containsAll(Arrays.asList(gridCell_0_0, gridCell_1_0, gridCell_0_1, gridCell_0_2, gridCell_1_2))
			&& Arrays.asList(gridCell_0_0, gridCell_1_0, gridCell_0_1, gridCell_0_2, gridCell_1_2)
			.containsAll(gridCell_1_1.findNeighbors()));

	assertTrue(gridCell_1_2.findNeighbors().containsAll(Arrays.asList(gridCell_0_1, gridCell_1_1, gridCell_0_2)) && Arrays
			.asList(gridCell_0_1, gridCell_1_1, gridCell_0_2).containsAll(gridCell_1_2.findNeighbors()));

    }

    @Test public void testFindNeighborsMiddleOut() throws Exception {

	char[] wordChars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };

	CharacterGrid characterGrid = new CharacterGrid(3, 3, wordChars);

	GridCell gridCell_1_1 = new GridCell(characterGrid, 1, 1);

	assertTrue(gridCell_1_1.findNeighbors().containsAll(Arrays
			.asList(new GridCell(characterGrid, 0, 0), new GridCell(characterGrid, 0, 1), new GridCell(characterGrid, 0, 2),
					new GridCell(characterGrid, 1, 0), new GridCell(characterGrid, 1, 2), new GridCell(characterGrid, 2, 0),
					new GridCell(characterGrid, 2, 1), new GridCell(characterGrid, 2, 2))) && Arrays
			.asList(new GridCell(characterGrid, 0, 0), new GridCell(characterGrid, 0, 1), new GridCell(characterGrid, 0, 2),
					new GridCell(characterGrid, 1, 0), new GridCell(characterGrid, 1, 2), new GridCell(characterGrid, 2, 0),
					new GridCell(characterGrid, 2, 1), new GridCell(characterGrid, 2, 2))
			.containsAll(gridCell_1_1.findNeighbors()));

    }
}