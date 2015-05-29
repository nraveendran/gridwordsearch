/**
 * Created by nidhish on 5/28/15.
 */
public class Dictionary {

    public static final int NUM_LETTERS_IN_ALPHABET = 26;
    Dictionary[] childDictionaries;
    boolean isWord = false;

    /*
    provide ability to have a bigger symbol set starting with 'a'
     */
    public Dictionary() {
	childDictionaries = new Dictionary[NUM_LETTERS_IN_ALPHABET];
    }

    /*
      Check if a word is present in a TrieDictionary
     */
    public boolean contains(String wordToFind) {
	return false;
    }

    /*
     Add a word to the dictionary
     */
    public void addWord(String word) {

        Dictionary currentDictionary = this;

	if (word != null && !"".equals(word)) { // StringUtil.isNotEmpty if using apache commons
	    //currently we support only a dictionary of lower case letters. Upper case strings will be converted to lower case strings
	    word = word.toLowerCase();
	    int wordLength = word.length();
	    for (int i = 0; i < wordLength; i++) {
		char currentCharacter = word.charAt(i);
		int indexOfCurrentCharacter = currentCharacter - 'a';

		if (indexOfCurrentCharacter < 0 || indexOfCurrentCharacter >= NUM_LETTERS_IN_ALPHABET) {
		    throw new RuntimeException("Invalid character " + currentCharacter);
		}

	        Dictionary childDictionaryAtIndex = currentDictionary.childDictionaries[indexOfCurrentCharacter];
	        if (childDictionaryAtIndex == null) {
	            childDictionaryAtIndex = new Dictionary();
	            currentDictionary.childDictionaries[indexOfCurrentCharacter] = childDictionaryAtIndex;
		}

	        if (i==wordLength-1){
	            childDictionaryAtIndex.setWord(true);
	        }

	        currentDictionary = childDictionaryAtIndex;
	    }
	}
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
}
