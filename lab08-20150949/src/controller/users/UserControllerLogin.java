package controller.users;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entity.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import controller.PMF;


@SuppressWarnings("serial")
public class UserControllerLogin extends HttpServlet{
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		UserService us = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User user = us.getCurrentUser();
		if(user == null){
			response.sendRedirect(us.createLoginURL("/users/login"));
		}else{
			
			PersistenceManager pm = PMF.get().getPersistenceManager();
			String query = "select from " + User.class.getName() + " where mail=='"+user.getEmail()+"' && status==true";
			
			List<User> uSearch = (List<User>) pm.newQuery(query).execute();
			
			if(uSearch.isEmpty()){
				response.sendRedirect(us.createLoginURL("/users/login"));
			}else{
				request.setAttribute("user", uSearch.get(0));
				RequestDispatcher dp = getServletContext().getRequestDispatcher("/WEB-INF/Views/Users/login.jsp");
				dp.forward(request, response);
			}
			
		}
		
		
	}
	
}
