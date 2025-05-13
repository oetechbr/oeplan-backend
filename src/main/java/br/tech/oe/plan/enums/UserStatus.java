package br.tech.oe.plan.enums;

public enum UserStatus {
    ACTIVE(1),
    INACTIVE(2),
    SUSPENDED(3),
    INVITED(4);

    private final int code;

    UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
