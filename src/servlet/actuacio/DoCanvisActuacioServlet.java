package servlet.actuacio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import bean.User;
import core.ActuacioCore;
import core.InformeCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCanvisActuacio
 */
@WebServlet("/DoCanvisActuacio")
public class DoCanvisActuacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCanvisActuacioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
Connection conn = MyUtils.getStoredConnection(request);
		
		//Registrar actuació
	    String idActuacio = request.getParameter("idActuacio");
	    String idIncidencia = request.getParameter("idIncidencia");
	    String idInforme = request.getParameter("idInforme");
	    String aprovarPA = request.getParameter("aprovarPA");
	    String tancar = request.getParameter("tancar");	   
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	   
	    	   	
   		
	    if (aprovarPA != null) { 
   			try {
   				//aprovam actuació
   				ActuacioCore.aprovarPA(conn, idActuacio, Usuari.getIdUsuari());
   				//aprovam informe
   				InformeCore.aprovacioInforme(conn, idInforme, Usuari.getIdUsuari());
   				//Mirem l'import per començar licitació o contracte d'obra menor
   				InformeActuacio informe = InformeCore.getInformePrevi(conn, idInforme);		
			   	String tipus = "";
			   	int usuariTasca = -1;
			   	
   				if (("obr".equals(informe.getTipusObra()) && informe.getVec() > 50000) || (!"obr".equals(informe.getTipusObra()) && informe.getVec() > 18000)) { //Contracte d'obres major   					
   					usuariTasca = UsuariCore.findUsuarisByRol(conn, "JUR").get(0).getIdUsuari();
   					tipus = "liciMajor";
   				}else{ //Contracte d'obres menor
   					usuariTasca = informe.getUsuari().getIdUsuari();   					
					tipus = "liciMenor";
   				}
   				//Registrar incidència nova
   				ActuacioCore.actualitzarActuacio(conn, idActuacio, "Sol·licitud proposta tècnica");
   				TascaCore.novaTasca(conn, tipus, usuariTasca, Usuari.getIdUsuari(), idActuacio, idIncidencia, "", "",informe.getIdInf());
   				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   		}else if (tancar != null) { // tancam actuació
   			try {
   				ActuacioCore.actualitzarActuacio(conn, idActuacio, "Tancar actuació");
   				ActuacioCore.tancar(conn, idActuacio, Usuari.getIdUsuari());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   			
   		}else {
   			
   		} 
   		response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
