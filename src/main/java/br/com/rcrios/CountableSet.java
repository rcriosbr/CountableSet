package br.com.rcrios;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Specialized {@code Set} that keeps a count of its elements. For example, when adding the same element {@code e1}
 * twice, since a {@code Set} cannot contains duplicate elements, it's size will be equals 1, but it's length will be
 * equals 2:
 *
 * <pre>
 *
 * CountableSet&#60;String&#62; cs = new CountableSet&#60;&#62;();
 * cs.add("e1");
 *
 * assertTrue(cs.size() == 1);
 * assertTrue(cs.lenght() == 1);
 *
 * cs.add("e1");
 *
 * assertTrue(cs.size() == 1);
 * assertTrue(cs.lenght() == 2);
 * </pre>
 *
 * @param <E> the type of elements maintained by this set
 */
public class CountableSet<E> extends AbstractSet<E> implements Set<E>, Cloneable, Serializable {

   private static final long serialVersionUID = 7486882967017248953L;

   private transient HashMap<E, Integer> elements;

   /**
    * Constructs a new, empty {@code CountableSet}; The backing {@code HashMap} instance has default initial capacity
    * (16) and load factor (0.75).
    */
   public CountableSet() {
      this.elements = new HashMap<>();
   }

   /**
    * Constructs a new {@code CountableSet} containing the elements in the specified collection. The backing
    * {@code HashMap} is created with default load factor (0.75) and an initial capacity sufficient to contain the
    * elements in the specified collection.
    *
    * @param c the collection whose elements are to be placed into this {@code CountableSet}
    * @throws NullPointerException if the specified collection is null
    */
   public CountableSet(Collection<? extends E> c) {
      this.elements = new HashMap<>(Math.max((int) (c.size() / .75f) + 1, 16));
      this.addAll(c);
   }

   /**
    * Constructs a new, empty {@code CountableSet}; The backing {@code HashMap} instance will be initialized with the
    * provided initial capacity and load factor.
    *
    * @param initialCapacity the initial capacity of the hash map
    *
    * @param loadFactor      the load factor of the hash map
    *
    * @throws IllegalArgumentException if the initial capacity is less than zero, or if the load factor is nonpositive
    */
   public CountableSet(int initialCapacity, float loadFactor) {
      this.elements = new HashMap<>(initialCapacity, loadFactor);
   }

   /**
    * Constructs a new, empty {@code CountableSet}; the backing {@code HashMap} instance has the specified initial
    * capacity and default load factor (0.75).
    *
    * @param initialCapacity the initial capacity of the hash table
    * @throws IllegalArgumentException if the initial capacity is less than zero
    */
   public CountableSet(int initialCapacity) {
      this.elements = new HashMap<>(initialCapacity);
   }

   /**
    * Adds the specified element to this {@code CountableSet}. If this set already contains the element, increments
    * element count, keeping the set size unaltered.
    *
    * @return Will always return {@code true}
    */
   @Override
   public boolean add(E e) {
      Integer count = this.elements.get(e);
      if (count == null) {
         count = 1;
      } else {
         count++;
      }
      this.elements.put(e, count);

      return true;
   }

   /**
    * Adds all of the elements in the specified collection to this {@code CountableSet}. if elements are already
    * present, increment its count. The behavior of this operation is undefined if the specifiedcollection is modified
    * while the operation is in progress.
    *
    * @return {@code true} if this set length is changed, otherwise returns {@code false}
    */
   @Override
   public boolean addAll(Collection<? extends E> c) {
      boolean modified = true;

      int currentLenght = this.lenght();
      for (E e : c) {
         this.add(e);
      }

      int modifiedLenght = this.lenght();
      if (Integer.compare(currentLenght, modifiedLenght) == 0) {
         modified = false;
      }

      return modified;
   }

