package servlet.empresa;

import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
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
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.EmpresaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class EmpresaListDespesaView
 */
@WebServlet("/empresaDespesaList")
public class EmpresaListDespesaView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpresaListDespesaView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
    	if (MyUtils.getLoginedUser(request.getSession()) == null){
 		   response.sendRedirect(request.getContextPath() + "/");
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.empreses_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	 
 	   	}else{	 
	        List<Empresa> list = null;
			Calendar cal = Calendar.getInstance(); 
			Date dataFi = cal.getTime();
			dataFi = new Date(dataFi.getTime());
			cal.set(2019, 0, 1);
			Date dataIni = cal.getTime();
	        list = EmpresaCore.getDespesaEmpreses(conn, dataIni, dataFi);
	   
	        // Store info in request attribute, before forward to views
	        
	        request.setAttribute("empresesList", list);
	        request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Empreses")); 
	     
	        // Forward to /WEB-INF/views/productListView.jsp
	        RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/empresa/empresaDespesaListView.jsp");
	        dispatcher.forward(request, response);
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
