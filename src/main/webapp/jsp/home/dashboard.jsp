<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%> 

<%-- <!-- header.jsp contiene head, portalHeader, selezioneAnno, selezionaAccount -->
<s:include value="../include/header.jsp" /> --%>

<div id="contentDashboard" class="contentDashboard">

<!-- //////////////////////////////////////////////////////////////////////// -->
		<!-- *************** include box visualizzazioni elenchi  ******************* -->

		
		
		
		
		<!-- ////////////////////////////////////////////////////////////////////// -->
		<!-- **************      contenuto pagina cruscotto         *************** -->
		
		<div class="contentFolio">
		
			<div class="boxBasic">
			
			<!-- //////////////////////////////////////////////////////////////// -->
			<!-- *******************  box statici principali ******************** -->
				
				<div class="tiles box-frequenti">
					<div class="slideTextUp">
						<div class="titleFrequenti">
							<div class="icons-freq"></div>
							<p>Attivit&agrave;  frequenti</p>
						</div>
						<div class="previewSlide">
							<a class="linkBox" data-page-type="s-page"  data-modal-id="#modal-frequenti">
								
								<div class="title-previewOver">Attivit&agrave;  frequenti
								<s:if test="cruscotto.azioniFrequenti.size > 8">
									<span>more<div class="arrowDash"></div></span>
								</s:if>
								</div>
							</a>
							<ul>
								<s:iterator value="cruscotto.azioniFrequenti" status="st">
									<s:if test="#st.index < 8">
										<a href="<s:url action="home.selezionaAzione"><s:param name="azioneSelezionata"><s:property value="uid"/></s:param></s:url>" >
										<li><s:property value="titolo"/></li></a>
									</s:if>
								</s:iterator>
							
							</ul>
						</div>
					</div>
				</div>
				
				<!-- **** 1 **** -->
				<div id="modal-frequenti" class="s-page">
					
					<div class="page-contentSlide">
						<div class="close-button s-close-button"><i class="icons-closeDash"></i></div>
						
						<div class="resultData">
							<h2 class="page-title">Attivit&agrave; frequenti</h2>
							<ul>
							<s:include value="../include/attivitaFrequenti_modale.jsp"/>
							</ul>
						</div>
						
					</div>
					<div class="clear"></div>
				</div>
				
				<div class="tiles box-pendenti">
					<div class="slideTextUp">
						<div class="titleFrequenti">
							<div class="icons-pend"></div>
							<p>Attivit&agrave;  pendenti</p>
						</div>
						<div class="previewSlide">
							<a class="linkBox" data-page-type="s-page" >
								<div class="title-previewOver">Attivit&agrave;  pendenti
								<s:if test="cruscotto.gruppiAttivitaPendenti.size > 8">
									<span>more<div class="arrowDash"></div></span>
								</s:if>
								
								</div>
							</a>
							<ul>
																
								<s:iterator value="cruscotto.gruppiAttivitaPendenti">
									<!-- <li> -->
									<a href="<s:url action="attivitaPendenti" >
										<s:param name="idAzione"><s:property value="azione.uid"/></s:param>
											<s:param name="totale"><s:property value="totali"/></s:param></s:url>">
										<li><s:property value="azione.titolo"/> (<s:property value="totali"/>) </li>
									</a>
									<!-- </li> -->
								</s:iterator>
														
							</ul>
						</div>
					</div>
				</div>
				
				
				
				
				<div class="tiles box-notifiche ">
					<a 		
						class="linkBox" data-page-type="s-page"  data-modal-id="#modal-notifiche">  					
					<div class="titleNotifiche">
						<div class="icons-notify">
							<s:if test="!cruscotto.gruppiNotificheOperazioneAsincrona.empty">
								<span class="num-notifiche"><s:property value="cruscotto.gruppiNotificheOperazioneAsincrona.size"/></span>
							</s:if>
							<s:else>
								<span class="num-notifiche">0</span>
							</s:else>
							
						</div>
						<p>Notifiche operazioni asincrone</p>
						<div class="arrowDash"></div>
					</div>	
					</a>					
				</div>
				
				
				<!-- **** 1 **** -->
				<div id="modal-notifiche" class="s-page">
					
					<div class="page-contentSlide">
						<div class="close-button s-close-button"><i class="icons-closeDash"></i></div>
						
						<div class="resultData">
							<h2 class="page-title">Notifiche operazioni asincrone</h2>
							<ul>
							<s:include value="../include/notifiche_modale.jsp"/>
							</ul>
						</div>
						
					</div>
					<div class="clear"></div>
				</div>
				
				
				
				
				<!-- ******************  fine box statici principali **************** -->				
				<!-- //////////////////////////////////////////////////////////////// -->
				
				
			</div>
			

			<div class="Border_line"></div>

			<div id="container-dashboard" class="list clearfix">
				
				<!-- /////////////////////////////////////////////////////// -->
				<!-- ***************  list dinamic box  ******************** -->
				
				<s:iterator value="cruscotto.gruppiAzioni" status="rowstatus">
				<%-- <s:if test="#rowstatus.odd"> --%>
					<div class="col4 box">				
						<a class="linkBox" data-page-type="s-page" data-modal-id="#modal-gruppoAzioni-<s:property value="#rowstatus.index" />">
						<div class="titleBox">
							<p><s:property value="titolo"/></p>
							<div class="arrowDash"></div>
						</div>
						</a>
						
						<div class="previewOver">
							<ul>
								<!-- <a href="FIN-SPE/FIN-InsImpegnoStep1.shtml"><li>Inserisci Capitolo Entrata Gestione</li></a>
								<li>Inserisci Capitolo Spesa Gestione</li>
								<li>Ricerca Capitolo Entrata Gestione</li> -->
								
								<s:iterator value="azioni"  status="st">
								<s:if test="#st.index < 3">
									<a href="<s:url action="home.selezionaAzione"><s:param name="azioneSelezionata"><s:property value="uid"/></s:param></s:url>" >
									<li><s:property value="titolo"/></li></a>
								</s:if>
								</s:iterator>
							</ul>
						</div>
					</div>
				</s:iterator>
				

				<!-- **************  fine list dinamic box  **************** -->
				<!-- /////////////////////////////////////////////////////// -->
				
				
			</div>
			<div class="clear"></div>
			
			
			<s:iterator value="cruscotto.gruppiAzioni" var="gruppo" status="rowstatus">
			
			<!-- **** 1 **** -->
				<div id="modal-gruppoAzioni-<s:property value="#rowstatus.index" />" class="s-page  ">
					
					<div class="page-contentSlide">
						<div class="close-button s-close-button"><i class="icons-closeDash"></i></div>
						
						<div class="resultData">
						<h2 class="page-title"><s:property value="titolo" /></h2>
						<ul>
							<s:iterator value="azioni"  >
								<a href="<s:url action="home.selezionaAzione"><s:param name="azioneSelezionata"><s:property value="uid"/></s:param></s:url>" >
								<li><s:property value="titolo"/></li></a>
							</s:iterator>
						
						</ul>
						</div>
						
					</div>
					<div class="clear"></div>
				</div>
			
		</div>
								
		</s:iterator>
			
			
		
	
	
</div>	
<br/>


<s:include value="../include/footer.jsp" />
<s:include value="../include/javascript.jsp" />


<%-- <script type="text/javascript" src="/siaccruapp/js/jquery-1.8.2.min.js"></script> --%>
<script type="text/javascript" src="${jspath}scripts.js"></script>
<script type="text/javascript" src="${jspath}dashboard.js"></script>

</body>
</html>

