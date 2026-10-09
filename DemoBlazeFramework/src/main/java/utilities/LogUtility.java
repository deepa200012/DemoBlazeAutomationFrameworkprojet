
package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtility {

    private static final Logger log =
            LogManager.getLogger(LogUtility.class);

    public static Logger getLogger() {
        return log;
    }
}
