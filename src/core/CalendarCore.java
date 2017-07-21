package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.Actuacio;
import bean.ReservaVehicle;
import bean.ReservaVehicle.Reserva;

public class CalendarCore {
	public static ReservaVehicle getSetmana(Connection conn, Calendar cal, String vehicle) {
		ReservaVehicle setmana = new ReservaVehicle();		     
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        setmana.setSetmana(week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);       
        setmana.setDilluns(cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.DATE, 1);
        setmana.setDimarts(cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.DATE, 1);
        setmana.setDimecres(cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.DATE, 1);
        setmana.setDijous(cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.DATE, 1);
        setmana.setDivendres(cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.DATE, 1);
        setmana.setDissabte(cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.DATE, 1);
        setmana.setDiumenge(cal.get(Calendar.DAY_OF_MONTH));
        
        setmana.setReservesDilluns(getReserves(conn, week, Calendar.MONDAY, vehicle));
        setmana.setReservesDimarts(getReserves(conn, week, Calendar.TUESDAY, vehicle));
        setmana.setReservesDimecres(getReserves(conn, week, Calendar.WEDNESDAY, vehicle));
        setmana.setReservesDijous(getReserves(conn, week, Calendar.THURSDAY, vehicle));
        setmana.setReservesDivendres(getReserves(conn, week, Calendar.FRIDAY, vehicle));        
      
		return setmana;
	}
	
