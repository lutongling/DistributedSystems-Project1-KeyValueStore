package server;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a key-value storage of memory. It implements KeyValue interface.
 * It is set up with using a Hashmap to store key value pairs.
 */
public class KeyValueStore implements KeyValue {
  private Map<String, String> dictionary;

  public KeyValueStore() {
    dictionary = new HashMap<>();
  }

  @Override
  public void put(String key, String value) {
    dictionary.put(key, value);
  }

  @Override
  public String get(String key) {
    if(!dictionary.containsKey(key)) {
      return null;
    }
    return dictionary.get(key);
  }

  @Override
  public boolean delete(String key) {
    if(!dictionary.containsKey(key)) {
      return false;
    }
    dictionary.remove(key);
    return true;
  }
}
