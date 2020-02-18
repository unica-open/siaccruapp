<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="row-fluid">
	<s:include value="azioniFrequenti.jsp" />
	<s:include value="attivitaPendenti.jsp" />
</div>
<p></p>
<div class="row-fluid">
	<s:iterator value="cruscotto.gruppiAzioni" status="rowstatus">
		<s:if test="#rowstatus.odd">
	</div>
	<p></p>
	<div class="row-fluid">
		</s:if>
		<div class="span6">
				<div class="thumbnail">
					<h2>
						<strong><s:property value="titolo"/></strong> 
					</h2>
					<div class="scroll">
						<ul>
							<s:iterator value="azioni">
							<li><a href="<s:url action="home.selezionaAzione"><s:param name="azioneSelezionata"><s:property value="uid"/></s:param></s:url>" >
							<s:property value="titolo"/></a></li>
							</s:iterator>
						</ul>
					</div>
				</div>
		</div>
	</s:iterator>
	<%-- <s:if test="!cruscotto.gruppiNotificheOperazioneAsincrona.empty">
			<s:include value="gruppoNotifichePendentiOperazioneAsinc.jsp" />
	</s:if> --%>
	
</div>
<p></p>
<div class="row-fluid">
	<s:if test="!cruscotto.gruppiNotificheOperazioneAsincrona.empty">
			<s:include value="gruppoNotifichePendentiOperazioneAsinc.jsp" />
	</s:if>
</div>