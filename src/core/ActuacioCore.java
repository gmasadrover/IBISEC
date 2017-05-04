package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.Actuacio;
import bean.Resultat;
import bean.Resultat.Estadistiques;
public class ActuacioCore {
	
	static final String SQL_CAMPS = "id, idincidencia, descripcio, idcentre, usucre, datacre, dataaprovacio, usuaprovacio, datatancament, usutancament, datamodificacio, darreramodificacio, usuaprovarpa, dataaprovarpa, notes";
	
	private static Actuacio initActuacio(Connection conn, ResultSet rs, Estadistiques estad) throws SQLException{
		Actuacio actuacio = new Actuacio();
		
		actuacio.setReferencia(rs.getString("id"));
		actuacio.setDescripcio(rs.getString("descripcio"));		
		actuacio.setDataCreacio(rs.getTimestamp("datacre"));
		actuacio.setIdUsuariCreacio(rs.getInt("usucre"));		
		actuacio.setIdCentre(rs.getString("idcentre"));
		actuacio.setNomCentre(CentreCore.nomCentreComplet(conn, rs.getString("idcentre")));			
		actuacio.setDataTancament(rs.getTimestamp("datatancament"));	
		actuacio.setDataAprovacio(rs.getTimestamp("dataaprovacio"));		
		actuacio.setInformePrevi(InformeCore.getInformePrevi(conn, rs.getString("id")));
		actuacio.setIdIncidencia(rs.getString("idincidencia"));		
		actuacio.setDarreraModificacio(rs.getTimestamp("datamodificacio"));
		actuacio.setModificacio(rs.getString("darreramodificacio"));
		actuacio.setDataAprovarPa(rs.getTimestamp("dataaprovarpa"));
		actuacio.setNotes(rs.getString("notes"));
		if (estad != null) {
			if (actuacio.getDataTancament() == null) {
				if (actuacio.getDataAprovacio() != null) {
					estad.setAprovadesPT(estad.getAprovadesPT() + 1);
				} else if (actuacio.getDataAprovarPa() != null) {
					estad.setAprovadesPA(estad.getAprovadesPA() + 1);
				} else {
					estad.setPendents(estad.getPendents() + 1);
				}			
			} else {
				estad.setTancades(estad.getTancades() + 1);
			}
		}
		return actuacio;
	}
	
	public static void novaActuacio(Connection conn, Actuacio actuacio) throws SQLException {
		String sql = "INSERT INTO public.tbl_actuacio(" + SQL_CAMPS + ")"
					+ "VALUES (?,?,?,?,?,localtimestamp,null,null,null,null,localtimestamp,?,null,null,'')";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setString(1, actuacio.getReferencia());
		pstm.setString(2, actuacio.getIdIncidencia());
		pstm.setString(3, actuacio.getDescripcio());
		pstm.setString(4, actuacio.getIdCentre());
		pstm.setInt(5, actuacio.getIdUsuariCreacio());
		pstm.setString(6, "Creació");		
		
		pstm.executeUpdate();
	}
	
