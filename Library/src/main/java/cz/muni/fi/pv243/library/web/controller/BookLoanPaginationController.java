package cz.muni.fi.pv243.library.web.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class BookLoanPaginationController {

	private int sessionPage = 0;

	/**
	 * Constructor.
	 */
	public BookLoanPaginationController() {
	}
	
	public int getSessionPage() {
		return sessionPage;
	}

	public void setSessionPage(int sessionPage) {
		this.sessionPage = sessionPage;
	}
	
	public String nextPage(){
		sessionPage++;
		return "/librarian/readerDetail";
	}
	
	public String previousPage(){
		sessionPage--;
		return "/librarian/readerDetail";
	}
	
	
	

}
