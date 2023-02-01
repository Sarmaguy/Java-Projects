package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Tester {

	@Test
	public void StudentDataBase() {
		List<String> records = new ArrayList<>();
		records.add("0000000001	Akšamović	Marin	2");
		records.add("0000000001	Akšamović	Marin	2");
		assertThrows(IllegalArgumentException.class, () -> new StudentDatabase(records));
	
}
	@Test
	public void forJMBAG() {
		List<String> records = new ArrayList<>();
		records.add("0000000001	Akšamović	Marin	2");
		StudentDatabase b = new  StudentDatabase(records);
		assertEquals(b.forJMBAG("0000000001"), new StudentRecord("0000000001", "Akšamović", "Marin", "2"));
	
}
	@Test
	public void forJMBAGnull() {
		List<String> records = new ArrayList<>();
		records.add("0000000001	Akšamović	Marin	2");
		StudentDatabase b = new  StudentDatabase(records);
		assertEquals(b.forJMBAG("0000000002"), null);
	
}
	@Test
	public void allTrue() {
		List<String> records = new ArrayList<>();
		records.add("0000000001	Akšamović	Marin	2");
		records.add("0000000002	Akšamović	Marin	2");
		StudentDatabase b = new  StudentDatabase(records);
		List<StudentRecord> result = b.filter(r -> true);
		assertEquals(result.size(), 2);
	
}
	@Test
	public void allFalse() {
		List<String> records = new ArrayList<>();
		records.add("0000000001	Akšamović	Marin	2");
		records.add("0000000002	Akšamović	Marin	2");
		StudentDatabase b = new  StudentDatabase(records);
		List<StudentRecord> result = b.filter(r -> false);
		assertEquals(result.size(), 0);
	
}
	@Test
	public void compareLESS() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertEquals(oper.satisfied("Ana", "Jasna"),true); 
	}
	
	@Test
	public void compareLESSOREQ() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(oper.satisfied("Ana", "Ana"),true); 
	}
	
	@Test
	public void compareGreat() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertEquals(oper.satisfied("Jana", "Ana"),true); 
	}
	
	@Test
	public void compareGreatOrEq() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(oper.satisfied("Ana", "Ana"),true); 
	}
	@Test
	public void compareEq() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertEquals(oper.satisfied("Ana", "Ana"),true); 
	}
	@Test
	public void compareNotEq() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertEquals(oper.satisfied("Ana", "Ana"),false); 
	}
	
	@Test
	public void compareLike1() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(oper.satisfied("Zagreb", "Aba*"),false); 
	}
	
	@Test
	public void compareLike2() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(oper.satisfied("AAA", "AA*AA"),false); 
	}
	@Test
	public void compareLike3() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(oper.satisfied("AAAA", "AA*AA"),true); 
	}
	@Test
	public void JMBAG(){
		StudentRecord r = new StudentRecord("1", "Peric", "Pero", "2");
		assertEquals(FieldValueGetters.JMBAG.get(r), "1");
	}
	@Test
	public void nameLast(){
		StudentRecord r = new StudentRecord("1", "Peric", "Pero", "2");
		assertEquals(FieldValueGetters.LAST_NAME.get(r), "Peric");
	}
	@Test
	public void name(){
		StudentRecord r = new StudentRecord("1", "Peric", "Pero", "2");
		assertEquals(FieldValueGetters.FIRST_NAME.get(r), "Pero");
	}
	
}
