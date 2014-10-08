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

TODO
