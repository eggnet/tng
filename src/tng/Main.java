package tng;

import git.GitController;
import db.DatabaseConnector;
import ast.CallGraphGenerator;


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
					
					//DatabaseConnector db = new DatabaseConnector();
					//db.connect(Resources.eggnetDB);
					//db.createDatabase("tng");
					
					//CallGraphGenerator cgg = new CallGraphGenerator(db);
					//cgg.createCallGraphAtCommit("84c7bea004e6f5f0ed523ef731963b649a26868e");
					
					GitController gc = new GitController();
					System.out.println(gc.getOwnersOfFileRange("src/fi/hut/soberit/agilefant/model/Story.java", 1, 10));
					
					// Close the db
					//db.close();
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
