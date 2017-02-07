package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Informe;
import bean.User;

public class InformeCore {
	
	public static int nouInforme(Connection conn, Informe informe, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.tbl_informe(\"idInf\", \"idTasca\", \"idActuacio\", objecte, \"tipusObra\", llicencia, \"tipusLlicencia\", contracte, vec, iva, plic, termini, servei, comentari, \"usuCre\", \"usuMod\")" +
					" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, localtimestamp);";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	
		int idNouInforme = idNouInforme(conn);
		pstm.setInt(1, idNouInforme);
		pstm.setInt(2, informe.getIdTasca());
		pstm.setInt(3, informe.getIdActuacio());
		pstm.setString(4, informe.getObjecte());
		pstm.setString(5, informe.getTipusObra());
		pstm.setBoolean(6, informe.isLlicencia());
		pstm.setString(7, informe.getTipusLlicencia());
		pstm.setBoolean(8, informe.isContracte());
		pstm.setDouble(9, informe.getVec());
		pstm.setDouble(10, informe.getIva());
		pstm.setDouble(11, informe.getPlic());
		pstm.setString(12, informe.getTermini());
		pstm.setString(13, informe.getServei());
		pstm.setString(14, informe.getComentari());
		pstm.setInt(15, idUsuari);
		pstm.executeUpdate();
		
		return idNouInforme;
	}
	
	public static int getInformePrevi(Connection conn, int idActuacio) throws SQLException {
		int informePrevi = -1;
		String sql = "SELECT \"idInf\" FROM public.tbl_informe WHERE \"idActuacio\" = ? ORDER BY \"idInf\" DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idActuacio);	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			informePrevi = rs.getInt("idInf");
		}
		return informePrevi;
	}
	
	public static Informe getInformeTasca(Connection conn, int idTasca) throws SQLException {
		Informe informe = new Informe();
		String sql = "SELECT \"idInf\", \"idTasca\", \"idActuacio\", objecte, \"tipusObra\", llicencia, \"tipusLlicencia\", contracte, vec, iva, plic, termini, servei, comentari, \"usuCre\", \"usuMod\"" + 
					" FROM public.tbl_informe WHERE \"idTasca\" = ? ORDER BY \"idInf\" DESC LIMIT 1;";		 
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idTasca);	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			informe = initInforme(conn, rs);			
		}
		return informe;
	}
	
	public static List<Informe> getInformesActuacio(Connection conn, int idActuacio) throws SQLException {
		 List<Informe> informes = new ArrayList<Informe>();
		 String sql = "SELECT \"idInf\", \"idTasca\", \"idActuacio\", objecte, \"tipusObra\", llicencia, \"tipusLlicencia\", contracte, vec, iva, plic, termini, servei, comentari, \"usuCre\", \"usuMod\"" + 
					" FROM public.tbl_informe WHERE \"idActuacio\" = ? ORDER BY \"idInf\" DESC;";		 
		
		 PreparedStatement pstm = conn.prepareStatement(sql);
		 pstm.setInt(1, idActuacio);	
		 ResultSet rs = pstm.executeQuery();
		 while (rs.next()) {
			 informes.add(initInforme(conn, rs));			
		 }
		 return informes;
	}
	
	private static Informe initInforme(Connection conn, ResultSet rs) throws SQLException {
		Informe informe = new Informe();
		informe.setIdInf(rs.getInt("idInf"));
		informe.setIdTasca(rs.getInt("idTasca"));
		informe.setIdActuacio(rs.getInt("idActuacio"));
		informe.setObjecte(rs.getString("objecte"));
		informe.setTipusObra(rs.getString("tipusObra"));
		informe.setLlicencia(rs.getBoolean("llicencia"));
		informe.setTipusLlicencia(rs.getString("tipusLlicencia"));
		informe.setContracte(rs.getBoolean("contracte"));
		informe.setVec(rs.getDouble("vec"));
		informe.setIva(rs.getDouble("iva"));
		informe.setPlic(rs.getDouble("plic"));
		informe.setTermini(rs.getString("termini"));
		informe.setServei(rs.getString("servei"));
		informe.setComentari(rs.getString("comentari"));	
		User usuari = UsuariCore.findUsuariByID(conn, rs.getInt("usuCre"));
		informe.setUsuari(usuari);
		informe.setDataCreacio(rs.getTimestamp("usuMod"));
		informe.setAdjunts(utils.Fitxers.ObtenirFitxers(rs.getInt("idActuacio"), "Tasca", rs.getInt("idTasca"),""));
		informe.setPartida(CreditCore.getPartidaActuacio(conn, rs.getInt("idActuacio")));		
		return informe;
	}
	
	
	private static int idNouInforme(Connection conn) throws SQLException{
		int id = 1;
		String sql = "SELECT \"idInf\" FROM public.tbl_informe ORDER BY \"idInf\" DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			String actualCode = rs.getString("idInf");
			int num = Integer.valueOf(actualCode);
			id = num + 1;
		}
		return id;
	}
}
