package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ControlInfo;

public class ControlCore {	
	public static List<ControlInfo> estatCentres(Connection conn) {
		List<ControlInfo> controlCentresList = new ArrayList<ControlInfo>();
		String sql = "SELECT DISTINCT a.idcentre AS idcentre, a.id AS id"
					+ " FROM public.tbl_tasques t LEFT JOIN public.tbl_actuacio a ON t.idactuacio = a.id"
					+ " WHERE t.activa = true AND t.tipus <> 'notificacio';";
 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			ControlInfo info;
			while (rs.next()) {
				if (isCentreValid(rs.getString("idcentre"))) {
					info = new ControlInfo();
					info.setCentre(CentreCore.findCentre(conn, rs.getString("idcentre"), false));
					info.setTasquesList(TascaCore.findTasquesActuacio(conn, rs.getString("id"), false));
					controlCentresList.add(info);
				}
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	
		return controlCentresList;		
	}
	
	private static boolean isCentreValid(String idCentre) {
		boolean isValid = true;
		if (idCentre == null || idCentre.equals("") || idCentre.equals("07000000")
				|| idCentre.equals("07000001")
				|| idCentre.equals("07000002")
				|| idCentre.equals("99999999")
				|| idCentre.equals("9999PERSO")
				|| idCentre.equals("99000521")) isValid = false;
		return isValid;
	}
}
