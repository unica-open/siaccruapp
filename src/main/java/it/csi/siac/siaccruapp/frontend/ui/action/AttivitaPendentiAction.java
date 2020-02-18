/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.opensymphony.xwork2.Action;

import it.csi.siac.siaccorser.frontend.webservice.CoreService;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetVariazioni;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetVariazioniResponse;
import it.csi.siac.siaccorser.model.Account;
import it.csi.siac.siaccorser.model.AnnoBilancio;
import it.csi.siac.siaccorser.model.AttivitaPendente;
import it.csi.siac.siaccorser.model.Azione;
import it.csi.siac.siaccorser.model.AzioneConsentita;
import it.csi.siac.siaccorser.model.AzioneRichiesta;
import it.csi.siac.siaccorser.model.Cruscotto;
import it.csi.siac.siaccorser.model.Errore;
import it.csi.siac.siaccorser.model.GruppoAttivitaPendenti;
import it.csi.siac.siaccorser.model.ParametroAzioneRichiesta;
import it.csi.siac.siaccorser.model.Variazione;
import it.csi.siac.siaccorser.model.errore.ErroreCore;
import it.csi.siac.siaccorser.util.Costanti;
import it.csi.siac.siaccruapp.frontend.ui.exception.AccountNonSelezionatoException;
import it.csi.siac.siaccruapp.frontend.ui.handler.session.CruSessionParameter;
import it.csi.siac.siaccruapp.frontend.ui.model.AttivitaPendentiPageModel;

/**
 * Action per la gestione della pagina delle
 * attività pendenti
 * 
 * @author Spin Servizi per l'Innovazione
 *
 */
