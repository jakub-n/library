package cz.muni.fi.pv243.library.web.controller;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

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
	private DataModel<BookCopyWithLoan> items = null;

	private Book book;
	private BookCopy currentCopy;

	/**
	 * Constructor.
	 */
	public BookDetailController() {
	}

	/**
	 * Gets book which is being viewed, when viewing different book, paging is
	 * zero again.
	 * 
	 */
	public void init() {
		String newBookId = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("book");
		if (!newBookId.equals(bookId)) {
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
	public DataModel<BookCopyWithLoan> getItems() {
		if (items == null) {
			items = getPagination().createPageDataModel();
		}
		return items;
	}

	private List<BookCopyWithLoan> getBookLoansForItems(List<BookCopy> bcList) {
		List<BookCopyWithLoan> bcwlList = new LinkedList<BookCopyWithLoan>();
		for (BookCopy bc : bcList) {
			bcwlList.add(new BookCopyWithLoan(bc, bookLoanManager
					.getActiveBookLoanForBookCopy(bc)));
		}

		return bcwlList;
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
					return bookCopyManager.countOfBookCopiesByBook(book);
				}

				@Override
				public DataModel<BookCopyWithLoan> createPageDataModel() {
					List<BookCopy> bcList = bookCopyManager
							.findRangeBookCopies(new int[] {
									(sessionPage * getPageSize()),
									(sessionPage * getPageSize())
											+ getPageSize() }, book);
					return new ListDataModel<BookCopyWithLoan>(
							getBookLoansForItems(bcList));
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
		BookCopyWithLoan bcwl = (BookCopyWithLoan) getItems().getRowData();
		currentCopy = bcwl.getBc();
		try {
			bookCopyManager.delete(currentCopy.getId());
			JsfUtil.addSuccessMessage("Výtisk byl smazán.");
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Při mazání výtisku nastal problém.");
		}
		return "/libraryIndex";

	}

	public int getCountOfActiveBookLoans() {
		List<BookCopy> bcList = bookCopyManager.findBookCopies(book);
		int count = 0;
		for (BookCopy bc : bcList) {
			if (bookLoanManager.getActiveBookLoanForBookCopy(bc) != null) {
				count++;
			}
		}

		return count;
	}

	public int getCountOfBookCopies() {
		if (book != null) {
			return bookCopyManager.countOfBookCopiesByBook(book);
		} else
			return 0;
	}

	public String addBookCopy() {
		BookCopy bc = new BookCopy();
		bc.setBook(book);
		bookCopyManager.create(bc);
		JsfUtil.addSuccessMessage("Výtisk byl přidán.");
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

	public class BookCopyWithLoan {
		private BookCopy bc;
		private BookLoan bl;

		public BookCopyWithLoan(BookCopy bc, BookLoan bl) {
			this.bc = bc;
			this.bl = bl;
		}

		public BookCopy getBc() {
			return bc;
		}

		public void setBc(BookCopy bc) {
			this.bc = bc;
		}

		public BookLoan getBl() {
			return bl;
		}

		public void setBl(BookLoan bl) {
			this.bl = bl;
		}

	}

}
