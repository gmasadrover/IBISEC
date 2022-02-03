package servlet.factura;

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

import bean.Empresa;
import bean.Factura;
import bean.User;
import bean.Actuacio;
import bean.ControlPage.SectionPage;
import core.ActuacioCore;
import core.ControlPageCore;
import core.EmpresaCore;
import core.FacturaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditFacturaServlet
 */
@WebServlet("/editFactura")
public class EditFacturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditFacturaServlet() {
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
        }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.factures_crear)) {
    		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{ 	 
 	   		String idFactura = request.getParameter("ref");
 	   		List<Empresa> empresesList = new ArrayList<Empresa>();
 	   		Factura factura = new Factura();
 	   		Actuacio actuacio = new Actuacio();
 	   		String idCentre = "";
 	   		boolean informeAntic = false;
	        factura = FacturaCore.getFactura(conn, idFactura);
			if (factura.getIdActuacio() != null && !factura.getIdActuacio().equals("-1")) {
				if (factura.getIdActuacio().contains("PRO-")) {
					idCentre = "procediment";
				} else {
					actuacio = ActuacioCore.findActuacio(conn, factura.getIdActuacio());
					idCentre = actuacio.getCentre().getIdCentre();
				}	        		
			}	        	
			if (factura.getIdInforme() != null){
				informeAntic = !factura.getIdInforme().contains("-INF-");
			}
			empresesList = EmpresaCore.getEmpreses(conn);
			request.setAttribute("llistaUsuaris", UsuariCore.llistaUsuaris(conn, true));
			request.setAttribute("informeAntic", informeAntic);
	        request.setAttribute("factura", factura);
	        request.setAttribute("idCentre", idCentre);
	        request.setAttribute("empresesList", empresesList);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Factures"));
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/factura/editFacturaView.jsp");
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
