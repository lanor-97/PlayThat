package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Brano;
import model.Playlist;
import model.Utente;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.PlaylistDao;
import persistence.dao.UtenteDao;

@SuppressWarnings("serial")
public class AggiungiBranoLibreria extends HttpServlet  {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String where = req.getParameter("destination");
		String which = req.getParameter("playlist");
		String idString = req.getParameter("song");
		
		long branoID = Long.parseLong(idString);
		
		Object prova = req.getSession().getAttribute("utente");
		if(prova == null)  {
			resp.getWriter().print("fail");
			return;
		}
		
		String username = ((Utente) prova).getUsername();
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		Utente utente = utenteDao.findByPrimaryKey(username);
		Brano brano = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getBranoDao().findByPrimaryKey(branoID);
		
		if(where.equals("BraniPreferiti"))  {
			utenteDao.addFavouriteSong(utente, brano);
			resp.getWriter().print("success");
			return;
		} else if(where.equals("Playlist") && which != null && !which.equals(""))  {
			PlaylistDao playlistDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPlaylistDao();
			
			Playlist playlist = null;
			for(Playlist p : playlistDao.findByCreator(utente))  {
				if(p.getName().equals(which))  {
					playlist = p;
					break;
				}
			}
			
			if(playlist != null)  {
				playlistDao.addBrano(playlist, brano);
				resp.getWriter().print("success");
				return;
			}
			
			resp.getWriter().print("fail");
		}
	}
}
