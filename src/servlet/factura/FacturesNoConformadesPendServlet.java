package servlet.factura;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Factura;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.FacturaCore;
import core.UsuariCore;
import utils.MyUtils;

/**
 * Servlet implementation class FacturesConformadesPendServlet
 */
@WebServlet("/facturesNoConformadesPend")
public class FacturesNoConformadesPendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FacturesNoConformadesPendServlet() {
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
			List<Factura> list = new ArrayList<Factura>();
			String errorString = null;			
			list = FacturaCore.getFacturesNoConformadesPend(conn);

			// Store info in request attribute, before forward to views
			request.setAttribute("errorString", errorString);
			request.setAttribute("facturesList", list);
			request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari,"Factures"));
			// Forward to /WEB-INF/views/homeView.jsp
			// (Users can not access directly into JSP pages placed in WEB-INF)
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/factura/facturaNoConformadaPendent.jsp");

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
