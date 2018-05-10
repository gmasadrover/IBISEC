package servlet.empresa;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.google.gson.JsonObject;

import bean.Empresa;
import bean.User;
import core.EmpresaCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoAddAdministrador
 */
@WebServlet("/addAdministrador")
public class DoAddAdministrador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoAddAdministrador() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
        response.setContentType("text/html");
 
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
 
        JsonObject myObj = new JsonObject();      
        Connection conn = MyUtils.getStoredConnection(request);
        User Usuari = MyUtils.getLoginedUser(request.getSession());	   
        Fitxers.formParameters multipartParams = new Fitxers.formParameters();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			multipartParams = Fitxers.getParamsFromMultipartForm(request);
			String cif = multipartParams.getParametres().get("cif");
			Empresa.Administrador administrador = new Empresa().new Administrador();			
	    	administrador.setNom(multipartParams.getParametres().get("nomAdmin"));
	    	administrador.setDni(multipartParams.getParametres().get("dniAdmin"));
	    	administrador.setTipus(multipartParams.getParametres().get("tipusAdmin"));
	    	if (! multipartParams.getParametres().get("validAdmin").isEmpty()) administrador.setDataValidesaFins(formatter.parse(multipartParams.getParametres().get("validAdmin")));
	    	administrador.setNotariModificacio(multipartParams.getParametres().get("nomNotari"));
	    	if (! multipartParams.getParametres().get("numProtocol").isEmpty()) administrador.setProtocolModificacio(multipartParams.getParametres().get("numProtocol"));
	    	if (! multipartParams.getParametres().get("dataAlta").isEmpty()) administrador.setDataModificacio(formatter.parse(multipartParams.getParametres().get("dataAlta")));
	    	if (! multipartParams.getParametres().get("dataValidacio").isEmpty()) {
	    		administrador.setDataValidacio(formatter.parse(multipartParams.getParametres().get("dataValidacio")));	    		
	    	} 
	    	administrador.setEntitatValidacio(multipartParams.getParametres().get("organValidador"));
			EmpresaCore.addAdministrador(conn, cif, administrador, Usuari.getIdUsuari());
			EmpresaCore.guardarFitxer(conn, Usuari.getIdUsuari(), multipartParams.getFitxers(), cif, administrador.getDni());
		} catch (FileUploadException | NamingException | ParseException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
		myObj.addProperty("success", true);	
		
        out.println(myObj.toString());
 
        out.close();
	}

}
