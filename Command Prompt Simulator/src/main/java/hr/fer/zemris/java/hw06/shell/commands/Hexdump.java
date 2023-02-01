package hr.fer.zemris.java.hw06.shell.commands;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class Hexdump implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		try {
			List<String> l = Helper.parse(arguments);
			
			if (l.size()!=1) throw new IllegalArgumentException();
			
			Path path = Paths.get(l.get(0));
			
			try(InputStream stream = Files.newInputStream(path)){
				int n=0;
				byte[] block = stream.readNBytes(16);
				
				while (block.length >0)
				{
					String s="";
					s+=String.format("%08X:", n++ * 16);
					
					String left="";
					String r="", t="";
					for (int i = 0; i < block.length; i++) {
						if (i<8) left += String.format(" %02X", block[i]);
						else r += String.format("%02X ", block[i]);
						t += String.format("%c", (block[i] < 32 || block[i] > 127) ? '.' : block[i]);
					}
					
					s += String.format("%-24s | ", left);
					s += String.format("%-24s| ", r);
					s += t;
					
					env.writeln(s);
					
					block = stream.readNBytes(16);
					
				}
			}
			
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("expects a single argument: file name");
		l.add("produces hex-output");
		l.add("works only with files");
		l.add("If the second argument is directory copies the original file into that directory using the original file name.");
		return l;
	}

}
