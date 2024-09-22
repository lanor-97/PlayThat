package controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Playlist;
import model.Utente;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.PlaylistDao;
import persistence.dao.UtenteDao;

@SuppressWarnings("serial")
public class MiaLibreria extends HttpServlet  {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		Object prova = session.getAttribute("utente");
		
		if(prova == null)  {
			//utente non loggato
			
			RequestDispatcher disp;
			disp = req.getRequestDispatcher("login.jsp");
			disp.forward(req, resp);
			
			return;
		}
		
		String username = ((Utente) prova).getUsername();
		
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		Utente creator = utenteDao.findByPrimaryKey(username);
		PlaylistDao playlistDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPlaylistDao();
		Set<Playlist> myplaylists = playlistDao.findByCreator(creator);
		Set<Playlist> myfavouriteplaylists = utenteDao.findFavouritePlaylists(creator);
		
		req.setAttribute("my_playlists", myplaylists);
		req.setAttribute("my_favourite_playlists", myfavouriteplaylists);
		
		RequestDispatcher disp;
		disp = req.getRequestDispatcher("library.jsp");
		disp.forward(req, resp);
	}
}
