<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:iterator value="cruscotto.gruppiNotificheOperazioneAsincrona">

		<a href="<s:url action="notifichePendentiOpAsinc" >
			<s:param name="idAzione"><s:property value="azione.uid"/></s:param>
			<s:param name="totale"><s:property value="totali"/>
			</s:param>
			</s:url>">
			<li><s:property value="azione.titolo"/> (<s:property value="totali"/>) </li>
		</a>
	
</s:iterator>
							
							
						

						
		
