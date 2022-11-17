package server;

/**
 * This class is an abstract class extended by TCPServer and UDPServer. This abstract class
 * implements the Interface IServer and KeyValue.
 */
public abstract class AbstractServer implements IServer, KeyValue {
  protected int portNum;
  protected KeyValueStore store;
  protected ServerLogger logger;

  /**
   * Construct a server object with given port number.
   * @param port the given port number
   */
  public AbstractServer(int port) {
    portNum = port;
    store = new KeyValueStore();
    logger = null;
  }

  @Override
  public void put(String key, String value) {
    store.put(key, value);
  }

  @Override
  public String get(String key) {
    return store.get(key);
  }

  @Override
  public boolean delete(String key) {
    return store.delete(key);
  }

  /**
   * Return corresponding response to be sent back to the client by receiving the input request.
   * This protected method is called in sub-classes TCPServer and UDPServer.
   * @param str given input request
   * @return corresponding response to be sent back to the client by receiving the input request
   */
  protected String receivedRequest(String str) {
    String[] strings = str.split("\\s+");
    String cmd = strings[0];

    if (cmd.equalsIgnoreCase("put")) {
      String key = strings[1];
      String val = strings[2];
      put(key, val);
      logger.logInfoMessage("REQUEST - PUT; KEY => " + key + "; VALUE => " + val);

      str = "200;RESPONSE - Put operation successful";

      logger.logInfoMessage("Response => code: 200; "
              + "message: Put operation successful");

    } else if (cmd.equalsIgnoreCase("get")) {
      String key = strings[1];
      String val = get(key);

      logger.logInfoMessage("REQUEST - GET; KEY => " + key);

      if (val == null) {

        logger.logWarningMessage("Response => code: 404; "
                + "message: key not found");

        str = "404;RESPONSE - key not found";

      } else {

        logger.logInfoMessage("Response => code: 200; "
                + "message: " + val);

        str = "200;RESPONSE - " + val;
      }
    } else if (cmd.equalsIgnoreCase("delete")) {
      String key = strings[1];
      boolean hasDeleted = delete(key);

      logger.logInfoMessage("REQUEST - DELETE; KEY => " + key);

      if (!hasDeleted) {

        logger.logWarningMessage("Response => code: 404; "
                + "message: key not found");

        str = "404;RESPONSE - key not found";

      } else {

        logger.logInfoMessage("Response => code: 200; "
                + "message: Delete operation successful");

        str = "200;RESPONSE - Delete operation successful";
      }

    }

    return str;
  }



}
