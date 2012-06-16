package cz.muni.fi.pv243.library.web.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class BookCopyPaginationController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int sessionPage = 0;

	private String bookId = "";

	/**
	 * Constructor.
	 */
	public BookCopyPaginationController() {
	}

	/**
	 * Gets book which is being viewed, when viewing different book, paging is
	 * zero again.
	 * 
	 */
	public void init() {
		String newBookId = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("book");

		if (newBookId == null) {
			this.sessionPage = 0;
			this.bookId = "";
		} else if (!newBookId.equals(this.bookId)) {
			this.bookId = newBookId;
			this.sessionPage = 0;
		}
	}

	public int getSessionPage() {
		return this.sessionPage;
	}

	public void setSessionPage(int sessionPage) {
		this.sessionPage = sessionPage;
	}

	/**
	 * Navigates to next page of book copies
	 * 
	 * @return book detail page
	 */
	public String nextPage() {
		this.sessionPage++;
		return "/bookDetail";
	}

	/**
	 * Navigates to previous page of book copies
	 * 
	 * @return book detail page
	 */
	public String previousPage() {
		this.sessionPage--;
		return "/bookDetail";
	}

	public String getBookId() {
		return this.bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

}
