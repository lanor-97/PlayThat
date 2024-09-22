package model;

import java.util.HashSet;
import java.util.Set;

public class Playlist {

	private long id;
	private String name;
	private Utente creator;
	private Set<Brano> songs;
	
	
	public Playlist()  {
		songs = new HashSet<Brano>();
	}
	
	public Playlist(String name, Utente creator) {
		this.name = name;
		this.creator = creator;
		songs = new HashSet<Brano>();
	}
	
	public Playlist(long id, String name, Utente creator) {
		this.id = id;
		this.name = name;
		this.creator = creator;
		songs = new HashSet<Brano>();
	}
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public Utente getCreator() {
		return creator;
	}
	public void setCreator(Utente creator) {
		this.creator = creator;
	}
	public Set<Brano> getSongs() {
		return songs;
	}
	public void setSongs(Set<Brano> songs) {
		this.songs = songs;
	}
	public int getNumSongs()  {
		return songs.size();
	}
	
	@Override
	public String toString()  {
		return this.name;
	}
}
