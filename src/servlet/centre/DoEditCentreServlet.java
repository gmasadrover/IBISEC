package servlet.centre;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Centre;
import core.CentreCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditCentreServlet
 */
@WebServlet("/DoEditCentre")
public class DoEditCentreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditCentreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		
		String codiCentre = request.getParameter("codi");
		String nom = request.getParameter("nom");
		String tipo = request.getParameter("tipo");		
		String direccio = request.getParameter("direccio");
		String cp = request.getParameter("cp");
		String illa = request.getParameter("illa");
		String municipi = request.getParameter("municipi");
		String localitat = request.getParameter("localitat");
					
		
		Centre centre = new Centre();
      
		
			
				centre = CentreCore.findCentre(conn, codiCentre, false);
				centre.setNom(nom);
				centre.setTipo(tipo);
				centre.setAdreca(direccio);
				centre.setCp(cp);
				centre.setIlla(illa);
				centre.setMunicipi(municipi);
				centre.setLocalitat(localitat);
				CentreCore.editCentre(conn, centre);
			
		
        
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("centre", centre);
 
		response.sendRedirect(request.getContextPath() + "/centreDetalls?codi=" + centre.getIdCentre());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
