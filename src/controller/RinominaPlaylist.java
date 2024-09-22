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
public class RinominaPlaylist extends HttpServlet  {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id_playlist = req.getParameter("oldName");
		String newName = req.getParameter("newName");
		id_playlist = id_playlist.substring(id_playlist.indexOf("ID: ") + 4);
		id_playlist = id_playlist.substring(0, id_playlist.indexOf(")"));
		int id = Integer.parseInt(id_playlist);
		
		HttpSession session = req.getSession();
		String username = ((Utente) session.getAttribute("utente")).getUsername();
		
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		Utente creator = utenteDao.findByPrimaryKey(username);
		
		PrintWriter out = resp.getWriter();
		
		//non può contenere ID: nel nome
		if(newName.contains("ID: "))  {
			out.write("invalid_name");
			return;
		}
		
		PlaylistDao playlistDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPlaylistDao();
		
		//non può esserci un'altra playlist con lo stesso nome
		for(Playlist p : playlistDao.findByCreator(creator))  {
			if(p.getName().equals(newName))  {
				out.write("same_name");
				return;
			}
		}
		
		Playlist playlist = playlistDao.findByPrimaryKey(id);
		playlist.setName(newName);
		playlistDao.update(playlist);
		
		out.write("success");
	}
}
