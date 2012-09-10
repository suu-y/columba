package org.columba.core.filter;

import org.columba.core.xml.XmlElement;

public interface IFilterCriteria {

  public final static int CONTAINS = 0;

  public final static int CONTAINS_NOT = 1;

  public final static int IS = 2;

  public final static int IS_NOT = 3;

  public final static int BEGINS_WITH = 4;

  public final static int ENDS_WITH = 5;

  public final static int DATE_BEFORE = 6;

  public final static int DATE_AFTER = 7;

  public final static int SIZE_SMALLER = 8;

  public final static int SIZE_BIGGER = 9;

  String getCriteriaString();

  void setCriteria(int c);

  int getCriteria();

  void setCriteriaString(String s);

  String getTypeString();

  void setTypeString(String s);

  String getPatternString();

  void setPatternString(String pattern);
  
  XmlElement getRoot();

}