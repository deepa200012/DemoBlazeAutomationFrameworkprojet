package databaseutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class DatabaseUtility {

	Connection con;

	public void getDbConnection(String url, String username, String password) {

		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void getDbConnection() throws SQLException {
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		con = DriverManager.getConnection("jdbc:mysql://49.249.29.4:3307/DemoBlaze", "root@%", "root");

	}

	public void closeDbConnection() throws SQLException {
		con.close();
	}

	public ResultSet executeSelectQuery(String query) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery(query);
		return result;

	}

	public int executeNonSelectQuery(String query) throws SQLException {
		Statement stmt = con.createStatement();
		int result = stmt.executeUpdate(query);
		return result;
	}
}
