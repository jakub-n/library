package cz.muni.fi.pv243.library.web.controller;

import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;

import cz.muni.fi.pv243.library.ejb.BookCopyManager;
import cz.muni.fi.pv243.library.ejb.BookLoanManager;
import cz.muni.fi.pv243.library.ejb.BookManager;
import cz.muni.fi.pv243.library.entity.Book;
import cz.muni.fi.pv243.library.entity.BookCopy;
import cz.muni.fi.pv243.library.entity.BookLoan;
import cz.muni.fi.pv243.library.web.util.AbstractPaginationHelper;
import cz.muni.fi.pv243.library.web.util.JsfUtil;

@ViewScoped
@ManagedBean
public class BookDetailController {

	@Inject
	private BookManager bookManager;
	@Inject
	private BookLoanManager bookLoanManager;
	@Inject
	private BookCopyManager bookCopyManager;

	@ManagedProperty(value = "#{bookCopyPaginationController.sessionPage}")
	private int sessionPage;
	
	@ManagedProperty(value = "#{bookCopyPaginationController.bookId}")
	private String bookId;

	private AbstractPaginationHelper pagination;
	private DataModel<BookCopy> items = null;

	private Book book;
	private BookCopy currentCopy;

	/**
	 * Constructor.
	 */
	public BookDetailController() {
	}

	/**
	 * Gets book which is being viewed, when viewing different book, paging is zero again.
	 * 
	 */
	public void init() {
		String newBookId = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("book");
		if(!newBookId.equals(bookId)){
			sessionPage = 0;
		}
		if (newBookId != null) {
			this.book = bookManager.getBookById(Long.parseLong(newBookId));
		}
	}

	public BookManager getBookManager() {
		return bookManager;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setBookManager(BookManager bookManager) {
		this.bookManager = bookManager;
	}

	/**
	 * Returns book and navigates to reader detail page
	 * 
	 * @return reader detail page
	 */
	public String deleteBook() {
		try {
			bookManager.delete(book);
			JsfUtil.addSuccessMessage("Kniha byla smazána.");
			return "/libraryIndex";
		} catch (Exception ex) {
			JsfUtil.addErrorMessage("Při mazání knihy došlo k chybě.");
			return "/libraryIndex";
		}

	}

	/**
	 * Returns data model of book loans.
	 * 
	 * @return data model of book loans
	 */
	public DataModel<BookCopy> getItems() {
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
		System.out.println("sessionPage 1 " + sessionPage);
		if (pagination == null) {
			pagination = new AbstractPaginationHelper(5) {
				@Override
				public int getItemsCount() {
					return bookCopyManager.countOfBookCopiesByBook(book);
				}

				@Override
				public DataModel<BookCopy> createPageDataModel() {
					return new ListDataModel<BookCopy>(
							bookCopyManager.findRangeBookCopies(new int[] {
									(sessionPage * getPageSize()),
									(sessionPage * getPageSize())
											+ getPageSize() }, book));
				}

				@Override
				public boolean isHasNextPage() {
					return ((sessionPage + 1) * getPageSize() + 1) <= getItemsCount();
				}

				@Override
				public boolean isHasPreviousPage() {
					return sessionPage > 0;
				}

			};
		}

		return pagination;
	}

	/**
	 * Deletes book copy.
	 * 
	 * @return book detail page
	 */
	public String deleteBookCopy() {
		currentCopy = (BookCopy) getItems().getRowData();
		try {
			bookCopyManager.delete(currentCopy);
			JsfUtil.addSuccessMessage("Výtisk byl smazán.");
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Při mazání výtisku nastal problém.");
		}
		return "/libraryIndex";

	}

	public int getSessionPage() {
		return sessionPage;
	}

	public void setSessionPage(int sessionPage) {
		this.sessionPage = sessionPage;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	
	
}
