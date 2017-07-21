package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

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
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    int idTasca = Integer.parseInt(multipartParams.getParametres().get("idTasca"));
	    String idActuacio = multipartParams.getParametres().get("idActuacio");
	    String idIncidencia = multipartParams.getParametres().get("idIncidencia");
	    String idProposta = multipartParams.getParametres().get("idProposta");
	    String idInforme = multipartParams.getParametres().get("idInformePrevi");	   
	    String errorString = null;
	    //Agafam totes les ofertes
	    String cifEmpresa = multipartParams.getParametres().get("llistaEmpreses");	    	    
	    double plic = Double.parseDouble(multipartParams.getParametres().get("oferta").replace(",", "."));
    	
    	Oferta oferta = new Oferta();
    	oferta.setIdInforme(idInforme);
   		oferta.setIdActuacio(idActuacio);
   		oferta.setCifEmpresa(cifEmpresa);
   		oferta.setPlic(plic);
   		DecimalFormat df = new DecimalFormat("#.##");  
   		double vec = plic / 1.21;
   		oferta.setVec(Double.valueOf(df.format(vec).replace(",",".")));
   		oferta.setIva(Double.valueOf(df.format(vec * 0.21).replace(",",".")));			   		
   		oferta.setSeleccionada(false);
   		oferta.setDescalificada(false);
		
		try {
			OfertaCore.novaOferta(conn, oferta, Usuari.getIdUsuari());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Guardar adjunts
	   	OfertaCore.guardarFitxer(multipartParams.getFitxers(), idIncidencia, idActuacio, idProposta, cifEmpresa);
			    	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	response.sendRedirect(request.getContextPath() + "/tasca?id=" + idTasca + "#afegirOfertes");		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
