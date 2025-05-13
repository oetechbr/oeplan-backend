package br.tech.oe.plan.enums;

public enum TaskStatus {
    PENDING(1),
    IN_PROGRESS(2),
    DONE(3);

    private final long code;

    TaskStatus(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}
