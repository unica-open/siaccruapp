/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.siac.siaccommonapp.action.GenericAction;
import it.csi.siac.siaccommonapp.handler.session.CommonSessionParameter;
import it.csi.siac.siaccommonapp.handler.session.SessionHandler;
import it.csi.siac.siaccommonapp.util.log.LogWebUtil;
import it.csi.siac.siaccorser.model.Account;
import it.csi.siac.siaccorser.model.AnnoBilancio;
import it.csi.siac.siaccorser.model.Cruscotto;
import it.csi.siac.siaccorser.model.Operatore;
import it.csi.siac.siaccorser.model.errore.ErroreCore;
import it.csi.siac.siaccruapp.frontend.ui.exception.UtenteNonAbilitatoException;
import it.csi.siac.siaccruapp.frontend.ui.handler.service.ServiceHandler;
import it.csi.siac.siaccruapp.frontend.ui.handler.session.CruSessionHandler;
import it.csi.siac.siaccruapp.frontend.ui.model.GeneriCruModel;


public abstract class GenericCruAction<M extends GeneriCruModel> extends GenericAction<M> {
	
	private static final long serialVersionUID = -3258690966997691587L;
	
	@Autowired protected transient ServiceHandler serviceHandler;
	
	protected CruSessionHandler cruSessionHandler;

	/** Utility per il log */
	protected transient LogWebUtil log = new LogWebUtil(this.getClass());

	@PostConstruct
	public void init() {
		cruSessionHandler = (CruSessionHandler) sessionHandler;
		serviceHandler.setSessionHandler(cruSessionHandler);
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	


	/**
	 * @param sessionHandler the sessionHandler to set
	 */
	public void setSessionHandler(SessionHandler sessionHandler) {
		this.cruSessionHandler = (CruSessionHandler) sessionHandler;
	}

	/**
	 * @param serviceHandler the serviceHandler to set
	 */
	public void setServiceHandler(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
	}



	@Override
	public void prepare() throws Exception {

		initModel();
		cruSessionHandler.setSession(session);
		initOperatore();
		initListaAccount();
		initAccount();
		
		initEvidenziaAnno();
	}

	protected void initModel() {
		if (model == null) {
			model = instantiateNewModel();
		}
	}
	
	
	public void initOperatore() {
		
		log.infoStart("initOperatore");
		Operatore login = null;
		try {
			login = loginHandler.getOperatore(session);
		} catch (Throwable t) {
			log.error("initOperatore", t);
		}
		
		Operatore operatore = null;
		try {
			operatore = cruSessionHandler.getOperatore();
			log.logXmlTypeObject(operatore, "operatore");;
			if (operatore==null) {
				operatore = login;
				cruSessionHandler.setOperatore(operatore);
			} else if (!operatore.getCodiceFiscale().equals(login.getCodiceFiscale())) {
				cruSessionHandler.cleanAll();
				operatore = login;
				cruSessionHandler.setOperatore(operatore);
			}
		} catch (Throwable t) {
			log.error("initOperatore", t);
		} finally {
			log.infoEnd("initOperatore");
		}
	}
	
	public void initListaAccount() {
		List<Account> listaAccount = null;
		try {
			log.infoStart("initListaAccount");
			listaAccount = cruSessionHandler.getListaAccount();
			if (listaAccount==null) {
				listaAccount = serviceHandler.getAccounts();
				
				sort(listaAccount);
				
				cruSessionHandler.setListaAccount(listaAccount);
			}
			if (listaAccount.isEmpty()) {
				throw new UtenteNonAbilitatoException();
			}
		} catch (Throwable t) {
			log.error("initListaAccount", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally {
			log.infoEnd("initListaAccount");
		}
	}
	
	private void sort(List<Account> listaAccount) {
		Collections.sort(listaAccount, new Comparator<Account>(){
			@Override
			public int compare(Account a1, Account a2) {
				return a1 == null ? -1 : a1.getCodice().compareTo(a2.getCodice());
			}});		
	}

	public void initAccount() {
		try {
			log.infoStart("initAccount");
			if (getAccount()==null) {
				List<Account> listaAccount = cruSessionHandler.getListaAccount();
				if (listaAccount.size()==1) {
					setupCruscotto(listaAccount.get(0));
				}
			}
		} catch (Throwable t) {
			log.error("initAccount", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally {
			log.infoEnd("initAccount");
		}
	}
	
	protected void initEvidenziaAnno() {
		log.infoStart("initEvidenziaAnno");
		Boolean evidenziaAnnoSelezionato = cruSessionHandler.getParametro(CommonSessionParameter.EVIDENZIA_ANNO_SELEZIONATO);
		model.setEvidenziaAnnoSelezionato(Boolean.TRUE.equals(evidenziaAnnoSelezionato));
		log.infoEnd("initEvidenziaAnno");
	}
	
	protected Cruscotto setupCruscotto(Account account) {
		String annoBilancio = System.getProperty("init.annoBilancio");

		return setupCruscotto(
				account, 
				annoBilancio != null ? Integer.parseInt(annoBilancio) : Calendar.getInstance().get(Calendar.YEAR));
	}
	
	protected Cruscotto setupCruscotto(Account account, Integer annoBilancio) {
		Cruscotto cruscotto = null;
		try {
			log.infoStart("setupCruscotto");
			cruscotto = serviceHandler.setupCruscotto(account, annoBilancio);
			cruSessionHandler.setAccount(account);
			cruSessionHandler.setCruscotto(cruscotto);
			
			model.setEvidenziaAnnoSelezionato(false);
			cruSessionHandler.setParametro(CommonSessionParameter.EVIDENZIA_ANNO_SELEZIONATO, null);
		} catch (Throwable t) {
			log.error("setupCruscotto", t);
			model.addErrore(ErroreCore.ERRORE_DI_SISTEMA.getErrore(t.getMessage()));
		} finally {
			log.infoEnd("setupCruscotto");
		}
		return cruscotto;
	}
	
	public Operatore getOperatore() {
		return cruSessionHandler.getOperatore();
		
	}

	public Account getAccount() {
		return cruSessionHandler.getAccount();
		
	}

	public List<AnnoBilancio> getAnniBilancio() {
		List<AnnoBilancio> anniBilancio = new ArrayList<AnnoBilancio>();
		if (cruSessionHandler.getCruscotto() != null) {
			anniBilancio = cruSessionHandler.getCruscotto().getAnniBilancio();
		}
		return anniBilancio;
	}
	
	public String getAnnoBilancioCorrente() {
		return cruSessionHandler.getCruscotto().getAnnoBilancio().getDescrizione();

	}
	
	
	public boolean valorizzaCheckbox(String nomeParametro, Boolean valueCheckHidden){
		
		
		
		HttpServletRequest req = ServletActionContext.getRequest();
		boolean risultato = false;
		
		if(null==req.getParameter(nomeParametro)){
			
			if(valueCheckHidden!=null && valueCheckHidden)
				risultato = valueCheckHidden;
			else
				risultato = false;
		}else if("true".equalsIgnoreCase(req.getParameter(nomeParametro))){
			
			risultato = true;
			
		}else if("false".equalsIgnoreCase(req.getParameter(nomeParametro))){
			
		    risultato = false;
		}
		
		
		log.debug("valorizzaCheckbox", "risultato:" + risultato); 
		return risultato;
		
	}
	

}
