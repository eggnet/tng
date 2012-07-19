package git;

import java.util.ArrayList;
import java.util.List;

import process.Spawner;
import tng.Resources;

public class GitController
{
	private Spawner spawner;
	
	public GitController() {
		spawner = new Spawner(Resources.repository);
	}
	
	public void checkout(String commitID) {
		spawner.spawnProcess(new String[] {"git", "reset", "--hard", commitID});
	}
	
	public List<String> getAllCommits() {
		List<String> commits = new ArrayList<String>();
		parseLogForCommits(commits);
		return commits;
	}
	
	private void parseLogForCommits(List<String> commits) {
		String output = spawner.spawnProcess(new String[] {"git", "log", "--reverse", "--no-merges"});
		
		String[] lines = output.split(System.getProperty("line.separator"));
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].matches(Resources.gitLogCommit)) {
				String[] split = lines[i].split(" ");
				commits.add(split[1]);
			}
		}
	}
}
