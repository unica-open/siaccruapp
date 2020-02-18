/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.action.operazioneasinc;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.opensymphony.xwork2.Action;

import it.csi.siac.siaccommon.util.date.DateConverter;
import it.csi.siac.siaccorser.frontend.webservice.OperazioneAsincronaService;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetDettaglioOperazioneAsincrona;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetDettaglioOperazioneAsincronaResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetNotificheOperazioneAsincrona;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetNotificheOperazioneAsincronaResponse;
import it.csi.siac.siaccorser.model.Azione;
import it.csi.siac.siaccorser.model.AzioneConsentita;
import it.csi.siac.siaccorser.model.Errore;
import it.csi.siac.siaccorser.model.NotificaOperazioneAsincrona;
import it.csi.siac.siaccorser.model.errore.ErroreCore;
import it.csi.siac.siaccorser.model.paginazione.ListaPaginata;
import it.csi.siac.siaccorser.model.paginazione.ParametriPaginazione;
import it.csi.siac.siaccruapp.frontend.ui.action.GenericCruAction;
import it.csi.siac.siaccruapp.frontend.ui.handler.session.CruSessionParameter;
import it.csi.siac.siaccruapp.frontend.ui.model.operazioneasinc.NotifichePendentiOperazioneAsincModel;

/**
 * Action per la gestione della pagina delle notificheda riportare all'utente
 * 
 * @author rmontuori
 * 
 */
