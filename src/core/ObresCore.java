package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.Obra;
import bean.Oferta;

public class ObresCore {
	private static Obra initObra(Connection conn, ResultSet rs) throws SQLException{
		Obra obra = new Obra();
		obra.setExpContratacio(rs.getString("expcontratacio"));
		obra.setIdActuacio(rs.getString("idactuacio"));
		obra.setDescripcio(rs.getString("desc"));
		obra.setDataCreacio(rs.getTimestamp("datacre"));
		obra.setDataAprovacio(rs.getTimestamp("dataaprovacio"));
		obra.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idcentre")));
		obra.setIdInforme(rs.getString("idinf"));
		obra.setFactures(FacturaCore.getFacturesInforme(conn, rs.getString("idinf")));
		Oferta oferta = OfertaCore.findOfertaSeleccionada(conn, rs.getString("idinf"));
		if (oferta != null) {
			obra.setEmpresa(EmpresaCore.findEmpresa(conn, oferta.getCifEmpresa()));
			obra.setValor(oferta.getPlic());
		}
		obra.setNotes(rs.getString("notes"));
		obra.setDataTancament(rs.getTimestamp("tancament"));
		return obra;
	}
		
	public static List<Obra> ObresMenors(Connection conn, String idCentre, Date dataIni, Date dataFi, boolean withTancades) throws SQLException {
		String sql = "SELECT a.id AS idactuacio, a.descripcio AS desc, a.datacre AS datacre, a.dataaprovacio AS dataaprovacio, a.idcentre AS idcentre, i.idinf AS idinf, i.datatancament AS tancament, e.notes AS notes"
					+ " FROM public.tbl_informeactuacio i LEFT JOIN public.tbl_actuacio a ON i.idactuacio = a.id"
					+ "		LEFT JOIN public.tbl_informacioextra e ON i.idinf = e.idinf"
					+ "		LEFT JOIN public.tbl_propostesinforme p ON p.idinf = i.idinf AND p.seleccionada = true"
					+ " WHERE p.tipusobra = 'obr' AND p.vec < 50000";
		if (!withTancades) sql += " AND i.datatancament IS NULL";			
		PreparedStatement pstm = conn.prepareStatement(sql);			
		if (dataIni != null && dataFi != null) {
			sql += " AND i.datacre >= ? AND i.datacre <= ? ";
			if (idCentre != "") {
				sql += " AND a.idcentre = ?";
				sql += " ORDER BY i.idactuacio DESC, i.idinf DESC";
				pstm = conn.prepareStatement(sql);
				pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
				Calendar fi = Calendar.getInstance();
			    fi.setTime(dataFi);
			    fi.add(Calendar.DATE, 1);	
			    dataFi = fi.getTime();
				pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
				pstm.setString(3, idCentre);	
			} else {				
				sql += " ORDER BY i.idactuacio DESC, i.idinf DESC";
				pstm = conn.prepareStatement(sql);
				pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
				Calendar fi = Calendar.getInstance();
			    fi.setTime(dataFi);
			    fi.add(Calendar.DATE, 1);	
			    dataFi = fi.getTime();
				pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
			}
		}else{
			if (idCentre != "") {
				sql += " AND a.idcentre = ?";				
				sql += " ORDER BY i.idactuacio DESC, i.idinf DESC";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, idCentre);			
			} else {				
				sql += " ORDER BY i.idactuacio DESC, i.idinf DESC";
				pstm = conn.prepareStatement(sql);
			}	
		}
		ResultSet rs = pstm.executeQuery();
		System.out.println(pstm.toString());
		List<Obra> list = new ArrayList<Obra>();
		while (rs.next()) {
			Obra obra = initObra(conn, rs);
			list.add(obra);
		}
		return list;
	}
	
	public static List<Obra> ObresMajor(Connection conn, String idCentre, Date dataIni, Date dataFi, boolean withTancades) throws SQLException {
	String sql = "SELECT i.expcontratacio AS expcontratacio, a.id AS idactuacio, a.descripcio AS desc, a.datacre AS datacre, a.dataaprovacio AS dataaprovacio, a.idcentre AS idcentre, i.idinf AS idinf, a.datatancament AS tancament, e.notes AS notes"
				+ " FROM public.tbl_informeactuacio i LEFT JOIN public.tbl_actuacio a ON i.idactuacio = a.id"
					+ " LEFT JOIN public.tbl_informacioextra e ON i.idinf = e.idinf"	
				+ " WHERE i.tipo = 'obraMajor'";
		
		PreparedStatement pstm = conn.prepareStatement(sql);			
		if (!withTancades) {
			sql += " AND a.datatancament IS NULL";			
		}
		if (dataIni != null) {
			sql += " AND i.datacre >= ?";			
		}
		if (dataFi != null) {
			sql += " AND i.datacre <= ?";				
		}
		if (idCentre != null && !idCentre.isEmpty() && ! "-1".equals(idCentre)) {
			sql += " AND a.idcentre = ?";			
		}
		
		sql += " ORDER BY i.idactuacio DESC, i.idinf DESC";
		pstm = conn.prepareStatement(sql);
		System.out.println(sql.toString());
		int contVars = 1;
		if (dataIni != null) {
			pstm.setDate(contVars, new java.sql.Date(dataIni.getTime()));
			contVars += 1;
		}
		if (dataFi != null) {
			pstm.setDate(contVars, new java.sql.Date(dataFi.getTime()));
			contVars += 1;
		}
		if (idCentre != null && !idCentre.isEmpty() && ! "-1".equals(idCentre)) {
			pstm.setString(contVars, idCentre);
			contVars += 1;
		}				
				
		ResultSet rs = pstm.executeQuery();
		List<Obra> list = new ArrayList<Obra>();
		while (rs.next()) {
			Obra obra = initObra(conn, rs);
			list.add(obra);
		}
		return list;
	}
}