@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AttivitaPendentiAction extends GenericCruAction<AttivitaPendentiPageModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<AttivitaPendente> attivitaPendenti;
	private String urlAzioneRichiesta;
	
	
	
	@Autowired
	protected CoreService coreService;
	
	
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
		if (cruSessionHandler.getAccount()==null) {
			throw new AccountNonSelezionatoException();
		}
	}

	@Override
	public String execute() {
		String ret = Action.SUCCESS;
		try {
			log.infoStart("execute");
			model.setOffset(0);
			model.setSize(10);
			ret = update();
		} catch (Throwable t) {
			log.error("execute", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally { 
			log.infoEnd("execute");
		}
		return ret;
	}
	
	public String next() {
		String ret = Action.SUCCESS;
		try {
			log.infoStart("next");
			int totale = cruSessionHandler.getTotaleAttivitaPendenti();
			int offset = cruSessionHandler.getOffsetAttivitaPendenti();
			int size = cruSessionHandler.getSizeAttivitaPendenti();
			model.setIdAzione(cruSessionHandler.getIdAzioneAttivitaPendenti());
			model.setTotale(totale);
			if (offset+size<totale) {
				model.setOffset(offset+size);
			} else {
				model.setOffset(offset);
			}
			model.setSize(size);
			ret = update();
		} catch (Throwable t) {
			log.error("next", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally { 
			log.infoEnd("next");
		}
		return ret;
	}

	public String prev() {
		String ret = Action.SUCCESS;
		try {
			log.infoStart("prev");
			int totale = cruSessionHandler.getTotaleAttivitaPendenti();
			int offset = cruSessionHandler.getOffsetAttivitaPendenti();
			int size = cruSessionHandler.getSizeAttivitaPendenti();
			model.setIdAzione(cruSessionHandler.getIdAzioneAttivitaPendenti());
			model.setTotale(totale);
			if (offset-size>0) {
				model.setOffset(offset-size);
			} else {
				model.setOffset(0);
			}
			model.setSize(size);
			ret = update();
		} catch (Throwable t) {
			log.error("prev", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally { 
			log.infoEnd("prev");
		}
		return ret;
	}

	public String first() {
		String ret = Action.SUCCESS;
		try {
			log.infoStart("first");
			model.setIdAzione(cruSessionHandler.getIdAzioneAttivitaPendenti());
			model.setTotale(cruSessionHandler.getTotaleAttivitaPendenti());
			model.setOffset(0);
			model.setSize(cruSessionHandler.getSizeAttivitaPendenti());
			ret = update();
		} catch (Throwable t) {
			log.error("first", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally { 
			log.infoEnd("first");
		}
		return ret;
	}

	public String last() {
		String ret = Action.SUCCESS;
		try {
			log.infoStart("last");
			int totale = cruSessionHandler.getTotaleAttivitaPendenti();
			int size = cruSessionHandler.getSizeAttivitaPendenti();
			int pagine = (totale-1)/size+1;
			model.setIdAzione(cruSessionHandler.getIdAzioneAttivitaPendenti());
			model.setTotale(totale);
			model.setOffset((pagine-1)*size);
			model.setSize(size);
			ret = update();
		} catch (Throwable t) {
			log.error("last", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally { 
			log.infoEnd("last");
		}
		return ret;
	}

	public String update() {
		try {
			log.infoStart("update");
			int idAzione = model.getIdAzione();
			int totale = model.getTotale();
			int offset = model.getOffset();
			int size = model.getSize();
			int start = model.getStart();
			int stop = model.getStop();
			if (idAzione==0) {
				throw new RuntimeException("Id azione per attività pendenti non inizializzato");
			}
			if (totale==0) {
				throw new RuntimeException("Totale per attività pendenti non inizializzato");
			}
			cruSessionHandler.setIdAzioneAttivitaPendenti(idAzione);
			cruSessionHandler.setTotaleAttivitaPendenti(totale);
			cruSessionHandler.setOffsetAttivitaPendenti(offset);
			cruSessionHandler.setSizeAttivitaPendenti(size);
			Cruscotto cruscotto = cruSessionHandler.getCruscotto();
			Integer annoBilancio = cruscotto.getAnnoBilancio().getAnno();
			Integer idEnteProprietario = cruSessionHandler.getAccount().getEnte().getUid();
			
			/*
			 * SIAC-6884 Variazioni decentrate
			 */
			Boolean idDecentrato = cruSessionHandler.getParametro(CruSessionParameter.UTENTE_VARIAZIONE_DECENTRATO);
			Integer idAzionePerUtenteVariazioneDecentata = cruSessionHandler.getParametro(CruSessionParameter.ID_AZIONE_PER_UTENTE_VARIAZIONE_DECENTRATA);
			if(idDecentrato && idAzionePerUtenteVariazioneDecentata!= null && idAzione==idAzionePerUtenteVariazioneDecentata){
			attivitaPendenti = new ArrayList<AttivitaPendente>();
			//Nel caso di utente per variazioni decentrato ricaviamo le sac associate
			Account account = serviceHandler.findAccountByIdUtente(cruSessionHandler.getRichiedente(),annoBilancio);
			if(account!= null && account.getStruttureAmministrativeContabili()!= null
					&& !account.getStruttureAmministrativeContabili().isEmpty()){
				//LISTA ID STRUTTURA AMMINISTRATIVA
				List<Integer> idSacList = new ArrayList<Integer>();
				for(int n=0; n<account.getStruttureAmministrativeContabili().size();n++){
					idSacList.add(account.getStruttureAmministrativeContabili().get(n).getUid());
				}
				
				GetVariazioni varRequest = new GetVariazioni();
				varRequest.setSacIdList(idSacList);
				varRequest.setFirst(model.getStart()-1);
				varRequest.setMaxresult(model.getSize());
				varRequest.setAnnoBilancio(annoBilancio);
				varRequest.setRichiedente(cruSessionHandler.getRichiedente());
				GetVariazioniResponse response = coreService.getVariazioniBySac(varRequest);
				if(response != null){
					cruSessionHandler.setTotaleAttivitaPendenti(response.getTotale().intValue());
					model.setTotale(response.getTotale().intValue());
					List<Variazione> variazioni = response.getVariazioni();
					if(variazioni!= null && !variazioni.isEmpty()){
						for(int f=0;f<variazioni.size();f++){
							AttivitaPendente ap = new AttivitaPendente();
							ap.setDataAperturaProposta(variazioni.get(f).getDataApertura());
							ap.setDataChiusuraProposta(variazioni.get(f).getDataChiusura());
							ap.setDirezioneProponente(variazioni.get(f).getDirezione());
							ap.setDescrizione(variazioni.get(f).getNumeroVariazione() + " " + variazioni.get(f).getDescrizione());
							ap.setId("" + variazioni.get(f).getUid());
							Azione azione = new Azione();
							azione.setUid(variazioni.get(f).getUid());
							ap.setIdAttivita(""+variazioni.get(f).getUid());
							ap.setAzione(azione);
							attivitaPendenti.add(ap);
						}
					}
				}
			}
			
			
			}else{
				for (AzioneConsentita azioneConsentita:cruscotto.getAzioniConsentite()) {
					if (azioneConsentita.getAzione().getUid() == model.getIdAzione()) {
						attivitaPendenti =serviceHandler.getAttivitaPendenti(azioneConsentita, annoBilancio,idEnteProprietario, offset, stop-start+1);
					}
				}
			}
			
			
			
			if(attivitaPendenti!=null && !attivitaPendenti.isEmpty()){
				model.setTitolo((attivitaPendenti.get(0)).getAzione().getTitolo());
			}else{
				
				List<Errore> errori = new ArrayList<Errore>();
				errori.add(ErroreCore.ERRORE_DI_SISTEMA.getErrore("Errore nella lettura dell'attività pendente selezionata"));
				model.addErrori(errori);
				
				return "errorRuntime";
			}
				
			
		} catch (Throwable t) {
			log.error("update", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally { 
			log.infoEnd("update");
		}
		return Action.SUCCESS;
	}


	public String size() {
		int size = model.getSize();
		if (size > 0) {
			model.setSize(size);
		} else {
			model.setSize(10);
		}
		return execute();
	}

	public String selezionaAttivita() {
		
		try {
			log.infoStart("size");
			Cruscotto cruscotto = cruSessionHandler.getCruscotto();
			if (cruscotto!=null) {
				for (GruppoAttivitaPendenti gruppoAttivitaPendenti:cruscotto.getGruppiAttivitaPendenti()) {
					Boolean idDecentrato = cruSessionHandler.getParametro(CruSessionParameter.UTENTE_VARIAZIONE_DECENTRATO);
					Integer idAzionePerUtenteVariazioneDecentata = cruSessionHandler.getParametro(CruSessionParameter.ID_AZIONE_PER_UTENTE_VARIAZIONE_DECENTRATA);
					AzioneRichiesta azioneRichiesta = new AzioneRichiesta();
					/*
					 * SIAC 6884 Variazioni decentrate
					 */
					if(idDecentrato	&& gruppoAttivitaPendenti.getAzione()!= null && 
					Costanti.CODICE_AZIONE_VARIAZIONE_DECENTRATO.equals(gruppoAttivitaPendenti.getAzione().getNome())){
						model.setAzioneSelezionata(idAzionePerUtenteVariazioneDecentata);
					}
					
					if (gruppoAttivitaPendenti.getAzione().getUid() == model.getAzioneSelezionata()) {
							Azione azione = gruppoAttivitaPendenti.getAzione();
							
							azioneRichiesta.setIdAttivita(model.getIdAttivita());
							azioneRichiesta.setData(new Date());
							azioneRichiesta.setDaCruscotto(false);
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

							azioneRichiesta = serviceHandler.saveAzioneRichiesta(azioneRichiesta);
							urlAzioneRichiesta = azione.getUrlApplicazione()+
								"?azioneRichiesta="+azioneRichiesta.getId();
							setUrlAzioneRichiesta(urlAzioneRichiesta);
						}
					
					
					
					
					
				}
			}
		} catch (Throwable t) {
			log.error("size", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally { 
			log.infoEnd("size");
		}
		return Action.SUCCESS;
	}

	/**
	 * @return the attivitaPendenti
	 */
	public List<AttivitaPendente> getAttivitaPendenti() {
		return attivitaPendenti;
	}


	/**
	 * @param attivitaPendenti the attivitaPendenti to set
	 */
	public void setAttivitaPendenti(List<AttivitaPendente> attivitaPendenti) {
		this.attivitaPendenti = attivitaPendenti;
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
