package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class Ls implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
		List<String> l = Helper.parse(arguments);
		
		if (l.size()!=1) throw new IllegalArgumentException();
		
		Path path = Paths.get(l.get(0));
		File[] files = path.toFile().listFiles();
		
		if (files==null) throw new IllegalArgumentException("Its not dir");
		
		for (File file : files) {
			String s="";
			
			s += file.isDirectory() ? "d" : "-";
			s += file.canRead() ? "r" : "-";
			s += file.canWrite() ? "w" : "-";
			s += file.canExecute() ? "x " : "- ";
			
			s+=String.format("%10d ", Files.walk(file.toPath()).mapToLong(p -> p.toFile().length()).sum());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BasicFileAttributeView faView = Files.getFileAttributeView(
			file.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
			);
			BasicFileAttributes attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			s += formattedDateTime;
			s += " " + file.getName();
			
			env.writeln(s);
		}
		
		} catch(Exception e) {
			env.writeln(e.getMessage());
		}
		
		
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("writes a directory listing (not recursive)");
		l.add("The output consists of 4 columns.");
		l.add("First column indicates if current object is directory (d), readable (r), writable (w) and executable (x).");
		l.add("Second column contains object size in bytes that is right aligned and occupies 10 characters.");
		l.add("Follows file creation date/time and finally file name.");
		return l;
	}

}
