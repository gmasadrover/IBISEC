package servlet.factura;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bean.Factura;
import bean.User;
import core.ConfiguracioCore;
import core.FacturaCore;
import utils.Fitxers;
import utils.MyUtils;
import utils.Fitxers.Fitxer;

/**
 * Servlet implementation class ImportFacturesHandler
 */
@WebServlet("/importFactures")
public class ImportFacturesHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportFacturesHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
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
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		double total = 0;
		String fileName = "";
		List<FileItem> arxius = new ArrayList<FileItem>();
        for(int i=0;i<items.size();i++){
            /*FileItem representa un archivo en memoria que puede ser pasado al disco duro*/
            FileItem item = (FileItem) items.get(i);
            /*item.isFormField() false=input file; true=text field*/
            if (! item.isFormField()){
               arxius.add(item); 
            } 
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	String ruta = "";
		ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		List<FileItem> facturesPDF = new ArrayList<FileItem>();
		List<String> facturesImport = new ArrayList<String>();
    	if (arxius != null) {
    		Factura factura = new Factura();
    		for (FileItem arxiu: arxius) {
    			if (arxiu.getName().toUpperCase().contains("XML")) {
    				fileName = ruta + "/documents/Factures/-1/";
    				File archivo_server = new File(fileName + "temp_" + arxiu.getName());
    				try {    					
						arxiu.write(archivo_server);
						Fitxers.guardarRegistreFitxer(conn, arxiu.getName(), fileName  + "temp_" + arxiu.getName(), Usuari.getIdUsuari());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				File fXmlFile = new File(fileName + "temp_" + arxiu.getName());
    				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    				DocumentBuilder dBuilder;
					try {
						dBuilder = dbFactory.newDocumentBuilder();
						Document doc = dBuilder.parse(fXmlFile);
						doc.getDocumentElement().normalize();						
						factura = new Factura();
						NodeList nList = doc.getElementsByTagName("SellerParty");
						for (int temp = 0; temp < nList.getLength(); temp++) {
							Node nNode = nList.item(temp);					            
							if (nNode.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) nNode;
								factura.setIdProveidor(eElement.getElementsByTagName("TaxIdentificationNumber").item(0).getTextContent());									
				        	}
						}
						nList = doc.getElementsByTagName("Invoices");
						for (int temp = 0; temp < nList.getLength(); temp++) {
							Node nNode = nList.item(temp);						            
							if (nNode.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) nNode;
								total = Double.parseDouble(eElement.getElementsByTagName("TotalOutstandingAmount").item(0).getTextContent());
								factura.setValor(total);
								factura.setNombreFactura(eElement.getElementsByTagName("InvoiceNumber").item(0).getTextContent());
								factura.setConcepte(eElement.getElementsByTagName("ItemDescription").item(0).getTextContent());
								factura.setDataFactura(formatter.parse(eElement.getElementsByTagName("IssueDate").item(0).getTextContent()));
								
								if (eElement.getElementsByTagName("AdditionalLineItemInformation").item(0) != null) factura.setNotes(eElement.getElementsByTagName("AdditionalLineItemInformation").item(0).getTextContent()); 	
							}
						}
						nList = doc.getElementsByTagName("etsi:CompleteRevocationRefs");
						for (int temp = 0; temp < nList.getLength(); temp++) {
							Node nNode = nList.item(temp);						            
							if (nNode.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) nNode;
								if (eElement.getElementsByTagName("etsi:ProducedAt").item(0)!=null) {
									factura.setDataEntrada(formatter.parse(eElement.getElementsByTagName("etsi:ProducedAt").item(0).getTextContent()));
								} else {
									factura.setDataEntrada(formatter.parse(eElement.getElementsByTagName("etsi:IssueTime").item(0).getTextContent()));
								}
								
							}
						}
	   		            factura.setIdFactura(FacturaCore.getNewCode(conn));
	   		            factura.setIdActuacio("-1");
		         	    factura.setIdInforme("-1");
		         	    factura.setTipusFactura("");
		         	    FacturaCore.newFactura(conn, factura, Usuari.getIdUsuari());
		         	    facturesImport.add(arxiu.getName().split("_")[0] + ";" + factura.getIdProveidor() + ";" + factura.getIdFactura());
					} catch (ParserConfigurationException | SAXException | DOMException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					archivo_server.delete();
    			} else {
    				facturesPDF.add(arxiu);
    			}
    		}
    		
    		for(FileItem facturaPDF: facturesPDF) {
    			Fitxer fitxer = new Fitxer();
    			fitxer.setFitxer(facturaPDF);
    			fitxer.setNom(facturaPDF.getName());
            	fitxer.setFitxer(facturaPDF);
            	for (String infoFact: facturesImport){
            		if (infoFact.split(";")[0].equals(facturaPDF.getName().split("_")[0])) {
            			FacturaCore.saveArxiu("-1", "-1", "-1", infoFact.split(";")[1], infoFact.split(";")[2], new ArrayList<Fitxer>(Arrays.asList(fitxer)), conn, Usuari.getIdUsuari());
            			facturesImport.remove(infoFact);
            			break;
            		}
            		
            	}    			
    		}
        }
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/importarFactures");
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
