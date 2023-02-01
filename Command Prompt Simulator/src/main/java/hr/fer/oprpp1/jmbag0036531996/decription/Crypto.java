package hr.fer.oprpp1.jmbag0036531996.decription;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * Class for encrypting and decrypting SHA256
 * @author Jura
 *
 */
public class Crypto {

	/**
	 * Main class
	 * @param args
	 */
	public static void main(String[] args) {
		if (args[0].equals("checksha")) checksha(args[1]);
		
		if (args[0].equals("encrypt")) encrypt(args[1] ,args[2], true);
		
		if (args[0].equals("decrypt")) decrypt(args[1], args[2]);
	}
	
	/**
	 * Method for decrypting
	 * @param input
	 * @param output
	 */
	private static void decrypt(String input, String output) {
		encrypt(input, output, false);
		
	}

/**
 * Method for encrypting
 * @param input
 * @param output
 * @param encrypt
 */
	private static void encrypt(String input, String output, boolean encrypt) {
		Scanner s = new Scanner(System.in);
		String keyText, ivText;
		
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		keyText = s.nextLine();
		
		if (keyText.length()!=32) {
			s.close();
			throw new IllegalArgumentException();
		}
		
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		ivText = s.nextLine();
		
		if (ivText.length()!=32) {
			s.close();
			throw new IllegalArgumentException();
		}
		
		s.close();
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			InputStream in = Files.newInputStream(Paths.get(input));
			OutputStream out = Files.newOutputStream(Paths.get(output));
			
			byte[] block = new byte[1024*4];
			byte[] generated;
			int bytesRead;
			
			while((bytesRead = in.read(block)) != -1) {
				generated = cipher.update(block, 0, bytesRead);
				
				if(generated!=null) out.write(generated);
			}
			
			generated = cipher.doFinal();
			out.write(generated);
			in.close();
			out.close();
			}catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (encrypt) System.out.println("Encryption completed. Generated file " + output + " based on file " + input +".");
		else System.out.println("Decryption completed. Generated file " + output+ " based on file " + input + ".\r\n"
				+ "");
	}


	private static void checksha(String string) {
		Scanner s = new Scanner(System.in);
		
		System.out.println("Please provide expected sha-256 digest for " + string + ":");
		
		String digest = s.nextLine();
		s.close();
		
		MessageDigest message = null;
		byte[] digestByte = null;
		
		try {
			message = MessageDigest.getInstance("SHA256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			InputStream stream = Files.newInputStream(Paths.get(string));
			byte[] block = new byte[1024*4];
			int bytesRead;
			
			
			while((bytesRead = stream.read(block)) != -1)  message.update(block,0,bytesRead);
			
			
			stream.close();
			
			digestByte = message.digest();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String generatedDigest = Util.bytetohex(digestByte);
		
		if (digest.equals(generatedDigest)) System.out.println("Digesting completed. Digest of " + string + " matches expected digest.");
		
		else System.out.println("Digesting completed. Digest of " + string +" does not match expected digest. Digest was: " + generatedDigest);

	}
}
