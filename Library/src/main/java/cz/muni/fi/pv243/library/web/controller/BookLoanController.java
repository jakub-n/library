package cz.muni.fi.pv243.library.web.controller;

import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;

import cz.muni.fi.pv243.library.ejb.BookLoanManager;
import cz.muni.fi.pv243.library.entity.BookLoan;
import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.web.util.AbstractPaginationHelper;
import cz.muni.fi.pv243.library.web.util.JsfUtil;

@ManagedBean
@ViewScoped
public class BookLoanController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private BookLoanManager bookLoanManager;

	private AbstractPaginationHelper pagination;
	private DataModel<BookLoan> items = null;

	@ManagedProperty(value = "#{readerController.current}")
	private Reader current;

	@ManagedProperty(value = "#{bookLoanPaginationController.sessionPage}")
	private int sessionPage;

	private Reader currentReader;
	private BookLoan currentLoan;

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
		this.currentReader = this.current;
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
					return BookLoanController.this.bookLoanManager
							.count(BookLoanController.this.currentReader);
				}

				@Override
				public DataModel<BookLoan> createPageDataModel() {
					return new ListDataModel<BookLoan>(
							BookLoanController.this.bookLoanManager
									.findRange(
											new int[] {
													(BookLoanController.this.sessionPage * getPageSize()),
													(BookLoanController.this.sessionPage * getPageSize())
															+ getPageSize() },
											BookLoanController.this.currentReader));
				}

				@Override
				public boolean isHasNextPage() {
					return ((BookLoanController.this.sessionPage + 1)
							* getPageSize() + 1) <= getItemsCount();
				}

				@Override
				public boolean isHasPreviousPage() {
					return BookLoanController.this.sessionPage > 0;
				}

			};
		}

		return this.pagination;
	}

	public Reader getCurrent() {
		return this.current;
	}

	public void setCurrent(Reader current) {
		this.current = current;
	}

	public int getSessionPage() {
		return this.sessionPage;
	}

	public void setSessionPage(int sessionPage) {
		this.sessionPage = sessionPage;
	}

	/**
	 * Returns book and navigates to reader detail page
	 * 
	 * @return reader detail page
	 */
	public String returnBook() {
		this.currentLoan = getItems().getRowData();
		this.currentLoan.setEndDate(new GregorianCalendar());
		this.bookLoanManager.update(this.currentLoan);
		JsfUtil.addSuccessMessage("Výtisk byl vrácen.");
		return "/librarian/readerDetail";

	}

}
