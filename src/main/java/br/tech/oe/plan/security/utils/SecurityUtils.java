package br.tech.oe.plan.security.utils;

import br.tech.oe.plan.enums.UserRole;
import br.tech.oe.plan.exception.ForbiddenException;
import br.tech.oe.plan.model.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private SecurityUtils() {
    }

    public static UserModel getAuthenticatedOrThrow() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new ForbiddenException("Access denied");
        }
        return (UserModel) authentication.getPrincipal();
    }

    public static boolean isAdminOrDirector() {
        var auth = getAuthenticatedOrThrow();
        return auth.getRole() == UserRole.ADMIN || auth.getRole() == UserRole.DIRECTOR;
    }
}
