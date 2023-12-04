<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%-- Informazioni sull'Utente effettuante il login --%>
<div class="navbarLogin">
	<div class="container-fluid">
		<p class="login-text pull-left">
			<s:if test="evidenziaAnnoSelezionato">
				<font color="red"> 
					Bilancio <s:property value="annoBilancioCorrente"/>
					<i class="icon-info-sign" data-toggle="tooltip" title="Attenzione! L'anno selezionato risulta essere precedente rispetto all'anno corrente"></i>
				</font>
			</s:if>
			<s:else>
				Bilancio <s:property value="annoBilancioCorrente"/>
				<i class="icon-pencil"></i>
			</s:else>
		</p>
		<p class="login-text pull-right">
			<s:property value="sessionHandler.account.ente.nome" /> - <s:property value="sessionHandler.account.nome" /> - <s:property value="sessionHandler.operatore.nome" />
			<a href="/siaccruapp/logout.do" class="navbar-link"></a>
		</p>
	</div>
</div>


