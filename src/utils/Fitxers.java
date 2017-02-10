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
	}
	
	public static final String RUTA_BASE = "V:/INTERCANVI D'OBRES/MAS, GUILLEM/documents/";
	
	public static List<Fitxer> ObtenirFitxers(int idIncidencia, int idActuacio, String tipus, int idTipus, String idSubTipus) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();
		String rutaBase = RUTA_BASE + idIncidencia;
		if (idActuacio != -1) rutaBase += "/Actuacio/" + idActuacio;				
		rutaBase += "/" + tipus + "/" + idTipus;
		if (idSubTipus != "") rutaBase += "/Comentari/" + idSubTipus;
		arxius = ObtenirTotsFitxers(rutaBase, tipus);		
		return arxius;		
	}
	
	public static List<Fitxer> ObtenirTotsFitxers(int idActuacio) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();		
		String rutaBase = RUTA_BASE + idActuacio + "/";
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
					System.out.println(ruta + "/" + ficheros[x].getName());
				}				
			}
		}
		return arxius;	
	}
	
	public static void guardarFitxer(List<FileItem> fitxers, String idIncidencia, String idActuacio, String tipus, String idTipus, String idSubTipus){		
		if (!fitxers.isEmpty()) {
			String fileName = "";
			// Crear directoris si no existeixen
			File tmpFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia);
			if (!tmpFile.exists()) {
				System.out.println(utils.Fitxers.RUTA_BASE + idIncidencia);
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio");
			if (!tmpFile.exists()) {
				System.out.println(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio");
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" + idActuacio);
			if (!tmpFile.exists()) {
				System.out.println(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" + idActuacio);
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" + idActuacio + "/" +tipus);
			if (!tmpFile.exists()) {
				System.out.println(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" + idActuacio + "/" +tipus);
				tmpFile.mkdir();
			}
			tmpFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" +idActuacio + "/" + tipus + "/" + idTipus);
			if (!tmpFile.exists()) {
				System.out.println(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" +idActuacio + "/" + tipus + "/" + idTipus);
				tmpFile.mkdir();
			}		
			fileName = utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" + idActuacio + "/" + tipus + "/" + idTipus + "/";
			// Miram si te subtipus
			if (!idSubTipus.isEmpty()) {
				tmpFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" + idActuacio + "/" + tipus + "/" + idTipus + "/Comentari");
				if (!tmpFile.exists()) {
					System.out.println(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" + idActuacio + "/" + tipus + "/" + idTipus + "/Comentari");
					tmpFile.mkdir();
				}
				tmpFile = new File(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" + idActuacio + "/" + tipus + "/" + idTipus + "/Comentari/" + idSubTipus);
				if (!tmpFile.exists()) {
					System.out.println(utils.Fitxers.RUTA_BASE + idIncidencia + "/Actuacio/" + idActuacio + "/" + tipus + "/" + idTipus + "/Comentari/" + idSubTipus);
					tmpFile.mkdir();
				}
				fileName +=  "Comentari/" + idSubTipus + "/";
			}
			System.out.println(fileName);
	        for(int i=0;i<fitxers.size();i++){
	            FileItem item = (FileItem) fitxers.get(i);
	            if (item.getName() != "") {
	            	File archivo_server = new File(fileName + item.getName());
	               	try {
	               		item.write(archivo_server);
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
		private List<FileItem> fitxers;
		
		public formParameters(){
			
		}
		
		public Hashtable<String,String> getParametres() {
			return parametres;
		}
		public void setParametres(Hashtable<String,String> parametres) {
			this.parametres = parametres;
		}
		public List<FileItem> getFitxers() {
			return fitxers;
		}
		public void setFitxers(List<FileItem> fitxers) {
			this.fitxers = fitxers;
		}
	}
	
	public static formParameters getParamsFromMultipartForm(HttpServletRequest req) throws FileUploadException, UnsupportedEncodingException {
        Hashtable<String,String> ret=new Hashtable<String,String>();
        List<?> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);  
        List<FileItem> fitxers = new ArrayList<FileItem>();
        for(int i=0;i<items.size();i++){
        	FileItem item = (FileItem) items.get(i);
            if (item.isFormField()) {
                ret.put(item.getFieldName(), item.getString("UTF-8"));
            } else {
            	fitxers.add(item);
            }
        }
        formParameters form = new formParameters();
        form.setParametres(ret);
        form.setFitxers(fitxers);
        return form;
    }
}
