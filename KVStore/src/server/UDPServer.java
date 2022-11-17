package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.*;

/**
 * This class represents a UDP Server. It offers all operations mandated by the IServer interface.
 */
public class UDPServer extends AbstractServer {
  private DatagramSocket socket;
  private byte[] buffer;
  private DatagramPacket dgPacket;
  private BufferedReader userInput;

  public UDPServer(int port) throws SocketException {
    super(port);
    logger = new ServerLogger("UDPServer");
    socket = new DatagramSocket(portNum);
    userInput = null;
    buffer = new byte[10000];
    dgPacket = new DatagramPacket(buffer, 0, buffer.length);
  }

  @Override
  public void runServer() throws IOException {
    // By default, this while loop should run forever without any forcibly termination
    // This represents the server would hang forever
    while (true) {

      // receive requests
      socket.receive(dgPacket);

      // convert dataBytes data from packet to string to handle request more easily
      byte[] dataBytes = dgPacket.getData();
      String str = new String(dataBytes, 0, dgPacket.getLength());

      // if client-side request is "exit", continue to receive client datagram packet
      if (str.equalsIgnoreCase("exit"))
        continue;

      logger.logInfoMessage("UDP request received from address: " + dgPacket.getAddress().toString());

      // handling received request and store the corresponding response
      str = receivedRequest(str);

      // send response back to client
      byte[] respData = str.getBytes();
      DatagramPacket packet = new DatagramPacket(respData, respData.length, new InetSocketAddress(dgPacket.getAddress(), dgPacket.getPort()));
      socket.send(packet);

    }
  }

  @Override
  public void close() {
    try {
      if(socket != null)
        socket.close();
      if(userInput != null)
        userInput.close();

    } catch (IOException e) {
      logger.logErrorMessage("UDP Server Closed Failed.");
    }
  }

}
