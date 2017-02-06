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
		incidencia.setIdIncidencia(rs.getInt("idIncidencia"));
		incidencia.setNom(rs.getString("nom"));
		incidencia.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usuCre")));
		incidencia.setUsuMod(rs.getDate("usuMod"));
		incidencia.setActiva(rs.getBoolean("activa"));
		incidencia.setDataCreacio(rs.getDate("dataCreacio"));
		incidencia.setDataTancament(rs.getDate("dataTancament"));
		incidencia.setIdCentre(rs.getString("idCentre"));
		incidencia.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idCentre")));
		incidencia.setLlistaActuacions(ActuacioCore.searchActuacionsInciencia(conn, rs.getInt("idIncidencia")));
		return incidencia;
	}
	
	public static void novaIncidencia(Connection conn, Incidencia incidencia) throws SQLException {
		String sql = "Insert into public.\"tbl_Incidencia\"(\"idIncidencia\", nom, \"usuCre\", \"usuMod\", activa, \"dataCreacio\", \"idCentre\") values (?,?,?,localtimestamp,true,?,?)";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setInt(1, incidencia.getIdIncidencia());		
		pstm.setString(2, incidencia.getNom());
		pstm.setInt(3, incidencia.getUsuCre().getIdUsuari());
		pstm.setDate(4, new java.sql.Date(incidencia.getDataCreacio().getTime()));		
		pstm.setString(5, incidencia.getIdCentre());
	 
		pstm.executeUpdate();
	}
	
	public static Incidencia findIncidencia(Connection conn, int idIncidencia) throws SQLException{
		Incidencia incidencia = new Incidencia();
		String sql = "SELECT \"idIncidencia\", nom, \"usuCre\", \"usuMod\", activa, \"dataCreacio\", \"dataTancament\", \"idCentre\" " 
				+ " FROM public.\"tbl_Incidencia\" "
		 		+ " WHERE \"idIncidencia\" = ? ";
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
		String sql = "SELECT \"idIncidencia\", nom, \"usuCre\", \"usuMod\", activa, \"dataCreacio\", \"dataTancament\", \"idCentre\" " 
					+ " FROM public.\"tbl_Incidencia\" ";
					
		PreparedStatement pstm;
		if (idCentre != "") {
			sql += " WHERE \"idCentre\" = ? ";
			if (onlyActives) { sql+= "and activa = true"; }
			sql += " ORDER BY \"idIncidencia\"::INT DESC";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idCentre);			
		} else {
			if (onlyActives) { sql+= " WHERE activa = true"; }
			sql += " ORDER BY \"idIncidencia\"::INT DESC";
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
		String sql = "SELECT \"idIncidencia\" FROM public.\"tbl_Incidencia\" ORDER BY \"idIncidencia\" DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			int actualCode = rs.getInt("idIncidencia");			
			newCode = actualCode + 1;
		}
		return newCode;
	}
}
