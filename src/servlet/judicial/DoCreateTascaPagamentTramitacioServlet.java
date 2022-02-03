package servlet.judicial;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Judicial;
import bean.User;
import bean.Judicial.Tramitacio;
import core.JudicialCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateTascaPagamentTramitacioServlet
 */
@WebServlet("/doCreateTascaPagamentTramitacio")
public class DoCreateTascaPagamentTramitacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateTascaPagamentTramitacioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);		
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		User Usuari = MyUtils.getLoginedUser(request.getSession());	
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
			
	    int idTasca = -1;
	    String idProcediment = multipartParams.getParametres().get("procediment");
	   	String comentari = multipartParams.getParametres().get("descripcio");
		String valor = multipartParams.getParametres().get("valor").replace(',','.');
		Tramitacio tramitacio = new Judicial().new Tramitacio();
		
	   		   		   	
	   	//Cream Tramitació	
		tramitacio.setQuantia(multipartParams.getParametres().get("valor"));
		tramitacio.setTipus("Pagament");
		tramitacio.setDescripcio(comentari);
		tramitacio.setQuantia(valor);
		int idTramitacio = JudicialCore.novaTramitacio(conn, tramitacio, idProcediment, Usuari.getIdUsuari(), request.getRemoteAddr());
		JudicialCore.guardarFitxerTramitacio(conn, multipartParams.getFitxers(), idProcediment, idTramitacio, Usuari.getIdUsuari());
		
		int idUsuariTasca = UsuariCore.findUsuarisByRol(conn, "GERENT,CAP").get(0).getIdUsuari();   				
		idTasca = TascaCore.novaTasca(conn, "pagamentJudicial", idUsuariTasca, Usuari.getIdUsuari(), String.valueOf(idTramitacio), "-1", comentari, "Despesa judicial", idProcediment, multipartParams.getFitxers(), request.getRemoteAddr(), "manual");
	   	
	   
	   	
	   	response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
