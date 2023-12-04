<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%>
 
<!-- header.jsp contiene head, portalHeader, selezioneAnno -->
<s:include value="../include/header.jsp" />

<!--  -->
<%-- BODY --%>

<s:if test="%{sessionHandler.account == null}">
	<s:include value="selezioneAccount.jsp"></s:include>
</s:if>
<s:else>
	<s:include value="dashboard.jsp"></s:include>
</s:else>



