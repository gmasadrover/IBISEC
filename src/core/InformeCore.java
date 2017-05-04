package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.InformeActuacio;
import bean.User;

public class InformeCore {
	
	private static InformeActuacio initInforme(Connection conn, ResultSet rs) throws SQLException {
		InformeActuacio informe = new InformeActuacio();
		informe.setIdInf(rs.getString("idinf"));
		informe.setIdTasca(rs.getInt("idtasca"));
		informe.setIdIncidencia(rs.getString("idincidencia"));
		informe.setIdActuacio(rs.getString("idactuacio"));
		informe.setObjecte(rs.getString("objecte"));
		informe.setTipusObra(rs.getString("tipusobra"));
		informe.setLlicencia(rs.getBoolean("llicencia"));
		informe.setTipusLlicencia(rs.getString("tipusllicencia"));
		informe.setContracte(rs.getBoolean("contracte"));
		informe.setVec(rs.getDouble("vec"));
		informe.setIva(rs.getDouble("iva"));
		informe.setPlic(rs.getDouble("plic"));
		informe.setTermini(rs.getString("termini"));
		informe.setComentari(rs.getString("comentari"));	
		User usuari = UsuariCore.findUsuariByID(conn, rs.getInt("usucre"));
		informe.setUsuari(usuari);
		informe.setDataCreacio(rs.getTimestamp("datacre"));
		informe.setAdjunts(utils.Fitxers.ObtenirFitxers(rs.getString("idincidencia"), rs.getString("idactuacio"), "Informe Previ", rs.getString("idtasca"),""));
		informe.setPartida(CreditCore.getPartidaInforme(conn, rs.getString("idinf")));	
		informe.setUsuariCapValidacio(UsuariCore.findUsuariByID(conn, rs.getInt("usucapvalidacio")));
		informe.setDataCapValidacio(rs.getTimestamp("datacapvalidacio"));
		informe.setComentariCap(rs.getString("comentaricap"));
		informe.setUsuariAprovacio(UsuariCore.findUsuariByID(conn, rs.getInt("usuaprovacio")));
		informe.setDataAprovacio(rs.getTimestamp("dataaprovacio"));
		informe.setNotes(rs.getString("notes"));
		informe.setLlistaOfertes(OfertaCore.findOfertes(conn, rs.getString("idinf")));
		informe.setOfertaSeleccionada(OfertaCore.findOfertaSeleccionada(conn, rs.getString("idinf")));
		if (OfertaCore.findOfertaSeleccionada(conn, rs.getString("idinf")) != null) {
			informe.setLlistaFactures(FacturaCore.getFacturesInforme(conn, rs.getString("idinf")));
		}
		return informe;
	}
	
	public static String nouInforme(Connection conn, InformeActuacio informe, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.tbl_informeactuacio(idinf, idtasca, idincidencia, idactuacio, objecte, tipusobra, llicencia, tipusllicencia, contracte, vec, iva, plic, termini, comentari, usucre, datacre)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, localtimestamp);";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);	
		String idNouInforme = idNouInforme(conn);
		pstm.setString(1, idNouInforme);
		pstm.setInt(2, informe.getIdTasca());
		pstm.setString(3, informe.getIdIncidencia());
		pstm.setString(4, informe.getIdActuacio());
		pstm.setString(5, informe.getObjecte());
		pstm.setString(6, informe.getTipusObra());
		pstm.setBoolean(7, informe.isLlicencia());
		pstm.setString(8, informe.getTipusLlicencia());
		pstm.setBoolean(9, informe.isContracte());
		pstm.setDouble(10, informe.getVec());
		pstm.setDouble(11, informe.getIva());
		pstm.setDouble(12, informe.getPlic());
		pstm.setString(13, informe.getTermini());
		pstm.setString(14, informe.getComentari());
		pstm.setInt(15, idUsuari);
		pstm.executeUpdate();
		
