/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.model.operazioneasinc;


import it.csi.siac.siaccorser.model.NotificaOperazioneAsincrona;
import it.csi.siac.siaccruapp.frontend.ui.model.GenericPagedModel;


/**
 * Model per la gestione della pagina delle notifiche da riportare all'utente
 * 
 * @author rmontuori
 * 
 */
public class NotifichePendentiOperazioneAsincModel extends
		GenericPagedModel<NotificaOperazioneAsincrona> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7261797294212404200L;
	private Integer idAzione;
	// uid dell'operazione asincrona
	private Integer uid;

	private String titolo;
	
	private String codiceStato;
	private String dataDa;
	private String dataA;
	private Boolean flagAltriUtenti ;
	private Boolean hiddenFlagAltriUtenti;
	
	

	/**
	 * @return the hiddenFlagAltriUtenti
	 */
	public Boolean getHiddenFlagAltriUtenti() {
		return hiddenFlagAltriUtenti;
	}

	/**
	 * @param hiddenFlagAltriUtenti the hiddenFlagAltriUtenti to set
	 */
	public void setHiddenFlagAltriUtenti(Boolean hiddenFlagAltriUtenti) {
		this.hiddenFlagAltriUtenti = hiddenFlagAltriUtenti;
	}

	/**
	 * @return the idAzione
	 */
	public Integer getIdAzione() {
		return idAzione;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setIdAzione(Integer idAzione) {
		this.idAzione = idAzione;
	}

	@Override
	public String getTitolo() {
		return titolo;
	}

	@Override
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	/**
	 * @return the codiceStato
	 */
	public String getCodiceStato() {
		return codiceStato;
	}

	/**
	 * @param codiceStato the codiceStato to set
	 */
	public void setCodiceStato(String codiceStato) {
		this.codiceStato = codiceStato;
	}

	

	/**
	 * @return the dataDa
	 */
	public String getDataDa() {
		return dataDa;
	}

	/**
	 * @param dataDa the dataDa to set
	 */
	public void setDataDa(String dataDa) {
		this.dataDa = dataDa;
	}

	/**
	 * @return the dataA
	 */
	public String getDataA() {
		return dataA;
	}

	/**
	 * @param dataA the dataA to set
	 */
	public void setDataA(String dataA) {
		this.dataA = dataA;
	}

	/**
	 * @return the flagAltriUtenti
	 */
	public Boolean getFlagAltriUtenti() {
		return flagAltriUtenti;
	}

	/**
	 * @param flagAltriUtenti the flagAltriUtenti to set
	 */
	public void setFlagAltriUtenti(Boolean flagAltriUtenti) {
		this.flagAltriUtenti = flagAltriUtenti;
	}
	

		
}
