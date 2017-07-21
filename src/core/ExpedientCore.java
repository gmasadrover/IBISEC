package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.Expedient;
import bean.InformeActuacio;
public class ExpedientCore {
	static final String SQL_CAMPS = "e.expcontratacio AS expcontratacio, databoib, datalimitprsentacio," +
									" dataadjudicacio, dataformalitzaciocontracte, datainiciexecucio, datarecepcio," +
									" dataretorngarantia, dataliquidacio, garantia, idinf, idactuacio, tipus, contracte, e.datacre AS datacre, e.descripcio AS descripcio";
	
	private static Expedient initExpedient(Connection conn, ResultSet rs) throws SQLException{
		Expedient expedient = new Expedient();
		expedient.setExpContratacio(rs.getString("expcontratacio"));
		expedient.setActuacio(ActuacioCore.findActuacio(conn, rs.getString("idactuacio")));
		expedient.setInforme(InformeCore.getInformePrevi(conn, rs.getString("idinf")));	
		expedient.setDataPublicacioBOIB(rs.getTimestamp("databoib"));
		expedient.setDataLimitPresentacio(rs.getTimestamp("datalimitprsentacio"));
		expedient.setDataAdjudicacio(rs.getTimestamp("dataadjudicacio"));
		expedient.setDataFormalitzacioContracte(rs.getTimestamp("dataformalitzaciocontracte"));
		expedient.setDataIniciExecucio(rs.getTimestamp("datainiciexecucio"));
		expedient.setDataRecepcio(rs.getTimestamp("datarecepcio"));
		expedient.setDataRetornGarantia(rs.getTimestamp("dataretorngarantia"));
		expedient.setDataLiquidacio(rs.getTimestamp("dataliquidacio"));
		expedient.setGarantia(rs.getString("garantia"));
		expedient.setDescripcio(rs.getString("descripcio"));
		expedient.setTipus(rs.getString("tipus"));
		expedient.setDataCreacio(rs.getTimestamp("datacre"));
		expedient.setContracte(rs.getString("contracte"));
		return expedient;
	}
	
	public static List<Expedient> getExpedients(Connection conn, String estat, String tipus, String contracte, double importObraMajor) throws SQLException {
		List<Expedient> expedientsList = new ArrayList<Expedient>();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_expedient e LEFT JOIN public.tbl_informeactuacio i ON e.expcontratacio = i.expcontratacio";					
		PreparedStatement pstm;
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance(); 
		Date dataFi = cal.getTime();
		String dataFiString = df.format(dataFi);	
		boolean primeraCondicio = true;
		
		if (estat != null && !estat.isEmpty() && ! estat.equals("-1")) {	
			primeraCondicio = false;
			if ("redaccio".equals(estat)) sql+= " WHERE a.datatancament IS NULL AND e.dataliquidacio IS NULL AND i.expcontratacio = ''"; 
			if ("iniciExpedient".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND i.expcontratacio != '' AND e.databoib IS NULL AND e.dataadjudicacio IS NULL"; 
			if ("publicat".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.databoib IS NOT NULL AND e.datalimitprsentacio >= '" + dataFiString + "' AND e.dataadjudicacio IS NULL"; 
			if ("licitacio".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.databoib IS NOT NULL AND e.datalimitprsentacio < '" + dataFiString + "' AND e.dataadjudicacio IS NULL"; 	
			if ("adjudicacio".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.dataadjudicacio IS NOT NULL AND e.dataformalitzaciocontracte IS NULL"; 	
			if ("firmat".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.datarecepcio IS NULL AND e.databoib > '01/01/2016' AND (e.datainiciexecucio IS NULL OR e.datainiciexecucio > localtimestamp) AND e.dataformalitzaciocontracte IS NOT NULL"; 	
			if ("execucio".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.databoib > '01/01/2016' AND e.datainiciexecucio <= localtimestamp AND e.datarecepcio IS NULL"; 	
			if ("garantia".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.databoib > '01/01/2016' AND e.datarecepcio IS NOT NULL AND e.dataretorngarantia < localtimestamp"; 	
			if ("acabat".equals(estat)) sql+= " WHERE e.dataliquidacio IS NOT NULL"; 	
		}		
		if (tipus != null && !tipus.isEmpty() && ! tipus.equals("-1")) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE e.tipus = '" + tipus + "'";
			}else{
				sql += " AND e.tipus = '" + tipus + "'";
			}
		}
		if (contracte != null && !contracte.isEmpty() && ! contracte.equals("-1")) {			
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE e.contracte = '" + contracte + "'";
			}else{
				sql += " AND e.contracte = '" + contracte + "'";
			}
			
		}	
		sql += " ORDER BY datacre DESC, expcontratacio DESC";
		pstm = conn.prepareStatement(sql);		
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {		
			expedientsList.add(initExpedient(conn, rs));
		}
		return expedientsList;
	}
	
	public static Expedient findExpedient(Connection conn, String referencia) throws SQLException {
		Expedient expedient = new Expedient();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_expedient e LEFT JOIN public.tbl_informeactuacio i ON e.expcontratacio = i.expcontratacio"
				+ " WHERE e.expcontratacio = ?";					
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);	
		pstm.setString(1, referencia);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) expedient = initExpedient(conn, rs);
		return expedient;
	}
	
	public static void crearExpedient(Connection conn, Expedient nouExpedient) throws SQLException {
		String sql = "INSERT INTO public.tbl_expedient(expcontratacio, dataadjudicacio, tipus, descripcio, contracte, datacre)"
					+ " VALUES(?, ?, ?, ?, ?, localtimestamp);";					
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);	
		pstm.setString(1, nouExpedient.getExpContratacio());
		if (nouExpedient.getDataAdjudicacio() != null) {
			pstm.setDate(2, new java.sql.Date(nouExpedient.getDataAdjudicacio().getTime()));
		} else {
			pstm.setDate(2, null);
		}	
		pstm.setString(3, nouExpedient.getTipus());
		pstm.setString(4, nouExpedient.getDescripcio());
		pstm.setString(5, nouExpedient.getContracte());
		pstm.executeUpdate();
	}
	
	public static void updateExpedient(Connection conn, Expedient expedient) throws SQLException {
		String sql = "UPDATE public.tbl_expedient"
					+ " SET databoib=?, datalimitprsentacio=?, dataadjudicacio=?, dataformalitzaciocontracte=?, datainiciexecucio=?, datarecepcio=?, dataretorngarantia=?, dataliquidacio=?, garantia=?, descripcio=?"
					+ " WHERE expcontratacio=?;";
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);	
		if (expedient.getDataPublicacioBOIB() != null) {
			pstm.setDate(1, new java.sql.Date(expedient.getDataPublicacioBOIB().getTime()));
		} else {
			pstm.setDate(1, null);
		}
		if (expedient.getDataLimitPresentacio() != null) {
			pstm.setDate(2, new java.sql.Date(expedient.getDataLimitPresentacio().getTime()));
		} else {
			pstm.setDate(2, null);
		}
		if (expedient.getDataAdjudicacio() != null) {
			pstm.setDate(3, new java.sql.Date(expedient.getDataAdjudicacio().getTime()));
		} else {
			pstm.setDate(3, null);
		}
		if (expedient.getDataFormalitzacioContracte() != null) {
			pstm.setDate(4, new java.sql.Date(expedient.getDataFormalitzacioContracte().getTime()));
		} else {
			pstm.setDate(4, null);
		}
		if (expedient.getDataIniciExecucio() != null) {
			pstm.setDate(5, new java.sql.Date(expedient.getDataIniciExecucio().getTime()));
		} else {
			pstm.setDate(5, null);
		}
		if (expedient.getDataRecepcio() != null) {
			pstm.setDate(6, new java.sql.Date(expedient.getDataRecepcio().getTime()));
		} else {
			pstm.setDate(6, null);
		}
		if (expedient.getDataRetornGarantia() != null) {
			pstm.setDate(7, new java.sql.Date(expedient.getDataRetornGarantia().getTime()));
		} else {
			pstm.setDate(7, null);
		}
		if (expedient.getDataLiquidacio() != null) {
			pstm.setDate(8, new java.sql.Date(expedient.getDataLiquidacio().getTime()));
		} else {
			pstm.setDate(8, null);
		}
		pstm.setString(9, expedient.getGarantia());
		pstm.setString(10, expedient.getDescripcio());
		pstm.setString(11, expedient.getExpContratacio());
		pstm.executeUpdate();
		
	}
	
