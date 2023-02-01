package hr.fer.oprpp1.custom.collections;
/**
 * Interface for static class  ElementsGetter for going through array
 * @author Jura
 *
 */
public interface ElementGetter<T> {
	/**
	 * 
	 * @return Returns true if collection contains no objects and false otherwise.
	 */
	public boolean hasNextElement();
	/**
	 * 
	 * @return next element in an array, if there's no next element throws NoSuchElementException
	 */
	public T getNextElement();
	/**
	 * Calls processor  p for every remaining element
	 * @param p
	 */
	 public default  void processRemaining(Processor<T> p) {
		while(hasNextElement()) {
				p.process(getNextElement());
	}
	}


}

