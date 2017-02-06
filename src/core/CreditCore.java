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
		String sql = "Insert into public.\"tbl_Credit\"(codi, presupost) values (?,?)";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setString(1, credit.getCodi());		
		pstm.setDouble(2, credit.getPresupost());
	 
		pstm.executeUpdate();
	}
	
	public static List<Credit> llistaCredits(Connection conn) throws SQLException {
		String sql = "SELECT codi, presupost FROM public.\"tbl_Credit\"";
		 
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
		 
		String sql = "Select c.presupost from public.\"tbl_Credit\" c "
					+ " where c.codi = ?";
	 
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
		
		String sql = "SELECT codi FROM public.\"tbl_Partides\" ORDER BY codi DESC LIMIT 1;";		 
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
		String sql = "INSERT INTO public.\"tbl_Partides\"(codi, nom, import, tipus, estat, \"usuCre\", \"usuMod\") VALUES (?, ?, ?, ?, true,?,localtimestamp);";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setString(1, partida.getCodi());
		pstm.setString(2, partida.getNom());
		pstm.setDouble(3, Double.valueOf(partida.getTotalPartida()));
		pstm.setString(4, partida.getTipus());
		pstm.setInt(5, idUsuari);
		pstm.executeUpdate();
	}
	
	public static List<Partida> getPartides(Connection conn, boolean tancades) throws SQLException {
		String sql = "Select * from public.\"tbl_Partides\"";
		if (!tancades) sql += " where estat=true";
		sql += " ORDER BY 1 desc"; 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		List<Partida> list = new ArrayList<Partida>();
		while (rs.next()) {
			Partida partida = new Partida();
			partida.setCodi(rs.getString("codi"));			
			partida.setEstat(rs.getBoolean("estat"));
			partida.setNom(rs.getString("nom"));
			partida.setTipus(rs.getString("tipus"));
			partida.setTotalPartida(rs.getDouble("import"));
			partida.setGastadaPartida(getTotalGastat(conn, rs.getString("codi")));
			partida.setReservaPartida(getTotalReservat(conn, rs.getString("codi")));
			list.add(partida);
		}
		return list;
	}
	
	public static String getPartidaActuacio(Connection conn, int idActuacio) throws SQLException {
		String idPartida = "";
		String sql = "Select \"idPartida\" from public.\"tbl_AssignacionsCredit\" where \"idActuacio\" = ?"		
					+ " ORDER BY 1 desc LIMIT 1"; 
		PreparedStatement pstm = conn.prepareStatement(sql);		 
		pstm.setInt(1, idActuacio);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			idPartida = rs.getString("idPartida");
		}
		return idPartida;
	}
	
	public static int newAssignacioCode(Connection conn) throws SQLException{
		int newCode = 1;
		
		String sql = "SELECT \"idAssignacio\" FROM public.\"tbl_AssignacionsCredit\" ORDER BY \"idAssignacio\" DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			newCode = rs.getInt("idAssignacio") + 1;
		}
		return newCode;
	}
	
	public static void reservar(Connection conn, String idPartida, int idActuacio, int idTasca, double valor, String comentari, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.\"tbl_AssignacionsCredit\"(\"idAssignacio\", \"idPartida\", \"idActuacio\", \"idTasca\", valor, reserva, assignacio, pagat, comentari, \"usuCre\", \"usuMod\")"
					+ " VALUES (?, ?, ?, ?, ?, true, false, false, ?, ?, localtimestamp);";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		 
		pstm.setInt(1, newAssignacioCode(conn));
		pstm.setString(2, idPartida);
		pstm.setInt(3, idActuacio);
		pstm.setInt(4, idTasca);
		pstm.setDouble(5, valor);
		pstm.setString(6, comentari);
		pstm.setInt(7, idUsuari);
		pstm.executeUpdate();
		
	}
	
	public static double getTotalReservat(Connection conn, String idPartida) throws SQLException{
		double totalReservat = 0;
		String sql = "SELECT SUM(valor) as total FROM public.\"tbl_AssignacionsCredit\" WHERE reserva=true and \"idPartida\" = ?";
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
		String sql = "SELECT SUM(valor) as total FROM public.\"tbl_AssignacionsCredit\" WHERE assignacio=true and \"idPartida\" = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);		 
		pstm.setString(1, idPartida);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			totalGastat = rs.getDouble("total");
		}
		return totalGastat;
	}
}
