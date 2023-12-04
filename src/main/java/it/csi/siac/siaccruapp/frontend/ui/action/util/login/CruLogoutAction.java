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
import it.csi.siac.siaccommonapp.util.logout.LogoutHandler;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class CruLogoutAction extends LogoutAction {
	
	private static final long serialVersionUID = 8205031471629896810L;
	
	//task-122
	@Resource(name = "logoutHandlerBean")
	private LogoutHandler logoutHandler;

	public String getLogoutUrl() {

		return logoutHandler.getLogoutUrl();
	}

}

