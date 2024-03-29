<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="span6">
	<div class="thumbnail">
		<h2>
			<strong>Notifiche operazioni asincrone</strong> 
		</h2>
		<div class="scroll">
			<ul>
				<s:iterator value="cruscotto.gruppiNotificheOperazioneAsincrona">
				<li>
					<a href="<s:url action="notifichePendentiOpAsinc" >
						<s:param name="idAzione"><s:property value="azione.uid"/></s:param>
						<s:param name="totale"><s:property value="totali"/>
						</s:param>
						</s:url>">
						<s:property value="azione.titolo"/> (<s:property value="totali"/>)
					</a>
				</li>
				</s:iterator>
			</ul>
		</div>
	</div>
</div>