	public static String getNouCodiExpedient(Connection conn, InformeActuacio informe, double importObraMajor) throws SQLException {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		String nouCodi = "";		
		String contracte = "";
		if (informe.getPropostaInformeSeleccionada().getVec() < importObraMajor) { // Contractes menors		
			contracte = "menor";
		} else {																   // Contractes majors
			contracte = "major";
		}
		if (!informe.getPropostaInformeSeleccionada().isContracte()) {             // Fora contractes
			contracte = "pd";
		}
		String tipus = "";
		if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("obr")) {
			tipus = "obra";				
		} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("srv")) {
			tipus = "servei";			
		} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("submi")) {
			tipus = "subministrament";			
		}
				
		String sql = "SELECT expcontratacio"
					+ " FROM public.tbl_expedient"
					+ " WHERE contracte = '" + contracte + "' AND tipus = '" + tipus + "'"
					+ " ORDER BY expcontratacio DESC LIMIT 1;";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		System.out.println(pstm.toString());
		if (rs.next()) { //Codis nous
			String actualCode = rs.getString("expcontratacio");			
			int num = 0;
			String numFormatted = String.format("%03d", num + 1);	
			if (informe.getPropostaInformeSeleccionada().getVec() < importObraMajor) {
				if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("obr")) {				
					if (informe.getPropostaInformeSeleccionada().isContracte()) {
						num = Integer.valueOf(actualCode.split("/")[0].replace("OBM", "").trim());
						numFormatted = String.format("%03d", num + 1);		
						nouCodi = "OBM " + numFormatted + "/" + yearInString;	
					}else{
						nouCodi = "CA " + informe.getIdActuacio();		
					}	
				} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("srv")) {
					if (informe.getPropostaInformeSeleccionada().isContracte()) {
						num = Integer.valueOf(actualCode.split("/")[0].replace("SVM", "").trim());
						System.out.println(num);
						numFormatted = String.format("%03d", num + 1);		
						nouCodi = "SVM " + numFormatted + "/" + yearInString;	
					}else{
						nouCodi = "CA " + informe.getIdActuacio();	
					}
				} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("submi")) {
					if (informe.getPropostaInformeSeleccionada().isContracte()) {
						num = Integer.valueOf(actualCode.split("/")[0].replace("SBM", "").trim());
						numFormatted = String.format("%03d", num + 1);		
						nouCodi = "SBM " + numFormatted + "/" + yearInString;	
					}else{
						nouCodi = "CA " + informe.getIdActuacio();		
					}
				}
			} else {
				num = Integer.valueOf(actualCode.split("/")[0].trim());
				numFormatted = String.format("%03d", num + 1);		
				nouCodi = numFormatted + "/" + yearInString.subSequence(2,3);
			}
		}
		
		return nouCodi;
	}
}
