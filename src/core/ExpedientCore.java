package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Expedient;
public class ExpedientCore {
	static final String SQL_CAMPS = "e.expcontratacio AS expcontratacio, importlicitacio, terminiexecucio, databoib, datalimitprsentacio," +
									" dataadjudicacio, importadjudicacio, dataformalitzaciocontracte, datainiciexecucio, datarecepcio," +
									" dataretorngarantia, dataliquidacio, garantia, idactuacio";
	
	private static Expedient initExpedient(Connection conn, ResultSet rs) throws SQLException{
		Expedient expedient = new Expedient();
		expedient.setExpContratacio(rs.getString("expcontratacio"));
		expedient.setActuacio(ActuacioCore.findActuacio(conn, rs.getString("idactuacio")));
		expedient.setImportAdjudicacio(rs.getDouble("importlicitacio"));
		expedient.setTerminiExecucio(rs.getString("terminiexecucio"));
		expedient.setDataPublicacioBOIB(rs.getTimestamp("databoib"));
		expedient.setDataLimitPresentacio(rs.getTimestamp("datalimitprsentacio"));
		expedient.setDataAdjudicacio(rs.getTimestamp("dataadjudicacio"));
		expedient.setImportAdjudicacio(rs.getDouble("importadjudicacio"));
		expedient.setDataFormalitzacioContracte(rs.getTimestamp("dataformalitzaciocontracte"));
		expedient.setDataIniciExecucio(rs.getTimestamp("datainiciexecucio"));
		expedient.setDataRecepcio(rs.getTimestamp("datarecepcio"));
		expedient.setDataRetornGarantia(rs.getTimestamp("dataretorngarantia"));
		expedient.setDataLiquidacio(rs.getTimestamp("dataliquidacio"));
		expedient.setGarantia(rs.getDouble("garantia"));
		return expedient;
	}
	
	public static List<Expedient> getExpedients(Connection conn) throws SQLException {
		List<Expedient> expedientsList = new ArrayList<Expedient>();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_expedient e LEFT JOIN public.tbl_informeactuacio i ON e.expcontratacio = i.expcontratacio";					
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);		
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {		
			expedientsList.add(initExpedient(conn, rs));
		}
		return expedientsList;
	}
}
