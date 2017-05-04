package servlet.empresa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * Servlet implementation class DoCreateUTEServlet
 */
@WebServlet("/doCreateUTE")
public class DoCreateUTEServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateUTEServlet() {
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
		List<Empresa> empreses = new ArrayList<Empresa>();
		String[] paramValues = request.getParameterValues("empreses");
		String errorString = "";
		for(int i=0; i<paramValues.length; i++) {
			try {
				empreses.add(EmpresaCore.findEmpresa(conn, paramValues[i]));
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				errorString = e.getMessage();
				e.printStackTrace();
			}	    	
	    }
		Empresa empresa = new Empresa();
		empresa.setCif(cif);
		empresa.setName(name);
		Empresa.UTE ute = empresa.new UTE();
		ute.setEmpreses(empreses);
		empresa.setUte(ute);
		try {
			EmpresaCore.insertUTE(conn, empresa, usuari.getIdUsuari());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("empresa", empresa);
 
		// If error, forward to Edit page.
		if (! errorString.isEmpty()) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/empresa/createUTEView.jsp");
           dispatcher.forward(request, response);
		} else {
           response.sendRedirect(request.getContextPath() + "/empresa?cif=" + empresa.getCif());
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
