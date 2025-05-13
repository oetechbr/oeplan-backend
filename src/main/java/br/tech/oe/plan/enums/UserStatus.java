package br.tech.oe.plan.enums;

public enum UserStatus {
    ACTIVE(1),
    INACTIVE(2),
    SUSPENDED(3),
    INVITED(4);

    private final long code;

    UserStatus(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}