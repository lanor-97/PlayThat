package model;

import java.util.HashSet;
import java.util.Set;

public class Utente {

	private String name;
	private String surname;
	private String username;
	private String email;
	private boolean premium;

	private Set<Utente> followers;		//utenti che lo seguono
	private Set<Utente> following;		//utenti seguiti
	
	private int numFollowers;
	private int numFollowingUsers;
	private int numFollowingArtists;
	

	public int getNumFollowingUsers() {
		return numFollowingUsers;
	}

	public void setNumFollowingUsers(int numFollowingUsers) {
		this.numFollowingUsers = numFollowingUsers;
	}

	public int getNumFollowingArtists() {
		return numFollowingArtists;
	}

	public void setNumFollowingArtists(int numFollowingArtists) {
		this.numFollowingArtists = numFollowingArtists;
	}
	
	public int getNumFollowers()  {
		return numFollowers;
	}

	public void setNumFollowers(int numFollowers) {
		this.numFollowers = numFollowers;
	}

	public Utente()  {
		followers = new HashSet<Utente>();
		following = new HashSet<Utente>();
	}
	
	public Utente(String name, String surname, String username, String email)  {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		followers = new HashSet<Utente>();
		following = new HashSet<Utente>();
	}

	public Utente(String name, String surname, String username, String email, Set<Utente> followers, Set<Utente> following) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.followers = followers;
		this.following = following;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Utente> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<Utente> followers) {
		this.followers = followers;
	}

	public Set<Utente> getFollowing() {
		return following;
	}

	public void setFollowing(Set<Utente> following) {
		this.following = following;
	}
	
	public int getNumFollowing()  {
		return following.size();
	}
	
	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	
	@Override
	public String toString()  {
		return this.name + " " + this.surname;
	}
}
