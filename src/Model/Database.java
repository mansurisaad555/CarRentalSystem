package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {

	private Properties prop = new Properties();
	private String url;
	private String user;
	private String password;
	private Statement statement;

	public Database() {
		try {
			// Load properties from the config file
			FileInputStream input = new FileInputStream("config.properties");
			prop.load(input);

			// Get the database connection details from properties file
			url = prop.getProperty("db.url");
			user = prop.getProperty("db.user");
			password = prop.getProperty("db.password");

			// Establish the connection
			Connection connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Config file not found", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading config file", e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Database connection error", e);
		}
	}

	public Statement getStatement() {
		return statement;
	}
}
