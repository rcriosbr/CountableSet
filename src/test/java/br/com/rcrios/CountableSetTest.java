package br.com.rcrios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CountableSetTest {

   @Test
   public void testAddE() {
      String e1 = "e1";

      CountableSet<String> cs = new CountableSet<>();
      assertTrue(cs.isEmpty());
      assertNull(cs.get(e1));

      assertTrue(cs.add(e1));
      assertTrue(cs.size() == 1);
      assertTrue(cs.lenght() == 1);

      Map<String, Integer> m = cs.getRaw(e1);
      assertTrue(m.get(e1) == 1);
      assertTrue(cs.get(e1) == 1);

      cs.add(e1);
      assertTrue(cs.size() == 1);
      assertTrue(cs.lenght() == 2);

      m = cs.getRaw(e1);
      assertTrue(m.get(e1) == 2);
      assertTrue(cs.get(e1) == 2);
   }

   @Test
   public void testAddWithCount(){
      String e1 = "e1";

      CountableSet<String> cs = new CountableSet<>();
      
      cs.add(e1, 5);
      assertTrue(cs.size() == 1);
      assertTrue(cs.lenght() == 5);

      cs.add(e1);
      assertTrue(cs.size() == 1);
      assertTrue(cs.lenght() == 6);
   }

   @Test
   public void testRemoveObject() {
      String e1 = "e1";
      String e2 = "e2";

      CountableSet<String> cs = new CountableSet<>();
      cs.add(e1);
      cs.add(e1);
      cs.add(e2);

      assertFalse(cs.remove("e666"));

      assertTrue(cs.remove(e1));
      assertTrue(cs.size() == 2);

      Map<String, Integer> m = cs.getRaw(e1);
      assertTrue(m.get(e1) == 1);
      assertTrue(cs.remove(e1));
      assertTrue(cs.size() == 1);

      m = cs.getRaw(e2);
      assertTrue(m.get(e2) == 1);

      cs.remove(e2);
      assertNull(cs.get(e2));

      assertTrue(cs.isEmpty());
      assertTrue(cs.lenght() == 0);
   }

   @Test
   public void testDelete() {
      CountableSet<String> cs = new CountableSet<>(Arrays.asList("e1", "e1"));

      assertTrue(cs.size() == 1);
      assertTrue(cs.lenght() == 2);

      assertFalse(cs.delete("e666"));
      assertTrue(cs.delete("e1"));

      assertTrue(cs.isEmpty());
      assertTrue(cs.lenght() == 0);
   }

   @Test
   public void testAddAll() {
      CountableSet<String> cs = new CountableSet<>();

      cs.addAll(Arrays.asList("e1", "e2", "e3"));

      assertTrue(cs.size() == 3);
      assertTrue(cs.lenght() == 3);

      cs.addAll(Arrays.asList("e1", "e3"));

      assertTrue(cs.size() == 3);
      assertTrue(cs.lenght() == 5);

      Map<String, Integer> m = cs.getRaw("e1");
      assertTrue(m.get("e1") == 2);

      m = cs.getRaw("e2");
      assertTrue(m.get("e2") == 1);

      m = cs.getRaw("e3");
      assertTrue(m.get("e3") == 2);

      List<String> empty = Collections.emptyList();
      assertFalse(cs.addAll(empty));

   }

   @Test
   public void testLenght() {
      String e1 = "e1";
      String e2 = "e2";

      CountableSet<String> cs = new CountableSet<>();
      cs.add(e1);
      cs.add(e1);
      cs.add(e2);

      assertTrue(cs.size() == 2);
      assertTrue(cs.lenght() == 3);
   }

   @Test
   public void testGetSortedElements() {
      String e1 = "e1";
      String e2 = "e2";
      String e3 = "e3";

      CountableSet<String> cs = new CountableSet<>();
      cs.add(e3);
      cs.add(e1);
      cs.add(e1);
      cs.add(e2);
      cs.add(e2);
      cs.add(e2);

      Map<String, Integer> m = cs.getSortedElementsByCount(true);

      Object[] keys = m.keySet().toArray();
      assertEquals(3, m.get(keys[0]));
      assertEquals(2, m.get(keys[1]));
      assertEquals(1, m.get(keys[2]));
   }

   @Test
   public void testIterator() {
      CountableSet<String> cs = new CountableSet<>(Arrays.asList("e1", "e1", "e2"));

      Iterator<String> i = cs.iterator();
      assertNotNull(i);

      int count = 0;
      while (i.hasNext()) {
         i.next();
         count++;
      }

      assertEquals(2, count);
   }
}
