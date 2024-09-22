package controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Brano;
import model.Playlist;
import model.Utente;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.BranoDao;
import persistence.dao.PlaylistDao;

@SuppressWarnings("serial")
public class CercaBrano extends HttpServlet  {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Object prova = req.getSession().getAttribute("utente");
		String username = "";
		if(prova != null)  {
			username = ((Utente) prova).getUsername();
		}
		
		String value = req.getParameter("value");
		String parameter = req.getParameter("parameter");
		
		BranoDao branoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getBranoDao();
		Set<Brano> brani = new HashSet<Brano>();
		Set<Playlist> playlists = new HashSet<Playlist>();
		
		if(parameter.equals("Tutti i brani"))  {
			brani = branoDao.findAll();
		}
		else if(parameter.equals("Tutte le playlist"))  {
			playlists = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPlaylistDao().findAll();
		}
		else if(parameter.equals("Brano - titolo"))  {
			brani = branoDao.findByName(value);
		}
		else if(parameter.equals("Brano - album"))  {
			brani = branoDao.findByAlbum(value);
		}
		else if(parameter.equals("Brano - genere"))  {
			brani = branoDao.findByGenre(value);
		}
		else if(parameter.equals("Brano - autore"))  {
			brani = branoDao.findByAuthor(value);
		}
		
		if(brani.size() == 0)  {
			if(playlists.size() == 0)  {
				req.setAttribute("nothing", true);
			    
			    RequestDispatcher disp;
				disp = req.getRequestDispatcher("home");
				disp.forward(req, resp);
			}
			
			req.setAttribute("playlists", playlists);
			RequestDispatcher disp;
			disp = req.getRequestDispatcher("songs.jsp");
			disp.forward(req, resp);
		}
		else  {
			req.setAttribute("brani", brani);
			
			if(username != null && !username.equals(""))  {
				Utente utente = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao().findByPrimaryKey(username);
				PlaylistDao playlistDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPlaylistDao();
				req.setAttribute("utente_playlists", playlistDao.findByCreator(utente));
			}
			RequestDispatcher disp;
			disp = req.getRequestDispatcher("songs.jsp");
			disp.forward(req, resp);
		}
	}	
}
