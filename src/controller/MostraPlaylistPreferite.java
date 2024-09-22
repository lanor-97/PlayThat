package controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.Playlist;
import model.Utente;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.UtenteDao;

@SuppressWarnings("serial")
public class MostraPlaylistPreferite extends HttpServlet  {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		String username = ((Utente) session.getAttribute("utente")).getUsername();
		
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		Utente creator = utenteDao.findByPrimaryKey(username);
		
		Set<Playlist> myplaylists = utenteDao.findFavouritePlaylists(creator);
		
		Gson gson = new Gson();
	    String json = gson.toJson(myplaylists);
	    resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    resp.getWriter().write(json);
	}	
}
