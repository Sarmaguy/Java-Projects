package hr.fer.oprpp1.hw04.db;

import java.util.Objects;
/**
 * Class that models one student record
 * @author Jura
 *
 */
public class StudentRecord {
	private String jmbag;
	private String lastName;
	private String firstName;
	private String finalGrade;
	/**
	 * Constructor
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param finalGrade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	@Override
	public int hashCode() {
		return Objects.hash(finalGrade, firstName, jmbag, lastName);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}
	/**
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}
	/**
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * 
	 * @return finalGrade
	 */
	public String getFinalGrade() {
		return finalGrade;
	}
	

	
	
}
