package org.columba.core.association.api;

/**
 * 
 * represents one association for an item
 * an association is the tuple serviceId, metadataId, itemId
 *
 */
public interface IAssociation {
	
	/**
	 * @return the item id
	 */
	String getItemId();
	
	/**
	 * @return the service id 
	 */
	String getServiceId();
	
	/**
	 * @return the metadata id
	 */
	String getMetaDataId();
	
}
