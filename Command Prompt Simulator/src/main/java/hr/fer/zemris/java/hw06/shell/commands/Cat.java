package hr.fer.zemris.java.hw06.shell.commands;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * Command cat
 * @author Jura
 *
 */
public class Cat implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> l = Helper.parse(arguments);
		
		if (l.size()!=1 && l.size()!=2) throw new IllegalArgumentException();
		
		Path path = Paths.get(l.get(0));
		
		Charset charset = null;
		if (l.size()==1) charset = Charset.defaultCharset();
		else Charset.forName(l.get(1));
		
		try (InputStream stream = Files.newInputStream(path)){
			byte[] block = new byte[1024*4];
			
			while (true) {
				int n = stream.read(block);
				
				if (n<1) break;
				
				byte[] arr = new byte[n];
				for (int i = 0; i < n; i++) arr[i] += block[i];
				
				env.write(new String(arr, charset));
			}
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		env.writeln("");
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("command opens given file and writes its content to console");
		l.add("takes one or two arguments");
		l.add("The first argument is path to some file and is mandatory. The second argument is charset name that should be used to interpret chars from bytes.");
		l.add("If not provided, a default platform charset will be used");
		
		return l;
	}

}
