package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.InformeActuacio;
import bean.Llicencia;
import bean.Partida;
import utils.Fitxers;
import utils.Fitxers.Fitxer;

public class LlicenciaCore {
	static final String SQL_CAMPS = "codi, expcontratacio, tipus, taxa, observacio, datapagadataxa, datapagadaico, importatib, ico, datasolicitud, dataconcesio";
	
	private static Llicencia initLlicencia(Connection conn, ResultSet rs) {
		Llicencia llicencia = new Llicencia();
		try {
			llicencia.setCodi(rs.getString("codi"));
			llicencia.setCodiExpedient(rs.getString("expcontratacio"));
			llicencia.setTipus(rs.getString("tipus"));
			llicencia.setTaxa(rs.getDouble("taxa"));
			llicencia.setIco(rs.getDouble("ico"));		
			llicencia.setObservacio(rs.getString("observacio"));
			llicencia.setPeticio(rs.getTimestamp("datasolicitud"));
			llicencia.setConcesio(rs.getTimestamp("dataconcesio"));
			llicencia.setPagamentTaxa(rs.getTimestamp("datapagadataxa"));
			llicencia.setPagamentICO(rs.getTimestamp("datapagadaico"));
			//llicencia.setArxius(getArxius(rs.getString("codi"), conn, idIncidencia, idInforme));
			llicencia.setDocumentSolLlicencia(getDocumentSolLlicenciaString(conn, rs.getString("codi")));
			llicencia.setDocumentConcessioLlicencia(getDocumentConcessioLlicencia(conn, rs.getString("codi")));
			llicencia.setDocumentPagamentLlicencia(getDocumentPagamentLlicencia(conn, rs.getString("codi")));
			llicencia.setDocumentTitolHabilitant(getDocumentTitolHabilitant(conn, rs.getString("codi")));
			llicencia.setIdPartida(getPartidaLlicencia(conn, rs.getString("codi")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return llicencia;
	}
	
	public static List<Llicencia> getLlicencies(Connection conn, String estat, String tipus) {
		List<Llicencia> llicencies = new ArrayList<Llicencia>();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_llicencia";		
		boolean primeraCondicio = true;
		if (!tipus.isEmpty()) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE tipus = ?";
			}			
		}
		if (!estat.isEmpty()) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE";
			} else {
				sql += " AND";
			}	
			if ("pendent".equals(estat)) {
				sql += " datasolicitud IS NULL";
			}
			if ("solicitad".equals(estat)) {
				sql += " datasolicitud IS NOT NULL AND dataconcesio IS NULL";
			}
			if ("concedida".equals(estat)) {
				sql += " dataconcesio IS NOT NULL AND datapagada IS NULL";
			}
			if ("pagada".equals(estat)) {
				sql += " datapagada IS NOT NULL";
			}
		}
		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			int contVars = 1;
			if (!tipus.isEmpty()) {
				pstm.setString(contVars, tipus);
				contVars += 1;
			}		
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {		
				llicencies.add(initLlicencia(conn, rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		return llicencies;
	}
	
	public static Llicencia findLlicencia(Connection conn, String codi) {
		Llicencia llicencia = new Llicencia();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_llicencia"
				+ " WHERE codi = ?";					
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codi);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) llicencia = initLlicencia(conn, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return llicencia;
	}
	
	public static Llicencia findLlicenciaExpedient(Connection conn, String idInforme, String idIncidencia) {
		Llicencia llicencia = new Llicencia();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_llicencia"
				+ " WHERE expcontratacio = ?";					
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idInforme);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) llicencia = initLlicencia(conn, rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return llicencia;
	}
	
