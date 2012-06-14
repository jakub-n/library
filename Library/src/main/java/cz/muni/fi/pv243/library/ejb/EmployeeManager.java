package cz.muni.fi.pv243.library.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.entity.Employee;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@Stateless
public class EmployeeManager {
	

    @Inject
    @LibraryDatabase
	private EntityManager em;
    
    @Inject
    private Logger log;
	
    /**
     * Persists new employee
     * 
     * @param emp
     */
	public void create(Employee emp){
		em.persist(emp);
		log.infof("Employee created %s", emp.getUsername());
	}
	
	/**
	 * Removes given employee
	 * 
	 * @param emp
	 */
	public void delete(Employee emp){
			em.remove(em.merge(emp));
			log.infof("Employee deleted %s", emp.getUsername());
	}
	
	/**
	 * Updates given employee
	 * 
	 * @param emp
	 */
	public void update(Employee emp){
		em.merge(emp);
		log.infof("Employee updated %s", emp.getUsername());
	}
	
	/**
	 * Returns employee with given username
	 * 
	 * @param username
	 * @return
	 */
	public Employee getEmployeeByName(String username){
		Query q 
		= em.createQuery
		("SELECT a FROM Employee a WHERE a.username=:name").setParameter("name", username);
		return (Employee)q.getSingleResult();
	}

}
