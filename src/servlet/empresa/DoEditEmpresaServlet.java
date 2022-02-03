package servlet.empresa;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Empresa;
import bean.User;
import core.EmpresaCore;
import utils.Fitxers;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/doEditEmpresa" })
public class DoEditEmpresaServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public DoEditEmpresaServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       	Connection conn = MyUtils.getStoredConnection(request);
       	User usuari = MyUtils.getLoginedUser(request.getSession());
       
       	Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		String extincio = multipartParams.getParametres().get("extincio");
		String succecio = multipartParams.getParametres().get("succecio");
		String prohibicio = multipartParams.getParametres().get("prohibicio");
		String concurs = multipartParams.getParametres().get("concurs");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dataFinsProhibicio = null;
		Date dataConcurs = null;
       	String cif = multipartParams.getParametres().get("newcif");
       	if (cif == null) cif = multipartParams.getParametres().get("cif");
       	Boolean isUte = "true".equals(multipartParams.getParametres().get("isute"));       	
       	Empresa empresa = new Empresa();
      
       	
       	if (extincio != null) {
       		String motiuExtincio = multipartParams.getParametres().get("motiuextincio");
			EmpresaCore.extincioEmpresa(conn, cif, motiuExtincio);
			empresa.setCif(cif);
			//Guardar adjunts
			EmpresaCore.guardarFitxer(conn, Usuari.getIdUsuari(), multipartParams.getFitxers(), empresa.getCif(), "");
       	} else if (succecio != null) {
       		String cifSuccesora = multipartParams.getParametres().get("cifsuccesora");
       		EmpresaCore.addSuccesora(conn, cif, cifSuccesora);
			empresa.setCif(cif);
			//Guardar adjunts
			EmpresaCore.guardarFitxer(conn, Usuari.getIdUsuari(), multipartParams.getFitxers(), empresa.getCif(), "");
       	} else if (prohibicio != null) {
       		try {       			
       			if (multipartParams.getParametres().get("dateLimitProhibicio") != null && ! multipartParams.getParametres().get("dateLimitProhibicio").isEmpty()) dataFinsProhibicio = formatter.parse(multipartParams.getParametres().get("dateLimitProhibicio"));
				EmpresaCore.prohibicioContractarEmpresa(conn, cif, dataFinsProhibicio);	
				//Guardar adjunts
			   	EmpresaCore.guardarFitxer(conn, Usuari.getIdUsuari(), multipartParams.getFitxers(), empresa.getCif(), "");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   		} else if (concurs != null) {       		
       		try {
       			String infoConcurs =  multipartParams.getParametres().get("infoConcurs");
       			boolean isIntervencio = "on".equals(multipartParams.getParametres().get("infoConcurs"));
       			String infoIntervencio = multipartParams.getParametres().get("infoIntervencio");
       			if (multipartParams.getParametres().get("dateConcurs") != null && ! multipartParams.getParametres().get("dateConcurs").isEmpty()) dataConcurs = formatter.parse(multipartParams.getParametres().get("dateConcurs"));
				EmpresaCore.addConcurs(conn, cif, infoConcurs, dataConcurs, isIntervencio, infoIntervencio);				
				//Guardar adjunts
			   	EmpresaCore.guardarFitxer(conn, Usuari.getIdUsuari(), multipartParams.getFitxers(), empresa.getCif(), "");
				empresa.setCif(cif);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       	} else {       	
			String name = multipartParams.getParametres().get("name");
			String direccio = multipartParams.getParametres().get("direccio");
			String cp = multipartParams.getParametres().get("cp");
			String ciutat = "";
			if (multipartParams.getParametres().get("localitat") != null) ciutat = URLDecoder.decode(multipartParams.getParametres().get("localitat"), "UTF-8");
			String provincia = "";
			if (multipartParams.getParametres().get("provincia") != null) provincia = URLDecoder.decode(multipartParams.getParametres().get("provincia").split("_")[1], "UTF-8");
			String telefon = multipartParams.getParametres().get("telefon");
			String fax = multipartParams.getParametres().get("fax");
			String email = multipartParams.getParametres().get("email");	
			String tipus =  multipartParams.getParametres().get("tipus");	
			String classificacio = multipartParams.getParametres().get("llistatClassificacio");		
			String informacioAdicional = multipartParams.getParametres().get("informacioAdicional");		
			
			Boolean isPime = "on".equals(multipartParams.getParametres().get("pime"));
			
			
			Date dataConstitucio = null;
			Date dateExpAcreditacio1 = null;
			Date dateExpAcreditacio2 = null;
			Date dateExpAcreditacio3 = null;
			Date dataRegistreMercantil = null;
			Date dataVigenciaClassificacioROLECE = null;
			Date dataVigenciaClassificacioJCCaib = null;
			Date dataVigenciaClassificacioJCA = null;
			
			SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
			Date dataExerciciEconomic = null;
			
			double ratioAP = 0;
			if (multipartParams.getParametres().get("ratioAP") != null && ! multipartParams.getParametres().get("ratioAP").isEmpty()) ratioAP = Double.parseDouble(multipartParams.getParametres().get("ratioAP"));
			
			List<Empresa.Administrador> administradors = new ArrayList<Empresa.Administrador>();
			
			try {
				if (multipartParams.getParametres().get("constitucio") != null && ! multipartParams.getParametres().get("constitucio").isEmpty()) dataConstitucio = formatter.parse(multipartParams.getParametres().get("constitucio"));
				if (multipartParams.getParametres().get("dateExpAcreditacio1") != null && ! multipartParams.getParametres().get("dateExpAcreditacio1").isEmpty()) dateExpAcreditacio1 = formatter.parse(multipartParams.getParametres().get("dateExpAcreditacio1"));
				if (multipartParams.getParametres().get("dateExpAcreditacio2") != null && ! multipartParams.getParametres().get("dateExpAcreditacio2").isEmpty()) dateExpAcreditacio2 = formatter.parse(multipartParams.getParametres().get("dateExpAcreditacio2"));
				if (multipartParams.getParametres().get("dateExpAcreditacio3") != null && ! multipartParams.getParametres().get("dateExpAcreditacio3").isEmpty()) dateExpAcreditacio3 = formatter.parse(multipartParams.getParametres().get("dateExpAcreditacio3"));
				if (multipartParams.getParametres().get("dataRegistreMercantil") != null && ! multipartParams.getParametres().get("dataRegistreMercantil").isEmpty()) dataRegistreMercantil = formatter.parse(multipartParams.getParametres().get("dataRegistreMercantil"));
				if (multipartParams.getParametres().get("dataExerciciEconomic") != null && ! multipartParams.getParametres().get("dataExerciciEconomic").isEmpty()) dataExerciciEconomic = formatterYear.parse(multipartParams.getParametres().get("dataExerciciEconomic"));
				if (multipartParams.getParametres().get("dataVigenciaClassificacioROLECE") != null && ! multipartParams.getParametres().get("dataVigenciaClassificacioROLECE").isEmpty()) dataVigenciaClassificacioROLECE = formatter.parse(multipartParams.getParametres().get("dataVigenciaClassificacioROLECE"));
				if (multipartParams.getParametres().get("dataVigenciaClassificacioJCCaib") != null && ! multipartParams.getParametres().get("dataVigenciaClassificacioJCCaib").isEmpty()) dataVigenciaClassificacioJCCaib = formatter.parse(multipartParams.getParametres().get("dataVigenciaClassificacioJCCaib"));
				if (multipartParams.getParametres().get("dataVigenciaClassificacioJCA") != null && ! multipartParams.getParametres().get("dataVigenciaClassificacioJCA").isEmpty()) dataVigenciaClassificacioJCA = formatter.parse(multipartParams.getParametres().get("dataVigenciaClassificacioJCA"));
				
				empresa.setCif(cif);
				empresa.setName(name);
				empresa.setDireccio(direccio);
				empresa.setCP(cp);
				empresa.setCiutat(ciutat);
				empresa.setProvincia(provincia);
				empresa.setTelefon(telefon);
				empresa.setFax(fax);
				empresa.setEmail(email);
				empresa.setTipus(tipus);
				empresa.setPime(isPime);
				empresa.setDataConstitucio(dataConstitucio);
				empresa.setClassificacioString(classificacio);
				empresa.setDateExpAcreditacio1(dateExpAcreditacio1);
				empresa.setDateExpAcreditacio2(dateExpAcreditacio2);
				empresa.setDateExpAcreditacio3(dateExpAcreditacio3);
				empresa.setInformacioAdicional(informacioAdicional);
				empresa.setRegistreMercantilData(dataRegistreMercantil);
				empresa.setExerciciEconomic(dataExerciciEconomic);
				empresa.setDataVigenciaClassificacioROLECE(dataVigenciaClassificacioROLECE);
				empresa.setDataVigenciaClassificacioJCCaib(dataVigenciaClassificacioJCCaib);
				empresa.setDataVigenciaClassificacioJCA(dataVigenciaClassificacioJCA);
				empresa.setRatioAP(ratioAP);			
				
				//Guardar adjunts
			   	EmpresaCore.guardarFitxer(conn, Usuari.getIdUsuari(), multipartParams.getFitxers(), empresa.getCif(), "");
			    
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			if (isUte) {
				 EmpresaCore.updateEmpresaUTE(conn, empresa, administradors, usuari.getIdUsuari());
			}else{
				 EmpresaCore.updateEmpresa(conn, empresa, multipartParams.getParametres().get("cif"), administradors, usuari.getIdUsuari());
			}
       	}
 
		// Store infomation to request attribute, before forward to views.
		
		request.setAttribute("empresa", empresa.getCif());
		response.sendRedirect(request.getContextPath() + "/empresa?cif=" + empresa.getCif());
		
   	}
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}
