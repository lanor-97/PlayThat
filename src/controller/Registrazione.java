package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Utente;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.UtenteCredenziali;
import persistence.dao.UtenteDao;

@SuppressWarnings("serial")
public class Registrazione extends HttpServlet  {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		String email = req.getParameter("email");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		Utente utenteProva = utenteDao.findByPrimaryKey(username);
		req.setAttribute("wrong", false);
		
		if(utenteProva != null)  {
			req.setAttribute("wrong", true);
			RequestDispatcher disp;
			disp = req.getRequestDispatcher("register.jsp");
			disp.forward(req, resp);
		}
		
		Utente utente = new Utente(name, surname, username, email);
		utenteDao.save(utente);
		utenteDao.findByPrimaryKeyCredential(username).setPassword(password);
		
		RequestDispatcher disp;
		disp = req.getRequestDispatcher("/login");
		disp.forward(req, resp);
	} 
}
