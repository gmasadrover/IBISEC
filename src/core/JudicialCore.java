package core;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import bean.Judicial;
import bean.Judicial.Tramitacio;
import utils.Fitxers.Fitxer;

public class JudicialCore {
	static final String SQL_CAMPS = "referencia, jutjat, numautos, demandant, demandat, objectedemanda, quantia, estat, notes, anyprocediment";
	static final String SQL_CAMPS_TRAMITACIO = "numstcia, refprocediment, data, quantia, recurs, datapagament, sentencia, tipus, notes, idtramitacio, termini";
	
	private static Judicial initProcediment(Connection conn, ResultSet rs) throws SQLException, NamingException{
		Judicial procediment = new Judicial();
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
		procediment.setDocumentsIniciList(getDocumentsInici(rs.getString("referencia")));
		procediment.setDocumentsComunicacioList(getDocumentsComunicacio(rs.getString("referencia")));
		procediment.setAnyProcediment(rs.getString("anyprocediment"));
		return procediment;
	}
	
	private static Tramitacio initTramitacio(Connection conn, ResultSet rs) throws SQLException, NamingException{
		Tramitacio tramitacio = new Judicial().new Tramitacio();
		tramitacio.setNumstcia(rs.getString("numstcia"));
		tramitacio.setData(rs.getTimestamp("data"));
		tramitacio.setQuantia(rs.getDouble("quantia"));
		tramitacio.setRecurs(rs.getString("recurs"));
		tramitacio.setDatapagament(rs.getTimestamp("datapagament"));
		tramitacio.setSentencia(rs.getString("sentencia"));
		tramitacio.setTipus(rs.getString("tipus"));
		tramitacio.setNotes(rs.getString("notes"));
		tramitacio.setIdTramitacio(rs.getInt("idtramitacio"));
		tramitacio.setTermini(rs.getString("termini"));
		tramitacio.setDocumentsList(getDocumentsTramitacio(rs.getString("refprocediment") ,rs.getInt("idtramitacio")));
		return tramitacio;
	}
	
