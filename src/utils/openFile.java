package utils;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.ConfiguracioCore;

import java.awt.Desktop;
import java.io.File;
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
		Connection conn = MyUtils.getStoredConnection(request);		
    	String ruta = "";
		ruta = ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
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

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
