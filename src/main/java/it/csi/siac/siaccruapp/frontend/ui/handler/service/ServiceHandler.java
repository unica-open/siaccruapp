/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.handler.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.siac.siaccommonapp.util.log.LogWebUtil;
import it.csi.siac.siaccorser.frontend.webservice.CoreService;
import it.csi.siac.siaccorser.frontend.webservice.exception.ServiceException;
import it.csi.siac.siaccorser.frontend.webservice.exception.SystemException;
import it.csi.siac.siaccorser.frontend.webservice.msg.ExecAzioneRichiesta;
import it.csi.siac.siaccorser.frontend.webservice.msg.ExecAzioneRichiestaResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.FindAzione;
import it.csi.siac.siaccorser.frontend.webservice.msg.FindAzioneResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetAccounts;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetAccountsResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetAzioneRichiesta;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetAzioneRichiestaResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetRuoli;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetRuoliResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.SaveAzioneRichiesta;
import it.csi.siac.siaccorser.frontend.webservice.msg.SaveAzioneRichiestaResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.SetupCruscotto;
import it.csi.siac.siaccorser.frontend.webservice.msg.SetupCruscottoResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.UpdateCruscotto;
import it.csi.siac.siaccorser.frontend.webservice.msg.UpdateCruscottoResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.attpendenti.RicercaSinteticaGruppoAttivitaPendentiVariazione;
import it.csi.siac.siaccorser.frontend.webservice.msg.attpendenti.RicercaSinteticaGruppoAttivitaPendentiVariazioneResponse;
import it.csi.siac.siaccorser.model.Account;
import it.csi.siac.siaccorser.model.AttivitaPendente;
import it.csi.siac.siaccorser.model.Azione;
import it.csi.siac.siaccorser.model.AzioneConsentita;
import it.csi.siac.siaccorser.model.AzioneRichiesta;
import it.csi.siac.siaccorser.model.Cruscotto;
import it.csi.siac.siaccorser.model.Errore;
import it.csi.siac.siaccorser.model.Esito;
import it.csi.siac.siaccorser.model.Richiedente;
import it.csi.siac.siaccorser.model.RuoloAccount;
import it.csi.siac.siaccorser.model.ServiceResponse;
import it.csi.siac.siaccorser.model.errore.ErroreCore;
import it.csi.siac.siaccorser.model.paginazione.ParametriPaginazione;
import it.csi.siac.siaccruapp.frontend.ui.handler.session.CruSessionHandler;
import it.csi.siac.siaccruapp.frontend.ui.handler.session.CruSessionParameter;

@Component
public class ServiceHandler {
	
	private LogWebUtil log = new LogWebUtil(getClass());
	private CruSessionHandler sessionHandler;
	

	@Autowired
	private CoreService coreService;	

	/**
	 * @param sessionHandler the sessionHandler to set
	 */
	public void setSessionHandler(CruSessionHandler sessionHandler) {
		this.sessionHandler = sessionHandler;
	}

	

	public List<Account> getAccounts() {
		log.infoStart("getAccounts");

		GetAccounts request = new GetAccounts();
		List<Account> accounts = null;
		try {

			request.setDataOra(new Date());
			request.setRichiedente(sessionHandler.getRichiedente());
			GetAccountsResponse response = coreService.getAccounts(request);
			
			checkResponse(response);
			
			accounts = response.getAccounts();
		
		} catch (Throwable t) {
			log.error("getAccounts", t);
			throw t;
		} finally { 
			log.infoEnd("getAccounts");
		}
		return accounts;
	}

	public Cruscotto setupCruscotto(Account account) {
		return setupCruscotto(account, null);
	}
	
	public Cruscotto setupCruscotto(Account account, Integer annoBilancio) {
		log.infoStart("setupCruscotto");
		SetupCruscotto request = new SetupCruscotto();
		Cruscotto cruscotto = null;
		try {
			log.infoStart("");
			request.setAnnoBilancio(annoBilancio);
			request.setDataOra(new Date());
			Richiedente richiedente = sessionHandler.getRichiedente();
			richiedente.setAccount(account);
			request.setRichiedente(richiedente);
			SetupCruscottoResponse response = coreService.setupCruscotto(request);
			//SIAC-6884
			sessionHandler.setParametro(CruSessionParameter.UTENTE_VARIAZIONE_DECENTRATO,response.isUtenteVariaizoniDecentrato());
			sessionHandler.setParametro(CruSessionParameter.ID_AZIONE_PER_UTENTE_VARIAZIONE_DECENTRATA,response.getIdAzionePerUtenteDecentrato());
			
			
			checkResponse(response);
			
			cruscotto = response.getCruscotto();
		} catch (Throwable t) {
			log.error("setupCruscotto", t);
			throw t;
		} finally { 
			log.infoEnd("setupCruscotto");
		}
		return cruscotto;
	}
	

