package server;

import java.sql.Timestamp;
import java.util.logging.Logger;

/**
 * This class represents the logger for Server. It can log info, error or warning message
 * for server-side management.
 * The log is time-stamped with the current system time maintaining milliseconds precision.
 */
public class ServerLogger {

  private Logger logger;

  public ServerLogger(String className) {
    this.logger = Logger.getLogger(className);
  }

  public void logInfoMessage(String errorMsg) {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    logger.info(timestamp + " " + errorMsg);
  }

  public void logErrorMessage(String errorMsg) {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    logger.severe(timestamp + " " + errorMsg);
  }

  public void logWarningMessage(String warningMsg) {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    logger.warning(timestamp + " " + warningMsg);
  }

}

