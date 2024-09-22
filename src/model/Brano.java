package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Brano {

	private long id;
	private String name;
	private Artista author;
	private String genre;
	private String album;
	private int releaseDate;
	
	
	public Brano(String name, Artista author, String genre, String album, int releaseDate)  {
		this.name = name;
		this.author = author;
		this.genre = genre;
		this.album = album;
		this.releaseDate = releaseDate;
	}
	
	public Brano(long id, String name, Artista author, String genre, String album, int releaseDate) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.genre = genre;
		this.album = album;
		this.releaseDate = releaseDate;
	}
	public long getId()  {
		return id;
	}
	public void setId(long id)  {
		this.id = id;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Artista getAuthor() {
		return author;
	}
	public void setAuthor(Artista author) {
		this.author = author;
	}
	public int getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(int releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	
	@Override
	public String toString()  {
		return this.name;
	}
}
