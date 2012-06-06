package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.library.entity.Booking;
import cz.muni.fi.pv243.library.entity.Tag;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@Stateless
public class BookingManager {
	

    @Inject
    @LibraryDatabase
	private EntityManager em;
	
	public void create(Booking booking){
		em.persist(booking);
	}
	
	public void delete(Booking booking){
		em.remove(em.merge(booking));
	}
	
	public void update(Booking booking){
		em.merge(booking);
	}

	public List<Booking> getAllBookings() {
		Query q = em.createQuery("SELECT b FROM Booking b");
		return q.getResultList();
	}
	
}
