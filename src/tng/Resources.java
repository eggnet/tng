package tng;

public class Resources
{
	public static String dbName;
	public static String repository;
	public static String branch;
	public static String configFile;
	
	// Config file constants
	public static final String classpath = "--ClassFolders";
	public static final String sourcepath = "--SourceFolders";
	
	// Encoding to use
	public static final String encoding = "UTF-8";
	
	// Diff regex
	public static final String diffOldFile = 	"^---.*";
	public static final String diffNewFile = 	"^\\+\\+\\+.*";
	public static final String diffHunkRange = 	"@@.*@@.*";
	public static final String diffOldRange = 	"-[0-9]+,?[0-9]*";
	public static final String diffNewRange = 	"\\+[0-9]+,?[0-9]*";
}
