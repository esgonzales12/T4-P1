package log;

import java.util.logging.Logger;

/**
 * Estefan Gonzales
 * Logging class
 * This class is used to add a logger to a static and or main() class
 */
public class StaticLogBase {
    protected static Logger log = Logger.getLogger("StaticLogBase");
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
    }
}