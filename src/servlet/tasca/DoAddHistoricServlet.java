package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;

import bean.Tasca;
import bean.User;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoAddHistoric
 */
@WebServlet("/DoAddHistoric")
public class DoAddHistoricServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAddHistoricServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Connection conn = MyUtils.getStoredConnection(request);
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		
	    int idTasca = Integer.parseInt(multipartParams.getParametres().get("idTasca"));
	   
	  
	    String reasignar = multipartParams.getParametres().get("reasignar");
	    boolean urgent = "on".equals(multipartParams.getParametres().get("urgent"));
	    String tancar = multipartParams.getParametres().get("tancar");
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	 
	    String comentari = multipartParams.getParametres().get("comentari");
	    String errorString = null;
	    Tasca tasca = new Tasca();
	    String tipus = "generic";
	   	//Registrar comentari;	   
	   	try {
	   		if (reasignar != null) {
	   			comentari = comentari + "<br><span class='missatgeAutomatic'>Es reassigna la tasca</span>";	   			
	   		}else if (tancar != null) {
	   			comentari = comentari + "<br><span class='missatgeAutomatic'>Es tanca la tasca</span>";
	   		}
			TascaCore.nouHistoric(conn, idTasca, comentari, Usuari.getIdUsuari(), request.getRemoteAddr(), "manual");
			tasca = TascaCore.findTascaId(conn, idTasca, Usuari.getIdUsuari());
			tipus = tasca.getTipus();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorString = e.getMessage();
		}
	   	TascaCore.guardarFitxer(conn, idTasca, multipartParams.getFitxers(), Usuari.getIdUsuari());
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/actuacio/tascaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   		if (reasignar != null) { //reassignem la incid�ncia
	   			String idUsuariNou = multipartParams.getParametres().get("idUsuari");
	   			if (idUsuariNou.equals("-1") ) {
	   				idUsuariNou = String.valueOf(Usuari.getIdUsuari());		
	   			}
	   			if (!NumberUtils.isNumber(idUsuariNou)) {
					idUsuariNou =  String.valueOf(UsuariCore.finCap(conn, idUsuariNou).getIdUsuari());
				}
				User usuariNou = UsuariCore.findUsuariByID(conn, Integer.parseInt(idUsuariNou));
				if (tasca.getTipus().equals("facturaConformada")) {
					if (!usuariNou.getRol().contains("CONTA")) {
						tipus = "conformarFactura";
					}
				} else if(tasca.getTipus().equals("solInfPrev")) {
					if (!Usuari.getDepartament().equals("gerencia")) { //Si no es gerencia	   							
						if(usuariNou.getDepartament().equals("gerencia")) { //Si va a gerencia
							tipus = "infPrev";
						}else if(usuariNou.getRol().contains("CAP")) { //Si va a cap
							tipus = "vistInfPrev";
						}
					}	   						
				} else if(tasca.getTipus().equals("vistInfPrev")) {
					if(usuariNou.getDepartament().equals("gerencia")) { //Si va a gerencia
						tipus = "infPrev";
					}else if(!usuariNou.getRol().contains("CAP")) { //Si va tecnic
						tipus = "solInfPrev";
					}
				} else if(tasca.getTipus().equals("infPrev")) {
					if(!usuariNou.getDepartament().equals("gerencia")) { //Si no va a gerencia
						tipus = "solInfPrev";
					}
				} else if(tasca.getTipus().equals("doctecnica")) {
					if (!Usuari.getDepartament().equals("gerencia")) { //Si no es gerencia	   							
						if(usuariNou.getRol().contains("CAP")) { //Si va a cap
							tipus = "vistDocTecnica";
						}
					}	 
				} else if(tasca.getTipus().equals("vistDocTecnica")) {
					if(!usuariNou.getRol().contains("CAP")) { //Si va tecnic
						tipus = "doctecnica";
					}
				} else if(tasca.getTipus().equals("docprelicitacio")) {
					if(!usuariNou.getDepartament().equals("gerencia") && !usuariNou.getDepartament().equals("juridica")) { //Si no va a gerencia ni juridica
						tipus = "doctecnica";
					}
				}
				String descripcio = tasca.getDescripcio();
				if (urgent) {
					if (!descripcio.contains("URGENT - ")) {
						descripcio = "URGENT - " + descripcio;
					}
				} else {
					descripcio = descripcio.replaceAll("URGENT - ", "");
				}
				TascaCore.reasignar(conn, Integer.parseInt(idUsuariNou), idTasca, tipus, descripcio);
	   			response.sendRedirect(request.getContextPath() + "/tascaList");
	   		}else if (tancar != null) { // tancam incid�ncia
	   			TascaCore.tancar(conn, idTasca);
	   			response.sendRedirect(request.getContextPath() + "/tascaList");
	   		}else {	   		
	   			try {
					TascaCore.marcarNoLlegida(conn,  idTasca);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	   			response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca);
	   		}	   		
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