   /**
    * Removes the specified element from this {@code CountableSet}. If element count is greater than 1, decreases
    * element count but KEEPS IT into the set:
    *
    * <pre>
    * CountableSet&#60;String&#62; cs = new CountableSet&#60;&#62;();
    * cs.add("e1");
    * cs.add("e1");
    *
    * assertTrue(cs.size() == 1);
    * assertTrue(cs.lenght() == 2);
    *
    * assertFalse(cs.remove("e666"));
    * assertTrue(cs.remove("e1"));
    *
    * assertTrue(cs.size() == 1);
    * assertTrue(cs.lenght() == 1);
    *
    * assertTrue(cs.remove("e1"));
    * assertTrue(cs.size() == 0);
    * assertTrue(cs.lenght() == 0);
    * </pre>
    *
    * @param o object to be removed from this set
    * @return {@code false} if the element doesn't exists into this set. {@code true} otherwise.
    */
   @SuppressWarnings("unchecked")
   @Override
   public boolean remove(Object o) {
      Integer count = this.elements.get(o);

      if (count == null) {
         return false;
      }

      if (count > 1) {
         count--;
         this.elements.put((E) o, count);
      } else {
         this.elements.remove(o);
      }

      return true;
   }

   /**
    * Deletes the specified element from this {@code CountableSet}, REGARDLESS its count.
    *
    * <pre>
    * CountableSet&#60;String&#62; cs = new CountableSet&#60;&#62;(Arrays.asList("e1", "e1"));
    *
    * assertTrue(cs.size() == 1);
    * assertTrue(cs.lenght() == 2);
    *
    * assertFalse(cs.delete("e666"));
    * assertTrue(cs.delete("e1"));
    *
    * assertTrue(cs.isEmpty());
    * assertTrue(cs.lenght() == 0);
    * </pre>
    *
    * @param o object to be deleted from this set
    *
    * @return {@code false} if the element doesn't exists into this set. {@code true} otherwise.
    */
   public boolean delete(Object o) {
      Integer count = this.elements.remove(o);

      if (count == null) {
         return false;
      }

      return true;
   }

   /**
    * Retrieves a specific element from this {@code CountableSet}.
    *
    * @param e The element to be retrieved.
    *
    * @return {@code Null} if the element doen't exist into this set. Otherwise returns a {@code Map<E, Integer>(1)}
    *         whose {@code key} is the element itself, and {@code value} is the count of that element.
    */
   public Map<E, Integer> getRaw(E e) {
      Integer count = this.elements.get(e);

      if (count == null) {
         return null;
      }

      Map<E, Integer> m = new HashMap<>(1);
      m.put(e, count);

      return m;
   }

   /**
    * Returns the count to which the specified element is mapped, or null if this set doesn't contains the key.
    *
    * More formally, if backing map contains a mapping from a key k to a value v such that (key==null ? k==null
    * :key.equals(k)), then this method returns v; otherwise it returns null.
    *
    * @param e Element from which the count will be retrieved
    *
    * @return {@code Null} if the element doesn't exist, or an {@code Integer} with its count
    */
   public Integer get(E e) {
      return this.elements.get(e);
   }

   @Override
   public Iterator<E> iterator() {
      return this.elements.keySet().iterator();
   }

   /**
    * Returns the number of elements in this set (its cardinality).
    *
    * @return the number of elements in this set (its cardinality)
    */
   @Override
   public int size() {
      return this.elements.size();
   }

   /**
    * The sum of all values from this {@code CountableSet}.
    *
    * @return
    */
   public int lenght() {
      int sum = 0;
      for (Integer value : this.elements.values()) {
         sum = sum + value;
      }

      return sum;
   }

   /**
    * Verifies if this {@code CountableSet} empty or not.
    *
    * @return {@code true} if this set contains no elements. {@code false} otherwise.
    */
   @Override
   public boolean isEmpty() {
      return this.elements.isEmpty();
   }

   /**
    * Retrieves this {@code CountableSet} elements ordered by their count values.
    *
    * @param descending If {@code true} elements will be ordered decreasingly.
    *
    * @return A {@code Map<E, Integer>} whose {@code key} is the element, and {@code value} is the count of respective
    *         element.
    */
   public Map<E, Integer> getSortedElementsByCount(boolean descending) {
      List<Map.Entry<E, Integer>> list = new LinkedList<>(this.elements.entrySet());

      Collections.sort(list, new Comparator<Map.Entry<E, Integer>>() {
         @Override
         public int compare(Map.Entry<E, Integer> o1, Map.Entry<E, Integer> o2) {
            if (descending) {
               return o2.getValue().compareTo(o1.getValue());
            } else {
               return o1.getValue().compareTo(o2.getValue());
            }
         }
      });

      Map<E, Integer> sortedMap = new LinkedHashMap<>(this.elements.size());
      for (Map.Entry<E, Integer> entry : list) {
         sortedMap.put(entry.getKey(), entry.getValue());
      }

      return sortedMap;
   }
}
