package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.Actuacio;
import bean.Factura;
import bean.InformeActuacio;
import bean.User;

public class FacturaCore {
	static final String SQL_CAMPS = "idfactura, idinforme, idactuacio, nifproveidor, datafactura, concepte, import, tipusfactura,"
									+ " nombrefactura, dataentrada, usuconformador, dataconformador, notes, usucre, datacre";
	
	private static Factura initFactura(Connection conn, ResultSet rs) throws SQLException{	
		Factura factura = new Factura();
		factura.setIdFactura(rs.getString("idfactura"));
		factura.setIdInforme(rs.getString("idinforme"));	
		//factura.setInforme(InformeCore.getInformePreviInfo(conn, rs.getString("idinforme")));
		factura.setIdActuacio(rs.getString("idactuacio"));
		//factura.setActuacio(ActuacioCore.findActuacio(conn,rs.getString("idactuacio")));
		factura.setIdProveidor(rs.getString("nifproveidor"));
		factura.setDataFactura(rs.getTimestamp("datafactura"));
		factura.setConcepte(rs.getString("concepte"));
		factura.setValor(rs.getDouble("import"));
		factura.setTipusFactura(rs.getString("tipusfactura"));
		factura.setNombreFactura(rs.getString("nombrefactura"));
		factura.setDataEntrada(rs.getTimestamp("dataentrada"));
		factura.setUsuariConformador(UsuariCore.findUsuariByID(conn, rs.getInt("usuconformador")));
		factura.setDataConformacio(rs.getTimestamp("dataconformador"));
		factura.setNotes(rs.getString("notes"));
		return factura;
	}	
	
