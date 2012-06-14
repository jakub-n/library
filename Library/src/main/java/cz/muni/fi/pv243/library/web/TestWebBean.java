package cz.muni.fi.pv243.library.web;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import cz.muni.fi.pv243.library.ejb.BookCopyManager;
import cz.muni.fi.pv243.library.ejb.BookLoanManager;
import cz.muni.fi.pv243.library.ejb.BookManager;
import cz.muni.fi.pv243.library.ejb.BookingManager;
import cz.muni.fi.pv243.library.ejb.EmployeeManager;
import cz.muni.fi.pv243.library.ejb.ReaderManager;
import cz.muni.fi.pv243.library.ejb.TagManager;
import cz.muni.fi.pv243.library.entity.Book;
import cz.muni.fi.pv243.library.entity.BookCopy;
import cz.muni.fi.pv243.library.entity.BookLoan;
import cz.muni.fi.pv243.library.entity.Booking;
import cz.muni.fi.pv243.library.entity.Employee;
import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.entity.Tag;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;


@ManagedBean
@SessionScoped
public class TestWebBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BookManager bookManager;
	@Inject
	private BookCopyManager bookCopyManager;
	@Inject
	private BookLoanManager bookLoanManager;
	@Inject
	private ReaderManager readerManager;
	@Inject
	private EmployeeManager employeeManager;
	@Inject
	private TagManager tagManager;
	@Inject
	private BookingManager bookingManager;
	

	public void addDefaultBook() {
		Book b = new Book();
		b.setAuthor("Jendrock Ericson");
    	b.setIsbn("978-0137081853");
    	b.setPagesNumber(600);
    	b.setLanguage(Locale.ENGLISH);
    	b.setTitle("The Java EE 6 Tutorial: Hard Concepts");
    	b.setPublicationDate(Calendar.getInstance());
		bookManager.create(b);
	}

	public List<Book> getBooks() {
		return bookManager.getAllBooks();
	}

	public void deleteBooks() {
		bookManager.deleteAllBooks();
	}
	
	public void addDefaultBookCopy(Book book) {
		BookCopy bc = new BookCopy();
		bc.setBook(book);
		bookCopyManager.create(bc);
	}
	
	public List<BookCopy> getBookCopies() {
		return bookCopyManager.getAllBookCopies();
	}
	
	public void removeBook(Book book) {
		bookManager.delete(book);
	}
	
	public void removeCopy(BookCopy copy){
		bookCopyManager.delete(copy);
	}
	
	public List<BookLoan> getBookLoans(){
		return bookLoanManager.getAllBookLoans();
	}
	
	public void addTestBookLoan() {
		BookLoan bl = new BookLoan();
		bl.setBeginDate(Calendar.getInstance());
		bl.setEndDate(Calendar.getInstance());
		Reader reader = readerManager.getReaderByName("readerUser");
		bl.setReader(reader);
		Employee emp = employeeManager.getEmployeeByName("librarianuser");
		Book b = new Book();
		b.setAuthor("Jendrock Ericson");
    	b.setIsbn("978-0137081853");
    	b.setPagesNumber(600);
    	b.setLanguage(Locale.ENGLISH);
    	b.setTitle("The Java EE 6 Tutorial: Hard Concepts");
    	b.setPublicationDate(Calendar.getInstance());
		bookManager.create(b);
		BookCopy bc = new BookCopy();
		bc.setBook(b);
		bookCopyManager.create(bc);
		bl.setEmployee(emp);
		bl.setReader(reader);
		bl.setBookCopy(bc);
		System.out.println("test!!!");
		bookLoanManager.create(bl);		
	}
	
	public void removeBookLoan(BookLoan loan){
		bookLoanManager.delete(loan);
	}
	
	public void deleteAllBookCopies(){
		bookCopyManager.deleteAllBookCopies();
	}
	
	public void deleteBookCopyById(Long id){
		bookCopyManager.delete(id);
	}
	
	public List<Reader> getReaders(){
		return readerManager.getAllReaders();
	}
	
	public void deleteReader(Reader reader){
		readerManager.delete(reader);
	}
	
	public void createTestTagBookRel(){
		Tag tag = new Tag();
		tag.setName("novel");
		tagManager.create(tag);
		Book b = new Book();
		b.setTitle("Introduction into Java EE 6");
		bookManager.create(b);
		Set<Tag> tags = new HashSet<Tag>();
    	tags.add(tag);
		b.setTags(tags);
		bookManager.update(b);
	}
	
	public void createTestBooking(){
		Book b = new Book();
		b.setTitle("Introduction into Java EE 6");
		bookManager.create(b);
		Reader reader = new Reader();
		readerManager.create(reader);
		Booking booking = new Booking();
		booking.setBook(b);
		booking.setReader(reader);
		bookingManager.create(booking);
	}
	
	public List<Tag> getTags(){
		return tagManager.getAllTags();
	}
	
	public List<Booking> getBookings(){
		return bookingManager.getAllBookings();
	}

}
