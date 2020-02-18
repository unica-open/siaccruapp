<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%> 
<!-- //////////////////////////////////////////////////////////////////////////////// -->
<!-- **************************   inclusione head   ******************************* -->		

<r:include url="/ris/servizi/siac/include/headDashboard.html" resourceProvider="rp"/>


<!-- ************************  fine inclusione head   ***************************** -->
<!-- //////////////////////////////////////////////////////////////////////////////// -->

</head>
<body>

<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
<!-- *********************   Non so se serve sta roba era già  presente su tutte le pagine  ********************** -->	

  <!-- NAVIGAZIONE -->
  <p class="nascosto"><a name="A-sommario" title="A-sommario"></a></p>    
	<ul id="sommario" class="nascosto">
		<li><a href="#A-contenuti">Salta ai contenuti</a></li>
	</ul>
  <!-- /NAVIGAZIONE -->
  <hr />

<!-- *****************  fine della roba che secondo me non serve (già  presente su tutte le pagine)  ************* -->	
<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////// -->

  
<div class="container-fluid-banner">

<!-- //////////////////////////////////////////////////////////////////////////////// -->
<!-- **********************  inclusione Banner portale   **************************** -->

<r:include url="/ris/servizi/siac/include/portalheader.html" resourceProvider="rp"/>

<!-- nel prototipo statico si chiama navbar.html  -->
<!--  include la combo per selezionare l'anno tra quelli proposti e visualizza le info dell'utente -->

<div class="navbarLogin">
	<div class="container-fluid">
		<s:include value="../include/selezioneAnno.jsp" />
	  	
		<s:include value="../include/enteAccountOperatoreLogout.jsp" />
	</div>
</div>

<r:include url="/ris/servizi/siac/include/applicationHeader.html" resourceProvider="rp"/>

<!-- **********************  fine inclusione Banner portale   *********************** -->
<!-- //////////////////////////////////////////////////////////////////////////////// -->


<a name="A-contenuti" title="A-contenuti"></a>
</div>
<div class="row-fluid">
  <div class="span12">
    <ul class="breadcrumb">
		<li><!-- <a href="../index.shtml">Home</a> -->
			<s:if test="%{sessionHandler.account!=null}">
					<!-- <a>Home</a> -->
					<a href="<s:url action="home.backSelezionaAccount" ></s:url>">Home</a>
			</s:if>	
		</li>
    </ul> 
  </div>	
</div>