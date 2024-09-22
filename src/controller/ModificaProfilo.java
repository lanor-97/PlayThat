package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
public class ModificaProfilo extends HttpServlet  {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		String email = req.getParameter("email");
		String username = ((Utente) session.getAttribute("utente")).getUsername();
		
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		Utente utente = utenteDao.findByPrimaryKey(username);
		utente.setName(name);
		utente.setSurname(surname);
		utente.setEmail(email);
		utenteDao.update(utente);
		
		session.setAttribute("utente", utente);
		req.setAttribute("utente", utente);
		
		RequestDispatcher disp;
		disp = req.getRequestDispatcher("myprofile.jsp");
		disp.forward(req, resp);
	}
}
