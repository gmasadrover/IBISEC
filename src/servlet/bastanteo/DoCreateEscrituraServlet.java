package servlet.bastanteo;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Bastanteo;
import bean.Bastanteo.Escritura;
import core.BastanteosCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateEscrituraServlet
 */
@WebServlet("/doCreateEscritura")
public class DoCreateEscrituraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateEscrituraServlet() {
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
       	
       
       	Escritura escritura = new Bastanteo().new Escritura();
    	String refBastanteo = multipartParams.getParametres().get("refBastanteo");
       	String errorString = null;
       	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       	try {
       		escritura.setEscritura(multipartParams.getParametres().get("escritura"));
			if (multipartParams.getParametres().get("dataEscritura") != null && ! multipartParams.getParametres().get("dataEscritura").isEmpty()) {
				escritura.setDataEscritura(formatter.parse(multipartParams.getParametres().get("dataEscritura")));
			}
			escritura.setNumProtocol(multipartParams.getParametres().get("nProtocol"));	
			escritura.setNotari(multipartParams.getParametres().get("notari"));
			BastanteosCore.novaEscritura(conn, escritura, refBastanteo);
		} catch (ParseException e) {
			errorString = e.toString();
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("escritura", escritura);
 
 
		// If error, forward to Edit page.
		if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/bastanteos/createEscrituraView.jsp");
           dispatcher.forward(request, response);
		}
        
		// If everything nice.
		// Redirect to the product listing page.            
		else {
           response.sendRedirect(request.getContextPath() + "/bastanteo?ref=" + refBastanteo);
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
