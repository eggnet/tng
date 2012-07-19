package tng;

import git.GitController;

import java.util.List;

import diff.UnifiedDiffParser;
import difflib.DiffUtils;
import difflib.Patch;
import process.Spawner;
import ast.Parser;

public class Main
{
	public static void main(String[] args) {
		System.out.println("TNG developed by eggnet.");
		System.out.println();
		try {
			if (args.length < 4 ) {
				System.out.println("Retry: TNG [dbname] [repository] [branch] [configFile]");
				throw new ArrayIndexOutOfBoundsException();
			}
			else {
				try  {
					// Set up the resources
					Resources.dbName = args[0];
					Resources.repository = args[1];
					Resources.branch = args[2];
					Resources.configFile = args[3];
					
					//Parser parser = new Parser(Resources.configFile);
					//parser.parseFile("/home/jordan/Documents/agilefant/src/fi/hut/soberit/agilefant/db/hibernate/EnumUserType.java");
					
					GitController gc = new GitController();
					gc.getAllCommits();
				} 
				catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
}
