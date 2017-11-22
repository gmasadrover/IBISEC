package utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.LoggerCore;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
/**
 * Servlet implementation class openFile
 */
@WebServlet("/openFile")
public class openFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public openFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String idIncidencia = request.getParameter("idincidencia");
		String idActuacio = request.getParameter("idactuacio");
		Context env;
    	String ruta = "";
		try {
			env = (Context)new InitialContext().lookup("java:comp/env");
			ruta = (String)env.lookup("ruta_base");
		} catch (NamingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		File tmpFile = new File(ruta + "/documents/" + idIncidencia);
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}
		tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio");
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}
		tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio);
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}
		//Desktop.getDesktop().browse(getFileURI("W:/1-GESTIÓ ADMINISTRATIVA"));
		Desktop.getDesktop().open(new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio));
	}

	
	private static URI getFileURI(String filePath) {
	    URI uri = null;
	    filePath = filePath.trim();
	    if (filePath.indexOf("http") == 0 || filePath.indexOf("\\") == 0) {
	        if (filePath.indexOf("\\") == 0){
	            filePath = "file:" + filePath;
	            filePath = filePath.replaceAll("#", "%23");
	        }
	        try {
	            filePath = filePath.replaceAll(" ", "%20");
	            URL url = new URL(filePath);
	            uri = url.toURI();
	        } catch (MalformedURLException ex) {
	            ex.printStackTrace();
	        } catch (URISyntaxException ex) {
	            ex.printStackTrace();
	        } 
	    } else {
	        File file = new File(filePath);
	        uri = file.toURI();
	    }
	    return uri;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
