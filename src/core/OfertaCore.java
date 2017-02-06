package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Oferta;

public class OfertaCore {
	private static Oferta initOferta(Connection conn, ResultSet rs) throws SQLException{
		Oferta oferta = new Oferta();
		oferta.setIdOferta(rs.getInt("idOferta"));
		oferta.setIdActuacio(rs.getInt("idActuacio"));		
   		oferta.setCifEmpresa(rs.getString("cifEmpresa"));
   		oferta.setNomEmpresa(EmpresaCore.findEmpresa(conn, oferta.getCifEmpresa()).getName());
   		oferta.setVec(rs.getDouble("vec"));   		
   		oferta.setIva(rs.getDouble("iva"));
   		oferta.setPlic(rs.getDouble("plic"));
   		oferta.setTermini(rs.getString("termini"));
   		oferta.setSeleccionada(rs.getBoolean("seleccionada"));
   		oferta.setDescalificada(rs.getBoolean("descalificada")); 
   		oferta.setComentari(rs.getString("comentari"));
   		return oferta;
	}
	
	public static void novaOferta(Connection conn, Oferta oferta) throws SQLException {
		String sql = "INSERT INTO public.\"tbl_EmpresaOferta\"(\"idOferta\", \"idActuacio\", \"cifEmpresa\", vec, iva, plic, termini, seleccionada, descalificada, comentari)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		pstm.setInt(1, getNewCode(conn));		
		pstm.setInt(2, oferta.getIdActuacio());
		pstm.setString(3, oferta.getCifEmpresa());
		pstm.setDouble(4, oferta.getVec());
		pstm.setDouble(5, oferta.getIva());
		pstm.setDouble(6, oferta.getPlic());
		pstm.setString(7, oferta.getTermini());
		pstm.setBoolean(8, oferta.isSeleccionada());
		pstm.setBoolean(9, oferta.isDescalificada());
		pstm.setString(10, oferta.getComentari());
		
		pstm.executeUpdate();
	}
	
	public static List<Oferta> findOfertes(Connection conn, int idActuacio) throws SQLException {
		List<Oferta> ofertes = new ArrayList<Oferta>();
		String sql = "SELECT \"idOferta\", \"idActuacio\", \"cifEmpresa\", vec, iva, plic, termini, seleccionada, descalificada, comentari"
					+ " FROM public.\"tbl_EmpresaOferta\" WHERE \"idActuacio\" = ? ;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idActuacio);		
		ResultSet rs = pstm.executeQuery();		
		while (rs.next()) {
			Oferta oferta = initOferta(conn, rs);
			ofertes.add(oferta);
		}
		return ofertes;
	}
	
	public static Oferta findOfertaSeleccionada(Connection conn, int idActuacio) throws SQLException {		
		String sql = "SELECT \"idOferta\", \"idActuacio\", \"cifEmpresa\", vec, iva, plic, termini, seleccionada, descalificada, comentari"
					+ " FROM public.\"tbl_EmpresaOferta\" WHERE \"idActuacio\" = ? and seleccionada = true ;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idActuacio);		
		ResultSet rs = pstm.executeQuery();		
		while (rs.next()) {
			Oferta oferta = initOferta(conn, rs);
			return oferta;
		}
		return null;
	}
	
	public static int getNewCode(Connection conn) throws SQLException {
		int newCode = 1;
		String sql = "SELECT \"idOferta\" FROM public.\"tbl_EmpresaOferta\" ORDER BY \"idOferta\" DESC LIMIT 1;";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			int actualCode = rs.getInt("idOferta");
			newCode = actualCode + 1;
		}
		return newCode;
	}
}
