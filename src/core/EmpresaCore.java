package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Empresa;

public class EmpresaCore {
	
	static final String SQL_CAMPS = "\"CIF\", nom, direccio, \"CP\", ciutat, provincia, telefon, email, fax, \"usuMod\", \"dataMod\"";
	
	
	public static void deleteEmpresa(Connection conn, String codi) throws SQLException {
		String sql = "Delete FROM public.\"tbl_Empreses\" where \"CIF\"= ?";		 
		PreparedStatement pstm = conn.prepareStatement(sql); 
		pstm.setString(1, codi);
		pstm.executeUpdate();
	}
	
	public static void insertEmpresa(Connection conn, Empresa empresa, int idUsuari) throws SQLException {
		String sql = "INSERT INTO public.\"tbl_Empreses\"(" + SQL_CAMPS + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, localtimestamp)";		 
		PreparedStatement pstm = conn.prepareStatement(sql);	 
		pstm.setString(1, empresa.getCif());
		pstm.setString(2, empresa.getName());
		pstm.setString(3, empresa.getDireccio());
		pstm.setString(4, empresa.getCP());
		pstm.setString(5, empresa.getCiutat());
		pstm.setString(6, empresa.getProvincia());
		pstm.setString(7, empresa.getTelefon());
		pstm.setString(8, empresa.getEmail());
		pstm.setString(9, empresa.getFax());
		pstm.setInt(10, idUsuari);
		pstm.executeUpdate();
	}
	
	 public static void updateEmpresa(Connection conn, Empresa empresa, int idUsuari) throws SQLException {
		 String sql = "UPDATE public.\"tbl_Empreses\" "
				 	+ "SET nom=?, direccio=?, \"CP\"=?, ciutat=?, provincia=?, telefon=?, fax=?, email=?, \"usuMod\"=?, \"dataMod\"=localtimestamp "
				 	+ "WHERE \"CIF\" =?";	 		 
		 PreparedStatement pstm = conn.prepareStatement(sql);	 
		 pstm.setString(1, empresa.getName());
		 pstm.setString(2, empresa.getDireccio());
		 pstm.setString(3, empresa.getCP());
		 pstm.setString(4, empresa.getCiutat());
		 pstm.setString(5, empresa.getProvincia());
		 pstm.setString(6, empresa.getTelefon());
		 pstm.setString(7, empresa.getFax());
		 pstm.setString(8, empresa.getEmail());
		 pstm.setInt(9, idUsuari);
		 pstm.setString(10, empresa.getCif());
		 pstm.executeUpdate();
	  }
	 
	 public static Empresa findEmpresa(Connection conn, String cif) throws SQLException {
		 String sql = "SELECT " + SQL_CAMPS + " from public.\"tbl_Empreses\" where \"CIF\"=?";
	 
		 PreparedStatement pstm = conn.prepareStatement(sql);
	     pstm.setString(1, cif);
	 
	     ResultSet rs = pstm.executeQuery();
	 
	     while (rs.next()) {
	    	 String name = rs.getString("nom");
	    	 String direccio = rs.getString("direccio");
	    	 String cp = rs.getString("cp");
	    	 String ciutat = rs.getString("ciutat");
	    	 String provincia = rs.getString("provincia");
	    	 String telefon = rs.getString("telefon");
	    	 String fax = rs.getString("fax");
	    	 String email = rs.getString("email");
	    	 Empresa product = new Empresa(cif, name, direccio, cp, ciutat, provincia, telefon, fax, email);
	    	 return product;
	     }
	     return null;
	 }
	 public static List<Empresa> getEmpreses(Connection conn) throws SQLException {
		 String sql = "SELECT " + SQL_CAMPS + " from public.\"tbl_Empreses\" ";	 
		 PreparedStatement pstm = conn.prepareStatement(sql);	 
		 ResultSet rs = pstm.executeQuery();
		 List<Empresa> list = new ArrayList<Empresa>();
		 while (rs.next()) {
			 Empresa empresa = new Empresa();
			 empresa.setCif(rs.getString("CIF"));
			 empresa.setName(rs.getString("nom"));
			 empresa.setDireccio(rs.getString("direccio"));
			 empresa.setCP(rs.getString("CP"));
			 empresa.setCiutat(rs.getString("ciutat"));
			 empresa.setProvincia(rs.getString("provincia"));
			 empresa.setTelefon(rs.getString("telefon"));
			 empresa.setFax(rs.getString("fax"));
			 empresa.setEmail(rs.getString("email"));
			 list.add(empresa);
		 }
	     return list;
     }	
}
