package hr.fer.oprpp1.hw04.db;
/**
 * Class contains element of query input
 * @author Jura
 *
 */
public class ConditionalExpression {
	private IFieldValueGetter FieldValueGetter;
	private String string;
	private IComparisonOperator ComparisonOperator;
	/**
	 * Constructor
	 * @param fieldValueGetter
	 * @param string
	 * @param comparisonOperator
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String string,
			IComparisonOperator comparisonOperator) {
		super();
		FieldValueGetter = fieldValueGetter;
		this.string = string;
		ComparisonOperator = comparisonOperator;
	}
	/**
	 * 
	 * @return IFieldValueGetter
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return FieldValueGetter;
	}
	/**
	 * 
	 * @return String
	 */
	public String getString() {
		return string;
	}
	/**
	 * 
	 * @return IComparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return ComparisonOperator;
	}
	
	
	
	
	
	
	

}
