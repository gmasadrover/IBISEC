package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.itextpdf.text.pdf.security.SignaturePermissions.FieldLock;
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
		private String ruta;
		private String seccio;
		private boolean signat;
		private List<infoFirma> firmesList;
		private FileItem fitxer;
		
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
			String encoded = "";
			if (this.ruta != null && !this.ruta.isEmpty()) {
				try {
					encoded = java.net.URLEncoder.encode(this.ruta, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return encoded;
		}
		public void setRuta(String ruta) {
			this.ruta = ruta;
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
	}
	
	public static final String RUTA_BASE = "//sibisec1/usuaris/INTERCANVI D'OBRES/IBISEC/NOVA INTRANET";
	//public static final String RUTA_BASE = "//sibisec1/usuaris/PERSONAL/MAS GUILLEM/NOVA INTRANET";
	
	public static List<Fitxer> ObtenirManuals() {
		List<Fitxer> arxius = new ArrayList<Fitxer>();	
		String ruta = RUTA_BASE + "/Manuals/";
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
	
	public static List<Fitxer> ObtenirFitxers(String idIncidencia, String idActuacio, String tipus, String idTipus, String idSubTipus) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();
		String rutaBase = RUTA_BASE + "/documents/" + idIncidencia;
		if (!idActuacio.isEmpty()) rutaBase += "/Actuacio/" + idActuacio;				
		rutaBase += "/" + tipus + "/" + idTipus;
		if (!idSubTipus.isEmpty()) rutaBase += "/Comentari/" + idSubTipus;
		arxius = ObtenirTotsFitxers(rutaBase, tipus);		
		return arxius;		
	}
	
	public static List<Fitxer> ObtenirTotsFitxers(String idIncidencia) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();		
		String rutaBase = RUTA_BASE + "/documents/" + idIncidencia + "/";		
		arxius = ObtenirTotsFitxers(rutaBase, "Incidencia");			
		return arxius;	
	}
	
	public static Fitxer ObtenirFitxer(String ruta) {
		Fitxer arxiu = new Fitxer();
		File dir = new File(ruta);
		File[] ficheros = dir.listFiles();
		LoggerFactory.getInstance().setLogger(new SysoLogger());
		BouncyCastleProvider provider = new BouncyCastleProvider();
		Security.addProvider(provider);
		if (ficheros == null || ficheros.length == 0) {			
		} else {
			try {Fitxer fitxer = new Fitxer();	
				PdfReader reader;						
				reader = new PdfReader(ruta + "/" + ficheros[0].getName());		
            	AcroFields acroFields = reader.getAcroFields();
            	List<String> signatureNames = acroFields.getSignatureNames();
            	SignaturePermissions perms = null;
            	for (String name: signatureNames) {
            		fitxer.setSignat(true);
            		perms = inspectSignature(acroFields, name, perms, fitxer);
    			}
            	
            	fitxer.setNom(ficheros[0].getName());
				fitxer.setRuta(ruta + "/" + ficheros[0].getName());
				fitxer.setSeccio("");
				arxiu = fitxer;	
				
			} catch (IOException | GeneralSecurityException e) {
				e.printStackTrace();
			}
		}
		return arxiu;
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
						arxius.addAll(ObtenirTotsFitxers(ruta + "/" + ficheros[x].getName(), seccio + "/" + ficheros[x].getName()));
					} else {
						Fitxer fitxer = new Fitxer();	
						if (isPDF(ficheros[x])) {
							PdfReader reader;						
							reader = new PdfReader(ruta + "/" + ficheros[x].getName());						
			            	AcroFields acroFields = reader.getAcroFields();
			            	List<String> signatureNames = acroFields.getSignatureNames();		            	
			            	SignaturePermissions perms = null;
			            	for (String name: signatureNames) {
			            		fitxer.setSignat(true);
			            		//verifySignature(acroFields, name);
			            		perms = inspectSignature(acroFields, name, perms, fitxer);
			            		/*System.out.println("Signature name: " + name);
			          		   	System.out.println("Signature covers whole document: "
			                                          + acroFields.signatureCoversWholeDocument(name));
			            		   // Affichage sur les revision - version
			          		   	System.out.println("Document revision: " + acroFields.getRevision(name) + " of "
			                                          + acroFields.getTotalRevisions());*/
			    			}
						}
		            	
		            	fitxer.setNom(ficheros[x].getName());
						fitxer.setRuta(ruta + "/" + ficheros[x].getName());
						fitxer.setSeccio(seccio);
						arxius.add(fitxer);
						
					}				
				}
			} catch (IOException | GeneralSecurityException e) {
				e.printStackTrace();
			}
		}
		return arxius;	
	}
	
	public static boolean isPDF(File file) throws FileNotFoundException{	    
	    Scanner inputFile = new Scanner(new FileReader(file));
	    while (inputFile.hasNextLine()) {
	        final String checkline = inputFile.nextLine();
	        if(checkline.contains("%PDF-")) { 
	            // a match!
	            return true;
	        }  
	    }
	    return false;
	}
	
	public static PdfPKCS7 verifySignature(AcroFields fields, String name) throws GeneralSecurityException, IOException {
		//System.out.println("Signature covers whole document: " + fields.signatureCoversWholeDocument(name));
		//System.out.println("Document revision: " + fields.getRevision(name) + " of " + fields.getTotalRevisions());
        PdfPKCS7 pkcs7 = fields.verifySignature(name);
        System.out.println("Integrity check OK? " + pkcs7.verify());
        return pkcs7;
	}
	
	
	public static SignaturePermissions inspectSignature(AcroFields fields, String name, SignaturePermissions perms, Fitxer fitxer) throws GeneralSecurityException, IOException {
		List<FieldPosition> fps = fields.getFieldPositions(name);
		if (fps != null && fps.size() > 0) {
			FieldPosition fp = fps.get(0);
			Rectangle pos = fp.position;
			if (pos.getWidth() == 0 || pos.getHeight() == 0) {
				//System.out.println("Invisible signature");
			}
			else {
				//System.out.println(String.format("Field on page %s; llx: %s, lly: %s, urx: %s; ury: %s",
					//fp.page, pos.getLeft(), pos.getBottom(), pos.getRight(), pos.getTop()));
			}
		}
 
		PdfPKCS7 pkcs7 = verifySignature(fields, name);
		//System.out.println("Digest algorithm: " + pkcs7.getHashAlgorithm());
		//System.out.println("Encryption algorithm: " + pkcs7.getEncryptionAlgorithm());
		//System.out.println("Filter subtype: " + pkcs7.getFilterSubtype());
		X509Certificate cert = (X509Certificate) pkcs7.getSigningCertificate();
		System.out.println("Name of the signer: " + CertificateInfo.getSubjectFields(cert).getField("CN"));
		Fitxer.infoFirma info = fitxer.new infoFirma();
		info.setNomFirmant(CertificateInfo.getSubjectFields(cert).getField("CN"));
		
		//if (pkcs7.getSignName() != null) System.out.println("Alternative name of the signer: " + pkcs7.getSignName());
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
		System.out.println("Signed on: " + date_format.format(pkcs7.getSignDate().getTime()));
		info.setDataFirma(date_format.format(pkcs7.getSignDate().getTime()));
		/*if (pkcs7.getTimeStampDate() != null) {
			System.out.println("TimeStamp: " + date_format.format(pkcs7.getTimeStampDate().getTime()));
			TimeStampToken ts = pkcs7.getTimeStampToken();
			System.out.println("TimeStamp service: " + ts.getTimeStampInfo().getTsa());
			System.out.println("Timestamp verified? " + pkcs7.verifyTimestampImprint());
		}*/
		//System.out.println("Location: " + pkcs7.getLocation());
		//System.out.println("Reason: " + pkcs7.getReason());
		PdfDictionary sigDict = fields.getSignatureDictionary(name);
		PdfString contact = sigDict.getAsString(PdfName.CONTACTINFO);
		if (contact != null)
			System.out.println("Contact info: " + contact);
		perms = new SignaturePermissions(sigDict, perms);
		//System.out.println("Signature type: " + (perms.isCertification() ? "certification" : "approval"));
		//System.out.println("Filling out fields allowed: " + perms.isFillInAllowed());
		//System.out.println("Adding annotations allowed: " + perms.isAnnotationsAllowed());
		for (FieldLock lock : perms.getFieldLocks()) {
			System.out.println("Lock: " + lock.toString());
		}
		fitxer.addFirmesList(info);
        return perms;
	}
	
	public static void guardarFitxer(List<Fitxer> fitxers, String idIncidencia, String idActuacio, String tipus, String idTipus, String idSubTipus, String idInforme, String docInforme) throws IOException{		
		if (fitxers != null && !fitxers.isEmpty()) {
			String fileName = "";
			// Crear directoris si no existeixen
			File tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/"  + idIncidencia);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio");
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio);
			if (!tmpFile.exists()) {
				tmpFile.mkdir();
			}
			fileName = utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/";
			// Miram si te tipus
			if (!tipus.isEmpty()) {
				tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/" +tipus);
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}
				tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" +idActuacio + "/" + tipus + "/" + idTipus);
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}		
				fileName = utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/" + tipus + "/" + idTipus + "/";
				// Miram si te subtipus
				if (!idSubTipus.isEmpty()) {
					tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/" + tipus + "/" + idTipus + "/Comentari");
					if (!tmpFile.exists()) {
						tmpFile.mkdir();
					}
					tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/" + tipus + "/" + idTipus + "/Comentari/" + idSubTipus);
					if (!tmpFile.exists()) {
						tmpFile.mkdir();
					}
					fileName +=  "Comentari/" + idSubTipus + "/";
				}
			}
			else if (!idInforme.isEmpty()) {
				tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe");
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}
				tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" +idActuacio + "/informe/" + idInforme);
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}
				tmpFile = new File(utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" +idActuacio + "/informe/" + idInforme + "/" + docInforme);
				if (!tmpFile.exists()) {
					tmpFile.mkdir();
				}
				fileName = utils.Fitxers.RUTA_BASE + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio + "/informe/" + idInforme + "/" + docInforme + "/";
			}
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
	
	public static void eliminarFitxer(String ruta) {
		File tmpFile = new File(ruta);
		if (tmpFile.exists()) tmpFile.delete();
	}
	
	public static class formParameters {
		private  Hashtable<String,String> parametres;
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
	}
	
	public static formParameters getParamsFromMultipartForm(HttpServletRequest req) throws FileUploadException, UnsupportedEncodingException {
        Hashtable<String,String> ret=new Hashtable<String,String>();
        List<?> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);  
        List<Fitxer> fitxers = new ArrayList<Fitxer>();
        formParameters form = new formParameters();
        for(int i=0;i<items.size();i++){
        	FileItem item = (FileItem) items.get(i);
            if (item.isFormField()) {
                ret.put(item.getFieldName(), item.getString("UTF-8"));
            } else {
            	if (item.getSize() != 0) {
            		Fitxer fitxer = new Fitxer();
                	fitxer.setNom(item.getFieldName());
                	fitxer.setFitxer(item);
                	fitxers.add(fitxer);
            	}
            }
        }        
        form.setParametres(ret);
        form.setFitxers(fitxers);
        return form;
    }
}
