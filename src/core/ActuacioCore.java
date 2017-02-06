package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Actuacio;
public class ActuacioCore {
	
	static final String SQL_CAMPS = "\"Referencia\", descripcio, \"dataCreacio\", \"idCentre\", activa, \"dataTancament\", \"usuTancament\", aprovada, \"dataAprovacio\", \"usuAprovacio\", \"idIncidencia\", \"dataModificacio\", modificacio, \"usuCreacio\"";
	
	private static Actuacio initActuacio(Connection conn, ResultSet rs) throws SQLException{
		Actuacio actuacio = new Actuacio();
		
		actuacio.setReferencia(rs.getInt("Referencia"));
		actuacio.setDescripcio(rs.getString("descripcio"));		
		actuacio.setDataCreacio(rs.getDate("dataCreacio"));
		actuacio.setIdUsuariCreacio(rs.getInt("usuCreacio"));		
		actuacio.setIdCentre(rs.getString("idCentre"));
		actuacio.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idCentre")));		
		actuacio.setActiva(rs.getBoolean("activa"));		
		actuacio.setDataTancament(rs.getTimestamp("dataTancament"));		
		actuacio.setAprovada(rs.getBoolean("aprovada"));
		actuacio.setDataAprovacio(rs.getTimestamp("dataAprovacio"));		
		actuacio.setIdInformePrevi(InformeCore.getInformePrevi(conn, rs.getInt("Referencia")));
		actuacio.setIdIncidencia(rs.getInt("idIncidencia"));		
		actuacio.setDarreraModificacio(rs.getTimestamp("dataModificacio"));
		actuacio.setModificacio(rs.getString("modificacio"));
		
		return actuacio;
	}
	
	public static void novaActuacio(Connection conn, Actuacio actuacio) throws SQLException {
		String sql = "Insert into public.\"tbl_actuacio\"(" + SQL_CAMPS + ") values (?,?,localtimestamp,?,true,null,null,false,null,null,?,localtimestamp,?,?)";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setInt(1, actuacio.getReferencia());		
		pstm.setString(2, actuacio.getDescripcio());
		pstm.setString(3, actuacio.getIdCentre());
		pstm.setInt(4, actuacio.getIdIncidencia());
		pstm.setString(5, "Creació");
		pstm.setInt(6, actuacio.getIdUsuariCreacio());
		
		pstm.executeUpdate();
	}
	
	public static Actuacio findActuacio(Connection conn, int referencia) throws SQLException {
		 
		String sql = "Select " + SQL_CAMPS
					+ " from public.\"tbl_actuacio\" "
					+ " where \"Referencia\" = ?";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, referencia);		
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			Actuacio actuacio = initActuacio(conn, rs); 
			return actuacio;
		}
		return null;
	}
	
	public static List<Actuacio> topAcuacions(Connection conn) throws SQLException {
		String sql = "Select " + SQL_CAMPS
					+ " from public.\"tbl_actuacio\" "
					+ " ORDER BY \"Referencia\" desc"
					+ " LIMIT 500";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		List<Actuacio> list = new ArrayList<Actuacio>();
		while (rs.next()) {
			Actuacio actuacio = initActuacio(conn, rs);
			list.add(actuacio);
		}
		return list;
	}
	
	public static List<Actuacio> searchActuacions(Connection conn, String idCentre, boolean onlyActives) throws SQLException {
		String sql = "Select " + SQL_CAMPS
					+ " from public.\"tbl_actuacio\" ";
					
		PreparedStatement pstm;
		if (idCentre != "") {
			sql += " WHERE \"idCentre\" = ? ";
			if (onlyActives) { sql+= "and activa = true"; }
			sql += " ORDER BY \"Referencia\"::INT DESC";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idCentre);			
		} else {
			if (onlyActives) { sql+= " WHERE activa = true"; }
			sql += " ORDER BY \"Referencia\"::INT DESC";
			pstm = conn.prepareStatement(sql);
		}	
		ResultSet rs = pstm.executeQuery();
		List<Actuacio> list = new ArrayList<Actuacio>();
		while (rs.next()) {
			Actuacio actuacio = initActuacio(conn, rs);			
			list.add(actuacio);
		}
		return list;
	}
	
	public static List<Actuacio> searchActuacionsInciencia(Connection conn, int idIncidencia) throws SQLException {
		List<Actuacio> list = new ArrayList<Actuacio>();
		String sql = "SELECT " + SQL_CAMPS
				 	+ " FROM public.\"tbl_actuacio\""
				 	+ " WHERE \"idIncidencia\" = ?";
				
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idIncidencia);	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			Actuacio actuacio = initActuacio(conn, rs);			
			list.add(actuacio);
		}
		return list;
	}
	
	public static String getNewCode(Connection conn) throws SQLException {
		String newCode = "1";
		String sql = "SELECT \"Referencia\" FROM public.\"tbl_actuacio\" ORDER BY \"Referencia\" DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			String actualCode = rs.getString("Referencia");
			int num = Integer.valueOf(actualCode);
			String formatted = String.valueOf(num + 1);
			newCode = formatted;
		}
		return newCode;
	}

	public static void aprovar(Connection conn, int referencia, int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_actuacio SET aprovada=true, \"dataAprovacio\"=localtimestamp, \"usuAprovacio\"=? WHERE \"Referencia\"=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);
		pstm.setInt(2, referencia);
		pstm.executeUpdate();
	}
	
	public static void tancar(Connection conn, int referencia,  int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_actuacio SET activa=false, \"dataTancament\"=localtimestamp, \"usuTancament\"=? WHERE \"Referencia\"=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	
		pstm.setInt(1, idUsuari);
		pstm.setInt(2, referencia);
		pstm.executeUpdate();
	}
}
