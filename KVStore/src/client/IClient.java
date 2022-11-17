package client;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * This Interface represents the client.
 * The client is for user to send requests to the server and receive respond from the server.
 * The current version is support for both TCP and UDP.
 */
public interface IClient {
  /**
   * Run and connect client with server.
   * @throws IOException for I/O Stream and Socket failed
   * @throws TimeoutException for waiting for server exceed 10000ms
   */
  void runClient() throws IOException, TimeoutException;

  /**
   * Close client after user prompts "exit" or client stops.
   */
  void close();
}
