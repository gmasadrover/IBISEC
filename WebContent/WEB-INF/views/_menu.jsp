<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="tascaList"><img  class="logo" src="favicon.png" /><span class="tittleLogo"><%=application.getInitParameter("pageName")%></span></a>
    </div>
    <!-- Top Menu Items -->
    <ul class="nav navbar-top-links navbar-right">        
        <li class="dropdown">        	
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i>${user.getNomComplet()}<b class="caret"></b></a>
            <ul class="dropdown-menu">
                 <li>
                    <a href="UsuariDetails?id=${user.idUsuari}"><i class="fa fa-fw fa-user"></i> Perfil</a>
                </li>               
                <li class="divider"></li>
                <li>
                    <a href="logout"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                </li>
            </ul>           
        </li>
    </ul>
    <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
    <div class="navbar-default sidebar" role="navigation">
    	<div class="sidebar-nav navbar-collapse">
        	<ul class="nav" id="side-menu">
        		${menu}
        	</ul>
        </div>
    </div>
    <!-- /.navbar-collapse -->
</nav>