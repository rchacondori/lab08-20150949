package controller.products;

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
import model.entity.Mercaderia;
import model.entity.Resource;
import model.entity.User;
import model.entity.Access;;

@SuppressWarnings("serial")
public class ProductControllerIndex extends HttpServlet{

	@Override
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {						  
		/*
		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();

		if(uGoogle == null){
			RequestDispatcher dp = getServletContext().getRequestDispatcher("/WEB-INF/Views/Errors/deny1.jsp");
			dp.forward(req, resp);
		}else{
			PersistenceManager pm = PMF.get().getPersistenceManager();

			String query = "select from " + User.class.getName() + " where mail=='"+uGoogle.getEmail()+"' && status==true";

			List<User> uSearch = (List<User>) pm.newQuery(query).execute();
			if(uSearch.isEmpty()){
				RequestDispatcher dp = getServletContext().getRequestDispatcher("/WEB-INF/Views/Errors/deny2.jsp");
				dp.forward(req, resp);
			}else{
				String query2 = "select from " + Resource.class.getName() + " where url=='"+req.getServletPath() + "' && status==true";
				List<Resource> rSearch = (List<Resource>) pm.newQuery(query2).execute();
				if(rSearch.isEmpty()){
					RequestDispatcher dp = getServletContext().getRequestDispatcher("/WEB-INF/Views/Errors/deny3.jsp");
					dp.forward(req, resp);
				}else{
					String query3 = "select from " + Access.class.getName() + 
							" where roleId==" + uSearch.get(0).getRolId() +
							" && resourceId==" + rSearch.get(0).getId() +
							" && status==true";
					List<Access> aSearch = (List<Access>) pm.newQuery(query3).execute();
					if(aSearch.isEmpty()){
						RequestDispatcher dp = getServletContext().getRequestDispatcher("/WEB-INF/Views/Errors/deny4.jsp");
						dp.forward(req, resp);
					}else{
						String query4 = "select from " + Mercaderia.class.getName();
						List<Mercaderia> products = (List<Mercaderia>) pm.newQuery(query4).execute();
						req.setAttribute("productos", products);
						RequestDispatcher dp = getServletContext().getRequestDispatcher("/WEB-INF/Views/Products/index.jsp");
						dp.forward(req, resp);
					}
				}
			}
		 */
		com.google.appengine.api.users.User uGoogle = UserServiceFactory.getUserService().getCurrentUser();
		boolean ans = Holder.Finder(this, req, resp, uGoogle, false);
		if(ans){
			PersistenceManager pm = PMF.get().getPersistenceManager();
			String query4 = "select from " + Mercaderia.class.getName();
			List<Mercaderia> products = (List<Mercaderia>) pm.newQuery(query4).execute();
			req.setAttribute("productos", products);
			RequestDispatcher dp = getServletContext().getRequestDispatcher("/WEB-INF/Views/Products/index.jsp");
			dp.forward(req, resp);
		}
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}


