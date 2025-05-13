package br.tech.oe.plan.enums;

public enum TaskPriority {
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final long code;

    TaskPriority(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}
