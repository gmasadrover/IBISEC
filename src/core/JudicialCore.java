package core;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.AssignacioCredit;
import bean.Judicial;
import bean.Judicial.Tramitacio;
import bean.Partida;
import utils.Fitxers;
import utils.Fitxers.Fitxer;

public class JudicialCore {
	static final String SQL_CAMPS = "referencia, jutjat, numautos, demandant, demandat, objectedemanda, quantia, estat, notes, anyprocediment, tipus, original";
	static final String SQL_CAMPS_TRAMITACIO = "numstcia, refprocediment, datadocument, quantia, recurs, dataregistre, sentencia, tipus, descripcio, idtramitacio, termini, pendenttercers, notes, pendentprovisio";
	
	private static Judicial initProcediment(Connection conn, ResultSet rs) {
		Judicial procediment = new Judicial();
		try {
			procediment.setReferencia(rs.getString("referencia"));
			procediment.setJutjat(rs.getString("jutjat"));
			procediment.setNumAutos(rs.getString("numautos"));
			procediment.setDemandant(rs.getString("demandant"));
			procediment.setDemandat(rs.getString("demandat"));
			procediment.setObjecteDemanda(rs.getString("objectedemanda"));
			procediment.setQuantia(rs.getString("quantia"));
			procediment.setEstat(rs.getString("estat"));
			procediment.setNotes(rs.getString("notes"));
			procediment.setTramitacionsList(getTramitacions(conn, rs.getString("referencia")));
			procediment.setDocumentsIniciList(getDocumentsInici(conn, rs.getString("referencia")));
			procediment.setDocumentsComunicacioList(getDocumentsComunicacio(conn, rs.getString("referencia")));
			procediment.setAnyProcediment(rs.getString("anyprocediment"));
			procediment.setSegonaInstancia(getSegonaInstancia(conn, rs.getString("referencia")));
			procediment.setAltresRecursosObert(getAltresRecursosObert(conn, rs.getString("referencia")));
			procediment.setMesuresCautelars(getMesuresCautelars(conn, rs.getString("referencia")));
			procediment.setExecucio(getExecucio(conn, rs.getString("referencia")));
			procediment.setRecursExecucio(getRecursExecucio(conn, rs.getString("referencia")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return procediment;
	}
	
	private static Tramitacio initTramitacio(Connection conn, ResultSet rs) {
		Tramitacio tramitacio = new Judicial().new Tramitacio();
		try {
			tramitacio.setNumstcia(rs.getString("numstcia"));
			tramitacio.setDataDocument(rs.getTimestamp("datadocument"));
			tramitacio.setQuantia(rs.getString("quantia"));
			tramitacio.setRecurs(rs.getString("recurs"));
			tramitacio.setDataRegistre(rs.getTimestamp("dataregistre"));
			tramitacio.setSentencia(rs.getString("sentencia"));
			tramitacio.setTipus(rs.getString("tipus"));
			tramitacio.setDescripcio(rs.getString("descripcio"));
			tramitacio.setIdTramitacio(rs.getInt("idtramitacio"));
			tramitacio.setTermini(rs.getString("termini"));
			tramitacio.setPendentTercers(rs.getBoolean("pendenttercers"));
			tramitacio.setPendentProvisio(rs.getBoolean("pendentprovisio"));
			tramitacio.setNotes(rs.getString("notes"));
			tramitacio.setCodiProcediment(rs.getString("refprocediment"));
			tramitacio.setDocumentsList(getDocumentsTramitacio(conn, rs.getString("refprocediment") ,rs.getInt("idtramitacio")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tramitacio;
	}
	
	public static String nouProcediment(Connection conn, Judicial judicial, String refOriginal, String tipus) {
		String newCode = getNewCodeProcediment(conn);
		String sql = "INSERT INTO public.tbl_judicial(" + SQL_CAMPS + ")"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, newCode);		
			pstm.setString(2, judicial.getJutjat());
			pstm.setString(3, judicial.getNumAutos());
			pstm.setString(4, judicial.getDemandant());
			pstm.setString(5, judicial.getDemandat());		
			pstm.setString(6, judicial.getObjecteDemanda());	
			pstm.setString(7, judicial.getQuantia());
			pstm.setString(8, "obert");	
			pstm.setString(9, judicial.getNotes());	
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			String yearInString = String.valueOf(year);
			pstm.setString(10, yearInString);
			pstm.setString(11, tipus);
			pstm.setString(12, refOriginal);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
		return newCode;
	}
	
	public static int novaTramitacio(Connection conn, Tramitacio tramitacio, String refJudicial, int idUsuari, String ipRemota) {
		int idTramitacio = getNewCodeTramitacio(conn);
		String sql = "INSERT INTO public.tbl_tramitacions(" + SQL_CAMPS_TRAMITACIO + ")"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, tramitacio.getNumstcia());		
			pstm.setString(2, refJudicial);
			if (tramitacio.getDataDocument() == null) {
				pstm.setDate(3, null);
			}else{
				pstm.setDate(3, new java.sql.Date(tramitacio.getDataDocument().getTime()));
			}
			pstm.setString(4, tramitacio.getQuantia());
			pstm.setString(5, tramitacio.getRecurs());		
			if (tramitacio.getDataRegistre() == null) {
				pstm.setDate(6, null);	
			}else{
				pstm.setDate(6, new java.sql.Date(tramitacio.getDataRegistre().getTime()));	
			}
			pstm.setString(7, tramitacio.getSentencia());
			pstm.setString(8, tramitacio.getTipus());
			pstm.setString(9, tramitacio.getDescripcio());
			pstm.setInt(10, idTramitacio);
			pstm.setString(11, tramitacio.getTermini());
			pstm.setBoolean(12, tramitacio.isPendentTercers());
			pstm.setString(13,  tramitacio.getNotes());
			pstm.setBoolean(14, tramitacio.isPendentProvisio());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
		
		//Cream tasca si hi ha termini
		if (tramitacio.getTermini() != null && !tramitacio.getTermini().isEmpty()) TascaCore.novaTasca(conn, "judicial", idUsuari, idUsuari, "-1", "-1", "S'ha afegit el termini de: " + tramitacio.getTermini(), "Nou termini procediment", refJudicial, null, ipRemota, "automatic");
		return idTramitacio;
	}
	
	public static Judicial findProcediment(Connection conn, String referencia) {		 
		String sql = "SELECT " + SQL_CAMPS
					+ " FROM public.tbl_judicial"
					+ " WHERE referencia = ?";
	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);		
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				Judicial procediment = initProcediment(conn, rs); 
				return procediment;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Judicial();
	}
	
	public static Tramitacio findTramitacio(Connection conn, String referencia) {
		String sql = "SELECT " + SQL_CAMPS_TRAMITACIO
				+ " FROM public.tbl_tramitacions"
				+ " WHERE idtramitacio = ?";
 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, Integer.parseInt(referencia));		
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				Tramitacio tramitacio = initTramitacio(conn, rs); 
				return tramitacio;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Judicial().new Tramitacio();
	}
	
	public static AssignacioCredit getPartidaTramit(Connection conn, String idProcediment, String idTramit) {
		AssignacioCredit assignacio = new AssignacioCredit();
		Partida partida = new Partida();
		String sql = "SELECT idpartida"
				+ " FROM public.tbl_assignacionscredit"
				+ " WHERE idactuacio = ? AND idinf = ?";
 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idProcediment);	
			pstm.setString(2, idTramit);	
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				partida.setCodi(rs.getString("idpartida"));
				assignacio.setPartida(partida)	;	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return assignacio;
	}
	
	public static List<Judicial> getProcediments(Connection conn, String estat, String year) {
		List<Judicial> procedimentsList = new ArrayList<Judicial>();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_judicial"
				+ " WHERE tipus IS NULL";					
		PreparedStatement pstm;	
		if (estat != null && !estat.isEmpty() && ! estat.equals("-1")) {
			sql += " AND estat = '" + estat + "'";
		}
		if (year != null && !year.isEmpty() && ! year.equals("-1")) {
			sql += " AND anyprocediment::INT = " + year;					
		}
		sql += " ORDER BY anyprocediment DESC";
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {		
				procedimentsList.add(initProcediment(conn, rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return procedimentsList;
	}
	
	public static List<Tramitacio> getTramitacions(Connection conn, String referencia)  {
		List<Tramitacio> tramitacionsList = new ArrayList<Tramitacio>();
		String sql = "SELECT " + SQL_CAMPS_TRAMITACIO
				+ " FROM public.tbl_tramitacions"
				+ " WHERE refprocediment = ?"
				+ " ORDER BY idtramitacio ASC";
		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {		
				tramitacionsList.add(initTramitacio(conn, rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						
		
		return tramitacionsList;
	}
	
	public static List<Judicial> ProcedimentsAmbtramitacionsPendentsTercersList(Connection conn) {
		List<Judicial> judicialList = new ArrayList<Judicial>();
		String sql = "SELECT p.referencia AS referencia, p.numautos AS numautos, p.estat AS estat, t.datadocument AS datadocument, t.termini AS termini, t.descripcio AS descripcio"
				+ " FROM public.tbl_judicial p LEFT JOIN public.tbl_tramitacions t ON p.referencia = t.refprocediment"
				+ " WHERE t.pendenttercers = true";
		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			Judicial judicial = new Judicial();
			Tramitacio tramitacio = judicial.new Tramitacio();
			List<Tramitacio> tramitacionsList = new ArrayList<Tramitacio>();
			while (rs.next()) {		
				judicial = new Judicial();
				judicial.setNumAutos(rs.getString("numautos"));
				judicial.setReferencia(rs.getString("referencia"));
				judicial.setEstat(rs.getString("estat"));
				tramitacio = judicial.new Tramitacio();
				tramitacionsList = new ArrayList<Tramitacio>();
				tramitacio.setDataDocument(rs.getTimestamp("datadocument"));
				tramitacio.setTermini(rs.getString("termini"));
				tramitacio.setDescripcio(rs.getString("descripcio"));
				tramitacionsList.add(tramitacio);
				judicial.setTramitacionsList(tramitacionsList);
				judicialList.add(judicial);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return judicialList;
	}
	
	public static List<Judicial> ProcedimentsAmbtramitacionsPendentsProvisioList(Connection conn) {
		List<Judicial> judicialList = new ArrayList<Judicial>();
		String sql = "SELECT p.referencia AS referencia, p.numautos AS numautos, p.estat AS estat, t.datadocument AS datadocument, t.termini AS termini, t.descripcio AS descripcio"
				+ " FROM public.tbl_judicial p LEFT JOIN public.tbl_tramitacions t ON p.referencia = t.refprocediment"
				+ " WHERE t.pendentprovisio = true";
		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			Judicial judicial = new Judicial();
			Tramitacio tramitacio = judicial.new Tramitacio();
			List<Tramitacio> tramitacionsList = new ArrayList<Tramitacio>();
			while (rs.next()) {		
				judicial = new Judicial();
				judicial.setNumAutos(rs.getString("numautos"));
				judicial.setReferencia(rs.getString("referencia"));
				judicial.setEstat(rs.getString("estat"));
				tramitacio = judicial.new Tramitacio();
				tramitacionsList = new ArrayList<Tramitacio>();
				tramitacio.setDataDocument(rs.getTimestamp("datadocument"));
				tramitacio.setTermini(rs.getString("termini"));
				tramitacio.setDescripcio(rs.getString("descripcio"));
				tramitacionsList.add(tramitacio);
				judicial.setTramitacionsList(tramitacionsList);
				judicialList.add(judicial);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return judicialList;
	}
	
	public static void modificarProcediment(Connection conn, Judicial procediment) {
		String sql = "UPDATE public.tbl_judicial"
					+ " SET jutjat=?, numautos=?, demandant=?, demandat=?, objectedemanda=?, quantia=?, notes=?"
					+ " WHERE referencia=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, procediment.getJutjat());
			pstm.setString(2, procediment.getNumAutos());
			pstm.setString(3, procediment.getDemandant());
			pstm.setString(4, procediment.getDemandat());			
			pstm.setString(5, procediment.getObjecteDemanda());	
			pstm.setString(6, procediment.getQuantia());
			pstm.setString(7, procediment.getNotes());
			pstm.setString(8, procediment.getReferencia());	
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	public static void canviarEstatProcediment(Connection conn, String estat, String refProcediment) {
		String sql = "UPDATE public.tbl_judicial"
				+ " SET estat=?"
				+ " WHERE referencia=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, estat);
			pstm.setString(2, refProcediment);	
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	public static void guardarFitxer(Connection conn, List<Fitxer> fitxers, String refPro, int idUsuari) {
		if (!fitxers.isEmpty()) {
			String fileName = "";
			// Crear directoris si no existeixen
			
			String ruta = ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
			File tmpFile = new File(ruta + "/documents/Procediments");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/Procediments/" + refPro);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			
	        for(int i=0;i<fitxers.size();i++){		        	
	            Fitxer fitxer = (Fitxer) fitxers.get(i);	
	            if (fitxer.getNomCamp().equals("fileInici")) {
	            	tmpFile = new File(ruta + "/documents/Procediments/" + refPro + "/Inici");
	    			if (!tmpFile.exists()) {
	    				tmpFile.mkdir();
	    			}
	    			fileName = ruta + "/documents/Procediments/" + refPro + "/Inici/";
	            } else if (fitxer.getNomCamp().equals("fileComunicacio")) {
	            	tmpFile = new File(ruta + "/documents/Procediments/" + refPro + "/Comunicacio");
	    			if (!tmpFile.exists()) {
	    				tmpFile.mkdir();
	    			}
	    			fileName = ruta + "/documents/Procediments/" + refPro + "/Comunicacio/";
	            }
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
		
	public static void guardarFitxerTramitacio(Connection conn, List<Fitxer> fitxers, String refPro, int idTramitacio, int idUsuari) {
		if (!fitxers.isEmpty()) {
			String fileName = "";
			// Crear directoris si no existeixen
			
			String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
			File tmpFile = new File(ruta + "/documents/Procediments");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/Procediments/" + refPro);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/Procediments/" + refPro + "/" + idTramitacio);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			fileName = ruta + "/documents/Procediments/" + refPro + "/" + idTramitacio + "/";
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
	
	public static List<Fitxer> getDocumentsInici(Connection conn, String refProcediment) {
		 List<Fitxer> fitxersList = new ArrayList<Fitxer>();
		
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Procediments/" + refProcediment  + "/Inici");
		 File[] fichers = dir.listFiles();
		 Fitxer fitxer = new Fitxer();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 if (fichers[x].isFile()) {
					 fitxer = new Fitxer();
					 fitxer.setNom(fichers[x].getName());
					 fitxer.setRuta(ruta + "/documents/Procediments/" + refProcediment + "/Inici/" + fichers[x].getName());
					 fitxersList.add(fitxer);
				 }
			 }
		 }
		 return fitxersList;
	}
	
	public static List<Fitxer> getDocumentsComunicacio(Connection conn, String refProcediment) {
		 List<Fitxer> fitxersList = new ArrayList<Fitxer>();
		 
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Procediments/" + refProcediment + "/Comunicacio");
		 File[] fichers = dir.listFiles();
		 Fitxer fitxer = new Fitxer();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 if (fichers[x].isFile()) {
					 fitxer = new Fitxer();
					 fitxer.setNom(fichers[x].getName());
					 fitxer.setRuta(ruta + "/documents/Procediments/" + refProcediment + "/Comunicacio/" + fichers[x].getName());
					 fitxersList.add(fitxer);
				 }
			 }
		 }
		 return fitxersList;
	}
	
	public static List<Fitxer> getDocumentsTramitacio(Connection conn, String refProcediment, int idProcediment) {
		List<Fitxer> fitxersList = new ArrayList<Fitxer>();
		
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Procediments/" + refProcediment + "/" + idProcediment);
		 File[] fichers = dir.listFiles();
		 Fitxer fitxer = new Fitxer();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Procediments/" + refProcediment + "/" + idProcediment + "/" + fichers[x].getName());
				 fitxersList.add(fitxer);
			 }
		 }
		 return fitxersList;
	}
	public static void modificarTramitacio(Connection conn, Tramitacio tramitacio) {
		String sql = "UPDATE public.tbl_tramitacions"
					+ " SET numstcia=?, datadocument = ?, quantia = ?, recurs = ?, dataregistre = ?, sentencia = ?, tipus= ?, descripcio = ?, termini = ?, pendenttercers = ?, notes = ?, pendentprovisio = ?"
					+ " WHERE idtramitacio=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, tramitacio.getNumstcia());	
			if (tramitacio.getDataDocument() == null) {
				pstm.setDate(2, null);
			}else{
				pstm.setDate(2, new java.sql.Date(tramitacio.getDataDocument().getTime()));
			}		
			pstm.setString(3, tramitacio.getQuantia());
			pstm.setString(4, tramitacio.getRecurs());	
			if (tramitacio.getDataRegistre() == null){
				pstm.setDate(5, null);	
			}else{
				pstm.setDate(5, new java.sql.Date(tramitacio.getDataRegistre().getTime()));	
			}		
			pstm.setString(6, tramitacio.getSentencia());
			pstm.setString(7, tramitacio.getTipus());
			pstm.setString(8, tramitacio.getDescripcio());
			pstm.setString(9, tramitacio.getTermini());
			pstm.setBoolean(10, tramitacio.isPendentTercers());
			pstm.setString(11, tramitacio.getNotes());
			pstm.setBoolean(12, tramitacio.isPendentProvisio());
			pstm.setInt(13, tramitacio.getIdTramitacio());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String getNewCodeProcediment(Connection conn) {
		String newCode = "PRO-0001";
		String sql = "SELECT referencia"
				+ " FROM public.tbl_judicial"
				+ " ORDER BY 1 desc LIMIT 1;";		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) { //Codis nous
				String actualCode = rs.getString("referencia");			
				int num = Integer.valueOf(actualCode.split("-")[1]);
				String numFormatted = String.format("%04d", num + 1);
				newCode = "PRO-" + numFormatted;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return newCode;
	}
	
	public static int getNewCodeTramitacio(Connection conn) {
		int newCode = 1;
		String sql = "SELECT idtramitacio"
				+ " FROM public.tbl_tramitacions"
				+ " ORDER BY idtramitacio desc LIMIT 1;";		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) { //Codis nous	
				newCode = rs.getInt("idtramitacio") + 1;		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return newCode;
	}
	
	public static void eliminarTramitacio(Connection conn, String idTramitacio, int idUsuari) {
		String sql = "DELETE FROM public.tbl_tramitacions"
					+ " WHERE idtramitacio = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, Integer.parseInt(idTramitacio));
			pstm.executeUpdate();
			Tramitacio tramitacio = findTramitacio(conn, idTramitacio);
			if (tramitacio.getDocumentsList() != null ) {
				for (Fitxer fitxer: tramitacio.getDocumentsList()) {
					Fitxers.eliminarFitxer(conn, idUsuari, fitxer.getRuta());
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public static List<String> getAnysProcediment(Connection conn) {
		List<String> anysList = new ArrayList<String>();
		String sql = "SELECT DISTINCT anyprocediment"
					+ " FROM public.tbl_judicial"
					+ " WHERE anyprocediment IS NOT NULL"
					+ " ORDER BY anyprocediment DESC";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				anysList.add(rs.getString("anyprocediment"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return anysList;
	}
	
	private static Judicial getSegonaInstancia(Connection conn, String referencia) {
		Judicial procediment = new Judicial();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_judicial"
				+ " WHERE original = ? AND tipus = '2ainstancia'";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				procediment = initProcediment(conn, rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return procediment;
	}
	
	private static Judicial getAltresRecursosObert(Connection conn, String referencia) {
		Judicial procediment = new Judicial();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_judicial"
				+ " WHERE original = ? AND tipus = 'altresrecursosobert'";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				procediment = initProcediment(conn, rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return procediment;
	}
	
	private static Judicial getMesuresCautelars(Connection conn, String referencia) {
		Judicial procediment = new Judicial();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_judicial"
				+ " WHERE original = ? AND tipus = 'mesurescautelars'";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				procediment = initProcediment(conn, rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return procediment;
	}
	
	private static Judicial getExecucio(Connection conn, String referencia) {
		Judicial procediment = new Judicial();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_judicial"
				+ " WHERE original = ? AND tipus = 'execucio'";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				procediment = initProcediment(conn, rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return procediment;
	}
	
	private static Judicial getRecursExecucio(Connection conn, String referencia) {
		Judicial procediment = new Judicial();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_judicial"
				+ " WHERE original = ? AND tipus = 'recursexecucio'";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				procediment = initProcediment(conn, rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return procediment;
	}
}
