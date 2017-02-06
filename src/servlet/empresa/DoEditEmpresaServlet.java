package servlet.empresa;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;

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

@WebServlet(urlPatterns = { "/doEditEmpresa" })
public class DoEditEmpresaServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
 
   public DoEditEmpresaServlet() {
       super();
   }
 
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
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
       Empresa empresa = new Empresa(cif, name, direccio, cp, ciutat, provincia, telefon, fax, email);
 
       String errorString = null;
 
       try {
           EmpresaCore.updateEmpresa(conn, empresa, usuari.getIdUsuari());
       } catch (SQLException e) {
           e.printStackTrace();
           errorString = e.getMessage();
       }
 
       // Store infomation to request attribute, before forward to views.
       request.setAttribute("errorString", errorString);
       request.setAttribute("empresa", empresa);
 
 
       // If error, forward to Edit page.
       if (errorString != null) {
           RequestDispatcher dispatcher = request.getServletContext()
                   .getRequestDispatcher("/WEB-INF/views/empresa/editEmpresaView.jsp");
           dispatcher.forward(request, response);
       }
        
       // If everything nice.
       // Redirect to the product listing page.            
       else {
           response.sendRedirect(request.getContextPath() + "/empresaList");
       }
 
   }
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       doGet(request, response);
   }
 
}
