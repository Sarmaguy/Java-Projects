package hr.fer.oprpp1.custom.collections;
/**
 * Processor interface
 * @author Jura
 *
 */
public interface Processor<T> {
	/**
	 * The Processor is a
	   model of an object capable of 
	   performing some operation on the passed object.
	 * @param value
	 */
	public abstract void process(T value);
}
