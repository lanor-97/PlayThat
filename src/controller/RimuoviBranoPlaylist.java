package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Brano;
import model.Playlist;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.PlaylistDao;

@SuppressWarnings("serial")
public class RimuoviBranoPlaylist extends HttpServlet{
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id_playlist = req.getParameter("playlist_id");
		String id_brano = req.getParameter("song_id");		
		
		long idPlaylist = Long.parseLong(id_playlist);
		long idBrano = Long.parseLong(id_brano);
		
		PlaylistDao playlistDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPlaylistDao();
		Brano brano = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getBranoDao().findByPrimaryKey(idBrano);
		Playlist playlist = playlistDao.findByPrimaryKey(idPlaylist);
		
		if(brano == null || playlist == null)  {
			resp.getWriter().print("fail");
			return;
		}
		
		playlistDao.removeBrano(playlist, brano);
		resp.getWriter().print("success");
	}
}
