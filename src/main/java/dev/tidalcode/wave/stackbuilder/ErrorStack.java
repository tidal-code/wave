package dev.tidalcode.wave.stackbuilder;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ErrorStack {

    public String constructedError(String errorDetail, StackTraceElement[] stackTrace) {
        StringBuilder errorMessage = new StringBuilder();

        errorMessage.append(System.lineSeparator());
        errorMessage.append("Details:");
        errorMessage.append(System.lineSeparator());
        errorMessage.append(errorDetail);
        errorMessage.append(System.lineSeparator());

        AtomicReference<AtomicInteger> i = new AtomicReference<>(new AtomicInteger());
        AtomicInteger stackBuilderNumber = getStackBuilderNumber(stackTrace);

        Arrays.stream(stackTrace).forEach(e -> {
            if (i.get().get() > stackBuilderNumber.get()) {
                return;
            }

            if (i.get().get() == stackBuilderNumber.get()) {
                errorMessage.append("at ");
                errorMessage.append(e.toString());
                errorMessage.append(System.lineSeparator());
            }
            i.get().getAndIncrement();
        });

        return errorMessage.toString();
    }

    private AtomicInteger getStackBuilderNumber(StackTraceElement[] stackTrace) {
        AtomicInteger stackBuilderNumber = new AtomicInteger(0);
        for (StackTraceElement e : stackTrace) {
            if (e.toString().contains("NativeMethodAccessorImpl.invoke0")) {
                stackBuilderNumber.decrementAndGet();
                break;
            }
            stackBuilderNumber.getAndIncrement();
        }
        return stackBuilderNumber;
    }

}
