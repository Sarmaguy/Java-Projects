package hr.fer.oprpp1.hw04.db;

import java.util.List;
/**
 * Class QueryFilter
 * @author Jura
 *
 */
public class QueryFilter implements IFilter {
	private List<ConditionalExpression> expressions;
	
	
	/**
	 * Constructor
	 * @param expressions
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		super();
		this.expressions = expressions;
	}


	/**
	 * If record satisfys filter returns true otherwise false
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression conditionalExpression : expressions) {
			String x = conditionalExpression.getFieldValueGetter().get(record);
			String y = conditionalExpression.getString();
			
			if (!conditionalExpression.getComparisonOperator().satisfied(x, y))
				return false;
		}
		return true;
	}

}
