package server;

/**
 * This Interface represents key-value pair operations applied to the Server.
 */
public interface KeyValue {

  /**
   * Insert a Key-Value pair to the storage.
   * NOTICE: Putting in an already-in-the-store key with a new value will override the old value.
   * @param key the unique identifier of the key value pair to be inserted
   * @param value the value of the unique identifier key to be inserted
   */
  void put(String key, String value);

  /**
   * Return the value of the given key, otherwise return null if the given key is not in the store
   * @param key the given key to get
   * @return the value of the given key, otherwise return null if the given key is not in the store
   */
  String get(String key);

  /**
   * Return true if the given key is in the store, and delete the key-value pair in the store,
   * otherwise return false if the given key cannot be found in the store
   * @param key given key to be deleted
   * @return true if the given key is in the store, and delete the key-value pair in the store,
   * otherwise return false if the given key cannot be found in the store
   */
  boolean delete(String key);
}
