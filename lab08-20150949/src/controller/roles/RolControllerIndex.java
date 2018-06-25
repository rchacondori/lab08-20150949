package controller.roles;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import controller.Holder;
import controller.PMF;
import model.entity.Role;

@SuppressWarnings("serial")
public class RolControllerIndex extends HttpServlet{

	@Override
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {						  		
		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		boolean ans = Holder.Finder(this, req, resp, uGoogle,false);
		if(ans){
			final PersistenceManager pm = PMF.get().getPersistenceManager();
			String query = "select from " + Role.class.getName();

			try{
				List<Role> roles = (List<Role>) pm.newQuery(query).execute();
				req.setAttribute("roles", roles);
				RequestDispatcher despachador = req.getRequestDispatcher("/WEB-INF/Views/Roles/index.jsp");
				despachador .forward(req, resp);
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}


