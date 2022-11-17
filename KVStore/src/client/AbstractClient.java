package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * This class is an abstract class extended by TCPClient and UDPClient. This abstract class
 * implements the Interface IClient.
 */
public abstract class AbstractClient implements IClient {
  protected String hostName;
  protected int portNum;
  protected BufferedReader userInput;
  protected ClientLogger logger;

  /**
   * Construct a client object with given hostname/IP address and port number of server to connect.
   * @param host given hostname / IP address of server to connect
   * @param port given port number of server to connect
   */
  public AbstractClient(String host, int port) {
    hostName = host;
    portNum = port;
    userInput = null;
    logger = null;
  }

  /**
   * Prompt user to input requests and handle the user requests in human-readable logs.
   * This protected method is called in sub-classes TCPClient and UDPClient.
   * @param type type of Client, TCP or UDP
   * @return the request of user to be sent to server
   * @throws IOException when I/O stream failed
   */
  protected String readUserInput(String type) throws IOException {
    System.out.println("Enter the operation to be performed:");
    System.out.println("PUT <key> <value>");
    System.out.println("GET <key>");
    System.out.println("DELETE <key>");

    userInput = new BufferedReader(new InputStreamReader(System.in));

    String str = userInput.readLine();

    String[] strings = str.split("\\s+");

    String cmd = strings[0];
    if (cmd.equalsIgnoreCase("put")) {
      if (strings.length != 3) {
        printInvalid("PUT", str);
        return null;
      }
      logger.logInfoMessage("; HOST: " + hostName + "_" + type + "; "
                + "PORT_NO: " + portNum + "; "
                + "REQUEST - PUT; "
                + "KEY: " + strings[1] + "; "
                + "VALUE: " + strings[2]);

    } else if (cmd.equalsIgnoreCase("get")) {
      if (strings.length != 2) {
        printInvalid("GET", str);
        return null;
      }
      logger.logInfoMessage("; HOST: " + hostName + "_" + type + "; "
              + "PORT_NO: " + portNum + "; "
              + "REQUEST - GET; "
              + "KEY: " + strings[1] + "; ");

    } else if (cmd.equalsIgnoreCase("delete")) {
      if (strings.length != 2) {
        printInvalid("DELETE", str);
        return null;
      }
      logger.logInfoMessage("; HOST: " + hostName + "_" + type + "; "
              + "PORT_NO: " + portNum + "; "
              + "REQUEST - DELETE; "
              + "KEY: " + strings[1] + "; ");

    } else if (!cmd.equalsIgnoreCase("exit")) {
      printInvalid("", str);
      return null;
    }

    return str;
  }

  /**
   * Handle response retrieved back from server and print out in human-readable logs.
   * This protected method is called in sub-classes TCPClient and UDPClient.
   * @param response given response back from server
   */
  protected void handleResponse(String response) {
    String[] codeWithResponse = response.split(";");

    if(codeWithResponse[0].equals("200"))
      logger.logInfoMessage("; " + codeWithResponse[1]);
    else
      logger.logWarningMessage("; " + codeWithResponse[1]);
  }

  /**
   * Return a list of strings that represents hard-coded prepopulate PUT/GET/DELETE operations
   * to test before prompting user to give inputs.
   * This protected method is called in sub-classes TCPClient and UDPClient.
   * @return a list of strings that represents hard-coded prepopulate PUT/GET/DELETE operations
   */
  protected List<String> prepopulateOperations() {
    List<String> preOperations = new ArrayList<>();
    preOperations.add("put firstKey firstVal");
    preOperations.add("get firstKey");
    preOperations.add("delete firstKey");
    preOperations.add("put secondKey secondVal");
    preOperations.add("get secondKey");
    preOperations.add("delete secondKey");
    preOperations.add("put thirdKey thirdVal");
    preOperations.add("get thirdKey");
    preOperations.add("put thirdKey anotherThirdVal");
    preOperations.add("get thirdKey");
    preOperations.add("delete thirdKey");
    preOperations.add("put fourthKey fourthVal");
    preOperations.add("get fourthKey");
    preOperations.add("delete fourthKey");
    preOperations.add("put fifthKey fifthVal");
    preOperations.add("delete fifthKey");

    return preOperations;
  }

  /**
   * Helper method for readUserInput to avoid code duplication.
   * @param type request type
   * @param str request content
   */
  private void printInvalid(String type, String str) {
    if(type.equals(""))
      logger.logWarningMessage("Invalid request received => " + str);
    else
      logger.logWarningMessage(String.format("Invalid %s request received => " + str, type));
  }



}
