package client;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * This class represents a UDP Client. It offers all operations mandated by the IClient interface.
 */
public class UDPClient extends AbstractClient {
  private DatagramSocket socket;

  public UDPClient(String host, int port) throws IOException {
    super(host, port);
    logger = new ClientLogger("UDPClient");
    socket = new DatagramSocket();
    socket.setSoTimeout(10000);
  }

  @Override
  public void runClient() throws IOException, TimeoutException {
    List<String> preOperations = prepopulateOperations();

    // run prepopulate hard-coded operations before prompting user to input
    for (String str : preOperations) {
      byte[] buffer = str.getBytes(StandardCharsets.UTF_8);
      DatagramPacket dgPacket =
              new DatagramPacket(buffer, 0, buffer.length, InetAddress.getByName(hostName), portNum);

      // send to server
      socket.send(dgPacket);

      // back from server
      getResponse();
    }

    System.out.println();

    while(true) {

      // prompting user to input, read and print out time-stamped logs
      String str = readUserInput("UDP");

      if(str == null)
        continue;

      byte[] buffer = str.getBytes(StandardCharsets.UTF_8);

      DatagramPacket dgPacket =
              new DatagramPacket(buffer, 0, buffer.length, InetAddress.getByName(hostName), portNum);

      // send to server
      socket.send(dgPacket);

      // stop and close the client if user put "exit" command
      if(str.equalsIgnoreCase("exit"))
        break;

      // back from server
      getResponse();

    }

  }

  /**
   * Helper method for runClient to avoid code duplication.
   * Handling received responses from server.
   * @throws IOException for I/O Stream and Socket failed
   */
  private void getResponse() throws IOException {
    byte[] resBuffer = new byte[10000];
    DatagramPacket resDgPacket =
            new DatagramPacket(resBuffer, 0, resBuffer.length, InetAddress.getByName(hostName), portNum);

    socket.receive(resDgPacket);
    byte[] dataBytes = resDgPacket.getData();

    String response = new String(dataBytes, 0, resDgPacket.getLength());

    handleResponse(response);
  }

  @Override
  public void close() {
    try {
      if(userInput != null)
        userInput.close();

      socket.close();
    } catch (IOException e) {
      logger.logErrorMessage("UDP Client Closed Failed.");
    }
  }

}