package org.columba.core.tagging;

import java.awt.Color;

import org.columba.core.tagging.api.ITag;

/**
 * Tag has a unique internal ID, a mandatory tag name and an optional color and description.
 *  
 * @author mhub
 * @author frd
 */
public class Tag implements ITag {
	
	private String id;
	//private Hashtable properties = new Hashtable();
	private Color color;
	private String name;
	private String description;
	
	public Tag(String newId, String name) {
		this.id = newId;
		this.name = name;
	}

	public String getId() {
		return id;
	}

//	public String getProperty(String name) {
//		return (String) properties.get(name);
//	}
//
//	public void setProperty(String name, String value) {
//		properties.put(name, value);
//	}
//
//	public Hashtable getProperties() {
//		return properties;
//	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public void setColor(Color color) {
		if ( color == null ) throw new IllegalArgumentException("color == null");
		this.color = color;
	}

	public void setName(String name) {
		if ( name == null ) throw new IllegalArgumentException("name == null");
		if ( name.length() == 0 ) throw new IllegalArgumentException("name length == 0");
		
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
