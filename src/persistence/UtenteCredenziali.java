package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Utente;

public class UtenteCredenziali extends Utente {
	private DataSource dataSource;

	public UtenteCredenziali(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public String getPassword(){						
		Connection connection = this.dataSource.getConnection();
		try {
			PreparedStatement statement;
			String query = "select * from utente where username = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, this.getUsername());
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				return result.getString("password");
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		return null;
	}
	
	public void setPassword(String password){						
		Connection connection = this.dataSource.getConnection();
		try {
			PreparedStatement statement;
			String update = "update utente set password = ? where username = ?";
			statement = connection.prepareStatement(update);
			statement.setString(1, password);
			statement.setString(2, this.getUsername());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}
}
