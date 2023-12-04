/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.handler.session;

import it.csi.siac.siaccommonapp.handler.session.SessionParameter;

/**
 * Enumeration contenente i parametri della sessione.
 * 
 *
 */
public enum CruSessionParameter implements SessionParameter {
	
	
	ELENCO_OPASINC,
	ID_AZIONE_OPASINC,
	ELENCO_DETTAGLIO_OPASINC,
	ID_OPASINC,
	CRUSCOTTO("it.csi.siac.siaccruapp.session.cruscotto", true),
	LISTA_ACCOUNT("it.csi.siac.siaccruapp.session.listaAccount", false),

	
	ATTIVITA_PENDENTI_ID_AZIONE("it.csi.siac.siaccruapp.session.attivitaPendenti.idAzione", true),
	ATTIVITA_PENDENTI_TOTALE("it.csi.siac.siaccruapp.session.attivitaPendenti.totale", true),
	ATTIVITA_PENDENTI_OFFSET("it.csi.siac.siaccruapp.session.attivitaPendenti.offset", true),
	ATTIVITA_PENDENTI_SIZE("it.csi.siac.siaccruapp.session.attivitaPendenti.size", true),
	//SIAC-6884
	UTENTE_VARIAZIONE_DECENTRATO,
	ID_AZIONE_PER_UTENTE_VARIAZIONE_DECENTRATA,
	
	//SIAC-7544
	DATA_A_OPASINC,
	DATA_DA_OPASINC,
	
	//SIAC-7537
	FLAG_ALTRI_UTENTI_OPASINC,
	HIDDEN_FLAG_ALTRI_UTENTI_OPASINC,
	STATO_OPASINC,
	
	//SIAC-8218
	PAGINA_ELENCO_OPERAZIONE_ASINCRONE,
	
	
	;
	
	
		
	
	private String paramName;
	private boolean isEliminabile;
	private boolean isEliminabileRedirectToCruscotto;

	/**
	 * Costruttore vuoto di default
	 */
	private CruSessionParameter(){
		this.paramName = this.name();
		this.isEliminabile = true;
		isEliminabileRedirectToCruscotto = true;
	}
	
	/**
	 * Costruttore definente l'eliminabilit&agrave;.
	 * 
	 * @param isEliminabile l'eliminabilit&agrave; del parametro
	 */
	private CruSessionParameter(Boolean isEliminabile) {
		this.paramName = this.name();
		this.isEliminabile = isEliminabile;
	}
	
	/**
	 * Costruttore utilizzante il nome del parametro e la condizione di eliminabilit&agrave;.
	 * 
	 * @param paramName		il nome del parametro. Nel caso sia impostato a <code>null</code>, viene considerato come
	 * 						nome del parametro quanto ottenuto dal metodo {@link Enum#name()}
	 * @param isEliminabile l'eliminabilit&agrave; del parametro
	 */
	private CruSessionParameter(String paramName, Boolean isEliminabile){
		this(paramName, isEliminabile, true);
	}
	
	
	private CruSessionParameter(String paramName, Boolean isEliminabile, Boolean isEliminabileRedirectToCruscotto){			
		this.paramName = paramName;
		this.isEliminabile = isEliminabile;
		this.isEliminabileRedirectToCruscotto = isEliminabileRedirectToCruscotto;
	}

	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @return the isEliminabile
	 */
	@Override
	public boolean isEliminabile() {
		return isEliminabile;
	}

//	/**
//	 * @return the isEliminabileRedirectToCruscotto
//	 */
	public Boolean isEliminabileRedirectToCruscotto() {
		return isEliminabileRedirectToCruscotto;
	}

	/**
	 * Ottengo un SessionParam a partire dal paramName.
	 * 
	 * @param paramName il nome del parametro
	 * 
	 * @return il SessionParam corrispondente, se censito; <code>null</code> altrimenti
	 */
	public static CruSessionParameter byParamName(String paramName) {
		for(CruSessionParameter sp : CruSessionParameter.values()){
			if(sp.getParamName().equals(paramName)){
				return sp;
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return getParamName();
	}
	
	

}