# Decrypt Vigenre Cipher
This script decrypts a message encrypted using Vigenre cipher with a known key length of 5.

Since we know the key length is 5, we split our given cipher text into n sequences (in our case n=5) where each sequence contains the n<sup>th</sup> letter starting with the first. Each of these individual sequences are considered as a caesar cipher as they have been encrypted using the same alphabet/key.

We use the Chi-squared statistic to get the keys for each of the n sequences. The Chi-squared Statistic is a measure of how similar two categorical probability distributions are. We decipher each sequence with each of the 26 possible Caesar ciphers, and compare the frequency distribution of the cipher text with the frequency distribution of English for each key. We get 26 values for the Chi-squared statistic and the correct key will correspond to the deciphered text with the lowest Chi-squared statistic.
