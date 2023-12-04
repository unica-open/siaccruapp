/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.model;


/**
 * Model per la gestione della pagina delle
 * attivita pendenti
 * 
 * @author Spin Servizi per l'Innovazione
 *
 */
public class AttivitaPendentiPageModel extends GeneriCruModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4747890203564415966L;
	
	private int idAzione = 0;
	private int totale = 0;
	private int offset = 0;
	private int size = 10;
	private int azioneSelezionata = 0;
	private String idAttivita;
	//SIAC-8332
	private int uidVariazione;
	private String tipologiaVariazione;

	public AttivitaPendentiPageModel() { 
		setTitolo("Attivita' pendenti"); 
	}

	/**
	 * @return the idAzione
	 */
	public int getIdAzione() {
		return idAzione;
	}

	/**
	 * @param idAzione the idAzione to set
	 */
	public void setIdAzione(int idAzione) {
		this.idAzione = idAzione;
	}

	/**
	 * @return the totale
	 */
	public int getTotale() {
		return totale;
	}

	/**
	 * @param totale the totale to set
	 */
	public void setTotale(int totale) {
		this.totale = totale;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the azioneSelezionata
	 */
	public int getAzioneSelezionata() {
		return azioneSelezionata;
	}

	/**
	 * @param azioneSelezionata the azioneSelezionata to set
	 */
	public void setAzioneSelezionata(int azioneSelezionata) {
		this.azioneSelezionata = azioneSelezionata;
	}

	/**
	 * @return the idAttivita
	 */
	public String getIdAttivita() {
		return idAttivita;
	}

	/**
	 * @param idAttivita the idAttivita to set
	 */
	public void setIdAttivita(String idAttivita) {
		this.idAttivita = idAttivita;
	}
	
	public int getUidVariazione() {
		return uidVariazione;
	}

	public void setUidVariazione(int uidVariazione) {
		this.uidVariazione = uidVariazione;
	}

	public String getTipologiaVariazione() {
		return tipologiaVariazione;
	}

	public void setTipologiaVariazione(String tipologiaVariazione) {
		this.tipologiaVariazione = tipologiaVariazione;
	}

	public int getStart() {
		return offset+1;
	}
	
	public int getStop() {
		if (offset+size>totale) {
			return totale;
		}
		return offset+size;
	}
	
	
	public Integer getPaginaPrec() {
		Integer cor = getPaginaCor();
		if (cor<=1)
			return null; 
		return cor - 1;
	}

	public Integer getPaginaCor() {
		return offset/size + 1;
	}

	public Integer getPaginaSuc() {
		Integer cor = getPaginaCor();
		if (totale<=cor*size)
			return null; 
		return cor+1;
	}
}
