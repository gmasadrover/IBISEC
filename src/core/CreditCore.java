package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.Actuacio;
import bean.AssignacioCredit;
import bean.Centre;
import bean.Credit;
import bean.Expedient;
import bean.InformeActuacio;
import bean.InformeActuacio.PropostaInforme;
import bean.Partida;

public class CreditCore {
	public static void nouCredit(Connection conn, Credit credit) {
		String sql = "INSERT INTO public.tbl_credit(codi, presupost)"
					+ " VALUES (?,?)";
	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, credit.getCodi());		
			pstm.setDouble(2, credit.getPresupost());		 
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static List<Credit> llistaCredits(Connection conn) {
		String sql = "SELECT codi, presupost"
					+ " FROM public.tbl_credit";
		 
		PreparedStatement pstm;
		List<Credit> list = new ArrayList<Credit>();
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			
			while (rs.next()) {
				Credit credit = new Credit();
				credit.setCodi(rs.getString("codi"));
				credit.setPresupost(rs.getDouble("presupost"));
				list.add(credit);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		
		return list;
	}
	
	public static Credit findCredit(Connection conn, String codi) {
		 
		String sql = "SELECT presupost"
					+ " FROM public.tbl_credit"
					+ " WHERE codi = ?";
	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codi);		
			ResultSet rs = pstm.executeQuery();
		 
			if (rs.next()) {
				Credit credit = new Credit();
				credit.setCodi(codi);
				credit.setPresupost(rs.getDouble("presupost"));			
				return credit;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getNewCode(Connection conn) {
		String newCode = "R.IBIS-001";
		
		String sql = "SELECT codi"
					+ " FROM public.tbl_partides"
				    + " WHERE subpartidade = '-1'"
					+ " ORDER BY codi DESC LIMIT 1;";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				String actualCode = rs.getString("codi");
				int num = Integer.valueOf(actualCode.split("-")[1]);
				String formatted = String.format("%03d", num + 1);
				newCode = newCode.split("-")[0] + "-" + formatted;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newCode;
	}
	
	public static void novaPartida(Connection conn, Partida partida, int idUsuari) {
		String sql = "INSERT INTO public.tbl_partides(codi, nom, import, tipus, activa, usucre, datacre, subpartidade)"
					+ " VALUES (?, ?, ?, ?, true,?,localtimestamp, ?);";
	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, partida.getCodi());
			pstm.setString(2, partida.getNom());
			pstm.setDouble(3, Double.valueOf(partida.getTotalPartida()));
			pstm.setString(4, partida.getTipus());
			pstm.setInt(5, idUsuari);
			pstm.setString(6, partida.getSubpartidaDe());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	
	}
	
	public static void updatePartida(Connection conn, Partida partida) {
		String sql = "UPDATE public.tbl_partides SET nom = ?, import = ?, tipus = ?, datacre=localtimestamp"
				+ " WHERE codi = ?"; 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, partida.getNom());
			pstm.setDouble(2, Double.valueOf(partida.getTotalPartida()));
			pstm.setString(3, partida.getTipus());
			pstm.setString(4, partida.getCodi());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	

	public static void obrirPartida(Connection conn, String idPartida) {
		String sql = "UPDATE public.tbl_partides SET activa = true, datacre=localtimestamp, bloquejat = 0"
				+ " WHERE codi = ?"; 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idPartida);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void tancarPartida(Connection conn, String idPartida) {
		Partida partida = getPartida(conn, idPartida);
		if (!partida.getSubpartidaDe().equals("-1")) { //subpartides
			double bloquejat = partida.getPartidaPerAsignar();
			String sql = "UPDATE public.tbl_partides SET activa = false, datacre=localtimestamp, bloquejat = ?"
					+ " WHERE codi = ?"; 
			PreparedStatement pstm;
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setDouble(1, bloquejat);
				pstm.setString(2, idPartida);
				pstm.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else { //partida principal
			String subpartides = "";
			String sql = "SELECT codi"
					+ " FROM public.tbl_partides"
					+ " WHERE subpartidade = ?";
			PreparedStatement pstm;
			try {
				pstm = conn.prepareStatement(sql);		 
				pstm.setString(1, idPartida);
				ResultSet rs = pstm.executeQuery();
				while (rs.next()) {
					subpartides += "'" + rs.getString("codi") + "',";
				}
				if (subpartides.length()>0) {
					List<String> subpartidesList = new ArrayList<String>(Arrays.asList(subpartides.split(",")));
					for (String subpartida: subpartidesList) {
						tancarPartida(conn, subpartida.replace("'", ""));
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql = "UPDATE public.tbl_partides SET activa = false, datacre=localtimestamp"
					+ " WHERE codi = ?"; 
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, idPartida);
				pstm.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public static Partida getPartidaDefecte(Connection conn) {
		String sql = "SELECT codi, nom, import, tipus, activa, usucre, datacre, perdefecte"
				+ " FROM public.tbl_partides"
				+ " WHERE perdefecte = true";	 	
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();	
			double totalPagat = 0;
			if (rs.next()) {
				Partida partida = new Partida();
				partida.setCodi(rs.getString("codi"));			
				partida.setEstat(rs.getBoolean("activa"));
				partida.setNom(rs.getString("nom"));
				partida.setTipus(rs.getString("tipus"));
				partida.setTotalPartida(getTotalReservat(conn, rs.getString("codi")));
				totalPagat = getTotalPagat(conn, rs.getString("codi"));
				partida.setPrevistPartida(getTotalContractat(conn, rs.getString("codi")) - totalPagat);
				partida.setReservaPartida(getTotalReservat(conn, rs.getString("codi")));
				partida.setPagatPartida(totalPagat);
				return partida;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
	public static Partida getPartida(Connection conn, String codi) {
		 
		String sql = "SELECT codi, nom, import, tipus, activa, usucre, datacre, subpartidade"
					+ " FROM public.tbl_partides"
					+ " WHERE codi = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codi);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				Partida partida = new Partida();
				partida.setCodi(rs.getString("codi"));			
				partida.setEstat(rs.getBoolean("activa"));
				partida.setNom(rs.getString("nom"));
				partida.setTipus(rs.getString("tipus"));
				partida.setTotalPartida(getTotalPartida(conn, rs.getString("codi")));
				partida.setPrevistPartida(getTotalContractat(conn, rs.getString("codi")));
				partida.setReservaPartida(getTotalReservat(conn, rs.getString("codi")));
				partida.setBloquejat(getTotalBloquejat(conn, rs.getString("codi")));
				partida.setSubpartidaDe(rs.getString("subpartidade"));
				return partida;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
	public static List<Partida> getPartides(Connection conn, boolean tancades) {
		List<Partida> list = new ArrayList<Partida>();
		String sql = "SELECT *"
					+ " FROM public.tbl_partides"
					+ " WHERE subpartidade = '-1'";
		if (!tancades) sql += " AND activa=true";
		sql += " ORDER BY 1 desc"; 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			
			//double totalPagat = 0;
			while (rs.next()) {
				Partida partida = new Partida();
				partida.setCodi(rs.getString("codi"));			
				partida.setEstat(rs.getBoolean("activa"));
				partida.setNom(rs.getString("nom"));
				partida.setTipus(rs.getString("tipus"));
				//totalPagat = getTotalPagat(conn, rs.getString("codi"));
				partida.setTotalPartida(getTotalPartida(conn, rs.getString("codi")));			
				partida.setPrevistPartida(getTotalContractat(conn, rs.getString("codi")));
				partida.setReservaPartida(getTotalReservat(conn, rs.getString("codi")));		
				partida.setBloquejat(getTotalBloquejat(conn, rs.getString("codi")));
				//partida.setPagatPartida(totalPagat);
				list.add(partida);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		
		return list;
	}
	
	public static Partida getPartidaPare(Connection conn, String idPartida) {
		Partida partidaPare = new Partida();
		String sql = "SELECT subpartidade"
				+ " FROM public.tbl_partides"
				+ " WHERE subpartidade = ?";	
		sql += " ORDER BY 1 desc"; 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idPartida);
			ResultSet rs = pstm.executeQuery();	
			if (rs.next()) {
				partidaPare.setCodi(rs.getString("subpartidade"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return partidaPare;
	}
	
	public static List<Partida> getSubPartides(Connection conn, String idPartida) {
		List<Partida> list = new ArrayList<Partida>();
		String sql = "SELECT *"
				+ " FROM public.tbl_partides"
				+ " WHERE subpartidade = ?";	
		sql += " ORDER BY 1"; 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idPartida);
			ResultSet rs = pstm.executeQuery();	
			
			//double totalPagat = 0;
			while (rs.next()) {
				Partida partida = new Partida();
				partida.setCodi(rs.getString("codi"));			
				partida.setEstat(rs.getBoolean("activa"));
				partida.setNom(rs.getString("nom"));
				partida.setTipus(rs.getString("tipus"));
				//totalPagat = getTotalPagat(conn, rs.getString("codi"));
				partida.setTotalPartida(rs.getDouble("import"));
				partida.setPrevistPartida(getTotalContractatSubpartida(conn, rs.getString("codi")));
				partida.setReservaPartida(getTotalReservatSubpartida(conn, rs.getString("codi")));
				partida.setBloquejat(getTotalBloquejat(conn, rs.getString("codi")));
				//partida.setPagatPartida(totalPagat);
				list.add(partida);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static List<AssignacioCredit> getPartidaInforme(Connection conn, String idInforme) {
		List<AssignacioCredit> assignacionsList = new ArrayList<AssignacioCredit>();
		String sql = "SELECT idassignacio, idactuacio, idinf, reserva, usureserva, datareserva, assignacio, usuassignacio, dataassignacio, comentari, usucre, datacre, idpartida, valorpa, valorpd, bei, feder"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE idinf = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idInforme);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				AssignacioCredit assignacio = new AssignacioCredit();
				assignacio.setIdAssignacio(rs.getString("idassignacio"));
				assignacio.setIdInforme(idInforme);
				assignacio.setReserva(rs.getBoolean("reserva"));
				assignacio.setUsuReserva(UsuariCore.findUsuariByID(conn, rs.getInt("usureserva")));
				assignacio.setDatareserva(rs.getTimestamp("datareserva"));
				assignacio.setAssignacio(rs.getBoolean("assignacio"));
				assignacio.setUsuAssignacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuassignacio")));
				assignacio.setDataAssignacio(rs.getTimestamp("dataassignacio"));
				assignacio.setComentari(rs.getString("comentari"));
				assignacio.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
				assignacio.setDataCre(rs.getTimestamp("datacre"));
				assignacio.setPartida(CreditCore.getPartida(conn, rs.getString("idpartida")));
				assignacio.setValorPA(rs.getDouble("valorpa"));
				assignacio.setValorPD(rs.getDouble("valorpd"));
				assignacio.setBei(rs.getBoolean("bei"));
				assignacio.setFeder(rs.getBoolean("feder"));
				assignacionsList.add(assignacio);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		
		return assignacionsList;
	}
	
	public static String getCodiPartidaInforme(Connection conn, String idInforme) {
		String idPartida = "";
		String sql = "SELECT p.codi AS codi"
					+ " FROM public.tbl_assignacionscredit a LEFT JOIN public.tbl_partides p ON a.idpartida=p.codi"
					+ " WHERE idinf = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idInforme);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				if (rs.getString("codi") != null) idPartida = rs.getString("codi");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		
		return idPartida;
	}
	
	public static String getComentariPartida(Connection conn, String idInforme) {
		String comentari = "";
		String sql = "SELECT comentari"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE idinf = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idInforme);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) comentari = rs.getString("comentari");	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
			
		return comentari;
	}
	
	public static String getPartidaActuacio(Connection conn, String idActuacio) {
		String idPartida = "";
		String sql = "SELECT idpartida"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE idactuacio = ?"		
					+ " ORDER BY 1 desc LIMIT 1"; 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idActuacio);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				idPartida = rs.getString("idpartida");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		
		return idPartida;
	}
	
	public static String newAssignacioCode(Connection conn) {
		String newCode = "1";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		
		String sql = "SELECT idassignacio"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE idassignacio like '%" + yearInString + "-INF%'"
					+ " ORDER BY idassignacio DESC, datacre DESC LIMIT 1;";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();		
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newCode;
	}
	
	public static List<AssignacioCredit> findAssignacionsPartida(Connection conn, String codi)  {
		List<AssignacioCredit> assignacionsList = new ArrayList<AssignacioCredit>();
		
		String sql = "SELECT a.idassignacio AS idassignacio, a.idactuacio AS idactuacio, a.idinf AS idinf, c.nom AS nomcentre, c.municipi AS municipicentre, c.localitat AS localitatcentre, p.objecte AS objecte,"
					+ "		e.dataadjudicacio AS dataadjudicacio, i.expcontratacio AS expedient, a.comentari AS comentari, a.valorpa AS valorpa,"
					+ "		a.valorpd AS valorpd, a.idinforiginal AS idinforiginal, a.idprocediment AS idprocediment, a.idtramit AS idtramit"
					+ " FROM public.tbl_assignacionscredit a LEFT JOIN public.tbl_propostesinforme p ON a.idinf = p.idinf AND p.seleccionada = true"
					+ " 	LEFT JOIN public.tbl_informeactuacio i ON a.idinf = i.idinf" 
					+ " 	LEFT JOIN public.tbl_expedient e ON i.expcontratacio = e.expcontratacio"
					+ " 	LEFT JOIN public.tbl_actuacio t ON a.idactuacio = t.id"
					+ " 	LEFT JOIN public.tbl_centres c ON t.idcentre = c.codi"
					+ " WHERE idpartida=?"; 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codi);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {			
				AssignacioCredit assignacio = new AssignacioCredit();
				assignacio.setIdAssignacio(rs.getString("idassignacio"));
				InformeActuacio informe = new InformeActuacio();
				Actuacio actuacio = new Actuacio();
				Centre centre = new Centre();
				centre.setNom(rs.getString("nomcentre"));
				centre.setLocalitat(rs.getString("localitatcentre"));
				centre.setMunicipi(rs.getString("municipicentre"));
				actuacio.setReferencia(rs.getString("idactuacio"));
				actuacio.setCentre(centre);
				informe.setIdInf(rs.getString("idinf"));
				PropostaInforme proposta = informe.new PropostaInforme();
				proposta.setObjecte(rs.getString("objecte"));
				informe.setPropostaInformeSeleccionada(proposta);
				Expedient expedient = new Expedient();
				expedient.setExpContratacio(rs.getString("expedient"));
				expedient.setDataAdjudicacio(rs.getDate("dataadjudicacio"));
				informe.setExpcontratacio(expedient);
				informe.setActuacio(actuacio);
				assignacio.setInforme(informe);
				assignacio.setValorPA(rs.getDouble("valorpa"));
				assignacio.setValorPD(rs.getDouble("valorpd"));
				assignacionsList.add(assignacio);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return assignacionsList;
	}
	
	
	public static List<AssignacioCredit> findAssignacionsPartida(Connection conn, String codi, String estat) {
		//PART 1 -> Assignacions de credit de obres on partida = codi
		List<AssignacioCredit> assignacionsList = new ArrayList<AssignacioCredit>();
		String sql = "SELECT a.idassignacio AS idassignacio, i.expcontratacio AS expcontratacio, a.idactuacio AS idactuacio, t.descripcio AS descripcioactuacio, a.idinf AS idinf, a.reserva AS reserva, a.usureserva AS usureserva,"
					+ " 	a.datareserva AS datareserva, a.assignacio AS assignacio, a.usuassignacio AS usuassignacio,"
					+ " 	a.dataassignacio AS dataassignacio, a.comentari AS comentari, a.usucre AS usucre, a.datacre AS datacre,"
					+ " 	a.idpartida AS idpartida, a.valorpa AS valorpa, a.valorpd AS valorpd, a.bei AS bei, a.feder AS feder, i.dataaprovacio AS dataaprovacio, c.nom AS nomcentre, c.municipi AS municipicentre, c.localitat AS localitatcentre"
					+ " FROM public.tbl_informeactuacio i LEFT JOIN  public.tbl_assignacionscredit a ON i.idinf = a.idinf AND i.idactuacio = a.idactuacio" 
					+ " 	LEFT JOIN public.tbl_actuacio t ON a.idactuacio = t.id"
					+ "		LEFT JOIN public.tbl_centres c ON t.idcentre = c.codi"					
					+ " WHERE a.idpartida=?"; 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codi);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {			
				InformeActuacio informe = new InformeActuacio();
				Actuacio actuacio = new Actuacio();
				actuacio.setReferencia(rs.getString("idactuacio"));
				actuacio.setDescripcio(rs.getString("descripcioactuacio"));
				Centre centre = new Centre();
				centre.setNom(rs.getString("nomcentre"));
				centre.setMunicipi(rs.getString("municipicentre"));
				centre.setLocalitat(rs.getString("localitatcentre"));
				actuacio.setCentre(centre);
				informe.setActuacio(actuacio);	
				double totalFacturat = FacturaCore.getTotalFacturatInforme(conn, rs.getString("idinf"));		
				Expedient expedient = new Expedient();
				expedient.setExpContratacio(rs.getString("expcontratacio"));
				informe.setExpcontratacio(expedient);
				informe.setDataAprovacio(rs.getTimestamp("dataaprovacio"));
				informe.setLlistaModificacions(InformeCore.getTotesMoficacionsInforme(conn, rs.getString("idinf")));
				AssignacioCredit assignacio = new AssignacioCredit();
				assignacio.setIdAssignacio(rs.getString("idassignacio"));
				assignacio.setIdInforme(rs.getString("idinf"));
				assignacio.setInforme(informe);
				assignacio.setReserva(rs.getBoolean("reserva"));
				assignacio.setUsuReserva(UsuariCore.findUsuariByID(conn, rs.getInt("usureserva")));
				assignacio.setDatareserva(rs.getTimestamp("datareserva"));
				assignacio.setAssignacio(rs.getBoolean("assignacio"));
				assignacio.setUsuAssignacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuassignacio")));
				assignacio.setDataAssignacio(rs.getTimestamp("dataassignacio"));
				assignacio.setComentari(rs.getString("comentari"));
				assignacio.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
				assignacio.setDataCre(rs.getTimestamp("datacre"));
				assignacio.setPartida(CreditCore.getPartida(conn, rs.getString("idpartida")));
				assignacio.setValorPA(rs.getDouble("valorpa"));
				assignacio.setValorPD(rs.getDouble("valorpd"));
				assignacio.setBei(rs.getBoolean("bei"));
				assignacio.setFeder(rs.getBoolean("feder"));
				
				
				if (totalFacturat <= assignacio.getValorPD()) { // facturat menys o igual al valor obra
					assignacio.setValorPagat(totalFacturat);
				} else { // hem facturat més que l'assignat
					if (informe.getTotalModificacions() >= 0) { // existeix modificació -> eliminam l'excedent ja que serà del modificat
						assignacio.setValorPagat(assignacio.getValorPD());
					} else { // no hi ha modificacions -> hem pagat més
						assignacio.setValorPagat(totalFacturat);
					}
				}		
				
				assignacionsList.add(assignacio);
				
				if (informe.getTotalModificacions() >= 0) { // existeixen modificats -> els tramitam
					double excedentFacturat = totalFacturat - assignacio.getValorPD(); // agafem l'exceden	
					for (InformeActuacio modificacio:  informe.getLlistaModificacions()) {		
						if (modificacio.getOfertaSeleccionada().getPlic() > 0) {	
							AssignacioCredit assignacioModificat = findAssignacio(conn, modificacio.getIdInf());
							// mirem si la modificació és de la mateixa partida
							if (assignacioModificat.getPartida() != null && assignacioModificat.getPartida().getCodi() == codi) {
								if (excedentFacturat >= assignacioModificat.getValorPD()) {
									assignacioModificat.setValorPagat(assignacioModificat.getValorPD());
									excedentFacturat -= assignacioModificat.getValorPD();
								} else if (excedentFacturat >= 0) {
									assignacioModificat.setValorPagat(excedentFacturat);
									excedentFacturat = 0;
								} else {
									assignacioModificat.setValorPagat(0);
								}
								assignacionsList.add(assignacioModificat);	
							}																	
						}
					}
				}
			}
			
			//PART 2 -> Assignacions de credit de modificat no contemplats!
			
			sql = "SELECT a.idassignacio AS idassignacio, i.idinforme AS idinformeoriginal, a.idactuacio AS idactuacio, t.descripcio AS descripcioactuacio, a.idinf AS idinf, a.reserva AS reserva, a.usureserva AS usureserva,"
					+ " 	a.datareserva AS datareserva, a.assignacio AS assignacio, a.usuassignacio AS usuassignacio,"
					+ " 	a.dataassignacio AS dataassignacio, a.comentari AS comentari, a.usucre AS usucre, a.datacre AS datacre,"
					+ " 	a.idpartida AS idpartida, a.valorpa AS valorpa, a.valorpd AS valorpd, a.bei AS bei, a.feder AS feder, c.nom AS nomcentre, c.municipi AS municipicentre, c.localitat AS localitatcentre"
					+ " FROM public.tbl_modificacioinforme i LEFT JOIN  public.tbl_assignacionscredit a ON i.idmodificacio = a.idinf" 
					+ " 	LEFT JOIN public.tbl_actuacio t ON a.idactuacio = t.id"
					+ "		LEFT JOIN public.tbl_centres c ON t.idcentre = c.codi"				
					+ " WHERE a.idinf LIKE '%-MOD-%' AND a.idpartida=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codi);
			rs = pstm.executeQuery();
			while (rs.next()) {	
				InformeActuacio informe = InformeCore.getMoficacioInforme(conn, rs.getString("idinf"), false);	
				InformeActuacio informeOriginal = InformeCore.getInformePrevi(conn, rs.getString("idinformeoriginal"), false);
				for (AssignacioCredit assignacioOriginal: informeOriginal.getAssignacioCredit()) {	
					if (!assignacioOriginal.getPartida().getCodi().equals(codi)) { // Es un modificat no contemplat
						AssignacioCredit assignacio = new AssignacioCredit();
						assignacio.setIdAssignacio(rs.getString("idassignacio") + " MODIFICACIÓ");
						assignacio.setIdInforme(rs.getString("idinf"));
						assignacio.setInforme(informe);
						assignacio.setReserva(rs.getBoolean("reserva"));
						assignacio.setUsuReserva(UsuariCore.findUsuariByID(conn, rs.getInt("usureserva")));
						assignacio.setDatareserva(rs.getTimestamp("datareserva"));
						assignacio.setAssignacio(rs.getBoolean("assignacio"));
						assignacio.setUsuAssignacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuassignacio")));
						assignacio.setDataAssignacio(rs.getTimestamp("dataassignacio"));
						assignacio.setComentari(rs.getString("comentari"));
						assignacio.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
						assignacio.setDataCre(rs.getTimestamp("datacre"));
						assignacio.setPartida(CreditCore.getPartida(conn, rs.getString("idpartida")));
						assignacio.setValorPA(rs.getDouble("valorpa"));
						assignacio.setValorPD(rs.getDouble("valorpd"));
						assignacio.setBei(rs.getBoolean("bei"));
						assignacio.setFeder(rs.getBoolean("feder"));
						double excedentFacturat = informeOriginal.getTotalFacturat() - assignacioOriginal.getValorPD(); // agafem l'exceden		
						if (excedentFacturat > 0){
							for (InformeActuacio modificacio:  informeOriginal.getLlistaModificacions()) {					
								if (modificacio.getOfertaSeleccionada().getPlic() > 0) {	
									AssignacioCredit assignacioModificat = findAssignacio(conn, modificacio.getIdInf());
									if (excedentFacturat >= assignacioModificat.getValorPD()) {
										assignacioModificat.setValorPagat(assignacioModificat.getValorPD());
										excedentFacturat -= assignacioModificat.getValorPD();
									} else if (excedentFacturat >= 0) {
										assignacioModificat.setValorPagat(excedentFacturat);
										excedentFacturat = 0;
									} else {
										assignacioModificat.setValorPagat(0);
									}
									if (modificacio.getIdInf().equals(rs.getString("idinf"))) {
										assignacio.setValorPagat(assignacioModificat.getValorPagat());
										break;
									}
								}
							}
						}
						assignacionsList.add(assignacio);
						
					}
				}
			}
			
			//PART 3 -> Llicencies
			sql = "SELECT a.idassignacio AS idassignacio, a.idactuacio AS idactuacio, a.idinf AS idinf, a.reserva AS reserva, a.usureserva AS usureserva,"
						+ " 	a.datareserva AS datareserva, a.assignacio AS assignacio, a.usuassignacio AS usuassignacio,"
						+ " 	a.dataassignacio AS dataassignacio, a.comentari AS comentari, a.usucre AS usucre, a.datacre AS datacre,"
						+ " 	a.idpartida AS idpartida, a.valorpa AS valorpa, a.valorpd AS valorpd, a.bei AS bei, a.feder AS feder"
						+ " FROM public.tbl_assignacionscredit a" 				
						+ " WHERE a.idpartida=? AND a.idinf LIKE '%-LLI-%'"; 
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codi);
			rs = pstm.executeQuery();
			while (rs.next()) {			
				InformeActuacio informe = new InformeActuacio();					
				AssignacioCredit assignacio = new AssignacioCredit();
				assignacio.setIdAssignacio(rs.getString("idassignacio") + " LLICÈNCIA");
				assignacio.setIdInforme(rs.getString("idinf"));
				assignacio.setInforme(informe);
				assignacio.setReserva(rs.getBoolean("reserva"));
				assignacio.setUsuReserva(UsuariCore.findUsuariByID(conn, rs.getInt("usureserva")));
				assignacio.setDatareserva(rs.getTimestamp("datareserva"));
				assignacio.setAssignacio(rs.getBoolean("assignacio"));
				assignacio.setUsuAssignacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuassignacio")));
				assignacio.setDataAssignacio(rs.getTimestamp("dataassignacio"));
				assignacio.setComentari(rs.getString("comentari"));
				assignacio.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
				assignacio.setDataCre(rs.getTimestamp("datacre"));
				assignacio.setPartida(CreditCore.getPartida(conn, rs.getString("idpartida")));
				assignacio.setValorPA(rs.getDouble("valorpa"));
				assignacio.setValorPD(rs.getDouble("valorpd"));
				assignacio.setBei(rs.getBoolean("bei"));
				assignacio.setFeder(rs.getBoolean("feder"));					
				assignacio.setValorPagat(rs.getDouble("valorpd"));					
				
				assignacionsList.add(assignacio);
				
			}
			
			//PART 3 -> Judicial
			sql = "SELECT a.idassignacio AS idassignacio, a.idactuacio AS idactuacio, a.idinf AS idinf, a.reserva AS reserva, a.usureserva AS usureserva,"
						+ " 	a.datareserva AS datareserva, a.assignacio AS assignacio, a.usuassignacio AS usuassignacio,"
						+ " 	a.dataassignacio AS dataassignacio, a.comentari AS comentari, a.usucre AS usucre, a.datacre AS datacre,"
						+ " 	a.idpartida AS idpartida, a.valorpa AS valorpa, a.valorpd AS valorpd, a.bei AS bei, a.feder AS feder"
						+ " FROM public.tbl_assignacionscredit a" 				
						+ " WHERE a.idpartida=? AND a.idactuacio LIKE '%PRO-%'"; 
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codi);
			rs = pstm.executeQuery();
			while (rs.next()) {			
				InformeActuacio informe = new InformeActuacio();					
				AssignacioCredit assignacio = new AssignacioCredit();
				assignacio.setIdAssignacio(rs.getString("idassignacio") + " PAGAMENT JUDICIAL");
				assignacio.setIdInforme(rs.getString("idinf"));
				assignacio.setInforme(informe);
				assignacio.setReserva(rs.getBoolean("reserva"));
				assignacio.setUsuReserva(UsuariCore.findUsuariByID(conn, rs.getInt("usureserva")));
				assignacio.setDatareserva(rs.getTimestamp("datareserva"));
				assignacio.setAssignacio(rs.getBoolean("assignacio"));
				assignacio.setUsuAssignacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuassignacio")));
				assignacio.setDataAssignacio(rs.getTimestamp("dataassignacio"));
				assignacio.setComentari(rs.getString("comentari"));
				assignacio.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
				assignacio.setDataCre(rs.getTimestamp("datacre"));
				assignacio.setPartida(CreditCore.getPartida(conn, rs.getString("idpartida")));
				assignacio.setValorPA(rs.getDouble("valorpa"));
				assignacio.setValorPD(rs.getDouble("valorpd"));
				assignacio.setBei(rs.getBoolean("bei"));
				assignacio.setFeder(rs.getBoolean("feder"));					
				assignacio.setValorPagat(0);					
				assignacionsList.add(assignacio);
				
			}
			
			//PART 3 -> ALTRES
			sql = "SELECT a.idassignacio AS idassignacio, a.idactuacio AS idactuacio, a.idinf AS idinf, a.reserva AS reserva, a.usureserva AS usureserva,"
						+ " 	a.datareserva AS datareserva, a.assignacio AS assignacio, a.usuassignacio AS usuassignacio,"
						+ " 	a.dataassignacio AS dataassignacio, a.comentari AS comentari, a.usucre AS usucre, a.datacre AS datacre,"
						+ " 	a.idpartida AS idpartida, a.valorpa AS valorpa, a.valorpd AS valorpd, a.bei AS bei, a.feder AS feder"
						+ " FROM public.tbl_assignacionscredit a" 				
						+ " WHERE a.idpartida=? AND a.idactuacio = '-1'"; 
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codi);
			rs = pstm.executeQuery();
			while (rs.next()) {			
				InformeActuacio informe = new InformeActuacio();					
				AssignacioCredit assignacio = new AssignacioCredit();
				assignacio.setIdAssignacio(rs.getString("idassignacio") + " ALTRES");
				assignacio.setIdInforme(rs.getString("idinf"));
				assignacio.setInforme(informe);
				assignacio.setReserva(rs.getBoolean("reserva"));
				assignacio.setUsuReserva(UsuariCore.findUsuariByID(conn, rs.getInt("usureserva")));
				assignacio.setDatareserva(rs.getTimestamp("datareserva"));
				assignacio.setAssignacio(rs.getBoolean("assignacio"));
				assignacio.setUsuAssignacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuassignacio")));
				assignacio.setDataAssignacio(rs.getTimestamp("dataassignacio"));
				assignacio.setComentari(rs.getString("comentari"));
				assignacio.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
				assignacio.setDataCre(rs.getTimestamp("datacre"));
				assignacio.setPartida(CreditCore.getPartida(conn, rs.getString("idpartida")));
				assignacio.setValorPA(rs.getDouble("valorpa"));
				assignacio.setValorPD(rs.getDouble("valorpd"));
				assignacio.setBei(rs.getBoolean("bei"));
				assignacio.setFeder(rs.getBoolean("feder"));					
				assignacio.setValorPagat(0);	
				assignacionsList.add(assignacio);			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		return assignacionsList;
	}
	
	public static List<AssignacioCredit> findAssignacions(Connection conn, String idPartida, String idCentre, Date dataIni, Date dataFi, boolean bei, boolean feder) {
		List<AssignacioCredit> assignacionsList = new ArrayList<AssignacioCredit>();
		String sql = "SELECT idassignacio, idactuacio, idinf, reserva, usureserva, datareserva, assignacio, usuassignacio,"
					+ " dataassignacio, comentari, ass.usucre AS usucre, ass.datacre AS datacre, idpartida, valorpa, valorpd, bei, feder"
					+ " FROM public.tbl_assignacionscredit ass LEFT JOIN public.tbl_actuacio ac ON ass.idactuacio = ac.id";	 
		PreparedStatement pstm = null;
		boolean primeraCondicio = true;
		if (dataIni != null) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE ass.dataassignacio >= ?";
			}			
		}
		if (dataFi != null) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE ass.dataassignacio <= ?";
			} else {
				sql += " AND ass.dataassignacio <= ?";
			}			
		}
		if (!idPartida.isEmpty()) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE idpartida = ?";
			} else {
				sql += " AND idpartida = ?";
			}			
		}
		if (idCentre != null && !idCentre.isEmpty() && ! "-1".equals(idCentre)) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE idcentre = ?";
			} else {
				sql += " AND idcentre = ?";
			}			
		}
		if (bei) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE bei = " + bei;
			} else {
				sql += " AND bei = " + bei;
			}
		}
		if (feder) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE feder = " + feder;
			} else {
				sql += " AND feder = " + feder;
			}
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			int contVars = 1;
			if (dataIni != null) {
				pstm.setDate(contVars, new java.sql.Date(dataIni.getTime()));
				contVars += 1;
			}
			if (dataFi != null) {
				pstm.setDate(contVars, new java.sql.Date(dataFi.getTime()));
				contVars += 1;
			}
			if (!idPartida.isEmpty()) {
				pstm.setString(contVars, idPartida);
				contVars += 1;
			}
			if (idCentre != null && !idCentre.isEmpty() && ! "-1".equals(idCentre)) {
				pstm.setString(contVars, idCentre);
				contVars += 1;
			}			
			ResultSet rs = pstm.executeQuery();
			AssignacioCredit assignacio = new AssignacioCredit();
			while (rs.next()) {			
				assignacio = new AssignacioCredit();
				assignacio.setIdAssignacio(rs.getString("idassignacio"));
				assignacio.setIdInforme(rs.getString("idinf"));
				if (rs.getString("idinf").contains("-MOD")){
					assignacio.setInforme(InformeCore.getMoficacioInforme(conn, rs.getString("idinf"), false));
				} else {
					assignacio.setInforme(InformeCore.getInformePrevi(conn, rs.getString("idinf"), false));
				}			
				assignacio.setReserva(rs.getBoolean("reserva"));
				assignacio.setUsuReserva(UsuariCore.findUsuariByID(conn, rs.getInt("usureserva")));
				assignacio.setDatareserva(rs.getTimestamp("datareserva"));
				assignacio.setAssignacio(rs.getBoolean("assignacio"));
				assignacio.setUsuAssignacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuassignacio")));
				assignacio.setDataAssignacio(rs.getTimestamp("dataassignacio"));
				assignacio.setComentari(rs.getString("comentari"));
				assignacio.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
				assignacio.setDataCre(rs.getTimestamp("datacre"));
				assignacio.setPartida(CreditCore.getPartida(conn, rs.getString("idpartida")));
				assignacio.setValorPA(rs.getDouble("valorpa"));
				assignacio.setValorPD(rs.getDouble("valorpd"));
				assignacio.setBei(rs.getBoolean("bei"));
				assignacio.setFeder(rs.getBoolean("feder"));
				assignacionsList.add(assignacio);			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return assignacionsList;
	}
	
	public static void reservar(Connection conn, String idPartida, String idActuacio, String idInforme, String idAssignacio, double valor, String comentari, int idUsuari) {
		String sql = "";
		PreparedStatement pstm = null;
		if (idInforme.contains("-MOD-")) {
			sql = "INSERT INTO public.tbl_assignacionscredit(idassignacio, idactuacio, idinf, reserva, usureserva, datareserva, assignacio, comentari, usucre, datacre, idpartida, valorpa)"
					+ " VALUES (?, ?, ?, true, ?, localtimestamp, false, ?, ?, localtimestamp, ?, ?);";
		
			try {
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		
		} else {
			if (idAssignacio.equals("-1")) {	
				if (valor > 0) {
					sql = "INSERT INTO public.tbl_assignacionscredit(idassignacio, idactuacio, idinf, reserva, usureserva, datareserva, assignacio, comentari, usucre, datacre, idpartida, valorpa)"
								+ " VALUES (?, ?, ?, true, ?, localtimestamp, false, ?, ?, localtimestamp, ?, ?);";
					
					try {
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
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
					
				}
			} else {	
				if (valor > 0) {
					sql = "UPDATE public.tbl_assignacionscredit"
							+ " SET datareserva=localtimestamp, comentari=?, idpartida=?, valorpa=?"
							+ " WHERE idassignacio = ?;";
						try {
							pstm = conn.prepareStatement(sql);
							pstm.setString(1, comentari);
							pstm.setString(2, idPartida);
							pstm.setDouble(3, valor);
							pstm.setString(4, idAssignacio);
							pstm.executeUpdate();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
				} else { // eliminar reserva
					sql = "DELETE FROM public.tbl_assignacionscredit"
							+ " WHERE idassignacio = ?;";
						try {
							pstm = conn.prepareStatement(sql);
							pstm.setString(1, idAssignacio);
							pstm.executeUpdate();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}			
			sql = "UPDATE public.tbl_informeactuacio"
					+ " SET datapartidarebujada = null, motiupartidarebujada = '', usupartidarebujada = null"
					+ " WHERE idinf=?";
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, idInforme);	
				pstm.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void eliminarReservar(Connection conn, String idAssignacio) {
		String sql = "";
		PreparedStatement pstm = null;		
		sql = "DELETE FROM public.tbl_assignacionscredit"
				+ " WHERE idassignacio = ?;";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idAssignacio);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static AssignacioCredit findAssignacio(Connection conn, String idinf) {
		AssignacioCredit assignacio = new AssignacioCredit();
		if (idinf.contains("-MOD-")) {
			assignacio = findAssignacioModificat(conn, idinf);
		} else {
			assignacio = findAssignacioExpedient(conn, idinf);
		}
		return assignacio;
	}
	
	public static AssignacioCredit findAssignacioExpedient(Connection conn, String idinf) {
		AssignacioCredit assignacio = new AssignacioCredit();
		String sql = "SELECT a.idassignacio AS idassignacio, i.expcontratacio AS expcontratacio, a.idactuacio AS idactuacio, t.descripcio AS descripcioactuacio, a.idinf AS idinf, a.reserva AS reserva, a.usureserva AS usureserva,"
				+ " 	a.datareserva AS datareserva, a.assignacio AS assignacio, a.usuassignacio AS usuassignacio,"
				+ " 	a.dataassignacio AS dataassignacio, a.comentari AS comentari, a.usucre AS usucre, a.datacre AS datacre,"
				+ " 	a.idpartida AS idpartida, a.valorpa AS valorpa, a.valorpd AS valorpd, a.bei AS bei, a.feder AS feder, i.dataaprovacio AS dataaprovacio, c.nom AS nomcentre, c.municipi AS municipicentre, c.localitat AS localitatcentre"
				+ " FROM public.tbl_informeactuacio i LEFT JOIN  public.tbl_assignacionscredit a ON i.idinf = a.idinf AND i.idactuacio = a.idactuacio" 
				+ " 	LEFT JOIN public.tbl_actuacio t ON a.idactuacio = t.id"
				+ "		LEFT JOIN public.tbl_centres c ON t.idcentre = c.codi"					
				+ " WHERE a.idinf=?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idinf);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {			
				InformeActuacio informe = new InformeActuacio();
				Actuacio actuacio = new Actuacio();
				actuacio.setReferencia(rs.getString("idactuacio"));
				actuacio.setDescripcio(rs.getString("descripcioactuacio"));
				Centre centre = new Centre();
				centre.setNom(rs.getString("nomcentre"));
				centre.setMunicipi(rs.getString("municipicentre"));
				centre.setLocalitat(rs.getString("localitatcentre"));
				actuacio.setCentre(centre);
				informe.setActuacio(actuacio);	
				Expedient expedient = new Expedient();
				expedient.setExpContratacio(rs.getString("expcontratacio"));
				informe.setExpcontratacio(expedient);
				informe.setDataAprovacio(rs.getTimestamp("dataaprovacio"));
				assignacio.setIdAssignacio(rs.getString("idassignacio"));
				assignacio.setIdInforme(rs.getString("idinf"));
				assignacio.setInforme(informe);
				assignacio.setReserva(rs.getBoolean("reserva"));
				assignacio.setUsuReserva(UsuariCore.findUsuariByID(conn, rs.getInt("usureserva")));
				assignacio.setDatareserva(rs.getTimestamp("datareserva"));
				assignacio.setAssignacio(rs.getBoolean("assignacio"));
				assignacio.setUsuAssignacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuassignacio")));
				assignacio.setDataAssignacio(rs.getTimestamp("dataassignacio"));
				assignacio.setComentari(rs.getString("comentari"));
				assignacio.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
				assignacio.setDataCre(rs.getTimestamp("datacre"));
				assignacio.setPartida(CreditCore.getPartida(conn, rs.getString("idpartida")));
				assignacio.setValorPA(rs.getDouble("valorpa"));
				assignacio.setValorPD(rs.getDouble("valorpd"));
				assignacio.setBei(rs.getBoolean("bei"));
				assignacio.setFeder(rs.getBoolean("feder"));					
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return assignacio;
	}
	
	public static AssignacioCredit findAssignacioModificat(Connection conn, String idinf) {
		AssignacioCredit assignacio = new AssignacioCredit();
		String sql = "SELECT a.idassignacio AS idassignacio, a.idactuacio AS idactuacio, t.descripcio AS descripcioactuacio, a.idinf AS idinf, a.reserva AS reserva, a.usureserva AS usureserva,"
				+ " 	a.datareserva AS datareserva, a.assignacio AS assignacio, a.usuassignacio AS usuassignacio,"
				+ " 	a.dataassignacio AS dataassignacio, a.comentari AS comentari, a.usucre AS usucre, a.datacre AS datacre,"
				+ " 	a.idpartida AS idpartida, a.valorpa AS valorpa, a.valorpd AS valorpd, a.bei AS bei, a.feder AS feder, c.nom AS nomcentre, c.municipi AS municipicentre, c.localitat AS localitatcentre"
				+ " FROM public.tbl_modificacioinforme i LEFT JOIN  public.tbl_assignacionscredit a ON i.idmodificacio = a.idinf" 
				+ " 	LEFT JOIN public.tbl_actuacio t ON a.idactuacio = t.id"
				+ "		LEFT JOIN public.tbl_centres c ON t.idcentre = c.codi"					
				+ " WHERE a.idinf=?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idinf);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {			
				InformeActuacio informe = new InformeActuacio();
				Actuacio actuacio = new Actuacio();
				actuacio.setReferencia(rs.getString("idactuacio"));
				actuacio.setDescripcio(rs.getString("descripcioactuacio"));
				Centre centre = new Centre();
				centre.setNom(rs.getString("nomcentre"));
				centre.setMunicipi(rs.getString("municipicentre"));
				centre.setLocalitat(rs.getString("localitatcentre"));
				actuacio.setCentre(centre);
				informe.setActuacio(actuacio);	
				Expedient expedient = new Expedient();
				informe.setExpcontratacio(expedient);
				assignacio.setIdAssignacio(rs.getString("idassignacio") + " MODIFICACIÓ");
				assignacio.setIdInforme(rs.getString("idinf"));
				assignacio.setInforme(informe);
				assignacio.setReserva(rs.getBoolean("reserva"));
				assignacio.setUsuReserva(UsuariCore.findUsuariByID(conn, rs.getInt("usureserva")));
				assignacio.setDatareserva(rs.getTimestamp("datareserva"));
				assignacio.setAssignacio(rs.getBoolean("assignacio"));
				assignacio.setUsuAssignacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuassignacio")));
				assignacio.setDataAssignacio(rs.getTimestamp("dataassignacio"));
				assignacio.setComentari(rs.getString("comentari"));
				assignacio.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
				assignacio.setDataCre(rs.getTimestamp("datacre"));
				assignacio.setPartida(CreditCore.getPartida(conn, rs.getString("idpartida")));
				assignacio.setValorPA(rs.getDouble("valorpa"));
				assignacio.setValorPD(rs.getDouble("valorpd"));
				assignacio.setBei(rs.getBoolean("bei"));
				assignacio.setFeder(rs.getBoolean("feder"));					
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return assignacio;
	}
	
	public static void modificarAssignacio(Connection conn, AssignacioCredit assignacio) {
		String sql = "UPDATE public.tbl_assignacionscredit"
				+ " SET bei=?, feder=?"
				+ " WHERE idinf = ?;";
	
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setBoolean(1, assignacio.isBei());
			pstm.setBoolean(2, assignacio.isFeder());
			pstm.setString(3, assignacio.getIdInforme());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void assignar(Connection conn, String idInforme, double valor) {
		//
		String sql = "SELECT idassignacio, valorpa"
					+ "	FROM public.tbl_assignacionscredit"
					+ "	WHERE idinf = ?";
		String sqlAux;
		PreparedStatement pstm;
		PreparedStatement pstmAux;
		double totalAdjudicat = 0;
		double pendentAdjudicar = valor;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idInforme);
			ResultSet rs = pstm.executeQuery();
			rs = pstm.executeQuery();
			while (rs.next()) {
				if (!rs.isLast() && rs.getDouble("valorpa") < pendentAdjudicar) {
					totalAdjudicat = rs.getDouble("valorpa");
					pendentAdjudicar -= totalAdjudicat;
					sqlAux = "UPDATE public.tbl_assignacionscredit"
							+ " SET valorpd=?, assignacio=true, dataassignacio=localtimestamp"
							+ " WHERE idassignacio = ?;";
					pstmAux = conn.prepareStatement(sqlAux);
					pstmAux.setDouble(1, totalAdjudicat);
					pstmAux.setString(2, rs.getString("idassignacio"));
					pstmAux.executeUpdate();
				} else {
					sqlAux = "UPDATE public.tbl_assignacionscredit"
							+ " SET valorpd=?, assignacio=true, dataassignacio=localtimestamp"
							+ " WHERE idassignacio = ?;";
					pstmAux = conn.prepareStatement(sqlAux);
					pstmAux.setDouble(1, pendentAdjudicar);
					pstmAux.setString(2, rs.getString("idassignacio"));
					pstmAux.executeUpdate();
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void anularReserva(Connection conn, String idInforme) {
		String sql = "DELETE FROM public.tbl_assignacionscredit"
					+ " WHERE idinf = ?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idInforme);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static double getTotalReservat(Connection conn, String idPartida) {
		double totalReservat = 0;
		String subpartides = "";
		String sql = "SELECT codi"
				+ " FROM public.tbl_partides"
				+ " WHERE subpartidade = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idPartida);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				subpartides += "'" + rs.getString("codi") + "',";
			}
			if (subpartides.length()>0) {
				subpartides = subpartides.substring(0, subpartides.length()-1);		
				sql = "SELECT SUM(valorpa) AS total"
						+ " FROM public.tbl_assignacionscredit"
						+ " WHERE reserva=true and assignacio=false and idpartida IN (" + subpartides + ")";
				pstm = conn.prepareStatement(sql);	
				rs = pstm.executeQuery();
				while (rs.next()) {
					totalReservat = rs.getDouble("total");
				}
			} else { // Es subpartida
				pstm.close();
				rs.close();
				sql = "SELECT SUM(valorpa) AS total"
						+ " FROM public.tbl_assignacionscredit"
						+ " WHERE reserva=true and assignacio=false and idpartida = ?";
				pstm = conn.prepareStatement(sql);	
				pstm.setString(1, idPartida);
				rs = pstm.executeQuery();
				while (rs.next()) {
					totalReservat = rs.getDouble("total");
				}			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		
		return totalReservat;
	}
	
	public static double getTotalBloquejat(Connection conn, String idPartida) {
		double totalBloquejat = 0;
		String subpartides = "";
		String sql = "SELECT codi"
				+ " FROM public.tbl_partides"
				+ " WHERE subpartidade = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);		 
			pstm.setString(1, idPartida);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				subpartides += "'" + rs.getString("codi") + "',";
			}
			if (subpartides.length()>0) {
				List<String> subpartidesList = new ArrayList<String>(Arrays.asList(subpartides.split(",")));
				for (String subpartida: subpartidesList) {
					totalBloquejat += getTotalBloquejat(conn, subpartida.replace("'", ""));
				}			
			} else { // Es subpartida
				//Agafam el total si està cancelada
				sql = "SELECT bloquejat"
						+ " FROM public.tbl_partides"
						+ " WHERE codi = ?";
				pstm = conn.prepareStatement(sql);	
				pstm.setString(1, idPartida);
				rs = pstm.executeQuery();
				if (rs.next()) {
					totalBloquejat = rs.getDouble("bloquejat");	
				}	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return totalBloquejat;
	}
	
	public static double getTotalPartida(Connection conn, String idPartida) {
		double totalPartida = 0;
		String subpartides = "";
		String sql = "SELECT codi"
				+ " FROM public.tbl_partides"
				+ " WHERE subpartidade = ?";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idPartida);
			rs = pstm.executeQuery();
			while (rs.next()) {
				subpartides += "'" + rs.getString("codi") + "',";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if (subpartides.length()>0) {
			subpartides = subpartides.substring(0, subpartides.length()-1);
			sql = "SELECT SUM(import) AS total"
						+ " FROM public.tbl_partides"
						+ " WHERE subpartidade = ?";
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, idPartida);
				rs = pstm.executeQuery();
				while (rs.next()) {
					totalPartida = rs.getDouble("total");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		} else { //Es subpartida
			sql = "SELECT SUM(import) AS total"
						+ " FROM public.tbl_partides"
						+ " WHERE codi = ?";
			try {
				pstm.close();
				rs.close();
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, idPartida);
				rs = pstm.executeQuery();
				while (rs.next()) {
					totalPartida = rs.getDouble("total");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
		return totalPartida;
	}
	
	public static double getTotalContractat(Connection conn, String idPartida) {		
		double totalGastat = 0;
		String subpartides = "";
		String sql = "SELECT codi"
				+ " FROM public.tbl_partides"
				+ " WHERE subpartidade = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idPartida);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				subpartides += "'" + rs.getString("codi") + "',";
			}
			if (subpartides.length()>0) {
				subpartides = subpartides.substring(0, subpartides.length()-1);	
				sql = "SELECT SUM(valorpd) AS total"
							+ " FROM public.tbl_assignacionscredit"
							+ " WHERE assignacio=true AND idpartida IN (" + subpartides + ")";
				pstm = conn.prepareStatement(sql);	
				rs = pstm.executeQuery();
				while (rs.next()) {
					totalGastat = rs.getDouble("total");
				}
			} else { //Es subpartida
				sql = "SELECT SUM(valorpd) AS total"
						+ " FROM public.tbl_assignacionscredit"
						+ " WHERE assignacio=true AND idpartida = ?";
				pstm.close();
				rs.close();
				pstm = conn.prepareStatement(sql);	
				pstm.setString(1, idPartida);
				rs = pstm.executeQuery();
				while (rs.next()) {
					totalGastat = rs.getDouble("total");
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		
		return totalGastat;
	}
	
	public static double getTotalReservatSubpartida(Connection conn, String idPartida) {
		double totalReservat = 0;		
		String sql = "SELECT SUM(valorpa) AS total"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE reserva=true and assignacio=false and idpartida = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idPartida);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				totalReservat = rs.getDouble("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		
		return totalReservat;
	}
	
	public static double getTotalContractatSubpartida(Connection conn, String idPartida) {		
		double totalGastat = 0;
		String sql = "SELECT SUM(valorpd) AS total"
					+ " FROM public.tbl_assignacionscredit"
					+ " WHERE assignacio=true AND idpartida = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idPartida);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				totalGastat = rs.getDouble("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		
		return totalGastat;
	}
	
	public static double getTotalPagat(Connection conn, String idPartida) {
		double totalGastat = 0;
		
							
		return totalGastat;
	}
}
