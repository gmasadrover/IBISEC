package servlet.registre;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Registre;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.RegistreCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class RegistreEntradaListServlet
 */
@WebServlet("/registreEntrada")
public class RegistreEntradaListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistreEntradaListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
 	   if (usuari == null){
 		   response.sendRedirect(request.getContextPath() + "/preLogin");
 	   }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.registre_ent_list)) {
  		response.sendRedirect(request.getContextPath() + "/");	
 	   }else{
 		   String filtrar = request.getParameter("filtrar");
 		   String filterWithOutDate = request.getParameter("filterWithOutDate");
 		   String idCentre = "";
		   String idCentreSelector = "";
		   DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		   Calendar cal = Calendar.getInstance(); 
		   Date dataFi = cal.getTime();
		   String dataFiString = df.format(dataFi);	 
		   cal.add(Calendar.MONTH, -2);
		   Date dataInici = cal.getTime();
		   String dataIniciString = df.format(dataInici);		   
 	       String errorString = null;
 	       List<Registre> list = new ArrayList<Registre>();
 	       try {
 	    	  if (filtrar != null) {
 	    		  try { 	    			 
 	    			 if (!"-1".equals(request.getParameter("idCentre").split("_")[0])) {
 						  idCentre = request.getParameter("idCentre").split("_")[0];
 						  idCentreSelector = request.getParameter("idCentre");
 					  }
 	    			 dataInici = null;
 	    			 dataIniciString = "";
 	    			 dataFi = null;
 	    			 dataFiString = "";
 	    			 if (filterWithOutDate == null){
 	    				 dataInici = df.parse(request.getParameter("dataInici"));
 	    				 dataIniciString = request.getParameter("dataInici");
 	    				 dataFi = df.parse(request.getParameter("dataFi"));
 	    				 dataFiString = request.getParameter("dataFi");
 	    			 }	
 	    			 list = RegistreCore.searchEntrades(conn, idCentre, dataInici, dataFi);
 	    		  } catch (ParseException e1) {
 	    			  // TODO Auto-generated catch block
 	    			  e1.printStackTrace();
 	    		  }				  		  
			  }else{
				  list = RegistreCore.entrades(conn);
			  } 
 	       } catch (SQLException e) {
 	           e.printStackTrace();
 	           errorString = e.getMessage();
 	       }  
 	     
 	       /*utils.GeneradorDocumentosServiceTest a = new utils.GeneradorDocumentosServiceTest();
 	       try {
			a.testDocx();
			a.testDocxPdf();
		} catch (XDocReportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
 	       
 	       // Store info in request attribute, before forward to views
 	       request.setAttribute("errorString", errorString);
 	       request.setAttribute("entrades", list);
	       request.setAttribute("idCentre", idCentreSelector );
	       request.setAttribute("filterWithOutDate", "on".equals(filterWithOutDate));
	       request.setAttribute("dataInici", dataIniciString);
	       request.setAttribute("dataFi", dataFiString);
	       request.setAttribute("canCreateRegistre", UsuariCore.hasPermision(conn, usuari, SectionPage.registre_ent_crear));
	       request.setAttribute("menu", ControlPageCore.renderMenu(conn,usuari, "Registre"));
 	       // Forward to /WEB-INF/views/homeView.jsp
 	       // (Users can not access directly into JSP pages placed in WEB-INF)
 	       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registre/registreEntradaListView.jsp");
 	        
 	       dispatcher.forward(request, response);
 	   }
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
