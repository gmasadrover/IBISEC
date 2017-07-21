package core;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bean.Empresa;
import utils.Fitxers;
import utils.Fitxers.Fitxer;

public class EmpresaCore {
	
	static final String SQL_CAMPS = "cif, nom, direccio, cp, ciutat, provincia, telefon, fax, email, usumod, datamod,"
								+ " acreditacio1, dataexpacreditacio1, acreditacio2, dataexpacreditacio2, acreditacio3, dataexpacreditacio3,"
								+ " classificacio, dataconstitucio, informacioadicional, exercicieconomic, dataregistremercantil, ratioap,"
								+ " datavigenciaclassificaciorolece, datavigenciaclassificaciojccaib, datavigenciaclassificaciojca, pime";
	
	
	private static Empresa initEmpresa(Connection conn, ResultSet rs) throws SQLException{		
		Empresa empresa = new Empresa();
		empresa.setCif(rs.getString("cif"));
		empresa.setName(rs.getString("nom"));
		empresa.setDireccio(rs.getString("direccio"));
		empresa.setCP(rs.getString("cp"));
		empresa.setCiutat(rs.getString("ciutat"));
		empresa.setProvincia(rs.getString("provincia"));
		empresa.setTelefon(rs.getString("telefon"));
		empresa.setFax(rs.getString("fax"));
		empresa.setEmail(rs.getString("email"));
		empresa.setDataConstitucio(rs.getTimestamp("dataconstitucio"));	
		empresa.setDocumentEscritura(getEscritra(empresa.getCif()));
		empresa.setAcreditacio1(rs.getBoolean("acreditacio1"));
		empresa.setDateExpAcreditacio1(rs.getTimestamp("dataexpacreditacio1"));
		empresa.setAcreditacio2(rs.getBoolean("acreditacio2"));
		empresa.setDateExpAcreditacio2(rs.getTimestamp("dataexpacreditacio2"));
		empresa.setAcreditacio3(rs.getBoolean("acreditacio3"));
		empresa.setDateExpAcreditacio3(rs.getTimestamp("dataexpacreditacio3"));	
		empresa.setClassificacioString(rs.getString("classificacio"));
		empresa.setAdministradorsString(getAdministradorsString(conn, empresa.getCif()));
		List<Empresa.Administrador> administradorsList = getAdministradors(conn, empresa.getCif());				
		empresa.setAdministradors(administradorsList);	
		empresa.setSolEconomica(getSolEconomica(empresa.getCif()));
		empresa.setSolTecnica(getSolTecnica(empresa.getCif()));
		empresa.setInformacioAdicional(rs.getString("informacioadicional"));
		empresa.setExerciciEconomic(rs.getTimestamp("exercicieconomic"));
		empresa.setRegistreMercantilData(rs.getTimestamp("dataregistremercantil"));
		empresa.setRatioAP(rs.getDouble("ratioap"));
		empresa.setClassificacioFileROLECE(getClassificacioROLECE(empresa.getCif()));
		empresa.setClassificacioFileJCCaib(getClassificacioJCCaib(empresa.getCif()));
		empresa.setClassificacioFileJCA(getClassificacioJCA(empresa.getCif()));		
		empresa.setDataVigenciaClassificacioROLECE(rs.getTimestamp("datavigenciaclassificaciorolece"));
		empresa.setDataVigenciaClassificacioJCCaib(rs.getTimestamp("datavigenciaclassificaciojccaib"));
		empresa.setDataVigenciaClassificacioJCA(rs.getTimestamp("datavigenciaclassificaciojca"));
		empresa.setPime(rs.getBoolean("pime"));
		return empresa;
	}
	
