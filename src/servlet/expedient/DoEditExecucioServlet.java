package servlet.expedient;

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

import bean.Expedient;
import core.ExpedientCore;
import utils.Fitxers;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/doEditExecucio" })
public class DoEditExecucioServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public DoEditExecucioServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       	Connection conn = MyUtils.getStoredConnection(request);
       	Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
       	
       	String refExp = multipartParams.getParametres().get("expedient");
       	Expedient expedient = new Expedient();
       	String errorString = null;
       	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       	try {
			expedient.setExpContratacio(refExp);			
			if (multipartParams.getParametres().get("dataPerfilContratant") != null && ! multipartParams.getParametres().get("dataPerfilContratant").isEmpty()) {
				expedient.setDataPublicacioPerfilContratant(formatter.parse(multipartParams.getParametres().get("dataPerfilContratant")));
			}			
			if (multipartParams.getParametres().get("dataLimitOfertes") != null && ! multipartParams.getParametres().get("dataLimitOfertes").isEmpty()) {
				expedient.setDataLimitPresentacio(formatter.parse(multipartParams.getParametres().get("dataLimitOfertes")));
			}
			if (multipartParams.getParametres().get("dataAdjudicacio") != null && ! multipartParams.getParametres().get("dataAdjudicacio").isEmpty()) {
				expedient.setDataAdjudicacio(formatter.parse(multipartParams.getParametres().get("dataAdjudicacio")));
			}
			if (multipartParams.getParametres().get("dataFirma") != null && ! multipartParams.getParametres().get("dataFirma").isEmpty()) {
				expedient.setDataFormalitzacioContracte(formatter.parse(multipartParams.getParametres().get("dataFirma")));
			}
			if (multipartParams.getParametres().get("dataIniciObra") != null && ! multipartParams.getParametres().get("dataIniciObra").isEmpty()) {
				expedient.setDataIniciExecucio(formatter.parse(multipartParams.getParametres().get("dataIniciObra")));
			}	
			ExpedientCore.updateExpedient(conn, expedient);
		} catch (ParseException e) {
			errorString = e.toString();
		}
       	
		

 
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("expedient", expedient);
 
 
		// If error, forward to Edit page.
		if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/expedients/editExecucioView.jsp");
           dispatcher.forward(request, response);
		}
        
		// If everything nice.
		// Redirect to the product listing page.            
		else {
           response.sendRedirect(request.getContextPath() + "/expedient?ref=" + expedient.getExpContratacio());
		}
   	}
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}
