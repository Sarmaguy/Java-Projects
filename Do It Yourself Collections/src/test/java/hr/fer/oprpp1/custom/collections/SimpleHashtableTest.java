package hr.fer.oprpp1.custom.collections;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {

	@Test
	public void ConstructorLessThenOne() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(0));
	
}
	@Test
	public void InputNull() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		assertThrows(NullPointerException.class, () -> d.put(null, null));
	
}
	@Test
	public void testNotNull() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<Integer, Integer>();
		d.put(1, 3);
		assertEquals(d.get(1), 3);
}
	@Test
	public void sizeUp() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<Integer, Integer>();
		d.put(1, 3);
		assertEquals(d.size(), 1);
}
	@Test
	public void sizeDown() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		d.put(1, 3);
		d.remove(1);
		assertEquals(d.size(), 0);
}
	@Test
	public void empty() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		assertEquals(d.isEmpty(), true);
}
	@Test
	public void notempty() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		d.put(1, 5);
		assertEquals(d.isEmpty(), false);
}
	@Test
	public void madeEmpty() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		d.put(1, 5);
		d.remove(1);
		assertEquals(d.isEmpty(), true);
}
	@Test
	public void clear() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		d.put(1, 5);
		d.put(2, 2);
		d.clear();
		assertEquals(d.isEmpty(), true);
}
	@Test
	public void putReturn() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		d.put(1, 5);
		assertEquals(d.put(1,4), 5);
}
	@Test
	public void putReturn2() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		d.put(1, 5);
		d.put(1,4);
		assertEquals(d.get(1), 4);
}
	@Test
	public void putReturn3() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		assertEquals(d.put(1,4), null);
}
	@Test
	public void getNothing() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		d.put(1, 5);
		assertEquals(d.get(2), null);
}
	@Test
	public void getSth() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		d.put(1, 5);
		assertEquals(d.get(1), 5);
}
	@Test
	public void removeNothing() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		d.put(1, 5);
		assertEquals(d.remove(2), null);
}
	@Test
	public void removeSth() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		d.put(1, 5);
		assertEquals(d.remove(1), 5);
}
	@Test
	public void iteratorNotNull() {
		SimpleHashtable<Integer, Integer> d = new SimpleHashtable<>();
		Iterator<TableEntry<Integer, Integer>> iter = d.iterator();
		assertNotNull(iter);
	}
	@Test
	public void removeAll() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
		SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
		iter.remove();
		}
		assertEquals(examMarks.size(),0);
	}
	@Test
	public void removeThrows() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
		SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
		if(pair.getKey().equals("Ivana")) {
		iter.remove();
		assertThrows(IllegalStateException.class,() -> iter.remove());
		}
		}
	}

		@Test
		public void removeThrows2() {
			SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
			// fill data:
			examMarks.put("Ivana", 2);
			examMarks.put("Ante", 2);
			examMarks.put("Jasna", 2);
			examMarks.put("Kristina", 5);
			examMarks.put("Ivana", 5); // overwrites old grade for Ivana
			Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			examMarks.remove(pair.getKey());
			assertThrows(ConcurrentModificationException.class, ()-> iter.next());
			

	}
}

