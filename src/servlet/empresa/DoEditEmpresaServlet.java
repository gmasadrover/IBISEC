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

import bean.Empresa;
import bean.User;
import core.EmpresaCore;
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
       
       	String cif = request.getParameter("cif");
		String name = request.getParameter("name");
		String direccio = request.getParameter("direccio");
		String cp = request.getParameter("cp");
		String ciutat = URLDecoder.decode(request.getParameter("localitat"), "UTF-8");
		String provincia = URLDecoder.decode(request.getParameter("provincia").split("_")[1], "UTF-8");
		String telefon = request.getParameter("telefon");
		String fax = request.getParameter("fax");
		String email = request.getParameter("email");
		String objecteSocial = request.getParameter("objSocial");
		String classificacio = request.getParameter("llistatClassificacio");		
		Boolean acreditacio1 = "on".equals(request.getParameter("acreditacio1"));
		Boolean acreditacio2 = "on".equals(request.getParameter("acreditacio2"));
		Boolean acreditacio3 = "on".equals(request.getParameter("acreditacio3"));
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dataConstitucio = null;
		Date dateExpAcreditacio1 = null;
		Date dateExpAcreditacio2 = null;
		Date dateExpAcreditacio3 = null;
		Empresa empresa = new Empresa();
		List<Empresa.Administrador> administradors = new ArrayList<Empresa.Administrador>();
		String errorString = null;
		try {
			if (! request.getParameter("constitucio").isEmpty()) dataConstitucio = formatter.parse(request.getParameter("constitucio"));
			if (! request.getParameter("dateExpAcreditacio1").isEmpty()) dateExpAcreditacio1 = formatter.parse(request.getParameter("dateExpAcreditacio1"));
			if (! request.getParameter("dateExpAcreditacio2").isEmpty()) dateExpAcreditacio2 = formatter.parse(request.getParameter("dateExpAcreditacio2"));
			if (! request.getParameter("dateExpAcreditacio3").isEmpty()) dateExpAcreditacio3 = formatter.parse(request.getParameter("dateExpAcreditacio3"));
			
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
	 
			Empresa.Administrador administrador = empresa.new Administrador();			
		    String[] administradorsString = request.getParameterValues("administradors"); //Agafam tots els administradors
		    if (administradorsString != null) {
			    for(int i=0; i<administradorsString.length; i++) {
			    	System.out.println("entra: " + administradorsString[i].split("#")[0]);
			    	administrador = empresa.new Administrador();	
			    	administrador.setNom(administradorsString[i].split("#")[0]);
			    	administrador.setDni(administradorsString[i].split("#")[1]);
			    	administrador.setTipus(administradorsString[i].split("#")[2]);
			    	administrador.setDataValidesaFins(formatter.parse(administradorsString[i].split("#")[3]));
			    	administrador.setNotariModificacio(administradorsString[i].split("#")[4]);
			    	administrador.setProtocolModificacio(Integer.parseInt(administradorsString[i].split("#")[5]));
			    	administrador.setDataModificacio(formatter.parse(administradorsString[i].split("#")[6]));
			    	administradors.add(administrador);
			    }
		    }
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
