import org.junit.Test;

import java.util.Set;

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

    @Test public void testGetAllWords1by1Grid() {
	char[] wordChars = { 'A'};
	Dictionary dictionary = new Dictionary();
	dictionary.addWord("a");

	CharacterGrid characterGrid = new CharacterGrid(1, 1, wordChars);

	Set<String> wordSet = characterGrid.findWordMatchingDictionary(dictionary);
        assertEquals(wordSet.size(),1);
        assertEquals(wordSet.toArray()[0],"a");
    }

    @Test public void testGet2LetterWord1by2Grid() {
	char[] wordChars = { 'A','B'};
	Dictionary dictionary = new Dictionary();
	dictionary.addWord("ab");

	CharacterGrid characterGrid = new CharacterGrid(1, 2, wordChars);

	Set<String> wordSet = characterGrid.findWordMatchingDictionary(dictionary);
	assertEquals(wordSet.size(),1);
	assertEquals(wordSet.toArray()[0],"ab");
    }

//    @Test public void testGetAllWordsFromGrid() {
//	char[] wordChars = { 'A', 'N', 'T','S'};
//        Dictionary dictionary = new Dictionary();
//        dictionary.addWord("ant");
//        dictionary.addWord("tan");
//        
//	CharacterGrid characterGrid = new CharacterGrid(2, 2, wordChars);
//        
//        Set wordSet = characterGrid.findWordMatchingDictionary(dictionary);
//    }
}