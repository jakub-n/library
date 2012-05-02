package cz.muni.fi.pv243.library.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Class representing a single book.
 * 
 * @author Ixi (Iva Zakova)
 */
@Entity
@Access(AccessType.FIELD)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @NotNull
    private String title;
    
    private String author;
    private String isbn;
    private Calendar publicationDate;
    private Locale language;
    private int pagesNumber;
    private Set<BookCopy> copies;
    private Set<Tag> tags;
    

    public Book() {
    }
    
    

    public Set<BookCopy> getCopies() {
        return copies;
    }
    
    

    public Set<Tag> getTags() {
        return tags;
    }



    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }



    public void setCopies(Set<BookCopy> copies) {
        this.copies = copies;
    }



    public String getAuthor() {
        return author;
    }



    public void setAuthor(String author) {
        this.author = author;
    }



    public String getIsbn() {
        return isbn;
    }



    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Calendar getPublicationDate() {
        return publicationDate;
    }



    public void setPublicationDate(Calendar publicationDate) {
        this.publicationDate = publicationDate;
    }



    public Locale getLanguage() {
        return language;
    }



    public void setLanguage(Locale language) {
        this.language = language;
    }



    public int getPagesNumber() {
        return pagesNumber;
    }



    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
