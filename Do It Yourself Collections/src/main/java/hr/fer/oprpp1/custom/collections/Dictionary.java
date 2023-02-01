package hr.fer.oprpp1.custom.collections;
/**
 * Implementation of dictionary
 * @author Jura
 *
 * @param <K> Type for key
 * @param <V> Type for value
 */
public class Dictionary<K,V>{
	/**
	 * Helper class for throwing elements in list
	 * @author Jura
	 *
	 * @param <K> Type for key
     * @param <V> Type for value
	 */
	private class Entry<K,V>{
		private K key;
		private V value;
		public Entry(K key, V value) {
			if (key==null) throw new NullPointerException();
			this.key = key;
			this.value = value;
		}
		public K getKey() {
			return key;
		}
		public void setKey(K key) {
			this.key = key;
		}
		public V getValue() {
			return value;
		}
		public void setValue(V value) {
			this.value = value;
		}
		
		
		
	}
	
	
	private ArrayIndexedCollection<Entry<K,V>> list;
	/**
	 * Basic constructor
	 */
	public Dictionary(){
		list=new ArrayIndexedCollection<>();
	}
	/**
	 * 
	 * @return true if dictionary is empty, false otherwise
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	/**
	 * 
	 * @return number of elements in dictionary
	 */
	public int size() {
		return list.size();
	}
	/**
	 * Removes all elements from dictionary
	 */
	public void clear() {
		list.clear();
	}
	/**
	 * Puts value to its key in the list and returns previous value of that key.
	 * If the key is not in the dictionary puts it in and returns null.
	 * @param key
	 * @param value
	 * @return value if key's in dictionary, otherwise null
	 */
	public V put(K key, V value) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey().equals(key)) {
				V temp = list.get(i).getValue();
				list.get(i).setValue(value);
				return temp;
			}
		}
		list.add(new Entry<K,V>(key,value));
		return null;// "gazi" eventualni postojeći zapis
	}
	/**
	 * 
	 * @param key
	 * @return value for the given key, if the key doesnt exist returns null
	 */
	V get(Object key) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey().equals(key)) return list.get(i).getValue();
		}// ako ne postoji pripadni value, vraća null
		return null;
	}
	/**
	 * Removes element which has given key
	 * @param key
	 * @return value of removed element, if nothing was removed returns null
	 */
	V remove(K key) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey().equals(key)) {
				V temp = list.get(i).getValue();
				list.remove(i);
				return temp;
			}
		}
		return null;
	}

}
