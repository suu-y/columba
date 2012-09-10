package org.columba.core.filter;

public interface IFilterList {

  /** The name of this object when stored in a XML document. */
  public static final String XML_NAME = "filterlist";

  /**
   * Adds the filter to this list.
   * 
   * @param f
   *            the filter.
   */
  void add(IFilter f);

  /**
   * Adds all filters in the supplied list to this filter list.
   * 
   * @param list
   *            a list containing other filters to add to this list.
   */
  void addAll(IFilterList list);

  /**
   * Remove the <code>Filter</code> from the list.
   * 
   * @param f
   *            the filter to remove.
   */
  void remove(IFilter f);

  /**
   * Inserts the filter into the specified index in the list.
   * 
   * @param filter
   *            filter to add.
   * @param index
   *            the index where to insert the filter.
   */
  void insert(IFilter filter, int index);

  /**
   * Moves the specified filter up in the list.
   * 
   * @param filter
   *            the filter to move up.
   */
  void moveUp(IFilter filter);

  /**
   * Moves the specified filter down in the list.
   * 
   * @param filter
   *            the filter to move down.
   */
  void moveDown(IFilter filter);

  /**
   * Moves the specified filter a number of positions in the list.
   * 
   * @param filter
   *            the filter to move.
   * @param nrOfPositions
   *            the number of positions to move in the list, can be negative.
   */
  void move(IFilter filter, int nrOfPositions);

  /**
   * Moves the filter at the specified index a number of positions in the
   * list.
   * 
   * @param filterIndex
   *            the filters index.
   * @param nrOfPositions
   *            the number of positions to move in the list, can be negative.
   */
  void move(int filterIndex, int nrOfPositions);

  /**
   * Returns the index in this list of the first occurrence of the specified
   * filter, or -1 if this list does not contain this element.
   * 
   * @param filter
   *            filter to search for.
   * @return the index in this list of the first occurrence of the specified
   *         filter, or -1 if this list does not contain this element.
   */
  int indexOf(IFilter filter);

  /**
   * Returns the number of filters in this list.
   * 
   * @return the number of filters in this list.
   */
  int count();

  /**
   * Returns the filter at the specified position in the list.
   * 
   * @param index
   *            the index
   * @return a Filter
   */
  IFilter get(int index);

  /**
   * Removes the filter at the specified list index.
   * 
   * @param index
   *            the index of the filter to remove from this list.
   */
  void remove(int index);

}