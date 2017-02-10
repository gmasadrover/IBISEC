package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Oferta;
import bean.User;
import core.ActuacioCore;
import core.EmpresaCore;
import core.OfertaCore;
import core.TascaCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoAddPresupostsServlet
 */
@WebServlet("/DoAddPresuposts")
public class DoAddPresupostsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAddPresupostsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn = MyUtils.getStoredConnection(request);		
		
	    int idTasca = Integer.parseInt(request.getParameter("idTasca"));
	    int idActuacio = Integer.parseInt(request.getParameter("idActuacio"));
	    User Usuari = MyUtils.getLoginedUser(request.getSession());	   
	    String comentari = request.getParameter("propostaTecnica");	   
	    String errorString = null;
	    String termini = request.getParameter("termini");
	    String seleccionada = request.getParameter("ofertaSeleccionadaNIF");
	    //Agafam totes les ofertes
	    String[] paramValues = request.getParameterValues("ofertes");
	    String cifEmpresa = "";
	    double plic = 0;
	    String ofertesPresentades = "Les ofertes han estat les següents:<br>"; 
	    String guardar = request.getParameter("guardar");
	    if (guardar != null) {
		  	try {	   	
		  		for(int i=0; i<paramValues.length; i++) {
			    	cifEmpresa = paramValues[i].split("#")[0];
			    	plic = Double.parseDouble(paramValues[i].split("#")[1]);
			    	Oferta oferta = new Oferta();
			   		oferta.setIdActuacio(idActuacio);
			   		oferta.setCifEmpresa(cifEmpresa);
			   		oferta.setPlic(plic);
			   		DecimalFormat df = new DecimalFormat("#.##");  
			   		oferta.setVec(Double.valueOf(df.format(plic / 1.21).replace(",",".")));
			   		oferta.setIva(Double.valueOf(df.format(oferta.getVec() * 1.21).replace(",",".")));
			   		if (seleccionada.equals(cifEmpresa)) {
			   			oferta.setTermini(termini);
			   			oferta.setComentari(comentari);
			   		}
			   		oferta.setSeleccionada(seleccionada.equals(cifEmpresa));
			   		oferta.setDescalificada(false);
			   		ofertesPresentades += "Empresa: " + EmpresaCore.findEmpresa(conn, cifEmpresa).getName() + " Oferta: " + plic + "€<br>";
			   		//Guardar oferta	
			   		OfertaCore.novaOferta(conn, oferta, Usuari.getIdUsuari());
			    }
		  		//Registrar comentari;	 
		   		String comentariHistoral = ofertesPresentades + "El tècnic proposa:<br>" + comentari + "<br>Amb un termini d'execució de: " + termini;	   		
		   		TascaCore.nouHistoric(conn, idTasca, comentariHistoral, Usuari.getIdUsuari());
		   		/*;*/
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errorString = e.getMessage();
			} 
	    } else {	    	
	   		try {
	   			OfertaCore.aprovarOferta(conn, idActuacio, Usuari.getIdUsuari());
	   			TascaCore.nouHistoric(conn, idTasca, "Proposta tècnica aprovada", Usuari.getIdUsuari());
	   			TascaCore.reasignar(conn, 902, idTasca);
				TascaCore.tancar(conn, idTasca);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/actuacio/tascaView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   		response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca);	   		
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
