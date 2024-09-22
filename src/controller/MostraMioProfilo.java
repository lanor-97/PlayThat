package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.UtenteCredenziali;
import persistence.UtenteProxy;

@SuppressWarnings("serial")
public class MostraMioProfilo extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		Object prova = session.getAttribute("utente");
		
		if(prova == null)  {
			//utente non loggato
			
			RequestDispatcher disp;
			disp = req.getRequestDispatcher("login.jsp");
			disp.forward(req, resp);
			
			return;
		}
		
		String username = ((Utente) prova).getUsername();
		
		UtenteProxy utenteproxy = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao().findByPrimaryKeyProxy(username);
		
		req.setAttribute("followers", utenteproxy.getFollowers());
		req.setAttribute("followingusers", utenteproxy.getFollowings());
		req.setAttribute("followingartists", utenteproxy.getFollowingArtists());
		
		RequestDispatcher disp;
		disp = req.getRequestDispatcher("myprofile.jsp");
		disp.forward(req, resp);
	}
}
