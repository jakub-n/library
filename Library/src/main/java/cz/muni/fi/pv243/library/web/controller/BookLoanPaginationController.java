package cz.muni.fi.pv243.library.web.controller;

import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import cz.muni.fi.pv243.library.ejb.BookCopyManager;
import cz.muni.fi.pv243.library.ejb.BookLoanManager;
import cz.muni.fi.pv243.library.ejb.EmployeeManager;
import cz.muni.fi.pv243.library.ejb.ReaderManager;
import cz.muni.fi.pv243.library.entity.BookCopy;
import cz.muni.fi.pv243.library.entity.BookLoan;
import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.web.util.JsfUtil;

@ManagedBean
@SessionScoped
public class BookLoanPaginationController {

	@Inject
	private BookLoanManager bookLoanManager;
	@Inject
	private EmployeeManager employeeManager;
	@Inject
	private ReaderManager readerManager;
	@Inject
	private BookCopyManager bookCopyManager;
	
	@ManagedProperty(value = "#{userController.username}")
	private String username;

	private int sessionPage = 0;

	@NotNull
	private String readerUsername;
	@Pattern(regexp = "[0-9]+", message = "Musí být zadáno číslo výtisku.")
	private String currentBookCopyId;

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

	/**
	 * Navigates to next page of book loans
	 * 
	 * @return reader detail page
	 */
	public String nextPage() {
		sessionPage++;
		return "/librarian/readerDetail";
	}

	/**
	 * Navigates to previous page of book loans
	 * 
	 * @return reader detail page
	 */
	public String previousPage() {
		sessionPage--;
		return "/librarian/readerDetail";
	}

	/**
	 * Loans book to a reader.
	 * 
	 * @return loan book page
	 */
	public String loanBook() {
		BookLoan bl = new BookLoan();
		bl.setBeginDate(new GregorianCalendar());
		bl.setEmployee(employeeManager.getEmployeeByName(username));
		Reader r = readerManager.getReaderByName(readerUsername);
		if (r != null) {
			bl.setReader(r);
		} else {
			JsfUtil.addErrorMessage("Čtenář neexistuje.");
			return "/librarian/loanBook";
		}
		BookCopy bc = bookCopyManager.getBookCopyById(Long
				.parseLong(currentBookCopyId));
		if (bc != null) {
			if(bookLoanManager.getActiveBookLoanForBookCopy(bc)!=null){
				JsfUtil.addErrorMessage("Výtisk knihy je půjčen.");
				return "/librarian/loanBook";
			}
			bl.setBookCopy(bc);
		} else {
			JsfUtil.addErrorMessage("Výtisk knihy neexistuje.");
			return "/librarian/loanBook";
		}

		bookLoanManager.create(bl);
		JsfUtil.addSuccessMessage("Výtisk byl vypůjčen.");
		currentBookCopyId = null;
		return "/librarian/loanBook";

	}
	

	/**
	 * Cleans username and shows loan page
	 * 
	 * @return loan book page
	 */
	public String cleanAndShowLoan() {
		readerUsername=null;
		currentBookCopyId=null;
		return "/librarian/loanBook";

	}	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReaderUsername() {
		return readerUsername;
	}

	public void setReaderUsername(String readerUsername) {
		this.readerUsername = readerUsername;
	}

	public String getCurrentBookCopyId() {
		return currentBookCopyId;
	}

	public void setCurrentBookCopyId(String currentBookCopyId) {
		this.currentBookCopyId = currentBookCopyId;
	}
	
	

}
