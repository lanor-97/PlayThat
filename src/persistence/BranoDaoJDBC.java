package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Artista;
import model.Brano;
import model.Utente;
import persistence.dao.ArtistaDao;
import persistence.dao.BranoDao;

public class BranoDaoJDBC implements BranoDao  {

	private DataSource dataSource;

	public BranoDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void save(Brano brano) {
		Connection connection = this.dataSource.getConnection();
		try {
			int idx = 1;
			String insert = "";
			boolean isInAlbum = brano.getAlbum() != "" && brano.getAlbum() != null;
			
			if(isInAlbum)  {
				insert = "insert into Brano(id, name, author, genre, album, releaseDate)"
						+ "values (?,?,?,?,?,?)";
			} else  {
				insert = "insert into Brano(id, name, author, genre, releaseDate)"
						+ "values (?,?,?,?,?)";
			}
			PreparedStatement statement = connection.prepareStatement(insert);
			
			long id = BranoIdBroker.getId(connection);
			brano.setId(id);
			statement.setLong(idx++, id);
			statement.setString(idx++, brano.getName());
			statement.setString(idx++, brano.getAuthor().getName());
			statement.setString(idx++, brano.getGenre());
			if(isInAlbum)  {
				statement.setString(idx++, brano.getAlbum());
			}
			statement.setInt(idx++, brano.getReleaseDate());
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
	public void update(Brano brano) {
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update Brano set name = ?, author = ?, genre = ?, album = ?, releaseDate = ? where id = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, brano.getName());
			statement.setString(2, brano.getAuthor().getName());
			statement.setString(3, brano.getGenre());
			statement.setString(4, brano.getAlbum());
			statement.setInt(5, brano.getReleaseDate());
			statement.setLong(6, brano.getId());
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
	public void delete(Brano brano) {
		Connection connection = this.dataSource.getConnection();
		try {			
			String delete = "delete from Brano where id = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setLong(1, brano.getId());
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
	public Brano findByPrimaryKey(long id) {
		Connection connection = this.dataSource.getConnection();
		Brano brano = null;
		try {
			String find = "select * from Brano where id = ?";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if(result.next())  {
				ArtistaDao artistaDao = new ArtistaDaoJDBC(dataSource);
				brano = new Brano(
					id,
					result.getString("name"),
					artistaDao.findByPrimaryKey(result.getString("author")),
					result.getString("genre"),
					result.getString("album"),
					result.getInt("releaseDate")
				);
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
		
		return brano;
	}

	@Override
	public Set<Brano> findByName(String name) {
		Connection connection = this.dataSource.getConnection();
		ArtistaDao artistaDao = new ArtistaDaoJDBC(dataSource);
		Set<Brano> brani = new HashSet<Brano>();
		Brano brano = null;
		try {
			String find = "select * from Brano where LOWER(name) = LOWER(?)";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setString(1, name);
			ResultSet result = statement.executeQuery();
			while(result.next())  {
				brano = new Brano(
					result.getLong("id"),
					result.getString("name"),
					artistaDao.findByPrimaryKey(result.getString("author")),
					result.getString("genre"),
					result.getString("album"),
					result.getInt("releaseDate")
				);
				
				brani.add(brano);
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
		
		return brani;
	}

	@Override
	public Set<Brano> findByAlbum(String album) {
		Connection connection = this.dataSource.getConnection();
		ArtistaDao artistaDao = new ArtistaDaoJDBC(dataSource);
		Set<Brano> brani = new HashSet<Brano>();
		Brano brano = null;
		try {
			String find = "select * from Brano where LOWER(album) = LOWER(?)";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setString(1, album);
			ResultSet result = statement.executeQuery();
			while(result.next())  {
				brano = new Brano(
					result.getLong("id"),
					result.getString("name"),
					artistaDao.findByPrimaryKey(result.getString("author")),
					result.getString("genre"),
					result.getString("album"),
					result.getInt("releaseDate")
				);
				
				brani.add(brano);
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
		
		return brani;
	}
	
	@Override
	public Set<Brano> findByGenre(String genre) {
		Connection connection = this.dataSource.getConnection();
		ArtistaDao artistaDao = new ArtistaDaoJDBC(dataSource);
		Set<Brano> brani = new HashSet<Brano>();
		Brano brano = null;
		try {
			String find = "select * from Brano where LOWER(genre) = LOWER(?)";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setString(1, genre);
			ResultSet result = statement.executeQuery();
			while(result.next())  {
				brano = new Brano(
					result.getLong("id"),
					result.getString("name"),
					artistaDao.findByPrimaryKey(result.getString("author")),
					result.getString("genre"),
					result.getString("album"),
					result.getInt("releaseDate")
				);
				
				brani.add(brano);
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
		
		return brani;
	}
	
	@Override
	public Set<Brano> findByAuthor(String author) {
		Connection connection = this.dataSource.getConnection();
		ArtistaDao artistaDao = new ArtistaDaoJDBC(dataSource);
		Set<Brano> brani = new HashSet<Brano>();
		Brano brano = null;
		try {
			String find = "select * from Brano where LOWER(author) = LOWER(?)";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setString(1, author);
			ResultSet result = statement.executeQuery();
			while(result.next())  {
				brano = new Brano(
					result.getLong("id"),
					result.getString("name"),
					artistaDao.findByPrimaryKey(result.getString("author")),
					result.getString("genre"),
					result.getString("album"),
					result.getInt("releaseDate")
				);
				
				brani.add(brano);
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
		
		return brani;
	}

	@Override
	public Set<Brano> findAll() {
		Connection connection = this.dataSource.getConnection();
		ArtistaDao artistaDao = new ArtistaDaoJDBC(dataSource);
		Set<Brano> brani = new HashSet<Brano>();
		Brano brano = null;
		try {
			String find = "select * from Brano";
			PreparedStatement statement = connection.prepareStatement(find);
			ResultSet result = statement.executeQuery();
			
			while(result.next())  {
				brano = new Brano(
					result.getLong("id"),
					result.getString("name"),
					artistaDao.findByPrimaryKey(result.getString("author")),
					result.getString("genre"),
					result.getString("album"),
					result.getInt("releaseDate")
				);
				
				brani.add(brano);
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
		
		return brani;
	}
}
