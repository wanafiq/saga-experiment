package com.example.orchestratorservice.common.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

    public static void printLog(String topicName, Object payload, boolean complete) {
        if (complete) {
            log.info("{} completed", topicName);
            log.info("Payload: {} \n", payload);
            return;
        }

        log.info("Processing {}", topicName);
        log.info("Payload: {} \n", payload);
    }

    public static void printErrorLog(String topicName, String message) {
        log.info("Error while processing {}: {}\n", topicName, message);
    }

    public static void printErrorLog(String topicName, Exception e) {
        log.info("Error while processing {}: {}\n", topicName, e.getMessage());
    }
}
