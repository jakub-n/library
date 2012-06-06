package cz.muni.fi.pv243.library.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
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
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="BOOK_LOAN_ID", unique=true, nullable=false)
    private Long bookLoanId;
    
    // datum som dal pre testovacie potreby ako string. neskor zmenim
    //@Column(nullable=false)
    @NotNull
    private String beginDate;
    
    //@Column(nullable=false)
    private String endDate;
    
    //@Column(nullable=true)
    private Calendar returnDate;
    
    @ManyToOne
    @JoinColumn(name="EMPLOYEE_ID")
    private Employee employee;
    
    @ManyToOne(optional=true)
    @JoinColumn(name="READER_ID", nullable=true)
    private Reader reader;
    
    @ManyToOne
    @JoinColumn(name="BOOK_COPY_ID")
    private BookCopy bookCopy;

    public Long getId() {
        return bookLoanId;
    }

    public void setId(Long id) {
        this.bookLoanId = id;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Calendar getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Calendar returnDate) {
        this.returnDate = returnDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

}
