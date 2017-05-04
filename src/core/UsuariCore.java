package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import bean.ControlPage.SectionPage;
import bean.User;

public class UsuariCore {
	private static User initUsuari(ResultSet rs) throws SQLException {
		User usuari = new User();
		usuari.setIdUsuari(rs.getInt("idusuari"));
		usuari.setName(rs.getString("nom"));
		usuari.setLlinatges(rs.getString("cognoms"));
		usuari.setRol(rs.getString("rol"));
		usuari.setCarreg(rs.getString("carreg"));
		usuari.setDepartament(rs.getString("departament"));
		usuari.setUsuari(rs.getString("nomusuari"));
		usuari.setPassword(rs.getString("password"));
		usuari.setAlias(rs.getString("alias"));
		return usuari;
	}
	
	public static User finCap(Connection conn, String departament) throws SQLException {
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias"
				+ " FROM public.tbl_usuaris"
				+ " WHERE departament = ? AND rol LIKE '%CAP%'";
 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, departament);
	 
		ResultSet rs = pstm.executeQuery();		
		if (rs.next()) {		
			User user = initUsuari(rs);			
			return user;
		}
		return null;
	}
	 
	public static User findUsuari(Connection conn, String Usuari) throws SQLException {
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias"
					+ " FROM public.tbl_usuaris"
					+ " WHERE nomusuari = ? ";
	 
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
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias"
					+ " FROM public.tbl_usuaris"
					+ " WHERE idusuari = ? ";
		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);
	 
		ResultSet rs = pstm.executeQuery();
	 
		if (rs.next()) {		
			User user = initUsuari(rs);	
			return user;
		}
		return null;
	}
	
	public static List<User> findUsuarisByRol(Connection conn, String tipus) throws SQLException{
		List<User> userList = new ArrayList<User>();
		User user = new User();
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias"
					+ " FROM public.tbl_usuaris"
					+ " WHERE actiu = true and departament <> '' and rol LIKE '%" + tipus + "%' "
					+ " ORDER BY 3, 2";		 
		PreparedStatement pstm = conn.prepareStatement(sql);		
		ResultSet rs = pstm.executeQuery();		 
		while (rs.next()) {
			user = initUsuari(rs);			
			userList.add(user);
		}
		return userList;
	}
	
	public static List<User> findUsuarisByDepartament(Connection conn, String tipus) throws SQLException{
		List<User> userList = new ArrayList<User>();
		User user = new User();
		
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias"
					+ " FROM public.tbl_usuaris"
					+ " WHERE actiu = true ";
		if (!"gerencia".equals(tipus)) {
			sql += "and departament = '" + tipus + "'";
		} else {
			sql += "and departament <> ''";
		}
		
		sql += " ORDER BY 3, 2";		 
		PreparedStatement pstm = conn.prepareStatement(sql);		
		ResultSet rs = pstm.executeQuery();	
		while (rs.next()) {			
			user = initUsuari(rs);			
			userList.add(user);
		}
		return userList;
	}
	
	public static List<User> llistaUsuaris(Connection conn) throws SQLException {
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias"
					+ " FROM public.tbl_usuaris";
		 
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
		String sql = "UPDATE public.tbl_usuaris"
					+ " SET nom=?, cognoms=?, carreg=?"
				  	+ " WHERE idusuari=?";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, newUsuari.getName());
		pstm.setString(2, newUsuari.getLlinatges());
		pstm.setString(3, newUsuari.getCarreg());
		pstm.setInt(4, idUsuari);
		pstm.executeUpdate();
	}
	
	public static void modificarPassword(Connection conn, int idUsuari, String newPassword) throws SQLException {
		String sql = "UPDATE public.tbl_usuaris"
					+ " SET password=?"
				  	+ " WHERE idusuari=?";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		String newPasswordMD5=DigestUtils.md5Hex(newPassword); 
		pstm.setString(1, newPasswordMD5);
		pstm.setInt(2, idUsuari);
		pstm.executeUpdate();
	}
	
	public static boolean coincideixPassword(Connection conn, int idUsuari, String oldPassword) throws SQLException {
		boolean coincideix = false;
		String sql = "SELECT password FROM public.tbl_usuaris"
			  		+ " WHERE idusuari=?";	
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			String oldPasswordMD5=DigestUtils.md5Hex(oldPassword); 
			coincideix = oldPasswordMD5.equals(rs.getString("password"));
		}
		return coincideix;
	}
	
	public static boolean hasPermision(Connection conn, User usuari, SectionPage section) {
		boolean permision = false;
		String rols = usuari.getRol();
		switch (section) {
			case actuacio_list:
				permision = true;
				break;
			case actuacio_modificar:
				permision = (rols.toUpperCase().contains("ADMIN")) || (rols.toUpperCase().contains("GER"));
				break;
			case actuacio_detalls:
				permision = true;
				break;
			case incidencia_list:
				permision = (rols.toUpperCase().contains("ADMIN")) || (rols.toUpperCase().contains("GER"));
				break;
			case incidencia_modificar:
				permision = (rols.toUpperCase().contains("ADMIN")) || (rols.toUpperCase().contains("GER"));
				break;
			case incidencia_detalls:
				permision = (rols.toUpperCase().contains("ADMIN")) || (rols.toUpperCase().contains("GER"));
				break;
			case empreses_crear:
				permision = (rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("JUR"));
				break;
			case empreses_list:
				permision = true;
				break;
			case centres_list:
				permision = true;
				break;
			case centres_detalls:
				permision = true;
				break;
			case partides_crear:
				permision = (rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("CONTA"));
				break;
			case partides_list:
				permision = true;
				break;
			case registre_detalls:
				permision = true;
				break;
			case registre_ent_crear:
				permision = (rols.toUpperCase().contains("ADM"));
				break;
			case registre_ent_list:
				permision = true;
				break;
			case registre_sort_crear:
				permision = (rols.toUpperCase().contains("ADM"));
				break;
			case registre_sort_list:
				permision = true;
				break;
			case tasques_crear:
				permision = (rols.toUpperCase().contains("ADMIN")) || (rols.toUpperCase().contains("CAP")) ;
				break;
			case tasques_list:
				permision = true;
				break;
			case tasques_detalls:
				permision = true;
				break;
			case usuari_detalls:
				permision = true;
				break;
			case obres_list:
				permision = rols.toUpperCase().contains("ADMIN") || (rols.toUpperCase().contains("GER"));
				break;
			case factures_list:
				permision = true;
				break;
			case factures_crear:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("CONTA");
				break;
			case llistats_list:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("GER");
			default:
				break;
		}
		return permision;
	}
}
