package core;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import bean.Expedient;
import bean.InformeActuacio;
import bean.InformeActuacio.PropostaInforme;
import bean.Oferta;
import bean.User;
import utils.Fitxers;
import utils.Fitxers.Fitxer;

public class InformeCore {
	
	private static InformeActuacio initInforme(Connection conn, ResultSet rs, boolean ambDocuments) throws SQLException, NamingException {
		InformeActuacio informe = new InformeActuacio();
		informe.setIdInf(rs.getString("idinf"));
		informe.setIdTasca(rs.getInt("idtasca"));
		informe.setIdIncidencia(rs.getString("idincidencia"));
		informe.setActuacio(ActuacioCore.findActuacio(conn, rs.getString("idactuacio")));	
		User usuari = UsuariCore.findUsuariByID(conn, rs.getInt("usucre"));
		informe.setUsuari(usuari);
		informe.setDataCreacio(rs.getTimestamp("datacre"));
		informe.setLlistaPropostes(getPropostesInforme(conn, rs.getString("idinf")));
		informe.setPropostaInformeSeleccionada(getPropostaSeleccionada(conn, rs.getString("idinf")));
		informe.setPartida(CreditCore.getPartidaInforme(conn, rs.getString("idinf")));	
		informe.setCodiPartida(CreditCore.getCodiPartidaInforme(conn, rs.getString("idinf")));	
		informe.setComentariPartida(CreditCore.getComentariPartida(conn, rs.getString("idinf")));
		informe.setUsuariCapValidacio(UsuariCore.findUsuariByID(conn, rs.getInt("usucapvalidacio")));
		informe.setDataCapValidacio(rs.getTimestamp("datacapvalidacio"));
		informe.setComentariCap(rs.getString("comentaricap"));
		informe.setUsuariAprovacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuaprovacio")));
		informe.setDataAprovacio(rs.getTimestamp("dataaprovacio"));
		informe.setNotes(rs.getString("notes"));
		informe.setLlistaOfertes(OfertaCore.findOfertes(conn, rs.getString("idinf")));
		informe.setOfertaSeleccionada(OfertaCore.findOfertaSeleccionada(conn, rs.getString("idinf")));
		informe.setLlistaFactures(FacturaCore.getFacturesInforme(conn, rs.getString("idinf")));
		informe.setLlistaCertificacions(FacturaCore.getCertificacionsInforme(conn, rs.getString("idinf")));
		informe.setDataRebujada(rs.getTimestamp("datapartidarebujada"));
		informe.setPartidaRebutjadaMotiu(rs.getString("motiupartidarebujada"));
		informe.setTipo(rs.getString("tipo"));
		informe.setExpcontratacio(ExpedientCore.findExpedient(conn, rs.getString("expcontratacio")));
		informe.setDataPD(rs.getTimestamp("datapd"));
		informe.setTipoPD(rs.getString("tipopd"));
		informe.setLlistaModificacions(getMoficacionsInforme(conn, rs.getString("idinf")));
		informe.setLlicencia(LlicenciaCore.findLlicenciaExpedient(conn, rs.getString("expcontratacio")));
		
		if (ambDocuments) {
			informe.setAdjunts(utils.Fitxers.ObtenirFitxers(rs.getString("idincidencia"), rs.getString("idactuacio"), "Informe Previ", rs.getString("idtasca"),""));
			informe.setPropostaActuacio(getPropostaActuacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setVistiplauPropostaActuacio(getVisiplauPropostaActuacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setInformeSupervisio(getInformeSupervisio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setConformeAreaEconomivaPropostaActuacio(getConformeAreaEconomivaPropostaActuacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setAutoritzacioPropostaAutoritzacio(getAutoritzacioPropostaActuacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setAutoritzacioConsellDeGovern(getAutoritzacioConsellDeGovern(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setAutoritzacioConseller(getAutoritzacioConseller(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setPropostaTecnica(getPropostaTecnica(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setAutoritzacioPropostaDespesa(getAutoritzacioPropostaDespesa(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setContracteSignat(getContracteSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			
		}
		return informe;
	}
	
	private static PropostaInforme initProposta(ResultSet rs) throws SQLException {
		PropostaInforme proposta = new InformeActuacio().new PropostaInforme();
		proposta.setIdProposta(rs.getString("idproposta"));
		proposta.setObjecte(rs.getString("objecte"));
		proposta.setTipusObra(rs.getString("tipusobra"));
		proposta.setLlicencia(rs.getBoolean("llicencia"));
		proposta.setTipusLlicencia(rs.getString("tipusllicencia"));
		proposta.setContracte(rs.getBoolean("contracte"));
		proposta.setPbase(rs.getDouble("pbase"));
		proposta.setIva(rs.getDouble("iva"));
		proposta.setPlic(rs.getDouble("plic"));
		proposta.setTermini(rs.getString("termini"));
		proposta.setComentari(rs.getString("comentari"));	
		proposta.setSeleccionada(rs.getBoolean("seleccionada"));
		return proposta;
	}
	
	public static String nouInforme(Connection conn, InformeActuacio informe, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.tbl_informeactuacio(idinf, idtasca, idincidencia, idactuacio, usucre, datacre)"
					+ " VALUES (?, ?, ?, ?, ?, localtimestamp);";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	
		String idNouInforme = idNouInforme(conn);
		pstm.setString(1, idNouInforme);
		pstm.setInt(2, informe.getIdTasca());
		pstm.setString(3, informe.getIdIncidencia());
		pstm.setString(4, informe.getActuacio().getReferencia());
		pstm.setInt(5, idUsuari);
		pstm.executeUpdate();
		
		for (PropostaInforme proposta: informe.getLlistaPropostes()) {
			novaProposta(conn, proposta, idNouInforme);
		}
		
		return idNouInforme;
	}
	public static String novaProposta(Connection conn, PropostaInforme proposta, String idInforme) throws SQLException {		
		String sql = "INSERT INTO public.tbl_propostesinforme(idproposta, idinf, objecte, tipusobra, llicencia, tipusllicencia, contracte, pbase, iva, plic, termini, comentari, seleccionada)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, false);";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	
		String idNovaProposta = idNovaProposta(conn);
		pstm.setString(1, idNovaProposta);
		pstm.setString(2, idInforme);
		pstm.setString(3, proposta.getObjecte());
		pstm.setString(4, proposta.getTipusObra());
		pstm.setBoolean(5, proposta.isLlicencia());
		pstm.setString(6, proposta.getTipusLlicencia());
		pstm.setBoolean(7, proposta.isContracte());
		pstm.setDouble(8, proposta.getPbase());
		pstm.setDouble(9, proposta.getIva());
		pstm.setDouble(10, proposta.getPlic());
		pstm.setString(11, proposta.getTermini());
		pstm.setString(12, proposta.getComentari());
		pstm.executeUpdate();
		return idNovaProposta;
	}
	
	public static void modificarInforme(Connection conn, InformeActuacio informe, int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
					+ " SET usucre = ?, datacre=localtimestamp, usuaprovacio=null, dataaprovacio=null"
					+ " WHERE idinf = ?";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	
		pstm.setInt(1, idUsuari);
		pstm.setString(2, informe.getIdInf());
		for (PropostaInforme proposta: informe.getLlistaPropostes()) {
			if (existProposta(conn, proposta.getIdProposta())) {
				modificarProposta(conn, proposta);				
			}else{
				novaProposta(conn,proposta,informe.getIdInf());
			}
			
		}
		pstm.executeUpdate();
		
	}
	
	public static void modificarProposta(Connection conn, PropostaInforme proposta) throws SQLException {		
		String sql = "UPDATE public.tbl_propostesinforme"
					+ " SET objecte=?, tipusobra=?, llicencia=?, tipusllicencia=?, contracte=?, pbase=?, iva=?, plic=?, termini=?, comentari=?"
					+ " WHERE idproposta=?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, proposta.getObjecte());
		pstm.setString(2, proposta.getTipusObra());
		pstm.setBoolean(3, proposta.isLlicencia());
		pstm.setString(4, proposta.getTipusLlicencia());
		pstm.setBoolean(5, proposta.isContracte());
		pstm.setDouble(6, proposta.getPbase());
		pstm.setDouble(7, proposta.getIva());
		pstm.setDouble(8, proposta.getPlic());
		pstm.setString(9, proposta.getTermini());
		pstm.setString(10, proposta.getComentari());
		pstm.setString(11, proposta.getIdProposta());
		pstm.executeUpdate();
	}
	
	public static InformeActuacio getInformePrevi(Connection conn, String idInforme, boolean ambDocuments) throws SQLException, NamingException {
		InformeActuacio informePrevi = new InformeActuacio();
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, expcontratacio, datapd, tipopd"
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idinf = ?";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInforme);	
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			informePrevi = initInforme(conn, rs, ambDocuments);
		}
		return informePrevi;
	}
	
	public static InformeActuacio getInformePreviInfo(Connection conn, String idInforme) throws SQLException, NamingException {
		InformeActuacio informePrevi = new InformeActuacio();
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, expcontratacio, datapd, tipopd"
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idinf = ?";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInforme);	
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			informePrevi.setIdInf(rs.getString("idinf"));
			informePrevi.setTipo(rs.getString("tipo"));
			informePrevi.setDataCreacio(rs.getTimestamp("datacre"));
			informePrevi.setDataAprovacio(rs.getTimestamp("dataaprovacio"));
			informePrevi.setOfertaSeleccionada(OfertaCore.findOfertaSeleccionada(conn, idInforme));
		}
		return informePrevi;
	}
	
	public static InformeActuacio getInformeTasca(Connection conn, int idTasca) throws SQLException, NamingException {
		InformeActuacio informe = new InformeActuacio();
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, expcontratacio, datapd, tipopd" 
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idtasca = ?"
					+ " ORDER BY idinf;";		 
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idTasca);	
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) informe = initInforme(conn, rs, true);	
		return informe;
	}
	
	public static Fitxer getPropostaActuacio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer PA = new Fitxer();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		PA =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Proposta actuació/");
		return PA;
	}
	
	public static Fitxer getVisiplauPropostaActuacio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer VistiplauPA = new Fitxer();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		VistiplauPA = utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Vistiplau Cap/");
		return VistiplauPA;
	}
	
	public static Fitxer getInformeSupervisio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer VistiplauPA = new Fitxer();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		VistiplauPA = utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Informe Supervisió/");
		return VistiplauPA;
	}
	
	public static Fitxer getConformeAreaEconomivaPropostaActuacio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer VistiplauPA = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		VistiplauPA = utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Conforme àrea financera/");
		return VistiplauPA;
	}
	
	public static Fitxer getAutoritzacioPropostaActuacio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer AutoritzacioPA = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		AutoritzacioPA =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Autorització Proposta d'actuació/");
		return AutoritzacioPA;
	}	
	
	public static Fitxer getAutoritzacioConsellDeGovern(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer AutoritzacioPA = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		AutoritzacioPA =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Autorització Consell De Govern/");
		return AutoritzacioPA;
	}	
	
	public static Fitxer getAutoritzacioConseller(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer AutoritzacioPA = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		AutoritzacioPA =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Autorització Conseller/");
		return AutoritzacioPA;
	}	
	
	public static Fitxer getPropostaTecnica(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer PT = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		PT =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Proposta tècnica/");
		return PT;
	}
	
	public static Fitxer getAutoritzacioPropostaDespesa(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer PT = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		PT =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Autorització  Proposta despesa/");
		return PT;
	}
	
	public static Fitxer getContracteSignat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer contracte = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		contracte =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Contracte signat/");
		return contracte;
	}
	
	public static List<InformeActuacio> getInformesActuacio(Connection conn, String idActuacio) throws SQLException, NamingException {
		 List<InformeActuacio> informes = new ArrayList<InformeActuacio>();
		 String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, expcontratacio, datapd, tipopd" 
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idactuacio = ?"
					+ " ORDER BY idinf DESC;";		 
		
		 PreparedStatement pstm = conn.prepareStatement(sql);
		 pstm.setString(1, idActuacio);	
		 ResultSet rs = pstm.executeQuery();
		 while (rs.next()) {
			 informes.add(initInforme(conn, rs, true));			
		 }
		
		 return informes;
	}
	
	public static List<PropostaInforme> getPropostesInforme(Connection conn, String idInf) throws SQLException {
		List<PropostaInforme> propostes = new ArrayList<PropostaInforme>();
		String sql = "SELECT idproposta, idinf, objecte, tipusobra, llicencia, tipusllicencia, contracte, pbase, iva, plic, termini, comentari, seleccionada"
					+ " FROM public.tbl_propostesinforme"
					+ " WHERE idinf = ?"
					+ " ORDER BY 1";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInf);	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			propostes.add(initProposta(rs));
		}
		return propostes;
	}
	
	public static PropostaInforme getPropostaSeleccionada(Connection conn, String idInf) throws SQLException {
		PropostaInforme proposta = new InformeActuacio().new PropostaInforme();
		String sql = "SELECT idproposta, idinf, objecte, tipusobra, llicencia, tipusllicencia, contracte, pbase, iva, plic, termini, comentari, seleccionada"
				+ " FROM public.tbl_propostesinforme"
				+ " WHERE idinf = ? AND seleccionada='true'";
		PreparedStatement pstm = conn.prepareStatement(sql);		
		pstm.setString(1, idInf);	
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) 	proposta = initProposta(rs);		
		return proposta;
	}
	
	public static void validacioCapInforme(Connection conn, String idInf, int idUsuari, String comentariCap, Date datacapvalidacio) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
					+ " SET usucapvalidacio=?, datacapvalidacio=?, comentaricap=?"
					+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);		
		if (datacapvalidacio == null) {
			pstm.setDate(2, null);
		}else{
			pstm.setDate(2,  new java.sql.Date(datacapvalidacio.getTime()));
		}
		pstm.setString(3, comentariCap);	
		pstm.setString(4, idInf);
		pstm.executeUpdate();
	}
	
	public static void eliminarValidacioCapInforme(Connection conn, String idInf) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
				+ " SET usucapvalidacio=null, datacapvalidacio=null, comentaricap=null"
				+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInf);
		pstm.executeUpdate();
	}
	
	
	public static void aprovacioInforme(Connection conn, String idInf, int idUsuari, Date dataAprovacio) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
					+ " SET usuaprovacio=?, dataaprovacio=?"
					+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);	
		if (dataAprovacio == null) {
			pstm.setDate(2,  null);
		} else {
			pstm.setDate(2,  new java.sql.Date(dataAprovacio.getTime()));
		}
		pstm.setString(3, idInf);
		pstm.executeUpdate();		
	}
	
	public static void eliminarAprovacioInforme(Connection conn, String idInf) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
				+ " SET usuaprovacio=null, dataaprovacio=null"
				+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);	
		pstm.setString(1, idInf);
		pstm.executeUpdate();
	}
	
	public static void assignarExpedient(Connection conn, String idInforme, String nouCodi) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
				+ " SET expcontratacio=?"
				+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, nouCodi);	
		pstm.setString(2, idInforme);
		pstm.executeUpdate();
	}
	
	private static String idNouInforme(Connection conn) throws SQLException{
		String newCode = "1";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		
		String sql = "SELECT idinf, datacre"
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idinf like '%" + yearInString + "-INF%'"
					+ " ORDER BY idinf DESC LIMIT 1;";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();		
		String prefix = "INF";
		if (rs.next()) { //Codis nous
			String actualCode = rs.getString("idinf");			
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
	
	private static String idNovaProposta(Connection conn) throws SQLException{
		String newCode = "1";	
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		
		String sql = "SELECT idproposta"
					+ " FROM public.tbl_propostesinforme"
					+ " WHERE idproposta like '%" + yearInString + "-PD%'"
					+ " ORDER BY idproposta DESC LIMIT 1;";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		
		String prefix = "PD";
		if (rs.next()) { //Codis nous
			String actualCode = rs.getString("idproposta");			
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
	
	private static boolean existProposta(Connection conn, String idProposta) throws SQLException {
		boolean exist = false;
		String sql = "SELECT idproposta"
				+ " FROM public.tbl_propostesinforme"
				+ " WHERE idproposta = ?";				
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idProposta);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) exist = true;
		return exist;
	}
	
	public static void seleccionarProposta(Connection conn, String idProposta, String idInforme) throws SQLException {
		String sql = "UPDATE public.tbl_propostesinforme"
				+ " SET seleccionada = false"
				+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInforme);	
		pstm.executeUpdate();
		sql = "UPDATE public.tbl_propostesinforme"
				+ " SET seleccionada = true"
				+ " WHERE idproposta=?";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, idProposta);	
		pstm.executeUpdate();
	}
	
	public static void rebutjarPartida(Connection conn, String idInforme, String comentari, int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
				+ " SET datapartidarebujada = localtimestamp, motiupartidarebujada = ?, usupartidarebujada = ?"
				+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, comentari);
		pstm.setInt(2, idUsuari);
		pstm.setString(3, idInforme);	
		pstm.executeUpdate();
	}
	
	public static List<String> getTiposPD(Connection conn) throws SQLException {
		List<String> tiposPDList = new ArrayList<String>();
		String sql = "SELECT DISTINCT tipopd"
				+ " FROM public.tbl_informeactuacio"
				+ " WHERE tipopd <> ''";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			tiposPDList.add(rs.getString("tipopd"));
		}
		return tiposPDList;
	}
	
	public static List<InformeActuacio> getInformesExpedients(Connection conn, String estat, String tipus, String contracte, double importObraMajor, String year) throws SQLException, NamingException {
		List<InformeActuacio> expedientsList = new ArrayList<InformeActuacio>();
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, i.datacre AS datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, i.expcontratacio AS expcontratacio, datapd, tipopd"
				+ " FROM public.tbl_expedient e LEFT JOIN public.tbl_informeactuacio i ON e.expcontratacio = i.expcontratacio";					
		PreparedStatement pstm;
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance(); 
		Date dataFi = cal.getTime();
		String dataFiString = df.format(dataFi);	
		boolean primeraCondicio = true;
		
		if (estat != null && !estat.isEmpty() && ! estat.equals("-1")) {	
			primeraCondicio = false;
			if ("redaccio".equals(estat)) sql+= " WHERE a.datatancament IS NULL AND e.dataliquidacio IS NULL AND i.expcontratacio = ''"; 
			if ("iniciExpedient".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND i.expcontratacio != '' AND e.databoib IS NULL AND e.dataadjudicacio IS NULL"; 
			if ("publicat".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.databoib IS NOT NULL AND e.datalimitprsentacio >= '" + dataFiString + "' AND e.dataadjudicacio IS NULL"; 
			if ("licitacio".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.databoib IS NOT NULL AND e.datalimitprsentacio < '" + dataFiString + "' AND e.dataadjudicacio IS NULL"; 	
			if ("adjudicacio".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.dataadjudicacio IS NOT NULL AND e.dataformalitzaciocontracte IS NULL"; 	
			if ("firmat".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.datarecepcio IS NULL AND e.databoib > '01/01/2016' AND (e.datainiciexecucio IS NULL OR e.datainiciexecucio > localtimestamp) AND e.dataformalitzaciocontracte IS NOT NULL"; 	
			if ("execucio".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.databoib > '01/01/2016' AND e.datainiciexecucio <= localtimestamp AND e.datarecepcio IS NULL"; 	
			if ("garantia".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.databoib > '01/01/2016' AND e.datarecepcio IS NOT NULL AND e.dataretorngarantia < localtimestamp"; 	
			if ("acabat".equals(estat)) sql+= " WHERE e.dataliquidacio IS NOT NULL"; 	
		}		
		if (tipus != null && !tipus.isEmpty() && ! tipus.equals("-1")) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE e.tipus = '" + tipus + "'";
			}else{
				sql += " AND e.tipus = '" + tipus + "'";
			}
		}
		if (contracte != null && !contracte.isEmpty() && ! contracte.equals("-1")) {			
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE e.contracte = '" + contracte + "'";
			}else{
				sql += " AND e.contracte = '" + contracte + "'";
			}
			
		}	
		if (year != null && !year.isEmpty() && ! year.equals("-1")) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE e.anyexpedient::INT = " + year;
							
			}else{
					sql += " AND e.anyexpedient::INT = " + year;
							
			}			
		}
		sql += " ORDER BY datacre DESC, expcontratacio DESC";
		pstm = conn.prepareStatement(sql);	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {		
			expedientsList.add(initInforme(conn, rs, false));
		}
		return expedientsList;
	}
	
	
	public static void modificarPartida(Connection conn, InformeActuacio informe, String idPartida, int idUsuari) throws SQLException {		
		if (idPartida.equals("-1")) {
			//Eliminam asignació anterior
			String sql = "DELETE FROM public.tbl_assignacionscredit WHERE idinf = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, informe.getIdInf());
			pstm.executeUpdate();	
		} else {
			if (informe.getPartida() != null && !informe.getPartida().equals(idPartida)) {
				//Eliminam asignació anterior
				String sql = "DELETE FROM public.tbl_assignacionscredit WHERE idinf = ?";
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setString(1, informe.getIdInf());
				pstm.executeUpdate();	
				//Nova assignacio
				CreditCore.reservar(conn, idPartida, informe.getActuacio().getReferencia(), informe.getIdInf(), informe.getPropostaInformeSeleccionada().getPlic(), informe.getComentariPartida(), idUsuari);			
			}
			if (informe.getPartida() == null) {
				//Nova assignacio
				CreditCore.reservar(conn, idPartida, informe.getActuacio().getReferencia(), informe.getIdInf(), informe.getPropostaInformeSeleccionada().getPlic(), informe.getComentariPartida(), idUsuari);			
			}
		}
	}
	
	public static void modificarTecnic(Connection conn, InformeActuacio informe, int idUsuari) throws SQLException {		
		if (informe.getUsuari() == null || informe.getUsuari().getIdUsuari() != idUsuari) {
			String sql = "UPDATE public.tbl_informeactuacio"
					+ " SET usucre = ?"
					+ " WHERE idinf=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);
			pstm.setString(2, informe.getIdInf());	
			pstm.executeUpdate();
		}		
	}
	
	public static void modificarDataCreacio(Connection conn, InformeActuacio informe, Date dataCreacio) throws SQLException {		
		if (informe.getDataCreacio() == null || informe.getDataCreacio() != dataCreacio) {
			String sql = "UPDATE public.tbl_informeactuacio"
					+ " SET datacre = ?"
					+ " WHERE idinf=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setDate(1,  new java.sql.Date(dataCreacio.getTime()));
			pstm.setString(2, informe.getIdInf());	
			pstm.executeUpdate();
		}		
	}
	
	public static InformeActuacio getModificacioTasca(Connection conn, int idTasca) throws SQLException, NamingException {
		InformeActuacio informe = new InformeActuacio();
		String sql = "SELECT idinforme"
					+ " FROM public.tbl_tasques"
					+ " WHERE idtasca = ?"
					+ " ORDER BY idtasca;";		 
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idTasca);	
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) informe = getMoficacioInforme(conn, rs.getString("idinforme"));
		return informe;
	}
	
	public static String afegirModificacioInforme(Connection conn, String idInforme, PropostaInforme proposta, Oferta oferta, User usuari) throws SQLException {
		String sql = "INSERT INTO public.tbl_modificacioinforme(idmodificacio, idinforme, tipusobra, llicencia, tipusllicencia, pbase, iva, plic, cifempresa, termini, comentari, usucre, datacre, objecte)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, localtimestamp, ?);";
		PreparedStatement pstm = conn.prepareStatement(sql);
		String idModificacio = getNovaModificacio(conn);
		pstm.setString(1, idModificacio);
		pstm.setString(2, idInforme);
		pstm.setString(3, proposta.getTipusObra());
		pstm.setBoolean(4, proposta.isLlicencia());
		pstm.setString(5, proposta.getTipusLlicencia());
		pstm.setDouble(6, proposta.getPbase());
		pstm.setDouble(7, proposta.getIva());
		pstm.setDouble(8, proposta.getPlic());
		pstm.setString(9, oferta.getCifEmpresa());
		pstm.setString(10, proposta.getTermini());
		pstm.setString(11, oferta.getComentari());
		pstm.setInt(12, usuari.getIdUsuari());
		pstm.setString(13, proposta.getObjecte());
		pstm.executeUpdate();
		return idModificacio;
	}
	
	public static InformeActuacio getMoficacioInforme(Connection conn, String idInforme) throws SQLException, NamingException {
		InformeActuacio modificacio = new InformeActuacio();
		String sql = "SELECT m.idmodificacio AS idmodificacio, m.idinforme AS idinforme, m.tipusobra AS tipusobra, m.llicencia AS llicencia, m.tipusllicencia AS tipusllicencia, m.pbase AS pbase, m.iva AS iva, m.plic AS plic, m.cifempresa AS cifempresa, m.termini AS termini, m.comentari AS comentari, m.usucre AS usucre, m.datacre AS datacre, m.objecte AS objecte , i.idincidencia AS idincidencia, i.idactuacio AS idactuacio, i.expcontratacio AS expcontratacio"
					+ " FROM public.tbl_modificacioinforme m LEFT JOIN public.tbl_informeactuacio i ON m.idinforme = i.idinf"
					+ " WHERE m.idmodificacio = ?";		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInforme);
		ResultSet rs = pstm.executeQuery();
		PropostaInforme propostaModificacio = null;
		Oferta ofertaModificacio = new Oferta();
		if (rs.next()) {
			modificacio.setIdInf(rs.getString("idmodificacio"));
			modificacio.setIdInfOriginal(rs.getString("idinforme"));
			modificacio.setUsuari(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
			modificacio.setDataCreacio(rs.getTimestamp("datacre"));
			modificacio.setPropostaTecnica(getInformeModificacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinforme"), rs.getString("idmodificacio")));
			propostaModificacio = modificacio.new PropostaInforme();
			propostaModificacio.setObjecte(rs.getString("objecte"));
			propostaModificacio.setTipusObra(rs.getString("tipusobra"));
			propostaModificacio.setLlicencia(rs.getBoolean("llicencia"));
			propostaModificacio.setTipusLlicencia(rs.getString("tipusLlicencia"));	
			propostaModificacio.setTermini(rs.getString("termini"));
			propostaModificacio.setPbase(rs.getDouble("pbase"));
			propostaModificacio.setIva(rs.getDouble("iva"));
			propostaModificacio.setPlic(rs.getDouble("plic"));
			ofertaModificacio = new Oferta();
			ofertaModificacio.setPbase(rs.getDouble("pbase"));
			ofertaModificacio.setIva(rs.getDouble("iva"));
			ofertaModificacio.setPlic(rs.getDouble("plic"));
			ofertaModificacio.setCifEmpresa(rs.getString("cifempresa"));
			ofertaModificacio.setNomEmpresa(EmpresaCore.findEmpresa(conn,rs.getString("cifEmpresa")).getName());
			ofertaModificacio.setComentari(rs.getString("comentari"));
			modificacio.setPropostaInformeSeleccionada(propostaModificacio);
			modificacio.setOfertaSeleccionada(ofertaModificacio);
			modificacio.setPartida(CreditCore.getPartidaInforme(conn, rs.getString("idmodificacio")));	
			modificacio.setExpcontratacio(ExpedientCore.findExpedient(conn, rs.getString("expcontratacio")));
			modificacio.setActuacio(ActuacioCore.findActuacio(conn, rs.getString("idactuacio")));
		}
		return modificacio;
	}
	
	public static List<InformeActuacio> getMoficacionsInforme(Connection conn, String idInforme) throws SQLException, NamingException {
		List<InformeActuacio> modificacionsList = new ArrayList<InformeActuacio>();
		String sql = "SELECT m.idmodificacio AS idmodificacio, m.idinforme AS idinforme, m.tipusobra AS tipusobra, m.llicencia AS llicencia, m.tipusllicencia AS tipusllicencia, m.pbase AS pbase, m.iva AS iva, m.plic AS plic, m.cifempresa AS cifempresa, m.termini AS termini, m.comentari AS comentari, m.usucre AS usucre, m.datacre AS datacre, m.objecte AS objecte , i.idincidencia AS idincidencia, i.idactuacio AS idactuacio"
					+ " FROM public.tbl_modificacioinforme m LEFT JOIN public.tbl_informeactuacio i ON m.idinforme = i.idinf"
					+ " WHERE m.idinforme = ?";		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInforme);
		ResultSet rs = pstm.executeQuery();
		InformeActuacio informeModificacio = new InformeActuacio();
		PropostaInforme propostaModificacio = null;
		Oferta ofertaModificacio = new Oferta();
		while (rs.next()) {
			informeModificacio = new InformeActuacio();
			informeModificacio.setIdInf(rs.getString("idmodificacio"));
			informeModificacio.setUsuari(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
			informeModificacio.setDataCreacio(rs.getTimestamp("datacre"));
			informeModificacio.setPropostaTecnica(getInformeModificacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), idInforme, rs.getString("idmodificacio")));
			informeModificacio.setConformeAreaEconomivaPropostaActuacio(getAutorotizacioFinanceraInformeModificacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), idInforme, rs.getString("idmodificacio")));
			informeModificacio.setAutoritzacioPropostaDespesa(getAutorotizacioDespesaModificacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), idInforme, rs.getString("idmodificacio")));
			informeModificacio.setResolucioModificacio(getResolucioModificacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), idInforme, rs.getString("idmodificacio")));
			propostaModificacio = informeModificacio.new PropostaInforme();
			propostaModificacio.setObjecte(rs.getString("objecte"));
			propostaModificacio.setTipusObra(rs.getString("tipusobra"));
			propostaModificacio.setLlicencia(rs.getBoolean("llicencia"));
			propostaModificacio.setTipusLlicencia(rs.getString("tipusLlicencia"));	
			propostaModificacio.setTermini(rs.getString("termini"));
			ofertaModificacio = new Oferta();
			ofertaModificacio.setPbase(rs.getDouble("pbase"));
			ofertaModificacio.setIva(rs.getDouble("iva"));
			ofertaModificacio.setPlic(rs.getDouble("plic"));
			ofertaModificacio.setCifEmpresa(rs.getString("cifempresa"));
			ofertaModificacio.setNomEmpresa(EmpresaCore.findEmpresa(conn,rs.getString("cifEmpresa")).getName());
			ofertaModificacio.setComentari(rs.getString("comentari"));
			informeModificacio.setPropostaInformeSeleccionada(propostaModificacio);
			informeModificacio.setOfertaSeleccionada(ofertaModificacio);
			modificacionsList.add(informeModificacio);
		}
		return modificacionsList;
	}
	
	private static String getNovaModificacio(Connection conn) throws SQLException {
		String newCode = "1";	
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		
		String sql = "SELECT idmodificacio"
					+ " FROM public.tbl_modificacioinforme"
					+ " WHERE idmodificacio like '%" + yearInString + "-MOD%'"
					+ " ORDER BY idmodificacio DESC LIMIT 1;";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		
		String prefix = "MOD";
		if (rs.next()) { //Codis nous
			String actualCode = rs.getString("idmodificacio");			
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
	
	public static Fitxer getInformeModificacio(Connection conn, String idIncidencia, String idActuacio, String idInforme, String idModificacio) throws SQLException, NamingException {
		Fitxer informeModificacio = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informeModificacio =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Modificacions/" + idModificacio + "/Informe/");
		return informeModificacio;
	}	
	
	public static Fitxer getAutorotizacioFinanceraInformeModificacio(Connection conn, String idIncidencia, String idActuacio, String idInforme, String idModificacio) throws SQLException, NamingException {
		Fitxer informeModificacio = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informeModificacio =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Modificacions/" + idModificacio + "/Conforme àrea financera/");
		return informeModificacio;
	}	

	public static Fitxer getResolucioModificacio(Connection conn, String idIncidencia, String idActuacio, String idInforme, String idModificacio) throws SQLException, NamingException {
		Fitxer informeModificacio = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informeModificacio =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Modificacions/" + idModificacio + "/Resolució/");
		return informeModificacio;
	}
		
	public static Fitxer getAutorotizacioDespesaModificacio(Connection conn, String idIncidencia, String idActuacio, String idInforme, String idModificacio) throws SQLException, NamingException {
		Fitxer informeModificacio = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informeModificacio =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Modificacions/" + idModificacio + "/Autorització Despesa modificació/");
		return informeModificacio;
	}
	
	public static void saveInformeModificacio(String idIncidencia, String idActuacio, String idInforme, String idModificacio, List<Fitxer> fitxers) throws NamingException{		
		if (!fitxers.isEmpty()) {
			String fileName = "";
			// Crear directoris si no existeixen
			 // Get the base naming context
		    Context env = (Context)new InitialContext().lookup("java:comp/env");
		    // Get a single value
			String ruta =  (String)env.lookup("ruta_base");
			File tmpFile =  new File(ruta + "/documents/" + idIncidencia);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Modificacions");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Modificacions/" + idModificacio);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Modificacions/" + idModificacio + "/Informe");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			fileName = ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Modificacions/" + idModificacio + "/Informe";
	        for(int i=0;i<fitxers.size();i++){		        	
	            Fitxer fitxer = (Fitxer) fitxers.get(i);	            
	            if (fitxer.getFitxer().getName() != "") {	
	            	File archivo_server = new File(fileName  + "/"+ fitxer.getFitxer().getName());
	               	try {
	               		fitxer.getFitxer().write(archivo_server);
	           		} catch (Exception e) {
	           			e.printStackTrace();
	           		}
	            } 
	        }
		}
	}
	public static void saveAutoritzacioDespesaModificacio(String idIncidencia, String idActuacio, String idInforme, String idModificacio, List<Fitxer> fitxers) throws NamingException{		
		if (!fitxers.isEmpty()) {
			String fileName = "";
			// Crear directoris si no existeixen
			 // Get the base naming context
		    Context env = (Context)new InitialContext().lookup("java:comp/env");
		    // Get a single value
			String ruta =  (String)env.lookup("ruta_base");
			File tmpFile =  new File(ruta + "/documents/" + idIncidencia);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Modificacions");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Modificacions/" + idModificacio);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Modificacions/" + idModificacio + "/Autorització Despesa modificació");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			fileName = ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Modificacions/" + idModificacio + "/Autorització Despesa modificació";
	        for(int i=0;i<fitxers.size();i++){		        	
	            Fitxer fitxer = (Fitxer) fitxers.get(i);	            
	            if (fitxer.getFitxer().getName() != "") {	
	            	File archivo_server = new File(fileName  + "/"+ fitxer.getFitxer().getName());
	               	try {
	               		fitxer.getFitxer().write(archivo_server);
	           		} catch (Exception e) {
	           			e.printStackTrace();
	           		}
	            } 
	        }
		}
	}
}
