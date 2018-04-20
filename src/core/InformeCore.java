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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import bean.Actuacio;
import bean.AssignacioCredit;
import bean.Centre;
import bean.Expedient;
import bean.InformeActuacio;
import bean.InformeActuacio.IncidenciaGarantia;
import bean.InformeActuacio.PropostaInforme;
import bean.Instalacions;
import bean.Oferta;
import bean.User;
import utils.Fitxers;
import utils.Fitxers.Fitxer;

public class InformeCore {
	
	private static InformeActuacio initInforme(Connection conn, ResultSet rs, boolean ambDocuments) throws SQLException, NamingException {
		InformeActuacio informe = new InformeActuacio();
		informe.setIdInf(rs.getString("idinf"));
		informe.setEstat(rs.getString("estat"));
		informe.setIdTasca(rs.getInt("idtasca"));
		informe.setIdIncidencia(rs.getString("idincidencia"));
		informe.setActuacio(ActuacioCore.findActuacio(conn, rs.getString("idactuacio")));	
		User usuari = UsuariCore.findUsuariByID(conn, rs.getInt("usucre"));
		informe.setUsuari(usuari);
		informe.setDataCreacio(rs.getTimestamp("datacre"));
		informe.setLlistaPropostes(getPropostesInforme(conn, rs.getString("idinf")));
		informe.setPropostaInformeSeleccionada(getPropostaSeleccionada(conn, rs.getString("idinf")));
		informe.setAssignacioCredit(CreditCore.getPartidaInforme(conn, rs.getString("idinf")));	
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
		informe.setPublicacioBOIB(rs.getTimestamp("databoib"));
		informe.setLlistaIncidenciesGarantia(getIncidenciesGarantia(conn, rs.getString("idinf")));
		informe.setRecursAdministratiu(rs.getString("recursadministratiu"));
		informe.setInstalacions(getInstalacions(conn, rs.getString("idinf")));
		informe.setAutoritzacioPropostaAutoritzacio(getAutoritzacioPropostaActuacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
		informe.setMemoriaOrdreInici(getMemoriaOrdreIniciSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
		informe.setAutoritzacioPropostaDespesa(getAutoritzacioPropostaDespesa(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
		informe.setContracteSignat(getContracteSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
		
		if (ambDocuments) {
			informe.setInformesPrevis(getInformesPrevis(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			List<Fitxer> adjunts = getDocumentacioAltre(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf"));
			adjunts.addAll(utils.Fitxers.ObtenirFitxers(rs.getString("idincidencia"), rs.getString("idactuacio"), "Informe Previ", rs.getString("idtasca"),""));			
			informe.setAdjunts(adjunts);
			informe.setDocTecnica(getDocumentacioTecnia(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setPropostaActuacio(getPropostaActuacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setVistiplauPropostaActuacio(getVisiplauPropostaActuacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setInformeSupervisio(getInformeSupervisio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setConformeAreaEconomivaPropostaActuacio(getConformeAreaEconomivaPropostaActuacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setAutoritzacioConsellDeGovern(getAutoritzacioConsellDeGovern(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setAutoritzacioConseller(getAutoritzacioConseller(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setPropostaTecnica(getPropostaTecnica(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setJustProcForma(getJustProcFormaSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setJustCriterisAdjudicacio(getJustCriterisAdjudicacioSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDeclaracioUrgencia(getDeclaracioUrgenciaSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setAprovacioEXPPlecsDespesa(getAprovacioEXPPlecsDespesaSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setAprovacioDispoTerrenys(getDisponibilitatTerrenysSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentBOIB(getDocumentBOIBSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setCorreuInvitacio(getCorreuInvitacioSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setRatificacioClassificacio(getRatificacioClassificacioSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setActaInici(getActaInciciSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setActaFinalitzacio(getActaFinalitzacioSignat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsAltresPrevis(getDocumentsAltresPrevis(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsAltresLicitacio(getDocumentsAltresLicitacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsAltresExecucio(getDocumentsAltresExecucio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsAltresGarantia(getDocumentsAltresGarantia(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsAltresAutUrbanistica(getDocumentsAltresAutUrbanistica(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsRecursosAdministratius(getRecursosAdministratius(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsIntalacioBaixaTensio(getDocumentsIntalacioBaixaTensio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsIntalacioContraincendis(getDocumentsIntalacioContraincendis(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsCertificatEficienciaEnergetica(getDocumentsCertificatEficienciaEnergetica(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsIntalacioTermica(getDocumentsIntalacioTermica(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsIntalacioAscensor(getDocumentsIntalacioAscensor(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsIntalacioAlarma(getDocumentsIntalacioAlarma(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsIntalacioSubministreAigua(getDocumentsIntalacioSubministreAigua(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsPlaAutoproteccio(getDocumentsPlaAutoproteccio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsCedulaDeHabitabilitat(getDocumentsCedulaDeHabitabilitat(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setDocumentsInstalacioPetrolifera(getDocumentsInstalacioPetrolifera(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
			informe.setEntrades(RegistreCore.getEntrades(conn, rs.getString("idinf")));
			informe.setSortides(RegistreCore.getSortides(conn, rs.getString("idinf")));
			informe.setTasques(TascaCore.findTasquesInforme(conn, rs.getString("idinf"), false));
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
		proposta.setEbss(rs.getBoolean("ebss"));
		proposta.setCoordinacio(rs.getBoolean("coordinacio"));
		return proposta;
	}
	
	public static String nouInforme(Connection conn, InformeActuacio informe, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.tbl_informeactuacio(idinf, idtasca, idincidencia, idactuacio, usucre, datacre, estat)"
					+ " VALUES (?, ?, ?, ?, ?, localtimestamp, ?);";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	
		String idNouInforme = idNouInforme(conn);
		pstm.setString(1, idNouInforme);
		pstm.setInt(2, informe.getIdTasca());
		pstm.setString(3, informe.getIdIncidencia());
		pstm.setString(4, informe.getActuacio().getReferencia());
		pstm.setInt(5, idUsuari);
		pstm.setString(6, "previs");
		pstm.executeUpdate();
		System.out.println(pstm.toString());
		if  (informe.getLlistaPropostes().size() > 0) {
			for (PropostaInforme proposta: informe.getLlistaPropostes()) {
				novaProposta(conn, proposta, idNouInforme);
			}
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
		System.out.println(pstm.toString());
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
		PropostaInforme proposta = informe.new PropostaInforme();
		if (!informe.getLlistaPropostes().isEmpty() && informe.getLlistaPropostes().get(0) != null) {
			proposta = informe.getLlistaPropostes().get(0);
		}
		if (existProposta(conn, proposta.getIdProposta())) {
			System.out.println("entra a exist");
			modificarProposta(conn, proposta);				
		}else{
			novaProposta(conn,proposta,informe.getIdInf());
			System.out.println("entra a nova");
		}
		pstm.executeUpdate();
	}
	
	public static void modificarProposta(Connection conn, PropostaInforme proposta) throws SQLException {		
		String sql = "UPDATE public.tbl_propostesinforme"
					+ " SET objecte=?, tipusobra=?, llicencia=?, tipusllicencia=?, contracte=?, pbase=?, iva=?, plic=?, termini=?, comentari=?, ebss=?, coordinacio=?"
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
		pstm.setBoolean(11, proposta.isEbss());
		pstm.setBoolean(12, proposta.isCoordinacio());
		pstm.setString(13, proposta.getIdProposta());
		pstm.executeUpdate();
	}
	
	public static InformeActuacio getInformePrevi(Connection conn, String idInforme, boolean ambDocuments) throws SQLException, NamingException {
		InformeActuacio informePrevi = new InformeActuacio();
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, expcontratacio, datapd, tipopd, databoib, recursadministratiu, estat"
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
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, expcontratacio, datapd, tipopd, databoib, recursadministratiu, estat"
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
			informePrevi.setActuacio(ActuacioCore.findActuacio(conn, rs.getString("idactuacio")));
		}
		return informePrevi;
	}
	
	public static InformeActuacio getInformeTasca(Connection conn, int idTasca) throws SQLException, NamingException {
		InformeActuacio informe = new InformeActuacio();
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, expcontratacio, datapd, tipopd, databoib, recursadministratiu, estat" 
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idtasca = ?"
					+ " ORDER BY idinf;";		 
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idTasca);	
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) informe = initInforme(conn, rs, true);	
		return informe;
	}
	
	public static List<Fitxer> getInformesPrevis(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Informe prèvi/");
		return informesPrevis;
	}
	
	public static List<Fitxer> getDocumentacioTecnia(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Documentació tècnica/");
		return informesPrevis;
	}
	
	public static List<Fitxer> getDocumentacioAltre(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Altre documentació/");
		return informesPrevis;
	}
		
	public static List<Fitxer> getDocumentsAltresPrevis(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Documentació altres previs/");
		informesPrevis.addAll(RegistreCore.getArxiusAdjunts(conn, idIncidencia, idInforme, "Previs"));
		return informesPrevis;
	}		
	
	public static List<Fitxer> getDocumentsAltresLicitacio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Documentació altres licitació/");
		informesPrevis.addAll(RegistreCore.getArxiusAdjunts(conn, idIncidencia, idInforme, "Licitació"));
		return informesPrevis;
	}		
	
	public static List<Fitxer> getDocumentsAltresExecucio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Documentació altres execució/");
		informesPrevis.addAll(RegistreCore.getArxiusAdjunts(conn, idIncidencia, idInforme, "Execució"));
		return informesPrevis;
	}		
	
	public static List<Fitxer> getDocumentsAltresGarantia(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Documentació altres garantia/");
		informesPrevis.addAll(RegistreCore.getArxiusAdjunts(conn, idIncidencia, idInforme, "Garantia"));
		return informesPrevis;
	}		
	
	public static List<Fitxer> getDocumentsAltresAutUrbanistica(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Documentació altres autUrbanística/");
		informesPrevis.addAll(RegistreCore.getArxiusAdjunts(conn, idIncidencia, idInforme, "Autorització urbanística"));
		return informesPrevis;
	}		
	
	public static List<Fitxer> getRecursosAdministratius(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Recursos Administratius/");
		return informesPrevis;
	}	
	
	public static List<Fitxer> getDocumentsIntalacioBaixaTensio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Instalacio baixa tensio/");
		return informesPrevis;
	}
	
	public static List<Fitxer> getDocumentsIntalacioContraincendis(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Instalacio contraincencis/");
		return informesPrevis;
	}
	
	public static List<Fitxer> getDocumentsCertificatEficienciaEnergetica(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Certificat eficiència energètica/");
		return informesPrevis;
	}
	
	public static List<Fitxer> getDocumentsIntalacioTermica(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Instalacio termica/");
		return informesPrevis;
	}
	
	public static List<Fitxer> getDocumentsIntalacioAscensor(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Instalacio ascensor/");
		return informesPrevis;
	}
	
	public static List<Fitxer> getDocumentsIntalacioAlarma(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Instalacio alarma/");
		return informesPrevis;
	}
	
	public static List<Fitxer> getDocumentsIntalacioSubministreAigua(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Subministre aigua/");
		return informesPrevis;
	}
	
	public static List<Fitxer> getDocumentsPlaAutoproteccio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Pla autoproteccio/");
		return informesPrevis;
	}
	
	public static List<Fitxer> getDocumentsCedulaDeHabitabilitat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Cedula habitabilitat/");
		return informesPrevis;
	}
	
	public static List<Fitxer> getDocumentsInstalacioPetrolifera(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> informesPrevis = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		informesPrevis =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Instalació petrolifers/");
		return informesPrevis;
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
	
	public static Fitxer getDocumentBOIBSignat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer AutoritzacioPA = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		AutoritzacioPA =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Document BOIB/");
		return AutoritzacioPA;
	}	
	
	public static Fitxer getCorreuInvitacioSignat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer AutoritzacioPA = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		AutoritzacioPA =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Correu invitació/");
		return AutoritzacioPA;
	}	
	
	public static List<Fitxer> getRatificacioClassificacioSignat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		List<Fitxer> ratificacioClassificacio = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		ratificacioClassificacio =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Ratificació classificació/");
		return ratificacioClassificacio;
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
	
	public static Fitxer getMemoriaOrdreIniciSignat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer contracte = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		contracte =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Memòria odre inici/");
		return contracte;
	}
	
	public static Fitxer getJustProcFormaSignat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer contracte = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		contracte =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Justificació procediment i forma/");
		return contracte;
	}
	
	public static Fitxer getJustCriterisAdjudicacioSignat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer contracte = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		contracte =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Justificació criteris adjudicació/");
		return contracte;
	}
	
	public static Fitxer getDeclaracioUrgenciaSignat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer contracte = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		contracte =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Declaració urgència/");
		return contracte;
	}
	
	public static Fitxer getAprovacioEXPPlecsDespesaSignat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer contracte = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		contracte =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Aprovació expedient/");
		return contracte;
	}
	
	public static Fitxer getDisponibilitatTerrenysSignat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer contracte = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		contracte =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Disponibilitat terrenys/");
		return contracte;
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
	
	public static Fitxer getActaInciciSignat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer contracte = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		contracte =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Acta inici/");
		return contracte;
	}
	
	public static Fitxer getActaFinalitzacioSignat(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException, NamingException {
		Fitxer contracte = new Fitxer();
		// Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
		contracte =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Acta final/");
		return contracte;
	}
	
	
	
	public static List<InformeActuacio> getInformesActuacio(Connection conn, String idActuacio) throws SQLException, NamingException {
		 List<InformeActuacio> informes = new ArrayList<InformeActuacio>();
		 String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, expcontratacio, datapd, tipopd, databoib, recursadministratiu, estat" 
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idactuacio = ?"
					+ " ORDER BY idinf DESC;";		 
		
		 PreparedStatement pstm = conn.prepareStatement(sql);
		 pstm.setString(1, idActuacio);	
		 ResultSet rs = pstm.executeQuery();
		 while (rs.next()) {
			 informes.add(initInforme(conn, rs, true));			
		 }
		
		 Collections.sort(informes, new Comparator<InformeActuacio>() {
		        @Override
		        public int compare(InformeActuacio o1, InformeActuacio o2) {
		        	int order = 0;
		            if (o1.getPropostaInformeSeleccionada().getTipusObraFormat().equals("Obra Major")) {
		            	order = -2;
		            } else if (o1.getPropostaInformeSeleccionada().getTipusObraFormat().equals("Obra menor")){
		            	order = -1;
		            }
		        	return order;		        	
		        }
		    });
		 return informes;
	}
	
	public static List<PropostaInforme> getPropostesInforme(Connection conn, String idInf) throws SQLException {
		List<PropostaInforme> propostes = new ArrayList<PropostaInforme>();
		String sql = "SELECT idproposta, idinf, objecte, tipusobra, llicencia, tipusllicencia, contracte, pbase, iva, plic, termini, comentari, seleccionada, ebss, coordinacio"
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
		String sql = "SELECT idproposta, idinf, objecte, tipusobra, llicencia, tipusllicencia, contracte, pbase, iva, plic, termini, comentari, seleccionada, ebss, coordinacio"
				+ " FROM public.tbl_propostesinforme"
				+ " WHERE idinf = ? AND seleccionada='true'";
		PreparedStatement pstm = conn.prepareStatement(sql);		
		pstm.setString(1, idInf);	
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) 	proposta = initProposta(rs);		
		return proposta;
	}
	
	public static List<InformeActuacio> getInformesLlicencia(Connection conn, String estat, String tipus) throws SQLException, NamingException {
		List<InformeActuacio> informes = new ArrayList<InformeActuacio>();
		String sql = "SELECT i.idinf AS idinf, i.idtasca AS idtasca, i.idincidencia AS idincidencia, i.idactuacio AS idactuacio, i.usucre AS usucre, i.datacre AS datacre, i.usucapvalidacio AS usucapvalidacio, i.datacapvalidacio AS datacapvalidacio, i.comentaricap AS comentaricap, i.usuaprovacio AS usuaprovacio, i.dataaprovacio AS dataaprovacio, i.notes AS notes, i.datapartidarebujada AS datapartidarebujada, i.motiupartidarebujada AS motiupartidarebujada, i.tipo AS tipo, i.expcontratacio AS expcontratacio, i.datapd AS datapd, i.tipopd AS tipopd, i.databoib AS databoib, recursadministratiu, estat" 
				+ " FROM public.tbl_informeactuacio i LEFT JOIN public.tbl_llicencia l ON i.expcontratacio = l.expcontratacio"
				+ " WHERE l.codi IS NOT NULL";
		if (!tipus.isEmpty()) {
			sql += " AND l.tipus = ?";
		}
		if (!estat.isEmpty()) {
			sql += " AND";			
			if ("pendent".equals(estat)) {
				sql += " l.datasolicitud IS NULL";
			}
			if ("solicitad".equals(estat)) {
				sql += " l.datasolicitud IS NOT NULL AND l.dataconcesio IS NULL";
			}
			if ("concedida".equals(estat)) {
				sql += " l.dataconcesio IS NOT NULL AND l.datapagada IS NULL";
			}
			if ("pagada".equals(estat)) {
				sql += " l.datapagada IS NOT NULL";
			}
		}
				
		sql += " ORDER BY l.codi DESC;";		 
	
		PreparedStatement pstm = conn.prepareStatement(sql);
		int contVars = 1;
		if (!tipus.isEmpty()) {
			pstm.setString(contVars, tipus);
			contVars += 1;
		}		
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			informes.add(initInforme(conn, rs, false));			
		}
		return informes;
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
	
	public static String nouInformeBuit(Connection conn, String idActuacio, String idIncidencia) throws SQLException {
		String sql = "INSERT INTO public.tbl_informeactuacio(idinf,idactuacio,idincidencia)"
				+ " VALUES (?,?,?);";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	
		String idNouInforme = idNouInforme(conn);
		pstm.setString(1, idNouInforme);
		pstm.setString(2, idActuacio);	
		pstm.setString(3, idIncidencia);	
		pstm.executeUpdate();
		return idNouInforme;
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
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, i.datacre AS datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, i.expcontratacio AS expcontratacio, datapd, tipopd, i.databoib AS databoib, recursadministratiu, estat"
				+ " FROM public.tbl_informeactuacio i LEFT JOIN public.tbl_expedient e ON i.expcontratacio = e.expcontratacio";					
		PreparedStatement pstm;
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance(); 
		Date dataFi = cal.getTime();
		String dataFiString = df.format(dataFi);	
		boolean primeraCondicio = true;
		
		if (estat != null && !estat.isEmpty() && ! estat.equals("-1")) {	
			primeraCondicio = false;
			if ("redaccio".equals(estat)) sql+= " WHERE e.expcontratacio IS NULL"; 
			if ("iniciExpedient".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND i.databoib IS NULL AND e.dataadjudicacio IS NULL"; 
			if ("publicat".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND i.databoib IS NOT NULL AND e.datalimitprsentacio >= '" + dataFiString + "' AND e.dataadjudicacio IS NULL"; 
			if ("licitacio".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND i.databoib IS NOT NULL AND e.datalimitprsentacio < '" + dataFiString + "' AND e.dataadjudicacio IS NULL"; 	
			if ("adjudicacio".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.dataadjudicacio IS NOT NULL AND e.dataformalitzaciocontracte IS NULL"; 	
			if ("firmat".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.datarecepcio IS NULL AND (e.datainiciexecucio IS NULL OR e.datainiciexecucio > localtimestamp) AND e.dataformalitzaciocontracte IS NOT NULL"; 	
			if ("execucio".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.datainiciexecucio <= localtimestamp AND e.datarecepcio IS NULL"; 	
			if ("garantia".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.datarecepcio IS NOT NULL AND e.dataretorngarantia < localtimestamp"; 	
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
		if (contracte != null && !contracte.isEmpty() && ! contracte.equals("-1") && !"redaccio".equals(estat)) {			
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE e.contracte = '" + contracte + "'";
			}else{
				sql += " AND e.contracte = '" + contracte + "'";
			}
			
		}	
		if (!"redaccio".equals(estat) && year != null && !year.isEmpty() && ! year.equals("-1")) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE e.anyexpedient::INT = " + year;
							
			}else{
				sql += " AND e.anyexpedient::INT = " + year;
							
			}			
		}
		if ("redaccio".equals(estat) && year != null && !year.isEmpty() && ! year.equals("-1")) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE i.datacre > '01/01/" + year + "'";
							
			}else{
				sql += " AND i.datacre > '01/01/" + year + "'";
							
			}			
		}
		sql += " ORDER BY datacre DESC, expcontratacio DESC";
		System.out.println(sql);
		pstm = conn.prepareStatement(sql);	
		System.out.println(pstm.toString());
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {		
			expedientsList.add(initInforme(conn, rs, false));
		}
		return expedientsList;
	}
	
	
	public static void modificarPartida(Connection conn, InformeActuacio informe, String idPartida, int idUsuari, AssignacioCredit assignacio) throws SQLException {		
		if (idPartida.equals("-1")) {
			//Eliminam asignació anterior
			String sql = "DELETE FROM public.tbl_assignacionscredit WHERE idinf = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, informe.getIdInf());
			pstm.executeUpdate();	
		} else {
			if (informe.getAssignacioCredit() != null && informe.getAssignacioCredit().getIdAssignacio() != null && !informe.getAssignacioCredit().getIdAssignacio().isEmpty() && !informe.getAssignacioCredit().getPartida().getCodi().equals(idPartida)) {
				//Eliminam asignació anterior
				String sql = "DELETE FROM public.tbl_assignacionscredit WHERE idinf = ?";
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setString(1, informe.getIdInf());
				pstm.executeUpdate();	
				//Nova assignacio
				CreditCore.reservar(conn, idPartida, informe.getActuacio().getReferencia(), informe.getIdInf(), informe.getPropostaInformeSeleccionada().getPlic(), informe.getComentariPartida(), idUsuari);			
			}
			if (informe.getAssignacioCredit() == null || informe.getAssignacioCredit().getIdAssignacio() == null || informe.getAssignacioCredit().getIdAssignacio().isEmpty()) {
				//Nova assignacio
				CreditCore.reservar(conn, idPartida, informe.getActuacio().getReferencia(), informe.getIdInf(), informe.getPropostaInformeSeleccionada().getPlic(), informe.getComentariPartida(), idUsuari);			
			}
			CreditCore.modificarAssignacio(conn, assignacio);
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

	public static void modificarNotes(Connection conn, InformeActuacio informe, String notes) throws SQLException {		
		String sql = "UPDATE public.tbl_informeactuacio"
				+ " SET notes = ?"
				+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, notes);
		pstm.setString(2, informe.getIdInf());	
		pstm.executeUpdate();				
	}
	
	public static void modificarDataPublicacioBOIB(Connection conn, InformeActuacio informe, Date dataPublicacio) throws SQLException {
		if (informe.getPublicacioBOIB() == null || informe.getPublicacioBOIB() != dataPublicacio) {
			String sql = "UPDATE public.tbl_informeactuacio"
					+ " SET databoib = ?"
					+ " WHERE idinf=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setDate(1,  new java.sql.Date(dataPublicacio.getTime()));
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
			modificacio.setAssignacioCredit(CreditCore.getPartidaInforme(conn, rs.getString("idmodificacio")));	
			modificacio.setExpcontratacio(ExpedientCore.findExpedient(conn, rs.getString("expcontratacio")));
			modificacio.setActuacio(ActuacioCore.findActuacio(conn, rs.getString("idactuacio")));
		}
		return modificacio;
	}
	
	public static List<InformeActuacio> getMoficacionsInforme(Connection conn, String idInforme) throws SQLException, NamingException {
		List<InformeActuacio> modificacionsList = new ArrayList<InformeActuacio>();
		String sql = "SELECT m.idmodificacio AS idmodificacio, m.idinforme AS idinforme, m.tipusobra AS tipusobra, m.llicencia AS llicencia, m.tipusllicencia AS tipusllicencia, m.pbase AS pbase, m.iva AS iva, m.plic AS plic, m.cifempresa AS cifempresa, m.termini AS termini, m.comentari AS comentari, m.usucre AS usucre, m.datacre AS datacre, m.objecte AS objecte , i.idincidencia AS idincidencia, i.idactuacio AS idactuacio, i.expcontratacio AS expcontratacio, i.idinf AS idinforme"
					+ " FROM public.tbl_modificacioinforme m LEFT JOIN public.tbl_informeactuacio i ON m.idinforme = i.idinf";
		if (idInforme != null) sql += " WHERE m.idinforme = ?";		
		PreparedStatement pstm = conn.prepareStatement(sql);
		if (idInforme != null) pstm.setString(1, idInforme);
		ResultSet rs = pstm.executeQuery();
		InformeActuacio informeModificacio = new InformeActuacio();
		PropostaInforme propostaModificacio = null;
		Oferta ofertaModificacio = new Oferta();
		while (rs.next()) {
			informeModificacio = new InformeActuacio();
			informeModificacio.setExpcontratacio(ExpedientCore.findExpedient(conn, rs.getString("expcontratacio")));
			informeModificacio.setActuacio(ActuacioCore.findActuacio(conn, rs.getString("idactuacio")));
			informeModificacio.setIdInf(rs.getString("idmodificacio"));
			informeModificacio.setUsuari(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
			informeModificacio.setDataCreacio(rs.getTimestamp("datacre"));
			informeModificacio.setPropostaTecnica(getInformeModificacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinforme"), rs.getString("idmodificacio")));
			informeModificacio.setConformeAreaEconomivaPropostaActuacio(getAutorotizacioFinanceraInformeModificacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinforme"), rs.getString("idmodificacio")));
			informeModificacio.setAutoritzacioPropostaDespesa(getAutorotizacioDespesaModificacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinforme"), rs.getString("idmodificacio")));
			informeModificacio.setResolucioModificacio(getResolucioModificacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinforme"), rs.getString("idmodificacio")));
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
	
	public static void novaIncidenciaGarantia(Connection conn, String idInforme, IncidenciaGarantia incidencia) throws SQLException {
		String sql = "INSERT INTO public.tbl_incidenciagarantia(idincidencia, objecte, dataini, datafi, idinforme)"
					+ " VALUES (?, ?, ?, ?, ?);";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, getNewCodiIncidenciaGarantia(conn));
		pstm.setString(2, incidencia.getObjecte());
		if (incidencia.getDataInici() == null) {
			pstm.setDate(3, null);
		}else{
			pstm.setDate(3,  new java.sql.Date(incidencia.getDataInici().getTime()));
		}
		if (incidencia.getDataFi() == null) {
			pstm.setDate(4, null);
		}else{
			pstm.setDate(4,  new java.sql.Date(incidencia.getDataFi().getTime()));
		}
		pstm.setString(5, idInforme);		
		pstm.executeUpdate();
	}
	
	private static int getNewCodiIncidenciaGarantia(Connection conn) throws SQLException {
		int newCode = 1;
		String sql = "SELECT idincidencia"
				+ " FROM public.tbl_incidenciagarantia"
				+ " ORDER BY idincidencia desc LIMIT 1;";		
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) { //Codis nous	
			newCode = rs.getInt("idincidencia") + 1;		
		}		
		return newCode;
	}
	
	public static IncidenciaGarantia getIncidenciaGarantia(Connection conn, int idIncidencia) throws SQLException {
		IncidenciaGarantia incidencia = new InformeActuacio().new IncidenciaGarantia();
		String sql = "SELECT idincidencia, objecte, dataini, datafi"
					+ " FROM public.tbl_incidenciagarantia"
					+ " WHERE idincidencia = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idIncidencia);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			incidencia.setIdIncidencia(idIncidencia);
			incidencia.setObjecte(rs.getString("objecte"));
			incidencia.setDataInici(rs.getTimestamp("dataini"));
			incidencia.setDataFi(rs.getTimestamp("datafi"));
			incidencia.setDocumentsList(getFitxersIncidenciaGarantia(rs.getInt("idincidencia")));
		}
		return incidencia;
	}
	
	public static List<IncidenciaGarantia> getIncidenciesGarantia(Connection conn, String idInforme) throws SQLException {
		List<IncidenciaGarantia> incidenciesGarantiaList = new ArrayList<IncidenciaGarantia>();
		String sql = "SELECT idincidencia, objecte, dataini, datafi, idinforme"
					+ " FROM public.tbl_incidenciagarantia"
					+ " WHERE idinforme = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInforme);
		ResultSet rs = pstm.executeQuery();
		IncidenciaGarantia incidencia = null;
		while (rs.next()) {
			incidencia = new InformeActuacio().new IncidenciaGarantia();
			incidencia.setIdIncidencia(rs.getInt("idincidencia"));
			incidencia.setObjecte(rs.getString("objecte"));
			incidencia.setDataInici(rs.getTimestamp("dataini"));
			incidencia.setDataFi(rs.getTimestamp("datafi"));
			incidencia.setDocumentsList(getFitxersIncidenciaGarantia(rs.getInt("idincidencia")));
			incidenciesGarantiaList.add(incidencia);
		}
		return incidenciesGarantiaList;
	}
	
	public static void modificarIncidenciaGarantia(Connection conn, IncidenciaGarantia incidencia, int idIncidencia) throws SQLException {
		String sql = "UPDATE public.tbl_incidenciagarantia"
					+ " SET objecte=?, dataini=?, datafi=?"
					+ " WHERE idincidencia = ?;";	
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, incidencia.getObjecte());
		if (incidencia.getDataInici() == null) {
			pstm.setDate(2, null);
		}else{
			pstm.setDate(2,  new java.sql.Date(incidencia.getDataInici().getTime()));
		}
		if (incidencia.getDataFi() == null) {
			pstm.setDate(3, null);
		}else{
			pstm.setDate(3,  new java.sql.Date(incidencia.getDataFi().getTime()));
		}
		pstm.setInt(4, idIncidencia);
		pstm.executeUpdate();
	}
	
	public static List<Fitxers.Fitxer> getFitxersIncidenciaGarantia(int idIncidencia) {
		List<Fitxer> fitxersList = new ArrayList<Fitxer>();
		
		return fitxersList;
	}
	
	public static void tancar(Connection conn, String idInforme) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
				+ " SET datatancament=localtimestamp"
				+ " WHERE idinf = ?;";	
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInforme);
		pstm.executeUpdate();
	}
	
	public static List<InformeActuacio> getInformesResumUsuari(Connection conn, int idUsuari) throws SQLException, NamingException {
		List<InformeActuacio> informesList = new ArrayList<InformeActuacio>();
		String sql = "SELECT i.idinf AS idinforme, i.expcontratacio AS expedient, i.estat AS estat, u.nom AS nom, u.cognoms AS llinatges, p.objecte AS objecte, a.id AS idactuacio, a.descripcio AS descripcio, c.nom AS nomcentre, c.municipi AS municipicentre, c.localitat AS localitatcentre, em.nom AS nomempresa"
				+ " FROM public.tbl_informeactuacio i LEFT JOIN public.tbl_propostesinforme p ON i.idinf = p.idinf" 
				+ " LEFT JOIN public.tbl_expedient exp ON exp.expcontratacio = i.expcontratacio"
				+ " LEFT JOIN public.tbl_empresaoferta e ON e.idinforme = i.idinf"
				+ " LEFT JOIN public.tbl_empreses em ON e.cifempresa = em.cif"
				+ " LEFT JOIN public.tbl_usuaris u ON i.usucre = u.idusuari"
				+ " LEFT JOIN public.tbl_actuacio a ON i.idactuacio = a.id"
				+ " LEFT JOIN public.tbl_centres c ON a.idcentre = c.codi"
				+ " WHERE (exp.anulat = false OR exp.anulat IS NULL) AND (p.seleccionada = true OR p.seleccionada IS NULL) AND (e.seleccionada = true OR e.seleccionada IS NULL) AND NOT(i.datatancament IS NOT NULL OR (exp.expcontratacio IS NOT NULL AND exp.datarecepcio IS NOT NULL)) AND i.estat != 'acabat' AND i.usucre = ?";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);	
		ResultSet rs = pstm.executeQuery();
		InformeActuacio informe = new InformeActuacio();
		Expedient expcontratacio = new Expedient();
		User usuari = new User();
		PropostaInforme proposta = informe.new PropostaInforme();
		Actuacio actuacio = new Actuacio();
		Centre centre = new Centre();
		Oferta oferta = new Oferta();
		while (rs.next()) {
			informe.setIdInf(rs.getString("idinforme"));
			expcontratacio.setExpContratacio(rs.getString("expedient"));
			informe.setExpcontratacio(expcontratacio);
			usuari.setName(rs.getString("nom"));
			usuari.setLlinatges(rs.getString("llinatges"));
			informe.setUsuari(usuari);
			proposta.setObjecte(rs.getString("objecte"));
			informe.setPropostaInformeSeleccionada(proposta);
			centre.setNom(rs.getString("nomcentre"));
			centre.setLocalitat(rs.getString("localitatcentre"));
			centre.setMunicipi(rs.getString("municipicentre"));
			actuacio.setReferencia(rs.getString("idactuacio"));
			actuacio.setCentre(centre);
			actuacio.setDescripcio(rs.getString("descripcio"));
			informe.setActuacio(actuacio);
			oferta.setNomEmpresa(rs.getString("nomempresa"));
			informe.setOfertaSeleccionada(oferta);
			informe.setEstat(rs.getString("estat"));
			informesList.add(informe);
			informe = new InformeActuacio();
			expcontratacio = new Expedient();
			usuari = new User();
			actuacio = new Actuacio();
			proposta = informe.new PropostaInforme();
			centre = new Centre();
			oferta = new Oferta();
		}
		return informesList;
	}
	
	public static List<InformeActuacio> getInformesResumArea(Connection conn, String departament) throws SQLException {
		List<InformeActuacio> informesList = new ArrayList<InformeActuacio>();
		String sql = "SELECT i.idinf AS idinforme, i.expcontratacio AS expedient, i.estat AS estat, u.nom AS nom, u.cognoms AS llinatges, p.objecte AS objecte, a.id AS idactuacio, a.descripcio AS descripcio, c.nom AS nomcentre, c.municipi AS municipicentre, c.localitat AS localitatcentre, em.nom AS nomempresa"
					+ " FROM public.tbl_informeactuacio i LEFT JOIN public.tbl_propostesinforme p ON i.idinf = p.idinf" 
					+ " LEFT JOIN public.tbl_expedient exp ON exp.expcontratacio = i.expcontratacio"
					+ " LEFT JOIN public.tbl_empresaoferta e ON e.idinforme = i.idinf"
					+ " LEFT JOIN public.tbl_empreses em ON e.cifempresa = em.cif"
					+ " LEFT JOIN public.tbl_usuaris u ON i.usucre = u.idusuari"
					+ " LEFT JOIN public.tbl_actuacio a ON i.idactuacio = a.id"
					+ " LEFT JOIN public.tbl_centres c ON a.idcentre = c.codi"
					+ " WHERE (exp.anulat = false OR exp.anulat IS NULL) AND (p.seleccionada = true OR p.seleccionada IS NULL) AND (e.seleccionada = true OR e.seleccionada IS NULL) AND NOT(i.datatancament IS NOT NULL OR (exp.expcontratacio IS NOT NULL AND exp.datarecepcio IS NOT NULL)) AND i.estat != 'acabat' AND i.estat != 'garantia' AND departament = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, departament);	
		ResultSet rs = pstm.executeQuery();
		InformeActuacio informe = new InformeActuacio();
		Expedient expcontratacio = new Expedient();
		User usuari = new User();
		PropostaInforme proposta = informe.new PropostaInforme();
		Actuacio actuacio = new Actuacio();
		Centre centre = new Centre();
		Oferta oferta = new Oferta();
		while (rs.next()) {
			informe.setIdInf(rs.getString("idinforme"));
			expcontratacio.setExpContratacio(rs.getString("expedient"));
			informe.setExpcontratacio(expcontratacio);
			usuari.setName(rs.getString("nom"));
			usuari.setLlinatges(rs.getString("llinatges"));
			informe.setUsuari(usuari);
			proposta.setObjecte(rs.getString("objecte"));
			informe.setPropostaInformeSeleccionada(proposta);
			centre.setNom(rs.getString("nomcentre"));
			centre.setLocalitat(rs.getString("localitatcentre"));
			centre.setMunicipi(rs.getString("municipicentre"));
			actuacio.setReferencia(rs.getString("idactuacio"));
			actuacio.setCentre(centre);
			actuacio.setDescripcio(rs.getString("descripcio"));
			informe.setActuacio(actuacio);
			oferta.setNomEmpresa(rs.getString("nomempresa"));
			informe.setOfertaSeleccionada(oferta);
			informe.setEstat(rs.getString("estat"));
			informesList.add(informe);
			informe = new InformeActuacio();
			expcontratacio = new Expedient();
			usuari = new User();
			actuacio = new Actuacio();
			proposta = informe.new PropostaInforme();
			centre = new Centre();
			oferta = new Oferta();
		}
		return informesList;
	}
	
	public static List<InformeActuacio> getInformesCentres(Connection conn, String idCentre) throws SQLException {
		List<InformeActuacio> informesList = new ArrayList<InformeActuacio>();
		String sql = "SELECT i.idinf AS idinforme, i.expcontratacio AS expedient, i.estat AS estat, u.nom AS nom, u.cognoms AS llinatges, p.objecte AS objecte, a.id AS idactuacio, a.descripcio AS descripcio, c.nom AS nomcentre, c.municipi AS municipicentre, c.localitat AS localitatcentre, em.nom AS nomempresa"
				+ " FROM public.tbl_informeactuacio i LEFT JOIN public.tbl_propostesinforme p ON i.idinf = p.idinf" 
				+ " LEFT JOIN public.tbl_expedient exp ON exp.expcontratacio = i.expcontratacio"
				+ " LEFT JOIN public.tbl_empresaoferta e ON e.idinforme = i.idinf"
				+ " LEFT JOIN public.tbl_empreses em ON e.cifempresa = em.cif"
				+ " LEFT JOIN public.tbl_usuaris u ON i.usucre = u.idusuari"
				+ " LEFT JOIN public.tbl_actuacio a ON i.idactuacio = a.id"
				+ " LEFT JOIN public.tbl_centres c ON a.idcentre = c.codi"
				+ " WHERE (exp.anulat = false OR exp.anulat IS NULL) AND (p.seleccionada = true OR p.seleccionada IS NULL) AND (e.seleccionada = true OR e.seleccionada IS NULL) AND NOT(i.datatancament IS NOT NULL OR (exp.expcontratacio IS NOT NULL AND exp.datarecepcio IS NOT NULL)) AND i.estat != 'acabat' AND i.estat != 'garantia' AND u.departament IN ('obres','instalacions') AND a.idcentre = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idCentre);	
		ResultSet rs = pstm.executeQuery();
		InformeActuacio informe = new InformeActuacio();
		Expedient expcontratacio = new Expedient();
		User usuari = new User();
		PropostaInforme proposta = informe.new PropostaInforme();
		Actuacio actuacio = new Actuacio();
		Centre centre = new Centre();
		Oferta oferta = new Oferta();
		while (rs.next()) {
			informe.setIdInf(rs.getString("idinforme"));
			expcontratacio.setExpContratacio(rs.getString("expedient"));
			informe.setExpcontratacio(expcontratacio);
			usuari.setName(rs.getString("nom"));
			usuari.setLlinatges(rs.getString("llinatges"));
			informe.setUsuari(usuari);
			proposta.setObjecte(rs.getString("objecte"));
			informe.setPropostaInformeSeleccionada(proposta);
			centre.setNom(rs.getString("nomcentre"));
			centre.setLocalitat(rs.getString("localitatcentre"));
			centre.setMunicipi(rs.getString("municipicentre"));
			actuacio.setReferencia(rs.getString("idactuacio"));
			actuacio.setCentre(centre);
			actuacio.setDescripcio(rs.getString("descripcio"));
			informe.setActuacio(actuacio);
			oferta.setNomEmpresa(rs.getString("nomempresa"));
			informe.setOfertaSeleccionada(oferta);
			informe.setEstat(rs.getString("estat"));
			informesList.add(informe);
			informe = new InformeActuacio();
			expcontratacio = new Expedient();
			usuari = new User();
			actuacio = new Actuacio();
			proposta = informe.new PropostaInforme();
			centre = new Centre();
			oferta = new Oferta();
		}
		return informesList;
	}
	
	public static List<InformeActuacio> getInformesEmpresa(Connection conn, String cif) throws SQLException, NamingException {
		List<InformeActuacio> informes = new ArrayList<InformeActuacio>();
		String sql = "SELECT idinf, idtasca, idincidencia, i.idactuacio AS idactuacio, i.usucre AS usucre, i.datacre AS datacre, i.usucapvalidacio AS usucapvalidacio, i.datacapvalidacio AS datacapvalidacio, comentaricap, i.usuaprovacio AS usuaprovacio, i.dataaprovacio AS dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, expcontratacio, datapd, tipopd, databoib, recursadministratiu, estat"
					+ " FROM public.tbl_empresaoferta e LEFT JOIN public.tbl_informeactuacio i ON e.idinforme = i.idinf"
					+ " WHERE e.cifempresa = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, cif);		
		ResultSet rs = pstm.executeQuery();		
		while (rs.next()) {
			InformeActuacio informe = InformeCore.initInforme(conn, rs, false);
			informes.add(informe);
		}
		return informes;
	}
	
	public static void actualitzarInstalacions(Connection conn, String idInf, Instalacions instalacions) throws SQLException {
		String sql = "SELECT idinf"
				+ " FROM public.tbl_instalacions"
				+ " WHERE idinf = ?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, idInf);		
		ResultSet rs = pstm.executeQuery();	
		if (rs.next()) {
			sql = "UPDATE public.tbl_instalacions"
					+ " SET  baixatensioexpedient = ?, baixatensiodataoca = ?, fotovoltaicaexpedient = ?, fotovoltaicadataoca = ?, contraincendisexpedient = ?, termiquesexpedient = ?, petrolifersexpedient = ?, gasexpedient = ?, ascensorexpedient = ?, eficienciaexpedient = ?, eficienciadataregistre = ?, autoproteccioexpedient = ?, habitabilitatdatacedula = ?, petrolifersdata = ?, petrolifersinstalacio = ?, petrolifersdiposits = ?, petroliferscapacitattotal = ?"
					+ " WHERE idinf = ?;";
			pstm = conn.prepareStatement(sql);	 
			pstm.setString(1, instalacions.getExpedientBaixaTensio());	
			if (instalacions.getDataOCABaixaTensio() == null) {
				pstm.setDate(2, null);
			}else{
				pstm.setDate(2,  new java.sql.Date(instalacions.getDataOCABaixaTensio().getTime()));
			}
			pstm.setString(3, instalacions.getExpedientFotovoltaica());	
			if (instalacions.getDataOCAFotovoltaica() == null) {
				pstm.setDate(4, null);
			}else{
				pstm.setDate(4,  new java.sql.Date(instalacions.getDataOCAFotovoltaica().getTime()));
			}
			pstm.setString(5, instalacions.getExpedientContraincendis());	
			pstm.setString(6, instalacions.getExpedientTermiques());	
			pstm.setString(7, instalacions.getExpedientPetrolifers());	
			pstm.setString(8, instalacions.getExpedientGas());	
			pstm.setString(9, instalacions.getExpedientAscensor());	
			pstm.setString(10, instalacions.getExpedientEficienciaEnergetica());	
			if (instalacions.getDataRegistreEficienciaEnergetica() == null) {
				pstm.setDate(11, null);
			}else{
				pstm.setDate(11,  new java.sql.Date(instalacions.getDataRegistreEficienciaEnergetica().getTime()));
			}	
			pstm.setString(12, instalacions.getExpedientPlaAutoproteccio());	
			if (instalacions.getDataCedulaHabitabilitat() == null) {
				pstm.setDate(13, null);
			}else{
				pstm.setDate(13,  new java.sql.Date(instalacions.getDataCedulaHabitabilitat().getTime()));
			}
			if (instalacions.getDataPetrolifers() == null) {
				pstm.setDate(14, null);
			}else{
				pstm.setDate(14,  new java.sql.Date(instalacions.getDataPetrolifers().getTime()));
			}
			pstm.setString(15, instalacions.getInstalacioPetrolifers());
			pstm.setString(16, instalacions.getDipositsPetrolifers());
			pstm.setString(17, instalacions.getCapTotalPetrolifers());
			pstm.setString(18, idInf);		
			pstm.executeUpdate();	
		} else {
			sql = "INSERT INTO public.tbl_instalacions(idinf, baixatensioexpedient, baixatensiodataoca, fotovoltaicaexpedient, fotovoltaicadataoca, contraincendisexpedient, termiquesexpedient, petrolifersexpedient, gasexpedient, ascensorexpedient, eficienciaexpedient, eficienciadataregistre, autoproteccioexpedient, habitabilitatdatacedula, petrolifersdata, petrolifersinstalacio, petrolifersdiposits, petroliferscapacitattotal)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?);";
			PreparedStatement pstmInsert = conn.prepareStatement(sql);	 
			pstmInsert.setString(1, idInf);		
			pstmInsert.setString(2, instalacions.getExpedientBaixaTensio());	
			if (instalacions.getDataOCABaixaTensio() == null) {
				pstmInsert.setDate(3, null);
			}else{
				pstmInsert.setDate(3,  new java.sql.Date(instalacions.getDataOCABaixaTensio().getTime()));
			}
			pstmInsert.setString(4, instalacions.getExpedientFotovoltaica());	
			if (instalacions.getDataOCAFotovoltaica() == null) {
				pstmInsert.setDate(5, null);
			}else{
				pstmInsert.setDate(5,  new java.sql.Date(instalacions.getDataOCAFotovoltaica().getTime()));
			}
			pstmInsert.setString(6, instalacions.getExpedientContraincendis());	
			pstmInsert.setString(7, instalacions.getExpedientTermiques());	
			pstmInsert.setString(8, instalacions.getExpedientPetrolifers());	
			pstmInsert.setString(9, instalacions.getExpedientGas());	
			pstmInsert.setString(10, instalacions.getExpedientAscensor());	
			pstmInsert.setString(11, instalacions.getExpedientEficienciaEnergetica());	
			if (instalacions.getDataRegistreEficienciaEnergetica() == null) {
				pstmInsert.setDate(12, null);
			}else{
				pstmInsert.setDate(12,  new java.sql.Date(instalacions.getDataRegistreEficienciaEnergetica().getTime()));
			}	
			pstmInsert.setString(13, instalacions.getExpedientPlaAutoproteccio());	
			if (instalacions.getDataCedulaHabitabilitat() == null) {
				pstmInsert.setDate(14, null);
			}else{
				pstmInsert.setDate(14,  new java.sql.Date(instalacions.getDataCedulaHabitabilitat().getTime()));
			}
			if (instalacions.getDataPetrolifers() == null) {
				pstmInsert.setDate(15, null);
			}else{
				pstmInsert.setDate(15,  new java.sql.Date(instalacions.getDataPetrolifers().getTime()));
			}
			pstmInsert.setString(16, instalacions.getInstalacioPetrolifers());
			pstmInsert.setString(17, instalacions.getDipositsPetrolifers());
			pstmInsert.setString(18, instalacions.getCapTotalPetrolifers());			
			pstmInsert.executeUpdate();	
		}
	}
	
	public static Instalacions getInstalacions(Connection conn, String idInf) throws SQLException {
		Instalacions instalacions = new Instalacions();
		String sql = "SELECT idinf, baixatensioexpedient, baixatensiodataoca, fotovoltaicaexpedient, fotovoltaicadataoca, contraincendisexpedient, termiquesexpedient, petrolifersexpedient, gasexpedient, ascensorexpedient, eficienciaexpedient, eficienciadataregistre, autoproteccioexpedient, habitabilitatdatacedula, petrolifersdata, petrolifersinstalacio, petrolifersdiposits, petroliferscapacitattotal"
					+ " FROM public.tbl_instalacions"
					+ " WHERE idinf = ?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, idInf);		
		ResultSet rs = pstm.executeQuery();		
		while (rs.next()) {
			instalacions.setIdInf(rs.getString("idInf"));
			instalacions.setExpedientBaixaTensio(rs.getString("baixatensioexpedient"));
			instalacions.setDataOCABaixaTensio(rs.getTimestamp("baixatensiodataoca"));
			instalacions.setExpedientFotovoltaica(rs.getString("fotovoltaicaexpedient"));
			instalacions.setDataOCAFotovoltaica(rs.getTimestamp("fotovoltaicadataoca"));
			instalacions.setExpedientContraincendis(rs.getString("contraincendisexpedient"));
			instalacions.setExpedientTermiques(rs.getString("termiquesexpedient"));
			instalacions.setExpedientPetrolifers(rs.getString("petrolifersexpedient"));
			instalacions.setDataPetrolifers(rs.getTimestamp("petrolifersdata"));
			instalacions.setInstalacioPetrolifers(rs.getString("petrolifersinstalacio"));
			instalacions.setDipositsPetrolifers(rs.getString("petrolifersdiposits"));
			instalacions.setCapTotalPetrolifers(rs.getString("petroliferscapacitattotal"));
			instalacions.setExpedientGas(rs.getString("gasexpedient"));
			instalacions.setExpedientAscensor(rs.getString("ascensorexpedient"));
			instalacions.setExpedientEficienciaEnergetica(rs.getString("eficienciaexpedient"));
			instalacions.setDataRegistreEficienciaEnergetica(rs.getTimestamp("eficienciadataregistre"));
			instalacions.setExpedientPlaAutoproteccio(rs.getString("autoproteccioexpedient"));
			instalacions.setDataCedulaHabitabilitat(rs.getTimestamp("habitabilitatdatacedula"));
		}
		return instalacions;
	}
	
	public static void modificarEstat(Connection conn, String idinf, String estat) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio SET estat = ?"
				+ " WHERE idinf = ?;";
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, estat);	
		pstm.setString(2, idinf);	
		pstm.executeUpdate();		
	}
}
