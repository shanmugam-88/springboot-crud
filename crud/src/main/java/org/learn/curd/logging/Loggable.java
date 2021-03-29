package org.learn.curd.logging;

import org.slf4j.Logger;

public abstract class Loggable {

    public void runLogger(Logger logger, final LogLevel logLevel,String message) {
        switch (logLevel) {
            case ERROR:
                if(logger.isErrorEnabled()) {
                    logger.error(message);
                }
                break;
            case WARN:
                if(logger.isWarnEnabled()) {
                    logger.warn(message);
                }
                break;
            case INFO:
                if(logger.isInfoEnabled()) {
                    logger.info(message);
                }
                break;
            case TRACE:
                if(logger.isTraceEnabled()) {
                    logger.trace(message);
                }
                break;
            default:
                if(logger.isDebugEnabled()) {
                    logger.trace(message);
                }
                break;
        }
    }
}
