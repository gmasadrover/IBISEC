package utils;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		File tmpFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia);
		if (!tmpFile.exists()) {
			System.out.println(utils.Fitxers.RUTA_BASE + idIncidencia);
			tmpFile.mkdir();
		}
		tmpFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio");
		if (!tmpFile.exists()) {
			System.out.println(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio");
			tmpFile.mkdir();
		}
		tmpFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" + idActuacio);
		if (!tmpFile.exists()) {
			System.out.println(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" + idActuacio);
			tmpFile.mkdir();
		}
		Desktop.getDesktop().open(new File(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" + idActuacio));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