	public static String nouProcediement(Connection conn, Judicial judicial) throws SQLException {
		String newCode = getNewCodeProcediment(conn);
		String sql = "INSERT INTO public.tbl_judicial(" + SQL_CAMPS + ")"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, newCode);		
		pstm.setString(2, judicial.getJutjat());
		pstm.setString(3, judicial.getNumAutos());
		pstm.setString(4, judicial.getDemandant());
		pstm.setString(5, judicial.getDemandat());		
		pstm.setString(6, judicial.getObjecteDemanda());	
		pstm.setString(7, judicial.getQuantia());
		pstm.setString(8, judicial.getEstat());	
		pstm.setString(9, judicial.getNotes());	
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		pstm.setString(10, yearInString);
		pstm.executeUpdate();
		return newCode;
	}
	
	public static void novaTramitacio(Connection conn, Tramitacio tramitacio, String refJudicial, int idUsuari) throws SQLException, NamingException {
		String sql = "INSERT INTO public.tbl_tramitacions(" + SQL_CAMPS_TRAMITACIO + ")"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, tramitacio.getNumstcia());		
		pstm.setString(2, refJudicial);
		if (tramitacio.getData() == null) {
			pstm.setDate(3, null);
		}else{
			pstm.setDate(3, new java.sql.Date(tramitacio.getData().getTime()));
		}
		pstm.setDouble(4, tramitacio.getQuantia());
		pstm.setString(5, tramitacio.getRecurs());		
		if (tramitacio.getDatapagament() == null) {
			pstm.setDate(6, null);	
		}else{
			pstm.setDate(6, new java.sql.Date(tramitacio.getDatapagament().getTime()));	
		}
		pstm.setString(7, tramitacio.getSentencia());
		pstm.setString(8, tramitacio.getTipus());
		pstm.setString(9, tramitacio.getNotes());
		pstm.setInt(10, getNewCodeTramitacio(conn));
		pstm.setString(11, tramitacio.getTermini());
		pstm.executeUpdate();
		
		//Cream tasca si hi ha termini
		if (tramitacio.getTermini() != "") TascaCore.novaTasca(conn, "judicial", idUsuari, idUsuari, "-1", "-1", "S'ha afegit el termini de: " + tramitacio.getTermini(), "Nou termini procediment", refJudicial, null);
	}
	
	public static Judicial findProcediment(Connection conn, String referencia) throws SQLException, NamingException{		 
		String sql = "SELECT " + SQL_CAMPS
					+ " FROM public.tbl_judicial"
					+ " WHERE numautos = ?";
	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, referencia);		
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			Judicial procediment = initProcediment(conn, rs); 
			return procediment;
		}
		return new Judicial();
	}
	
	public static Tramitacio findTramitacio(Connection conn, String referencia) throws SQLException, NamingException{
		String sql = "SELECT " + SQL_CAMPS_TRAMITACIO
				+ " FROM public.tbl_tramitacions"
				+ " WHERE idtramitacio = ?";
 
	PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setInt(1, Integer.parseInt(referencia));		
	ResultSet rs = pstm.executeQuery();
	if (rs.next()) {
		Tramitacio tramitacio = initTramitacio(conn, rs); 
		return tramitacio;
	}
	return new Judicial().new Tramitacio();
	}
	
	public static List<Judicial> getProcediments(Connection conn, String estat, String year) throws SQLException, NamingException{
		List<Judicial> procediementsList = new ArrayList<Judicial>();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_judicial";					
		PreparedStatement pstm;	
		boolean primeraCondicio = true;
		if (estat != null && !estat.isEmpty() && ! estat.equals("-1")) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE estat = '" + estat + "'";
			}else{
				sql += " AND estat = '" + estat + "'";
			}
		}
		if (year != null && !year.isEmpty() && ! year.equals("-1")) {
			if (primeraCondicio) {
				primeraCondicio = false;
				sql += " WHERE anyprocediment::INT = " + year;
							
			}else{
					sql += " AND anyprocediment::INT = " + year;
							
			}			
		}
		sql += " ORDER BY anyprocediment DESC";
		pstm = conn.prepareStatement(sql);	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {		
			procediementsList.add(initProcediment(conn, rs));
		}
		return procediementsList;
	}
	
	public static List<Tramitacio> getTramitacions(Connection conn, String referencia) throws SQLException, NamingException {
		List<Tramitacio> tramitacionsList = new ArrayList<Tramitacio>();
		String sql = "SELECT " + SQL_CAMPS_TRAMITACIO
				+ " FROM public.tbl_tramitacions"
				+ " WHERE refprocediment = ?"
				+ " ORDER BY data ASC";
		
		PreparedStatement pstm = conn.prepareStatement(sql);						
		pstm.setString(1, referencia);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {		
			tramitacionsList.add(initTramitacio(conn, rs));
		}
		return tramitacionsList;
	}
	
	public static void modificarProcediment(Connection conn, Judicial procediment) throws SQLException {
		String sql = "UPDATE public.tbl_judicial"
					+ " SET jutjat=?, numautos=?, demandant=?, demandat=?, objectedemanda=?, quantia=?, estat=?, notes=?"
					+ " WHERE referencia=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);		
		pstm.setString(1, procediment.getJutjat());
		pstm.setString(2, procediment.getNumAutos());
		pstm.setString(3, procediment.getDemandant());
		pstm.setString(4, procediment.getDemandat());			
		pstm.setString(5, procediment.getObjecteDemanda());	
		pstm.setString(6, procediment.getQuantia());
		pstm.setString(7, procediment.getEstat());	
		pstm.setString(8, procediment.getNotes());
		pstm.setString(9, procediment.getReferencia());	
		pstm.executeUpdate();
	}
	
	public static void guardarFitxer(List<Fitxer> fitxers, String refPro) throws NamingException {
		if (!fitxers.isEmpty()) {
			String fileName = "";
			// Crear directoris si no existeixen
			 // Get the base naming context
		    Context env = (Context)new InitialContext().lookup("java:comp/env");
		    // Get a single value
			String ruta =  (String)env.lookup("ruta_base");
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
	           		} catch (Exception e) {
	           			e.printStackTrace();
	           		}
	            } 
	        }
		}
	}
		
	public static void guardarFitxerTramitacio(List<Fitxer> fitxers, String refPro, int idTramitacio) throws NamingException {
		if (!fitxers.isEmpty()) {
			String fileName = "";
			// Crear directoris si no existeixen
			 // Get the base naming context
		    Context env = (Context)new InitialContext().lookup("java:comp/env");
		    // Get a single value
			String ruta =  (String)env.lookup("ruta_base");
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
	           		} catch (Exception e) {
	           			e.printStackTrace();
	           		}
	            } 
	        }
		}
	}	
	
	public static List<Fitxer> getDocumentsInici(String refProcediment) throws NamingException {
		 List<Fitxer> fitxersList = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
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
	
	public static List<Fitxer> getDocumentsComunicacio(String refProcediment) throws NamingException {
		 List<Fitxer> fitxersList = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
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
	
	public static List<Fitxer> getDocumentsTramitacio(String refProcediment, int idProcediment) throws NamingException {
		List<Fitxer> fitxersList = new ArrayList<Fitxer>();
		 // Get the base naming context
	    Context env = (Context)new InitialContext().lookup("java:comp/env");
	    // Get a single value
		String ruta =  (String)env.lookup("ruta_base");
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
	public static void modificarTramitacio(Connection conn, Tramitacio tramitacio) throws SQLException {
		String sql = "UPDATE public.tbl_tramitacions"
					+ " SET numstcia=?, data = ?, quantia = ?, recurs = ?, datapagament = ?, sentencia = ?, tipus= ?, notes = ?, termini = ?"
					+ " WHERE idtramitacio=?;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, tramitacio.getNumstcia());	
		if (tramitacio.getData() == null) {
			pstm.setDate(2, null);
		}else{
			pstm.setDate(2, new java.sql.Date(tramitacio.getData().getTime()));
		}		
		pstm.setDouble(3, tramitacio.getQuantia());
		pstm.setString(4, tramitacio.getRecurs());	
		if (tramitacio.getDatapagament() == null){
			pstm.setDate(5, null);	
		}else{
			pstm.setDate(5, new java.sql.Date(tramitacio.getDatapagament().getTime()));	
		}		
		pstm.setString(6, tramitacio.getSentencia());
		pstm.setString(7, tramitacio.getTipus());
		pstm.setString(8, tramitacio.getNotes());
		pstm.setString(9, tramitacio.getTermini());
		pstm.setInt(10, tramitacio.getIdTramitacio());
		pstm.executeUpdate();
	}
	
	public static String getNewCodeProcediment(Connection conn) throws SQLException {
		String newCode = "PRO-0001";
		String sql = "SELECT referencia"
				+ " FROM public.tbl_judicial"
				+ " ORDER BY 1 desc LIMIT 1;";		
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) { //Codis nous
			String actualCode = rs.getString("referencia");			
			int num = Integer.valueOf(actualCode.split("-")[1]);
			String numFormatted = String.format("%04d", num + 1);
			newCode = "PRO-" + numFormatted;
		}		
		return newCode;
	}
	
	public static int getNewCodeTramitacio(Connection conn) throws SQLException {
		int newCode = 1;
		String sql = "SELECT idtramitacio"
				+ " FROM public.tbl_tramitacions"
				+ " ORDER BY idtramitacio desc LIMIT 1;";		
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) { //Codis nous	
			newCode = rs.getInt("idtramitacio") + 1;		
		}		
		return newCode;
	}
	
	public static List<String> getAnysProcediment(Connection conn) throws SQLException {
		List<String> anysList = new ArrayList<String>();
		String sql = "SELECT DISTINCT anyprocediment"
					+ " FROM public.tbl_judicial"
					+ " WHERE anyprocediment IS NOT NULL"
					+ " ORDER BY anyprocediment DESC";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			anysList.add(rs.getString("anyprocediment"));
		}
		return anysList;
	}
}
