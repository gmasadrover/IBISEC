package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfContentByte;

import bean.Registre;
import utils.Fitxers;
import utils.Fitxers.Fitxer;

public class RegistreCore {
	
	private static Registre initRegistre(Connection conn, ResultSet rs, String tipus, boolean ambDocuments) {
		Registre registre = new Registre();
		try {
			registre.setId(rs.getString("id"));
			registre.setData(rs.getDate("data"));
			if ("S".equals(tipus)) {
				registre.setRemDes(rs.getString("destinatari"));
			}else{
				registre.setRemDes(rs.getString("remitent"));
			}		
			registre.setTipus(rs.getString("tipus"));
			registre.setContingut(rs.getString("contingut"));
			registre.setIdIncidencies(rs.getString("idincidencia"));
			registre.setIdActuacions(findActuacions(conn, rs.getString("idincidencia")));
			registre.setIdCentres(rs.getString("idcentre"));
			String centresList = rs.getString("idcentre");
			if (centresList != null && !centresList.isEmpty()) {
				String[] idCentresList = centresList.split("#");
				String nomCentres = "";
				for(int i=0; i<idCentresList.length; i++) { 	
					if (!idCentresList[i].isEmpty()) nomCentres += CentreCore.nomCentre(conn, idCentresList[i]) + "#";
				}		
				registre.setNomCentres(nomCentres);	
			}
			registre.setActiu(rs.getBoolean("actiu"));
			if (ambDocuments) {
				if ("S".equals(tipus)) {
					registre.setDocuments(getDocumentsSortida(conn, rs.getString("id")));
				} else {
					registre.setDocuments(getDocumentsEntrada(conn, rs.getString("id")));
				}				
				registre.setResguard(getResguardArxiu(conn, rs.getString("id"), tipus));
				registre.setConfirmacioRecepcio(getConfirmacioRecepcio(conn, rs.getString("id"), tipus));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return registre;
	}
	
	public static void nouRegistre(Connection conn, String tipus, Registre registre) {
		String sql = "INSERT INTO public.tbl_regentrada (id, data, tipus, remitent, contingut, idcentre, idincidencia, usucre, datacre, actiu, idinforme)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, localtimestamp, true, ?);";
		if (tipus == "S") {
			sql = "INSERT INTO public.tbl_regsortida (id, data, tipus, destinatari, contingut, idcentre, idincidencia, usucre, datacre, actiu, idinforme)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, localtimestamp, true, ?);";
		}	
		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, getNewCode(conn, tipus));		
			pstm.setDate(2, new java.sql.Date(registre.getData().getTime()));
			pstm.setString(3, registre.getTipus());
			pstm.setString(4, registre.getRemDes());
			pstm.setString(5, registre.getContingut());
			pstm.setString(6,  registre.getIdCentres());
			pstm.setString(7, registre.getIdIncidencies());
			pstm.setInt(8, registre.getIdUsuari());			
			pstm.setString(9, registre.getIdInforme());		
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		
	}
	
	public static void modificarRegistre(Connection conn, Registre registre, String tipus) {
		String sql = "UPDATE public.tbl_regentrada"
				+ " SET  data = ?, tipus = ?, remitent = ?, contingut = ?, idcentre = ?, idincidencia = ?, idinforme = ?"
				+ " WHERE id=?";
		if ("S".equals(tipus)) {
			sql = "UPDATE public.tbl_regsortida"
					+ " SET data = ?, tipus = ?, destinatari = ?, contingut = ?, idcentre = ?, idincidencia = ?, idinforme = ?"
					+ " WHERE id=?";
		}		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setDate(1, new java.sql.Date(registre.getData().getTime()));
			pstm.setString(2, registre.getTipus());
			pstm.setString(3, registre.getRemDes());
			pstm.setString(4, registre.getContingut());
			pstm.setString(5,  registre.getIdCentres());
			pstm.setString(6, registre.getIdIncidencies());
			pstm.setString(7, registre.getIdInforme());
			pstm.setString(8, registre.getId());				
			pstm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static void anularRegistre(Connection conn, String idRegistre, String tipus) {
		String sql = "UPDATE public.tbl_regentrada"
					+ " SET actiu = false"
					+ " WHERE id=?";
		if ("S".equals(tipus)) {
			sql = "UPDATE public.tbl_regsortida"
					+ " SET actiu = false"
					+ " WHERE id=?";
		}		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idRegistre);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Registre findRegistre(Connection conn, String tipus, String idRegistre) {
		Registre registre = new Registre();
		String sql = "SELECT id, data, tipus, remitent, contingut, idcentre, idincidencia, usucre, datacre, actiu"
					+ " FROM public.tbl_regentrada"
					+ " WHERE id=?";
		if ("S".equals(tipus)) {
			sql = "SELECT id, data, tipus, destinatari, contingut, idcentre, idincidencia, usucre, datacre, actiu"
					+ " FROM public.tbl_regsortida"
					+ " WHERE id=?";
		}
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idRegistre);	
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {		
				registre = initRegistre(conn, rs, tipus, true);			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return registre;
	}
	
	public static List<Registre> entrades(Connection conn) {
		String sql = "SELECT id, data, tipus, remitent, contingut, idcentre, idincidencia, actiu"
					+ " FROM public.tbl_regentrada"
					+ " WHERE actiu = true"
					+ " ORDER BY data DESC, id DESC LIMIT 500";
	 
		PreparedStatement pstm;
		List<Registre> list = new ArrayList<Registre>();
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
		
			while (rs.next()) {			
				list.add(initRegistre(conn, rs, "E", false));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return list;
	}
	
	public static List<Registre> searchEntrades(Connection conn, String idCentre, Date dataIni, Date dataFi) {
		String sql = "SELECT id, data, tipus, remitent, contingut, idcentre, idincidencia, actiu"
					+ " FROM public.tbl_regentrada";
		ResultSet rs = null;
		if (dataIni != null && dataFi != null) {
			sql += " WHERE data >= ? and data <= ?";
			if (idCentre != "") {
				sql += "and idcentre like ?";
			}
			sql += " ORDER BY data DESC, id DESC";
			PreparedStatement pstm;
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
				pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
				if (idCentre != "") pstm.setString(3, "%" + idCentre + "%");
				rs = pstm.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			if (idCentre != "") {
				sql += " WHERE idcentre like ?";
			}
			sql += " ORDER BY data DESC, id DESC";
			PreparedStatement pstm;
			try {
				pstm = conn.prepareStatement(sql);
				if (idCentre != "") pstm.setString(1, "%" + idCentre + "%");
				rs = pstm.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		List<Registre> list = new ArrayList<Registre>();
		
		try {
			while (rs.next()) {
				list.add(initRegistre(conn, rs, "E", false));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Registre> searchEntradesIncidencia(Connection conn, String referencia, String centre) {
		String sql = "SELECT id, data, tipus, remitent, contingut, idcentre, idincidencia, actiu"
					+ " FROM public.tbl_regentrada"
					+ " WHERE idincidencia LIKE ?";
		
		if (centre != null)	sql += " AND idcentre LIKE ?";
		
		sql+= " ORDER BY data DESC, id DESC";
		
		PreparedStatement pstm;
		List<Registre> list = new ArrayList<Registre>();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%" + referencia + "%");
			if (centre != null) {
				pstm.setString(2, "%" + centre + "%");
			}
			ResultSet rs = pstm.executeQuery();
				
			while (rs.next()) {			
				list.add(initRegistre(conn, rs, "E", false));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static void actualitzarIdIncidencia(Connection conn, String tipus, String idRegistre, String idActuacio) {
		String sql = "UPDATE public.tbl_regentrada"
					+ " SET idincidencia=?"
					+ " WHERE id=?";
		if (tipus == "S") {
			sql = "UPDATE public.tbl_regsortida"
					+ " SET idincidencia=?"
					+ " WHERE id=?";
		}		
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idActuacio);
			pstm.setString(2, idRegistre);				
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static List<Registre> sortides(Connection conn) {
		String sql = "SELECT id, data, tipus, destinatari, contingut, idincidencia, idcentre, actiu"
					+ " FROM public.tbl_regsortida"
					+ " WHERE actiu = true"
					+ " ORDER BY data DESC, id DESC LIMIT 500";
	 
		PreparedStatement pstm;
		List<Registre> list = new ArrayList<Registre>();
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
		
			while (rs.next()) {			
				list.add(initRegistre(conn, rs, "S", false));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		
		return list;
	}
	
	public static List<Registre> searchSortides(Connection conn, String idCentre, Date dataIni, Date dataFi) {
		String sql = "SELECT id, data, tipus, destinatari, contingut, idcentre, idincidencia, actiu"
					+ " FROM public.tbl_regsortida";
					ResultSet rs = null;
		if (dataIni != null && dataFi != null) {
			sql += " WHERE data >= ? and data <= ?";
			if (idCentre != "") {
				sql += "and idcentre like ?";
			}
			sql += " ORDER BY data DESC, id DESC";
			PreparedStatement pstm;
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
				pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
				if (idCentre != "") pstm.setString(3, "%" + idCentre + "%");
				rs = pstm.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			if (idCentre != "") {
				sql += " WHERE idcentre like ?";
			}
			sql += " ORDER BY data DESC, id DESC";
			PreparedStatement pstm;
			try {
				pstm = conn.prepareStatement(sql);
				if (idCentre != "") pstm.setString(1, "%" + idCentre + "%");
				rs = pstm.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		List<Registre> list = new ArrayList<Registre>();
		try {
			while (rs.next()) {			
				list.add(initRegistre(conn, rs, "S", false));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Registre> searchSortidesIncidencia(Connection conn, String referencia, String centre) {
		String sql = "SELECT id, data, tipus, destinatari, contingut, idcentre, idincidencia, actiu"
					+ " FROM public.tbl_regsortida"
					+ " WHERE idincidencia LIKE ?";
		
		if (centre != null)	sql += " AND idcentre LIKE ?";
		
		sql+= " ORDER BY data DESC, id DESC";
		
		PreparedStatement pstm;
		List<Registre> list = new ArrayList<Registre>();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%" + referencia + "%");
			if (centre != null) pstm.setString(2, "%" + centre + "%");
			ResultSet rs = pstm.executeQuery();
			
			while (rs.next()) {			
				list.add(initRegistre(conn, rs, "S", false));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static String getNewCode(Connection conn, String tipus) {
		String code = "1";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		
		String sql = "SELECT id"
					+ " FROM public.tbl_regentrada"
					+ " WHERE id like '%" + yearInString + "-RE%'"
					+ " ORDER BY id DESC LIMIT 1;";		
		if ("S".equals(tipus)) {
			sql = "SELECT id"
					+ " FROM public.tbl_regsortida"
					+ " WHERE id like '%" + yearInString + "-RS%'"
					+ " ORDER BY id DESC LIMIT 1;";	
		}
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();		
			String prefix = "RE";
			if ("S".equals(tipus)) prefix = "RS";
			if (rs.next()) { //Codis nous
				String actualCode = rs.getString("id");			 
				int num = Integer.valueOf(actualCode.split("-")[2]);
				String numFormatted = String.format("%04d", num + 1);
				code = yearInString + "-" + prefix + "-" + numFormatted;			
			} else {//Codis antics
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
	public static String findActuacions(Connection conn, String idIncidencies) {
		String idActuacions = "";
		if (idIncidencies != null && !idIncidencies.isEmpty()) {
			for (String incidencia: idIncidencies.split("#")) {
				String sql = "SELECT id"
						+ " FROM public.tbl_actuacio"
						+ " WHERE idincidencia = ?";	
				PreparedStatement pstm;
				try {
					pstm = conn.prepareStatement(sql);
					pstm.setString(1, incidencia);
					ResultSet rs = pstm.executeQuery();
					while (rs.next()) {
						idActuacions += rs.getString("id") + "#";
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return idActuacions;
	}
	
	public static List<Fitxer> getArxiusAdjunts(Connection conn, String idincidencia, String idInforme, String tipus) {
		List<Fitxer> documentsAdjunts = new ArrayList<Fitxer>();
		String sql = "SELECT id"
				+ " FROM public.tbl_regentrada"
				+ " WHERE tipus = ? AND idinforme LIKE '%" + idInforme + "%'";	
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, tipus);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				documentsAdjunts.addAll(Fitxers.ObtenirFitxers(conn, idincidencia, "", "RegistreE", rs.getString("id"), ""));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return documentsAdjunts;
	}
	
	public static List<Fitxer> getArxiusAdjuntsPerTipus(Connection conn, String idincidencia, String idInforme, String tipus) {
		List<Fitxer> documentsAdjunts = new ArrayList<Fitxer>();
		String sql = "SELECT id"
				+ " FROM public.tbl_regentrada"
				+ " WHERE tipus = ? AND idinforme LIKE '%" + idInforme + "%'";	
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, tipus);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				documentsAdjunts.addAll(Fitxers.ObtenirFitxers(conn, idincidencia, "", "RegistreE", rs.getString("id"), ""));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return documentsAdjunts;
	}
	
	public static void modificarNotificacio(Connection conn, Registre registre) {
		String sql = "UPDATE public.tbl_tasques"
				+ " SET idactuacio=?, idincidencia=?, idinforme=?"
				+ " WHERE registre=?";		
	PreparedStatement pstm;
	try {
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, registre.getPrimeraActuacio());
		pstm.setString(2, registre.getPrimeraIncidencia());	
		pstm.setString(3, registre.getPrimerInforme());
		pstm.setString(4, registre.getId());
		pstm.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	 
	
	}
	
	public static List<Registre> getEntrades(Connection conn, String idInforme) {
		List<Registre> registresList = new ArrayList<Registre>();
		String sql = "SELECT id, data, tipus, remitent, contingut, idcentre, idincidencia, usucre, datacre, actiu"
				+ " FROM public.tbl_regentrada"
				+ " WHERE idinforme LIKE '%" + idInforme + "%'";	
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				registresList.add(initRegistre(conn, rs, "E", false));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return registresList;
	}
	
	public static List<Registre> getSortides(Connection conn, String idInforme) {
		List<Registre> registresList = new ArrayList<Registre>();
		String sql = "SELECT id, data, tipus, destinatari, contingut, idcentre, idincidencia, usucre, datacre, actiu"
				+ " FROM public.tbl_regsortida"
				+ " WHERE idinforme LIKE '%" + idInforme + "%'";	
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				registresList.add(initRegistre(conn, rs, "S", false));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return registresList;
	}
	
	private static List<Fitxer> getDocumentsEntrada(Connection conn, String idReg) {
		List<Fitxer> documents = new ArrayList<Fitxer>();
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		documents =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/Registre Entrada/" + idReg, false);
		return documents;
	}
	
	private static List<Fitxer> getDocumentsSortida(Connection conn, String idReg) {
		List<Fitxer> documents = new ArrayList<Fitxer>();
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		documents =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/Registre Sortida/" + idReg, false);
		return documents;
	}
	
	private static Fitxer getResguardArxiu(Connection conn, String idReg, String tipus) {
		Fitxer resguard = new Fitxer();
		
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		if (tipus.equals("E")) {
			resguard =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/Registre Entrada/" + idReg + "/Resguard/", false);	
		} else {
			resguard =  utils.Fitxers.ObtenirFitxer(ruta + "/documents/Registre Sortida/" + idReg + "/Resguard/", false);	
		}
			
		return resguard;
	}
	
	public static void guardarConfirmacioRecepcio(Connection conn, Registre registre, List<Fitxer> resguard) {
		
    	String ruta = "";
		ruta = ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		File tmpFile =  null;
		tmpFile =  new File(ruta + "/documents/Registre Sortida/" + registre.getId());		
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}
		tmpFile = new File(ruta + "/documents/Registre Sortida/" + registre.getId() + "/Rebut");
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}  
		for(int i=0;i<resguard.size();i++){		        	
            Fitxer fitxer = (Fitxer) resguard.get(i);	            
            if (fitxer.getFitxer() != null && fitxer.getFitxer().getName() != "") {			            	
            	File archivo_server = new File(tmpFile + "/" + fitxer.getFitxer().getName());
            	try {
               		fitxer.getFitxer().write(archivo_server);               		
           		} catch (Exception e) {
           			e.printStackTrace();
           		}
            } 
        }
	}
	
	public static List<Fitxer> getConfirmacioRecepcio(Connection conn, String idReg, String tipus) {
		List<Fitxer> resguard = new ArrayList<Fitxer>();
		
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		if (tipus.equals("S")) {
			resguard =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/Registre Sortida/" + idReg + "/Rebut/", false);		
		} else {
			resguard =  utils.Fitxers.ObtenirFitxers(ruta + "/documents/Registre Entrada/" + idReg + "/Rebut/", false);		
		}
		
		return resguard;
	}
	
	public static void crearResguard(Connection conn, Registre registre, String tipus) {
		String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
		File tmpFile;
		try {	
			if (tipus.equals("S")) {
				tmpFile = new File(ruta + "/documents/Registre Sortida/" + registre.getId() + "/Resguard");
			} else {
				tmpFile = new File(ruta + "/documents/Registre Entrada/" + registre.getId() + "/Resguard");
			}			
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}   				
			
			PdfReader reader = new PdfReader(ruta + "/base/Registre IBISEC.pdf"); // input PDF
			PdfStamper stamper = new PdfStamper(reader,
			new FileOutputStream(tmpFile + "/registre-"+ registre.getId() +".pdf"));			
	        
	        Rectangle pagesize = reader.getPageSize(1);
					 				
			PdfPTable table = new PdfPTable(2);
 	        PdfPCell cell = new PdfPCell();   
 	        Paragraph text = new Paragraph();    	
 	        
 	        String FONT = ruta + "/fonts/NotoSans-Regular.ttf";
 	        FontFactory.register(FONT,"Noto-Sans");
 	        Font f = FontFactory.getFont("Noto-Sans", "ISO-8859-1", true);
 	        f.setSize(11); 
 	        String FONTBOLD = ruta + "/fonts/NotoSans-Bold.ttf";
 	        FontFactory.register(FONTBOLD,"Noto-Sans-Bold");
 	        Font fb = FontFactory.getFont("Noto-Sans-Bold", "ISO-8859-1", true);
 	        fb.setSize(11); 
 	        String FONTBOLDTITLE = ruta + "/fonts/NotoSans-Bold.ttf";
 	        FontFactory.register(FONTBOLDTITLE,"Noto-Sans-Bold");
 	        Font fbt = FontFactory.getFont("Noto-Sans-Bold", "ISO-8859-1", true);
 	        fbt.setSize(14); 
 	        
 	        //Dades bàsiques
 	        
 	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 	        cell.setPadding(5);
 	        cell.setBorder(0);
 	        String tipusRegistre = "entrada";
 	        if (tipus.equals("S")) tipusRegistre = "sortida";
 	        text = new Paragraph("Registre " + tipusRegistre + " IBISEC", fbt);    	            
	        	cell.addElement(text);    	            
 	        table.addCell(cell);
 	        
 	        cell = new PdfPCell();   
 	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
 	        cell.setPadding(5);
 	        cell.setBorder(0);   	            
 	        table.addCell(cell);    
 	        
 	        table.setHeaderRows(1);     	        	                    
 	        
            cell = new PdfPCell();
        	cell.setPadding(5);
            text = new Paragraph("Referència", fb);    	            
        	cell.addElement(text); 
        	cell.setBorder(0);   	 
            table.addCell(cell);
            
            cell = new PdfPCell();
            cell.setPadding(5);
            text = new Paragraph(registre.getId(), f);    	            
        	cell.addElement(text);  
        	cell.setBorder(0);   	 
            table.addCell(cell);
 	           
	        cell = new PdfPCell();
        	cell.setPadding(5);
            text = new Paragraph("Data Registre", fb);    	            
        	cell.addElement(text); 
        	cell.setBorder(0);   	 
            table.addCell(cell);
 	            
            cell = new PdfPCell();
            cell.setPadding(5);
            text = new Paragraph(registre.getDataString(), f);    	            
        	cell.addElement(text);  
        	cell.setBorder(0);   	 
            table.addCell(cell);
	            
	        cell = new PdfPCell();
        	cell.setPadding(5);
        	String remitentDestinatari = "Remitent";
        	if (tipus.equals("S")) remitentDestinatari = "Destinatari";
            text = new Paragraph(remitentDestinatari, fb);    	            
        	cell.addElement(text);    	
        	cell.setBorder(0);   	 
            table.addCell(cell);
	            
            cell = new PdfPCell();
            cell.setPadding(5);
            text = new Paragraph(registre.getRemDes(), f);    	            
        	cell.addElement(text);   
        	cell.setBorder(0);   	 
            table.addCell(cell);
 	       	
       		cell = new PdfPCell();
        	cell.setPadding(5);
            text = new Paragraph("Contingut", fb);    	            
        	cell.addElement(text);    	
        	cell.setBorder(0);   	 
            table.addCell(cell);
   	            
            cell = new PdfPCell();
            cell.setPadding(5);
            text = new Paragraph(registre.getContingut(), f);    	            
        	cell.addElement(text);   
        	cell.setBorder(0);   	 
            table.addCell(cell);  	            
            		       	
            cell = new PdfPCell();
        	cell.setPadding(5);
            text = new Paragraph("Documents adjunts", fb);    	            
        	cell.addElement(text);    	
        	cell.setBorder(0);   	 
            table.addCell(cell);   	            
	          
            String documents = "";
            List<Fitxer> arxius = registre.getDocuments();
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
	         
            for (Fitxer fitxer : arxius) {  
            	documents += fitxer.getNom() + "\n";
            	File file = new File(fitxer.getRuta()); 
            	documents += "MD5: " + getFileChecksum(md5Digest, file) + "\n\n" ;
            }
	            
            cell = new PdfPCell();
            cell.setPadding(5);
            text = new Paragraph(documents, f);    	            
        	cell.addElement(text);   
        	cell.setBorder(0);   	 
            table.addCell(cell);  
	            
            int pagecount = 1;
 	        ColumnText column = new ColumnText(stamper.getOverContent(1));
 	        Rectangle rectPage1 = new Rectangle(36, 36, 559, 650);
 	        column.setSimpleColumn(rectPage1);
 	        column.addElement(table);
 	       
 	        Rectangle rectPage2 = new Rectangle(36, 36, 559, 806);
 	        int status = column.go();
 	        while (ColumnText.hasMoreText(status)) {
 	           status = triggerNewPage(stamper, pagesize, column, rectPage2, ++pagecount);
 	       	}        
 	        
 	        //Representació
				
	        stamper.close();
	        reader.close();
	        
	        
		} catch (DocumentException | NoSuchAlgorithmException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // output PDF
	}
	
	private static int triggerNewPage(PdfStamper stamper,
		    Rectangle pagesize, ColumnText column, Rectangle rect, int pagecount)
		        {
		    stamper.insertPage(pagecount, pagesize);
		    PdfContentByte canvas = stamper.getOverContent(pagecount);
		    column.setCanvas(canvas);
		    column.setSimpleColumn(rect);
		    try {
				return column.go();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return 0;
		}
	
	private static String getFileChecksum(MessageDigest digest, File file) 
	{
	    //Get file input stream for reading the file content
	    FileInputStream fis;
	    StringBuilder sb = new StringBuilder();
		try {
			fis = new FileInputStream(file);
			 //Create byte array to read data in chunks
		    byte[] byteArray = new byte[1024];
		    int bytesCount = 0;
		      
		    //Read file data and update in message digest
		    while ((bytesCount = fis.read(byteArray)) != -1) {
		        digest.update(byteArray, 0, bytesCount);
		    };
		     
		    //close the stream; We don't need it now.
		    fis.close();
		     
		    //Get the hash's bytes
		    byte[] bytes = digest.digest();
		     
		    //This bytes[] has bytes in decimal format;
		    //Convert it to hexadecimal format
		   
		    for(int i=0; i< bytes.length ;i++)
		    {
		        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	   
	     
	    //return complete hash
	   return sb.toString();
	}
}
