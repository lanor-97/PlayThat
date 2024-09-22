package persistence.dao;

import java.util.Set;

import model.Artista;
import model.Brano;
import model.Playlist;
import model.Utente;
import persistence.UtenteCredenziali;
import persistence.UtenteProxy;

public interface UtenteDao {
	public void save(Utente utente);
	public void update(Utente utente);
	public void delete(Utente utente);
	
	public Utente findByPrimaryKey(String username);
	public UtenteCredenziali findByPrimaryKeyCredential(String username);
	UtenteProxy findByPrimaryKeyProxy(String username);
	public Set<Utente> findAll();
	public Set<Playlist> findFavouritePlaylists(Utente utente);
	public Set<Brano> findFavouriteSongs(Utente utente);
	
	public void addFollower(Utente utente, Utente follower);
	public void removeFollower(Utente utente, Utente follower);
	public void addFollowingArtist(Utente utente, Artista artista);
	
	public void addFavouritePlaylist(Utente utente, Playlist playlist);
	public void removeFavouritePlaylist(Utente utente, Playlist playlist);
	public void addFavouriteSong(Utente utente, Brano brano);
	public void removeFavouriteSong(Utente utente, Brano brano);
	
	public void setPremium(Utente utente, boolean premium);
	public void setPassword(Utente utente, String password);
	
}
