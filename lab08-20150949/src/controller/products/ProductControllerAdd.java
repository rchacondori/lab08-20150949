package controller.products;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import controller.Holder;
import controller.PMF;
import model.entity.Mercaderia;

@SuppressWarnings("serial")
public class ProductControllerAdd extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		boolean ans = Holder.Finder(this, request, response, uGoogle,true);
		if(ans){
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/Products/add.jsp");
			rd.forward(request, response);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		final PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			Mercaderia a = new Mercaderia(
					request.getParameter("name"),
					request.getParameter("medida"),
					request.getParameter("cantidad"),
					request.getParameter("unitario")
					);
			pm.makePersistent(a);
			response.sendRedirect("/products");
		}catch (Exception e) {
			System.out.println(e);
		} finally {
			pm.close();
		}
	}
}
