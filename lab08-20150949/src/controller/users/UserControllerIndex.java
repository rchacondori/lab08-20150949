package controller.users;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import controller.Holder;
import controller.PMF;
import model.entity.Role;
import model.entity.User;

@SuppressWarnings("Serial")
public class UserControllerIndex extends HttpServlet{

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		final PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "select from " + User.class.getName();
		String query2 = "select from " + Role.class.getName();

		try{
			List<User> users = (List<User>) pm.newQuery(query).execute();
			request.setAttribute("usuarios", users);
			List<Role> roles = (List<Role>) pm.newQuery(query2).execute();
			request.setAttribute("roles", roles);
			RequestDispatcher despachador = request.getRequestDispatcher("/WEB-INF/Views/Users/index.jsp");
			despachador .forward(request, response);
		}catch(Exception e){
			System.out.println(e);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
