package client;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * This class is the Client controller/app with a main method to take 3 command line arguments:
 * 1. hostname / IP address 2. port number 3. TCP / UDP (protocol type)
 * to connect to the server by creating TCPClient / UDPClient instance.
 */
public class ClientController {
  private static ClientLogger logger = new ClientLogger("ClientController");

  public static void main(String[] args) {
    if (args.length != 3 || !args[1].matches("\\d+")) {
      logger.logErrorMessage("Please enter in valid format: <Host Name / IP> <Port Number> <TCP / UDP>");
      System.exit(1);
    } else if (!(args[2].equalsIgnoreCase("TCP") || args[2].equalsIgnoreCase("UDP"))) {
      logger.logErrorMessage("Please choose either TCP or UDP");
      System.exit(1);
    }

    // valid CLI args
    String hostName = args[0];
    int portNum = Integer.parseInt(args[1]);
    String protocolType = args[2];

    try {
      IClient client = protocolType.equalsIgnoreCase("UDP") ?
              new UDPClient(hostName, portNum) : new TCPClient(hostName, portNum);

      try {
        client.runClient();
      } catch (IOException e) {
        logger.logErrorMessage("Client failed to connect.");
      } catch (TimeoutException e) {
        logger.logErrorMessage("Time out. Unresponsive Server.");
      }

      client.close();
    } catch (IOException e) {
      logger.logErrorMessage("Fail to create client socket/connect.");
    }

  }
}
