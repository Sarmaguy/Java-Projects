package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class Mkdir implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> l = Helper.parse(arguments);
			
			if (l.size()!=1) throw new IllegalArgumentException();
			
			Path path = Paths.get(l.get(0));
			
			Files.createDirectory(path);
			
			env.writeln("Directory created");
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add(" takes a single argument: directory name");
		l.add("creates the appropriate directory structure");
		return l;
	}

}
