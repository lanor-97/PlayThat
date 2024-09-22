package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import model.Artista;
import model.Utente;
import persistence.dao.ArtistaDao;

public class ArtistaDaoJDBC implements ArtistaDao  {

	private DataSource dataSource;

	public ArtistaDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}	
	
	@Override
	public void save(Artista artista) {
		Connection connection = this.dataSource.getConnection();
		try {
			String insert = "insert into Artista(name, country, startDate)"
					+ "values (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, artista.getName());
			statement.setString(2, artista.getCountry());
			statement.setInt(3, artista.getStartDate());
			statement.executeUpdate();
			
			for(Utente utente : artista.getFollowers())  {
				insert = "insert into SegueArtista(follower, followed)"
						+ "values(?, ?)";
				statement = connection.prepareStatement(insert);
				statement.setString(1, utente.getUsername());
				statement.setString(2, artista.getName());	
				statement.executeUpdate();
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
	}
	
	@Override
	public void update(Artista artista) {
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update Artista set country = ?, startDate = ? where name = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, artista.getCountry());
			statement.setInt(2, artista.getStartDate());
			statement.setString(3, artista.getName());
			
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
	
	@Override
	public void delete(Artista artista) {
		Connection connection = this.dataSource.getConnection();
		try { 
			String delete = "delete from Artista where name = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, artista.getName());
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

	@Override
	public Artista findByPrimaryKey(String name) {
		Connection connection = this.dataSource.getConnection();
		Artista artista = null;
		try {
			String find = "select * from Artista where name = ?";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setString(1, name);
			ResultSet result = statement.executeQuery();
			if(result.next())  {
				artista = new ArtistaProxy(this.dataSource);
				artista.setName(result.getString("name"));				
				artista.setCountry(result.getString("country"));
				artista.setStartDate(result.getInt("startDate"));
				
				find = "select numfollowers from artista_numutentifollowers where name = ?";
				statement = connection.prepareStatement(find);
				statement.setString(1, name);
				result = statement.executeQuery();
				
				if(result.next())  {
					artista.setNumFollowers(result.getInt("numfollowers"));
				} else  {
					artista.setNumFollowers(0);
				}
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
		
		return artista;
	}
	
	@Override
	public ArtistaProxy findByPrimaryKeyProxy(String name) {
		Artista artista = findByPrimaryKey(name);
		ArtistaProxy artistaproxy = null;
		if (artista != null){
			artistaproxy = new ArtistaProxy(dataSource);
			artistaproxy.setAttributes(artista);
		}
		return artistaproxy;
	}

	@Override
	public Set<Artista> findAll() {
		Connection connection = this.dataSource.getConnection();
		Set<Artista> artisti = new HashSet<Artista>();
		Artista artista = null;
		try {
			String find = "select * from Artista";
			PreparedStatement statement = connection.prepareStatement(find);
			ResultSet result = statement.executeQuery();
			while(result.next())  {
				artista = new Artista();
				artista.setName(result.getString("name"));				
				artista.setCountry(result.getString("country"));
				artista.setStartDate(result.getInt("startDate"));
				artisti.add(artista);
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
		
		return artisti;
	}

	
	
	@Override
	public void addFollower(Artista artista, Utente utente)  {
		Connection connection = this.dataSource.getConnection();
		try {			
			String insert = "insert into SegueArtista(follower, followed)"
					+ "values(?, ?);";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, utente.getUsername());
			statement.setString(2, artista.getName());
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
	
	@Override
	public void removeFollower(Artista artista, Utente utente)  {
		Connection connection = this.dataSource.getConnection();
		try {			
			String delete = "delete from SegueArtista where follower = ? and followed = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, utente.getUsername());
			statement.setString(2, artista.getName());
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
