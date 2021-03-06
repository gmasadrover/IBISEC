package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.log.SysoLogger;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.security.CertificateInfo;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.itextpdf.text.pdf.security.SignaturePermissions;

import bean.Actuacio;
import bean.Actuacio.ArxiusAdjunts;
import core.ConfiguracioCore;
import bean.User;
import utils.Fitxers.Fitxer.infoFirma;
public class Fitxers {
	
	
	public static class Fitxer{
		
		public class infoFirma{
			private String nomFirmant;
			private String dataFirma;
			public infoFirma() {}
			public String getNomFirmant() {
				return nomFirmant;
			}
			public void setNomFirmant(String nomFirmant) {
				this.nomFirmant = nomFirmant;
			}
			public String getDataFirma() {
				return dataFirma;
			}
			public void setDataFirma(String dataFirma) {
				this.dataFirma = dataFirma;
			}
		}
		
		private String nom;
		private String nomCamp;
		private String ruta;
		private String encodedRuta;
		private String seccio;
		private boolean signat;
		private List<infoFirma> firmesList;
		private FileItem fitxer;
		private FileTime data;
		private String dataString;
		private int idRegistre;
		private User usuari;
		private Date dataPujada;
		private Date dataEliminat;
		private User usuariEliminat;
		
		
		public Fitxer(){
			this.signat = false;
			this.firmesList = new ArrayList<infoFirma>();
		}
		
		public String getNom() {
			return nom;
		}
		public void setNom(String nom) {
			this.nom = nom;
		}
		public String getRuta() {
			return ruta;
		}
		public String getEncodedRuta() {
			this.encodedRuta = "";
			if (this.ruta != null && !this.ruta.isEmpty()) {
				try {
					this.encodedRuta = java.net.URLEncoder.encode(this.ruta, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return this.encodedRuta;
		}
		public void setRuta(String ruta) {
			this.ruta = ruta;
			this.encodedRuta = getEncodedRuta();
		}

		public String getSeccio() {
			return seccio;
		}

		public void setSeccio(String seccio) {
			this.seccio = seccio;
		}

		public FileItem getFitxer() {
			return fitxer;
		}

		public void setFitxer(FileItem fitxer) {
			this.fitxer = fitxer;
		}

		public boolean isSignat() {
			return signat;
		}

		public void setSignat(boolean signat) {
			this.signat = signat;
		}

		public List<infoFirma> getFirmesList() {
			return firmesList;
		}

		public void setFirmesList(List<infoFirma> firmesList) {
			this.firmesList = firmesList;
		}	
		
		public void addFirmesList(infoFirma firma) {
			this.firmesList.add(firma);
		}

		public FileTime getData() {
			return data;
		}
		
		public String getDataString() {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
			this.dataString = "";
			if (this.data != null) this.dataString = df.format(this.data.toMillis());
			return this.dataString;
		}

		public void setData(FileTime data) {
			this.data = data;
			this.dataString = getDataString();
		}

		public String getNomCamp() {
			return nomCamp;
		}

		public void setNomCamp(String nomCamp) {
			this.nomCamp = nomCamp;
		}

		public int getIdRegistre() {
			return idRegistre;
		}

		public void setIdRegistre(int idRegistre) {
			this.idRegistre = idRegistre;
		}

		public User getUsuari() {
			return usuari;
		}

		public void setUsuari(User usuari) {
			this.usuari = usuari;
		}

		public Date getDataPujada() {
			return dataPujada;
		}

		public void setDataPujada(Date dataPujada) {
			this.dataPujada = dataPujada;
		}

		public Date getDataEliminat() {
			return dataEliminat;
		}

		public void setDataEliminat(Date dataEliminat) {
			this.dataEliminat = dataEliminat;
		}

		public User getUsuariEliminat() {
			return usuariEliminat;
		}

		public void setUsuariEliminat(User usuariEliminat) {
			this.usuariEliminat = usuariEliminat;
		}	
		
	}	
	
	public static List<Fitxer> ObtenirManuals(Connection conn) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();	 
		
		String ruta = ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio() + "/Manuals/";
		
		File dir = new File(ruta);
		File[] ficheros = dir.listFiles();
		LoggerFactory.getInstance().setLogger(new SysoLogger());
		BouncyCastleProvider provider = new BouncyCastleProvider();
		Security.addProvider(provider);
		if (ficheros == null) {			
		} else {
			for (int x=0;x<ficheros.length;x++) {
					Fitxer fitxer = new Fitxer();
	            	fitxer.setNom(ficheros[x].getName());
					fitxer.setRuta(ruta + "/" + ficheros[x].getName());
					fitxer.setSeccio("Manuals");
					arxius.add(fitxer);			
			}			
		}
		return arxius;	
	}
	
	public static List<Fitxer> ObtenirFitxers(Connection conn, String idIncidencia, String idActuacio, String tipus, String idTipus, String idSubTipus) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();
		
		String rutaBase =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio() + "/documents/" + idIncidencia;
		if (idActuacio != null && !idActuacio.isEmpty()) rutaBase += "/Actuacio/" + idActuacio;				
		rutaBase += "/" + tipus + "/" + idTipus;
		if (!idSubTipus.isEmpty()) rutaBase += "/Comentari/" + idSubTipus;		
		arxius = ObtenirTotsFitxers(rutaBase, tipus);		
		return arxius;		
	}
	
