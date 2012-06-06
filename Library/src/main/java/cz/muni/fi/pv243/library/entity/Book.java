package cz.muni.fi.pv243.library.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
	@Column(name = "BOOK_ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;

	@Column(nullable = false)
	@NotNull
	private String title;

	// @Column(nullable=false)
	@NotNull
	private String author;

	// @Column(nullable=false)
	@NotNull
	private String isbn;

	// @Column(nullable=false)
	private Calendar publicationDate;

	// @Column(nullable=false)
	private Locale language;

	// @Column(nullable=false)
	private int pagesNumber;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
	private Set<BookCopy> copies;

	@ManyToMany
	@JoinTable(name = "BOOK_TAG", 
	joinColumns = { @JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID") }, 
	inverseJoinColumns = { @JoinColumn(name = "TAG_ID", referencedColumnName = "TAG_ID") })
	private Set<Tag> tags;
	
	@OneToMany(mappedBy="book", orphanRemoval=true, fetch = FetchType.LAZY)
	private Set<Booking> bookings;

	public Set<BookCopy> getCopies() {
		return copies;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	//test
	public void addTag(Tag tag) {
		if (this.tags != null) {
			this.tags.add(tag);
		} else {
			Set<Tag> tags = new HashSet<Tag>();
			tags.add(tag);
			this.tags = tags;
		}
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

	public Set<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

}
