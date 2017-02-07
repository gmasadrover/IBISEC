package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Historic;
import bean.Informe;
import bean.Tasca;

public class TascaCore {
	
	private static Tasca initTasca(Connection conn, ResultSet rs) throws SQLException {
		Tasca tasca = new Tasca();
		tasca.setIdTasca(rs.getInt("idtasca"));
		tasca.setUsuari(UsuariCore.findUsuariByID(conn, rs.getInt("idusuari")));
		tasca.setActuacio(ActuacioCore.findActuacio(conn,  rs.getInt("idactuacio")));
		tasca.setIncidencia(IncidenciaCore.findIncidencia(conn, rs.getInt("idincidencia")));
		tasca.setActiva(rs.getBoolean("activa"));
		tasca.setDescripcio(rs.getString("descripcio"));
		tasca.setTipus(rs.getString("tipus"));
		tasca.setDataCreacio(TascaCore.findInici(conn, rs.getInt("idtasca")));
		return tasca;		
	}
	
	public static Tasca findTascaId(Connection conn, int idTasca) throws SQLException {
		String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia"
					+ " FROM public.tbl_tasques"
					+ " WHERE idtasca = ?";	 
		 PreparedStatement pstm = conn.prepareStatement(sql);	 
		 pstm.setInt(1, idTasca);		
		 ResultSet rs = pstm.executeQuery();
		 Tasca tasca = new Tasca();
		 while (rs.next()) {			
			 tasca = initTasca(conn, rs);
		 }
	     return tasca;
	}
	
	public static List<Tasca> llistaTasquesUsuari(Connection conn, int idUsuari) throws SQLException {
		 String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia"
				 	+ " FROM public.tbl_tasques"
				 	+ " WHERE idusuari = ?";	 
		 PreparedStatement pstm = conn.prepareStatement(sql);	 
		 pstm.setInt(1, idUsuari);		
		 ResultSet rs = pstm.executeQuery();
		 List<Tasca> list = new ArrayList<Tasca>();
		 while (rs.next()) {
			 Tasca tasca = initTasca(conn, rs);
			 list.add(tasca);
		 }
	     return list;
    }
	
	public static List<Tasca> llistaTasquesArea(Connection conn, String area) throws SQLException {
		String sql = "SELECT t.idtasca, t.idusuari, t.idactuacio, t.descripcio, t.tipus, t.activa, t.idincidencia"
				 	+ " FROM public.tbl_tasques t"
				 	+ " LEFT JOIN public.tbl_usuaris u on t.idusuari = u.idusuari"
				 	+ " WHERE u.departament = ?";
		 PreparedStatement pstm = conn.prepareStatement(sql);	 
		 pstm.setString(1, area);		
		 ResultSet rs = pstm.executeQuery();
		 List<Tasca> list = new ArrayList<Tasca>();
		 while (rs.next()) {
			 Tasca tasca = initTasca(conn, rs);
			 list.add(tasca);
		 }
	     return list;
   }
	
	public static List<Tasca> findTasquesActuacio(Connection conn, int referencia) throws SQLException {
		 String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia"
				 	+ " FROM public.tbl_tasques"
				 	+ " WHERE idactuacio = ?";	 
		 PreparedStatement pstm = conn.prepareStatement(sql);	 
		 pstm.setInt(1, referencia);		
		 ResultSet rs = pstm.executeQuery();
		 List<Tasca> list = new ArrayList<Tasca>();
		 while (rs.next()) {
			 Tasca tasca = initTasca(conn, rs);
			 list.add(tasca);
		 }
	     return list;
	}
	
	public static List<Tasca> findTasquesIncidencia(Connection conn, int referencia) throws SQLException {
		 String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia"
				 	+ " FROM public.tbl_tasques"
				 	+ " WHERE idincidencia = ?";	 
		 PreparedStatement pstm = conn.prepareStatement(sql);	 
		 pstm.setInt(1, referencia);		
		 ResultSet rs = pstm.executeQuery();
		 List<Tasca> list = new ArrayList<Tasca>();
		 while (rs.next()) {
			 Tasca tasca = initTasca(conn, rs);
			 list.add(tasca);
		 }
	     return list;
	}
	
