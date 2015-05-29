import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CharacterGridTest {

    @Test public void testGridSize() {

	char[] wordChars = { 'A', 'B', 'C', 'D', 'E', 'F' };
	CharacterGrid characterGrid = new CharacterGrid(2, 3, wordChars);
	assertEquals(characterGrid.getNumberOfRows(), 2);
	assertEquals(characterGrid.getNumberOfColumns(), 3);
    }

    @Test public void testInvalidWordGrid() {
	char[] wordChars = { 'A', 'B', 'C', 'D', 'E', 'F' };
	try {
	    new CharacterGrid(4, 5, wordChars);
	    fail();
	} catch (Exception ex) {
	    assertEquals(ex.getMessage(), "20 characters expected. 6 provided");
	}

    }

    @Test public void testWordGridCharacters() {
        char[] wordChars = { 'A', 'B', 'C', 'D', 'E', 'F' };
        CharacterGrid characterGrid = new CharacterGrid(2, 3, wordChars);
        char[][] charMatrix = characterGrid.getGridCharsMatrix();
        assertEquals(charMatrix[0][0],'a');
        assertEquals(charMatrix[0][1],'b');
        assertEquals(charMatrix[0][2],'c');
        assertEquals(charMatrix[1][0],'d');
        assertEquals(charMatrix[1][1],'e');
        assertEquals(charMatrix[1][2],'f');
    }
}