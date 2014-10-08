import java.util.*;

/**
 * Created by mardurhack-ubuntu on 10/6/14.
 */
public class VigenereBreaker {

    VigenereCipher vc;

    String ciphertext, dictionaryDump, alphabet = "a b c d e f g h i j k l m n o p q r s t u v w x y z";

    int keyLength = 0;

    Map<String, Integer> letterFrequency;
    List<String> dictionary;
    List<String> wordsByFrequency;

    /**
     * Compute the frequency of each character in the alphabet
     * by scanning each word in the given dictionary.
     * Finally build a list of the characters sorted by frequency.
     * @param dictionary
     * @throws RuntimeException
     */
    private void populateLetterFrequency(List<String> dictionary) throws RuntimeException {
        char[] wordChars;
        String currentChar;
        int oldCount;

        if (dictionary == null) {
            throw new RuntimeException("ERROR: given dictionary is null");
        }

        // Generate alphabet
        for (String c : alphabet.split(" ")) {
            letterFrequency.put(c, 0);
        }

        int counter = 0;
        // Scan each word and analyze characters
        for (String w : dictionary) {
            wordChars = w.toCharArray();

            for (int i = 0; i < w.length(); i++) {
                currentChar = String.valueOf(Character.toLowerCase(wordChars[i]));
                if (letterFrequency.containsKey(currentChar)) {
                    oldCount = letterFrequency.get(currentChar);
                    letterFrequency.put(currentChar, oldCount + 1);
                }
            }
        }

        // Sort characters by frequency and put them into a list
        for (String w : letterFrequency.keySet()) {
            wordsByFrequency.add(w);
        }
        Collections.sort(wordsByFrequency, new FrequencyComparator(letterFrequency));

        System.out.println(wordsByFrequency);

    }

    /**
     * Constructor.
     * Takes the paths to the ciphertext and an English dictionary and
     * builds up the breaker.
     * @param ciphertextFilename
     * @param dictionaryFilename
     */
    public VigenereBreaker(String ciphertextFilename, String dictionaryFilename) {
        // vc = new VigenereCipher(plaintextFilename, ciphertextFilename, keyFilename);
        ciphertext = FileUtils.readFileContent(ciphertextFilename, true);
        dictionaryDump = FileUtils.readFileContent(dictionaryFilename);

        letterFrequency = new HashMap<String, Integer>();
        dictionary = new ArrayList<String>();
        wordsByFrequency = new ArrayList<String>();

        // Separate words into a list
        for (String word : dictionaryDump.split("\n")) {
            dictionary.add(word);
        }
        populateLetterFrequency(dictionary);
    }

    /**
     * Sort characters in a list depending on their frequency in the
     * given dictionary.
     */
    private class FrequencyComparator implements Comparator<String> {

        Map<String, Integer> lettersFrequency;

        public FrequencyComparator(Map<String, Integer> lettersFrequency) {
            this.lettersFrequency = lettersFrequency;
        }

        @Override
        public int compare(String o1, String o2) {
            Integer f1, f2;
            f1 = lettersFrequency.get(o1);
            f2 = lettersFrequency.get(o2);
            return f2.compareTo(f1);
        }
    }
}
