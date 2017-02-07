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
		String sql = "INSERT INTO public.tbl_regentrada (id, data, tipus, remitent, contingut, idcentre, idincidencia, usucre, datacre)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, localtimestamp);";
		if (tipus == "S") {
			sql = "INSERT INTO public.tbl_regsortida (id, data, tipus, destinatari, contingut, idcentre, idincidencia, usucre, datacre)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, localtimestamp);";
		}	
		
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setInt(1, registre.getId());		
		pstm.setDate(2, new java.sql.Date(registre.getData().getTime()));
		pstm.setString(3, registre.getTipus());
		pstm.setString(4, registre.getRemDes());
		pstm.setString(5, registre.getContingut());
		pstm.setString(6,  registre.getIdCentre());
		pstm.setInt(7, registre.getIdIncidencia());
		pstm.setInt(8, registre.getIdUsuari());			
				
		pstm.executeUpdate();
	}
	
	public static Registre findRegistre(Connection conn, String tipus, int idRegistre) throws SQLException {
		Registre registre = new Registre();
		String sql = "SELECT id, data, tipus, remitent, contingut, idcentre, idincidencia, usucre, datacre"
					+ " FROM public.tbl_regentrada"
					+ " WHERE id=?";
		if ("S".equals(tipus)) {
			sql = "SELECT id, data, tipus, destinatari, contingut, idcentre, idincidencia, usucre, datacre"
					+ " FROM public.tbl_regsortida"
					+ " WHERE id=?";
		}
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idRegistre);	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {			
			registre.setId(rs.getInt("id"));
			registre.setData(rs.getDate("data"));
			if ("S".equals(tipus)) {
				registre.setRemDes(rs.getString("destinatari"));
			}else{
				registre.setRemDes(rs.getString("remitent"));
			}			
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idincidencia"));
			registre.setIdCentre(rs.getString("idcentre"));
			registre.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idcentre")));	
		}
		return registre;
	}
	
	public static List<Registre> entrades(Connection conn) throws SQLException {
		String sql = "SELECT id, data, remitent, contingut, idcentre, idincidencia"
					+ " FROM public.tbl_regentrada"
					+ " ORDER BY id DESC LIMIT 500";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		List<Registre> list = new ArrayList<Registre>();
		while (rs.next()) {
			Registre registre = new Registre();
			registre.setId(rs.getInt("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("remitent"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idincidencia"));
			registre.setIdCentre(rs.getString("idcentre"));
			registre.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idcentre")));
			list.add(registre);
		}
		return list;
	}
	
	public static List<Registre> searchEntrades(Connection conn, String idCentre, Date dataIni, Date dataFi) throws SQLException {
		String sql = "SELECT id, data, remitent, contingut, idcentre, idincidencia"
					+ " FROM public.tbl_regentrada"
					+ " WHERE data >= ? and data <= ? ";
		if (idCentre != "") {
			sql += "and idcentre = ?";
		}
		sql += "ORDER BY id DESC LIMIT 500";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
		pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
		if (idCentre != "") pstm.setString(3, idCentre);
		ResultSet rs = pstm.executeQuery();
		List<Registre> list = new ArrayList<Registre>();
		while (rs.next()) {
			Registre registre = new Registre();
			registre.setId(rs.getInt("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("remitent"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idincidencia"));
			registre.setIdCentre(rs.getString("idcentre"));
			registre.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idcentre")));
			list.add(registre);
		}
		return list;
	}
	
	public static List<Registre> searchEntradesIncidencia(Connection conn, int referencia) throws SQLException {
		String sql = "SELECT id, data, remitent, contingut, idcentre, idincidencia"
					+ " FROM public.tbl_regentrada"
					+ " WHERE idincidencia = ? "		
					+ " ORDER BY id DESC";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, referencia);
		ResultSet rs = pstm.executeQuery();
		List<Registre> list = new ArrayList<Registre>();
		while (rs.next()) {
			Registre registre = new Registre();
			registre.setId(rs.getInt("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("remitent"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idincidencia"));
			registre.setIdCentre(rs.getString("idcentre"));
			registre.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idcentre")));
			list.add(registre);
		}
		return list;
	}
	
	public static void actualitzarIdIncidencia(Connection conn, String tipus, int idRegistre, int idActuacio) throws SQLException{
		String sql = "UPDATE public.tbl_regentrada"
					+ " SET idincidencia=?"
					+ " WHERE id=?";
		if (tipus == "S") {
			sql = "UPDATE public.tbl_regsortida"
					+ " SET idincidencia=?"
					+ " WHERE id=?";
		}		
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idActuacio);
		pstm.setInt(2, idRegistre);				
		pstm.executeUpdate();
	}
	
	public static List<Registre> sortides(Connection conn) throws SQLException {
		String sql = "SELECT id, data, destinatari, contingut, idincidencia"
					+ " FROM public.tbl_regsortida"
					+ " ORDER BY id DESC LIMIT 500";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		List<Registre> list = new ArrayList<Registre>();
		while (rs.next()) {
			Registre registre = new Registre();
			registre.setId(rs.getInt("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("destinatari"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idincidencia"));
			list.add(registre);
		}
		return list;
	}
	
	public static List<Registre> searchSortides(Connection conn, String idCentre, Date dataIni, Date dataFi) throws SQLException {
		String sql = "SELECT id, data, destinatari, contingut, idcentre, idincidencia"
					+ " FROM public.tbl_regsortida"
					+ " WHERE data >= ? and data <= ? ";
		if (idCentre != "") {
			sql += "and idcentre = ?";
		}
		sql += "ORDER BY id DESC LIMIT 500";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
		pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
		if (idCentre != "") pstm.setString(3, idCentre);
		ResultSet rs = pstm.executeQuery();
		List<Registre> list = new ArrayList<Registre>();
		while (rs.next()) {
			Registre registre = new Registre();
			registre.setId(rs.getInt("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("destinatari"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idincidencia"));
			registre.setIdCentre(rs.getString("idcentre"));
			registre.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idcentre")));
			list.add(registre);
		}
		return list;
	}
	
	public static List<Registre> searchSortidesIncidencia(Connection conn, int referencia) throws SQLException {
		String sql = "SELECT id, data, destinatari, contingut, idcentre, idincidencia"
					+ " FROM public.tbl_regsortida"
					+ " WHERE idincidencia = ? "		
					+ " ORDER BY id DESC";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, referencia);
		ResultSet rs = pstm.executeQuery();
		List<Registre> list = new ArrayList<Registre>();
		while (rs.next()) {
			Registre registre = new Registre();
			registre.setId(rs.getInt("id"));
			registre.setData(rs.getDate("data"));
			registre.setRemDes(rs.getString("destinatari"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencia(rs.getInt("idincidencia"));
			registre.setIdCentre(rs.getString("idcentre"));
			registre.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idcentre")));
			list.add(registre);
		}
		return list;
	}
	
	public static String getNewCode(Connection conn, String tipus) throws SQLException {
		String code = "1";
		String sql = "SELECT id"
					+ " FROM public.tbl_regentrada"
					+ " ORDER BY id DESC LIMIT 1;";		
		if (tipus == "S") {
			sql = "SELECT id"
					+ " FROM public.tbl_regsortida"
					+ " ORDER BY id DESC LIMIT 1;";	
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
