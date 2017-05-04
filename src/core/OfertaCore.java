package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.Oferta;

public class OfertaCore {
	private static Oferta initOferta(Connection conn, ResultSet rs, boolean complet) throws SQLException{
		Oferta oferta = new Oferta();
		oferta.setIdOferta(rs.getString("idoferta"));
		oferta.setIdActuacio(rs.getString("idactuacio"));		
   		oferta.setCifEmpresa(rs.getString("cifempresa"));
   		if (EmpresaCore.findEmpresa(conn, oferta.getCifEmpresa()) != null) oferta.setNomEmpresa(EmpresaCore.findEmpresa(conn, oferta.getCifEmpresa()).getName());
   		oferta.setVec(rs.getDouble("vec"));   		
   		oferta.setIva(rs.getDouble("iva"));
   		oferta.setPlic(rs.getDouble("plic"));
   		oferta.setTermini(rs.getString("termini"));
   		oferta.setSeleccionada(rs.getBoolean("seleccionada"));
   		oferta.setDescalificada(rs.getBoolean("descalificada")); 
   		oferta.setUsuariCreacio(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
   		oferta.setDataCreacio(rs.getTimestamp("datacre"));
   		oferta.setComentari(rs.getString("comentari"));
   		oferta.setUsuariAprovacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuaprovacio")));
   		oferta.setDataAprovacio(rs.getTimestamp("dataaprovacio"));
   		oferta.setUsuariCapValidacio(UsuariCore.findUsuariByID(conn, rs.getInt("usucapvalidacio")));
   		oferta.setDataCapValidacio(rs.getTimestamp("datacapvalidacio"));
   		oferta.setIdInforme(rs.getString("idinforme"));
   		if (complet) {
   			oferta.setActuacio(ActuacioCore.findActuacio(conn, rs.getString("idactuacio")));
   		}
   		return oferta;
	}
	
	public static void novaOferta(Connection conn, Oferta oferta, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.tbl_empresaoferta(idoferta, idactuacio, cifempresa, vec, iva, plic, termini, seleccionada, descalificada, comentari, usucre, datacre, idinforme)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, localtimestamp, ?);";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setString(1, getNewCode(conn));		
		pstm.setString(2, oferta.getIdActuacio());
		pstm.setString(3, oferta.getCifEmpresa());
		pstm.setDouble(4, oferta.getVec());
		pstm.setDouble(5, oferta.getIva());
		pstm.setDouble(6, oferta.getPlic());
		pstm.setString(7, oferta.getTermini());
		pstm.setBoolean(8, oferta.isSeleccionada());
		pstm.setBoolean(9, oferta.isDescalificada());
		pstm.setString(10, oferta.getComentari());
		pstm.setInt(11, idUsuari);
		pstm.setString(12, oferta.getIdInforme());
		pstm.executeUpdate();
	}
	
	public static List<Oferta> findOfertes(Connection conn, String idInforme) throws SQLException {
		List<Oferta> ofertes = new ArrayList<Oferta>();
		String sql = "SELECT idoferta, idactuacio, cifempresa, vec, iva, plic, termini, seleccionada, descalificada, comentari, usucre, datacre, usuaprovacio, dataaprovacio, idinforme, usucapvalidacio, datacapvalidacio"
					+ " FROM public.tbl_empresaoferta"
					+ " WHERE idinforme = ? ;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, idInforme);		
		ResultSet rs = pstm.executeQuery();		
		while (rs.next()) {
			Oferta oferta = initOferta(conn, rs, false);
			ofertes.add(oferta);
		}
		return ofertes;
	}
	
	public static List<Oferta> findOfertesEmpresa(Connection conn, String cif) throws SQLException {
		List<Oferta> ofertes = new ArrayList<Oferta>();
		String sql = "SELECT idoferta, idactuacio, cifempresa, vec, iva, plic, termini, seleccionada, descalificada, comentari, usucre, datacre, usuaprovacio, dataaprovacio, idinforme, usucapvalidacio, datacapvalidacio"
					+ " FROM public.tbl_empresaoferta"
					+ " WHERE cifempresa = ? ;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, cif);		
		ResultSet rs = pstm.executeQuery();		
		while (rs.next()) {
			Oferta oferta = initOferta(conn, rs, true);
			ofertes.add(oferta);
		}
		return ofertes;
	}
	
	public static Oferta findOfertaSeleccionada(Connection conn, String idInforme) throws SQLException {		
		String sql = "SELECT idoferta, idactuacio, cifempresa, vec, iva, plic, termini, seleccionada, descalificada, comentari, usucre, datacre, usuaprovacio, dataaprovacio, idinforme, usucapvalidacio, datacapvalidacio"
					+ " FROM public.tbl_empresaoferta"
					+ " WHERE idinforme = ? AND seleccionada = true ;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, idInforme);		
		ResultSet rs = pstm.executeQuery();		
		while (rs.next()) {
			Oferta oferta = initOferta(conn, rs, false);
			return oferta;
		}
		return null;
	}
	
	public static void validacioCapOferta(Connection conn, String idInforme, int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_empresaoferta"
					+ " SET usucapvalidacio=?, datacapvalidacio=localtimestamp"
					+ " WHERE idinforme = ? AND seleccionada = true;";
		PreparedStatement pstm = conn.prepareStatement(sql);	
		pstm.setInt(1, idUsuari);
		pstm.setString(2, idInforme);		
		pstm.executeUpdate();
	}
	
	public static void aprovarOferta(Connection conn, String idInforme, int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_empresaoferta"
					+ " SET usuaprovacio=?, dataaprovacio=localtimestamp"
					+ " WHERE idinforme = ? AND seleccionada = true;";
		PreparedStatement pstm = conn.prepareStatement(sql);	
		pstm.setInt(1, idUsuari);
		pstm.setString(2, idInforme);		
		pstm.executeUpdate();
	}
	
	public static String getNewCode(Connection conn) throws SQLException {
		String newCode = "1";
		
		String sql = "SELECT idoferta, datacre"
					+ " FROM public.tbl_empresaoferta"
					+ " WHERE idoferta like '%OFE%'"
					+ " ORDER BY datacre DESC, idoferta DESC LIMIT 1;";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		String prefix = "OFE";
		if (rs.next()) { //Codis nous
			String actualCode = rs.getString("idoferta");			
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
}
