package controller.roles;

import java.io.IOException;

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
import model.entity.Role;

@SuppressWarnings("serial")
public class RolControllerView extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		boolean ans = Holder.Finder(this, request, response, uGoogle,true);
		if(ans){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Role a;
			if( request.getParameter("rolId") != null ){
				Key k = KeyFactory.createKey(Role.class.getSimpleName(), new Long(request.getParameter("rolId")).longValue());
				a = pm.getObjectById(Role.class, k);
			}else{
				a = new Role("");
			}

			request.setAttribute("rol", a);

			RequestDispatcher dispatcher =
					getServletContext().getRequestDispatcher("/WEB-INF/Views/Roles/view.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key k = KeyFactory.createKey(Role.class.getSimpleName(), new Long(request.getParameter("rolId")).longValue());
		Role a = pm.getObjectById(Role.class, k);

		a.setName(request.getParameter("name"));
		a.setStatus(new Boolean(request.getParameter("state")));

		response.sendRedirect("/roles");
	}
}
