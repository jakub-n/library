package cz.muni.fi.pv243.library.web.controller;

import java.io.Serializable;
import java.security.Principal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.ejb.UserManager;
import cz.muni.fi.pv243.library.entity.User;
import cz.muni.fi.pv243.library.web.util.JsfUtil;

@SessionScoped
@ManagedBean
public class UserController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String password;
	private String username;

	// TODO jakub: je package opravdu spravne pristupove pravo?
	User user;

	@Inject
	private UserManager userManager;
	
	@Inject
	private Logger log;

	/**
	 * Constructor
	 */
	public UserController() {
	}

	/**
	 * Performs login.
	 */
	public void login() {

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		try {
			Principal userPrincipal = request.getUserPrincipal();
			if (request.getUserPrincipal() != null) {
				request.logout();
			}
			request.login(username, password);
			userPrincipal = request.getUserPrincipal();
			//System.out.println("Prihlasen " + username);
			log.infof("Login: %s -->success", username);
			user = userManager.getUserByUsername(username);
			JsfUtil.addSuccessMessage("Přihlášení se zdařilo.");

		} catch (ServletException ex) {
			JsfUtil.addErrorMessage("Přihlášení se nezdařilo.");
			System.out.println("Neprihlasen, pokus byl " + username + " "
					+ password);
			log.infof("Login: %s --> unsuccessful", username);

		}

	}

	/**
	 * Performs logout.
	 * 
	 * @return navigation page
	 */
	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();

		try {
			this.user = null;
			request.logout();
			// clear the session
			((HttpSession) context.getExternalContext().getSession(false))
					.invalidate();
			log.infof("Logout: %s -->success", username);
			JsfUtil.addSuccessMessage("Odhlášení se zdařilo.");

		} catch (ServletException ex) {
			JsfUtil.addErrorMessage("Odhlášení se nezdařilo.");
			log.infof("Logout: %s -->unsuccessful", username);
		}
		return "/login";

	}

	/**
	 * Returns true if user is logged.
	 * 
	 * @return true if user is logged, false otherwise
	 */
	public boolean isLogged() {
		return (getUser() == null) ? false : true;
	}

	/**
	 * Returns username.
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets username.
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns password.
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets password.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns user.
	 * 
	 * @return user
	 */
	public User getUser() {
		return user;
	}
}
