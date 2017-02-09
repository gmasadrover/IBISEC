package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.PropostaActuacio;
import bean.User;

public class InformeCore {
	
	private static PropostaActuacio initInforme(Connection conn, ResultSet rs) throws SQLException {
		PropostaActuacio informe = new PropostaActuacio();
		informe.setIdInf(rs.getInt("idinf"));
		informe.setIdTasca(rs.getInt("idtasca"));
		informe.setIdIncidencia(rs.getInt("idincidencia"));
		informe.setIdActuacio(rs.getInt("idactuacio"));
		informe.setObjecte(rs.getString("objecte"));
		informe.setTipusObra(rs.getString("tipusobra"));
		informe.setLlicencia(rs.getBoolean("llicencia"));
		informe.setTipusLlicencia(rs.getString("tipusllicencia"));
		informe.setContracte(rs.getBoolean("contracte"));
		informe.setVec(rs.getDouble("vec"));
		informe.setIva(rs.getDouble("iva"));
		informe.setPlic(rs.getDouble("plic"));
		informe.setTermini(rs.getString("termini"));
		informe.setServei(rs.getString("servei"));
		informe.setComentari(rs.getString("comentari"));	
		User usuari = UsuariCore.findUsuariByID(conn, rs.getInt("usucre"));
		informe.setUsuari(usuari);
		informe.setDataCreacio(rs.getTimestamp("datacre"));
		informe.setAdjunts(utils.Fitxers.ObtenirFitxers(rs.getInt("idactuacio"), "Tasca", rs.getInt("idtasca"),""));
		informe.setPartida(CreditCore.getPartidaActuacio(conn, rs.getInt("idactuacio")));	
		informe.setUsuariAprovacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuaprovacio")));
		informe.setDataAprovacio(rs.getTimestamp("dataaprovacio"));
		return informe;
	}
	
	public static int nouInforme(Connection conn, PropostaActuacio informe, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.tbl_propostaactuacio(idinf, idtasca, idincidencia, idactuacio, objecte, tipusobra, llicencia, tipusllicencia, contracte, vec, iva, plic, termini, servei, comentari, usucre, datacre)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, localtimestamp);";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	
		int idNouInforme = idNouInforme(conn);
		pstm.setInt(1, idNouInforme);
		pstm.setInt(2, informe.getIdTasca());
		pstm.setInt(3, informe.getIdIncidencia());
		pstm.setInt(4, informe.getIdActuacio());
		pstm.setString(5, informe.getObjecte());
		pstm.setString(6, informe.getTipusObra());
		pstm.setBoolean(7, informe.isLlicencia());
		pstm.setString(8, informe.getTipusLlicencia());
		pstm.setBoolean(9, informe.isContracte());
		pstm.setDouble(10, informe.getVec());
		pstm.setDouble(11, informe.getIva());
		pstm.setDouble(12, informe.getPlic());
		pstm.setString(13, informe.getTermini());
		pstm.setString(14, informe.getServei());
		pstm.setString(15, informe.getComentari());
		pstm.setInt(16, idUsuari);
		pstm.executeUpdate();
		
		return idNouInforme;
	}
	
	public static int getInformePrevi(Connection conn, int idActuacio) throws SQLException {
		int informePrevi = -1;
		String sql = "SELECT idinf"
					+ " FROM public.tbl_propostaactuacio"
					+ " WHERE idactuacio = ?"
					+ " ORDER BY idinf DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idActuacio);	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			informePrevi = rs.getInt("idinf");
		}
		return informePrevi;
	}
	
	public static PropostaActuacio getInformeTasca(Connection conn, int idTasca) throws SQLException {
		PropostaActuacio informe = new PropostaActuacio();
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, objecte, tipusobra, llicencia, tipusllicencia, contracte, vec, iva, plic, termini, servei, comentari, usucre, datacre, usuaprovacio, dataaprovacio" 
					+ " FROM public.tbl_propostaactuacio"
					+ " WHERE idtasca = ?"
					+ " ORDER BY idinf DESC LIMIT 1;";		 
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idTasca);	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			informe = initInforme(conn, rs);			
		}
		return informe;
	}
	
	public static List<PropostaActuacio> getInformesActuacio(Connection conn, int idActuacio) throws SQLException {
		 List<PropostaActuacio> informes = new ArrayList<PropostaActuacio>();
		 String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, objecte, tipusobra, llicencia, tipusllicencia, contracte, vec, iva, plic, termini, servei, comentari, usucre, datacre, usuaprovacio, dataaprovacio" 
					+ " FROM public.tbl_propostaactuacio"
					+ " WHERE idactuacio = ?"
					+ " ORDER BY idinf DESC;";		 
		
		 PreparedStatement pstm = conn.prepareStatement(sql);
		 pstm.setInt(1, idActuacio);	
		 ResultSet rs = pstm.executeQuery();
		 while (rs.next()) {
			 informes.add(initInforme(conn, rs));			
		 }
		 return informes;
	}
	
	public static void aprovarInforme(Connection conn, int idInf, int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_propostaactuacio"
					+ " SET usuaprovacio=?, dataaprovacio=localtimestamp"
					+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);	
		pstm.setInt(2, idInf);
		pstm.executeUpdate();
	}
	
	private static int idNouInforme(Connection conn) throws SQLException{
		int id = 1;
		String sql = "SELECT idinf"
					+ " FROM public.tbl_propostaactuacio"
					+ " ORDER BY idinf DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			String actualCode = rs.getString("idinf");
			int num = Integer.valueOf(actualCode);
			id = num + 1;
		}
		return id;
	}
}
