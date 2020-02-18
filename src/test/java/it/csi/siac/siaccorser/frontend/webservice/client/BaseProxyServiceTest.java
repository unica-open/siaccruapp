/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccorser.frontend.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.WebService;

import org.junit.Before;
import org.springframework.core.GenericTypeResolver;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;

import it.csi.siac.siacbilser.BaseJunit4TestCase;

/**
 * Classe base di test per i serviz&icirc;.
 * 
 * @author Marchino Alessandro
 * @version 1.0.0 - 01/08/2019
 * @param <S> la tipizzazione del servizio
 *
 */
public abstract class BaseProxyServiceTest <S> extends BaseJunit4TestCase {
	
	/** Il servizio */
	protected S service;
	protected Class<S> serviceInterface;
	
	/**
	 * Setup del client per il WS.
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		createServiceProxy();
	}
	
	
	/**
	 * Creazione del proxy del servizio
	 * 
	 * @throws MalformedURLException in caso di URL non correttamente formattato
	 */
	@SuppressWarnings("unchecked")
	protected void createServiceProxy() throws MalformedURLException {
		this.serviceInterface = resolveServiceInterface();
		String endpoint = getEndpoint();
		String namespaceUri = getNamespaceUri();
		String getServiceName = getServiceName();
		
		JaxWsPortProxyFactoryBean proxyFactoryBean = initProxyFactory();
		proxyFactoryBean.setServiceInterface(serviceInterface);
		proxyFactoryBean.setWsdlDocumentUrl(new URL(endpoint + "?wsdl"));
		proxyFactoryBean.setNamespaceUri(namespaceUri);
		proxyFactoryBean.setServiceName(getServiceName);
		proxyFactoryBean.setEndpointAddress(endpoint);
		
		// Inizializzazione
		proxyFactoryBean.afterPropertiesSet();
		this.service = (S) proxyFactoryBean.getObject();
		
		if(this.service == null) {
			throw new IllegalStateException("Il servizio non e' stato creato correttamente");
		}
	}
	
	/**
	 * Inizializzazione della factory.
	 * 
	 * @return la factory creata
	 */
	protected JaxWsPortProxyFactoryBean initProxyFactory() {
		return new JaxWsPortProxyFactoryBean();
	}
	
	/**
	 * Risolve l'interfaccia del servizio
	 * 
	 * @return la classe rappresentante l'interfaccia
	 */
	@SuppressWarnings("unchecked")
	protected Class<S> resolveServiceInterface() {
		Class<?>[] genericTypeArguments = GenericTypeResolver.resolveTypeArguments(this.getClass(), BaseProxyServiceTest.class);
		return (Class<S>) genericTypeArguments[0];
	}
	
	/**
	 * Ottiene il nome del servizio.
	 * 
	 * @return il nome del servizio
	 */
	protected String getServiceName() {
		return this.serviceInterface.getSimpleName();
	}
	
	/**
	 * Ottiene l'URI del namespace del servizio.
	 * 
	 * @return l'URI del servizio
	 */
	protected String getNamespaceUri() {
		WebService webService = this.serviceInterface.getAnnotation(WebService.class);
		if(webService == null) {
			throw new IllegalStateException("No annotation fonund for namespace URI");
		}
		return webService.targetNamespace();
	}
	
	/**
	 * Ottiene l'endpoint del servizio.
	 * 
	 * @return l'endpoint del servizio
	 */
	protected abstract String getEndpoint();
	
}
