package core;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;


public class LoggerCore {
	
	public static void addLog(Connection conn, String text, String usuari) {
		BasicConfigurator.configure();
		Logger log = Logger.getLogger("Logger de Ejemplo");
		try {
			//log.addAppender(new FileAppender(new PatternLayout(),"V:/INTERCANVI D'OBRES/MAS, GUILLEM/prova.log", false));	
			// Get the base naming context
			String ruta =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio();
			log.addAppender(new FileAppender(new PatternLayout(),ruta + "/logs/intranetIBISEC.log", false));
			
			log.error(new Date().toString() + " - " + usuari + " - " + text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
