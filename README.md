# CountableSet
Specialized  java.util.Set that keeps a count of its elements

```
CountableSet<String> cs = new CountableSet<>();
cs.add("e1");

assertTrue(cs.size() == 1);
assertTrue(cs.lenght() == 1);

cs.add("e1");

assertTrue(cs.size() == 1);
assertTrue(cs.lenght() == 2);

cs.add("e2");

assertTrue(cs.size() == 2);
assertTrue(cs.lenght() == 3);

Map<String, Integer> m = cs.get("e1");
assertTrue(m.get("e1") == 2);

cs.remove("e1")

assertTrue(cs.size() == 2);
assertTrue(cs.lenght() == 2);
```
