package cz.muni.fi.pv243.library.web.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;

import cz.muni.fi.pv243.library.ejb.BookLoanManager;
import cz.muni.fi.pv243.library.entity.BookLoan;
import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.web.util.AbstractPaginationHelper;

@ManagedBean
@ViewScoped
public class BookLoanController {

	@Inject
	private BookLoanManager bookLoanManager;
	private AbstractPaginationHelper pagination;
	private DataModel<BookLoan> items = null;

	@ManagedProperty(value = "#{readerController.current}")
    private Reader current;
	
	private Reader currentReader;

	/**
	 * Constructor.
	 */
	public BookLoanController() {
	}

	/**
	 * Returns data model of book loans.
	 * 
	 * @return data model of book loans
	 */
	public DataModel<BookLoan> getItems() {
		this.currentReader = current;
		if (items == null) {
			items = getPagination().createPageDataModel();
		}
		return items;
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
					return bookLoanManager.count(currentReader);
				}

				@Override
				public DataModel<BookLoan> createPageDataModel() {
					return new ListDataModel<BookLoan>(
							bookLoanManager.findRange(new int[] {
									getPageFirstItem(),
									getPageFirstItem() + getPageSize() },
									currentReader));
				}
			};
		}

		return pagination;
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
	 * @return book loan list view
	 */
	public String next() {
		getPagination().nextPage();
		recreateModel();

		return "/librarian/readerDetail";
	}

	/**
	 * Navigates to previous page of pagination.
	 * 
	 * @return reader list view
	 */
	public String previous() {
		getPagination().previousPage();
		recreateModel();

		return "/librarian/readerDetail";
	}

	public Reader getCurrent() {
		return current;
	}

	public void setCurrent(Reader current) {
		this.current = current;
	}
	
	

}
