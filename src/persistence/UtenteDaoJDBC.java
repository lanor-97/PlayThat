package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import model.Artista;
import model.Brano;
import model.Playlist;
import model.Utente;
import persistence.dao.BranoDao;
import persistence.dao.PlaylistDao;
import persistence.dao.UtenteDao;

public class UtenteDaoJDBC implements UtenteDao  {

	private DataSource dataSource;

	public UtenteDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void save(Utente utente) {
		Connection connection = this.dataSource.getConnection();
		try {
			String insert = "insert into Utente(name, surname, username, email)"
					+ "values (?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, utente.getName());
			statement.setString(2, utente.getSurname());
			statement.setString(3, utente.getUsername());
			statement.setString(4, utente.getEmail());
			statement.executeUpdate();
			
			for(Utente u : utente.getFollowers())  {
				insert = "insert into SegueUtente(follower, followed)"
						+ "values(?, ?)";
				statement = connection.prepareStatement(insert);
				statement.setString(1, u.getUsername());
				statement.setString(2, utente.getUsername());	
				statement.executeUpdate();
			}
			
			for(Utente u : utente.getFollowing())  {
				insert = "insert into SegueUtente(follower, followed)"
						+ "values(?, ?)";
				statement = connection.prepareStatement(insert);
				statement.setString(1, utente.getUsername());
				statement.setString(2, u.getUsername());	
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
	public void update(Utente utente) {
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update Utente set name = ?, surname = ?, email = ? where username = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, utente.getName());
			statement.setString(2, utente.getSurname());
			statement.setString(3, utente.getEmail());
			statement.setString(4, utente.getUsername());
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
	public void delete(Utente utente) {
		Connection connection = this.dataSource.getConnection();
		try { 
			String delete = "delete from Utente where username = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, utente.getUsername());
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
	public Utente findByPrimaryKey(String username) {
		Connection connection = this.dataSource.getConnection();
		Utente utente = null;
		try {
			String find = "select * from Utente where username = ?";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setString(1, username);
			ResultSet result = statement.executeQuery();
			if(result.next())  {
				utente = new Utente();
				utente.setName(result.getString("name"));
				utente.setSurname(result.getString("surname"));
				utente.setUsername(result.getString("username"));
				utente.setEmail(result.getString("email"));
				utente.setPremium(result.getBoolean("premium"));
				
				find = "select numfollowing from utente_numutentifollowing where username = ?";
				statement = connection.prepareStatement(find);
				statement.setString(1, username);
				result = statement.executeQuery();
				
				if(result.next())  {
					utente.setNumFollowingUsers(result.getInt("numfollowing"));
				} else  {
					utente.setNumFollowingUsers(0);
				}
				
				find = "select numfollowers from utente_numutentifollowers where username = ?";
				statement = connection.prepareStatement(find);
				statement.setString(1, username);
				result = statement.executeQuery();
				
				if(result.next())  {
					utente.setNumFollowers(result.getInt("numfollowers"));
				} else {
					utente.setNumFollowers(0);
				}
				
				find = "select numfollowing from utente_numartistifollowing where username = ?";
				statement = connection.prepareStatement(find);
				statement.setString(1, username);
				result = statement.executeQuery();
				
				if(result.next())  {
					utente.setNumFollowingArtists(result.getInt("numfollowing"));
				} else  {
					utente.setNumFollowingArtists(0);
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
		
		return utente;
	}
	
	@Override
	public UtenteProxy findByPrimaryKeyProxy(String username) {
		Utente utente = findByPrimaryKey(username);
		UtenteProxy utenteproxy = null;
		if (utente != null){
			utenteproxy = new UtenteProxy(dataSource);
			utenteproxy.setAttributes(utente);
		}
		return utenteproxy;
	}

	@Override
	public UtenteCredenziali findByPrimaryKeyCredential(String username) {
		Utente utente = findByPrimaryKey(username);
		UtenteCredenziali utenteCred = null;
		if (utente != null){
			utenteCred = new UtenteCredenziali(dataSource);
			utenteCred.setName(utente.getName());				
			utenteCred.setSurname(utente.getSurname());
			utenteCred.setUsername(utente.getUsername());
			utenteCred.setEmail(utente.getEmail());
		}
		return utenteCred;
	}

	@Override
	public Set<Utente> findAll() {
		Connection connection = this.dataSource.getConnection();
		Set<Utente> utenti = new HashSet<Utente>();
		Utente utente = null;
		try {
			String find = "select * from Utente";
			PreparedStatement statement = connection.prepareStatement(find);
			ResultSet result = statement.executeQuery();
			while(result.next())  {
				utente = new Utente(
					result.getString("name"),
					result.getString("surname"),
					result.getString("username"),
					result.getString("email")
				);
				utente.setPremium(result.getBoolean("premium"));
				utenti.add(utente);
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
		
		return utenti;
	}

	@Override
	public void addFollower(Utente utente, Utente follower) {
		Connection connection = this.dataSource.getConnection();
		try {			
			String insert = "insert into SegueUtente(follower, followed)"
					+ "values(?, ?);";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, follower.getUsername());
			statement.setString(2, utente.getUsername());
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
	public void addFollowingArtist(Utente utente, Artista artista) {
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
	public void removeFollower(Utente utente, Utente follower) {
		Connection connection = this.dataSource.getConnection();
		try {			
			String delete = "delete from SegueUtente where follower = ? and followed = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, follower.getUsername());
			statement.setString(2, utente.getUsername());
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
	public void setPassword(Utente utente, String password) {
		Connection connection = this.dataSource.getConnection();
		try {
			String find = "select * from Utente where name = ?";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setString(1, utente.getUsername());
			ResultSet result = statement.executeQuery();
			if(result.next())  {
				find = "update Utente set password = ?";
				statement.setString(1, password);
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
	public Set<Playlist> findFavouritePlaylists(Utente utente) {
		Connection connection = this.dataSource.getConnection();
		PlaylistDao playlistDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPlaylistDao();
		Set<Playlist> playlists = new HashSet<Playlist>();
		Playlist playlist = null;
		try {
			String find = "select playlist from PlaylistPreferita where username = ?";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setString(1, utente.getUsername());
			ResultSet result = statement.executeQuery();
			
			while(result.next())  {
				playlist = playlistDao.findByPrimaryKey(result.getLong("playlist"));
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
	public void addFavouritePlaylist(Utente utente, Playlist playlist) {
		Connection connection = this.dataSource.getConnection();
		try {
			String insert = "insert into PlaylistPreferita(username, playlist) "
					+ "values(?, ?);";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, utente.getUsername());
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
	public void removeFavouritePlaylist(Utente utente, Playlist playlist) {
		Connection connection = this.dataSource.getConnection();
		try {
			String delete = "delete from PlaylistPreferita where username = ? and playlist = ?;";
			
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, utente.getUsername());
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
	public Set<Brano> findFavouriteSongs(Utente utente) {
		Connection connection = this.dataSource.getConnection();
		BranoDao branoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getBranoDao();
		Set<Brano> brani = new HashSet<Brano>();
		Brano brano = null;
		try {
			String find = "select song from BranoPreferito where username = ?";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setString(1, utente.getUsername());
			ResultSet result = statement.executeQuery();
			
			while(result.next())  {
				brano = branoDao.findByPrimaryKey(result.getLong("song"));
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
	public void addFavouriteSong(Utente utente, Brano brano) {
		Connection connection = this.dataSource.getConnection();
		try {
			String insert = "insert into BranoPreferito(username, song) "
					+ "values(?, ?);";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, utente.getUsername());
			statement.setLong(2, brano.getId());
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
	public void removeFavouriteSong(Utente utente, Brano brano) {
		Connection connection = this.dataSource.getConnection();
		try {
			String delete = "delete from BranoPreferito where username = ? and song = ?;";
			
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, utente.getUsername());
			statement.setLong(2, brano.getId());
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
	public void setPremium(Utente utente, boolean premium) {
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update Utente set premium = ? where username = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setBoolean(1, utente.isPremium());
			statement.setString(2, utente.getUsername());
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
