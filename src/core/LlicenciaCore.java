package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;

import bean.Llicencia;

public class LlicenciaCore {
	static final String SQL_CAMPS = "codi, expcontratacio, tipus, taxa, observacio, datapagada, importatib, ico, datasolicitud, dataconcesio";
	
	private static Llicencia initLlicencia(Connection conn, ResultSet rs) throws SQLException, NamingException{
		Llicencia llicencia = new Llicencia();
		llicencia.setCodi(rs.getString("codi"));
		llicencia.setCodiExpedient(rs.getString("expcontratacio"));
		llicencia.setTipus(rs.getString("tipus"));
		llicencia.setTaxa(rs.getDouble("taxa"));
		llicencia.setIco(rs.getDouble("ico"));
		llicencia.setValorATIB(rs.getDouble("importatib"));
		llicencia.setObservacio(rs.getString("observacio"));
		llicencia.setPeticio(rs.getTimestamp("datasolicitud"));
		llicencia.setConcesio(rs.getTimestamp("dataconcesio"));
		llicencia.setPagament(rs.getTimestamp("datapagada"));
		return llicencia;
	}
	
	public static List<Llicencia> getLlicencies(Connection conn, String estat, String tipus) throws SQLException, NamingException {
		List<Llicencia> llicencies = new ArrayList<Llicencia>();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_llicencia";		
		boolean primeraCondicio = true;
		if (!tipus.isEmpty()) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE tipus = ?";
			}			
		}
		if (!estat.isEmpty()) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE";
			} else {
				sql += " AND";
			}	
			if ("pendent".equals(estat)) {
				sql += " datasolicitud IS NULL";
			}
			if ("solicitad".equals(estat)) {
				sql += " datasolicitud IS NOT NULL AND dataconcesio IS NULL";
			}
			if ("concedida".equals(estat)) {
				sql += " dataconcesio IS NOT NULL AND datapagada IS NULL";
			}
			if ("pagada".equals(estat)) {
				sql += " datapagada IS NOT NULL";
			}
		}
		
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);	
		
		int contVars = 1;
		if (!tipus.isEmpty()) {
			pstm.setString(contVars, tipus);
			contVars += 1;
		}		
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {		
			llicencies.add(initLlicencia(conn, rs));
		}
		return llicencies;
	}
	
	public static Llicencia findLlicencia(Connection conn, String codi) throws SQLException, NamingException {
		Llicencia llicencia = new Llicencia();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_llicencia"
				+ " WHERE codi = ?";					
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);	
		pstm.setString(1, codi);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) llicencia = initLlicencia(conn, rs);
		return llicencia;
	}
	
	public static Llicencia findLlicenciaExpedient(Connection conn, String expContratacio) throws SQLException, NamingException {
		Llicencia llicencia = new Llicencia();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_llicencia"
				+ " WHERE expcontratacio = ?";					
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);	
		pstm.setString(1, expContratacio);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) llicencia = initLlicencia(conn, rs);
		return llicencia;
	}
	
	public static void updateLlicencia(Connection conn, Llicencia llicencia) throws SQLException {
		String sql = "UPDATE public.tbl_llicencia"
					+ " SET taxa=?, observacio=?, datapagada=?, ico=?, datasolicitud=?, dataconcesio=?"
					+ " WHERE codi = ?";	
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);	
		pstm.setDouble(1, llicencia.getTaxa());
		pstm.setString(2, llicencia.getObservacio());
		if (llicencia.getPagament() != null) {
			pstm.setDate(3, new java.sql.Date(llicencia.getPagament().getTime()));
		} else {
			pstm.setDate(3, null);
		}
		pstm.setDouble(4, llicencia.getIco());
		if (llicencia.getPeticio() != null) {
			pstm.setDate(5, new java.sql.Date(llicencia.getPeticio().getTime()));
		} else {
			pstm.setDate(5, null);
		}
		if (llicencia.getConcesio() != null) {
			pstm.setDate(6, new java.sql.Date(llicencia.getConcesio().getTime()));
		} else {
			pstm.setDate(6, null);
		}
		pstm.setString(7, llicencia.getCodi());
		pstm.executeUpdate();
	}
	
	public static void novaLlicencia(Connection conn, String expContractacio, String tipus) throws SQLException {
		String sql = "INSERT INTO public.tbl_llicencia(codi, expcontratacio, tipus, datacre)"
					+ " VALUES (?, ?, ?, localtimestamp);";
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);	
		pstm.setString(1, getNouCodi(conn));
		pstm.setString(2, expContractacio);
		pstm.setString(3, tipus);
		pstm.executeUpdate();
	}
	
	private static String getNouCodi(Connection conn) throws SQLException {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		String code = "1";
		String sql = "SELECT codi, datacre"
					+ " FROM public.tbl_llicencia"
					+ " WHERE codi like '%" + yearInString + "-LLI%'"
					+ " ORDER BY codi DESC, datacre DESC LIMIT 1;";	
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();		
		String prefix = "LLI";
		if (rs.next()) { //Codis nous
			String actualCode = rs.getString("codi");			
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
