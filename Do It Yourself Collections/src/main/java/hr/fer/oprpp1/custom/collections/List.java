package hr.fer.oprpp1.custom.collections;
/**
 * Interface for lists
 * @author Jura
 *
 */
public interface List<T> extends Collection<T>{
	
	T get(int index);
	void insert(T value, int position);
	int indexOf(T value);
	void remove(int index);


}
