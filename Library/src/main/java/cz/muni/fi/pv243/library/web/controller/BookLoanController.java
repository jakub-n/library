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

	@ManagedProperty(value = "#{bookLoanPaginationController.sessionPage}")
	private int sessionPage;

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
									(sessionPage*getPageSize()),
									(sessionPage*getPageSize()) + getPageSize() },
									currentReader));
				}

				@Override
				public boolean isHasNextPage() {
					return ((sessionPage+1)*getPageSize()+1)<=getItemsCount();
				}

				@Override
				public boolean isHasPreviousPage() {
					return sessionPage > 0;
				}
				
				
			};
		}

		return pagination;
	}


	public Reader getCurrent() {
		return current;
	}

	public void setCurrent(Reader current) {
		this.current = current;
	}

	public int getSessionPage() {
		return sessionPage;
	}

	public void setSessionPage(int sessionPage) {
		this.sessionPage = sessionPage;
	}

}
