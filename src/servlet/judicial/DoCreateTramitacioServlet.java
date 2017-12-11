package servlet.judicial;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.Judicial;
import bean.User;
import bean.Judicial.Tramitacio;
import core.JudicialCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateSentenciaServlet
 */
@WebServlet("/doCreateTramitacio")
public class DoCreateTramitacioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateTramitacioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuari = MyUtils.getLoginedUser(request.getSession());
       	Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}       	
       	String ref = multipartParams.getParametres().get("referencia");
       	String refPro = multipartParams.getParametres().get("procediment");
       	Tramitacio tramitacio = new Judicial().new Tramitacio();
       	String errorString = null;
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       	try {
       		tramitacio.setNumstcia(ref);
       		tramitacio.setSentencia(multipartParams.getParametres().get("sentencia"));
			if (multipartParams.getParametres().get("data") != null && ! multipartParams.getParametres().get("data").isEmpty()) {
				tramitacio.setData(formatter.parse(multipartParams.getParametres().get("data")));
			}
			if (!multipartParams.getParametres().get("quantia").isEmpty()) tramitacio.setQuantia(Double.parseDouble(multipartParams.getParametres().get("quantia")));
			tramitacio.setRecurs(multipartParams.getParametres().get("recurs"));
			if (multipartParams.getParametres().get("dataPagament") != null && ! multipartParams.getParametres().get("dataPagament").isEmpty()) {
				tramitacio.setDatapagament(formatter.parse(multipartParams.getParametres().get("dataPagament")));
			}
			tramitacio.setTermini(multipartParams.getParametres().get("termini"));
			JudicialCore.novaTramitacio(conn, tramitacio, refPro, usuari.getIdUsuari());
		} catch (SQLException | ParseException | NamingException e) {
			errorString = e.toString();
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("tramitacio", tramitacio);
 
 
		// If error, forward to Edit page.
		if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/judicial/editJudicialView.jsp");
           dispatcher.forward(request, response);
		}
        
		// If everything nice.
		// Redirect to the product listing page.            
		else {
           response.sendRedirect(request.getContextPath() + "/procediment?ref=" + refPro);
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
