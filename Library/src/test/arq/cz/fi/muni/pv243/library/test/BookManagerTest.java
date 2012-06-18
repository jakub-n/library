package cz.fi.muni.pv243.library.test;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;
import javax.naming.Context;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;

import org.jboss.shrinkwrap.api.Archive;

import org.jboss.shrinkwrap.api.ShrinkWrap;

import org.jboss.shrinkwrap.api.asset.EmptyAsset;

import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.solder.logging.Logger;

import org.junit.Test;

import org.junit.runner.RunWith;

import cz.muni.fi.pv243.library.ejb.BookManager;
import cz.muni.fi.pv243.library.entity.Book;

@RunWith(Arquillian.class)
public class BookManagerTest {

	@Deployment
	public static Archive<?> createTestArchive() {

		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addClasses(BookManager.class, Resources.class,
						JBossLoginContextFactory.class, Logger.class)
				.addPackage("cz.muni.fi.pv243.library.entity")

				.addAsResource("META-INF/persistence.xml",
						"META-INF/persistence.xml")

				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

	}
	
	@Inject
	BookManager bm;

	@Test
	public void testRegister() throws LoginException {

	//	LoginContext loginContext = JBossLoginContextFactory
	//			.createLoginContext("librarianuser", "password");
	//	loginContext.login();

		Book book = new Book();
		book.setAuthor("Jendrock Erics");
		book.setIsbn("978-0137081862  ");
		book.setPagesNumber(768);
		book.setLanguage(Locale.ENGLISH);
		book.setTitle("The Java EE 6 Tutorial: Topics");
		book.setPublicationDate(Calendar.getInstance());
		bm.create(book);
		assertNotNull(book.getBookId());

	}
}
