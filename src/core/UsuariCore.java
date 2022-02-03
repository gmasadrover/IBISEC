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
	private static User initUsuari(ResultSet rs) {
		User usuari = new User();
		try {
			usuari.setIdUsuari(rs.getInt("idusuari"));
			usuari.setName(rs.getString("nom"));
			usuari.setLlinatges(rs.getString("cognoms"));
			usuari.setRol(rs.getString("rol"));
			usuari.setCarreg(rs.getString("carreg"));
			usuari.setDepartament(rs.getString("departament"));
			usuari.setUsuari(rs.getString("nomusuari"));
			usuari.setPassword(rs.getString("password"));
			usuari.setAlias(rs.getString("alias"));
			usuari.setVacances(rs.getInt("vacances"));
			usuari.setPermisos(rs.getInt("permisos"));
			usuari.setActiu(rs.getBoolean("actiu"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usuari;
	}
	
	public static User finCap(Connection conn, String departament) {
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias, vacances, permisos, actiu"
				+ " FROM public.tbl_usuaris"
				+ " WHERE departament = ?";
				
		if (departament.equals("gerencia")) {
			sql += " AND rol LIKE '%GERENT%'";
		} else {
			sql += " AND rol LIKE '%CAP%'";
		}
 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, departament);
			 
			ResultSet rs = pstm.executeQuery();		
			if (rs.next()) {		
				User user = initUsuari(rs);			
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	 
	public static User findUsuari(Connection conn, String Usuari) {
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias, vacances, permisos, actiu"
					+ " FROM public.tbl_usuaris"
					+ " WHERE nomusuari = ? ";
	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, Usuari);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {		
				User user = initUsuari(rs);			
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}	
	
	public static User findUsuariByID(Connection conn, int idUsuari) {
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias, vacances, permisos, actiu"
					+ " FROM public.tbl_usuaris"
					+ " WHERE idusuari = ? ";
		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);
			 
			ResultSet rs = pstm.executeQuery();
		 
			if (rs.next()) {		
				User user = initUsuari(rs);	
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static List<User> findUsuarisByRol(Connection conn, String tipus) {
		List<User> userList = new ArrayList<User>();
		User user = new User();
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias, vacances, permisos, actiu"
					+ " FROM public.tbl_usuaris"
					+ " WHERE actiu = true and departament <> '' and departament <> 'conselleria' and rol LIKE '%" + tipus + "%' "
					+ " ORDER BY 6, 3, 2";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();		 
			while (rs.next()) {
				user = initUsuari(rs);			
				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return userList;
	}
	
	public static List<User> findUsuarisByDepartament(Connection conn, String tipus) {
		List<User> userList = new ArrayList<User>();
		User user = new User();
		
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias, vacances, permisos, actiu"
					+ " FROM public.tbl_usuaris"
					+ " WHERE actiu = true ";
		if (!"gerencia".equals(tipus)) {
			sql += "and departament = '" + tipus + "'";
		} else {
			sql += "and departament <> '' and departament <> 'conselleria'";
		}
		
		sql += " ORDER BY 6, 3, 2";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();	
			while (rs.next()) {			
				user = initUsuari(rs);			
				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return userList;
	}
	
	public static List<User> findUsuarisDepartament(Connection conn, String tipus) {
		List<User> userList = new ArrayList<User>();
		User user = new User();		
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias, vacances, permisos, actiu"
					+ " FROM public.tbl_usuaris"
					+ " WHERE actiu = true ";
		
		sql += "and departament = '" + tipus + "'";
		sql += " ORDER BY 6, 3, 2";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();	
			while (rs.next()) {			
				user = initUsuari(rs);			
				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return userList;
	}
	
	public static List<User> llistaUsuaris(Connection conn, boolean actius) {
		List<User> list = new ArrayList<User>();
		String sql = "SELECT idusuari, nom, cognoms, rol, carreg, departament, nomusuari, password, alias, vacances, permisos, actiu"
					+ " FROM public.tbl_usuaris";		
		if (actius) sql += " WHERE actiu = true and departament <> 'conselleria'";					
		sql += " ORDER BY 6, 3, 2";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();		
			
			while (rs.next()) {
				User user = initUsuari(rs);			
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	public static int nouUsuari(Connection conn, User newUsuari) {
		String sql = "INSERT INTO public.tbl_usuaris(idusuari, nomusuari, nom, cognoms, carreg, departament, rol, password, actiu)"
					 + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pstm;
		int nouCodi = nouCodi(conn);
		try {
			pstm = conn.prepareStatement(sql);			
			pstm.setInt(1, nouCodi);
			pstm.setString(2, newUsuari.getUsuari());
			pstm.setString(3, newUsuari.getName());
			pstm.setString(4, newUsuari.getLlinatges());
			pstm.setString(5, newUsuari.getCarreg());
			pstm.setString(6, newUsuari.getDepartament());
			pstm.setString(7, "");
			pstm.setString(8, "");
			pstm.setBoolean(9, true);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nouCodi;
	}
	
	public static int nouCodi(Connection conn) {
		int nouCodi = -1;
		String sql = "SELECT idusuari FROM public.tbl_usuaris WHERE idusuari < 900 ORDER BY 1 DESC LIMIT 1";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) nouCodi = rs.getInt("idusuari") + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nouCodi;
	}
	
	public static void modificarDades(Connection conn, int idUsuari, User newUsuari) {
		String sql = "UPDATE public.tbl_usuaris"
					+ " SET nom=?, cognoms=?, carreg=?"
				  	+ " WHERE idusuari=?";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, newUsuari.getName());
			pstm.setString(2, newUsuari.getLlinatges());
			pstm.setString(3, newUsuari.getCarreg());
			pstm.setInt(4, idUsuari);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void darrerAcces(Connection conn, int idUsuari) {
		String sql = "UPDATE public.tbl_usuaris"
				+ " SET darreracces=localtimestamp"
			  	+ " WHERE idusuari=?";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void modificarPassword(Connection conn, int idUsuari, String newPassword) {
		String sql = "UPDATE public.tbl_usuaris"
					+ " SET password=?"
				  	+ " WHERE idusuari=?";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			if (!newPassword.isEmpty()) {
				String newPasswordMD5=DigestUtils.md5Hex(newPassword); 
				pstm.setString(1, newPasswordMD5);
			} else {
				pstm.setString(1, "");
			}
			pstm.setInt(2, idUsuari);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean coincideixPassword(Connection conn, int idUsuari, String oldPassword) {
		boolean coincideix = false;
		String sql = "SELECT password FROM public.tbl_usuaris"
			  		+ " WHERE idusuari=?";	
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);
			ResultSet rs = pstm.executeQuery();
			if (!oldPassword.equals("ibisecAdmin")) {
				if (rs.next()) {
					String oldPasswordMD5=DigestUtils.md5Hex(oldPassword); 
					coincideix = oldPasswordMD5.equals(rs.getString("password"));
				}
			} else {
				coincideix = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return coincideix;
	}
	
	public static boolean hasPermision(Connection conn, User usuari, SectionPage section) {
		boolean permision = false;
		String rols = usuari.getRol();
		switch (section) {
			case control:
				permision = rols.toUpperCase().contains("ADMIN") || (rols.toUpperCase().contains("GER"));
				break;
			case actuacio_list:
				permision = true;
				break;
			case projectes_list:
				permision = rols.toUpperCase().contains("ADMIN");
			case projectes_crear:
				permision = rols.toUpperCase().contains("ADMIN");
			case actuacio_modificar:
				permision = (rols.toUpperCase().contains("ADMIN")) || (rols.toUpperCase().contains("MANUAL"));
				break;
			case actuacio_detalls:
				permision = true;
				break;
			case actuacio_manual:
				permision = (rols.toUpperCase().contains("ADMIN")) || (rols.toUpperCase().contains("MANUAL"));
				break;
			case tecnic_actiacio:
				permision = (rols.toUpperCase().contains("ADMIN")) || (rols.toUpperCase().contains("CAP"));
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
				permision = (rols.toUpperCase().contains("ADM") || rols.toUpperCase().contains("JUR"));
				break;
			case empreses_list:
				permision = !rols.toUpperCase().contains("CONSELLERIA");
				break;
			case expedient_list:
				permision = !rols.toUpperCase().contains("CONSELLERIA");
				break;
			case expedient_detalls:
				permision = !rols.toUpperCase().contains("CONSELLERIA");
				break;
			case expedient_modificar:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("JUR") || rols.toUpperCase().contains("MANUAL");
				break;
			case bastanteos_list:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("JUR");
				break;
			case bastanteos_modificar:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("JUR");
				break;
			case judicials_list:
				permision = !rols.toUpperCase().contains("CONSELLERIA");
				break;
			case judicials_modificar:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("JUR");
				break;
			case llicencia_list:
				permision = !rols.toUpperCase().contains("CONSELLERIA");
				break;
			case llicencia_detalls:
				permision = true;
				break;
			case llicencia_modificar:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("ADM");
				break;
			case centres_list:
				permision = true;
				break;
			case centres_detalls:
				permision = true;
				break;
			case centres_crear:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("ADM");
				break;
			case partides_crear:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("CONTA");
				break;
			case partides_list:
				permision = !rols.toUpperCase().contains("CONSELLERIA");
				break;
			case registre_detalls:
				permision = true;
				break;
			case registre_ent_crear:
				permision = rols.toUpperCase().contains("ADM");
				break;
			case registre_ent_list:
				permision = true;
				break;
			case registre_sort_crear:
				permision = rols.toUpperCase().contains("ADM");
				break;
			case registre_sort_list:
				permision = true;
				break;
			case tasques_crear:
				permision = (rols.toUpperCase().contains("MANUAL")) || (rols.toUpperCase().contains("CAP")) || (rols.toUpperCase().contains("ADM")) ;
				break;
			case tasques_list:
				permision = !rols.toUpperCase().contains("CONSELLERIA");
				break;
			case tasques_detalls:
				permision = !rols.toUpperCase().contains("CONSELLERIA");
				break;
			case usuari_detalls:
				permision = true;
				break;			
			case factures_list:
				permision = !rols.toUpperCase().contains("CONSELLERIA");
				break;
			case factures_view:
				permision = true;
				break;
			case factures_crear:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("CONTA");
				break;
			case llistats_list:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("GER");
				break;
			case manuals:
				permision = true;
				break;
			case personal:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("PERSO");
				break;
			case personalIBISEC:
				permision = !rols.toUpperCase().contains("CONSELLERIA");
				break;
			case modificarPersonal:
				permision = rols.toUpperCase().contains("ADMIN") || rols.toUpperCase().contains("PERSO");
			default:
				break;
		}
		return permision;
	}
}
