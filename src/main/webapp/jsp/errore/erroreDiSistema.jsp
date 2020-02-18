<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%> 

<%-- Inclusione head e CSS --%>
<s:include value="/jsp/include/head.jsp" />

<%-- Inclusione selezioneAnno --%>
<s:include value="/jsp/include/selezioneAnno.jsp" />

<%--breadcumb.JSP --%>
<div class="row-fluid">
	<div class="span12">
		<ul class="breadcrumb">
			<li><a href="<s:url action="home.do" />">Home</a> <span class="divider">&gt;</span></li>
			<li class="active">Errore</li>
		</ul>
	</div>
</div>


<div class="container-fluid"> 
	<div class="row-fluid">
	<div class="span12 contentPage">

<%--bodyErroreDiSistema.JSP --%>
<h2><strong>Errore di sistema</strong></h2>

	Si &egrave; verificato un errore di sistema
	<p>
		<a href="<s:url action="home"/>" class="btn">indietro</a>
	</p>

</div>
  </div>
</div>

    <%-- Caricamento del footer --%>
	<s:include value="/jsp/include/footer.jsp" />
	<s:include value="/jsp/include/javascript.jsp" />
	
</body>
</html>    
   