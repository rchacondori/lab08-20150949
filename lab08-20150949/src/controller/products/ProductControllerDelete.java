package controller.products;

import java.io.IOException;


import javax.jdo.PersistenceManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserServiceFactory;

import controller.Holder;
import controller.PMF;
import model.entity.Mercaderia;

@SuppressWarnings("serial")
public class ProductControllerDelete extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		boolean ans = Holder.Finder(this, request, response, uGoogle,true);
		if(ans){
			PersistenceManager pm = PMF.get().getPersistenceManager();

			Key k = KeyFactory.createKey(Mercaderia.class.getSimpleName(), new Long(request.getParameter("mercaderiaId")).longValue());
			Mercaderia a = pm.getObjectById(Mercaderia.class, k);
			pm.deletePersistent(a);
			response.sendRedirect("/products");	
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}

}

