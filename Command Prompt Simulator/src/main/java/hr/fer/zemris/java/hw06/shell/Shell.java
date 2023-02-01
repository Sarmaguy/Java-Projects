package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.Cat;
import hr.fer.zemris.java.hw06.shell.commands.Charsets;
import hr.fer.zemris.java.hw06.shell.commands.Copy;
import hr.fer.zemris.java.hw06.shell.commands.Exit;
import hr.fer.zemris.java.hw06.shell.commands.Help;
import hr.fer.zemris.java.hw06.shell.commands.Hexdump;
import hr.fer.zemris.java.hw06.shell.commands.Ls;
import hr.fer.zemris.java.hw06.shell.commands.Mkdir;
import hr.fer.zemris.java.hw06.shell.commands.Symbol;
import hr.fer.zemris.java.hw06.shell.commands.Tree;
/**
 * Implementation of shell
 * @author Jura
 *
 */
public class Shell {

	public static void main(String[] args) {
		SortedMap<String, ShellCommand> commandsMap = new TreeMap<>();
		commandsMap.put("cat", new Cat());
		commandsMap.put("charsets", new Charsets());
		commandsMap.put("copy", new Copy());
		commandsMap.put("exit", new Exit());
		commandsMap.put("help", new Help());
		commandsMap.put("hexdump", new Hexdump());
		commandsMap.put("ls", new Ls());
		commandsMap.put("mkdir", new Mkdir());
		commandsMap.put("symbol", new Symbol());
		commandsMap.put("tree", new Tree());
		
		Scanner s = new Scanner(System.in);
		
		Environment env = new Environment() {
			

			private char promptSymbol = '>';

			private char multiLinesSymbol = '|';

			private char moreLinesSymbol = '\\';

			private SortedMap<String, ShellCommand> commands = Collections.unmodifiableSortedMap(commandsMap);
			
			@Override
			public String readLine() throws ShellIOException {
				String nextLine;
				
				try {
					nextLine = s.nextLine();
				} catch (Exception e) {
					throw new ShellIOException(e.getMessage());
				}
				
				return nextLine;
			}
			
			@Override
			public void write(String text) throws ShellIOException {
				try {
					System.out.print(text);
				} catch (Exception e) {
					throw new ShellIOException(e.getMessage());
				}
				
			}
			
			@Override
			public void writeln(String text) throws ShellIOException {
				write(text + "\n");
			}
			
			
			
			@Override
			public SortedMap<String, ShellCommand> commands() {
				return commands;
			}
			
			@Override
			public void setPromptSymbol(Character symbol) {
				promptSymbol = symbol;
			}
			
			@Override
			public void setMultilineSymbol(Character symbol) {
				multiLinesSymbol = symbol;
			}
			
			@Override
			public void setMorelinesSymbol(Character symbol) {
				moreLinesSymbol = symbol;
			}
			
			@Override
			public Character getPromptSymbol() {
				return promptSymbol;
			}
			
			@Override
			public Character getMultilineSymbol() {
				return multiLinesSymbol;
			}
			
			@Override
			public Character getMorelinesSymbol() {
				return moreLinesSymbol;
			}

		};
		
		env.writeln("Welcome to MyShell v 1.0");
		
		while (true) {
			String string ="", line,arguments = "";
			
			env.write(env.getPromptSymbol() + " ");
			
			while(true) {
				line = env.readLine();
				
				if(!line.endsWith(" " + env.getMorelinesSymbol())) {
					string += line;
					break;
				}
				string += line.substring(0, line.length() - 1);
				env.write(env.getMultilineSymbol() + " ");
			}
			
				string = string.trim();
				int index = string.indexOf(' ');
				
				arguments = string.substring(index+1);
				if (index==-1) arguments="";
				
				String commandName = string;
				
				if (index!=-1) commandName = string.substring(0,index);
				

				
				ShellCommand command = env.commands().get(commandName);
				
				if(command == null) {
					env.writeln("Command not found");
					continue;
				}
				
				if (command.executeCommand(env, arguments)==ShellStatus.TERMINATE) break;
			
			
			
			
			}
		s.close();
		
		
	}
}
