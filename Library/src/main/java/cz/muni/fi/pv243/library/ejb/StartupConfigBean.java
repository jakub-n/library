package cz.muni.fi.pv243.library.ejb;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.entity.Book;
import cz.muni.fi.pv243.library.entity.BookCopy;
import cz.muni.fi.pv243.library.entity.BookLoan;
import cz.muni.fi.pv243.library.entity.Booking;
import cz.muni.fi.pv243.library.entity.Librarian;
import cz.muni.fi.pv243.library.entity.Manager;
import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.entity.Role;
import cz.muni.fi.pv243.library.entity.Tag;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@Singleton
@Startup
public class StartupConfigBean {

	@Inject
	@LibraryDatabase
	private EntityManager em;

	@Inject
	private Logger log;

	@PostConstruct
	public void init() throws NoSuchAlgorithmException {

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

		Librarian librarianUser2 = new Librarian();
		librarianUser2.setUsername("librarianuser2");
		librarianUser2.setPassword("password");
		Set<Role> librarianUser2Roles = new HashSet<Role>();
		librarianUser2Roles.add(Role.LIBRARIAN);
		librarianUser2.setRoles(librarianUser2Roles);
		librarianUser2.setFirstName("Dobromysl");
		librarianUser2.setLastName("Pracovitý");

		Librarian librarianUser3 = new Librarian();
		librarianUser3.setUsername("librarianuser3");
		librarianUser3.setPassword("password");
		Set<Role> librarianUser3Roles = new HashSet<Role>();
		librarianUser3Roles.add(Role.LIBRARIAN);
		librarianUser3.setRoles(librarianUser3Roles);
		librarianUser3.setFirstName("Karel");
		librarianUser3.setLastName("Druhý");

		Librarian librarianUser4 = new Librarian();
		librarianUser4.setUsername("librarianuser4");
		librarianUser4.setPassword("password");
		Set<Role> librarianUser4Roles = new HashSet<Role>();
		librarianUser4Roles.add(Role.LIBRARIAN);
		librarianUser4.setRoles(librarianUser4Roles);
		librarianUser4.setFirstName("Vzoromil");
		librarianUser4.setLastName("Nerozhodný");

		Librarian librarianUser5 = new Librarian();
		librarianUser5.setUsername("librarianuser5");
		librarianUser5.setPassword("password");
		Set<Role> librarianUser5Roles = new HashSet<Role>();
		librarianUser5Roles.add(Role.LIBRARIAN);
		librarianUser5.setRoles(librarianUser5Roles);
		librarianUser5.setFirstName("Otakar");
		librarianUser5.setLastName("Sedminohý");

		Librarian librarianUser6 = new Librarian();
		librarianUser6.setUsername("librarianuser6");
		librarianUser6.setPassword("password");
		Set<Role> librarianUser6Roles = new HashSet<Role>();
		librarianUser6Roles.add(Role.LIBRARIAN);
		librarianUser6.setRoles(librarianUser6Roles);
		librarianUser6.setFirstName("Vilém");
		librarianUser6.setLastName("Dokonavý");

		Librarian librarianUser7 = new Librarian();
		librarianUser7.setUsername("librarianuser7");
		librarianUser7.setPassword("password");
		Set<Role> librarianUser7Roles = new HashSet<Role>();
		librarianUser7Roles.add(Role.LIBRARIAN);
		librarianUser7.setRoles(librarianUser7Roles);
		librarianUser7.setFirstName("Vávra");
		librarianUser7.setLastName("Mírumilovný");

		Manager managerUser1 = new Manager();
		managerUser1.setUsername("manageruser");
		managerUser1.setPassword("password");
		Set<Role> managerUser1Roles = new HashSet<Role>();
		managerUser1Roles.add(Role.MANAGER);
		managerUser1.setRoles(managerUser1Roles);
		managerUser1.setFirstName("Přemysl");
		managerUser1.setLastName("Velkolepý");

		// Bookcopys
		BookCopy bookCopy1 = new BookCopy();
		bookCopy1.setBook(book1);

		BookCopy bookCopy2 = new BookCopy();
		bookCopy2.setBook(book2);

		// Bookloans
		BookLoan bookLoan1 = new BookLoan();
		bookLoan1.setBeginDate(Calendar.getInstance());
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		sdf.format(date);
		Calendar cal = new GregorianCalendar(
				Integer.parseInt(sdf.format(date)), Integer.parseInt(sdf2
						.format(date)), Integer.parseInt(sdf3.format(date)) + 1);
		bookLoan1.setReturnDate(cal);
		bookLoan1.setBookCopy(bookCopy1);
		bookLoan1.setReader(readerUser1);
		bookLoan1.setEmployee(librarianUser1);

		// naplneni:
		this.em.persist(tag1);
		this.em.persist(tag2);

		this.em.persist(readerUser1);

		this.em.persist(librarianUser1);
		this.em.persist(librarianUser2);
		this.em.persist(librarianUser3);
		this.em.persist(librarianUser4);
		this.em.persist(librarianUser5);
		this.em.persist(librarianUser6);
		this.em.persist(librarianUser7);

		// add managerManager :)
		this.em.persist(managerUser1);

		this.em.persist(book1);
		this.em.persist(book2);

		this.em.persist(booking1);

		this.em.persist(bookCopy1);
		this.em.persist(bookCopy2);

		this.em.persist(bookLoan1);

		this.log.infof("Startup initialization successful");
	}

}
