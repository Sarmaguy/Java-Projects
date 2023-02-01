package hr.fer.zemris.java.hw06.shell.commands;

import java.awt.image.RescaleOp;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class Copy implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> l = Helper.parse(arguments);
			
			if (l.size()!=2) throw new IllegalArgumentException();
			
			Path original = Paths.get(l.get(0));
			Path destination = Paths.get(l.get(1));
			
			if (Files.isDirectory(destination)) destination = destination.resolve(original.getFileName());
			
			if(Files.exists(destination)) {
				env.writeln("File " + destination + " already exists. Do you want to overwrite it? (y/n)");
				env.write(env.getPromptSymbol() + " ");
				
				if(env.readLine().equals("n")) {
					env.writeln("Command terminated.");
					return ShellStatus.CONTINUE;
				}
			}
			
			try (InputStream input = Files.newInputStream(original);
					OutputStream output = Files.newOutputStream(destination)) {
				byte[] block = new byte[4096];
				
				while(true) {
					int n = input.read(block);
					
					if(n < 1)
						break;

					output.write(block, 0, n);
				}
			}
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		
		
		return ShellStatus.CONTINUE;
	}
	
	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("The copy command expects two arguments: source file name and destination file name");
		l.add("If destination file exists, you asks user is it allowed to overwrite it");
		l.add("works only with files");
		l.add("If the second argument is directory copies the original file into that directory using the original file name.");
		return l;
	}

}
