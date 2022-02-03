package servlet.empresa;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Empresa;
import bean.User;
import core.EmpresaCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoCreateEmpresaServlet
 */
@WebServlet(urlPatterns = { "/doCreateEmpresa" })
public class DoCreateEmpresaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateEmpresaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		User usuari = MyUtils.getLoginedUser(request.getSession());
		
		String cif = request.getParameter("cif");
		String name = request.getParameter("name");
		if (cif.isEmpty()) cif = name;
		String direccio = request.getParameter("direccio");
		String cp = request.getParameter("cp");
		String ciutat = URLDecoder.decode(request.getParameter("localitat"), "UTF-8");
		String provincia = URLDecoder.decode(request.getParameter("provincia").split("_")[1], "UTF-8");
		String telefon = request.getParameter("telefon");
		String fax = request.getParameter("fax");
		String email = request.getParameter("email");
		String tipus = request.getParameter("tipus");
			
		List<Empresa.Administrador> administradors = new ArrayList<Empresa.Administrador>();
		
		Empresa empresa = new Empresa();
		empresa.setCif(cif);
		empresa.setName(name);
		empresa.setDireccio(direccio);
		empresa.setCP(cp);
		empresa.setCiutat(ciutat);
		empresa.setProvincia(provincia);
		empresa.setTelefon(telefon);
		empresa.setFax(fax);
		empresa.setEmail(email);
		empresa.setTipus(tipus);
		empresa.setAdministradors(administradors);
		
			EmpresaCore.insertEmpresa(conn, empresa, administradors, usuari.getIdUsuari());
		
        
		// Store infomation to request attribute, before forward to views.
		
		request.setAttribute("empresa", empresa);
 
		
			response.sendRedirect(request.getContextPath() + "/empresaList");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
