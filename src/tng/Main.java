package tng;

import java.io.File;
import java.util.List;

import models.Method;
import models.Pair;

import git.GitController;
import db.DatabaseConnector;
import ast.CallGraphGenerator;
import ast.FolderTraversar;


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
					// test for foldertraversal
					String folderPath = "C:\\Users\\Infiro\\testproject\\src";
					FolderTraversar traversal = new FolderTraversar(new File(folderPath));
					traversal.traverse();
					
					// Set up the resources
					Resources.dbName = args[0];
					Resources.repository = args[1];
					Resources.branch = args[2];
					Resources.configFile = args[3];
					setRepositoryName(args[1]);
					
					DatabaseConnector db = new DatabaseConnector();
					db.connect("eggnet");
					db.createDatabase("tng");
					
					NetworkBuilder nb = new NetworkBuilder(db);
					nb.buildAllNetworksNoUpdate();
					//nb.buildNetwork("809d8a783abb0058875fbc1bff45bc08054a27ea", false);
					
					db.close();
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
	
	private static void setRepositoryName(String path) {
		Resources.repositoryName = path.substring(path.lastIndexOf("/")+1);
	}
}
