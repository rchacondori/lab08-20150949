package controller.access;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import controller.Holder;
import controller.PMF;
import model.entity.Access;
import model.entity.Resource;
import model.entity.Role;

@SuppressWarnings("serial")
public class AccessControllerView extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		boolean ans = Holder.Finder(this, request, response, uGoogle,true);
		if(ans){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Access a;
			if( request.getParameter("accessId") != null ){
				Key k = KeyFactory.createKey(Access.class.getSimpleName(), new Long(request.getParameter("accessId")).longValue());
				a = pm.getObjectById(Access.class, k);
			}else{
				a = new Access(new Long(0), new Long(0));
			}

			request.setAttribute("access", a);

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

			RequestDispatcher dispatcher =
					getServletContext().getRequestDispatcher("/WEB-INF/Views/Access/view.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key k = KeyFactory.createKey(Access.class.getSimpleName(), new Long(request.getParameter("accessId")).longValue());
		Access a = pm.getObjectById(Access.class, k);

		a.setRoleId(new Long(request.getParameter("rolId")));
		a.setResourceId(new Long(request.getParameter("resourceId")));
		a.setStatus(new Boolean(request.getParameter("state")));

		response.sendRedirect("/access");
	}
}
