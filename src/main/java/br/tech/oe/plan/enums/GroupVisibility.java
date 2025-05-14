package br.tech.oe.plan.enums;

public enum GroupVisibility {
    PUBLIC(1),
    PRIVATE(2),
    SHARED(3);

    private final long code;

    GroupVisibility(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}
