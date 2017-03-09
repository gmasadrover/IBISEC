package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Centre;

public class CentreCore {
	
	private static Centre initCentre(Connection conn, ResultSet rs, Boolean complet) throws SQLException {
		Centre centre = new Centre();
		centre.setIdCentre(rs.getString("codi"));
		centre.setNom(rs.getString("nom"));
		centre.setTipo(rs.getString("tipo"));
		centre.setIlla(rs.getString("illa"));
		centre.setMunicipi(rs.getString("municipi"));
		centre.setLocalitat(rs.getString("localitat"));
		centre.setAdreca(rs.getString("adreca"));
		centre.setCp(rs.getString("cp"));
		if (complet) {
			centre.setActuacions(ActuacioCore.searchActuacions(conn, rs.getString("codi"), false,null,null));
			centre.setIncidencies(IncidenciaCore.searchIncidencies(conn, rs.getString("codi"), false, null, null));
		}
		return centre;
	}
	
	public static String nomCentre(Connection conn, String idCentre) throws SQLException {		 
		String sql = "SELECT nom from public.tbl_centres"
					+ " WHERE codi = ?";
		String nomCentre = "";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idCentre);		
		ResultSet rs = pstm.executeQuery();	 
		if (rs.next()) {
			nomCentre = rs.getString("nom");
		}
		return nomCentre;
	}
	
	public static List<Centre> findCentres(Connection conn, boolean ambIncidencies) throws SQLException {
		List<Centre> centres = new ArrayList<Centre>();
		String sql = "SELECT codi, tipo, nom, illa, municipi, localitat, adreca, cp"
					+ " FROM public.tbl_centres;";
		PreparedStatement pstm = conn.prepareStatement(sql);				
		ResultSet rs = pstm.executeQuery();	 
		while (rs.next()) {
			Centre centre = initCentre(conn, rs, false);
			centres.add(centre);
		}
		return centres;
	}
	
	public static Centre findCentre(Connection conn, String codi) throws SQLException {		
		String sql = "SELECT codi, tipo, nom, illa, municipi, localitat, adreca, cp"
					+ " FROM public.tbl_centres"
					+ " WHERE codi = ?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	
		pstm.setString(1, codi);	
		ResultSet rs = pstm.executeQuery();	 
		Centre centre = new Centre();
		if (rs.next()) {
			centre = initCentre(conn, rs, true);			
		}
		return centre;
	}
}
