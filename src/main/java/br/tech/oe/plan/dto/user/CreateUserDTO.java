package br.tech.oe.plan.dto.user;

import br.tech.oe.plan.enums.UserRole;
import br.tech.oe.plan.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserDTO implements Serializable {
    @NotBlank
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private UserRole role;

    private UserStatus status = UserStatus.INVITED;

    private String phone;

    private String gender;

    private LocalDateTime birthDate;

    @NotNull
    private String department;

    @NotNull
    private String titlePosition;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitlePosition() {
        return titlePosition;
    }

    public void setTitlePosition(String titlePosition) {
        this.titlePosition = titlePosition;
    }
}
