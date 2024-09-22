package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import model.Artista;
import model.Brano;
import model.Utente;
import persistence.dao.BranoDao;
import persistence.dao.UtenteDao;

public class ArtistaProxy extends Artista  {

	private DataSource dataSource;

	public ArtistaProxy(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setAttributes(Artista artista)  {
		
		setName(artista.getName());
		setCountry(artista.getCountry());
		setStartDate(artista.getStartDate());
		
		setNumFollowers(artista.getNumFollowers());
	}
	
	public Set<Brano> getBrani(Artista artista) {
		Connection connection = this.dataSource.getConnection();
		Set<Brano> brani = new HashSet<Brano>();
		Brano brano = null;
		try {
			String find = "select * from Brano where author = ?";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setString(1, artista.getName());
			ResultSet result = statement.executeQuery();
			while(result.next())  {
				brano = new Brano(
					result.getLong("id"),
					result.getString("name"),
					artista,
					result.getString("genre"),
					result.getString("album"),
					result.getInt("releaseDate"));
				brani.add(brano);
			}
			
			find = "select brano from Featuring where artista = ?";
			statement = connection.prepareStatement(find);
			statement.setString(1, artista.getName());
			result = statement.executeQuery();
			BranoDao branoDao = new BranoDaoJDBC(dataSource);
			while(result.next())  {
				
				brano = branoDao.findByPrimaryKey(result.getLong("brano"));
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
	
	public Set<Utente> getFollowers() {
		Connection connection = this.dataSource.getConnection();
		Set<Utente> followers = new HashSet<Utente>();
		Utente utente = null;
		try {
			String find = "select * from SegueArtista where followed = ?";
			PreparedStatement statement = connection.prepareStatement(find);
			statement.setString(1, this.getName());
			ResultSet result = statement.executeQuery();
			
			UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
			while(result.next())  {
				utente = utenteDao.findByPrimaryKey(result.getString("follower"));
				followers.add(utente);
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
	
	public boolean isFollowedByUsername(String username)  {
		
		Connection connection = this.dataSource.getConnection();
		try {
			String select = "select * from SegueArtista where follower = ? and followed = ?";
			PreparedStatement statement = connection.prepareStatement(select);
			statement.setString(1, username);
			statement.setString(2, this.getName());
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
}
