import java.math.BigInteger;
import java.util.*;

/**
 * Created by mardurhack-ubuntu on 10/6/14.
 */
public class VigenereBreaker {

    VigenereCipher vc;

    String ciphertext, dictionaryDump, alphabet = "a b c d e f g h i j k l m n o p q r s t u v w x y z";

    int keyLength = 0;

    Map<String, Double> letterFrequency;
    List<String> dictionary;
    List<String> charactersByFrequency;

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

        letterFrequency = new HashMap<String, Double>();
        dictionary = new ArrayList<String>();
        charactersByFrequency = new ArrayList<String>();

        // Separate words into a list
        for (String word : dictionaryDump.split("\n")) {
            dictionary.add(word);
        }
        populateLetterFrequency(dictionary);
        keyLength = findKeyLength(ciphertext);
        System.out.println(keyLength);
        findKey(ciphertext, keyLength);
    }

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
        double oldCount;

        if (dictionary == null) {
            throw new RuntimeException("ERROR: given dictionary is null");
        }

        // Generate alphabet
        for (String c : alphabet.split(" ")) {
            letterFrequency.put(c, 0.0);
        }

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

        // Convert absolute frequency in percentage
        for (String c : letterFrequency.keySet()) {
            oldCount = letterFrequency.get(c);
            oldCount /= dictionaryDump.length();
            letterFrequency.put(c, oldCount);
        }

        // Sort characters by frequency and put them into a list
        for (String w : letterFrequency.keySet()) {
            charactersByFrequency.add(w);
        }
        Collections.sort(charactersByFrequency, new FrequencyComparator(letterFrequency));
    }

    /**
     * Find the length of the key given a ciphertext.
     * @param ciphertext
     * @return
     */
    int findKeyLength(String ciphertext) {
        String strip1 = ciphertext;
        String strip2 = ciphertext.substring(1);
        int curMatch = 0, maxMatch = 0, curShift = 1, maxShift = 1;
        BigInteger tempMaxShift, tempCurShift, oneBig = new BigInteger(Integer.toString(1));

        while (strip2.length() > maxMatch) {
            for (int i = 0; i < strip2.length(); i++) {
                if (strip1.charAt(i) == strip2.charAt(i)) {
                    curMatch++;
                }
            }
            tempMaxShift = new BigInteger(Integer.toString(maxShift));
            tempCurShift = new BigInteger(Integer.toString(curShift));
            // Of all the greatest shifts that share the same GCD take the lowest
            // Why? I dunno, just intuition. Maybe I'm wrong, maybe not!
            // But if the key is 6 (e.g. turing) sometimes maxShift becomes 6, 9 and 24 and 6 is the right length.
            if (curMatch > maxMatch) { // && tempCurShift.gcd(tempMaxShift).compareTo(oneBig) == 0) {
                System.out.println("Found " + curMatch + " with shift " + curShift);
                maxMatch = curMatch;
                maxShift = curShift;
            }
            curMatch = 0;
            strip2 = strip2.substring(1); // Slide left by one character
            curShift++;
        }
        return maxShift;
    }

    /**
     * Find the key given the ciphertext and the length of the key.
     * @param ciphertext
     * @param keyLength
     * @return
     */
    String findKey(String ciphertext, int keyLength) {
        List<Double> W = new ArrayList<Double>(); // Frequency of each letter of the alphabet in the ciphertext
        List<Double> A = new ArrayList<Double>(); // Frequency of each letter of the alphabet in English (shifted)
        String[] realAlphabet = alphabet.split(" ");
        StringBuilder key = new StringBuilder();
        int pos, occs;


        // Map alphabet index (0 = A, 1 = B etc.) to frequency
        for (String c : realAlphabet) {
            A.add(letterFrequency.get(c));
        }

        // Find frequency for each i-th letter, according to the key length
        for (int i = 0; i < keyLength; i++) {
            pos = i;
            while (pos < ciphertext.length()) {
                occs = countOccurrencies(ciphertext, ciphertext.substring(pos, pos + 1));
                pos += keyLength;
            }
        }

        return null;
    }

    double dotProduct(List<Integer> W, List<Integer> A) {
        // Assume W.length() == A.length()
        double result = 0;
        for (int i = 0; i < W.size(); i++) {
            result += W.get(i) * A.get(i);
        }
        return result;
    }

    /**
     * Sort characters in a list depending on their frequency in the
     * given dictionary.
     */
    private class FrequencyComparator implements Comparator<String> {

        Map<String, Double> lettersFrequency;

        public FrequencyComparator(Map<String, Double> lettersFrequency) {
            this.lettersFrequency = lettersFrequency;
        }

        @Override
        public int compare(String o1, String o2) {
            Double f1, f2;
            f1 = lettersFrequency.get(o1);
            f2 = lettersFrequency.get(o2);
            return f2.compareTo(f1);
        }
    }
}
