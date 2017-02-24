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
import utils.Fitxers.Fitxer;

public class EmpresaCore {
	
	static final String SQL_CAMPS = "cif, nom, direccio, cp, ciutat, provincia, telefon, fax, email, usumod, datamod, objectesocial,"
								+ " acreditacio1, dataexpacreditacio1, acreditacio2, dataexpacreditacio2, acreditacio3, dataexpacreditacio3,"
								+ " classificacio, dataconstitucio, informacioadicional";
	
	
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
		empresa.setObjecteSocial(rs.getString("objectesocial"));		
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
	
	 public static void updateEmpresa(Connection conn, Empresa empresa, List<Empresa.Administrador> administradorsList, int idUsuari) throws SQLException {
		 String sql = "UPDATE public.tbl_empreses"
				 	+ " SET nom=?, direccio=?, cp=?, ciutat=?, provincia=?, telefon=?, fax=?, email=?, usumod=?, datamod=localtimestamp, objectesocial=?," 
				 		+ " acreditacio1=?, dataexpacreditacio1=?, acreditacio2=?, dataexpacreditacio2=?, acreditacio3=?, dataexpacreditacio3=?," 
				 		+ " classificacio=?, dataconstitucio=?, informacioadicional=?"
				 	+ " WHERE cif = ?";	 		 
		 PreparedStatement pstm = conn.prepareStatement(sql);	 		 
		 pstm.setString(1, empresa.getName());
		 pstm.setString(2, empresa.getDireccio());
		 pstm.setString(3, empresa.getCP());
		 pstm.setString(4, empresa.getCiutat());
		 pstm.setString(5, empresa.getProvincia());
		 pstm.setString(6, empresa.getTelefon());
		 pstm.setString(7, empresa.getFax());
		 pstm.setString(8, empresa.getEmail());
		 pstm.setInt(9, idUsuari);
		 pstm.setString(10, empresa.getObjecteSocial());
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
		 pstm.setString(20, empresa.getCif());
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
	 
	     while (rs.next()) {
	    	 return initEmpresa(conn, rs);
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
			 list.add(empresa);
		 }
	     return list;
     }	
	 
	 private static void addAdministradors(Connection conn, String cif, List<Empresa.Administrador> administradorsList, int idUsuari) throws SQLException {
		 String sqlInsert = "INSERT INTO public.tbl_administradorsempresa(nifempresa, nom, dni, validfins, usucre, datacre, protocolmod, notarimod, datamod, tipus, datavalidacio)"
			 			+ " VALUES (?, ?, ?, ?, ?, localtimestamp,?,?,?,?, ?);";	 
		 PreparedStatement pstmInsert = null; 
		 String sqlUpdate = "UPDATE public.tbl_administradorsempresa"
				 		+ " SET nom=?, validfins=?, usucre=?, datacre=localtimestamp, protocolmod=?, notarimod=?, datamod=?, tipus=?, datavalidacio=?"
			 			+ " WHERE dni = ?;";	 
		 PreparedStatement pstmUpdate = null; 
		 String sqlDelete = "DELETE FROM public.tbl_administradorsempresa"
			 			+ " WHERE dni = ?;";	 
	 PreparedStatement pstmDelete = null; 
		 for(Iterator<Empresa.Administrador> i = administradorsList.iterator(); i.hasNext();) {
			 Empresa.Administrador administrador = i.next();
			 if (administrador.isEliminar()) {
				 System.out.println("Eliminam administrador: " + administrador.getDni());
				 pstmDelete = conn.prepareStatement(sqlDelete);	
				 pstmDelete.setString(1, administrador.getDni());
				 pstmDelete.executeUpdate();
			 } else if (existAdministrador(conn, cif, administrador.getDni())) {
				 System.out.println("Existeix administrador: " + administrador.getDni());
				 pstmUpdate = conn.prepareStatement(sqlUpdate);	
				 pstmUpdate.setString(1, administrador.getNom());
				 pstmUpdate.setDate(2, new java.sql.Date(administrador.getDataValidesaFins().getTime()));
				 pstmUpdate.setInt(3, idUsuari);
				 pstmUpdate.setInt(4, administrador.getProtocolModificacio());
				 pstmUpdate.setString(5, administrador.getNotariModificacio());
				 pstmUpdate.setDate(6, new java.sql.Date(administrador.getDataModificacio().getTime()));
				 pstmUpdate.setString(7, administrador.getTipus());
				 pstmUpdate.setDate(8, new java.sql.Date(administrador.getDataValidacio().getTime()));
				 pstmUpdate.setString(9, administrador.getDni());
				 pstmUpdate.executeUpdate();	
			 } else {
				 System.out.println("Nou administrador: " + administrador.getDni());
				 pstmInsert = conn.prepareStatement(sqlInsert);	
				 pstmInsert.setString(1, cif);
				 pstmInsert.setString(2, administrador.getNom());
				 pstmInsert.setString(3, administrador.getDni());
				 pstmInsert.setDate(4, new java.sql.Date(administrador.getDataValidesaFins().getTime()));
				 pstmInsert.setInt(5, idUsuari);
				 pstmInsert.setInt(6, administrador.getProtocolModificacio());
				 pstmInsert.setString(7, administrador.getNotariModificacio());
				 pstmInsert.setDate(8, new java.sql.Date(administrador.getDataModificacio().getTime()));
				 pstmInsert.setString(9, administrador.getTipus());
				 pstmInsert.setDate(10, new java.sql.Date(administrador.getDataValidacio().getTime()));
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
		 String sql = "SELECT nom, dni, validfins, protocolmod, notarimod, datamod, tipus, datavalidacio"
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
			 administradorsList.add(administrador);
		 }
		 return administradorsList;
	 }
	 private static String getAdministradorsString(Connection conn, String cif) throws SQLException {
		String administradorsList = "";
		 String sql = "SELECT nom, dni, validfins, protocolmod, notarimod, datamod, tipus, datavalidacio"
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
			 						+ "#" + rs.getString("notarimod") + "#" + rs.getString("protocolmod") + "#" + datamod + "#" + datavalidacio + ";";
		 }
		 return administradorsList;
	 }
	 
