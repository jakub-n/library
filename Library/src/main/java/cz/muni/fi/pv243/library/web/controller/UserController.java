package cz.muni.fi.pv243.library.web.controller;

/*
 * Copyright 2012 Oracle and/or its affiliates.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developers.sun.com/license/berkeley_license.html
 */

//import com.forest.web.util.JsfUtil;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import cz.muni.fi.pv243.library.entity.User;

@SessionScoped
@ManagedBean
public class UserController implements Serializable {

	// @Inject
	// CustomerController customerController;


	private static final long serialVersionUID = 1L;

	User user;

	// @EJB
	// private com.forest.ejb.UserBean ejbFacade;
	private String password;
	private String username;

	public UserController() {
	}

	/**
	 * Login method based on <code>HttpServletRequest</code> and security realm
	 */
	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		String result="";
		
		try {
			request.login(this.getUsername(), this.getPassword());
			System.out.println("Prihlasen " + username);
		} catch (ServletException ex) {
			// TODO zalogovat
			System.out.println("Neprihlasen ");
		}

		/*
		 * try { request.login( this.getUsername(), this.getPassword());
		 * 
		 * JsfUtil.addSuccessMessage( JsfUtil.getStringFromBundle(BUNDLE,
		 * "Login_Success"));
		 * 
		 * this.user = ejbFacade.getUserByEmail(getUsername());
		 * this.getAuthenticatedUser();
		 * 
		 * if (isAdmin()) { result = "/admin/index"; } else { result = "/index";
		 * } } catch (ServletException ex) {
		 * Logger.getLogger(UserController.class.getName()) .log(Level.SEVERE,
		 * null, ex); JsfUtil.addErrorMessage(
		 * JsfUtil.getStringFromBundle(BUNDLE, "Login_Failed"));
		 * 
		 * result = "login"; }
		 */

		return result;
	}

	/*
	 * public com.forest.ejb.UserBean getEjbFacade() { return ejbFacade; }
	 * 
	 * @Produces
	 * 
	 * @LoggedIn public Person getAuthenticatedUser() { return user; }
	 * 
	 * 
	 * 
	 * public boolean isAdmin() { for (Groups g : user.getGroupsList()) { if
	 * (g.getName() .equals("ADMINS")) { return true; } }
	 * 
	 * return false; }
	 * 
	 * public String goAdmin() { if (isAdmin()) { return "/admin/index"; } else
	 * { return "index"; } }
	 */
	
	public boolean isLogged() { return (getUser() == null) ? false : true; }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}
}
