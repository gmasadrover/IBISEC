package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.AssignacioCredit;
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
	
	public static Partida getPartida(Connection conn, String codi) throws SQLException {
		 
		String sql = "SELECT codi, nom, import, tipus, activa, usucre, datacre"
					+ " FROM public.tbl_partides"
					+ " WHERE codi = ?";
	 	
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, codi);		
		ResultSet rs = pstm.executeQuery();
	 
		if (rs.next()) {
			Partida partida = new Partida();
			partida.setCodi(rs.getString("codi"));			
			partida.setEstat(rs.getBoolean("activa"));
			partida.setNom(rs.getString("nom"));
			partida.setTipus(rs.getString("tipus"));
			partida.setTotalPartida(rs.getDouble("import"));
			partida.setPrevistPartida(getTotalPrevist(conn, rs.getString("codi")));
			partida.setReservaPartida(getTotalReservat(conn, rs.getString("codi")));
			partida.setPagatPartida(getTotalPagat(conn, rs.getString("codi")));
			return partida;
		}
		return null;
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
			partida.setPrevistPartida(getTotalPrevist(conn, rs.getString("codi")));
			partida.setReservaPartida(getTotalReservat(conn, rs.getString("codi")));
			partida.setPagatPartida(getTotalPagat(conn, rs.getString("codi")));
			list.add(partida);
		}
		return list;
	}
	
	public static String getPartidaInforme(Connection conn, String idInforme) throws SQLException {
		String idPartida = "";
		String sql = "SELECT p.nom AS nom"
					+ " FROM public.tbl_assignacionscredit a LEFT JOIN public.tbl_partides p ON a.idpartida=p.codi"
					+ " WHERE idinf = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);		 
		pstm.setString(1, idInforme);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			idPartida = rs.getString("nom");
		}
		return idPartida;
	}
	
	public static String getComentariPartida(Connection conn, String idInforme) throws SQLException {
		String comentari = "";
		String sql = "SELECT comentari"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE idinf = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);		 
		pstm.setString(1, idInforme);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) comentari = rs.getString("comentari");		
		return comentari;
	}
	
	public static String getPartidaActuacio(Connection conn, String idActuacio) throws SQLException {
		String idPartida = "";
		String sql = "SELECT idpartida"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE idactuacio = ?"		
					+ " ORDER BY 1 desc LIMIT 1"; 
		PreparedStatement pstm = conn.prepareStatement(sql);		 
		pstm.setString(1, idActuacio);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			idPartida = rs.getString("idpartida");
		}
		return idPartida;
	}
	
	public static String newAssignacioCode(Connection conn) throws SQLException{
		String newCode = "1";
		
		String sql = "SELECT idassignacio"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE idassignacio like '%INF%'"
					+ " ORDER BY datacre DESC, idassignacio DESC LIMIT 1;";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		String prefix = "INF";
		if (rs.next()) { //Codis nous
			String actualCode = rs.getString("idassignacio");			
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

	public static List<AssignacioCredit> findAssignacionsPartida(Connection conn, String codi) throws SQLException {
		List<AssignacioCredit> assignacionsList = new ArrayList<AssignacioCredit>();
		String sql = "SELECT idassignacio, idactuacio, idinf, reserva, usureserva, datareserva, assignacio, usuassignacio,"
					+ " dataassignacio, comentari, usucre, datacre, idpartida, valorpa, valorpd"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE idpartida=?;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, codi);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			AssignacioCredit assignacio = new AssignacioCredit();
			assignacio.setIdAssignacio(rs.getString("idassignacio"));
			assignacio.setIdActuacio(rs.getString("idactuacio"));
			assignacio.setIdInf(rs.getString("idinf"));
			assignacio.setReserva(rs.getBoolean("reserva"));
			assignacio.setUsuReserva(UsuariCore.findUsuariByID(conn, rs.getInt("usureserva")));
			assignacio.setDatareserva(rs.getTimestamp("datareserva"));
			assignacio.setAssignacio(rs.getBoolean("assignacio"));
			assignacio.setUsuAssignacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuassignacio")));
			assignacio.setDataAssignacio(rs.getTimestamp("dataassignacio"));
			assignacio.setComentari(rs.getString("comentari"));
			assignacio.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
			assignacio.setDataCre(rs.getTimestamp("datacre"));
			assignacio.setIdPartida(rs.getString("idpartida"));
			assignacio.setValorPA(rs.getDouble("valorpa"));
			assignacio.setValorPD(rs.getDouble("valorpd"));
			assignacionsList.add(assignacio);
		}
		return assignacionsList;
	}
	
	public static List<AssignacioCredit> findAssignacions(Connection conn, String idPartida, Date dataIni, Date dataFi) throws SQLException {
		List<AssignacioCredit> assignacionsList = new ArrayList<AssignacioCredit>();
		String sql = "SELECT idassignacio, idactuacio, idinf, reserva, usureserva, datareserva, assignacio, usuassignacio,"
					+ " dataassignacio, comentari, usucre, datacre, idpartida, valorpa, valorpd"
					+ " FROM public.tbl_assignacionscredit";	 
		PreparedStatement pstm = null;
		if (dataIni != null && dataFi != null) {
			sql += " WHERE datacre >= ? AND datacre <= ? ";
			if (idPartida != "") {
				sql += " AND idpartida = ?";
			}
			pstm = conn.prepareStatement(sql);
			pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
			Calendar fi = Calendar.getInstance();
		    fi.setTime(dataFi);
		    fi.add(Calendar.DATE, 1);	
		    dataFi = fi.getTime();
			pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
			if (idPartida != "") pstm.setString(3, idPartida);	
		} else {
			if (idPartida != "") {
				sql += " WHERE idpartida = ?";				
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, idPartida);			
			} else {
				pstm = conn.prepareStatement(sql);
			}	
		}
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			AssignacioCredit assignacio = new AssignacioCredit();
			assignacio.setIdAssignacio(rs.getString("idassignacio"));
			assignacio.setIdActuacio(rs.getString("idactuacio"));
			assignacio.setIdInf(rs.getString("idinf"));
			assignacio.setReserva(rs.getBoolean("reserva"));
			assignacio.setUsuReserva(UsuariCore.findUsuariByID(conn, rs.getInt("usureserva")));
			assignacio.setDatareserva(rs.getTimestamp("datareserva"));
			assignacio.setAssignacio(rs.getBoolean("assignacio"));
			assignacio.setUsuAssignacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuassignacio")));
			assignacio.setDataAssignacio(rs.getTimestamp("dataassignacio"));
			assignacio.setComentari(rs.getString("comentari"));
			assignacio.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
			assignacio.setDataCre(rs.getTimestamp("datacre"));
			assignacio.setIdPartida(rs.getString("idpartida"));
			assignacio.setValorPA(rs.getDouble("valorpa"));
			assignacio.setValorPD(rs.getDouble("valorpd"));
			assignacionsList.add(assignacio);
		}
		return assignacionsList;
	}
	
	public static void reservar(Connection conn, String idPartida, String idActuacio, String idInforme, double valor, String comentari, int idUsuari) throws SQLException {
		String sql = "";
		PreparedStatement pstm = null;
		if (getPartidaInforme(conn, idInforme).isEmpty()) {		
			sql = "INSERT INTO public.tbl_assignacionscredit(idassignacio, idactuacio, idinf, reserva, usureserva, datareserva, assignacio, comentari, usucre, datacre, idpartida, valorpa)"
						+ " VALUES (?, ?, ?, true, ?, localtimestamp, false, ?, ?, localtimestamp, ?, ?);";
			
			pstm = conn.prepareStatement(sql);
			 
			pstm.setString(1, newAssignacioCode(conn));		
			pstm.setString(2, idActuacio);
			pstm.setString(3, idInforme);
			pstm.setInt(4, idUsuari);		
			pstm.setString(5, comentari);
			pstm.setInt(6, idUsuari);
			pstm.setString(7, idPartida);
			pstm.setDouble(8, valor);
			pstm.executeUpdate();
		} else {
			sql = "UPDATE public.tbl_assignacionscredit"
				+ " SET datareserva=localtimestamp, comentari=?, idpartida=?, valorpa=?"
				+ " WHERE idinf = ?;";
			pstm = conn.prepareStatement(sql);		
			pstm.setString(1, comentari);
			pstm.setString(2, idPartida);
			pstm.setDouble(3, valor);
			pstm.setString(4, idInforme);
			pstm.executeUpdate();
		}
		sql = "UPDATE public.tbl_informeactuacio"
				+ " SET datapartidarebujada = null, motiupartidarebujada = '', usupartidarebujada = null"
				+ " WHERE idinf=?";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInforme);	
		pstm.executeUpdate();
		
	}
	
	public static void assignar(Connection conn, String idInforme, double valor) throws SQLException {
		String sql = "UPDATE public.tbl_assignacionscredit"
					+ " SET valorpd=?, assignacio=?, dataassignacio=localtimestamp"
					+ " WHERE idinf = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setDouble(1, valor);
		pstm.setBoolean(2, true);
		pstm.setString(3, idInforme);
		pstm.executeUpdate();
	}
	
	public static double getTotalReservat(Connection conn, String idPartida) throws SQLException{
		double totalReservat = 0;
		String sql = "SELECT SUM(valorpa) AS total"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE reserva=true and assignacio=false and idpartida = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);		 
		pstm.setString(1, idPartida);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			totalReservat = rs.getDouble("total");
		}
		return totalReservat;
	}
	
	public static double getTotalPrevist(Connection conn, String idPartida) throws SQLException{
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
	
	public static double getTotalPagat(Connection conn, String idPartida) throws SQLException{
		double totalGastat = 0;
		String sql = "SELECT SUM(f.import) AS total"
				  	+ " FROM public.tbl_factures f LEFT JOIN public.tbl_assignacionscredit a ON f.idactuacio = a.idactuacio"
				  	+ " WHERE a.idpartida = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);		 
		pstm.setString(1, idPartida);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			totalGastat = rs.getDouble("total");
		}
		return totalGastat;
	}
}