	public AzioneRichiesta saveAzioneRichiesta(AzioneRichiesta azioneRichiesta) {
		
		SaveAzioneRichiesta request = new SaveAzioneRichiesta();
		AzioneRichiesta result = azioneRichiesta;
		try {
			log.infoStart("saveAzioneRichiesta");
			request.setDataOra(new Date());
			request.setRichiedente(sessionHandler.getRichiedente());
			request.setAnnoBilancio(sessionHandler.getCruscotto().getAnnoBilancio().getAnno());
			request.setAzioneRichiesta(result);
			SaveAzioneRichiestaResponse response = coreService.saveAzioneRichiesta(request);
			
			checkResponse(response);
			
			result = response.getAzioneRichiesta();
		} catch (Throwable t) {
			log.error("saveAzioneRichiesta", t);
			throw t;
		} finally { 
			log.infoEnd("saveAzioneRichiesta");
		}
		return result;
	}

	public void updateCruscotto(Cruscotto cruscotto) {
		UpdateCruscotto request = new UpdateCruscotto();
		try {
			log.infoStart("updateCruscotto");
			request.setDataOra(new Date());
			request.setRichiedente(sessionHandler.getRichiedente());
			request.setAnnoBilancio(cruscotto.getAnnoBilancio().getAnno());
			request.setCruscotto(cruscotto);
			
			//SIAC-6884
			Boolean idDecentrato = sessionHandler.getParametro(CruSessionParameter.UTENTE_VARIAZIONE_DECENTRATO);
			request.setUtenteVariaizoniDecentrato(idDecentrato);
			UpdateCruscottoResponse response = coreService.updateCruscotto(request);
			checkResponse(response);
			cruscotto.setGruppiAttivitaPendenti(response.getGruppiAttivitaPendenti());
			cruscotto.setGruppiNotifichePendenti(response.getGruppiNotifichePendenti());
			cruscotto.setAzioniFrequenti(response.getAzioniFrequenti());
			cruscotto.setGruppiNotificheOperazioneAsincrona(response.getGruppiNotificheOperazioneAsincrona());
		} catch (Throwable t) {
			log.error("updateCruscotto", t);
			throw t;
		} finally { 
			log.infoEnd("updateCruscotto");
		}
	}
	
	public AzioneRichiesta getAzioneRichiesta(AzioneRichiesta azioneRichiesta) {
		
		GetAzioneRichiesta request = new GetAzioneRichiesta();
		GetAzioneRichiestaResponse response;
		AzioneRichiesta result = azioneRichiesta;
		try {
			log.infoStart("getAzioneRichiesta");
			request.setDataOra(new Date());
			request.setRichiedente(sessionHandler.getRichiedente());
			request.setAzioneRichiesta(result);
			response = coreService.getAzioneRichiesta(request); //CAMBIREI IL SERVIZIO
			
			checkResponse(response);
			
			result = response.getAzioneRichiesta();
		} catch (Throwable t) {
			log.error("getAzioneRichiesta", t);
			throw t;
		} finally { 
			log.infoEnd("getAzioneRichiesta");
		}
		return result;
	}
	
	public void execAzioneRichiesta(AzioneRichiesta azioneRichiesta) {
		
		ExecAzioneRichiesta request = new ExecAzioneRichiesta();
		ExecAzioneRichiestaResponse response;
		try {
			log.infoStart("execAzioneRichiesta");
			request.setDataOra(new Date());
			request.setRichiedente(sessionHandler.getRichiedente());
			request.setAzioneRichiesta(azioneRichiesta);
			response = coreService.execAzioneRichiesta(request);
			
			checkResponse(response);
		} catch (Throwable t) {
			log.error("execAzioneRichiesta", t);
			throw t;
		} finally { 
			log.infoEnd("execAzioneRichiesta");
		}
	}
	
