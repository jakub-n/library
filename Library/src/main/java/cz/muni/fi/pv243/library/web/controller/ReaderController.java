package cz.muni.fi.pv243.library.web.controller;

import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.jboss.solder.logging.Logger;
import cz.muni.fi.pv243.library.ejb.ReaderManager;
import cz.muni.fi.pv243.library.ejb.UserManager;
import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.entity.Role;
import cz.muni.fi.pv243.library.entity.User;
import cz.muni.fi.pv243.library.web.util.JsfUtil;


@ManagedBean
@SessionScoped
public class ReaderController {

	@Inject
	ReaderManager readerManager;

	@Inject
	UserManager userManager;
	
	@Inject
	private Logger log;

	private Reader current;

	/**
	 * Constructor.
	 */
	public ReaderController() {
	}

	/**
	 * Returns selected reader, creates new if null.
	 * 
	 * @return selected reader
	 */
	public Reader getSelected() {
		if (current == null) {
			current = new Reader();
		}

		return current;
	}

	/**
	 * Creates new reader.
	 * 
	 * @return navigation page
	 */
	public String create() {
		try {
			if (!isUserDuplicated(current)) {
				Set<Role> roles = new HashSet<Role>();
				roles.add(Role.READER);
				current.setRoles(roles);
				readerManager.create(current);
				log.infof("Create reader account: %s -->account created.", current.getUsername());
				JsfUtil.addSuccessMessage("Čtenářské konto bylo úspěšně vytvořeno. Můžete se přihlásit.");
			} else {
				log.infof("Create reader account: %s --> unsuccessful, account with given username already exist",
						current.getUsername());
				JsfUtil.addErrorMessage("Uživatel s takovýmto přihlašovacím jménem již existuje");
				return null;
			}
			current = new Reader();
			return "/login";
		} catch (Exception e) {
			log.infof("Create reader account: %s -->unsuccessful", current.getUsername());
			JsfUtil.addErrorMessage("Při vytváření uživatele došlo k chybě");
            return null;
		}
	}

	/**
	 * Checks if username of given user doesn't already exists
	 * 
	 * @param user user to be checked
	 * @return true if user with username already exists 
	 */
	private boolean isUserDuplicated(User user) {
		return (userManager.getUserByUsername(user.getUsername()) == null) ? false
				: true;
	}
}
