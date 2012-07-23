package models;

public class Node
{
	private String 	email;
	private int 	id;

	public Node()
	{
		super();
	}
	
	public Node(String email)
	{
		super();
		this.email = email;
	}

	public Node(String email, int id)
	{
		super();
		this.email = email;
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
