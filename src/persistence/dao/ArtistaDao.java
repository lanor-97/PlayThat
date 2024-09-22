package persistence.dao;

import java.util.Set;

import model.Artista;
import model.Utente;
import persistence.ArtistaProxy;

public interface ArtistaDao {
	public void save(Artista artista);
	public void update(Artista corso);
	public void delete(Artista corso); 
	
	public Artista findByPrimaryKey(String name);
	public ArtistaProxy findByPrimaryKeyProxy(String name);
	public Set<Artista> findAll(); 
	
	public void addFollower(Artista artista, Utente utente);
	public void removeFollower(Artista artista, Utente utente);
}
