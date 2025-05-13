package br.tech.oe.plan.enums;

public enum UserRole {
    ADMIN(1),
    DIRECTOR(2),
    COORDINATOR(3),
    TEACHER(4);

    private final long code;

    UserRole(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}