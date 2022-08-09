package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Empresa;
import bean.Empresa.Administrador;
import utils.Fitxers;
import utils.Fitxers.Fitxer;

public class EmpresaCore {
	
	static final String SQL_CAMPS = "cif, nom, direccio, cp, ciutat, provincia, telefon, fax, email, usumod, datamod,"
								+ " dataexpacreditacio1, dataexpacreditacio2, dataexpacreditacio3,"
								+ " classificacio, dataconstitucio, informacioadicional, exercicieconomic, dataregistremercantil, ratioap,"
								+ " datavigenciaclassificaciorolece, datavigenciaclassificaciojccaib, datavigenciaclassificaciojca, pime, activa,"
								+ " cifsuccesora, motiuextincio, prohibiciocontractar, prohibitcontractarfins, tipus, enconcurs, dataconcurs, infoconcurs, empresesute, intervencio, infointervencio,"
								+ " negatiuacreditacio1, negatiuacreditacio2, negatiuacreditacio3";
	
	
	private static Empresa initEmpresa(Connection conn, ResultSet rs) {		
		Empresa empresa = new Empresa();
		try {
			empresa.setCif(rs.getString("cif"));
			empresa.setName(rs.getString("nom"));
			empresa.setDireccio(rs.getString("direccio"));
			empresa.setCP(rs.getString("cp"));
			empresa.setCiutat(rs.getString("ciutat"));
			empresa.setProvincia(rs.getString("provincia"));
			empresa.setTelefon(rs.getString("telefon"));
			empresa.setFax(rs.getString("fax"));
			empresa.setEmail(rs.getString("email"));
			empresa.setTipus(rs.getString("tipus"));
			empresa.setDataConstitucio(rs.getTimestamp("dataconstitucio"));	
			empresa.setDocumentsEscrituraList(getEscritures(conn, empresa.getCif()));
			empresa.setDocumentsBancList(getDocumentsBanc(conn, empresa.getCif()));
			empresa.setDocumentREA(getDocumentREA(conn, empresa.getCif()));
			empresa.setDateExpAcreditacio1(rs.getTimestamp("dataexpacreditacio1"));
			empresa.setIsNegativaAcreditacio1(rs.getBoolean("negatiuacreditacio1"));
			empresa.setDateExpAcreditacio2(rs.getTimestamp("dataexpacreditacio2"));
			empresa.setIsNegativaAcreditacio2(rs.getBoolean("negatiuacreditacio2"));
			empresa.setDateExpAcreditacio3(rs.getTimestamp("dataexpacreditacio3"));	
			empresa.setIsNegativaAcreditacio3(rs.getBoolean("negatiuacreditacio3"));
			empresa.setClassificacioString(rs.getString("classificacio"));
			empresa.setAdministradorsString(getAdministradorsString(conn, empresa.getCif()));
			List<Empresa.Administrador> administradorsList = getAdministradors(conn, empresa.getCif());				
			empresa.setAdministradors(administradorsList);	
			empresa.setSolEconomica(getSolEconomica(conn, empresa.getCif()));
			empresa.setSolTecnica(getSolTecnica(conn, empresa.getCif()));
			empresa.setInformacioAdicional(rs.getString("informacioadicional"));
			empresa.setExerciciEconomic(rs.getTimestamp("exercicieconomic"));
			empresa.setRegistreMercantilData(rs.getTimestamp("dataregistremercantil"));
			empresa.setRatioAP(rs.getDouble("ratioap"));
			empresa.setClassificacioFileROLECE(getClassificacioROLECE(conn, empresa.getCif()));
			empresa.setClassificacioFileJCCaib(getClassificacioJCCaib(conn, empresa.getCif()));
			empresa.setClassificacioFileJCA(getClassificacioJCA(conn, empresa.getCif()));		
			empresa.setDataVigenciaClassificacioROLECE(rs.getTimestamp("datavigenciaclassificaciorolece"));
			empresa.setDataVigenciaClassificacioJCCaib(rs.getTimestamp("datavigenciaclassificaciojccaib"));
			empresa.setDataVigenciaClassificacioJCA(rs.getTimestamp("datavigenciaclassificaciojca"));
			empresa.setPime(rs.getBoolean("pime"));
			empresa.setActiva(rs.getBoolean("activa"));
			empresa.setProhibicioContractar(rs.getBoolean("prohibiciocontractar"));			
			empresa.setDocumentsProhibicioContractarList(getDocumentsProhibicioContractar(conn, rs.getString("cif")));
			empresa.setProhibitContractarFins(rs.getTimestamp("prohibitcontractarfins"));
			empresa.setEnConcurs(rs.getBoolean("enconcurs"));
			empresa.setDataConcurs(rs.getTimestamp("dataconcurs"));
			empresa.setInfoConcurs(rs.getString("infoconcurs"));
			
			if (!rs.getBoolean("activa")) {
				empresa.setSuccesora(findSuccesora(conn, rs.getString("cifsuccesora")));
				empresa.setMotiuExtincio(rs.getString("motiuExtincio"));
				empresa.setExtincioFile(getExtincioFile(conn, empresa.getCif()));
				empresa.setSuccesoraFile(getSuccesioFile(conn, empresa.getCif()));
				empresa.setDocumentsConcursList(getConcursFile(conn, empresa.getCif()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return empresa;
	}
	
	private static Empresa initUTE(Connection conn, ResultSet rs) {
		Empresa empresa = new Empresa();
		 try {
			empresa.setCif(rs.getString("cif"));
			empresa.setName(rs.getString("nom"));
			 empresa.setInformacioAdicional(rs.getString("informacioadicional"));
			 empresa.setActiva(rs.getBoolean("activa"));
			 Empresa.UTE ute = empresa.new UTE();
			 String[] empresesString = rs.getString("empreses").split("#");
			 List<Empresa> empreses = new ArrayList<Empresa>();			
			 for(int i=0; i<empresesString.length; i++) {
				 if (! empresesString[i].isEmpty()) {
					 empreses.add(EmpresaCore.findEmpresa(conn, empresesString[i]));	    	
				 }
			 }				 
			 ute.setEmpreses(empreses);
			 List<Empresa.Administrador> administradorsList = getAdministradors(conn, empresa.getCif());				
			 empresa.setAdministradors(administradorsList);	
			 empresa.setUte(ute);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 			 
		 return empresa;
	}
	
	public static void deleteEmpresa(Connection conn, String codi) {
		String sql = "DELETE FROM public.tbl_empreses"
					+ " WHERE cif= ?";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codi);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static void extincioEmpresa(Connection conn, String codi, String motiuExtincio) {
		String sql = "UPDATE public.tbl_empreses"
					+ " SET activa = false, motiuextincio = ?"
					+ " WHERE cif= ?";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, motiuExtincio);
			pstm.setString(2, codi);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static void addSuccesora(Connection conn, String codi, String codiSuccesora) {
		String sql = "UPDATE public.tbl_empreses"
					+ " SET activa = false, cifsuccesora = ?"
					+ " WHERE cif= ?";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, codiSuccesora);
			pstm.setString(2, codi);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static void prohibicioContractarEmpresa(Connection conn, String cif, Date dataLimit) {
		String sql = "UPDATE public.tbl_empreses"
				+ " SET activa = false, prohibiciocontractar = true, prohibitcontractarfins = ?"
				+ " WHERE cif= ?";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setDate(1, new java.sql.Date(dataLimit.getTime()));
			pstm.setString(2, cif);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static void addConcurs(Connection conn, String cif, String infoConcurs, Date dataConcurs, boolean isIntervencio, String infoIntervencio) {
		String sql = "UPDATE public.tbl_empreses"
				+ " SET activa = false, enconcurs = true, dataconcurs = ?, infoconcurs = ?, intervencio = ?, infointervencio = ?"
				+ " WHERE cif= ?";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setDate(1, new java.sql.Date(dataConcurs.getTime()));
			pstm.setString(2, infoConcurs);
			pstm.setBoolean(3, isIntervencio);
			pstm.setString(4, infoIntervencio);
			pstm.setString(5, cif);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}	
	
	public static void insertEmpresa(Connection conn, Empresa empresa, List<Empresa.Administrador> administradorsList, int idUsuari) {
		String sql = "INSERT INTO public.tbl_empreses(cif, nom, direccio, cp, ciutat, provincia, telefon, fax, email, usumod, datamod, activa, tipus)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, localtimestamp, true, ?)";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
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
			pstm.setString(11, empresa.getTipus());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
				
	}
	
	public static void updateEmpresa(Connection conn, Empresa empresa, String cifActual, List<Empresa.Administrador> administradorsList, int idUsuari) {
		 String sql = "UPDATE public.tbl_empreses"
				 	+ " SET cif=?, nom=?, direccio=?, cp=?, ciutat=?, provincia=?, telefon=?, fax=?, email=?, usumod=?, datamod=localtimestamp," 
				 		+ " dataexpacreditacio1=?, dataexpacreditacio2=?, dataexpacreditacio3=?," 
				 		+ " classificacio=?, dataconstitucio=?, informacioadicional=?, exercicieconomic=?,  dataregistremercantil=?,  ratioap=?,"
				 		+ " datavigenciaclassificaciorolece=?, datavigenciaclassificaciojccaib=?, datavigenciaclassificaciojca=?, pime=?, prohibiciocontractar=?, prohibitcontractarfins=?, tipus=?,"
				 		+ " negatiuacreditacio1 = ?, negatiuacreditacio2 = ?, negatiuacreditacio3 = ?"
				 	+ " WHERE cif = ?";	 
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
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
				
			 if (empresa.getDateExpAcreditacio1() != null) {
				 pstm.setDate(11, new java.sql.Date(empresa.getDateExpAcreditacio1().getTime()));
			 } else {
				 pstm.setDate(11, null);
			 }		 
			
			 if (empresa.getDateExpAcreditacio2() != null) {
				 pstm.setDate(12, new java.sql.Date(empresa.getDateExpAcreditacio2().getTime()));
			 } else {
				 pstm.setDate(12, null);
			 }				
			 if (empresa.getDateExpAcreditacio3() != null) {
				 pstm.setDate(13, new java.sql.Date(empresa.getDateExpAcreditacio3().getTime()));
			 } else {
				 pstm.setDate(13, null);
			 }	
			 pstm.setString(14, empresa.getClassificacioString());		
			 if (empresa.getDataConstitucio() != null) {
				 pstm.setDate(15, new java.sql.Date(empresa.getDataConstitucio().getTime()));
			 } else {
				 pstm.setDate(15, null);
			 }	
			 pstm.setString(16, empresa.getInformacioAdicional());		 
			 if (empresa.getExerciciEconomic() != null) {
				 pstm.setDate(17, new java.sql.Date(empresa.getExerciciEconomic().getTime()));
			 } else {
				 pstm.setDate(17, null);
			 }	
			 if (empresa.getRegistreMercantilData() != null) {
				 pstm.setDate(18, new java.sql.Date(empresa.getRegistreMercantilData().getTime()));
			 } else {
				 pstm.setDate(18, null);
			 }	
			 pstm.setDouble(19, empresa.getRatioAP());
			 if (empresa.getDataVigenciaClassificacioROLECE() != null) {
				 pstm.setDate(20, new java.sql.Date(empresa.getDataVigenciaClassificacioROLECE().getTime()));
			 } else {
				 pstm.setDate(20, null);
			 }	
			 if (empresa.getDataVigenciaClassificacioJCCaib() != null) {
				 pstm.setDate(21, new java.sql.Date(empresa.getDataVigenciaClassificacioJCCaib().getTime()));
			 } else {
				 pstm.setDate(21, null);
			 }	
			 if (empresa.getDataVigenciaClassificacioJCA() != null) {
				 pstm.setDate(22, new java.sql.Date(empresa.getDataVigenciaClassificacioJCA().getTime()));
			 } else {
				 pstm.setDate(22, null);
			 }	
			 pstm.setBoolean(23, empresa.isPime());
			 pstm.setBoolean(24, empresa.isProhibicioContractar());
			 if (empresa.getProhibitContractarFins() != null) {
				 pstm.setDate(25, new java.sql.Date(empresa.getProhibitContractarFins().getTime()));
			 } else {
				 pstm.setDate(25, null);
			 }	
			 pstm.setString(26, empresa.getTipus());
			 pstm.setBoolean(27, empresa.isNegativaAcreditacio1());
			 pstm.setBoolean(28, empresa.isNegativaAcreditacio2());
			 pstm.setBoolean(29, empresa.isNegativaAcreditacio3());
			 pstm.setString(30, cifActual);
			 pstm.executeUpdate();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		 	
	 }
	 
	 public static Empresa findEmpresa(Connection conn, String cif) {
		 String sql = "SELECT " + SQL_CAMPS 
				 	+ " FROM public.tbl_empreses"
				 	+ " WHERE cif=? OR cif = ?";
	 
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, cif);
			pstm.setString(2, cif.replace("ES", ""));
		     ResultSet rs = pstm.executeQuery();	 
		     if (rs.next()) {
		    	 return initEmpresa(conn, rs);
		     } else { //És una UTE
		    	 sql = "SELECT cif, nom, empreses, informacioadicional, activa"
						 	+ " FROM public.tbl_ute"
						 	+ " WHERE cif=?";
			 
		    	 
				 pstm = conn.prepareStatement(sql);
			     pstm.setString(1, cif);	 
			     rs = pstm.executeQuery();	 
			     if (rs.next()) {
			    	 return initUTE(conn, rs);
			     }
		     }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	     return null;
	 }
	 public static List<Empresa> getEmpreses(Connection conn) {
		 List<Empresa> list = new ArrayList<Empresa>();
		 String sql = "SELECT " + SQL_CAMPS
				 	+ " FROM public.tbl_empreses";	 
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			 
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
				 empresa.setActiva(rs.getBoolean("activa"));
				 empresa.setTipus(rs.getString("tipus"));
				 list.add(empresa);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		 
	     return list;
     }	
	 
	 public static List<Empresa> getDespesaEmpreses(Connection conn, Date dataIni, Date dataFi) {
		 List<Empresa> list = new ArrayList<Empresa>();
		 String sql = "SELECT e.cif AS cif, e.nom AS nom, e.direccio AS direccio, e.ciutat AS ciutat, e.provincia AS provincia, e.email AS email, e.activa AS activa, SUM(o.pbase) AS pbase, SUM(o.plic) AS plic, e.tipus AS tipus"
				 	+ " FROM public.tbl_empresaoferta o"
				 	+ " 	LEFT JOIN public.tbl_empreses e ON o.cifempresa = e.cif"
				 	+ " 	LEFT JOIN public.tbl_informeactuacio i ON o.idinforme = i.idinf"
				 	+ "		LEFT JOIN public.tbl_propostesinforme p ON p.idinf = i.idinf"
				 	+ " 	LEFT JOIN public.tbl_expedient x ON x.expcontratacio = i.expcontratacio"
				 	+ " WHERE x.anulat = false AND o.seleccionada = true AND p.seleccionada = true AND i.dataaprovacio >= '01/01/2020' AND o.datacre >= ? AND o.datacre <= ? AND ((o.pbase < 15000 AND p.tipusobra != 'obr') OR (o.pbase < 40000 AND p.tipusobra = 'obr'))"  
					+ " GROUP BY e.cif"
				 	+ " ORDER BY e.cif";
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
			 pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
			 ResultSet rs = pstm.executeQuery();
			
			 while (rs.next()) {
				 Empresa empresa = new Empresa();
				 empresa.setCif(rs.getString("cif"));
				 empresa.setName(rs.getString("nom"));
				 empresa.setDireccio(rs.getString("direccio"));
				 empresa.setCiutat(rs.getString("ciutat"));
				 empresa.setProvincia(rs.getString("provincia"));
				 empresa.setEmail(rs.getString("email"));
				 empresa.setTipus(rs.getString("tipus"));
				 empresa.setActiva(rs.getBoolean("activa"));
				 empresa.setTotalPbasePeriode(rs.getDouble("pbase"));
				 empresa.setTotalPLicPeriode(rs.getDouble("plic"));
				 empresa.setTotalPbasePeriodeAdjudicat(getTotalBaseAdjudicat(conn, rs.getString("cif"), dataIni, dataFi));
				 list.add(empresa);
			 }
			 sql = "SELECT e.cif AS cif, SUM(m.pbase) AS pbase, e.nom AS nom, e.direccio AS direccio, e.ciutat AS ciutat, e.provincia AS provincia, e.email AS email, e.activa AS activa, SUM(m.plic) AS plic, e.tipus AS tipus"
					 + " FROM public.tbl_modificacioinforme m"
					 + " 	LEFT JOIN public.tbl_empreses e ON m.cifempresa = e.cif"
					 + " 	LEFT JOIN public.tbl_informeactuacio i ON m.idinforme = i.idinf"
					 + " 	LEFT JOIN public.tbl_empresaoferta o ON i.idinf = o.idinforme" 
					 + " 	LEFT JOIN public.tbl_propostesinforme p ON p.idinf = i.idinf"
					 + " 	LEFT JOIN public.tbl_expedient x ON x.expcontratacio = i.expcontratacio"
					 + " WHERE x.anulat = false AND o.seleccionada = true AND p.seleccionada = true AND i.dataaprovacio >= '01/01/2020' AND o.datacre >= ? AND o.datacre <= ? AND ((o.pbase < 15000 AND p.tipusobra != 'obr') OR (o.pbase < 40000 AND p.tipusobra = 'obr'))"
					 + " GROUP BY e.cif"
					 + " ORDER BY e.cif";		 		
			 pstm = conn.prepareStatement(sql);	 
			 pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
			 pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
			 rs = pstm.executeQuery();
			 while (rs.next()) {
				 Empresa empresa = new Empresa();
				 empresa.setCif(rs.getString("cif"));
				 empresa.setName(rs.getString("nom"));
				 empresa.setDireccio(rs.getString("direccio"));
				 empresa.setCiutat(rs.getString("ciutat"));
				 empresa.setProvincia(rs.getString("provincia"));
				 empresa.setEmail(rs.getString("email"));
				 empresa.setTipus(rs.getString("tipus"));
				 empresa.setActiva(rs.getBoolean("activa"));
				 empresa.setTotalPbasePeriode(rs.getDouble("pbase"));
				 empresa.setTotalPLicPeriode(rs.getDouble("plic"));
				 empresa.setTotalPbasePeriodeAdjudicat(getTotalBaseAdjudicat(conn, rs.getString("cif"), dataIni, dataFi));
				 list.add(empresa);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		 
	     return list;
  }	
	 
	 public static double getDespesaEmpresa(Connection conn, String cif, Date dataIni, Date dataFi) {
		 String sql = "SELECT SUM(o.pbase) AS pbase, SUM(o.plic) AS plic"
				 	+ " FROM public.tbl_empresaoferta o"
				 	+ " 	LEFT JOIN public.tbl_empreses e ON o.cifempresa = e.cif"
				 	+ " 	LEFT JOIN public.tbl_informeactuacio i ON o.idinforme = i.idinf"
				 	+ "		LEFT JOIN public.tbl_propostesinforme p ON p.idinf = i.idinf"
				 	+ " 	LEFT JOIN public.tbl_expedient x ON x.expcontratacio = i.expcontratacio"
				 	+ " WHERE e.cif = ? AND x.anulat = false AND o.seleccionada = true AND p.seleccionada = true AND i.dataaprovacio >= '01/01/2020' AND o.datacre >= ? AND o.datacre <= ? AND ((o.pbase < 15000 AND p.tipusobra != 'obr') OR (o.pbase < 40000 AND p.tipusobra = 'obr'))"  
					+ " GROUP BY e.cif"
				 	+ " ORDER BY e.cif";
		 PreparedStatement pstm;
		 double totalAdjudicat = 0;
		try {
			pstm = conn.prepareStatement(sql);
			 pstm.setString(1, cif);
			 pstm.setDate(2, new java.sql.Date(dataIni.getTime()));
			 pstm.setDate(3, new java.sql.Date(dataFi.getTime()));
			 ResultSet rs = pstm.executeQuery();
			
			 if (rs.next()) totalAdjudicat = rs.getDouble("pbase");
			 sql = "SELECT e.cif AS cif, SUM(m.pbase) AS pbase, e.nom AS nom, e.direccio AS direccio, e.ciutat AS ciutat, e.provincia AS provincia, e.email AS email, e.activa AS activa, SUM(m.plic) AS plic"
					 + " FROM public.tbl_modificacioinforme m"
					 + " 	LEFT JOIN public.tbl_empreses e ON m.cifempresa = e.cif"
					 + " 	LEFT JOIN public.tbl_informeactuacio i ON m.idinforme = i.idinf"
					 + " 	LEFT JOIN public.tbl_empresaoferta o ON i.idinf = o.idinforme" 
					 + " 	LEFT JOIN public.tbl_propostesinforme p ON p.idinf = i.idinf"
					 + " 	LEFT JOIN public.tbl_expedient x ON x.expcontratacio = i.expcontratacio"
					 + " WHERE  e.cif = ? AND x.anulat = false AND o.seleccionada = true AND p.seleccionada = true AND i.dataaprovacio >= '01/01/2020' AND o.datacre >= ? AND o.datacre <= ? AND ((o.pbase < 15000 AND p.tipusobra != 'obr') OR (o.pbase < 40000 AND p.tipusobra = 'obr'))"
					 + " GROUP BY e.cif"
					 + " ORDER BY e.cif";		 		
			 pstm = conn.prepareStatement(sql);	 
			 pstm.setString(1, cif);
			 pstm.setDate(2, new java.sql.Date(dataIni.getTime()));
			 pstm.setDate(3, new java.sql.Date(dataFi.getTime()));
			 rs = pstm.executeQuery();
			 if (rs.next()) totalAdjudicat += rs.getDouble("pbase");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	     return totalAdjudicat;
  }	
	 
	 private static Empresa findSuccesora(Connection conn, String cif) {
		 Empresa succesora = new Empresa();
		 String sql = "SELECT " + SQL_CAMPS
				 	+ " FROM public.tbl_empreses"
				 	+ " WHERE cif = ?";
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			 pstm.setString(1, cif);
			 ResultSet rs = pstm.executeQuery();
			 if (rs.next()) {
				 succesora = initEmpresa(conn, rs);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return succesora;
	 }
	 
	 public static void insertUTE(Connection conn, Empresa empresa, int idUsuari) {
		String sql = "INSERT INTO public.tbl_ute(cif, nom, empreses, datacre, usucre, activa)"
					+ " VALUES (?, ?, ?, localtimestamp, ?, true)";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, empresa.getCif());
			pstm.setString(2, empresa.getName());
			pstm.setString(3, empresa.getUte().getEmpresesString());		
			pstm.setInt(4, idUsuari);		
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	 }
		
	 public static List<Empresa> getEmpresesUTE(Connection conn) {
		 String sql = "SELECT cif, nom, empreses, activa"
				 	+ " FROM public.tbl_ute";	 
		 PreparedStatement pstm;
		 List<Empresa> list = new ArrayList<Empresa>();
		try {
			pstm = conn.prepareStatement(sql);
			 ResultSet rs = pstm.executeQuery();
			
			 while (rs.next()) {
				 Empresa empresa = new Empresa();
				 empresa.setCif(rs.getString("cif"));
				 empresa.setName(rs.getString("nom"));
				 empresa.setActiva(rs.getBoolean("activa"));
				 Empresa.UTE ute = empresa.new UTE();		
				 empresa.setUte(ute);			 
				 list.add(empresa);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	     return list;
     }
	 
	 public static void updateEmpresaUTE(Connection conn, Empresa empresa, List<Empresa.Administrador> administradorsList, int idUsuari) {
		 String sql = "UPDATE public.tbl_ute"
				 	+ " SET nom=?, informacioadicional=?"
				 	+ " WHERE cif = ?";
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			 pstm.setString(1, empresa.getName());
			 pstm.setString(2, empresa.getInformacioAdicional());
			 pstm.setString(3, empresa.getCif());
			 pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 		 
		
	 }
	 
	 public static void addAdministrador(Connection conn, String cif, Empresa.Administrador administrador, int idUsuari) {
		 String sqlInsert = "INSERT INTO public.tbl_administradorsempresa(nifempresa, nom, dni, validfins, usucre, datacre, protocolmod, notarimod, datamod, tipus, datavalidacio, organvalidacio)"
			 			+ " VALUES (?, ?, ?, ?, ?, localtimestamp,?,?,?,?,?,?);";	 
		 PreparedStatement pstmInsert = null; 			 
		 try {
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
			 pstmInsert.setString(6, administrador.getProtocolModificacio());
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	 }
	 
	 public static void deleteAdministrador(Connection conn, String cif, String dniAdministrador, int idUsuari) {
		 String sql = "DELETE FROM public.tbl_administradorsempresa"
				 	+ " WHERE nifempresa = ? AND dni = ?";
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			 pstm.setString(1, cif);
			 pstm.setString(2, dniAdministrador);
			 pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		 Fitxer documentAdministrador = getDocumentAdministrador(conn, cif, dniAdministrador);
		 if (documentAdministrador.getRuta() != null && !documentAdministrador.getRuta().isEmpty())  Fitxers.eliminarFitxer(conn, idUsuari, documentAdministrador.getRuta());
	 }
	 
	 public static void updateAdministrador(Connection conn, String cif, String dniAdministrador, Administrador administrador) {
		 String sql = "UPDATE public.tbl_administradorsempresa"
				 	+ " SET nifempresa=?, nom=?, dni=?, validfins=?, protocolmod=?, notarimod=?, datamod=?, tipus=?, datavalidacio=?, organvalidacio=?"
				 	+ " WHERE dni = ? AND nifempresa = ?";
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			 pstm.setString(1, cif);
			 pstm.setString(2, administrador.getNom());
			 pstm.setString(3, administrador.getDni());
			 if (administrador.getDataValidesaFins() != null) {
				 pstm.setDate(4, new java.sql.Date(administrador.getDataValidesaFins().getTime()));
			 } else {
				 pstm.setDate(4, null);
			 }	
			 pstm.setString(5, administrador.getProtocolModificacio());
			 pstm.setString(6, administrador.getNotariModificacio());
			 if (administrador.getDataModificacio() != null) {
				 pstm.setDate(7, new java.sql.Date(administrador.getDataModificacio().getTime()));
			 } else {
				 pstm.setDate(7, null);
			 }
			 pstm.setString(8, administrador.getTipus());
			 if (administrador.getDataValidacio() != null) {
				 pstm.setDate(9, new java.sql.Date(administrador.getDataValidacio().getTime()));
			 } else {
				 pstm.setDate(9, null);
			 }				
			 pstm.setString(10, administrador.getEntitatValidacio());
			 pstm.setString(11, dniAdministrador);
			 pstm.setString(12, cif);
			 pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	 }
	 
	 public static Administrador findAdministrador(Connection conn, String cif, String dniAdministrador) {
		 Administrador administrador = new Empresa().new Administrador();
		 String sql = "SELECT nom, dni, validfins, protocolmod, notarimod, datamod, tipus, datavalidacio, organvalidacio"
				 	+ " FROM public.tbl_administradorsempresa"
				 	+ " WHERE nifempresa = ? AND dni = ?";
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			 pstm.setString(1, cif);
			 pstm.setString(2, dniAdministrador);
			 ResultSet rs = pstm.executeQuery();
			 if (rs.next()) {
				 administrador = new Empresa().new Administrador();
				 administrador.setNom(rs.getString("nom"));
				 administrador.setDni(rs.getString("dni"));
				 administrador.setDataValidesaFins(rs.getTimestamp("validfins"));
				 administrador.setProtocolModificacio(rs.getString("protocolmod"));
				 administrador.setNotariModificacio(rs.getString("notarimod"));
				 administrador.setDataModificacio(rs.getTimestamp("datamod"));
				 administrador.setTipus(rs.getString("tipus"));
				 administrador.setDataValidacio(rs.getTimestamp("datavalidacio"));
				 administrador.setEntitatValidacio(rs.getString("organvalidacio"));
				 administrador.setDocumentAdministrador(getDocumentAdministrador(conn, cif, administrador.getDni()));
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		 return administrador;
	 }
	 
	 private static List<Empresa.Administrador> getAdministradors(Connection conn, String cif) {
		 List<Empresa.Administrador> administradorsList = new ArrayList<Empresa.Administrador>();
		 String sql = "SELECT nom, dni, validfins, protocolmod, notarimod, datamod, tipus, datavalidacio, organvalidacio"
				 	+ " FROM public.tbl_administradorsempresa"
				 	+ " WHERE nifempresa = ?"; 
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, cif);
			 ResultSet rs = pstm.executeQuery();
			 Empresa.Administrador administrador = new Empresa().new Administrador();
			 while (rs.next()) {
				 administrador = new Empresa().new Administrador();
				 administrador.setNom(rs.getString("nom"));
				 administrador.setDni(rs.getString("dni"));
				 administrador.setDataValidesaFins(rs.getTimestamp("validfins"));
				 administrador.setProtocolModificacio(rs.getString("protocolmod"));
				 administrador.setNotariModificacio(rs.getString("notarimod"));
				 administrador.setDataModificacio(rs.getTimestamp("datamod"));
				 administrador.setTipus(rs.getString("tipus"));
				 administrador.setDataValidacio(rs.getTimestamp("datavalidacio"));
				 administrador.setEntitatValidacio(rs.getString("organvalidacio"));
				 administrador.setDocumentAdministrador(getDocumentAdministrador(conn, cif, administrador.getDni()));
				 administradorsList.add(administrador);
			 }
			 sql = "SELECT b.ref AS ref, b.databastanteo AS databastanteo, b.empresa AS empresa, b.personafacultada AS personafacultada, b.carrec AS carrec, b.anybastanteo AS anybastanteo, e.refescritura AS refescritura, e.escritura AS escritura, e.dataescritura AS dataescritura, e.nprotocol AS nprotocol, e.notari AS notari"
						+ " FROM public.tbl_bastanteos b LEFT JOIN public.tbl_escritura e ON b.ref = e.refbastanteo"
						+ " WHERE b.empresa = ?";
		 
			 pstm = conn.prepareStatement(sql);
			 pstm.setString(1, cif);		
			 rs = pstm.executeQuery();
			 while (rs.next()) {
				 administrador = new Empresa().new Administrador();
				 administrador.setNom(rs.getString("personafacultada"));
				 administrador.setDni(rs.getString("ref"));
				 //administrador.setDataValidesaFins(rs.getTimestamp("validfins"));
				 administrador.setProtocolModificacio(rs.getString("nprotocol"));
				 administrador.setNotariModificacio(rs.getString("notari"));
				 administrador.setDataModificacio(rs.getTimestamp("dataescritura"));
				 administrador.setTipus(rs.getString("escritura"));
				 administrador.setDataValidacio(rs.getTimestamp("databastanteo"));
				 administrador.setEntitatValidacio("ibisec");
				 //administrador.setDocumentAdministrador(getDocumentAdministrador(conn, cif, administrador.getDni()));
				 administradorsList.add(administrador);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		 return administradorsList;
	 }
	 
	 private static Fitxer getDocumentAdministrador(Connection conn, String cif, String cifAdministrador) {
		 Fitxer fitxer = new Fitxer();		
		 String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/Administrador/" + cifAdministrador);
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 			 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/Administrador/" + cifAdministrador +"/" + fichers[x].getName());
			 }
		 }
		 
		 return fitxer;
	 }
	 
	 private static String getAdministradorsString(Connection conn, String cif) {
		String administradorsList = "";
		 String sql = "SELECT nom, dni, validfins, protocolmod, notarimod, datamod, tipus, datavalidacio, organvalidacio"
				 	+ " FROM public.tbl_administradorsempresa"
				 	+ " WHERE nifempresa = ?"; 
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		 return administradorsList;
	 }
	
	 public static List<Fitxer> getEscritures(Connection conn, String cif) {
		 List<Fitxer> fitxersList = new ArrayList<Fitxer>();		
		 String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/Escritura");
		 File[] fichers = dir.listFiles();
		 Fitxer fitxer = new Fitxer();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/Escritura/" + fichers[x].getName());
				 fitxersList.add(fitxer);
			 }
		 }
		 return fitxersList;
	 }
	 
	 public static List<Fitxer> getDocumentsBanc(Connection conn, String cif) {
		 List<Fitxer> fitxersList = new ArrayList<Fitxer>();		
		 String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/IBAN");
		 File[] fichers = dir.listFiles();
		 Fitxer fitxer = new Fitxer();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/IBAN/" + fichers[x].getName());
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
	 
	 public static List<Fitxer> getDocumentsProhibicioContractar(Connection conn, String cif) {
		 List<Fitxer> fitxersList = new ArrayList<Fitxer>();
		 String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/Prohibicio");
		 File[] fichers = dir.listFiles();
		 Fitxer fitxer = new Fitxer();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/Prohibicio/" + fichers[x].getName());
				 fitxersList.add(fitxer);
			 }
		 }
		 return fitxersList;
	 }
	 
	 public static Fitxer getDocumentREA(Connection conn, String cif) {
		 Fitxer fitxer = new Fitxer();
		
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/REA");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/REA/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 }
	 
	 public static Fitxer getClassificacioROLECE(Connection conn, String cif) {
		 Fitxer fitxer = new Fitxer();
		
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/Classificacio/ROLECE");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/Classificacio/ROLECE/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 } 
	 
	 public static Fitxer getClassificacioJCCaib(Connection conn, String cif) {
		 Fitxer fitxer = new Fitxer();
		
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/Classificacio/JCCaib");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/Classificacio/JCCaib/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 } 
	 
	 public static Fitxer getClassificacioJCA(Connection conn, String cif) {
		 Fitxer fitxer = new Fitxer();
		
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/Classificacio/JCA");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/Classificacio/JCA/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 } 
	 
	 public static Fitxer getSolEconomica(Connection conn, String cif) {
		 Fitxer fitxer = new Fitxer();
		
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/Sol Economica");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/Sol Economica/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 }
	 
	 public static Fitxer getSolTecnica(Connection conn, String cif) {
		 Fitxer fitxer = new Fitxer();
		 
			String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/Sol Tecnica");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/Sol Tecnica/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 }
	 
	 public static Fitxer getExtincioFile(Connection conn, String cif) {
		 Fitxer fitxer = new Fitxer();
		
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/Extincio");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/Extincio/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 } 
	 
	 public static Fitxer getSuccesioFile(Connection conn, String cif) {
		 Fitxer fitxer = new Fitxer();
		 
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/Succesio");
		 File[] fichers = dir.listFiles();
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/Succesio/" + fichers[x].getName());
			 }
		 }
		 return fitxer;
	 } 
	 
	 public static List<Fitxer> getConcursFile(Connection conn, String cif) {
		 List<Fitxer> arxiu = new ArrayList<Fitxer>();
		 
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		 File dir = new File(ruta + "/documents/Empreses/" + cif + "/Concurs");
		 File[] fichers = dir.listFiles();
		 Fitxer fitxer;
		 if (fichers == null) {
			
		 } else { 
			 for (int x=0;x<fichers.length;x++) {
				 fitxer = new Fitxer();
				 fitxer.setNom(fichers[x].getName());
				 fitxer.setRuta(ruta + "/documents/Empreses/" + cif + "/Concurs/" + fichers[x].getName());
				 arxiu.add(fitxer);
			 }
		 }
		 return arxiu;
	 } 
	 
	 public static void guardarFitxer(Connection conn, int idUsuari, List<Fitxer> fitxers, String cif, String cifAdministrador) {		
			if (!fitxers.isEmpty()) {
				String fileName = "";
				// Crear directoris si no existeixen
				
				String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
				File tmpFile = new File(ruta + "/documents/Empreses");
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}
				tmpFile = new File(ruta + "/documents/Empreses/" + cif);
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}
		        for(int i=0;i<fitxers.size();i++){		        	
		            Fitxer fitxer = (Fitxer) fitxers.get(i);
		            if ("classificacioROLECE".equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Classificacio");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Classificacio/ROLECE");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/Classificacio/ROLECE/";
					}
		            if ("classificacioJCCaib".equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Classificacio");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Classificacio/JCCaib");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/Classificacio/JCCaib/";
					}
		            if ("classificacioJCA".equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Classificacio");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Classificacio/JCA");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/Classificacio/JCA/";
					}
		            if ("fileEconomica".equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Sol Economica");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/Sol Economica/";
					}
					if ("FileTecnica".equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Sol Tecnica");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/Sol Tecnica/";
					}
					if (("fileEscritura").equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Escritura");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/Escritura/";
					}
					if (("fileREA").equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/REA");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/REA/";
					}
					if (("fileAdministrador").equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Administrador");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Administrador/" + cifAdministrador);
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/Administrador/" + cifAdministrador + "/";
					}
					if (("documentextincio").equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Extincio");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/Extincio/";
					}
					if (("documentsuccessio").equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Successio");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/Successio/";
					}
					if (("documentProhibicioContractar").equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Prohibicio");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/Prohibicio/";
					}
					if (("documentConcurs").equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/Concurs");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/Concurs/";
					}
					if (("fileIBAN").equals(fitxer.getNomCamp())) {
						tmpFile = new File(ruta + "/documents/Empreses/" + cif + "/IBAN");
						if (!tmpFile.exists()) {
							tmpFile.mkdir();
						}
						fileName = ruta + "/documents/Empreses/" + cif + "/IBAN/";
					}
		            if (fitxer.getFitxer().getName() != "") {	
		            	File archivo_server = new File(fileName + fitxer.getFitxer().getName());
		               	try {
		               		fitxer.getFitxer().write(archivo_server);
		               		Fitxers.guardarRegistreFitxer(conn, fitxer.getFitxer().getName(), fileName + fitxer.getFitxer().getName(), idUsuari);
		           		} catch (Exception e) {
		           			e.printStackTrace();
		           		}
		            } 
		        }
			}
		}
	 
	 public static double getTotalBaseAdjudicat(Connection conn, String cif, Date dataIni, Date dataFi) {
		 double total = 0;
		 String sql = "SELECT SUM(o.pbase) AS total"
				 	+ " FROM public.tbl_empresaoferta o"
				 	+ " 	LEFT JOIN public.tbl_empreses e ON o.cifempresa = e.cif"
				 	+ " 	LEFT JOIN public.tbl_informeactuacio i ON o.idinforme = i.idinf"
				 	+ "		LEFT JOIN public.tbl_propostesinforme p ON p.idinf = i.idinf"
				 	+ " 	LEFT JOIN public.tbl_expedient x ON x.expcontratacio = i.expcontratacio"
				 	+ " WHERE x.anulat = false  AND o.seleccionada = true AND p.seleccionada = true AND (x.dataadjudicacio IS NOT null OR x.dataformalitzaciocontracte IS NOT null) AND i.dataaprovacio >= '01/01/2020' AND o.datacre >= ? AND o.datacre <= ? AND ((o.pbase < 15000 AND p.tipusobra != 'obr') OR (o.pbase < 40000 AND p.tipusobra = 'obr')) AND cif = ?";
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
			 pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
			 pstm.setString(3, cif);
			 ResultSet rs = pstm.executeQuery();
			 if (rs.next()) {
				 total = rs.getDouble("total");
			 }
			 sql = "SELECT SUM(m.pbase) AS total"
					 + " FROM public.tbl_modificacioinforme m"
					 + "    LEFT JOIN public.tbl_informeactuacio i ON m.idinforme = i.idinf"	
					 + "	LEFT JOIN public.tbl_propostesinforme p ON p.idinf = i.idinf"
					 + " 	LEFT JOIN public.tbl_empresaoferta o ON  i.idinf = o.idinforme"
					 + " WHERE i.dataaprovacio >= '01/01/2020' AND o.seleccionada = true AND p.seleccionada = true AND m.datacre >= ? AND m.datacre <= ? AND ((o.pbase < 15000 AND p.tipusobra != 'obr') OR (o.pbase < 40000 AND p.tipusobra = 'obr')) AND m.cifempresa = ?"; 
			 pstm = conn.prepareStatement(sql);	
			 pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
			 pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
			 pstm.setString(3, cif);
			 rs = pstm.executeQuery();
			 if (rs.next()) {
				 total += rs.getDouble("total");
			 }		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		 
		 return total;
	 }
	 
}
