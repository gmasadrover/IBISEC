package core;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import bean.Actuacio;
import bean.Centre;
import bean.Historic;
import bean.InformeActuacio;
import bean.Registre;
import bean.Tasca;
import bean.User;
import utils.Fitxers;
import utils.Fitxers.Fitxer;

public class TascaCore {
	
	private static Tasca initTasca(Connection conn, ResultSet rs, boolean complet) {
		Tasca tasca = new Tasca();
		try {
			tasca.setIdTasca(rs.getInt("idtasca"));
			tasca.setUsuari(UsuariCore.findUsuariByID(conn,rs.getInt("idusuari")));
			tasca.setDepartament(rs.getString("departament"));
			Actuacio actuacio = new Actuacio();
			if (rs.getString("idactuacio") != null && !rs.getString("idactuacio").equals("-1"))  {
				tasca.setActuacio(ActuacioCore.findActuacio(conn,  rs.getString("idactuacio")));
			}
			if (rs.getString("idincidencia") != null && !rs.getString("idincidencia").equals("-1"))  {
				tasca.setIncidencia(IncidenciaCore.findIncidencia(conn, rs.getString("idincidencia")));
			} else {			
				Registre registre = RegistreCore.findRegistre(conn, "E", rs.getString("registre"));	
				if (registre.getIdCentres() != null && registre.getIdCentres() != "") {
					Centre centre = CentreCore.findCentre(conn, registre.getIdCentresList().get(0), false);
					actuacio.setCentre(centre);
				}			
				actuacio.setReferencia("0");
				tasca.setActuacio(actuacio);
			}
			
			tasca.setIdActuacio(rs.getString("idactuacio"));
			tasca.setActiva(rs.getBoolean("activa"));
			tasca.setDescripcio(rs.getString("descripcio"));
			tasca.setTipus(rs.getString("tipus"));
			tasca.setDataCreacio(TascaCore.findInici(conn, rs.getInt("idtasca")));
			tasca.setIdinforme(rs.getString("idinforme"));
			tasca.setIdinformeOriginal(rs.getString("idinforme"));
			tasca.setPrioritat(rs.getInt("prioritat"));		
			tasca.setRegistre(rs.getString("registre"));
			tasca.setUsuCre(rs.getString("usucre"));
			if (rs.getString("idinforme") != null) {
				if (NumberUtils.isNumber(rs.getString("idinforme")) || rs.getString("idinforme").contains("-INF-")) {
					tasca.setInforme(InformeCore.getInformePreviInfo(conn, rs.getString("idinforme")));
				} else if (rs.getString("idinforme").contains("-MOD-")) {
					InformeActuacio informe = InformeCore.getMoficacioInforme(conn, rs.getString("idinforme"), complet);
					tasca.setInforme(informe);
					tasca.setIdinformeOriginal(informe.getIdInfOriginal());
				}
			}		
			tasca.setLlegida(rs.getBoolean("llegida"));		
			List<Historic> comentaris = findHistorial(conn, rs.getInt("idtasca"), rs.getString("idincidencia"), rs.getString("idactuacio"));
			if (comentaris.size() > 0) {
				tasca.setPrimerComentari(comentaris.get(0).getComentari());
				tasca.setDarrerComentari(comentaris.get(comentaris.size() - 1).getComentari());
			}
			tasca.setDarreraModificacio(findDarreraModificacioHistorial(conn, rs.getInt("idtasca"), rs.getString("idincidencia"), rs.getString("idactuacio")));
			if (complet) tasca.setDocuments(getDocuments(conn, rs.getInt("idtasca")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return tasca;		
	}
	
	public static Tasca findTascaId(Connection conn, int idTasca, int idUsuari) {
		String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament, prioritat, registre, usucre"
					+ " FROM public.tbl_tasques"
					+ " WHERE idtasca = ?";	 
		 PreparedStatement pstm;
		 Tasca tasca = new Tasca();
		try {
			pstm = conn.prepareStatement(sql);
			 pstm.setInt(1, idTasca);		
			 ResultSet rs = pstm.executeQuery();
			 
			 if (rs.next()) {
				 tasca = initTasca(conn, rs, true);
				 tasca.setSeguiment(isSeguiment(conn,tasca.getIdTasca(), idUsuari));
				 if (tasca.getActuacio() != null) tasca.setSeguimentActuacio(ActuacioCore.isSeguimentActuacio(conn,tasca.getActuacio().getReferencia(), idUsuari));
			 }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	     return tasca;
	}
	
	public static void assignarPrioritat(Connection conn, int idTasca, int prioritat) {
		String sql = "UPDATE public.tbl_tasques"
				+ " SET prioritat=?"
				+ " WHERE idtasca=?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, prioritat);	
			pstm.setInt(2, idTasca);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static List<List<Tasca>> llistaTasquesUsuari(Connection conn, int idUsuari, String area, boolean withTancades) {
		List<List<Tasca>> list = new ArrayList<List<Tasca>>();
		List<Tasca> infPrevList = new ArrayList<Tasca>();
		List<Tasca> solInfPrevList = new ArrayList<Tasca>();
		List<Tasca> vistInfPrevList = new ArrayList<Tasca>();
        List<Tasca> redacDocTecnicaList = new ArrayList<Tasca>();
        List<Tasca> vistDocTecnicaList = new ArrayList<Tasca>();        
        List<Tasca> sigDocExpList = new ArrayList<Tasca>();
        List<Tasca> propAdjList = new ArrayList<Tasca>();
        List<Tasca> resAdjList = new ArrayList<Tasca>();
        List<Tasca> redContracteList = new ArrayList<Tasca>();
        List<Tasca> resCreditList = new ArrayList<Tasca>();
        List<Tasca> altresList = new ArrayList<Tasca>();  
        List<Tasca> docPreLicitacioList = new ArrayList<Tasca>();
        List<Tasca> judicialList = new ArrayList<Tasca>();
        List<Tasca> conformarFacturaList = new ArrayList<Tasca>();    
        List<Tasca> revisarCertificacioList = new ArrayList<Tasca>();
        List<Tasca> contractesList = new ArrayList<Tasca>();
        List<Tasca> contractesFirmaList = new ArrayList<Tasca>();
        List<Tasca> notificacionsList = new ArrayList<Tasca>();
        List<Tasca> reservaDronList = new ArrayList<Tasca>();
		String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament, prioritat, registre, usucre"
				 	+ " FROM public.tbl_tasques";
		if (idUsuari > -1) {
			sql  += " WHERE idusuari = ?";
			if (! withTancades) sql += " AND activa = true";
		} else if (area != null) {
			sql  += " WHERE departament = ?";
			if (! withTancades) sql += " AND activa = true";
		} else if (! withTancades) sql += " WHERE activa = true";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			if (idUsuari > -1) {
				pstm.setInt(1, idUsuari);
			} else if (area != null) {
				pstm.setString(1, area);
			}			
			ResultSet rs = pstm.executeQuery();
			
			while (rs.next()) {			
				if (rs.getString("tipus").equals("infPrev")) { // 0
					infPrevList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("solInfPrev")) { // 1
					solInfPrevList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("vistInfPrev")) { // 2
					vistInfPrevList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("doctecnica")) { // 3
					redacDocTecnicaList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("vistDocTecnica")) { // 4
					vistDocTecnicaList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("autoritzacioActuacio")) { // 5
					sigDocExpList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("liciMenor")) { // 6
					propAdjList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("autoritzacioDespesa") || rs.getString("tipus").equals("autoritzacioModificacio") || rs.getString("tipus").equals("autoritzacioPenalitzacio")) { // 7
					resAdjList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("doctecnica")) { // 8
					redContracteList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("resPartida") || rs.getString("tipus").equals("resPartidaModificacio")) { // 9
					resCreditList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("docprelicitacio") || rs.getString("tipus").equals("certificatCreditGerencia") || rs.getString("tipus").equals("preLicitacio")) { // 10
					docPreLicitacioList.add(initTasca(conn, rs, false));			
				} else if (rs.getString("tipus").equals("judicial") || rs.getString("tipus").equals("pagamentJudicial") || rs.getString("tipus").equals("partidaJudicial")) { // 11
					judicialList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("conformarFactura")) { // 12
					conformarFacturaList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("revisarCertificacio") || rs.getString("tipus").equals("firmaCertificacio") || rs.getString("tipus").equals("certificacioFirmada")) { // 13
					revisarCertificacioList.add(initTasca(conn, rs, false));
				} else if (rs.getString("tipus").equals("contracte")) {
					contractesList.add(initTasca(conn, rs, false)); // 14
				} else if (rs.getString("tipus").equals("firmaContracte")) {
					contractesFirmaList.add(initTasca(conn, rs, false)); // 15
				} else if (rs.getString("tipus").equals("notificacio")) {
					notificacionsList.add(initTasca(conn, rs, false)); // 17
				} else if (rs.getString("tipus").equals("reservaDron")) {
					reservaDronList.add(initTasca(conn, rs, false)); // 17
				}else {
					altresList.add(initTasca(conn, rs, false)); // 16
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
				
		list.add(infPrevList); // 0
        list.add(solInfPrevList); // 1
        list.add(vistInfPrevList); // 2
        list.add(redacDocTecnicaList); // 3
        list.add(vistDocTecnicaList);  // 4
        list.add(sigDocExpList); // 5
        list.add(propAdjList); // 6
        list.add(resAdjList);  // 7
        list.add(redContracteList);  // 8
        list.add(resCreditList); // 9
        list.add(docPreLicitacioList);  // 10
        list.add(judicialList);  // 11
        list.add(conformarFacturaList); // 12
        list.add(revisarCertificacioList); // 13
        list.add(contractesList);  // 14
        list.add(contractesFirmaList);  // 15 
        list.add(altresList);  // 16 
        list.add(notificacionsList);  // 17 
        list.add(reservaDronList); // 18
	  	return list;
    }
	
	public static List<Tasca> llistaTasquesSeguiment(Connection conn, int idUsuari) {
		String sql = "SELECT t.idtasca AS idtasca, t.idusuari AS idusuari, t.idactuacio AS idactuacio, t.idincidencia AS idincidencia, t.descripcio AS descripcio, t.tipus AS tipus, t.activa AS activa, t.idinforme AS idinforme, t.llegida AS llegida, t.departament AS departament, t.prioritat AS prioritat, t.registre AS registre, t.usucre AS usucre"
					+ " FROM public.tbl_seguiments s LEFT JOIN public.tbl_tasques t ON s.idtasca = t.idtasca"
					+ " WHERE s.idusuari = ? AND tipus NOT LIKE '%notificacio%'";
		PreparedStatement pstm;
		List<Tasca> list = new ArrayList<Tasca>();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);		
			ResultSet rs = pstm.executeQuery();
			
			while (rs.next()) {
				Tasca tasca = initTasca(conn, rs, false);
				list.add(tasca);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	    return list;
	}
	
	public static List<Tasca> llistaNotificacionsUsuari(Connection conn, int idUsuari) {
		 String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament, prioritat, registre"
				 	+ " FROM public.tbl_tasques"
				 	+ " WHERE idusuari = ? AND activa = true AND tipus LIKE '%notificacio%'";	 
		 PreparedStatement pstm;
		 List<Tasca> list = new ArrayList<Tasca>();
		try {
			pstm = conn.prepareStatement(sql);
			 pstm.setInt(1, idUsuari);		
			 ResultSet rs = pstm.executeQuery();
			
			 while (rs.next()) {
				 Tasca tasca = initTasca(conn, rs, false);
				 list.add(tasca);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	     return list;
   }
	
	public static void llegirNotificacio(Connection conn, int idNotificacio) {
		String sql = "UPDATE public.tbl_tasques"
				+ " SET llegida=true"
				+ " WHERE idtasca=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);	 
			pstm.setInt(1, idNotificacio);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static int numNotificacions(Connection conn, int idUsuari) {
		int notificacions = 0;
		String sql = "SELECT COUNT(*) AS notificacions"
			 	+ " FROM public.tbl_tasques"
			 	+ " WHERE idusuari = ? AND activa = true AND tipus LIKE '%notificacio%' AND llegida = false";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);		
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				notificacions = rs.getInt("notificacions");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
	
		return notificacions;
	}
	
	public static List<Tasca> findTasquesActuacio(Connection conn, String referencia, boolean withCancelades) {
		 String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament, prioritat, registre, usucre"
				 	+ " FROM public.tbl_tasques"
				 	+ " WHERE idactuacio = ? AND tipus NOT LIKE '%notificacio%'";	 
		 if (!withCancelades) sql += " AND activa = true";
		 PreparedStatement pstm;
		 List<Tasca> list = new ArrayList<Tasca>();
		try {
			pstm = conn.prepareStatement(sql);
			 pstm.setString(1, referencia);		
			 ResultSet rs = pstm.executeQuery();
			
			 while (rs.next()) {
				 Tasca tasca = initTasca(conn, rs, false);
				 list.add(tasca);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	     return list;
	}
	
	public static List<Tasca> findNotificacionsActuacio(Connection conn, String referencia)  {
		 String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament, prioritat, registre, usucre"
				 	+ " FROM public.tbl_tasques"
				 	+ " WHERE idactuacio = ? AND tipus LIKE '%notificacio%'";	 
		 PreparedStatement pstm;
		 List<Tasca> list = new ArrayList<Tasca>();
		try {
			pstm = conn.prepareStatement(sql);
			 pstm.setString(1, referencia);		
			 ResultSet rs = pstm.executeQuery();
			
			 while (rs.next()) {
				 Tasca tasca = initTasca(conn, rs, false);
				 list.add(tasca);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	     return list;
	}
	
	public static List<Tasca> findTasquesIncidencia(Connection conn, String referencia) {
		List<Tasca> list = new ArrayList<Tasca>(); 
		String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament, prioritat, registre, usucre"
				 	+ " FROM public.tbl_tasques"
				 	+ " WHERE idincidencia = ? AND tipus NOT LIKE '%notificacio%'";	 
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);		
			 ResultSet rs = pstm.executeQuery();
			 
			 while (rs.next()) {
				 Tasca tasca = initTasca(conn, rs, false);
				 list.add(tasca);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		 
	     return list;
	}
	
	public static List<Tasca> findNotificacionsIncidencia(Connection conn, String referencia) {
		 List<Tasca> list = new ArrayList<Tasca>();
		String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament, prioritat, registre, usucre"
				 	+ " FROM public.tbl_tasques"
				 	+ " WHERE idincidencia = ? AND tipus LIKE '%notificacio%'";	 
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);		
			 ResultSet rs = pstm.executeQuery();
			
			 while (rs.next()) {
				 Tasca tasca = initTasca(conn, rs, false);
				 list.add(tasca);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		 
	     return list;
	}
	
	public static List<Tasca> findTasquesJudicial(Connection conn, String referencia) {
		 List<Tasca> list = new ArrayList<Tasca>(); 
		String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament, prioritat, registre, usucre"
				 	+ " FROM public.tbl_tasques"
				 	+ " WHERE idinforme = ?";	 
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);		
			 ResultSet rs = pstm.executeQuery();
			
			 while (rs.next()) {
				 Tasca tasca = initTasca(conn, rs, false);
				 list.add(tasca);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		 
	     return list;
	}
	
	
	public static List<Tasca> findTasquesRegistre(Connection conn, String referencia) {
		List<Tasca> list = new ArrayList<Tasca>(); 
		String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament, prioritat, registre, usucre"
				 	+ " FROM public.tbl_tasques"
				 	+ " WHERE registre = ?";	 
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);		
			 ResultSet rs = pstm.executeQuery();
			
			 while (rs.next()) {
				 Tasca tasca = initTasca(conn, rs, false);
				 list.add(tasca);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		 
	     return list;
	}
	
	public static Tasca findTascaVacances(Connection conn, int idSolicitud) {
		 Tasca tasca = new Tasca(); 
		String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament, prioritat, registre, usucre"
				 	+ " FROM public.tbl_tasques"
				 	+ " WHERE tipus = 'vacances' AND activa = true AND idinforme = ?" ;	 
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			 pstm.setString(1, Integer.toString(idSolicitud));		
			 ResultSet rs = pstm.executeQuery();
			
			 if (rs.next()) tasca = initTasca(conn, rs, false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	     return tasca;
	}
	
	public static List<Tasca> findTasquesInforme(Connection conn, String idInforme, boolean withCancelades) {
		 List<Tasca> list = new ArrayList<Tasca>();
		String sql = "SELECT idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament, prioritat, registre, usucre"
			 	+ " FROM public.tbl_tasques"
			 	+ " WHERE idinforme = ?";	
		 if (!withCancelades) {
			 sql += " AND activa = true";
		 }
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idInforme);		
			 ResultSet rs = pstm.executeQuery();
			
			 while (rs.next()) {
				 Tasca tasca = initTasca(conn, rs, false);
				 list.add(tasca);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		 
	     return list;
	}
	
	public static Date findInici(Connection conn, int idTasca) {
		Date dataInici = new Date();
		String sql = "SELECT data"
					+ " FROM public.tbl_historial"
					+ " WHERE idtasca = ?"
					+ " ORDER BY data"
					+ " LIMIT 1;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idTasca);		
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				dataInici = rs.getTimestamp("data");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
		return dataInici;
	}
	
	public static List<Historic> findHistorial(Connection conn, int idTasca, String idIncidencia, String idActuacio) {
		List<Historic> list = new ArrayList<Historic>();
		String sql = "SELECT idhistoric, idtasca, comentari, idusuari, data, ipremota, tipus"
					+ " FROM public.tbl_historial"
					+ " WHERE idtasca = ?"
					+ " ORDER BY idhistoric ASC";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idTasca);		
			ResultSet rs = pstm.executeQuery();
					
			while (rs.next()) {
				Historic historic = new Historic();
				historic.setComentari(rs.getString("comentari"));
				historic.setData(rs.getTimestamp("data"));
				historic.setIdHistoric(rs.getInt("idhistoric"));
				historic.setIdTasca(idTasca);
				historic.setUsuari(UsuariCore.findUsuariByID(conn, rs.getInt("idusuari")));
				historic.setIpRemota(rs.getString("ipremota"));
				historic.setTipus(rs.getString("tipus"));			
				if (!idIncidencia.equals("-1")) historic.setAdjunts(utils.Fitxers.ObtenirFitxers(conn, idIncidencia, idActuacio, "Tasca", String.valueOf(idTasca), rs.getString("idhistoric")));
				list.add(historic);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	    return list;
	}
	
	public static List<Historic> findHistorialComplet(Connection conn) {
		List<Historic> list = new ArrayList<Historic>();
		String sql = "SELECT h.idhistoric AS idhistoric, u.nom AS nom, u.cognoms AS cognoms, h.comentari AS comentari, h.data AS data, h.tipus AS tipus, t.descripcio AS descripciotasca, a.descripcio AS descripcioactuacio, c.nom AS nomcentre"
					+ " FROM public.tbl_historial h LEFT JOIN public.tbl_usuaris u ON h.idusuari = u.idusuari"
					+ "     LEFT JOIN public.tbl_tasques t ON h.idtasca::INT = t.idtasca"
					+ "     LEFT JOIN public.tbl_actuacio a ON t.idactuacio = a.id"
					+ "     LEFT JOIN public.tbl_centres c ON a.idcentre = c.codi";
		
		sql += " ORDER BY h.idhistoric ASC";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			User usuari = new User();
			Tasca tasca = new Tasca();
			Centre centre = new Centre();
			Actuacio actuacio = new Actuacio();
			
			while (rs.next()) {
				Historic historic = new Historic();
				historic.setIdHistoric(rs.getInt("idhistoric"));
				usuari.setName(rs.getString("nom"));
				usuari.setLlinatges(rs.getString("cognoms"));
				historic.setUsuari(usuari);			
				historic.setComentari(rs.getString("comentari"));			
				historic.setData(rs.getTimestamp("data"));
				historic.setTipus(rs.getString("tipus"));
				tasca.setDescripcio(rs.getString("descripciotasca"));
				historic.setTasca(tasca);
				centre.setNom(rs.getString("nomcentre"));
				actuacio.setDescripcio(rs.getString("descripcioactuacio"));
				actuacio.setCentre(centre);
				historic.setActuacio(actuacio);			
				list.add(historic);
				usuari = new User();
				tasca = new Tasca();
				centre = new Centre();
				actuacio = new Actuacio();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return list;
	}
	
	public static List<Historic> getTasquesSetmanals(Connection conn, int idusuari) {
		List<Historic> list = new ArrayList<Historic>();
		Calendar cal = Calendar.getInstance();			       
        String sql = "SELECT h.idhistoric AS idhistoric, u.nom AS nom, u.cognoms AS cognoms, h.comentari AS comentari, h.data AS data, t.registre AS registre, t.descripcio AS descripciotasca, a.descripcio AS descripcioactuacio, i.expcontratacio AS expedient, p.objecte AS objecte, r.contingut AS contingutregistre, r.idcentre AS idcentreregistre, c.nom AS nomcentre, c.municipi AS municipicentre, c.localitat AS localitatcentre"
				+ " FROM public.tbl_historial h LEFT JOIN public.tbl_usuaris u ON h.idusuari = u.idusuari"
				+ "     LEFT JOIN public.tbl_tasques t ON h.idtasca::INT = t.idtasca"
				+ "     LEFT JOIN public.tbl_informeactuacio i ON t.idinforme = i.idinf"
				+ "     LEFT JOIN public.tbl_propostesinforme p ON i.idinf = p.idinf"
				+ "     LEFT JOIN public.tbl_actuacio a ON t.idactuacio = a.id"
				+ "     LEFT JOIN public.tbl_centres c ON a.idcentre = c.codi"
				+ "		LEFT JOIN public.tbl_regentrada r ON t.registre = r.id"
	            + " WHERE p.seleccionada = true AND h.idusuari = ? AND h.data between ? AND ?"
	            + " ORDER BY h.idhistoric ASC";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idusuari);	
			//cal.add(Calendar.DATE, -1);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);     
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);  		
			Date dilluns = cal.getTime();
			pstm.setDate(2, new java.sql.Date(dilluns.getTime()));
			cal.add(Calendar.DATE, 7);
			Date diumenge = cal.getTime();
			pstm.setDate(3, new java.sql.Date(diumenge.getTime()));
			ResultSet rs = pstm.executeQuery();
			User usuari = new User();
			Tasca tasca = new Tasca();
			Centre centre = new Centre();
			Actuacio actuacio = new Actuacio();
			
			String descripcio = "";
			while (rs.next()) {
				descripcio = "";
				Historic historic = new Historic();
				historic.setIdHistoric(rs.getInt("idhistoric"));
				usuari.setName(rs.getString("nom"));
				usuari.setLlinatges(rs.getString("cognoms"));
				historic.setUsuari(usuari);			
				historic.setComentari(rs.getString("comentari"));			
				historic.setData(rs.getTimestamp("data"));
				tasca.setRegistre(rs.getString("registre"));
				tasca.setDescripcio(rs.getString("descripciotasca"));
				historic.setTasca(tasca);
				centre.setNom(rs.getString("nomcentre"));
				centre.setLocalitat(rs.getString("localitatcentre"));
				centre.setMunicipi(rs.getString("municipicentre"));
				if (rs.getString("nomcentre") == null && rs.getString("idcentreregistre")!=null) {
					centre = CentreCore.findCentre(conn, rs.getString("idcentreregistre").replace("#", ""), true);
				}
				
				actuacio.setRefExt(rs.getString("expedient"));
				if (rs.getString("descripcioactuacio")!=null) {
					descripcio = rs.getString("descripcioactuacio");
				}	
				if (rs.getString("objecte")!=null) {
					descripcio += " - " + rs.getString("objecte");
				}
				if (rs.getString("registre")!=null) {
					descripcio += " - " + rs.getString("contingutregistre");
				}
				actuacio.setDescripcio(descripcio);
				actuacio.setCentre(centre);
				historic.setActuacio(actuacio);			
				list.add(historic);
				usuari = new User();
				tasca = new Tasca();
				centre = new Centre();
				actuacio = new Actuacio();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return list;
	}
	
	public static Date findDarreraModificacioHistorial(Connection conn, int idTasca, String idIncidencia, String idActuacio) {
		Date data = new Date();
		String sql = "SELECT data"
					+ " FROM public.tbl_historial"
					+ " WHERE idtasca = ?"
					+ " ORDER BY idhistoric DESC LIMIT 1";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idTasca);		
			ResultSet rs = pstm.executeQuery();
			
			if (rs.next())  data = rs.getTimestamp("data");		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
			
	    return data;
	}
	
	public static int novaTasca(Connection conn, String tipus, int idUsuari, int idUsuariComentari, String idActuacio, String idIncidencia, String comentari, String assumpte, String idInformePrevi, List<Fitxer> adjunts, String ipRemota, String tipusHistorial) {
		String sql = "INSERT INTO public.tbl_tasques(idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament)"
					+" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";		 
		PreparedStatement pstm;
		int idNovaTasca = idNovaTasca(conn);
		try {
			pstm = conn.prepareStatement(sql);
			
			
			if ("solInfPrev".equals(tipus)) {
				assumpte = "Sol·licitud informe previ";
			} else if("resPartida".equals(tipus)) {	
				assumpte = "Sol·licitud reserva partida";
				comentari = "Sol·licitud reserva partida";
				tipusHistorial = "automatic";
			} else if("liciMenor".equals(tipus)) {
				assumpte = "Proposta d'adjudicació";
				comentari ="1. Recerca de pressupostos";
				tipusHistorial = "automatic";
			} else if("liciMajor".equals(tipus)) {
				assumpte = "Licitació contracte major";
				comentari = "Iniciar procés licitació contracte major";		
				tipusHistorial = "automatic";
			}
			pstm.setInt(1, idNovaTasca);
			pstm.setInt(2, idUsuari);
			pstm.setString(3, idActuacio);
			pstm.setString(4, assumpte);
			pstm.setString(5, tipus);
			pstm.setBoolean(6, true);
			pstm.setString(7, idIncidencia);
			pstm.setString(8, idInformePrevi);
			pstm.setBoolean(9, false);
			pstm.setString(10, UsuariCore.findUsuariByID(conn, idUsuari).getDepartament());			
			pstm.executeUpdate();		
			//Registrar comentari 1
			nouHistoric(conn, idNovaTasca, comentari, idUsuariComentari, ipRemota, tipusHistorial);
			TascaCore.guardarFitxer(conn, idNovaTasca, adjunts, idUsuariComentari);	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
		return idNovaTasca;
	}
	
	public static int novaTasca(Connection conn, String tipus, int idUsuari, int idUsuariComentari, String idActuacio, String idIncidencia, String comentari, String assumpte, String idInformePrevi, List<Fitxer> adjunts, String ipRemota, String tipusHistorial, String registre, String usucre) {
		String sql = "INSERT INTO public.tbl_tasques(idtasca, idusuari, idactuacio, descripcio, tipus, activa, idincidencia, idinforme, llegida, departament, registre, usucre)"
					+" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";		 
		PreparedStatement pstm;
		int idNovaTasca = idNovaTasca(conn);	
		try {
			pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, idNovaTasca);
			pstm.setInt(2, idUsuari);
			pstm.setString(3, idActuacio);
			pstm.setString(4, assumpte);
			pstm.setString(5, tipus);
			pstm.setBoolean(6, true);
			pstm.setString(7, idIncidencia);
			pstm.setString(8, idInformePrevi);
			pstm.setBoolean(9, false);
			pstm.setString(10, UsuariCore.findUsuariByID(conn, idUsuari).getDepartament());		
			pstm.setString(11, registre);
			pstm.setString(12, usucre);
			pstm.executeUpdate();		
			//Registrar comentari 1
			nouHistoric(conn, idNovaTasca, comentari, idUsuariComentari, ipRemota, tipusHistorial);
			Fitxers.guardarFitxer(conn, adjunts, idIncidencia, idActuacio, "Tasca", Integer.toString(idNovaTasca), "", "", "", idUsuariComentari);	
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	 
		
		return idNovaTasca;
	}
	
	
	public static void actualitzarInforme(Connection conn, int idTasca, String idInforme) {
		String sql = "UPDATE public.tbl_tasques"
				+ " SET idinforme = ?"
				+ " WHERE idtasca=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);	 
			pstm.setString(1, idInforme);
			pstm.setInt(2, idTasca);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static String nouHistoric(Connection conn, int idTasca, String comentari, int idUsuari, String ipRemota, String tipus) {
		String sql = "INSERT INTO public.tbl_historial(idhistoric, idtasca, comentari, idusuari, data, ipremota, tipus)"
					+ " VALUES (?, ?, ?, ?,localtimestamp, ?, ?);";		 
		PreparedStatement pstm = null; 
		int idNouHistoric = idNouHistoric(conn);
		try {
			pstm = conn.prepareStatement(sql);			
			pstm.setInt(1, idNouHistoric);
			pstm.setInt(2, idTasca);
			pstm.setString(3, comentari);
			pstm.setInt(4, idUsuari);
			pstm.setString(5, ipRemota);
			pstm.setString(6, tipus);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return String.valueOf(idNouHistoric);
	}
	
	public static void reasignar(Connection conn, int idUsuari, int idTasca, String tipus, String assumpte) {
		String sql = "UPDATE public.tbl_tasques"
					+ " SET idusuari=?, departament =?, tipus = ?, descripcio = ?, llegida = false"
					+ " WHERE idtasca=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);	 
			pstm.setInt(1, idUsuari);
			pstm.setString(2, UsuariCore.findUsuariByID(conn, idUsuari).getDepartament());
			pstm.setString(3, tipus);
			pstm.setString(4, assumpte);
			pstm.setInt(5, idTasca);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static void marcarNoLlegida(Connection conn, int idTasca) {
		String sql = "UPDATE public.tbl_tasques"
					+ " SET llegida = false"
					+ " WHERE idtasca=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);	 
			pstm.setInt(1, idTasca);		
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static void modificarTipus(Connection conn, int idTasca, String tipus) {
		String sql = "UPDATE public.tbl_tasques"
					+ " SET tipus = ?"
					+ " WHERE idtasca=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);	
			pstm.setString(1, tipus);
			pstm.setInt(2, idTasca);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static void tancar(Connection conn, int idTasca) {
		String sql = "UPDATE public.tbl_tasques"
					+ " SET activa=false"
					+ " WHERE idtasca=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);	 
			pstm.setInt(1, idTasca);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static void eliminarTasca(Connection conn, int idTasca) {
		String sql = "DELETE FROM public.tbl_tasques"
				+ " WHERE idtasca=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);	 
			pstm.setInt(1, idTasca);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
		
		sql = "DELETE FROM public.tbl_historial"
				+ " WHERE idtasca=?;";
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);	 
			pstm.setInt(1, idTasca);
			pstm.executeUpdate();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static void obrirTasca(Connection conn, int idTasca) {
		String sql = "UPDATE public.tbl_tasques"
				+ " SET activa = true"
				+ " WHERE idtasca=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);	 
			pstm.setInt(1, idTasca);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static List<Fitxer> getDocuments(Connection conn, int idTasca) {		
		List<Fitxer> adjunts = new ArrayList<Fitxer>();
		adjunts = utils.Fitxers.ObtenirFitxersTasca(conn, idTasca);
		return adjunts;
	}
	
	public static int idNovaTasca(Connection conn) {
		int id = 1;
		String sql = "SELECT idtasca"
					+ " FROM public.tbl_tasques"
					+ " ORDER BY idtasca"
					+ " DESC LIMIT 1;";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				String actualCode = rs.getString("idtasca");
				int num = Integer.valueOf(actualCode);
				id = num + 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	public static int idNouHistoric(Connection conn) {
		int id = 1;
		String sql = "SELECT idhistoric"
					+ " FROM public.tbl_historial"
					+ " ORDER BY idhistoric"
					+ " DESC LIMIT 1;";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				String actualCode = rs.getString("idhistoric");
				int num = Integer.valueOf(actualCode);
				id = num + 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	
	public static void seguirTasca(Connection conn, int idTasca, int idUsuari) {
		String sql = "INSERT INTO public.tbl_seguiments(idusuari, idtasca)"
				+ " VALUES (?, ?);";		 
		PreparedStatement pstm = null; 
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);
			pstm.setInt(2, idTasca);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	public static void desSeguirTasca(Connection conn, int idTasca, int idUsuari) {
		String sql = "DELETE FROM public.tbl_seguiments"
					+ " WHERE idtasca = ? AND idusuari = ?";		 
		PreparedStatement pstm = null; 
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idTasca);
			pstm.setInt(2, idUsuari);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	private static boolean isSeguiment(Connection conn, int idTasca, int idUsuari) {
		boolean seguint = false;
		String sql = "SELECT idusuari, idtasca"
					+ " FROM public.tbl_seguiments"
					+ " WHERE idtasca = ? AND idusuari = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idTasca);
			pstm.setInt(2, idUsuari);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) seguint = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return seguint;
	}
		
	public static List<Fitxer> getTascaDocuments(Connection conn, int idTasca) {
		List<Fitxer> adjunts = new ArrayList<Fitxer>();
		adjunts = utils.Fitxers.ObtenirFitxersTasca(conn, idTasca);
		return adjunts;
	}
	
	public static boolean existTascaReservaCredit(Connection conn, String idInf) {
		boolean exist = false;
		int idUsuari = UsuariCore.findUsuarisByRol(conn, "CAP,CONTA").get(0).getIdUsuari();
		String sql = "SELECT idtasca" 
					+ " FROM public.tbl_tasques"
					+ " WHERE idusuari = ? AND activa = true AND idinforme = ? AND tipus = 'resPartida'";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);
			pstm.setString(2, idInf);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return exist;
	}
	
	public static boolean existTascaPrevisioLlicencies(Connection conn, int mes) {
		boolean exist = false;	
		String sql = "SELECT idtasca" 
					+ " FROM public.tbl_tasques"
					+ " WHERE activa = true AND idinforme = ? AND tipus = 'previsioLlicencies'";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, String.valueOf(mes));
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return exist;
	}
	
	public static List<Tasca> getTasquesEstatActuacio(Connection conn)  {
		List<Tasca> tasques = new ArrayList<Tasca>();
		String sql = "SELECT idtasca, idusuari, idactuacio, idincidencia, descripcio, tipus, activa, idinforme, llegida, departament, prioritat, registre"
					+ " FROM public.tbl_tasques"
					+ " WHERE activa = true AND tipus != 'notificacio'";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				 Tasca tasca = initTasca(conn, rs, false);
				 tasques.add(tasca);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tasques;
	}
	
	public static void guardarFitxer(Connection conn, int idTasca, List<Fitxer> fitxers, int idUsuari) {
		if (fitxers != null && !fitxers.isEmpty()) {
			String fileName = "";
			
			String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
			// Crear directoris si no existeixen
			File tmpFile = new File(ruta + "/documents/Tasca");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/Tasca/" + idTasca );
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}			
			fileName = ruta + "/documents/Tasca/" + idTasca + "/";
						
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
}
