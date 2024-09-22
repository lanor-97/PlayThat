package controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;
import persistence.ArtistaProxy;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;

@SuppressWarnings("serial")
public class ProfiloArtista extends HttpServlet  {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		session.getAttribute("utente");
		
		String name = req.getQueryString();
		String usernameLoggato = "";
		
		name = URLDecoder.decode(name, StandardCharsets.UTF_8.toString());
		
		if(session.getAttribute("utente") != null)  {
			usernameLoggato = ((Utente) session.getAttribute("utente")).getUsername();
		}
		
		ArtistaProxy artista = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getArtistaDao().findByPrimaryKeyProxy(name);
		
		if(artista == null)  {
			req.setAttribute("no_artist", true);
			
			RequestDispatcher disp;
			disp = req.getRequestDispatcher("home");
			disp.forward(req, resp);
			
			return;
		}
		req.setAttribute("followers", artista.getFollowers());
		
		req.setAttribute("artista_cercato", artista);
		
		if(session.getAttribute("utente") != null)  {
			
			req.setAttribute("following_already", artista.isFollowedByUsername(usernameLoggato));
		}
		
		
		RequestDispatcher disp;
		disp = req.getRequestDispatcher("artist-profile.jsp");
		disp.forward(req, resp);
	}
}
