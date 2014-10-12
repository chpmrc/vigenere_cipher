# Vigenere Cipher

This is an implementation of Vigenere cipher in Java. The user is allowed to either encrypt/decrypt some text file or break (TODO) a given ciphertext without knowing the key nor the key length.

## Building

Simply import the project in your IntelliJ IDEA and build it. Alternatively all the classes are in the `src` folder and can be compiled manually using `javac`.

*Note:* only tested with Oracle JDK 7. Should also work with OpenJDK 7 though. 

## Usage

### Encryption/Decryption

From a terminal:

`java Vigenere -encdec path_to_plaintext path_to_key path_to_ciphertext`

Either the pair (key, plaintext) or (key, ciphertext) has to exist. If no plaintext is found the ciphertext is decrypted in the given `path_to_plaintext` file. If the plaintext is found it is encrypted into the `path_to_ciphertext` folder.

*Note:* all files are overwritten without prompt!

### Breaking the cipher

An English dictionary with a big number of words (~200000) is required. The dictionary must be a text file with a word per line, not necessarily sorted. One is freely downloadable here: [http://www.math.sjsu.edu/~foster/dictionary.txt](http://www.math.sjsu.edu/~foster/dictionary.txt).

From a terminal:

`java Vigenere -break path_to_ciphertext path_to_dictionary path_to_plaintext`

The key will be written both on the standard output and a file named `key`_path_to_plaintext (notice the prefix).
__Note__: breaking a Vigenere cipher through statistical analysis requires a ciphertext with a large number of characters. If the ciphertext is too short you might recover the key only partially (or not at all).
