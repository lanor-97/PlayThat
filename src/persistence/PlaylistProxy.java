package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import model.Brano;
import model.Playlist;
import persistence.dao.BranoDao;

public class PlaylistProxy extends Playlist  {

	private DataSource dataSource;

	public PlaylistProxy(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Set<Brano> getSongs(Playlist playlist) {
		Connection connection = this.dataSource.getConnection();
		BranoDao branoDao = new BranoDaoJDBC(dataSource);
		Set<Brano> songs = new HashSet<Brano>();
		Brano brano = null;
		try {
			String select = "select * from BranoPlaylist where playlist = ?";
			PreparedStatement statement = connection.prepareStatement(select);
			statement.setLong(1, playlist.getId());
			ResultSet result = statement.executeQuery();
			
			while(result.next())  {
				brano = branoDao.findByPrimaryKey(result.getLong("song"));
				
				songs.add(brano);
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
		
		return songs;
	}
}
