package servlet.factura;

import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
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

import bean.User;
import bean.ControlPage.SectionPage;
import bean.Empresa;
import bean.InformeActuacio;
import bean.Oferta;
import core.ControlPageCore;
import core.EmpresaCore;
import core.FacturaCore;
import core.InformeCore;
import core.OfertaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CreateFacturaServlet
 */
@WebServlet("/registrarFactura")
public class CreateFacturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateFacturaServlet() {
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
 	   		String idInforme = request.getParameter("idInforme");
 	   		List<Empresa> empresesList = new ArrayList<Empresa>();
	        InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
			Oferta oferta = OfertaCore.findOfertaSeleccionada(conn, idInforme);
			if (oferta != null) {
				request.setAttribute("idFactura", FacturaCore.getNewCode(conn));
				request.setAttribute("idActuacio", oferta.getIdActuacio());	        	
				request.setAttribute("idInforme", oferta.getIdInforme());
				request.setAttribute("nifProveidor", oferta.getCifEmpresa());
				request.setAttribute("valorOferta", oferta.getPlic() - informe.getTotalFacturat());
			} else {
				request.setAttribute("idFactura", FacturaCore.getCodeFacturaAntic(conn));
				request.setAttribute("idActuacio", informe.getActuacio().getReferencia());	        	
				request.setAttribute("idInforme", idInforme);
			}			
			request.setAttribute("idUsuariInforme", informe.getUsuari().getIdUsuari());
			request.setAttribute("concepte", informe.getPropostaInformeSeleccionada().getObjecte());
			request.setAttribute("llistaUsuaris", UsuariCore.llistaUsuaris(conn, true));
			empresesList = EmpresaCore.getEmpreses(conn);
			empresesList.addAll(EmpresaCore.getEmpresesUTE(conn));
	        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	        String today = df.format(new Date().getTime());	
	        request.setAttribute("data", today);
	        request.setAttribute("empresesList", empresesList);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Factures"));
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/factura/createFacturaView.jsp");
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
