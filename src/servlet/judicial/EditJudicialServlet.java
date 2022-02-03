package servlet.judicial;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Judicial;
import bean.Registre;
import bean.Tasca;
import bean.Factura;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.FacturaCore;
import core.JudicialCore;
import core.RegistreCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditJudicialServlet
 */
@WebServlet("/procediment")
public class EditJudicialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditJudicialServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
    	if (MyUtils.getLoginedUser(request.getSession()) == null){
 		   response.sendRedirect(request.getContextPath() + "/");
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.judicials_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{
			String ref = request.getParameter("ref");
	        Judicial procediment = new Judicial();
	          
	        List<Tasca> tasquesList = new ArrayList<Tasca>();
	        List<Registre> entrades = new ArrayList<Registre>();
	        List<Registre> sortides = new ArrayList<Registre>();
	        List<Factura> llistaFactures = new ArrayList<Factura>();
	        boolean canModificarProcediment = false;
	        procediment = JudicialCore.findProcediment(conn, ref);
			tasquesList = TascaCore.findTasquesJudicial(conn, ref);
			entrades = RegistreCore.searchEntradesIncidencia(conn, ref, null);
			sortides = RegistreCore.searchSortidesIncidencia(conn, ref, null);
			llistaFactures = FacturaCore.getFacturesActuacio(conn, ref);
			canModificarProcediment = UsuariCore.hasPermision(conn, usuari, SectionPage.judicials_modificar);	
	        // If no error.
	        // The product does not exist to edit.
	        // Redirect to productList page.
	        if (procediment.getReferencia() == null) {
	            response.sendRedirect("/judicials");
	            return;
	        }
	 
	        // Store errorString in request attribute, before forward to views.
	        request.setAttribute("canModificarProcediment", canModificarProcediment);
	        request.setAttribute("entrades", entrades);
	        request.setAttribute("sortides", sortides);
	        request.setAttribute("llistaFactures", llistaFactures);
	        request.setAttribute("tasquesList", tasquesList);
	        request.setAttribute("procediment", procediment);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"judicials"));
	        
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/judicial/editJudicialView.jsp");
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
