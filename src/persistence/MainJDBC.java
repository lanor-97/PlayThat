package persistence;

import model.Artista;
import model.Brano;
import model.Playlist;
import model.Utente;
import persistence.dao.ArtistaDao;
import persistence.dao.BranoDao;
import persistence.dao.PlaylistDao;
import persistence.dao.UtenteDao;

public class MainJDBC {

	public static void main(String args[]) {

		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
		UtilDao util = factory.getUtilDao();
		
		util.dropViews();
		util.dropFunctionsAndTriggers();
		util.dropDatabase();
		util.createDatabase();
		util.createFunctionsAndTriggers();
		util.createViews();

		UtenteDao utenteDao = factory.getUtenteDao();
		ArtistaDao artistaDao = factory.getArtistaDao();
		PlaylistDao playlistDao = factory.getPlaylistDao();
		BranoDao branoDao = factory.getBranoDao();
		
		//inserimento dati iniziali
		Artista queen = new Artista("Queen", "England", 1970);
		Artista thebeatles = new Artista("The Beatles", "England", 1960);
		Artista acdc = new Artista("AC-DC", "Australia", 1973);
		Artista eminem = new Artista("Eminem", "U.S.A.", 1992);
		
		Utente user1 = new Utente("Antonio", "Carto", "user1", "antonio.carto@mail.it");
		Utente user2 = new Utente("Giovanni", "Lolo", "user2", "giovanni_lolo@mail.it");
		
		Brano help = new Brano("Help!", thebeatles, "Rock", "Help!", 1965);
		Brano dontstopmenow = new Brano("Don't stop me now", queen, "Rock", "Jazz", 1979);
		Brano rapgod = new Brano("Rap God", eminem, "Hip hop", "", 2013);
		Brano innuendo = new Brano("Innuendo", queen, "Rock", "Innuendo", 1991);
		Brano cometogether = new Brano("Come together", thebeatles, "Rock", "Abbey Road", 1969);
		Brano thunderstruck = new Brano("Thunderstruck", acdc, "Metal", "The Razors Edge", 1990);
		Brano letitbe = new Brano("Let it be", thebeatles, "Rock", "", 1970);
		Brano heyjude = new Brano("Hey Jude", thebeatles, "Rock", "", 1968);
		Brano yesterday = new Brano("Yesterday", thebeatles, "Rock", "Help!", 1965);
		Brano dontletmedown = new Brano("Dont let me down", thebeatles, "Rock", "", 1969);
		Brano bohemian = new Brano("Bohemian Rhapsody", queen, "Rock", "A night at the Opera", 1975);
		Brano rockyou = new Brano("We will rock you", queen, "Rock", "News of the World", 1977);
		Brano anotherone = new Brano("Another one bites the dust", queen, "Rock", "The Game", 1980);
		Brano backinblack = new Brano("Back in black", acdc, "Metal", "Back in Black", 1980);
		Brano loseyourself = new Brano("Lose yourself", eminem, "Hip hop", "", 2002);
		
		Playlist play1 = new Playlist("Play", user1);
		Playlist play2 = new Playlist("Play2", user1);
		Playlist play3 = new Playlist("Play3", user1);
		
		utenteDao.save(user1);
		utenteDao.save(user2);
		
		utenteDao.findByPrimaryKeyCredential(user1.getUsername()).setPassword("psw1");
		utenteDao.findByPrimaryKeyCredential(user2.getUsername()).setPassword("psw2");
		
		artistaDao.save(queen);
		artistaDao.save(thebeatles);
		artistaDao.save(acdc);
		artistaDao.save(eminem);
		
		branoDao.save(help);
		branoDao.save(dontstopmenow);
		branoDao.save(rapgod);
		branoDao.save(innuendo);
		branoDao.save(cometogether);
		branoDao.save(thunderstruck);
		branoDao.save(letitbe);
		branoDao.save(heyjude);
		branoDao.save(yesterday);
		branoDao.save(dontletmedown);
		branoDao.save(bohemian);
		branoDao.save(rockyou);
		branoDao.save(anotherone);
		branoDao.save(backinblack);
		branoDao.save(loseyourself);
		
		utenteDao.addFollower(user1, user2);
		utenteDao.addFollower(user2, user1);
		artistaDao.addFollower(eminem, user2);
		
		playlistDao.save(play1);
		playlistDao.save(play2);
		playlistDao.save(play3);
	}
}
