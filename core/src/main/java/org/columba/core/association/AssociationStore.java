package org.columba.core.association;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.columba.core.association.api.IAssociation;
import org.columba.core.association.api.IAssociationStore;
import org.columba.core.config.DefaultConfigDirectory;

public class AssociationStore implements IAssociationStore, Runnable {

	final static String ENTITY_MANAGER = "associations";

	/** JDK 1.4+ logging framework logger, used for logging. */
	private static final Logger LOG = Logger
			.getLogger("org.columba.core.association.AssociationStore");

	EntityManagerFactory factory;

	EntityManager manager;

	Connection conn;

	static private AssociationStore instance;

	/**
	 * private constructor for the singelton implementation
	 *
	 */
	private AssociationStore() {
		factory = null;
		manager = null;
		conn = null;
	}

	public void addAssociation(String serviceId, String metaDataId,
			String itemId) {

		// passive: if not initialized do it now
		if (!isReady())
			init();

		// still not ready, exit!
		if (!isReady())
			return;

		// transaction is needed for the underlying jpa architecture
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			IAssociation association = new Association(itemId, serviceId,
					metaDataId);
			manager.persist(association);
			tx.commit();
		} catch (Exception ex) {
			if (tx.isActive()) {
				tx.rollback();
				LOG
						.severe("AddAssociation: Exception while persisting new association! Will Rollback! "
								+ serviceId + " " + metaDataId + " " + itemId);
			} else
				LOG
						.log(
								Level.SEVERE,
								"Got an Exception, but no Transaction active, so no Rollback!",
								ex);
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<IAssociation> getAllAssociations(String itemId) {

		// passive: if not initialized do it now
		if (!isReady())
			init();

		// still not ready, exit!
		if (!isReady())
			return new Vector<IAssociation>();

		// transaction is needed for the underlying jpa architecture
		EntityTransaction tx = manager.getTransaction();

		// wait, if there is an active transaction
		// TODO @author hubms retrycount!
		while (tx.isActive())
			;

		tx.begin();
		Query query = manager
				.createQuery("select a from org.columba.core.association.Association a where a.itemId = '"
						+ itemId + "'");
		Collection<IAssociation> results = (Collection<IAssociation>) query
				.getResultList();
		// for(IAssociation a : results) {
		// System.out.println("got a association: " + a.getItemId() + " " +
		// a.getMetaDataId() + " " + a.getServiceId());
		// }
		tx.commit();
		return results;
	}

	@SuppressWarnings("unchecked")
	public Collection<String> getAssociatedItems(String serviceId,
			String metaDataId) {

		// passive: if not initialized do it now
		if (!isReady())
			init();

		// still not ready, exit!
		if (!isReady())
			return new Vector<String>();

		// transaction is needed for the underlying jpa architecture
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Query query = manager
				.createQuery("select a from org.columba.core.association.Association a where a.serviceId = '"
						+ serviceId
						+ "' and a.metaDataId = '"
						+ metaDataId
						+ "'");
		Collection<IAssociation> results = (Collection<IAssociation>) query
				.getResultList();
		Collection<String> itemCollection = new LinkedList<String>();
		for (IAssociation a : results) {
			itemCollection.add(a.getItemId());
		}
		tx.commit();
		return itemCollection;
	}

	/**
	 * starts the jpa manager starts the database
	 */
	public void init() {

		// disable logging for the startup
		Handler[] handlers = Logger.getLogger("").getHandlers();
		ConsoleHandler consolehandler = null;
		Level level = null;
		for (int index = 0; index < handlers.length; index++) {
			// set console handler to OFF
			if (handlers[index] instanceof ConsoleHandler) {
				level = handlers[index].getLevel();
				handlers[index].setLevel(Level.OFF);
				consolehandler = (ConsoleHandler) handlers[index];
				break;
			}
		}

		//ShutdownManager.getInstance().register(this);

		String connectionString = "jdbc:hsqldb:file:"
				+ DefaultConfigDirectory.getInstance().getCurrentPath().getAbsolutePath()
				+ File.separator + "associations";

		// start HSQLDB
		try {

			// do not start a second time!
			if (conn == null) {
				Class.forName("org.hsqldb.jdbcDriver").newInstance();
				conn = DriverManager.getConnection(connectionString, "sa", "");
			}
		} catch (InstantiationException e) {
			LOG.severe("AssociationStore: Could not start the HSQLDB! " + e.getClass().getName()
					+ ": " + e.getMessage());
		} catch (IllegalAccessException e) {
			LOG.severe("AssociationStore: Could not start the HSQLDB! " + e.getClass().getName()
					+ ": " + e.getMessage());
		} catch (ClassNotFoundException e) {
			LOG.severe("AssociationStore: Could not start the HSQLDB! " + e.getClass().getName()
					+ ": " + e.getMessage());
		} catch (SQLException e) {
			LOG.severe("AssociationStore: Could not start the HSQLDB! " + e.getClass().getName()
					+ ": " + e.getMessage());
		}

		try {

			// manually rewrite the connection url, because
			// if there is no rewrite the database files are created
			// in the starting directory, we don't want that!
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("hibernate.connection.url", connectionString);
			map.put("exclude-unlisted-classes", "true"); // refers to the
															// entry in
															// persistence.xml

			// start JPA entity manager
			if (factory == null)
				factory = Persistence.createEntityManagerFactory(
						ENTITY_MANAGER, map);

			if (manager == null)
				manager = factory.createEntityManager(map);

		} catch (PersistenceException pEx) {
			LOG.severe("AssociationStore: Could not start the Entity manager! "
					+ pEx.getMessage());
		}

		// restore log level
		if (consolehandler != null)
			consolehandler.setLevel(level);

	}

	public boolean isReady() {
		return ((factory != null) && (manager != null) && (conn != null));
	}

	@SuppressWarnings("unchecked")
	public void removeAssociation(String serviceId, String metaDataId,
			String itemId) {

		// passive: if not initialized do it now
		if (!isReady())
			init();

		// still not ready, exit!
		if (!isReady())
			return;

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			Query query = manager
					.createQuery("select a from org.columba.core.association.Association a where a.itemId = '"
							+ itemId
							+ "' and a.serviceId = '"
							+ serviceId
							+ "'" + " and a.metaDataId = '" + metaDataId + "'");
			if (query.getResultList().size() > 1) {
				// more than one item, very strange! duplicate entries! remove
				// all
				LOG
						.info("RemoveAssociation: Got more than one association, that is strange! We try to remove all!");
				for (Object row : query.getResultList())
					manager.remove(row);
				tx.commit();
			} else if (query.getResultList().size() == 1) {
				manager.remove(query.getSingleResult());
				tx.commit();
			} else {
				// no item exists in table
				// -> nothing todo
			}
		} catch (Exception ex) {
			if (tx.isActive()) {
				tx.rollback();
				LOG
						.severe("RemoveAssociation: Exception while removing association! Will Rollback! "
								+ serviceId + " " + metaDataId + " " + itemId);
			} else {
				ex.printStackTrace();
				LOG
						.log(
								Level.SEVERE,
								"RemoveAssociation: Got an Exception, but no Transaction active, so no Rollback!",
								ex);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void removeItem(String itemId) {

		// passive: if not initialized do it now
		if (!isReady())
			init();

		// still not ready, exit!
		if (!isReady())
			return;

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			Query query = manager
					.createQuery("select a from org.columba.core.association.Association a where a.itemId = '"
							+ itemId + "'");
			List<Association> results = (List<Association>) query
					.getResultList();
			for (Association a : results) {
				manager.remove(a);
			}
			tx.commit();
		} catch (Exception ex) {
			LOG.severe("RemoveItem: could not remove item" + ex.getMessage());
			ex.printStackTrace();
			tx.rollback();
		}
	}

	public void shutdown() {

		// shutdown hsql
		try {
			// if the database is not started, don't do it
			if (conn != null) {
				Statement stmt = conn.createStatement();
				stmt.execute("SHUTDOWN");
			}
		} catch (SQLException e) {
			LOG.severe("AssociationStore: Could not shutdwon the database! "
					+ e.getClass().getName() + ": " + e.getMessage());
		}

		// shutdown entity manager
		if (manager != null)
			manager.close();

		if (factory != null)
			factory.close();
	}

	public static AssociationStore getInstance() {
		if (instance == null) {
			synchronized (AssociationStore.class) {
				if (instance == null)
					instance = new AssociationStore();
			}
		}
		return instance;
	}

	public void run() {
		shutdown();
	}
}
