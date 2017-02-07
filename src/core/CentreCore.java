package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CentreCore {
	public static String nomCentre(Connection conn, String idCentre) throws SQLException {
		 
		String sql = "SELECT nom from public.tbl_centres"
					+ " WHERE codi = ?";
		String nomCentre = "";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idCentre);		
		ResultSet rs = pstm.executeQuery();	 
		if (rs.next()) {
			nomCentre = rs.getString("nom");
		}
		return nomCentre;
	}
}
