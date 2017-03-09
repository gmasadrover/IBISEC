package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Factura;

public class FacturaCore {
	static final String SQL_CAMPS = "idfactura, idoferta, idactuacio, nifproveidor, datafactura, concepte, import, tipusfactura,"
									+ " nombrefactura, dataentrada, usuconformador, dataconformador, notes";
	
	private static Factura initFactura(Connection conn, ResultSet rs) throws SQLException{	
		Factura factura = new Factura();
		factura.setIdFactura(rs.getInt("idfactura"));
		factura.setIdOferta(rs.getInt("idoferta"));
		factura.setIdActuacio(rs.getInt("idactuacio"));
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
	
	public static Factura getFactura(Connection conn, int idFactura) throws SQLException{
		Factura factura = new Factura();
		String sql = "SELECT " + SQL_CAMPS
			 		+ " FROM public.tbl_factures"
			 		+ " WHERE idfactura = ?";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idFactura);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			factura = initFactura(conn, rs);
		}
		return factura;
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
	
	public static List<Factura> getFacturesActuacio(Connection conn, int idActuacio) throws SQLException {
		List<Factura> factures = new ArrayList<Factura>();
		String sql = "SELECT " + SQL_CAMPS
			 		+ " FROM public.tbl_factures"
			 		+ " WHERE idactuacio = ?";	 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setInt(1, idActuacio);
		ResultSet rs = pstm.executeQuery();		
		while (rs.next()) {
			factures.add(initFactura(conn, rs));
		}
		return factures;
	}
}
