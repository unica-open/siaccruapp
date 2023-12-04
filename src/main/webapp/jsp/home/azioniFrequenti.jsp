<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="span6">
	<div class="thumbnail">
		<h2>
			<strong>Attivit&agrave; frequenti</strong> 
		</h2>
		<div class="scroll">
			<ul>
				<s:iterator value="cruscotto.azioniFrequenti">
				<li><a href="<s:url action="home.selezionaAzione"><s:param name="azioneSelezionata"><s:property value="uid"/></s:param></s:url>" >
				<s:property value="titolo"/></a></li>
				</s:iterator>
			</ul>
		</div>
	</div>
</div>
