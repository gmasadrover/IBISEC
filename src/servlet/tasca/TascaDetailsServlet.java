package servlet.tasca;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import bean.Empresa;
import bean.Factura;
import bean.Historic;
import bean.Incidencia;
import bean.Oferta;
import bean.InformeActuacio;
import bean.InformeActuacio.PropostaInforme;
import bean.Judicial;
import bean.Judicial.Tramitacio;
import bean.Partida;
import bean.Tasca;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.CreditCore;
import core.EmpresaCore;
import core.FacturaCore;
import core.IncidenciaCore;
import core.InformeCore;
import core.JudicialCore;
import core.OfertaCore;
import core.TascaCore;
import core.UsuariCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class IncidenciaDetailsServlet
 */
@WebServlet("/tasca")
public class TascaDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TascaDetailsServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
 	   if (MyUtils.getLoginedUser(request.getSession()) == null){
 		   response.sendRedirect(request.getContextPath() + "/preLogin");
 	   }else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.tasques_detalls)) {
  		response.sendRedirect(request.getContextPath() + "/");	
 	   }else{
 		   int idTasca = Integer.parseInt(request.getParameter("id"));
 	       String errorString = null;
 	       Actuacio actuacio = new Actuacio();
 	       List<InformeActuacio> informes = new ArrayList<InformeActuacio>();
 	       Incidencia incidencia = new Incidencia();
 	       Tasca tasca = new Tasca();
 	       boolean esCap = false;
 	       boolean isGerencia = false;
 	       InformeActuacio informeActuacioPrevi = new InformeActuacio();
 	       InformeActuacio informeActuacioOriginal = new InformeActuacio();
 	       List<PropostaInforme> propostaInformeList = new ArrayList<PropostaInforme>();
 	       List<Historic> historial = new ArrayList<Historic>();
 	       List<Partida> partidesList = new ArrayList<Partida>();
 	       List<Empresa> empresesList = new ArrayList<Empresa>();
 	       List<User> llistaUsuaris = new ArrayList<User>(); 	    
 	       boolean canRealitzarTasca = false;
 	       boolean canModificarFactura = false;
 	       boolean canBorrarTasca = false;
 	       boolean areaJuridica = false;
 	       List<Oferta> ofertes = new ArrayList<Oferta>();
	       Oferta ofertaSeleccionada = new Oferta();
	       Factura factura = new Factura();
	       Judicial procediment = new Judicial();
	       Tramitacio tramitacio = procediment.new Tramitacio();
	       String totalAdjudicatEmpresa = "";
 	       try {
 	    	   canBorrarTasca = usuari.getRol().contains("ADMIN");
 	    	   tasca = TascaCore.findTascaId(conn, idTasca, usuari.getIdUsuari());
 	    	   incidencia = tasca.getIncidencia();
 	    	   if (usuari.getIdUsuari() == tasca.getUsuari().getIdUsuari()) TascaCore.llegirNotificacio(conn, tasca.getIdTasca());
 	    	   if (usuari.getIdUsuari()==4 && tasca.getUsuari().getIdUsuari()==1) TascaCore.llegirNotificacio(conn, tasca.getIdTasca());
 	    	   actuacio = tasca.getActuacio();  	    	   
 	    	   if (actuacio != null) {
 	    		   if (actuacio.getCentre() != null && actuacio.getCentre().getIdCentre() != null && actuacio.getCentre().getIdCentre().equals("9999PERSO") && !UsuariCore.hasPermision(conn, usuari, SectionPage.personal)) response.sendRedirect(request.getContextPath() + "/");
 	 	    	   informes = InformeCore.getInformesActuacio(conn, actuacio.getReferencia());
 	 	    	   if (!actuacio.getReferencia().isEmpty()) {
 	 	    		  incidencia = IncidenciaCore.findIncidencia(conn, actuacio.getIdIncidencia());
 	 	    		  //actuacio.setArxiusAdjunts(Fitxers.ObtenirTotsFitxers(incidencia.getIdIncidencia()));
 	 	    		  historial = TascaCore.findHistorial(conn, idTasca, incidencia.getIdIncidencia(), actuacio.getReferencia());
 	 	    		  ofertes = OfertaCore.findOfertes(conn, tasca.getIdinforme());
 	 	    		  ofertaSeleccionada = OfertaCore.findOfertaSeleccionada(conn, tasca.getIdinforme());
 	 	    	   }else{
 	 	    		  historial = TascaCore.findHistorial(conn, idTasca, incidencia.getIdIncidencia(), "");
 	 	    	   } 	
 	    	   } else {
 	    		  historial = TascaCore.findHistorial(conn, idTasca, "-1", "");
 	    	   } 	    	  
 	    	   llistaUsuaris = UsuariCore.findUsuarisByDepartament(conn, tasca.getDepartament());
 	    	   if (usuari.getRol().contains("CAP")) {
 	    		   llistaUsuaris = UsuariCore.llistaUsuaris(conn, true); 
 	    	   }
 	    	   canRealitzarTasca = usuari.getDepartament().equals(tasca.getDepartament()) || usuari.getRol().contains("ADMIN") || usuari.getRol().contains("MANUAL"); 
 	    	   String tipusTasca = tasca.getTipus();
 	    	   if ((tasca.getDepartament().equals(usuari.getDepartament()) && usuari.getRol().contains("CAP")) || usuari.getRol().contains("MANUAL")) esCap = true;
 	    	   if (usuari.getRol().contains("GER") || usuari.getRol().contains("ADMIN")) isGerencia = true;
 	    	   if ("infPrev".equals(tipusTasca) || "solInfPrev".equals(tipusTasca) || "vistInfPrev".equals(tipusTasca)) { 	    		  
 	    		  informeActuacioPrevi = InformeCore.getInformePrevi(conn, tasca.getIdinforme(), true);
 	    		  if (informeActuacioPrevi.getIdInf() != null) {
 	    			  propostaInformeList = informeActuacioPrevi.getLlistaPropostes();
 	    		  }
 	    		  if (!esCap) {
	 	    		  llistaUsuaris.clear();
	 	    		  llistaUsuaris.add(UsuariCore.finCap(conn, tasca.getDepartament()));
 	    		  }
 	    	   }else if ("contracte".equals(tipusTasca)) {
 	    		  if (usuari.getRol().contains("CONTA")) {
 	    			  llistaUsuaris.clear();
 	    			  llistaUsuaris.addAll(UsuariCore.llistaUsuaris(conn, true));
	    		  } 
 	    	   }else if ("doctecnica".equals(tipusTasca) || "vistDocTecnica".equals(tipusTasca)) { 
 	    		  String tascaInforme = tasca.getIdinforme();
  	    		  informeActuacioPrevi = InformeCore.getInformePrevi(conn, tascaInforme, true);  
  	    		  if (informeActuacioPrevi.getIdInf() != null) {
	    			  propostaInformeList = informeActuacioPrevi.getLlistaPropostes();
	    		  }
  	    		  llistaUsuaris.clear();
	    		  llistaUsuaris.addAll(UsuariCore.llistaUsuaris(conn, true));
 	    	  }else if ("docprelicitacio".equals(tipusTasca)) {
 	    		  areaJuridica = usuari.getRol().contains("JUR");
 	    		  informeActuacioPrevi = InformeCore.getInformePrevi(conn, tasca.getIdinforme(), true); 	    		  
	    		  if (informeActuacioPrevi.getIdInf() != null) {
	    			  propostaInformeList = informeActuacioPrevi.getLlistaPropostes();
	    		  }
	    		  llistaUsuaris.clear();
	    		  llistaUsuaris.addAll(UsuariCore.llistaUsuaris(conn, true));
 	    	   }else if ("modificacio".equals(tipusTasca) || "penalitzacio".equals(tipusTasca)) {
 	    		  informeActuacioPrevi = InformeCore.getModificacioTasca(conn, idTasca); 
 	    		  informeActuacioOriginal = InformeCore.getInformePrevi(conn, informeActuacioPrevi.getIdInfOriginal(), false);
 	    		  if (!esCap) {
	 	    		  llistaUsuaris.clear();
	 	    		  llistaUsuaris.add(UsuariCore.finCap(conn, tasca.getDepartament()));
 	    		  } 
 	    	   }else if ("ratClassificacio".equals(tipusTasca)) {
 	    		  informeActuacioPrevi = InformeCore.getInformePrevi(conn, tasca.getIdinforme(), true); 	    		  
 	    	   }else if ("autoritModificacio".equals(tipusTasca)){ 	  
 	    		  String tascaInforme = tasca.getIdinforme();
 	    		  informeActuacioPrevi = InformeCore.getMoficacioInforme(conn, tascaInforme); 	  
 	    		  informeActuacioOriginal = InformeCore.getInformePrevi(conn, informeActuacioPrevi.getIdInfOriginal(), false);
 	    		  if (!esCap) {
	 	    		  llistaUsuaris.clear();
	 	    		  llistaUsuaris.add(UsuariCore.finCap(conn, tasca.getDepartament()));
	    		  }    	   
 	    	   }else if ("autoritzacioActuacio".equals(tipusTasca)){ 	  
 	    		  String tascaInforme = tasca.getIdinforme();
 	    		  informeActuacioPrevi = InformeCore.getInformePrevi(conn, tascaInforme, true);   		  
 	    		  if (!esCap) {
	 	    		  llistaUsuaris.clear();
	 	    		  llistaUsuaris.add(UsuariCore.finCap(conn, tasca.getDepartament()));
	    		  }   
 	    	  }else if ("autoritzacioModificacio".equals(tipusTasca)){ 	  
 	    		  String tascaInforme = tasca.getIdinforme();
 	    		 if (tascaInforme.contains("-MOD-")) {
 	    			 informeActuacioPrevi = InformeCore.getMoficacioInforme(conn, tascaInforme); 	
 	 	    		 informeActuacioOriginal = InformeCore.getInformePrevi(conn, informeActuacioPrevi.getIdInfOriginal(), false);
 	    		  } else {
 	    			  informeActuacioPrevi = InformeCore.getInformePrevi(conn, tascaInforme, true);   	
 	    		  }
 	    		  if (!esCap) {
	 	    		  llistaUsuaris.clear();
	 	    		  llistaUsuaris.add(UsuariCore.finCap(conn, tasca.getDepartament()));
	    		  }   
 	    	   }else if ("resPartidaModificacio".equals(tipusTasca)){ 	  
 	    		  String tascaInforme = tasca.getIdinforme();
 	    		  informeActuacioPrevi = InformeCore.getMoficacioInforme(conn, tascaInforme); 	
 	    		  informeActuacioOriginal = InformeCore.getInformePrevi(conn, informeActuacioPrevi.getIdInfOriginal(), false); 	    		  
 	    		  partidesList = CreditCore.getPartides(conn, false); 	    	
 	    	   }else if ("certificatCredit".equals(tipusTasca) || "certificatCreditGerencia".equals(tipusTasca)){ 	  
 	    		  String tascaInforme = tasca.getIdinforme();
 	    		  informeActuacioPrevi  = InformeCore.getInformePrevi(conn, tascaInforme, true); 		
 	    	   }else if ("resPartida".equals(tipusTasca)){ 	    
 	    		  String tascaInforme = tasca.getIdinforme();
 	    		  informeActuacioPrevi = InformeCore.getInformePrevi(conn, tascaInforme, true); 	    		  
 	    		  partidesList = CreditCore.getPartides(conn, false);
 	    	   }else if ("preLicitacio".equals(tipusTasca) || "ratClassificacio".equals(tipusTasca)) {
 	    		  String tascaInforme = tasca.getIdinforme();
 	    		  informeActuacioPrevi = InformeCore.getInformePrevi(conn, tascaInforme, true); 	    
 	    	   }else if ("liciMenor".equals(tipusTasca)){ 
 	    		  String tascaInforme = tasca.getIdinforme(); 	    		
 	    		  informeActuacioPrevi = InformeCore.getInformePrevi(conn, tascaInforme, true);
 	    		  empresesList = EmpresaCore.getEmpreses(conn); 	    		  
 	    	   }else if ("autoritzacioDespesa".equals(tipusTasca)){ 	
 	    		  String tascaInforme = tasca.getIdinforme();
 	    		  informeActuacioPrevi = InformeCore.getInformePrevi(conn, tascaInforme, true);
 	    		  Calendar cal = Calendar.getInstance(); 
 	    		  Date dataFi = cal.getTime();	
 	    		  dataFi = new Date(dataFi.getTime());
 	    		  cal.set(2018, 2, 9);
 	    		  Date dataIni = cal.getTime();	
 	    		  DecimalFormat num = new DecimalFormat("#,##0.00");
 	    		  if (informeActuacioPrevi.getOfertaSeleccionada() != null) {
 	    			 totalAdjudicatEmpresa = num.format(EmpresaCore.getDespesaEmpresa(conn, informeActuacioPrevi.getOfertaSeleccionada().getCifEmpresa(), dataIni, dataFi));
 	    		  } 	    		  
 	    	   }else if ("notificacio".equals(tipusTasca)) {
 	    		   TascaCore.llegirNotificacio(conn, tasca.getIdTasca());
 	    	   }else if ("vacances".equals(tipusTasca)) {
 	    		  User usuariCap = UsuariCore.finCap(conn, tasca.getDepartament());
 	    		  canRealitzarTasca = usuari.getIdUsuari() == usuariCap.getIdUsuari() || "ADMIN".equals(usuari.getRol());
 	    	   }else if ("conformarFactura".equals(tipusTasca)) {
 	    		   factura = FacturaCore.getFactura(conn, tasca.getIdinforme());
 	    		   informeActuacioPrevi = InformeCore.getInformePrevi(conn, factura.getIdInforme(), true); 	 
 	    		   if (usuari.getRol().contains("CONTA")) {
 	    			   llistaUsuaris.clear();
 	    			   llistaUsuaris.addAll(UsuariCore.llistaUsuaris(conn, true));
 	    		   } 	    		   
 	    	   }else if ("facturaConformada".equals(tipusTasca)) {
	    		   factura = FacturaCore.getFactura(conn, tasca.getIdinforme());
	    		   informeActuacioPrevi = InformeCore.getInformePrevi(conn, factura.getIdInforme(), true); 	
	    		   if (usuari.getRol().contains("CONTA") || usuari.getRol().contains("ADMIN")) {
	    			   canModificarFactura = true;
 	    			   llistaUsuaris.clear();
 	 	    		   llistaUsuaris.addAll(UsuariCore.llistaUsuaris(conn, true));
 	    		   } 	
 	    	  }else if ("revisarCertificacio".equals(tipusTasca) || "firmaCertificacio".equals(tipusTasca) || "certificacioFirmada".equals(tipusTasca)) {
	    		   factura = FacturaCore.getCertificacio(conn, tasca.getIdinforme());
	    		   informeActuacioPrevi = InformeCore.getInformePrevi(conn, factura.getIdInforme(), true); 	
	    		   if (usuari.getRol().contains("CONTA") || usuari.getRol().contains("ADMIN")) {
	    			   canModificarFactura = true;
	    			   llistaUsuaris.clear();
	 	    		   llistaUsuaris.addAll(UsuariCore.llistaUsuaris(conn, true));
	    		   }
	    	   }else if("judicial".equals(tipusTasca) || "pagamentJudicial".equals(tipusTasca)) {
	    		   procediment = JudicialCore.findProcediment(conn, tasca.getIdinforme());	    
	    		   tramitacio = JudicialCore.findTramitacio(conn, tasca.getIdActuacio());	
	    		   tasca.setDocuments(TascaCore.getTascaDocuments(idTasca));
	    	   }else if("factura".equals(tipusTasca)) {
	    		   llistaUsuaris.clear();
 	    		   llistaUsuaris.addAll(UsuariCore.llistaUsuaris(conn, true));
	    	   }
 	    	   
 	       } catch (SQLException | NamingException e) {
 	           e.printStackTrace();
 	           errorString = e.getMessage();
 	       }
 	       
 	       if (request.getParameter("error") != null && !request.getParameter("error").isEmpty()) {
 	    	  errorString = "S'ha de seleccionar l'oferta adjudicatària";
 	       }
 	       // Store info in request attribute, before forward to views
 	       request.setAttribute("errorString", errorString);
 	       request.setAttribute("actuacio", actuacio);
 	       request.setAttribute("informes", informes);
 	       request.setAttribute("incidencia", incidencia);
 	       request.setAttribute("tasca", tasca);
 	       request.setAttribute("esCap", esCap);
 	       request.setAttribute("isGerencia", isGerencia);
 	       request.setAttribute("historial", historial);
 	       request.setAttribute("informePrevi", informeActuacioPrevi);
 	       request.setAttribute("informeActuacioOriginal", informeActuacioOriginal);
 	       request.setAttribute("propostesInformeList", propostaInformeList);
 	       request.setAttribute("partidesList", partidesList);
 	       request.setAttribute("empresesList", empresesList);
 	       request.setAttribute("llistaUsuaris", llistaUsuaris);
 	       request.setAttribute("ofertes", ofertes);
	       request.setAttribute("ofertaSeleccionada", ofertaSeleccionada);
	       request.setAttribute("factura", factura);
 	       request.setAttribute("canRealitzarTasca", canRealitzarTasca);
 	       request.setAttribute("canRealitzarPropostaTecnica", true);
 	       request.setAttribute("canModificarFactura", canModificarFactura);
 	       request.setAttribute("canBorrarTasca", canBorrarTasca);
 	       request.setAttribute("areaJuridica", areaJuridica);
 	       request.setAttribute("procediment", procediment);
 	       request.setAttribute("tramitacioJudicial", tramitacio);
 	       request.setAttribute("totalAdjudicatEmpresa", totalAdjudicatEmpresa);
 	       request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Tasques"));
 	       request.setAttribute("idUsuariLogg", usuari.getIdUsuari());

 	       RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/tasca/tascaView.jsp");
 	        
 	       dispatcher.forward(request, response);
 	   }
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
