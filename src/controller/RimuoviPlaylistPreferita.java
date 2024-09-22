package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Playlist;
import model.Utente;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.UtenteDao;

@SuppressWarnings("serial")
public class RimuoviPlaylistPreferita extends HttpServlet  {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id_playlist = req.getParameter("name_id");
		
		id_playlist = id_playlist.substring(id_playlist.indexOf("ID: ") + 4);
		id_playlist = id_playlist.substring(0, id_playlist.indexOf(")"));
		int id = Integer.parseInt(id_playlist);
		
		HttpSession session = req.getSession();
		String username = ((Utente) session.getAttribute("utente")).getUsername();
		
		Playlist playlist = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPlaylistDao().findByPrimaryKey(id);
		
		if(playlist == null)  {
			resp.getWriter().print("fail");
			return;
		}
		
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		Utente utente = utenteDao.findByPrimaryKey(username);
		
		utenteDao.removeFavouritePlaylist(utente, playlist);
		
		resp.getWriter().print("success");
	}
}
