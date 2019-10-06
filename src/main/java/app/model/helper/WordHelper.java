package app.model.helper;

import org.apache.log4j.Logger;

import app.main.Application;

public class WordHelper {
	
	private final static Logger LOGGER = Logger.getLogger(WordHelper.class);
	
	public static String computeRelativeCrypto(String word) {
        String compare = "";
        String rel_crypto = word;
        int count = 1;
        
        for(int i = 0; i<word.length(); i++){
            
            String character = word.substring(i, i+1);
            
            if(compare.lastIndexOf(character) == -1){
                
                compare+=character;
                String crypto = Character.toString((char) (64 + count));
                rel_crypto = rel_crypto.replaceAll(character, crypto);
                count++;
                
            }
        }
        
        return rel_crypto;
	}

	public static String numericalToAlphabetic(String numericalCrypto) {
		
		numericalCrypto = numericalCrypto.trim();		
		String[] letters = numericalCrypto.split("-");
		String word = "";
		for(String s : letters) {
			s.trim();
			word += Character.toString((char) (94 + Integer.parseInt(s)));
		}
		String alphabeticCrypto = computeRelativeCrypto(word);
		LOGGER.debug("numerical key " + numericalCrypto + " converted to " + alphabeticCrypto);
		
		return alphabeticCrypto;
	}

}
