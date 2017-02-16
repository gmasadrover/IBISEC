package servlet.empresa;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
		String direccio = request.getParameter("direccio");
		String cp = request.getParameter("cp");
		String ciutat = URLDecoder.decode(request.getParameter("localitat"), "UTF-8");
		String provincia = URLDecoder.decode(request.getParameter("provincia").split("_")[1], "UTF-8");
		String telefon = request.getParameter("telefon");
		String fax = request.getParameter("fax");
		String email = request.getParameter("email");
		String objecteSocial = request.getParameter("objSocial");
		String classificacio = request.getParameter("llistatClassificacio");
		Boolean acreditacio1 = "on".equals(request.getParameter("acreditacio1"));
		Boolean acreditacio2 = "on".equals(request.getParameter("acreditacio2"));
		Boolean acreditacio3 = "on".equals(request.getParameter("acreditacio3"));
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dataConstitucio = new Date();
		Date dateExpAcreditacio1 = new Date();
		Date dateExpAcreditacio2 = new Date();
		Date dateExpAcreditacio3 = new Date();
		try {
			dataConstitucio = formatter.parse(request.getParameter("constitucio"));
			dateExpAcreditacio1 = formatter.parse(request.getParameter("dateExpAcreditacio1"));
			dateExpAcreditacio2 = formatter.parse(request.getParameter("dateExpAcreditacio2"));
			dateExpAcreditacio3 = formatter.parse(request.getParameter("dateExpAcreditacio3"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
			
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
		empresa.setDataConstitucio(dataConstitucio);
		empresa.setObjecteSocial(objecteSocial);
		empresa.setClassificacioString(classificacio);
		empresa.setAdministradors(administradors);
		empresa.setAcreditacio1(acreditacio1);
		empresa.setDateExpAcreditacio1(dateExpAcreditacio1);
		empresa.setAcreditacio2(acreditacio2);
		empresa.setDateExpAcreditacio2(dateExpAcreditacio2);
		empresa.setAcreditacio3(acreditacio3);
		empresa.setDateExpAcreditacio3(dateExpAcreditacio3);
		
		String errorString = null; 
      
		if (errorString == null) {
			try {
				EmpresaCore.insertEmpresa(conn, empresa, administradors, usuari.getIdUsuari());
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}
        
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("empresa", empresa);
 
		// If error, forward to Edit page.
		if (errorString != null) {
			RequestDispatcher dispatcher = request.getServletContext()
               .getRequestDispatcher("/WEB-INF/views/empresa/createEmpresaView.jsp");
           dispatcher.forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/empresaList");
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
