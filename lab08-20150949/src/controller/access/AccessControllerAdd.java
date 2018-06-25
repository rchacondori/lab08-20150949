package controller.access;

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
import model.entity.Access;
import model.entity.Resource;
import model.entity.Role;

@SuppressWarnings("serial")
public class AccessControllerAdd extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		boolean ans = Holder.Finder(this, request, response, uGoogle,true);
		if(ans){
			final PersistenceManager pm = PMF.get().getPersistenceManager();
			String query1 = "select from " + Resource.class.getName();
			String query2 = "select from " + Role.class.getName();

			try{
				List<Resource> resources = (List<Resource>) pm.newQuery(query1).execute();
				request.setAttribute("resources", resources);
				List<Role> roles = (List<Role>) pm.newQuery(query2).execute();
				request.setAttribute("roles", roles);
			}catch (Exception e) {
				System.out.println(e);
			}

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/Access/add.jsp");
			rd.forward(request, response);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Access a = new Access(new Long(request.getParameter("rolId")), new Long(request.getParameter("resourceId")));
			pm.makePersistent(a);
			response.sendRedirect("/access");
		}catch (Exception e) {
			System.out.println(e);
		} finally {
			pm.close();
		}
	}
}
