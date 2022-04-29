package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.AulesModulars;

public class AulesModularsCore {
	private static final String SQL_CAMPS = "idinf, datalimit, importmensual, idinfautoritzat, noautoritzat, actiu";
	
	private static AulesModulars initAulaModular(Connection conn, ResultSet rs) {
		AulesModulars aula = new AulesModulars();
		try {
			aula.setInforme(InformeCore.getInformePreviInfo(conn, rs.getString("idinf")));
			aula.setDataLimitContracte(rs.getTimestamp("datalimit"));
			aula.setImportPrevist(rs.getDouble("importmensual"));
			if (!rs.getString("idinfautoritzat").equals("-1")) aula.setInformeAutoritzacio(InformeCore.getInformePreviInfo(conn, rs.getString("idinfautoritzat")));
			if (!rs.getString("noautoritzat").equals("-1"))aula.setNoAutoritzada(InformeCore.getInformePreviInfo(conn, rs.getString("noautoritzat")));
			aula.setDarreraFactura(findDarreraFactura(conn, aula));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return aula;
	}
	
	public static AulesModulars getAulaModular(Connection conn, String referencia) {
		AulesModulars aula = new AulesModulars();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_aulesmodulars"
				+ " WHERE idinf = ?";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);	
			pstm.setString(1, referencia);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {		
				aula = initAulaModular(conn, rs); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aula;
	}
	
	public static List<AulesModulars> getAulesModulars(Connection conn) {		 
		List<AulesModulars> list = new ArrayList<AulesModulars>();
		
		String sql = "SELECT " + SQL_CAMPS
					+ " FROM public.tbl_aulesmodulars"
					+ " WHERE actiu = true";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);	
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {		
				AulesModulars aula = initAulaModular(conn, rs); 
				list.add(aula);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static void actualitzarAulaModula(Connection conn, AulesModulars aula) {
		String sql = "UPDATE public.tbl_aulesmodulars"
				+ " SET datalimit = ?, importmensual = ?, idinfautoritzat=?, noautoritzat=?"
				+ " WHERE idinf=?;";		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			if (aula.getDataLimitContracte() != null) {
				pstm.setDate(1, new java.sql.Date(aula.getDataLimitContracte().getTime()));
			} else {
				 pstm.setDate(1, null);
			}
			pstm.setDouble(2, aula.getImportPrevist());
			if (aula.getInformeAutoritzacio().getIdIncidencia() != "-1") {
				pstm.setString(3, aula.getInformeAutoritzacio().getIdInf());
			} else {
				pstm.setString(3, null);
			}
			if (aula.getNoAutoritzada().getIdIncidencia() != "-1") {
				pstm.setString(4, aula.getNoAutoritzada().getIdInf());
			} else {
				pstm.setString(4, null);
			}			
			pstm.setString(5, aula.getInforme().getIdInf());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void novaAulaModula(Connection conn, AulesModulars aula) {
		String sql = "INSERT INTO public.tbl_aulesmodulars(idinf, datalimit, importmensual, idinfautoritzat, noautoritzat, actiu)"
				+ " VALUES (?, ?, ?, '-1', '-1', true);";		 
		PreparedStatement pstm = null; 
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, aula.getInforme().getIdInf());
			if (aula.getDataLimitContracte() != null) {
				pstm.setDate(2, new java.sql.Date(aula.getDataLimitContracte().getTime()));
			} else {
				 pstm.setDate(2, null);
			}
			pstm.setDouble(3, aula.getImportPrevist());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void anularAulaModula(Connection conn, String idInforme) {
		String sql = "UPDATE public.tbl_aulesmodulars"
				+ " SET actiu=false"
				+ " WHERE idinf=?;";		
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
	
	public static String findDarreraFactura(Connection conn, AulesModulars aula) {
		String darreraFactura = "";
		if (aula.getInformeAutoritzacio() != null) {
			String expedient = aula.getInforme().getExpcontratacio().getExpContratacio();
			if (expedient.equals("-1")) expedient = aula.getInforme().getIdInf();
			darreraFactura = getDarreraFacturaRegulacio(conn, aula.getInformeAutoritzacio().getIdInf(), expedient);
		} else if (aula.getNoAutoritzada() != null) {
			String expedient = aula.getInforme().getExpcontratacio().getExpContratacio();
			if (expedient.equals("-1")) expedient = aula.getInforme().getIdInf();
			darreraFactura = getDarreraFacturaRegulacio(conn, aula.getNoAutoritzada().getIdInf(), aula.getInforme().getExpcontratacio().getExpContratacio());
		} else {
			darreraFactura = getDarreraFactura(conn, aula.getInforme().getIdInf());
		}
		return darreraFactura;
	}

	public static String getDarreraFactura(Connection conn, String idInf) {
		String darreraFactura = "";		
		String sql = "SELECT notes"
					+ "	FROM public.tbl_factures"
					+ "	WHERE idinforme = ? AND anulada = false"
					+ "	ORDER BY datafactura DESC";
		
		PreparedStatement pstm;
		try {			
			pstm = conn.prepareStatement(sql);	
			pstm.setString(1, idInf);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {		
				darreraFactura += rs.getString("notes") + "<br>";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return darreraFactura;
	}
	
	public static String getDarreraFacturaRegulacio(Connection conn, String idInf, String expedient) {
		String darreraFactura = "";		
		String sql = "SELECT notes"
					+ "	FROM public.tbl_factures"
					+ "	WHERE idinforme = ? AND notes LIKE ? AND anulada = false"
					+ "	ORDER BY datafactura DESC";
		
		PreparedStatement pstm;
		try {			
			pstm = conn.prepareStatement(sql);	
			pstm.setString(1, idInf);
			pstm.setString(2, "%Exp:" + expedient + "%");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {		
				darreraFactura += rs.getString("notes") + "<br>";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return darreraFactura;
	}
}
