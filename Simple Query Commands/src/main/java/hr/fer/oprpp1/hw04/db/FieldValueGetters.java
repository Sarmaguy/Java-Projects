package hr.fer.oprpp1.hw04.db;
/**
 * Class that contains what value  for comparing
 * @author Jura
 *
 */
public class FieldValueGetters {
	/**
	 * Returns JMBAG
	 */
	public static final IFieldValueGetter JMBAG = record -> record.getJmbag();
	/**
	 * Returns First Name
	 */
	public static final IFieldValueGetter FIRST_NAME = record -> record.getFirstName();
	/**
	 * Returns Last Name
	 */
	public static final IFieldValueGetter LAST_NAME = record -> record.getLastName();
}
