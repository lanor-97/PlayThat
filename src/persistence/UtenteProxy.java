package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Artista;
import model.Utente;
import persistence.dao.ArtistaDao;
import persistence.dao.BranoDao;
import persistence.dao.PlaylistDao;
import persistence.dao.UtenteDao;

public class UtenteProxy extends Utente  {

	private DataSource dataSource;

	public UtenteProxy(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setAttributes(Utente utente)  {
		setName(utente.getName());
		setSurname(utente.getSurname());
		setUsername(utente.getUsername());
		setEmail(utente.getEmail());
		
		setNumFollowers(utente.getNumFollowers());
		setNumFollowingUsers(utente.getNumFollowingUsers());
		setNumFollowingArtists(utente.getNumFollowingArtists());
	}
	
	public Set<Utente> getFollowers() {
		Connection connection = this.dataSource.getConnection();
		Set<Utente> followers = new HashSet<Utente>();
		Utente u = null;
		try {
			String select = "select * from SegueUtente where followed = ?";
			PreparedStatement statement = connection.prepareStatement(select);
			statement.setString(1, this.getUsername());
			ResultSet result = statement.executeQuery();
			while(result.next())  {
				u = new Utente();
				u.setUsername(result.getString("follower"));
				
				followers.add(u);
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
		
		return followers;
	}

	public Set<Utente> getFollowings() {
		Connection connection = this.dataSource.getConnection();
		Set<Utente> followings = new HashSet<Utente>();
		Utente u = null;
		try {
			String select = "select * from SegueUtente where follower = ?";
			PreparedStatement statement = connection.prepareStatement(select);
			statement.setString(1, this.getUsername());
			ResultSet result = statement.executeQuery();
			while(result.next())  {
				u = new Utente();
				u.setUsername(result.getString("followed"));
				
				followings.add(u);
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
		
		return followings;
	}

	public Set<Artista> getFollowingArtists() {
		Connection connection = this.dataSource.getConnection();
		Set<Artista> artisti = new HashSet<Artista>();
		Artista artista = null;
		try {
			String select = "select * from SegueArtista where follower = ?";
			PreparedStatement statement = connection.prepareStatement(select);
			statement.setString(1, this.getUsername());
			ResultSet result = statement.executeQuery();
			while(result.next())  {
				artista = new Artista();
				artista.setName(result.getString("followed"));
				
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
	
	public boolean isFollowedByUsername(String username)  {
		
		Connection connection = this.dataSource.getConnection();
		try {
			String select = "select * from SegueUtente where follower = ? and followed = ?";
			PreparedStatement statement = connection.prepareStatement(select);
			statement.setString(1, username);
			statement.setString(2, this.getUsername());
			ResultSet result = statement.executeQuery();
			
			if(result.next())  {
				return true;
			}
			return false;
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
	
	//query molto pesante
	public List<List<Object>> getAttivit‡Recenti()  {
		Connection connection = this.dataSource.getConnection();
		
		List<List<Object>> results = new ArrayList<List<Object>>();
		
		results.add(new ArrayList<Object>());	//actor, utente che esegue attivit‡
		results.add(new ArrayList<Object>());	//description, tipo attivit‡
		results.add(new ArrayList<Object>());	//subject, destinatario attivit‡
		
		Set<Utente> followingUsers = this.getFollowings();
		Set<Artista> followingArtists = this.getFollowingArtists();
		
		UtenteDao utenteDao = new UtenteDaoJDBC(this.dataSource);
		PlaylistDao playlistDao = new PlaylistDaoJDBC(this.dataSource);
		BranoDao branoDao = new BranoDaoJDBC(this.dataSource);
		ArtistaDao artistaDao = new ArtistaDaoJDBC(this.dataSource);
		
		try {
			//seleziono max 15 attivit‡ recenti degli utenti seguiti
			//pi˘ poi 5 attivit‡ recenti artisti seguiti
			String select = "";
			PreparedStatement statement;
			
			if(followingUsers.size() > 0)  {
				
				select = "select * from UtenteAttivit‡ where actor in (?";
				
				for(int i = 1; i < followingUsers.size(); i++)  {
					select += ", ?";
				}
				select += ") order by date_ desc, time_ desc limit(15);";
				
				statement = connection.prepareStatement(select);
				int i = 1;
				for(Utente u : followingUsers)  {
					statement.setString(i, u.getUsername());
					i++;
				}
				
				ResultSet result = statement.executeQuery();
				
				while(result.next())  {
					results.get(0).add(utenteDao.findByPrimaryKey(result.getString("actor")));
					String desc = result.getString("description");
					results.get(1).add(desc);
					
					if(desc.equals("new_follower_utente") || desc.equals("new_follow_utente"))  {
						results.get(2).add(utenteDao.findByPrimaryKey(result.getString("subjectUtente")));
					} else if(desc.equals("new_follow_artista"))  {
						results.get(2).add(artistaDao.findByPrimaryKey(result.getString("subjectArtista")));
					} else if(desc.equals("new_playlist") || desc.equals("new_favourite_playlist"))  {
						results.get(2).add(playlistDao.findByPrimaryKey(result.getLong("subjectPlaylist")));
					} else if(desc.equals("new_favourite_brano"))  {
						results.get(2).add(branoDao.findByPrimaryKey(result.getLong("subjectBrano")));
					}
				}
			}
			
			if(followingArtists.size() > 0)  {
				select = "select * from ArtistaAttivit‡ where actor in (?";
				
				for(int i = 1; i < followingArtists.size(); i++)  {
					select += ", ?";
				}
				select += ") order by date_ desc, time_ desc limit(5);";
				
				statement = connection.prepareStatement(select);
				int i = 1;
				for(Artista a : followingArtists)  {
					statement.setString(i, a.getName());
					i++;
				}
				
				ResultSet result = statement.executeQuery();
				
				while(result.next())  {
					results.get(0).add(artistaDao.findByPrimaryKey(result.getString("actor")));
					
					String desc = result.getString("description");
					results.get(1).add(desc);
					
					if(desc.equals("new_brano"))  {
						results.get(2).add(branoDao.findByPrimaryKey(result.getLong("subjectBrano")));
					}
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
		
		return results;
	}
	
}
