<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

	
			  	
	<p class="login-text pull-right">
		<s:if test="%{sessionHandler.account!=null}">
			<s:property value="sessionHandler.account.ente.nome" /> - <s:property value="sessionHandler.account.nome" /> - 
		</s:if>	
			<s:property value="operatore.nome" /> <s:property value="operatore.cognome" />
		 	<a href="logout.do"	class="navbar-link"> </a>
	</p>

		
