package servlet.registre;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ControlPage.SectionPage;
import bean.Registre;
import bean.User;
import core.ControlPageCore;
import core.RegistreCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class RegistreSortidaListServlet
 */
@WebServlet("/registreSortida")
public class RegistreSortidaListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistreSortidaListServlet() {
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
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.registre_sort_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	 	
    	}else{
    	   String filtrar = request.getParameter("filtrar");
		   String idCentre = "";
		   String idCentreSelector = "";
		   DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		   String dataIniciString = "01/01/1990";
		   String dataFiString = df.format(new Date().getTime());	 	   
	       String errorString = null;
	       List<Registre> list = new ArrayList<Registre>();
	       try {
	    	  if (filtrar != null) {
	    		  try {
	    			 Date dataInici = df.parse(request.getParameter("dataInici"));
	    			 dataIniciString = request.getParameter("dataInici");
	    			 Date dataFi = df.parse(request.getParameter("dataFi"));
	    			 dataFiString = request.getParameter("dataFi");
	    			 if (request.getParameter("filterCentre") != null) {
						  idCentre = request.getParameter("idCentre").split("_")[0];
						  idCentreSelector = request.getParameter("idCentre");
					  }
					  list = RegistreCore.searchSortides(conn, idCentre, dataInici, dataFi);
	    		  } catch (ParseException e1) {
	    			  // TODO Auto-generated catch block
	    			  e1.printStackTrace();
	    		  }				  		  
			  }else{
				  list = RegistreCore.sortides(conn);
			  } 
	       } catch (SQLException e) {
	           e.printStackTrace();
	           errorString = e.getMessage();
	       }   		   
 	  
 	       // Store info in request attribute, before forward to views
 	       request.setAttribute("errorString", errorString);
 	       request.setAttribute("sortides", list);
 	       request.setAttribute("idCentre", idCentreSelector );
	       request.setAttribute("dataInici", dataIniciString);
	       request.setAttribute("dataFi", dataFiString);
	       request.setAttribute("canCreateRegistre", UsuariCore.hasPermision(conn, usuari, SectionPage.registre_sort_crear));
	       request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Registre"));
 	       // Forward to /WEB-INF/views/homeView.jsp
 	       // (Users can not access directly into JSP pages placed in WEB-INF)
 	       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registre/registreSortidaListView.jsp");
 	        
 	       dispatcher.forward(request, response);
 	   }
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
