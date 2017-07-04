package core;

import java.sql.Connection;
import java.sql.SQLException;

import bean.ControlPage.SectionPage;
import bean.User;

public class ControlPageCore {
	public static String renderMenu(Connection conn, User usuari, String seccio) {
		StringBuilder menu = new StringBuilder();
		String active = "";
		String collapse = "";
		//Tasques
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.tasques_list)) {			
			if (seccio.equals("Tasques")) active = "active"; 
			menu.append("<li class='" + active + "'>");
			menu.append("	<a href='tascaList'><i class='fa fa-fw fa-tasks'></i> Tasques</a>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}
		//Notificació
		/*if (UsuariCore.hasPermision(conn, usuari, SectionPage.tasques_list)) {			
			if (seccio.equals("Notificacions")) active = "active"; 
			menu.append("<li class='" + active + "'>");
			int notificacions = 0;
			try {
				notificacions = TascaCore.numNotificacions(conn, usuari.getIdUsuari());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			menu.append("	<a href='notificacioList'><i class='fa fa-fw fa-tasks'></i> Notificacions <span class='badge'> " + notificacions + "</span></a>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}*/
		//Registre
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.registre_ent_list) || UsuariCore.hasPermision(conn, usuari, SectionPage.registre_sort_list)) {
			if ( seccio.equals("Registre")) {
				active = "active"; 
				collapse = "in";
			}		
			menu.append("<li class='"+ active + "'>");
			menu.append("	<a href='javascript:;' data-toggle='collapse' data-target='#registres'><i class='fa fa-fw fa-archive'></i> Registre <i class='fa fa-fw fa-caret-down'></i></a>");
			menu.append("	<ul id='registres' class='nav nav-second-level collapse " + collapse + "'>");
			if (UsuariCore.hasPermision(conn, usuari, SectionPage.registre_ent_list)) {
				menu.append("   	<li>");
				menu.append("   	    <a href='registreEntrada'>Entrades</a>");
				menu.append("   	</li>");
			}
			if (UsuariCore.hasPermision(conn, usuari, SectionPage.registre_sort_list)) {
				menu.append("   	<li>");
				menu.append("       	<a href='registreSortida'>Sortides</a>");
				menu.append("   	</li>");
			}
			menu.append("	</ul>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}
		//Incidencies
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.incidencia_list)) {			
			if (seccio.equals("Incidencies")) active = "active"; 
			menu.append("<li class='" + active + "'>");
			menu.append("	<a href='incidencies'><i class='fa fa-fw fa-bookmark-o'></i> Incidències</a>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}
		//Actuacions
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.actuacio_list)) {			
			if (seccio.equals("Actuacions")) active = "active"; 
			menu.append("<li class='" + active + "'>");
			menu.append("	<a href='actuacions'><i class='fa fa-fw fa-bookmark'></i> Actuacions</a>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}
		//Obres
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.obres_list)) {
			if (seccio.equals("Obres")) {
				active = "active"; 
				collapse = "in";
			}
			menu.append("<li class='"+ active + "'>");
			menu.append("	<a href='javascript:;' data-toggle='collapse' data-target='#obresMenu'><i class='fa fa-fw fa-list'></i> Obres<i class='fa fa-fw fa-caret-down'></i></a>");
			menu.append("	<ul id='obresMenu' class='nav nav-second-level collapse " + collapse + "'>");			
			menu.append("		<li>");
			menu.append("       	<a href='obresMenors'>Contractes menors</a>");
			menu.append("    	</li>");		
			menu.append("     	<li>");
			menu.append("        	<a href='obresMajors'>Contractes majors</a>");
			menu.append("    	</li>");
			menu.append("	</ul>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}
		//Serveis
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.obres_list)) {
			if (seccio.equals("Serveis")) {
				active = "active"; 
				collapse = "in";
			}
			menu.append("<li class='"+ active + "'>");
			menu.append("	<a href='javascript:;' data-toggle='collapse' data-target='#serveisMenu'><i class='fa fa-fw fa-list'></i> Serveis<i class='fa fa-fw fa-caret-down'></i></a>");
			menu.append("	<ul id='serveisMenu' class='nav nav-second-level collapse " + collapse + "'>");			
			menu.append("		<li>");
			menu.append("       	<a href='serveisMenors'>Contractes menors</a>");
			menu.append("    	</li>");		
			menu.append("     	<li>");
			menu.append("        	<a href='serveisMajors'>Contractes majors</a>");
			menu.append("    	</li>");
			menu.append("	</ul>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}
		//Subministrament
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.obres_list)) {
			if (seccio.equals("Subministrament")) {
				active = "active"; 
				collapse = "in";
			}
			menu.append("<li class='"+ active + "'>");
			menu.append("	<a href='javascript:;' data-toggle='collapse' data-target='#subministramentMenu'><i class='fa fa-fw fa-list'></i> Subministrament<i class='fa fa-fw fa-caret-down'></i></a>");
			menu.append("	<ul id='subministramentMenu' class='nav nav-second-level collapse " + collapse + "'>");			
			menu.append("		<li>");
			menu.append("       	<a href='subministramentMenors'>Contractes menors</a>");
			menu.append("    	</li>");		
			menu.append("     	<li>");
			menu.append("        	<a href='subministramentMajors'>Contractes majors</a>");
			menu.append("    	</li>");
			menu.append("	</ul>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}
		//Altres BD antiga
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.obres_list)) {
			if (seccio.equals("Altres")) {
				active = "active"; 
				collapse = "in";
			}
			menu.append("<li class='"+ active + "'>");
			menu.append("	<a href='javascript:;' data-toggle='collapse' data-target='#altresMenu'><i class='fa fa-fw fa-list'></i> Altres BD antiga<i class='fa fa-fw fa-caret-down'></i></a>");
			menu.append("	<ul id='altresMenu' class='nav nav-second-level collapse " + collapse + "'>");			
			menu.append("		<li>");
			menu.append("       	<a href='altresMenors'>Contractes menors</a>");
			menu.append("    	</li>");		
			menu.append("     	<li>");
			menu.append("        	<a href='altresMajors'>Contractes majors</a>");
			menu.append("    	</li>");
			menu.append("	</ul>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}
		//Expedients
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.expedient_list)) {			
			if (seccio.equals("expedients")) active = "active"; 
			menu.append("<li class='" + active + "'>");
			menu.append("	<a href='expedients'><i class='fa fa-fw fa-list'></i> Expedients</a>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}
		//Centres
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.centres_list)) {			
			if (seccio.equals("centres")) active = "active"; 
			menu.append("<li class='" + active + "'>");
			menu.append("	<a href='centres'><i class='fa fa-fw fa-university'></i> Centres</a>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}		
		//Empreses
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.empreses_list) || UsuariCore.hasPermision(conn, usuari, SectionPage.empreses_crear)) {
			if (seccio.equals("Empreses")) {
				active = "active"; 
				collapse = "in";
			}
			menu.append("<li class='"+ active + "'>");
			menu.append("	<a href='javascript:;' data-toggle='collapse' data-target='#empresaMenu'><i class='fa fa-fw fa-suitcase'></i> Empreses<i class='fa fa-fw fa-caret-down'></i></a>");
			menu.append("	<ul id='empresaMenu' class='nav nav-second-level collapse " + collapse + "'>");
			if (UsuariCore.hasPermision(conn, usuari, SectionPage.empreses_list)) {
				menu.append("		<li>");
				menu.append("       	<a href='empresaList'>Llista empreses creades</a>");
				menu.append("    	</li>");
			}
			if (UsuariCore.hasPermision(conn, usuari, SectionPage.empreses_crear)) {
				menu.append("     	<li>");
				menu.append("        	<a href='createEmpresa'>Afegir empresa</a>");
				menu.append("    	</li>");
			}
			if (UsuariCore.hasPermision(conn, usuari, SectionPage.empreses_crear)) {
				menu.append("     	<li>");
				menu.append("        	<a href='createUTE'>Afegir UTE</a>");
				menu.append("    	</li>");
			}
			menu.append("	</ul>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}
		//Factures
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.factures_list)) {			
			if (seccio.equals("Factures")) active = "active"; 
			menu.append("<li class='" + active + "'>");
			menu.append("	<a href='factures'><i class='fa fa-fw fa-edit'></i> Factures</a>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}	
		//Partides
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.partides_list) || UsuariCore.hasPermision(conn, usuari, SectionPage.partides_crear)) {
			if (seccio.equals("Partides")) {
				active = "active"; 
				collapse = "in";
			}
			menu.append("<li class='"+ active + "'>");
			menu.append("	<a href='javascript:;' data-toggle='collapse' data-target='#creditMenu'><i class='fa fa-fw fa-calculator'></i> Crèdit / Partides<i class='fa fa-fw fa-caret-down'></i></a>");
			menu.append("	<ul id='creditMenu' class='nav nav-second-level collapse " + collapse + "'>");
			if (UsuariCore.hasPermision(conn, usuari, SectionPage.partides_list)) {
				menu.append("		<li>");
				menu.append("			<a href='despeses'>Consulta despeses</a>");
				menu.append("		</li>");
			}
			if (UsuariCore.hasPermision(conn, usuari, SectionPage.partides_list)) {
				menu.append("		<li>");
				menu.append("			<a href='credit'>Partides creades</a>");
				menu.append("		</li>");
			}
			if (UsuariCore.hasPermision(conn, usuari, SectionPage.partides_crear)) {
				menu.append("		<li>");
				menu.append("			<a href='CreatePartida'>Crear partida</a>");
				menu.append("		</li>");
			}
			menu.append("	</ul>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}
		//Llistats
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.llistats_list)) {			
			if (seccio.equals("Llistats")) active = "active"; 
			menu.append("<li class='" + active + "'>");
			menu.append("	<a href='llistats'><i class='fa fa-fw fa-edit'></i> Mapa</a>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}	
		//Documents
		if (UsuariCore.hasPermision(conn, usuari, SectionPage.manuals)) {			
			if (seccio.equals("Manuals")) active = "active"; 
			menu.append("<li class='" + active + "'>");
			menu.append("	<a href='manuals'><i class='fa fa-fw fa-edit'></i> Manuals</a>");
			menu.append("</li>");
			active = "";
			collapse = "";
		}	
		return menu.toString();
	}
	
}
