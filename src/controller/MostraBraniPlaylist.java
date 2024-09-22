package controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Brano;
import persistence.DAOFactory;
import persistence.PlaylistProxy;
import persistence.PostgresDAOFactory;

@SuppressWarnings("serial")
public class MostraBraniPlaylist extends HttpServlet  {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String idString = req.getParameter("playlist_id");
		long id = Integer.parseInt(idString);
		
		PlaylistProxy playlist = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPlaylistDao().findByPrimaryKeyProxy(id);
		
		if(playlist == null)  {
			resp.getWriter().write("noplaylist");
		}
		
		Set<Brano> mysongs = playlist.getSongs(playlist);
		
		Gson gson = new Gson();
	    String json = gson.toJson(mysongs);
	    resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    resp.getWriter().write(json);
	}
}
