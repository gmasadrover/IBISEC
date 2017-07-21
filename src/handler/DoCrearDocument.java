package handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PRAcroForm;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import bean.Actuacio;
import bean.InformeActuacio;
import bean.Oferta;
import bean.User;
import core.ActuacioCore;
import core.CreditCore;
import core.InformeCore;
import core.LoggerCore;
import core.OfertaCore;
import core.UsuariCore;
import utils.MyUtils;

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
		String idActuacio = request.getParameter("idActuacio");
		String idIncidencia = request.getParameter("idIncidencia");
		String idInforme = request.getParameter("idInforme");
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
    	String tipus = request.getParameter("tipus");
    	Connection conn = MyUtils.getStoredConnection(request);		
    	InformeActuacio informe = new InformeActuacio();
    	Actuacio actuacio = new Actuacio();
    	Oferta oferta = new Oferta();
    	try {
    		informe = InformeCore.getInformePrevi(conn, idInforme);
			actuacio = ActuacioCore.findActuacio(conn, idActuacio);	
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	System.out.println("ENTRA AMB TIPUS: " + tipus);
    	String filePath = "";
    	File downloadFile = null;
    	if (tipus.equals("autMen")) {	
			// Crear directoris si no existeixen
			File tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
    		
    		filePath = utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio +"/autoritzacio_informe_"+ idInforme +".pdf";
            try {
            	PdfReader reader = new PdfReader(utils.Fitxers.RUTA_BASE + "/base/MODELLICIMEN2v5.pdf"); // input PDF
    			PdfStamper stamper = new PdfStamper(reader,
    			  new FileOutputStream("/autoritzacio_informe_"+ idInforme +".pdf"));			
    			            
                AcroFields fields = stamper.getAcroFields();    	        
    	        fields.setField("tipusContracte",informe.getPropostaInformeSeleccionada().getTipusObraFormat());
    	        fields.setField("data",getData());
    	        fields.setField("adjudicatari","Adjudica l'execució d'aquesta actuació a " + informe.getOfertaSeleccionada().getNomEmpresa());
    	        fields.setField("vec", informe.getOfertaSeleccionada().getVecFormat());
    	        fields.setField("iva", informe.getOfertaSeleccionada().getIvaFormat());
    	        fields.setField("plic", informe.getOfertaSeleccionada().getPlicFormat());
    	        fields.setField("termini", informe.getOfertaSeleccionada().getTermini());
    	        fields.setField("cifAdjudicatari", informe.getOfertaSeleccionada().getCifEmpresa());
    	        fields.setField("nomAdjudicatari", informe.getOfertaSeleccionada().getNomEmpresa());
    	        fields.setField("numActuacio", String.valueOf(idActuacio));
    	        fields.setField("nomTecnic", informe.getUsuari().getNomComplet());
    	        fields.setField("objecte", informe.getPropostaInformeSeleccionada().getObjecte());
    	        fields.setField("nomCentre", actuacio.getNomCentre());
    	        stamper.close();
    	        reader.close();
                
    		} catch (DocumentException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		} // output PDF
            downloadFile = new File("/autoritzacio_informe_"+ idInforme +".pdf");
       } else if ("PAObres".equals(tipus)) {
    		// Crear directoris si no existeixen
			File tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
    		
    		filePath = utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio +"/proposta_actuacio_"+ idInforme +".pdf";
            try {
            	
	        	PdfReader reader = new PdfReader(utils.Fitxers.RUTA_BASE + "/base/PA tècnic.pdf"); // input PDF
				PdfStamper stamper = new PdfStamper(reader,
				  new FileOutputStream("/proposta_actuacio_"+ idInforme +".pdf"));			
				            
	            AcroFields fields = stamper.getAcroFields();
	            fields.setField("informe",informe.getIdInf());
    	        fields.setField("tipusContracte",informe.getLlistaPropostes().get(0).getTipusObraFormat());
    	        fields.setField("nomCentre",actuacio.getNomCentre());
    	        fields.setField("objecte",informe.getLlistaPropostes().get(0).getObjecte());
    	        fields.setField("vec", informe.getLlistaPropostes().get(0).getVecFormat());
    	        fields.setField("iva", informe.getLlistaPropostes().get(0).getIvaFormat());
    	        fields.setField("plic", informe.getLlistaPropostes().get(0).getPlicFormat());
    	        fields.setField("termini", informe.getLlistaPropostes().get(0).getTermini());
    	        fields.setField("idActuacio", actuacio.getReferencia());
    	        fields.setField("tecnic", Usuari.getNomCompletReal());
    	        fields.setField("comentaris", informe.getLlistaPropostes().get(0).getComentari());
    	        String llicencia = "No";
    	        if (informe.getLlistaPropostes().get(0).isLlicencia()) llicencia = "Si";
    	        fields.setField("llicencia", llicencia);
    	        fields.setField("tipusLlicencia", informe.getLlistaPropostes().get(0).getTipusLlicencia());
    	        String contracte = "No";
    	        if (informe.getLlistaPropostes().get(0).isContracte()) contracte = "Si";
    	        fields.setField("contracte", contracte);
    	        stamper.close();
    	        reader.close();    		                
    		} catch (DocumentException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		} // output PDF
            downloadFile = new File("/proposta_actuacio_"+ idInforme +".pdf");
       } else if ("VistiplauPAObres".equals(tipus)) {
   		// Crear directoris si no existeixen
			File tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
   		
			filePath = utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio +"/vistiplau_proposta_actuacio_"+ idInforme +".pdf";
			try {
	        	PdfReader reader = new PdfReader(utils.Fitxers.RUTA_BASE + "/base/PA cap de servei.pdf"); // input PDF
				PdfStamper stamper = new PdfStamper(reader,
				  new FileOutputStream("/vistiplau_proposta_actuacio_"+ idInforme +".pdf"));			
				            
	            AcroFields fields = stamper.getAcroFields();
	            fields.setField("informe",informe.getIdInf());
	        	fields.setField("capServei", Usuari.getNomCompletReal());
	   	       	fields.setField("observacions", informe.getComentariCap());	   	       
	   	       	stamper.close();
	   	       	reader.close();
   		                
	   		} catch (DocumentException e1) {
	   			// TODO Auto-generated catch block
	   			e1.printStackTrace();
	   		} // output PDF
			downloadFile = new File("/vistiplau_proposta_actuacio_"+ idInforme +".pdf");
       } else if ("financeraPAObres".equals(tipus)) {
      		// Crear directoris si no existeixen
   			File tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia);
   			if (!tmpFile.exists()) {
   				tmpFile.mkdir();
   			}
   			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio");
   			if (!tmpFile.exists()) {
   				tmpFile.mkdir();
   			}
   			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio);
   			if (!tmpFile.exists()) {
   				tmpFile.mkdir();
   			}
      		
   			filePath = utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio +"/financera_proposta_actuacio_"+ idInforme +".pdf";
   			try {
   	        	PdfReader reader = new PdfReader(utils.Fitxers.RUTA_BASE + "/base/PA àrea econòmica.pdf"); // input PDF
   				PdfStamper stamper = new PdfStamper(reader,
   				  new FileOutputStream("/financera_proposta_actuacio_"+ idInforme +".pdf"));			
   				            
   	            AcroFields fields = stamper.getAcroFields();
   	            fields.setField("informe",informe.getIdInf());
   	        	fields.setField("capFinancera", Usuari.getNomCompletReal());
   	        	fields.setField("partida", informe.getPartida());	   	       
   	   	       	fields.setField("observacions", CreditCore.getComentariPartida(conn, idInforme));	
   	   	       	String resolucio = "El cap de l'àrea econòmica financera dóna el seu vistiplau a l'informe i procedeix a l'assignació de la partida pressupostària";
	   	        if (informe.getDataRebujada() != null) {
	   	        	resolucio = "El cap de l'àrea econòmica financera no dóna el seu vistiplau a l'informe";
	   	        	fields.setField("observacions", informe.getPartidaRebutjadaMotiu());	
   	   	       	}
   	   	       	fields.setField("resolucio", resolucio);
   	   	       	stamper.close();
   	   	       	reader.close();
      		                
   	   		} catch (DocumentException | SQLException e1) {
   	   			// TODO Auto-generated catch block
   	   			e1.printStackTrace();
   	   		} // output PDF
   			downloadFile = new File("/financera_proposta_actuacio_"+ idInforme +".pdf");
       } else if ("AutoritzacioPAObres".equals(tipus)) {
     		// Crear directoris si no existeixen
  			File tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia);
  			if (!tmpFile.exists()) {
  				tmpFile.mkdir();
  			}
  			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio");
  			if (!tmpFile.exists()) {
  				tmpFile.mkdir();
  			}
  			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio);
  			if (!tmpFile.exists()) {
  				tmpFile.mkdir();
  			}
     		
  			filePath = utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio +"/autoritzacio_proposta_actuacio_"+ idInforme +".pdf";
  			try {
  	        	PdfReader reader = new PdfReader(utils.Fitxers.RUTA_BASE + "/base/PA gerència.pdf"); // input PDF
  				PdfStamper stamper = new PdfStamper(reader,
  				  new FileOutputStream("/autoritzacio_proposta_actuacio_"+ idInforme +".pdf"));			
  				            
  	            AcroFields fields = stamper.getAcroFields();
  	            fields.setField("informe",informe.getIdInf());
  	        	fields.setField("gerent", UsuariCore.findUsuarisByRol(conn, "GERENT").get(0).getNomCompletReal());
  	   	       	fields.setField("observacions", "");	  	   	      
  	   	       	stamper.close();
  	   	       	reader.close();
     		                
  	   		} catch (DocumentException | SQLException e1) {
  	   			// TODO Auto-generated catch block
  	   			e1.printStackTrace();
  	   		} // output PDF
  			downloadFile = new File("/autoritzacio_proposta_actuacio_"+ idInforme +".pdf");
    	} else if ("PTObres".equals(tipus)) {
    		// Crear directoris si no existeixen
			File tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
    		
    		filePath = utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio +"/proposta_tecnica_"+ idInforme +".pdf";
            try {
	        	PdfReader reader = new PdfReader(utils.Fitxers.RUTA_BASE + "/base/MODPTOBRES.pdf"); // input PDF
	        	Rectangle pagesize = reader.getPageSize(1);
				PdfStamper stamper = new PdfStamper(reader,
				  new FileOutputStream("/proposta_tecnica_"+ idInforme +".pdf"));			
				            
	            AcroFields fields = stamper.getAcroFields();
    	        fields.setField("tipusContracte",informe.getLlistaPropostes().get(0).getTipusObraFormat());
    	        fields.setField("nomCentre",actuacio.getNomCentre());
    	        fields.setField("objecte",informe.getLlistaPropostes().get(0).getObjecte());
    	        fields.setField("vec", informe.getOfertaSeleccionada().getVecFormat());
    	        fields.setField("iva", informe.getOfertaSeleccionada().getIvaFormat());
    	        fields.setField("plic", informe.getOfertaSeleccionada().getPlicFormat());
    	        fields.setField("terminiInicial", informe.getOfertaSeleccionada().getTermini());
    	        fields.setField("idActuacio", actuacio.getReferencia());
    	        fields.setField("empresaSeleccionada", informe.getOfertaSeleccionada().getNomEmpresa() + " (" + informe.getOfertaSeleccionada().getCifEmpresa() + ")" );    	 
    	        fields.setField("comentaris", informe.getOfertaSeleccionada().getComentari());    	 
    	        fields.setField("terminiDefinitiu", informe.getOfertaSeleccionada().getTermini());
    	        fields.setField("data", getData());
    	        PdfPTable table = new PdfPTable(3);
    	        PdfPCell cell = new PdfPCell();   
    	        Paragraph text = new Paragraph();    	
    	        
    	       // table.setWidths(new int[]{1,3});
    	        List<Oferta> ofertesList = informe.getLlistaOfertes();
    	         
    	        String FONT = utils.Fitxers.RUTA_BASE + "/fonts/NotoSans-Regular.ttf";
    	        FontFactory.register(FONT,"Noto-Sans");
    	        Font f = FontFactory.getFont("Noto-Sans", "Cp1253", true);
    	        f.setSize(11);   	    
    	        String FONTBOLD = utils.Fitxers.RUTA_BASE + "/fonts/NotoSans-Bold.ttf";
    	        FontFactory.register(FONTBOLD,"Noto-Sans-Bold");
    	        Font fb = FontFactory.getFont("Noto-Sans-Bold", "Cp1253", true);
    	        fb.setSize(11); 
    	            
    	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	        cell.setPadding(5);
    	        text = new Paragraph("Import Oferta", fb);    	            
	        	cell.addElement(text);    	            
    	        table.addCell(cell);
    	        
    	        cell = new PdfPCell();   
    	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	        cell.setPadding(5);
    	        text = new Paragraph("Nom licitador", fb);    	            
	        	cell.addElement(text);    	            
    	        table.addCell(cell);
    	        
    	        cell = new PdfPCell();   
    	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	        cell.setPadding(5);
    	        text = new Paragraph("DNI/CIF licitador", fb);    	            
	        	cell.addElement(text);    	            
    	        table.addCell(cell);
    	        
    	        table.setHeaderRows(1);
    	        	                    
    	        for (Oferta ofertaActual : ofertesList) {
    	        	cell = new PdfPCell();
    	        	cell.setPadding(5);
    	            text = new Paragraph(ofertaActual.getPlicFormat(), f);    	            
    	        	cell.addElement(text);    	            
    	            table.addCell(cell);
    	            
    	            cell = new PdfPCell();
    	            cell.setPadding(5);
    	            text = new Paragraph(ofertaActual.getNomEmpresa(), f);    	            
    	        	cell.addElement(text);    	            
    	            table.addCell(cell);
    	            
    	            cell = new PdfPCell();
    	            cell.setPadding(5);
    	            text = new Paragraph(ofertaActual.getCifEmpresa(), f);    	            
    	        	cell.addElement(text);    	            
    	            table.addCell(cell);
    	        }
    	        
    	        ColumnText column = new ColumnText(stamper.getOverContent(2));
    	        Rectangle rectPage1 = new Rectangle(36, 36, 559, 650);
    	        column.setSimpleColumn(rectPage1);
    	        column.addElement(table);
    	        int pagecount = 1;
    	        Rectangle rectPage2 = new Rectangle(36, 36, 559, 806);
    	        int status = column.go();
    	        while (ColumnText.hasMoreText(status)) {
    	           status = triggerNewPage(stamper, pagesize, column, rectPage2, ++pagecount);
    	       	}             
    	    	        
    	        stamper.close();
    	        reader.close();
    		                
    		} catch (DocumentException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		} // output PDF
            downloadFile = new File("/proposta_tecnica_"+ idInforme +".pdf");
    	} else {
    		/*try {
    			Document doc = new Document();
    		          RtfWriter2 writer2 = RtfWriter2.getInstance(doc,
    		              new FileOutputStream("V:/PERSONAL/MAS GUILLEM/testNew.rtf"));
    		          RtfWriter2.getInstance(doc, new FileOutputStream("V:/PERSONAL/MAS GUILLEM/testOld.rtf"));
    		  
    		          filePath = "V:/PERSONAL/MAS GUILLEM/testNew.rtf";
    		         writer2.setAutogenerateTOCEntries(true);
    		 
    		          Table headerTable = new Table(3);
    		         headerTable.addCell("Test Cell 1");
    		         headerTable.addCell("Test Cell 2");
    		          headerTable.addCell("Test Cell 3");
    		          HeaderFooter header = new RtfHeaderFooter(headerTable);
    		          RtfHeaderFooterGroup footer = new RtfHeaderFooterGroup();
    		          footer
    		              .setHeaderFooter(
    		              new RtfHeaderFooter(new Phrase(
    		              "This is the footer on the title page")),
    		              com.lowagie.text.rtf.headerfooter.RtfHeaderFooter.DISPLAY_FIRST_PAGE);
    		          footer
    		              .setHeaderFooter(
    		              new RtfHeaderFooter(new Phrase(
    		              "This is a left side page")),
    		              com.lowagie.text.rtf.headerfooter.RtfHeaderFooter.DISPLAY_LEFT_PAGES);
    		          footer
    		              .setHeaderFooter(
    		              new RtfHeaderFooter(new Phrase(
    		              "This is a right side page")),
    		              com.lowagie.text.rtf.headerfooter.RtfHeaderFooter.DISPLAY_RIGHT_PAGES);
    		  
    		          doc.setHeader(header);
    		          doc.setFooter(footer);
    		  
    		          doc.open();
    		          Paragraph p = new Paragraph();
    		          p.add(new RtfTableOfContents("UPDATE ME!"));
    		          doc.add(p);
    		  
    		          p = new Paragraph("", new RtfFont("Staccato222 BT"));
    		          p.add(new Chunk("Hello! "));
    		          p.add(new Chunk("How do you do?"));
    		          doc.add(p);
    		          p.setAlignment(Element.ALIGN_RIGHT);
    		          doc.add(p);
    		  
    		          Anchor a = new Anchor("http://www.uni-klu.ac.at");
    		          a.setReference("http://www.uni-klu.ac.at");
    		          doc.add(a);
    		  
    		         // Image img = Image.getInstance("pngnow.png");
    		         // doc.add(new Chunk(img, 0, 0));
    		         // doc.add(new Annotation("Mark", "This works!"));
    		 
    		          Chunk c = new Chunk("");
    		         c.setNewPage();
    		         doc.add(c);
    		 
    		         List subList = new List(true, 40);
    		         subList.add(new ListItem("Sub list 1"));
    		         subList.add(new ListItem("Sub list 2"));
    		 
    		         List list = new List(true, 20);
    		         list.add(new ListItem("Test line 1"));
    		         list
    		             .add(new ListItem(
    		             "Test line 2 - This is a really long test line to test that linebreaks are working the way they are supposed to work so a really really long line of drivel is required"));
    		         list.add(subList);
    		         list.add(new ListItem("Test line 3 - \t\u20ac\t 60,-"));
    		         doc.add(list);
    		 
    		         list = new List(false, 20);
    		         list.add(new ListItem("Bullet"));
    		         list.add(new ListItem("Another one"));
    		         doc.add(list);
    		 
    		         doc.newPage();
    		 
    		         Chapter chapter = new Chapter(new Paragraph("This is a Chapter"), 1);
    		         chapter.add(new Paragraph(
    		             "This is some text that belongs to this chapter."));
    		         chapter.add(new Paragraph("A second paragraph. What a surprise."));
    		 
    		         Section section = chapter.addSection(new Paragraph(
    		             "This is a subsection"));
    		         section.add(new Paragraph("Text in the subsection."));
    		 
    		         doc.add(chapter);
    		 
    		         com.lowagie.text.rtf.field.RtfTOCEntry rtfTOC = new com.lowagie.text.rtf.field.RtfTOCEntry(
    		             "Table Test");
    		         doc.add(rtfTOC);
    		 
    		         Table table = new Table(3);
    		         table.setSpacing(2);
    		         table.setAlignment(Element.ALIGN_LEFT);
    		         table.setSpacing(2);
    		 
    		         Cell emptyCell = new Cell("");
    		 
    		         Cell cellLeft = new Cell("Left Alignment");
    		         cellLeft.setHorizontalAlignment(Element.ALIGN_LEFT);
    		         Cell cellCenter = new Cell("Center Alignment");
    		         cellCenter.setHorizontalAlignment(Element.ALIGN_CENTER);
    		         Cell cellRight = new Cell("Right Alignment");
    		         cellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);
    		 
    		         table.addCell(cellLeft);
    		         table.addCell(cellCenter);
    		         table.addCell(cellRight);
    		 
    		         Cell cellSpanHoriz = new Cell("This Cell spans two columns");
    		         cellSpanHoriz.setColspan(2);
    		         table.addCell(cellSpanHoriz);
    		         table.addCell(emptyCell);
    		 
    		         Cell cellSpanVert = new Cell("This Cell spans two rows");
    		         cellSpanVert.setRowspan(2);
    		         table.addCell(emptyCell);
    		         table.addCell(cellSpanVert);
    		         table.addCell(emptyCell);
    		         table.addCell(emptyCell);
    		         table.addCell(emptyCell);
    		 
    		         Cell cellSpanHorizVert = new Cell(
    		             "This Cell spans both two columns and two rows");
    		         cellSpanHorizVert.setColspan(2);
    		         cellSpanHorizVert.setRowspan(2);
    		         table.addCell(emptyCell);
    		         table.addCell(cellSpanHorizVert);
    		         table.addCell(emptyCell);
    		 
    		         RtfCell cellDotted = new RtfCell("Dotted border");
    		         cellDotted.setBorders(new RtfBorderGroup());
    		         RtfCell cellEmbossed = new RtfCell("Embossed border");
    		         RtfCell cellNoBorder = new RtfCell("No border");
    		         cellNoBorder.setBorders(new RtfBorderGroup());
    		 
    		         table.addCell(cellDotted);
    		         table.addCell(cellEmbossed);
    		         table.addCell(cellNoBorder);
    		 
    		         try {
						doc.add(table);
						for (int i = 0; i < 300; i++) {
	    		             doc.add(new Paragraph(
	    		                 "Dummy line to get multi-page document. Line "
	    		                 + (i + 1)));
	    		         }
					} catch (com.lowagie.text.DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    		 
    		 	          
    		 
    		         doc.close();
    		 	}   	
    		 catch (com.lowagie.text.DocumentException e) {
    			
    		}*/
    	}
				
		//Descarrega del document
	
        
        FileInputStream inStream = new FileInputStream(downloadFile);
                           
        // gets MIME type of the file
        String mimeType = null;
        if (mimeType == null) {        
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
         
        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("inline; filename=\"%s\"", downloadFile.getName());
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
	
	public int triggerNewPage(PdfStamper stamper,
		    Rectangle pagesize, ColumnText column, Rectangle rect, int pagecount)
		        throws DocumentException {
		    stamper.insertPage(pagecount, pagesize);
		    PdfContentByte canvas = stamper.getOverContent(pagecount);
		    column.setCanvas(canvas);
		    column.setSimpleColumn(rect);
		    return column.go();
		}
	
	private static String getData(){
		String dataStr = "";
		Calendar today = Calendar.getInstance();
		dataStr += String.format("%02d", today.get(Calendar.DAY_OF_MONTH));
		dataStr += " de " + getMonth(today.get(Calendar.MONTH));
		dataStr += " de " + (today.get(Calendar.YEAR));
		return dataStr;
	}
	
	private static String getMonth(int month){
		switch (month) {
			case 0: 
				return "Gener";
			case 1:
				return "Febrer";
			case 2: 
				return "Març";
			case 3:
				return "Abril";
			case 4: 
				return "Maig";
			case 5:
				return "Juny";
			case 6: 
				return "Juliol";
			case 7:
				return "Agost";
			case 8: 
				return "Setembre";
			case 9:
				return "Octubre";
			case 10: 
				return "Novembre";
			case 11:
				return "Desembre";
			default:
				return "";
		}
	}

}
