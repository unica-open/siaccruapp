<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%> 

<%-- vecchia gestione 
<s:set name="content_head" value="" />
<s:set name="content_breadcumb" value="%{'../attivitaPendenti/breadcumb.jsp'}" />
<s:set name="content_body" value="%{'../attivitaPendenti/body.jsp'}" /> --%>

<%-- Inclusione head e CSS --%>
<s:include value="../include/head.jsp" />

<div class="container-fluid-banner">

<!-- //////////////////////////////////////////////////////////////////////////////// -->
<!-- **********************  inclusione Banner portale   **************************** -->

<r:include url="/ris/servizi/siac/include/portalheader.html" resourceProvider="rp"/>

<%-- Inclusione selezioneAnno --%>
<s:include value="../include/selezioneAnno.jsp" />

<r:include url="/ris/servizi/siac/include/applicationHeader.html" resourceProvider="rp"/>

<!-- **********************  fine inclusione Banner portale   *********************** -->
<!-- //////////////////////////////////////////////////////////////////////////////// -->

<%--breadcumb.JSP --%>
<div class="row-fluid">
	<div class="span12">
		<ul class="breadcrumb">
			<li><a href="<s:url action="home.do" />">Home</a> <span class="divider">&gt;</span></li>
			<li class="active">Attivit&agrave; pendenti</li>
		</ul>
	</div>
</div>


<div class="container-fluid"> 
	<div class="row-fluid">
	<div class="span12 contentPage">



	<%--BODY.JSP --%>
	<h2><strong>Attivita' Pendenti</strong></h2>
	<h3><strong><s:property value="titolo"/> </strong></h3>
	
	<h4>
		<span class="num_result"><s:property value="totale" /></span> risultati
		trovati
	</h4>
		<table
			class="table table-striped table-bordered table-hover"
			summary="....">
			<thead>
				<tr>
					<th scope="col">Descrizione<span class="shortDesc"></span></th>
					<th scope="col">Data Apertura Proposta</th>
					<th scope="col">Data Chiusura Proposta</th>
					<th scope="col">Direzione Proponente</th>
					<th scope="col">Azioni</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="attivitaPendenti">
					<tr>
						<td><s:property value="descrizione" /></td>
						<td><s:property value="dataAperturaProposta" /></td>
						<td><s:property value="dataChiusuraProposta" /></td>
						<td><s:property value="direzioneProponente" /></td>
						<td>
							<div class="btn-group">
								<button
									class="btn dropdown-toggle"
									data-toggle="dropdown">
									Azioni <span class="caret"></span>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a
										href="<s:url action="attivitaPendenti.selezionaAttivita"><s:param name="azioneSelezionata"><s:property value="azione.uid"/></s:param><s:param name="idAttivita"><s:property value="idAttivita"/></s:param></s:url>">esegui</a></li>
								</ul>
							</div>
							<!-- /btn-group -->
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<div class="row pagination_conth">
			<div id="risultatiricerca_info" class="span6">
			<s:property value="start"/> - 
			<s:property value="stop"/> di 
			<s:property value="totale"/>  risultati
			</div>
			<div class="span6">
				<div
					id="paginazione"
					class="pagination pagination-right">
	
					<ul>
						<li><a href="<s:url action="attivitaPendenti.first" />">&laquo; inizio</a></li>
						<li><a href="<s:url action="attivitaPendenti.prev" />">&laquo; prec</a></li>
						<li><a href="<s:url action="attivitaPendenti.prev" />"><s:property value="paginaPrec"/></a></li>
						<li class="active"><a><s:property value="paginaCor"/></a></li>
						<li><a href="<s:url action="attivitaPendenti.next" />"><s:property value="paginaSuc"/></a></li>
						<li><a href="<s:url action="attivitaPendenti.next" />">succ &raquo;</a></li>
						<li><a href="<s:url action="attivitaPendenti.last" />"> fine &raquo;</a></li>
	
					</ul>
				</div>
	
			</div>
		</div>
	
	
		<p>
			<a
				href="<s:url action="home"/>"
				class="btn">indietro</a>
		</p>

	</div>
  </div>
</div>

    <%-- Caricamento del footer --%>
	<s:include value="../include/footer.jsp" />
	<s:include value="../include/javascript.jsp" />
	
</body>
</html>
   