<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="dropdown pull-left login-text">
	<s:if test="%{sessionHandler.account!=null}">
		<a class="dropdown-toggle"
			data-toggle="dropdown"
			href="#"
			title="seleziona l'anno di bilancio su cui vuoi lavorare">
			
			<s:if test="evidenziaAnnoSelezionato">
				<font color="red"> 
					Bilancio  <s:property value="annoBilancioCorrente"/> 
					<!-- <i class="icon-info-sign">&nbsp;Anno selezionato precedente a quello corrente.</i> -->
					<i class="icon-info-sign" data-toggle="tooltip" title="Attenzione! L'anno selezionato risulta essere precedente rispetto all'anno corrente"></i>
				</font>
			</s:if>
			<s:else>
				Bilancio  <s:property value="annoBilancioCorrente"/>
				<i class="icon-pencil"></i>
			</s:else>	
			 	
		</a>
		
		
		<%-- <s:if test="evidenziaAnnoSelezionato">
			<div id="messaggioInformativoAnno"  style="
				    display: inline-block;
				    margin-left: 20px;
				    background-color: red;
				    color: white;
				    padding: 0 10px 0 10px;">Anno selezionato precedente a quello corrente.</div>
		</s:if> --%>
		
		<div id="messaggioInformativo"  style="
			    display: inline-block;
			    margin-left: 20px;
			    background-color: red;
			    color: white;
			    padding: 0 10px 0 10px;"><s:property value="cruscotto.messaggioInformativo" /></div>
		
		<div class="dropdown-menu span7" role="menu" aria-labelledby="dLabel">
			<%-- SIAC-7950 rimozione struts action extension .do --%>
			<s:form action="home.selezionaAnnoEsercizio" method="post" accept-charset="UTF-8">
				<div class="form-horizontal">
					<label class="control-label" for="annoda2">Anno&nbsp;</label>
					 <s:select list="anniBilancio" listKey="%{idBilancio + '-' + idPeriodo}" listValue="descrizione" name="annoSelezionato" />
					 <s:submit cssClass="btn btn-primary" value="modifica"/>
					 
				</div>
			</s:form>
		</div>
	</s:if>
</div>
