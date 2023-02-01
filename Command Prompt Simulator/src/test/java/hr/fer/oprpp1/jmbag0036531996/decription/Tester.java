package hr.fer.oprpp1.jmbag0036531996.decription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class Tester {
	@Test
	public void IllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("123"));
	}
	
	@Test
	public void IllegalArgumentException1() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("xy"));
	}
	
	
	@Test
	public void emptyStringHexToByteTest() {
		byte[] array = Util.hextobyte("");
		
		assertEquals(0, array.length);
	}

	@Test
	public void hexToByteTest() {
		byte[] arrayLowercase = Util.hextobyte("01ae22");
		byte[] arrayUppercase = Util.hextobyte("01AE22");
		byte[] arrayLandU = Util.hextobyte("01Ae22");
		
		assertTrue(Arrays.equals(new byte[] {1,  -82, 34}, arrayLowercase));
		assertTrue(Arrays.equals(new byte[] {1,  -82, 34}, arrayUppercase));
		assertTrue(Arrays.equals(new byte[] {1,  -82, 34}, arrayLandU));
	}
	
	
	@Test
	public void byteToHexTest() {
		String str = Util.bytetohex(new byte[] {1, -82, 34});
		
		assertEquals("01ae22", str);
	}
}
