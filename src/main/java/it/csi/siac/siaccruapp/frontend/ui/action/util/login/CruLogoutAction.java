/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.siac.siaccruapp.frontend.ui.action.util.login;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import it.csi.siac.siaccommonapp.action.LogoutAction;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class CruLogoutAction extends LogoutAction {

	private static final long serialVersionUID = 1L;

	@Resource(name = "logoutUrl")    
	private String logoutUrl;
	
	/**
	 * @return the logoutUrl
	 */
	public String getLogoutUrl() {
		return logoutUrl;
	}

	/**
	 * @param logoutUrl
	 *            the logoutUrl to set
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
}