package controller.resources;

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

@SuppressWarnings("serial")
public class ResourceControllerAdd extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		boolean ans = Holder.Finder(this, request, response, uGoogle,true);
		if(ans){
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/Resources/add.jsp");
			rd.forward(request, response);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		final PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Resource a = new Resource(request.getParameter("url"));
			pm.makePersistent(a);
			response.sendRedirect("/resources");
		}catch (Exception e) {
			System.out.println("error");
			System.out.println(e);
		} finally {
			pm.close();
		}
	}
}
