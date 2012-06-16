package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.entity.Tag;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

/**
 * Manager class for Tag entity
 * 
 * @author esopeso
 * 
 */
@Stateless
public class TagManager {

	@Inject
	@LibraryDatabase
	private EntityManager em;

	@Inject
	private Logger log;

	/**
	 * Persists new tag
	 * 
	 * @param tag
	 */
	public void create(Tag tag) {
		this.em.persist(tag);
		this.log.infof("Tag created: %s", tag.getName());
	}

	/**
	 * Removes given tag
	 * 
	 * @param tag
	 */
	public void delete(Tag tag) {
		this.em.remove(this.em.merge(tag));
		this.log.infof("Tag deleted: %s", tag.getName());
	}

	/**
	 * Updates given tag
	 * 
	 * @param tag
	 */
	public void update(Tag tag) {
		this.em.merge(tag);
		this.log.infof("Tag updated: %s", tag.getName());
	}

	/**
	 * Returns all tags
	 * 
	 * @return all tags
	 */
	public List<Tag> getAllTags() {
		TypedQuery<Tag> q = this.em.createQuery("SELECT t FROM Tag t",
				Tag.class);
		return q.getResultList();
	}

}
