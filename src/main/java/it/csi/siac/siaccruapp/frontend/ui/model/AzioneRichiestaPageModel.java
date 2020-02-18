/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.model;

/**
 * Model per la gestione della pagina dell'azione richiesta
 * 
 * @author Spin Servizi per l'Innovazione
 *
 */
public class AzioneRichiestaPageModel extends GeneriCruModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7255319116788855215L;
	
	private String azioneRichiesta; 
	private String descrizione;
	private String descrizioneBreve;
	private String annoBilancio;
	private String strutture;
	
	public AzioneRichiestaPageModel() {
		setTitolo("Azione richiesta"); 
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
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * @param descrizione the descrizione to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	/**
	 * @return the descrizioneBreve
	 */
	public String getDescrizioneBreve() {
		return descrizioneBreve;
	}

	/**
	 * @param descrizioneBreve the descrizioneBreve to set
	 */
	public void setDescrizioneBreve(String descrizioneBreve) {
		this.descrizioneBreve = descrizioneBreve;
	}

	/**
	 * @return the annoBilancio
	 */
	public String getAnnoBilancio() {
		return annoBilancio;
	}

	/**
	 * @param annoBilancio the annoBilancio to set
	 */
	public void setAnnoBilancio(String annoBilancio) {
		this.annoBilancio = annoBilancio;
	}

	/**
	 * @return the strutture
	 */
	public String getStrutture() {
		return strutture;
	}

	/**
	 * @param strutture the strutture to set
	 */
	public void setStrutture(String strutture) {
		this.strutture = strutture;
	}
}
