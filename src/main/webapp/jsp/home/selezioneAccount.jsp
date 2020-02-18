<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="container-fluid"> 
	<div class="row-fluid">
		<div class="span12 contentPage">
	
	
			<div class="row-fluid">
				<div class="span6">
					<div class="thumbnail">
						<h2>
							<strong>Selezione profilo</strong>
						</h2>
						<div class="scroll">
							<p>Prima di procedere &egrave; necessario selezionare un profilo operativo.</p>
							<ul>
								<s:iterator value="accounts">
								<li><a href="<s:url action="home.selezionaAccount" >	<s:param name="accountSelezionato" value="id"/></s:url>">
										<s:property value="descrizione"/> - <s:property value="ente.nome"/></a>
								</li>
								</s:iterator>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!-- <p></p> -->

		</div>
  	</div>
</div>

 <%-- Caricamento del footer --%>
 <s:include value="../include/footer.jsp" />
<s:include value="../include/javascript.jsp" />
	
</body>
</html>