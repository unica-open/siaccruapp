/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.model;

/**
 * Model per la gestione della Home Page
 * 
 * @author Spin Servizi per l'Innovazione
 *
 */
public class HomePageModel extends GeneriCruModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7336733656538652675L;
	
	private String accountSelezionato;
	private String notificaPendenteSelezionata;
	private String attivitaPendenteSelezionata;
	private String azioneSelezionata;
	private String azioneRichiesta;
	private String annoSelezionato;

	private String parametriAzioneSelezionata;

	/**
	 * Gestione per la modale della dashboard
	 */
	private String modaleSelezionata;
	private String apriModale="";
	private String titoloModale="";
	
	
	
	
	/**
	 * @return the apriModale
	 */
	public String getApriModale() {
		return apriModale;
	}

	/**
	 * @param apriModale the apriModale to set
	 */
	public void setApriModale(String apriModale) {
		this.apriModale = apriModale;
	}

	/**
	 * @return the modaleSelezionata
	 */
	public String getModaleSelezionata() {
		return modaleSelezionata;
	}

	/**
	 * @param modaleSelezionata the modaleSelezionata to set
	 */
	public void setModaleSelezionata(String modaleSelezionata) {
		this.modaleSelezionata = modaleSelezionata;
	}

	
	
	/**
	 * @return the titoloModale
	 */
	public String getTitoloModale() {
		return titoloModale;
	}

	/**
	 * @param titoloModale the titoloModale to set
	 */
	public void setTitoloModale(String titoloModale) {
		this.titoloModale = titoloModale;
	}

	public HomePageModel() {
		setTitolo("Home page"); 
	}

	/**
	 * @return the accountSelezionato
	 */
	public String getAccountSelezionato() {
		return accountSelezionato;
	}

	/**
	 * @param accountSelezionato the accountSelezionato to set
	 */
	public void setAccountSelezionato(String accountSelezionato) {
		this.accountSelezionato = accountSelezionato;
	}

	/**
	 * @return the notificaPendenteSelezionata
	 */
	public String getNotificaPendenteSelezionata() {
		return notificaPendenteSelezionata;
	}

	/**
	 * @param notificaPendenteSelezionata the notificaPendenteSelezionata to set
	 */
	public void setNotificaPendenteSelezionata(String notificaPendenteSelezionata) {
		this.notificaPendenteSelezionata = notificaPendenteSelezionata;
	}

	/**
	 * @return the azionePendenteSelezionata
	 */
	public String getAttivitaPendenteSelezionata() {
		return attivitaPendenteSelezionata;
	}

	/**
	 * @param azionePendenteSelezionata the azionePendenteSelezionata to set
	 */
	public void setAttivitaPendenteSelezionata(String attivitaPendenteSelezionata) {
		this.attivitaPendenteSelezionata = attivitaPendenteSelezionata;
	}

	/**
	 * @return the azioneSelezionata
	 */
	public String getAzioneSelezionata() {
		return azioneSelezionata;
	}

	/**
	 * @param azioneSelezionata the azioneSelezionata to set
	 */
	public void setAzioneSelezionata(String azioneSelezionata) {
		this.azioneSelezionata = azioneSelezionata;
	}

	/**
	 * @return the azioneRichiesta
	 */
	public String getAzioneRichiesta() {
		return azioneRichiesta;
	}

	/**
	 * @param azioneRichiesta the azioneRichiesta to set
	 */
	public void setAzioneRichiesta(String azioneRichiesta) {
		this.azioneRichiesta = azioneRichiesta;
	}

	/**
	 * @return the annoSelezionato
	 */
	public String getAnnoSelezionato() {
		return annoSelezionato;
	}

	/**
	 * @param annoSelezionato the annoSelezionato to set
	 */
	public void setAnnoSelezionato(String annoSelezionato) {
		this.annoSelezionato = annoSelezionato;
	}

	public String getParametriAzioneSelezionata() {
		return parametriAzioneSelezionata;
	}

	public void setParametriAzioneSelezionata(String parametriAzioneSelezionata) {
		this.parametriAzioneSelezionata = parametriAzioneSelezionata;
	}

}
