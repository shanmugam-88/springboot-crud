package org.learn.curd.logging;

import org.slf4j.Logger;

public class BaseLogger extends Loggable {

    private Logger logger;

    public void log(LogLevel logLevel,String message) {
        runLogger(this.getLogger(),logLevel,message);
    }

    public BaseLogger(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
