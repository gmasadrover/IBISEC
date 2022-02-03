package servlet.credit;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Partida;
import core.CreditCore;
import utils.MyUtils;

/**
 * Servlet implementation class DoEditPartidaServlet
 */
@WebServlet("/DoEditPartida")
public class DoEditPartidaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditPartidaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		
		String codi = request.getParameter("codi");	
		String nom = request.getParameter("nom");
		double totalPartida =  Double.parseDouble(request.getParameter("import").replace(",", "."));	
		String tipus = request.getParameter("idTipus");		
		Partida partida = new Partida(); 
	
           if (request.getParameter("modificar") != null) {
			   partida = CreditCore.getPartida(conn, codi);
			   partida.setNom(nom);
			   partida.setTotalPartida(totalPartida);
			   partida.setTipus(tipus);
			   CreditCore.updatePartida(conn, partida);
		   } else {
			   if (request.getParameter("obrir") != null) {
				   CreditCore.obrirPartida(conn, codi);
			   }else{
				   CreditCore.tancarPartida(conn, codi);
			   }
			   
		   }
		
        
       // Store infomation to request attribute, before forward to views.
     
       request.setAttribute("partida", partida);	      
	   response.sendRedirect(request.getContextPath() + "/partidaDetalls?codi=" + codi);
       
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
