package cz.muni.fi.pv243.library.entity;

import java.io.Serializable;

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
public class BookCopy implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="BOOK_COPY_ID",nullable=false, unique=true)
	private Long bookCopyId;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="BOOK_ID")
	private Book book;
	
	public Long getId() {
		return bookCopyId;
	}

	public void setId(Long id) {
		this.bookCopyId = id;
	}

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

	
}
