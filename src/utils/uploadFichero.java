package utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import bean.Registre;
import bean.User;
import core.RegistreCore;

import java.io.*;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
		}
		Connection conn = MyUtils.getStoredConnection(request);	
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		String[] idIncidencies = null;	
		String tipus = "";
		String idTipus = "";
		String fileName = "";
		String redirect = "/";
		List<FileItem> arxius = new ArrayList<FileItem>();
        for(int i=0;i<items.size();i++){
            /*FileItem representa un archivo en memoria que puede ser pasado al disco duro*/
            FileItem item = (FileItem) items.get(i);
            /*item.isFormField() false=input file; true=text field*/
            if (! item.isFormField()){
               arxius.add(item); 
            } else {
            	switch (item.getFieldName()) {
            		case "idIncidencies":
            			if (item.getString().isEmpty()) {
            				idIncidencies = new String[1];
            	        	idIncidencies[0] = "-1";
            			} else {
            				idIncidencies = item.getString().split("#");
            			}            			
            			break;	            	
            		case "tipus": 
            			tipus = item.getString();            			
            			break;
            		case "idTipus": 
            			idTipus = item.getString();            			        			
            			break;
            		case "redirect":
            			redirect = item.getString();
            	} 
            }
        }
        
        Context env;
    	String ruta = "";
		try {
			env = (Context)new InitialContext().lookup("java:comp/env");
			ruta = (String)env.lookup("ruta_base");
		} catch (NamingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
        for (String idInc: idIncidencies) {
        	if (arxius != null) {
        		for (FileItem arxiu: arxius) {
	    	    	fileName = idInc + "/";
	    	    	File incidenciaFile = new File(ruta + "/documents/" + idInc);
	    			if (!incidenciaFile.exists()) {
	    				incidenciaFile.mkdir();
	    			}
	    	    	if (! tipus.isEmpty()) {
	    	    		fileName += tipus + "/";
	    	    		File tipusFile = new File(ruta + "/documents/" + idInc + "/" +tipus);
	        			if (!tipusFile.exists()) {
	        				tipusFile.mkdir();
	        			}
	    	    	} 
	    	    	if (! idTipus.isEmpty()) {
	    	    		fileName += idTipus + "/";
	    	    		File idTipusFile = new File(ruta + "/documents/" + idInc + "/" + tipus + "/" + idTipus);
	        			if (!idTipusFile.exists()) {
	        				idTipusFile.mkdir();
	        			}    
	    	    	}
	    	        /*y lo escribimos en el servido*/
	    	        try {
	    	        	if (tipus.equals("RegistreE")) {
	    		        	File archivo_server = new File(ruta + "/documents/" + fileName + "temp_" + arxiu.getName());
	    		        	arxiu.write(archivo_server);
	    		        	Fitxers.guardarRegistreFitxer(conn, arxiu.getName(), ruta + "/documents/" + fileName + "temp_" + arxiu.getName(), Usuari.getIdUsuari());
	    		        	String tipusRegistre = "E";
	    		        	if (idTipus.contains("RS")) tipusRegistre = "S";
	    		        	Registre reg = RegistreCore.findRegistre(conn, tipusRegistre, idTipus);
	    		        	PdfReader reader = new PdfReader(ruta + "/documents/"+  fileName + "temp_" + arxiu.getName()); // input PDF
		    		        PdfStamper stamper = new PdfStamper(reader,
		    		          new FileOutputStream(ruta + "/documents/"+ fileName + arxiu.getName())); // output PDF		    		     
			    	        BaseFont font = BaseFont.createFont(); // Helvetica, WinAnsiEncoding
			    	        for (int i = 0; i < reader.getNumberOfPages(); ++i) {
			    	          PdfContentByte overContent = stamper.getOverContent( i + 1 );
			    	          Rectangle rect = new Rectangle(240, 815, 480, 830);
			    	          rect.setBorder(Rectangle.BOX);		
			    	          rect.setBackgroundColor(BaseColor.LIGHT_GRAY);
			    	          overContent.rectangle(rect);
			    	          overContent.saveState();
			    	          overContent.beginText();
			    	          overContent.setFontAndSize( font, 10.0f );
			    	          overContent.setTextMatrix(250, 820);
			    	          overContent.showText("IBISEC        Registre: "+ idTipus + " (" + reg.getDataString() + ")");
			    	          overContent.endText();		    	        
			    	          overContent.restoreState();
			    	        }		    	       
		    		        stamper.close();
		    		        reader.close();
		    		        //Delete Temp file
		    		        archivo_server.delete();
	    		        } else {
	    		        	File archivo_server = new File(ruta + "/documents/" + fileName + arxiu.getName());
	    		        	arxiu.write(archivo_server);
	    		        	Fitxers.guardarRegistreFitxer(conn, arxiu.getName(), ruta + "/documents/" + fileName + arxiu.getName(), Usuari.getIdUsuari());
	    		        }
	    		        
	    		        
	    			} catch (Exception e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
        		}
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
