<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%> 

<%-- Inclusione head e CSS --%>
<s:include value="../include/head.jsp" />

<%-- Inclusione selezioneAnno --%>
<s:include value="../include/selezioneAnno.jsp" />

<%--vecchia gestione 
<s:set name="content_head" value="" />
<s:set name="content_breadcumb" value="%{'../azioneRichiesta/breadcumb.jsp'}" />
<s:set name="content_body" value="%{'../azioneRichiesta/body.jsp'}" />
 --%>

<%--breadcumb.JSP --%>
<div class="row-fluid">
	<div class="span12">
		<ul class="breadcrumb">
			<li><a href="<s:url action="home.do" />">Home</a> <span class="divider">&gt;</span></li>
			<li class="active">Azione richiesta</li>
		</ul>
	</div>
</div>


<div class="container-fluid"> 
	<div class="row-fluid">
	<div class="span12 contentPage">


<%--body.JSP --%>
<div class="row-fluid">
		<div class="span6">
			<div class="thumbnail">
				<h2>
					<strong>Azione richiesta</strong>
				</h2>
				<div class="scroll">
					Azione richiesta: <s:property value="azione.azione.titolo"/><br> 
					Richiesta numero: <s:property value="azione.uid"/><br> 
					effettuata da: <s:property value="azione.account.nome"/><br>
					id attivit&agrave;: <s:property value="azione.idAttivita"/><br>
					<s:iterator value="azione.parametri">
						<s:property value="nome"/>: <s:property value="valore"/><br>
					</s:iterator>
					<s:form action="azioneRichiesta.exec.do" method="post" >
						<s:hidden name="azioneRichiesta" />
   						Descrizione: <s:textfield name="descrizione" />
   						<br>Decrizione breve: <s:textfield name="descrizioneBreve" />
   						<br>Anno di bilancio: <s:textfield name="annoBilancio" />
   						<br>Strutture: <s:textfield name="strutture" />
						<br><s:submit value="Esegui" />
					</s:form>
				</div>
			</div>
		</div>
	</div>
	<p></p>


</div>
  </div>
</div>

    <%-- Caricamento del footer --%>
	<s:include value="../include/footer.jsp" />
	<s:include value="../include/javascript.jsp" />
	
</body>
</html>
    
   