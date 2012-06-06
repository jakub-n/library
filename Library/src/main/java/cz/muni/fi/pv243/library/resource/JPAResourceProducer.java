package cz.muni.fi.pv243.library.resource;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

@Singleton
public class JPAResourceProducer {

	@Produces
	@PersistenceContext(unitName="primary")
	@LibraryDatabase
	EntityManagerFactory libraryDatabasePersistenceUnit;
	
}