@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NotifichePendentiOperazioneAsincAction extends	GenericCruAction<NotifichePendentiOperazioneAsincModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 305120746096757873L;

	@Autowired
	private transient OperazioneAsincronaService operazioneAsincronaService;
	

	@Override
	public void prepare() throws Exception {
		super.prepare();
		
		if(StringUtils.isEmpty(model.getDataDa()) && 
				StringUtils.isEmpty(model.getDataA())){
			// al primo caricamento imposto la data odierna da far vedere e calcolo gli ultimi dieci giorni 
			Date pastDate = DateConverter.calcolaDataPerNumeroGiorni(new Date(),false, 10);
			model.setDataDa(DateConverter.convertToString(pastDate));
		}
	}

	@Override
	public String execute() throws Exception {
		updateListaOpAsinc();
		return model.getErrori().isEmpty() ? SUCCESS : INPUT;
	}
	

	public String cerca() throws Exception {
		model.setListaPaginata(null);
		model.setPagina(0);

		updateListaOpAsinc();
		
		return model.getErrori().isEmpty() ? SUCCESS : INPUT;
	}
	
	private void updateListaOpAsinc() throws Exception {
		
		log.debug("prepare", " FlagAltriUtenti:" + model.getFlagAltriUtenti());
		log.debug("prepare", " HiddenFlagAltriUtenti:" + model.getHiddenFlagAltriUtenti());
		
		model.resetErrori();
		model.setTitolo("");
		
		if (model.getIdAzione() == null) {
			model.setIdAzione((Integer) cruSessionHandler.getParametro(CruSessionParameter.ID_AZIONE_OPASINC));
		}
		
		Azione azione = findAzione();
		
		if (azione == null) {
			return;
		}
		
		cruSessionHandler.setParametro(CruSessionParameter.ID_AZIONE_OPASINC,
				model.getIdAzione());

		
		GetNotificheOperazioneAsincrona req = new GetNotificheOperazioneAsincrona();
		
		if(StringUtils.isNotEmpty(model.getDataDa()))
				req.setDataDa(model.getDataDa());
				
		if(StringUtils.isNotEmpty(model.getDataA())){
			
				Date dtA = DateConverter.convertFromString(model.getDataA());
				
				if (StringUtils.isEmpty(model.getDataDa())){
					model.addErrore(ErroreCore.DATE_INCONGRUENTI.getErrore("Data Da non puo' essere nulla"));
					return;
				}
				Date dtDa = DateConverter.convertFromString(model.getDataDa());
				
				Date pasteDataA = DateConverter.calcolaDataPerNumeroGiorni(new Date(), false,10);
				
				if(dtDa.after(dtA)){
					model.addErrore(ErroreCore.DATE_INCONGRUENTI.getErrore("DataA non puo' essere inefriore a Data Da"));
					return;
				}
				
				if(pasteDataA.after(dtA)){
					model.addErrore(new Errore("","Non e' possibile impostare un intervallo di ricerca superiore ai 10 giorni"));
					return;
				}
				
				// devo incrementare il dataA +1 perchè altrimenti non considera il giorno 'a' indicato, 
				// ho provato sia con il between che con il <= 
				req.setDataA(model.getDataA());
		}else{
				// se dataA e' vuota e sono al primo caricamnento, la setto con dataDa +10
				if((model.getListaPaginata()==null || model.getListaPaginata().isEmpty())
								&& StringUtils.isEmpty(model.getDataA())){
					
					model.setDataA(DateConverter.convertToString(new Date()));
					req.setDataA(model.getDataA());
				}
		}
		
		
		req.setCodiceStato(model.getCodiceStato());
		
		model.setFlagAltriUtenti( valorizzaCheckbox("flagAltriUtenti", model.getHiddenFlagAltriUtenti()));
		
		req.setFlagAltriUtenti(model.getFlagAltriUtenti());
		
		req.setRichiedente(cruSessionHandler.getRichiedente());
		req.setAccountId(cruSessionHandler.getAccount().getUid());
		req.setEnteProprietarioId(cruSessionHandler.getAccount().getEnte().getUid());
		req.setAzioneId(model.getIdAzione());

		ParametriPaginazione pp = new ParametriPaginazione();
		pp.setElementiPerPagina(10);
		pp.setNumeroPagina(model.getPagina());
		req.setParametriPaginazione(pp);
		
		GetNotificheOperazioneAsincronaResponse response = operazioneAsincronaService.getNotificheOperazioneAsincrona(req);

		if (response.isFallimento()) {
			model.addErrori(response.getErrori());
			return;
		}

		model.setListaPaginata(response.getElencoPaginato());
		cruSessionHandler.setParametro(CruSessionParameter.ELENCO_OPASINC,
				response.getElencoPaginato());


		model.setTitolo(azione.getTitolo());
	}

	private Azione findAzione() {
		for (AzioneConsentita ac : cruSessionHandler.getCruscotto().getAzioniConsentite()) {
			if (ac.getAzione().getUid() == model.getIdAzione()) {
				return ac.getAzione();
			}
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public String consultaDettaglio() throws Exception {
		
		final String methodName = "consultaDettaglio";
		log.debugStart(methodName, " Start");
		
		initListaPaginata();
		
		Integer idOperazioneAsincrona = model.getElementoByIndex().getUid();

		GetDettaglioOperazioneAsincrona req = new GetDettaglioOperazioneAsincrona();
		req.setOpAsincId(idOperazioneAsincrona);
		req.setRichiedente(cruSessionHandler.getRichiedente());
		req.setParametriPaginazione(new ParametriPaginazione(0));

		GetDettaglioOperazioneAsincronaResponse response = operazioneAsincronaService
				.getDettaglioOperazioneAsincrona(req);

		if (response.isFallimento()) {
			model.addErrori(response.getErrori());
			return Action.INPUT;
		}

		/**
		 * Se non trovo risultati visualizzo subito il messaggio di warnig
		 */
		if(response.getElencoPaginato() !=null && !response.getElencoPaginato().isEmpty()){
			cruSessionHandler.setParametro(CruSessionParameter.ID_OPASINC, idOperazioneAsincrona);
			cruSessionHandler.setParametro(CruSessionParameter.ELENCO_DETTAGLIO_OPASINC,
					response.getElencoPaginato());
		}else{
			// eseguo prima il reset perchè altrimenti accoda quello nuovo a quelli precedenti
			model.resetErrori();
			model.addErrore(ErroreCore.NESSUN_DATO_REPERITO.getErrore());
			return Action.INPUT;

		}
		
		return SUCCESS;
	}

	private void initListaPaginata() {
		model.setListaPaginata((ListaPaginata<NotificaOperazioneAsincrona>) cruSessionHandler.getParametro(CruSessionParameter.ELENCO_OPASINC));
	}

	/**
	 * Ritorno alla home page del cruscotto
	 * @return
	 * @throws Exception
	 */
	public String indietro() throws Exception{
		final String methodName="indietro";
		log.debugStart(methodName, " Start");
		
		/**
		 * ripulisco la sessione
		 */
		model.resetErrori();
		clearParametriRicerca();
		log.debugEnd(methodName, " End");
		
		return "backHomePageCrusctto";
	}

	/**
	 * Ritorno alla home page del cruscotto
	 * @return
	 * @throws Exception
	 */
	private void clearParametriRicerca() {
		
		model.setCodiceStato("");
		model.setDataDa("");
		model.setDataA("");
		model.setFlagAltriUtenti(null);
		model.setHiddenFlagAltriUtenti(null);
		model.setPagina(0);
		model.setListaPaginata(null);
		
	}

	
}
