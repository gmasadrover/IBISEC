package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Configuracio;

public class ConfiguracioCore {
	
	static final String SQL_CAMPS = "importobramajor, importserveimajor, importsubministrament, rutabasedocumentacio, usuarirecercapresuposts, usuarifactures, usuaricertificacions, usuariordreinici,"
								+ " usuariredacciocontracte, usuariactualitzarempresa, usuarillicencies, usuaridron";
	
	public static Configuracio getConfiguracio(Connection conn) {
		Configuracio configuracioActual = new Configuracio();
		String sql = "SELECT " + SQL_CAMPS
				+ " FROM public.tbl_configuracio"; 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				configuracioActual.setImportObraMajor(rs.getDouble("importobramajor"));
				configuracioActual.setImportServeiMajor(rs.getDouble("importserveimajor"));
				configuracioActual.setImportSubministramentMajor(rs.getDouble("importsubministrament"));
				configuracioActual.setRutaBaseDocumentacio(rs.getString("rutabasedocumentacio"));
				configuracioActual.setIdUsuariRecercaPresuposts(rs.getInt("usuarirecercapresuposts"));
				configuracioActual.setIdUsuariFactures(rs.getInt("usuarifactures"));
				configuracioActual.setIdUsuariCertificacions(rs.getInt("usuaricertificacions"));
				configuracioActual.setIdUsuariOrdreInici(rs.getInt("usuariordreinici"));
				configuracioActual.setIdUsuariRedaccioContracte(rs.getInt("usuariredacciocontracte"));
				configuracioActual.setIdUsuariActualitzarEmpresa(rs.getInt("usuariactualitzarempresa"));
				configuracioActual.setIdUsuariLlicencies(rs.getInt("usuarillicencies"));
				configuracioActual.setIdUsuariDron(rs.getInt("usuaridron"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return configuracioActual;
	}
	
	public static void updateConfiguracio(Connection conn, Configuracio novaConfiguracio) {
		String sql = "UPDATE public.tbl_configuracio"
				+ " SET importobramajor=?, importserveimajor=?, importsubministrament=?, rutabasedocumentacio=?, usuarirecercapresuposts=?, usuarifactures=?, usuaricertificacions=?, usuariordreinici=?,"
				+ " 	usuariredacciocontracte=?, usuariactualitzarempresa=?, usuarillicencies=?, usuaridron=?, "
				+ "		datamodificacio=localtimestamp";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setDouble(1, novaConfiguracio.getImportObraMajor());
			pstm.setDouble(2, novaConfiguracio.getImportServeiMajor());
			pstm.setDouble(3, novaConfiguracio.getImportSubministramentMajor());
			pstm.setString(4, novaConfiguracio.getRutaBaseDocumentacio());
			pstm.setInt(5, novaConfiguracio.getIdUsuariRecercaPresuposts());
			pstm.setInt(6, novaConfiguracio.getIdUsuariFactures());
			pstm.setInt(7, novaConfiguracio.getIdUsuariCertificacions());
			pstm.setInt(8, novaConfiguracio.getIdUsuariOrdreInici());
			pstm.setInt(9, novaConfiguracio.getIdUsuariRedaccioContracte());
			pstm.setInt(10, novaConfiguracio.getIdUsuariActualitzarEmpresa());
			pstm.setInt(11, novaConfiguracio.getIdUsuariLlicencies());
			pstm.setInt(12, novaConfiguracio.getIdUsuariDron());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
}
