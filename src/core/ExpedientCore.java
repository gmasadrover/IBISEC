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
	static final String SQL_CAMPS = "e.expcontratacio AS expcontratacio, dataperfilcontratant, datalimitprsentacio," +
									" dataadjudicacio, dataformalitzaciocontracte, datainiciexecucio, datarecepcio," +
									" datafigarantia, dataretorngarantia, dataliquidacio, idinf, idactuacio, tipus, contracte, e.datacre AS datacre, e.anulat AS anulat, e.motiuanulacio AS motiuanulacio";
	
	private static Expedient initExpedient(Connection conn, ResultSet rs) {
		Expedient expedient = new Expedient();
		try {
			expedient.setExpContratacio(rs.getString("expcontratacio"));
			expedient.setIdActuacio(rs.getString("idactuacio"));
			expedient.setIdInforme(rs.getString("idinf"));	
			expedient.setDataPublicacioPerfilContratant(rs.getTimestamp("dataperfilcontratant"));
			expedient.setDataLimitPresentacio(rs.getTimestamp("datalimitprsentacio"));
			expedient.setDataAdjudicacio(rs.getTimestamp("dataadjudicacio"));
			expedient.setDataFormalitzacioContracte(rs.getTimestamp("dataformalitzaciocontracte"));
			expedient.setDataIniciExecucio(rs.getTimestamp("datainiciexecucio"));
			expedient.setDataRecepcio(rs.getTimestamp("datarecepcio"));
			expedient.setDataFiGarantia(rs.getTimestamp("datafigarantia"));
			expedient.setDataRetornGarantia(rs.getTimestamp("dataretorngarantia"));
			expedient.setDataLiquidacio(rs.getTimestamp("dataliquidacio"));			
			expedient.setTipus(rs.getString("tipus"));
			expedient.setDataCreacio(rs.getTimestamp("datacre"));
			expedient.setContracte(rs.getString("contracte"));
			expedient.setAnulat(rs.getBoolean("anulat"));
			expedient.setMotiuAnulament(rs.getString("motiuanulacio"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return expedient;
	}
	
	public static List<Expedient> getExpedients(Connection conn, String estat, String tipus, String contracte, double importObraMajor, String year) {
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
			if ("iniciExpedient".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND i.expcontratacio != '' AND i.databoib IS NULL AND e.dataadjudicacio IS NULL"; 
			if ("publicat".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND i.databoib IS NOT NULL AND e.datalimitprsentacio >= '" + dataFiString + "' AND e.dataadjudicacio IS NULL"; 
			if ("licitacio".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND i.databoib IS NOT NULL AND e.datalimitprsentacio < '" + dataFiString + "' AND e.dataadjudicacio IS NULL"; 	
			if ("adjudicacio".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.dataadjudicacio IS NOT NULL AND e.dataformalitzaciocontracte IS NULL"; 	
			if ("firmat".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND e.datarecepcio IS NULL AND i.databoib > '01/01/2016' AND (e.datainiciexecucio IS NULL OR e.datainiciexecucio > localtimestamp) AND e.dataformalitzaciocontracte IS NOT NULL"; 	
			if ("execucio".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND i.databoib > '01/01/2016' AND e.datainiciexecucio <= localtimestamp AND e.datarecepcio IS NULL"; 	
			if ("garantia".equals(estat)) sql+= " WHERE e.dataliquidacio IS NULL AND i.databoib > '01/01/2016' AND e.datarecepcio IS NOT NULL AND e.dataretorngarantia < localtimestamp"; 	
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
		if (year != null && !year.isEmpty() && ! year.equals("-1")) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE e.anyexpedient::INT = " + year;
							
			}else{
					sql += " AND e.anyexpedient::INT = " + year;
							
			}			
		}
		sql += " ORDER BY datacre DESC, expcontratacio DESC";
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {		
				expedientsList.add(initExpedient(conn, rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return expedientsList;
	}
	
	public static Expedient findExpedient(Connection conn, String referencia) {
		Expedient expedient = new Expedient();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_expedient e LEFT JOIN public.tbl_informeactuacio i ON e.expcontratacio = i.expcontratacio"
				+ " WHERE e.expcontratacio = ?";					
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) expedient = initExpedient(conn, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return expedient;
	}
	
	public static String crearExpedient(Connection conn, InformeActuacio informe, boolean senseCodiAutomatic, String codExpManual, int responsable) {
		String tipusContracte = "";
		String tipus = "";
		double importMajor = 0;
		if (informe.getPropostaInformeSeleccionada() != null) {
			if (informe.getPropostaInformeSeleccionada().getTipusObra() != null) {
				if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("obr")) {
					tipus = "obra";			
					importMajor = ConfiguracioCore.getConfiguracio(conn).getImportObraMajor();
				} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("srv")) {
					tipus = "servei";	
					importMajor = ConfiguracioCore.getConfiguracio(conn).getImportServeiMajor();
				} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("submi")) {
					tipus = "subministrament";	
					importMajor = ConfiguracioCore.getConfiguracio(conn).getImportSubministramentMajor();
				} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("conveni")) {
					tipus = "conveni";		
				}
			}
			if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("conveni")) {
				tipusContracte = "conveni";		
			} else {
				if (informe.getPropostaInformeSeleccionada().getPbase() < importMajor) { // Contractes menors		
					tipusContracte = "menor";				
				} else {																   // Contractes majors
					tipusContracte = "major";
				}	
			}							
			
		}
		String sql = "INSERT INTO public.tbl_expedient(expcontratacio, dataadjudicacio, tipus, contracte, datacre, anyexpedient, anulat)"
				+ " VALUES(?, ?, ?, ?, localtimestamp, ?, false);";					
		PreparedStatement pstm;
		String nouCodi = "";
		try {
			pstm = conn.prepareStatement(sql);
			
			if (senseCodiAutomatic) {
				nouCodi = informe.getIdInf();
				if (!codExpManual.isEmpty()) nouCodi = codExpManual;
				pstm.setString(1, nouCodi);
			}else{
				nouCodi = getNouCodiExpedient(conn, informe);
				pstm.setString(1, nouCodi);
			}
			if (informe.getDataAprovacio() != null) {
				pstm.setDate(2, new java.sql.Date(informe.getDataAprovacio().getTime()));		
			} else {
				pstm.setDate(2, null);			
			}
			pstm.setString(3, tipus);
			pstm.setString(4, tipusContracte);
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			String yearInString = String.valueOf(year);
			pstm.setString(5, yearInString);
			pstm.executeUpdate();
			
			InformeCore.assignarExpedient(conn, informe.getIdInf(), nouCodi);
			
			//Actualitzam personal associat
			sql = "INSERT INTO public.tbl_personaexpedient(idusuari, idinf, funcio, actiu, dataalta, relacioid)"
					+ " VALUES (?, ?, ?, true, localtimestamp, ?);";	
			pstm = conn.prepareStatement(sql);		
			pstm.setInt(1, responsable);
			pstm.setString(2, informe.getIdInf());
			pstm.setString(3, "Responsable contracte");
			pstm.setInt(4, InformeCore.getNewRelacioPersonalId(conn));
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		return nouCodi;
	}
	
	public static void actualitzarCodiExpedient(Connection conn, String refExp, String refExpNou, String idInforme) {
		String sql = "UPDATE public.tbl_expedient"
					+ " SET expcontratacio = ?"
					+ " WHERE expcontratacio = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, refExpNou);
			pstm.setString(2, refExp);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
				
		
		sql = "UPDATE public.tbl_informeactuacio"
				+ " SET expcontratacio = ?"
				+ " WHERE idinf = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, refExpNou);
			pstm.setString(2, idInforme);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
		
		sql = "UPDATE public.tbl_llicencia"
				+ " SET expcontratacio = ?"
				+ " WHERE expcontratacio = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, refExpNou);
			pstm.setString(2, refExp);
			pstm.executeUpdate();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	public static void anularExpedient(Connection conn, String idinf, String refExp, String motiuAnulacio) {
		String sql = "UPDATE public.tbl_expedient"
				+ " SET anulat = true, motiuanulacio = ?"
				+ " WHERE expcontratacio = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, motiuAnulacio);
			pstm.setString(2, refExp);
			pstm.executeUpdate();
			sql = "UPDATE public.tbl_informeactuacio"
				+ " SET estat = 'anulat', datatancament = localtimestamp"
				+ " WHERE idinf = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idinf);
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	public static void anularModificacioExpedient(Connection conn, String idModificacio, String motiuAnulacio) {
		String sql = "UPDATE public.tbl_modificacioinforme"
				+ " SET anulat = true, motiuanulat = ?"
				+ " WHERE idmodificacio = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, motiuAnulacio);
			pstm.setString(2, idModificacio);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	public static void updateExpedient(Connection conn, Expedient expedient) {
		String sql = "UPDATE public.tbl_expedient"
					+ " SET datalimitprsentacio=?, dataadjudicacio=?, dataformalitzaciocontracte=?, datainiciexecucio=?, datarecepcio=?, datafigarantia=?, dataretorngarantia=?, dataliquidacio=?, dataperfilcontratant=?"
					+ " WHERE expcontratacio=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			if (expedient.getDataLimitPresentacio() != null) {
				pstm.setDate(1, new java.sql.Date(expedient.getDataLimitPresentacio().getTime()));
			} else {
				pstm.setDate(1, null);
			}
			if (expedient.getDataAdjudicacio() != null) {
				pstm.setDate(2, new java.sql.Date(expedient.getDataAdjudicacio().getTime()));
			} else {
				pstm.setDate(2, null);
			}
			if (expedient.getDataFormalitzacioContracte() != null) {
				pstm.setDate(3, new java.sql.Date(expedient.getDataFormalitzacioContracte().getTime()));
			} else {
				pstm.setDate(3, null);
			}
			if (expedient.getDataIniciExecucio() != null) {
				pstm.setDate(4, new java.sql.Date(expedient.getDataIniciExecucio().getTime()));
			} else {
				pstm.setDate(4, null);
			}
			if (expedient.getDataRecepcio() != null) {
				pstm.setDate(5, new java.sql.Date(expedient.getDataRecepcio().getTime()));
			} else {
				pstm.setDate(5, null);
			}
			if (expedient.getDataFiGarantia() != null) {
				pstm.setDate(6, new java.sql.Date(expedient.getDataFiGarantia().getTime()));
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
			if (expedient.getDataPublicacioPerfilContratant() != null) {
				pstm.setDate(9, new java.sql.Date(expedient.getDataPublicacioPerfilContratant().getTime()));
			} else {
				pstm.setDate(9, null);
			}
			pstm.setString(10, expedient.getExpContratacio());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
				
	}
	
	public static List<String> getAnysExpedients(Connection conn) {
		List<String> anysList = new ArrayList<String>();
		String sql = "SELECT DISTINCT anyexpedient"
					+ " FROM public.tbl_expedient"
					+ " WHERE anyexpedient IS NOT NULL"
					+ " ORDER BY anyexpedient DESC";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				anysList.add(rs.getString("anyexpedient"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return anysList;
	}
	
	public static String getNouCodiExpedient(Connection conn, InformeActuacio informe) {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		String nouCodi = "";		
		String contracte = "";
		double importMajor = 0;
		String tipus = "";
		if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("obr")) {
			tipus = "OBM";				
			importMajor = ConfiguracioCore.getConfiguracio(conn).getImportObraMajor();
		} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("srv")) {
			tipus = "SVM";			
			importMajor = ConfiguracioCore.getConfiguracio(conn).getImportServeiMajor();
		} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("submi")) {
			tipus = "SBM";	
			importMajor = ConfiguracioCore.getConfiguracio(conn).getImportSubministramentMajor();
		} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("conveni")) {
			tipus = "Conveni";			
		}			
		if (tipus.equals("Conveni")) {
			contracte = "conveni";
			yearInString = yearInString.substring(2,4);
		} else {		
			if (informe.getPropostaInformeSeleccionada().getPbase() < importMajor) { // Contractes menors		
				contracte = "menor";			
			} else {																   // Contractes majors
				contracte = "major";
				yearInString = yearInString.substring(2,4);
			}	
		}
		
		String sql = "SELECT expcontratacio"
					+ " FROM public.tbl_expedient"
					+ " WHERE tipus != 'conveni' AND expcontratacio like '%/" + yearInString + "%' AND contracte = '" + contracte + "'"
					+ " ORDER BY expcontratacio DESC LIMIT 1;";	 
		if (!contracte.equals("major")) {
			sql = "SELECT expcontratacio"
					+ " FROM public.tbl_expedient"
					+ " WHERE tipus != 'conveni' AND expcontratacio like '" + tipus + "%/" + yearInString + "%'"
					+ " ORDER BY expcontratacio DESC LIMIT 1;";	 
		}
		if (tipus.equals("Conveni")) {
			sql = "SELECT expcontratacio"
					+ " FROM public.tbl_expedient"
					+ " WHERE tipus = 'conveni' AND expcontratacio like '" + tipus + "%/" + yearInString + "%'"
					+ " ORDER BY expcontratacio DESC LIMIT 1;";	 
		}
		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();
				if (rs.next()) { //Codis nous
					String actualCode = rs.getString("expcontratacio");			
					int num = 0;
					String numFormatted = String.format("%03d", num + 1);	
					if (tipus.equals("Conveni")) {
						num = Integer.valueOf(actualCode.split("/")[0].replace("Conveni", "").trim());
						numFormatted = String.format("%03d", num + 1);		
						nouCodi = "Conveni " + numFormatted + "/" + yearInString;
					} else {
						if (informe.getPropostaInformeSeleccionada().getPbase() < importMajor) {
							if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("obr")) {				
								num = Integer.valueOf(actualCode.split("/")[0].replace("OBM", "").trim());
								numFormatted = String.format("%03d", num + 1);		
								nouCodi = "OBM " + numFormatted + "/" + yearInString;						
							} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("srv")) {					
								num = Integer.valueOf(actualCode.split("/")[0].replace("SVM", "").trim());						
								numFormatted = String.format("%03d", num + 1);		
								nouCodi = "SVM " + numFormatted + "/" + yearInString;						
							} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("submi")) {
								num = Integer.valueOf(actualCode.split("/")[0].replace("SBM", "").trim());
								numFormatted = String.format("%03d", num + 1);		
								nouCodi = "SBM " + numFormatted + "/" + yearInString;	
							}
						} else {
							num = Integer.valueOf(actualCode.split("/")[0].trim());
							numFormatted = String.format("%03d", num + 1);		
							nouCodi = numFormatted + "/" + yearInString;
						}
					}
				} else {
					if (informe.getPropostaInformeSeleccionada().getPbase() < importMajor) {
						if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("obr")) {				
							nouCodi = "OBM 001/" + yearInString;					
						} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("srv")) {
							nouCodi = "SVM 001/" + yearInString;					
						} else if(informe.getPropostaInformeSeleccionada().getTipusObra().equals("submi")) {
							nouCodi = "SBM 001/" + yearInString;					
						}
					} else {				
						nouCodi = "001/" + yearInString;
					}
				}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nouCodi;
	}
}
