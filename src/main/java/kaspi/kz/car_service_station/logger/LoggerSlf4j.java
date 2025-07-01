package kaspi.kz.car_service_station.logger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoggerSlf4j {
    Logger logger = LoggerFactory.getLogger(LoggerSlf4j.class);
    private final UUID uuid = UUID.randomUUID();
    private String loggerName;
    private String userName;
    private int count = 0;


    public String info(String message) {
        String msg = getMessage(message);
        logger.info(msg);
        return msg;
    }

    public void error(String msg, Exception ex) {
        if (logger.isErrorEnabled()) {
            logger.error(getMessage(msg), ex);
        }
    }

    public String getMessage (String msg){
        count++;
        StringBuilder result = new StringBuilder("[" + loggerName + "] " + count + " " + uuid + " | ");
        if (userName != null) {
            result.append(userName).append(" | ");
        }
        result.append(msg);
        return result.toString();
    }

    public LoggerSlf4j(String loggerName) {
        this.loggerName = loggerName;
    }

    public LoggerSlf4j(String loggerName, String userName) {
        this.loggerName = loggerName;
        this.userName = userName;
    }

}