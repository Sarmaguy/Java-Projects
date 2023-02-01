/**
 * 
 */
package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * @author Jura
 *
 */
public class Charsets implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			
			List<String> l = Helper.parse(arguments);
			
			if (l.size()!=0) throw new IllegalAccessException();
		
			for (String s : Charset.availableCharsets().keySet()) env.writeln(s);
		
		}catch(Exception e) { env.writeln(e.getMessage());}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charset";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("lists names of supported charsets for your Java platform");
		return l;
	}
}
