package servlet.empresa;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Empresa;
import bean.User;
import bean.Empresa.Administrador;
import core.EmpresaCore;
import utils.Fitxers;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditAdministradorServlet
 */
@WebServlet("/doEditAdministrador")
public class DoEditAdministradorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditAdministradorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);	
		Fitxers.formParameters multipartParams = new Fitxers.formParameters();
		multipartParams = Fitxers.getParamsFromMultipartForm(request);
		String modificar = multipartParams.getParametres().get("modificar");
		String eliminar = multipartParams.getParametres().get("eliminar");
		String cif = multipartParams.getParametres().get("cif");
		String nifAdministrador = multipartParams.getParametres().get("dniAdmin");	
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		User Usuari = MyUtils.getLoginedUser(request.getSession());	   
		try {
			if (eliminar != null) {
				EmpresaCore.deleteAdministrador(conn, cif, nifAdministrador, Usuari.getIdUsuari());
			}else if (modificar != null){
				Administrador administrador = new Empresa().new Administrador();
				administrador.setNom(multipartParams.getParametres().get("nomAdmin"));
		    	administrador.setDni(multipartParams.getParametres().get("dniAdmin"));
		    	administrador.setTipus(multipartParams.getParametres().get("tipusAdmin"));
		    	if (! multipartParams.getParametres().get("validAdmin").isEmpty()) administrador.setDataValidesaFins(formatter.parse(multipartParams.getParametres().get("validAdmin")));
		    	administrador.setNotariModificacio(multipartParams.getParametres().get("nomNotari"));
		    	if (! multipartParams.getParametres().get("numProtocol").isEmpty()) administrador.setProtocolModificacio(multipartParams.getParametres().get("numProtocol"));
		    	if (! multipartParams.getParametres().get("dataAlta").isEmpty()) administrador.setDataModificacio(formatter.parse(multipartParams.getParametres().get("dataAlta")));
		    	if (! multipartParams.getParametres().get("dataValidacio").isEmpty()) {
		    		administrador.setDataValidacio(formatter.parse(multipartParams.getParametres().get("dataValidacio")));		    		
		    	} 
		    	administrador.setEntitatValidacio(multipartParams.getParametres().get("organValidador"));
		    	EmpresaCore.updateAdministrador(conn, cif, nifAdministrador, administrador);
		    	EmpresaCore.guardarFitxer(conn, Usuari.getIdUsuari(), multipartParams.getFitxers(), cif, nifAdministrador);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// If error, forward to Edit page.
		if (eliminar != null) {
			response.sendRedirect(request.getContextPath() + "/editEmpresa?cif=" + cif);
		} else {
			response.sendRedirect(request.getContextPath() + "/editAdministrador?empresa=" + cif + "&administrador=" + nifAdministrador);
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
