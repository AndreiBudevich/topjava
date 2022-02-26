package ru.javawebinar.topjava;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyJUnitStopWatch extends Stopwatch {
    private static final Logger log = LoggerFactory.getLogger(MyJUnitStopWatch.class);
    private static final ArrayList<String> messageBuffers = new ArrayList<>();

    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        String message = String.format("Test %s %s, spent %d microseconds\n", testName, status, TimeUnit.NANOSECONDS.toMicros(nanos));
        log.info(message);
        messageBuffers.add(message);
    }

    public static void runAddAllMessageInLogAfterAllTest() {
        for (String message : messageBuffers) {
            log.info(message);
        }
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "finished", nanos);
    }
}