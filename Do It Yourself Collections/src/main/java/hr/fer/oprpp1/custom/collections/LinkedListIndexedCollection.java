package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
/**
 * Homemade linked list
 * @author Jura
 *
 */
public class LinkedListIndexedCollection<T> implements List<T> {
	/**
	 * Static class ElementsGetter for going through array
	 * @author Jura
	 *
	 */
	private static class ElementsGetter<T> implements ElementGetter<T>{
		private  ListNode<T> current;
		private LinkedListIndexedCollection<T> l;
		private long savedModificationCount;
		
		public ElementsGetter(LinkedListIndexedCollection<T> l, long savedModificationCount) {
			this.l=l;
			current=l.first;
			this.savedModificationCount=savedModificationCount;
		}
		
		public boolean hasNextElement() {
			if(savedModificationCount!=l.modificationCount) throw new ConcurrentModificationException();
			return current!=null;
			
		}
		public T getNextElement() {
			if(savedModificationCount!=l.modificationCount) throw new ConcurrentModificationException();
			if(!hasNextElement()) throw new NoSuchElementException();
			T temp = (T) current.getValue();
			current=current.getRight();
			return temp;
		}
	}
	
	private static class ListNode<T>{
		ListNode<T> left;
		ListNode<T> right;
		T value;
		/**
		 * Constructor for local nodes
		 * @param left Pointer to left element
		 * @param right Pointer to right element
		 * @param value 
		 */
		public ListNode(ListNode<T> left, ListNode<T> right, T value) {
			super();
			this.left = left;
			this.right = right;
			this.value = value;
		}
		/**
		 * 
		 * 
		 * @return left node
		 */
		public ListNode<T> getLeft() {
			return left;
		}

		/**
		 * Setter for left node
		 * @param left
		 */
		public void setLeft(ListNode<T> left) {
			this.left = left;
		}

		/**
		 * 
		 * @return right node 
		 */
		public ListNode<T> getRight() {
			return right;
		}

		/**
		 * Setter for right node
		 * @param right
		 */
		public void setRight(ListNode<T> right) {
			this.right = right;
		}

		/**
		 * 
		 * @return value inside of node
		 */
		public T getValue() {
			return value;
		}
		
		
	}
	
	private int size;
	private ListNode<T> first;
	private ListNode<T> last;
	private long modificationCount=0;
	/**
	 * Basic constructor
	 */
	public LinkedListIndexedCollection(){
		super();
		first=last=null;
		size=0;
	}
	/**
	 * Elements of other collection are copied into this newly constructed collection
	 * @param collection
	 */
	LinkedListIndexedCollection(Collection<T> collection){
		super();
		this.addAll(collection);
		
	}
	/**
	 * 
	 * @return Returns the number of currently stored objects in this collections.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object into this collection.
	 * @param value
	 */
	@Override
	public void add(T value) {
		
		if (value==null) throw new NullPointerException();
		size++;
		modificationCount++;
		if (first==null) {
			
			first=last=new ListNode<T>(null, null, value);
			return;
		}
		last.setRight(new ListNode<T>(last, null, value));
		last=last.getRight();
		
		
	}
	/**
	 * 
	 * @param value
	 * @return Returns true only if the collection contains given value, 
	 * as determined by equals method.
	 */
	@Override
	public boolean contains(T value) {
		ListNode<T> current = first;
		if (current.getValue().equals(value)) return true;
		for (int i = 0; i < size-1; i++) {
			current=current.getRight();
			if (current.getValue().equals(value)) return true;
		}
		return false;
	}

	/**
	 * 
	 * @param index
	 * @return Returns the object that is stored in backing array at position index.
	 */
	public T get(int index) {
		if (index<0 || index>size-1) throw new IndexOutOfBoundsException();
		
		if (size/2-1>=index) {
			ListNode<T> current = first;
			for (int i = 0; i < index; i++) {
				current=current.getRight();
			}
			return current.getValue();
		}
		else {
			ListNode<T> current = last;
			for (int i = 0; i < size-1-index; i++) {
				current=current.getLeft();
			}
			return current.getValue();
		}
	}
	/**
	 * Removes all elements from this collection.
	 */
	@Override
	public void clear() {
		modificationCount++;
		last=first=null;
		size=0;
	}
	/**
	 * Inserts the given value at the given position in array.
	 * @param value
	 * @param position
	 */
	public void insert(T value, int position) { //O(n/2)->O(n)
		
		if (position<0 || position>size) throw new IndexOutOfBoundsException();
		
		ListNode<T> current = first;
		if (position==size) {
			add(value);
			return;
		}
		size++;

		modificationCount++;
		for (int i = 0; i < position; i++) {
			current=current.getRight();
		}
		if (current!=null)
		{
			current.setLeft(new ListNode<T>(current.getLeft(), current, value));
			current.getLeft().setRight(current.getLeft());
			if (position==0) first=current.getLeft();
		}
		else first=last= new ListNode<T>(null, null, value);
	
	}
	/**
	 * 
	 * @param value
	 * @return Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is
not found
	 */
	public int indexOf(Object value) {
		if (value==null) return -1;
		
		ListNode<T> current = first;
		for (int i = 0; i < size; i++) {
			if (current.getValue().equals(value)) return i;
			current=current.getRight();
		}
		return -1;
	}
	/**
	 * Removes element at specified index from collection
	 * @param index
	 */
	public void remove(int index) {
		
		if (index<0 || index>size-1) throw new IndexOutOfBoundsException(); //document this!!!
		modificationCount++;
		size--;
		ListNode<T> current = first;
		for (int i = 0; i < size; i++) current=current.getRight();
		if (current.getLeft()!=null) current.getLeft().setRight(current.getRight());	//ak je prvi	
		if (current.getRight()!=null) current.getRight().setLeft(current.getLeft()); //ak je zadnji
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
		ListNode<T> current= first;
		T[] o = (T[]) new Object[size];
		for (int i = 0; i < size; i++) {
			o[i]=current.getValue();
			current=current.getRight();
		}
			return o;
		
	}
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ElementsGetter<T>(this,modificationCount);
	}
}
