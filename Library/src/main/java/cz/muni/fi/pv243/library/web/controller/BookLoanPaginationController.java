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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
			this.sessionPage = 0;
			this.curentUserUsername = "";
		} else if (!(newCurrent.equals(this.curentUserUsername))) {
			this.curentUserUsername = newCurrent;
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
	 * Navigates to next page of book loans
	 * 
	 * @return reader detail page
	 */
	public String nextPage() {
		this.sessionPage++;
		return "/librarian/readerDetail";
	}

	/**
	 * Navigates to previous page of book loans
	 * 
	 * @return reader detail page
	 */
	public String previousPage() {
		this.sessionPage--;
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
		bl.setEmployee(this.employeeManager.getEmployeeByName(this.username));
		Reader r = this.readerManager.getReaderByName(this.readerUsername);
		if (r != null) {
			bl.setReader(r);
		} else {
			JsfUtil.addErrorMessage("Čtenář neexistuje.");
			return "/librarian/loanBook";
		}
		BookCopy bc = this.bookCopyManager.getBookCopyById(Long
				.parseLong(this.currentBookCopyId));
		if (bc != null) {
			if (this.bookLoanManager.getActiveBookLoanForBookCopy(bc) != null) {
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
						.format(date)) - 1 + this.loanLength,
				Integer.parseInt(sdf3.format(date)) + 1);
		bl.setReturnDate(cal);

		this.bookLoanManager.create(bl);
		JsfUtil.addSuccessMessage("Výtisk byl vypůjčen.");
		this.currentBookCopyId = null;
		this.loanLength = 1;
		return "/librarian/loanBook";

	}

	/**
	 * Cleans username and shows loan page
	 * 
	 * @return loan book page
	 */
	public String cleanAndShowLoan() {
		this.readerUsername = null;
		this.currentBookCopyId = null;
		this.loanLength = 1;
		return "/librarian/loanBook";

	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReaderUsername() {
		return this.readerUsername;
	}

	public void setReaderUsername(String readerUsername) {
		this.readerUsername = readerUsername;
	}

	public String getCurrentBookCopyId() {
		return this.currentBookCopyId;
	}

	public void setCurrentBookCopyId(String currentBookCopyId) {
		this.currentBookCopyId = currentBookCopyId;
	}

	public int getLoanLength() {
		return this.loanLength;
	}

	public void setLoanLength(int loanLength) {
		this.loanLength = loanLength;
	}

}
