package cz.muni.fi.pv243.library.ejb;

import java.security.NoSuchAlgorithmException;
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

import cz.muni.fi.pv243.library.entity.Book;
import cz.muni.fi.pv243.library.entity.BookCopy;
import cz.muni.fi.pv243.library.entity.BookLoan;
import cz.muni.fi.pv243.library.entity.Booking;
import cz.muni.fi.pv243.library.entity.Librarian;
import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.entity.Role;
import cz.muni.fi.pv243.library.entity.Tag;
import cz.muni.fi.pv243.library.entity.User;

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
     
     @Inject
	 private UserManager userManager;
	 
	 @PersistenceContext
	 private EntityManager entityManager;


		

	    @PostConstruct
	    public void init() throws NoSuchAlgorithmException {
	    	
	    	// deletovani, kdyztak zakomentovat
	    	/*Query query= null;
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
	    	query.executeUpdate();
	    	query = entityManager.createQuery("DELETE FROM User u");
	    	query.executeUpdate();*/
	    	
	    	
	    

	    	
	    	// tagy
	    	Tag tag1 = new Tag();
	    	tag1.setName("Vzrušující literatura");
	    	
	    	Tag tag2 = new Tag();
	    	tag2.setName("Nutnost pro život");
	    	
	    	// readers
	    	Reader readerUser1 = new Reader();
	    	readerUser1.setUsername("readeruser");
	    	readerUser1.setPassword("password");
	    	Set<Role> readerUser1Roles = new HashSet<Role>();
	    	readerUser1Roles.add(Role.READER);
	    	readerUser1.setRoles(readerUser1Roles);
	    	readerUser1.setBirthDate(Calendar.getInstance());
	    	readerUser1.setCellNumber("42424242");
	    	readerUser1.setCity("Brno");
	    	readerUser1.setEmail("reader@mail.muni.cz");
	    	readerUser1.setFirstName("Hugo");
	    	readerUser1.setLastName("Kokoska");
	    	readerUser1.setStreet("Botanicka");
	    	readerUser1.setZipCode("60200");
	    	
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
	    	booking1.setReader(readerUser1);
	    	
	    	// employees
	    	Librarian librarianUser1 = new Librarian();
	    	librarianUser1.setUsername("librarianuser");
	    	librarianUser1.setPassword("password");
	    	Set<Role> librarianUser1Roles = new HashSet<Role>();
	    	librarianUser1Roles.add(Role.LIBRARIAN);
	    	librarianUser1.setRoles(librarianUser1Roles);
	    	librarianUser1.setFirstName("Valibuk");
	    	librarianUser1.setLastName("Štíhlý");
	    	
	    	// Bookcopys
	    	BookCopy bookCopy1 = new BookCopy();
	    	bookCopy1.setBook(book1);
	    	
	    	BookCopy bookCopy2 = new BookCopy();
	    	bookCopy2.setBook(book2);
	    	
	    	// Bookloans
	    	BookLoan bookLoan1 = new BookLoan();
	    	bookLoan1.setBeginDate("od včera"); // needs fix not to be string
	    	bookLoan1.setBookCopy(bookCopy1);
	    	bookLoan1.setReader(readerUser1);
	    	bookLoan1.setEmployee(librarianUser1);
	    	
	    	// naplneni:

	    	
	    	
	    	tagManager.create(tag1);
	    	tagManager.create(tag2);
	    	
	    	userManager.create(readerUser1);
	    	
	    	bookManager.create(book1);
	    	bookManager.create(book2);
	    	
	    	bookingManager.create(booking1);
	    	
	    	userManager.create(librarianUser1);
	    	
	    	bookCopyManager.create(bookCopy1);
	    	bookCopyManager.create(bookCopy2);
	    	
	    	bookLoanManager.create(bookLoan1);
	    }
	
}
