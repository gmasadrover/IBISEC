package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.Incidencia;

public class IncidenciaCore {
	
	private static Incidencia initIncidencia(Connection conn, ResultSet rs) {
		Incidencia incidencia = new Incidencia();
		try {
			incidencia.setIdIncidencia(rs.getString("idincidencia"));
			incidencia.setDescripcio(rs.getString("descripcio"));
			incidencia.setUsuCre(UsuariCore.findUsuariByID(conn, rs.getInt("usucre")));
			incidencia.setUsuMod(rs.getDate("datacre"));
			incidencia.setActiva(rs.getBoolean("activa"));
			incidencia.setDataTancament(rs.getDate("datatancament"));
			incidencia.setMotiuTancament(rs.getString("motiutancament"));
			incidencia.setIgnorada(rs.getBoolean("ignorada"));
			incidencia.setIdCentre(rs.getString("idcentre"));
			incidencia.setNomCentre(CentreCore.nomCentre(conn, rs.getString("idcentre")));
			incidencia.setSolicitant(rs.getString("solicitant"));
			incidencia.setLlistaActuacions(ActuacioCore.searchActuacionsInciencia(conn, rs.getString("idincidencia")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return incidencia;
	}
	
	public static void novaIncidencia(Connection conn, Incidencia incidencia) {
		String sql = "INSERT INTO public.tbl_incidencia(idincidencia, descripcio, usucre, datacre, activa, idcentre, solicitant, ignorada)"
					+ " VALUES (?,?,?,localtimestamp,true,?,?, false)";
	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, incidencia.getIdIncidencia());		
			pstm.setString(2, incidencia.getDescripcio());
			pstm.setInt(3, incidencia.getUsuCre().getIdUsuari());
			pstm.setString(4, incidencia.getIdCentre());
			pstm.setString(5, incidencia.getSolicitant());
		 
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		
	}
	
	public static Incidencia findIncidencia(Connection conn, String idIncidencia) {
		Incidencia incidencia = new Incidencia();
		String sql = "SELECT idincidencia, descripcio, usucre, datacre, activa, datatancament, idcentre, solicitant, ignorada, motiutancament" 
					+ " FROM public.tbl_incidencia"
					+ " WHERE idincidencia = ?";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, idIncidencia);			
			
			ResultSet rs = pstm.executeQuery();
			
			if (rs.next()) {
				incidencia = initIncidencia(conn, rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return incidencia;
	}
	
	public static List<Incidencia> searchIncidencies(Connection conn, String idCentre, boolean onlyActives, boolean withIgnorades, Date dataIni, Date dataFi) {
		String sql = "SELECT idincidencia, descripcio, usucre, datacre, activa, datatancament, idcentre, solicitant, ignorada, motiutancament" 
					+ " FROM public.tbl_incidencia";
					
		PreparedStatement pstm = null;	
		if (dataIni != null && dataFi != null) {
			sql += " WHERE datacre >= ? and datacre <= ? ";
			if (idCentre != "") {
				sql += " AND idcentre = ?";
				if (onlyActives) { sql+= " AND activa = true"; }
				if (! withIgnorades) { sql+= " AND ignorada = false"; }
				sql += " ORDER BY datacre DESC, idincidencia DESC";
				try {
					pstm = conn.prepareStatement(sql);
					pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
					Calendar fi = Calendar.getInstance();
				    fi.setTime(dataFi);
				    fi.add(Calendar.DATE, 1);	
				    dataFi = fi.getTime();
					pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
					pstm.setString(3, idCentre);	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			} else {
				if (onlyActives) { sql+= " AND activa = true"; }
				if (! withIgnorades) { sql+= " AND ignorada = false"; }
				sql += " ORDER BY datacre DESC, idincidencia DESC";
				try {
					pstm = conn.prepareStatement(sql);
					pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
					Calendar fi = Calendar.getInstance();
				    fi.setTime(dataFi);
				    fi.add(Calendar.DATE, 1);	
				    dataFi = fi.getTime();
					pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		} else {
			if (idCentre != "") {
				sql += " WHERE idcentre = ?";
				if (onlyActives) { sql+= " AND activa = true"; }
				if (! withIgnorades) { sql+= " AND ignorada = false"; }
				sql += " ORDER BY datacre DESC, idincidencia DESC";
				try {
					pstm = conn.prepareStatement(sql);
					pstm.setString(1, idCentre);	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			} else {
				if (onlyActives) { 
					sql+= " WHERE activa = true"; 
					if (! withIgnorades) { sql+= " AND ignorada = false"; }
				} else if (! withIgnorades) { sql+= " WHERE ignorada = false"; }
				sql += " ORDER BY datacre DESC, idincidencia DESC";
				try {
					pstm = conn.prepareStatement(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		ResultSet rs;
		List<Incidencia> list = new ArrayList<Incidencia>();
		try {
			rs = pstm.executeQuery();
			while (rs.next()) {
				Incidencia incidencia = initIncidencia(conn, rs);			
				list.add(incidencia);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	public static List<Incidencia> searchIncidenciesIgnorades(Connection conn, String idCentre, Date dataIni, Date dataFi) {
		List<Incidencia> list = new ArrayList<Incidencia>();
		String sql = "SELECT idincidencia, descripcio, usucre, datacre, activa, datatancament, idcentre, solicitant, ignorada, motiutancament" 
				+ " FROM public.tbl_incidencia i";				
		PreparedStatement pstm = null;	
		if (dataIni != null && dataFi != null) {
			sql += " WHERE i.ignorada = true AND i.datacre >= ? AND i.datacre <= ? ";
			if (idCentre != "") {
				sql += " AND i.idcentre = ?";				
				sql += " ORDER BY i.datacre DESC, i.idincidencia DESC";
				try {
					pstm = conn.prepareStatement(sql);
					pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
					Calendar fi = Calendar.getInstance();
				    fi.setTime(dataFi);
				    fi.add(Calendar.DATE, 1);	
				    dataFi = fi.getTime();
					pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
					pstm.setString(3, idCentre);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			} else {				
				sql += " ORDER BY i.datacre DESC, i.idincidencia DESC";
				try {
					pstm = conn.prepareStatement(sql);
					pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
					Calendar fi = Calendar.getInstance();
				    fi.setTime(dataFi);
				    fi.add(Calendar.DATE, 1);	
				    dataFi = fi.getTime();
					pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} else {
			if (idCentre != "") {
				sql += " WHERE i.ignorada = true AND i.idcentre = ?";				
				sql += " ORDER BY i.datacre DESC, i.idincidencia DESC";
				try {
					pstm = conn.prepareStatement(sql);
					pstm.setString(1, idCentre);	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
			} else {
				sql += " WHERE i.ignorada = true";			
				sql += " ORDER BY i.datacre DESC, i.idincidencia DESC";
				try {
					pstm = conn.prepareStatement(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		ResultSet rs;
		try {
			rs = pstm.executeQuery();
		
			while (rs.next()) {
				Incidencia incidencia = initIncidencia(conn, rs);			
				list.add(incidencia);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static List<Incidencia> searchIncidenciesWithActuacio(Connection conn, String idCentre, boolean onlyActives, Date dataIni, Date dataFi, boolean withActuacio) {
		List<Incidencia> list = new ArrayList<Incidencia>();
		String sql = "SELECT idincidencia, descripcio, usucre, datacre, activa, datatancament, idcentre, solicitant, ignorada, motiutancament" 
					+ " FROM public.tbl_incidencia i";
					
		PreparedStatement pstm = null;	
		if (dataIni != null && dataFi != null) {
			sql += " WHERE i.ignorada = false AND i.datacre >= ? AND i.datacre <= ? ";
			if (idCentre != "") {
				sql += " AND i.idcentre = ?";
				if (onlyActives) { sql+= " AND i.activa = true"; }
				if (withActuacio) {
					sql += " AND EXISTS(SELECT id FROM public.tbl_actuacio a WHERE i.idincidencia = a.idincidencia)";
				}
				else {
					sql += " AND NOT EXISTS(SELECT id FROM public.tbl_actuacio a WHERE i.idincidencia = a.idincidencia)";
				}
				sql += " ORDER BY i.datacre DESC, i.idincidencia DESC";
				try {
					pstm = conn.prepareStatement(sql);
					pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
					Calendar fi = Calendar.getInstance();
				    fi.setTime(dataFi);
				    fi.add(Calendar.DATE, 1);	
				    dataFi = fi.getTime();
					pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
					pstm.setString(3, idCentre);	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				if (onlyActives) { sql+= " AND i.activa = true"; }
				if (withActuacio) {
					sql += " AND EXISTS(SELECT id FROM public.tbl_actuacio a WHERE i.idincidencia = a.idincidencia)";
				}
				else {
					sql += " AND NOT EXISTS(SELECT id FROM public.tbl_actuacio a WHERE i.idincidencia = a.idincidencia)";
				}
				sql += " ORDER BY i.datacre DESC, i.idincidencia DESC";
				try {
					pstm = conn.prepareStatement(sql);
					pstm.setDate(1, new java.sql.Date(dataIni.getTime()));
					Calendar fi = Calendar.getInstance();
				    fi.setTime(dataFi);
				    fi.add(Calendar.DATE, 1);	
				    dataFi = fi.getTime();
					pstm.setDate(2, new java.sql.Date(dataFi.getTime()));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} else {
			if (idCentre != "") {
				sql += " WHERE i.ignorada = false AND i.idcentre = ?";
				if (onlyActives) { sql+= " AND i.activa = true"; }
				if (withActuacio) {
					sql += " AND EXISTS(SELECT id FROM public.tbl_actuacio a WHERE i.idincidencia = a.idincidencia)";
				}
				else {
					sql += " AND NOT EXISTS(SELECT id FROM public.tbl_actuacio a WHERE i.idincidencia = a.idincidencia)";
				}
				sql += " ORDER BY i.datacre DESC, i.idincidencia DESC";
				try {
					pstm = conn.prepareStatement(sql);
					pstm.setString(1, idCentre);	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
			} else {
				if (onlyActives) { 
					sql+= " WHERE i.ignorada = false AND i.activa = true"; 
					if (withActuacio) {
						sql += " AND EXISTS(SELECT id FROM public.tbl_actuacio a WHERE i.idincidencia = a.idincidencia)";
					}
					else {
						sql += " AND NOT EXISTS(SELECT id FROM public.tbl_actuacio a WHERE i.idincidencia = a.idincidencia)";
					}
				} else {
					if (withActuacio) {
						sql += " WHERE i.ignorada = false AND EXISTS(SELECT id FROM public.tbl_actuacio a WHERE i.idincidencia = a.idincidencia)";
					}
					else {
						sql += " WHERE i.ignorada = false AND NOT EXISTS(SELECT id FROM public.tbl_actuacio a WHERE i.idincidencia = a.idincidencia)";
					}
				}
				
				sql += " ORDER BY i.datacre DESC, i.idincidencia DESC";
				try {
					pstm = conn.prepareStatement(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		ResultSet rs;
		try {
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				Incidencia incidencia = initIncidencia(conn, rs);			
				list.add(incidencia);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static void tancar(Connection conn, String referencia, String motiu,  int idUsuari) {
		String sql = "UPDATE public.tbl_incidencia"
					+ " SET datatancament=localtimestamp, motiutancament=?, activa=false"
					+ " WHERE idincidencia=?;";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, motiu);
			pstm.setString(2, referencia);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 		
	}
	
	public static void ignorar(Connection conn, String referencia) {
		String sql = "UPDATE public.tbl_incidencia"
					+ " SET ignorada=true"
					+ " WHERE idincidencia=?;";
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
	
	public static void recuperar(Connection conn, String referencia) {
		String sql = "UPDATE public.tbl_incidencia"
					+ " SET ignorada=false"
					+ " WHERE idincidencia=?;";
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
	
	public static String getNewCode(Connection conn) {
		String newCode = "1";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);
		
		String sql = "SELECT idincidencia, datacre"
					+ " FROM public.tbl_incidencia"
					+ " WHERE idincidencia like '%" + yearInString + "-INC%'"
					+ " ORDER BY datacre DESC, idincidencia DESC LIMIT 1;";	 
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			
			String prefix = "INC";
			if (rs.next()) { //Codis nous
				String actualCode = rs.getString("idincidencia");			
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
}
