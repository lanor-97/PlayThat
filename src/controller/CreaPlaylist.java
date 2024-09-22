package controller;

import java.io.IOException;
import java.io.PrintWriter;

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
public class CreaPlaylist  extends HttpServlet  {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String username = ((Utente) session.getAttribute("utente")).getUsername();
		String nomePlaylist = req.getParameter("name");
		
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		Utente creator = utenteDao.findByPrimaryKey(username);
		PlaylistDao playlistDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPlaylistDao();
		
	    resp.setCharacterEncoding("UTF-8");
	    PrintWriter out = resp.getWriter();
		
	    if(nomePlaylist.contains("ID: "))  {
			out.write("invalid_name");
			return;
		}
	    
		for(Playlist p : playlistDao.findByCreator(creator))  {
			if(p.getName().equals(nomePlaylist))  {
				//non può crearla perchè ha già un'altra playlist con nome uguale
				out.write("same_name");
				return;
			}
		}
		
		Playlist playlist = new Playlist(nomePlaylist, creator);
		playlistDao.save(playlist);

		out.write("success");
	}
}
