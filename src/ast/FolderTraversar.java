package ast;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
 
public class FolderTraversar
{
	private static class JavaFilter implements FileFilter
	{
		private String[] extension = {"java", "jar"};

		@Override
		public boolean accept(File pathname)
		{
			if (pathname.isDirectory())
			{
				return true;
			}

			String name = pathname.getName().toLowerCase();
			for (String anExt : extension) {
				if (name.endsWith(anExt)) {
					return true;
				}
			}
			return false;
		}
	}
	
	private String indent = "";
	private File originalFileObject;
	private File fileObject;
	
	private List<String> javaPackage;
	private List<String> jarPackage;
	private List<String> filePaths;
 
	public List<String> getJavaPackage() {
		return javaPackage;
	}

	public void setJavaPackage(List<String> javaPackage) {
		this.javaPackage = javaPackage;
	}

	public List<String> getJarPackage() {
		return jarPackage;
	}

	public void setJarPackage(List<String> jarPackage) {
		this.jarPackage = jarPackage;
	}

	public FolderTraversar(File fileObject) 
	{
		this.originalFileObject = fileObject;
		this.fileObject = fileObject;
		
		javaPackage = new ArrayList<String>();
		jarPackage  = new ArrayList<String>();
		filePaths = new ArrayList<String>();
	}
 
	public void traverse()
	{
		recursiveTraversal(fileObject);
		
		// Process filePaths
		for(String filePath : filePaths)
		{
			String[] allfilePaths = filePath.split("\\\\");
			String dir ="";
			for(String dirname : allfilePaths)
			{
				dir += "\\\\" + dirname;
				javaPackage.add(dir);
				
			}
		}
	}
 
	/**
	 * Get a list of package pack that contains java file
	 * @param fileObject
	 */
	public void recursiveTraversal(File fileObject)
	{
		if (fileObject.isDirectory())
		{
			indent = getIndent(fileObject);
			System.out.println(indent + fileObject.getName());
			File allFiles[] = fileObject.listFiles(new JavaFilter());
			
			for (File aFile : allFiles)
			{
				// add them to Java list
				recursiveTraversal(aFile);
			}
		}
		else
		if (fileObject.isFile())
		{
			filePaths.add(fileObject.getAbsolutePath());
			System.out.println(indent + " " + fileObject.getName());
		}
	}
 
	/**
	 * Get a list of subdirectories
	 * @param fileObject
	 * @return
	 */
	private String getIndent(File fileObject)
	{
		String original = originalFileObject.getAbsolutePath();
		String fileStr = fileObject.getAbsolutePath();
		
		String subString = fileStr.substring(original.length(),	fileStr.length());
		
		String indent = "";
		
		// get the indent per folder
		for (int index = 0; index < subString.length(); index++)
		{
			char aChar = subString.charAt(index);
			if (aChar == File.separatorChar)
			{
				indent = indent + " ";
			}
		}
		return indent;
	}
}
