package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.UtenteDao;

@SuppressWarnings("serial")
public class ToggleFollow extends HttpServlet  {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		String sessionUsername = ((Utente) session.getAttribute("utente")).getUsername();
		String toggleFollowUsername = req.getParameter("username");
		boolean toFollow = Boolean.parseBoolean(req.getParameter("toFollow"));
		
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		Utente sessionUtente = utenteDao.findByPrimaryKey(sessionUsername);
		Utente followUtente = utenteDao.findByPrimaryKey(toggleFollowUsername);
		
		if(toFollow)  {
			utenteDao.addFollower(followUtente, sessionUtente);
		} else  {
			utenteDao.removeFollower(followUtente, sessionUtente);
		}
	}

}
