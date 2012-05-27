package cz.muni.fi.pv243.library.ejb;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.library.entity.Book;
import cz.muni.fi.pv243.library.entity.BookCopy;
import cz.muni.fi.pv243.library.entity.BookLoan;
import cz.muni.fi.pv243.library.entity.Booking;
import cz.muni.fi.pv243.library.entity.Employee;
import cz.muni.fi.pv243.library.entity.Librarian;
import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.entity.Tag;

@Singleton
@Startup
public class StartupConfigBean {

     @Inject
     private TagManager tagManager;
     
     @Inject
     private ReaderManager readerManager;
     
     @Inject
     private BookManager bookManager;
     
     @Inject
     private BookingManager bookingManager;
	
     @Inject
     private EmployeeManager employeeManager;
     
     @Inject
     private BookCopyManager bookCopyManager;
     
     @Inject
     private BookLoanManager bookLoanManager;
	
	 @PersistenceContext
	 private EntityManager entityManager;
	 
	    @PostConstruct
	    public void init() {
	    	
	    	// deletovani, kdyztak zakomentovat
	    	Query query= null;
	    	query = entityManager.createQuery("DELETE FROM Tag t");
	    	query.executeUpdate();
	    	query = entityManager.createQuery("DELETE FROM Credentials c");
	    	query.executeUpdate();
	    	query = entityManager.createQuery("DELETE FROM Booking b");
	    	query.executeUpdate();
	    	query = entityManager.createQuery("DELETE FROM BookLoan bl");
	    	query.executeUpdate();
	    	query = entityManager.createQuery("DELETE FROM Employee e");
	    	query.executeUpdate();
	    	query = entityManager.createQuery("DELETE FROM Reader r");
	    	query.executeUpdate();
	    	query = entityManager.createQuery("DELETE FROM BookCopy bc");
	    	query.executeUpdate();
	    	query = entityManager.createQuery("DELETE FROM Book b");
	    	query.executeUpdate();/**/
	    	
	    	// tagy
	    	Tag tag1 = new Tag();
	    	tag1.setName("Vzrušující literatura");
	    	
	    	Tag tag2 = new Tag();
	    	tag2.setName("Nutnost pro život");
	    	
	    	// readers
	    	Reader reader1 = new Reader();
	    	reader1.setBirthDate(Calendar.getInstance());
	    	reader1.setCellNumber("42424242");
	    	reader1.setCity("Brno");
	    	reader1.setEmail("reader@mail.muni.cz");
	    	reader1.setFirstName("Hugo");
	    	reader1.setLastName("Kokoska");
	    	reader1.setStreet("Botanicka");
	    	reader1.setZipCode("60200");
	    	
	    	// Books
	    	Book book1 = new Book();
	    	book1.setAuthor("Jendrock Eric");
	    	book1.setIsbn("978-0137081851");
	    	book1.setPagesNumber(600);
	    	book1.setLanguage(Locale.ENGLISH);
	    	book1.setTitle("The Java EE 6 Tutorial: Basic Concepts");
	    	book1.setPublicationDate(Calendar.getInstance());
	    	Set<Tag> tags1 = new HashSet<Tag>();
	    	tags1.add(tag2);
	    	book1.setTags(tags1);
	    	
	    	Book book2 = new Book();
	    	book2.setAuthor("Jendrock Eric");
	    	book2.setIsbn("978-0137081868  ");
	    	book2.setPagesNumber(768);
	    	book2.setLanguage(Locale.ENGLISH);
	    	book2.setTitle("The Java EE 6 Tutorial: Advanced Topics");
	    	book2.setPublicationDate(Calendar.getInstance());
	    	Set<Tag> tags2 = new HashSet<Tag>();
	    	tags2.add(tag1);
	    	book2.setTags(tags2);
	    	
	    	// Bookings
	    	Booking booking1 = new Booking();
	    	booking1.setBook(book2);
	    	booking1.setCreationDate(Calendar.getInstance());
	    	booking1.setReader(reader1);
	    	
	    	// employees
	    	Employee emplLib1 = new Librarian();
	    	emplLib1.setFirstName("Valibuk");
	    	emplLib1.setLastName("Štíhlý");
	    	
	    	// Bookcopys
	    	BookCopy bookCopy1 = new BookCopy();
	    	bookCopy1.setBook(book1);
	    	
	    	BookCopy bookCopy2 = new BookCopy();
	    	bookCopy2.setBook(book2);
	    	
	    	// Bookloans
	    	BookLoan bookLoan1 = new BookLoan();
	    	bookLoan1.setBeginDate("od včera");
	    	bookLoan1.setBookCopy(bookCopy1);
	    	bookLoan1.setReader(reader1);
	    	
	    	// naplneni:
	    	tagManager.create(tag1);
	    	tagManager.create(tag2);
	    	
	    	readerManager.create(reader1);
	    	
	    	bookManager.create(book1);
	    	bookManager.create(book2);
	    	
	    	bookingManager.create(booking1);
	    	
	    	employeeManager.create(emplLib1);
	    	
	    	bookCopyManager.create(bookCopy1);
	    	bookCopyManager.create(bookCopy2);
	    	
	    	bookLoanManager.create(bookLoan1);
	    	
	    }
	
}
