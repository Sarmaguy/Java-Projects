package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class Symbol implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		try {
			List<String> l = Helper.parse(arguments);
			
			if (l.size()!=1 && l.size()!=2) throw new IllegalArgumentException();
			
			char c;
			String symbol = l.get(0);
			
			if (symbol.equals("PROMPT")) {
				if(l.size() == 2) {
					env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() +"' to '" + l.get(1)+"'");
					env.setPromptSymbol(l.get(1).charAt(0));
					
				}
				else {
					env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() +"'");
				}
			}
			
			if (symbol.equals("MORELINES")) {
				if(l.size() == 2) {
					env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() +"' to '" + l.get(1)+"'");
					env.setMorelinesSymbol(l.get(1).charAt(0));
					
				}
				else {
					env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() +"'");
				}
			}
			
			if (symbol.equals("MULTILINE")) {
				if(l.size() == 2) {
					env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() +"' to '" + l.get(1)+"'");
					env.setMultilineSymbol(l.get(1).charAt(0));
					
				}
				else {
					env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() +"'");
				}
			}
			
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("Modifies shell symbols");
		return l;
	}

}
