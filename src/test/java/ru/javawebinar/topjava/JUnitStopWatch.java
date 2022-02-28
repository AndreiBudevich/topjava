package ru.javawebinar.topjava;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUnitStopWatch extends Stopwatch {
    private static final Logger log = LoggerFactory.getLogger(JUnitStopWatch.class);
    private static String messageAll="\n";

    private static void logInfo(Description description, long nanos) {
        String testName = description.getMethodName();
        String message = String.format("%24s %4d ms", testName, TimeUnit.NANOSECONDS.toMillis(nanos));
        log.info(message);
        messageAll += message + "\n";
    }

    public static void getFinalResponseAllTesTime() {
        log.info(messageAll);
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, nanos);
    }
}