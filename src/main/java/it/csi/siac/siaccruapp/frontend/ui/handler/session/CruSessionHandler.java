/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.handler.session;

import java.util.List;

import it.csi.siac.siaccommonapp.handler.session.SessionHandler;
import it.csi.siac.siaccorser.model.Account;
import it.csi.siac.siaccorser.model.Cruscotto;

public class CruSessionHandler extends SessionHandler {

	private static final long serialVersionUID = 2689921246184509203L;
	
	public List<Account> getListaAccount() {
		return getParametro(CruSessionParameter.LISTA_ACCOUNT);
	}

	public void setListaAccount(List<Account> listaAccount) {
		setParametro(CruSessionParameter.LISTA_ACCOUNT, listaAccount);
	}

	public Cruscotto getCruscotto() {
		return getParametro(CruSessionParameter.CRUSCOTTO);
	}

	public void setCruscotto(Cruscotto cruscotto) {
		setParametro(CruSessionParameter.CRUSCOTTO, cruscotto);
	}
	
	public Integer getIdAzioneAttivitaPendenti() {
		return getParametro(CruSessionParameter.ATTIVITA_PENDENTI_ID_AZIONE);
	}

	public void setIdAzioneAttivitaPendenti(Integer idAzione) {
		setParametro(CruSessionParameter.ATTIVITA_PENDENTI_ID_AZIONE, idAzione);
	}

	public Integer getTotaleAttivitaPendenti() {
		return getParametro(CruSessionParameter.ATTIVITA_PENDENTI_TOTALE);
	}

	public void setTotaleAttivitaPendenti(Integer totale) {
		setParametro(CruSessionParameter.ATTIVITA_PENDENTI_TOTALE, totale);
	}

	public Integer getOffsetAttivitaPendenti() {
		return getParametro(CruSessionParameter.ATTIVITA_PENDENTI_OFFSET);
	}

	public void setOffsetAttivitaPendenti(Integer offset) {
		setParametro(CruSessionParameter.ATTIVITA_PENDENTI_OFFSET, offset);
	}

	public Integer getSizeAttivitaPendenti() {
		return getParametro(CruSessionParameter.ATTIVITA_PENDENTI_SIZE);
	}

	public void setSizeAttivitaPendenti(Integer size) {
		setParametro(CruSessionParameter.ATTIVITA_PENDENTI_SIZE, size);
	}

}
