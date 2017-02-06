package servlet.actuacio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Informe;
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
	    int idActuacio = Integer.parseInt(request.getParameter("idActuacio"));
	    String aprovar = request.getParameter("aprovar");
	    String tancar = request.getParameter("tancar");	   
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	   
	    	   	
   		if (aprovar != null) { //aprovam actuació
   			try {
   				ActuacioCore.aprovar(conn, idActuacio, Usuari.getIdUsuari());
   				//Mirem l'import per començar licitació o contracte d'obra menor
   				List<Informe> informesList = InformeCore.getInformesActuacio(conn, idActuacio);
   				Informe informeFinal = informesList.get(informesList.size() - 1);   				
			   	String tipus = "";
			   	int usuariTasca = -1;
			   	
   				if (("obr".equals(informeFinal.getTipusObra()) && informeFinal.getVec() > 50000) || (!"obr".equals(informeFinal.getTipusObra()) && informeFinal.getVec() > 18000)) { //Contracte d'obres major   					
   					usuariTasca = UsuariCore.findUsuarisByTipus(conn, "JUR").get(0).getIdUsuari();
   					tipus = "liciMajor";
   				}else{ //Contracte d'obres menor
   					usuariTasca = informeFinal.getUsuari().getIdUsuari();   					
					tipus = "liciMenor";
   				}
   				//Registrar incidència nova
   				TascaCore.novaTasca(conn, tipus, usuariTasca, Usuari.getIdUsuari(), idActuacio, "", "");
   				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   		}else if (tancar != null) { // tancam actuació
   			try {
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
