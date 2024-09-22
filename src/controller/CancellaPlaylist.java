package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Playlist;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.PlaylistDao;

@SuppressWarnings("serial")
public class CancellaPlaylist extends HttpServlet  {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id_playlist = req.getParameter("name_id");
		
		id_playlist = id_playlist.substring(id_playlist.indexOf("ID: ") + 4);
		id_playlist = id_playlist.substring(0, id_playlist.indexOf(")"));
		int id = Integer.parseInt(id_playlist);
		
		PlaylistDao playlistDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPlaylistDao();
		Playlist playlist = playlistDao.findByPrimaryKey(id);
		
		playlistDao.delete(playlist);
		
		resp.getWriter().print("success");
	}
}
