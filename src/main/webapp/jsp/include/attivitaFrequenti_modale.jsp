<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:iterator value="cruscotto.azioniFrequenti">

		<a href="<s:url action="home.selezionaAzione"><s:param name="azioneSelezionata"><s:property value="uid"/></s:param></s:url>">
		<li><s:property value="titolo"/></li>
		</a>
	
</s:iterator>
							
							
						

						
		
