package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import bean.User;

public class GestioDron {
	public static void reservar(Connection conn, int idUsuari, Date peticio, String motiu) {
		String sql = "INSERT INTO public.tbl_dron(idreserva, peticio, usuari, motiu, datapeticio)"
				+ "VALUES (?,?,?,?,localtimestamp)";
 
		PreparedStatement pstm;
		int newCode = getNewReservaCode(conn);
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, newCode);
			pstm.setDate(2, new java.sql.Date(peticio.getTime()));
			pstm.setInt(3, idUsuari);
			pstm.setString(4, motiu);			
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String pintarReservesUsuari(Connection conn, int idUsuari){
		String reservesHTML = "";
		String estat = "";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		
		String sql = "SELECT peticio, motiu, datapeticio, autoritzada"
				+ " FROM public.tbl_dron"
				+ " WHERE usuari = ? AND peticio >=  DATE(localtimestamp)"
				+ " ORDER BY peticio";
 
		PreparedStatement pstm;		
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);	
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {		
				if (rs.getString("autoritzada") != null) {
					if (rs.getBoolean("autoritzada")) {
						estat = "està autoritzada";
					}else{
						estat = "no autoritzat";
					}
				} else {
					estat = "pendent autorització";	
				}
				reservesHTML += "<span>Petició per dia: " +  df.format(rs.getTimestamp("peticio").getTime()) + " per " + rs.getString("motiu") + " " + estat + "</span></br>";		
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return reservesHTML;
	}
	
	public static String pintarTotesReserves(Connection conn){
		String reservesHTML = "";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		User usuari = new User();
		String sql = "SELECT peticio, motiu, datapeticio, autoritzada, usuari"
				+ " FROM public.tbl_dron"
				+ " WHERE peticio >=  DATE(localtimestamp) AND autoritzada = true"
				+ " ORDER BY peticio";
 
		PreparedStatement pstm;		
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {						
				usuari = UsuariCore.findUsuariByID(conn, rs.getInt("usuari"));
				reservesHTML += "<span>Reserva de " + usuari.getNomCompletReal() +" per dia: " +  df.format(rs.getTimestamp("peticio").getTime()) + " per " + rs.getString("motiu") + "</span></br>";		
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return reservesHTML;
	}
	
	private static int getNewReservaCode(Connection conn) {
		int idReserva = 1;
		String sql = "SELECT idreserva"
					+ " FROM public.tbl_dron"
					+ " ORDER BY idreserva DESC" 
					+ " LIMIT 1";
		PreparedStatement pstm;	
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) idReserva = rs.getInt("idreserva") + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idReserva;
	}
	
}
