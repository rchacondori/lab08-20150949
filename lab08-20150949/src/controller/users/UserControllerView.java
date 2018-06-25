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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import controller.Holder;
import controller.PMF;
import model.entity.Resource;
import model.entity.Role;
import model.entity.User;

@SuppressWarnings("serial")
public class UserControllerView extends HttpServlet {

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		boolean ans = Holder.Finder(this, request, response, uGoogle,true);
		if(ans){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			User a;
			if( request.getParameter("userId") != null ){
				Key k = KeyFactory.createKey(User.class.getSimpleName(), new Long(request.getParameter("userId")).longValue());
				a = pm.getObjectById(User.class, k);
			}else{
				a = new User("", new Date(), true, new Long(0));
			}

			request.setAttribute("usuario", a);

			String query2 = "select from " + Role.class.getName();
			try{
				List<Role> roles = (List<Role>) pm.newQuery(query2).execute();
				request.setAttribute("roles", roles);
			}catch (Exception e) {
				System.out.println(e);
			}

			RequestDispatcher dispatcher =
					getServletContext().getRequestDispatcher("/WEB-INF/Views/Users/view.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key k = KeyFactory.createKey(User.class.getSimpleName(), new Long(request.getParameter("userId")).longValue());
		User a = pm.getObjectById(User.class, k);

		a.setMail(request.getParameter("mail"));
		a.setGenero(new Boolean(request.getParameter("genero")));
		a.setRolId(new Long(request.getParameter("rolId")));
		System.out.println(request.getParameter("state"));
		a.setStatus(new Boolean(request.getParameter("state")));

		response.sendRedirect("/users");
	}
}
