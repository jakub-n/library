package cz.muni.fi.pv243.library.web.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;

import cz.muni.fi.pv243.library.ejb.BookLoanManager;
import cz.muni.fi.pv243.library.ejb.ReaderManager;
import cz.muni.fi.pv243.library.entity.BookLoan;
import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.web.util.AbstractPaginationHelper;

@ManagedBean
@ViewScoped
public class ReaderPageController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private BookLoanManager bookLoanManager;

	@Inject
	private ReaderManager readerManager;

	private AbstractPaginationHelper pagination;
	private DataModel<BookLoan> items = null;

	@ManagedProperty(value = "#{userController.username}")
	private String username;

	private Reader currentReader;

	/**
	 * Constructor.
	 */
	public ReaderPageController() {
	}

	/**
	 * Returns data model of book loans.
	 * 
	 * @return data model of book loans
	 */
	public DataModel<BookLoan> getItems() {
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
		this.currentReader = this.readerManager.getReaderByName(this.username);
		if (this.pagination == null) {
			this.pagination = new AbstractPaginationHelper(5) {
				@Override
				public int getItemsCount() {
					return ReaderPageController.this.bookLoanManager
							.count(ReaderPageController.this.currentReader);
				}

				@Override
				public DataModel<BookLoan> createPageDataModel() {
					return new ListDataModel<BookLoan>(
							ReaderPageController.this.bookLoanManager
									.findRange(
											new int[] {
													getPageFirstItem(),
													getPageFirstItem()
															+ getPageSize() },
											ReaderPageController.this.currentReader));
				}

			};
		}

		return this.pagination;
	}

	/**
	 * Navigates to next page of pagination.
	 * 
	 * @return librarian list view
	 */
	public String next() {
		getPagination().nextPage();
		recreateModel();

		return "/reader/readerLoans";
	}

	/**
	 * Navigates to previous page of pagination.
	 * 
	 * @return librarian list view
	 */
	public String previous() {
		getPagination().previousPage();
		recreateModel();

		return "/reader/readerLoans";
	}

	/**
	 * Recreates the model (list).
	 */
	private void recreateModel() {
		this.items = null;
	}

	/**
	 * Prolongs loan one month
	 * 
	 * @return reader loans page
	 */
	public String prolongLoan() {
		BookLoan bl = getItems().getRowData();
		Date date = bl.getReturnDate().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		sdf.format(date);
		Calendar cal = new GregorianCalendar(
				Integer.parseInt(sdf.format(date)), Integer.parseInt(sdf2
						.format(date)) - 1 + 1, Integer.parseInt(sdf3
						.format(date)));
		bl.setReturnDate(cal);

		this.bookLoanManager.update(bl);
		return "/reader/readerLoans";
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
