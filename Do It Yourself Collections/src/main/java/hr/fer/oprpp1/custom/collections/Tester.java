package hr.fer.oprpp1.custom.collections;
/**
 * Interface tests if object is acceptable
 * @author Jura
 *
 */
public interface Tester<T>{
	/**
	 * 
	 * @param object
	 * @return true if object is acceptable, false otherwise
	 */
	boolean test(T object);


}
