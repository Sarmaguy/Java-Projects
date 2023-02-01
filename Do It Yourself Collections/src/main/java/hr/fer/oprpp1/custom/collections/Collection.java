package hr.fer.oprpp1.custom.collections;

/**
 * Collection interface
 * @author Jura
 *
 */

public interface Collection<T> {


	/**
	 * 
	 * @return Returns the number of currently stored objects in this collections.
	 */
	public abstract int size();
	/**
	 * 
	 * @return Returns true if collection contains no objects and false otherwise.
	 */
	public default boolean isEmpty() {
		return !(this.size()>0);
	}
	/**
	 * Adds the given object into this collection.
	 * @param value
	 */
	public abstract void add(T value);
	/**
	 * 
	 * @param value
	 * @return Returns true only if the collection contains given value, 
	 * as determined by equals method.
	 */
	public abstract boolean contains(T value);
	/**
	 * 
	 * @param value
	 * @return Returns true only if the collection contains 
	 * given value as determined by equals method and removes
		one occurrence of it
	 */
	public abstract boolean remove(T value);
	/**
	 * 
	 * @return Allocates new array with size equals to the size of this collections, fills it with collection content and
returns the array. This method never returns null
	 */
	public abstract T[] toArray();
	/**
	 * Method calls processor.process(.) for each element of this collection. The order in which elements
will be sent is undefined in this class.
	 * @param processor
	 */
	default void forEach(Processor<? super T> processor) {
		
		ElementGetter<T> getter = this.createElementsGetter();
		
		while(getter.hasNextElement())
			processor.process(getter.getNextElement());
	}

	/**
	 * Method adds into the current collection all elements from the given collection. This other collection
remains unchanged
	 * @param other the given collection
	 */
	default void addAll(Collection<? extends T> other)  {
		
		Processor<T> cp = value -> add(value);
		other.forEach(cp);
	}
	/**
	 * Removes all elements from this collection.
	 */
	public abstract void clear();
	/**
	 * 
	 * @return new ElementsGetter
	 */
	public abstract ElementGetter<T> createElementsGetter();
	/**
	 * Method uses class tester do decide if object is acceptable and if so, it's being added to the existing collection
	 * @param col
	 * @param tester
	 * @return 
	 */
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {

		ElementGetter<? extends T> getter = (ElementGetter<? extends T>) col.createElementsGetter();
		while(getter.hasNextElement()) {
			T value = getter.getNextElement();
			
			if(tester.test(value))
				add(value);
		}
	}

}
