package br.tech.oe.plan.runners.seeder;

import br.tech.oe.plan.enums.UserRole;
import br.tech.oe.plan.enums.UserStatus;
import br.tech.oe.plan.model.UserModel;
import br.tech.oe.plan.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void seed() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            UserModel user = new UserModel();
            user.setFirstName("Admin");
            user.setLastName("OEPLan");
            user.setUsername("admin");
            user.setEmail("admin.oeplan@theproject.id");
            user.setPassword(passwordEncoder.encode("admin.oeplan@theproject.id"));
            user.setRole(UserRole.ADMIN);
            user.setStatus(UserStatus.ACTIVE);
            user.setTitlePosition("Administrator");
            user.setDepartment("Administration");
            user.setEmailVerified(true);
            userRepository.save(user);
        }
    }
}