	public static Date findInici(Connection conn, int idTasca) throws SQLException{
		Date dataInici = new Date();
		String sql = "SELECT data"
					+ " FROM public.tbl_historial"
					+ " WHERE idtasca = ?"
					+ " ORDER BY data";	 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idTasca);		
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			dataInici = rs.getTimestamp("data");
		}
		return dataInici;
	}
	
	public static List<Historic> findHistorial(Connection conn, int idTasca, int idActuacio) throws SQLException {
		String sql = "SELECT idhistoric, comentari, idusuari, data"
					+ " FROM public.tbl_historial"
					+ " WHERE idtasca = ?";	 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idTasca);		
		ResultSet rs = pstm.executeQuery();
		List<Historic> list = new ArrayList<Historic>();
		while (rs.next()) {
			Historic historic = new Historic();
			historic.setComentari(rs.getString("comentari"));
			historic.setData(rs.getTimestamp("data"));
			historic.setIdHistoric(rs.getInt("idhistoric"));
			historic.setIdTasca(idTasca);
			historic.setUsuari(UsuariCore.findUsuariByID(conn, rs.getInt("idusuari")));
			historic.setAdjunts(utils.Fitxers.ObtenirFitxers(idActuacio, "Tasca", idTasca, rs.getString("idhistoric")));
			list.add(historic);
		}
	    return list;
	}
	
	public static void novaTasca(Connection conn, String tipus, int idUsuari, int idUsuariComentari, int idActuacio, int idIncidencia, String comentari, String assumpte) throws SQLException {
		
		String sql = "INSERT INTO public.tbl_tasques(idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia)"
					+" VALUES (?, ?, ?, ?, ?, ?, ?);";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		int idNovaTasca = idNovaTasca(conn);
		if ("infPrev".equals(tipus)) {
			assumpte = "Sol·licitud informe previ";
			comentari = "Sol·licitud informe previ";
		} else if("resPartida".equals(tipus)) {
			String tmp = assumpte;
			assumpte = "Sol·licitud reserva partida - " + tmp;
			comentari = "Sol·licitud reserva partida - " + tmp;
		} else if("liciMenor".equals(tipus)) {
			Informe informePrevi = InformeCore.getInformesActuacio(conn, idActuacio).get(0);
			assumpte = "Licitació contracte menor";
			comentari ="1. Recerca de pressupostos";
			if (informePrevi.isLlicencia()) comentari += "<br> 2. Sol·licitud de llicència o autorització urbanística corresponent";
		} else if("liciMajor".equals(tipus)) {
			assumpte = "Licitació contracte major";
			comentari = "Iniciar procés licitació contracte major";			
		}
		pstm.setInt(1, idNovaTasca);
		pstm.setInt(2, idUsuari);
		pstm.setInt(3, idActuacio);
		pstm.setString(4, assumpte);
		pstm.setString(5, tipus);
		pstm.setBoolean(6, true);
		pstm.setInt(7, idIncidencia);
		pstm.executeUpdate();
		
		//Registrar comentari 1
		nouHistoric(conn, idNovaTasca, comentari, idUsuariComentari);
	}
	
	public static String nouHistoric(Connection conn, int idTasca, String comentari, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.tbl_historial(idhistoric, idtasca, comentari, idusuari, data)"
					+ " VALUES (?, ?, ?, ?,localtimestamp);";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	
		int idNouHistoric = idNouHistoric(conn);
		pstm.setInt(1, idNouHistoric);
		pstm.setInt(2, idTasca);
		pstm.setString(3, comentari);
		pstm.setInt(4, idUsuari);
		pstm.executeUpdate();
		return String.valueOf(idNouHistoric);
	}
	
	public static void reasignar(Connection conn, int idUsuari, int idTasca) throws SQLException{
		String sql = "UPDATE public.tbl_tasques"
					+ " SET idusuari=?"
					+ " WHERE idtasca=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idUsuari);
		pstm.setInt(2, idTasca);
		pstm.executeUpdate();
	}
	
	public static void tancar(Connection conn, int idTasca) throws SQLException {
		String sql = "UPDATE public.tbl_tasques"
					+ " SET activa=false"
					+ " WHERE idtasca=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idTasca);
		pstm.executeUpdate();
	}
	
	public static int idNovaTasca(Connection conn) throws SQLException{
		int id = 1;
		String sql = "SELECT idtasca"
					+ " FROM public.tbl_tasques"
					+ " ORDER BY idtasca"
					+ " DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			String actualCode = rs.getString("idtasca");
			int num = Integer.valueOf(actualCode);
			id = num + 1;
		}
		return id;
	}
	public static int idNouHistoric(Connection conn) throws SQLException{
		int id = 1;
		String sql = "SELECT idhistoric"
					+ " FROM public.tbl_historial"
					+ " ORDER BY idhistoric"
					+ " DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			String actualCode = rs.getString("idhistoric");
			int num = Integer.valueOf(actualCode);
			id = num + 1;
		}
		return id;
	}
}
