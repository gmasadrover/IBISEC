package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Security;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.log.SysoLogger;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;

import bean.Actuacio;
import bean.Actuacio.Feina;
import bean.Centre;
import utils.Fitxers;
import utils.Fitxers.Fitxer;
public class ActuacioCore {
	
	private static final String SQL_CAMPS = "id, idincidencia, descripcio, idcentre, usucre, datacre, dataaprovacio, usuaprovacio, datatancament, usutancament, datamodificacio, darreramodificacio, usuaprovarpa, dataaprovarpa, notes, motiutancament";
	
	/** 
     * Retorna l'objecte actuacio inicialitzat amb els resultats de la consulta
     * @param conn Connexió
     * @param rs Resultat de la consulta
     *  
     */
	private static Actuacio initActuacio(Connection conn, ResultSet rs) {
		Actuacio actuacio = new Actuacio();
		try {
			actuacio.setReferencia(rs.getString("id"));
			actuacio.setDescripcio(rs.getString("descripcio"));		
			actuacio.setDataCreacio(rs.getTimestamp("datacre"));
			actuacio.setIdUsuariCreacio(rs.getInt("usucre"));		
			actuacio.setCentre(CentreCore.findCentre(conn, rs.getString("idcentre"), false));	
			actuacio.setDataTancament(rs.getTimestamp("datatancament"));	
			actuacio.setMotiuTancament(rs.getString("motiutancament"));
			actuacio.setDataAprovacio(rs.getTimestamp("dataaprovacio"));		
			actuacio.setIdIncidencia(rs.getString("idincidencia"));		
			actuacio.setDarreraModificacio(rs.getTimestamp("datamodificacio"));
			actuacio.setModificacio(rs.getString("darreramodificacio"));
			actuacio.setDataAprovarPa(rs.getTimestamp("dataaprovarpa"));
			actuacio.setNotes(rs.getString("notes"));
			actuacio.setRefExt(searchRefExterna(conn, rs.getString("id")));		
			actuacio.setFeines(getFeinesActuacio(conn, rs.getString("id")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return actuacio;
	}
	
	/** 
     * Insereeix a la BD la nova actuació
     * @param conn Connexió
     * @param actuacio Objecte de la nova actuació
     *  
     */
	public static String novaActuacio(Connection conn, Actuacio actuacio) {
		String sql = "INSERT INTO public.tbl_actuacio(" + SQL_CAMPS + ")"
					+ "VALUES (?,?,?,?,?,localtimestamp,null,null,null,null,localtimestamp,?,null,null,'','')";
	 
		PreparedStatement pstm;
		String newCode = getNewCode(conn);
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, newCode);
			pstm.setString(2, actuacio.getIdIncidencia());
			pstm.setString(3, actuacio.getDescripcio());
			pstm.setString(4, actuacio.getCentre().getIdCentre());
			pstm.setInt(5, actuacio.getIdUsuariCreacio());
			pstm.setString(6, "Creació");				
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newCode;		
	}
	
	/** 
     * Retorna l'actuació especificada pel seu id
     * @param conn Connexió
     * @param referencia Identificador de l'actuació
     *  
     */
	public static Actuacio findActuacio(Connection conn, String referencia) {		 
		String sql = "SELECT " + SQL_CAMPS
					+ " FROM public.tbl_actuacio"
					+ " WHERE id = ?";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);		
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				Actuacio actuacio = initActuacio(conn, rs); 
				return actuacio;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Actuacio();
	}
		
	/** 
     * Retorna les actuacions que compleixen els parametres de cerca.
     * @param conn Connexió
     * @param idCentre Centre associat a l'actuació, pot ser NULL
     * @param estat Estat en que es troba l'actuació, pot ser NULL
     * @param dataPeticioIni Data desde quan es varen crear, pot ser NULL
     * @param dataPeticioFi Data fins quan es varen crear, pot ser NULL
     * @param tipus Tipus de l'actuació, pot ser NULL
     * @param dataExecucioIni Data desde quan es varen executar, pot ser NULL
     * @param dataExecucioFi Data fins quan es varen executar, pot ser NULL
     *  
     */
	public static List<Actuacio> searchActuacionsList(Connection conn, String idCentre, String estat, Date dataPeticioIni, Date dataPeticioFi, String tipus, Date dataExecucioIni, Date dataExecucioFi) {
		String sql = "SELECT DISTINCT a.id AS idactuacio, a.descripcio AS descripcio, a.datatancament AS datatancament, a.dataaprovacio AS dataaprovacio, a.dataaprovarpa AS dataaprovarpa, a.datacre AS datacre, a.darreramodificacio AS darreramodificacio, a.datamodificacio AS datamodificacio, c.codi AS codicentre, c.nom AS nomcentre, c.municipi AS municipicentre, c.localitat AS localitatcentre"
				+ 	" FROM public.tbl_actuacio a LEFT JOIN public.tbl_informeactuacio i ON a.id = i.idactuacio"
				+ 	"							 LEFT JOIN public.tbl_expedient e ON e.expcontratacio = i.expcontratacio"
				+   "							 LEFT JOIN public.tbl_centres c ON a.idcentre = c.codi";		;
		
		PreparedStatement pstm;		
		
		boolean primeraCondicio = true;
		if (idCentre != null) {
			if (primeraCondicio) {
				sql += " WHERE a.idcentre = ?";
				primeraCondicio = false;
			} 			
		}
		
		if (dataPeticioIni != null) {
			if (primeraCondicio) {
				sql += " WHERE a.datacre >= ?";
				primeraCondicio = false;
			} else {
				sql += " AND a.datacre >= ?";	
			}			
		}
		if (dataPeticioFi != null) {
			if (primeraCondicio) {
				sql += " WHERE a.datacre <= ?";
				primeraCondicio = false;
			} else {
				sql += " AND a.datacre <= ?";
			}
								
		}
		if (dataExecucioIni != null && dataExecucioFi != null) {
			if (primeraCondicio) {
				sql += " WHERE e.datainiciexecucio <= ? AND e.datarecepcio IS NULL";
				primeraCondicio = false;
			} else {
				sql += " AND e.datainiciexecucio <= ? AND e.datarecepcio IS NULL";	
			}
							
		} else {
			if (dataExecucioIni != null) {
				if (primeraCondicio) {
					sql += " WHERE e.datainiciexecucio <= ? AND e.datarecepcio IS NULL";
					primeraCondicio = false;
				} else {
					sql += " AND e.datainiciexecucio <= ? AND e.datarecepcio IS NULL";	
				}
				
			}
			if (dataExecucioFi != null) {
				if (primeraCondicio) {
					sql += " WHERE e.datainiciexecucio <= ? AND e.datarecepcio IS NULL";
					primeraCondicio = false;
				} else {
					sql += " AND e.datainiciexecucio <= ? AND e.datarecepcio IS NULL";
				}
									
			}
		}	
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance(); 
		Date dataFi = cal.getTime();
		String dataFiString = df.format(dataFi);
		if (estat != null && ! estat.equals("-1")) {	
			if ("redaccio".equals(estat)) sql+= " AND a.datatancament IS NULL AND e.dataliquidacio IS NULL AND i.expcontratacio = ''"; 
			if ("iniciExpedient".equals(estat)) sql+= " AND e.dataliquidacio IS NULL AND i.expcontratacio != '' AND i.databoib IS NULL AND e.dataadjudicacio IS NULL"; 
			if ("publicat".equals(estat)) sql+= " AND e.dataliquidacio IS NULL AND i.databoib IS NOT NULL AND e.datalimitprsentacio >= '" + dataFiString + "' AND e.dataadjudicacio IS NULL"; 
			if ("licitacio".equals(estat)) sql+= " AND e.dataliquidacio IS NULL AND i.databoib IS NOT NULL AND e.datalimitprsentacio < '" + dataFiString + "' AND e.dataadjudicacio IS NULL";
			if ("adjudicacio".equals(estat)) sql+= " AND e.dataliquidacio IS NULL AND e.dataadjudicacio IS NOT NULL AND e.dataformalitzaciocontracte IS NULL"; 	
			if ("firmat".equals(estat)) sql+= " AND e.dataliquidacio IS NULL AND i.databoib > '01/01/2015' AND (e.datainiciexecucio IS NULL OR e.datainiciexecucio > localtimestamp) AND e.dataformalitzaciocontracte IS NOT NULL"; 	
			if ("execucio".equals(estat)) sql+= " AND e.dataliquidacio IS NULL AND i.databoib > '01/01/2015' AND e.datainiciexecucio <= localtimestamp AND e.datarecepcio IS NULL"; 	
			if ("garantia".equals(estat)) sql+= " AND e.dataliquidacio IS NULL AND i.databoib > '01/01/2015' AND e.datarecepcio IS NOT NULL AND e.dataretorngarantia < localtimestamp"; 	
			if ("acabat".equals(estat)) sql+= " AND e.dataliquidacio IS NOT NULL"; 	
		} 
		List<Actuacio> list = new ArrayList<Actuacio>();
		try {
			pstm = conn.prepareStatement(sql);			
			int contVars = 1;
			if (idCentre != null) {
				pstm.setString(contVars, idCentre);
				contVars += 1;
			}
			if (dataPeticioIni != null) {
				pstm.setDate(contVars, new java.sql.Date(dataPeticioIni.getTime()));
				contVars += 1;
			}
			if (dataPeticioFi != null) {
				pstm.setDate(contVars, new java.sql.Date(dataPeticioFi.getTime()));
				contVars += 1;
			}
			if (dataExecucioIni != null && dataExecucioFi != null) {
				pstm.setDate(contVars, new java.sql.Date(dataExecucioFi.getTime()));
				contVars += 1;
			} else {
				if (dataExecucioIni != null) {
					pstm.setDate(contVars, new java.sql.Date(dataExecucioIni.getTime()));
					contVars += 1;
				}
				if (dataExecucioFi != null) {
					pstm.setDate(contVars, new java.sql.Date(dataExecucioFi.getTime()));
					contVars += 1;
				}
			}
			if (tipus != null && !tipus.isEmpty() && ! tipus.equals("-1")) {
				pstm.setString(contVars, tipus);
				contVars += 1;
			}		
			ResultSet rs = pstm.executeQuery();		
			while (rs.next()) {			
				Actuacio actuacio = new Actuacio();
				actuacio.setReferencia(rs.getString("idactuacio"));
				actuacio.setDescripcio(rs.getString("descripcio"));
				actuacio.setDataTancament(rs.getDate("datatancament"));
				actuacio.setDataAprovacio(rs.getDate("dataaprovacio"));
				actuacio.setDataAprovarPa(rs.getDate("dataaprovarpa"));
				actuacio.setDataCreacio(rs.getDate("datacre"));
				actuacio.setDarreraModificacio(rs.getDate("datamodificacio"));
				actuacio.setModificacio(rs.getString("darreramodificacio"));				
				Centre centre = new Centre();
				centre.setIdCentre(rs.getString("codicentre"));
				centre.setNom(rs.getString("nomcentre"));
				centre.setMunicipi(rs.getString("municipicentre"));
				centre.setLocalitat(rs.getString("localitatcentre"));
				actuacio.setCentre(centre);
				list.add(actuacio);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public static List<Actuacio> searchActuacionsCentre(Connection conn, String idCentre) {
		String sql = "SELECT DISTINCT a.id AS idactuacio, a.descripcio AS descripcio, a.datatancament AS datatancament, a.dataaprovacio AS dataaprovacio, a.dataaprovarpa AS dataaprovarpa, a.datacre AS datacre, a.darreramodificacio AS darreramodificacio, a.datamodificacio AS datamodificacio"
				+ 	" FROM public.tbl_actuacio a"
				+ 	" WHERE a.idcentre = ?";
		
		PreparedStatement pstm;	
		List<Actuacio> list = new ArrayList<Actuacio>();
		try {
			pstm = conn.prepareStatement(sql);			
			pstm.setString(1, idCentre);
			ResultSet rs = pstm.executeQuery();		
			while (rs.next()) {			
				Actuacio actuacio = new Actuacio();
				actuacio.setReferencia(rs.getString("idactuacio"));
				actuacio.setDescripcio(rs.getString("descripcio"));
				actuacio.setDataTancament(rs.getDate("datatancament"));
				actuacio.setDataAprovacio(rs.getDate("dataaprovacio"));
				actuacio.setDataAprovarPa(rs.getDate("dataaprovarpa"));
				actuacio.setDataCreacio(rs.getDate("datacre"));
				actuacio.setDarreraModificacio(rs.getDate("datamodificacio"));
				actuacio.setModificacio(rs.getString("darreramodificacio"));
				list.add(actuacio);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	/** 
     * Retorna una llista amb les actuacions associades a l'incidència
     * @param conn Connexió
     * @param idIncidencia Identificador de l'incidència
     *  
     */
	public static List<Actuacio> searchActuacionsInciencia(Connection conn, String idIncidencia) {
		List<Actuacio> list = new ArrayList<Actuacio>();
		String sql = "SELECT " + SQL_CAMPS
				 	+ " FROM public.tbl_actuacio"
				 	+ " WHERE idincidencia = ?";
				
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idIncidencia);	
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Actuacio actuacio = initActuacio(conn, rs);			
				list.add(actuacio);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	/** 
     * Retorna els codis dels expedients relacionats amb l'actuació
     * @param conn Connexió
     * @param idActuacio Identificador de l'actuació
     *  
     */
	private static String searchRefExterna(Connection conn, String idActuacio) {
		String refExt = "";
		String sql = "SELECT expcontratacio"
			 	+ " FROM public.tbl_informeactuacio"
			 	+ " WHERE idactuacio = ?";			
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idActuacio);	
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				refExt += rs.getString("expcontratacio") + " // ";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return refExt;
	}
	
	/** 
     * Retorna el codi per a la nova actuació
     * @param conn Connexió
     *  
     */
	private static String getNewCode(Connection conn) {		
		String newCode = "1";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		String sql = "SELECT id, datacre"
					+ " FROM public.tbl_actuacio"
					+ " WHERE id like '%" + yearInString + "-ACT%'"
					+ " ORDER BY datacre DESC, id DESC LIMIT 1;";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();		
			String prefix = "ACT";
			if (rs.next()) { //Codis nous
				String actualCode = rs.getString("id");			
				int num = Integer.valueOf(actualCode.split("-")[2]);
				String numFormatted = String.format("%04d", num + 1);
				newCode = yearInString + "-" + prefix + "-" + numFormatted;
			}
			else {
				int num = 0;		
				String numFormatted = String.format("%04d", num + 1);
				newCode = yearInString + "-" + prefix + "-" + numFormatted;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newCode;
	}

	/** 
     * Retorna els codis dels expedients relacionats amb l'actuació
     * @param conn Connexió
     * @param idActuacio Identificador de l'actuació
     *  
     */
	public static void actualitzarActuacio(Connection conn, String idActuacio, String modificacio) {
		String sql = "UPDATE public.tbl_actuacio"
				+ " SET darreramodificacio=?, datamodificacio=localtimestamp"
				+ " WHERE id=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, modificacio);
			pstm.setString(2, idActuacio);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	
	public static void modificarActuacio(Connection conn, String idActuacio, String descripcio, String notes) {
		String sql = "UPDATE public.tbl_actuacio"
				+ " SET descripcio=?, notes=?"
				+ " WHERE id=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, descripcio);
			pstm.setString(2, notes);
			pstm.setString(3, idActuacio);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
	
	}

	public static void aprovarPA(Connection conn, String referencia, int idUsuari) {
		String sql = "UPDATE public.tbl_actuacio"
					+ " SET dataaprovarpa=localtimestamp, usuaprovarpa=?, darreramodificacio='aprovar proposta', datamodificacio=localtimestamp"
					+ " WHERE id=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);
			pstm.setString(2, referencia);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static void eliminarAprovacioPA(Connection conn, String referencia) {
		String sql = "UPDATE public.tbl_actuacio"
				+ " SET dataaprovarpa=null, usuaprovarpa=null, darreramodificacio='', datamodificacio=localtimestamp"
				+ " WHERE id=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static void aprovar(Connection conn, String referencia, int idUsuari) {
		String sql = "UPDATE public.tbl_actuacio"
					+ " SET dataaprovacio=localtimestamp, usuaprovacio=?, darreramodificacio='aprovar actuació', datamodificacio=localtimestamp"
					+ " WHERE id=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);
			pstm.setString(2, referencia);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	

	public static void tancar(Connection conn, String referencia, String motiu,  int idUsuari) {
		String sql = "UPDATE public.tbl_actuacio"
					+ " SET datatancament=localtimestamp, usutancament=?, darreramodificacio='tancar actuació', datamodificacio=localtimestamp, motiutancament=?"
					+ " WHERE id=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);
			pstm.setString(2, motiu);
			pstm.setString(3, referencia);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
	
	public static void obrir(Connection conn, String referencia)  {
		String sql = "UPDATE public.tbl_actuacio"
					+ " SET datatancament=null, usutancament=null, darreramodificacio='reobrir actuació', datamodificacio=localtimestamp, motiutancament=null"
					+ " WHERE id=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, referencia);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public static void seguirActuacio(Connection conn, String idActuacio, int idUsuari) {
		String sql = "INSERT INTO public.tbl_seguiments(idusuari, idactuacio)"
				+ " VALUES (?, ?);";		 
		PreparedStatement pstm = null; 
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);
			pstm.setString(2, idActuacio);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	

	public static void desSeguirActuacio(Connection conn, String idActuacio, int idUsuari) {
		String sql = "DELETE FROM public.tbl_seguiments"
					+ " WHERE idactuacio = ? AND idusuari = ?";		 
		PreparedStatement pstm = null; 
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idActuacio);
			pstm.setInt(2, idUsuari);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	

	public static List<Actuacio> llistaActuacionsSeguiment(Connection conn, int idUsuari) {
		String sql = "SELECT *"
					+ " FROM public.tbl_seguiments s LEFT JOIN public.tbl_actuacio a ON s.idactuacio = a.id"
					+ " WHERE idactuacio IS NOT NULL AND s.idusuari = ?";
		PreparedStatement pstm;
		List<Actuacio> list = new ArrayList<Actuacio>();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idUsuari);		
			ResultSet rs = pstm.executeQuery();
			
			while (rs.next()) {
				Actuacio actuacio = initActuacio(conn, rs);
				list.add(actuacio);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return list;
	}
	

	public static boolean isSeguimentActuacio(Connection conn, String idActuacio, int idUsuari) {
		boolean seguint = false;
		String sql = "SELECT idusuari, idactuacio"
					+ " FROM public.tbl_seguiments"
					+ " WHERE idactuacio = ? AND idusuari = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idActuacio);
			pstm.setInt(2, idUsuari);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) seguint = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return seguint;
	}
	

	private static List<Feina> getFeinesActuacio(Connection conn, String idActuacio) {
		List<Feina> feinesList = new ArrayList<Feina>();
		String sql = "SELECT idfeina, nomremitent, nomdestinatari, contingut, data, notes"
					+ " FROM public.tbl_feines"
					+ " WHERE idactuacio = ?"
					+ " ORDER BY idfeina";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idActuacio);	
			ResultSet rs = pstm.executeQuery();
			Feina feina = null;
			while (rs.next()) {
				feina = new Actuacio().new Feina();
				feina.setIdFeina(rs.getString("idfeina"));
				feina.setNomRemitent(rs.getString("nomremitent"));
				feina.setNomDestinatari(rs.getString("nomdestinatari"));
				feina.setContingut(rs.getString("contingut"));
				feina.setData(rs.getTimestamp("data"));
				feina.setNotes(rs.getString("notes"));		
				feinesList.add(feina);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return feinesList;
	}
	

	public static void afegirFeina(Connection conn, String idActuacio, Feina feina) {
		String sql = "INSERT INTO public.tbl_feines(idfeina, idactuacio, nomremitent, nomdestinatari, contingut, data, notes)"
					+ " VALUES (?, ?, ?, ?, ?, localtimestamp, ?);";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, getCodiNovaFeina(conn));
			pstm.setString(2, idActuacio);
			pstm.setString(3, feina.getNomRemitent());
			pstm.setString(4, feina.getNomDestinatari());
			pstm.setString(5, feina.getContingut());
			pstm.setString(6, feina.getNotes());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	

	public static void eliminarFeina(Connection conn, String idFeina) {
		String sql = "DELETE FROM public.tbl_feines"
				+ " WHERE idfeina = ?";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idFeina);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	

	public static void modificarFeina(Connection conn, Feina feina) {
		String sql = "UPDATE public.tbl_feines"
					+ " SET nomremitent = ?, nomdestinatari = ?, contingut = ?, notes = ?"
					+ " WHERE idfeina = ?";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, feina.getNomRemitent());
			pstm.setString(2, feina.getNomDestinatari());
			pstm.setString(3, feina.getContingut());
			pstm.setString(4, feina.getNotes());
			pstm.setString(5, feina.getIdFeina());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}

	public static Feina findFeina(Connection conn, String idFeina) {
		Feina feina = new Actuacio().new Feina();
		String sql = "SELECT idfeina, idactuacio, nomremitent, nomdestinatari, contingut, data, notes"
				+ " FROM public.tbl_feines"
				+ " WHERE idfeina = ?";		 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idFeina);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) { //Codis nous
				feina.setIdFeina(rs.getString("idfeina"));
				feina.setNomRemitent(rs.getString("nomremitent"));
				feina.setNomDestinatari(rs.getString("nomdestinatari"));
				feina.setContingut(rs.getString("contingut"));
				feina.setNotes(rs.getString("notes"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return feina;
	}
	

	private static String getCodiNovaFeina(Connection conn) {
		String codi = "1";
		String sql = "SELECT idfeina"
				+ " FROM public.tbl_feines"
				+ " ORDER BY idfeina::INT DESC LIMIT 1;";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) { //Codis nous
				String actualCode = rs.getString("idfeina");			
				int num = Integer.valueOf(actualCode) + 1;	
				codi = String.valueOf(num);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return codi;
	}
	
	public static List<Fitxer> findDocumentsTasques(String idIncidencia, Connection conn) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();
		
		String sql = "SELECT idtasca"
				+ " FROM public.tbl_tasques"
				+ " WHERE idincidencia = ?";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idIncidencia);
			ResultSet rs = pstm.executeQuery();		
			String rutaBase =  ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio() + "/documents/Tasca/";
			
			while (rs.next()) {
				arxius.addAll(Fitxers.ObtenirTotsFitxers(rutaBase + rs.getString("idtasca"), ""));				
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return arxius;		
	}
	
	public static List<Fitxer> findDocumentsAltres(String idIncidencia, String idActuacio, Connection conn) {
		List<Fitxer> arxius = new ArrayList<Fitxer>();
		
		String rutaBase = "";
		rutaBase = ConfiguracioCore.getConfiguracio(conn).getRutaBaseDocumentacio() + "/documents/" + idIncidencia + "/Actuacio/" + idActuacio;
		File dir = new File(rutaBase);
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
						if (!ficheros[x].getName().contains(".zip") && Fitxers.isPDF(ficheros[x])) {
							PdfReader reader;						
							reader = new PdfReader(rutaBase + "/" + ficheros[x].getName());						
			            	AcroFields acroFields = reader.getAcroFields();
			            	List<String> signatureNames = acroFields.getSignatureNames();
			            	if (signatureNames.size() > 0) fitxer.setSignat(true);
						}
		            	fitxer.setNom(ficheros[x].getName());
						fitxer.setRuta(rutaBase + "/" + ficheros[x].getName());
						fitxer.setSeccio("Actuació");
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
	
	
}
