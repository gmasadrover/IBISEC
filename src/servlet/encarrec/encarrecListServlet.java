package servlet.encarrec;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;

import bean.Actuacio;
import bean.InformeActuacio;
import bean.Tasca;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
import core.ControlPageCore;
import core.InformeCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class encarrecListServlet
 */
@WebServlet("/encarrecsList")
public class encarrecListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public encarrecListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
    	
    	if (usuari == null){
 		   response.sendRedirect(request.getContextPath() + "/preLogin");
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.tasques_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{
 	   		
	 	    String filtrar = request.getParameter("filtrar"); 	   		
	        String errorString = null;	       
	        List<User> llistaUsuaris = new ArrayList<User>();
	        List<InformeActuacio> obresAssignades = new ArrayList<InformeActuacio>();
	        String[] usuarisValues = null; 	  
	        String usuarisSeleccionats = String.valueOf(usuari.getIdUsuari());
	        boolean veureTotes = usuari.getDepartament().equals("gerencia");
	   	    try {
	   	    	
	   	    	if (filtrar != null) {
	        		usuarisValues = request.getParameterValues("idUsuari");
	        		if (usuarisValues != null) {
	        			usuarisSeleccionats = "";
	     		        for(int i=0; i<usuarisValues.length; i++) { 		        	
	     		        	String idUsuari = usuarisValues[i];
	     		        	usuarisSeleccionats += idUsuari + "#";
	     		        	if (NumberUtils.isNumber(idUsuari)) {
	    	        			obresAssignades = InformeCore.getObresUsuari(conn, Integer.parseInt(idUsuari));  	    	        			
	     		        	}else{
	    	        			if ("totes".equals(idUsuari)) {
	    	        				obresAssignades = InformeCore.getObresUsuari(conn, -1);    
	    	        			} else {
	    	        				obresAssignades = InformeCore.getInformesResumArea(conn, idUsuari);	    	        				
	    	        			}	        			
	    	        		}	
	     		        }
	     	        }
	        		
	        	}else{
	        		obresAssignades = InformeCore.getObresUsuari(conn, usuari.getIdUsuari());       		
	        		if (usuari.getRol().contains("GERENT")) usuarisSeleccionats = "totes#";
	        	}
	        	llistaUsuaris =  UsuariCore.findUsuarisByRol(conn, "");	        	
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }  
	        // Store info in request attribute, before forward to views
	        request.setAttribute("veureTotes", veureTotes);
	        request.setAttribute("canViewPersonal", UsuariCore.hasPermision(conn, usuari, SectionPage.personal));	       
	        request.setAttribute("llistaUsuaris", llistaUsuaris);
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("obresAssignades", obresAssignades);	       
	        request.setAttribute("usuarisSeleccionats", usuarisSeleccionats);
		    request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "Encarrecs"));
		
		   /* request.setAttribute("infoLog", "IP remote: " + request.getRemoteAddr() + "</br>remote host: " + request.getRemoteHost());
		    System.out.println("IP remote: " + request.getRemoteAddr());
		    System.out.println("remote host: " + request.getRemoteHost()); */
	       
		    // Forward to /WEB-INF/views/productListView.jsp
	        RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/encarrec/encarrecsListView.jsp");
	        dispatcher.forward(request, response);
 	   	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
