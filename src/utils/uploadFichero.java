package utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import java.io.*;
/**
 * Servlet implementation class uploadFichero
 */
@WebServlet("/uploadFichero")
public class uploadFichero extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadFichero() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*FileItemFactory es una interfaz para crear FileItem*/
		FileItemFactory file_factory = new DiskFileItemFactory();
        /*ServletFileUpload esta clase convierte los input file a FileItem*/
        ServletFileUpload servlet_up = new ServletFileUpload(file_factory);
        /*sacando los FileItem del ServletFileUpload en una lista */
        List<?> items = null;
		try {
			items = servlet_up.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		String idActuacio = "00000";
		String idIncidencia = "00000";
		String tipus = "altres";
		String idTipus = "00000";
		String fileName = "";
		String redirect = "/";
		FileItem arxiu = null;
        for(int i=0;i<items.size();i++){
            /*FileItem representa un archivo en memoria que puede ser pasado al disco duro*/
            FileItem item = (FileItem) items.get(i);
            /*item.isFormField() false=input file; true=text field*/
            if (! item.isFormField()){
               arxiu = item; 
            } else {
            	switch (item.getFieldName()) {
	            	case "idIncidencia":
	        			idIncidencia = item.getString();
	        			File incidenciaFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia);
	        			if (!incidenciaFile.exists()) {
	        				incidenciaFile.mkdir();
	        			}
	        			break;
            		case "idActuacio": 
            			idActuacio = item.getString();
            			File actuacioFile = new File(utils.Fitxers.RUTA_BASE + "/Actuacio/" + idActuacio);
            			if (!actuacioFile.exists()) {
            				actuacioFile.mkdir();
            			}
            			break;
            		case "tipus": 
            			tipus = item.getString();
            			File tipusFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia + "/" +tipus);
            			if (!tipusFile.exists()) {
            				tipusFile.mkdir();
            			}
            			break;
            		case "idTipus": 
            			idTipus = item.getString();
            			File idTipusFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia + "/" + tipus + "/" + idTipus);
            			if (!idTipusFile.exists()) {
            				idTipusFile.mkdir();
            			}            			
            			break;
            		case "redirect":
            			redirect = item.getString();
            	} 
            }
        }
        if (arxiu != null) {
	        /*cual sera la ruta al archivo en el servidor*/
	    	fileName = idIncidencia + "/" + tipus + "/" + idTipus + "/";
	    	if (idActuacio != "00000") fileName = idIncidencia + "/Actuacio/" + idActuacio + "/" + tipus + "/" + idTipus + "/";
	        File archivo_server = new File(utils.Fitxers.RUTA_BASE + fileName + arxiu.getName());
	        /*y lo escribimos en el servido*/
	        try {
				arxiu.write(archivo_server);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(redirect);
        dispatcher.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
