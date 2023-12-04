<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%> 
	<%-- Inclusione dell'HEAD --%>
	<r:include url="/ris/servizi/siac/include/headAccount.html" resourceProvider="rp"/>

<style>
<!--
div#portalHeader_title a { 
   text-indent: -1000px !important 
}
-->
</style>

</head>
<body>

  <!-- NAVIGAZIONE -->
  <p class="nascosto"><a name="A-sommario" title="A-sommario"></a></p>    
	<ul id="sommario" class="nascosto">
		<li><a href="#A-contenuti">Salta ai contenuti</a></li>
	</ul>
  <!-- /NAVIGAZIONE -->
  <hr />


  
<div class="container-fluid-banner">

<!-- //////////////////////////////////////////////////////////////////////////////// -->
<!-- **********************  inclusione Banner portale   **************************** -->


<r:include url="/ris/servizi/siac/include/portalheader.html" resourceProvider="rp"/>

<!-- nel prototipo statico si chiama navbar.html  -->
<!--  include la combo per selezionare l'anno tra quelli proposti e visualizza le info dell'utente -->

<s:include value="../include/selezioneAnno.jsp" />
<r:include url="/ris/servizi/siac/include/applicationHeader.html" resourceProvider="rp"/>

<!-- **********************  fine inclusione Banner portale   *********************** -->
<!-- //////////////////////////////////////////////////////////////////////////////// -->


<a name="A-contenuti" title="A-contenuti"></a>
</div>

<div class="row-fluid">
  <div class="span12">
    <ul class="breadcrumb">
		<li><!-- <a href="index.shtml">Home</a> --></li>
	</ul> 
  </div>	
</div>

<div class="container-fluid">
  <div class="row-fluid">
    <div class="span12 contentPage">    
		<form class="form-horizontal">
		
				<div class="row-fluid">
				<div id="box-center" class="span10" >
					<div id="box-account">
						<h2><strong>Selezione profilo</strong></h2>
						
						<div class="scroll">
							<p class="well">Prima di procedere e' necessario selezionare un profilo operativo.</p>
							<ul class="lista-account" >
								
								<s:iterator value="accounts">
									<li>
										<a href="<s:url action="home.selezionaAccount" >	
										<s:param name="accountSelezionato" value="id"/></s:url>">
										
										<div class="tlt-Account"><s:property value="descrizione"/> - <s:property value="ente.nome"/></div>
										<div class="arr-Account"></div>
										</a>
									</li>
								</s:iterator>
	
								
							</ul>
						</div>
					</div>
				</div>
			</div>
			<p></p>     
        
		</form>
			
    </div>
		  
  </div>	 
</div>	

<s:include value="../include/footer.jsp" />
<s:include value="../include/javascript.jsp" />

</body>
</html>
