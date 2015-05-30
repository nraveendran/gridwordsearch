package com.mine.nidhish;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryTest {

    @org.junit.Before public void setUp() throws Exception {

    }



    @Test public void testCreateDefaultDictionary() throws Exception {
	Dictionary dictionary = new Dictionary();
	Assert.assertEquals(dictionary.getChildDictionaries().length, 26);

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
        assertTrue(thirdLevelDictionary.isWord());

    }


    @Test public void testWordsWithCommonPrefix() {

	Dictionary dictionary = new Dictionary();

	dictionary.addWord("ants");
	dictionary.addWord("and");

	Dictionary firstLevelDictionary = dictionary.getChildDictionaries()[0];
	Dictionary secondLevelDictionary = firstLevelDictionary.getChildDictionaries()[13];
	Dictionary thirdLevelDictionaryForAnd = secondLevelDictionary.getChildDictionaries()[3];

	assertTrue(thirdLevelDictionaryForAnd.isWord());

	Dictionary thirdLevelDictionaryForAnt = secondLevelDictionary.getChildDictionaries()[19];
	assertFalse(thirdLevelDictionaryForAnt.isWord());
	Dictionary fourthLevelDictionaryForAnt = thirdLevelDictionaryForAnt.getChildDictionaries()[18];
	assertTrue(fourthLevelDictionaryForAnt.isWord());
    }

    @Test public void testAddExistingWord() {

	Dictionary dictionary = new Dictionary();

	dictionary.addWord("dogged");

	Dictionary firstLevelDictionary = dictionary.getChildDictionaries()[3];
	Dictionary secondLevelDictionary = firstLevelDictionary.getChildDictionaries()[14];
	Dictionary thirdLevelDictionary = secondLevelDictionary.getChildDictionaries()[6];


	assertFalse(thirdLevelDictionary.isWord());

        dictionary.addWord("dog");

        assertTrue(thirdLevelDictionary.isWord());

    }

    @Test public void testNumberOfChildren() {

	Dictionary dictionary = new Dictionary();

	dictionary.addWord("abac");
        dictionary.addWord("abstract");
        dictionary.addWord("accent");
	dictionary.addWord("ad");

	Dictionary firstLevelDictionary = dictionary.getChildDictionaries()[0];
        Assert.assertEquals(firstLevelDictionary.getNumberOfChildren(), 3);

    }

    @Test public void testNumberOfChildrenAtEachSuffix() {

	Dictionary dictionary = new Dictionary();

	dictionary.addWord("ants");
	dictionary.addWord("and");

	Dictionary firstLevelDictionary = dictionary.getChildDictionaries()[0];
        Assert.assertEquals(firstLevelDictionary.getNumberOfChildren(), 1);

	Dictionary secondLevelDictionary = firstLevelDictionary.getChildDictionaries()[13];
        Assert.assertEquals(secondLevelDictionary.getNumberOfChildren(), 2);

	Dictionary thirdLevelDictionaryForAnd = secondLevelDictionary.getChildDictionaries()[3];
        Assert.assertEquals(thirdLevelDictionaryForAnd.getNumberOfChildren(), 0);

	Dictionary thirdLevelDictionaryForAnt = secondLevelDictionary.getChildDictionaries()[19];
	Assert.assertEquals(thirdLevelDictionaryForAnt.getNumberOfChildren(), 1);
	Dictionary fourthLevelDictionaryForAnt = thirdLevelDictionaryForAnt.getChildDictionaries()[18];
	Assert.assertEquals(fourthLevelDictionaryForAnt.getNumberOfChildren(), 0);
    }

    @Test public void testFindWordInEmptyDictionary() {

	Dictionary dictionary = new Dictionary();
	assertFalse(dictionary.containsWord("noexisting"));
    }

    @Test public void testFindEmptyWord() {

	Dictionary dictionary = new Dictionary();
        dictionary.addWord("administrationist");
        assertFalse(dictionary.containsWord(""));
    }

    @Test public void testFindWordInDictionary() {

	Dictionary dictionary = new Dictionary();
	dictionary.addWord("administrationist");
	assertTrue(dictionary.containsWord("administrationist"));
        assertFalse(dictionary.containsWord("admin"));
        assertFalse(dictionary.containsWord("zzzz"));
        dictionary.addWord("admin");
        assertTrue(dictionary.containsWord("admin"));
    }

    @Test public void testIsWordPrefix() {

	Dictionary dictionary = new Dictionary();
	dictionary.addWord("administrationist");
        dictionary.addWord("administration");
	assertTrue(dictionary.isPrefixForWords("admini"));
	assertTrue(dictionary.isPrefixForWords("administration"));
	assertTrue(dictionary.isPrefixForWords("administrationis"));
        assertFalse(dictionary.isPrefixForWords("administrationist"));
        assertFalse(dictionary.isPrefixForWords("admina"));
        assertFalse(dictionary.isPrefixForWords("administratione"));
    }

    @org.junit.After public void tearDown() throws Exception {

    }
}