package br.tech.oe.plan.enums;

public enum UserStatus {
    ACTIVE(1), // Fully registered
    INACTIVE(2), // User deactivated
    SUSPENDED(3), // Temporarily blocked
    INVITED(4), // User was invited but hasn't accepted
    PENDING(5), // User was created but not invited
    INCOMPLETE(6); // Active user but missing required profile information

    private final long code;

    UserStatus(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}