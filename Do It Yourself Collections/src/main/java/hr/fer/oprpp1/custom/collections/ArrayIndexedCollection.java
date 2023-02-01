package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Handwritten Indexed array
 * @author Jura
 *
 */
public class ArrayIndexedCollection<T> implements List<T>{
	/**
	 * Static class ElementsGetter for going through array
	 * @author Jura
	 *
	 */
	private static class ElementsGetter<T> implements ElementGetter<T>{
		private  int index=0;
		ArrayIndexedCollection<T> a;
		private long savedModificationCount;
		/**
		 * Retruns new instance of class ElementsGetter
		 * @param a reference to the array
		 */
		public ElementsGetter(ArrayIndexedCollection<T> a, long savedModificationCount) {
			this.savedModificationCount = savedModificationCount;
			this.a=a;
		}
		/**
		 * 
		 * @return true if array has next element, false if not
		 */
		public boolean hasNextElement() {
			if(savedModificationCount!=a.modificationCount) throw new ConcurrentModificationException();
			return index<=a.size-1;
		}
		/**
		 * 
		 * @return next element in an array, if there's no next element throws NoSuchElementException
		 */
		public T getNextElement() {
			if(savedModificationCount!=a.modificationCount) throw new ConcurrentModificationException();
			if(!hasNextElement()) throw new NoSuchElementException();
			
			return a.get(index++);
		}
	}
	
	private int size=0;
	private T[] elements;
	private long modificationCount=0;
	
	
	/**
	 * Constructor
	 * @param collection a non-null reference to some other Collection which elements are copied into this newly
constructed collection
	 * @param initialCapacity capacity is being set to that value
	 */
	public ArrayIndexedCollection(Collection<T> collection, int initialCapacity) {
		super();
		size=0;
		if (collection==null) throw new NullPointerException();
		if (initialCapacity<1) throw new IllegalArgumentException();
		
		if (collection.size() > initialCapacity) elements = (T[]) new Object[collection.size()];
		else elements = (T[]) new Object[initialCapacity];
		
		
		this.addAll(collection);
		for (T element : elements) {
			if (element==null) break;
			size++;
		}
	
	}
	
	/**
	 * Creates an instance with capacity set to 16
	 * @param collection a non-null reference to some other Collection which elements are copied into this newly
constructed collection
	 */
	public ArrayIndexedCollection(Collection<T> collection) {
		this(collection,16);	
	}
	/**
	 * Constructor
	 * @param initialCapacity capacity is being set to that value
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		super();
		size=0;
		if (initialCapacity<1) throw new IllegalArgumentException();
		
		elements = (T[]) new Object[initialCapacity];
		
	
	}
	/**
	 * Creates an instance with capacity set to 16
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	/**
	 * 
	 * @return Returns the number of currently stored objects in this collections.
	 */
	@Override
	public int size(){
		return size;
	}

	/**
	 * Adds the given object into this collection.
	 * @param value
	 */
	@Override
	public void add(T value) {   	//O(n/2)->O(n)
		
		if (value==null) throw new NullPointerException();
		modificationCount++;
		
		size++;
		for (int i = 0; i < elements.length+1; i++) {
			  if (i==elements.length) {
				  Object[] temp = new Object[elements.length*2];
				  for (int j = 0; j < elements.length; j++) {
					temp[j]=elements[j];
				}
				  temp[elements.length]=value;
				  size=elements.length+1;
				  elements=(T[]) temp;
				  break;
			  }
			
			  if (elements[i]==null) {
				  elements[i]=value;
				  break;
			  }
			}
		
	}
	/**
	 * 
	 * @param value
	 * @return Returns true only if the collection contains given value, 
	 * as determined by equals method.
	 */
	@Override
	public boolean contains(T value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) return true;
		}
		return false;
	}
	/**
	 * 
	 * @param index
	 * @return Returns the object that is stored in backing array at position index.
	 */
	public T get(int index) {    //O(1)
		if (index<0 || index>size-1) throw new IndexOutOfBoundsException();
		return elements[index];
	}

	/**
	 * Removes all elements from this collection.
	 */
	@Override
	public void clear() {
		modificationCount++;
		for (int i = 0; i < size; i++) {
			elements[i]=null;
		}
		size=0;
	}
/**
 * Inserts the given value at the given position in array.
 * @param value
 * @param position
 */
	public void insert(T value, int position) { //O(n)
		if (position<0 || position>size) throw new IndexOutOfBoundsException();
		modificationCount++;
		Object temp=elements[position];
		elements[position]=value;
		for (int i = position+1; i < size; i++) {
			Object temp2=elements[i];
			elements[i]=(T) temp;
			temp=temp2;
		}
		size++;
		if (temp!=null) this.add((T) temp); //impresioniran sam samim sobom
	}
	/**
	 * 
	 * @param value
	 * @return Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is
not found
	 */
	public  int indexOf(T value) { //O(n/2)->O(n)
		if (value==null) return -1;
		
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) return i;
		}
		return -1;
	}
	/**
	 * Removes element at specified index from collection
	 * @param index
	 */
	public void remove(int index) {
		if (index<0 || index>size-1) throw new IndexOutOfBoundsException();
		modificationCount++;
		
		for (int i = index+1; i < size; i++) elements[i-1]=elements[i]; //prepisi gornji for ovako ako ti se bude dalo
		elements[--size]=null;
		
	}
	/**
	 * Method calls processor.process(.) for each element of this collection. The order in which elements
will be sent is undefined in this class.
	 * @param processor
	 */
	@Override
	public void forEach(Processor<? super T> processor) {
		for (T element : elements) {
			if (element==null) break;
			processor.process(element);
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return Returns true only if the collection contains 
	 * given value as determined by equals method and removes
		one occurrence of it
	 */
	@Override
	public boolean remove(T value) {
		modificationCount++;
		int x = indexOf(value);
		if (x==-1) return false;
		remove(x);
		return true;
	}
	/**
	 * 
	 * @return Allocates new array with size equals to the size of this collections, fills it with collection content and
returns the array. This method never returns null
	 */
	@Override
	public T[] toArray() {
		T[] o = (T[]) new Object[size];
		for (int i = 0; i < size; i++) o[i]=elements[i];
		return o;
	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ElementsGetter<T>(this,modificationCount);
	}


	
	
//	
	
	
	
	

}
