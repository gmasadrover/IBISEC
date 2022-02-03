package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Configuracio;

public class ConfiguracioCore {
	
	static final String SQL_CAMPS = "importobramajor, importserveimajor, importsubministrament, rutabasedocumentacio";
	
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return configuracioActual;
	}
	
	public static void updateConfiguracio(Connection conn, Configuracio novaConfiguracio) {
		String sql = "UPDATE public.tbl_configuracio"
				+ " SET importobramajor=?, importserveimajor=?, importsubministrament=?, rutabasedocumentacio=? , datamodificacio=localtimestamp";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm = conn.prepareStatement(sql);
			pstm.setDouble(1, novaConfiguracio.getImportObraMajor());
			pstm.setDouble(2, novaConfiguracio.getImportServeiMajor());
			pstm.setDouble(3, novaConfiguracio.getImportSubministramentMajor());
			pstm.setString(4, novaConfiguracio.getRutaBaseDocumentacio());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
	}
}
