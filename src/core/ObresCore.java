package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ObraAltra;
import bean.ObraMajor;
import bean.ObraMenor;

public class ObresCore {
	static final String SQL_CAMPSMENOR = "id, idactuacio, datainici, obert, datatancament, motiutancament";
	static final String SQL_CAMPSMAJOR = "id, idactuacio, datainici, obert, datatancament, motiutancament";
	static final String SQL_CAMPSALTRES = "id, idactuacio, datainici, obert, datatancament, motiutancament";
	
	private static ObraMenor initObraMenor(Connection conn, ResultSet rs) throws SQLException{
		ObraMenor obra = new ObraMenor();
		obra.setId(rs.getInt("id"));
		obra.setIdActuacio(rs.getInt("idactuacio"));
		obra.setDataInici(rs.getTimestamp("datainici"));
		obra.setObert(rs.getBoolean("obert"));
		obra.setDataTancament(rs.getTimestamp("datatancament"));
		obra.setMotiuTancament(rs.getString("motiutancament"));	
		return obra;
	}
	
	private static ObraMajor initObraMajor(Connection conn, ResultSet rs) throws SQLException{
		ObraMajor obra = new ObraMajor();
		obra.setId(rs.getString("id"));
		obra.setIdActuacio(rs.getInt("idactuacio"));
		obra.setDataInici(rs.getTimestamp("datainici"));
		obra.setObert(rs.getBoolean("obert"));
		obra.setDataTancament(rs.getTimestamp("datatancament"));
		obra.setMotiuTancament(rs.getString("motiutancament"));	
		return obra;
	}
	
	private static ObraAltra initObraAltra(Connection conn, ResultSet rs) throws SQLException{
		ObraAltra obra = new ObraAltra();
		obra.setId(rs.getInt("id"));
		obra.setIdActuacio(rs.getInt("idactuacio"));
		obra.setDataInici(rs.getTimestamp("datainici"));
		obra.setObert(rs.getBoolean("obert"));
		obra.setDataTancament(rs.getTimestamp("datatancament"));
		obra.setMotiuTancament(rs.getString("motiutancament"));	
		return obra;
	}
	
	public static List<ObraMenor> ObresMenors(Connection conn) throws SQLException {
		String sql = "SELECT " + SQL_CAMPSMENOR
					+ " FROM public.tbl_obramenor"
					+ " ORDER BY id::INT DESC";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		List<ObraMenor> list = new ArrayList<ObraMenor>();
		while (rs.next()) {
			ObraMenor obra = initObraMenor(conn, rs);
			list.add(obra);
		}
		return list;
	}
	
	public static List<ObraMajor> ObresMajor(Connection conn) throws SQLException {
		String sql = "SELECT " + SQL_CAMPSMAJOR
					+ " FROM public.tbl_obramajor"
					+ " ORDER BY id::INT DESC";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		List<ObraMajor> list = new ArrayList<ObraMajor>();
		while (rs.next()) {
			ObraMajor obra = initObraMajor(conn, rs);
			list.add(obra);
		}
		return list;
	}
	
	public static List<ObraAltra> ObresAltres(Connection conn) throws SQLException {
		String sql = "SELECT " + SQL_CAMPSALTRES
					+ " FROM public.tbl_obraaltres"
					+ " ORDER BY id::INT DESC";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
	 
		ResultSet rs = pstm.executeQuery();
		List<ObraAltra> list = new ArrayList<ObraAltra>();
		while (rs.next()) {
			ObraAltra obra = initObraAltra(conn, rs);
			list.add(obra);
		}
		return list;
	}
}
