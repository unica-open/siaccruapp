/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.action.util.login;

import java.util.Map;

import it.csi.siac.siaccommonapp.util.login.LoginHandler;
import it.csi.siac.siaccorser.model.Operatore;


public class TestLoginCruscottoHandler extends LoginHandler {

	private static final long serialVersionUID = 6589268336444125090L;

	/**
	 * @return the operatore
	 */
	@Override
	public Operatore getOperatore(Map<String, Object> session) {
		String codiceFiscale = (String)session.get("it.csi.siac.siaccruapp.login.test.codiceFiscaleOperatore");
		if (codiceFiscale==null) {
//			throw new IdentitaDigitaleNonConformeException("identita");
			// Demo 20
//			codiceFiscale = "AAAAAA00B77B000F";
			// Demo 21
//			codiceFiscale = "AAAAAA00A11B000J";
			// Demo 24
			codiceFiscale = "AAAAAA00A11E000M";
			// Demo 22
//			codiceFiscale = "AAAAAA00A11C000K";
			// Demo 23
//			codiceFiscale = "AAAAAA00A11D000L";
			// Demo 30
//			codiceFiscale = "AAAAAA00A11K000S";
			// Demo 31
//			codiceFiscale = "AAAAAA00A11L000T";
		}
		Operatore operatore = new Operatore();
		operatore.setNome("Raffaela");
		operatore.setCognome("Montuori");
		operatore.setCodiceFiscale(codiceFiscale);
		return operatore;
	}
	
	

}
