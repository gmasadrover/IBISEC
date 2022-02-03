package servlet.factura;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import bean.Factura;
import bean.InformeActuacio;
import core.ConfiguracioCore;
import core.FacturaCore;
import core.InformeCore;
import core.TascaCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class EnviarAComptabilitat
 */
@WebServlet("/enviarAComptabilitat")
public class EnviarAComptabilitat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnviarAComptabilitat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);	
		
	    String idFactura = request.getParameter("ref");   
	    String idTasca = request.getParameter("idtasca");
	    
	    int idUsuari = MyUtils.getLoginedUser(request.getSession()).getIdUsuari();	  
	    
	    Factura factura = new Factura();
	    
	    if (idTasca != null) {
	    	TascaCore.tancar(conn, Integer.parseInt(idTasca));
	    }
	    
	    String errorString = null;	 	      
	   	if (errorString == null) {
	   		try {
	   			factura = FacturaCore.getFactura(conn, idFactura);	
	   			InformeActuacio informeActual = InformeCore.getInformePrevi(conn, factura.getIdInforme(), false);
	   			Date date = new Date();
	   			if (factura.getDataEnviatComptabilitat() == null) {
	   				
	    	        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    	        String rutaFactua = factura.getFactura().getRuta();
	    	        PdfReader reader = new PdfReader(rutaFactua);
	    	        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(rutaFactua.replace(".pdf", "AU.pdf")));
	    	        BaseFont font = BaseFont.createFont(); // Helvetica, WinAnsiEncoding
	    	        for (int i = 0; i < reader.getNumberOfPages(); ++i) {
	    	          PdfContentByte overContent = stamper.getOverContent( i + 1 );
	    	          overContent.saveState();
	    	          overContent.beginText();
	    	          overContent.setFontAndSize( font, 10.0f );
	    	          overContent.setTextMatrix( 24, 35 );
	    	          overContent.showText("Data passat a comptabilitat " + dateFormat.format(date));
	    	          overContent.endText();
	    	          overContent.restoreState();
	    	          
	    	          overContent = stamper.getOverContent( i + 1 );
	    	          overContent.saveState();
	    	          overContent.beginText();
	    	          overContent.setFontAndSize( font, 10.0f );
	    	          overContent.setTextMatrix( 24, 24 );
	    	          overContent.showText(informeActual.getActuacio().getCentre().getNom() + " - " + informeActual.getExpcontratacio().getExpContratacio());
	    	          overContent.endText();
	    	          overContent.restoreState();
	    	          
	    	          if (factura.getNotes()!=null) {
		    	          overContent = stamper.getOverContent( i + 1 );
		    	          overContent.saveState();
		    	          overContent.beginText();
		    	          overContent.setFontAndSize( font, 10.0f );
		    	          overContent.setTextMatrix( 24, 13 );
		    	          overContent.showText("Notes: " + factura.getNotes());
		    	          overContent.endText();
		    	          overContent.restoreState();
	    	          }
	    	          
	    	          overContent = stamper.getOverContent( i + 1 );
	    	          overContent.saveState();
	    	          overContent.beginText();
	    	          overContent.setFontAndSize( font, 10.0f );
	    	          overContent.setTextMatrix( 350, 50 );
	    	          overContent.showText("Vist, el cap d'Administració i Comptabilitat");
	    	          overContent.endText();
	    	          overContent.restoreState();
	    	          
	    	        }
	    	        stamper.close();
	    	        reader.close();
	    	        Fitxers.eliminarFitxer(conn, idUsuari, rutaFactua);
	   			}
	   			factura.setDataEnviatComptabilitat(date);
	   			factura.setDataConformacio(date);
	   			FacturaCore.modificarFactura(conn, factura, idUsuari);
	   			if (informeActual.getOfertaSeleccionada().getPlic() < ConfiguracioCore.getConfiguracio(conn).getImportObraMajor()) {
	   								
	   				if (informeActual.getOfertaSeleccionada().getPlic() > informeActual.getTotalFacturat()) {
	   						   				
	   				} else {
	   					InformeCore.modificarEstat(conn, factura.getIdInforme(), "acabat");
	   					InformeCore.tancar(conn, factura.getIdInforme());
	   				}
	   			}
	   		} catch (DocumentException e) {
	  			e.printStackTrace();
	  			errorString = e.getMessage();
	   		}
	   	}	    
	   	
	   	// Store infomation to request attribute, before forward to views.
	   	request.setAttribute("errorString", errorString);
	  	// If error, forward to Edit page.
	   	if (errorString != null) {
	   		RequestDispatcher dispatcher = request.getServletContext()
	   				.getRequestDispatcher("/WEB-INF/views/actuacio/createActuacioView.jsp");
	   		dispatcher.forward(request, response);
	   	}// If everything nice. Redirect to the product listing page.            
	   	else {
	   		response.sendRedirect(request.getContextPath() + "/facturaDetalls?ref=" + idFactura);
	   	}
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
}
