package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * This class represents a TCP Client. It offers all operations mandated by the IClient interface.
 */
public class TCPClient extends AbstractClient {
  private PrintWriter out;
  private BufferedReader in;
  private Socket socket;

  public TCPClient(String host, int port) throws IOException {
    super(host, port);
    logger = new ClientLogger("TCPClient");
    out = null;
    in = null;
    socket = new Socket(host, port);
    socket.setSoTimeout(10000);
  }

  @Override
  public void runClient() throws IOException, TimeoutException {
    List<String> preOperations = prepopulateOperations();

    // run prepopulate hard-coded operations before prompting user to input
    for (String str : preOperations) {
      // send to server
      out = new PrintWriter(socket.getOutputStream(), true);
      out.println(str);

      // back from server
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      handleResponse(in.readLine());
    }

    System.out.println();

    while (true) {

      // prompting user to input, read and print out time-stamped logs
      String str = readUserInput("TCP");
      if(str == null)
        continue;

      // send to server
      out = new PrintWriter(socket.getOutputStream(), true);
      out.println(str);

      // stop and close the client if user put "exit" command
      if (str.equalsIgnoreCase("exit"))
        break;

      // back from server
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      // handle responses receiving from server
      handleResponse(in.readLine());

    }
  }

  @Override
  public void close() {
    try {
      if(userInput != null)
        userInput.close();
      if(out != null)
        out.close();
      if(in != null)
        in.close();

      socket.close();

    } catch (IOException e) {
      logger.logErrorMessage("TCP Client Closed Failed.");
    }
  }


}
