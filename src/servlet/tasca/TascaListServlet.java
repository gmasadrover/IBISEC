package servlet.tasca;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;

import bean.Tasca;
import bean.User;
import bean.Actuacio;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
import core.ControlPageCore;
import core.LoggerCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/tascaList" })
public class TascaListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public TascaListServlet() {
        super();
    } 
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
    	if (usuari == null){
 		   response.sendRedirect(request.getContextPath() + "/preLogin");
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.tasques_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{	   		
 	   		String filtrar = request.getParameter("filtrar");
 	   		String filterWithClosed = request.getParameter("filterWithClosed");
	        String errorString = null;
	        List<Tasca> list = new ArrayList<Tasca>();
	        List<Tasca> listSeguiment = new ArrayList<Tasca>();
	        List<Actuacio> seguimentActuacionsList = new ArrayList<Actuacio>();
	        List<User> llistaUsuaris = new ArrayList<User>();
	        String[] usuarisValues = null; 	  
	        String usuarisSeleccionats = String.valueOf(usuari.getIdUsuari());
	        boolean veureTotes = usuari.getDepartament().equals("gerencia");
	        boolean canCreateTasca = UsuariCore.hasPermision(conn, usuari, SectionPage.tasques_crear);
	   	    try {
	        	if (filtrar != null) {
	        		usuarisValues = request.getParameterValues("idUsuari");
	        		if (usuarisValues != null) {
	        			usuarisSeleccionats = "";
	     		        for(int i=0; i<usuarisValues.length; i++) { 		        	
	     		        	String idUsuari = usuarisValues[i];
	     		        	usuarisSeleccionats += idUsuari + "#";
	     		        	if (NumberUtils.isNumber(idUsuari)) {
	    	        			list.addAll(TascaCore.llistaTasquesUsuari(conn, Integer.parseInt(idUsuari), "on".equals(filterWithClosed) ));
	    	        		}else{
	    	        			if ("totes".equals(idUsuari)) {
	    	        				list = TascaCore.llistaTotesTasques(conn, "on".equals(filterWithClosed));
	    	        			} else {
	    	        				list.addAll(TascaCore.llistaTasquesArea(conn, idUsuari, "on".equals(filterWithClosed)));
	    	        			}	        			
	    	        		}	
	     		        }
	     	        }
	        	}else{
	        		list = TascaCore.llistaTasquesUsuari(conn, usuari.getIdUsuari(), false);		
	        		if (usuari.getRol().contains("GERENT")) usuarisSeleccionats = "totes#";
	        	}	
	        	llistaUsuaris =  UsuariCore.findUsuarisByRol(conn, "");
	        	listSeguiment.addAll(TascaCore.llistaTasquesSeguiment(conn, usuari.getIdUsuari()));
	        	seguimentActuacionsList.addAll(ActuacioCore.llistaActuacionsSeguiment(conn, usuari.getIdUsuari()));
	        } catch (SQLException | NumberFormatException | NamingException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }	        
	        
	        // Store info in request attribute, before forward to views
	        request.setAttribute("veureTotes", veureTotes);
	        request.setAttribute("canViewPersonal", UsuariCore.hasPermision(conn, usuari, SectionPage.personal));
	        request.setAttribute("canCreateTasca", canCreateTasca);
	        request.setAttribute("llistaUsuaris", llistaUsuaris);
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("tasquesList", list);  
	        request.setAttribute("seguimentList", listSeguiment);
	        request.setAttribute("seguimentActuacionsList", seguimentActuacionsList);
	        request.setAttribute("usuarisSeleccionats", usuarisSeleccionats);
	        request.setAttribute("filterWithClosed", "on".equals(filterWithClosed));
		    request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "Tasques"));
		
		    
		    //InetAddress IP=InetAddress.getLocalHost();
		    //LoggerCore.addLog("IP remote: " + request.getRemoteAddr(), usuari.getUsuari());
		    //LoggerCore.addLog("IP of my system is := "+IP.getHostAddress(), usuari.getUsuari());
		    //LoggerCore.addLog("USER: " + System.getProperty("user.name"), usuari.getUsuari());
	        // Forward to /WEB-INF/views/productListView.jsp
	        RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/tasca/tascaListView.jsp");
	        dispatcher.forward(request, response);
 	   	}
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}

