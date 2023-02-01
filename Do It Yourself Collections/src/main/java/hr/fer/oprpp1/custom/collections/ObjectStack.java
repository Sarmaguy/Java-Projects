package hr.fer.oprpp1.custom.collections;

import java.util.EmptyStackException;
/**
 * Stack implementation
 * @author Jura
 *
 */
public class ObjectStack<T> {
	ArrayIndexedCollection<T> list;
	/**
	 * Creates new stack
	 */
	public ObjectStack() {
		list=new ArrayIndexedCollection<T>();
	}
	/**
	 * 
	 * @return Returns true if stack contains no objects and false otherwise.
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	/**
	 * 
	 * @return Returns the number of currently stored objects on this stack.
	 */
	public int size() {
		return list.size();
	}
	/**
	 * Pushes given value on the stack. null value must not be allowed to be
placed on stack
	 * @param value
	 */
	public void push(T value) {
		list.insert(value, 0);
	}
	/**
	 * Removes last value pushed on stack from stack and returns it
	 * @return
	 */
	public T pop() {
		if (list.isEmpty()) throw new EmptyStackException();
		
		T x=list.get(0);
		list.remove(0);
		return x;
	}
	/**
	 *  Returns last element placed on stack but does not delete it from stack.
	 * @return
	 */
	public T peek() {
		if (list.isEmpty()) throw new EmptyStackException();
		
		return list.get(0);
	}
	/**
	 *  Removes all elements from stack
	 */
	void clear() {
		list.clear();
	}

}
