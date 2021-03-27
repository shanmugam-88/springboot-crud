package org.learn.curd.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogFactory {
    public static BaseLogger getLogger(Class classType) {
        Logger logger = LoggerFactory.getLogger(classType);
        return new BaseLogger(logger);
    }
}
