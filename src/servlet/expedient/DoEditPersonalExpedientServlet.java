package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InformeActuacio;
import core.InformeCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditPersonalExpedientServlet
 */
@WebServlet("/DoEditPersonalExpedient")
public class DoEditPersonalExpedientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditPersonalExpedientServlet() {
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
		     	
		String idInforme = multipartParams.getParametres().get("idInforme");      
		String idActuacio = multipartParams.getParametres().get("idActuacio");      
       	int idUsuari = Integer.parseInt(multipartParams.getParametres().get("llistaUsuaris"));	
       	String tecnic = multipartParams.getParametres().get("llistaTecnic");
       	String funcio = multipartParams.getParametres().get("llistaFuncions");
       	String empresa = multipartParams.getParametres().get("llistaEmpreses");
       	
        String guardar = multipartParams.getParametres().get("guardar");
	    String afegir = multipartParams.getParametres().get("afegir");
             	
       	if (!funcio.equals("-1") && !tecnic.equals("-1")) InformeCore.nouPersonalAssociat(conn, idInforme, idUsuari, funcio, tecnic, empresa);	
 
		
		if (guardar != null) response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + idActuacio);
		
		if (afegir != null) response.sendRedirect(request.getContextPath() + "/newPersonalInforme?idactuacio="+ idActuacio +"&idinf=" + idInforme + "&from=actuacions");

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
