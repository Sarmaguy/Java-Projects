package hr.fer.oprpp1.hw04.db;
/**
 * Interface for filters
 * @author Jura
 *
 */
public interface IFilter {
	 public boolean accepts(StudentRecord record);
	}