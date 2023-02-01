package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DictionaryTest {
	
	@Test
	public void InputNull() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> d.put(null, null));
	
}
	
	@Test
	public void testNotNull() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		d.put(1, 3);
		assertEquals(d.get(1), 3);
}
	@Test
	public void sizeUp() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		d.put(1, 3);
		assertEquals(d.size(), 1);
}
	@Test
	public void sizeDown() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		d.put(1, 3);
		d.remove(1);
		assertEquals(d.size(), 0);
}
	@Test
	public void empty() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		assertEquals(d.isEmpty(), true);
}
	@Test
	public void notempty() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		d.put(1, 5);
		assertEquals(d.isEmpty(), false);
}
	@Test
	public void madeEmpty() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		d.put(1, 5);
		d.remove(1);
		assertEquals(d.isEmpty(), true);
}
	@Test
	public void clear() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		d.put(1, 5);
		d.put(2, 2);
		d.clear();
		assertEquals(d.isEmpty(), true);
}
	@Test
	public void putReturn() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		d.put(1, 5);
		assertEquals(d.put(1,4), 5);
}
	@Test
	public void putReturn2() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		d.put(1, 5);
		d.put(1,4);
		assertEquals(d.get(1), 4);
}
	@Test
	public void putReturn3() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		assertEquals(d.put(1,4), null);
}
	@Test
	public void getNothing() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		d.put(1, 5);
		assertEquals(d.get(2), null);
}
	@Test
	public void getSth() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		d.put(1, 5);
		assertEquals(d.get(1), 5);
}
	@Test
	public void removeNothing() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		d.put(1, 5);
		assertEquals(d.remove(2), null);
}
	@Test
	public void removeSth() {
		Dictionary<Integer, Integer> d = new Dictionary<>();
		d.put(1, 5);
		assertEquals(d.remove(1), 5);
}
}
