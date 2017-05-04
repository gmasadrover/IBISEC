package utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Fitxers {
	
	public static class Fitxer{
		private String nom;
		private String ruta;
		private String seccio;
		private FileItem fitxer;
		
		public Fitxer(){
			
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
	}
	
	public static final String RUTA_BASE = "//sibisec1/usuaris/INTERCANVI D'OBRES/IBISEC/NOVA INTRANET";
	
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
	
	public static List<Fitxer> ObtenirTotsFitxers(String ruta, String seccio) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();		
		File dir = new File(ruta);
		File[] ficheros = dir.listFiles();
		if (ficheros == null) {			
		} else {
			for (int x=0;x<ficheros.length;x++) {
				if (ficheros[x].isDirectory()) {
					arxius.addAll(ObtenirTotsFitxers(ruta + "/" + ficheros[x].getName(), seccio + "/" + ficheros[x].getName()));
				} else {
					Fitxer fitxer = new Fitxer();
					fitxer.setNom(ficheros[x].getName());
					fitxer.setRuta(ruta + "/" + ficheros[x].getName());
					fitxer.setSeccio(seccio);
					arxius.add(fitxer);
				}				
			}
		}
		return arxius;	
	}
	
	public static void guardarFitxer(List<Fitxer> fitxers, String idIncidencia, String idActuacio, String tipus, String idTipus, String idSubTipus){		
		if (!fitxers.isEmpty()) {
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
		tmpFile.delete();
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
            	Fitxer fitxer = new Fitxer();
            	fitxer.setNom(item.getFieldName());
            	fitxer.setFitxer(item);
            	fitxers.add(fitxer);
            }
        }        
        form.setParametres(ret);
        form.setFitxers(fitxers);
        return form;
    }
}
