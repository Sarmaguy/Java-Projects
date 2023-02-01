package hr.fer.oprpp1.hw04.db;
/**
 * Implementation of operators
 * @author Jura
 *
 */
public class ComparisonOperators {
	/**
	 * Operator less
	 */
	public static final IComparisonOperator LESS = (x,y) -> x.compareTo(y) < 0;
	/**
	 * Operator less or equals
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (x,y) -> x.compareTo(y) <= 0;
	/**
	 * Operator greater
	 */
	public static final IComparisonOperator GREATER = (x,y) -> x.compareTo(y) > 0;
	/**
	 * Operator greater or equals
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (x,y) -> x.compareTo(y) >= 0;
	/**
	 * Operator equals
	 */
	public static final IComparisonOperator EQUALS = (x,y) -> x.compareTo(y) == 0;
	/**
	 * Operator not equals
	 */
	public static final IComparisonOperator NOT_EQUALS = (x,y) -> x.compareTo(y) != 0;
	/**
	 * Operator Like
	 */
	public static final IComparisonOperator LIKE = (x,y) -> {
		int index = y.indexOf("*");
		
		if (y.lastIndexOf("*")!=index) throw new IllegalArgumentException();
		
		if (index==-1) return x.compareTo(y)==0;
		
		if (index==0) return x.endsWith(y.substring(1));
		
		if (index==y.length()-1) return x.startsWith(y.substring(0,index));
		
		return x.startsWith(y.substring(0,index)) && x.substring(index).endsWith(y.substring(index+1));
		
		};
}