	public static Actuacio findActuacio(Connection conn, String referencia) throws SQLException {
		 
		String sql = "SELECT " + SQL_CAMPS
					+ " FROM public.tbl_actuacio"
					+ " WHERE id = ?";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, referencia);		
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			Actuacio actuacio = initActuacio(conn, rs, null); 
			return actuacio;
		}
		return null;
	}
	
	public static Resultat topAcuacions(Connection conn) throws SQLException {
		String sql = "SELECT " + SQL_CAMPS
					+ " FROM public.tbl_actuacio"
					+ " ORDER BY datacre DESC, id DESC"
					+ " LIMIT 500";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		Resultat result = new Resultat();
		List<Actuacio> list = new ArrayList<Actuacio>();
		Estadistiques estad = result.new Estadistiques();
		while (rs.next()) {
			Actuacio actuacio = initActuacio(conn, rs, estad);
			list.add(actuacio);
		}
		result.setEstad(estad);
		result.setLlistaActuacions(list);
		return result;
	}
	
	public static Resultat searchActuacions(Connection conn, String idCentre, String estat, Date dataIni, Date dataFi) throws SQLException {
		String sql = "SELECT " + SQL_CAMPS
					+ " FROM public.tbl_actuacio";					
		PreparedStatement pstm;
		if (dataIni != null && dataFi != null) {
			sql += " WHERE datacre >= ? AND datacre <= ? ";
			if (idCentre != "") {
				sql += " AND idcentre = ?";
				if (estat != "-1") {					
					if ("AprovadaPT".equals(estat)) sql+= " AND datatancament IS null AND dataaprovacio IS NOT null"; 
					if ("AprovadaPA".equals(estat)) sql+= " AND datatancament IS null AND dataaprovarpa IS NOT null"; 
					if ("Pendent".equals(estat)) sql+= " AND datatancament IS null AND dataaprovacio IS null AND dataaprovarpa IS null"; 
					if ("Tancada".equals(estat)) sql+= " AND datatancament IS NOT null"; 			
				}
				sql += " ORDER BY datacre DESC, id DESC";
				pstm = conn.prepareStatement(sql);
				pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
				Calendar fi = Calendar.getInstance();
			    fi.setTime(dataFi);
			    fi.add(Calendar.DATE, 1);	
			    dataFi = fi.getTime();
				pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
				pstm.setString(3, idCentre);	
			} else {
				if (estat != "-1") {					
					if ("AprovadaPT".equals(estat)) sql+= " AND datatancament IS null AND dataaprovacio IS NOT null"; 
					if ("AprovadaPA".equals(estat)) sql+= " AND datatancament IS null AND dataaprovarpa IS NOT null"; 
					if ("Pendent".equals(estat)) sql+= " AND datatancament IS null AND dataaprovacio IS null AND dataaprovarpa IS null"; 
					if ("Tancada".equals(estat)) sql+= " AND datatancament IS NOT null"; 		
				}
				sql += " ORDER BY datacre DESC, id DESC";
				pstm = conn.prepareStatement(sql);
				pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
				Calendar fi = Calendar.getInstance();
			    fi.setTime(dataFi);
			    fi.add(Calendar.DATE, 1);	
			    dataFi = fi.getTime();
				pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
			}
		}else{
			if (idCentre != "") {
				sql += " WHERE idcentre = ?";
				if (estat != "-1") {					
					if ("AprovadaPT".equals(estat)) sql+= " AND datatancament IS null AND dataaprovacio IS NOT null"; 
					if ("AprovadaPA".equals(estat)) sql+= " AND datatancament IS null AND dataaprovarpa IS NOT null"; 
					if ("Pendent".equals(estat)) sql+= " AND datatancament IS null AND dataaprovacio IS null AND dataaprovarpa IS null"; 
					if ("Tancada".equals(estat)) sql+= " AND datatancament IS NOT null"; 	
				}
				sql += " ORDER BY datacre DESC, id DESC";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, idCentre);			
			} else {
				if (estat != "-1") {					
					if ("AprovadaPT".equals(estat)) sql+= " WHERE datatancament IS null AND dataaprovacio IS NOT null"; 
					if ("AprovadaPA".equals(estat)) sql+= " WHERE datatancament IS null AND dataaprovarpa IS NOT null"; 
					if ("Pendent".equals(estat)) sql+= " WHERE datatancament IS null AND dataaprovacio IS null AND dataaprovarpa IS null"; 
					if ("Tancada".equals(estat)) sql+= " WHERE datatancament IS NOT null"; 			
				}
				sql += " ORDER BY datacre DESC, id DESC";
				pstm = conn.prepareStatement(sql);
			}	
		}
		
		ResultSet rs = pstm.executeQuery();
		Resultat result = new Resultat();
		List<Actuacio> list = new ArrayList<Actuacio>();
		Estadistiques estad = result.new Estadistiques();
		while (rs.next()) {			
			Actuacio actuacio = initActuacio(conn, rs, estad);	
			list.add(actuacio);
		}
		result.setEstad(estad);
		result.setLlistaActuacions(list);
		return result;
	}
	
	public static Resultat searchActuacionsInciencia(Connection conn, String idIncidencia) throws SQLException {
		List<Actuacio> list = new ArrayList<Actuacio>();
		String sql = "SELECT " + SQL_CAMPS
				 	+ " FROM public.tbl_actuacio"
				 	+ " WHERE idincidencia = ?";
				
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, idIncidencia);	
		ResultSet rs = pstm.executeQuery();
		Resultat result = new Resultat();
		Estadistiques estad = result.new Estadistiques();
		while (rs.next()) {
			Actuacio actuacio = initActuacio(conn, rs, estad);			
			list.add(actuacio);
		}
		result.setEstad(estad);
		result.setLlistaActuacions(list);
		return result;
	}
	
	public static String getNewCode(Connection conn) throws SQLException {		
		String newCode = "1";
		
		String sql = "SELECT id, datacre"
					+ " FROM public.tbl_actuacio"
					+ " WHERE id like '%ACT%'"
					+ " ORDER BY datacre DESC, id DESC LIMIT 1;";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		String prefix = "ACT";
		if (rs.next()) { //Codis nous
			String actualCode = rs.getString("id");			
			int num = Integer.valueOf(actualCode.split("-")[2]);
			String numFormatted = String.format("%04d", num + 1);
			newCode = yearInString + "-" + prefix + "-" + numFormatted;
		}
		else {
			int num = 0;		
			String numFormatted = String.format("%04d", num + 1);
			newCode = yearInString + "-" + prefix + "-" + numFormatted;
		}
		return newCode;
	}

	public static void actualitzarActuacio(Connection conn, String idActuacio, String modificacio) throws SQLException{
		String sql = "UPDATE public.tbl_actuacio"
				+ " SET darreramodificacio=?, datamodificacio=localtimestamp"
				+ " WHERE id=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, modificacio);
		pstm.setString(2, idActuacio);
		pstm.executeUpdate();
	}
	
	public static void aprovarPA(Connection conn, String referencia, int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_actuacio"
					+ " SET dataaprovarpa=localtimestamp, usuaprovarpa=?, darreramodificacio='aprovar proposta', datamodificacio=localtimestamp"
					+ " WHERE id=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);
		pstm.setString(2, referencia);
		pstm.executeUpdate();
	}
	
	public static void aprovar(Connection conn, String referencia, int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_actuacio"
					+ " SET dataaprovacio=localtimestamp, usuaprovacio=?, darreramodificacio='aprovar actuació', datamodificacio=localtimestamp"
					+ " WHERE id=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);
		pstm.setString(2, referencia);
		pstm.executeUpdate();
	}
	
	public static void tancar(Connection conn, String referencia,  int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_actuacio"
					+ " SET datatancament=localtimestamp, usutancament=?, darreramodificacio='tancar actuació', datamodificacio=localtimestamp"
					+ " WHERE id=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	
		pstm.setInt(1, idUsuari);
		pstm.setString(2, referencia);
		pstm.executeUpdate();
	}
}
