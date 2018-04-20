package servlet.expedient;

import java.io.IOException;
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

import bean.Actuacio;
import bean.Expedient;
import bean.Incidencia;
import bean.InformeActuacio;
import bean.Partida;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.CreditCore;
import core.ExpedientCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/editExecucio" })
public class EditExecucioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public EditExecucioServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
    	if (MyUtils.getLoginedUser(request.getSession()) == null){
 		   response.sendRedirect(request.getContextPath() + "/");
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_modificar)) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{
			String refExp = request.getParameter("ref");
			String idInf = request.getParameter("idinf");
	        Expedient expedient = new Expedient();
	       
	        String errorString = null;
	        double importObraMajor = Double.parseDouble(getServletContext().getInitParameter("importObraMajor"));      
	        InformeActuacio informe = new InformeActuacio();
	        try {
	        	if (refExp == null || refExp.isEmpty()) {
	        		informe = InformeCore.getInformePrevi(conn, idInf, false);
					refExp = ExpedientCore.crearExpedient(conn, informe, importObraMajor, true, "");
				}
	            expedient = ExpedientCore.findExpedient(conn, refExp);	    
	            if (expedient == null || expedient.getExpContratacio() == null || expedient.getExpContratacio().isEmpty()) {
	            	refExp = ExpedientCore.crearExpedient(conn, informe, importObraMajor, true, refExp);
	            }
	        } catch (SQLException | NamingException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }
	 
	         
	        // If no error.
	        // The product does not exist to edit.
	        // Redirect to productList page.
	        if (errorString != null && expedient == null) {
	            response.sendRedirect(request.getServletPath() + "/expedients");
	            return;
	        }
	 
	        // Store errorString in request attribute, before forward to views.	       
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("expedient", expedient);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"expedients"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/expedients/editExecucioView.jsp");
	        dispatcher.forward(request, response);
 	   	}
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}