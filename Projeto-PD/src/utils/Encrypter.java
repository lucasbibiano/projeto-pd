package utils;

public class Encrypter {
	public static String encrypt(String string) {
		return rot13(string);
	}
	
	public static String rot13(String s) {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < s.length(); i++) {
	        char c = s.charAt(i);
	        if       (c >= 'a' && c <= 'm') c += 13;
	        else if  (c >= 'A' && c <= 'M') c += 13;
	        else if  (c >= 'n' && c <= 'z') c -= 13;
	        else if  (c >= 'N' && c <= 'Z') c -= 13;
	        
	        builder.append(c);
	    }
		
		return s;
	}
}