	public static void newFactura(Connection conn, Factura factura, int idUsuari) throws SQLException{
		String sql = "INSERT INTO public.tbl_factures(" + SQL_CAMPS + ")"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, localtimestamp)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, factura.getIdFactura());
		pstm.setString(2, factura.getIdInforme());
		pstm.setString(3, factura.getIdActuacio());
		pstm.setString(4, factura.getIdProveidor());
		pstm.setDate(5, new java.sql.Date(factura.getDataFactura().getTime()));
		pstm.setString(6, factura.getConcepte());
		pstm.setDouble(7, factura.getValor());
		pstm.setString(8, factura.getTipusFactura());
		pstm.setString(9, factura.getNombreFactura());
		pstm.setDate(10, new java.sql.Date(factura.getDataEntrada().getTime()));
		pstm.setInt(11, factura.getUsuariConformador().getIdUsuari());
		pstm.setDate(12, new java.sql.Date(factura.getDataConformacio().getTime()));
		pstm.setString(13, factura.getNotes());
		pstm.setInt(14, idUsuari);
		pstm.executeUpdate();
	}
	
	public static Factura getFactura(Connection conn, String idFactura) throws SQLException{
		Factura factura = new Factura();
		String sql = "SELECT " + SQL_CAMPS
			 		+ " FROM public.tbl_factures"
			 		+ " WHERE idfactura = ?";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idFactura);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			factura = initFactura(conn, rs);
		}
		return factura;
	}
	
	public static List<Factura> advancedSearchFactures(Connection conn, Date dataInici, Date dataFi, Date dataIniciIdPD, Date dataFiIdPD, String concepte, String descAct, String nombreFact, String tipoContracte, String tipoPD) throws SQLException{
		List<Factura> factures = new ArrayList<Factura>();
		String CAMPS_FACTURA = "f.idfactura AS idfactura, f.idinforme AS idinforme, f.idactuacio AS idactuacio, f.nifproveidor AS proveidorfactura, f.datafactura AS datafactura, f.concepte AS concepte, f.import AS import, f.nombrefactura AS nombrefactura, f.dataentrada AS dataentrada, f.usuconformador AS usuconformador,  f.dataconformador AS dataconformador, f.notes AS notesfactura";
		String CAMPS_INFORME = "i.usucre AS usuariinforme, i.datacre AS datacreacioinforme, i.usuaprovacio AS usuaprovacioinforme, i.dataaprovacio AS dataaprovacioinforme, i.notes AS notesinforme, i.tipo AS tipocontracte, i.expcontratacio AS expcontratacio, i.datapd AS datapd, i.tipopd AS tipopd";
		String CAMPS_ACTUACIO = "a.descripcio AS descactuacio, a.usucre AS usucreactuacio, a.datacre AS datacreactuacio, a.dataaprovacio AS dataaprovacioactuacio, a.idcentre AS idcentre, a.notes AS notesactuacio";
		String sql = "SELECT " + CAMPS_FACTURA + ", " + CAMPS_INFORME + ", " + CAMPS_ACTUACIO 
		 		+ " FROM public.tbl_factures f LEFT JOIN public.tbl_informeactuacio i ON f.idinforme = i.idinf"
		 		+ " LEFT JOIN public.tbl_actuacio a ON i.idactuacio = a.id";
		System.out.println("aaa: " + descAct);
		boolean primeraCondicio = true;
		if (dataInici != null) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE datafactura >= ?";
			}			
		}
		if (dataFi != null) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE datafactura <= ?";
			} else {
				sql += " and datafactura <= ?";
			}			
		}
		if (dataIniciIdPD != null) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE datapd >= ?";
			} else {
				sql += " and datapd >= ?";
			}			
		}
		if (dataFiIdPD != null) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE datapd <= ?";
			} else {
				sql += " and datapd <= ?";
			}			
		}
		if (concepte != null && !concepte.isEmpty()) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE concepte like ?";
			} else {
				sql += " and concepte like ?";
			}			
		}
		if (descAct != null && !descAct.isEmpty()) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE a.descripcio like ?";
			} else {
				sql += " and a.descripcio like ?";
			}			
		}
		if (nombreFact != null && !nombreFact.isEmpty()) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE nombrefactura like ?";
			} else {
				sql += " and nombrefactura like ?";
			}			
		}
		if (tipoContracte != null && ! tipoContracte.equals("-1")) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE i.tipo like ?";
			} else {
				sql += " and i.tipo like ?";
			}			
		}
		if (tipoPD != null && ! tipoPD.equals("-1")) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE tipopd like ?";
			} else {
				sql += " and tipopd like ?";
			}			
		}
		
		
		PreparedStatement pstm = conn.prepareStatement(sql);	
		int contVars = 1;
		if (dataInici != null) {
			pstm.setDate(contVars, new java.sql.Date(dataInici.getTime()));
			contVars += 1;
		}
		if (dataFi != null) {
			pstm.setDate(contVars, new java.sql.Date(dataFi.getTime()));
			contVars += 1;
		}
		if (dataIniciIdPD != null) {
			pstm.setDate(contVars, new java.sql.Date(dataIniciIdPD.getTime()));
			contVars += 1;
		}
		if (dataFiIdPD != null) {
			pstm.setDate(contVars, new java.sql.Date(dataFiIdPD.getTime()));
			contVars += 1;
		}
		if (concepte != null && !concepte.isEmpty()) {
			pstm.setString(contVars, "%" + concepte + "%");
			contVars += 1;
		}
		if (descAct != null && !descAct.isEmpty()) {
			pstm.setString(contVars, "%" + descAct + "%");
			contVars += 1;
		}
		if (nombreFact != null && !nombreFact.isEmpty()) {
			pstm.setString(contVars, "%" + nombreFact + "%");
			contVars += 1;
		}
		if (tipoContracte != null && ! tipoContracte.equals("-1")) {
			pstm.setString(contVars, "%" + tipoContracte + "%");
			contVars += 1;
		}
		if (tipoPD != null && ! tipoPD.equals("-1")) {
			pstm.setString(contVars, "%" + tipoPD + "%");
			contVars += 1;
		}
		System.out.println(pstm.toString());
		ResultSet rs = pstm.executeQuery();	
		Factura factura = new Factura();
		InformeActuacio informe = new InformeActuacio();
		Actuacio actuacio = new Actuacio();
		while (rs.next()) {
			factura = new Factura();
			factura.setIdFactura(rs.getString("idfactura"));
			factura.setIdInforme(rs.getString("idinforme"));	
			factura.setIdActuacio(rs.getString("idactuacio"));
			factura.setIdProveidor(rs.getString("proveidorfactura"));
			factura.setDataFactura(rs.getTimestamp("datafactura"));
			factura.setConcepte(rs.getString("concepte"));
			factura.setValor(rs.getDouble("import"));			
			factura.setNombreFactura(rs.getString("nombrefactura"));
			factura.setDataEntrada(rs.getTimestamp("dataentrada"));
			factura.setUsuariConformador(UsuariCore.findUsuariByID(conn, rs.getInt("usuconformador")));
			factura.setDataConformacio(rs.getTimestamp("dataconformador"));
			factura.setNotes(rs.getString("notesfactura"));
			
			informe = new InformeActuacio();
			informe.setIdInf(rs.getString("idinforme"));	
			User usuari = UsuariCore.findUsuariByID(conn, rs.getInt("usuariinforme"));
			informe.setUsuari(usuari);
			informe.setDataCreacio(rs.getTimestamp("datacreacioinforme"));
			informe.setPropostaInformeSeleccionada(InformeCore.getPropostaSeleccionada(conn, rs.getString("idinforme")));
			informe.setPartida(CreditCore.getPartidaInforme(conn, rs.getString("idinforme")));	
			informe.setUsuariAprovacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuaprovacioinforme")));
			informe.setDataAprovacio(rs.getTimestamp("dataaprovacioinforme"));
			informe.setNotes(rs.getString("notesinforme"));			
			informe.setOfertaSeleccionada(OfertaCore.findOfertaSeleccionada(conn, rs.getString("idinforme")));			
			informe.setTipo(rs.getString("tipocontracte"));
			informe.setExpcontratacio(rs.getString("expcontratacio"));
			informe.setDataPD(rs.getTimestamp("datapd"));
			informe.setTipoPD(rs.getString("tipopd"));
			factura.setInforme(informe);
			
			actuacio = new Actuacio();			
			actuacio.setReferencia(rs.getString("idactuacio"));
			actuacio.setDescripcio(rs.getString("descactuacio"));		
			actuacio.setDataCreacio(rs.getTimestamp("datacreactuacio"));
			actuacio.setIdUsuariCreacio(rs.getInt("usucreactuacio"));		
			actuacio.setIdCentre(rs.getString("idcentre"));
			actuacio.setNomCentre(CentreCore.nomCentreComplet(conn, rs.getString("idcentre")));				
			actuacio.setDataAprovacio(rs.getTimestamp("dataaprovacioactuacio"));		
			actuacio.setNotes(rs.getString("notesactuacio"));
			factura.setActuacio(actuacio);
			
			factures.add(factura);
		}
		return factures;
	}
	
	public static List<Factura> searchFactures(Connection conn, Date dataInici, Date dataFi) throws SQLException{
		List<Factura> factures = new ArrayList<Factura>();
		String sql = "SELECT " + SQL_CAMPS
			 		+ " FROM public.tbl_factures"
			 		+ " WHERE datafactura >= ? and datafactura <= ?";	 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setDate(1, new java.sql.Date(dataInici.getTime()));
		pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
		ResultSet rs = pstm.executeQuery();		
		while (rs.next()) {
			factures.add(initFactura(conn, rs));
		}
		return factures;
	}

	public static List<Factura> getFacturesActuacio(Connection conn, String idActuacio) throws SQLException {
		List<Factura> factures = new ArrayList<Factura>();
		String sql = "SELECT " + SQL_CAMPS
			 		+ " FROM public.tbl_factures"
			 		+ " WHERE idactuacio = ?";	 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, idActuacio);
		ResultSet rs = pstm.executeQuery();		
		while (rs.next()) {
			factures.add(initFactura(conn, rs));
		}
		return factures;
	}
	
	public static List<Factura> getFacturesInforme(Connection conn, String idInforme) throws SQLException {
		List<Factura> factures = new ArrayList<Factura>();
		String sql = "SELECT " + SQL_CAMPS
			 		+ " FROM public.tbl_factures"
			 		+ " WHERE idinforme = ?";	 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, idInforme);
		ResultSet rs = pstm.executeQuery();		
		while (rs.next()) {
			factures.add(initFactura(conn, rs));
		}
		return factures;
	}
	
	public static String getNewCode(Connection conn) throws SQLException {
		String code = "1";
		String sql = "SELECT idfactura, datafactura"
					+ " FROM public.tbl_factures"
					+ " WHERE idfactura like '%FA%'"
					+ " ORDER BY datafactura DESC, idfactura DESC LIMIT 1;";	
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		String prefix = "FA";
		if (rs.next()) { //Codis nous
			String actualCode = rs.getString("idfactura");			
			int num = Integer.valueOf(actualCode.split("-")[2]);
			String numFormatted = String.format("%04d", num + 1);
			code = yearInString + "-" + prefix + "-" + numFormatted;
		} else { //Codis antics
			int num = 0;		
			String numFormatted = String.format("%04d", num + 1);
			code = yearInString + "-" + prefix + "-" + numFormatted;
		}
		return code;
	}
}
