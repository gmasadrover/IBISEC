package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.Oferta;
import bean.Subministrament;

public class SubministramentCore {
	private static Subministrament initSubministrament(Connection conn, ResultSet rs) throws SQLException{
		Subministrament subministrament = new Subministrament();
		subministrament.setIdActuacio(rs.getString("idactuacio"));
		subministrament.setDescripcio(rs.getString("desc"));
		subministrament.setDataCreacio(rs.getTimestamp("datacre"));
		subministrament.setDataAprovacio(rs.getTimestamp("dataaprovacio"));
		subministrament.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idcentre")));
		subministrament.setIdInforme(rs.getString("idinf"));
		subministrament.setFactures(FacturaCore.getFacturesInforme(conn, rs.getString("idinf")));
		Oferta oferta = OfertaCore.findOfertaSeleccionada(conn, rs.getString("idinf"));
		if (oferta != null) {
			subministrament.setEmpresa(EmpresaCore.findEmpresa(conn, oferta.getCifEmpresa()));
			subministrament.setValor(oferta.getPlic());
		}
		subministrament.setNotes(rs.getString("notes"));
		subministrament.setDataTancament(rs.getTimestamp("tancament"));
		return subministrament;
	}
		
	public static List<Subministrament> SubministramentMenors(Connection conn, String idCentre, Date dataIni, Date dataFi) throws SQLException {
		String sql = "SELECT a.id AS idactuacio, a.descripcio AS desc, a.datacre AS datacre, a.dataaprovacio AS dataaprovacio, a.idcentre AS idcentre, i.idinf AS idinf, i.datatancament AS tancament, e.notes AS notes"
					+ " FROM public.tbl_informeactuacio i LEFT JOIN public.tbl_actuacio a ON i.idactuacio = a.id"
					+ "		LEFT JOIN public.tbl_informacioextra e ON i.idinf = e.idinf"
					+ " WHERE i.tipusobra = 'submi' AND i.vec < 50000";
					
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
		List<Subministrament> list = new ArrayList<Subministrament>();
		while (rs.next()) {
			Subministrament subministrament = initSubministrament(conn, rs);
			list.add(subministrament);
		}
		return list;
	}
	
	public static List<Subministrament> SubministramentMajor(Connection conn, String idCentre, Date dataIni, Date dataFi) throws SQLException {
		String sql = "SELECT a.id AS idactuacio, a.descripcio AS desc, a.datacre AS datacre, a.dataaprovacio AS dataaprovacio, a.idcentre AS idcentre, i.idinf AS idinf, i.datatancament AS tancament, e.notes AS notes"
				+ " FROM public.tbl_informeactuacio i LEFT JOIN public.tbl_actuacio a ON i.idactuacio = a.id"
				+ "		LEFT JOIN public.tbl_informacioextra e ON i.idinf = e.idinf"
				+ " WHERE i.tipusobra = 'submi' AND i.vec >= 50000";
				
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
		List<Subministrament> list = new ArrayList<Subministrament>();
		while (rs.next()) {
			Subministrament subministrament = initSubministrament(conn, rs);
			list.add(subministrament);
		}
		return list;
	}
}
