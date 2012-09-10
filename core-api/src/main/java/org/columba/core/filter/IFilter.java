package org.columba.core.filter;

import org.columa.core.config.IDefaultItem;
import org.columba.core.xml.XmlElement;

public interface IFilter extends IDefaultItem{

  /**
   * 
   * @return FilterActionList this is also a simple wrapper
   */
  IFilterActionList getFilterActionList();

  /**
   * 
   * 
   * @return FilterRule this is also a simple wrapper
   */
  IFilterRule getFilterRule();

  /**
   * Is filter enabled?
   * 
   * @return boolean true if enabled
   */
  boolean getEnabled();

  /**
   * 
   * enable Filter
   * 
   * @param bool
   *            if true enable filter otherwise disable filter
   */
  void setEnabled(boolean bool);

  /**
   * Set filter name
   * 
   * @param s
   *            new filter name
   */
  void setName(String s);

  /**
   * 
   * return Name of Filter
   * 
   * @return String
   */
  String getName();

  /** {@inheritDoc} */
  Object clone();

  /** {@inheritDoc} */
  String toString();
  
}