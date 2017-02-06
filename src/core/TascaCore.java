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
import bean.User;

public class TascaCore {
	
	private static Tasca initTasca(Connection conn, ResultSet rs) throws SQLException {
		Tasca tasca = new Tasca();
		tasca.setIdTasca(rs.getInt("idTasca"));
		tasca.setUsuari(UsuariCore.findUsuariByID(conn, rs.getInt("idUsuari")));
		tasca.setActuacio(ActuacioCore.findActuacio(conn,  rs.getInt("idActuacio")));
		tasca.setIncidencia(IncidenciaCore.findIncidencia(conn, rs.getInt("idIncidencia")));
		tasca.setActiva(rs.getBoolean("activa"));
		tasca.setName(rs.getString("nom"));
		tasca.setTipus(rs.getString("tipus"));
		tasca.setDataCreacio(TascaCore.findInici(conn, rs.getInt("idTasca")));
		return tasca;		
	}
	
	public static Tasca findTascaId(Connection conn, int idTasca) throws SQLException {
		String sql = "SELECT \"idTasca\", \"idUsuari\", \"idActuacio\", nom, tipus, activa, \"idIncidencia\" FROM public.\"tbl_Tasques\" WHERE \"idTasca\" = ?";	 
		 PreparedStatement pstm = conn.prepareStatement(sql);	 
		 pstm.setInt(1, idTasca);		
		 ResultSet rs = pstm.executeQuery();
		 Tasca tasca = new Tasca();
		 while (rs.next()) {			
			 tasca = initTasca(conn, rs);
		 }
	     return tasca;
	}
	
	public static List<Tasca> llistaTasquesUsuari(Connection conn, User usuari) throws SQLException {
		 String sql = "SELECT \"idTasca\", \"idUsuari\", \"idActuacio\", nom, tipus, activa, \"idIncidencia\" FROM public.\"tbl_Tasques\" WHERE \"idUsuari\" = ? AND activa=true";	 
		 PreparedStatement pstm = conn.prepareStatement(sql);	 
		 pstm.setInt(1, usuari.getIdUsuari());		
		 ResultSet rs = pstm.executeQuery();
		 List<Tasca> list = new ArrayList<Tasca>();
		 while (rs.next()) {
			 Tasca tasca = initTasca(conn, rs);
			 list.add(tasca);
		 }
	     return list;
    }
	
	public static List<Tasca> findTasquesActuacio(Connection conn, int referencia) throws SQLException {
		 String sql = "SELECT \"idTasca\", \"idUsuari\", \"idActuacio\", nom, tipus, activa, \"idIncidencia\" FROM public.\"tbl_Tasques\" WHERE \"idActuacio\" = ?";	 
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
		 String sql = "SELECT \"idTasca\", \"idUsuari\", \"idActuacio\", nom, tipus, activa, \"idIncidencia\" FROM public.\"tbl_Tasques\" WHERE \"idIncidencia\" = ?";	 
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
		String sql = "SELECT data FROM public.tbl_historic WHERE \"idTasca\" = ? ORDER BY data";	 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idTasca);		
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			dataInici = rs.getTimestamp("data");
		}
		return dataInici;
	}
	
	public static List<Historic> findHistorial(Connection conn, int idTasca, int idActuacio) throws SQLException {
		String sql = "SELECT \"idHistoric\", comentari, \"idUsuari\", data FROM public.tbl_historic WHERE \"idTasca\" = ?";	 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idTasca);		
		ResultSet rs = pstm.executeQuery();
		List<Historic> list = new ArrayList<Historic>();
		while (rs.next()) {
			Historic historic = new Historic();
			historic.setComentari(rs.getString("comentari"));
			historic.setData(rs.getTimestamp("data"));
			historic.setIdHistoric(rs.getInt("idHistoric"));
			historic.setIdTasca(idTasca);
			historic.setUsuari(UsuariCore.findUsuariByID(conn, rs.getInt("idUsuari")));
			historic.setAdjunts(utils.Fitxers.ObtenirFitxers(idActuacio, "Tasca", String.valueOf(idTasca), rs.getString("idHistoric")));
			list.add(historic);
		}
	    return list;
	}
	
	public static void novaTasca(Connection conn, String tipus, int idUsuari, int idUsuariComentari, int idActuacio, int idIncidencia, String comentari, String assumpte) throws SQLException {
		
		String sql = "INSERT INTO public.\"tbl_Tasques\"(\"idTasca\", \"idUsuari\", \"idActuacio\", nom, tipus, activa, \"idIncidencia\") VALUES (?, ?, ?, ?, ?, ?, ?);";		 
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
		String sql = "INSERT INTO public.tbl_historic(\"idHistoric\", \"idTasca\", comentari, \"idUsuari\", data) VALUES (?, ?, ?, ?,localtimestamp);";		 
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
		String sql = "UPDATE public.\"tbl_Tasques\"	SET \"idUsuari\"=? WHERE \"idTasca\"=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idUsuari);
		pstm.setInt(2, idTasca);
		pstm.executeUpdate();
	}
	
	public static void tancar(Connection conn, int idTasca) throws SQLException {
		String sql = "UPDATE public.\"tbl_Tasques\"	SET activa=false WHERE \"idTasca\"=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idTasca);
		pstm.executeUpdate();
	}
	
	public static int idNovaTasca(Connection conn) throws SQLException{
		int id = 1;
		String sql = "SELECT \"idTasca\" FROM public.\"tbl_Tasques\" ORDER BY \"idTasca\" DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			String actualCode = rs.getString("idTasca");
			int num = Integer.valueOf(actualCode);
			id = num + 1;
		}
		return id;
	}
	public static int idNouHistoric(Connection conn) throws SQLException{
		int id = 1;
		String sql = "SELECT \"idHistoric\" FROM public.tbl_historic ORDER BY \"idHistoric\" DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			String actualCode = rs.getString("idHistoric");
			int num = Integer.valueOf(actualCode);
			id = num + 1;
		}
		return id;
	}
}
