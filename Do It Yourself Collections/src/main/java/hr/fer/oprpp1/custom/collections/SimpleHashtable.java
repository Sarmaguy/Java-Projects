package hr.fer.oprpp1.custom.collections;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Handwritten hashlist
 * @author Jura
 *
 * @param <K>
 * @param <V>
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{
	/**
	 * Iterator for this SimpleHastable class
	 * @author Jura
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		private int index=0;
		private TableEntry<K, V> current=null;
		private TableEntry<K, V> last=null;
		private int counter;
		/**
		 * Constructor
		 */
		public IteratorImpl() {
			counter=modificationCount;
			while (current==null && index<table.length) current=table[index++];
		}
		/**
		 * Method tells us if there is next element after current one
		 * @return true if there is next element, false otherwise
		 */
		public boolean hasNext() {
			if (counter!=modificationCount) throw new ConcurrentModificationException();
			if (current==null) return false;
			return true;
		}
		/**
		 * @return next element
		 */
		public SimpleHashtable.TableEntry next() {
			last=current;
			if (current==null) throw new NoSuchElementException();
			if (counter!=modificationCount) throw new ConcurrentModificationException();
			
			current=current.next;
			if (current==null) while (current==null && index<table.length) current=table[index++];
			
			return last;
		}
		/**
		 * Removes last element returned by method "next()"
		 */
		public void remove() {
			if (last==null) throw new IllegalStateException();
			if (counter!=modificationCount) throw new ConcurrentModificationException();
			counter++;
			SimpleHashtable.this.remove(last.key);
			last=null;
		}
		}

	/**
	 * This class represents one entry in our hash table
	 * @author Jura
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static class  TableEntry<K,V>{
		private K key;
		private V value;
		TableEntry<K, V> next;
		/**
		 * Constructor
		 * @param key
		 * @param value
		 * @param next
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			super();
			this.key = key;
			this.value = value;
			this.next=next;
		}
		/**
		 * 
		 * @return value
		 */
		public V getValue() {
			return value;
		}
		/**
		 * Sets value
		 * @param value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		/**
		 * 
		 * @return key
		 */
		public K getKey() {
			return key;
		}
		/**
		 * returns data in "key=value," format
		 */
		@Override
		public String toString() {
			return key + "=" + value + ", ";
		}
		
		
	}

	private TableEntry<K,V>[] table;
	private int size;
	private int modificationCount=0;
	
	/**
	 * Constructor
	 */
	public SimpleHashtable() {
		this(16);
	}
	/**
	 * Constructor
	 * Sets the capacity to the nearest number that is power of 2 from given capacity
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity<1) throw new IllegalArgumentException();
		int veci=2;
		int manji=1;
		while (capacity>veci) {
			veci*=2;
			manji*=2;
		}
		if (veci-capacity<capacity-manji) capacity=veci;
		else capacity=manji;
		
		table= (TableEntry<K, V>[]) Array.newInstance(TableEntry.class,capacity);
		size=0;
	}
	/**
	 * If the key is already in the list it overwrites the value, otherwise adds key and value in the table
	 * @param key
	 * @param value
	 * @return null if there is no key in table already, if there is returns overwritten value
	 */
	public V put(K key, V value) {
		if (key==null) throw new NullPointerException();
		
		int index = key.hashCode()%table.length;
		index=Math.abs(index);
		
		
		if (containsKey(key)) {
			TableEntry<K, V> current= table[index];
			if (current.key.equals(key)) {
				V temp=current.value;
				current.value=value;
				return temp;
			}
			while (current.next!=null) {
				current=current.next;
				if (current.key.equals(key)) {
					V temp=current.value;
					current.value=value;
					return temp;
				}
			}
		}
		size++;
		modificationCount++;
		if (Double.valueOf(size)/table.length>=0.75d) {
			doTheThing(table.length);
			return put(key,value);
		}
		index = key.hashCode()%table.length;
		index=Math.abs(index);
		if (table[index]==null) {
			table[index]= new TableEntry<K, V>(key, value, null);
			return null;
		}
		TableEntry<K, V> current= table[index];
		while (current.next!=null) current=current.next;
		current.next=new TableEntry<K, V>(key, value, null);
		return null;
	}
	
	/**
	 * 
	 * @param key
	 * @return value assigned to the given key
	 */
	public V get(Object key) {
		int index = key.hashCode()%table.length;
		index=Math.abs(index);
		if (table[index]==null) return null;
		if (table[index].key.equals(key)) return table[index].value;
		TableEntry<K, V> current = table[index];
		while (current.next!=null) {
			current=current.next;
			if (current.key.equals(key)) return current.value;
		}
		return null;
	}
	/**
	 * 
	 * @return number of elements inside
	 */
	public int size() {
		return size;
	}
	/**
	 * 
	 * @param key
	 * @return true if key is inside, false otherwise
	 */
	public boolean containsKey(Object key) {
		int index = key.hashCode()%table.length;
		index=Math.abs(index);
		if (table[index]==null) return false;
		if (table[index].key.equals(key)) return true;
		TableEntry<K, V> current = table[index];
		while (current.next!=null) {
			current=current.next;
			if (current.key.equals(key)) return true;
		}
		return false;
	}
	/**
	 * 
	 * @param value
	 * @return true if contains value, false otherwise
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			if (table[i]==null) continue;
			if (table[i].value.equals(value)) return true;
			TableEntry<K, V> current = table[i];
			while (current.next!=null) {
				current=current.next;
				if (current.value.equals(value)) return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param key
	 * @return given key, if there's no such key inside returns null
	 */
	public V remove(Object key) {
		if (key==null) return null;
		
		int index = key.hashCode()%table.length;
		index=Math.abs(index);
		if (table[index]==null) return null;
		if (table[index].key.equals(key)) {
			V temp= table[index].value;
			table[index]=table[index].next;
			size--;
			modificationCount++;
			return temp;
		}
		TableEntry<K, V> current = table[index];
		TableEntry<K, V> prev=null;
		while (current.next!=null) {
			prev=current;
			current=current.next;
			if (current.key.equals(key)) {
				V temp= current.value;
				prev.next=current.next;
				size--;
				modificationCount++;
				return temp;
			}
		}
		return null;
		
	}
	/**
	 * 
	 * @return true if list is empty, otherwise true
	 */
	public boolean isEmpty() {
		return size==0;
	}
	/**
	 * @return object turned to string
	 */
	public String toString() {
		String s="[";
		for (int i = 0; i < table.length; i++) {
			if (table[i]==null) continue;
			s+=table[i].toString();
 			TableEntry<K, V> current = table[i];
			while (current.next!=null) {
				current=current.next;
				s+=current.toString();
			}
		}
		if (s.length()==1) return "[]";	
		s=s.substring(0, s.length()-2);
		s+="]";
		return s;
	}
	/**
	 * 
	 * @return all elements in array
	 */
	public TableEntry<K,V>[] toArray(){
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] array= (TableEntry<K, V>[]) Array.newInstance(TableEntry.class,size);
		int index=0;
		for (int i = 0; i < table.length; i++) {
			if (table[i]==null) continue;
			array[index++]=table[i];
			TableEntry<K, V> current = table[i];
			while (current.next!=null) {
				current=current.next;
				array[index++]=current;
			}
		}
		return array;
	}
	/**
	 * Doubles the sizes of table
	 * @param capacity
	 */
	private void doTheThing(int capacity) {
		TableEntry<K, V>[] array=toArray();
		table= (TableEntry<K, V>[]) Array.newInstance(TableEntry.class,capacity*2);
		size=0;
		for (TableEntry<K, V> tableEntry : array) {
			if(tableEntry==null) continue;
			put(tableEntry.key, tableEntry.value);
		}
	}
	/**
	 * Removes all elements from the list
	 */
	public void clear() {
		modificationCount++;
		size=0;
		for (int i = 0; i < table.length; i++) table[i]=null;
	}
	/**
	 * @return element from 
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
}
