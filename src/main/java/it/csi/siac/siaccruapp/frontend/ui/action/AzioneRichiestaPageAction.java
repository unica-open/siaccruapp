/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.opensymphony.xwork2.Action;

import it.csi.siac.siaccorser.model.AzioneRichiesta;
import it.csi.siac.siaccorser.model.VariabileProcesso;
import it.csi.siac.siaccorser.model.errore.ErroreCore;
import it.csi.siac.siaccruapp.frontend.ui.exception.AccountNonSelezionatoException;
import it.csi.siac.siaccruapp.frontend.ui.model.AzioneRichiestaPageModel;

/**
 * Action per la gestione della pagina dell'azione richiesta
 * 
 * @author Spin Servizi per l'Innovazione
 *
 */
@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AzioneRichiestaPageAction extends GenericCruAction<AzioneRichiestaPageModel> {

	/** Per la serializzazione */
	private static final long serialVersionUID = -8016902918068595669L;
	
	private AzioneRichiesta azione;
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
		if (cruSessionHandler.getAccount()==null) {
			throw new AccountNonSelezionatoException();
		}
	}

	
	@Override
	public String execute() {

		try {
			log.infoStart("execute");
			azione = new AzioneRichiesta();
			azione.setUid(Integer.parseInt(model.getAzioneRichiesta()));
			azione = serviceHandler.getAzioneRichiesta(azione);
			
			VariabileProcesso variabile = getVariabileProcesso(azione,VariabileProcesso.NOME_VARIABILE_DESCRIZIONE);
			if (variabile.getValore()==null) {
				variabile.setValore("");
			}
			model.setDescrizione(variabile.getValore().toString());

			variabile = getVariabileProcesso(azione,VariabileProcesso.NOME_VARIABILE_DESCRIZIONE_BREVE);
			if (variabile.getValore()==null) {
				variabile.setValore("");
			}
			model.setDescrizioneBreve(variabile.getValore().toString());
			
			variabile = getVariabileProcesso(azione,VariabileProcesso.NOME_VARIABILE_ANNO_ESERCIZIO_PROCESSO);
			if (variabile.getValore()==null) {
				variabile.setValore("");
			}
			model.setAnnoBilancio(variabile.getValore().toString());

			variabile = getVariabileProcesso(azione,VariabileProcesso.NOME_VARIABILE_SAC_PROCESSO);
			if (variabile.getValore()==null) {
				variabile.setValore("");
			}
			model.setStrutture(variabile.getValore().toString());
		} catch (Throwable t) {
			log.error("execute", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally { 
			log.infoEnd("execute");
		}
		return Action.SUCCESS;
	}

	public String eseguiAzioneRichiesta() {

		try {
			log.infoStart("eseguiAzioneRichiesta");
			azione = new AzioneRichiesta();
			azione.setUid(Integer.parseInt(model.getAzioneRichiesta()));
			azione = serviceHandler.getAzioneRichiesta(azione);
			VariabileProcesso variabile = getVariabileProcesso(azione,VariabileProcesso.NOME_VARIABILE_DESCRIZIONE);
			variabile.setValore(model.getDescrizione());
			variabile = getVariabileProcesso(azione,VariabileProcesso.NOME_VARIABILE_DESCRIZIONE_BREVE);
			variabile.setValore(model.getDescrizioneBreve());
			variabile = getVariabileProcesso(azione,VariabileProcesso.NOME_VARIABILE_ANNO_ESERCIZIO_PROCESSO);
			variabile.setValore(model.getAnnoBilancio());
			variabile = getVariabileProcesso(azione,VariabileProcesso.NOME_VARIABILE_SAC_PROCESSO);
			variabile.setValore(model.getStrutture());
			serviceHandler.execAzioneRichiesta(azione);
		} catch (Throwable t) {
			log.error("eseguiAzioneRichiesta", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally { 
			log.infoStart("eseguiAzioneRichiesta");
		}
		return Action.SUCCESS;
	}


	/**
	 * @return the azione
	 */
	public AzioneRichiesta getAzione() {
		return azione;
	}


	/**
	 * @param azione the azione to set
	 */
	public void setAzione(AzioneRichiesta azione) {
		this.azione = azione;
	}
	
	private VariabileProcesso getVariabileProcesso(AzioneRichiesta azioneRichiesta, String nome) {
		for(VariabileProcesso variabile:azioneRichiesta.getVariabiliProcesso() ) {
			if (variabile.getNome().equals(nome)) {
				return variabile;
			}
		}
		VariabileProcesso variabile = new VariabileProcesso();
		variabile.setNome(nome);
		azioneRichiesta.getVariabiliProcesso().add(variabile);
		return variabile;
	}
}
