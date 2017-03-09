package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Credit;
import bean.Partida;

public class CreditCore {
	public static void nouCredit(Connection conn, Credit credit) throws SQLException {
		String sql = "INSERT INTO public.tbl_credit(codi, presupost)"
					+ " VALUES (?,?)";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setString(1, credit.getCodi());		
		pstm.setDouble(2, credit.getPresupost());
	 
		pstm.executeUpdate();
	}
	
	public static List<Credit> llistaCredits(Connection conn) throws SQLException {
		String sql = "SELECT codi, presupost"
					+ " FROM public.tbl_credit";
		 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		List<Credit> list = new ArrayList<Credit>();
		while (rs.next()) {
			Credit credit = new Credit();
			credit.setCodi(rs.getString("codi"));
			credit.setPresupost(rs.getDouble("presupost"));
			list.add(credit);
		}
		return list;
	}
	
	public static Credit findCredit(Connection conn, String codi) throws SQLException {
		 
		String sql = "SELECT presupost"
					+ " FROM public.tbl_credit"
					+ " WHERE codi = ?";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, codi);		
		ResultSet rs = pstm.executeQuery();
	 
		if (rs.next()) {
			Credit credit = new Credit();
			credit.setCodi(codi);
			credit.setPresupost(rs.getDouble("presupost"));			
			return credit;
		}
		return null;
	}
	
	public static String getNewCode(Connection conn) throws SQLException {
		String newCode = "R.IBIS-001";
		
		String sql = "SELECT codi"
					+ " FROM public.tbl_partides"
					+ " ORDER BY codi DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			String actualCode = rs.getString("codi");
			int num = Integer.valueOf(actualCode.split("-")[1]);
			String formatted = String.format("%03d", num + 1);
			newCode = newCode.split("-")[0] + "-" + formatted;
		}
		return newCode;
	}
	
	public static void novaPartida(Connection conn, Partida partida, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.tbl_partides(codi, nom, import, tipus, activa, usucre, datacre)"
					+ " VALUES (?, ?, ?, ?, true,?,localtimestamp);";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setString(1, partida.getCodi());
		pstm.setString(2, partida.getNom());
		pstm.setDouble(3, Double.valueOf(partida.getTotalPartida()));
		pstm.setString(4, partida.getTipus());
		pstm.setInt(5, idUsuari);
		pstm.executeUpdate();
	}
	
	public static List<Partida> getPartides(Connection conn, boolean tancades) throws SQLException {
		String sql = "SELECT *"
					+ " FROM public.tbl_partides";
		if (!tancades) sql += " WHERE activa=true";
		sql += " ORDER BY 1 desc"; 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		List<Partida> list = new ArrayList<Partida>();
		while (rs.next()) {
			Partida partida = new Partida();
			partida.setCodi(rs.getString("codi"));			
			partida.setEstat(rs.getBoolean("activa"));
			partida.setNom(rs.getString("nom"));
			partida.setTipus(rs.getString("tipus"));
			partida.setTotalPartida(rs.getDouble("import"));
			partida.setGastadaPartida(getTotalGastat(conn, rs.getString("codi")));
			partida.setReservaPartida(getTotalReservat(conn, rs.getString("codi")));
			list.add(partida);
		}
		return list;
	}
	
	public static String getPartidaInforme(Connection conn, int idInforme) throws SQLException {
		String idPartida = "";
		String sql = "SELECT idpartida"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE idinf = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);		 
		pstm.setInt(1, idInforme);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			idPartida = rs.getString("idpartida");
		}
		return idPartida;
	}
	
	public static String getPartidaActuacio(Connection conn, int idActuacio) throws SQLException {
		String idPartida = "";
		String sql = "SELECT idpartida"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE idactuacio = ?"		
					+ " ORDER BY 1 desc LIMIT 1"; 
		PreparedStatement pstm = conn.prepareStatement(sql);		 
		pstm.setInt(1, idActuacio);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			idPartida = rs.getString("idpartida");
		}
		return idPartida;
	}
	
	public static int newAssignacioCode(Connection conn) throws SQLException{
		int newCode = 1;
		
		String sql = "SELECT idassignacio"
					+ " FROM public.tbl_assignacionscredit"
					+ " ORDER BY idassignacio DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			newCode = rs.getInt("idassignacio") + 1;
		}
		return newCode;
	}

	public static void reservar(Connection conn, String idPartida, int idActuacio, int idInforme, double valor, String comentari, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.tbl_assignacionscredit(idassignacio, idactuacio, idinf, reserva, usureserva, datareserva, assignacio, comentari, usucre, datacre, idpartida, valorpa)"
					+ " VALUES (?, ?, ?, true, ?, localtimestamp, false, ?, ?, localtimestamp, ?, ?);";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		 
		pstm.setInt(1, newAssignacioCode(conn));		
		pstm.setInt(2, idActuacio);
		pstm.setInt(3, idInforme);
		pstm.setInt(4, idUsuari);		
		pstm.setString(5, comentari);
		pstm.setInt(6, idUsuari);
		pstm.setString(7, idPartida);
		pstm.setDouble(8, valor);
		pstm.executeUpdate();
		
	}
	
	public static double getTotalReservat(Connection conn, String idPartida) throws SQLException{
		double totalReservat = 0;
		String sql = "SELECT SUM(valorpa) AS total"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE reserva=true and idpartida = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);		 
		pstm.setString(1, idPartida);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			totalReservat = rs.getDouble("total");
		}
		return totalReservat;
	}
	
	public static double getTotalGastat(Connection conn, String idPartida) throws SQLException{
		double totalGastat = 0;
		String sql = "SELECT SUM(valorpd) AS total"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE assignacio=true AND idpartida = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);		 
		pstm.setString(1, idPartida);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			totalGastat = rs.getDouble("total");
		}
		return totalGastat;
	}
}
