package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Artista;
import model.Utente;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.UtenteProxy;

@SuppressWarnings("serial")
public class Homepage extends HttpServlet  {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Object prova = req.getSession().getAttribute("utente");
		
		if(prova == null)  {
			RequestDispatcher disp;
			disp = req.getRequestDispatcher("index.jsp");
			disp.forward(req, resp);
			return;
		}
		
		String username = ((Utente) prova).getUsername();
		UtenteProxy utenteProxy = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getUtenteDao().findByPrimaryKeyProxy(username);
		
		List<List<Object>> attivit‡ = utenteProxy.getAttivit‡Recenti();
		
		List<Object> actors = attivit‡.get(0);
		List<String> descriptions = (ArrayList) attivit‡.get(1);
		List<Object> subjects = attivit‡.get(2);
		
		ArrayList<String> feeds = new ArrayList<String>();
		
		String temp = "";
		for(int i = 0; i < actors.size(); i++)  {
			//con questo if capisco se l'attore Ë artista o utente
			temp = "";
			
			if(descriptions.get(i).equals("new_brano"))  {
				//artista
				
				Artista actor = (Artista) actors.get(i);
				temp += "E' stato aggiunto il brano intitolato " + subjects.get(i).toString() + " dell'artista <a href=\"artist?" + actor.getName() + "\">" + actor.toString() + "</a>";
			} else  {
				
				Utente actor = (Utente) actors.get(i);
				
				switch(descriptions.get(i))  {
				case "new_follower_utente":
					
					Utente subject = (Utente) subjects.get(i);
					temp += "<a href=\"user?" + actor.getUsername() + "\">" + actor.toString() + "</a> ha un nuovo follower: <a href=\"user?" + subject.getUsername() + "\">" + subject.toString() + "</a>";
					break;
					
				case "new_follow_utente":
					
					subject = (Utente) subjects.get(i);
					temp += "<a href=\"user?" + actor.getUsername() + "\">" + actor.toString() + "</a> ha iniziato a seguire <a href=\"user?" + subject.getUsername() + "\">" + subject.toString() + "</a>";
					break;
					
				case "new_follow_artista":
					
					Artista subject2 = (Artista) subjects.get(i);
					temp += "<a href=\"user?" + actor.getUsername() + "\">" + actor.toString() + "</a> ha iniziato a seguire <a href=\"artist?" + subject2.toString() + "\">" + subject2.toString() + "</a>";
					break;
					
				case "new_playlist":
					
					temp += "<a href=\"user?" + actor.getUsername() + "\">" + actor.toString() + "</a> ha creato una playlist chiamata <i>" + subjects.get(i).toString() + "</i>";
					break;
					
				case "new_favourite_brano":
					
					temp += "<a href=\"user?" + actor.getUsername() + "\">" + actor.toString() + "</a> ha aggiunto il brano <i>" + subjects.get(i).toString() + "</i> ai preferiti";
					break;
					
				case "new_favourite_playlist":
					
					temp += "<a href=\"user?" + actor.getUsername() + "\">" + actor.toString() + "</a> ha aggiunto la playlist <i>" + subjects.get(i).toString() + "</i> alle preferite";
					break;
				}
			}
			
			feeds.add(temp);
		}
		
		req.setAttribute("feeds", feeds);
		
		RequestDispatcher disp;
		disp = req.getRequestDispatcher("index.jsp");
		disp.forward(req, resp);
	}
}
