package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Brano;
import model.Utente;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.UtenteDao;

@SuppressWarnings("serial")
public class RimuoviBranoPreferito extends HttpServlet  {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id_brano = req.getParameter("name_id");
		
		id_brano = id_brano.substring(id_brano.indexOf("ID: ") + 4);
		id_brano = id_brano.substring(0, id_brano.indexOf(")"));
		int id = Integer.parseInt(id_brano);
		
		HttpSession session = req.getSession();
		String username = ((Utente) session.getAttribute("utente")).getUsername();
		
		Brano brano = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getBranoDao().findByPrimaryKey(id);
		
		if(brano == null)  {
			resp.getWriter().print("fail");
			return;
		}
		
		UtenteDao utenteDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao();
		Utente utente = utenteDao.findByPrimaryKey(username);
		
		utenteDao.removeFavouriteSong(utente, brano);
		
		resp.getWriter().print("success");
	}
}
