package db;

import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Method;

import db.util.ISetter;
import db.util.PreparedStatementExecutionItem;
import db.util.ISetter.StringSetter;

public class DatabaseConnector extends DbConnection
{	
	public void createDatabase(String dbName) {
		try {
			// Drop the DB if it already exists
			String query = "DROP DATABASE IF EXISTS " + dbName;
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(query, null);
			addExecutionItem(ei);
			ei.waitUntilExecuted();
			
			// First create the DB.
			query = "CREATE DATABASE " + dbName + ";";
			ei = new PreparedStatementExecutionItem(query, null);
			addExecutionItem(ei);
			ei.waitUntilExecuted();
			
			// Reconnect to our new database.
			close();
			connect(dbName.toLowerCase());
			
			// load our schema			
			runScript(new InputStreamReader(this.getClass().getResourceAsStream("schema.sql")));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int upsertMethod(Method method) {
		String query = "INSERT INTO methods (file_name, package_name, class_type, method_name, " +
				"start_line, end_line, id) VALUES " +
				"(?, ?, ?, ?, " + method.getStart() + ", " + method.getEnd() + ", default)";
		ISetter[] params = {
				new StringSetter(1,method.getFile()),
				new StringSetter(2,method.getPkg()),
				new StringSetter(3,method.getClazz()),
				new StringSetter(4,method.getName())
		};
		PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(query, params);
		addExecutionItem(ei);
		ei.waitUntilExecuted();
		return getSequenceValue("method_id_seq"); 
	}
	
	/**
	 * Get Sequence Id for a sequence table
	 * @param sequence
	 * @return sequence id, -1 of none found or there is exception
	 */
	private int getSequenceValue(String sequence) {
		try 
		{
			// Get the ID
			String sql = "SELECT currval(?)"; 
			ISetter[] params = {new StringSetter(1, sequence)};
			PreparedStatementExecutionItem ei = new PreparedStatementExecutionItem(sql, params);
			addExecutionItem(ei);
			ei.waitUntilExecuted();
			ResultSet rs = ei.getResult();
			if(rs != null && rs.next())
				return rs.getInt("currval");
			return -1;
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
	}
}
