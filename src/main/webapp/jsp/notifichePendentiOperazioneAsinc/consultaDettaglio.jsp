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
<s:include value="../include/infoUtenteLogin.jsp" />

<r:include url="/ris/servizi/siac/include/applicationHeader.html" resourceProvider="rp"/>

<!-- **********************  fine inclusione Banner portale   *********************** -->
<!-- //////////////////////////////////////////////////////////////////////////////// -->

<a name="A-contenuti" title="A-contenuti"></a>
</div>

<div class="row-fluid">
  <div class="span12">
    <ul class="breadcrumb">
      <li><a href="../siaccruapp/home.do">Home</a> <span class="divider">></span></li>
      <%-- <li><a href="../index.shtml">XXXX</a> <span class="divider">></span></li> --%>
      <li class="active">Elenco dettaglio operazione asincrona <s:property value="titolo"/> </li>
    </ul> 
  </div>	
</div>


<div class="container-fluid">
  <div class="row-fluid">
    <div class="span12 contentPage">    
    
<s:form action="xxxxxx" method="post">

		<s:if test="hasActionMessages()">
						<%-- Messaggio di WARNING --%>
						<div class="alert alert-warning">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Attenzione!!</strong><br>
							<ul>
								<s:iterator value="messaggi">
									<li><s:property value="codice"/> - <s:property value="descrizione"/> </li>
								</s:iterator>
							</ul>
						</div>
					</s:if>


			<%-- <s:if test="hasActionErrors()"> --%>
			<s:if test="errori.size > 0">
						<%-- Messaggio di ERROR --%>
						<div class="alert alert-error">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Attenzione!!</strong><br>
							<ul>
								<s:iterator value="errori">
									<li><s:property value="testo"/> </li>
								</s:iterator>
							</ul>
						</div>
			</s:if>


		
		<h3>Elenco dettaglio operazione asincrona <s:property value="titolo"/></h3>   
		<fieldset class="form-horizontal">

		<h4 class="step-pane">Elenco</h4>
		<h4><span class="num_result"><s:property value="listaPaginata.totaleElementi"/></span> Risultati trovati</h4>                 
		
		<s:if test="listaPaginata.totaleElementi > 0 ">
			<table class="table table-hover tab_left"  summary="...." >
				<thead>
					<tr>
						<th class="span6">Descrizione</th>
						<th>Codice</th>
						<th>Messaggio</th>
					</tr>
				</thead>
				<tbody>
				
				
				<s:iterator value="listaPaginata" status="st">

	
					<tr>
						<td ><s:property value="descrizione" /></td>
						<td ><s:property value="codice" /></td>
						<td ><s:property value="messaggio" /></td>
						<%-- <td ><s:property value="esito" /></td> --%>
					</tr>
	
					
				</s:iterator>	
				
				

				</tbody>
				<tfoot>
				</tfoot>

			</table>
			
			
			<div class="row pagination_conth">
				  <div id="risultatiricerca_info2" class="span6"><s:property value="listaPaginata.numeroElementoInizio"/> - <s:property value="listaPaginata.numeroElementoFine"/>
				  	 di <s:property value="listaPaginata.totaleElementi"/> risultati</div>   
				  <div class="span6">                               
					<div id="paginazione2" class="pagination pagination-right">
					  <ul>
						<li><s:if test="not listaPaginata.primaPagina"><a href="<s:url method="update" action="dettaglioOperazioneAsinc.update"><s:param name="pagina" value="0"/></s:url>">&laquo; inizio</a></s:if><s:else><a href="#">&laquo; inizio</a></s:else></li>
						<li><s:if test="listaPaginata.hasPaginaPrecedente"><a href="<s:url method="update" action="dettaglioOperazioneAsinc.update"><s:param name="pagina" value="%{pagina-1}"/></s:url>">&laquo; prec</a></s:if><s:else><a href="#">&laquo; prec</a></s:else></li>
						
						<s:iterator id="p" begin="numeroPaginaInizio" end="numeroPaginaFine">
							<li <s:if test="%{(#p-1) eq listaPaginata.paginaCorrente}">class="active"</s:if>><a href="<s:url method="update" action="dettaglioOperazioneAsinc.update"><s:param name="pagina" value="%{#p-1}"/></s:url>"><s:property/></a></li>
						</s:iterator>
						
						<li><s:if test="listaPaginata.hasPaginaSuccessiva"><a href="<s:url method="update" action="dettaglioOperazioneAsinc.update"><s:param name="pagina" value="%{pagina + 1}"/></s:url>">succ &raquo;</a></s:if><s:else><a href="#">succ &raquo;</a></s:else></li>
						<li><s:if test="not listaPaginata.ultimaPagina"><a href="<s:url method="update" action="dettaglioOperazioneAsinc.update"><s:param name="pagina" value="%{listaPaginata.totalePagine-1}"/></s:url>">fine &raquo;</a></s:if><s:else><a href="#">fine &raquo;</a></s:else></li>              
					  </ul>
					</div>         
				  </div>  
			</div> 
			</s:if>
			<div class="Border_line"></div>

		</fieldset>
		<p class="margin-medium"><a class="btn btn-secondary" href="<s:url method="indietro" action="dettaglioOperazioneAsinc"/>">indietro</a> </p>       
          
      </s:form>
    </div>
  </div>	 
</div>	

	
	<%-- Caricamento del footer --%>
	<s:include value="../include/footer.jsp" />
	<s:include value="../include/javascript.jsp" />
	
	
</body>
</html>