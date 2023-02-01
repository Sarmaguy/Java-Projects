package hr.fer.zemris.java.hw06.shell;

import java.util.List;
/**
 * Interface for commands
 * @author Jura
 *
 */
public interface ShellCommand {
	/**
	 * Implementation of command
	 * @param env
	 * @param arguments
	 * @return
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	/**
	 * 
	 * @return name of command
	 */
	String getCommandName();
	/**
	 * 
	 * @return description of command as a list
	 */
	List<String> getCommandDescription();
}
