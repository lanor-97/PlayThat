package persistence.dao;

import java.util.List;
import java.util.Set;

import model.Brano;

public interface BranoDao {
	public void save(Brano brano);
	public void update(Brano brano);
	public void delete(Brano brano);
	
	public Brano findByPrimaryKey(long id);
	public Set<Brano> findByName(String name);
	public Set<Brano> findByAlbum(String album);
	public Set<Brano> findByGenre(String genre);
	public Set<Brano> findByAuthor(String author);
	public Set<Brano> findAll();	 
}
