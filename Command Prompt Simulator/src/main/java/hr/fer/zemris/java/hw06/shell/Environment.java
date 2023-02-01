package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;
/**
 * Enviroment interface
 * @author Jura
 *
 */
public interface Environment {
	/**
	 * Reads input
	 * @return
	 * @throws ShellIOException
	 */
	 String readLine() throws ShellIOException;
	 /**
	  * Writes given string
	  * @param text
	  * @throws ShellIOException
	  */
	 void write(String text) throws ShellIOException;
	 /**
	  * Writes given string and ends line
	  * @param text
	  * @throws ShellIOException
	  */
	 void writeln(String text) throws ShellIOException;
	 /**
	  * Map of commands
	  * @return
	  */
	 SortedMap<String, ShellCommand> commands();
	 /**
	  * 
	  * @return current multiline symbol
	  */
	 Character getMultilineSymbol();
	 /**
	  * Sets multiline symbol 
	  * @param symbol
	  */
	 void setMultilineSymbol(Character symbol);
	 /**
	  * 
	  * @return current promp symbol
	  */
	 Character getPromptSymbol();
	 /**
	  * Sets prompt symbol
	  * @param symbol
	  */
	 void setPromptSymbol(Character symbol);
	 /**
	  * 
	  * @return morelines symbol
	  */
	 Character getMorelinesSymbol();
	 /**
	  * 
	  * @param symbol
	  */
	 void setMorelinesSymbol(Character symbol);

}
