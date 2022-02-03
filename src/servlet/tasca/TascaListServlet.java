package servlet.tasca;

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

import bean.Tasca;
import bean.User;
import bean.Actuacio;
import bean.InformeActuacio;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
import core.ControlPageCore;
import core.InformeCore;
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
    		response.sendRedirect(request.getContextPath() + "/actuacions");	
 	   	}else{
 	   		
	 	    String filtrar = request.getParameter("filtrar");
 	   		String filterWithClosed = request.getParameter("filterWithClosed");
	        String errorString = null;
	        List<List<Tasca>> list = null;
	        List<Tasca> listSeguiment = new ArrayList<Tasca>();
	        List<Actuacio> seguimentActuacionsList = new ArrayList<Actuacio>();
	        List<User> llistaUsuaris = new ArrayList<User>();
	        List<InformeActuacio> obresAssignades = new ArrayList<InformeActuacio>();
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
	    	        			list = TascaCore.llistaTasquesUsuari(conn, Integer.parseInt(idUsuari), null, "on".equals(filterWithClosed));      			
	     		        	}else{
	    	        			if ("totes".equals(idUsuari)) {
	    	        				list = TascaCore.llistaTasquesUsuari(conn, -1, null, "on".equals(filterWithClosed));
	    	        			} else {
	    	        				list = TascaCore.llistaTasquesUsuari(conn, -1, idUsuari, "on".equals(filterWithClosed));		
	    	        			}	        			
	    	        		}	
	     		        }
	     	        }
	        		
	        	}else{
	        		Calendar avui = Calendar.getInstance();
	        		if (usuari.getRol().contains("LLICENCIA") && avui.get(Calendar.DATE) == 10 && avui.get(Calendar.DATE) <= 15) {
	        			//Avisar Tasca llicències
	        			if (!TascaCore.existTascaPrevisioLlicencies(conn, avui.get(Calendar.MONTH) + 1)) {
	        				TascaCore.novaTasca(conn, "previsioLlicencies", usuari.getIdUsuari(), usuari.getIdUsuari(), "-1", "-1", "Previsió fons llicències", "Previsió fons llicències", String.valueOf(avui.get(Calendar.MONTH) + 1), null, request.getRemoteAddr(), "automàtica");
	        			}
	        		}
	        		list = TascaCore.llistaTasquesUsuari(conn, usuari.getIdUsuari(), null, false);	
	        		if (usuari.getRol().contains("GERENT")) usuarisSeleccionats = "totes#";
	        	}
	        	llistaUsuaris =  UsuariCore.findUsuarisByRol(conn, "");
	        	listSeguiment.addAll(TascaCore.llistaTasquesSeguiment(conn, usuari.getIdUsuari()));
	        	seguimentActuacionsList.addAll(ActuacioCore.llistaActuacionsSeguiment(conn, usuari.getIdUsuari()));
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }  
	        // Store info in request attribute, before forward to views
	        request.setAttribute("veureTotes", veureTotes);
	        request.setAttribute("canViewPersonal", UsuariCore.hasPermision(conn, usuari, SectionPage.personal));
	        request.setAttribute("canCreateTasca", canCreateTasca);
	        request.setAttribute("llistaUsuaris", llistaUsuaris);
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("obresAssignades", obresAssignades);
	        request.setAttribute("tasquesList", list);  
	        request.setAttribute("infPrevList", list.get(0));  // 0
	        request.setAttribute("solInfPrevList", list.get(1));  // 1
	        request.setAttribute("vistInfPrevList", list.get(2));  // 2
	        request.setAttribute("redacDocTecnicaList", list.get(3));  // 3
	        request.setAttribute("vistDocTecnicaList", list.get(4));  // 4
	        request.setAttribute("sigDocExpList", list.get(5));  // 5
	        request.setAttribute("propAdjList", list.get(6));  // 6
	        request.setAttribute("resAdjList", list.get(7));  // 7
	        request.setAttribute("redContracteList", list.get(8));  // 8  
	        request.setAttribute("resCreditList", list.get(9)); // 9
	        request.setAttribute("docPreLicitacioList", list.get(10)); // 10
	        request.setAttribute("judicialList", list.get(11)); // 11
	        request.setAttribute("conformarFacturaList", list.get(12)); // 12
	        request.setAttribute("revisarCertificacioList", list.get(13)); // 13
	        request.setAttribute("contractesList", list.get(14)); // 14
	        request.setAttribute("contractesFirmaList", list.get(15)); // 15
	        request.setAttribute("altresList", list.get(16));  // 16	 
	        request.setAttribute("notificacionsList", list.get(17));  // 17	 
	        request.setAttribute("reservaDronList", list.get(18));  // 18	 
	        request.setAttribute("seguimentList", listSeguiment);
	        request.setAttribute("seguimentActuacionsList", seguimentActuacionsList);
	        request.setAttribute("usuarisSeleccionats", usuarisSeleccionats);
	        request.setAttribute("filterWithClosed", "on".equals(filterWithClosed));
		    request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "Tasques"));
		
		   /* request.setAttribute("infoLog", "IP remote: " + request.getRemoteAddr() + "</br>remote host: " + request.getRemoteHost());
		    System.out.println("IP remote: " + request.getRemoteAddr());
		    System.out.println("remote host: " + request.getRemoteHost()); */
	       
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

