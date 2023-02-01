package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class Tree implements ShellCommand{

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> l = Helper.parse(arguments);
			
			if (l.size()!=1) throw new IllegalArgumentException();
			
			Path path = Paths.get(l.get(0));
			
			Files.walkFileTree(path, new FileVisitor<Path>() {
				private int indent=0;
				
				
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					String s="";
					for (int i = 0; i < indent; i++) s+= " ";
					s += dir.toFile().getName();
					indent+=2;
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					String s="";
					for (int i = 0; i < indent; i++) s+= " ";
					s += file.getFileName();
					env.writeln(s);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					// TODO Auto-generated method stub
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					indent-=2;
					return FileVisitResult.CONTINUE;
				}
			});
			
		} catch (Exception e) {
			env.writeln(e.getMessage());
		}
		
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> l = new ArrayList<>();
		l.add("command expects a single argument (directory name)");
		l.add("prints a tree (each directory level shifts output two charatcers to the right)");
		return l;
	}

}
