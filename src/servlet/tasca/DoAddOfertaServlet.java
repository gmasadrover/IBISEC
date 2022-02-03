package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Oferta;
import bean.User;
import core.OfertaCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoAddOferta
 */
@WebServlet("/DoAddOferta")
public class DoAddOfertaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAddOfertaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = MyUtils.getStoredConnection(request);		
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		
	    int idTasca = Integer.parseInt(multipartParams.getParametres().get("idTasca"));
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idInforme = multipartParams.getParametres().get("idInformePrevi");	   
	    String errorString = null;
	    
	    String afegirOfertaExpedient = multipartParams.getParametres().get("afegirOfertaExpedient");
	    
	    //Agafam totes les ofertes
	    String cifEmpresa = multipartParams.getParametres().get("llistaEmpreses");	    	    
	    double plic = Double.parseDouble(multipartParams.getParametres().get("oferta").replace(",", "."));
    	
    	Oferta oferta = new Oferta();
    	oferta.setIdInforme(idInforme);
   		oferta.setIdActuacio(idActuacio);
   		oferta.setCifEmpresa(cifEmpresa);
   		oferta.setPlic(plic);
   		DecimalFormat df = new DecimalFormat("#.##");  
   		double pbase = plic / 1.21;
   		oferta.setPbase(Double.valueOf(df.format(pbase).replace(",",".")));
   		oferta.setIva(Double.valueOf(df.format(pbase * 0.21).replace(",",".")));			   		
   		oferta.setSeleccionada(false);
   		oferta.setDescalificada(false);
		
		OfertaCore.novaOferta(conn, oferta, Usuari.getIdUsuari());
		OfertaCore.guardarFitxer(conn, multipartParams.getFitxers(), idIncidencia, idActuacio, idInforme, cifEmpresa, "Oferta", Usuari.getIdUsuari());
			    	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	if (afegirOfertaExpedient != null) {
	   		String refExp = multipartParams.getParametres().get("expedient");	   
	   		response.sendRedirect(request.getContextPath() + "/editLicitacio?ref=" + refExp);
	   	} else {
	   		response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca + "#afegirOfertes");
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
