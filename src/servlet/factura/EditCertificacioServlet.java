package servlet.factura;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Empresa;
import bean.Factura;
import bean.InformeActuacio;
import bean.Oferta;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.EmpresaCore;
import core.FacturaCore;
import core.InformeCore;
import core.OfertaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EditFacturaServlet
 */
@WebServlet("/editCertificacio")
public class EditCertificacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCertificacioServlet() {
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
 	   		String idCertificacio = request.getParameter("ref");
 	   		List<Empresa> empresesList = new ArrayList<Empresa>();
 	   		Factura certificacio = new Factura();
	        try {
	        	certificacio = FacturaCore.getCertificacio(conn, idCertificacio);
				empresesList = EmpresaCore.getEmpreses(conn);
				request.setAttribute("llistaUsuaris", UsuariCore.llistaUsuaris(conn, true));
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        request.setAttribute("certificacio", certificacio);
	        request.setAttribute("empresesList", empresesList);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Factures"));
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/factura/editCertificacioView.jsp");
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
