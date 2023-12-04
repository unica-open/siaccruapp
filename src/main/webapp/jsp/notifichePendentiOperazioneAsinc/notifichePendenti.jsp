<%--
SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r"%> 
<!-- //////////////////////////////////////////////////////////////////////////////// -->
<!-- **************************   inclusione head   ******************************* -->		

<r:include url="/ris/servizi/siac/include/headDashboard.html" resourceProvider="rp"/>


<!-- ************************  fine inclusione head   ***************************** -->
<!-- //////////////////////////////////////////////////////////////////////////////// -->

	<s:include value="../include/javascript.jsp" />
	
	

</head>
<body>

  
<div class="container-fluid-banner">

<!-- //////////////////////////////////////////////////////////////////////////////// -->
<!-- **********************  inclusione Banner portale   **************************** -->

<r:include url="/ris/servizi/siac/include/portalheader.html" resourceProvider="rp"/>

<%-- nel prototipo statico si chiama navbar.html  --%>
<%--  include la combo per selezionare l'anno tra quelli proposti e visualizza le info dell'utente --%>
<s:include value="../include/infoUtenteLogin.jsp" />

<r:include url="/ris/servizi/siac/include/applicationHeader.html" resourceProvider="rp"/>

<!-- **********************  fine inclusione Banner portale   *********************** -->
<!-- //////////////////////////////////////////////////////////////////////////////// -->

<a name="A-contenuti" title="A-contenuti"></a>
</div>
<div class="row-fluid">
  <div class="span12">
    <ul class="breadcrumb">
      <li><a href="../siaccruapp/home.do">Home</a> <span class="divider">></span></li>
      <%-- <li><a href="../index.shtml"></a> <span class="divider">></span></li> --%> 
     <%--  <span class="divider">> --%>
      <li class="active">Elenco operazioni asincrone per azione <s:property value="titolo"/> </li>
    </ul> 
  </div>	
</div>


