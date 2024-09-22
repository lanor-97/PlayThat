package persistence;

import persistence.dao.ArtistaDao;
import persistence.dao.BranoDao;
import persistence.dao.PlaylistDao;
import persistence.dao.UtenteDao;

public class PostgresDAOFactory extends DAOFactory {

	private static  DataSource dataSource;
	
	static {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			dataSource=new DataSource("jdbc:postgresql://localhost:5432/PlayThat","postgres","postgres");
		} 
		catch (Exception e) {
			System.err.println("PostgresDAOFactory.class: failed to load MySQL JDBC driver\n"+e);
			e.printStackTrace();
		}
	}
	
	@Override
	public UtilDao getUtilDao() {
		return new UtilDao(dataSource);
	}

	@Override
	public ArtistaDao getArtistaDao() {
		return new ArtistaDaoJDBC(dataSource);
	}

	@Override
	public BranoDao getBranoDao() {
		return new BranoDaoJDBC(dataSource);
	}

	@Override
	public PlaylistDao getPlaylistDao() {
		return new PlaylistDaoJDBC(dataSource);
	}

	@Override
	public UtenteDao getUtenteDao() {
		return new UtenteDaoJDBC(dataSource);
	}
}
