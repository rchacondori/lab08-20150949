package controller.users;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import controller.Holder;
import controller.PMF;
import model.entity.Resource;
import model.entity.Role;
import model.entity.User;

@SuppressWarnings("serial")
public class UserControllerAdd extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		boolean ans = Holder.Finder(this, request, response, uGoogle,true);
		if(ans){
			final PersistenceManager pm = PMF.get().getPersistenceManager();
			String query2 = "select from " + Role.class.getName();
			try{
				List<Role> roles = (List<Role>) pm.newQuery(query2).execute();
				request.setAttribute("roles", roles);
			}catch (Exception e) {
				System.out.println(e);
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/Users/add.jsp");
			rd.forward(request, response);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			User a = new User(
					request.getParameter("mail"),
					new Date(),
					new Boolean(request.getParameter("genero")),
					new Long(request.getParameter("rolId")));
			pm.makePersistent(a);
			response.sendRedirect("/users");
		}catch (Exception e) {
			System.out.println("error");
			System.out.println(e);
		} finally {
			pm.close();
		}
	}
}
