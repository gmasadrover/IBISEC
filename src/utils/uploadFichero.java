package utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import core.LoggerCore;
import fr.opensagres.xdocreport.template.utils.TemplateUtils;

import java.io.*;
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
		String[] idIncidencies = null;
		String idIncidencia = "";
		String tipus = "";
		String idTipus = "";
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
            		case "idIncidencies":
            			idIncidencies = item.getString().split("#");
            			break;
	            	case "idIncidencia":
	        			idIncidencia = item.getString();	        			
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
        
        for (String idInc: idIncidencies) {
        	if (arxiu != null) {
    	    	fileName = idInc + "/";
    	    	File incidenciaFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idInc);
    			if (!incidenciaFile.exists()) {
    				incidenciaFile.mkdir();
    				LoggerCore.addLog("crear directori - >" + incidenciaFile.toString(), "");
    			}
    	    	if (! tipus.isEmpty()) {
    	    		fileName += tipus + "/";
    	    		File tipusFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idInc + "/" +tipus);
        			if (!tipusFile.exists()) {
        				tipusFile.mkdir();
        				LoggerCore.addLog("crear directori - >" + tipusFile.toString(), "");
        			}
    	    	} 
    	    	if (! idTipus.isEmpty()) {
    	    		fileName += idTipus + "/";
    	    		File idTipusFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idInc + "/" + tipus + "/" + idTipus);
        			if (!idTipusFile.exists()) {
        				idTipusFile.mkdir();
        				LoggerCore.addLog("crear directori - >" + idTipusFile.toString(), "");
        			}    
    	    	}
    	    	LoggerCore.addLog(fileName, "");
    	        File archivo_server = new File(utils.Fitxers.RUTA_BASE + "/documents/" + fileName + "temp_" + arxiu.getName());
    	        /*y lo escribimos en el servido*/
    	        try {
    				arxiu.write(archivo_server);
    				LoggerCore.addLog(utils.Fitxers.RUTA_BASE + "/documents/"+ fileName + arxiu.getName(), "");
    		        PdfReader reader = new PdfReader(utils.Fitxers.RUTA_BASE + "/documents/"+  fileName + "temp_" + arxiu.getName()); // input PDF
    		        PdfStamper stamper = new PdfStamper(reader,
    		          new FileOutputStream(utils.Fitxers.RUTA_BASE + "/documents/"+ fileName + arxiu.getName())); // output PDF
    		        BaseFont bf = BaseFont.createFont(
    		                BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // set font
    		        try {

    		            // pageNumber = 1
    		            String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);

    		            LoggerCore.addLog(textFromPage, "");


    		        } catch (IOException e) {
    		            e.printStackTrace();
    		            LoggerCore.addLog(e.toString(), "");
    		        }
    		        
    		        
    		        AcroFields fields = stamper.getAcroFields();
    		        PRAcroForm form = reader.getAcroForm();
    		        if (form != null) {
    			        for (Iterator<?> it = form.getFields().iterator(); it.hasNext(); ) {
    						PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation) it.next();
    						String fieldValue = fields.getField(field.getName());
    						LoggerCore.addLog("Field: " + field.getName() + ", Value: " + fieldValue, "");
    						
    						if (fieldValue != null && !fieldValue.equals("")) {
    							
    						} else {
    							
    						}
    					}
    		        }
    		        
    		        //loop on pages (1-based)
    		        for (int i=1; i<=reader.getNumberOfPages(); i++){

    		            // get object for writing over the existing content;
    		            // you can also use getUnderContent for writing in the bottom layer
    		           // PdfContentByte over = stamper.getOverContent(i);

    		            // Data
//    		            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
//    		    		String dataString = df.format(new Date());
//    		            over.beginText();
//    		            over.setFontAndSize(bf, 9);    // set font and size
//    		            over.setTextMatrix(445, 758);   // set x,y position (0,0 is at the bottom left)
//    		            over.showText(dataString);  // set text
//    		            over.endText();
//    		            
//    		            // Num entrada
//    		            over.beginText();
//    		            over.setFontAndSize(bf, 9);    // set font and size
//    		            over.setTextMatrix(445, 748);   // set x,y position (0,0 is at the bottom left)
//    		            over.showText(idTipus);  // set text
//    		            over.endText();

    		            // draw a red circle
//    		            over.setRGBColorStroke(0xFF, 0x00, 0x00);
//    		            over.setLineWidth(5f);
//    		            over.ellipse(250, 450, 350, 550);
//    		            over.stroke();
    		        }

    		        stamper.close();
    		        reader.close();
    		        //Delete Temp file
    		        archivo_server.delete();
    		        
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    				LoggerCore.addLog(e.toString(), "");
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
