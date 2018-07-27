package servlet.factura;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.ControlPage.SectionPage;
import bean.Empresa;
import bean.Factura;
import core.ControlPageCore;
import core.EmpresaCore;
import core.FacturaCore;
import core.InformeCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class FacturaListServlet
 */
@WebServlet("/factures")
public class FacturaListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FacturaListServlet() {
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
		}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.factures_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
		} else {
			List<Factura> list = new ArrayList<Factura>();
			List<Empresa> empresesList = new ArrayList<Empresa>();
			String errorString = null;
			String filtrar = request.getParameter("filtrar");
			String estatFactura = request.getParameter("estatFactura");
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance(); 
			Date dataFi = cal.getTime();
			String dataFiString = df.format(dataFi);	 
			cal.add(Calendar.MONTH, -2);
			Date dataInici = cal.getTime();
			String dataIniciString = df.format(dataInici);			
			String empresaSeleccionada = request.getParameter("llistaEmpreses");
			try {
				empresesList = EmpresaCore.getEmpreses(conn);
				empresesList.addAll(EmpresaCore.getEmpresesUTE(conn));
				if (filtrar != null) {	
					dataInici = null;
					dataFi = null;
					if (!request.getParameter("dataInici").isEmpty()) dataInici = df.parse(request.getParameter("dataInici"));
					dataIniciString = request.getParameter("dataInici");
	    			if (!request.getParameter("dataFi").isEmpty()) dataFi = df.parse(request.getParameter("dataFi"));	    			
	    			dataFiString = request.getParameter("dataFi");	 
	    			list = FacturaCore.advancedSearchFactures(conn, dataInici, dataFi, estatFactura);	    				    			
				} else {
					list = FacturaCore.advancedSearchFactures(conn, dataInici, dataFi,"-1");
				}
				
			} catch (SQLException | ParseException | NamingException e) {
				e.printStackTrace();
				errorString = e.getMessage();			
			}

			// Store info in request attribute, before forward to views
			request.setAttribute("errorString", errorString);
			request.setAttribute("facturesList", list);
			request.setAttribute("dataInici", dataIniciString);
		    request.setAttribute("dataFi", dataFiString);
		    request.setAttribute("estatFactura", estatFactura);
		    request.setAttribute("empresesList", empresesList);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Factures"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/factura/facturaListView.jsp");

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
