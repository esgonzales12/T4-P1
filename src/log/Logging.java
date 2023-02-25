package log;

import java.util.logging.Logger;

/**
 * Estefan Gonzales
 * This class is used to add a logger to a class
 * while being able to update log output format.
 */
public class Logging {
    protected Logger log;
    public Logging(String logName) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        this.log = Logger.getLogger(logName);
    }
}