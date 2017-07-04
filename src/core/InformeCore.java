package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.InformeActuacio;
import bean.InformeActuacio.PropostaInforme;
import bean.User;
import utils.Fitxers;
import utils.Fitxers.Fitxer;

public class InformeCore {
	
	private static InformeActuacio initInforme(Connection conn, ResultSet rs) throws SQLException {
		InformeActuacio informe = new InformeActuacio();
		informe.setIdInf(rs.getString("idinf"));
		informe.setIdTasca(rs.getInt("idtasca"));
		informe.setIdIncidencia(rs.getString("idincidencia"));
		informe.setIdActuacio(rs.getString("idactuacio"));		
		User usuari = UsuariCore.findUsuariByID(conn, rs.getInt("usucre"));
		informe.setUsuari(usuari);
		informe.setDataCreacio(rs.getTimestamp("datacre"));
		informe.setAdjunts(utils.Fitxers.ObtenirFitxers(rs.getString("idincidencia"), rs.getString("idactuacio"), "Informe Previ", rs.getString("idtasca"),""));
		informe.setLlistaPropostes(getPropostesInforme(conn, rs.getString("idinf")));
		informe.setPropostaInformeSeleccionada(getPropostaSeleccionada(conn, rs.getString("idinf")));
		informe.setPartida(CreditCore.getPartidaInforme(conn, rs.getString("idinf")));	
		informe.setComentariPartida(CreditCore.getComentariPartida(conn, rs.getString("idinf")));
		informe.setUsuariCapValidacio(UsuariCore.findUsuariByID(conn, rs.getInt("usucapvalidacio")));
		informe.setDataCapValidacio(rs.getTimestamp("datacapvalidacio"));
		informe.setComentariCap(rs.getString("comentaricap"));
		informe.setUsuariAprovacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuaprovacio")));
		informe.setDataAprovacio(rs.getTimestamp("dataaprovacio"));
		informe.setNotes(rs.getString("notes"));
		informe.setLlistaOfertes(OfertaCore.findOfertes(conn, rs.getString("idinf")));
		informe.setOfertaSeleccionada(OfertaCore.findOfertaSeleccionada(conn, rs.getString("idinf")));
		if (OfertaCore.findOfertaSeleccionada(conn, rs.getString("idinf")) != null) {
			informe.setLlistaFactures(FacturaCore.getFacturesInforme(conn, rs.getString("idinf")));
		}
		informe.setDataRebujada(rs.getTimestamp("datapartidarebujada"));
		informe.setPartidaRebutjadaMotiu(rs.getString("motiupartidarebujada"));
		informe.setTipo(rs.getString("tipo"));
		informe.setExpcontratacio(rs.getString("expcontratacio"));
		informe.setDataPD(rs.getTimestamp("datapd"));
		informe.setTipoPD(rs.getString("tipopd"));
		informe.setPropostaActuacio(getPropostaActuacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
		informe.setVistiplauPropostaActuacio(getVisiplauPropostaActuacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
		informe.setConformeAreaEconomivaPropostaActuacio(getConformeAreaEconomivaPropostaActuacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
		informe.setAutoritzacioPropostaAutoritzacio(getAutoritzacioPropostaActuacio(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
		informe.setPropostaTecnica(getPropostaTecnica(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
		informe.setAutoritzacioPropostaDespesa(getAutoritzacioPropostaDespesa(conn, rs.getString("idincidencia"), rs.getString("idactuacio"), rs.getString("idinf")));
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
		proposta.setVec(rs.getDouble("vec"));
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
		pstm.setString(4, informe.getIdActuacio());
		pstm.setInt(5, idUsuari);
		pstm.executeUpdate();
		
		for (PropostaInforme proposta: informe.getLlistaPropostes()) {
			novaProposta(conn, proposta, idNouInforme);
		}
		
		return idNouInforme;
	}
	public static void novaProposta(Connection conn, PropostaInforme proposta, String idInforme) throws SQLException {		
		String sql = "INSERT INTO public.tbl_propostesinforme(idproposta, idinf, objecte, tipusobra, llicencia, tipusllicencia, contracte, vec, iva, plic, termini, comentari, seleccionada)"
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
		pstm.setDouble(8, proposta.getVec());
		pstm.setDouble(9, proposta.getIva());
		pstm.setDouble(10, proposta.getPlic());
		pstm.setString(11, proposta.getTermini());
		pstm.setString(12, proposta.getComentari());
		pstm.executeUpdate();
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
					+ " SET objecte=?, tipusobra=?, llicencia=?, tipusllicencia=?, contracte=?, vec=?, iva=?, plic=?, termini=?, comentari=?"
					+ " WHERE idproposta=?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, proposta.getObjecte());
		pstm.setString(2, proposta.getTipusObra());
		pstm.setBoolean(3, proposta.isLlicencia());
		pstm.setString(4, proposta.getTipusLlicencia());
		pstm.setBoolean(5, proposta.isContracte());
		pstm.setDouble(6, proposta.getVec());
		pstm.setDouble(7, proposta.getIva());
		pstm.setDouble(8, proposta.getPlic());
		pstm.setString(9, proposta.getTermini());
		pstm.setString(10, proposta.getComentari());
		pstm.setString(11, proposta.getIdProposta());
		pstm.executeUpdate();
	}
	
	public static InformeActuacio getInformePrevi(Connection conn, String idInforme) throws SQLException {
		InformeActuacio informePrevi = new InformeActuacio();
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, expcontratacio, datapd, tipopd"
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idinf = ?";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInforme);	
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			informePrevi = initInforme(conn, rs);
		}
		return informePrevi;
	}
	
	public static InformeActuacio getInformePreviInfo(Connection conn, String idInforme) throws SQLException {
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
	
	public static InformeActuacio getInformeTasca(Connection conn, int idTasca) throws SQLException {
		InformeActuacio informe = new InformeActuacio();
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, expcontratacio, datapd, tipopd" 
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idtasca = ?"
					+ " ORDER BY idinf;";		 
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idTasca);	
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) informe = initInforme(conn, rs);	
		return informe;
	}
	
	public static Fitxer getPropostaActuacio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException {
		Fitxer PA = new Fitxer();
		PA =  utils.Fitxers.ObtenirFitxer(Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Proposta actuació/");
		return PA;
	}
	
	public static Fitxer getVisiplauPropostaActuacio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException {
		Fitxer VistiplauPA = new Fitxer();
		VistiplauPA = utils.Fitxers.ObtenirFitxer(Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Vistiplau Cap/");
		return VistiplauPA;
	}
	
	public static Fitxer getConformeAreaEconomivaPropostaActuacio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException {
		Fitxer VistiplauPA = new Fitxer();
		VistiplauPA = utils.Fitxers.ObtenirFitxer(Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Conforme àrea financera/");
		return VistiplauPA;
	}
	
	public static Fitxer getAutoritzacioPropostaActuacio(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException {
		Fitxer AutoritzacioPA = new Fitxer();
		AutoritzacioPA =  utils.Fitxers.ObtenirFitxer(Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Autorització Proposta d'actuació/");
		return AutoritzacioPA;
	}	
	
	public static Fitxer getPropostaTecnica(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException {
		Fitxer PT = new Fitxer();
		PT =  utils.Fitxers.ObtenirFitxer(Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Proposta tècnica/");
		return PT;
	}
	
	public static Fitxer getAutoritzacioPropostaDespesa(Connection conn, String idIncidencia, String idActuacio, String idInforme) throws SQLException {
		Fitxer PT = new Fitxer();
		PT =  utils.Fitxers.ObtenirFitxer(Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/Autorització  Proposta despesa/");
		return PT;
	}
	
	public static List<InformeActuacio> getInformesActuacio(Connection conn, String idActuacio) throws SQLException {
		 List<InformeActuacio> informes = new ArrayList<InformeActuacio>();
		 String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes, datapartidarebujada, motiupartidarebujada, tipo, expcontratacio, datapd, tipopd" 
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idactuacio = ?"
					+ " ORDER BY idinf DESC;";		 
		
		 PreparedStatement pstm = conn.prepareStatement(sql);
		 pstm.setString(1, idActuacio);	
		 ResultSet rs = pstm.executeQuery();
		 while (rs.next()) {
			 informes.add(initInforme(conn, rs));			
		 }
		
		 return informes;
	}
	
	public static List<PropostaInforme> getPropostesInforme(Connection conn, String idInf) throws SQLException {
		List<PropostaInforme> propostes = new ArrayList<PropostaInforme>();
		String sql = "SELECT idproposta, idinf, objecte, tipusobra, llicencia, tipusllicencia, contracte, vec, iva, plic, termini, comentari, seleccionada"
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
		String sql = "SELECT idproposta, idinf, objecte, tipusobra, llicencia, tipusllicencia, contracte, vec, iva, plic, termini, comentari, seleccionada"
				+ " FROM public.tbl_propostesinforme"
				+ " WHERE idinf = ? AND seleccionada='true'";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInf);	
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) 	proposta = initProposta(rs);		
		return proposta;
	}
	
	public static void validacioCapInforme(Connection conn, String idInf, int idUsuari, String comentariCap) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
					+ " SET usucapvalidacio=?, datacapvalidacio=localtimestamp, comentaricap=?"
					+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);	
		pstm.setString(2, comentariCap);	
		pstm.setString(3, idInf);
		pstm.executeUpdate();
	}
	
	public static void aprovacioInforme(Connection conn, String idInf, int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
					+ " SET usuaprovacio=?, dataaprovacio=localtimestamp"
					+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);	
		pstm.setString(2, idInf);
		pstm.executeUpdate();
	}
	
	private static String idNouInforme(Connection conn) throws SQLException{
		String newCode = "1";
		
		String sql = "SELECT idinf, datacre"
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idinf like '%INF%'"
					+ " ORDER BY idinf DESC LIMIT 1;";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
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
		String sql = "SELECT idproposta"
					+ " FROM public.tbl_propostesinforme"
					+ " WHERE idproposta like '%PD%'"
					+ " ORDER BY idproposta DESC LIMIT 1;";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
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
}
