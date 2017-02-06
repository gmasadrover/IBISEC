package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ControlPage.SectionPage;
import bean.User;

public class UsuariCore {
	private static User initUsuari(ResultSet rs) throws SQLException {
		User usuari = new User();
		usuari.setIdUsuari(rs.getInt("idUsuari"));
		usuari.setName(rs.getString("nom"));
		usuari.setLlinatges(rs.getString("llinatges"));
		usuari.setRol(rs.getString("rol"));
		usuari.setCarreg(rs.getString("carreg"));
		usuari.setUsuari(rs.getString("usuari"));
		return usuari;
	}
	
	
	public static User findUsuari(Connection conn, String usuari, String password) throws SQLException {
		 
		String sql = "Select \"idUsuari\", nom, llinatges, rol, carreg, usuari from public.\"tbl_Usuaris\" "
					+ " where usuari = ? and password= ?";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, usuari);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();
	 
		if (rs.next()) {
			User user = initUsuari(rs);			
			return user;
		}
		return null;
	}
	 
	public static User findUsuari(Connection conn, String Usuari) throws SQLException {

		String sql = "Select \"idUsuari\", nom, llinatges, rol, carreg, usuari from public.\"tbl_Usuaris\" where usuari = ? ";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, Usuari);
	 
		ResultSet rs = pstm.executeQuery();
	 
		if (rs.next()) {		
			User user = initUsuari(rs);			
			return user;
		}
		return null;
	}	
	
	public static User findUsuariByID(Connection conn, int idUsuari) throws SQLException{
		String sql = "Select \"idUsuari\", nom, llinatges, rol, carreg, usuari from public.\"tbl_Usuaris\" where \"idUsuari\" = ? ";
		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);
	 
		ResultSet rs = pstm.executeQuery();
	 
		if (rs.next()) {		
			User user = initUsuari(rs);	
			return user;
		}
		return null;
	}
	
	public static List<User> findUsuarisByTipus(Connection conn, String tipus) throws SQLException{
		List<User> userList = new ArrayList<User>();
		User user = new User();
		String sql = "Select \"idUsuari\", nom, llinatges, rol, carreg, usuari from public.\"tbl_Usuaris\" where rol LIKE '%" + tipus + "%' ";		 
		PreparedStatement pstm = conn.prepareStatement(sql);		
		ResultSet rs = pstm.executeQuery();		 
		while (rs.next()) {
			user = initUsuari(rs);			
			userList.add(user);
		}
		return userList;
	}
	
	public static List<User> llistaUsuaris(Connection conn) throws SQLException {
		String sql = "SELECT \"idUsuari\", nom, llinatges, rol, carreg, usuari FROM public.\"tbl_Usuaris\"";
		 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		List<User> list = new ArrayList<User>();
		while (rs.next()) {
			User user = initUsuari(rs);			
			list.add(user);
		}
		return list;
		
	}
	
	public static void modificarDades(Connection conn, int idUsuari, User newUsuari) throws SQLException {
		String sql = "UPDATE public.\"tbl_Usuaris\"	SET nom=?, llinatges=?, carreg=?"
				  	+ " WHERE \"idUsuari\"=?";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, newUsuari.getName());
		pstm.setString(2, newUsuari.getLlinatges());
		pstm.setString(3, newUsuari.getCarreg());
		pstm.setInt(4, idUsuari);
		pstm.executeUpdate();
	}
	
	public static void modificarPassword(Connection conn, int idUsuari, String newPassword) throws SQLException {
		String sql = "UPDATE public.\"tbl_Usuaris\"	SET password=?"
				  	+ " WHERE \"idUsuari\"=?";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, newPassword);
		pstm.setInt(2, idUsuari);
		pstm.executeUpdate();
	}
	
	public static boolean coincideixPassword(Connection conn, int idUsuari, String oldPassword) throws SQLException {
		boolean coincideix = false;
		String sql = "SELECT password FROM public.\"tbl_Usuaris\" "
			  	+ " WHERE \"idUsuari\"=?";	
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			coincideix = oldPassword.equals(rs.getString("password"));
		}
		return coincideix;
	}
	
	public static boolean hasPermision(Connection conn, User usuari, SectionPage section) {
		boolean permision = false;
		String rols = usuari.getRol();
		switch (section) {
			case actuacio_list:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case actuacio_modificar:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case actuacio_detalls:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case incidencia_list:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case incidencia_modificar:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case incidencia_detalls:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case empreses_crear:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case empreses_list:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case partides_crear:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case partides_list:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case registre_detalls:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case registre_ent_crear:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case registre_ent_list:
				permision = true;
				break;
			case registre_sort_crear:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case registre_sort_list:
				permision = true;
				break;
			case tasques_crear:
				permision = (rols.toUpperCase().contains("ADMIN"));
				break;
			case tasques_list:
				permision = true;
				break;
			case usuari_detalls:
				permision = true;
				break;
			default:
				break;
		}
		return permision;
	}
}
