package servlet.registre;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Registre;
import core.RegistreCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditRegistreServlet
 */
@WebServlet("/DoEditRegistre")
public class DoEditRegistreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditRegistreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);		
	    String idRegistre = request.getParameter("idCodiRegistre");
	    String entradaSortida = request.getParameter("entradaSortida");
	    String remDes = request.getParameter("remitent");
	    String tipus = URLDecoder.decode(request.getParameter("tipus"),  "UTF-8");	
	    String contingut = request.getParameter("contingut");	    
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");	    
	    Date peticio = new Date();
	    String errorString = null;
	    Registre registre = new Registre();
		try {
			peticio = formatter.parse(request.getParameter("peticio"));
			registre = RegistreCore.findRegistre(conn, entradaSortida, idRegistre);
			registre.setRemDes(remDes);
			registre.setTipus(tipus);
			registre.setContingut(contingut);
			registre.setData(peticio);
		    RegistreCore.modificarRegistre(conn, registre, entradaSortida);
		} catch (ParseException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			errorString = e1.toString();
		}   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	   	request.setAttribute("registre", registre);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/registre/registreEditView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   			response.sendRedirect(request.getContextPath() + "/registre?tipus=" + entradaSortida + "&referencia=" + idRegistre);
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
