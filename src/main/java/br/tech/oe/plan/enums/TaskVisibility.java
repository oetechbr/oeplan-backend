package br.tech.oe.plan.enums;

public enum TaskVisibility {
    PUBLIC(1),
    PRIVATE(2),
    TEAM(3);

    private final long code;

    TaskVisibility(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}
