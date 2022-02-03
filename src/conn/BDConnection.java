package conn;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class BDConnection {	
	
	//static final String hostName = "10.215.26.93";
	//static final String dbName = "proves";  
	
	//static final String hostName =  "10.215.26.87";
	//static final String dbName = "IBISEC";
	
	
	public static Connection getPostgreConnection() 
	  {
	    Connection conn = null;
	    String userName = "postgres";
	    String password = "guillem";
	    // connect to the database
	    
	    // Get the base naming context
	    Context env;
		try {
			env = (Context)new InitialContext().lookup("java:comp/env");
			 String hostName = (String)env.lookup("hostName");
		     String dbName = (String)env.lookup("dbName");
		    conn = getPostgreConnection(hostName, dbName, userName, password);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // Get a single value
	   
	    return conn;	    
	  }   

	  public static Connection getPostgreConnection(String hostName, String dbName,
		        String userName, String password) 
	  {
	    Connection conn = null;
	    String url = "jdbc:postgresql://" + hostName + ":5432/" + dbName;
	   	try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url,userName, password);    	    
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	
	   	
	    return conn;
	  }

}
