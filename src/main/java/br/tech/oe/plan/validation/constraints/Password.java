package br.tech.oe.plan.validation.constraints;

import br.tech.oe.plan.validation.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "Password must be at least {minLength} characters long, contain at least one uppercase letter, one digit, and one special character.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int minLength() default 12;

    boolean requireUppercase() default true;

    boolean requireSpecialChar() default true;

    boolean requireDigit() default true;
}