	public static List<Fitxer> ObtenirFitxersTasca(Connection conn, int idTasca) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();
		
		String rutaBase =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio() + "/documents/Tasca/" + idTasca;
		arxius = ObtenirTotsFitxers(rutaBase, "");				
		return arxius;		
	}
	
	public static ArxiusAdjunts ObtenirTotsFitxers(Connection conn, String idIncidencia) {
		ArxiusAdjunts arxius = new Actuacio().new ArxiusAdjunts();
				
	    List<Fitxers.Fitxer> arxiusAltres = new ArrayList<Fitxers.Fitxer>();
	    String rutaBase =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio() + "/documents/" + idIncidencia;		
		arxiusAltres = ObtenirTotsFitxers(rutaBase, "Altres");	
		arxius.setArxiusAltres(arxiusAltres);
		
		return arxius;	
	}
	
	public static Fitxer ObtenirFitxer(String ruta, boolean readFirma) {
		Fitxer arxiu = new Fitxer();
		File dir = new File(ruta);
		File[] ficheros = dir.listFiles();
		LoggerFactory.getInstance().setLogger(new SysoLogger());
		BouncyCastleProvider provider = new BouncyCastleProvider();
		Security.addProvider(provider);
		if (ficheros == null || ficheros.length == 0) {			
		} else {
			try {
				for (int x=0;x<ficheros.length;x++) {	
					if (!ficheros[x].isDirectory()) {
						Fitxer fitxer = new Fitxer();	
						if (readFirma && isPDF(ficheros[x])) {
							PdfReader reader;		
							reader = new PdfReader(ruta + "/" + ficheros[x].getName());		
			            	AcroFields acroFields = reader.getAcroFields();
			            	List<String> signatureNames = acroFields.getSignatureNames();			            	
			            	if (signatureNames.size()>0) fitxer.setSignat(true);
						}
		            	fitxer.setNom(ficheros[x].getName());
						fitxer.setRuta(ruta + "/" + ficheros[x].getName());
						fitxer.setSeccio("");
						arxiu = fitxer;				
					}
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return arxiu;
	}
	
	public static List<Fitxer> ObtenirFitxers(String ruta, boolean readFirma) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();		
		File dir = new File(ruta);
		File[] ficheros = dir.listFiles();
		LoggerFactory.getInstance().setLogger(new SysoLogger());
		BouncyCastleProvider provider = new BouncyCastleProvider();
		Security.addProvider(provider);
		if (ficheros == null) {			
		
		} else {
			try {
				for (int x=0;x<ficheros.length;x++) {					
					if (!ficheros[x].isDirectory()) {
						Fitxer fitxer = new Fitxer();	
						if (readFirma && isPDF(ficheros[x])) {
							PdfReader reader;						
							reader = new PdfReader(ruta + "/" + ficheros[x].getName());						
			            	AcroFields acroFields = reader.getAcroFields();
			            	List<String> signatureNames = acroFields.getSignatureNames();
			            	if (signatureNames.size()>0) fitxer.setSignat(true);
						}
		            	fitxer.setNom(ficheros[x].getName());
						fitxer.setRuta(ruta + "/" + ficheros[x].getName());
						fitxer.setData(Files.getLastModifiedTime(ficheros[x].toPath()));
						arxius.add(fitxer);
					}				
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return arxius;	
	}
	
	public static List<Fitxer> ObtenirTotsFitxers(String ruta, String seccio) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();		
		File dir = new File(ruta);
		File[] ficheros = dir.listFiles();
		LoggerFactory.getInstance().setLogger(new SysoLogger());
		BouncyCastleProvider provider = new BouncyCastleProvider();
		Security.addProvider(provider);
		if (ficheros == null) {			
		} else {
			try {
				for (int x=0;x<ficheros.length;x++) {					
					if (ficheros[x].isDirectory()) {
						if (! ficheros[x].getName().equals("informe")  && ! ficheros[x].getName().equals("RegistreE")  && ! ficheros[x].getName().equals("Resguard") && ! ficheros[x].getName().equals("Rebut")) arxius.addAll(ObtenirTotsFitxers(ruta + "/" + ficheros[x].getName(), seccio + " - " + ficheros[x].getName()));
					} else {
						Fitxer fitxer = new Fitxer();	
						if (!ficheros[x].getName().contains(".zip") && isPDF(ficheros[x])) {
							PdfReader reader;						
							reader = new PdfReader(ruta + "/" + ficheros[x].getName());						
			            	AcroFields acroFields = reader.getAcroFields();
			            	List<String> signatureNames = acroFields.getSignatureNames();
			            	if (signatureNames.size() > 0) fitxer.setSignat(true);
						}
		            	fitxer.setNom(ficheros[x].getName());
						fitxer.setRuta(ruta + "/" + ficheros[x].getName());
						fitxer.setSeccio(seccio);
						fitxer.setData(Files.getLastModifiedTime(ficheros[x].toPath()));
						arxius.add(fitxer);
						
					}				
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return arxius;	
	}
	
	public static List<Fitxer> ObtenirTotsFitxersBD(String rutaBase) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();	
		String ruta = rutaBase;
		File dir = new File(ruta);
		File[] ficheros = dir.listFiles();
		LoggerFactory.getInstance().setLogger(new SysoLogger());
		BouncyCastleProvider provider = new BouncyCastleProvider();
		Security.addProvider(provider);
		if (ficheros == null) {			
		} else {
			for (int x=0;x<ficheros.length;x++) {					
				if (ficheros[x].isDirectory()) {
					if (!ficheros[x].getName().equals("Llicencies") && !ficheros[x].getName().equals("Empreses") && !ficheros[x].getName().equals("RegistreE") && !ficheros[x].getName().equals("Factures") && !ficheros[x].getName().equals("Certificacions"))
					arxius.addAll(ObtenirTotsFitxersBD(ruta + "/" + ficheros[x].getName()));
				} else {
					Fitxer fitxer = new Fitxer();
			    	fitxer.setNom(ficheros[x].getName());
					fitxer.setRuta(ruta + "/" + ficheros[x].getName());						
					//fitxer.setData(Files.getLastModifiedTime(ficheros[x].toPath()));
					arxius.add(fitxer);
				}				
			}
		}
		return arxius;	
	}
	
	public static boolean isPDF(File file) {	    
	    Scanner inputFile = null;
		try {
			inputFile = new Scanner(new FileReader(file));
			 while (inputFile.hasNextLine()) {
			        final String checkline = inputFile.nextLine();
			        if(checkline.contains("%PDF-")) { 
			            // a match!
			        	inputFile.close();
			            return true;
			        }  
			    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inputFile.close();
	    return false;
	}
	
	public static List<Fitxers.Fitxer.infoFirma> getSignaturesDocument(String ruta) {
		 List<infoFirma> firmesList = new ArrayList<infoFirma>();
		 PdfReader reader;						
		 try {
			reader = new PdfReader(ruta);
			AcroFields acroFields = reader.getAcroFields();
			 List<String> signatureNames = acroFields.getSignatureNames();	
			 Fitxer.infoFirma info = null;
			 for (String name: signatureNames) {     		
					PdfPKCS7 pkcs7 = verifySignature(acroFields, name);
					if (pkcs7 != null) {
						info = new Fitxer().new infoFirma();
						X509Certificate cert = (X509Certificate) pkcs7.getSigningCertificate();	
						info.setNomFirmant(CertificateInfo.getSubjectFields(cert).getField("CN"));		
						SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy HH:mm");		
						info.setDataFirma(date_format.format(pkcs7.getSignDate().getTime()));	
						firmesList.add(info);
					}
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						
		 
		 return firmesList;
	}
	
	public static PdfPKCS7 verifySignature(AcroFields fields, String name) {
		try {
			PdfPKCS7 pkcs7 = fields.verifySignature(name);   
			 return pkcs7;
		} catch (java.lang.IllegalArgumentException e) {
		}
		return null;
	}
	
	
	public static SignaturePermissions inspectSignature(AcroFields fields, String name, SignaturePermissions perms, Fitxer fitxer) {
		PdfPKCS7 pkcs7 = verifySignature(fields, name);
		
		if (pkcs7 != null) {
			X509Certificate cert = (X509Certificate) pkcs7.getSigningCertificate();		
			Fitxer.infoFirma info = fitxer.new infoFirma();
			info.setNomFirmant(CertificateInfo.getSubjectFields(cert).getField("CN"));		
			
			SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy HH:mm");		
			info.setDataFirma(date_format.format(pkcs7.getSignDate().getTime()));
			
			
			PdfDictionary sigDict = fields.getSignatureDictionary(name);
			PdfString contact = sigDict.getAsString(PdfName.CONTACTINFO);
			if (contact != null)
			perms = new SignaturePermissions(sigDict, perms);
		
			fitxer.addFirmesList(info);
		}		
        return perms;
	}
	
	private static int getIdRegistreDocument(Connection conn) {
		int idRegistre = 0;
		String sql = "SELECT idregistre " +
					" FROM public.tbl_registredocuments" +
					" ORDER BY idregistre DESC" +
					" LIMIT 1";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) idRegistre = rs.getInt("idregistre") + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idRegistre;
	}
	
	public static void guardarRegistreFitxer(Connection conn, String nomdocument, String ruta, int idUsuari) {
		 String sqlInsert = "INSERT INTO public.tbl_registredocuments(idregistre, usuari, data, nomdocument, dataeliminacio, usuarieliminacio, rutadocument)"
		 			+ " VALUES (?, ?, localtimestamp, ?, null, null, ?);";	 
		 PreparedStatement pstmInsert = null; 			 
		 try {
			pstmInsert = conn.prepareStatement(sqlInsert);
			 pstmInsert.setInt(1, getIdRegistreDocument(conn));
			 pstmInsert.setInt(2, idUsuari);
			 pstmInsert.setString(3, nomdocument);		
			 pstmInsert.setString(4, ruta);		
			 pstmInsert.executeUpdate();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}
	
	public static void marcarDocumentEliminat(Connection conn, int idUsuari, String ruta) {
		String sqlUpdate = "UPDATE public.tbl_registredocuments" + 
							" SET dataeliminacio=localtimestamp, usuarieliminacio=?" +
							" WHERE rutadocument=?;";
		 PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sqlUpdate);
			 pstm.setInt(1,idUsuari);
			 pstm.setString(2, ruta);		
			 pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
	}
	
	public static void guardarFitxer(Connection conn, List<Fitxer> fitxers, String idIncidencia, String idActuacio, String tipus, String idTipus, String idSubTipus, String idInforme, String docInforme, int idUsuari) {		
		if (fitxers != null && !fitxers.isEmpty()) {
			String fileName = "";
			
			String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
			// Crear directoris si no existeixen
			File tmpFile = new File(ruta + "/documents/"  + idIncidencia);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			fileName = ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/";
			// Miram si te tipus
			if (!tipus.isEmpty()) {
				tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/" +tipus);
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}
				tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" +idActuacio + "/" + tipus + "/" + idTipus);
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}		
				fileName = ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/" + tipus + "/" + idTipus + "/";
				// Miram si te subtipus
				if (!idSubTipus.isEmpty()) {
					tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/" + tipus + "/" + idTipus + "/Comentari");
					if (!tmpFile.exists()) {
						tmpFile.mkdir();
					}
					tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/" + tipus + "/" + idTipus + "/Comentari/" + idSubTipus);
					if (!tmpFile.exists()) {
						tmpFile.mkdir();
					}
					fileName +=  "Comentari/" + idSubTipus + "/";
				}
			}
			else if (!idInforme.isEmpty()) {
				if (idInforme.contains("-MOD-")) {
					tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe");
					if (!tmpFile.exists()) {
						tmpFile.mkdir();
					}
					tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" +idActuacio + "/informe/" + idSubTipus);
					if (!tmpFile.exists()) {
						tmpFile.mkdir();
					}
					tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" +idActuacio + "/informe/" + idSubTipus + "/Modificacions");
					if (!tmpFile.exists()) {
						tmpFile.mkdir();
					}
					tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" +idActuacio + "/informe/" + idSubTipus + "/Modificacions/" + idInforme);
					if (!tmpFile.exists()) {
						tmpFile.mkdir();
					}
					tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" +idActuacio + "/informe/" + idSubTipus + "/Modificacions/" + idInforme + "/" + docInforme);
					if (!tmpFile.exists()) {
						tmpFile.mkdir();
					}
					fileName = ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idSubTipus + "/Modificacions/" + idInforme + "/" + docInforme + "/";
				} else {
					tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe");
					if (!tmpFile.exists()) {
						tmpFile.mkdir();
					}
					tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" +idActuacio + "/informe/" + idInforme);
					if (!tmpFile.exists()) {
						tmpFile.mkdir();
					}
					tmpFile = new File(ruta + "/documents/" + idIncidencia + "/Actuacio/" +idActuacio + "/informe/" + idInforme + "/" + docInforme);
					if (!tmpFile.exists()) {
						tmpFile.mkdir();
					}
					fileName = ruta + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/" + docInforme + "/";
				}				
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
	
	public static void eliminarFitxer(Connection conn, int idUsuari, String ruta) {
		try {
			File tmpFile = new File(ruta);
			tmpFile = tmpFile.getCanonicalFile(); 
			if (tmpFile.exists()) {
				if (tmpFile.delete()) {
					Fitxers.marcarDocumentEliminat(conn, idUsuari, ruta);
				}
			}
		} catch (Exception e) {
   		}
	}
	
	public static class formParameters {
		private  Hashtable<String,String> parametres;
		private  Hashtable<String,List<Fitxer>> fitxersByName;
		private List<Fitxer> fitxers;
		
		public formParameters(){
			
		}
		
		public Hashtable<String,String> getParametres() {
			return parametres;
		}
		public void setParametres(Hashtable<String,String> parametres) {
			this.parametres = parametres;
		}
		public List<Fitxer> getFitxers() {
			return fitxers;
		}		
		public void setFitxers(List<Fitxer> fitxers) {
			this.fitxers = fitxers;
		}
		public Hashtable<String,List<Fitxer>> getFitxersByName() {
			return fitxersByName;
		}
		public void setFitxersByName(Hashtable<String,List<Fitxer>> fitxersByName) {
			this.fitxersByName = fitxersByName;
		}
	}
	
	public static formParameters getParamsFromMultipartForm(HttpServletRequest req) {
        Hashtable<String,String> parametres=new Hashtable<String,String>();
        Hashtable<String,List<Fitxer>> fitxersByName = new Hashtable<String,List<Fitxer>>();
        List<?> items;
        formParameters form = new formParameters();
		try {
			items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
			List<Fitxer> fitxers = new ArrayList<Fitxer>();
	      
	        for(int i=0;i<items.size();i++){
	        	FileItem item = (FileItem) items.get(i);
	            if (item.isFormField()) {
	                parametres.put(item.getFieldName(), item.getString("UTF-8"));
	            } else {
	            	if (item.getSize() != 0) {
	            		Fitxer fitxer = new Fitxer();
	                	fitxer.setNom(item.getName());
	                	fitxer.setNomCamp(item.getFieldName());
	                	fitxer.setFitxer(item);
	                	fitxers.add(fitxer);
	                	List<Fitxer> aux = new ArrayList<Fitxer>();
	                	if (!fitxersByName.containsKey(item.getFieldName())) {
	                		aux.add(fitxer);
	                		fitxersByName.put(item.getFieldName(), aux);
	                	}else{
	                		aux = fitxersByName.get(item.getFieldName());
	                		aux.add(fitxer);
	                		fitxersByName.replace(item.getFieldName(), aux);
	                	}
	                	
	            	}
	            }
	        }        
	        form.setParametres(parametres);
	        form.setFitxers(fitxers);
	        form.setFitxersByName(fitxersByName);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
        return form;
    }
}
