<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!-- **** 1 **** -->
		<div id="viewPage" class="s-page random-r-page">
			
			<div class="page-contentSlide">
				<div class="close-button s-close-button"><i class="icons-closeDash"></i></div>
				
				<div class="resultData">
					<h2 class="page-title"><s:property value="titolo"/></h2>
						<ul>
							<s:iterator value="azioni">
								<a href="<s:url action="home.selezionaAzione"><s:param name="azioneSelezionata"><s:property value="uid"/></s:param></s:url>" >
								<li><s:property value="titolo"/></li></a>
							</s:iterator>
						</ul>
				</div>
				
			</div>
			<div class="clear"></div>
		</div>