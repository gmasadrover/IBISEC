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
       	
       	String cif = multipartParams.getParametres().get("cif");
		String name = multipartParams.getParametres().get("name");
		String direccio = multipartParams.getParametres().get("direccio");
		String cp = multipartParams.getParametres().get("cp");
		String ciutat = URLDecoder.decode(multipartParams.getParametres().get("localitat"), "UTF-8");
		String provincia = URLDecoder.decode(multipartParams.getParametres().get("provincia").split("_")[1], "UTF-8");
		String telefon = multipartParams.getParametres().get("telefon");
		String fax = multipartParams.getParametres().get("fax");
		String email = multipartParams.getParametres().get("email");
		String objecteSocial = multipartParams.getParametres().get("objSocial");
		String classificacio = multipartParams.getParametres().get("llistatClassificacio");		
		String informacioAdicional = multipartParams.getParametres().get("informacioAdicional");
		
		Boolean acreditacio1 = "on".equals(multipartParams.getParametres().get("acreditacio1"));
		Boolean acreditacio2 = "on".equals(multipartParams.getParametres().get("acreditacio2"));
		Boolean acreditacio3 = "on".equals(multipartParams.getParametres().get("acreditacio3"));
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dataConstitucio = null;
		Date dateExpAcreditacio1 = null;
		Date dateExpAcreditacio2 = null;
		Date dateExpAcreditacio3 = null;
		Empresa empresa = new Empresa();
		List<Empresa.Administrador> administradors = new ArrayList<Empresa.Administrador>();
		String errorString = null;
		try {
			if (! multipartParams.getParametres().get("constitucio").isEmpty()) dataConstitucio = formatter.parse(multipartParams.getParametres().get("constitucio"));
			if (! multipartParams.getParametres().get("dateExpAcreditacio1").isEmpty()) dateExpAcreditacio1 = formatter.parse(multipartParams.getParametres().get("dateExpAcreditacio1"));
			if (! multipartParams.getParametres().get("dateExpAcreditacio2").isEmpty()) dateExpAcreditacio2 = formatter.parse(multipartParams.getParametres().get("dateExpAcreditacio2"));
			if (! multipartParams.getParametres().get("dateExpAcreditacio3").isEmpty()) dateExpAcreditacio3 = formatter.parse(multipartParams.getParametres().get("dateExpAcreditacio3"));
			
			empresa.setCif(cif);
			empresa.setName(name);
			empresa.setDireccio(direccio);
			empresa.setCP(cp);
			empresa.setCiutat(ciutat);
			empresa.setProvincia(provincia);
			empresa.setTelefon(telefon);
			empresa.setFax(fax);
			empresa.setEmail(email);
			empresa.setDataConstitucio(dataConstitucio);
			empresa.setObjecteSocial(objecteSocial);
			empresa.setClassificacioString(classificacio);
			empresa.setAcreditacio1(acreditacio1);
			empresa.setDateExpAcreditacio1(dateExpAcreditacio1);
			empresa.setAcreditacio2(acreditacio2);
			empresa.setDateExpAcreditacio2(dateExpAcreditacio2);
			empresa.setAcreditacio3(acreditacio3);
			empresa.setDateExpAcreditacio3(dateExpAcreditacio3);
			empresa.setInformacioAdicional(informacioAdicional);
			
			Empresa.Administrador administrador = empresa.new Administrador();			
		    String[] administradorsString = multipartParams.getParametres().get("llistatAdministradors").split(";"); //Agafam tots els administradors
		    if (administradorsString != null) {
			    for(int i=0; i<administradorsString.length; i++) {
			    	if (! administradorsString[i].isEmpty()) {
				    	administrador = empresa.new Administrador();	
				    	administrador.setNom(administradorsString[i].split("#")[0]);
				    	administrador.setDni(administradorsString[i].split("#")[1]);
				    	administrador.setTipus(administradorsString[i].split("#")[2]);
				    	if (! administradorsString[i].split("#")[3].isEmpty()) administrador.setDataValidesaFins(formatter.parse(administradorsString[i].split("#")[3]));
				    	administrador.setNotariModificacio(administradorsString[i].split("#")[4]);
				    	administrador.setProtocolModificacio(Integer.parseInt(administradorsString[i].split("#")[5]));
				    	if (! administradorsString[i].split("#")[6].isEmpty()) administrador.setDataModificacio(formatter.parse(administradorsString[i].split("#")[6]));
				    	if (administradorsString[i].split("#")[7] != null) administrador.setDataValidacio(formatter.parse(administradorsString[i].split("#")[7]));
				    	if (administradorsString[i].indexOf("eliminar") >= 0) {
				    		administrador.setEliminar(true);
				    	}			    	
				    	administradors.add(administrador);
			    	}
			    }
		    }
		    
			//Guardar adjunts
		   	EmpresaCore.guardarFitxer(multipartParams.getFitxers(), empresa.getCif());
		    
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		try {
           EmpresaCore.updateEmpresa(conn, empresa, administradors, usuari.getIdUsuari());
		} catch (SQLException e) {
           e.printStackTrace();
           errorString = e.getMessage();
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