	public static void updateLlicencia(Connection conn, Llicencia llicencia) {
		String sql = "UPDATE public.tbl_llicencia"
					+ " SET taxa=?, observacio=?, datapagadataxa=?, datapagadaico=?, ico=?, datasolicitud=?, dataconcesio=?"
					+ " WHERE codi = ?";	
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setDouble(1, llicencia.getTaxa());
			pstm.setString(2, llicencia.getObservacio());
			if (llicencia.getPagamentTaxa() != null) {
				pstm.setDate(3, new java.sql.Date(llicencia.getPagamentTaxa().getTime()));
			} else {
				pstm.setDate(3, null);
			}
			if (llicencia.getPagamentICO() != null) {
				pstm.setDate(4, new java.sql.Date(llicencia.getPagamentICO().getTime()));
			} else {
				pstm.setDate(4, null);
			}
			pstm.setDouble(5, llicencia.getIco());
			if (llicencia.getPeticio() != null) {
				pstm.setDate(6, new java.sql.Date(llicencia.getPeticio().getTime()));
			} else {
				pstm.setDate(6, null);
			}
			if (llicencia.getConcesio() != null) {
				pstm.setDate(7, new java.sql.Date(llicencia.getConcesio().getTime()));
			} else {
				pstm.setDate(7, null);
			}
			pstm.setString(8, llicencia.getCodi());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	public static String novaLlicencia(Connection conn, String expContractacio, Llicencia llicencia) {
		String sql = "INSERT INTO public.tbl_llicencia(codi, expcontratacio, tipus, taxa, observacio, datapagadataxa, ico, datasolicitud, dataconcesio, datacre, datapagadaico)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, localtimestamp, ?);";	
		PreparedStatement pstm;
		String codi = getNouCodi(conn);
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codi);
			pstm.setString(2, llicencia.getCodiExpedient());
			pstm.setString(3, llicencia.getTipus());
			pstm.setDouble(4, llicencia.getTaxa());
			pstm.setString(5, llicencia.getObservacio());
			if (llicencia.getPagamentTaxa() != null) {
				pstm.setDate(6, new java.sql.Date(llicencia.getPagamentTaxa().getTime()));
			} else {
				pstm.setDate(6, null);
			}
			pstm.setDouble(7, llicencia.getIco());
			if (llicencia.getPeticio() != null) {
				pstm.setDate(8, new java.sql.Date(llicencia.getPeticio().getTime()));
			} else {
				pstm.setDate(8, null);
			}
			if (llicencia.getConcesio() != null) {
				pstm.setDate(9, new java.sql.Date(llicencia.getConcesio().getTime()));
			} else {
				pstm.setDate(9, null);
			}
			if (llicencia.getPagamentICO() != null) {
				pstm.setDate(10, new java.sql.Date(llicencia.getPagamentICO().getTime()));
			} else {
				pstm.setDate(10, null);
			}
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		return codi;
	}
	
	private static String getNouCodi(Connection conn) {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		String code = "1";
		String sql = "SELECT codi, datacre"
					+ " FROM public.tbl_llicencia"
					+ " WHERE codi like '%" + yearInString + "-LLI%'"
					+ " ORDER BY codi DESC, datacre DESC LIMIT 1;";	
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();		
			String prefix = "LLI";
			if (rs.next()) { //Codis nous
				String actualCode = rs.getString("codi");			
				int num = Integer.valueOf(actualCode.split("-")[2]);
				String numFormatted = String.format("%04d", num + 1);
				code = yearInString + "-" + prefix + "-" + numFormatted;
			} else { //Codis antics
				int num = 0;		
				String numFormatted = String.format("%04d", num + 1);
				code = yearInString + "-" + prefix + "-" + numFormatted;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return code;
	}
	
	public static void guardarArxiu(Connection conn, List<Fitxer> fitxers, String codi, int idUsuari, String seccio) {
		if (fitxers != null) {
			String fileName = "";
			// Crear directoris si no existeixen
			
			String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
			File tmpFile = new File(ruta + "/documents/Llicencies");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/Llicencies/" + codi);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			fileName = ruta + "/documents/Llicencies/" + codi + "/";
			if (!seccio.isEmpty()) {
				tmpFile = new File(ruta + "/documents/Llicencies/" + codi + "/" + seccio);
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}
				fileName = ruta + "/documents/Llicencies/" + codi + "/" + seccio + "/";
			}
			
	        for(int i=0;i<fitxers.size();i++){		        	
	            Fitxer fitxer = (Fitxer) fitxers.get(i);	           
	            if (fitxer.getFitxer().getName() != "") {	
	            	File archivo_server = new File(fileName + fitxer.getFitxer().getName());
	               	try {
	               		fitxer.getFitxer().write(archivo_server);
	               		Fitxers.guardarRegistreFitxer(conn, fitxer.getFitxer().getName(), fileName  + "/" + fitxer.getFitxer().getName(), idUsuari);
	           		} catch (Exception e) {
	           			e.printStackTrace();
	           		}
	            } 
	        }
		}
	}
	