	public List<AttivitaPendente> getAttivitaPendenti(AzioneConsentita azioneConsentita, Integer annoBilancio,Integer idEnteProprietario, int offset, int size, int numeroPagina) {
		
//		GetAttivitaPendenti request = new GetAttivitaPendenti();
//		GetAttivitaPendentiResponse response;
		List<AttivitaPendente> attivitaPendenti = new ArrayList<AttivitaPendente>();
		try {
			RicercaSinteticaGruppoAttivitaPendentiVariazione req = new RicercaSinteticaGruppoAttivitaPendentiVariazione();
			req.setDataOra(new Date());
			req.setAnnoBilancio(annoBilancio);
			req.setSoloTotali(false);
			req.setParametriPaginazione(new ParametriPaginazione());
			req.getParametriPaginazione().setElementiPerPagina(size);
			req.getParametriPaginazione().setNumeroPagina(numeroPagina);
			req.setAzioneConsentita(azioneConsentita);
			req.setRichiedente(sessionHandler.getRichiedente());
			RicercaSinteticaGruppoAttivitaPendentiVariazioneResponse response = coreService.ricercaSinteticaGruppoAttivitaPendentiVariazione(req);

			
//			log.infoStart("getAttivitaPendenti");
//			request.setRichiedente(sessionHandler.getRichiedente());
//			request.setAzioneConsentita(azioneConsentita);
//			request.setAnnoBilancio(annoBilancio);
//			request.setIdEnteProprietario(idEnteProprietario);
//			request.setOffset(offset);
//			request.setSize(size);
//			
//			response = coreService.getAttivitaPendenti(request);
//			
			checkResponse(response);
			
			attivitaPendenti = response.getAttivitaPendenti();
		} catch (Throwable t) {
			log.error("getAttivitaPendenti", t);
			throw t;
		} finally { 
			log.infoEnd("getAttivitaPendenti");
		}
		return attivitaPendenti;
	}
	
	private  void checkResponse(ServiceResponse response) {
		if (response.getEsito() != Esito.SUCCESSO) {
			for(Errore errore:response.getErrori()) {
				log.error("setupCruscotto", errore);
				if (errore.getCodice().equals(ErroreCore.ERRORE_DI_SISTEMA.getCodice())) {
					throw new SystemException(errore);
				}
			}
			throw new ServiceException(response.getErrori());
		}
	}

	public Azione findAzione(Integer idAzione) {
		
		FindAzione request = new FindAzione();
		
		request.setRichiedente(sessionHandler.getRichiedente());
		request.setIdAzione(idAzione);
		
		FindAzioneResponse response = coreService.findAzione(request);
		
		checkResponse(response);
				
		return response.getAzione();
	}
	
	
	
	public Account findAccountByIdUtente(Richiedente richiedente, Integer anno) {
		log.infoStart("findAccountByIdUtente");

		Account account = new Account();
		try {
			GetAccounts request = new GetAccounts();
			request.setAnnoBilancio(anno);
			request.setRichiedente(richiedente);
			GetAccountsResponse accountResponse  = coreService.getAccount(request); 
			if(accountResponse!= null && accountResponse.getAccounts()!= null
					&& !accountResponse.getAccounts().isEmpty() ){
				account = accountResponse.getAccounts().get(0);
			}
			
		} catch (Throwable t) {
			log.error("findAccountByIdUtente", t);
			throw t;
		} finally { 
			log.infoEnd("findAccountByIdUtente");
		}
		return account;
	}
	
	
	public List<RuoloAccount> findRuoliByIdUtente(Richiedente richiedente, Integer anno) {
		log.infoStart("findRuoliByIdUtente");
		List<RuoloAccount> ruoliAccount = new ArrayList<RuoloAccount>();
		try {
			GetRuoli request = new GetRuoli();
			request.setAnnoBilancio(anno);
			request.setRichiedente(richiedente);
			GetRuoliResponse ruoliResposne  = coreService.getRuoliAccount(request); 
			if(ruoliResposne!= null && ruoliResposne.getRuoli()!= null &&
					!ruoliResposne.getRuoli().isEmpty()){
				ruoliAccount = ruoliResposne.getRuoli();
			}
			
		} catch (Throwable t) {
			log.error("findRuoliByIdUtente", t);
			throw t;
		} finally { 
			log.infoEnd("findRuoliByIdUtente");
		}
		return ruoliAccount;
	}
	
	
	
	
	
	
	
	
	
	
}
