package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.entity.Booking;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@Stateless
public class BookingManager {
	

    @Inject
    @LibraryDatabase
	private EntityManager em;
    
    @Inject
    private Logger log;
	
    /**
     * Persists new booking
     * 
     * @param booking
     */
	public void create(Booking booking){
		em.persist(booking);
		log.infof("Booking created: %d",booking.getId());
	}
	
	/**
	 * Removes given booking
	 * 
	 * @param booking
	 */
	public void delete(Booking booking){
		em.remove(em.merge(booking));
		log.infof("Booking deleted: %d",booking.getId());
	}
	
	/**
	 * Updates given booking
	 * 
	 * @param booking
	 */
	public void update(Booking booking){
		em.merge(booking);
		log.infof("Booking updated: %d",booking.getId());
	}

	/**
	 * Returns all bookings
	 * 
	 * @return all bookings
	 */
	public List<Booking> getAllBookings() {
		TypedQuery<Booking> q = em.createQuery("SELECT b FROM Booking b",Booking.class);
		return q.getResultList();
	}
	
}
