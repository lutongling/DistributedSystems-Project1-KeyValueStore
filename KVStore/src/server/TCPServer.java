package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class represents a TCP Server. It offers all operations mandated by the IServer interface.
 */
public class TCPServer extends AbstractServer {

  private BufferedReader in;
  private PrintWriter out;
  private BufferedReader userInput;
  private ServerSocket serverSocket;
  private Socket clientSocket;

  public TCPServer(int port) throws IOException {
    super(port);
    logger = new ServerLogger("TCPServer");
    in = null;
    out = null;
    userInput = null;
    serverSocket = new ServerSocket(portNum);

  }

  public void runServer() throws IOException {

    while(true) {

      // connected
      clientSocket = serverSocket.accept();

      // TODO REMOVE
      // System.out.println("connected");

      InetSocketAddress clientIpAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
      String clientIp = clientIpAddress.getAddress().getHostAddress();

      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      while(true) {
        // read client requests
        String str = in.readLine();

        // if client request is exit, just break out this nested loop and remain hanging, waiting
        // for other clients ask for connecting/reconnecting
        // NOTICE: this nested loop ensures the server would run forever without forcibly termination.
        if(str.equalsIgnoreCase("exit"))
          break;

        logger.logInfoMessage("TCP request received from address: /" + clientIp);

        // handling received request and store the corresponding response
        str = receivedRequest(str);

        // send response back to client
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(str);
      }

    }

  }

  @Override
  public void close() {
    try {
      if(in != null)
        in.close();
      if(out != null)
        out.close();
      if(userInput != null)
        userInput.close();

      serverSocket.close();
    } catch (IOException e) {
      logger.logErrorMessage("TCP Server Closed Failed.");
    }
  }

}
