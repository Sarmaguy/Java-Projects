package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
/**
 * Query parser
 * @author Jura
 *
 */
public class QueryParser {
	private String data;
	private List<ConditionalExpression> expressions;
	private int currentIndex;
	private char[] array;
	/**
	 * Constructor
	 * @param data
	 */
	public QueryParser(String data) {
		super();
		this.data = data;
		expressions = new ArrayList<>();
		currentIndex = 0;
		parse(data);
	}
	/**
	 * 
	 * @return true if its direct query, false otherwise
	 */
	boolean isDirectQuery() {
		String modified = data.replaceAll("\\s", "");
		
		if (modified.startsWith("jmbag=\"") && modified.endsWith("\"") && modified.length()==18) 
			return true;
		
		return false;
	}
	/**
	 * 
	 * @return queried JMBAG
	 */
	String getQueriedJMBAG() {
		
		if (!isDirectQuery()) throw new IllegalStateException();
		
		String modified = data.replaceAll("\\s", "");
		return modified.substring(7,17);
	}
	/**
	 * 
	 * @return list of conditional expressions
	 */
	List<ConditionalExpression> getQuery(){
		return expressions;
	}
	/**
	 * 
	 * @return true if next word is "and", false otherwise
	 */
	private boolean checkForAnd() {
		if (currentIndex+3<data.length()) {
			String s = data.substring(currentIndex,currentIndex+3);
		
			if (s.toUpperCase().equals("AND")) {
				currentIndex+=3;
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @return parsed string
	 */
	private String getString() {
		String s = data.substring(++currentIndex);
		s=s.substring(0,s.indexOf("\""));
		currentIndex+=s.length();
		
		if (currentIndex+1<data.length()) currentIndex++;
		
		return s;
	}
	
	
	/**
	 * 
	 * @return parsed operator
	 */
	private IComparisonOperator getOperator() {
		String s = data.substring(currentIndex);
		currentIndex++;
		if (s.startsWith("<=")) {
			currentIndex++;
			return ComparisonOperators.LESS_OR_EQUALS;
		}
		
		if (s.startsWith("<")) return ComparisonOperators.LESS;
		
		if (s.startsWith(">=")) {
			currentIndex++;
			return ComparisonOperators.GREATER_OR_EQUALS;
		}
		
		if (s.startsWith(">")) return ComparisonOperators.GREATER;
		
		if (s.startsWith("=")) return ComparisonOperators.EQUALS;
		
		if (s.startsWith("!=")) {
			currentIndex++;
			return ComparisonOperators.NOT_EQUALS;
		}
		
		if (s.startsWith("LIKE")) {
			currentIndex+=3;
			return ComparisonOperators.LIKE;
		}
		
		throw new IllegalArgumentException();
	}
	/**
	 * 
	 * @return parsed value
	 */
	private IFieldValueGetter getFieldValue() {
		String s = data.substring(currentIndex);
		
		if (s.startsWith("jmbag")) {
			currentIndex+=5;
			return FieldValueGetters.JMBAG;
		}
		
		if (s.startsWith("lastName")) {
			currentIndex+=8;
			return FieldValueGetters.LAST_NAME;
		}
		
		if (s.startsWith("firstName")) {
			currentIndex+=9;
			return FieldValueGetters.FIRST_NAME;
		}
		throw new IllegalArgumentException();
		
	}
	/**
	 * skips empty spaces
	 */
	private void preskok() {
		while(data.length() > currentIndex && ( array[currentIndex]==' ' || array[currentIndex]=='\t'))
			currentIndex++;
	}
	/**
	 * Method that does parsing using methods above
	 * @param data
	 */
	private void parse(String data) {
		array = data.toCharArray();
		
		while(currentIndex<data.length()) {
			preskok();
			IFieldValueGetter fieldValue = getFieldValue();
			preskok();
			IComparisonOperator operator = getOperator();
			preskok();
			String string = getString();
			expressions.add(new ConditionalExpression(fieldValue, string, operator));
			preskok();
			if(!checkForAnd()) break;
			
			
		}
		
	}

}
