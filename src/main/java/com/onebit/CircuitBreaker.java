package com.onebit;

public class CircuitBreaker {

    private enum State {
        CLOSED, OPEN, HALF_OPEN
    }

    private State state = State.CLOSED;
    private int failureCount = 0;
    private final int failureThreshold;
    private final long timeout;
    private long lastFailureTime = 0;

    public CircuitBreaker(int failureThreshold, long timeout) {
        this.failureThreshold = failureThreshold;
        this.timeout = timeout;
    }

    public boolean allowRequest() {
        if (state == State.OPEN) {
            if (System.currentTimeMillis() - lastFailureTime > timeout) {
                state = State.HALF_OPEN;
                return true; // Permitir un reintento
            }
            return false; // Circuito abierto, no permitir llamadas
        }
        return true; // Circuito cerrado o en estado HALF_OPEN
    }

    public void recordSuccess() {
        state = State.CLOSED;
        failureCount = 0;
    }

    public void recordFailure() {
        failureCount++;
        lastFailureTime = System.currentTimeMillis();

        if (failureCount >= failureThreshold) {
            state = State.OPEN;
        }
    }
}
