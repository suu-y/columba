package org.columba.core.filter;

import org.columa.core.config.IDefaultItem;

public interface IFilterRule extends IDefaultItem{

  // Condition
  public final static int MATCH_ALL = 0;

  public final static int MATCH_ANY = 1;

  IFilterCriteria addEmptyCriteria();

  void add(IFilterCriteria criteria);

  void remove(int index);

  void removeAll();

  void removeLast();

  IFilterCriteria get(int index);

  int count();

  String getCondition();

  void setCondition(String s);

  void setCondition(int condition);

  /*
   * public FilterCriteria getCriteria(int index) { return (FilterCriteria)
   * list.get(index); }
   */
  int getConditionInt();

}