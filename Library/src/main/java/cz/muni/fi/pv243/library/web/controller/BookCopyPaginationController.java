package cz.muni.fi.pv243.library.web.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class BookCopyPaginationController {

	private int sessionPage = 0;
	
	private String bookId="";
	

	/**
	 * Constructor.
	 */
	public BookCopyPaginationController() {
	}
	
	/**
	 * Gets book which is being viewed, when viewing different book, paging is zero again.
	 * 
	 */
	public void init(){
		String newBookId = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("book");

		if(newBookId == null){
			sessionPage = 0;
			this.bookId="";
		}else if(!newBookId.equals(bookId)){
			this.bookId=newBookId;
			sessionPage = 0;
		}
	}

	public int getSessionPage() {
		return sessionPage;
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
		sessionPage++;
		return "/bookDetail";
	}

	/**
	 * Navigates to previous page of book copies
	 * 
	 * @return book detail page
	 */
	public String previousPage() {
		sessionPage--;
		return "/bookDetail";
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	


}
