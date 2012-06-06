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
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="BOOKING_ID")
    private Long id;
    private Reader creator; 
    private Book bookedBook;
    
    @NotNull
    private Calendar creationDate;
    private Calendar expirationDate;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="READER_ID")
    private Reader reader;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="BOOK_ID")
    private Book book;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Reader getCreator() {
        return creator;
    }
    public void setCreator(Reader creator) {
        this.creator = creator;
    }
    public Book getBookedBook() {
        return bookedBook;
    }
    public void setBookedBook(Book bookedBook) {
        this.bookedBook = bookedBook;
    }
    public Calendar getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }
    public Calendar getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }
	public Reader getReader() {
		return reader;
	}
	public void setReader(Reader reader) {
		this.reader = reader;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
    
    

}