	/*private static List<Fitxers.Fitxer> getArxius(String codi, Connection conn, String idIncidencia, String idInforme) {
		List<Fitxer> fitxersList = new ArrayList<Fitxer>();
		
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Llicencies/" + codi);
		 File[] fichers = dir.listFiles();
		 Fitxer fitxer = new Fitxer();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 if (!fichers[x].isDirectory()) {
					 fitxer = new Fitxer();
					 fitxer.setNom(fichers[x].getName());
					 fitxer.setRuta(ruta + "/documents/Llicencies/" + codi + "/" + fichers[x].getName());
					 try {
						fitxer.setData(Files.getLastModifiedTime(fichers[x].toPath()));
					 } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					 }
					 fitxersList.add(fitxer);
				 }
			 }
		 }
		 fitxersList.addAll(RegistreCore.getArxiusAdjuntsPerTipus(conn, idIncidencia, idInforme, "Autorització urbanística"));
		 return fitxersList;
	}*/
	
	private static List<Fitxer> getDocumentSolLlicenciaString(Connection conn, String codi) {
		List<Fitxer> fitxersList = new ArrayList<Fitxer>();
	 
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		File dir = new File(ruta + "/documents/Llicencies/" + codi + "/Solicitud");
		File[] fichers = dir.listFiles();
		Fitxer fitxer = new Fitxer();
		if (fichers == null) {
			
		} else { 
			for (int x=0;x<fichers.length;x++) {
				fitxer = new Fitxer();
				fitxer.setNom(fichers[x].getName());
				fitxer.setRuta(ruta + "/documents/Llicencies/" + codi + "/Solicitud/" + fichers[x].getName());
				try {
					fitxer.setData(Files.getLastModifiedTime(fichers[x].toPath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fitxersList.add(fitxer);
			}
		}
		return fitxersList;
	}
	private static List<Fitxer> getDocumentConcessioLlicencia(Connection conn, String codi) {
		List<Fitxer> fitxersList = new ArrayList<Fitxer>();
	   
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		File dir = new File(ruta + "/documents/Llicencies/" + codi + "/Concessio");
		File[] fichers = dir.listFiles();
		Fitxer fitxer = new Fitxer();
		if (fichers == null) {
			
		} else { 
			for (int x=0;x<fichers.length;x++) {
				fitxer = new Fitxer();
				fitxer.setNom(fichers[x].getName());
				fitxer.setRuta(ruta + "/documents/Llicencies/" + codi + "/Concessio/" + fichers[x].getName());
				try {
					fitxer.setData(Files.getLastModifiedTime(fichers[x].toPath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fitxersList.add(fitxer);
			}
		}
		return fitxersList;
	}
	private static List<Fitxer> getDocumentPagamentLlicencia(Connection conn, String codi) {
		List<Fitxer> fitxersList = new ArrayList<Fitxer>();
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		File dir = new File(ruta + "/documents/Llicencies/" + codi + "/Pagament");
		File[] fichers = dir.listFiles();
		Fitxer fitxer = new Fitxer();
		if (fichers == null) {
			
		} else { 
			for (int x=0;x<fichers.length;x++) {
				fitxer = new Fitxer();
				fitxer.setNom(fichers[x].getName());
				fitxer.setRuta(ruta + "/documents/Llicencies/" + codi + "/Pagament/" + fichers[x].getName());
				try {
					fitxer.setData(Files.getLastModifiedTime(fichers[x].toPath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fitxersList.add(fitxer);
			}
		}
		return fitxersList;
	}
	private static List<Fitxer> getDocumentTitolHabilitant(Connection conn, String codi) {
		List<Fitxer> fitxersList = new ArrayList<Fitxer>();
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		File dir = new File(ruta + "/documents/Llicencies/" + codi + "/Habilitant");
		File[] fichers = dir.listFiles();
		Fitxer fitxer = new Fitxer();
		if (fichers == null) {
			
		} else { 
			for (int x=0;x<fichers.length;x++) {
				fitxer = new Fitxer();
				fitxer.setNom(fichers[x].getName());
				fitxer.setRuta(ruta + "/documents/Llicencies/" + codi + "/Habilitant/" + fichers[x].getName());
				try {
					fitxer.setData(Files.getLastModifiedTime(fichers[x].toPath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fitxersList.add(fitxer);
			}
		}
		return fitxersList;
	}
	
	public static String getPartidaLlicencia(Connection conn, String codi) {
		String idPartida = "";
		String sql = "SELECT idpartida"
				+ " FROM public.tbl_assignacionscredit"
				+ " WHERE idinf = ?";					
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codi);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) idPartida = rs.getString("idpartida");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return idPartida;
	}
	
	public static void actualitzarPartida(Connection conn, Llicencia llicencia, double valor, InformeActuacio informe) {	
		if (valor > 0) {
			if (informe.getAssignacioCredit() != null  && informe.getAssignacioCredit().size()>0 && informe.getAssignacioCredit().get(0).getPartida() != null) {
				if (llicencia.getIdPartida() == null || llicencia.getIdPartida().isEmpty()) {
					String sql = "INSERT INTO public.tbl_assignacionscredit(idassignacio, idactuacio, idinf, reserva, assignacio, idpartida, valorpa, valorpd)"
								+ " VALUES (?, ?, ?, true, true, ?, ?, ?);";
					PreparedStatement pstm;
					try {
						pstm = conn.prepareStatement(sql);
						pstm.setString(1, llicencia.getCodi());
						pstm.setString(2, informe.getActuacio().getReferencia());
						pstm.setString(3, llicencia.getCodi());
						Partida partida = new Partida();
						if (informe.getAssignacioCredit() != null && informe.getAssignacioCredit().get(0).getPartida() != null) {
							partida = CreditCore.getPartida(conn, informe.getAssignacioCredit().get(0).getPartida().getCodi());
							if (partida.getEstat()) {
								if (partida.getPartidaPerAsignar() >= valor) {
									pstm.setString(4, informe.getAssignacioCredit().get(0).getPartida().getCodi());
								} else {
									
								}
							} else {
								partida = CreditCore.getPartidaDefecte(conn);
								if (partida.getPartidaPerAsignar() >= valor) {
									pstm.setString(4, partida.getCodi());
								} else {
									
								}							
							}						
						} else {
							partida = CreditCore.getPartidaDefecte(conn);
							pstm.setString(4, partida.getCodi());
						}			
						pstm.setDouble(5, valor);
						pstm.setDouble(6, valor);
						pstm.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
				} else {
					String sql = "UPDATE public.tbl_assignacionscredit"
								+ " SET valorpa=?, valorpd=?"
								+ " WHERE idassignacio = ?;";
					PreparedStatement pstm;
					try {
						pstm = conn.prepareStatement(sql);
						pstm.setDouble(1, valor);
						pstm.setDouble(2, valor);
						pstm.setString(3, llicencia.getCodi());
						pstm.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
				}
			} else {
				// assignar partida manual?
			}
		}		
	}
}
