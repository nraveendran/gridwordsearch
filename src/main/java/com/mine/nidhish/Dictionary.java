package com.mine.nidhish;

/**
 * A dictionary based on Trie datastructure
 */
public class Dictionary {

    public static final int NUM_LETTERS_IN_ALPHABET = 26;
    public static final char LOWERCASE_A = 'a';
    Dictionary[] childDictionaries;
    boolean isWord = false;
    private int numberOfChildren;

    /**
     * provide ability to have a bigger symbol set starting with 'a'
     */
    public Dictionary() {
	childDictionaries = new Dictionary[NUM_LETTERS_IN_ALPHABET];
    }

    /**
     * Check if a word is present in Dictionary
     */
    public boolean containsWord(String wordToFind) {
	if (wordToFind == null || "".equals(wordToFind)) {
	    return false;
	}

	Dictionary lastReachableDictionary = getLastReachableDictionary(wordToFind);

	//if the sequence was not found in the dictionary, it means the word is not found
	// if the sequence was found but it was not marked as a word, it mean the sequence found was a mere prefix of another word
	if (lastReachableDictionary != null && lastReachableDictionary.isWord()) {
	    return true;
	}

	return false;
    }

    /**
     * Check if a word is a prefix for other words.
     *
     * @param characterSequence the characterSequence to be checked
     * @return true if the characterSequence is a prefix for other words else returns false
     */
    public boolean isPrefixForWords(String characterSequence) {

	if (characterSequence == null || "".equals(characterSequence)) {
	    return false;
	}

	Dictionary lastReachableDictionary = getLastReachableDictionary(characterSequence);

	//if the sequence was not found in the dictionary, it means the word is not found
	// if the sequence was found and it has children, it means this character sequence is a prefix of other words
	if (lastReachableDictionary != null && lastReachableDictionary.hasChildren()) {
	    return true;
	}

	return false;

    }

    private boolean hasChildren() {
	return this.getNumberOfChildren() > 0;
    }

    /**
     * Walks through a sequence of characters making sure that the dictionary levels can be followed till the last character of sequence. If a character can't be followed, immediately returns null
     *
     * @param characterSequence the sequence of characters to find in the dictionary
     * @return null if the characterSequence can't be found
     * the node for the last character of the sequence if the sequence is found
     */
    private Dictionary getLastReachableDictionary(String characterSequence) {
	Dictionary currentDictionary = this;

	characterSequence = characterSequence.toLowerCase();
	int wordLength = characterSequence.length();
	for (int i = 0; i < wordLength; i++) {
	    char currentCharacter = characterSequence.charAt(i);
	    int indexOfCurrentCharacter = currentCharacter - LOWERCASE_A;

	    validateCharacterIndex(currentCharacter, indexOfCurrentCharacter);

	    Dictionary childDictionaryAtIndex = currentDictionary.childDictionaries[indexOfCurrentCharacter];

	    if (childDictionaryAtIndex == null) {
		return null;
	    }

	    currentDictionary = childDictionaryAtIndex;
	}
	return currentDictionary;
    }

    private void validateCharacterIndex(char currentCharacter, int indexOfCurrentCharacter) {
	if (indexOfCurrentCharacter < 0 || indexOfCurrentCharacter >= NUM_LETTERS_IN_ALPHABET) {
	    throw new RuntimeException("Invalid character " + currentCharacter);
	}
    }

    /**
     * Add a word to the dictionary
     */
    public void addWord(String word) {

	Dictionary currentDictionary = this;

	if (word != null && !"".equals(word)) { // StringUtil.isNotEmpty if using apache commons
	    //currently we support only a dictionary of lower case letters. Upper case strings will be converted to lower case strings
	    word = word.toLowerCase();
	    int wordLength = word.length();
	    for (int i = 0; i < wordLength; i++) {
		char currentCharacter = word.charAt(i);
		int indexOfCurrentCharacter = currentCharacter - LOWERCASE_A;

		validateCharacterIndex(currentCharacter, indexOfCurrentCharacter);

		Dictionary childDictionaryAtIndex = currentDictionary.childDictionaries[indexOfCurrentCharacter];
		if (childDictionaryAtIndex == null) {
		    childDictionaryAtIndex = new Dictionary();
		    currentDictionary.childDictionaries[indexOfCurrentCharacter] = childDictionaryAtIndex;
		    currentDictionary.incrementNumberOfChildren();
		}

		currentDictionary = childDictionaryAtIndex;
	    }

	    currentDictionary.setWord(true);

	}
    }

    private void incrementNumberOfChildren() {
	numberOfChildren++;
    }

    protected Dictionary[] getChildDictionaries() {
	return childDictionaries;
    }

    public boolean isWord() {
	return isWord;
    }

    public void setWord(boolean isWord) {
	this.isWord = isWord;
    }

    public int getNumberOfChildren() {
	return numberOfChildren;
    }

}
