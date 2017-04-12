package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.Factura;

public class FacturaCore {
	static final String SQL_CAMPS = "idfactura, idinforme, idactuacio, nifproveidor, datafactura, concepte, import, tipusfactura,"
									+ " nombrefactura, dataentrada, usuconformador, dataconformador, notes, usucre, datacre";
	
	private static Factura initFactura(Connection conn, ResultSet rs) throws SQLException{	
		Factura factura = new Factura();
		factura.setIdFactura(rs.getString("idfactura"));
		factura.setIdInforme(rs.getString("idinforme"));
		factura.setIdActuacio(rs.getString("idactuacio"));
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
