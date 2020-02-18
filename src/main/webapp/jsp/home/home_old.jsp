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

  
<%-- breadcumb --%>
<div class="row-fluid">
	<div class="span12">
		<ul class="breadcrumb">
			<li class="active">
			<s:if test="%{account!=null}">
				<!-- <a>Home</a> -->
				<a href="<s:url action="home.backSelezionaAccount" ></s:url>">Home
				</a>
			</s:if>	
			</li>
		</ul>
	</div>
</div>


<div class="container-fluid"> 
	<div class="row-fluid">
	<div class="span12 contentPage">


<%-- BODY --%>
<s:if test="%{account==null}">
	<s:include value="selezioneAccount.jsp"></s:include>
</s:if>
<s:else>
	<s:include value="cruscotto.jsp"></s:include>
</s:else>


</div>
  </div>
</div>

    <%-- Caricamento del footer --%>
	<s:include value="../include/footer.jsp" />
	<s:include value="../include/javascript.jsp" />
	
</body>
</html>