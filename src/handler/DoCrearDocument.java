package handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Actuacio;
import bean.Informe;
import bean.Oferta;
import core.ActuacioCore;
import core.InformeCore;
import core.OfertaCore;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import utils.documentos;
import utils.GeneradorDocumentosServiceTest;
import utils.MyUtils;
import utils.GeneradorDocumentosServiceTest.Numero;

/**
 * Servlet implementation class DoCrearDocument
 */
@WebServlet("/CrearDocument")
public class DoCrearDocument extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCrearDocument() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idActuacio = Integer.parseInt(request.getParameter("idActuacio"));
    	String tipus = request.getParameter("tipus");
    	Connection conn = MyUtils.getStoredConnection(request);		
    	Informe informe = new Informe();
    	Actuacio actuacio = new Actuacio();
    	Oferta oferta = new Oferta();
    	try {
			informe = InformeCore.getInformesActuacio(conn, idActuacio).get(0);
			actuacio = ActuacioCore.findActuacio(conn, idActuacio);
			oferta = OfertaCore.findOfertaSeleccionada(conn, idActuacio);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	String rutaPlantilla = "";
    	String filePath = "";
    	if (tipus.equals("autMen")) {
    		rutaPlantilla = "V:/INTERCANVI D'OBRES/MAS, GUILLEM/base/MODELLICIMEN.docx";
    		filePath = "V:/INTERCANVI D'OBRES/MAS, GUILLEM/documents/"+ idActuacio +"/autoritzacio.pdf";
    	}
		Map<String, Object> variablesMap = new HashMap<String, Object>();
		String tipusObra = informe.getTipusObraFormat(); 
		String nomCentre = actuacio.getNomCentre();
		String objecte = informe.getObjecte();
		String tecnic = informe.getUsuari().getNomComplet();
		String nomAdjudicatari = oferta.getNomEmpresa();
		String cifAdjudicatari = oferta.getCifEmpresa();
		String vec = oferta.getVecFormat();
		String iva = oferta.getIvaFormat();
		String plic = oferta.getPlicFormat();
		String terminiExecucio = oferta.getTermini();
		String reqContracte = "Si";
		if (!informe.isContracte()) reqContracte = "No";
		variablesMap.put("contracte", tipusObra);
		variablesMap.put("nomcentre", nomCentre); 
		variablesMap.put("objecte", objecte);		 
		variablesMap.put("formalitzacio", reqContracte); 
		variablesMap.put("tecnic",tecnic);
		variablesMap.put("numactuacio", idActuacio);
		variablesMap.put("nomadjudicatari", nomAdjudicatari);
		variablesMap.put("cifadjudicatari", cifAdjudicatari);
		variablesMap.put("vec", vec);
		variablesMap.put("iva", iva);
		variablesMap.put("plic", plic);
		variablesMap.put("terminiexecucio", terminiExecucio);
		/*variablesMap.put("fecha", FECHA);
		variablesMap.put("nombre", NOMBRE);
		variablesMap.put("texto", TEXTO);
		variablesMap.put("autor", AUTOR);
		variablesMap.put("Numero", new Numero(1L));
		variablesMap.put("Numeros", this.construirListaNumeros());
		variablesMap.put("listaNumeros", this.construirListaNumeros());*/
		
		// 2) Create fields metadata to manage lazy loop (#forech velocity)
        // for table row.
		FieldsMetadata metadata = new FieldsMetadata();
       /* metadata.addFieldAsList("listaNumeros.Numero");
        metadata.addFieldAsList("listaNumeros.Cuadrado");
        metadata.addFieldAsList("listaNumeros.Raiz");*/
		
		// Mapa con las variables de tipo imagen. Estas variables contienen el path de la imagen
		Map<String, String> imagenesMap = new HashMap<String, String>();
		/*imagenesMap.put("header_image_logo", "./Logo.png");*/

		documentos generadorDocumentosService = new documentos();
		byte[] mergedOutput = null;
		try {
			mergedOutput = generadorDocumentosService.generarDocumento(rutaPlantilla,
					TemplateEngineKind.Freemarker, variablesMap, imagenesMap, true
					, metadata
					);
		} catch (XDocReportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		// Se escribe el documento de salida
		FileOutputStream os = new FileOutputStream(filePath);
		os.write(mergedOutput);
		os.flush();
		os.close();
		
		//Descarrega del document
	
        File downloadFile = new File(filePath);
        FileInputStream inStream = new FileInputStream(downloadFile);
                  
        // obtains ServletContext
        ServletContext context = getServletContext();
         
        // gets MIME type of the file
        String mimeType = context.getMimeType(filePath);
        if (mimeType == null) {        
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
         
        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);
         
        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();
         
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
         
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
         
        inStream.close();
        outStream.close();    
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
