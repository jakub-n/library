package cz.muni.fi.pv243.library.web.controller;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.ejb.ReaderManager;
import cz.muni.fi.pv243.library.ejb.UserManager;
import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.entity.Role;
import cz.muni.fi.pv243.library.entity.User;
import cz.muni.fi.pv243.library.web.util.AbstractPaginationHelper;
import cz.muni.fi.pv243.library.web.util.JsfUtil;

@ManagedBean
@SessionScoped
public class ReaderController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ReaderManager readerManager;
	private AbstractPaginationHelper pagination;
	private DataModel<Reader> items = null;

	@Inject
	UserManager userManager;

	@Inject
	private Logger log;

	private Reader current;
	private int selectedItemIndex;
	private String searchText = null;

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
		if (this.current == null) {
			this.current = new Reader();
		}

		return this.current;
	}

	/**
	 * Creates new reader.
	 * 
	 * @return navigation page
	 */
	public String create() {
		try {
			if (!isUserDuplicated(this.current)) {
				Set<Role> roles = new HashSet<Role>();
				roles.add(Role.READER);
				this.current.setRoles(roles);
				this.readerManager.create(this.current);
				this.log.infof("Create reader account: %s -->account created.",
						this.current.getUsername());
				JsfUtil.addSuccessMessage("Čtenářské konto bylo úspěšně vytvořeno. Můžete se přihlásit.");
			} else {
				this.log.infof(
						"Create reader account: %s --> unsuccessful, account with given username already exist",
						this.current.getUsername());
				JsfUtil.addErrorMessage("Uživatel s takovýmto přihlašovacím jménem již existuje");
				return null;
			}
			this.current = new Reader();
			return "/login";
		} catch (Exception e) {
			this.log.infof("Create reader account: %s -->unsuccessful",
					this.current.getUsername());
			JsfUtil.addErrorMessage("Při vytváření uživatele došlo k chybě");
			return null;
		}
	}

	/**
	 * Checks if username of given user doesn't already exists
	 * 
	 * @param user
	 *            user to be checked
	 * @return true if user with username already exists
	 */
	private boolean isUserDuplicated(User user) {
		return (this.userManager.getUserByUsername(user.getUsername()) == null) ? false
				: true;
	}

	/**
	 * Returns data model of readers.
	 * 
	 * @return data model of readers
	 */
	public DataModel<Reader> getItems() {
		if (this.items == null) {
			this.items = getPagination().createPageDataModel();
		}
		return this.items;
	}

	/**
	 * Method for pagination
	 * 
	 * @return AbstractPaginationHelper
	 */
	public AbstractPaginationHelper getPagination() {
		if (this.pagination == null) {
			this.pagination = new AbstractPaginationHelper(5) {
				@Override
				public int getItemsCount() {
					return ReaderController.this.readerManager.count();
				}

				@Override
				public DataModel<Reader> createPageDataModel() {
					return new ListDataModel<Reader>(
							ReaderController.this.readerManager
									.findRange(
											new int[] {
													getPageFirstItem(),
													getPageFirstItem()
															+ getPageSize() },
											ReaderController.this.searchText));
				}
			};
		}

		return this.pagination;
	}

	/**
	 * Method which loads selected reader and navigates to reader detail.
	 * 
	 * @return reader detail view
	 */
	public String prepareView() {
		this.current = getItems().getRowData();
		this.selectedItemIndex = this.pagination.getPageFirstItem()
				+ getItems().getRowIndex();

		return "/librarian/readerDetail";
	}

	/**
	 * Performs delete of current reader and navigates to list of readers.
	 * 
	 * @return reader list view
	 */
	public String destroyAndView() {
		performDestroy();
		recreateModel();

		return "/librarian/readersList";

	}

	/**
	 * Deletes current reader.
	 */
	private void performDestroy() {
		try {
			this.readerManager.delete(this.current);
			JsfUtil.addSuccessMessage("Čtenář byl úspěšně smazán.");
		} catch (EJBTransactionRolledbackException e) {
			JsfUtil.addErrorMessage("Čtenář s výpůjčkami nelze smazat.");
		} catch (Exception ex) {
			JsfUtil.addErrorMessage("Při mazání čtenáře nastal problém.");
		}
	}

	/**
	 * Recreates the model (list).
	 */
	private void recreateModel() {
		this.items = null;
	}

	/**
	 * Method which loads selected reader and navigates to reader edit.
	 * 
	 * @return reader edit view
	 */
	public String prepareEdit() {
		this.current = getItems().getRowData();
		this.selectedItemIndex = this.pagination.getPageFirstItem()
				+ getItems().getRowIndex();

		return "/librarian/editReader";
	}

	/**
	 * Updates current reader.
	 * 
	 * @return navigation to updated reader view
	 */
	public String update() {
		try {
			this.readerManager.update(this.current);
			JsfUtil.addSuccessMessage("Čtenář byl úspěšně upraven.");

			return "/librarian/readerDetail";
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Při úpravě čtenáře došlo k chybě.");

			return null;
		}
	}

	/**
	 * Navigates to next page of pagination.
	 * 
	 * @return reader list view
	 */
	public String next() {
		getPagination().nextPage();
		recreateModel();

		return "/librarian/readersManagement";
	}

	/**
	 * Navigates to previous page of pagination.
	 * 
	 * @return reader list view
	 */
	public String previous() {
		getPagination().previousPage();
		recreateModel();

		return "/librarian/readersManagement";
	}

	public String getSearchText() {
		return this.searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	/**
	 * Cleans the search text and navigates to readers' management page.
	 * 
	 * @return reader list view
	 */
	public String cleanAndShowManagement() {
		this.searchText = null;
		recreateModel();
		return "/librarian/readersManagement";
	}

	/**
	 * Cleans the search text and recreates model.
	 */
	public void cleanSearch() {
		this.searchText = null;
		recreateModel();
	}

	/**
	 * Cleans the current reader and navigates to registration page.
	 * 
	 * @return reader registration page
	 */
	public String cleanAndShowRegistration() {
		this.current = new Reader();
		return "/librarian/createReader";
	}

	public Reader getCurrent() {
		return this.current;
	}

	public void setCurrent(Reader current) {
		this.current = current;
	}

}
