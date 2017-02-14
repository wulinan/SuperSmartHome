package com.tos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class DBManager {
	private DBManager Instance ;
	
	public synchronized DBManager getInstance(){
		if(Instance == null){
			Instance = new DBManager();
		}
		return Instance;
	}
	
	private Connection connection;
	private DBManager() {
		 Connection c = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      connection = c;
		      Statement stmt = c.createStatement();
		      if (!isTableExists("DEVICE")){
			      String sql = "CREATE TABLE DEVICE " +
			                   "(ID CHAR(50) PRIMARY KEY     NOT NULL," +
			                   " NAME           TEXT    NOT NULL)"; 
			      stmt.executeUpdate(sql);
			      stmt.close();
		      }
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    }
		    System.out.println("Opened database successfully");
	}
	
	
	boolean isTableExists(String tableName) throws SQLException
	{
	    if (tableName == null || !connection.isValid(10))
	    {
	        return false;
	    }
	    Statement statement = connection.createStatement();
	    
	    ResultSet rs = statement.executeQuery(String.format("SELECT COUNT(*) FROM sqlite_master WHERE type = %s AND name = %s",
	    		"table", tableName));
	   if(!rs.first()){
		   statement.close();
		   return false;
	   }
	  
	   int count = rs.getInt(0);
	    statement.close();
	    return count > 0;
	}
	
	public boolean insertDevice(String name,String uuid) throws SQLException{
	      Statement stmt = connection.createStatement();
	      String sql = String.format("INSERT INTO DEVICE (ID,NAME) VALUES (%s,%s );",uuid,name); 
	      stmt.executeUpdate(sql);
	      return true;
	}
	
	public boolean containDevice(String uuid) throws SQLException{
	      Statement stmt = connection.createStatement();
	      String sql = String.format("SELECT * FROM DEVICE WHERE ID = %s;",uuid); 
	      ResultSet rs = stmt.executeQuery(sql);
	      boolean res =  rs.first();
	      rs.close();
	      return res;
	}
}
