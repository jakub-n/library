package cz.muni.fi.pv243.library.web.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
public class BookLoanPaginationController implements Serializable {

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
	private String curentUserUsername = "";

	@NotNull
	private String readerUsername;
	@Pattern(regexp = "[0-9]+", message = "Musí být zadáno číslo výtisku.")
	private String currentBookCopyId;

	private int loanLength;

	/**
	 * Constructor.
	 */
	public BookLoanPaginationController() {
	}

	/**
	 * When viewing different reader, paging is zero again.
	 * 
	 */
	public void init() {
		String newCurrent = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap()
				.get("readerUsername");
		if (newCurrent == null) {
			sessionPage = 0;
			this.curentUserUsername = "";
		} else if (!(newCurrent.equals(curentUserUsername))) {
			this.curentUserUsername = newCurrent;
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
			if (bookLoanManager.getActiveBookLoanForBookCopy(bc) != null) {
				JsfUtil.addErrorMessage("Výtisk knihy je půjčen.");
				return "/librarian/loanBook";
			}
			bl.setBookCopy(bc);
		} else {
			JsfUtil.addErrorMessage("Výtisk knihy neexistuje.");
			return "/librarian/loanBook";
		}

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		sdf.format(date);
		Calendar cal = new GregorianCalendar(
				Integer.parseInt(sdf.format(date)), Integer.parseInt(sdf2
						.format(date)) - 1 + loanLength, Integer.parseInt(sdf3
						.format(date)) + 1);
		bl.setReturnDate(cal);

		bookLoanManager.create(bl);
		JsfUtil.addSuccessMessage("Výtisk byl vypůjčen.");
		currentBookCopyId = null;
		loanLength = 1;
		return "/librarian/loanBook";

	}

	/**
	 * Cleans username and shows loan page
	 * 
	 * @return loan book page
	 */
	public String cleanAndShowLoan() {
		readerUsername = null;
		currentBookCopyId = null;
		loanLength = 1;
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

	public int getLoanLength() {
		return loanLength;
	}

	public void setLoanLength(int loanLength) {
		this.loanLength = loanLength;
	}

}
