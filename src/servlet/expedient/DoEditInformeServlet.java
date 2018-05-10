package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import bean.AssignacioCredit;
import bean.Expedient;
import bean.InformeActuacio;
import bean.User;
import bean.InformeActuacio.PropostaInforme;
import core.ActuacioCore;
import core.ExpedientCore;
import core.InformeCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditInformeServlet
 */
@WebServlet("/doEditInforme")
public class DoEditInformeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditInformeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
       	Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		User Usuari = MyUtils.getLoginedUser(request.getSession());	
       	String refExp = multipartParams.getParametres().get("expedient");
       	String refExpNou = multipartParams.getParametres().get("expedientNou");
       	String idInforme = multipartParams.getParametres().get("informe");
       	Expedient expedient = new Expedient();
       	String errorString = null;       	
     	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     	InformeActuacio informe = new InformeActuacio();
       	try {       		
       		informe =  InformeCore.getInformePrevi(conn, idInforme, false);   
       		InformeCore.modificarTecnic(conn, informe, Integer.parseInt(multipartParams.getParametres().get("llistaUsuaris"))); 
       		if (multipartParams.getParametres().get("dataCreacio") != null && ! multipartParams.getParametres().get("dataCreacio").isEmpty()) {
				InformeCore.modificarDataCreacio(conn, informe, formatter.parse(multipartParams.getParametres().get("dataCreacio")));
			}
       		
    	    //Proposta    	    
    	    PropostaInforme proposta = informe.getPropostaInformeSeleccionada();
    	    proposta.setObjecte(multipartParams.getParametres().get("descripcio"));
		    proposta.setTipusObra(multipartParams.getParametres().get("tipusContracte"));
		    if ("obr".equals(multipartParams.getParametres().get("tipusContracte"))) {
		    	 proposta.setLlicencia(multipartParams.getParametres().get("reqLlicencia").equals("si"));
				 proposta.setTipusLlicencia(multipartParams.getParametres().get("tipusLlicencia"));
		    } else {
		    	 proposta.setLlicencia(false);
				 proposta.setTipusLlicencia("");
		    }
		    proposta.setContracte(true);
		    proposta.setPbase(Double.parseDouble(multipartParams.getParametres().get("pbase").replace(',','.')));
		    proposta.setIva(Double.parseDouble(multipartParams.getParametres().get("iva").replace(',','.')));
		    proposta.setPlic(Double.parseDouble(multipartParams.getParametres().get("plic").replace(',','.')));
		    proposta.setTermini(multipartParams.getParametres().get("termini"));
		    proposta.setComentari(multipartParams.getParametres().get("comentariTecnic"));
		    if (proposta.getIdProposta() == null) {
		    	String idProposta = InformeCore.novaProposta(conn, proposta, idInforme);
		    	InformeCore.seleccionarProposta(conn, idProposta, idInforme);
		    } else {
		    	InformeCore.modificarProposta(conn, proposta);
		    }
		    if (multipartParams.getFitxersByName().get("informe") != null) Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("informe")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "Informe Previ", "", "", idInforme, "", Usuari.getIdUsuari());
		    if (multipartParams.getFitxersByName().get("propostaActuacio") != null) Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("propostaActuacio")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Proposta actuació", Usuari.getIdUsuari());
		    
		    //Vistiplau cap
		   /* Date dataVistiPlau = null;
		    if (multipartParams.getParametres().get("dataVistiplau") != null && ! multipartParams.getParametres().get("dataVistiplau").isEmpty()) dataVistiPlau = formatter.parse(multipartParams.getParametres().get("dataVistiplau"));
		    if (!multipartParams.getParametres().get("llistaCaps").equals("-1")) {
		    	InformeCore.validacioCapInforme(conn, idInforme, Integer.parseInt(multipartParams.getParametres().get("llistaCaps")), multipartParams.getParametres().get("comentariCap"), dataVistiPlau);				
		    } else if (informe.getUsuariCapValidacio() != null) {
		    	InformeCore.eliminarValidacioCapInforme(conn, idInforme);
		    }
		    if (multipartParams.getFitxersByName().get("vistiplauProposta") != null) Fitxers.guardarFitxer(Arrays.asList(multipartParams.getFitxersByName().get("vistiplauProposta")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Vistiplau cap");*/
		    
		    //Informe supervisió
			if (multipartParams.getFitxersByName().get("informeSupervisio") != null) Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("informeSupervisio")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Informe Supervisió", Usuari.getIdUsuari());
		    
			informe.setNotes(multipartParams.getParametres().get("notes"));
			InformeCore.modificarNotes(conn, informe, multipartParams.getParametres().get("notes"));
			
		    //Partida
		    informe.setComentariPartida(multipartParams.getParametres().get("comentariFinancer"));
		    AssignacioCredit assignacio = informe.getAssignacioCredit();
    	    assignacio.setBei(multipartParams.getParametres().get("bei") != null);
    	    assignacio.setFeder(multipartParams.getParametres().get("feder") != null);    	 
    	    InformeCore.modificarPartida(conn, informe, multipartParams.getParametres().get("llistaPartides"), Usuari.getIdUsuari(), assignacio);    	     
    	    if (multipartParams.getFitxersByName().get("conformeAreaEconomica") != null) Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("conformeAreaEconomica")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Conforme àrea financera", Usuari.getIdUsuari());
    	  
    	    //Autorització
    	    boolean aprovar = false;
    	    if (multipartParams.getParametres().get("dataAprovacio") != null && ! multipartParams.getParametres().get("dataAprovacio").isEmpty()) {
    	    	InformeCore.aprovacioInforme(conn, idInforme, Usuari.getIdUsuari(), formatter.parse(multipartParams.getParametres().get("dataAprovacio")));  
    	    	aprovar = true;
    	    }
			if (multipartParams.getFitxersByName().get("autoritzacioActuacio") != null) {
				Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("autoritzacioActuacio")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Autorització Proposta d'actuació", Usuari.getIdUsuari());
				aprovar = true;
			}
			if (aprovar) {
				ActuacioCore.aprovarPA(conn, informe.getActuacio().getReferencia(), Usuari.getIdUsuari());	
			} else if(InformeCore.getAutoritzacioPropostaActuacio(conn, informe.getIdIncidencia(), informe.getActuacio().getReferencia(), idInforme).getRuta() == null) {
				ActuacioCore.eliminarAprovacioPA(conn, informe.getActuacio().getReferencia());	
				InformeCore.eliminarAprovacioInforme(conn, idInforme);  
			}
			
			//Autorització Consell de Govern
			 if (multipartParams.getFitxersByName().get("autoritzacioConsellDeGovern") != null) Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("autoritzacioConsellDeGovern")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Autorització Consell De Govern", Usuari.getIdUsuari());
			//Autorització Conseller
			 if (multipartParams.getFitxersByName().get("autoritzacioConseller") != null) Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("autoritzacioConseller")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Autorització Conseller", Usuari.getIdUsuari());
			 
			 if (multipartParams.getFitxersByName().get("documentBOIB") != null) Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("documentBOIB")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Document BOIB", Usuari.getIdUsuari());
			 if (multipartParams.getParametres().get("dataPublicacioBOIB") != null && ! multipartParams.getParametres().get("dataPublicacioBOIB").isEmpty()) {
					InformeCore.modificarDataPublicacioBOIB(conn, informe, formatter.parse(multipartParams.getParametres().get("dataPublicacioBOIB")));
				}
			 if (multipartParams.getFitxersByName().get("correuInvitacio") != null) Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("correuInvitacio")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Correu invitació", Usuari.getIdUsuari());
			 
    	   
    	    if (!refExp.equals(refExpNou)) {
    	    	ExpedientCore.actualitzarCodiExpedient(conn, refExp, refExpNou, idInforme);
    	    }
    	    expedient = ExpedientCore.findExpedient(conn, refExp);
		} catch (SQLException | NamingException | ParseException e) {
			errorString = e.toString();
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("expedient", expedient);
 
 
		// If error, forward to Edit page.
		if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/expedients/editInformeView.jsp");
           dispatcher.forward(request, response);
		}
        
		// If everything nice.
		// Redirect to the product listing page.            
		else {
			if (multipartParams.getParametres().get("redireccio").equals("actuacions")) {
				response.sendRedirect(request.getContextPath() + "/actuacionsDetalls?ref=" + informe.getActuacio().getReferencia());
			}else{
				response.sendRedirect(request.getContextPath() + "/expedient?ref=" + refExpNou);
			}
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
