/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.action.operazioneasinc;

import java.util.Calendar;
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
	//SIAC-8062
	final static int DAYS_INTERVAL = 10;

	@Override
	public void prepare() throws Exception {
		super.prepare();
		
		log.infoStart("initDate");
		if(StringUtils.isEmpty(model.getDataDa()) && 
				StringUtils.isEmpty(model.getDataA())){
			// al primo caricamento imposto la data odierna da far vedere e calcolo gli ultimi dieci giorni 
			Date pastDate = calcolaDataPerNumeroGiorni(new Date(),false, DAYS_INTERVAL);
			//SIAC-7544
			model.setDataDa(cruSessionHandler.containsKey(CruSessionParameter.DATA_DA_OPASINC) == true ? (String) sessionHandler.getParametro(CruSessionParameter.DATA_DA_OPASINC) : DateConverter.convertToString(pastDate));
		}
		log.infoEnd("initDate");
		
		//SIAC-7573
		if(model.getFlagAltriUtenti() == null && cruSessionHandler.containsKey(CruSessionParameter.FLAG_ALTRI_UTENTI_OPASINC)) {
			model.setFlagAltriUtenti((Boolean) cruSessionHandler.getParametro(CruSessionParameter.FLAG_ALTRI_UTENTI_OPASINC));
		}

		if(model.getHiddenFlagAltriUtenti() == null && cruSessionHandler.containsKey(CruSessionParameter.HIDDEN_FLAG_ALTRI_UTENTI_OPASINC)) {
			model.setHiddenFlagAltriUtenti((Boolean) cruSessionHandler.getParametro(CruSessionParameter.HIDDEN_FLAG_ALTRI_UTENTI_OPASINC));
		}
		//
		
		//SIAC-7471
//		List<String> paramTemp = new ArrayList<String>();
//		if(model.getPagina() == 0 && paramTemp.add(getParameterFromStrutsRequest("pagina"))) {
//			model.setPagina(new Integer(paramTemp.get(0)));
//		}
		
		//SIAC-8218
		getPaginaFromSession(false);
		
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
		
		cruSessionHandler.setParametro(CruSessionParameter.ID_AZIONE_OPASINC, azione.getUid());

		
		GetNotificheOperazioneAsincrona req = new GetNotificheOperazioneAsincrona();
		
		//SIAC-7544
		if(StringUtils.isNotEmpty(model.getDataDa()) || cruSessionHandler.containsKey(CruSessionParameter.DATA_DA_OPASINC)) {
			
			//se ci sono dei parametri in sessione vi do' la precedente passata in sessione
			req.setDataDa(model.getDataDa() == null ? (String) cruSessionHandler.getParametro(CruSessionParameter.DATA_DA_OPASINC) : model.getDataDa());
			//se sono qui la data e' valida e la posso salvare
			cruSessionHandler.setParametro(CruSessionParameter.DATA_DA_OPASINC, model.getDataDa());
			//
		}
				
		//SIAC-7544
		if(StringUtils.isNotEmpty(model.getDataA()) || cruSessionHandler.containsKey(CruSessionParameter.DATA_A_OPASINC)){
			
			//se ci sono dei parametri in sessione vi do' la precedente passata in sessione
			if(model.getDataA() == null) {
				model.setDataA((String) cruSessionHandler.getParametro(CruSessionParameter.DATA_A_OPASINC));
			}
			//	

			Date dtA = DateConverter.convertFromString(model.getDataA());
			
			if (StringUtils.isEmpty(model.getDataDa())){
				model.addErrore(ErroreCore.DATE_INCONGRUENTI.getErrore("Data Da non puo' essere nulla"));
				return;
			}
			Date dtDa = DateConverter.convertFromString(model.getDataDa());
			
			//SIAC-8062 il calcolo impediva una data precedente ad oggi 
			//in quando la data inclusa nel calcolo era quella odierna
//			Date pasteDataA = DateConverter.calcolaDataPerNumeroGiorni(new Date(), false,10);
			
			if(dtDa.after(dtA)){
				model.addErrore(ErroreCore.DATE_INCONGRUENTI.getErrore("DataA non puo' essere inefriore a Data Da"));
				return;
			}
			
			if(!equalsOrBetween(dtDa, dtA, DAYS_INTERVAL)){
				model.addErrore(ErroreCore.DATE_INCONGRUENTI.getErrore("Non e' possibile impostare un intervallo di ricerca superiore ai 10 giorni"));
				return;
			}
			
			// devo incrementare il dataA +1 perchè altrimenti non considera il giorno 'a' indicato, 
			// ho provato sia con il between che con il <= 
			req.setDataA(model.getDataA());
			
			//SIAC-7544
			//se sono qui la data e' valida e la posso salvare
			cruSessionHandler.setParametro(CruSessionParameter.DATA_A_OPASINC, model.getDataA());
			//
		
		}else{
			// se dataA e' vuota e sono al primo caricamnento, la setto con dataDa +10
			if((model.getListaPaginata()==null || model.getListaPaginata().isEmpty())
							&& StringUtils.isEmpty(model.getDataA())){
				
				model.setDataA(DateConverter.convertToString(new Date()));
				req.setDataA(model.getDataA());
				
			}
		}
		
		
		
		model.setFlagAltriUtenti(valorizzaCheckbox("flagAltriUtenti", model.getHiddenFlagAltriUtenti()));
		
		//SIAC-7537
		if(model.getFlagAltriUtenti() == null && cruSessionHandler.containsKey(CruSessionParameter.FLAG_ALTRI_UTENTI_OPASINC)) {
			model.setFlagAltriUtenti((Boolean) cruSessionHandler.getParametro(CruSessionParameter.FLAG_ALTRI_UTENTI_OPASINC));
		}
		
		cruSessionHandler.setParametro(CruSessionParameter.FLAG_ALTRI_UTENTI_OPASINC, model.getFlagAltriUtenti());
		cruSessionHandler.setParametro(CruSessionParameter.HIDDEN_FLAG_ALTRI_UTENTI_OPASINC, model.getHiddenFlagAltriUtenti());
		
		if(model.getCodiceStato() == null && cruSessionHandler.containsKey(CruSessionParameter.STATO_OPASINC)) {
			model.setCodiceStato((String) cruSessionHandler.getParametro(CruSessionParameter.STATO_OPASINC));
		}
		
		req.setCodiceStato(model.getCodiceStato());
		
		cruSessionHandler.setParametro(CruSessionParameter.STATO_OPASINC, model.getCodiceStato());
		
		//
		
		req.setFlagAltriUtenti(model.getFlagAltriUtenti());
		
		req.setRichiedente(cruSessionHandler.getRichiedente());
		req.setAccountId(cruSessionHandler.getAccount().getUid());
		req.setEnteProprietarioId(cruSessionHandler.getAccount().getEnte().getUid());
		req.setAzioneId(azione.getUid());

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
		
		//SIAC-8218
		cruSessionHandler.setParametro(CruSessionParameter.PAGINA_ELENCO_OPERAZIONE_ASINCRONE, model.getPagina());

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

	@SuppressWarnings("unchecked")
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
		
		//SIAC-7573
		cruSessionHandler.clean(CruSessionParameter.DATA_A_OPASINC.getName());
		cruSessionHandler.clean(CruSessionParameter.STATO_OPASINC.getName());
		cruSessionHandler.clean(CruSessionParameter.DATA_DA_OPASINC.getName());
		cruSessionHandler.clean(CruSessionParameter.ELENCO_OPASINC.getName());
		cruSessionHandler.clean(CruSessionParameter.FLAG_ALTRI_UTENTI_OPASINC.getName());
		cruSessionHandler.clean(CruSessionParameter.HIDDEN_FLAG_ALTRI_UTENTI_OPASINC.getName());
		//
		
		//SIAC-8218
		getPaginaFromSession(true);
	}
	
	/**
	 * SIAC-8062
	 * 
	 * @param <Date> date
	 * @param <boolean> lower
	 * @param <int> daysInterval
	 * @return <Date>
	 */
	private Date getDateWithDayInterval(Date date, boolean lower, int daysInterval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, lower ? -daysInterval : daysInterval);
		return calendar.getTime();
	}
	
	/**
	 * SIAC-8062
	 * 
	 * @param <Date> dateDa
	 * @param <Date> dateA
	 * @param <int> daysInterval
	 * @return <boolean>
	 */
	protected boolean equalsOrBetween(Date dateDa, Date dateA, int daysInterval) {
		if(dateDa == null) {
			return false;
		}
		
		Date maxInterval = getDateWithDayInterval(dateDa, false, daysInterval);
		Date minInterval = getDateWithDayInterval(dateA != null ? dateA : maxInterval, true, daysInterval);
		
		if((dateA.before(maxInterval) || dateA.equals(maxInterval))
				&& (dateDa.after(minInterval) || dateDa.equals(minInterval))) {
			return true;
		}
		
		return false;
	}
	
	//SIAC-8218
	private void getPaginaFromSession(boolean cleanSessionAfterGet) {
		if(model.getPagina() == 0 && cruSessionHandler.containsKey(CruSessionParameter.PAGINA_ELENCO_OPERAZIONE_ASINCRONE)) {
			model.setPagina((Integer) cruSessionHandler.getParametro(CruSessionParameter.PAGINA_ELENCO_OPERAZIONE_ASINCRONE));
		}
		if(cleanSessionAfterGet) {
			cruSessionHandler.clean(CruSessionParameter.PAGINA_ELENCO_OPERAZIONE_ASINCRONE.getName());
		}
	}
	
	private Date calcolaDataPerNumeroGiorni(Date date, boolean b, int numerogiorni) {

		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DATE, -numerogiorni);
		return calendar.getTime();
	}
	

}
