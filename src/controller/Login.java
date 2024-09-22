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

@SuppressWarnings("serial")
public class Login extends HttpServlet  {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		System.out.println(username + " is trying login");
		UtenteCredenziali utentecred = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao().findByPrimaryKeyCredential(username);
		Utente utente = null;
		
		req.setAttribute("wrong", true);
		
		if(utentecred != null)  {
			
			if(!utentecred.getPassword().equals(password))  {
				
				RequestDispatcher disp;
				disp = req.getRequestDispatcher("login.jsp");
				disp.forward(req, resp);
			}
			else  {
				
				req.setAttribute("wrong", false);
				req.setAttribute("success", true);
				utente = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao().findByPrimaryKey(username);
				session.setAttribute("loggato", true);
				req.setAttribute("loggato", true);
				
				session.setAttribute("utente", utente);
				req.setAttribute("utente", utente);
				RequestDispatcher disp;
				disp = req.getRequestDispatcher("login.jsp");
				disp.forward(req, resp);
			}
		}
		else  {
			
			RequestDispatcher disp;
			disp = req.getRequestDispatcher("login.jsp");
			disp.forward(req, resp);
		}
	} 
}
