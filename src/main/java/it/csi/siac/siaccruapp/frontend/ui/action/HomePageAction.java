/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.opensymphony.xwork2.Action;

import it.csi.siac.siaccommonapp.handler.session.CommonSessionParameter;
import it.csi.siac.siaccorser.model.Account;
import it.csi.siac.siaccorser.model.AnnoBilancio;
import it.csi.siac.siaccorser.model.Azione;
import it.csi.siac.siaccorser.model.AzioneRichiesta;
import it.csi.siac.siaccorser.model.Cruscotto;
import it.csi.siac.siaccorser.model.ParametroAzioneRichiesta;
import it.csi.siac.siaccorser.model.errore.ErroreCore;
import it.csi.siac.siaccruapp.frontend.ui.handler.session.CruSessionParameter;
import it.csi.siac.siaccruapp.frontend.ui.model.HomePageModel;

/**
 * Action per la gestione della home page
 * 
 * @author Spin Servizi per l'Innovazione
 *
 */
@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class HomePageAction extends GenericCruAction<HomePageModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6981364625330734897L;
	
	private String urlAzioneRichiesta;
	
	@Override
	public String execute() {
		updateCruscotto();
		return Action.SUCCESS;
	}
	
	private void updateCruscotto() {
		try {
			log.infoStart("updateCruscotto", "");
			Cruscotto cruscotto = cruSessionHandler.getCruscotto();
			if (cruscotto!=null) {
				serviceHandler.updateCruscotto(cruscotto);
			}
		} catch (Throwable t) {
			log.error("updateCruscotto", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally { 
			log.infoEnd("updateCruscotto", "");
		}
	}
	
	
	public  String selezionaAccount() {
		String accountSelezionato = model.getAccountSelezionato();
		try {
			log.infoStart("selezionaAccount", "");
			Account account = cruSessionHandler.getAccount();
			if (accountSelezionato!=null && account==null){
				List<Account> listaAccount = cruSessionHandler.getListaAccount();
				for (int counter=0; counter<listaAccount.size(); counter++) {
					account = listaAccount.get(counter);
					if (accountSelezionato.equals(account.getId())) {
						setupCruscotto(account);
						break;
					}
				}
			}
		} catch (Throwable t) {
			log.error("selezionaAccount", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
			return "errorRuntime";
		} finally { 
			log.infoEnd("selezionaAccount", "");
		}
		return Action.SUCCESS;
	}
	
	public  String backSelezionaAccount() {
		log.infoStart("backSelezionaAccount", "");
		try {
			cruSessionHandler.cleanAllExcluding(CruSessionParameter.LISTA_ACCOUNT);
		} finally { 
			log.infoEnd("backSelezionaAccount", "");
		}
		return Action.SUCCESS;
	}
	
	public String selezionaAzione() {
		String idAzioneSelezionata = model.getAzioneSelezionata();
		try {
			log.infoStart("selezionaAzione", "");
			
			Azione azione = findAzione(Integer.parseInt(idAzioneSelezionata));
			
			if (azione==null) {
				throw new RuntimeException("azione non trovata");
			}
			
			AzioneRichiesta azioneRichiesta = new AzioneRichiesta();
			azioneRichiesta.setIdAttivita(null);
			azioneRichiesta.setData(new Date());
			azioneRichiesta.setDaCruscotto(true);
			azioneRichiesta.setAzione(azione);
			azioneRichiesta.setAccount(cruSessionHandler.getAccount());
			AnnoBilancio annoBilancio = new AnnoBilancio();
			if (cruSessionHandler.getCruscotto() != null) {
				annoBilancio = cruSessionHandler.getCruscotto().getAnnoBilancio();
			}
								
			ParametroAzioneRichiesta p = new ParametroAzioneRichiesta();
			p.setNome(ParametroAzioneRichiesta.ANNO_BILANCIO);
			p.setValore(String.valueOf(annoBilancio.getAnno()));
			azioneRichiesta.getParametri().add(p);
			
			
			p = new ParametroAzioneRichiesta();
			p.setNome(ParametroAzioneRichiesta.ID_BILANCIO);
			p.setValore(annoBilancio.getIdBilancio().toString());
			azioneRichiesta.getParametri().add(p);

			p = new ParametroAzioneRichiesta();
			p.setNome(ParametroAzioneRichiesta.DESCRIZIONE_ANNO_BILANCIO);
			p.setValore(annoBilancio.getDescrizione());
			azioneRichiesta.getParametri().add(p);

			p = new ParametroAzioneRichiesta();
			p.setNome(ParametroAzioneRichiesta.CODICE_FASE_ANNO_BILANCIO);
			p.setValore(annoBilancio.getCodiceFase());
			azioneRichiesta.getParametri().add(p);
			
			// SIAC-5022
			p = new ParametroAzioneRichiesta();
			p.setNome(ParametroAzioneRichiesta.BILANCIO_ANNO_PRECEDENTE);
			p.setValore(Boolean.toString(model.isEvidenziaAnnoSelezionato()));
			azioneRichiesta.getParametri().add(p);
			
			// SIAC-6257
			p = new ParametroAzioneRichiesta();
			p.setNome(CommonSessionParameter.PARAMETRI_AZIONE_SELEZIONATA.getName());
			p.setValore(model.getParametriAzioneSelezionata());
			azioneRichiesta.getParametri().add(p);
			
			// Se esiste carico l'anno precedente + relativa fase
			for (AnnoBilancio anno : getAnniBilancio()) {
			
				String codiceFaseAnnoPrec = "";
			
				Integer annoPrecedente  =  annoBilancio.getAnno() - 1;
				if(annoPrecedente.equals(anno.getAnno())){
					codiceFaseAnnoPrec = anno.getCodiceFase();
					
					p = new ParametroAzioneRichiesta();
					p.setNome(ParametroAzioneRichiesta.CODICE_FASE_ANNO_BILANCIO_PRECEDENTE);
					p.setValore(codiceFaseAnnoPrec);
					azioneRichiesta.getParametri().add(p);
					break;
				}
				codiceFaseAnnoPrec = "";
			}
			
			azioneRichiesta = serviceHandler.saveAzioneRichiesta(azioneRichiesta);
			String url = azione.getUrlApplicazione()+
				"?azioneRichiesta="+azioneRichiesta.getId();
			setUrlAzioneRichiesta(url);
		} catch (Throwable t) {
			log.error("selezionaAzione", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally { 
			log.infoEnd("selezionaAzione", "");
		}
		return Action.SUCCESS;
	}


	private Azione findAzione(Integer idAzioneSelezionata) {
		Cruscotto cruscotto = getCruscotto();
		cruscotto.initLookupAzioneCaches();
		Azione azione = cruscotto.findAzione(idAzioneSelezionata);
		
		return azione;
		
//		if (azione != null) {
//			return azione;
//		}
//		
//		return serviceHandler.findAzione(idAzioneSelezionata);
	}
	
	public String selezionaAnnoEsercizio() {
		
		String idBilancioPeriodo = model.getAnnoSelezionato();
		
		try {
			log.infoStart("selezionaAnnoEsercizio", "");
			
			Integer annoSelezionato = getAnnoBilancioByIdBilancioPeriodo(idBilancioPeriodo);
			
			setupCruscotto(cruSessionHandler.getAccount(), annoSelezionato);
			
			evidenziaAnnoSelezionato(Integer.valueOf(annoSelezionato));
			cruSessionHandler.setParametro(CommonSessionParameter.EVIDENZIA_ANNO_SELEZIONATO, model.isEvidenziaAnnoSelezionato());
			
//			updateCruscotto();
		} catch (Throwable t) {
			log.error("selezionaAnnoEsercizio", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally { 
			log.infoEnd("selezionaAnnoEsercizio", "");
		}

		return Action.SUCCESS;
	}


	private Integer getAnnoBilancioByIdBilancioPeriodo(String idBilancioPeriodo)
	{
		if (cruSessionHandler.getCruscotto() != null) {
			
			String[] tmp = idBilancioPeriodo.split("\\-");
			int idBilancio = Integer.parseInt(tmp[0]);
			int idPeriodo = Integer.parseInt(tmp[1]);
			
			for(AnnoBilancio annoBilancio: cruSessionHandler.getCruscotto().getAnniBilancio()) {
				if (annoBilancio.getIdBilancio().equals(idBilancio) && annoBilancio.getIdPeriodo().equals(idPeriodo))
					return annoBilancio.getAnno();
			}
		}
		
		return null;
	}
	
	
	public void evidenziaAnnoSelezionato(Integer annoSelezionato) throws Exception {
		
		if (annoSelezionato!=null) {
			
			// CR 726A - CRU - Font anno di bilancio
			// visualizzare in colore ROSSO molto visibile l'anno di esercizio nel caso in cui sia stato selezionato  
			// l'anno precedente all'ultimo anno di bilancio definito.
			
			// Il messaggio deve essere visualizzato solo se l'utente seleziona un anno di bilancio minore dell'anno corrente.
			int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);

			if (Integer.valueOf(annoCorrente).compareTo(annoSelezionato) > 0){
				model.setEvidenziaAnnoSelezionato(true);
				return;
			}
		}
		
		model.setEvidenziaAnnoSelezionato(false);
	}

	
	public List<Account> getAccounts() {
		return cruSessionHandler.getListaAccount();
	}
	
	public Cruscotto getCruscotto() {
		return cruSessionHandler.getCruscotto();
	}

	/**
	 * @return the urlAzioneRichiesta
	 */
	public String getUrlAzioneRichiesta() {
		return urlAzioneRichiesta;
	}

	/**
	 * @param urlAzioneRichiesta the urlAzioneRichiesta to set
	 */
	public void setUrlAzioneRichiesta(String urlAzioneRichiesta) {
		this.urlAzioneRichiesta = urlAzioneRichiesta;
	}
	
}
