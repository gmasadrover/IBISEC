package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Incidencia;

public class IncidenciaCore {
	
	private static Incidencia initIncidencia(Connection conn, ResultSet rs) throws SQLException{
		Incidencia incidencia = new Incidencia();
		incidencia.setIdIncidencia(rs.getInt("idincidencia"));
		incidencia.setDescripcio(rs.getString("descripcio"));
		incidencia.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
		incidencia.setUsuMod(rs.getDate("datacre"));
		incidencia.setActiva(rs.getBoolean("activa"));
		incidencia.setDataTancament(rs.getDate("datatancament"));
		incidencia.setIdCentre(rs.getString("idcentre"));
		incidencia.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idcentre")));
		incidencia.setSolicitant(rs.getString("solicitant"));
		incidencia.setLlistaActuacions(ActuacioCore.searchActuacionsInciencia(conn, rs.getInt("idincidencia")));
		return incidencia;
	}
	
	public static void novaIncidencia(Connection conn, Incidencia incidencia) throws SQLException {
		String sql = "INSERT INTO public.tbl_incidencia(idincidencia, descripcio, usucre, datacre, activa, idcentre, solicitant)"
					+ " VALUES (?,?,?,localtimestamp,true,?,?)";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setInt(1, incidencia.getIdIncidencia());		
		pstm.setString(2, incidencia.getDescripcio());
		pstm.setInt(3, incidencia.getUsuCre().getIdUsuari());
		pstm.setString(4, incidencia.getIdCentre());
		pstm.setString(5, incidencia.getSolicitant());
	 
		pstm.executeUpdate();
	}
	
	public static Incidencia findIncidencia(Connection conn, int idIncidencia) throws SQLException{
		Incidencia incidencia = new Incidencia();
		String sql = "SELECT idincidencia, descripcio, usucre, datacre, activa, datatancament, idcentre, solicitant" 
					+ " FROM public.tbl_incidencia"
					+ " WHERE idincidencia = ?";
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idIncidencia);			
		
		ResultSet rs = pstm.executeQuery();
		
		if (rs.next()) {
			incidencia = initIncidencia(conn, rs);
		}
		return incidencia;
	}
	
	public static List<Incidencia> searchIncidencies(Connection conn, String idCentre, boolean onlyActives) throws SQLException {
		String sql = "SELECT idincidencia, descripcio, usucre, datacre, activa, datatancament, idcentre, solicitant" 
					+ " FROM public.tbl_incidencia";
					
		PreparedStatement pstm;
		if (idCentre != "") {
			sql += " WHERE idcentre = ?";
			if (onlyActives) { sql+= " and activa = true"; }
			sql += " ORDER BY idincidencia::INT DESC";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idCentre);			
		} else {
			if (onlyActives) { sql+= " WHERE activa = true"; }
			sql += " ORDER BY idincidencia::INT DESC";
			pstm = conn.prepareStatement(sql);
		}	
		ResultSet rs = pstm.executeQuery();
		List<Incidencia> list = new ArrayList<Incidencia>();
		while (rs.next()) {
			Incidencia incidencia = initIncidencia(conn, rs);			
			list.add(incidencia);
		}
		return list;
	}
	
	public static int getNewCode(Connection conn) throws SQLException {
		int newCode = 1;
		String sql = "SELECT idincidencia"
					+ " FROM public.tbl_incidencia"
					+ " ORDER BY idincidencia DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			int actualCode = rs.getInt("idincidencia");			
			newCode = actualCode + 1;
		}
		return newCode;
	}
}
