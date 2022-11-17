package server;

import java.io.IOException;

/**
 * This Interface represents the server.
 * The server is for receiving requests from the client and sending responses back to the client.
 * The current version is support for both TCP and UDP.
 */
public interface IServer {

  /**
   * Run and connect server.
   * @throws IOException for I/O Stream and Socket failed
   */
  void runServer() throws IOException;

  /**
   * Forcibly close server when handling force terminated signals.
   * The server should be running forever without any force terminated signals.
   */
  void close();

}
