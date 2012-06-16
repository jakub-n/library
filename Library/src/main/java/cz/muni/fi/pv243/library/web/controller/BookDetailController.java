package cz.muni.fi.pv243.library.web.controller;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJBTransactionRolledbackException;
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
public class BookDetailController implements Serializable {

	private static final long serialVersionUID = 1L;
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
		if (!newBookId.equals(this.bookId)) {
			this.sessionPage = 0;
		}
		if (newBookId != null) {
			this.book = this.bookManager.getBookById(Long.parseLong(newBookId));
		}
	}

	public BookManager getBookManager() {
		return this.bookManager;
	}

	public Book getBook() {
		return this.book;
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
			this.bookManager.delete(this.book);
			JsfUtil.addSuccessMessage("Kniha byla smazána.");
			return "/libraryIndex";
		} catch (EJBTransactionRolledbackException e) {
			JsfUtil.addErrorMessage("Kniha s existujícími výtisky nelze smazat.");
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
		if (this.items == null) {
			this.items = getPagination().createPageDataModel();
		}
		return this.items;
	}

	private List<BookCopyWithLoan> getBookLoansForItems(List<BookCopy> bcList) {
		List<BookCopyWithLoan> bcwlList = new LinkedList<BookCopyWithLoan>();
		for (BookCopy bc : bcList) {
			bcwlList.add(new BookCopyWithLoan(bc, this.bookLoanManager
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
		if (this.pagination == null) {
			this.pagination = new AbstractPaginationHelper(5) {
				@Override
				public int getItemsCount() {
					return BookDetailController.this.bookCopyManager
							.countOfBookCopiesByBook(BookDetailController.this.book);
				}

				@Override
				public DataModel<BookCopyWithLoan> createPageDataModel() {
					List<BookCopy> bcList = BookDetailController.this.bookCopyManager
							.findRangeBookCopies(
									new int[] {
											(BookDetailController.this.sessionPage * getPageSize()),
											(BookDetailController.this.sessionPage * getPageSize())
													+ getPageSize() },
									BookDetailController.this.book);
					return new ListDataModel<BookCopyWithLoan>(
							getBookLoansForItems(bcList));
				}

				@Override
				public boolean isHasNextPage() {
					return ((BookDetailController.this.sessionPage + 1)
							* getPageSize() + 1) <= getItemsCount();
				}

				@Override
				public boolean isHasPreviousPage() {
					return BookDetailController.this.sessionPage > 0;
				}

			};
		}

		return this.pagination;
	}

	/**
	 * Deletes book copy.
	 * 
	 * @return book detail page
	 */
	public String deleteBookCopy() {
		BookCopyWithLoan bcwl = getItems().getRowData();
		this.currentCopy = bcwl.getBc();
		try {
			this.bookCopyManager.delete(this.currentCopy.getId());
			JsfUtil.addSuccessMessage("Výtisk byl smazán.");
		} catch (EJBTransactionRolledbackException e) {
			JsfUtil.addErrorMessage("Výtisk, který byl vypůjčen, nelze smazat.");
			return "/libraryIndex";
		} catch (Exception ex) {
			JsfUtil.addErrorMessage("Při mazání výtisku nastal problém.");
		}
		return "/libraryIndex";

	}

	public int getCountOfActiveBookLoans() {
		List<BookCopy> bcList = this.bookCopyManager.findBookCopies(this.book);
		int count = 0;
		for (BookCopy bc : bcList) {
			if (this.bookLoanManager.getActiveBookLoanForBookCopy(bc) != null) {
				count++;
			}
		}

		return count;
	}

	public int getCountOfBookCopies() {
		if (this.book != null) {
			return this.bookCopyManager.countOfBookCopiesByBook(this.book);
		} else
			return 0;
	}

	public String addBookCopy() {
		BookCopy bc = new BookCopy();
		bc.setBook(this.book);
		this.bookCopyManager.create(bc);
		JsfUtil.addSuccessMessage("Výtisk byl přidán.");
		return "/libraryIndex";

	}

	public int getSessionPage() {
		return this.sessionPage;
	}

	public void setSessionPage(int sessionPage) {
		this.sessionPage = sessionPage;
	}

	public String getBookId() {
		return this.bookId;
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
			return this.bc;
		}

		public void setBc(BookCopy bc) {
			this.bc = bc;
		}

		public BookLoan getBl() {
			return this.bl;
		}

		public void setBl(BookLoan bl) {
			this.bl = bl;
		}

	}

}