	private static Empresa initUTE(Connection conn, ResultSet rs) throws SQLException{
		Empresa empresa = new Empresa();
		 empresa.setCif(rs.getString("cif"));
		 empresa.setName(rs.getString("nom"));
		 empresa.setInformacioAdicional(rs.getString("informacioadicional"));
		 Empresa.UTE ute = empresa.new UTE();
		 String[] empresesString = rs.getString("empreses").split("#");
		 List<Empresa> empreses = new ArrayList<Empresa>();			
		 for(int i=0; i<empresesString.length; i++) {
			 if (! empresesString[i].isEmpty()) {
				 try {
					 empreses.add(EmpresaCore.findEmpresa(conn, empresesString[i]));
				 } catch (SQLException e) {
					 e.printStackTrace();
				 }	    	
			 }
		 }				 
		 ute.setEmpreses(empreses);
		 List<Empresa.Administrador> administradorsList = getAdministradors(conn, empresa.getCif());				
		 empresa.setAdministradors(administradorsList);	
		 empresa.setUte(ute);			 
		 return empresa;
	}
	
	public static void deleteEmpresa(Connection conn, String codi) throws SQLException {
		String sql = "DELETE FROM public.tbl_empreses"
					+ " WHERE cif= ?";		 
		PreparedStatement pstm = conn.prepareStatement(sql); 
		pstm.setString(1, codi);
		pstm.executeUpdate();
	}
	
	public static void insertEmpresa(Connection conn, Empresa empresa, List<Empresa.Administrador> administradorsList, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.tbl_empreses(cif, nom, direccio, cp, ciutat, provincia, telefon, fax, email, usumod, datamod)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, localtimestamp)";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, empresa.getCif());
		pstm.setString(2, empresa.getName());
		pstm.setString(3, empresa.getDireccio());
		pstm.setString(4, empresa.getCP());
		pstm.setString(5, empresa.getCiutat());
		pstm.setString(6, empresa.getProvincia());
		pstm.setString(7, empresa.getTelefon());
		pstm.setString(8, empresa.getFax());
		pstm.setString(9, empresa.getEmail());
		pstm.setInt(10, idUsuari);		
		pstm.executeUpdate();
		
