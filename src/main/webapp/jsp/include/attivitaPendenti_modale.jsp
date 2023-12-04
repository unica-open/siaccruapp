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
					<h2 class="page-title">Attivit&agrave; pendenti</h2>
						<ul>
							<s:iterator value="cruscotto.gruppiAttivitaPendenti">
							
								<a href="<s:url action="attivitaPendenti" >
									<s:param name="idAzione"><s:property value="azione.uid"/>
									</s:param>
									<s:param name="totale"><s:property value="totali"/>
									</s:param>
									</s:url>">
									<li><s:property value="azione.titolo"/> (<s:property value="totali"/>) </li>
								</a>
						
							</s:iterator>
						</ul>
				</div>
				
			</div>
			<div class="clear"></div>
		</div>