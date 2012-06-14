package cz.muni.fi.pv243.library.web.controller;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.ejb.LibrarianManager;
import cz.muni.fi.pv243.library.entity.Librarian;
import cz.muni.fi.pv243.library.entity.Role;
import cz.muni.fi.pv243.library.entity.User;
import cz.muni.fi.pv243.library.web.util.AbstractPaginationHelper;
import cz.muni.fi.pv243.library.web.util.JsfUtil;

@ManagedBean
@SessionScoped
public class LibrarianController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	  @Inject
	  private LibrarianManager librarianManager;
	  private AbstractPaginationHelper pagination;
	  private Librarian current;
	  private DataModel<Librarian> items = null;
	  @Inject
	  private Logger log;
	 
	    private int selectedItemIndex;

	    /**
	     * Constructor
	     */
	    public LibrarianController() {
	    }

	    /**
	     * Returns selected librarian, creates a new one when is null.
	     * 
	     * @return selected librarian
	     */
	    public Librarian getSelected() {
	        if (current == null) {
	            current = new Librarian();
	            selectedItemIndex = -1;
	        }

	        return current;
	    }



	    /**
	     * Method for pagination
	     * 
	     * @return AbstractPaginationHelper
	     */
	    public AbstractPaginationHelper getPagination() {
	        if (pagination == null) {
	            pagination = new AbstractPaginationHelper(5) {
	                        @Override
	                        public int getItemsCount() {
	                            return librarianManager.count();
	                        }

	                        @Override
	                        public DataModel<Librarian> createPageDataModel() {
	                            return new ListDataModel<Librarian>(
	                            		librarianManager.findRange(
	                                            new int[] {
	                                                getPageFirstItem(),
	                                                getPageFirstItem()
	                                                + getPageSize()
	                                            }));
	                        }
	                    };
	        }

	        return pagination;
	    }

	    /**
	     * Enables recreating the model and navigates to the list of librarians.
	     * 
	     * @return librarian list view
	     */
	  public String prepareList() {
	        recreateModel();

	        return "/manager/librariansList";
	    }

	  
	  /**
	   * Method which loads selected librarian and navigates to librarian detail.
	   * 
	   * @return librarian detail view
	   */
	    public String prepareView() {
	        current = (Librarian) getItems().getRowData();
	        selectedItemIndex = pagination.getPageFirstItem()
	            + getItems().getRowIndex();

	        return "/manager/librarianDetail";
	    }

	    /**
	     * Method which prepares creating new librarian and navigates to creating page.
	     * 
	     * @return librarian creating view
	     */
	    public String prepareCreate() {
	        current = new Librarian();
	        selectedItemIndex = -1;

	        return "/manager/createLibrarian";
	    }

	    /** 
	     * If user with username doesn't exist, creates new librarian.
	     * 
	     * @return navigation to same page
	     */
	    public String create() {
	        try {
	        	if (!isUserDuplicated(current)) {
					Set<Role> roles = new HashSet<Role>();
					roles.add(Role.LIBRARIAN);
					current.setRoles(roles);
	                librarianManager.create(current);
	                log.infof("Create librarian account: %s -->account created", current.getUsername());
	                JsfUtil.addSuccessMessage("Nový knihovník byl úspěšně vytvořen.");
	                return prepareCreate();
	        	} else {
	        		log.infof("Create librarian account: %s -->unsuccessful, account with given username already exist",
							current.getUsername());
					JsfUtil.addErrorMessage("Uživatel s takovýmto přihlašovacím jménem již existuje");
					return null;
				}
	            
	        } catch (Exception e) {
	        	log.infof("Create librarian account: %s -->unsuccessful", current.getUsername());
	            JsfUtil.addErrorMessage("Při vytváření nového knihovníka došlo k chybě.");
	            return null;
	        }
	    }

	    /**
	     * Method which loads selected librarian and navigates to librarian edit.
	     * 
	     * @return librarian edit view
	     */
	    public String prepareEdit() {
	        current = (Librarian) getItems().getRowData();
	        selectedItemIndex = pagination.getPageFirstItem()
	            + getItems().getRowIndex();

	        return "/manager/editLibrarian";
	    }
	    
	    /**
	     * Updates current librarian.
	     * 
	     * @return navigation to updated librarian view
	     */
	    public String update() {
	        try {
	            librarianManager.update(current);
	            log.infof("Update librarian: %s -->updated", current.getUsername());
	            JsfUtil.addSuccessMessage("Knihovník byl úspěšně upraven.");
	            return "/manager/librarianDetail";
	        } catch (Exception e) {
	        	log.infof("Update librarian: %s -->unsuccessful", current.getUsername());
	            JsfUtil.addErrorMessage("Při úpravě knihovníka došlo k chybě.");

	            return null;
	        }
	    }

	    /**
	     * Performs delete of current librarian and navigates to list of librarians.
	     * 
	     * @return librarian list view
	     */
	    public String destroyAndView() {
	        performDestroy();
	        recreateModel();

	        return "/manager/librariansList";

	    }

	    /**
	     * Deletes current librarian.
	     */
	    private void performDestroy() {
	        try {
	            librarianManager.delete(current);
	            log.infof("Delete librarian: %s -->deleted", current.getUsername());
	            JsfUtil.addSuccessMessage("Knihovník byl úspěšně smazán.");
	        } catch (Exception e) {
	        	log.infof("Delete librarian: %s -->unsuccessfull", current.getUsername());
	            JsfUtil.addErrorMessage("Při mazání knihovníka nastal problém.");
	        }
	    }

	   
	    /**
	     * Returns data model of librarians.
	     * 
	     * @return data model of librarians 
	     */
	    public DataModel<Librarian> getItems() {
	        if (items == null) {
	            items = getPagination().createPageDataModel();
	        }
	        
	        return items;
	    }

	    /**
	     * Recreates the model (list).
	     */
	    private void recreateModel() {
	        items = null;
	    }

	    /**
	     * Navigates to next page of pagination.
	     * 
	     * @return librarian list view
	     */
	    public String next() {
	        getPagination().nextPage();
	        recreateModel();

	        return "/manager/librariansList";
	    }

	    /**
	     * Navigates to previous page of pagination.
	     * 
	     * @return librarian list view
	     */
	    public String previous() {
	        getPagination().previousPage();
	        recreateModel();

	        return "/manager/librariansList";
	    }

	    public SelectItem[] getItemsAvailableSelectMany() {
	        return JsfUtil.getSelectItems(librarianManager.getAllLibrarians(), false);
	    }

	    public SelectItem[] getItemsAvailableSelectOne() {
	        return JsfUtil.getSelectItems(librarianManager.getAllLibrarians(),true);
	    }

	    /**
	     * Converter for Librarian ckass,
	     */
	    @FacesConverter(forClass = Librarian.class)
	    public static class AdministratorControllerConverter implements Converter {
	        public Object getAsObject(
	            FacesContext facesContext,
	            UIComponent component,
	            String value) {
	            if ((value == null) || (value.length() == 0)) {
	                return null;
	            }

	            LibrarianController controller = (LibrarianController) facesContext.getApplication()
	                                                                                       .getELResolver()
	                                                                                       .getValue(
	                        facesContext.getELContext(),
	                        null,
	                        "librarianController");

	            return controller.librarianManager.getLibrarianByUsername(value);
	        }
	      
	        public String getAsString(
	            FacesContext facesContext,
	            UIComponent component,
	            Object object) {
	            if (object == null) {
	                return null;
	            }

	            if (object instanceof Librarian) {
	            	Librarian o = (Librarian) object;

	                return o.getUsername();
	            } else {
	                throw new IllegalArgumentException(
	                        "object " + object + " is of type "
	                        + object.getClass().getName() + "; expected type: "
	                        + LibrarianController.class.getName());
	            }
	        }
	    }
	

	    /**
		 * Checks if username of given user doesn't already exists
		 * 
		 * @param user user to be checked
		 * @return true if user with username already exists 
		 */
		private boolean isUserDuplicated(User user) {
			return (librarianManager.getLibrarianByUsername(user.getUsername()) == null) ? false
					: true;
		}
}
