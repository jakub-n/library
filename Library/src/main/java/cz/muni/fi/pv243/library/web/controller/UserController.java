package cz.muni.fi.pv243.library.web.controller;

import java.security.Principal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cz.muni.fi.pv243.library.ejb.UserManager;
import cz.muni.fi.pv243.library.entity.User;

@SessionScoped
@ManagedBean
public class UserController{


	private String password;
	private String username;

	User user;

	@Inject
	private UserManager userManager;

	public UserController() {
	}

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
			System.out.println("Prihlasen " + username);
			user = userManager.getUserByUsername(username);
		} catch (ServletException ex) {
			// TODO zalogovat
			System.out.println("Neprihlasen, pokus byl " + username + " "
					+ password);
		}

	}

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

		} catch (ServletException ex) {
			// TODO zalogovat
		}
		return "/libraryIndex";

	}

	public boolean isLogged() {
		return (getUser() == null) ? false : true;
	}

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
