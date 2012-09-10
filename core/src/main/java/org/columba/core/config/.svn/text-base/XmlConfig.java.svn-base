package org.columba.core.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.columba.api.exception.StoreException;
import org.columba.api.shutdown.IShutdownManager;
import org.columba.core.io.DiskIO;
import org.columba.core.shutdown.ShutdownManager;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class XmlConfig {

	private static final java.util.logging.Logger LOG = java.util.logging.Logger
			.getLogger("org.columba.core.confignew");

	private String defaultConfig;

	private File xmlFile;

	private Document document;
	
	/**
	 * @param xmlFile
	 *            location of xml configuration file
	 * @param defaultConfig
	 *            location of default configuration file in classpath (for
	 *            example: "org/columba/core/config/tags"). Can be
	 *            <code>null</code>.
	 */
	public XmlConfig(final File xmlFile, final String defaultConfig) {
		if (xmlFile == null)
			throw new IllegalArgumentException("xmlFile == null");

		this.defaultConfig = defaultConfig;
		this.xmlFile = xmlFile;

		// register at shutdown manager
		// -> this will save all configuration data, when closing Columba
		final IShutdownManager shutdownManager = ShutdownManager.getInstance();

		shutdownManager.register(new Runnable() {
			public void run() {
				try {
					// callback
					transformModelToDocument(document);
					
					File tmpFile = File.createTempFile("tag", ".backup");
					
					BufferedOutputStream out = new BufferedOutputStream(
							new FileOutputStream(tmpFile));
					 
					saveDocument(document, out);
					
					// if everything worked out all right, we copy the tmp file to the destination file
					DiskIO.copyFile(tmpFile, xmlFile);
				} catch (final Exception e) {
					LOG.severe(e.getMessage());
					throw new StoreException(
							"could not store configuration file at: "
									+ xmlFile.getAbsolutePath(), e);
				}
			}
		});
	}

	public Document load() throws StoreException {
		if (!xmlFile.exists()) {
			if (defaultConfig != null) {
				// create initial version from default configuration file
				try {
					DiskIO.copyResource(defaultConfig + ".xml", xmlFile);
				} catch (IOException e) {
					LOG.severe(e.getMessage());
					throw new StoreException(
							"could not create initial configuration file at: "
									+ xmlFile.getAbsolutePath(), e);
				}
			} else {
				// return plain document
				document = new Document();
				return document;
			}

		}

		// load xml configuration from file
		BufferedInputStream buf;
		try {
			buf = new BufferedInputStream(new FileInputStream(xmlFile));
		} catch (FileNotFoundException e) {
			LOG.severe(e.getMessage());
			throw new StoreException("could not load configuration file at: "
					+ xmlFile.getAbsolutePath(), e);
		}

		document = retrieveDocument(buf);

		return document;
	}

	/**
	 * retrieve JDom Document from inputstream
	 */
	private Document retrieveDocument(InputStream is) throws StoreException {
		SAXBuilder builder = new SAXBuilder();
		builder.setIgnoringElementContentWhitespace(true);
		Document doc = null;
		try {
			doc = builder.build(is);
		} catch (JDOMException e) {
			LOG.severe(e.getMessage());
			throw new StoreException("could not parse configuration file at: "
					+ xmlFile.getAbsolutePath(), e);
		} catch (IOException e) {
			LOG.severe(e.getMessage());
			throw new StoreException("could not parse configuration file at: "
					+ xmlFile.getAbsolutePath(), e);
		}
		return doc;
	}

	/**
	 * store JDom document in outputstream
	 */
	public void saveDocument(Document doc, OutputStream os)
			throws StoreException {
		try {
			XMLOutputter outp = new XMLOutputter(" ", true);

			outp.output(doc, os);
		} catch (IOException e) {
			LOG.severe(e.getMessage());
			throw new StoreException("could not store configuration file at: "
					+ xmlFile.getAbsolutePath(), e);
		}
	}
	
	
	/**
	 * Overwrite method in case you transformed the xml presentation to your internal
	 * domain model. Before saving the xml document this method is used to transform
	 * the domain model back to the xml presentation.
	 * 
	 * @param document		jdom xml document
	 */
	protected void transformModelToDocument(Document document) {}
}
