package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Playlist;
import model.Utente;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.UtenteDao;

@SuppressWarnings("serial")
public class AggiungiPlaylistPreferita extends HttpServlet  {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String idString = req.getParameter("playlist");
		
		long playlistID = Long.parseLong(idString);
		
		Object prova = req.getSession().getAttribute("utente");
		if(prova == null)  {
			resp.getWriter().print("fail");
			return;
		}
		
		String username = ((Utente) prova).getUsername();
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		Utente utente = utenteDao.findByPrimaryKey(username);
		Playlist playlist = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPlaylistDao().findByPrimaryKey(playlistID);
		utenteDao.addFavouritePlaylist(utente, playlist);
		
		resp.getWriter().print("success");
	}
}