		return idNouInforme;
	}
	
	public static void modificar(Connection conn, InformeActuacio informe, int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
					+ " SET objecte=?, tipusobra=?, llicencia=?, tipusllicencia=?, contracte=?, vec=?, iva=?, plic=?, termini=?, comentari=?, usucre=?, usuaprovacio=?, dataaprovacio=?"
					+ " WHERE idInf=?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, informe.getObjecte());
		pstm.setString(2, informe.getTipusObra());
		pstm.setBoolean(3, informe.isLlicencia());
		pstm.setString(4, informe.getTipusLlicencia());
		pstm.setBoolean(5, informe.isContracte());
		pstm.setDouble(6, informe.getVec());
		pstm.setDouble(7, informe.getIva());
		pstm.setDouble(8, informe.getPlic());
		pstm.setString(9, informe.getTermini());
		pstm.setString(10, informe.getComentari());
		pstm.setInt(11, idUsuari);
		pstm.setNull(12, java.sql.Types.INTEGER);
		pstm.setDate(13, null);
		pstm.setString(14, informe.getIdInf());
		pstm.executeUpdate();
	}
	
	public static InformeActuacio getInformePrevi(Connection conn, String idInforme) throws SQLException {
		InformeActuacio informePrevi = new InformeActuacio();
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, objecte, tipusobra, llicencia, tipusllicencia, contracte, vec, iva,"
					+ " plic, termini, comentari, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes"
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idinf = ?";		 
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idInforme);	
		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			informePrevi = initInforme(conn, rs);
		}
		return informePrevi;
	}
	
	public static InformeActuacio getInformeTasca(Connection conn, int idTasca) throws SQLException {
		InformeActuacio informe = new InformeActuacio();
		String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, objecte, tipusobra, llicencia, tipusllicencia, contracte, vec, iva,"
					+ " plic, termini, comentari, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes" 
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idtasca = ?"
					+ " ORDER BY idinf DESC LIMIT 1;";		 
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idTasca);	
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			informe = initInforme(conn, rs);			
		}
		return informe;
	}
	
	public static List<InformeActuacio> getInformesActuacio(Connection conn, String idActuacio) throws SQLException {
		 List<InformeActuacio> informes = new ArrayList<InformeActuacio>();
		 String sql = "SELECT idinf, idtasca, idincidencia, idactuacio, objecte, tipusobra, llicencia, tipusllicencia, contracte, vec, iva,"
				 	+ " plic, termini, comentari, usucre, datacre, usucapvalidacio, datacapvalidacio, comentaricap, usuaprovacio, dataaprovacio, notes" 
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idactuacio = ?"
					+ " ORDER BY idinf DESC;";		 
		
		 PreparedStatement pstm = conn.prepareStatement(sql);
		 pstm.setString(1, idActuacio);	
		 ResultSet rs = pstm.executeQuery();
		 while (rs.next()) {
			 informes.add(initInforme(conn, rs));			
		 }
		
		 return informes;
	}
	
	public static void validacioCapInforme(Connection conn, String idInf, int idUsuari, String comentariCap) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
					+ " SET usucapvalidacio=?, datacapvalidacio=localtimestamp, comentaricap=?"
					+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);	
		pstm.setString(2, comentariCap);	
		pstm.setString(3, idInf);
		pstm.executeUpdate();
	}
	
	public static void aprovacioInforme(Connection conn, String idInf, int idUsuari) throws SQLException {
		String sql = "UPDATE public.tbl_informeactuacio"
					+ " SET usuaprovacio=?, dataaprovacio=localtimestamp"
					+ " WHERE idinf=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idUsuari);	
		pstm.setString(2, idInf);
		pstm.executeUpdate();
	}
	
	private static String idNouInforme(Connection conn) throws SQLException{
		String newCode = "1";
		
		String sql = "SELECT idinf, datacre"
					+ " FROM public.tbl_informeactuacio"
					+ " WHERE idinf like '%PD%'"
					+ " ORDER BY datacre DESC, idinf DESC LIMIT 1;";	 
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		String prefix = "PD";
		if (rs.next()) { //Codis nous
			String actualCode = rs.getString("idinf");			
			int num = Integer.valueOf(actualCode.split("-")[2]);
			String numFormatted = String.format("%04d", num + 1);
			newCode = yearInString + "-" + prefix + "-" + numFormatted;
		}
		else {
			int num = 0;		
			String numFormatted = String.format("%04d", num + 1);
			newCode = yearInString + "-" + prefix + "-" + numFormatted;
		}
		return newCode;	
	}
}
