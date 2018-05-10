package servlet.empresa;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

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
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		String extincio = multipartParams.getParametres().get("extincio");
		String succecio = multipartParams.getParametres().get("succecio");
		
       	String cif = multipartParams.getParametres().get("newcif");
       	if (cif == null) cif = multipartParams.getParametres().get("cif");
       	Boolean isUte = "true".equals(multipartParams.getParametres().get("isute"));       	
       	Empresa empresa = new Empresa();
       	String errorString = null;
       	
       	if (extincio != null) {
       		try {
       			String motiuExtincio = multipartParams.getParametres().get("motiuextincio");
				EmpresaCore.extincioEmpresa(conn, cif, motiuExtincio);
				empresa.setCif(cif);
				//Guardar adjunts
			   	EmpresaCore.guardarFitxer(conn, Usuari.getIdUsuari(), multipartParams.getFitxers(), empresa.getCif(), "");
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       	} else if (succecio != null) {
       		String cifSuccesora = multipartParams.getParametres().get("cifsuccesora");
       		try {
				EmpresaCore.addSuccesora(conn, cif, cifSuccesora);
				empresa.setCif(cif);
				//Guardar adjunts
			   	EmpresaCore.guardarFitxer(conn, Usuari.getIdUsuari(), multipartParams.getFitxers(), empresa.getCif(), "");
			} catch (SQLException | NamingException e) {
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
			String classificacio = multipartParams.getParametres().get("llistatClassificacio");		
			String informacioAdicional = multipartParams.getParametres().get("informacioAdicional");		
			
			Boolean isPime = "on".equals(multipartParams.getParametres().get("pime"));
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
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
			    
			} catch (ParseException | NamingException e1) {
				e1.printStackTrace();
			}
			
			try {
				if (isUte) {
					 EmpresaCore.updateEmpresaUTE(conn, empresa, administradors, usuari.getIdUsuari());
				}else{
					 EmpresaCore.updateEmpresa(conn, empresa, multipartParams.getParametres().get("cif"), administradors, usuari.getIdUsuari());
				}          
			} catch (SQLException e) {
	           e.printStackTrace();
	           errorString = e.getMessage();
			}
       	}
 
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("empresa", empresa);
 
 
		// If error, forward to Edit page.
		if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/empresa/editEmpresaView.jsp");
           dispatcher.forward(request, response);
		}
        
		// If everything nice.
		// Redirect to the product listing page.            
		else {
           response.sendRedirect(request.getContextPath() + "/editEmpresa?cif=" + empresa.getCif());
		}
   	}
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}
