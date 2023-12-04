/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.action.operazioneasinc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.opensymphony.xwork2.Action;

import it.csi.siac.siaccorser.frontend.webservice.OperazioneAsincronaService;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetDettaglioOperazioneAsincrona;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetDettaglioOperazioneAsincronaResponse;
import it.csi.siac.siaccorser.model.DettaglioOperazioneAsincrona;
import it.csi.siac.siaccorser.model.paginazione.ListaPaginata;
import it.csi.siac.siaccorser.model.paginazione.ParametriPaginazione;
import it.csi.siac.siaccruapp.frontend.ui.action.GenericCruAction;
import it.csi.siac.siaccruapp.frontend.ui.handler.session.CruSessionParameter;
import it.csi.siac.siaccruapp.frontend.ui.model.operazioneasinc.DettaglioOperazioneAsincModel;

/**
 * Action per la gestione della pagina delle notificheda riportare all'utente
 * 
 * @author rmontuori
 *
 */
@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class DettaglioOperazioneAsincAction extends GenericCruAction<DettaglioOperazioneAsincModel> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1806161833660098354L;

	@Autowired
	private transient OperazioneAsincronaService operazioneAsincronaService;
	
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		String methodName ="execute";
		log.debugStart(methodName, " Start ");
		
		//model.setTitolo((String)cruSessionHandler.getParametro(CruSessionParameter.TITOLO_PAGE_OPASINC));
		model.setListaPaginata(
				(ListaPaginata<DettaglioOperazioneAsincrona>) cruSessionHandler.getParametro(CruSessionParameter.ELENCO_DETTAGLIO_OPASINC));
		
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String update() throws Exception{
		final String methodName="update";
		log.debugStart(methodName, " Start");
		
		Integer idOperazioneAsincrona = cruSessionHandler.getParametro(CruSessionParameter.ID_OPASINC);
			
		
		GetDettaglioOperazioneAsincrona req = new GetDettaglioOperazioneAsincrona();
		
		req.setRichiedente(cruSessionHandler.getRichiedente());
		req.setOpAsincId(idOperazioneAsincrona);
		req.setParametriPaginazione(new ParametriPaginazione(model.getPagina()));
			
		GetDettaglioOperazioneAsincronaResponse res = operazioneAsincronaService.getDettaglioOperazioneAsincrona(req);
			
		if(res.isFallimento()){
			model.addErrori(res.getErrori());
			return Action.INPUT;
		}
			
		cruSessionHandler.setParametro(CruSessionParameter.ELENCO_DETTAGLIO_OPASINC,res.getElencoPaginato());
		model.setListaPaginata((ListaPaginata<DettaglioOperazioneAsincrona>) cruSessionHandler.getParametro(CruSessionParameter.ELENCO_DETTAGLIO_OPASINC));
				
		log.debugEnd(methodName, " End");
		
		return Action.SUCCESS;
	}


		
	public String indietro() throws Exception{
		final String methodName="indietro";
		log.debugStart(methodName, " Start");
		
		model.resetErrori();
		
		log.debugEnd(methodName, " End");
		
		return "backNotifichePendentiOpAsinc";
	}

	
}
