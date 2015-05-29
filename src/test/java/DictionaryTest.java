import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryTest {

    @org.junit.Before public void setUp() throws Exception {

    }

    @Test public void findWordInEmptyDictionary() {

	Dictionary dictionary = new Dictionary();
	assertFalse(dictionary.contains("noexisting"));
    }

    @Test public void testCreateDefaultTrieDictionary() throws Exception {
	Dictionary dictionary = new Dictionary();
	assertEquals(dictionary.getChildDictionaries().length, 26);

    }

    @Test public void testAddWordWithInvalidFirstCharacter() {

	Dictionary dictionary = new Dictionary();
	try {
	    dictionary.addWord("_A");
	    fail();
	} catch (Exception ex) {
	    assertEquals(ex.getMessage(), "Invalid character _");
	}

    }

    @Test public void testAddWordWithSingleCharacter() {

	Dictionary dictionary = new Dictionary();

	dictionary.addWord("C");

        Dictionary firstLevelDictionary = dictionary.getChildDictionaries()[2];
        assertNotNull(firstLevelDictionary);
        assertTrue(firstLevelDictionary.isWord());

    }

    @Test public void testAddWordWithMultiCharacters() {

	Dictionary dictionary = new Dictionary();

	dictionary.addWord("dog");

        Dictionary firstLevelDictionary = dictionary.getChildDictionaries()[3];
        assertNotNull(firstLevelDictionary);
        assertFalse(firstLevelDictionary.isWord());

        Dictionary secondLevelDictionary = firstLevelDictionary.getChildDictionaries()[14];

        assertNotNull(secondLevelDictionary);
        assertFalse(secondLevelDictionary.isWord());

        Dictionary thirdLevelDictionary = secondLevelDictionary.getChildDictionaries()[6];

        assertNotNull(thirdLevelDictionary);
        assertTrue(secondLevelDictionary.isWord());

    }

    @Test public void findWordInDictionary() {

	Dictionary dictionary = new Dictionary();
	dictionary.addWord("aardvark");
	assertTrue(dictionary.contains("aardvark"));
    }

    @org.junit.After public void tearDown() throws Exception {

    }
}