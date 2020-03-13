package servlet.expedient;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import bean.InformeActuacio;
import bean.User;
import bean.ControlPage.SectionPage;
import core.FacturaCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoDownloadExpedientServlet
 */
@WebServlet("/doDownloadExpedient")
public class DoDownloadExpedientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoDownloadExpedientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuari = MyUtils.getLoginedUser(request.getSession());
		InformeActuacio informe = null;
		if (usuari == null) {
			response.sendRedirect(request.getContextPath() + "/preLogin");
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_detalls)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			//Create list for file URLs - these are files from all different locations
		    List<String> filenames = new ArrayList<String>();
		    try {
		    	informe = InformeCore.getInformePrevi(conn, request.getParameter("idInforme"), true);
				/*if (informe.getInformeSupervisio() != null && informe.getInformeSupervisio().getRuta() != null) {
					filenames.add(informe.getInformeSupervisio().getRuta());
				}*/
				if (informe.getConformeAreaEconomivaPropostaActuacio() != null && informe.getConformeAreaEconomivaPropostaActuacio().getRuta() != null) {
					filenames.add(informe.getConformeAreaEconomivaPropostaActuacio().getRuta());
				}
				if (informe.getAutoritzacioConseller() != null && informe.getAutoritzacioConseller().getRuta() != null) {
					filenames.add(informe.getAutoritzacioConseller().getRuta());
				}
				/*if (informe.getAutoritzacioConsellDeGovern() != null && informe.getAutoritzacioConsellDeGovern().getRuta() != null) {
					filenames.add(informe.getAutoritzacioConsellDeGovern().getRuta());
				}*/
				if (informe.getMemoriaOrdreInici() != null && informe.getMemoriaOrdreInici().getRuta() != null) {
					filenames.add(informe.getMemoriaOrdreInici().getRuta());
				}
				if (informe.getJustProcForma() != null && informe.getJustProcForma().getRuta() != null) {
					filenames.add(informe.getJustProcForma().getRuta());
				}
				if (informe.getAprovacioDispoTerrenys() != null && informe.getAprovacioDispoTerrenys().getRuta() != null) {
					filenames.add(informe.getAprovacioDispoTerrenys().getRuta());
				}
				if (informe.getAprovacioEXPPlecsDespesa() != null && informe.getAprovacioEXPPlecsDespesa().getRuta() != null) {
					filenames.add(informe.getAprovacioEXPPlecsDespesa().getRuta());
				}
				//if (informe.getAutoritzacioPropostaDespesa() != null && informe.getAutoritzacioPropostaDespesa().getRuta() != null) {
				//	filenames.add(informe.getAutoritzacioPropostaDespesa().getRuta());
				//}
				if (informe.getContracteSignat() != null && informe.getContracteSignat().getRuta() != null) {
					filenames.add(informe.getContracteSignat().getRuta());
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
		    response.setHeader("Content-Disposition", "attachment; filename=\"Expedient_" + informe.getExpcontratacio().getExpContratacio() + ".ZIP\"");
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
