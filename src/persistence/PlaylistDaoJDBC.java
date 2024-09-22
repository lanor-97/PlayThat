package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import model.Brano;
import model.Playlist;
import model.Utente;
import persistence.dao.PlaylistDao;
import persistence.dao.UtenteDao;

public class PlaylistDaoJDBC implements PlaylistDao  {

	private DataSource dataSource;

	public PlaylistDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void save(Playlist playlist) {
		Connection connection = this.dataSource.getConnection();
		try {
			String insert = "insert into Playlist(id, name, creator)"
					+ "values (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			
			long id = PlaylistIdBroker.getId(connection);
			playlist.setId(id);
			statement.setLong(1, id);
			statement.setString(2, playlist.getName());
			statement.setString(3, playlist.getCreator().getUsername());
			statement.executeUpdate();
			
			for(Brano b : playlist.getSongs())  {
				insert = "insert into BranoPlaylist(brano, playlist)"
						+ "values(?, ?)";
				statement = connection.prepareStatement(insert);
				statement.setLong(1, b.getId());
				statement.setLong(2, playlist.getId());	
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
	public void update(Playlist playlist) {
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update Playlist set name = ?, creator = ? where id = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, playlist.getName());
			statement.setString(2, playlist.getCreator().getUsername());
			statement.setLong(3, playlist.getId());
			
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
	public void delete(Playlist playlist) {
		Connection connection = this.dataSource.getConnection();
		try {			
			String delete = "delete from Playlist where id = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setLong(1, playlist.getId());
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
	public Playlist findByPrimaryKey(long id) {
		Connection connection = this.dataSource.getConnection();
		Playlist playlist = null;
		try {
			String find = "select * from Playlist where id = ?";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if(result.next())  {
				UtenteDao utenteDao = new UtenteDaoJDBC(dataSource);
				playlist = new Playlist();
				playlist.setId(id);
				playlist.setName(result.getString("name"));
				playlist.setCreator(utenteDao.findByPrimaryKey(result.getString("creator")));
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
		
		return playlist;
	}
	
	@Override
	public PlaylistProxy findByPrimaryKeyProxy(long id) {
		Connection connection = this.dataSource.getConnection();
		PlaylistProxy playlist = null;
		try {
			String find = "select * from Playlist where id = ?";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if(result.next())  {
				UtenteDao utenteDao = new UtenteDaoJDBC(dataSource);
				playlist = new PlaylistProxy(this.dataSource);
				playlist.setId(id);
				playlist.setName(result.getString("name"));
				playlist.setCreator(utenteDao.findByPrimaryKey(result.getString("creator")));
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
		
		return playlist;
	}

	@Override
	public Set<Playlist> findByCreator(Utente creator) {
		Connection connection = this.dataSource.getConnection();
		Set<Playlist> playlists = new HashSet<Playlist>();
		Playlist playlist = null;
		try {
			String find = "select * from Playlist where creator = ?";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setString(1, creator.getUsername());
			ResultSet result = statement.executeQuery();
			while(result.next())  {
				playlist = new Playlist(
					result.getLong("id"),
					result.getString("name"),
					creator
				);
				
				playlists.add(playlist);
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
		
		return playlists;
	}

	@Override
	public Set<Playlist> findAll() {
		Connection connection = this.dataSource.getConnection();
		Set<Playlist> playlists = new HashSet<Playlist>();
		Playlist playlist = null;
		try {
			String find = "select * from Playlist";
			PreparedStatement statement = connection.prepareStatement(find);
			ResultSet result = statement.executeQuery();
			while(result.next())  {
				UtenteDao utenteDao = new UtenteDaoJDBC(dataSource);
				playlist = new Playlist(
					result.getLong("id"),
					result.getString("name"),
					utenteDao.findByPrimaryKey(result.getString("creator"))
				);
				
				playlists.add(playlist);
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
		
		return playlists;
	}

	@Override
	public void addBrano(Playlist playlist, Brano brano) {
		Connection connection = this.dataSource.getConnection();
		try {
			String select = "select from BranoPlaylist where song = ? and playlist = ?;";
			PreparedStatement statement = connection.prepareStatement(select);
			statement.setLong(1, brano.getId());
			statement.setLong(2, playlist.getId());
			ResultSet result = statement.executeQuery();
			
			if(result.next())  {
				return;
			}
			
			String insert = "insert into BranoPlaylist(song, playlist)"
					+ "values(?, ?);";
			statement = connection.prepareStatement(insert);
			statement.setLong(1, brano.getId());
			statement.setLong(2, playlist.getId());
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
	public void removeBrano(Playlist playlist, Brano brano) {
		Connection connection = this.dataSource.getConnection();
		try {			
			String delete = "delete from BranoPlaylist where song = ? and playlist = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setLong(1, brano.getId());
			statement.setLong(2, playlist.getId());
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
