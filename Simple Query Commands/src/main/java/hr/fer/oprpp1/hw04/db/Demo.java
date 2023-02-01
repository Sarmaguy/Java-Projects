package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;




public class Demo {
	/**
	 * Class formats data into a table 
	 * @param records
	 * @return list of strings ready to be printed
	 */
	private static List<String> format(List<StudentRecord> records){
		int nameLength=0;
		int surnameLength=0;
		
		for (StudentRecord studentRecord : records) {
			int tmpname=studentRecord.getFirstName().length();
			int tmpsurname=studentRecord.getLastName().length();
			
			if (tmpname>nameLength) nameLength=tmpname;
			
			if (tmpsurname>surnameLength) surnameLength=tmpsurname;
		}
		List<String> list = new ArrayList<>();
		String s = "+============+";
		for (int i = 0; i < surnameLength+2; i++) s+="=";
		s+="+";
		for (int i = 0; i < nameLength+2; i++) s+="=";
		s+="+===+";
		list.add(s);
		
		for (StudentRecord studentRecord : records) {
			int namespace = nameLength - studentRecord.getFirstName().length();
			int surnamespace = surnameLength - studentRecord.getLastName().length();
			String string="";
			string+= "| " + studentRecord.getJmbag() + " | " + studentRecord.getLastName();
			for (int i = 0; i < surnamespace; i++) string+=" ";
			string+= " | " + studentRecord.getFirstName();
			for (int i = 0; i < namespace; i++) string+=" ";
			string+= " | " + studentRecord.getFinalGrade() + " |";
			list.add(string);
		}
		list.add(s);
		return list;
	}
	/**
	 * Main method that runs the app
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		List<String> lines = Files.readAllLines(
				 Paths.get("database.txt"),
				 StandardCharsets.UTF_8);
		
		StudentDatabase database = new StudentDatabase(lines);
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			List<StudentRecord> records = new ArrayList<>();
			System.out.print(">");
			String string = scanner.nextLine().trim();
			
			if(string.equals("exit"))
				break;
			try {
				
				if(!string.startsWith("query"))
					throw new IllegalArgumentException("Input must start with \"query\"");
				
				QueryParser parser = new QueryParser(string.substring(5));
				
				if(parser.isDirectQuery()) {
					StudentRecord record = database.forJMBAG(parser.getQueriedJMBAG());
					System.out.println("Using index for record retrieval.");
					records.add(record);
				}
				else
					records.addAll(database.filter(new QueryFilter(parser.getQuery())));
				
				if(records.size() > 0)
					format(records).forEach(System.out::println);
				System.out.println("Records selected: " + records.size());
				System.out.println();
				
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			
		}
		scanner.close();
		System.out.println("Goodbye!");
	}
}
