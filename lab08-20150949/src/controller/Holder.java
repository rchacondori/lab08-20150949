package controller;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entity.Access;
import model.entity.Resource;
import model.entity.User;

public class Holder {
	
	static String deny1 = "/WEB-INF/Views/Errors/deny1.jsp";
	static String deny2 = "/WEB-INF/Views/Errors/deny2.jsp";
	static String deny3 = "/WEB-INF/Views/Errors/deny3.jsp";
	static String deny4 = "/WEB-INF/Views/Errors/deny4.jsp";
	static String deny5 = "/WEB-INF/Views/Errors/deny5.jsp";
	
	static String deny12 = "/WEB-INF/Views/Errors/2deny1.jsp";
	static String deny22 = "/WEB-INF/Views/Errors/2deny2.jsp";
	static String deny32 = "/WEB-INF/Views/Errors/2deny3.jsp";
	static String deny42 = "/WEB-INF/Views/Errors/2deny4.jsp";
	static String deny52 = "/WEB-INF/Views/Errors/2deny5.jsp";
	
	public static boolean Finder(HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp, com.google.appengine.api.users.User uGoogle, boolean flag) throws ServletException, IOException{
		
		if(uGoogle == null){
			RequestDispatcher dp = servlet.getServletContext().getRequestDispatcher(flag?deny12:deny1);
			dp.forward(req, resp);
		}else{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			
			String query = "select from " + User.class.getName() + " where mail=='"+uGoogle.getEmail()+"' && status==true";
			
			List<User> uSearch = (List<User>) pm.newQuery(query).execute();
			if(uSearch.isEmpty()){
				RequestDispatcher dp = servlet.getServletContext().getRequestDispatcher(flag?deny22:deny2);
				dp.forward(req, resp);
			}else{
				String query2 = "select from " + Resource.class.getName() + " where url=='"+req.getServletPath() + "' && status==true";
				List<Resource> rSearch = (List<Resource>) pm.newQuery(query2).execute();
				if(rSearch.isEmpty()){
					RequestDispatcher dp = servlet.getServletContext().getRequestDispatcher(flag?deny32:deny3);
					dp.forward(req, resp);
				}else{
					String query3 = "select from " + Access.class.getName() + 
							" where roleId==" + uSearch.get(0).getRolId() +
							" && resourceId==" + rSearch.get(0).getId() +
							" && status==true";
					List<Access> aSearch = (List<Access>) pm.newQuery(query3).execute();
					if(aSearch.isEmpty()){
						RequestDispatcher dp = servlet.getServletContext().getRequestDispatcher(flag?deny42:deny4);
						dp.forward(req, resp);
					}else{
						return true;
					}
				}
			}
			
		}
		return false;
	}
	
}
