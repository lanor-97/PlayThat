package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UtilDao {

	private DataSource dataSource;
	
	public UtilDao(DataSource dataSource) {
		this.dataSource=dataSource;
	}

	public void dropDatabase(){
		
		Connection connection = dataSource.getConnection();
		try {
			String delete = "drop SEQUENCE if exists sequenza_id_playlist;"
					+ "drop SEQUENCE if exists sequenza_id_brano;"
					+ "drop table if exists BranoPlaylist;"
					+ "drop table if exists SegueArtista;"
					+ "drop table if exists SegueUtente;"
					+ "drop table if exists BranoPreferito;"
					+ "drop table if exists PlaylistPreferita;"
					+ "drop table if exists UtenteAttività;"
					+ "drop table if exists ArtistaAttività;"
					+ "drop table if exists Playlist;"
					+ "drop table if exists Brano;"
					+ "drop table if exists Utente;"
					+ "drop table if exists Artista;";					
					
			PreparedStatement statement = connection.prepareStatement(delete);
			
			statement.executeUpdate();
			System.out.println("Dropped database tables");
			
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
	
	public void createDatabase(){
		
		Connection connection = dataSource.getConnection();
		try {
			
			String create = "create SEQUENCE sequenza_id_brano;"
					+ "create SEQUENCE sequenza_id_playlist;";
			
			create += "create table Utente("
					+ "name varchar(255) NOT NULL, "
					+ "surname varchar(255) NOT NULL, "
					+ "username varchar(255) PRIMARY KEY, "
					+ "email varchar(255) NOT NULL,"
					+ "password varchar(255),"
					+ "premium boolean DEFAULT('false'));";
			
			create += "create table Artista("
					+ "name varchar(255) PRIMARY KEY,"
					+ "country varchar(255) NOT NULL,"
					+ "startDate int NOT NULL);";
			
			PreparedStatement statement = connection.prepareStatement(create);
			statement.executeUpdate();
			
			create = "create table SegueUtente("
					+ "follower varchar(255) NOT NULL REFERENCES Utente(username),"
					+ "followed varchar(255) NOT NULL REFERENCES Utente(username));";
			
			create += "create table SegueArtista("
					+ "follower varchar(255) NOT NULL REFERENCES Utente(username),"
					+ "followed varchar(255) NOT NULL REFERENCES Artista(name));";
			statement = connection.prepareStatement(create);
			statement.executeUpdate();
			
			create = "create table Brano("
					+ "id bigint PRIMARY KEY,"
					+ "name varchar(255) NOT NULL,"
					+ "author varchar(255) NOT NULL REFERENCES Artista(name),"
					+ "genre varchar(255) NOT NULL,"
					+ "album varchar(255),"
					+ "releaseDate int NOT NULL,"
					+ "UNIQUE(name, author));";
			
			statement = connection.prepareStatement(create);
			statement.executeUpdate();
			
			create = "create table Playlist("
					+ "id bigint PRIMARY KEY,"
					+ "name varchar(255) NOT NULL,"
					+ "creator varchar(255) NOT NULL REFERENCES Utente(username));";
			
			create += "create table BranoPlaylist("
					+ "song int NOT NULL REFERENCES Brano(id),"
					+ "playlist bigint NOT NULL REFERENCES Playlist(id));";
			
			statement = connection.prepareStatement(create);
			statement.executeUpdate();
			
			create = "create table BranoPreferito("
					+ "username varchar(255) NOT NULL REFERENCES Utente(username),"
					+ "song bigint NOT NULL REFERENCES Brano(id));";
			
			create += "create table PlaylistPreferita("
					+ "username varchar(255) NOT NULL REFERENCES Utente(username),"
					+ "playlist bigint NOT NULL REFERENCES Playlist(id));";
			
			statement = connection.prepareStatement(create);
			statement.executeUpdate();
			
			create = "create table UtenteAttività("
					+ "actor varchar(255) NOT NULL REFERENCES Utente(username),"
					+ "description varchar(255) NOT NULL,"
					+ "subjectUtente varchar(255) REFERENCES Utente(username),"
					+ "subjectArtista varchar(255) REFERENCES Artista(name),"
					+ "subjectBrano bigint REFERENCES Brano(id),"
					+ "subjectPlaylist bigint REFERENCES Playlist(id),"
					+ "date_ date DEFAULT(NOW()),"
					+ "time_ time DEFAULT(NOW()));";
			
			create += "create table ArtistaAttività("
					+ "actor varchar(255) NOT NULL REFERENCES Artista(name),"
					+ "description varchar(255) NOT NULL,"
					+ "subjectBrano bigint REFERENCES Brano(id),"
					+ "date_ date DEFAULT(NOW()),"
					+ "time_ time DEFAULT(NOW()));";
			
			statement = connection.prepareStatement(create);
			statement.executeUpdate();
			
			System.out.println("Created database tables");
			
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
	
	public  void resetDatabase() {
			
		Connection connection = dataSource.getConnection();
		try {
			String delete = "";
			PreparedStatement statement = connection.prepareStatement(delete);
			
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
	
	public void dropFunctionsAndTriggers()  {
		Connection connection = dataSource.getConnection();
		try {
			String drop = "drop trigger if exists delete_artista on artista;"
					+ "drop function if exists delete_artista;"
					+ "drop trigger if exists delete_utente on utente;"
					+ "drop function if exists delete_utente;"
					+ "drop trigger if exists delete_brano on brano;"
					+ "drop function if exists delete_brano;"
					+ "drop trigger if exists delete_playlist on playlist;"
					+ "drop function if exists delete_playlist;"
					+ "drop trigger if exists insert_brano on brano;"
					+ "drop function if exists insert_brano;"
					+ "drop trigger if exists insert_segueutente on segueutente;"
					+ "drop function if exists insert_segueutente;"
					+ "drop trigger if exists insert_segueartista on segueartista;"
					+ "drop function if exists insert_segueartista;"
					+ "drop trigger if exists insert_playlist on playlist;"
					+ "drop function if exists insert_playlist;"
					+ "drop trigger if exists insert_branopreferito on branopreferito;"
					+ "drop function if exists insert_branopreferito;"
					+ "drop trigger if exists insert_playlistpreferita on playlistpreferita;"
					+ "drop function if exists insert_playlistpreferita;";
			
			PreparedStatement statement = connection.prepareStatement(drop);
			statement.executeUpdate();
			
			System.out.println("Dropped database functions and triggers");
			
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
	
	public void createFunctionsAndTriggers()  {
		Connection connection = dataSource.getConnection();
		try {
			String function = "";
			function = "CREATE FUNCTION delete_artista() RETURNS trigger AS $delete_artista$"
					+ " BEGIN"
					+ " IF OLD.name IS NULL THEN RAISE EXCEPTION 'Nome artista non può essere null'; END IF;"
					+ " DELETE FROM SegueArtista WHERE followed = OLD.name;"
					+ " DELETE FROM Brano WHERE author = OLD.name;"
					+ " RETURN OLD; "
					+ " END;"
					+ " $delete_artista$ LANGUAGE plpgsql;";
					
			function += "CREATE TRIGGER delete_artista BEFORE DELETE ON Artista"
						+ " FOR EACH ROW EXECUTE PROCEDURE delete_artista();";
			
			PreparedStatement statement = connection.prepareStatement(function);
			statement.executeUpdate();
			
			function = "CREATE FUNCTION delete_utente() RETURNS trigger AS $delete_utente$"
					+ " BEGIN"
					+ " IF OLD.username IS NULL THEN RAISE EXCEPTION 'Username utente non può essere null'; END IF;"
					+ " DELETE FROM SegueArtista WHERE follower = OLD.username;"
					+ " DELETE FROM SegueUtente WHERE follower = OLD.username OR followed = OLD.username;"
					+ " DELETE FROM Playlist WHERE creator = OLD.username;"
					+ " DELETE FROM BranoPreferito WHERE user = OLD.username;"
					+ " DELETE FROM PlaylistPreferita WHERE user = OLD.username;"
					+ " RETURN OLD; "
					+ " END;"
					+ " $delete_utente$ LANGUAGE plpgsql;";
			
			function += "CREATE TRIGGER delete_utente BEFORE DELETE ON Utente"
						+ " FOR EACH ROW EXECUTE PROCEDURE delete_utente();";
			
			statement = connection.prepareStatement(function);
			statement.executeUpdate();
			
			function = "CREATE FUNCTION delete_brano() RETURNS trigger AS $delete_brano$"
					+ " BEGIN"
					+ " IF OLD.id IS NULL THEN RAISE EXCEPTION 'Brano.id non può essere null'; END IF;"
					+ " DELETE FROM BranoPlaylist WHERE song = OLD.id;"
					+ " DELETE FROM BranoPreferito WHERE song = OLD.id;"
					+ " RETURN OLD; "
					+ " END;"
					+ " $delete_brano$ LANGUAGE plpgsql;";
			
			function += "CREATE TRIGGER delete_brano BEFORE DELETE ON Brano"
					+ " FOR EACH ROW EXECUTE PROCEDURE delete_brano();";
			
			statement = connection.prepareStatement(function);
			statement.executeUpdate();
			
			function = "CREATE FUNCTION delete_playlist() RETURNS trigger AS $delete_playlist$"
					+ " BEGIN"
					+ " IF OLD.id IS NULL THEN RAISE EXCEPTION 'Playlist.id non può essere null'; END IF;"
					+ " DELETE FROM BranoPlaylist WHERE playlist = OLD.id;"
					+ " DELETE FROM PlaylistPreferita WHERE playlist = OLD.id;"
					+ " RETURN OLD; "
					+ " END;"
					+ " $delete_playlist$ LANGUAGE plpgsql;";
			
			function += "CREATE TRIGGER delete_playlist BEFORE DELETE ON Playlist"
					+ " FOR EACH ROW EXECUTE PROCEDURE delete_playlist();";
			
			statement = connection.prepareStatement(function);
			statement.executeUpdate();
			
			function = "CREATE FUNCTION insert_brano() RETURNS trigger AS $insert_brano$"
					+ " BEGIN"
					+ " INSERT INTO ArtistaAttività(actor, description, subjectBrano)"
					+ "	values(NEW.author, 'new_brano', NEW.id);"
					+ " RETURN NEW; "
					+ " END;"
					+ " $insert_brano$ LANGUAGE plpgsql;";
			
			function += "CREATE TRIGGER insert_brano AFTER INSERT ON Brano"
					+ " FOR EACH ROW EXECUTE PROCEDURE insert_brano();";
			
			statement = connection.prepareStatement(function);
			statement.executeUpdate();
			
			function = "CREATE FUNCTION insert_segueutente() RETURNS trigger AS $insert_segueutente$"
					+ " BEGIN"
					+ " INSERT INTO UtenteAttività(actor, description, subjectUtente)"
					+ "	values(NEW.followed, 'new_follower_utente', NEW.follower);"
					+ " INSERT INTO UtenteAttività(actor, description, subjectUtente)"
					+ "	values(NEW.follower, 'new_follow_utente', NEW.followed);"
					+ " RETURN NEW; "
					+ " END;"
					+ " $insert_segueutente$ LANGUAGE plpgsql;";
			
			function += "CREATE TRIGGER insert_segueutente AFTER INSERT ON SegueUtente"
					+ " FOR EACH ROW EXECUTE PROCEDURE insert_segueutente();";
			
			statement = connection.prepareStatement(function);
			statement.executeUpdate();
			
			function = "CREATE FUNCTION insert_segueartista() RETURNS trigger AS $insert_segueartista$"
					+ " BEGIN"
					+ " INSERT INTO UtenteAttività(actor, description, subjectArtista)"
					+ "	values(NEW.follower, 'new_follow_artista', NEW.followed);"
					+ " RETURN NEW; "
					+ " END;"
					+ " $insert_segueartista$ LANGUAGE plpgsql;";
			
			function += "CREATE TRIGGER insert_segueartista AFTER INSERT ON SegueArtista"
					+ " FOR EACH ROW EXECUTE PROCEDURE insert_segueartista();";
			
			statement = connection.prepareStatement(function);
			statement.executeUpdate();
			
			function = "CREATE FUNCTION insert_playlist() RETURNS trigger AS $insert_playlist$"
					+ " BEGIN"
					+ " INSERT INTO UtenteAttività(actor, description, subjectPlaylist)"
					+ "	values(NEW.creator, 'new_playlist', NEW.id);"
					+ " RETURN NEW; "
					+ " END;"
					+ " $insert_playlist$ LANGUAGE plpgsql;";
			
			function += "CREATE TRIGGER insert_playlist AFTER INSERT ON Playlist"
					+ " FOR EACH ROW EXECUTE PROCEDURE insert_playlist();";
			
			statement = connection.prepareStatement(function);
			statement.executeUpdate();
			
			function = "CREATE FUNCTION insert_branopreferito() RETURNS trigger AS $insert_branopreferito$"
					+ " BEGIN"
					+ " INSERT INTO UtenteAttività(actor, description, subjectBrano)"
					+ "	values(NEW.username, 'new_favourite_brano', NEW.song);"
					+ " RETURN NEW; "
					+ " END;"
					+ " $insert_branopreferito$ LANGUAGE plpgsql;";
			
			function += "CREATE TRIGGER insert_branopreferito AFTER INSERT ON BranoPreferito"
					+ " FOR EACH ROW EXECUTE PROCEDURE insert_branopreferito();";
			
			statement = connection.prepareStatement(function);
			statement.executeUpdate();
			
			function = "CREATE FUNCTION insert_playlistpreferita() RETURNS trigger AS $insert_playlistpreferita$"
					+ " BEGIN"
					+ " INSERT INTO UtenteAttività(actor, description, subjectPlaylist)"
					+ "	values(NEW.username, 'new_favourite_playlist', NEW.playlist);"
					+ " RETURN NEW; "
					+ " END;"
					+ " $insert_playlistpreferita$ LANGUAGE plpgsql;";
			
			function += "CREATE TRIGGER insert_playlistpreferita AFTER INSERT ON PlaylistPreferita"
					+ " FOR EACH ROW EXECUTE PROCEDURE insert_playlistpreferita();";
			
			statement = connection.prepareStatement(function);
			statement.executeUpdate();
			
			System.out.println("Created database functions and triggers");
			
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
	
	public void dropViews(){
		
		Connection connection = dataSource.getConnection();
		try {
			String delete = "drop view if exists utente_numutentifollowing;"
					+ "drop view if exists utente_numutentifollowers;"
					+ "drop view if exists utente_numartistifollowing;"
					+ "drop view if exists artista_numutentifollowers;"
					+ "drop view if exists utente_numplaylists;"
					+ "drop view if exists utente_numbranipreferiti;"
					+ "drop view if exists utente_numplaylistspreferite;";
					
			PreparedStatement statement = connection.prepareStatement(delete);
			
			statement.executeUpdate();
			System.out.println("Dropped database views");
			
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
	
public void createViews(){
		
		Connection connection = dataSource.getConnection();
		try {
			String create = "create view utente_numutentifollowing as " + 
							"select u.username, count(su.follower) as numfollowing " + 
							"from utente u, segueutente su " + 
							"where u.username = su.follower " + 
							"group by u.username;";
			
			create +=  	"create view utente_numutentifollowers as " + 
						"select u.username, count(su.followed) as numfollowers " + 
						"from utente u, segueutente su " + 
						"where u.username = su.followed " + 
						"group by u.username; ";
			
			create += 	"create view utente_numartistifollowing as " + 
						"select u.username, count(sa.follower) as numfollowing " + 
						"from utente u, segueartista sa " + 
						"where u.username = sa.follower " + 
						"group by u.username;";
			
			create += 	"create view artista_numutentifollowers as " + 
						"select a.name, count(sa.follower) as numfollowers " + 
						"from artista a, segueartista sa " + 
						"where a.name = sa.followed " + 
						"group by a.name;";
			
			create += 	"create view utente_numplaylists as " + 
						"select u.username, count(p.id) as numplaylists " + 
						"from utente u, playlist p " + 
						"where u.username = p.creator " + 
						"group by u.username;";
			
			create += 	"create view utente_numbranipreferiti as " + 
						"select u.username, count(bp.song) as numsongs " + 
						"from utente u, branopreferito bp " + 
						"where u.username = bp.username " + 
						"group by u.username;";
			
			create += 	"create view utente_numplaylistspreferite as " + 
						"select u.username, count(pp.playlist) as numplaylists " + 
						"from utente u, playlistpreferita pp " + 
						"where u.username = pp.username " + 
						"group by u.username;";
					
			PreparedStatement statement = connection.prepareStatement(create);
			
			statement.executeUpdate();
			System.out.println("Created database views");
			
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

