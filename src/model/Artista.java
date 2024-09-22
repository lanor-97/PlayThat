package model;

import java.util.HashSet;
import java.util.Set;

public class Artista {
	
	private String name;
	private String country;
	private int startDate;
	private Set<Utente> followers;

	private int numFollowers;
	
	
	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}
	public void setNumFollowers(int numFollowers) {
		this.numFollowers = numFollowers;
	}

	public Artista()  {
		followers = new HashSet<Utente>();
	}
	
	public Artista(String name, String country, int startDate)  {
		this.name = name;
		this.country = country;
		this.startDate = startDate;
		this.followers = new HashSet<Utente>();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Set<Utente> getFollowers() {
		return followers;
	}
	public int getNumFollowers()  {
		return numFollowers;
	}
	public void setFollowers(HashSet<Utente> followers) {
		this.followers = followers;
	}
	
	@Override
	public String toString()  {
		return this.name;
	}
}
