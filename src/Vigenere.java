import java.io.IOException;

/**
 * Author: Marco Chiappetta
 * Email: marcoc@sabanciuniv.edu
 */
public class Vigenere {

    private static void printUsageVc() {
        System.out.println("Usage: Vigenere -encdec plaintext_file ciphertext_file key_file");
    }

    private static void printUsageVb() {
        System.out.println("Usage: Vigenere -break ciphertext_file dictionary_file plaintext_file");
    }

    public static void main(String[] args) throws IOException {
        if (args[0].equals("-encdec")) {
            VigenereCipher vc;

            if (args.length != 4) {
                printUsageVc();
                return;
            }
            vc = new VigenereCipher(args[1], args[2], args[3]);
            vc.execute();
            System.out.println(vc);
        } else if (args[0].equals("-break")) {
            VigenereBreaker vb;

            if (args.length != 4) {
                printUsageVb();
                return;
            }
            vb = new VigenereBreaker(args[1], args[2], args[3]);


        } else {
            System.out.println("Invalid operation. Please select either -encdec for encryption/decryption or -break to break a ciphertext.");
            printUsageVb();
            printUsageVc();
        }


    }
}
