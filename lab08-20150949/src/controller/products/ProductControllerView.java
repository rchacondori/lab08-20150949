package controller.products;

import java.io.IOException;
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
import model.entity.Mercaderia;

@SuppressWarnings("serial")
public class ProductControllerView extends HttpServlet {

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		boolean ans = Holder.Finder(this, request, response, uGoogle,true);
		if(ans){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Mercaderia a;
			if( request.getParameter("mercaderiaId") != null ){
				Key k = KeyFactory.createKey(Mercaderia.class.getSimpleName(), new Long(request.getParameter("mercaderiaId")).longValue());
				a = pm.getObjectById(Mercaderia.class, k);
			}else{
				a = new Mercaderia("", "", "0", "0");
			}

			request.setAttribute("producto", a);

			RequestDispatcher dispatcher =
					getServletContext().getRequestDispatcher("/WEB-INF/Views/Products/view.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key k = KeyFactory.createKey(Mercaderia.class.getSimpleName(), new Long(request.getParameter("mercaderiaId")).longValue());
		Mercaderia a = pm.getObjectById(Mercaderia.class, k);

		a.setName(request.getParameter("name"));
		a.setMedida(request.getParameter("medida"));
		a.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
		a.setCostoU(Double.parseDouble(request.getParameter("unitario")));

		response.sendRedirect("/products");
	}

}
