package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class Help implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> l = Helper.parse(arguments);
			
			if(l.size()!=0 && l.size()!=1) throw new IllegalArgumentException();
			
			if (l.size()==0)
				
				for (ShellCommand command : env.commands().values()) env.writeln(command.getCommandName());
			else {
				ShellCommand command = env.commands().get(l.get(0));
				if(command == null) throw new IllegalArgumentException("There's no such command");
				
				env.writeln(command.getCommandName());
				for (String string : command.getCommandDescription()) {
					env.writeln(string);
				}
			}
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("If started with no arguments, it lists names of all supported commands");
		l.add("If started with single argument, it  prints name and the description of selected command");
		return l;
	}

}
