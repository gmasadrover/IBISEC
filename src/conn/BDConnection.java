package conn;
import java.sql.*;

public class BDConnection {	
	
	static final String hostName = "10.215.26.93";
	static final String dbName = "proves";
	
	//static final String hostName = "10.215.26.87";
	//static final String dbName = "IBISEC";
	
	public static Connection getPostgreConnection() throws ClassNotFoundException, SQLException 
	  {
	    Connection conn = null;
	    String userName = "postgres";
	    String password = "guillem";
	    // connect to the database
	    conn = getPostgreConnection(hostName, dbName, userName, password);
	    return conn;	    
	  }   

	  public static Connection getPostgreConnection(String hostName, String dbName,
		        String userName, String password) throws SQLException,
      ClassNotFoundException
	  {
	    Connection conn = null;
	   	Class.forName("org.postgresql.Driver");
	   	String url = "jdbc:postgresql://" + hostName + ":5432/" + dbName;
	   	conn = DriverManager.getConnection(url,userName, password);    	    
	    return conn;
	  }

}
