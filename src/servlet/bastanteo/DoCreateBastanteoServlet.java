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
import core.EmpresaCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateBastanteoServlet
 */
@WebServlet("/doCreateBastanteo")
public class DoCreateBastanteoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateBastanteoServlet() {
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
       	
       	String ref = "";
       	Bastanteo bastanteo = new Bastanteo();
      	Escritura escritura = new Bastanteo().new Escritura();
       	String errorString = null;
       	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       	try {
			if (multipartParams.getParametres().get("dataBastanteo") != null && ! multipartParams.getParametres().get("dataBastanteo").isEmpty()) {
				bastanteo.setDatabastanteo(formatter.parse(multipartParams.getParametres().get("dataBastanteo")));
			}
			bastanteo.setEmpresa(EmpresaCore.findEmpresa(conn, multipartParams.getParametres().get("llistaEmpreses")));			
			bastanteo.setPersonaFacultada(multipartParams.getParametres().get("personaFacultada"));
			bastanteo.setCarrec(multipartParams.getParametres().get("carrec"));		
			ref = BastanteosCore.nouBastanteo(conn, bastanteo);
			escritura.setEscritura(multipartParams.getParametres().get("escritura"));
			if (multipartParams.getParametres().get("dataEscritura") != null && ! multipartParams.getParametres().get("dataEscritura").isEmpty()) {
				escritura.setDataEscritura(formatter.parse(multipartParams.getParametres().get("dataEscritura")));
			}
			escritura.setNumProtocol(multipartParams.getParametres().get("nProtocol"));	
			escritura.setNotari(multipartParams.getParametres().get("notari"));
			BastanteosCore.novaEscritura(conn, escritura, ref);
		} catch (ParseException e) {
			errorString = e.toString();
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("bastanteo", bastanteo);
 
 
		// If error, forward to Edit page.
		if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/bastanteos/createBastanteoView.jsp");
           dispatcher.forward(request, response);
		}
        
		// If everything nice.
		// Redirect to the product listing page.            
		else {
           response.sendRedirect(request.getContextPath() + "/bastanteos");
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
