package servlet.factura;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Factura;
import bean.User;
import bean.ControlPage.SectionPage;
import core.FacturaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoDownloadFacturesServlet
 */
@WebServlet("/doDownloadFactures")
public class DoDownloadFacturesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoDownloadFacturesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuari = MyUtils.getLoginedUser(request.getSession());
		if (usuari == null) {
			response.sendRedirect(request.getContextPath() + "/preLogin");
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.factures_crear)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			//Create list for file URLs - these are files from all different locations
		    List<String> filenames = new ArrayList<String>();
		    try {
				List<Factura> factures = FacturaCore.getFacturesConformadesPend(conn);
				for (Factura factura: factures) {
					 if (factura.getArxiu() != null && factura.getArxiu().getRuta() != null) {
						 FacturaCore.descarregada(conn, factura.getIdFactura());
						 if (!filenames.contains(factura.getArxiu().getRuta())) filenames.add(factura.getArxiu().getRuta());
					 }
				}
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		    
		    
		    //..code to add URLs to the list
		    byte[] buf = new byte[2048];
		    // Create the ZIP file
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ZipOutputStream out = new ZipOutputStream(baos);
		    // Compress the files
		    for (int i=0; i<filenames.size(); i++) {
			    FileInputStream fis = new FileInputStream(filenames.get(i).toString());
			    BufferedInputStream bis = new BufferedInputStream(fis);
			    // Add ZIP entry to output stream.
			    File file = new File(filenames.get(i).toString());
			    String entryname = file.getName();			    
			    out.putNextEntry(new ZipEntry(entryname));
			    int bytesRead;
			    while ((bytesRead = bis.read(buf)) != -1) {
				    out.write(buf, 0, bytesRead);
			    }
			    out.closeEntry();
			    bis.close();
			    fis.close();
		    }
		    out.flush();
		    baos.flush();
		    out.close();
		    baos.close();
		    ServletOutputStream sos = response.getOutputStream();
		    response.setContentType("application/zip");
		    response.setHeader("Content-Disposition", "attachment; filename=\"Factures.ZIP\"");
		    sos.write(baos.toByteArray());
		    out.flush();
		    out.close();
		    sos.flush();
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