	 public static Fitxer getSolEconomica(String cif) {
		 Fitxer fitxer = new Fitxer();
		 File dir = new File(utils.Fitxers.RUTA_BASE + "/Empreses/" + cif + "/Sol Economica");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(utils.Fitxers.RUTA_BASE + "/Empreses/" + cif + "/Sol Economica/" + fichers[x].getName());
				 System.out.println(utils.Fitxers.RUTA_BASE + "/Empreses/" + cif + "/Sol Economica/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 }
	 
	 public static Fitxer getSolTecnica(String cif) {
		 Fitxer fitxer = new Fitxer();
		 File dir = new File(utils.Fitxers.RUTA_BASE + "/Empreses/" + cif + "/Sol Tecnica");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(utils.Fitxers.RUTA_BASE + "/Empreses/" + cif + "/Sol Tecnica/" + fichers[x].getName());
				 System.out.println(utils.Fitxers.RUTA_BASE + "/Empreses/" + cif + "/Sol Tecnica/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 }
	 
	 public static void guardarFitxer(List<Fitxer> fitxers, String cif){		
			if (!fitxers.isEmpty()) {
				String fileName = "";
				// Crear directoris si no existeixen
				File tmpFile = new File(utils.Fitxers.RUTA_BASE + "/Empreses");
				if (!tmpFile.exists()) {
					System.out.println(utils.Fitxers.RUTA_BASE + "/Empreses");
					tmpFile.mkdir();
				}
				tmpFile = new File(utils.Fitxers.RUTA_BASE + "/Empreses/" + cif);
				if (!tmpFile.exists()) {
					System.out.println(utils.Fitxers.RUTA_BASE + "/Empreses/" + cif);
					tmpFile.mkdir();
				}
		        for(int i=0;i<fitxers.size();i++){
		            Fitxer fitxer = (Fitxer) fitxers.get(i);
		            if ("fileEconomica".equals(fitxer.getNom())) {
						tmpFile = new File(utils.Fitxers.RUTA_BASE + "/Empreses/" + cif + "/Sol Economica");
						if (!tmpFile.exists()) {
							System.out.println(utils.Fitxers.RUTA_BASE + "/Empreses/" + cif + "/Sol Economica");
							tmpFile.mkdir();
						}
						fileName = utils.Fitxers.RUTA_BASE + "/Empreses/" + cif + "/Sol Economica/";
					}
					if ("FileTecnica".equals(fitxer.getNom())) {
						tmpFile = new File(utils.Fitxers.RUTA_BASE + "/Empreses/" + cif + "/Sol Tecnica");
						if (!tmpFile.exists()) {
							System.out.println(utils.Fitxers.RUTA_BASE + "/Empreses/" + cif + "/Sol Tecnica");
							tmpFile.mkdir();
						}
						fileName = utils.Fitxers.RUTA_BASE + "/Empreses/" + cif + "/Sol Tecnica/";
					}
					System.out.println(fileName);
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
