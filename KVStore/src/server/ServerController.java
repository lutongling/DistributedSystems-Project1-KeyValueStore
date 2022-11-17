package server;

import java.io.IOException;

/**
 * This class is the Server controller/app with a main method to take 2 command line arguments:
 * 1. port number 2. TCP / UDP (protocol type)
 * to respond to the client by creating TCPServer / UDPServer instance.
 */
public class ServerController {
  private static ServerLogger logger = new ServerLogger("ServerController");

  public static void main(String[] args) throws IOException {
    if (args.length != 2 || !args[0].matches("\\d+")) {
      logger.logErrorMessage("Please enter in valid format: <Port Number> <TCP / UDP>");
      System.exit(1);
    } else if (!(args[1].equalsIgnoreCase("TCP") || args[1].equalsIgnoreCase("UDP"))) {
      logger.logErrorMessage("Please choose either TCP or UDP");
      System.exit(1);
    }

    // valid CLI args
    int portNum = Integer.parseInt(args[0]);
    String protocolType = args[1];

    try {
      IServer server = protocolType.equalsIgnoreCase("UDP") ?
              new UDPServer(portNum) : new TCPServer(portNum);

      try {
        server.runServer();
      } catch (IOException e) {
        logger.logErrorMessage("Server failed to connect.");
      }

      server.close();

    } catch (IOException e) {
      logger.logErrorMessage("Fail to create server socket/connect.");
    }


  }
}
