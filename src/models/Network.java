package models;

import java.util.ArrayList;
import java.util.List;

public class Network
{
	private List<Edge> 	edges;
	private String		commit;

	public Network()
	{
		super();
		this.edges = new ArrayList<Edge>();
	}

	public Network(List<Edge> edges, String commit)
	{
		super();
		this.edges = edges;
		this.commit = commit;
	}
	
	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}

	public List<Edge> getEdges()
	{
		return edges;
	}

	public void setEdges(List<Edge> edges)
	{
		this.edges = edges;
	}

	public String getCommit()
	{
		return commit;
	}

	public void setCommit(String commit)
	{
		this.commit = commit;
	}
}
