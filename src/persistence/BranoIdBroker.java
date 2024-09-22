package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class BranoIdBroker {
	
	private static final String query = "SELECT nextval('sequenza_id_brano') AS id";

	public static Long getId(Connection connection) {
		Long id = null;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			result.next();
			id = result.getLong("id");
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
		return id;
	}
}
