package cz.muni.fi.pv243.library.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Access(AccessType.FIELD)
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;
    private Reader creator;
    private Book bookedBook;
    private Calendar creationDate;
    private Calendar expirationDate;
    
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
    
    

}
