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
public class DiventaPremium extends HttpServlet  {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Object prova = session.getAttribute("utente");
		
		if(prova == null)  {
			resp.getWriter().write("fail");
			return;
		}
		
		String username = ((Utente) prova).getUsername();
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		Utente utente = utenteDao.findByPrimaryKey(username);
		
		utenteDao.setPremium(utente, true);
		session.removeAttribute("premium");
		session.setAttribute("premium", true);

		resp.getWriter().write("success");
	}
}
