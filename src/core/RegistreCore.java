package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Registre;

public class RegistreCore {
	
	public static void nouRegistre(Connection conn, String tipus, Registre registre) throws SQLException {
		String sql = "INSERT INTO public.\"tbl_RegEntrada\" (id, data, tipus, remitent, contingut, \"idCentre\", \"idIncidencia\", valid, \"usuCre\", \"usuMod\") "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, localtimestamp);";
		if (tipus == "S") {
			sql = "INSERT INTO public.\"tbl_RegSortida\" (id, data, tipus, destinatari, contingut, \"idCentre\", \"idIncidencia\", valid, \"usuCre\", \"usuMod\") "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, localtimestamp);";
		}	
		
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setString(1, registre.getId());		
		pstm.setDate(2, new java.sql.Date(registre.getData().getTime()));
		pstm.setString(3, registre.getTipus());
		pstm.setString(4, registre.getRemDes());
		pstm.setString(5, registre.getContingut());
		pstm.setString(6,  registre.getIdCentre());
		pstm.setInt(7, registre.getIdIncidencia());
		pstm.setBoolean(8, registre.isValid());
		pstm.setInt(9, registre.getIdUsuari());			
				
		pstm.executeUpdate();
	}
	
	public static Registre findRegistre(Connection conn, String tipus, String idRegistre) throws SQLException {
		Registre registre = new Registre();
		String sql = "SELECT id, data, tipus, remitent, contingut, \"idCentre\", \"idIncidencia\", valid, \"usuCre\", \"usuMod\""
					+ "FROM public.\"tbl_RegEntrada\""
					+ "WHERE id=?";
		if (tipus == "S") {
			sql = "SELECT id, data, tipus, destinatari, contingut, \"idCentre\", \"idIncidencia\", valid, \"usuCre\", \"usuMod\""
					+ "FROM public.\"tbl_RegSortida\""
					+ "WHERE id=?";
		}
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idRegistre);	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {			
			registre.setId(rs.getString("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("remitent"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idIncidencia"));
			registre.setIdCentre(rs.getString("idCentre"));
			registre.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idCentre")));
			registre.setValid(rs.getBoolean("valid"));			
		}
		return registre;
	}
	
	public static List<Registre> entrades(Connection conn) throws SQLException {
		String sql = "SELECT id, data, remitent, contingut, \"idCentre\", \"idIncidencia\", valid FROM public.\"tbl_RegEntrada\" ORDER BY id::INT DESC LIMIT 500";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		List<Registre> list = new ArrayList<Registre>();
		while (rs.next()) {
			Registre registre = new Registre();
			registre.setId(rs.getString("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("remitent"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idIncidencia"));
			registre.setIdCentre(rs.getString("idCentre"));
			registre.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idCentre")));
			registre.setValid(rs.getBoolean("valid"));
			list.add(registre);
		}
		return list;
	}
	
	public static List<Registre> searchEntrades(Connection conn, String idCentre, Date dataIni, Date dataFi) throws SQLException {
		String sql = "SELECT id, data, remitent, contingut, \"idCentre\", \"idIncidencia\", valid FROM public.\"tbl_RegEntrada\" ";
		sql += "WHERE data >= ? and data <= ? ";
		if (idCentre != "") {
			sql += "and \"idCentre\" = ?";
		}
		sql += "ORDER BY id::INT DESC LIMIT 500";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
		pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
		if (idCentre != "") pstm.setString(3, idCentre);
		ResultSet rs = pstm.executeQuery();
		List<Registre> list = new ArrayList<Registre>();
		while (rs.next()) {
			Registre registre = new Registre();
			registre.setId(rs.getString("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("remitent"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idIncidencia"));
			registre.setIdCentre(rs.getString("idCentre"));
			registre.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idCentre")));
			registre.setValid(rs.getBoolean("valid"));
			list.add(registre);
		}
		return list;
	}
	
	public static List<Registre> searchEntradesIncidencia(Connection conn, int referencia) throws SQLException {
		String sql = "SELECT id, data, remitent, contingut, \"idCentre\", \"idIncidencia\", valid FROM public.\"tbl_RegEntrada\" ";
		sql += "WHERE \"idIncidencia\" = ? ";		
		sql += "ORDER BY id::INT DESC";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, referencia);
		ResultSet rs = pstm.executeQuery();
		List<Registre> list = new ArrayList<Registre>();
		while (rs.next()) {
			Registre registre = new Registre();
			registre.setId(rs.getString("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("remitent"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idIncidencia"));
			registre.setIdCentre(rs.getString("idCentre"));
			registre.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idCentre")));
			registre.setValid(rs.getBoolean("valid"));
			list.add(registre);
		}
		return list;
	}
	
	public static void actualitzarIdIncidencia(Connection conn, String tipus, String idRegistre, int idActuacio) throws SQLException{
		String sql = "UPDATE public.\"tbl_RegEntrada\" SET \"idIncidencia\"=? WHERE id=?";
		if (tipus == "S") {
			sql = "UPDATE public.\"tbl_RegSortida\" SET \"idIncidencia\"=? WHERE id=?";
		}		
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idActuacio);
		pstm.setString(2, idRegistre);				
		pstm.executeUpdate();
	}
	
	public static List<Registre> sortides(Connection conn) throws SQLException {
		String sql = "SELECT id, data, destinatari, contingut, \"idIncidencia\", valid FROM public.\"tbl_RegSortida\" ORDER BY id::INT DESC LIMIT 500";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		List<Registre> list = new ArrayList<Registre>();
		while (rs.next()) {
			Registre registre = new Registre();
			registre.setId(rs.getString("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("destinatari"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idIncidencia"));
			registre.setValid(rs.getBoolean("valid"));
			list.add(registre);
		}
		return list;
	}
	
	public static List<Registre> searchSortides(Connection conn, String idCentre, Date dataIni, Date dataFi) throws SQLException {
		String sql = "SELECT id, data, destinatari, contingut, \"idCentre\", \"idIncidencia\", valid FROM public.\"tbl_RegSortida\" ";
		sql += "WHERE data >= ? and data <= ? ";
		if (idCentre != "") {
			sql += "and \"idCentre\" = ?";
		}
		sql += "ORDER BY id::INT DESC LIMIT 500";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
		pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
		if (idCentre != "") pstm.setString(3, idCentre);
		ResultSet rs = pstm.executeQuery();
		List<Registre> list = new ArrayList<Registre>();
		while (rs.next()) {
			Registre registre = new Registre();
			registre.setId(rs.getString("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("destinatari"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idIncidencia"));
			registre.setIdCentre(rs.getString("idCentre"));
			registre.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idCentre")));
			registre.setValid(rs.getBoolean("valid"));
			list.add(registre);
		}
		return list;
	}
	
	public static List<Registre> searchSortidesIncidencia(Connection conn, int referencia) throws SQLException {
		String sql = "SELECT id, data, destinatari, contingut, \"idCentre\", \"idIncidencia\", valid FROM public.\"tbl_RegSortida\" ";
		sql += "WHERE \"idIncidencia\" = ? ";		
		sql += "ORDER BY id::INT DESC";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, referencia);
		ResultSet rs = pstm.executeQuery();
		List<Registre> list = new ArrayList<Registre>();
		while (rs.next()) {
			Registre registre = new Registre();
			registre.setId(rs.getString("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("destinatari"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idIncidencia"));
			registre.setIdCentre(rs.getString("idCentre"));
			registre.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idCentre")));
			registre.setValid(rs.getBoolean("valid"));
			list.add(registre);
		}
		return list;
	}
	
	public static String getNewCode(Connection conn, String tipus) throws SQLException {
		String code = "1";
		String sql = "SELECT id FROM public.\"tbl_RegEntrada\" ORDER BY id::INT DESC LIMIT 1;";		
		if (tipus == "S") {
			sql = "SELECT id FROM public.\"tbl_RegSortida\" ORDER BY id::INT DESC LIMIT 1;";	
		}
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			String actualCode = rs.getString("id");
			int num = Integer.valueOf(actualCode);
			String formatted = String.valueOf(num + 1);
			code = formatted;
		}
		return code;
	}
	
}
