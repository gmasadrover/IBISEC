package servlet.tasca;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;

import bean.Tasca;
import bean.User;
import bean.ControlPage.SectionPage;
import core.ControlPageCore;
import core.TascaCore;
import core.UsuariCore;
import utils.MyUtils;

@WebServlet(urlPatterns = { "/tascaList" })
public class TascaListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public TascaListServlet() {
        super();
    } 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	User usuari = MyUtils.getLoginedUser(request.getSession());
    	Connection conn = MyUtils.getStoredConnection(request);
    	if (usuari == null){
 		   response.sendRedirect(request.getContextPath() + "/preLogin");
    	}else if (!UsuariCore.hasPermision(conn, usuari, SectionPage.tasques_list)) {
    		response.sendRedirect(request.getContextPath() + "/");	
 	   	}else{	
 	   		//Desktop.getDesktop().open(new File("W://1-GESTIÓ ADMINISTRATIVA"));
 	   		String filtrar = request.getParameter("filtrar");
	        String errorString = null;
	        List<Tasca> list = null;
	        List<User> llistaUsuaris = null;
	        String idUsuari = request.getParameter("idUsuari");
	        String usuariSelected = String.valueOf(usuari.getIdUsuari());
	        try {
	        	if (filtrar != null) {
	        		if (NumberUtils.isNumber(idUsuari)) {
	        			list = TascaCore.llistaTasquesUsuari(conn, Integer.parseInt(idUsuari));
	        		}else{
	        			list = TascaCore.llistaTasquesArea(conn, idUsuari);
	        		}
	        		usuariSelected = idUsuari;
	        	}else{
	        		list = TascaCore.llistaTasquesUsuari(conn, usuari.getIdUsuari());		            
	        	}	 
	        	llistaUsuaris =  UsuariCore.findUsuarisByRol(conn, "");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        }	        
	        
	        // Store info in request attribute, before forward to views
	        request.setAttribute("llistaUsuaris", llistaUsuaris);
	        request.setAttribute("errorString", errorString);
	        request.setAttribute("tasquesList", list);  
	        request.setAttribute("usuariSelected", usuariSelected);
		    request.setAttribute("menu", ControlPageCore.renderMenu(conn, usuari, "Tasques"));
	     
	        // Forward to /WEB-INF/views/productListView.jsp
	        RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/tasca/tascaListView.jsp");
	        dispatcher.forward(request, response);
 	   	}
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}

