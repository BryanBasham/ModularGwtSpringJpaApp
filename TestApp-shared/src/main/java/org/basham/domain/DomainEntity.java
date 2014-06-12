package org.basham.domain;

import java.io.Serializable;

/**
 * A contract for all domain entity classes.
 * 
 * @author <a href='mailto:basham47@gmail.com'>Bryan Basham</a> 
 */
public interface DomainEntity<K extends Serializable> extends Serializable {

	/**
	 * Gets the database ID for this Entity.
	 */
	K getId();
	
	/**
	 * Queries if the Entity is new; yet to be created in the database.
	 * 
	 * @return <code>true</code> if the Entity is new
	 */
	boolean isNew();
}
