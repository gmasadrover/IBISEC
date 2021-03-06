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

import bean.Empresa;
import bean.InformeActuacio;
import bean.Oferta;
import bean.User;
import core.ControlPageCore;
import core.EmpresaCore;
import core.FacturaCore;
import core.InformeCore;
import core.OfertaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class CreateCertificacioServlet
 */
@WebServlet("/registrarCertificacio")
public class CreateCertificacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCertificacioServlet() {
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
        } else{ 	 
 	   		String idInforme = request.getParameter("idInforme");
 	   		List<Empresa> empresesList = new ArrayList<Empresa>();
	        InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme, false);
	        if (informe.isInformeAntic()) {
	        	request.setAttribute("idActuacio", informe.getActuacio().getReferencia());	        	
				request.setAttribute("idInforme", idInforme);
				request.setAttribute("concepte", informe.getActuacio().getDescripcio());
				request.setAttribute("idCertificacio", FacturaCore.getCodeCertAntic(conn));
	        } else {
	        	Oferta oferta = OfertaCore.findOfertaSeleccionada(conn, idInforme);
				request.setAttribute("idActuacio", oferta.getIdActuacio());	        	
				request.setAttribute("idInforme", oferta.getIdInforme());
				request.setAttribute("nifProveidor", oferta.getCifEmpresa());
				request.setAttribute("valorRestantCertificar", oferta.getPlic() + informe.getTotalModificacions() - informe.getTotalCertificat());
				request.setAttribute("concepte", informe.getPropostaInformeSeleccionada().getObjecte());
				request.setAttribute("idCertificacio", FacturaCore.getNewCodeCert(conn));
	        }			
			
			request.setAttribute("idUsuariInforme", informe.getUsuari().getIdUsuari());
			request.setAttribute("llistaUsuaris", UsuariCore.llistaUsuaris(conn, true));
			empresesList = EmpresaCore.getEmpreses(conn);
			empresesList.addAll(EmpresaCore.getEmpresesUTE(conn));
	        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	        String today = df.format(new Date().getTime());	
	        request.setAttribute("data", today);
	        request.setAttribute("empresesList", empresesList);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Factures"));
	        request.setAttribute("NovaLLei", true);
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/views/factura/createCertificacioView.jsp");
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
