package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Artista;
import model.Utente;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.ArtistaDao;
import persistence.dao.UtenteDao;

@SuppressWarnings("serial")
public class ToggleFollowArtista extends HttpServlet  {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		String sessionUsername = ((Utente) session.getAttribute("utente")).getUsername();
		String artist = req.getParameter("name");
		boolean toFollow = Boolean.parseBoolean(req.getParameter("toFollow"));
		
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		ArtistaDao artistaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getArtistaDao();
		Utente sessionUtente = utenteDao.findByPrimaryKey(sessionUsername);
		Artista artista = artistaDao.findByPrimaryKey(artist);
		
		if(toFollow)  {
			artistaDao.addFollower(artista, sessionUtente);
		} else  {
			artistaDao.removeFollower(artista, sessionUtente);
		}
	}

}
