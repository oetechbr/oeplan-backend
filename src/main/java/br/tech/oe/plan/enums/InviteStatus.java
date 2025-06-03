package br.tech.oe.plan.enums;

public enum InviteStatus {
    PENDING(1),
    ACCEPTED(2),
    EXPIRED(3),
    REVOKED(4);

    private final long code;

    InviteStatus(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}

