package servlet.usuari;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import bean.User;
import core.LoggerCore;
import core.UsuariCore;
import utils.MyUtils;
 
@WebServlet(urlPatterns = { "/doLogin" })
public class DoLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public DoLoginServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        boolean newUser = false;
       
        User user = null;
        boolean hasError = false;
        String errorString = null;
 
        if (userName == null || userName.length() == 0) {
            hasError = true;
            errorString = "Required username and password!";
        } else {
            Connection conn = MyUtils.getStoredConnection(request);
            try {   
            	user = UsuariCore.findUsuari(conn, userName);
            	if (user == null) {
                    hasError = true;
                    errorString = "Usuari incorrecte";
                }
            	else {
            		if (repeatPassword == null) {    
	            		if (user.getPassword().isEmpty()) {
	            			hasError = true;
	            			newUser = true;
	            			errorString = "És el teu primer accès? Introdueix la contrasenya que desitgis";            			
	            		} else if (! UsuariCore.coincideixPassword(conn, user.getIdUsuari(), password)) {
		                    hasError = true;
		                    errorString = "Password invalid";
		                }
            		} else {
            			if (! password.equals(repeatPassword)){
            				hasError = true;
		                    errorString = "No coincideixen els passwords";
            			} else {
            				UsuariCore.modificarPassword(conn, user.getIdUsuari(), password);
            			}
            		}
            	}
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        
        // If error, forward to /WEB-INF/views/login.jsp
        if (hasError) {        	
            user = new User();
            user.setName(userName);
            user.setPassword(password);           
        
            // Store information in request attribute, before forward.
            request.setAttribute("newUser", newUser);
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);
       
            // Forward to /WEB-INF/views/productListView.jsp
	        RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/usuari/preLoginView.jsp");
	        dispatcher.forward(request, response);
            //response.sendRedirect(request.getContextPath());
        }
     
        // If no error
        // Store user information in Session
        // And redirect to userInfo page.
        else {
            HttpSession session = request.getSession();
            MyUtils.storeLoginedUser(session, user);            
            // Redirect to userInfo page.
            response.sendRedirect(request.getContextPath() + "/tascaList");
        }
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}