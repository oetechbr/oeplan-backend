package br.tech.oe.plan.validation;

import br.tech.oe.plan.validation.constraints.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private int minLength;
    private boolean requireUppercase;
    private boolean requireSpecialChar;
    private boolean requireDigit;

    @Override
    public void initialize(Password constraint) {
        this.minLength = constraint.minLength();
        this.requireUppercase = constraint.requireUppercase();
        this.requireSpecialChar = constraint.requireSpecialChar();
        this.requireDigit = constraint.requireDigit();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;

        if (password.length() < minLength) return false;

        if (requireUppercase && !password.matches(".*[A-Z].*")) return false;

        if (requireDigit && !password.matches(".*\\d.*")) return false;

        return !requireSpecialChar || password.matches(".*[!@#$%^&*()\\-_=+{};:,<.>].*");
    }
}
