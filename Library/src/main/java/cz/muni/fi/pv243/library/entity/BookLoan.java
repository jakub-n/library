package cz.muni.fi.pv243.library.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.FIELD)
public class BookLoan implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BOOK_LOAN_ID", unique = true, nullable = false)
	private Long bookLoanId;

	// @Column(nullable=false)
	@NotNull
	private Calendar beginDate;

	// @Column(nullable=false)
	private Calendar endDate;

	// @Column(nullable=true)
	private Calendar returnDate;

	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	private Employee employee;

	@ManyToOne(optional = true)
	@JoinColumn(name = "READER_ID", nullable = true)
	private Reader reader;

	@ManyToOne
	@JoinColumn(name = "BOOK_COPY_ID")
	private BookCopy bookCopy;

	public Long getId() {
		return this.bookLoanId;
	}

	public void setId(Long id) {
		this.bookLoanId = id;
	}

	public Calendar getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Calendar beginDate) {
		this.beginDate = beginDate;
	}

	public Calendar getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Calendar getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(Calendar returnDate) {
		this.returnDate = returnDate;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Reader getReader() {
		return this.reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public BookCopy getBookCopy() {
		return this.bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

}