		addAdministradors(conn, empresa.getCif(), administradorsList, idUsuari);
		
	}
	
	public static void updateEmpresa(Connection conn, Empresa empresa, String cifActual, List<Empresa.Administrador> administradorsList, int idUsuari) throws SQLException {
		 String sql = "UPDATE public.tbl_empreses"
				 	+ " SET cif=?, nom=?, direccio=?, cp=?, ciutat=?, provincia=?, telefon=?, fax=?, email=?, usumod=?, datamod=localtimestamp," 
				 		+ " acreditacio1=?, dataexpacreditacio1=?, acreditacio2=?, dataexpacreditacio2=?, acreditacio3=?, dataexpacreditacio3=?," 
				 		+ " classificacio=?, dataconstitucio=?, informacioadicional=?, exercicieconomic=?,  dataregistremercantil=?,  ratioap=?,"
				 		+ " datavigenciaclassificaciorolece=?, datavigenciaclassificaciojccaib=?, datavigenciaclassificaciojca=?, pime=?"
				 	+ " WHERE cif = ?";	 
		 PreparedStatement pstm = conn.prepareStatement(sql);	
		 pstm.setString(1, empresa.getCif());
		 pstm.setString(2, empresa.getName());
		 pstm.setString(3, empresa.getDireccio());
		 pstm.setString(4, empresa.getCP());
		 pstm.setString(5, empresa.getCiutat());
		 pstm.setString(6, empresa.getProvincia());
		 pstm.setString(7, empresa.getTelefon());
		 pstm.setString(8, empresa.getFax());
		 pstm.setString(9, empresa.getEmail());
		 pstm.setInt(10, idUsuari);
		 pstm.setBoolean(11, empresa.isAcreditacio1());		
		 if (empresa.getDateExpAcreditacio1() != null) {
			 pstm.setDate(12, new java.sql.Date(empresa.getDateExpAcreditacio1().getTime()));
		 } else {
			 pstm.setDate(12, null);
		 }		 
		 pstm.setBoolean(13, empresa.isAcreditacio2());
		 if (empresa.getDateExpAcreditacio2() != null) {
			 pstm.setDate(14, new java.sql.Date(empresa.getDateExpAcreditacio2().getTime()));
		 } else {
			 pstm.setDate(14, null);
		 }				
		 pstm.setBoolean(15, empresa.isAcreditacio3());
		 if (empresa.getDateExpAcreditacio3() != null) {
			 pstm.setDate(16, new java.sql.Date(empresa.getDateExpAcreditacio3().getTime()));
		 } else {
			 pstm.setDate(16, null);
		 }	
		 pstm.setString(17, empresa.getClassificacioString());		
		 if (empresa.getDataConstitucio() != null) {
			 pstm.setDate(18, new java.sql.Date(empresa.getDataConstitucio().getTime()));
		 } else {
			 pstm.setDate(18, null);
		 }	
		 pstm.setString(19, empresa.getInformacioAdicional());		 
		 if (empresa.getExerciciEconomic() != null) {
			 pstm.setDate(20, new java.sql.Date(empresa.getExerciciEconomic().getTime()));
		 } else {
			 pstm.setDate(20, null);
		 }	
		 if (empresa.getRegistreMercantilData() != null) {
			 pstm.setDate(21, new java.sql.Date(empresa.getRegistreMercantilData().getTime()));
		 } else {
			 pstm.setDate(21, null);
		 }	
		 pstm.setDouble(22, empresa.getRatioAP());
		 if (empresa.getDataVigenciaClassificacioROLECE() != null) {
			 pstm.setDate(23, new java.sql.Date(empresa.getDataVigenciaClassificacioROLECE().getTime()));
		 } else {
			 pstm.setDate(23, null);
		 }	
		 if (empresa.getDataVigenciaClassificacioJCCaib() != null) {
			 pstm.setDate(24, new java.sql.Date(empresa.getDataVigenciaClassificacioJCCaib().getTime()));
		 } else {
			 pstm.setDate(24, null);
		 }	
		 if (empresa.getDataVigenciaClassificacioJCA() != null) {
			 pstm.setDate(25, new java.sql.Date(empresa.getDataVigenciaClassificacioJCA().getTime()));
		 } else {
			 pstm.setDate(25, null);
		 }	
		 pstm.setBoolean(26, empresa.isPime());
		 pstm.setString(27, cifActual);
		 pstm.executeUpdate();
		
		 addAdministradors(conn, empresa.getCif(), administradorsList, idUsuari);
	 }
	 
	 public static Empresa findEmpresa(Connection conn, String cif) throws SQLException {
		 String sql = "SELECT " + SQL_CAMPS 
				 	+ " FROM public.tbl_empreses"
				 	+ " WHERE cif=?";
	 
		 PreparedStatement pstm = conn.prepareStatement(sql);
	     pstm.setString(1, cif);	 
	     ResultSet rs = pstm.executeQuery();	 
	     if (rs.next()) {
	    	 return initEmpresa(conn, rs);
	     } else { // és una UTE
	    	 sql = "SELECT cif, nom, empreses, informacioadicional"
					 	+ " FROM public.tbl_ute"
					 	+ " WHERE cif=?";
		 
	    	 
			 pstm = conn.prepareStatement(sql);
		     pstm.setString(1, cif);	 
		     rs = pstm.executeQuery();	 
		     if (rs.next()) {
		    	 return initUTE(conn, rs);
		     }
	     }
	     return null;
	 }
	 public static List<Empresa> getEmpreses(Connection conn) throws SQLException {
		 String sql = "SELECT " + SQL_CAMPS
				 	+ " FROM public.tbl_empreses";	 
		 PreparedStatement pstm = conn.prepareStatement(sql);	 
		 ResultSet rs = pstm.executeQuery();
		 List<Empresa> list = new ArrayList<Empresa>();
		 while (rs.next()) {
			 Empresa empresa = new Empresa();
			 empresa.setCif(rs.getString("cif"));
			 empresa.setName(rs.getString("nom"));
			 empresa.setDireccio(rs.getString("direccio"));
			 empresa.setCP(rs.getString("cp"));
			 empresa.setCiutat(rs.getString("ciutat"));
			 empresa.setProvincia(rs.getString("provincia"));
			 empresa.setTelefon(rs.getString("telefon"));
			 empresa.setFax(rs.getString("fax"));
			 empresa.setEmail(rs.getString("email"));
			 empresa.setPime(rs.getBoolean("pime"));
			 list.add(empresa);
		 }
	     return list;
     }	
	 
	 public static void insertUTE(Connection conn, Empresa empresa, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.tbl_ute(cif, nom, empreses, datacre, usucre)"
					+ " VALUES (?, ?, ?, localtimestamp, ?)";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, empresa.getCif());
		pstm.setString(2, empresa.getName());
		pstm.setString(3, empresa.getUte().getEmpresesString());		
		pstm.setInt(4, idUsuari);		
		pstm.executeUpdate();
	 }
		
	 public static List<Empresa> getEmpresesUTE(Connection conn) throws SQLException {
		 String sql = "SELECT cif, nom, empreses"
				 	+ " FROM public.tbl_ute";	 
		 PreparedStatement pstm = conn.prepareStatement(sql);	 
		 ResultSet rs = pstm.executeQuery();
		 List<Empresa> list = new ArrayList<Empresa>();
		 while (rs.next()) {
			 Empresa empresa = new Empresa();
			 empresa.setCif(rs.getString("cif"));
			 empresa.setName(rs.getString("nom"));
			 Empresa.UTE ute = empresa.new UTE();		
			 empresa.setUte(ute);			 
			 list.add(empresa);
		 }
	     return list;
     }
	 
	 public static void updateEmpresaUTE(Connection conn, Empresa empresa, List<Empresa.Administrador> administradorsList, int idUsuari) throws SQLException {
		 String sql = "UPDATE public.tbl_ute"
				 	+ " SET nom=?, informacioadicional=?"
				 	+ " WHERE cif = ?";
		 PreparedStatement pstm = conn.prepareStatement(sql);	 		 
		 pstm.setString(1, empresa.getName());
		 pstm.setString(2, empresa.getInformacioAdicional());
		 pstm.setString(3, empresa.getCif());
		 pstm.executeUpdate();
		 addAdministradors(conn, empresa.getCif(), administradorsList, idUsuari);
	 }
	 
	 private static void addAdministradors(Connection conn, String cif, List<Empresa.Administrador> administradorsList, int idUsuari) throws SQLException {
		 String sqlInsert = "INSERT INTO public.tbl_administradorsempresa(nifempresa, nom, dni, validfins, usucre, datacre, protocolmod, notarimod, datamod, tipus, datavalidacio, organvalidacio)"
			 			+ " VALUES (?, ?, ?, ?, ?, localtimestamp,?,?,?,?,?,?);";	 
		 PreparedStatement pstmInsert = null; 
		 String sqlUpdate = "UPDATE public.tbl_administradorsempresa"
				 		+ " SET nom=?, validfins=?, usucre=?, datacre=localtimestamp, protocolmod=?, notarimod=?, datamod=?, tipus=?, datavalidacio=?, organvalidacio=?"
			 			+ " WHERE dni = ?;";	 
		 PreparedStatement pstmUpdate = null; 
		 String sqlDelete = "DELETE FROM public.tbl_administradorsempresa"
			 			+ " WHERE dni = ?;";	 
	 PreparedStatement pstmDelete = null; 
		 for(Iterator<Empresa.Administrador> i = administradorsList.iterator(); i.hasNext();) {
			 Empresa.Administrador administrador = i.next();
			 if (administrador.isEliminar()) {
				 pstmDelete = conn.prepareStatement(sqlDelete);	
				 pstmDelete.setString(1, administrador.getDni());
				 pstmDelete.executeUpdate();
			 } else if (existAdministrador(conn, cif, administrador.getDni())) {
				 pstmUpdate = conn.prepareStatement(sqlUpdate);	
				 pstmUpdate.setString(1, administrador.getNom());
				 if (administrador.getDataValidesaFins() != null) {
					 pstmUpdate.setDate(2, new java.sql.Date(administrador.getDataValidesaFins().getTime()));
				 } else {
					 pstmUpdate.setDate(2, null);
				 }	
				 pstmUpdate.setInt(3, idUsuari);
				 pstmUpdate.setInt(4, administrador.getProtocolModificacio());
				 pstmUpdate.setString(5, administrador.getNotariModificacio());
				 if (administrador.getDataModificacio() != null) {
					 pstmUpdate.setDate(6, new java.sql.Date(administrador.getDataModificacio().getTime()));
				 } else {
					 pstmUpdate.setDate(6, null);
				 }				
				 pstmUpdate.setString(7, administrador.getTipus());
				 if (administrador.getDataValidacio() != null) {
					 pstmUpdate.setDate(8, new java.sql.Date(administrador.getDataValidacio().getTime()));
				 } else {
					 pstmUpdate.setDate(8, null);
				 }				 
				 pstmUpdate.setString(9, administrador.getEntitatValidacio());
				 pstmUpdate.setString(10, administrador.getDni());
				 pstmUpdate.executeUpdate();	
			 } else {
				 pstmInsert = conn.prepareStatement(sqlInsert);	
				 pstmInsert.setString(1, cif);
				 pstmInsert.setString(2, administrador.getNom());
				 pstmInsert.setString(3, administrador.getDni());
				 if (administrador.getDataValidesaFins() != null) {
					 pstmInsert.setDate(4, new java.sql.Date(administrador.getDataValidesaFins().getTime()));
				 } else {
					 pstmInsert.setDate(4, null);
				 }				
				 pstmInsert.setInt(5, idUsuari);
				 pstmInsert.setInt(6, administrador.getProtocolModificacio());
				 pstmInsert.setString(7, administrador.getNotariModificacio());
				 if (administrador.getDataModificacio() != null) {
					 pstmInsert.setDate(8, new java.sql.Date(administrador.getDataModificacio().getTime()));
				 } else {
					 pstmInsert.setDate(8, null);
				 }
				 pstmInsert.setString(9, administrador.getTipus());
				 if (administrador.getDataValidacio() != null) {
					 pstmInsert.setDate(10, new java.sql.Date(administrador.getDataValidacio().getTime()));
				 } else {
					 pstmInsert.setDate(10, null);
				 }				
				 pstmInsert.setString(11, administrador.getEntitatValidacio());
				 pstmInsert.executeUpdate();		
			 }
		 }
		
	 }
	 
	 private static boolean existAdministrador(Connection conn, String cif, String dni) throws SQLException {
		 boolean exist = false;
		 String sql = "SELECT dni"
				 	+ " FROM public.tbl_administradorsempresa"
				 	+ " WHERE nifempresa = ? AND dni = ?"; 
		 PreparedStatement pstm = conn.prepareStatement(sql); 
		 pstm.setString(1, cif);
		 pstm.setString(2, dni);
		 ResultSet rs = pstm.executeQuery();
		 while (rs.next()) {
			 exist = true;
		 }
		 return exist;
	 }
	 
	 private static List<Empresa.Administrador> getAdministradors(Connection conn, String cif) throws SQLException {
		 List<Empresa.Administrador> administradorsList = new ArrayList<Empresa.Administrador>();
		 String sql = "SELECT nom, dni, validfins, protocolmod, notarimod, datamod, tipus, datavalidacio, organvalidacio"
				 	+ " FROM public.tbl_administradorsempresa"
				 	+ " WHERE nifempresa = ?"; 
		 PreparedStatement pstm = conn.prepareStatement(sql); 
		 pstm.setString(1, cif);
		 ResultSet rs = pstm.executeQuery();
		 Empresa.Administrador administrador = new Empresa().new Administrador();
		 while (rs.next()) {
			 administrador = new Empresa().new Administrador();
			 administrador.setNom(rs.getString("nom"));
			 administrador.setDni(rs.getString("dni"));
			 administrador.setDataValidesaFins(rs.getTimestamp("validfins"));
			 administrador.setProtocolModificacio(rs.getInt("protocolmod"));
			 administrador.setNotariModificacio(rs.getString("notarimod"));
			 administrador.setDataModificacio(rs.getTimestamp("datamod"));
			 administrador.setTipus(rs.getString("tipus"));
			 administrador.setDataValidacio(rs.getTimestamp("datavalidacio"));
			 administrador.setEntitatValidacio(rs.getString("organvalidacio"));
			 administradorsList.add(administrador);
		 }
		 return administradorsList;
	 }
	 private static String getAdministradorsString(Connection conn, String cif) throws SQLException {
		String administradorsList = "";
		 String sql = "SELECT nom, dni, validfins, protocolmod, notarimod, datamod, tipus, datavalidacio, organvalidacio"
				 	+ " FROM public.tbl_administradorsempresa"
				 	+ " WHERE nifempresa = ?"; 
		 PreparedStatement pstm = conn.prepareStatement(sql); 
		 pstm.setString(1, cif);
		 ResultSet rs = pstm.executeQuery();
		 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	
		 while (rs.next()) {	
			 String validfins = "";
			 String datamod = "";
			 String datavalidacio = "";
			 if (rs.getDate("validfins") != null) validfins = df.format(rs.getDate("validfins"));
			 if (rs.getDate("datamod") != null) datamod = df.format(rs.getDate("datamod"));
			 if (rs.getDate("datavalidacio") != null) datavalidacio = df.format(rs.getDate("datavalidacio"));
			 administradorsList += rs.getString("nom") + "#" + rs.getString("dni") + "#" + rs.getString("tipus") + "#" + validfins
			 						+ "#" + rs.getString("notarimod") + "#" + rs.getString("protocolmod") + "#" + datamod + "#" + datavalidacio + "#" + rs.getString("organvalidacio") + ";";
		 }
		 return administradorsList;
	 }
	
	 public static Fitxer getEscritra(String cif) {
		 Fitxer fitxer = new Fitxer();
		 File dir = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Escritura");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Escritura/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 }
	 
	 public static Fitxer getClassificacioROLECE(String cif) {
		 Fitxer fitxer = new Fitxer();
		 File dir = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio/ROLECE");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio/ROLECE/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 } 
	 
	 public static Fitxer getClassificacioJCCaib(String cif) {
		 Fitxer fitxer = new Fitxer();
		 File dir = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio/JCCaib");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio/JCCaib/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 } 
	 
	 public static Fitxer getClassificacioJCA(String cif) {
		 Fitxer fitxer = new Fitxer();
		 File dir = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio/JCA");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio/JCA/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 } 
	 
	 public static Fitxer getSolEconomica(String cif) {
		 Fitxer fitxer = new Fitxer();
		 File dir = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Sol Economica");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Sol Economica/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 }
	 
	 public static Fitxer getSolTecnica(String cif) {
		 Fitxer fitxer = new Fitxer();
		 File dir = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Sol Tecnica");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Sol Tecnica/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 }
	 
	 public static void guardarFitxer(List<Fitxer> fitxers, String cif){		
			if (!fitxers.isEmpty()) {
				String fileName = "";
				// Crear directoris si no existeixen
				File tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses");
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}
				tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif);
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}
		        for(int i=0;i<fitxers.size();i++){
		            Fitxer fitxer = (Fitxer) fitxers.get(i);
		            if ("classificacioROLECE".equals(fitxer.getNom())) {
						tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio/ROLECE");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio/ROLECE/";
					}
		            if ("classificacioJCCaib".equals(fitxer.getNom())) {
						tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio/JCCaib");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio/JCCaib/";
					}
		            if ("classificacioJCA".equals(fitxer.getNom())) {
						tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio/JCA");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Classificacio/JCA/";
					}
		            if ("fileEconomica".equals(fitxer.getNom())) {
						tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Sol Economica");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Sol Economica/";
					}
					if ("FileTecnica".equals(fitxer.getNom())) {
						tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Sol Tecnica");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Sol Tecnica/";
					}
					if (("fileEscritura").equals(fitxer.getNom())) {
						tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Escritura");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = utils.Fitxers.RUTA_BASE + "/documents/Empreses/" + cif + "/Escritura/";
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
	 
}
