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
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.UtenteProxy;

@SuppressWarnings("serial")
public class ProfiloUtente extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		session.getAttribute("utente");
		
		String username = req.getQueryString();
		String usernameLoggato = "";
		
		username = URLDecoder.decode(username, StandardCharsets.UTF_8.toString());
		
		if(session.getAttribute("utente") != null)  {
			usernameLoggato = ((Utente) session.getAttribute("utente")).getUsername();
			
			if(usernameLoggato.equals(username))  {
				RequestDispatcher disp;
				disp = req.getRequestDispatcher("/myprofile");
				disp.forward(req, resp);
				return;
			}
		}
		
		UtenteProxy utenteproxy = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao().findByPrimaryKeyProxy(username);
		
		if(utenteproxy == null)  {
			req.setAttribute("no_user", true);
			RequestDispatcher disp;
			disp = req.getRequestDispatcher("home");
			disp.forward(req, resp);
			
			return;
		}
		req.setAttribute("followers", utenteproxy.getFollowers());
		req.setAttribute("followingusers", utenteproxy.getFollowings());
		req.setAttribute("followingartists", utenteproxy.getFollowingArtists());
		
		req.setAttribute("utente_cercato", utenteproxy);
		
		if(session.getAttribute("utente") != null)  {
			
			req.setAttribute("following_already", utenteproxy.isFollowedByUsername(usernameLoggato));
		}
		
		
		RequestDispatcher disp;
		disp = req.getRequestDispatcher("user-profile.jsp");
		disp.forward(req, resp);
	}
}
