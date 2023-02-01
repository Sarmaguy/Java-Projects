package hr.fer.oprpp1.jmbag0036531996.decription;
/**
 * Helper class for parsing to hex / to bytes
 * @author Jura
 *
 */
public class Util {
	/**
	 * Converts hex to bytes
	 * @param keyText
	 * @return
	 */
	public static byte[] hextobyte(String keyText) {
		int n = keyText.length();
		
		if(n % 2 != 0)
			throw new IllegalArgumentException();
	
		byte[] l = new byte[n/2];
		
		for (int i = 0; i < n; i+=2) {
			char c1 = keyText.charAt(i);
			char c2 = keyText.charAt(i+1);
			
			if (!((c1>='A' && c1<='F') || (c1>='0' && c1<='9') || (c1>='a' && c1<='f'))) throw new IllegalArgumentException();
			
			if (!((c2>='A' && c2<='F') || (c2>='0' && c2<='9') || (c2>='a' && c2<='f'))) throw new IllegalArgumentException();
		
			l[i/2] = (byte) ((Integer.parseInt(""+c1,16)<<4) + Integer.parseInt(""+c2,16));
		
		}
		
		
		
		return l;
	}
	/**
	 * Converts bytes to hex
	 * @param bytearray
	 * @return
	 */
	public static String bytetohex(byte[] bytearray) {
		String s="";
		
		for (byte b : bytearray) {
			if (Math.abs(b)<10) s += "0";
			if (b>=0) s += Integer.toHexString(b);
			
			else s+= Integer.toHexString(b).substring(6);
		}
		
		return s;
	}


public static void main(String[] args) {
	char c ='a';
	byte b=-82;
	byte[] l= hextobyte("01aE22");
	for (byte d : l) {
		System.out.println(d);
	}
}
}
