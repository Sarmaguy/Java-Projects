package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Class that represents database
 * @author Jura
 *
 */
public class StudentDatabase {
	private List<StudentRecord> records = new ArrayList<>();
	private HashMap<String, StudentRecord> hashMap = new HashMap<>();
	
	/**
	 * Constructor
	 * @param data
	 */
	public StudentDatabase(List<String> data) {
		for (String string : data) {
			String[] l = string.split("\t");
			
			if (hashMap.get(l[0])!=null) throw new IllegalArgumentException("JMBAG vec postoji u bazi!");
			if(Integer.valueOf(l[3])>5 || Integer.valueOf(l[3])<1 ) throw new IllegalArgumentException("Neispravna ocjena");
			
			hashMap.put(l[0], new StudentRecord(l[0], l[1], l[2], l[3]));
			records.add(hashMap.get(l[0]));
		}
		
	}
	/**
	 * 
	 * @param jmbag
	 * @return JMBAG
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return hashMap.get(jmbag);
	}
	/**
	 * 
	 * @param filter
	 * @return list of filters that satisfy filter
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> list = new ArrayList<>();
		
		for (StudentRecord studentRecord : records) 
			if (filter.accepts(studentRecord)==true) list.add(studentRecord);
		
		return list;
	}

}
