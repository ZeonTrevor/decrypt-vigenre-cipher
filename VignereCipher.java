import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VignereCipher {

	//Frequencies obtained from http://en.wikipedia.org/wiki/Letter_frequency#Relative_frequencies_of_letters_in_the_English_language
	static double[] engFreq = new double[]{0.08167, 0.01492, 0.02782, 0.04253, 0.12702, 0.02228, 0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025, 0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987, 0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150, 0.01974, 0.00074};

	//To obtain the plaintext given the ciphertext and the key
	static String decipherVigenere(String cipherText, String key)
	{
	    StringBuilder plainText = new StringBuilder();
	    int keyLength = key.length();
	    int difference;
	    char decoded;
	 
	    for (int i = 0; i < cipherText.length(); i++)
	    {
	        difference = cipherText.charAt(i) - key.charAt(i%keyLength);
	 
	        if(difference < 0)
	            difference += 26;
	 
	        decoded = (char)(difference + 'a') ;
	        plainText.append(decoded);
	    }
	 
	    return plainText.toString();
	}
	
	//To obtain the key for each cipher block by calculating the chi-squared test
	static char calChiSquareStat(String cipherBlock){

		int[] freq = new int[26];
		int strLen = cipherBlock.length();
		double chiSquareVal;
		double sum,min = 0.0;
		int index = 0;

		for(int j=0;j<26;j++){
			sum = 0.0;
			Arrays.fill(freq, 0);
			for(char ch: cipherBlock.toCharArray()){
				if(ch - j < 97)
					ch = (char)(ch - j + 26);
				else
					ch = (char)(ch - j);
				freq[ch - 'a']++;
			}

			for(int i=0;i<26;i++)
			{
				chiSquareVal = Math.pow((freq[i] - engFreq[i]*strLen), 2)/(engFreq[i]*strLen);
				sum += chiSquareVal;
			}
			if(j==0)
				{min = sum;index =j;}
			if(sum < min)
				{min = sum;index=j;}
		}
		
		return (char)('a' + index);
	}
	
	static String readFile(String fileName){
	    BufferedReader br;
	    StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(fileName));
		
			String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	    	br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        return sb.toString();
	}
	
	public static void main(String[] args){
		String cipherText = readFile("q5ciphertext.txt");
		String[] splitCipherText = new String[]{"","","","",""};
		String keyObtained = "";
		
		for(int i=0;i<cipherText.length();i++)
		{
			if(i%5 == 0)
				splitCipherText[0] += cipherText.charAt(i);
			else if(i%5 == 1)
				splitCipherText[1] += cipherText.charAt(i);
			else if(i%5 == 2)
				splitCipherText[2] += cipherText.charAt(i);
			else if(i%5 == 3)
				splitCipherText[3] += cipherText.charAt(i);
			else if(i%5 == 4)
				splitCipherText[4] += cipherText.charAt(i);
		}
		
		for(String s:splitCipherText){
			keyObtained += calChiSquareStat(s);
		}

		System.out.println("Key Obtained:" + keyObtained);
		String plainText = decipherVigenere(cipherText, keyObtained);
		System.out.println("plain text:" + plainText);
	}
}
