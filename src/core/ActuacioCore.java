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
	
	static final String SQL_CAMPS = "id, idincidencia, descripcio, idcentre, usucre, datacre, aprovada, dataaprovacio, usuaprovacio, activa, datatancament, usutancament, datamodificacio, darreramodificacio";
	
	private static Actuacio initActuacio(Connection conn, ResultSet rs, Estadistiques estad) throws SQLException{
		Actuacio actuacio = new Actuacio();
		
		actuacio.setReferencia(rs.getInt("id"));
		actuacio.setDescripcio(rs.getString("descripcio"));		
		actuacio.setDataCreacio(rs.getTimestamp("datacre"));
		actuacio.setIdUsuariCreacio(rs.getInt("usucre"));		
		actuacio.setIdCentre(rs.getString("idcentre"));
		actuacio.setNomCentre(CentreCore.nomCentreComplet(conn, rs.getString("idcentre")));		
		actuacio.setActiva(rs.getBoolean("activa"));		
		actuacio.setDataTancament(rs.getTimestamp("datatancament"));		
		actuacio.setAprovada(rs.getBoolean("aprovada"));
		actuacio.setDataAprovacio(rs.getTimestamp("dataaprovacio"));		
		actuacio.setIdInformePrevi(InformeCore.getInformePrevi(conn, rs.getInt("id")));
		actuacio.setIdIncidencia(rs.getInt("idincidencia"));		
		actuacio.setDarreraModificacio(rs.getTimestamp("datamodificacio"));
		actuacio.setModificacio(rs.getString("darreramodificacio"));
		if (estad != null) {
			if (actuacio.isActiva()) {
				if (actuacio.isAprovada()) {
					estad.setAprovades(estad.getAprovades() + 1);
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
					+ "VALUES (?,?,?,?,?,localtimestamp,false,null,null,true,null,null,localtimestamp,?)";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setInt(1, actuacio.getReferencia());
		pstm.setInt(2, actuacio.getIdIncidencia());
		pstm.setString(3, actuacio.getDescripcio());
		pstm.setString(4, actuacio.getIdCentre());
		pstm.setInt(5, actuacio.getIdUsuariCreacio());
		pstm.setString(6, "Creació");		
		
		pstm.executeUpdate();
	}
	
	public static Actuacio findActuacio(Connection conn, int referencia) throws SQLException {
		 
		String sql = "SELECT " + SQL_CAMPS
					+ " FROM public.tbl_actuacio"
					+ " WHERE id::INT = ?";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, referencia);		
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
					+ " ORDER BY id::INT DESC"
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
			sql += " WHERE datacre >= ? and datacre <= ? ";
			if (idCentre != "") {
				sql += " AND idcentre = ?";
				if (estat != "-1") {					
					if ("Aprovada".equals(estat)) sql+= " AND activa = true AND aprovada = true"; 
					if ("Pendent".equals(estat)) sql+= " AND activa = true AND aprovada = false"; 
					if ("Tancada".equals(estat)) sql+= " AND activa = false"; 			
				}
				sql += " ORDER BY id::INT DESC";
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
					if ("Aprovada".equals(estat)) sql+= " AND activa = true AND aprovada = true"; 
					if ("Pendent".equals(estat)) sql+= " AND activa = true AND aprovada = false"; 
					if ("Tancada".equals(estat)) sql+= " AND activa = false"; 
				}
				sql += " ORDER BY id::INT DESC";
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
					if ("Aprovada".equals(estat)) sql+= " AND activa = true AND aprovada = true"; 
					if ("Pendent".equals(estat)) sql+= " AND activa = true AND aprovada = false"; 
					if ("Tancada".equals(estat)) sql+= " AND activa = false"; 
				}
				sql += " ORDER BY id::INT DESC";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, idCentre);			
			} else {
				if (estat != "-1") {					
					if ("Aprovada".equals(estat)) sql+= " WHERE activa = true AND aprovada = true"; 
					if ("Pendent".equals(estat)) sql+= " WHERE activa = true AND aprovada = false"; 
					if ("Tancada".equals(estat)) sql+= " WHERE activa = false"; 					
				}
				sql += " ORDER BY id::INT DESC";
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
	
	public static Resultat searchActuacionsInciencia(Connection conn, int idIncidencia) throws SQLException {
		List<Actuacio> list = new ArrayList<Actuacio>();
		String sql = "SELECT " + SQL_CAMPS
				 	+ " FROM public.tbl_actuacio"
				 	+ " WHERE idincidencia = ?";
				
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idIncidencia);	
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
		String sql = "SELECT id"
					+ " FROM public.tbl_actuacio"
					+ " ORDER BY id DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			int num =  rs.getInt("id");
			String formatted = String.valueOf(num + 1);
			newCode = formatted;
		}
		return newCode;
	}

	public static void actualitzarActuacio(Connection conn, int idActuacio, String modificacio) throws SQLException{
		String sql = "UPDATE public.tbl_actuacio"
				+ " SET darreramodificacio=?, datamodificacio=localtimestamp"
				+ " WHERE id=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, modificacio);
		pstm.setInt(2, idActuacio);
		pstm.executeUpdate();
	}
	
	public static void aprovar(Connection conn, int referencia, int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_actuacio"
					+ " SET aprovada=true, dataaprovacio=localtimestamp, usuaprovacio=?, darreramodificacio='aprovar actuació', datamodificacio=localtimestamp"
					+ " WHERE id=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);
		pstm.setInt(2, referencia);
		pstm.executeUpdate();
	}
	
	public static void tancar(Connection conn, int referencia,  int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_actuacio"
					+ " SET activa=false, datatancament=localtimestamp, usutancament=?, darreramodificacio='tancar actuació', datamodificacio=localtimestamp"
					+ " WHERE id=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	
		pstm.setInt(1, idUsuari);
		pstm.setInt(2, referencia);
		pstm.executeUpdate();
	}
}
