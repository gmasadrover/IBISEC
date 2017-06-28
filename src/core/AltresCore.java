package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.Altres;
import bean.Oferta;

public class AltresCore {
	private static Altres initAltra(Connection conn, ResultSet rs) throws SQLException{
		Altres altra = new Altres();
		altra.setIdActuacio(rs.getString("idactuacio"));
		altra.setDescripcio(rs.getString("desc"));
		altra.setDataCreacio(rs.getTimestamp("datacre"));
		altra.setDataAprovacio(rs.getTimestamp("dataaprovacio"));
		altra.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idcentre")));
		altra.setIdInforme(rs.getString("idinf"));
		altra.setFactures(FacturaCore.getFacturesInforme(conn, rs.getString("idinf")));
		Oferta oferta = OfertaCore.findOfertaSeleccionada(conn, rs.getString("idinf"));
		if (oferta != null) {
			altra.setEmpresa(EmpresaCore.findEmpresa(conn, oferta.getCifEmpresa()));
			altra.setValor(oferta.getPlic());
		}
		altra.setNotes(rs.getString("notes"));
		altra.setDataTancament(rs.getTimestamp("tancament"));
		return altra;
	}
		
	public static List<Altres> AltresMenors(Connection conn, String idCentre, Date dataIni, Date dataFi) throws SQLException {
		String sql = "SELECT a.id AS idactuacio, a.descripcio AS desc, a.datacre AS datacre, a.dataaprovacio AS dataaprovacio, a.idcentre AS idcentre, i.idinf AS idinf, i.datatancament AS tancament, e.notes AS notes"
					+ " FROM public.tbl_informeactuacio i LEFT JOIN public.tbl_actuacio a ON i.idactuacio = a.id"
					+ "		LEFT JOIN public.tbl_informacioextra e ON i.idinf = e.idinf"
					+ "		LEFT JOIN public.tbl_propostesinforme p ON p.idinf = i.idinf AND p.seleccionada = true"
					+ " WHERE p.tipusobra IS NULL AND p.vec < 50000";
					
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
		List<Altres> list = new ArrayList<Altres>();
		while (rs.next()) {
			Altres altra = initAltra(conn, rs);
			list.add(altra);
		}
		return list;
	}
	
	public static List<Altres> AltresMajor(Connection conn, String idCentre, Date dataIni, Date dataFi) throws SQLException {
		String sql = "SELECT a.id AS idactuacio, a.descripcio AS desc, a.datacre AS datacre, a.dataaprovacio AS dataaprovacio, a.idcentre AS idcentre, i.idinf AS idinf, i.datatancament AS tancament, e.notes AS notes"
				+ " FROM public.tbl_informeactuacio i LEFT JOIN public.tbl_actuacio a ON i.idactuacio = a.id"
				+ "		LEFT JOIN public.tbl_informacioextra e ON i.idinf = e.idinf"
				+ "		LEFT JOIN public.tbl_propostesinforme p ON p.idinf = i.idinf AND p.seleccionada = true"
				+ " WHERE p.tipusobra IS NULL AND p.vec >= 50000";
				
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
		List<Altres> list = new ArrayList<Altres>();
		while (rs.next()) {
			Altres altra = initAltra(conn, rs);
			list.add(altra);
		}
		return list;
	}
}
