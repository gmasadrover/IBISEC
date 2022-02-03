package servlet.expedient;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		User Usuari = MyUtils.getLoginedUser(request.getSession());	
       	String refExp = multipartParams.getParametres().get("expedient");
       	String refExpNou = multipartParams.getParametres().get("expedientNou");
       	String idInforme = multipartParams.getParametres().get("informe");
       	Expedient expedient = new Expedient();
       	String errorString = null;       	
     	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     	InformeActuacio informe = new InformeActuacio();
     	boolean tramitacioIBISEC = "on".equals(multipartParams.getParametres().get("tramitacioIBISEC"));
		boolean tramitacioConselleria = "on".equals(multipartParams.getParametres().get("tramitacioConselleria"));
		boolean tramitacioAltres = "on".equals(multipartParams.getParametres().get("tramitacioAltres"));
		boolean cessioCredit = "on".equals(multipartParams.getParametres().get("esCessioDeCredit"));
       	try {       		
       		informe =  InformeCore.getInformePrevi(conn, idInforme, false);   
       		//InformeCore.modificarTecnic(conn, informe, Integer.parseInt(multipartParams.getParametres().get("llistaUsuaris"))); 
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
		    proposta.setVec(Double.parseDouble(multipartParams.getParametres().get("pbase").replace(',','.')));
		    proposta.setIva(Double.parseDouble(multipartParams.getParametres().get("iva").replace(',','.')));
		    proposta.setPlic(Double.parseDouble(multipartParams.getParametres().get("plic").replace(',','.')));
		    double vec = Double.parseDouble(multipartParams.getParametres().get("vec").replace(',','.'));
		    if (vec > 0) proposta.setVec(Double.parseDouble(multipartParams.getParametres().get("vec").replace(',','.')));
		    proposta.setTermini(multipartParams.getParametres().get("termini"));
		    proposta.setComentari(multipartParams.getParametres().get("comentariTecnic"));
		    proposta.setComentariAdministratiu(multipartParams.getParametres().get("comentariAdministratiu"));
		    if (proposta.getIdProposta() == null) {
		    	String idProposta = InformeCore.novaProposta(conn, proposta, idInforme);
		    	InformeCore.seleccionarProposta(conn, idProposta, idInforme);
		    } else {
		    	InformeCore.modificarProposta(conn, proposta);
		    }
		    
		    if (proposta.getTipusObra().equals("conveni")) {
		    	String dependencies = "";				    	
		    	if (tramitacioIBISEC) dependencies += "IBISEC#";
		    	if (tramitacioConselleria) dependencies += "Conselleria#";
		    	if (tramitacioAltres) dependencies += "Altres#";
		    	informe.setCessioCredit(cessioCredit);
		    	
		    	if (multipartParams.getParametres().get("dataContracte") != null && ! multipartParams.getParametres().get("dataContracte").isEmpty()) {
		    		expedient = ExpedientCore.findExpedient(conn, informe.getExpcontratacio().getExpContratacio());
		    		expedient.setDataFormalitzacioContracte(formatter.parse(multipartParams.getParametres().get("dataContracte"))); 
		    		ExpedientCore.updateExpedient(conn, expedient);
		    	}
		    	
				    	
		    	if (multipartParams.getParametres().get("dataPublicacioRegistreConvenis") != null && ! multipartParams.getParametres().get("dataPublicacioRegistreConvenis").isEmpty()) {
		    		informe.setDataPublicacioRegitreConvenis(formatter.parse(multipartParams.getParametres().get("dataPublicacioRegistreConvenis"))); 
		    	}
		    	else {
		    		informe.setDataPublicacioRegitreConvenis(null);
		    	}
		    	if (multipartParams.getParametres().get("dataPublicacioBOIB") != null && ! multipartParams.getParametres().get("dataPublicacioBOIB").isEmpty()) {
		    		informe.setPublicacioBOIB(formatter.parse(multipartParams.getParametres().get("dataPublicacioBOIB")));
				}else {
		    		informe.setPublicacioBOIB(null);
		    	}
		    	if (multipartParams.getParametres().get("dataPublicacioDGP") != null && ! multipartParams.getParametres().get("dataPublicacioDGP").isEmpty()) {
		    		informe.setDataPublicacioDGPressuposts(formatter.parse(multipartParams.getParametres().get("dataPublicacioDGP")));
		    	}else {
		    		informe.setDataPublicacioDGPressuposts(null);
		    	}
		    	if (multipartParams.getParametres().get("dataPublicacioDGT") != null && ! multipartParams.getParametres().get("dataPublicacioDGT").isEmpty()) {
			    	informe.setDataPublicacioDGTresoreria(formatter.parse(multipartParams.getParametres().get("dataPublicacioDGT")));
		    	}else {
		    		informe.setDataPublicacioDGTresoreria(null);
		    	}
		    	
		      	InformeCore.modificarDataPublicacio(conn, informe); 		    	
		    	InformeCore.moficarDependencies(conn, informe.getIdInf(), dependencies);
		    	InformeCore.modiciarCessioCredit(conn, informe.getIdInf(), cessioCredit);
		    	
		    	if (multipartParams.getFitxersByName().get("contracte") != null) {
	    	    	Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("contracte")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Contracte signat", Usuari.getIdUsuari());
	    	    	InformeCore.modificarEstat(conn, idInforme, "execucio");
	    	    }
		    	
		    	
		    }
		    
		    
		    if (multipartParams.getParametres().get("dataPublicacioPerfilContractant") != null && ! multipartParams.getParametres().get("dataPublicacioPerfilContractant").isEmpty()) {
	    		informe.setDataPublicacioPerfilContractant(formatter.parse(multipartParams.getParametres().get("dataPublicacioPerfilContractant")));
	    		InformeCore.modificarDataPublicacio(conn, informe);  
		    }else {
	    		informe.setDataPublicacioPerfilContractant(null);
	    	}
		    
		    if (multipartParams.getFitxersByName().get("informe") != null) Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("informe")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "Informe previ", "", "", idInforme, "", Usuari.getIdUsuari());
		    if (multipartParams.getFitxersByName().get("propostaActuacio") != null) Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("propostaActuacio")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Proposta actuació", Usuari.getIdUsuari());
		    
		
		    //Informe supervisió
			if (multipartParams.getFitxersByName().get("informeSupervisio") != null) Fitxers.guardarFitxer(conn, Arrays.asList(multipartParams.getFitxersByName().get("informeSupervisio")).get(0), informe.getIdIncidencia(), informe.getActuacio().getReferencia(), "", "", "", idInforme, "Informe Supervisió", Usuari.getIdUsuari());
		    
			informe.setNotes(multipartParams.getParametres().get("notes"));
			InformeCore.modificarNotes(conn, informe, multipartParams.getParametres().get("notes"));
			
		    //Partida
		    informe.setComentariPartida(multipartParams.getParametres().get("comentariFinancer"));
		  
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
		} catch (ParseException e) {
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