	public static String getDiesPossibles() {
		String dies = "";
		Calendar cal = Calendar.getInstance();	  
		cal.add(Calendar.DATE, 1);
		if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
			cal.add(Calendar.DATE, 1);
			cal.add(Calendar.DATE, 1);
		}
		dies = "<option value='" + cal.get(Calendar.DAY_OF_WEEK) + "#" + cal.get(Calendar.WEEK_OF_YEAR) + "#" + cal.get(Calendar.YEAR) + "'>" + getDiaSetmana(cal.get(Calendar.DAY_OF_WEEK)) + " " + cal.get(Calendar.DAY_OF_MONTH) + "</option>";
		cal.add(Calendar.DATE, 1);
		if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
			cal.add(Calendar.DATE, 1);
			cal.add(Calendar.DATE, 1);
		}
        dies += "<option value='" + cal.get(Calendar.DAY_OF_WEEK) + "#" + cal.get(Calendar.WEEK_OF_YEAR) + "#" + cal.get(Calendar.YEAR) + "'>" + getDiaSetmana(cal.get(Calendar.DAY_OF_WEEK)) + " " + cal.get(Calendar.DAY_OF_MONTH) + "</option>";
        cal.add(Calendar.DATE, 1);
        if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
			cal.add(Calendar.DATE, 1);
			cal.add(Calendar.DATE, 1);
		}
        dies += "<option value='" + cal.get(Calendar.DAY_OF_WEEK) + "#" + cal.get(Calendar.WEEK_OF_YEAR) + "#" + cal.get(Calendar.YEAR) + "'>" + getDiaSetmana(cal.get(Calendar.DAY_OF_WEEK)) + " " + cal.get(Calendar.DAY_OF_MONTH) + "</option>";
        cal.add(Calendar.DATE, 1);
        if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
			cal.add(Calendar.DATE, 1);
			cal.add(Calendar.DATE, 1);
		}
        dies += "<option value='" + cal.get(Calendar.DAY_OF_WEEK) + "#" + cal.get(Calendar.WEEK_OF_YEAR) + "#" + cal.get(Calendar.YEAR) + "'>" + getDiaSetmana(cal.get(Calendar.DAY_OF_WEEK)) + " " + cal.get(Calendar.DAY_OF_MONTH) + "</option>";
        cal.add(Calendar.DATE, 1);
        if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
			cal.add(Calendar.DATE, 1);
			cal.add(Calendar.DATE, 1);
		}
        dies += "<option value='" + cal.get(Calendar.DAY_OF_WEEK) + "#" + cal.get(Calendar.WEEK_OF_YEAR) + "#" + cal.get(Calendar.YEAR) + "'>" + getDiaSetmana(cal.get(Calendar.DAY_OF_WEEK)) + " " + cal.get(Calendar.DAY_OF_MONTH) + "</option>";
		return dies;
	}
	
	public static boolean potReservar(Connection conn, int idUsuari, String vehicle, int setmana, int dia, int year, int horaIni, int horaFi) throws SQLException {
		boolean potReservar = true;
		String sql = "SELECT setmana"
				+ " FROM public.tbl_vehicles"
				+ " WHERE setmana = ? AND dia = ? AND hora >= ? AND hora <= ? AND year = ? LIMIT 1";
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, setmana);	
		pstm.setInt(2, dia);
		pstm.setInt(3, horaIni);
		if (horaIni < horaFi) horaFi = -1;
		pstm.setInt(4, horaFi);
		pstm.setInt(5, year);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) potReservar = false;
		sql = "SELECT setmana"
				+ " FROM public.tbl_vehicles"
				+ " WHERE setmana = ? AND year = ? AND idusuari = ? LIMIT 1";
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, setmana);	
		pstm.setInt(2, year);
		pstm.setInt(3, idUsuari);
		rs = pstm.executeQuery();
		if (rs.next()) potReservar = false;
		return potReservar;
	}
	
	public static void reservar(Connection conn, int idUsuari, String vehicle, int setmana, int dia, int any, int horaIni, int horaFi, String motiu) throws SQLException {
		String sql = "INSERT INTO public.tbl_vehicles(setmana, dia, hora, idusuari, motiu, vehicle, year)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pstm;
		int cont = horaIni + 1;
		while (cont < horaFi) {
			sql += ",(?, ?, ?, ?, ?, ?, ?)";
			cont += 1;
		}		
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, setmana);
		pstm.setInt(2, dia);
		pstm.setInt(3, horaIni);
		pstm.setInt(4, idUsuari);
		pstm.setString(5, motiu);
		pstm.setString(6, vehicle);
		pstm.setInt(7, any);
		cont = horaIni + 1;
		int numVar = 8;
		while (cont < horaFi) {
			pstm.setInt(numVar, setmana);
			pstm.setInt(numVar + 1, dia);
			pstm.setInt(numVar + 2, cont);
			pstm.setInt(numVar + 3, idUsuari);
			pstm.setString(numVar + 4, motiu);
			pstm.setString(numVar + 5, vehicle);
			pstm.setInt(numVar + 6, any);
			cont += 1;
			numVar += 7;
		}		
		pstm.executeUpdate();
	}
	
	public static void eliminarReserva(Connection conn, int any, int setmana, int dia, String vehicle, int idusuari) throws SQLException {
		String sql = "DELETE FROM public.tbl_vehicles"
				+ " WHERE setmana = ? AND year = ? AND dia = ? AND vehicle = ? AND idusuari = ?";
 
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, setmana);
		pstm.setInt(2, any);
		pstm.setInt(3, dia);
		pstm.setString(4, vehicle);
		pstm.setInt(5, idusuari);
		System.out.println(pstm.toString());
		pstm.executeUpdate();
	}
	
	public static List<Reserva> getReservesUsuari(Connection conn, int setmana, int idUsuari) throws SQLException {
		List<Reserva> reservesList = new ArrayList<Reserva>();
		String sql = "SELECT setmana, dia, hora, idusuari, motiu, vehicle, year"
				+ " FROM public.tbl_vehicles"
				+ " WHERE (setmana = ? OR setmana = ?)  AND idusuari = ?";
 
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, setmana);	
		pstm.setInt(2, setmana + 1);	
		pstm.setInt(3, idUsuari);
		ResultSet rs = pstm.executeQuery();
		Reserva reserva = new ReservaVehicle().new Reserva();
		while (rs.next()) {			
			reserva = new ReservaVehicle().new Reserva();
			reserva.setMotiu(rs.getString("motiu"));
			reserva.setUsuari(UsuariCore.findUsuariByID(conn, rs.getInt("idusuari")));
			reserva.setSetmana(rs.getInt("setmana"));
			reserva.setDia(rs.getInt("dia"));
			reserva.setHora(rs.getInt("hora"));
			reserva.setVehicle(rs.getString("vehicle"));
			reserva.setYear(rs.getInt("year"));
			reservesList.add(reserva);
		}
		return reservesList;
	}
	
	public static String pintarReservesUsuari(List<Reserva> reserves) {
		String reservesHTML = "";
		String reservaActual = "";
		int anyAnterior = -1;
		int setmanaAnterior = -1;
		int diaAnterior = -1;
		String horaFinal = "";
		Calendar date = Calendar.getInstance();
		String horesFormat = "";
		String vehicle = "";
		Reserva reservaAnt = null;
		for (Reserva reserva: reserves) {			
			System.out.println("data-hora=" + reserva.getHora() + " data-any=" + reserva.getYear() + " data-setmana=" + reserva.getSetmana() + " data-dia=" + reserva.getDia() + " data-vehicle=" + reserva.getVehicle());
			if (diaAnterior != reserva.getDia()) { // Nova reserva
				if (!reservaActual.isEmpty()) { // acabam la reserva anterior
					reservesHTML += "<span>" + reservaActual + horaFinal + "</span>";
					reservesHTML += "<span data-any=" + reserva.getYear() + " data-setmana=" + reserva.getSetmana() + " data-dia=" + reserva.getDia() + " data-vehicle=" + reserva.getVehicle() + " data-idusuari=" + reserva.getUsuari().getIdUsuari() + " class='glyphicon glyphicon-remove deleteReservaVehicle'></span></br>"; 
				}
				date = Calendar.getInstance();
				date.set(Calendar.YEAR, reserva.getYear());
				date.set(Calendar.WEEK_OF_YEAR, reserva.getSetmana());
				date.set(Calendar.DAY_OF_WEEK, reserva.getDia());
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");					
				if (reserva.getVehicle().equals("cotxe")) {
					horesFormat = ReservaVehicle.horesCotxe[reserva.getHora()-1];
					vehicle = " del cotxe";
				} else {
					horesFormat = ReservaVehicle.horesFurgoneta[reserva.getHora()-1];
					vehicle = " de la furgoneta";
				}
				reservaActual = "Reserva dia " + df.format(date.getTime()) + vehicle + " de " + horesFormat + " a ";				
				diaAnterior = reserva.getDia();
			}
			if (reserva.getVehicle().equals("cotxe")) {
				horaFinal = ReservaVehicle.horesCotxe[reserva.getHora()];
			} else {
				horaFinal = ReservaVehicle.horesFurgoneta[reserva.getHora()];
			}
			reservaAnt = reserva;
		}	
		if (!reservaActual.isEmpty()) { // acabam la reserva anterior
			reservesHTML += "<span>" + reservaActual + horaFinal + "  </span>";
			reservesHTML += "<span data-any=" + reservaAnt.getYear() + " data-setmana=" + reservaAnt.getSetmana() + " data-dia=" + reservaAnt.getDia() + " data-vehicle=" + reservaAnt.getVehicle() + " data-idusuari=" + reservaAnt.getUsuari().getIdUsuari() + " class='glyphicon glyphicon-remove deleteReservaVehicle'></span></br>"; 
		}
		return reservesHTML;
	}
	
	private static Reserva[] getReserves(Connection conn, int setmana, int dia, String vehicle) {
		Reserva[] reserves = new Reserva[19];
		String sql = "SELECT setmana, dia, hora, idusuari, motiu, vehicle"
				+ " FROM public.tbl_vehicles"
				+ " WHERE setmana = ? AND dia = ? AND vehicle = ?";
 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, setmana);	
			pstm.setInt(2, dia);	
			pstm.setString(3, vehicle);
			ResultSet rs = pstm.executeQuery();
			int hora = 1;
			Reserva reserva = new ReservaVehicle().new Reserva();
			while (rs.next()) {
				hora = rs.getInt("hora");
				reserva = new ReservaVehicle().new Reserva();
				reserva.setMotiu(rs.getString("motiu"));
				reserva.setUsuari(UsuariCore.findUsuariByID(conn, rs.getInt("idusuari")));
				reserves[hora -1] = reserva;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reserves;
	}
	
	private static String getDiaSetmana(int diaSetmana) {
		String dia = "";
		switch (diaSetmana) {
			case 2:
				dia = "dilluns";
				break;
			case 3:
				dia = "dimarts";
				break;
			case 4:
				dia = "dimecres";
				break;
			case 5:
				dia = "dijous";
				break;
			case 6:
				dia = "divendres";
				break;
		}
		return dia;
	}
}