<div class="container-fluid">
  <div class="row-fluid">
    <div class="span12 contentPage">    
    
	<s:form action="notifichePendentiOpAsinc" method="post">

			<s:if test="hasActionMessages()">
				<%-- Messaggio di WARNING --%>
				<div class="alert alert-warning">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<strong>Attenzione!!</strong><br>
					<ul>
						<s:iterator value="messaggi">
							<li><s:property value="codice"/> - <s:property value="descrizione"/> </li>
						</s:iterator>
					</ul>
				</div>
			</s:if>


			<s:if test="errori.size > 0">
				<%-- Messaggio di ERROR --%>
				<div class="alert alert-error">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<strong>Attenzione!!</strong><br>
					<ul>
						<s:iterator value="errori">
							<li><s:property value="testo"/> </li>
						</s:iterator>
					</ul>
				</div>
			</s:if>

		
		
		<h3>Operazioni asincrone per azione <s:property value="titolo"/></h3>   
		
		<p>&Egrave; possibile filtrare i risultati inserendo i criteri di ricerca.</p>            
		<div class="step-content">
			<div class="step-pane active" id="step1">
			
				<fieldset class="form-horizontal margin-large">
				
					<!-- Data Da .. A .. -->
					<div class="control-group">
						<label class="control-label" for="dataDa">Da</label>			  
						<div class="controls">  
							<s:textfield id="dataDa"  cssClass="lbTextSmall span2 datepicker" name="dataDa" maxlength="10" />
							<span class="al">
								<label class="radio inline" for="dataA">A</label>
							</span>
							<s:textfield id="dataA"  cssClass="lbTextSmall span2 datepicker" name="dataA" maxlength="10"/> 
						</div>
					</div> 
					
					<div class="control-group">
						<label class="control-label" for="statoOperazioneAsinc">Stato </label>			  
						<div class="controls"> 
							<s:select cssClass="span4" name="codiceStato" 
            						list="#{'':'Selezionare lo Stato','CONCLUSA':'CONCLUSA','ERRORE':'ERRORE'}" /> 
      					</div>
      				</div>	
					
					<div class="control-group">
						
						<label for="flagAltriUtenti" class="control-label">Altri utenti </label>
						
						<div class="controls">
							
							<s:checkbox id="idFlagAltriUtenti" name="flagAltriUtenti" onclick="salvaValoreCheck()"/>	
							<s:hidden id="hiddenFlagAltriUtenti" name="hiddenFlagAltriUtenti" />
 
						</div>
					</div>
				
					<p class="margin-medium">
				  		<%-- <a class="btn btn-secondary" href="<s:url action="notifichePendentiOpAsinc_annulla"/>">annulla</a> --%>
				  		<s:submit cssClass="btn btn-primary pull-right" action="notifichePendentiOpAsinc_cerca" value="cerca" /> 
					</p>
				
				</fieldset>
			</div>
		</div>	
		
		<%-- <p class="margin-medium">
			  <a class="btn btn-secondary" href="<s:url action="notifichePendentiOpAsinc_annulla"/>">annulla</a>
			  <s:submit cssClass="btn btn-primary pull-right" action="notifichePendentiOpAsinc.cerca" value="cerca" /> 
		</p>	 --%>
		
		<fieldset class="form-horizontal">
		<h4 class="step-pane">Elenco</h4>
		<h4><span class="num_result"><s:property value="listaPaginata.totaleElementi"/></span> Risultati trovati</h4>                 
		
			<table class="table table-hover tab_left"  summary="...." >
				<thead>
					<tr>
						<th>Messaggio</th>
						<th>Stato</th>
						<th>Data avvio elaborazione</th>
						<th>Data fine elaborazione</th>
						<th class="span2 tab_Right">&nbsp;</th>			
					</tr>
				</thead>
				<tbody>
				
				
				<s:iterator value="listaPaginata" var="notifica" status="st">

	
					<tr>
						<td><s:property value="messaggio" /> &nbsp; [ID OPERAZIONE: <s:property value="uid" />]<br>
						<s:if test="%{#notifica.statoOperazione == 'CONCLUSA'}">
							eseguiti con successo (
							<s:if test="%{#notifica.totaleSuccess == null}">0</s:if>
							<s:else><s:property value="totaleSuccess" /></s:else>)
							eseguiti con errore (
							 <s:if test="%{#notifica.totaleFalliti == null}">0</s:if>
							 <s:else><s:property value="totaleFalliti" /></s:else>)
						</s:if>
						<br>
						Account elaborazione: <s:property value="accountElaborazione" />
						</td>
						<td><s:property value="statoOperazione" /></td>
						<td><s:date name="dataAvvioOperazione" format="dd/MM/yyyy H:mm" /></td>
						<td><s:date name="dataFineOperazione" format="dd/MM/yyyy H:mm" /></td>
						<td class="tab_Right">
							<div class="btn-group">
								<button class="btn dropdown-toggle" data-toggle="dropdown">Azioni<span class="caret"></span></button>
								<ul class="dropdown-menu pull-right">
									<li><a href="<s:url action="notifichePendentiOpAsinc_consultaDettaglio">
													<s:param name="index" value="#st.index"/>
												</s:url>">Consulta</a></li> 	
								</ul>
							</div>
						</td>
					</tr>
	
					
				</s:iterator>	
				

				</tbody>
				<tfoot>
				</tfoot>

			</table>
			
			<s:if test="listaPaginata.totaleElementi != 0">
				
			<div class="row pagination_conth">
				 <div id="risultatiricerca_info2" class="span6"><s:property value="listaPaginata.numeroElementoInizio"/> - <s:property value="listaPaginata.numeroElementoFine"/>
				  	 di <s:property value="listaPaginata.totaleElementi"/> risultati</div>   
				  <div class="span6">      
				           
					  <div id="paginazione2" class="pagination pagination-right">
						  <ul>
						  	<s:hidden name="pagina" id="numeroPaginaCorrente" />
							<li><s:if test="not listaPaginata.primaPagina"><a href="<s:url action="notifichePendentiOpAsinc_update"><s:param name="pagina" value="0"/></s:url>">&laquo; inizio</a></s:if><s:else><a href="#">&laquo; inizio</a></s:else></li>
							<s:url action="notifichePendentiOpAsinc_update" var="pagPrec"><s:param name="pagina" value="%{(pagina-1)}"/></s:url>
							<li><s:if test="listaPaginata.hasPaginaPrecedente"><a href="<s:property value="#pagPrec"/>">&laquo; prec</a></s:if><s:else><a href="#">&laquo; prec</a></s:else></li>
							<s:iterator var="p" begin="numeroPaginaInizio" end="numeroPaginaFine">
								<li <s:if test="%{(#p-1) eq listaPaginata.paginaCorrente}">class="active"</s:if>><a href="<s:url action="notifichePendentiOpAsinc_update"><s:param name="pagina" value="%{#p-1}"/></s:url>"><s:property/></a></li>
							</s:iterator>
							<s:url action="notifichePendentiOpAsinc_update" var="pagSucc"><s:param name="pagina" value="%{(pagina + 1)}"/></s:url>
							<li><s:if test="listaPaginata.hasPaginaSuccessiva"><a href="<s:property value="#pagSucc"/>">succ &raquo;</a></s:if><s:else><a href="#" id="pagSucc">succ &raquo;</a></s:else></li>
							<li><s:if test="not listaPaginata.ultimaPagina"><a href="<s:url action="notifichePendentiOpAsinc_update"><s:param name="pagina" value="%{(listaPaginata.totalePagine-1)}"/></s:url>">fine &raquo;</a></s:if><s:else><a href="#">fine &raquo;</a></s:else></li>              
						  </ul>
					</div>         
				</div>  
			</div> 
				
				
			 
			</s:if>
			<s:else>
			
				<div class="row pagination_conth">
						  <div id="risultatiricerca_info2" class="span6">Nessun risultato trovato</div>   
						                
				</div>		  
			</s:else>	
			<div class="Border_line"></div>

		</fieldset>
		<p class="margin-medium"><a class="btn btn-secondary" href="<s:url action="notifichePendentiOpAsinc_indietro"/>">indietro</a> </p>       
          
      </s:form>
    </div>
  </div>	 
</div>	


<script type="text/javascript">

		//SIAC-7573
		if($("#idFlagAltriUtenti").val() === 'true' && $('#hiddenFlagAltriUtenti').val() === 'true' ){
			$("#idFlagAltriUtenti").attr("checked", "checked");
		}
	
		function salvaValoreCheck(){


			//alert("xxxxxxxxxxx salvaValoreCheck xxxxxxxxxxxxxx");

			//$('input[name=foo]').is(':checked')
			// La variabile 'valore' conterrà il value del checkbox se questo è selezionato, altrimenti 'null'.
			var valore = $("#idFlagAltriUtenti:checked").val();
			//alert("valore check: " + valore);
			
			if(valore)
				$("#hiddenFlagAltriUtenti").val(valore);
			else 
				$("#hiddenFlagAltriUtenti").val(false);

			//alert("valore hidden: " + $("#hiddenFlagAltriUtenti").val());
		}
		

</script>
	
	<%-- Caricamento del footer --%>
	<s:include value="../include/footer.jsp" />

	
	
</body>
</html>