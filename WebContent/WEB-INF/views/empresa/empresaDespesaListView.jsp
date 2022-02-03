<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="m"  %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<m:setLocale value="${language}" />
<m:setBundle basename="i18n.base"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
	<jsp:include page="../_header.jsp"></jsp:include>	
</head>	
<body>	
    <div id="wrapper">
    	<jsp:include page="../_menu.jsp"></jsp:include>
       	<div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            Empreses Despesa <small>Llistat</small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Empreses Despesa
                            </li>
                            <li class="active">
                                <i class="fa fa-table"></i> Llistat
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->


                <div class="row">
                    <div class="col-md-12">
                        <h2>Empreses Despesa</h2>
                        <div class="table-responsive">                        
                            <table class="table table-striped table-bordered despesaTable">
                                <thead>
                                    <tr>
                                        <th>CIF</th>
                                        <th>Nom</th>
                                        <th>Direcció</th>
                                        <th>CP</th>
                                        <th>Ciutat</th>
                                        <th>Provincia</th>
                                        <!-- <th>Email</th> --> <!--Ocult a petició M.Garcia en data 10/11/21--> 
                                        <th>Total base (adjudicat)</th>
                                        <th>Total base (adjudicat)</th>
                                        <th>Total base (proposta)</th>
                                        <th>Total base (proposta)</th>
                                        <th>Tatal amb IVA (proposta)</th>
                                        <th>Tatal amb IVA (proposta)</th>
                                        <th>Tipus</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach items="${empresesList}" var="empresa" >
							          	<tr class="${!empresa.activa? 'danger' : ''}">							          	
							           		<td><a href="empresa?cif=${empresa.cif}" class="loadingButton"  data-msg="obrint empresa...">${empresa.cif}</a></td>
							            	<td>${empresa.name}</td>
							            	<td>${empresa.direccio}</td>
							            	<td>${empresa.getCP()}</td>
							            	<td>${empresa.ciutat}</td>
							            	<td>${empresa.provincia}</td>
							            	<!-- <td>${empresa.email}</td>--> <!--Ocult a petició M.Garcia en data 10/11/21--> 
							            	<td>${empresa.getTotalPbasePeriodeAdjudicatString()}</td>		
							            	<td>${empresa.totalPbasePeriodeAdjudicat}</td>	
							            	<td>${empresa.getTotalPbasePeriodeString()}</td>		
							            	<td>${empresa.totalPbasePeriode}</td>	
							            	<td>${empresa.getTotalPLicPeriodeString()}</td>									            				
							            	<td>${empresa.totalPLicPeriode}</td>	
							            	<td>${empresa.tipus}</td>							            					            	
							          	</tr>
							       	</c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
                    </div>
                </div>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <jsp:include page="../_footer.jsp"></jsp:include>
    <script src="js/empresa/llistat.js?<%=application.getInitParameter("datakey")%>"></script>
    <!-- /#wrapper -->
</body>
</html>