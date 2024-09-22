package persistence;

public abstract class DAOFactory {
	
	public static final int MYSQL = 1;
	public static final int POSTGRESQL = 2;
	
	public static DAOFactory getDAOFactory(int whichFactory) {
		switch ( whichFactory ) {
		
		case MYSQL:
			return null;
		case POSTGRESQL:
			return new PostgresDAOFactory();
		default:
			return null;
		}
	}
	
	public abstract persistence.dao.ArtistaDao getArtistaDao();
	public abstract persistence.dao.BranoDao getBranoDao();
	public abstract persistence.dao.PlaylistDao getPlaylistDao();
	public abstract persistence.dao.UtenteDao getUtenteDao();
	public abstract UtilDao getUtilDao();
}
