package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;

import bean.Bastanteo;
import bean.Empresa;

public class BastanteosCore {
	static final String SQL_CAMPS = "ref, databastanteo, empresa, escritura, personafacultada, carrec, dataescritura, nprotocol, notari, procedencia, desti, anybastanteo";
	
	private static Bastanteo initBastanteo(Connection conn, ResultSet rs) throws SQLException, NamingException{
		Bastanteo bastanteo = new Bastanteo();
		bastanteo.setRef(rs.getString("ref"));
		bastanteo.setDatabastanteo(rs.getTimestamp("databastanteo"));
		Empresa empresa = EmpresaCore.findEmpresa(conn, rs.getString("empresa"));
		if (empresa == null) {
			empresa = new Empresa();
			empresa.setName(rs.getString("empresa"));;
		}
		bastanteo.setEmpresa(empresa);
		bastanteo.setEscritura(rs.getString("escritura"));
		bastanteo.setPersonaFacultada(rs.getString("personafacultada"));
		bastanteo.setCarrec(rs.getString("carrec"));
		bastanteo.setDataEscritura(rs.getTimestamp("dataescritura"));
		bastanteo.setNumProtocol(rs.getString("nprotocol"));
		bastanteo.setNotari(rs.getString("notari"));
		bastanteo.setProcedencia(rs.getString("procedencia"));
		bastanteo.setDesti(rs.getString("desti"));
		bastanteo.setAnyBastanteo(rs.getString("anybastanteo"));
		
		return bastanteo;
	}
	
	public static String nouBastanteo(Connection conn, Bastanteo bastanteo) throws SQLException {
		String newCode = getNewCode(conn);
		String sql = "INSERT INTO public.tbl_bastanteos(" + SQL_CAMPS + ")"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setString(1, newCode);
		if (bastanteo.getDatabastanteo() != null) {
			pstm.setDate(2, new java.sql.Date(bastanteo.getDatabastanteo().getTime()));
		} else {
			pstm.setDate(2, null);
		}
		pstm.setString(3, bastanteo.getEmpresa().getCif());
		pstm.setString(4, bastanteo.getEscritura());
		pstm.setString(5, bastanteo.getPersonaFacultada());
		pstm.setString(6, bastanteo.getCarrec());	
		if (bastanteo.getDataEscritura() != null) {
			pstm.setDate(7, new java.sql.Date(bastanteo.getDataEscritura().getTime()));
		} else {
			pstm.setDate(7, null);
		}
		pstm.setString(8, bastanteo.getNumProtocol());	
		pstm.setString(9, bastanteo.getNotari());
		pstm.setString(10, bastanteo.getProcedencia());	
		pstm.setString(11, bastanteo.getDesti());	
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		pstm.setString(12, yearInString);	
		pstm.executeUpdate();
		return newCode;
	}
	
	public static Bastanteo findBastanteo(Connection conn, String referencia) throws SQLException, NamingException {		 
		String sql = "SELECT " + SQL_CAMPS
					+ " FROM public.tbl_bastanteos"
					+ " WHERE ref = ?";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, referencia);		
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			Bastanteo bastanteo = initBastanteo(conn, rs); 
			return bastanteo;
		}
		return new Bastanteo();
	}
	
	public static List<Bastanteo> getBastanteos(Connection conn, String year) throws SQLException, NamingException {
		List<Bastanteo> bastanteosList = new ArrayList<Bastanteo>();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_bastanteos";					
		PreparedStatement pstm;		
		boolean primeraCondicio = true;	
		if (year != null && !year.isEmpty() && ! year.equals("-1")) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE anybastanteo::INT = " + year;
							
			}else{
					sql += " AND anybastanteo::INT = " + year;
							
			}			
		}
		sql += " ORDER BY databastanteo DESC, ref DESC";
		pstm = conn.prepareStatement(sql);	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {		
			bastanteosList.add(initBastanteo(conn, rs));
		}
		return bastanteosList;
	}
	
	public static void modificarBastanteo(Connection conn, Bastanteo bastanteo) throws SQLException, NamingException {
		String sql = "UPDATE public.tbl_bastanteos"
					+ " SET databastanteo=?, empresa=?, escritura=?, personafacultada=?, carrec=?, dataescritura=?, nprotocol=?, notari=?, procedencia=?, desti=?"
					+ " WHERE ref=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		if (bastanteo.getDatabastanteo() != null) {
			pstm.setDate(1, new java.sql.Date(bastanteo.getDatabastanteo().getTime()));
		} else {
			pstm.setDate(1, null);
		}
		pstm.setString(2, bastanteo.getEmpresa().getCif());
		pstm.setString(3, bastanteo.getEscritura());
		pstm.setString(4, bastanteo.getPersonaFacultada());
		pstm.setString(5, bastanteo.getCarrec());	
		if (bastanteo.getDataEscritura() != null) {
			pstm.setDate(6, new java.sql.Date(bastanteo.getDataEscritura().getTime()));
		} else {
			pstm.setDate(6, null);
		}
		pstm.setString(7, bastanteo.getNumProtocol());	
		pstm.setString(8, bastanteo.getNotari());
		pstm.setString(9, bastanteo.getProcedencia());	
		pstm.setString(10, bastanteo.getDesti());	
		pstm.setString(11, bastanteo.getRef());	
		System.out.println(pstm.toString());
		pstm.executeUpdate();
	}
	
	public static String getNewCode(Connection conn) throws SQLException {
		String newCode = "";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		String sql = "SELECT ref"
					+ " FROM public.tbl_bastanteos"
					+ " WHERE ref like '%/" + yearInString + "%'"
					+ " ORDER BY ref DESC;";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();		
		if (rs.next()) { //Codis nous
			String actualCode = rs.getString("ref");			
			int num = Integer.valueOf(actualCode.split("/")[0]);
			String numFormatted = String.format("%03d", num + 1);
			newCode = numFormatted + "/" + yearInString;
		}
		return newCode;
	}
}
