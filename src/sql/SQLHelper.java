package sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLHelper {
	private static final String URL = "com.mysql.jdbc.Driver";
	private static final String PATH = "";
	private static final String DB_NAME = "DB.db";
	
	
	public SQLHelper() {
		if(!(new File(DB_NAME).exists())) {
			System.err.println("ERROR: DB not found, Creating new database");
			init();
		}
	}
	
	private void init() {
		String sql = "CREATE TABLE IF NOT EXISTS games ("
				+ "autoID INTEGER NOT NULL AUTO_INCREMENT,"
				+ "p1 INTEGER DEFAULT NULL,"
				+ "p2 INTEGER DEFAULT NULL, "
				+ "gamestate TINYINT DEFAULT -1,"
				+ "PRIMARY KEY(autoID),"
				+ "FOREIGN KEY(p1) REFERENCES users(autoID),"
				+ "FOREIGN KEY(p2) REFERENCES users(autoID));";
		
		String sql2 = "CREATE TABLE IF NOT EXISTS moves ("
				+ "autoID INTEGER NOT NULL AUTO_INCREMENT,"
				+ "gID INTEGER DEFAULT NULL,"
				+ "pID INTEGER DEFAULT NULL,"
				+ "x TINYINT DEFAULT NULL,"
				+ "y TINYINT DEFAULT NULL,"
				+ "PRIMARY KEY(autoID),"
				+ "FOREIGN KEY (gID) REFERENCES games(autoID),"
				+ "FOREIGN KEY (pID) REFERENCES games(autoID));";
		
		String sql3 = "CREATE TABLE IF NOT EXISTS users ("
				+ "autoID INTEGER NOT NULL AUTO_INCREMENT,"
				+ "name VARCHAR(15) NOT NULL,"
				+ "surname VARCHAR(25) NOT NULL,"
				+ "username VARCHAR(255) NOT NULL,"
				+ "password VARCHAR(255) NOT NULL,"
				+ "email VARCHAR(50) DEFAULT NULL,"
				+ "isactive TINYINT DEFAULT 1,"
				+ "access_level TINYINT DEFAULT 1"
				+ "PRIMARY KEY(autoID, username, password));";
		
		try(Connection conn = this.connect();
				Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			stmt.execute(sql2);
			stmt.execute(sql3);
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private Connection connect() {
		Connection conn = null;
		
		try {
			Class.forName(URL);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
		} catch(SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			close();
		}
		return conn;
	}
	
	public void getUsers() {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		try((Connection conn = this.connect();
				Statement stmt = conn.createStatement()) {
			String sql = "SELECT ";
		}
	}
	
	public void insertIntoUsers(String name, String surname, String username, String password, String email) {
		String sql = "INSERT INTO users(name, surname, username, password, email) VALUES (?,?,?,PASSWORD(?),?)";
		try(Connection conn = this.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, name);
			pstmt.setString(2, surname);
			pstmt.setString(3, username);
			pstmt.setString(4, password);
			pstmt.setString(5, email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	private void close() {
		
	}
	
	
}
