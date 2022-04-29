package servlet.aulesModulars;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AulesModulars;
import bean.InformeActuacio;
import core.AulesModularsCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateAulaModularServlet
 */
@WebServlet("/doCreateAulaModular")
public class DoCreateAulaModularServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateAulaModularServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
	    String idInforme = request.getParameter("expedientsList");
	    Double importPrevist = Double.parseDouble(request.getParameter("import"));
	   
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    Date dataLimitContracte = null;
		try {
			 if (request.getParameter("dataLimitContracte") != null && ! request.getParameter("dataLimitContracte").isEmpty()) dataLimitContracte = formatter.parse(request.getParameter("dataLimitContracte"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		AulesModulars aula = new AulesModulars();
	    InformeActuacio informe = new InformeActuacio();
	    InformeActuacio informeAutoritzat = new InformeActuacio();
	    InformeActuacio informeNoAutoritzat = new InformeActuacio();
	    informe.setIdInf(idInforme);
	    
	    aula.setInforme(informe);
	    aula.setImportPrevist(importPrevist);
	    aula.setDataLimitContracte(dataLimitContracte);
	    aula.setInformeAutoritzacio(informeAutoritzat);
	    aula.setNoAutoritzada(informeNoAutoritzat);
	    
	    AulesModularsCore.novaAulaModula(conn, aula);
	    
	    
	    
	  	// If error, forward to Edit page.
	   	response.sendRedirect(request.getContextPath() + "/aulesModulars");	   	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
