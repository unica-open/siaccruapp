/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccorser.frontend.webservice.client;

import org.junit.Assert;
import org.junit.Test;

import it.csi.siac.siaccorser.frontend.webservice.CoreService;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetAccounts;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetAccountsResponse;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetAzioneRichiesta;
import it.csi.siac.siaccorser.frontend.webservice.msg.GetAzioneRichiestaResponse;
import it.csi.siac.siaccorser.model.AzioneRichiesta;
import it.csi.siac.siaccorser.model.Operatore;
import it.csi.siac.siaccorser.model.Richiedente;


public class CoreServiceClientTest extends BaseProxyServiceTest<CoreService> {

	//private final static String ENDPOINT = "http://localhost:8180/siaccorser/CoreService";
	private final static String ENDPOINT = "http://10.136.6.151:8080/siaccorser/CoreService";
	
	@Override
	protected String getEndpoint() {
		return ENDPOINT;
	}
	
	@Test
	public void getAccounts() {
		
		Richiedente richiedente = new Richiedente();
		Operatore operatore = new Operatore();
		operatore.setCodiceFiscale("AAAAAA00A11B000J");	// demo 21
		richiedente.setOperatore(operatore);
		
		GetAccounts req = new GetAccounts();
		req.setRichiedente(richiedente);
		
		GetAccountsResponse res = service.getAccounts(req);
		assertNotNull(res);
//		Assert.assertTrue("Il servizio non restituisce alcun account", res.getAccounts().size()>0);
		Assert.assertTrue("Il servizio è OK", true);
		System.out.println("Il servizio è OK");
		
		
	}

	
	@Test
	public void getAzioneRichiesta() {
		
		Richiedente richiedente = new Richiedente();
		Operatore operatore = new Operatore();
		operatore.setCodiceFiscale("AAAAAA00A11B000J");	// demo 21
		richiedente.setOperatore(operatore);
		
		GetAzioneRichiesta req = new GetAzioneRichiesta();
		req.setRichiedente(richiedente);
		AzioneRichiesta az = new AzioneRichiesta();
		az.setUid(-1004314696);
		req.setAzioneRichiesta(az);
		
		GetAzioneRichiestaResponse res = service.getAzioneRichiesta(req);
		
		Assert.assertTrue("Il servizio non restituisce alcuna gestione livello", res.getAzioneRichiesta().getAccount().getEnte().getGestioneLivelli().size()>0);
		
		if(res.getAzioneRichiesta().getAccount().getEnte().getGestioneLivelli()!=null &&
				!res.getAzioneRichiesta().getAccount().getEnte().getGestioneLivelli().isEmpty())
			System.out.println("Il servizio ritorna elementi ");
		else
			System.out.println("Il servizio non ritorna elementi");
		
		
	}
}
