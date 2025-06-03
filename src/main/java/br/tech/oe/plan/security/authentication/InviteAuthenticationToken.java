package br.tech.oe.plan.security.authentication;

import br.tech.oe.plan.model.UserModel;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;

public class InviteAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 620L;

    private final UserModel authenticatedUser;

    public InviteAuthenticationToken(Collection<? extends GrantedAuthority> authorities, UserModel user) {
        super(authorities);
        this.authenticatedUser = user;
    }

    public InviteAuthenticationToken(UserModel user) {
        super(user.getAuthorities());
        this.authenticatedUser = user;
    }

    @Override
    public Object getCredentials() {
        return authenticatedUser.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return authenticatedUser;
    }
}
