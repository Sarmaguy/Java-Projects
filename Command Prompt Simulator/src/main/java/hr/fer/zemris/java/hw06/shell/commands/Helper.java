package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

public class Helper {

	public static List<String> parse(String arguments) {
		List<String> l = new ArrayList<>();
		char[] arr = arguments.toCharArray();
		int index = 0;
		
		while(true) {
			
			while(arr.length != index) { 
				
				if(arr[index] == '\t' || arr[index] == ' ') {
					index++;
					continue;
				}
				break;
			}
			
			if(index == arr.length)
				break;
			
			if(arr[index]=='"') {
				boolean flag = true;
				index++;
				String s="";
				
				while (index != arr.length) {
					char c = arr[index];
					
					if(c=='"') {
						
						if (index+1 != arr.length && arr[index+1]!=' ') throw new IllegalArgumentException();
						
						index++;
						flag=false;
						break;
					}
					
					if (c=='\\' && (arr[index+1]=='"') || arr[index+1]=='\\') c = arr[++index];
				
					s+=c;
					index++;
				}
				if (flag) throw new IllegalArgumentException("Missing \"");
				
				l.add(s);
				
			}
			
			else{
				String s="";
				
				while(arr.length != index) { 
					
					if(arr[index] != ' ') {
						
						s+=arr[index];
						index++;
						continue;
					}
					break;
				}
				l.add(s);
			}
		}
		
		
		return l;
	}
	
	public static void main(String[] args) {
		List<String> l = Helper.parse("\\naredba1 naredba2 ");
		l.forEach(System.out::println);
	}
	


}
