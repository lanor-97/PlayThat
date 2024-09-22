package persistence.dao;

import java.util.Set;

import model.Brano;
import model.Playlist;
import model.Utente;
import persistence.PlaylistProxy;

public interface PlaylistDao {
	public void save(Playlist playlist);
	public void update(Playlist playlist);
	public void delete(Playlist playlist);
	
	public void addBrano(Playlist playlist, Brano brano);
	public void removeBrano(Playlist playlist, Brano brano);
	
	public Playlist findByPrimaryKey(long id);
	public PlaylistProxy findByPrimaryKeyProxy(long id);
	public Set<Playlist> findByCreator(Utente creator);
	public Set<Playlist> findAll();       
	
}
